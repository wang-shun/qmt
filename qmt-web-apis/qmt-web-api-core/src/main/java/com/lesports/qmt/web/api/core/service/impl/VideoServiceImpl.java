package com.lesports.qmt.web.api.core.service.impl;

import com.google.common.base.Stopwatch;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.lesports.LeConstants;
import com.lesports.api.common.CallerParam;
import com.lesports.api.common.CountryCode;
import com.lesports.api.common.PageParam;
import com.lesports.api.common.Platform;
import com.lesports.id.client.LeIdApis;
import com.lesports.model.VideoInfos;
import com.lesports.qmt.config.api.dto.TCaller;
import com.lesports.qmt.config.client.QmtConfigApis;
import com.lesports.qmt.sbc.api.common.EpisodeType;
import com.lesports.qmt.sbc.api.dto.*;
import com.lesports.qmt.sbc.api.service.GetRelatedVideosParam;
import com.lesports.qmt.sbc.client.QmtSbcApis;
import com.lesports.qmt.sbc.client.QmtSbcEpisodeApis;
import com.lesports.qmt.sbd.api.dto.TCompetitor;
import com.lesports.qmt.web.api.core.creater.VideoVoCreater;
import com.lesports.qmt.web.api.core.service.VideoService;
import com.lesports.qmt.web.api.core.util.ResponseUtils;
import com.lesports.qmt.web.api.core.vo.AlbumVideo;
import com.lesports.qmt.web.api.core.vo.UltimateVo;
import com.lesports.qmt.web.api.core.vo.VideoInfo;
import com.lesports.qmt.web.api.core.vo.VideoVo;
import com.lesports.qmt.web.helper.ShareHelper;
import com.lesports.qmt.web.model.Share;
import com.lesports.api.common.LiveShowStatus;
import com.lesports.utils.MmsApis;
import com.lesports.utils.PageUtils;
import com.lesports.utils.SuggestApis;
import com.lesports.utils.math.LeNumberUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
* User: ellios
* Time: 15-11-5 : 上午10:54
*/
@Service("videoService")
public class VideoServiceImpl implements VideoService {

    private static final Logger LOG = LoggerFactory.getLogger(VideoServiceImpl.class);

    @Resource
    private VideoVoCreater videoVoCreater;

    @Resource
    private ShareHelper shareHelper;

    @Resource
    private MessageSource messageSource;

    private static final String RELATED_VIDEOS_TITLE = "related.videos";
    private static final String LATEST_VIDEOS_TITLE = "latest.videos";
    private static final String CURRENT_VIDEOS_TITLE = "current.episodes.express";
    private static final String EPISODES_LIST_TITLE = "episodes.list";

    @Override
    public List<VideoVo> getVideosByRelatedId(long relatedId, PageParam page, CallerParam caller) {
        GetRelatedVideosParam p = new GetRelatedVideosParam();
        p.setRelatedId(relatedId);
        List<Long> vids = QmtSbcApis.getVideoIdsByRelatedId(p, page, caller);
        List<TVideo> relatedVideos = QmtSbcApis.getTVideosByIds(vids, caller);
        if (CollectionUtils.isEmpty(relatedVideos)) {
            LOG.info(" this id not have relatition videos ! relatedId ={}", relatedId);

            return Collections.EMPTY_LIST;
        }
        List<VideoVo> results = Lists.newArrayList();
        for (TVideo tVideo : relatedVideos) {
            results.add(videoVoCreater.createVideoVo(tVideo));
        }
        return results;
    }

    @Override
    public List<VideoVo> getRelatedVideos(long vid, PageParam page, CallerParam caller) {
        List<VideoVo> returnVideos = Lists.newArrayList();
        List<Long> vids = QmtSbcApis.getVideoIdsRelatedWithSomeVideo(vid, page, caller);
        List<TVideo> relatedVideos = QmtSbcApis.getTVideosByIds(vids, caller);
        if (CollectionUtils.isNotEmpty(relatedVideos)) {
            for (TVideo tVideo : relatedVideos) {
                returnVideos.add(videoVoCreater.createVideoVo(tVideo));
            }
        } else {
            List<Long> ids = QmtSbcApis.getLatestVideoIds(page, caller);
            relatedVideos = QmtSbcApis.getTVideosByIds(ids, caller);
            if (CollectionUtils.isNotEmpty(relatedVideos)) {
                for (TVideo tVideo : relatedVideos) {
                    returnVideos.add(videoVoCreater.createVideoVo(tVideo));
                }
            }
            LOG.info("has no related videos ! vid = {}, pageRequest = {}", vid, page);
        }
        return returnVideos;
    }

    @Override
    public Map<String, Object> getRelatedVideos4Tv(long vid, CallerParam callerParam, PageParam pageParam) {
        Locale locale = org.springframework.util.StringUtils.parseLocaleString(callerParam.getLanguage().name());
        List<VideoVo> returnVideos = Lists.newArrayList();
        PageParam pageParam1 = PageUtils.createPageParam(1, 20);
        List<Long> ids = QmtSbcApis.getVideoIdsRelatedWithSomeVideo(vid, pageParam1, callerParam);
        List<TVideo> relatedVideos = QmtSbcApis.getTVideosByIds(ids, callerParam);
        if (CollectionUtils.isNotEmpty(relatedVideos)) {
            if (pageParam.getPage() > 1) {
                return generateRelatedVideosResult(pageParam.getPage(), Collections.EMPTY_LIST, messageSource.getMessage(RELATED_VIDEOS_TITLE, null, locale));
            }
            for (TVideo tVideo : relatedVideos) {
                returnVideos.add(videoVoCreater.createVideoVo(tVideo));
                if (returnVideos.size() >= 20) {
                    break;
                }
            }
            return generateRelatedVideosResult(1, returnVideos, messageSource.getMessage(RELATED_VIDEOS_TITLE, null, locale));
        } else {
            if (pageParam.getPage() > 1) {
                return generateRelatedVideosResult(pageParam.getPage(), Collections.EMPTY_LIST, messageSource.getMessage(LATEST_VIDEOS_TITLE, null, locale));
            }

            List<VideoVo> latestVideos = getLatestVideos(callerParam, pageParam1);
            return generateRelatedVideosResult(1, latestVideos, messageSource.getMessage(LATEST_VIDEOS_TITLE, null, locale));
        }
    }

    @Override
    public List<VideoVo> getNonPositiveVideosByAid(long aid, PageParam page, CallerParam caller) {
        List<VideoVo> returnVideos = Lists.newArrayList();
        List<TVideo> positiveVideos = QmtSbcApis.getNonPositiveVideosByAid(aid, page, caller);
        if (CollectionUtils.isNotEmpty(positiveVideos)) {
            for (TVideo tVideo : positiveVideos) {
                Share share = shareHelper.getShareByType(tVideo.getId() + "", Share.ShareType.VIDEO, caller);
                AlbumVideo albumVideo = videoVoCreater.createAVideo(tVideo, caller);
                if (null != albumVideo) {
                    albumVideo.setShare(share);
                    returnVideos.add(albumVideo);
                }
            }
        } else {
            LOG.info("has no positive videos ! aid = {}, page = {}", aid, page);
        }
        return returnVideos;
    }

    @Override
    public Map<String, Map<String, List<VideoVo>>> getFeatureVideosByAidAndYears(long aid, String yearAndMonth, CallerParam caller) {
        Map<String, Map<String, List<VideoVo>>> yearsMap = Maps.newLinkedHashMap();
        TFeatureInfo tFeatureInfo = QmtSbcApis.getFeatureVideosByYears(aid, yearAndMonth, caller);
        if (tFeatureInfo == null) {
            return yearsMap;
        }
        //该专辑起始时间
        String startTime = tFeatureInfo.getStartTime();
        //该专辑最新更新的时间
        String endTime = tFeatureInfo.getEndTime();
        if (StringUtils.isBlank(yearAndMonth)) {//如果传入的是非法的年月的话,则默认给最新的一个月的数据
            yearAndMonth = endTime;
        }
        int year = LeNumberUtils.toInt(StringUtils.substring(yearAndMonth, 0, 4));
        int month = LeNumberUtils.toInt(StringUtils.substring(yearAndMonth, 4, 6));
        //该月的视频集合
        List<VideoVo> videos = new ArrayList<VideoVo>();
        List<TVideo> tVideos = tFeatureInfo.getVideos();
        if (CollectionUtils.isNotEmpty(tVideos)) {
            for (TVideo tVideo : tVideos) {
                Share share = shareHelper.getShareByType(tVideo.getId() + "", Share.ShareType.VIDEO, caller);
                AlbumVideo albumVideo = videoVoCreater.createAVideo(tVideo, caller);
                if (null != albumVideo) {
                    albumVideo.setShare(share);
                    videos.add(albumVideo);
                }
            }
        }
        /*
         *拼接返回的数据格式
		 */
        int startYear = LeNumberUtils.toInt(StringUtils.substring(startTime, 0, 4));
        int startMonth = LeNumberUtils.toInt(StringUtils.substring(startTime, 4, 6));
        int endYear = LeNumberUtils.toInt(StringUtils.substring(endTime, 0, 4));
        int endMonth = LeNumberUtils.toInt(StringUtils.substring(endTime, 4, 6));
        //说明该专辑没有跨年
        if (startYear == endYear && year == startYear) {
            Map<String, List<VideoVo>> monthsMap = Maps.newLinkedHashMap();
            for (int i = startMonth; i <= endMonth; i++) {
                if (month == i) {
                    monthsMap.put(key2String(i), videos);
                } else {
                    monthsMap.put(key2String(i), new ArrayList<VideoVo>());
                }
            }
            yearsMap.put(key2String(startYear), monthsMap);
        } else {//说明该专辑已经跨年
            int startMonth4cycle;//循环中用到的起始月份
            int endMonth4cycle;//循环中用到的截至月份
            for (int j = endYear; j >= startYear; j--) {
                //每次循环初始化为最初的起始月
                startMonth4cycle = startMonth;
                endMonth4cycle = endMonth;
                Map<String, List<VideoVo>> monthsMap = Maps.newLinkedHashMap();
                if (j > startYear) {//如果查询的年份大于专辑起始年份的话,则从1月开始
                    startMonth4cycle = 1;
                }
                if (j != endYear) {//如果查询的年份不等于专辑结束年份的话,则结束月份为12月
                    endMonth4cycle = 12;
                }
                for (int i = startMonth4cycle; i <= endMonth4cycle; i++) {
                    if (month == i && year == j) {
                        monthsMap.put(key2String(i), videos);
                    } else {
                        monthsMap.put(key2String(i), new ArrayList<VideoVo>());
                    }
                }
                yearsMap.put(key2String(j), monthsMap);
            }
        }
        return yearsMap;
    }

    @Override
    public List<VideoVo> getLatestVideos(CallerParam callerParam, PageParam pageParam) {
        List<VideoVo> returnVideos = Lists.newArrayList();
        List<Long> ids = QmtSbcApis.getLatestVideoIds(pageParam, callerParam);
        List<TVideo> relatedVideos = QmtSbcApis.getTVideosByIds(ids, callerParam);
        if (CollectionUtils.isNotEmpty(relatedVideos)) {
            for (TVideo tVideo : relatedVideos) {
                returnVideos.add(videoVoCreater.createVideoVo(tVideo));
            }
        } else {
            LOG.info("has no lastest videos ! pageParam = {},callerParam:{}", pageParam, callerParam);
        }
        return returnVideos;
    }

    @Override
    public List<VideoVo> getVideosByRelatedIdAndCid(GetRelatedVideosParam p, PageParam page, CallerParam caller) {
        List<VideoVo> returnVideos = Lists.newArrayList();
        List<Long> ids = QmtSbcApis.getVideoIdsByRelatedId(p, page, caller);
        List<TVideo> relatedVideos = QmtSbcApis.getTVideosByIds(ids, caller);
        if (CollectionUtils.isNotEmpty(relatedVideos)) {
            for (TVideo tVideo : relatedVideos) {
                returnVideos.add(videoVoCreater.createVideoVo(tVideo));
            }
        } else {
            LOG.info(" no related videos ! id:{},page = {}", p.getRelatedId(), page);
        }
        return returnVideos;
    }

    @Override
    public VideoVo getVideoById(long id, CallerParam callerParam) {
        VideoVo videoVo = new VideoVo();
        TVideo tVideo = QmtSbcApis.getTVideoById(id, callerParam);
        if (tVideo != null) {
            return videoVoCreater.createVideoVo(tVideo);
        }
        return null;
    }


    @Override
    public List<AlbumVideo> getAlbumVideos(long aid, PageParam pageParam, CallerParam callerParam) {
        List<AlbumVideo> returnAvideos = Lists.newArrayList();
        List<TVideo> tAvideos = QmtSbcApis.getRecordVideosInAlbum(aid, pageParam, callerParam);
        if (CollectionUtils.isNotEmpty(tAvideos)) {
            for (TVideo tVideo : tAvideos) {
                Share share = shareHelper.getShareByType(tVideo.getId() + "", Share.ShareType.VIDEO, callerParam);
                AlbumVideo albumVideo = videoVoCreater.createAVideo(tVideo, callerParam);
                if (null != albumVideo) {
                    albumVideo.setShare(share);
                    returnAvideos.add(albumVideo);
                }
            }
        } else {
            LOG.info("has no album videos ! aid = {}, pageParam = {},callerParam:{}", aid, pageParam, callerParam);
        }
        return returnAvideos;
    }


    private List<TVideo> getEpisodeVideos(TComboEpisode episode, CallerParam callerParam, Platform platform) {
        List<TVideo> results = Lists.newArrayList();
        //整场回放的集合(多种语言的回放合集),页面会有不同的icon进行展示
        List<TVideo> recordVideos = Lists.newArrayList();
        //集锦
        List<TVideo> highligths = Lists.newArrayList();
        //其它视频
        List<TVideo> others = Lists.newArrayList();
        //获取整场回放
        if (CollectionUtils.isNotEmpty(episode.getVideos())) {
            for (TSimpleVideo tSimpleVideo : episode.getVideos()) {
                TVideo tVideo = QmtSbcApis.getTVideoById(LeIdApis.convertMmsVideoIdToLeSportsVideoId(tSimpleVideo.getVid()), callerParam);
                if (tVideo == null || tVideo.isCloneVideo()) {
                    continue;
                }
                if (CollectionUtils.isEmpty(tVideo.getPlatforms()) || !tVideo.getPlatforms().contains(platform)) {
                    LOG.info("video : {} not allow play in platform:{}.", tVideo.getId(), platform);
                    continue;
                }
                if (CollectionUtils.isEmpty(tVideo.getAllowCountries()) || (!tVideo.getAllowCountries().contains(callerParam.getCountry()) && !tVideo.getAllowCountries().contains(CountryCode.ALL))) {
                    LOG.info("video : {} not allow play in country:{},episode:{}", tVideo.getId(), callerParam.getCountry(),episode.getId());
                    continue;
                }
                switch (tSimpleVideo.getType()) {
                    case RECORD:
                        recordVideos.add(tVideo);
                        continue;
                    case HIGHLIGHTS:
                        highligths.add(tVideo);
                        continue;
                    default:
                        others.add(tVideo);
                }
            }
        }
        if (CollectionUtils.isNotEmpty(highligths)) {
            //对集锦倒序排序
            Collections.sort(highligths, new Comparator<TVideo>() {
                @Override
                public int compare(TVideo o1, TVideo o2) {
                    return o2.getCreateAt().compareTo(o1.getCreateAt());
                }
            });
        }
        if (CollectionUtils.isNotEmpty(others)) {
            //对其它视频倒序排序
            Collections.sort(others, new Comparator<TVideo>() {
                @Override
                public int compare(TVideo o1, TVideo o2) {
                    return o2.getCreateAt().compareTo(o1.getCreateAt());
                }
            });
            highligths.addAll(others);
        }
        results.addAll(recordVideos);
        results.addAll(highligths);
        results.addAll(others);

        return results;

    }

    /**
     * 本场速递
     *
     * @param episode
     * @return
     */
    @Override
    public List<VideoVo> getFormatEpisodeVideos(TComboEpisode episode, CallerParam callerParam) {
        TCaller caller = QmtConfigApis.getTCallerById(callerParam.getCallerId());
        if (null == caller) {
            LOG.error("caller is not exsits, callerId = {}", callerParam.getCallerId());
            return Collections.EMPTY_LIST;
        }
        List<TVideo> episodeVideos = getEpisodeVideos(episode, callerParam, caller.getPlatform());

        return formatVideo(episodeVideos, episodeVideos.size());
    }

    /**
     * 获取相关视频
     *
     * @param episode
     * @param callerParam
     * @return
     */
    @Override
    public List<VideoVo> getRelatedVideos(TComboEpisode episode, CallerParam callerParam) {

        LinkedList<Long> videoTypeList = Lists.newLinkedList();
        //优先展示两队相关视频、无两队相关视频用赛事视频补齐(上限30条)
        if (episode.isVs()) {
            //对阵的获取参赛者集合
            List<TCompetitor> competitors = episode.getCompetitors();
            if (CollectionUtils.isNotEmpty(competitors) && competitors.size() <= 2) {
                for (int i = 0; i < competitors.size(); i++) {
                    videoTypeList.add(competitors.get(i).getId());
                }
            } else {
                LOG.error("match competitors is error, eid = {}", episode.getId());
            }
        }
        //添加赛事id
        videoTypeList.add(episode.getCid());
        //有些冷门的赛事没有相关视频,则需要用大项来进行补充
        videoTypeList.add(episode.getGameFType());
        List<TVideo> relateVideos = getVideosByRelatedIds(videoTypeList, callerParam);
        return formatVideo(relateVideos, 20);
    }

    private Map<String, Object> generateRelatedVideosResult(int page, List entries, String type) {
        Map<String, Object> results = Maps.newHashMap();
        results.put(LeConstants.PAGE_KEY, page);
        results.put(LeConstants.COUNT_KEY, entries.size());
        results.put("title", type);
        results.put(LeConstants.ENTRIES_KEY, entries);
        return results;
    }

    @Override
    public Map<String, Object> getEpisodeRelatedVideos(long eid, CallerParam callerParam, PageParam pageParam) {
        Stopwatch stopwatch = Stopwatch.createStarted();
        Map<String, Object> results = Maps.newHashMap();
        Locale locale = org.springframework.util.StringUtils.parseLocaleString(callerParam.getLanguage().name());
        TComboEpisode episode = QmtSbcEpisodeApis.getTComboEpisodeById(eid, callerParam);
        if (null == episode) {
            LOG.error("episode is null, eid = {}", eid);
        }
        PageParam pageParam1 = PageUtils.createPageParam(1, 20);

        if (episode.getType().equals(EpisodeType.MATCH)) {

            List<VideoVo> episodeVideos = getFormatEpisodeVideos(episode, callerParam);
            boolean isEpisodeVideosEmpty = CollectionUtils.isEmpty(episodeVideos) ? true : false;
            List<VideoVo> currentEpisodeVideos = getCurrentEpisodeVideos(episodeVideos, pageParam);
            if (episode.getStatus().equals(LiveShowStatus.LIVE_NOT_START)) {
                List<VideoVo> relatedVideos = getRelatedVideos(episode, callerParam);
                if (CollectionUtils.isEmpty(relatedVideos)) {

                    List<VideoVo> latestVideos = getLatestVideos(callerParam, pageParam1);
                    if (pageParam.getPage() > 1) {
                        results = generateRelatedVideosResult(pageParam.getPage(), Collections.EMPTY_LIST, messageSource.getMessage(LATEST_VIDEOS_TITLE, null, locale));
                    } else {
                        results = generateRelatedVideosResult(1, latestVideos, messageSource.getMessage(LATEST_VIDEOS_TITLE, null, locale));
                    }
                } else {
                    if (pageParam.getPage() > 1) {
                        results = generateRelatedVideosResult(pageParam.getPage(), Collections.EMPTY_LIST, messageSource.getMessage(RELATED_VIDEOS_TITLE, null, locale));
                    } else {
                        results = generateRelatedVideosResult(1, relatedVideos, messageSource.getMessage(RELATED_VIDEOS_TITLE, null, locale));

                    }
                }
            } else if (episode.getStatus().equals(LiveShowStatus.LIVING)) {
                if (!isEpisodeVideosEmpty) {
                    results = generateRelatedVideosResult(pageParam.getPage(), currentEpisodeVideos, messageSource.getMessage(CURRENT_VIDEOS_TITLE, null, locale));
                }
            } else if (episode.getStatus().equals(LiveShowStatus.LIVE_END)) {
                if (isEpisodeVideosEmpty) {
                    List<VideoVo> relatedVideos = getRelatedVideos(episode, callerParam);
                    if (CollectionUtils.isEmpty(relatedVideos)) {

                        List<VideoVo> latestVideos = getLatestVideos(callerParam, pageParam1);
                        if (pageParam.getPage() > 1) {
                            results = generateRelatedVideosResult(pageParam.getPage(), Collections.EMPTY_LIST, messageSource.getMessage(LATEST_VIDEOS_TITLE, null, locale));
                        } else {
                            results = generateRelatedVideosResult(1, latestVideos, messageSource.getMessage(LATEST_VIDEOS_TITLE, null, locale));
                        }
                    } else {
                        if (pageParam.getPage() > 1) {
                            results = generateRelatedVideosResult(pageParam.getPage(), Collections.EMPTY_LIST, messageSource.getMessage(RELATED_VIDEOS_TITLE, null, locale));
                        } else {
                            results = generateRelatedVideosResult(1, relatedVideos, messageSource.getMessage(RELATED_VIDEOS_TITLE, null, locale));
                        }
                    }
                } else {
                    results = generateRelatedVideosResult(pageParam.getPage(), currentEpisodeVideos, messageSource.getMessage(CURRENT_VIDEOS_TITLE, null, locale));
                }
            }
        } else {
            if (episode.getAid() > 0) {
                List<VideoVo> latestEpisodeVos = getVideoListByAid(episode.getAid(), pageParam, callerParam);
                results = generateRelatedVideosResult(pageParam.getPage(), latestEpisodeVos, messageSource.getMessage(EPISODES_LIST_TITLE, null, locale));
            } else {
                List<VideoVo> latestVideos = getLatestVideos(callerParam, pageParam1);
                if (pageParam.getPage() > 1) {
                    results = generateRelatedVideosResult(pageParam.getPage(), Collections.EMPTY_LIST, messageSource.getMessage(LATEST_VIDEOS_TITLE, null, locale));
                } else {
                    results = generateRelatedVideosResult(1, latestVideos, messageSource.getMessage(LATEST_VIDEOS_TITLE, null, locale));
                }
            }
        }
        stopwatch.stop();
        LOG.info("get Episode Related Videos for tv, eid: {},elapsed: {}", eid, stopwatch.elapsed(TimeUnit.MILLISECONDS));
        return results;
    }

    private List<VideoVo> getCurrentEpisodeVideos(List<VideoVo> videoVos, PageParam page) {

        int size = page.getPage();
        int count = page.getCount();

        if (CollectionUtils.isNotEmpty(videoVos)) {
            if (videoVos.size() > size * count) {
                return videoVos.subList((size - 1) * count, size * count);
            } else {
                if (videoVos.size() > (size - 1) * count) {
                    return videoVos.subList((size - 1) * count, videoVos.size());
                }
            }
        }
        return Collections.EMPTY_LIST;
    }

    @Override
    public List<VideoVo> getRelatedVideosByEid(long eid, CallerParam callerParam,PageParam pageParam) {
        TComboEpisode episode = QmtSbcEpisodeApis.getTComboEpisodeById(eid, callerParam);
        if (null == episode) {
            return getLatestVideos(callerParam, pageParam);
        }

        if (episode.getType().equals(EpisodeType.MATCH)) {
            List<VideoVo> episodeVideos = getFormatEpisodeVideos(episode, callerParam);
            if(CollectionUtils.isNotEmpty(episodeVideos) && episodeVideos.size() >= pageParam.getCount()){
                return episodeVideos.subList(0,pageParam.getCount());
            }else{
                List<VideoVo> relatedVideos = getRelatedVideos(episode, callerParam);
                if(CollectionUtils.isNotEmpty(episodeVideos)){
                    episodeVideos.addAll(relatedVideos);
                    return episodeVideos.subList(0,pageParam.getCount());
                }else{
                    if(CollectionUtils.isNotEmpty(relatedVideos) && relatedVideos.size() >= pageParam.getCount()){
                        return relatedVideos.subList(0,pageParam.getCount());
                    }else{
                        List<VideoVo> latestVideos = getLatestVideos(callerParam, pageParam);
                        if(CollectionUtils.isNotEmpty(relatedVideos)){
                            relatedVideos.addAll(latestVideos);
                            return relatedVideos.subList(0,pageParam.getCount());
                        }else{
                            return latestVideos;
                        }
                    }
                }

            }

        } else {
            if (episode.getAid() > 0) {
                List<VideoVo> albumVideoVos = getVideoListByAid(episode.getAid(), pageParam,callerParam);
                if(CollectionUtils.isNotEmpty(albumVideoVos) && albumVideoVos.size() >= pageParam.getCount()){
                    return albumVideoVos.subList(0,pageParam.getCount());
                }else{
                    List<VideoVo> latestVideos = getLatestVideos(callerParam, pageParam);
                    if(CollectionUtils.isNotEmpty(albumVideoVos)){
                        albumVideoVos.addAll(latestVideos);
                        return albumVideoVos.subList(0,pageParam.getCount());
                    }else{
                        return latestVideos;
                    }
                }

            } else {
                return getLatestVideos(callerParam, pageParam);
            }
        }
    }

    @Override
    public List<VideoVo> getVideoListByAid(long aid, PageParam pageParam, CallerParam callerParam) {
        List<VideoVo> returnList = Lists.newArrayList();
        List<TComboEpisode> episodes = gettComboEpisodes(aid, pageParam, callerParam);

        if (CollectionUtils.isNotEmpty(episodes)) {
            for (TComboEpisode comboEpisode : episodes) {
                VideoVo videoVo = videoVoCreater.createVideoVo(comboEpisode);
                returnList.add(videoVo);
            }
            return returnList;
        } else {
            return Collections.EMPTY_LIST;
        }
    }

    @Override
    public List<VideoVo> getVideoVosByAlbumId(String albumId, PageParam pageParam, CallerParam callerParam, String plat) {

        List<VideoVo> returnList = Lists.newArrayList();
        VideoInfos videoInfos = MmsApis.getALLVideosInAlbum(albumId, pageParam.getPage(), pageParam.getCount(), plat);
        if (null != videoInfos) {
            List<VideoInfos.VideoInfo> videoInfos1 = videoInfos.getVideoInfo();
            if (CollectionUtils.isNotEmpty(videoInfos1)) {
                for (VideoInfos.VideoInfo videoInfo : videoInfos1) {
                    VideoVo videoVo = getVideoById(videoInfo.getId(), callerParam);
                    if (null != videoVo && null != videoVo.getId()) {
                        returnList.add(videoVo);
                    }
                }
                return returnList;
            }
        }

        return Collections.EMPTY_LIST;

    }

    @Override
    public List<UltimateVo> getUltimateVosByAlbumId(String albumId, PageParam pageParam, CallerParam callerParam, String plat) {
        List<UltimateVo> returnList = Lists.newArrayList();
        Map<String, List<VideoVo>> map = new LinkedHashMap<>();
        VideoInfos videoInfos = MmsApis.getALLVideosInAlbum(albumId, pageParam.getPage(), pageParam.getCount(), plat);
        if (null != videoInfos) {
            List<VideoInfos.VideoInfo> videoInfos1 = videoInfos.getVideoInfo();
            if (CollectionUtils.isNotEmpty(videoInfos1)) {
                for (VideoInfos.VideoInfo videoInfo : videoInfos1) {
                    VideoVo videoVo = getVideoById(videoInfo.getId(), callerParam);
                    if (null != videoVo && null != videoVo.getCreateTime()) {
                        String month = videoVo.getCreateTime().substring(0, 6);
                        if (CollectionUtils.isNotEmpty(map.get(month))) {
                            map.get(month).add(videoVo);
                        } else {
                            List<VideoVo> latestEpisodeVos = Lists.newArrayList();
                            latestEpisodeVos.add(videoVo);
                            map.put(month, latestEpisodeVos);
                        }
                    }
                }
                returnList = convertMapToList(map);
                return returnList;
            }
        }

        return Collections.EMPTY_LIST;

    }

    private List<UltimateVo> convertMapToList(Map<String, List<VideoVo>> map) {
        List<UltimateVo> returnList = Lists.newArrayList();
        Iterator iterator = map.keySet().iterator();
        while (iterator.hasNext()) {
            UltimateVo episodeUltimateVo = new UltimateVo();
            String month = (String) iterator.next();
            episodeUltimateVo.setMonth(month);
            episodeUltimateVo.setVideoVos(map.get(month));
            returnList.add(episodeUltimateVo);
        }
        return returnList;
    }

    @Override
    public List<UltimateVo> getUltimateById(Long id, PageParam pageParam, CallerParam callerParam) {
        try {
            List<UltimateVo> returnList = Lists.newArrayList();
            Map<String, List<VideoVo>> map = new LinkedHashMap<>();

            List<TComboEpisode> episodes = gettComboEpisodes(id, pageParam, callerParam);
            if (CollectionUtils.isNotEmpty(episodes)) {
                for (TComboEpisode comboEpisode : episodes) {
                    VideoVo videoVo = videoVoCreater.createVideoVo(comboEpisode);
                    String month = comboEpisode.getStartTime().substring(0, 6);
                    if (CollectionUtils.isNotEmpty(map.get(month))) {
                        map.get(month).add(videoVo);
                    } else {
                        List<VideoVo> latestEpisodeVos = Lists.newArrayList();
                        latestEpisodeVos.add(videoVo);
                        map.put(month, latestEpisodeVos);
                    }
                }
                returnList = convertMapToList(map);
                return returnList;
            }
        } catch (Exception e) {
            LOG.error("getMultiEpisodesByIds fail, e={}", e);
        }
        return Collections.EMPTY_LIST;
    }

    private List<TComboEpisode> gettComboEpisodes(Long id, PageParam pageParam, CallerParam callerParam) {
        List<Long> ids = QmtSbcEpisodeApis.getPassedEpisodeIdsInAlbum(id, pageParam, callerParam);
        return QmtSbcEpisodeApis.getTComboEpisodesByIds(ids, callerParam);
    }

    /**
     * 获取比赛页的相关视频
     *
     * @param eid
     * @return
     */
    @Override
    public List<VideoVo> getVideosRelatedWithMatch(long eid, CallerParam callerParam) {
        TComboEpisode episode = QmtSbcEpisodeApis.getTComboEpisodeById(eid, callerParam);
        if (null == episode) {
            LOG.error("episode is null, eid = {}", episode.getId());
        }
        TCaller caller = QmtConfigApis.getTCallerById(callerParam.getCallerId());
        if (null == caller) {
            LOG.error("caller is not exsits, callerId = {}", callerParam.getCallerId());
            return Collections.EMPTY_LIST;
        }
        List<TVideo> episodeVideos = getEpisodeVideos(episode, callerParam, caller.getPlatform());
        LinkedList<Long> videoTypeList = Lists.newLinkedList();
        //优先展示两队相关视频、无两队相关视频用赛事视频补齐(上限30条)
        if (episode.isVs()) {
            //对阵的获取参赛者集合
            List<TCompetitor> competitors = episode.getCompetitors();
            if (CollectionUtils.isNotEmpty(competitors) && competitors.size() <= 2) {
                for (int i = 0; i < competitors.size(); i++) {
                    videoTypeList.add(competitors.get(i).getId());
                }
            } else {
                LOG.error("match competitors is error, eid = {}", episode.getId());
            }
        }
        //添加赛事id
        videoTypeList.add(episode.getCid());
        //有些冷门的赛事没有相关视频,则需要用大项来进行补充
        videoTypeList.add(episode.getGameFType());
        List<TVideo> relateVideos = getVideosByRelatedIds(videoTypeList, callerParam);
        int videoSize = episodeVideos.size() + 20;
        episodeVideos.addAll(relateVideos);
        return formatVideo(episodeVideos, videoSize);

    }

    /**
     * 获取直播点播页的相关视频
     *
     * @param relatedIds
     * @return
     */
    private List<TVideo> getVideosByRelatedIds(List<Long> relatedIds, CallerParam callerParam) {
        List<TVideo> videoList = Lists.newArrayList();
        //获取相关视频
        for (Long relatedId : relatedIds) {
            try {

                GetRelatedVideosParam p = new GetRelatedVideosParam();
                p.setRelatedId(relatedId);
                List<Long> ids = QmtSbcApis.getVideoIdsByRelatedId(p, PageUtils.createPageParam(0, 20), callerParam);
                List<TVideo> videos = QmtSbcApis.getTVideosByIds(ids, callerParam);
                if (CollectionUtils.isNotEmpty(videos)) {
                    videoList.addAll(videos);
                    if (videoList.size() >= 20) {
                        break;
                    }
                }
            } catch (Exception e) {
                LOG.error("fai to  getVideosByRelatedId, relatedId : {}", relatedId, e);
            }
        }
        Collections.sort(videoList, new Comparator<TVideo>() {
            @Override
            public int compare(TVideo o1, TVideo o2) {
                return o2.getCreateAt().compareTo(o1.getCreateAt());
            }
        });
        return videoList;
    }


    private List<VideoVo> formatVideo(List<TVideo> videoList, int videoSize) {
        List<VideoVo> returnList = Lists.newLinkedList();
        //过滤重复视频的idSet
        Set<Long> vidsSet = Sets.newHashSet();
        //添加发布时间和格式化视频播放时长
        if (CollectionUtils.isNotEmpty(videoList)) {
            //不过滤第一个视频是否是克隆视频的标志
            Boolean first_flag = true;
            for (TVideo tVideo : videoList) {
                try {
                    if (tVideo == null) {
                        continue;
                    }
                    VideoVo video = videoVoCreater.createVideoVo(tVideo);
                    //去掉视频集合中的重复视频和克隆视频
                    if (!vidsSet.contains(tVideo.getId())) {
                        if (first_flag || !tVideo.isCloneVideo()) {
                            vidsSet.add(video.getId());
                            returnList.add(video);
                            first_flag = false;//第一个视频过后就需要过滤克隆视频了
                        }
                    }
                } catch (Exception e) {
                    LOG.error("format video time fails, videoId = {}", tVideo.getId());
                }
            }
        }
        return returnList.size() > videoSize ? returnList.subList(0, videoSize) : returnList;
    }

    /**
     * 获取推荐的视频列表
     *
     * @param lc
     * @param uid
     * @param num
     * @return
     */
    @Override
    public List<VideoVo> getVideosBySuggest(String lc, String uid, int num, CallerParam callerParam) {
        List<Map> maps = SuggestApis.searchNullSuggest(lc, uid, num);
        List<VideoVo> videoVos = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(maps)) {
            for (Map map : maps) {
                long vid = (Integer) map.get("vid");
                VideoVo videoVo = getVideoById(vid, callerParam);
                if (null != videoVo) {
                    videoVos.add(videoVo);
                }
            }
        }
        return videoVos;
    }

    private String key2String(int i) {
        if (i < 10) {
            return "0" + i;
        }
        return i + "";
    }

    /**
     * 根据type判断是自制节目id还是媒资专辑id获取专辑视频结合
     *
     * @param aid
     * @param type
     * @param pageParam
     * @param callerParam
     * @return
     */
    @Override
    public List<VideoVo> getVideoVosByAid(long aid, int type, PageParam pageParam, CallerParam callerParam) {
        List<VideoVo> latestEpisodeVos = Lists.newArrayList();
        switch (type) {
            case 0:
                latestEpisodeVos = getVideoListByAid(aid, pageParam, callerParam);
                break;
            case 1:
                latestEpisodeVos = getVideoVosByAlbumId(aid + "", pageParam, callerParam, "tv");
                break;
            default:
                latestEpisodeVos = getVideoListByAid(aid, pageParam, callerParam);
                break;
        }
        return latestEpisodeVos;
    }

    /**
     * 根据type判断是自制节目id还是媒资专辑id获取专辑选集集合
     *
     * @param id
     * @param type
     * @param pageParam
     * @param callerParam
     * @return
     */
    @Override
    public Map<String, Object> getUltimateMapById(long id, int type, PageParam pageParam, CallerParam callerParam) {
        switch (type) {
            case 0:
                return ResponseUtils.createPageResponse(pageParam.getPage(), getUltimateById(id, pageParam, callerParam));
            case 1:
                return ResponseUtils.createPageResponse(pageParam.getPage(), getUltimateVosByAlbumId(id + "", pageParam, callerParam, "tv"));
            default:
                return ResponseUtils.createPageResponse(pageParam.getPage(), getUltimateById(id, pageParam, callerParam));
        }
    }

    @Override
    public VideoInfo getVideoInfoByRelatedIdAndType(GetRelatedVideosParam p, PageParam page, CallerParam caller) {
        VideoInfo videoInfo = new VideoInfo();
        TVideoInfo tVideoInfo = QmtSbcApis.getTVideoInfoByRelatedIdAndType(p, page, caller);
        if (tVideoInfo != null && CollectionUtils.isNotEmpty(tVideoInfo.getVideos())) {
            List<VideoVo> videoVos = Lists.newArrayList();
            for (TVideo tVideo : tVideoInfo.getVideos()) {
                videoVos.add(videoVoCreater.createVideoVo(tVideo));
            }
            videoInfo.setVideos(videoVos);
            videoInfo.setTotal(tVideoInfo.getTotal());
            return videoInfo;
        } else {
            return null;
        }
    }
}
