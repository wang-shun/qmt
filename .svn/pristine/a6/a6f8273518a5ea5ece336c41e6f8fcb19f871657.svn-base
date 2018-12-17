package com.lesports.qmt.web.api.core.service;

import com.lesports.api.common.CallerParam;
import com.lesports.api.common.PageParam;
import com.lesports.api.common.Platform;
import com.lesports.qmt.sbc.api.dto.TComboEpisode;
import com.lesports.qmt.sbc.api.service.GetRelatedVideosParam;
import com.lesports.qmt.web.api.core.vo.AlbumVideo;
import com.lesports.qmt.web.api.core.vo.UltimateVo;
import com.lesports.qmt.web.api.core.vo.VideoInfo;
import com.lesports.qmt.web.api.core.vo.VideoVo;

import java.util.List;
import java.util.Map;

/**
 * User: ellios
 * Time: 15-11-5 : 上午10:53
 */
public interface VideoService {

    public List<VideoVo> getVideosByRelatedId(long relatedId, PageParam page, CallerParam caller);

    List<VideoVo> getRelatedVideos(long vid, PageParam page, CallerParam caller);

    /**
     * 获取相关视频，tv专用
     * @param vid
     * @param callerParam
     * @param pageParam
     * @return
     */
    Map<String, Object> getRelatedVideos4Tv(long vid, CallerParam callerParam, PageParam pageParam);

    List<VideoVo> getNonPositiveVideosByAid(long aid, PageParam page, CallerParam caller);

    Map<String, Map<String, List<VideoVo>>> getFeatureVideosByAidAndYears(long aid, String yearAndMonth, CallerParam caller);

    List<VideoVo> getLatestVideos(CallerParam callerParam, PageParam pageParam);

    List<VideoVo> getVideosByRelatedIdAndCid(GetRelatedVideosParam p, PageParam page, CallerParam caller) ;

	VideoVo getVideoById(long id, CallerParam callerParam);

    List<AlbumVideo> getAlbumVideos(long aid, PageParam pageParam, CallerParam callerParam);

    List<VideoVo> getVideosBySuggest(String lc, String uid, int num, CallerParam callerParam);

    /**
     * 获取比赛页相关视频
     * @param eid
     * @param callerParam
     * @return
     */
    List<VideoVo> getVideosRelatedWithMatch(long eid, CallerParam callerParam);


    /**
     * 根据专辑id获取录播视频
     * @param aid
     * @param pageParam
     * @param callerParam
     * @return
     */
    List<VideoVo> getVideoListByAid(long aid, PageParam pageParam, CallerParam callerParam);

    /**
     * 获取相关视频
     * @param episode
     * @param callerParam
     * @return
     */
    List<VideoVo> getRelatedVideos(TComboEpisode episode, CallerParam callerParam);


    /**
     * 获取本场速递
     * @param episode
     * @return
     */
    List<VideoVo> getFormatEpisodeVideos(TComboEpisode episode, CallerParam callerParam);

    /**
     *
     * @param eid
     * @param callerParam
     * @param pageParam
     * @return
     */
    Map<String, Object> getEpisodeRelatedVideos(long eid, CallerParam callerParam, PageParam pageParam);


    /**
     * TV根据媒资专辑id获取选集信息
     * @param albumId
     * @param pageParam
     * @param callerParam
     * @param plat
     * @return
     */
    List<UltimateVo> getUltimateVosByAlbumId(String albumId, PageParam pageParam, CallerParam callerParam, String plat);

    /**
     * TV根据媒资专辑id获取视频信息
     * @param albumId
     * @param pageParam
     * @param callerParam
     * @param plat
     * @return
     */
    List<VideoVo> getVideoVosByAlbumId(String albumId, PageParam pageParam, CallerParam callerParam, String plat);

    /**
     * 获取自制节目选集，按月份
     *
     * @param id
     * @param pageParam
     * @param callerParam
     * @return
     */
    List<UltimateVo> getUltimateById(Long id, PageParam pageParam, CallerParam callerParam);

    /**
     * 根据type判断是自制节目id还是媒资专辑id获取专辑视频结合
     * @param aid
     * @param type
     * @param pageParam
     * @param callerParam
     * @return
     */

    List<VideoVo> getVideoVosByAid(long aid, int type, PageParam pageParam, CallerParam callerParam);

    /**
     * 根据type判断是自制节目id还是媒资专辑id获取专辑选集集合
     * @param id
     * @param type
     * @param pageParam
     * @param callerParam
     * @return
     */
    Map<String, Object> getUltimateMapById(long id, int type, PageParam pageParam, CallerParam callerParam);

    VideoInfo getVideoInfoByRelatedIdAndType(GetRelatedVideosParam p, PageParam page, CallerParam caller);

    List<VideoVo> getRelatedVideosByEid(long eid, CallerParam callerParam,PageParam pageParam);

}
