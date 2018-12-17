package com.lesports.qmt.web.api.core.service.impl;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Stopwatch;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.lesports.LeConstants;
import com.lesports.api.common.CallerParam;
import com.lesports.api.common.Platform;
import com.lesports.api.common.PubChannel;
import com.lesports.exception.VrsAccessException;
import com.lesports.id.client.LeIdApis;
import com.lesports.jersey.core.LeStatus;
import com.lesports.jersey.exception.LeWebApplicationException;
import com.lesports.model.*;
import com.lesports.model.ScheduleUrl;
import com.lesports.qmt.config.api.dto.TTag;
import com.lesports.qmt.sbc.api.dto.TVideo;
import com.lesports.qmt.web.api.core.cache.impl.FallbackCache;
import com.lesports.qmt.web.api.core.cache.impl.LiveCache;
import com.lesports.qmt.web.api.core.cache.impl.VrsCache;
import com.lesports.qmt.web.api.core.service.VideoPlayer2Service;
import com.lesports.qmt.web.api.core.util.CacheContainer;
import com.lesports.qmt.web.api.core.util.Constants;
import com.lesports.qmt.web.api.core.util.PlayerConstants;
import com.lesports.qmt.web.api.core.vo.*;
import com.lesports.utils.*;
import com.lesports.utils.math.LeNumberUtils;
import com.lesports.utils.pojo.PlayerErrorCode;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.TimeUnit;

//import com.lesports.api.common.PubChannel;
//import com.lesports.sms.api.common.Platform;
//import com.lesports.sms.api.common.TTag;
//import com.lesports.sms.api.vo.TVideo;
//import com.lesports.sms.api.web.core.cache.impl.FallbackCache;
//import com.lesports.sms.api.web.core.cache.impl.LiveCache;
//import com.lesports.sms.api.web.core.cache.impl.VrsCache;
//import com.lesports.sms.api.web.core.service.VideoPlayer2Service;
//import com.lesports.sms.api.web.core.util.CacheContainer;
//import com.lesports.sms.api.web.core.util.Constants;
//import com.lesports.sms.api.web.core.util.PlayerConstants;

/**
 * Created by lufei1 on 2015/7/7.
 */
@Service("VideoPlayer2Service")
public class VideoPlayer2ServiceImpl extends AbstractService implements VideoPlayer2Service {

    private static final Logger logger = LoggerFactory.getLogger(VideoPlayer2ServiceImpl.class);

    @Resource
    private LiveCache liveCache;
    @Resource
    private VrsCache vrsCache;
    @Resource
    private FallbackCache fallbackCache;

    private boolean SWITCH = LeProperties.getBoolean("live.1080p.pay", true);

    private static final String CIBN_NONLETV_LIVE_EPL = "英超";

    private static final List<Long> CIBN_NONLETV_VIDEO_EPL = Lists.newArrayList(100004008l, 101520008l, 101608008l, 101609008l);

    @Override
    public VodRes dealVod(VodReq videoOnDemandReq) throws VrsAccessException {
        logger.debug("[app] [videoPlayer] [dealVideoOnDemand] [param:{}]", JSON.toJSON(videoOnDemandReq));
        Stopwatch stopwatch = Stopwatch.createStarted();
        VodRes videoOnDemandRes = new VodRes();
        Video video = vrsCache.getVideo(videoOnDemandReq.getVid());
        if (video == null) {
            try {
                video = MmsApis.getVideo(videoOnDemandReq.getVid());
            } catch (Exception e) {
                logger.error("get video from mms error", e);
                videoOnDemandRes.setStatus(PlayerErrorCode.MMS_ACCESS_ERROR.getCode());
                return videoOnDemandRes;
            }
            if (null == video || video.getDeleted() == 1) {
                logger.warn("[app] [videoPlayer] [dealVideoOnDemand] [video off line] [vid:{}]", videoOnDemandReq.getVid());
                videoOnDemandRes.setStatus(PlayerErrorCode.BUSI_VIDEO_OFFLINE.getCode());
                return videoOnDemandRes;
            }
            vrsCache.saveVideo(video);
        }

        String platform = Constants.PLAY_PLATFORM_MAP.get(getPlatFormFromCaller(videoOnDemandReq.getCaller()));
        //无版权视频下线
        if (video.getPlayPlatform() == null || CollectionUtils.isEmpty(video.getPlayPlatform().keySet()) ||
                !video.getPlayPlatform().keySet().contains(platform)) {
            logger.warn("[app] [videoPlayer] [dealVideoOnDemand] [video offline] [vid:{}]", videoOnDemandReq.getVid());
            videoOnDemandRes.setStatus(PlayerErrorCode.BUSI_VIDEO_OFFLINE.getCode());
            return videoOnDemandRes;
        }

        String mid = StringUtils.isNotBlank(video.getMid()) ? video.getMid().substring(1, video.getMid().length() - 1) : "";//mid前面带有一个逗号

        ChainUrlResponse chainUrlResponse = null;
        try {
            chainUrlResponse = MmsApis.getChainUrl(videoOnDemandReq, mid);
        } catch (Exception e) {
            logger.error("getChainUrl from mms error", e);
            videoOnDemandRes.setStatus(PlayerErrorCode.MMS_ACCESS_ERROR.getCode());
            return videoOnDemandRes;
        }
        //媒资返回数据错误
        if (chainUrlResponse == null) {
            logger.warn("[app] [videoPlayer] [dealVideoOnDemand] [request chain param error] [vid:{}]", videoOnDemandReq.getVid());
            videoOnDemandRes.setStatus(PlayerErrorCode.MMS_RES_ERROR.getCode());
            return videoOnDemandRes;
        }
        //屏蔽禁播
        if (ChainUrlResponse.PLAY_STATUS_NO == chainUrlResponse.getPlayStatus()) {
            logger.warn("[app] [videoPlayer] [dealVideoOnDemand] [video cannot play] [vid:{}]", videoOnDemandReq.getVid());
            videoOnDemandRes.setStatus(PlayerErrorCode.BUSI_VOD_IP_HAIWAI.getCode());
            return videoOnDemandRes;
        }
        //防盗链返回数据错误
        if (CollectionUtils.isEmpty(chainUrlResponse.getData())) {
            logger.warn("[app] [videoPlayer] [dealVideoOnDemand] [chain response data error] [vid:{}]", videoOnDemandReq.getVid());
            videoOnDemandRes.setStatus(PlayerErrorCode.MMS_RES_ERROR.getCode());
            return videoOnDemandRes;
        }

        ChainUrl chainUrl = chainUrlResponse.getData().get(0);
        Map<String, ScheduleUrl> scheduleUrlMap = Maps.newHashMap();

//        String uid = "";
//        if (StringUtils.isNotBlank(videoOnDemandReq.getToken())) {
//            try {
//                uid = SSOApi.checkToken(videoOnDemandReq.getToken());
//            } catch (Exception e) {
//                logger.error("get user info error. token : {}", videoOnDemandReq.getToken(), e);
//                videoOnDemandRes.setStatus(PlayerErrorCode.USER_RES_ERROR.getCode());
//                return videoOnDemandRes;
//            }
//        } else {
//            uid = videoOnDemandReq.getUid();
//        }
//
//        int playStatus = getPlayStatus(uid, videoOnDemandReq.getDevicekey(), videoOnDemandReq.getTerminal(), videoOnDemandReq.getFlag());
        videoOnDemandRes.setStatus(PlayerErrorCode.SUCCESS.getCode());
        //
        for (ChainUrlInfo chainUrlInfo : chainUrl.getInfos()) {
            ScheduleUrl scheduleUrl = new ScheduleUrl();
            //会员可以观看高码流
//            if (SWITCH && PlayerConstants.VOD_HIGH_VTYPE.contains(chainUrlInfo.getVtype())) {
//                scheduleUrl.setCode(playStatus);
//                scheduleUrl.setIsPay(1);
//            } else {
            scheduleUrl.setCode(1);
            scheduleUrl.setIsPay(0);
//            }
            //414移动端暂时不对点播1080p付费
//            if (scheduleUrl.getCode() == 1) {
            StringBuffer sbMain = new StringBuffer(chainUrlInfo.getMainUrl());
            scheduleUrl.setMainUrl(sbMain.toString());

            StringBuffer sb0 = new StringBuffer(chainUrlInfo.getBackUrl0());
            scheduleUrl.setBackUrl0(sb0.toString());

            StringBuffer sb1 = new StringBuffer(chainUrlInfo.getBackUrl1());
            scheduleUrl.setBackUrl1(sb1.toString());


            StringBuffer sb2 = new StringBuffer(chainUrlInfo.getBackUrl2());
            scheduleUrl.setBackUrl2(sb2.toString());
//            }
            scheduleUrlMap.put(PlayerConstants.vodVTypeMap.get(chainUrlInfo.getVtype()), scheduleUrl);
        }

        videoOnDemandRes.setInfos(scheduleUrlMap);
        stopwatch.stop();
        logger.info("[app] [videoPlayer] [dealVideoOnDemand] [elapsed:{}]", stopwatch.elapsed(TimeUnit.MILLISECONDS));
        return videoOnDemandRes;
    }

    @Override
    public VodRes dealVodWithFallback(VodReq vodReq) {
        VodRes vodRes = null;
        try {
            vodRes = dealVod(vodReq);
        } catch (Exception e) {
            logger.error("deal vod error.vodReq : {}", vodReq, e);
        }
        if (vodRes == null) {
            logger.info("app dealVod begin to enter fallback.vodReq : {}", vodReq);
            Map<String, String> paramMap = Maps.newHashMap();
            paramMap.put("vid", vodReq.getVid());
            vodRes = fallbackCache.findByKey(Platform.MOBILE.name(), LeConstants.METHOD_APP_VOD, paramMap, VodRes.class);
        }
        return vodRes;
    }

    @Override
    public LiveVideoResPC dealLive4PC(String liveId, String splatId, CallerParam callerParam) {
        LiveVideoResPC liveVideoResPC = LiveApis.getLiveVideoResPc(splatId, liveId);
        if (null != liveVideoResPC && LeNumberUtils.toInt(liveVideoResPC.getIsPay()) == 1) {
            //播放平台跟支付平台都支持才是付费
            if (null == liveVideoResPC.getPayPlatForm() || !liveVideoResPC.getPayPlatForm().contains(splatId)) {
                liveVideoResPC.setIsPay(0);
            }
        }
        if (null != liveVideoResPC) {
            LiveStreamResponse liveStreamResponse = LiveApis.getLiveStream(String.valueOf(liveVideoResPC.getSelectId()), splatId, liveId);
            if (null != liveStreamResponse) {
                liveVideoResPC.setRows(liveStreamResponse.getRows());
            }
        }
        return liveVideoResPC;
    }

    @Override
    public VodRes dealVod4Drm(VodReq videoOnDemandReq, CallerParam callerParam) throws VrsAccessException {
        logger.debug("[app] [videoPlayer] [dealVod4Drm] [param:{}]", JSON.toJSON(videoOnDemandReq));
        Stopwatch stopwatch = Stopwatch.createStarted();
        VodRes videoOnDemandRes = new VodRes();
        //用户ID
        String uid = "";
        //tv端用户校验及互踢
        if (videoOnDemandReq.getCaller() == 1051) {
            if (StringUtils.isNotBlank(videoOnDemandReq.getToken())) {
                try {
                    uid = SSOApi.checkToken(videoOnDemandReq.getToken());
                } catch (Exception e) {
                    logger.error("get user info error. token : {}", videoOnDemandReq.getToken(), e);
                    videoOnDemandRes.setStatus(PlayerErrorCode.USER_RES_ERROR.getCode());
                    return videoOnDemandRes;
                }
                //tv互踢
                Boolean landing = UserApis.checkTvLogin(uid, videoOnDemandReq.getFlag(), videoOnDemandReq.getDevicekey(), videoOnDemandReq.getTerminal());
                if (landing) {
                    videoOnDemandRes.setStatus(PlayerErrorCode.BUSI_USER_HAS_LOGIN.getCode());
                    return videoOnDemandRes;
                }
            }
        }


        Video video;
        try {
            video = CacheContainer.VIDEO_LOADING_CACHE.get(videoOnDemandReq.getVid());
        } catch (Exception e) {
            logger.error("get video from mms error {}",videoOnDemandReq.getVid(), e);
            videoOnDemandRes.setStatus(PlayerErrorCode.MMS_ACCESS_ERROR.getCode());
            return videoOnDemandRes;
        }
        if (null == video || video.getDeleted() == 1) {
            logger.warn("[app] [videoPlayer] [dealVideoOnDemand] [video off line] [vid:{}]", videoOnDemandReq.getVid());
            videoOnDemandRes.setStatus(PlayerErrorCode.BUSI_VIDEO_OFFLINE.getCode());
            return videoOnDemandRes;
        }

        logger.debug("[app] [videoPlayer] [getVideo] [elapsed:{}]", stopwatch.elapsed(TimeUnit.MILLISECONDS));

        String playPlatform = Constants.PLAY_PLATFORM_MAP.get(getPlatFormFromCaller(callerParam.getCallerId()));
        //无版权视频下线
        if (null == video.getPlayPlatform() || CollectionUtils.isEmpty(video.getPlayPlatform().keySet()) ||
                !video.getPlayPlatform().keySet().contains(playPlatform)) {
            logger.warn("[app] [videoPlayer] [dealVideoOnDemand] [video offline] [vid:{}]", videoOnDemandReq.getVid());
            videoOnDemandRes.setStatus(PlayerErrorCode.BUSI_VIDEO_OFFLINE.getCode());
            return videoOnDemandRes;
        }

        List<Long> tagIds = Lists.newArrayList();
        //国广非乐视TV过滤英超频道 和 videoInfo中添加视频的tag信息
        if (callerParam.getCallerId() == 1051) {
            TVideo tVideo = null;
            try {
                tVideo = CacheContainer.TVIDEO_CACHE.get(new GetTVideoCacheParam(LeIdApis.convertMmsVideoIdToLeSportsVideoId(LeNumberUtils.toLong(videoOnDemandReq.getVid())), callerParam.getCallerId()));
            } catch (Exception e) {
                logger.error("get video from mms error", e);
                videoOnDemandRes.setStatus(PlayerErrorCode.MMS_ACCESS_ERROR.getCode());
                return videoOnDemandRes;
            }

            if (tVideo != null && CollectionUtils.isNotEmpty(tVideo.getTags())) {
                //国广非乐视TV过滤英超频道
                if (callerParam.getPubChannel() != null && callerParam.getPubChannel() != PubChannel.LETV) {
                    for (TTag tag : tVideo.getTags()) {
                        if (CIBN_NONLETV_VIDEO_EPL.contains(tag.getId())) {
                            logger.warn("[cibn] [videoPlayer] [dealVod4Drm] [epl filter ] [vid:{}] ", videoOnDemandReq.getVid());
                            videoOnDemandRes.setStatus(PlayerErrorCode.CIBN_VIDEO_EPL_ERROR.getCode());
                            return videoOnDemandRes;
                        }
                    }
                }
                for (TTag tag : tVideo.getTags()) {
                    tagIds.add(tag.getId());
                }
            }
        }

        //构建视频信息
        if (videoOnDemandReq.getCaller() == 3002 || videoOnDemandReq.getCaller() == 1051) {
            createVideoInfo(videoOnDemandRes, video, tagIds);
        }

        String mid = StringUtils.isNotBlank(video.getMid()) ? video.getMid().substring(1, video.getMid().length() - 1) : "";//mid前面带有一个逗号

        //drm标示
        videoOnDemandRes.setDrmFlag(MmsApis.isDrm(video));
        //付费标示
        videoOnDemandRes.setIsPay(MmsApis.isPay(video, getPlatFormFromCaller(videoOnDemandReq.getCaller())));

        if (videoOnDemandRes.getDrmFlag() == 1) {
            videoOnDemandReq.setVtype(PlayerConstants.drmVodVType);
        } else {
            videoOnDemandReq.setVtype(PlayerConstants.vodVType);
        }

        logger.debug("[app] [videoPlayer] [makeVideo] [elapsed:{}]", stopwatch.elapsed(TimeUnit.MILLISECONDS));

        ChainUrlResponse chainUrlResponse;
        try {
            chainUrlResponse = CacheContainer.VIDEO_CHAIN_LOADING_CACHE.get(new GetVrsChainCacheParam(videoOnDemandReq, mid));
        } catch (Exception e) {
            logger.error("getChainUrl from mms error", e);
            videoOnDemandRes.setStatus(PlayerErrorCode.MMS_ACCESS_ERROR.getCode());
            return videoOnDemandRes;
        }
        logger.debug("[app] [videoPlayer] [getChain] [elapsed:{}]", stopwatch.elapsed(TimeUnit.MILLISECONDS));

        //媒资返回数据错误
        if (chainUrlResponse == null) {
            logger.warn("[app] [videoPlayer] [dealVideoOnDemand] [request chain param error] [vid:{}]", videoOnDemandReq.getVid());
            videoOnDemandRes.setStatus(PlayerErrorCode.MMS_RES_ERROR.getCode());
            return videoOnDemandRes;
        }
        //屏蔽禁播
        if (ChainUrlResponse.PLAY_STATUS_NO == chainUrlResponse.getPlayStatus()) {
            logger.warn("[app] [videoPlayer] [dealVideoOnDemand] [video cannot play] [vid:{}]", videoOnDemandReq.getVid());
            videoOnDemandRes.setStatus(PlayerErrorCode.BUSI_VOD_IP_HAIWAI.getCode());
            return videoOnDemandRes;
        }
        //防盗链返回数据错误
        if (CollectionUtils.isEmpty(chainUrlResponse.getData())) {
            logger.warn("[app] [videoPlayer] [dealVideoOnDemand] [chain response data error] [vid:{}]", videoOnDemandReq.getVid());
            videoOnDemandRes.setStatus(PlayerErrorCode.MMS_RES_ERROR.getCode());
            return videoOnDemandRes;
        }

        ChainUrl chainUrl = chainUrlResponse.getData().get(0);
        Map<String, ScheduleUrl> scheduleUrlMap = Maps.newHashMap();

        videoOnDemandRes.setStatus(PlayerErrorCode.SUCCESS.getCode());

        for (ChainUrlInfo chainUrlInfo : chainUrl.getInfos()) {
            ScheduleUrl scheduleUrl = new ScheduleUrl();

            setPlayCode(videoOnDemandReq, videoOnDemandRes, chainUrlInfo, scheduleUrl, uid);

            if (scheduleUrl.getCode() == 1) {
                scheduleUrl.setStorePath(chainUrlInfo.getStorePath());
                scheduleUrl.setEncodeId(chainUrlInfo.getEncodeId());
                StringBuffer sbMain = new StringBuffer(chainUrlInfo.getMainUrl());
                scheduleUrl.setMainUrl(sbMain.toString());

                StringBuffer sb0 = new StringBuffer(chainUrlInfo.getBackUrl0());
                scheduleUrl.setBackUrl0(sb0.toString());

                StringBuffer sb1 = new StringBuffer(chainUrlInfo.getBackUrl1());
                scheduleUrl.setBackUrl1(sb1.toString());


                StringBuffer sb2 = new StringBuffer(chainUrlInfo.getBackUrl2());
                scheduleUrl.setBackUrl2(sb2.toString());
            }
            if (videoOnDemandRes.getDrmFlag() == 1) {
                scheduleUrlMap.put(PlayerConstants.drmVodVTypeMap.get(chainUrlInfo.getVtype()), scheduleUrl);
            } else {
                scheduleUrlMap.put(PlayerConstants.vodVTypeMap.get(chainUrlInfo.getVtype()), scheduleUrl);
            }
        }
        videoOnDemandRes.setInfos(scheduleUrlMap);

        //付费鉴权（付费、drm and 付费、非drm ）
        if (videoOnDemandRes.getIsPay() == 1) {
            if ((videoOnDemandReq.getCaller() == 3001 || videoOnDemandReq.getCaller() == 3002
                    || videoOnDemandReq.getCaller() == 3003 || videoOnDemandReq.getCaller() == 3004) &&
                    StringUtils.isBlank(videoOnDemandReq.getToken())) {
                videoOnDemandRes.setStatus(PlayerErrorCode.BUSI_USER_NO_LOGIN.getCode());
                return videoOnDemandRes;
            }
        } else if (videoOnDemandRes.getDrmFlag() == 1) {
            if (StringUtils.isBlank(videoOnDemandReq.getToken())) {
                videoOnDemandRes.setStatus(PlayerErrorCode.BUSI_USER_NO_LOGIN.getCode());
                return videoOnDemandRes;
            }
        }
        logger.debug("[app] [videoPlayer] [makeChain] [elapsed:{}]", stopwatch.elapsed(TimeUnit.MILLISECONDS));
        stopwatch.stop();
        logger.info("[app] [videoPlayer] [dealVideoOnDemand] [elapsed:{}]", stopwatch.elapsed(TimeUnit.MILLISECONDS));
        return videoOnDemandRes;
    }

    /**
     * set 码流播放状态和付费标示
     *
     * @param videoOnDemandReq
     * @param videoOnDemandRes
     * @param chainUrlInfo
     * @param scheduleUrl
     */
    private void setPlayCode(VodReq videoOnDemandReq, VodRes videoOnDemandRes, ChainUrlInfo chainUrlInfo, ScheduleUrl scheduleUrl, String uid) {
        //免费高码流付费
        if (videoOnDemandRes.getIsPay() == 0) {
            int playStatus = 0;
            if (LeNumberUtils.toInt(videoOnDemandReq.getHighVtypePay()) == 1
                    && PlayerConstants.VOD_HIGH_VTYPE.contains(chainUrlInfo.getVtype())) {
                if (StringUtils.isBlank(uid)) {
                    if (StringUtils.isNotBlank(videoOnDemandReq.getToken())) {
                        try {
                            uid = SSOApi.checkToken(videoOnDemandReq.getToken());
                        } catch (Exception e) {
                            logger.error("get user info error. token : {}", videoOnDemandReq.getToken(), e);
                        }
                    }
                }
                if (StringUtils.isNotBlank(uid)) {
                    playStatus = BossApi.getPlayStatus(uid, videoOnDemandReq.getDevicekey(), videoOnDemandReq.getTerminal(), videoOnDemandReq.getFlag());
                }
                scheduleUrl.setCode(playStatus);
                scheduleUrl.setIsPay(1);
            } else {
                scheduleUrl.setCode(1);
                scheduleUrl.setIsPay(0);
            }
        } else {
            scheduleUrl.setCode(1);
            scheduleUrl.setIsPay(videoOnDemandRes.getIsPay());
        }
    }

    @Override
    public PcPlayVo dealVod4PcDrm(VodReq vodReq, CallerParam callerParam) throws VrsAccessException {
        Stopwatch stopwatch = Stopwatch.createStarted();
        PcPlayVo pcPlayVo = new PcPlayVo();
        Video video = vrsCache.getVideo(vodReq.getVid());
        if (video == null) {
            try {
                video = MmsApis.getVideo(vodReq.getVid());
            } catch (Exception e) {
                logger.error("get video from mms error", e);
                pcPlayVo.setStatuscode(PlayerErrorCode.MMS_ACCESS_ERROR.getCode());
                return pcPlayVo;
            }
            if (null == video || video.getDeleted() == 1) {
                logger.warn("[app] [videoPlayer] [dealVod4PcDrm] [video off line] [vid:{}]", vodReq.getVid());
                pcPlayVo.setStatuscode(PlayerErrorCode.BUSI_VIDEO_OFFLINE.getCode());
                return pcPlayVo;
            }
            vrsCache.saveVideo(video);
        }

        String playPlatform = Constants.PLAY_PLATFORM_MAP.get(getPlatFormFromCaller(callerParam.getCallerId()));

        //无版权视频下线
        if (null == video.getPlayPlatform() || CollectionUtils.isEmpty(video.getPlayPlatform().keySet()) ||
                !video.getPlayPlatform().keySet().contains(playPlatform)) {
            logger.warn("[app] [videoPlayer] [dealVod4PcDrm] [video offline] [vid:{}]", vodReq.getVid());
            pcPlayVo.setStatuscode(PlayerErrorCode.BUSI_VIDEO_OFFLINE.getCode());
            return pcPlayVo;
        }

        String mid = StringUtils.isNotBlank(video.getMid()) ? video.getMid().substring(1, video.getMid().length() - 1) : "";//mid前面带有一个逗号

        //drm标示
        if (MapUtils.isNotEmpty(video.getDrmFlag())) {
            //思科drm
            if (StringUtils.isNotBlank(video.getDrmFlag().get(PlayerConstants.CISCO_DRM))) {
                pcPlayVo.setDrmFlag(1);
            }
        }

        //付费标示
        pcPlayVo.setIsPay(MmsApis.isPay(video, getPlatFormFromCaller(vodReq.getCaller())));
        //drm 或者 付费,未登录用户返回400
        if ((pcPlayVo.getDrmFlag() == 1 || pcPlayVo.getIsPay() == 1) && StringUtils.isEmpty(vodReq.getUid())) {
            throw new LeWebApplicationException("User fail login", LeStatus.PARAM_INVALID);
        }

        ChainUrlResponse chainUrlResponse;
        try {
            chainUrlResponse = CacheContainer.VIDEO_CHAIN_LOADING_CACHE.get(new GetVrsChainCacheParam(vodReq, mid));
        } catch (Exception e) {
            logger.error("getChainUrl from mms error", e);
            pcPlayVo.setStatuscode(PlayerErrorCode.MMS_ACCESS_ERROR.getCode());
            return pcPlayVo;
        }
        //媒资返回数据错误
        if (chainUrlResponse == null) {
            logger.warn("[app] [videoPlayer] [dealVod4PcDrm] [request chain param error] [vid:{}]", vodReq.getVid());
            pcPlayVo.setStatuscode(PlayerErrorCode.MMS_RES_ERROR.getCode());
            return pcPlayVo;
        }
        //屏蔽禁播
        if (ChainUrlResponse.PLAY_STATUS_NO == chainUrlResponse.getPlayStatus()) {
            logger.warn("[app] [videoPlayer] [dealVod4PcDrm] [video cannot play] [vid:{}]", vodReq.getVid());
            pcPlayVo.setStatuscode(PlayerErrorCode.BUSI_VOD_IP_HAIWAI.getCode());
            return pcPlayVo;
        }
        //防盗链返回数据错误
        if (CollectionUtils.isEmpty(chainUrlResponse.getData())) {
            logger.warn("[app] [videoPlayer] [dealVod4PcDrm] [chain response data error] [vid:{}]", vodReq.getVid());
            pcPlayVo.setStatuscode(PlayerErrorCode.MMS_RES_ERROR.getCode());
            return pcPlayVo;
        }

        ChainUrl chainUrl = chainUrlResponse.getData().get(0);

        Set<String> domainList = Sets.newHashSet();
        Map<String, Object> dispatchMap = Maps.newHashMap();
        String defaultEncodeId = "";
        String defaultStorePath = "";
        if (CollectionUtils.isNotEmpty(chainUrl.getInfos())) {
            ChainUrlInfo defaultChainUrlInfo = chainUrl.getInfos().get(0);
            for (ChainUrlInfo chainUrlInfo : chainUrl.getInfos()) {
                Map<String, Object> chainInfoMap = Maps.newHashMap();
                chainInfoMap.put("encodeId", chainUrlInfo.getEncodeId());
                chainInfoMap.put("storePath", chainUrlInfo.getStorePath());
                String mainUrl = chainUrlInfo.getMainUrl();
                chainInfoMap.put("url", StringUtils.substring(mainUrl, mainUrl.indexOf("/", 10), mainUrl.length()));
                dispatchMap.put(PlayerConstants.drmVodVTypeMap.get(chainUrlInfo.getVtype()), chainInfoMap);
                if (CollectionUtils.isEmpty(domainList)) {
                    domainList = createDomain(chainUrlInfo, chainInfoMap, domainList);
                }
                if (chainUrlInfo.getVtype().equals(vodReq.getDvtype())) {
                    defaultEncodeId = chainUrlInfo.getEncodeId();
                    defaultStorePath = chainUrlInfo.getStorePath();
                }
            }
            if (StringUtils.isEmpty(defaultEncodeId) && null != defaultChainUrlInfo) {
                defaultEncodeId = defaultChainUrlInfo.getEncodeId();
                defaultStorePath = defaultChainUrlInfo.getStorePath();
            }
        }

        pcPlayVo.setStatuscode(PlayerErrorCode.SUCCESS.getCode());
        Map<String, Object> playUrlMap = Maps.newHashMap();
        playUrlMap.put("pid", video.getPid());
        playUrlMap.put("vid", video.getId());
        playUrlMap.put("title", video.getNameCn());
        playUrlMap.put("mid", mid);
        playUrlMap.put("duration", video.getDuration());
        playUrlMap.put("domain", domainList);
        playUrlMap.put("dispatch", dispatchMap);
        pcPlayVo.setPlayurl(playUrlMap);
        pcPlayVo.setTrylook(String.valueOf(video.getIsPay()));

        ValidateRes validate = null;
        if (pcPlayVo.getIsPay() == 1) {
            //默认码流鉴权
            ValidateReq validateReq = new ValidateReq();
            if (pcPlayVo.getDrmFlag() == 1) {
                validateReq.setActivate(vodReq.getActivate());
                validateReq.setEncodeId(defaultEncodeId);
            }
            validateReq.setCountry(String.valueOf(PlayerConstants.countryMap.get(callerParam.getCountry())));
            validateReq.setFrom("141001");
            validateReq.setUserId(vodReq.getUid());
            validateReq.setStorepath(defaultStorePath);
            validateReq.setSplatId(String.valueOf(vodReq.getSplatid()));
            validateReq.setVid(String.valueOf(video.getId()));
            validateReq.setIsDrm(pcPlayVo.getDrmFlag());
            validate = BossApi.authVideoForDrmNew(validateReq);
        } else if (pcPlayVo.getIsPay() == 0 && pcPlayVo.getDrmFlag() == 1) {
            validate = TgsApis.genToken(vodReq.getUid(), defaultEncodeId, vodReq.getActivate());
        }

        //会员信息
        List<HashMap> vipInfo = BossApi.getHkVipInfoV2(vodReq.getUid(), PlayerConstants.countryMap.get(callerParam.getCountry()),
                vodReq.getTerminal());
        Map<String, Object> yuanxianMap = Maps.newHashMap();
        yuanxianMap.put("validate", validate);
        yuanxianMap.put("vip", vipInfo);
        yuanxianMap.put("uid", vodReq.getUid());
        pcPlayVo.setYuanxian(yuanxianMap);
        stopwatch.stop();
        logger.info("[app] [videoPlayer] [dealVod4PcDrm] [elapsed:{}]", stopwatch.elapsed(TimeUnit.MILLISECONDS));
        return pcPlayVo;
    }


    private Set createDomain(ChainUrlInfo chainUrlInfo, Map<String, Object> chainInfoMap, Set domainSet) {
        String mainUrl = chainUrlInfo.getMainUrl();
        String backUrl0 = chainUrlInfo.getBackUrl0();
        String backUrl1 = chainUrlInfo.getBackUrl1();
        String backUrl2 = chainUrlInfo.getBackUrl2();

        String mainDomain = StringUtils.substring(mainUrl, 0, mainUrl.indexOf("/", 10));
        String back0Domain = StringUtils.substring(backUrl0, 0, backUrl0.indexOf("/", 10));
        String back1Domain = StringUtils.substring(backUrl1, 0, backUrl1.indexOf("/", 10));
        String back2Domain = StringUtils.substring(backUrl2, 0, backUrl2.indexOf("/", 10));
        return Sets.newHashSet(mainDomain, back0Domain, back1Domain, back2Domain);
    }

    @Override
    public LiveVideoRes dealLiveVideo(LiveVideoReq liveVideoReq) {
        Stopwatch stopwatch = Stopwatch.createStarted();
        LiveVideoRes liveVideoRes = new LiveVideoRes();
        Map<String, LiveVideo> infos = Maps.newHashMap();
        String uid;
        if (StringUtils.isNotBlank(liveVideoReq.getToken())) {
            try {
                uid = SSOApi.checkToken(liveVideoReq.getToken());
            } catch (Exception e) {
                logger.error("get user info error. token : {}", liveVideoReq.getToken(), e);
                liveVideoRes.setStatus(PlayerErrorCode.USER_RES_ERROR.getCode());
                return liveVideoRes;
            }
        } else {
            uid = liveVideoReq.getUserId();
        }
        LiveRoom liveRoom = liveCache.getLiveRoom(liveVideoReq.getLiveId());
        if (null == liveRoom) {
            LiveRoomResponse liveRoomResponse = null;
            try {
                liveRoomResponse = LiveApis.getLiveRoom(liveVideoReq.getLiveId(), liveVideoReq.getSplatId());
            } catch (Exception e) {
                logger.error("getLiveRoom from live error", e);
                liveVideoRes.setStatus(PlayerErrorCode.LIVE_ACCESS_ERROR.getCode());
                return liveVideoRes;
            }
            if (liveRoomResponse == null || CollectionUtils.isEmpty(liveRoomResponse.getRows()) || liveRoomResponse.getRows().get(0).getSelectId() <= 0) {//StringUtils.isBlank(liveRoomResponse.getRows().get(0).getSelectId())) {
                logger.warn("[app] [videoPlayer] [dealLiveVideo] [get live room error ] [liveId:{}] ", liveVideoReq.getLiveId());
                liveVideoRes.setStatus(PlayerErrorCode.LIVE_RES_ERROR.getCode());
                return liveVideoRes;
            }
            liveRoom = liveRoomResponse.getRows().get(0);
            liveCache.saveLiveRoom(liveVideoReq.getLiveId(), liveRoom);
        }
        LiveStreamResponse liveStreamResponse = liveCache.getLiveStream(String.valueOf(liveRoom.getSelectId()), liveVideoReq.getSplatId(), liveVideoReq.getWithCibnStreams(), liveVideoReq.getLiveId());
        if (liveStreamResponse == null) {
            try {
                liveStreamResponse = LiveApis.getLiveStream(String.valueOf(liveRoom.getSelectId()), liveVideoReq.getSplatId(), liveVideoReq.getWithCibnStreams(), liveVideoReq.getLiveId());
            } catch (Exception e) {
                logger.error("getLiveStream from live error", e);
                liveVideoRes.setStatus(PlayerErrorCode.LIVE_ACCESS_ERROR.getCode());
                return liveVideoRes;
            }

            if (liveStreamResponse == null || CollectionUtils.isEmpty(liveStreamResponse.getRows())) {
                logger.warn("[app] [videoPlayer] [dealLiveVideo] [get live stream error ] [liveId:{}] [selectedId:{}]", liveVideoReq.getLiveId(), liveRoom.getSelectId());
                liveVideoRes.setStatus(PlayerErrorCode.LIVE_RES_ERROR.getCode());
                return liveVideoRes;
            }
            liveCache.saveLiveStream(String.valueOf(liveRoom.getSelectId()), liveVideoReq.getSplatId(), liveVideoReq.getWithCibnStreams(), liveVideoReq.getLiveId(), liveStreamResponse);
        }

        int isPay = 0;
        if (liveRoom.getIsPay() == 1) {
            if (StringUtils.isNotBlank(liveRoom.getPayPlatForm())) {
                isPay = liveRoom.getPayPlatForm().contains(liveVideoReq.getSplatId()) ? 1 : 0;
            }
        }

        liveVideoRes.setLiveStartTime(liveRoom.getBeginTime());
        liveVideoRes.setIsPay(isPay);

        //收费
        if (isPay == 1) {
            LiveStream steam = liveStreamResponse.getRows().get(0);
            LiveValidateResponse liveValidateResponse = BossApi.authLiveValidate(liveVideoReq, liveRoom, steam.getStreamName(), uid);
            //1004已经试看过
            if (liveValidateResponse != null && liveValidateResponse.getError() != null && liveValidateResponse.getError().getCode().equals("1004")) {
                liveVideoRes.setStatus(PlayerErrorCode.BUSI_USER_TRY_SEED.getCode());
                return liveVideoRes;
            }
            //1004以外的错误返回鉴权不通过
            if (liveValidateResponse == null || "0".equals(liveValidateResponse.getStatus())) {
                liveVideoRes.setStatus(PlayerErrorCode.BUSI_LIVE_AUTH_FAIL.getCode());
                return liveVideoRes;
            }
            for (LiveStream liveStream : liveStreamResponse.getRows()) {
                if (PlayerConstants.liveVTypeList.contains(liveStream.getRateType())) {
                    LiveVideo liveVideo = new LiveVideo();
                    //无播放权限，直播鉴权不通过
                    if ("1".equals(liveValidateResponse.getStatus())) {
                        liveVideo.setCode(1);
                    } else {
                        liveVideo.setCode(0);
                    }
                    //会员的高清码流也需要标注ispay
                    if (SWITCH && PlayerConstants.LIVE_HIGH_VTYPE.contains(liveStream.getRateType())) {
                        liveVideo.setIsPay(1);
                    } else {
                        liveVideo.setIsPay(0);
                    }

                    if (liveVideo.getCode() == 1) {
                        liveVideo.setPrestart(liveValidateResponse.getPrestart());
                        liveVideo.setPreend(liveValidateResponse.getPreend());
                        liveVideo.setCurTime(liveValidateResponse.getCurTime());
                        liveVideo.setIsPre(LeNumberUtils.toBoolean(liveValidateResponse.isPre()) ? 1 : 0);

                        StringBuffer sb = new StringBuffer(liveStream.getStreamUrl());
                        buildLivePlayerParam(sb, liveVideoReq, isPay, liveValidateResponse, uid);
                        buildWechatUrl(sb, liveVideoReq, liveStream.getStreamName());
                        liveVideo.setUrl(sb.toString());
                    }

                    infos.put(liveStream.getRateType(), liveVideo);
                }
            }
        } else {
            int playStatus = BossApi.getPlayStatus(uid, liveVideoReq.getDevicekey(), liveVideoReq.getTerminal(), liveVideoReq.getFlag());
            for (LiveStream liveStream : liveStreamResponse.getRows()) {
                if (PlayerConstants.liveVTypeList.contains(liveStream.getRateType())) {
                    LiveVideo liveVideo = new LiveVideo();
                    //会员可以观看高码流
                    if (SWITCH && PlayerConstants.LIVE_HIGH_VTYPE.contains(liveStream.getRateType())) {
                        liveVideo.setCode(playStatus);
                        liveVideo.setIsPay(1);
                    } else {
                        liveVideo.setCode(1);
                        liveVideo.setIsPay(0);
                    }
                    if (liveVideo.getCode() == 1) {
                        StringBuffer sb = new StringBuffer(liveStream.getStreamUrl());
                        buildLivePlayerParam(sb, liveVideoReq, isPay, null, uid);
                        buildWechatUrl(sb, liveVideoReq, liveStream.getStreamName());
                        liveVideo.setUrl(sb.toString());
                    }

                    infos.put(liveStream.getRateType(), liveVideo);
                }
            }
        }
        if (!infos.isEmpty()) {
            liveVideoRes.setStatus(PlayerErrorCode.SUCCESS.getCode());
        }
        liveVideoRes.setInfos(infos);
        stopwatch.stop();
        logger.info("[app] [videoPlayer] [dealLiveVideo] [elapsed:{}]", stopwatch.elapsed(TimeUnit.MILLISECONDS));
        return liveVideoRes;
    }

    @Override
    public LiveVideoRes dealLive4Drm(LiveVideoReq liveVideoReq, CallerParam callerParam) {
        Stopwatch stopwatch = Stopwatch.createStarted();
        LiveVideoRes liveVideoRes = new LiveVideoRes();
        Map<String, LiveVideo> infos = Maps.newHashMap();

        //tv端用户校验及互踢
        String uid = "";
        if (callerParam.getCallerId() == 1051) {
            if (StringUtils.isNotBlank(liveVideoReq.getToken())) {
                try {
                    uid = SSOApi.checkToken(liveVideoReq.getToken());
                } catch (Exception e) {
                    logger.error("get user info error. token : {}", liveVideoReq.getToken(), e);
                    liveVideoRes.setStatus(PlayerErrorCode.USER_RES_ERROR.getCode());
                    return liveVideoRes;
                }
                //tv互踢
                Boolean landing = UserApis.checkTvLogin(uid, liveVideoReq.getFlag(), liveVideoReq.getDevicekey(), liveVideoReq.getTerminal());
                if (landing) {
                    liveVideoRes.setStatus(PlayerErrorCode.BUSI_USER_HAS_LOGIN.getCode());
                    return liveVideoRes;
                }
            }
        }

        LiveRoomResponse liveRoomResponse;
        try {
            liveRoomResponse = CacheContainer.LIVE_ROOM_LOADING_CACHE.get(new GetLiveRoomCacheParam(liveVideoReq.getLiveId(), liveVideoReq.getSplatId()));
        } catch (Exception e) {
            logger.error("getLiveRoom from live error", e);
            liveVideoRes.setStatus(PlayerErrorCode.LIVE_ACCESS_ERROR.getCode());
            return liveVideoRes;
        }
        if (liveRoomResponse == null || CollectionUtils.isEmpty(liveRoomResponse.getRows()) || liveRoomResponse.getRows().get(0).getSelectId() <= 0) {//StringUtils.isBlank(liveRoomResponse.getRows().get(0).getSelectId())) {
            logger.warn("[app] [videoPlayer] [dealLiveVideo] [get live room error ] [liveId:{}] ", liveVideoReq.getLiveId());
            liveVideoRes.setStatus(PlayerErrorCode.LIVE_RES_ERROR.getCode());
            return liveVideoRes;
        }

        //国广非乐视TV过滤英超频道
        //FIXME:CallerParam定义需要找路飞确认
//        if (callerParam.getCallerId() == 1051 && callerParam.getPubChannel() != null && callerParam.getPubChannel() != PubChannel.LETV) {
//            if (liveRoomResponse.getRows().get(0).getLevel2() != null && liveRoomResponse.getRows().get(0).getLevel2().equals(CIBN_NONLETV_LIVE_EPL)) {
//                logger.warn("[cibn] [videoPlayer] [dealLiveVideo] [epl filter ] [liveId:{}] ", liveVideoReq.getLiveId());
//                liveVideoRes.setStatus(PlayerErrorCode.CIBN_LIVE_EPL_ERROR.getCode());
//                return liveVideoRes;
//            }
//        }

        LiveRoom liveRoom = liveRoomResponse.getRows().get(0);
        logger.debug("[app] [videoPlayer] [getLiveRoom] [elapsed:{}]", stopwatch.elapsed(TimeUnit.MILLISECONDS));

        LiveStreamResponse liveStreamResponse;
        try {
            liveStreamResponse = CacheContainer.LIVE_STREAM_LOADING_CACHE.get(new GetLiveStreamCacheParam(String.valueOf(liveRoom.getSelectId()), liveVideoReq.getSplatId(), liveVideoReq.getWithCibnStreams(), liveVideoReq.getLiveId()));
        } catch (Exception e) {
            logger.error("getLiveStream from live error", e);
            liveVideoRes.setStatus(PlayerErrorCode.LIVE_ACCESS_ERROR.getCode());
            return liveVideoRes;
        }

        if (liveStreamResponse == null || CollectionUtils.isEmpty(liveStreamResponse.getRows())) {
            logger.warn("[app] [videoPlayer] [dealLiveVideo] [get live stream error ] [liveId:{}] [selectedId:{}]", liveVideoReq.getLiveId(), liveRoom.getSelectId());
            liveVideoRes.setStatus(PlayerErrorCode.LIVE_RES_ERROR.getCode());
            return liveVideoRes;
        }
        logger.debug("[app] [videoPlayer] [getLiveStream] [elapsed:{}]", stopwatch.elapsed(TimeUnit.MILLISECONDS));
        if (liveVideoReq.getCaller() == 3002 || liveVideoReq.getCaller() == 1051) {
            //构建直播信息
            createLiveInfo(liveVideoRes, liveStreamResponse.getRows());
        }

        int isPay = LiveApis.isPay(liveRoom, liveVideoReq.getSplatId());

        liveVideoRes.setLiveStartTime(liveRoom.getBeginTime());
        liveVideoRes.setScreenings(StringUtils.isNotBlank(liveRoom.getScreenings()) ? liveRoom.getScreenings() : "");
        liveVideoRes.setIsPay(isPay);
        if (liveRoom.getDrmFlag() != null && liveRoom.getDrmFlag().equals(String.valueOf(PlayerConstants.CISCO_DRM))) {
            liveVideoRes.setDrmFlag(1);
        }

        if (liveVideoRes.getIsPay() == 1) {
            if (liveVideoReq.getCaller() != 1003 && liveVideoReq.getCaller() != 1014 && liveVideoReq.getCaller() != 1016 && liveVideoReq.getCaller() != 1051) {
                if (StringUtils.isBlank(liveVideoReq.getToken())) {
                    liveVideoRes.setStatus(PlayerErrorCode.BUSI_USER_NO_LOGIN.getCode());
                    return liveVideoRes;
                }
            }
        } else if (liveVideoRes.getDrmFlag() == 1) {
            if (StringUtils.isBlank(liveVideoReq.getToken())) {
                liveVideoRes.setStatus(PlayerErrorCode.BUSI_USER_NO_LOGIN.getCode());
                return liveVideoRes;
            }
        }

        for (LiveStream liveStream : liveStreamResponse.getRows()) {
            LiveVideo liveVideo = new LiveVideo();

            createLiveVideoCodeInfo(liveVideoReq, liveVideoRes, liveVideo, liveStream, uid);

            if (liveVideoRes.getStatus() != 0) {
                return liveVideoRes;
            }

            if (liveVideo.getCode() == 1) {
                StringBuffer sb = new StringBuffer(liveStream.getStreamUrl());
                //付费token等参数统一在客户端处理，服务端不再拼接
                buildLivePlayerParam(sb, liveVideoReq, 0, null, null);
                liveVideo.setUrl(sb.toString());
            }
            liveVideo.setStreamName(liveStream.getStreamName());
            if (liveVideoRes.getDrmFlag() == 1 && MapUtils.isNotEmpty(liveRoom.getCids())) {
                liveVideo.setEncodeId(liveRoom.getCids().get(liveStream.getStreamName()));
            }

            if (liveVideoReq.getCaller() == 1003 || liveVideoReq.getCaller() == 1014) {
                if (PlayerConstants.liveVTypeList.contains(liveStream.getRateType())) {
                    infos.put(liveStream.getRateType(), liveVideo);
                }
            } else {
                infos.put(liveStream.getRateType(), liveVideo);
            }
        }
        if (!infos.isEmpty()) {
            liveVideoRes.setStatus(PlayerErrorCode.SUCCESS.getCode());
        }

        liveVideoRes.setInfos(infos);
        logger.debug("[app] [videoPlayer] [makeLive] [elapsed:{}]", stopwatch.elapsed(TimeUnit.MILLISECONDS));
        stopwatch.stop();
        logger.info("[app] [videoPlayer] [dealLiveVideo] [elapsed:{}]", stopwatch.elapsed(TimeUnit.MILLISECONDS));
        return liveVideoRes;
    }


    private void createLiveVideoCodeInfo(LiveVideoReq liveVideoReq, LiveVideoRes liveVideoRes, LiveVideo liveVideo, LiveStream liveStream, String uid) {
        if (liveVideoReq.getCaller() == 1003 || liveVideoReq.getCaller() == 1014 || liveVideoReq.getCaller() == 1051) {
            if (liveVideoRes.getIsPay() == 0) {
                int playStatus = 0;
                if (StringUtils.isBlank(uid)) {
                    try {
                        if (StringUtils.isNotBlank(liveVideoReq.getToken())) {
                            uid = SSOApi.checkToken(liveVideoReq.getToken());
                        }
                    } catch (Exception e) {
                        logger.error("get user info error. token : {}", liveVideoReq.getToken(), e);
                        liveVideoRes.setStatus(PlayerErrorCode.USER_RES_ERROR.getCode());
                    }
                }
                if (StringUtils.isNotBlank(uid)) {
                    playStatus = BossApi.getPlayStatus(uid, liveVideoReq.getDevicekey(), liveVideoReq.getTerminal(), liveVideoReq.getFlag());
                }

                //会员可以观看高码流
                if (SWITCH && PlayerConstants.LIVE_HIGH_VTYPE.contains(liveStream.getRateType())) {
                    liveVideo.setCode(playStatus);
                    liveVideo.setIsPay(1);
                } else {
                    liveVideo.setCode(1);
                    liveVideo.setIsPay(0);
                }
            } else {
                liveVideo.setCode(1);
                if (SWITCH && PlayerConstants.LIVE_HIGH_VTYPE.contains(liveStream.getRateType())) {
                    liveVideo.setIsPay(1);
                } else {
                    liveVideo.setIsPay(0);
                }
            }

        } else {
            liveVideo.setCode(1);
            liveVideo.setIsPay(liveVideoRes.getIsPay());
        }
    }

    @Override
    public LiveVideoRes dealLiveVideoWithFallback(LiveVideoReq liveVideoReq) {
        LiveVideoRes liveVideoRes = null;
        try {
            liveVideoRes = dealLiveVideo(liveVideoReq);
        } catch (Exception e) {
            logger.error("deal live with fallback error.liveVideoReq : {}", liveVideoReq, e);
        }
        if (liveVideoRes == null) {
            logger.info("app dealLiveVideo begin to enter fallback.liveVideoReq : {}", liveVideoReq);
            Map<String, String> paramMap = Maps.newHashMap();
            paramMap.put("liveId", liveVideoReq.getLiveId());
            liveVideoRes = fallbackCache.findByKey(Platform.MOBILE.name(), LeConstants.METHOD_APP_LIVE, paramMap, LiveVideoRes.class);
        }
        return liveVideoRes;
    }


    /**
     * 微信直播添加 tm 和 key 两个参数
     *
     * @param liveVideoReq
     * @param streamName
     * @return
     */
    private StringBuffer buildWechatUrl(StringBuffer sb, LiveVideoReq liveVideoReq, String streamName) {
        if (StringUtils.isNotBlank(liveVideoReq.getFrom()) && liveVideoReq.getFrom().equals("9")) {
            long wechatTm = LeDateUtils.addHour(new Date(), 2).getTime() / 1000;
            sb.append("&tm=").append(wechatTm).append("&key=").append(getWeChatKey(PlayerConstants.WECHATKEY, wechatTm, streamName)).toString();
        }
        return sb;
    }


    /**
     * 微信直播key MD5加密
     *
     * @param wechatkey
     * @param wechatTm
     * @param streamName
     * @return
     */
    private String getWeChatKey(String wechatkey, Long wechatTm, String streamName) {
        StringBuffer sb = new StringBuffer();
        String chatStr = sb.append(streamName).append(",").append(wechatTm).append(",").append(wechatkey).toString();
        return MD5Util.md5(chatStr, "UTF-8");
    }

    /**
     * 构建直播playerUrl参数
     *
     * @param sb
     * @param liveVideoReq
     * @param isPay
     * @param liveValidateResponse
     * @return
     */
    public StringBuffer buildLivePlayerParam(StringBuffer sb, LiveVideoReq liveVideoReq, int isPay, LiveValidateResponse liveValidateResponse, String uid) {
        sb.append("&platid=").append(liveVideoReq.getPlatId()).append("&splatid=").append(liveVideoReq.getSplatId()).append("&playid=1").append("&pay=1")
                .append("&ostype=").append(liveVideoReq.getOstype()).append("&hwtype=").append(liveVideoReq.getHwtype()).append("&termid=").append(liveVideoReq.getTermid());
        if (isPay == 1) {
            sb.append("&uid=").append(uid).append("&token=").append(liveValidateResponse.getToken());
//                    .append("&uinfo=").append(liveValidateResponse.getUinfo());
        }
        return sb;
    }

    /**
     * 创建直播信息
     *
     * @param liveVideoRes
     * @param streams
     */
    private void createLiveInfo(LiveVideoRes liveVideoRes, List<LiveStream> streams) {
        List<LiveVideoRes.StreamInfo> streamInfos = Lists.newArrayList();
        for (LiveStream stream : streams) {
            LiveVideoRes.StreamInfo streamInfo = new LiveVideoRes.StreamInfo();
            streamInfo.setStreamId(stream.getStreamId());
            streamInfo.setStreamUrl(stream.getStreamUrl());
            streamInfos.add(streamInfo);
        }
        liveVideoRes.setStreamInfos(streamInfos);
    }

    private void createVideoInfo(VodRes vodRes, Video video, List<Long> tagIds) {
        VodRes.VideoInfo videoInfo = new VodRes.VideoInfo();
        videoInfo.setVid(String.valueOf(video.getId()));
        Map<Integer, String> category = video.getCategory();
        int cid = category.keySet().iterator().next();
        videoInfo.setCid(String.valueOf(cid));
        //mid前面带有一个逗号
        String mid = StringUtils.isNotBlank(video.getMid()) ? video.getMid().substring(1, video.getMid().length() - 1) : "";
        videoInfo.setMid(mid);
        if (video.getPid() != null && video.getPid() != 0) {
            videoInfo.setPid(String.valueOf(video.getPid()));
        }
        videoInfo.setDuration(video.getDuration());
        //FIXME:CallerParam定义需要找路飞确认
//        if (CollectionUtils.isNotEmpty(tagIds)) {
//            videoInfo.setTagIds(tagIds);
//        }
        vodRes.setVideoInfo(videoInfo);
    }


}
