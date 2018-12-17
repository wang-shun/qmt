package com.lesports.qmt.web.api.core.service.impl;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Stopwatch;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.lesports.LeConstants;
import com.lesports.api.common.CallerParam;
import com.lesports.api.common.Platform;
import com.lesports.exception.VrsAccessException;
import com.lesports.model.*;
//import com.lesports.sms.api.common.Platform;
//import com.lesports.sms.api.vo.TCaller;
//import com.lesports.sms.api.web.core.cache.impl.FallbackCache;
//import com.lesports.sms.api.web.core.cache.impl.LiveCache;
//import com.lesports.sms.api.web.core.cache.impl.SsoCache;
//import com.lesports.sms.api.web.core.cache.impl.VrsCache;
//import com.lesports.sms.api.web.core.service.VideoPlayerService;
//import com.lesports.sms.api.web.core.util.Constants;
//import com.lesports.sms.api.web.core.util.PlayerConstants;
//import com.lesports.sms.client.SbdsApis;
import com.lesports.qmt.config.api.dto.TCaller;
import com.lesports.qmt.sbd.client.SbdApis;
import com.lesports.qmt.web.api.core.cache.impl.FallbackCache;
import com.lesports.qmt.web.api.core.cache.impl.LiveCache;
import com.lesports.qmt.web.api.core.cache.impl.SsoCache;
import com.lesports.qmt.web.api.core.cache.impl.VrsCache;
import com.lesports.qmt.web.api.core.service.VideoPlayerService;
import com.lesports.qmt.web.api.core.util.Constants;
import com.lesports.qmt.web.api.core.util.PlayerConstants;
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
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by lufei1 on 2015/7/7.
 */
@Service
public class VideoPlayerServiceImpl implements VideoPlayerService {

    private static final Logger logger = LoggerFactory.getLogger(VideoPlayerServiceImpl.class);

    @Resource
    private LiveCache liveCache;
    @Resource
    private VrsCache vrsCache;
    @Resource
    private SsoCache ssoCache;
    @Resource
    private FallbackCache fallbackCache;


    @Override
    public VodRes dealVod(VodReq videoOnDemandReq) throws VrsAccessException {
        logger.info("[app] [videoPlayer] [dealVideoOnDemand] [param:{}]", JSON.toJSON(videoOnDemandReq));
        Stopwatch stopwatch = Stopwatch.createStarted();
        VodRes videoOnDemandRes = new VodRes();
        //tv端用户校验及互踢
        if (videoOnDemandReq.getCaller() == 1002 || videoOnDemandReq.getCaller() == 3002 || videoOnDemandReq.getCaller() == 1051) {
            String uid = "";
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
                videoOnDemandReq.setUid(uid);
            }
        }

        //获取视频
        Video video;
        try {
            video = getVideo(videoOnDemandReq.getVid());
        } catch (Exception e) {
            logger.error("get video {} from mms error", videoOnDemandReq.getVid(), e);
            videoOnDemandRes.setStatus(PlayerErrorCode.MMS_ACCESS_ERROR.getCode());
            return videoOnDemandRes;
        }
        if (null == video || video.getDeleted() == 1) {
            logger.warn("[app] [videoPlayer] [dealVideoOnDemand] [video off line] [vid:{}]", videoOnDemandReq.getVid());
            videoOnDemandRes.setStatus(PlayerErrorCode.BUSI_VIDEO_OFFLINE.getCode());
            return videoOnDemandRes;
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
        //构建视频信息
        if (videoOnDemandReq.getCaller() == 1002 || videoOnDemandReq.getCaller() == 1051) {
            createVideoInfo(videoOnDemandRes, video);
        }
        ChainUrlResponse chainUrlResponse;
        try {
            chainUrlResponse = MmsApis.getChainUrl(videoOnDemandReq, mid);
        } catch (Exception e) {
            logger.error("getChainUrl from mms error. vid : {}", video.getId(), e);
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
        Boolean authResult = false;
        if (videoOnDemandReq.getCaller() == 3002) {
            //香港欧洲杯鉴权
            Map resultMap = UserApis.authForHkEuro(videoOnDemandReq.getUid(), 0, videoOnDemandReq.getClientip());
            if (MapUtils.isEmpty(resultMap)) {
                videoOnDemandRes.setStatus(PlayerErrorCode.PARAM_INVALID.getCode());
                return videoOnDemandRes;
            }
            authResult = (Boolean) resultMap.get("authaccess");
            //鉴权不通过
            if (!LeNumberUtils.toBoolean(authResult)) {
                String errorcode = (String) resultMap.get("errorcode");
                if ("10001".equals(errorcode)) {
                    logger.warn("[app] [vod] [dealVod4TVHk] [this user is not vip. uid : {}]", videoOnDemandReq.getUid());
                    videoOnDemandRes.setStatus(PlayerErrorCode.BUSI_PAY_VIDEO.getCode());
                    return videoOnDemandRes;
                } else if ("10002".equals(errorcode)) {
                    logger.warn("[app] [vod] [dealVod4TVHk] [this user has login on more device. uid : {}]", videoOnDemandReq.getUid());
                    videoOnDemandRes.setStatus(PlayerErrorCode.BUSI_USER_HAS_LOGIN.getCode());
                    return videoOnDemandRes;
                }
            }
        }

        ChainUrl chainUrl = chainUrlResponse.getData().get(0);
        Map<String, ScheduleUrl> scheduleUrlMap;
        //tv 大陆和国广版
        if (videoOnDemandReq.getCaller() == 1002 || videoOnDemandReq.getCaller() == 1051) {
            scheduleUrlMap = createTvScheduleMap(video, videoOnDemandReq, chainUrl);
        } else if (videoOnDemandReq.getCaller() == 3002) {
            scheduleUrlMap = createTvHkScheduleMap(video, videoOnDemandReq, chainUrl, authResult);
        } else {
            scheduleUrlMap = createScheduleMap(videoOnDemandReq, chainUrl);
        }
        videoOnDemandRes.setInfos(scheduleUrlMap);
        videoOnDemandRes.setStatus(PlayerErrorCode.SUCCESS.getCode());
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
        if (null == vodRes) {
            logger.info("dealVod begin to enter fallback. vodReq : {}", vodReq);
            Map<String, String> paramMap = Maps.newHashMap();
            paramMap.put("vid", vodReq.getVid());
            vodRes = fallbackCache.findByKey(Platform.MOBILE.name(), LeConstants.METHOD_APP_VOD, paramMap, VodRes.class);
        }
        return vodRes;
    }

    @Override
    public LiveVideoRes dealLiveVideo(LiveVideoReq liveVideoReq) {
        Stopwatch stopwatch = Stopwatch.createStarted();
        LiveVideoRes liveVideoRes = new LiveVideoRes();

        Map<String, LiveVideo> infos = Maps.newHashMap();
        //获取直播室信息
        LiveRoom liveRoom;
        try {
            liveRoom = getLiveRoom(liveVideoReq.getLiveId(), liveVideoReq.getSplatId());
        } catch (Exception e) {
            logger.error("getLiveRoom {} {} from live error", liveVideoReq.getLiveId(), liveVideoReq.getSplatId(), e);
            liveVideoRes.setStatus(PlayerErrorCode.LIVE_ACCESS_ERROR.getCode());
            return liveVideoRes;
        }
        if (liveRoom == null) {
            logger.warn("[app] [videoPlayer] [dealLiveVideo] [get live room error ] [liveId:{}] ", liveVideoReq.getLiveId());
            liveVideoRes.setStatus(PlayerErrorCode.LIVE_RES_ERROR.getCode());
            return liveVideoRes;
        }
        //获取流
        LiveStreamResponse liveStreamResponse;
        try {
            liveStreamResponse = getLiveStreamResponse(String.valueOf(liveRoom.getSelectId()), liveVideoReq.getSplatId(), liveVideoReq.getWithCibnStreams(), liveVideoReq.getLiveId());
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

        liveVideoRes.setLiveStartTime(liveRoom.getBeginTime());
        //app 414之前的版本都不支持付费，客户端需求对老版强升
        for (LiveStream liveStream : liveStreamResponse.getRows()) {
            if (!PlayerConstants.LIVE_CONTAIN_1080P && liveStream.getRateType().contains(PlayerConstants.RATE_TYPE_1080P_PREFIX)) {
                logger.error("drop 1080p rate. liveId : {}, selectId : {}", liveVideoReq.getLiveId(), liveRoom.getSelectId());
                continue;
            }
            if (PlayerConstants.liveVTypeList.contains(liveStream.getRateType())) {
                LiveVideo liveVideo = new LiveVideo();
                StringBuffer sb = new StringBuffer(liveStream.getStreamUrl());
                liveVideo.setCode(1);
                buildLivePlayerParam(sb, liveVideoReq, 0, null, null);
                buildWechatUrl(sb, liveVideoReq, liveStream.getStreamName());
                build360PlayerParam(sb, liveVideoReq.getSplatId());
                liveVideo.setUrl(sb.toString());
                infos.put(getRateTypeKey(liveStream), liveVideo);
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
    public LiveVideoRes dealLiveVideoWithFallback(LiveVideoReq liveVideoReq) {
        LiveVideoRes liveVideoRes = null;
        try {
            liveVideoRes = dealLiveVideo(liveVideoReq);
        } catch (Exception e) {
            logger.error("deal live with fallback error.liveVideoReq : {}", liveVideoReq, e);
        }
        if (null == liveVideoRes) {
            logger.info("app dealLiveVideo begin to enter fallback. liveVideoReq : {}", liveVideoReq);
            Map<String, String> paramMap = Maps.newHashMap();
            paramMap.put("liveId", liveVideoReq.getLiveId());
            liveVideoRes = fallbackCache.findByKey(Platform.MOBILE.name(), LeConstants.METHOD_APP_LIVE, paramMap, LiveVideoRes.class);
        }
        return liveVideoRes;
    }


    @Override
    public Boolean eachPlayLanding(String token, String flag, String devicekey, String terminal) {
        if (StringUtils.isBlank(token)) {
            return false;
        }
        String uid = SSOApi.checkToken(token);
        //tv互踢
        return UserApis.checkTvLogin(uid, flag, devicekey, terminal);
    }

    private void createVideoInfo(VodRes vodRes, Video video) {
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
        vodRes.setVideoInfo(videoInfo);
    }

    @Override
    public LiveVideoRes dealLiveVideo4TV(LiveVideoReq liveVideoReq) {
        Stopwatch stopwatch = Stopwatch.createStarted();
        LiveVideoRes liveVideoRes = new LiveVideoRes();
        Map<String, LiveVideo> infos = Maps.newHashMap();

        String uid = "";
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
            if (liveRoomResponse == null || CollectionUtils.isEmpty(liveRoomResponse.getRows())) {
                logger.warn("[app] [live] [dealLiveVideo] [get live room error ] [liveId:{}] ", liveVideoReq.getLiveId());
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

            if (CollectionUtils.isEmpty(liveStreamResponse.getRows())) {
                logger.warn("[app] [live] [dealLiveVideo] [get live stream error ] [liveId:{}] [selectedId:{}]", liveVideoReq.getLiveId(), liveRoom.getSelectId());
                liveVideoRes.setStatus(PlayerErrorCode.LIVE_RES_ERROR.getCode());
                return liveVideoRes;
            }
            liveCache.saveLiveStream(String.valueOf(liveRoom.getSelectId()), liveVideoReq.getSplatId(), liveVideoReq.getWithCibnStreams(), liveVideoReq.getLiveId(), liveStreamResponse);
        }

        //构建直播信息
        createLiveInfo(liveVideoRes, liveStreamResponse.getRows());
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
            //付费直播，没有登录
            if (StringUtils.isBlank(uid)) {
                liveVideoRes.setStatus(PlayerErrorCode.BUSI_USER_NO_LOGIN.getCode());
                return liveVideoRes;
            }
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
                LiveVideo liveVideo = new LiveVideo();

                //无播放权限，直播鉴权不通过
                if ("1".equals(liveValidateResponse.getStatus())) {
                    liveVideo.setCode(1);
                } else {
                    liveVideo.setCode(0);
                }
                if (liveVideo.getCode() == 1) {
                    liveVideo.setPrestart(liveValidateResponse.getPrestart());
                    liveVideo.setPreend(liveValidateResponse.getPreend());
                    liveVideo.setCurTime(liveValidateResponse.getCurTime());
                    liveVideo.setIsPre(LeNumberUtils.toBoolean(liveValidateResponse.isPre()) ? 1 : 0);

                    StringBuffer sb = new StringBuffer(liveStream.getStreamUrl());
                    buildLivePlayerParam(sb, liveVideoReq, isPay, liveValidateResponse, uid);
                    buildWechatUrl(sb, liveVideoReq, liveStream.getStreamName());
                    build360PlayerParam(sb, liveVideoReq.getSplatId());
                    liveVideo.setUrl(sb.toString());
                }
                //兼容tv app 1080p码流
                if (liveStream.getRateType().equals(PlayerConstants.RATE_TYPE_1080P)) {
                    infos.put(PlayerConstants.RATE_TYPE_3000_1080P, liveVideo);
                } else {
                    infos.put(liveStream.getRateType(), liveVideo);
                }
            }
        } else {
            int playStatus = BossApi.getPlayStatus(uid, liveVideoReq.getDevicekey(), liveVideoReq.getTerminal(), liveVideoReq.getFlag());
            for (LiveStream liveStream : liveStreamResponse.getRows()) {
                LiveVideo liveVideo = new LiveVideo();
                //会员可以观看高码流
                if (PlayerConstants.PAY_SWITCH && PlayerConstants.LIVE_HIGH_VTYPE.contains(liveStream.getRateType())) {
                    liveVideo.setCode(playStatus);
                    liveVideo.setIsPay(1);
                } else {
                    liveVideo.setCode(1);
                    liveVideo.setIsPay(0);
                }
                if (liveVideo.getCode() == 1) {
                    StringBuffer sb = new StringBuffer(liveStream.getStreamUrl());
                    buildLivePlayerParam(sb, liveVideoReq, isPay, null, null);
                    buildWechatUrl(sb, liveVideoReq, liveStream.getStreamName());
                    build360PlayerParam(sb, liveVideoReq.getSplatId());
                    liveVideo.setUrl(sb.toString());
                }

                //兼容tv app 1080p码流
                if (liveStream.getRateType().equals(PlayerConstants.RATE_TYPE_1080P)) {
                    infos.put(PlayerConstants.RATE_TYPE_3000_1080P, liveVideo);
                } else {
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
    public LiveVideoRes dealLiveVideo4TVHk(LiveVideoReq liveVideoReq, CallerParam callerParam) {
        Stopwatch stopwatch = Stopwatch.createStarted();
        LiveVideoRes liveVideoRes = new LiveVideoRes();
        Map<String, LiveVideo> infos = Maps.newHashMap();

        String uid = "";
        if (StringUtils.isNotBlank(liveVideoReq.getToken())) {
            uid = ssoCache.getUid(liveVideoReq.getToken());
            if (StringUtils.isBlank(uid)) {
                try {
                    uid = SSOApi.checkToken(liveVideoReq.getToken());
                } catch (Exception e) {
                    logger.error("get user info error. token : {}", liveVideoReq.getToken(), e);
                    liveVideoRes.setStatus(PlayerErrorCode.USER_RES_ERROR.getCode());
                    return liveVideoRes;
                }
                ssoCache.saveUid(liveVideoReq.getToken(), uid);
            }

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
            if (liveRoomResponse == null || CollectionUtils.isEmpty(liveRoomResponse.getRows())) {
                logger.warn("[app] [videoPlayer] [dealLiveVideo4TVHk] [get live room error ] [liveId:{}] ", liveVideoReq.getLiveId());
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

            if (CollectionUtils.isEmpty(liveStreamResponse.getRows())) {
                logger.warn("[app] [videoPlayer] [dealLiveVideo4TVHk] [get live stream error ] [liveId:{}] [selectedId:{}]", liveVideoReq.getLiveId(), liveRoom.getSelectId());
                liveVideoRes.setStatus(PlayerErrorCode.LIVE_RES_ERROR.getCode());
                return liveVideoRes;
            }
            liveCache.saveLiveStream(String.valueOf(liveRoom.getSelectId()), liveVideoReq.getSplatId(), liveVideoReq.getWithCibnStreams(), liveVideoReq.getLiveId(), liveStreamResponse);
        }

        //构建直播信息
        createLiveInfo(liveVideoRes, liveStreamResponse.getRows());
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
            //付费直播，没有登录
            if (StringUtils.isBlank(uid)) {
                liveVideoRes.setStatus(PlayerErrorCode.BUSI_USER_NO_LOGIN.getCode());
                return liveVideoRes;
            }
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
                LiveVideo liveVideo = new LiveVideo();

                //无播放权限，直播鉴权不通过
                if ("1".equals(liveValidateResponse.getStatus())) {
                    liveVideo.setCode(1);
                } else {
                    liveVideo.setCode(0);
                }
                if (liveVideo.getCode() == 1) {
                    liveVideo.setPrestart(liveValidateResponse.getPrestart());
                    liveVideo.setPreend(liveValidateResponse.getPreend());
                    liveVideo.setCurTime(liveValidateResponse.getCurTime());
                    liveVideo.setIsPre(LeNumberUtils.toBoolean(liveValidateResponse.isPre()) ? 1 : 0);

                    StringBuffer sb = new StringBuffer(liveStream.getStreamUrl());
                    buildLivePlayerParam(sb, liveVideoReq, isPay, liveValidateResponse, uid);
                    buildWechatUrl(sb, liveVideoReq, liveStream.getStreamName());
                    build360PlayerParam(sb, liveVideoReq.getSplatId());
                    liveVideo.setUrl(sb.toString());
                }
                //兼容tv app 1080p码流
                if (liveStream.getRateType().equals(PlayerConstants.RATE_TYPE_1080P)) {
                    infos.put(PlayerConstants.RATE_TYPE_3000_1080P, liveVideo);
                } else {
                    infos.put(liveStream.getRateType(), liveVideo);
                }
            }
        } else {
            //香港欧洲杯鉴权
            Map resultMap = UserApis.authForHkEuro(uid, 1, liveVideoReq.getClientip());
            if (MapUtils.isEmpty(resultMap)) {
                liveVideoRes.setStatus(PlayerErrorCode.PARAM_INVALID.getCode());
                return liveVideoRes;
            }
            Boolean authResult = (Boolean) resultMap.get("authaccess");
            //鉴权不通过
            if (!LeNumberUtils.toBoolean(authResult)) {
                String errorcode = (String) resultMap.get("errorcode");
                if ("10001".equals(errorcode)) {
                    logger.warn("[app] [videoPlayer] [dealLiveVideo4TVHk] [this user is not vip. uid : {}]", uid);
                    liveVideoRes.setStatus(PlayerErrorCode.BUSI_PAY_VIDEO.getCode());
                    return liveVideoRes;
                } else if ("10002".equals(errorcode)) {
                    logger.warn("[app] [videoPlayer] [dealLiveVideo4TVHk] [this user has login on more device. uid : {}]", uid);
                    liveVideoRes.setStatus(PlayerErrorCode.BUSI_USER_HAS_LOGIN.getCode());
                    return liveVideoRes;
                }
            }
            for (LiveStream liveStream : liveStreamResponse.getRows()) {
                LiveVideo liveVideo = new LiveVideo();
                liveVideo.setCode(1);
                if (LeNumberUtils.toBoolean(authResult)) {
                    liveVideo.setIsPay(1);
                } else {
                    liveVideo.setIsPay(0);
                }

                if (liveVideo.getCode() == 1) {
                    StringBuffer sb = new StringBuffer(liveStream.getStreamUrl());
                    buildLivePlayerParam(sb, liveVideoReq, isPay, null, null);
                    buildWechatUrl(sb, liveVideoReq, liveStream.getStreamName());
                    build360PlayerParam(sb, liveVideoReq.getSplatId());
                    liveVideo.setUrl(sb.toString());
                }

                //兼容tv app 1080p码流
                if (liveStream.getRateType().equals(PlayerConstants.RATE_TYPE_1080P)) {
                    infos.put(PlayerConstants.RATE_TYPE_3000_1080P, liveVideo);
                } else {
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
            streamInfo.setStreamName(stream.getStreamName());
            streamInfo.setStreamUrl(stream.getStreamUrl());
            streamInfos.add(streamInfo);
        }
        liveVideoRes.setStreamInfos(streamInfos);
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
     * 构建360度视频直播参数
     *
     * @param sb
     * @param splatId
     * @return
     */
    public StringBuffer build360PlayerParam(StringBuffer sb, String splatId) {
        if (PlayerConstants.SPLATID_360_LIVE.equals(splatId) || PlayerConstants.SPLATID_360_VOD.equals(splatId)) {
            sb.append("&format=0&expect=1");
        }
        return sb;
    }

    private Platform getPlatFormFromCaller(long callerId) {
        TCaller tCaller = SbdApis.getTCallerById(callerId);
        if (null == tCaller) {
            throw new IllegalArgumentException("illegal callerId : " + callerId);
        }
        return tCaller.getPlatform();
    }

    private Video getVideo(String vid) throws VrsAccessException {
        Video video = vrsCache.getVideo(vid);
        if (video == null) {
            video = MmsApis.getVideo(vid);
            if (null != video) {
                vrsCache.saveVideo(video);
            }
        }
        return video;
    }

    private LiveRoom getLiveRoom(String liveId, String splitId) {
        LiveRoom liveRoom = liveCache.getLiveRoom(liveId);
        if (null == liveRoom) {
            LiveRoomResponse liveRoomResponse = LiveApis.getLiveRoom(liveId, splitId);
            if (null != liveRoomResponse && CollectionUtils.isNotEmpty(liveRoomResponse.getRows())) {
                liveRoom = liveRoomResponse.getRows().get(0);
                liveCache.saveLiveRoom(liveId, liveRoom);
            }
        }
        return liveRoom;
    }

    private LiveStreamResponse getLiveStreamResponse(String selectId, String splitId, String guoguang, String liveId) {
        LiveStreamResponse liveStreamResponse = liveCache.getLiveStream(selectId, splitId, guoguang, liveId);
        if (liveStreamResponse == null) {
            liveStreamResponse = LiveApis.getLiveStream(selectId, splitId, guoguang, liveId);

            if (null != liveStreamResponse) {
                liveCache.saveLiveStream(selectId, splitId, guoguang, liveId, liveStreamResponse);
            }
        }
        return liveStreamResponse;
    }

    private String getRateTypeKey(LiveStream liveStream) {
        if (liveStream.getRateType().equals(PlayerConstants.RATE_TYPE_3000_1080P)) {
            //infos.put(PlayerConstants.APP_COMPATIBLE_RATE_TYPE_3000_1080P, liveVideo);
            return liveStream.getRateType();
        } else if (liveStream.getRateType().equals(PlayerConstants.RATE_TYPE_5000_1080P)) {
            //infos.put(PlayerConstants.APP_COMPATIBLE_RATE_TYPE_5000_1080P, liveVideo);
            return liveStream.getRateType();
        } else {
            return liveStream.getRateType();
        }
    }

    private void setChainUrl(String splitId, ChainUrlInfo chainUrlInfo, ScheduleUrl scheduleUrl) {
        if (scheduleUrl.getCode() == 1) {
            StringBuffer sbMain = new StringBuffer(chainUrlInfo.getMainUrl());
            build360PlayerParam(sbMain, splitId);
            scheduleUrl.setMainUrl(sbMain.toString());

            StringBuffer sb0 = new StringBuffer(chainUrlInfo.getBackUrl0());
            build360PlayerParam(sb0, splitId);
            scheduleUrl.setBackUrl0(sb0.toString());

            StringBuffer sb1 = new StringBuffer(chainUrlInfo.getBackUrl1());
            build360PlayerParam(sb1, splitId);
            scheduleUrl.setBackUrl1(sb1.toString());


            StringBuffer sb2 = new StringBuffer(chainUrlInfo.getBackUrl2());
            build360PlayerParam(sb2, splitId);
            scheduleUrl.setBackUrl2(sb2.toString());
        }
    }


    private Map<String, ScheduleUrl> createScheduleMap(VodReq videoOnDemandReq, ChainUrl chainUrl) {
        Map<String, ScheduleUrl> scheduleUrlMap = Maps.newHashMap();

        for (ChainUrlInfo chainUrlInfo : chainUrl.getInfos()) {
            ScheduleUrl scheduleUrl = new ScheduleUrl();
            scheduleUrl.setCode(1);
            setChainUrl(String.valueOf(videoOnDemandReq.getSplatid()), chainUrlInfo, scheduleUrl);
            scheduleUrlMap.put(PlayerConstants.vodVTypeMap.get(chainUrlInfo.getVtype()), scheduleUrl);
        }
        return scheduleUrlMap;
    }

    private Map<String, ScheduleUrl> createTvScheduleMap(Video video, VodReq videoOnDemandReq, ChainUrl chainUrl) {
        Map<String, ScheduleUrl> scheduleUrlMap = Maps.newHashMap();
        //0:免费
        if (video.getIsPay() == 0) {
            int playStatus = BossApi.getPlayStatus(videoOnDemandReq.getUid(), videoOnDemandReq.getDevicekey(), videoOnDemandReq.getTerminal(), videoOnDemandReq.getFlag());
            for (ChainUrlInfo chainUrlInfo : chainUrl.getInfos()) {
                ScheduleUrl scheduleUrl = new ScheduleUrl();
                //会员可以观看高码流
                if (PlayerConstants.PAY_SWITCH && PlayerConstants.VOD_HIGH_VTYPE.contains(chainUrlInfo.getVtype())) {
                    scheduleUrl.setCode(playStatus);
                    scheduleUrl.setIsPay(1);
                } else {
                    scheduleUrl.setCode(1);
                    scheduleUrl.setIsPay(0);
                }
                setChainUrl(String.valueOf(videoOnDemandReq.getSplatid()), chainUrlInfo, scheduleUrl);
                scheduleUrlMap.put(PlayerConstants.vodVTypeMap.get(chainUrlInfo.getVtype()), scheduleUrl);
            }
            //暂时没有收费视频
        } else if (video.getIsPay() == 1) {

        }
        return scheduleUrlMap;
    }

    private Map<String, ScheduleUrl> createTvHkScheduleMap(Video video, VodReq videoOnDemandReq, ChainUrl chainUrl, Boolean authResult) {
        Map<String, ScheduleUrl> scheduleUrlMap = Maps.newHashMap();
        //0:免费
        if (video.getIsPay() == 0) {

            for (ChainUrlInfo chainUrlInfo : chainUrl.getInfos()) {
                ScheduleUrl scheduleUrl = new ScheduleUrl();
                scheduleUrl.setCode(1);
                if (LeNumberUtils.toBoolean(authResult)) {
                    //香港欧洲杯会员可看
                    scheduleUrl.setIsPay(1);
                } else {
                    scheduleUrl.setIsPay(0);
                }

                setChainUrl(String.valueOf(videoOnDemandReq.getSplatid()), chainUrlInfo, scheduleUrl);
                scheduleUrlMap.put(PlayerConstants.vodVTypeMap.get(chainUrlInfo.getVtype()), scheduleUrl);
            }
        }
        return scheduleUrlMap;
    }

}
