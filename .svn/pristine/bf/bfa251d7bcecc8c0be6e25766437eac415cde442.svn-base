package com.lesports.qmt.web.api.core.creater;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.lesports.api.common.CallerParam;
import com.lesports.id.client.LeIdApis;
import com.lesports.qmt.sbc.api.dto.TComboEpisode;
import com.lesports.qmt.sbc.api.dto.TNews;
import com.lesports.qmt.sbc.api.dto.TNewsImage;
import com.lesports.qmt.sbc.client.QmtSbcApis;
import com.lesports.qmt.sbc.client.QmtSbcEpisodeApis;
import com.lesports.qmt.web.api.core.util.Constants;
import com.lesports.qmt.web.api.core.vo.HallEpisodeVo;
import com.lesports.qmt.web.api.core.vo.HallEpisodeVo4Tv;
import com.lesports.qmt.web.api.core.vo.SearchResultItem;
import com.lesports.qmt.web.api.core.vo.TopicInfo;
import com.lesports.utils.LeConcurrentUtils;
import com.lesports.utils.LeDateUtils;
import com.lesports.utils.math.LeNumberUtils;
import com.lesports.utils.pojo.SearchData;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import javax.annotation.Resource;
import java.util.*;

/**
 * Created by pangchuanxiao on 2015/6/30.
 */
@Component
public class SearchResultAdapter {
    private static final int SEARCH_SUBJECT_VIDEO_SIZE = 3;
    private static final Function<SearchData.SubjectData, SearchResultItem> subjectFunction = new Function<SearchData.SubjectData, SearchResultItem>() {
        @Nullable
        @Override
        public SearchResultItem apply(@Nullable SearchData.SubjectData subjectData) {
            return adapt(subjectData);
        }
    };
//    private static final Function<SearchData.VideoInfo, SearchResultItem> videoInfoFunction = new Function<SearchData.VideoInfo, SearchResultItem>() {
//        @Nullable
//        @Override
//        public SearchResultItem apply(@Nullable SearchData.VideoInfo videoInfo) {
//            return adapt(videoInfo);
//        }
//    };
//    private static final Function<SearchData.SportNewsData, SearchResultItem> textNewsInfoFunction = new Function<SearchData.SportNewsData, SearchResultItem>() {
//        @Nullable
//        @Override
//        public SearchResultItem apply(@Nullable SearchData.SportNewsData sportNewsData) {
//            return adapt(sportNewsData);
//        }
//    };
    @Resource
    private EpisodeVoCreater episodeVoCreater;

    public <T> List<SearchResultItem> adapt(List<T> list, final Class<T> clazz) {
        if (clazz == SearchData.SubjectData.class) {
            return LeConcurrentUtils.parallelApply((List<SearchData.SubjectData>) list, subjectFunction);
        }
//        else if (clazz == SearchData.VideoInfo.class) {
//            return LeConcurrentUtils.parallelApply((List<SearchData.VideoInfo>) list, videoInfoFunction);
//        } else if (clazz == SearchData.SportNewsData.class) {
//            return LeConcurrentUtils.parallelApply((List<SearchData.SportNewsData>) list, textNewsInfoFunction);
//        }
        return Collections.emptyList();
    }

    public List<HallEpisodeVo> adapt(List<SearchData.LiveData.GameInfo> gameInfos, CallerParam caller) {
        List<HallEpisodeVo> result = Lists.newArrayList();
        Set<Long> episodeIds = Sets.newHashSet();
        if (CollectionUtils.isNotEmpty(gameInfos)) {
            for (SearchData.LiveData.GameInfo gameInfo : gameInfos) {
                HallEpisodeVo episodeVo = adapt(gameInfo, caller);
                if (null != episodeVo && !episodeIds.contains(episodeVo.getId())) {
                    result.add(episodeVo);
                    episodeIds.add(episodeVo.getId());
                }
            }
        }
        return result;
    }

    public List<SearchResultItem> adaptVideos(List<SearchData.VideoInfo> videoInfos,CallerParam caller) {
        List<SearchResultItem> result = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(videoInfos)) {
            for (SearchData.VideoInfo videoInfo : videoInfos) {
                SearchResultItem searchResultItem = adapt(videoInfo, caller);
                if (null != searchResultItem) {
                    result.add(searchResultItem);
                }
            }
        }
        return result;
    }

    public List<SearchResultItem> adaptNews(List<SearchData.SportNewsData> newsDatas,CallerParam caller) {
        List<SearchResultItem> result = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(newsDatas)) {
            for (SearchData.SportNewsData newsData : newsDatas) {
                SearchResultItem searchResultItem = adapt(newsData, caller);
                if (null != searchResultItem) {
                    result.add(searchResultItem);
                }
            }
        }
        return result;
    }

    public HallEpisodeVo adapt(SearchData.LiveData.GameInfo gameInfo, CallerParam caller) {
        HallEpisodeVo episodeVo = null;
        long episodeId = QmtSbcEpisodeApis.getEpisodeIdByLiveId(gameInfo.getId(), caller);
        if (episodeId > 0) {
            TComboEpisode episode = QmtSbcEpisodeApis.getTComboEpisodeById(episodeId,caller);
            if (null != episode) {
                episodeVo = episodeVoCreater.createHallEpisodeVo4App(episode, caller);
            }
        }
        return episodeVo;
    }

    public HallEpisodeVo4Tv adapt4Tv(SearchData.LiveData.GameInfo gameInfo, CallerParam caller) {
        HallEpisodeVo4Tv episodeVo = null;
        long episodeId = QmtSbcEpisodeApis.getEpisodeIdByLiveId(gameInfo.getId(),caller);
        if (episodeId > 0) {
            TComboEpisode episode = QmtSbcEpisodeApis.getTComboEpisodeById(episodeId,caller);
            if (null != episode) {
                episodeVo = episodeVoCreater.createHallEpisodeVo4Tv(episode, caller);
            }
        }
        return episodeVo;
    }

    public static SearchResultItem adapt(SearchData.SubjectData subjectData) {
        if (CollectionUtils.isEmpty(subjectData.getVideos())) {
            return null;
        }
        SearchResultItem result = new SearchResultItem();
        List<TopicInfo.VideoInfo> videoInfos = new ArrayList<>(SEARCH_SUBJECT_VIDEO_SIZE);
        for (SearchData.SubjectData.VideoInfo videoInfo : subjectData.getVideos()) {
            if (videoInfos.size() < SEARCH_SUBJECT_VIDEO_SIZE) {
                TopicInfo.VideoInfo info = new TopicInfo.VideoInfo();
                info.setId(videoInfo.getId());
                info.setName(videoInfo.getName());
                Map<String, String> videoImageUrls = Maps.newHashMap();
                if (null != videoInfo.getImages() && videoInfo.getImages().containsKey(Constants.VIDEO_NEWS_IMAGE_43)) {
                    videoImageUrls.put(Constants.VIDEO_NEWS_IMAGE_43, subjectData.getImages().get(Constants.VIDEO_NEWS_IMAGE_43));
                }
                info.setImageUrl(videoImageUrls);
                videoInfos.add(info);
            }
        }
        result.setVideos(videoInfos);
        result.setId(LeNumberUtils.toLong(subjectData.getAid()));
        result.setDesc(subjectData.getDescription());
        Map<String, String> imageUrls = Maps.newHashMap();
        if (null != subjectData.getImages() && subjectData.getImages().containsKey(Constants.VIDEO_NEWS_IMAGE_BIG_169)) {
            imageUrls.put(Constants.VIDEO_NEWS_IMAGE_BIG_169, subjectData.getImages().get(Constants.VIDEO_NEWS_IMAGE_BIG_169));
        }
        result.setImageUrl(imageUrls);
        result.setName(subjectData.getTitle());

        return result;
    }

    public static SearchResultItem adapt(SearchData.VideoInfo videoInfo,CallerParam caller) {
        SearchResultItem result = new SearchResultItem();
        TNews news = QmtSbcApis.getTNewsByVid(LeIdApis.convertMmsVideoIdToLeSportsVideoId(LeNumberUtils.toLong(videoInfo.getVid())),caller);
        if (null == news) {
            return null;
        }
        result.setId(news.getId());
        result.setVid(LeNumberUtils.toLong(videoInfo.getVid()));
        result.setDesc(videoInfo.getDescription());
        Map<String, String> imageUrls = Maps.newHashMap();
        if (null != videoInfo.getImages() && videoInfo.getImages().containsKey(Constants.VIDEO_NEWS_IMAGE_BIG_169)) {
            imageUrls.put(Constants.VIDEO_NEWS_IMAGE_BIG_169, videoInfo.getImages().get(Constants.VIDEO_NEWS_IMAGE_BIG_169));

        }
        if (null != videoInfo.getImages() && videoInfo.getImages().containsKey(Constants.VIDEO_NEWS_IMAGE_43)) {
            imageUrls.put(Constants.VIDEO_NEWS_IMAGE_43, videoInfo.getImages().get(Constants.VIDEO_NEWS_IMAGE_43));

        }

        if (null != videoInfo.getImages() && videoInfo.getImages().containsKey(Constants.VIDEO_NEWS_IMAGE_169)) {
            imageUrls.put(Constants.VIDEO_NEWS_IMAGE_169, videoInfo.getImages().get(Constants.VIDEO_NEWS_IMAGE_169));

        }
        result.setImageUrl(imageUrls);
        result.setName(videoInfo.getName());
        result.setNewsType(Constants.NEWS_TYPE_VIDEO);
        result.setCreateTime(LeDateUtils.formatYYYYMMDDHHMMSS(new Date(videoInfo.getCtime())));

        return result;
    }

    public static SearchResultItem adapt(SearchData.SportNewsData sportNewsData,CallerParam caller) {
        SearchResultItem result = new SearchResultItem();
        if (null == sportNewsData) {
            return null;
        }
        String globalId = sportNewsData.getGlobalId();
        long id = LeNumberUtils.toLong(StringUtils.substringAfterLast(globalId, "_"));
		TNews news = QmtSbcApis.getTNewsById(id, caller);
        if (null == news) {
            return null;
        }
        result.setId(news.getId());
        result.setVid(null);
        result.setDesc(news.getDesc());
        Map<String, String> imageUrls = Maps.newHashMap();
        List<TNewsImage> images = news.getImages();
        if (CollectionUtils.isNotEmpty(images)) {
            TNewsImage tNewsImage = images.get(0);
            String rawUrl = tNewsImage.getImageUrl();
            imageUrls.put(Constants.VIDEO_NEWS_IMAGE_BIG_169, getImageUrl(rawUrl, Constants.VIDEO_NEWS_IMAGE_BIG_169));
            imageUrls.put(Constants.VIDEO_NEWS_IMAGE_43, getImageUrl(rawUrl, Constants.VIDEO_NEWS_IMAGE_43));
            imageUrls.put(Constants.VIDEO_NEWS_IMAGE_169, getImageUrl(rawUrl, Constants.VIDEO_NEWS_IMAGE_169));
        }

        result.setImageUrl(imageUrls);
        result.setName(news.getName());
        result.setNewsType(Constants.NEWS_TYPE_IMAGETEXT);
        result.setCreateTime(news.getPublishAt());

        return result;
    }

    private static String getImageUrl(String rawUrl, String format) {
        if (null == rawUrl) {
            return null;
        }
        format = StringUtils.replace(format, "*", "_");
        rawUrl = StringUtils.replace(rawUrl, ".jpg", "/169_" + format + ".jpg");
        rawUrl = StringUtils.replace(rawUrl, ".png", "/169_" + format + ".png");
        rawUrl = StringUtils.replace(rawUrl, ".gif", "/169_" + format + ".gif");
        return rawUrl;
    }

}
