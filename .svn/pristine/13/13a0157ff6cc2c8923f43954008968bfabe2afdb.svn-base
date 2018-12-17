package com.lesports.qmt.web.api.core.service.impl;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.lesports.LeConstants;
import com.lesports.api.common.CallerParam;
import com.lesports.api.common.Platform;
import com.lesports.api.common.PubChannel;
import com.lesports.qmt.config.api.dto.TCaller;
import com.lesports.qmt.config.client.QmtConfigApis;
import com.lesports.qmt.web.api.core.creater.SearchResultAdapter;
import com.lesports.qmt.web.api.core.service.SearchService;
import com.lesports.qmt.web.api.core.util.Constants;
import com.lesports.qmt.web.api.core.vo.*;
import com.lesports.utils.LeDateUtils;
import com.lesports.utils.LeProperties;
import com.lesports.utils.SearchApis;
import com.lesports.utils.pojo.SearchData;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import javax.annotation.Resource;
import java.util.*;
import java.util.regex.Pattern;

/**
 * lesports-projects
 *
 * @author pangchuanxiao
 * @since 15-6-27
 */
@Service("searchService")
public class SearchServiceImpl implements SearchService {

    private static final Logger LOG = LoggerFactory.getLogger(SearchServiceImpl.class);
    private static final int MAX_LIVE_COUNT = LeProperties.getInt("count.max.live", 3);
    private static final int MAX_SUBJECT_COUNT = LeProperties.getInt("count.max.subject", 1);
    private static final int APP_VIDEO_TEXT_NEWS_RATIO = LeProperties.getInt("app.video.text.news.ratio", 3);
    String pattern = ".*[\u4e00-\u9fa5\\w]+.*";
    @Resource
    private SearchResultAdapter searchResultAdapter;

    @Override
    public List<SearchSuggestItem> suggest(String word, long callerId, int count) {
        List<SearchData.Suggest> suggests = SearchApis.suggest(word, getPlatFormFromCaller(callerId));
        if (CollectionUtils.isNotEmpty(suggests)) {
            sortMostMatch(suggests, word);
            int size = suggests.size() > count ? count : suggests.size();
            return Lists.transform(suggests.subList(0, size), new Function<SearchData.Suggest, SearchSuggestItem>() {
                @Nullable
                @Override
                public SearchSuggestItem apply(SearchData.Suggest suggest) {
                    SearchSuggestItem searchSuggest = new SearchSuggestItem();
                    searchSuggest.setSuggest(suggest.getName());
                    return searchSuggest;
                }
            });
        }
        return Collections.emptyList();
    }


    private static boolean isValidLive(SearchData.LiveData.GameInfo gameInfo) {
        if (null != gameInfo) {
            Date startDate = LeDateUtils.parseYMDHMS(gameInfo.getMatchStartTime());
            Calendar right = Calendar.getInstance();
            right.add(Calendar.DAY_OF_MONTH, 2);
            Calendar left = Calendar.getInstance();
            left.add(Calendar.DAY_OF_MONTH, -5);
            if (startDate.after(left.getTime()) && startDate.before(right.getTime())) {
                return true;
            }
        }
        return false;
    }

    private boolean isRepeatEpisode(HallEpisodeVo hallEpisodeVo, List<HallEpisodeVo> liveResult) {

        boolean result = false;
        if (CollectionUtils.isNotEmpty(liveResult)) {
            for (HallEpisodeVo hallEpisodeVo1 : liveResult) {
                if (hallEpisodeVo.getId().equals(hallEpisodeVo1.getId())) {
                    result = true;
                    break;
                }
            }

        }
        return result;

    }

    private boolean isRepeatEpisode4Tv(HallEpisodeVo4Tv hallEpisodeVo, List<HallEpisodeVo4Tv> liveResult) {

        boolean result = false;
        if (CollectionUtils.isNotEmpty(liveResult)) {
            for (HallEpisodeVo4Tv hallEpisodeVo1 : liveResult) {
                if (hallEpisodeVo.getId().equals(hallEpisodeVo1.getId())) {
                    result = true;
                    break;
                }
            }

        }
        return result;

    }


    private long getPlatFormFromCaller(long callerId) {
        TCaller tCaller = QmtConfigApis.getTCallerById(callerId);
        if (null == tCaller) {
            throw new IllegalArgumentException("illegal callerId : " + callerId);
        }

        if (tCaller.getPlatform() == Platform.PC) {
            return LeConstants.LESPORTS_PC_CALLER_CODE;
        } else if (tCaller.getPlatform() == Platform.MOBILE) {
            return LeConstants.LESPORTS_MOBILE_APP_CALLER_CODE;
        } else if (tCaller.getPlatform() == Platform.TV) {
            return LeConstants.LESPORTS_TV_CALLER_CODE;
        } else if (tCaller.getPlatform() == Platform.PAD) {
            return LeConstants.LESPORTS_PAD_APP_CALLER_CODE;
        } else {
            throw new IllegalArgumentException("illegal callerId : " + callerId);
        }

    }

    @Override
    public SearchResult search(String word, int page, int count, String uid, String imei, String hl, CallerParam caller, String deviceId) {
        SearchResult searchResultItems = new SearchResult();
        searchResultItems.setPage(page);

        if (page <= 1) {
            List<HallEpisodeVo> liveResult = new ArrayList<>();
            int idle = count;
            int i = 1;
            while (liveResult.size() < MAX_LIVE_COUNT) {
                List<SearchData.LiveData> liveDatas = SearchApis.searchLiveData(word, i++, MAX_LIVE_COUNT, getPlatFormFromCaller(caller.getCallerId()), uid, imei, hl, deviceId);
                if (CollectionUtils.isEmpty(liveDatas)) {
                    break;
                }
                for (SearchData.LiveData liveData : liveDatas) {
                    if (null != liveData && CollectionUtils.isNotEmpty(liveData.getGames())) {
                        for (SearchData.LiveData.GameInfo gameInfo : liveData.getGames()) {
                            if (null != gameInfo && isValidLive(gameInfo)) {
                               	HallEpisodeVo hallEpisodeVo = searchResultAdapter.adapt(gameInfo, caller);
                                if (null != hallEpisodeVo && !isRepeatEpisode(hallEpisodeVo, liveResult)) {
                                    liveResult.add(hallEpisodeVo);
                                }
                                if (liveResult.size() >= MAX_LIVE_COUNT) {
                                    break;
                                }
                            }
                        }
                    }
                }
            }

            searchResultItems.setLives(liveResult);
            idle -= liveResult.size();

            List<SearchData.SubjectData> subjectDatas = SearchApis.searchSubjectData(word, 1, MAX_SUBJECT_COUNT, getPlatFormFromCaller(caller.getCallerId()), uid, imei, hl, deviceId);
            List<SearchResultItem> subjectResult = searchResultAdapter.adapt(subjectDatas, SearchData.SubjectData.class);
            searchResultItems.setTopics(subjectResult);
            idle -= subjectResult.size();

            List<SearchResultItem> newsResult = searchNews(word, 1, idle, uid, imei, hl, caller, deviceId);
            searchResultItems.setNews(newsResult);
            searchResultItems.setCount(liveResult.size() + newsResult.size() + subjectResult.size());
        } else {
            List<SearchResultItem> newsResult = searchNews(word, page, count, uid, imei, hl, caller, deviceId);
            searchResultItems.setNews(newsResult);
            searchResultItems.setCount(newsResult.size());
        }
        return searchResultItems;
    }

    @Override
    public List<SearchLiveResult> searchLives(String word, int page, int count, String uid, String imei, String hl, CallerParam caller, String deviceId) {
        List<SearchLiveResult> searchLiveResults = new ArrayList<>();

        List<SearchData.LiveData> liveDatas = SearchApis.searchLiveData(word, page, count, getPlatFormFromCaller(caller.getCallerId()), uid, imei, hl, deviceId);

        for (SearchData.LiveData liveData : liveDatas) {
            SearchLiveResult searchLiveResult = new SearchLiveResult();
            searchLiveResult.setChannel(liveData.getChannel());
            searchLiveResult.setGameName(liveData.getGameName());
            searchLiveResult.setImgUrl(liveData.getImgUrl());
            searchLiveResult.setSport(liveData.getSport());
            List<HallEpisodeVo> liveResult = Lists.newArrayList();
            if (null != liveData && CollectionUtils.isNotEmpty(liveData.getGames())) {
                for (SearchData.LiveData.GameInfo gameInfo : liveData.getGames()) {
                    if (null != gameInfo) {
                        HallEpisodeVo hallEpisodeVo = searchResultAdapter.adapt(gameInfo, caller);
                        if (null != hallEpisodeVo && !isRepeatEpisode(hallEpisodeVo, liveResult)) {
                            liveResult.add(hallEpisodeVo);
                        }
                    }
                }
            }
            searchLiveResult.setGames(liveResult);
            searchLiveResults.add(searchLiveResult);
        }

        return searchLiveResults;
    }

    @Override
    public List<SearchData.StarInfo> searchStars(String word, int page, int count, long callerId, String uid, String imei, String hl, String deviceId) {
        List<SearchData.StarInfo> StarInfos = SearchApis.searchStarData(word, page, count, getPlatFormFromCaller(callerId), uid, imei, hl, deviceId);
        return StarInfos;
    }

    @Override
    public List<SearchData.SubjectData> searchSubjects(String word, int page, int count, long callerId, String uid, String imei, String hl, String deviceId) {
        List<SearchData.SubjectData> subjectDatas = SearchApis.searchSubjectData(word, page, count, getPlatFormFromCaller(callerId), uid, imei, hl, deviceId);
        return subjectDatas;
    }

    @Override
    public List<SearchData.AlbumData> searchAlbums(String word, int page, int count, long callerId, String uid, String imei, String hl, String deviceId) {
        List<SearchData.AlbumData> albumDatas = SearchApis.searchAlbumData(word, page, count, getPlatFormFromCaller(callerId), uid, imei, hl, deviceId);
        return albumDatas;
    }

    @Override
    public List<SearchData.VideoInfo> searchVideos(String word, int page, int count, long callerId, String uid, String imei, String hl, String dur, String releaseRange, String style, String or, String deviceId) {
        List<SearchData.VideoInfo> videoInfos = SearchApis.searchVideoData(word, page, count, getPlatFormFromCaller(callerId), uid, imei, hl, dur, releaseRange, style, or, deviceId);
        return videoInfos;
    }

    @Override
    public int searchVideoCount(String word, int page, int count, long callerId, String uid, String imei, String hl, String dur, String releaseRange, String style, String or, String deviceId) {
        int videoCount = SearchApis.searchVideoCount(word, page, count, getPlatFormFromCaller(callerId), uid, imei, hl, dur, releaseRange, style, or, deviceId);
        return videoCount;
    }

    @Override
    public SearchResult4Tv searchTV(String word, int page, int count, CallerParam caller, String uid, String imei, String hl, String deviceId) {
        SearchResult4Tv searchResultItems = new SearchResult4Tv();
        searchResultItems.setPage(page);
        if (page <= 1) {
            List<HallEpisodeVo4Tv> liveResult = new ArrayList<>();
            int idle = count;
            int i = 1;
            while (liveResult.size() < MAX_LIVE_COUNT) {
                List<SearchData.LiveData> liveDatas = SearchApis.searchLiveData(word, i++, MAX_LIVE_COUNT, getPlatFormFromCaller(caller.getCallerId()), uid, imei, hl, deviceId);
                if (CollectionUtils.isEmpty(liveDatas)) {
                    break;
                }
                for (SearchData.LiveData liveData : liveDatas) {
                    if (null != liveData && CollectionUtils.isNotEmpty(liveData.getGames())) {
                        for (SearchData.LiveData.GameInfo gameInfo : liveData.getGames()) {
                            if (null != gameInfo && isValidLive(gameInfo)) {
                                HallEpisodeVo4Tv hallEpisodeVo = searchResultAdapter.adapt4Tv(gameInfo, caller);
                                if (null != hallEpisodeVo && !isRepeatEpisode4Tv(hallEpisodeVo, liveResult)) {
                                    if(hallEpisodeVo.getCid().equals(Constants.ETL_CID) && caller.getPubChannel() == PubChannel.TCL){
                                        continue;
                                    }
                                    liveResult.add(hallEpisodeVo);
                                }
                                if (liveResult.size() >= MAX_LIVE_COUNT) {
                                    break;
                                }
                            }
                        }
                    }
                }
            }

            searchResultItems.setLives(liveResult);
            idle -= liveResult.size();

            List<SearchData.VideoInfo> newsDatas = SearchApis.searchVideoData(word, 1, idle, getPlatFormFromCaller(caller.getCallerId()), uid, imei, hl, "", "", "", "", deviceId);
            List<SearchResultItem> newsResult = searchResultAdapter.adaptVideos(newsDatas,caller);
            searchResultItems.setNews(newsResult);
            searchResultItems.setCount(liveResult.size() + newsResult.size());
        } else {
            List<SearchData.VideoInfo> newsDatas = SearchApis.searchVideoData(word, page, count, getPlatFormFromCaller(caller.getCallerId()), uid, imei, hl, "", "", "", "", deviceId);
            List<SearchResultItem> newsResult = searchResultAdapter.adaptVideos(newsDatas,caller);
            searchResultItems.setNews(newsResult);
            searchResultItems.setCount(newsResult.size());
        }
        return searchResultItems;
    }

    /**
     * 搜索新闻数据
     *
     * @param word
     * @param page
     * @param count
     * @param uid
     * @param imei
     * @param hl
     * @param caller
     * @param deviceId
     * @return
     */
    private List<SearchResultItem> searchNews(String word, int page, int count, String uid, String imei, String hl, CallerParam caller, String deviceId) {
        List<SearchResultItem> newsResult = Lists.newArrayList();
        int videoCount = getNewsVideoCount(caller.getCallerId(), count);
        int textCount = count - videoCount;
        List<SearchData.VideoInfo> videoInfos = SearchApis.searchVideoData(word, page, videoCount, getPlatFormFromCaller(caller.getCallerId()), uid, imei, hl, "", "", "", "", deviceId);
        newsResult.addAll(searchResultAdapter.adaptVideos(videoInfos,caller));

        if (textCount > 0) {
            List<SearchData.SportNewsData> sportNewsDatas = SearchApis.searchSportNews(word, page, textCount, getPlatFormFromCaller(caller.getCallerId()), uid, imei, hl, "", "", "", "", deviceId);
            newsResult.addAll(searchResultAdapter.adaptNews(sportNewsDatas,caller));
        }
        //新闻按照发布时间排序
        Collections.sort(newsResult, new Comparator<SearchResultItem>() {
            @Override
            public int compare(SearchResultItem o1, SearchResultItem o2) {
                return null != o1.getCreateTime() && null != o2.getCreateTime() ? o2.getCreateTime().compareTo(o1.getCreateTime()) : 0;
            }
        });

        return newsResult;
    }

    /**
     * 根据比例获取视频新闻数量
     * @param callerId
     * @param count
     * @return
     */
    private int getNewsVideoCount(long callerId, int count) {
        int videoCount = count;
//        if (callerId == Caller.SPORTS_APP.getValue()) {
//            videoCount = (int) (count * (APP_VIDEO_TEXT_NEWS_RATIO / (double) (APP_VIDEO_TEXT_NEWS_RATIO + 1)));
//        }
        return videoCount;
    }

    private void sortMostMatch(List<SearchData.Suggest> items, final String word) {
        Comparator<SearchData.Suggest> MOST_MATCH = new Comparator<SearchData.Suggest>() {
            @Override
            public int compare(SearchData.Suggest o1, SearchData.Suggest o2) {
                if (o2.getName().equals(word)) {
                    return 1;
                } else if (o1.getName().equals(word)) {
                    return -1;
                }
                return 0;
            }
        };
        Collections.sort(items, MOST_MATCH);
    }

    @Override
    public boolean valid(String word) {
        if (StringUtils.isEmpty(word)) {
            return false;
        }
        if (Pattern.matches(pattern, word)) {
            return true;
        }
        return false;
    }
}
