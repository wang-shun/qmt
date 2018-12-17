package com.lesports.qmt.web.api.core.service;

import com.lesports.api.common.CallerParam;
import com.lesports.qmt.web.api.core.vo.SearchLiveResult;
import com.lesports.qmt.web.api.core.vo.SearchResult;
import com.lesports.qmt.web.api.core.vo.SearchResult4Tv;
import com.lesports.qmt.web.api.core.vo.SearchSuggestItem;
import com.lesports.utils.pojo.SearchData;

import java.util.List;

/**
 * lesports-projects
 *
 * @author pangchuanxiao
 * @since 15-6-27
 */
public interface SearchService {
    /**
     * search subject/match/news related object.
     *
     * @param word  the key word which user input.
     * @param page  the page user select.
     * @param count the size of this page.
     * @param uid   user id of the request.
     * @param imei  user device id.
     * @return
     */
    public SearchResult search(String word, int page, int count, String uid, String imei, String hl, CallerParam caller, String deviceId);

    public List<SearchSuggestItem> suggest(String word, long callerId, int count);

    /**
     * TV端搜索
     *
     * @param word
     * @param page
     * @param count
     * @param caller
     * @param uid
     * @param imei
     * @return
     */
    public SearchResult4Tv searchTV(String word, int page, int count, CallerParam caller, String uid, String imei, String hl, String deviceId);


    public boolean valid(String word);


    /**
     * 获取明星列表
     *
     * @param word
     * @param page
     * @param count
     * @param callerId
     * @param uid
     * @param imei
     * @return
     */
    List<SearchData.StarInfo> searchStars(String word, int page, int count, long callerId, String uid, String imei, String hl, String deviceId);

    /**
     * 获取搜索直播列表
     *
     * @param word
     * @param page
     * @param count
     * @param uid
     * @param imei
     * @return
     */
    public List<SearchLiveResult> searchLives(String word, int page, int count, String uid, String imei, String hl, CallerParam caller, String deviceId);

    /**
     * 获取搜索专题列表
     *
     * @param word
     * @param page
     * @param count
     * @param callerId
     * @param uid
     * @param imei
     * @return
     */
    public List<SearchData.SubjectData> searchSubjects(String word, int page, int count, long callerId, String uid, String imei, String hl, String deviceId);


    /**
     * 获取搜索专辑列表
     *
     * @param word
     * @param page
     * @param count
     * @param callerId
     * @param uid
     * @param imei
     * @return
     */
    public List<SearchData.AlbumData> searchAlbums(String word, int page, int count, long callerId, String uid, String imei, String hl, String deviceId);

    /**
     * 获取单视频列表
     *
     * @param word
     * @param page
     * @param count
     * @param callerId
     * @param uid
     * @param imei
     * @return
     */
    public List<SearchData.VideoInfo> searchVideos(String word, int page, int count, long callerId, String uid, String imei, String hl, String dur, String releaseRange, String style, String or, String deviceId);


    /**
     *
     * @param word
     * @param page
     * @param count
     * @param callerId
     * @param uid
     * @param imei
     * @param dur
     * @param releaseRange
     * @param style
     * @param or
     * @param deviceId
     * @return
     */
    int searchVideoCount(String word, int page, int count, long callerId, String uid, String imei, String hl, String dur, String releaseRange, String style, String or, String deviceId);

    }