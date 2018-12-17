package com.lesports.qmt.sbc.service;

import com.lesports.api.common.CallerParam;
import com.lesports.qmt.sbc.api.dto.TComboEpisode;
import com.lesports.qmt.sbc.api.dto.TSimpleEpisode;
import com.lesports.qmt.sbc.api.service.*;
import com.lesports.qmt.sbc.model.Episode;
import com.lesports.qmt.sbc.model.Live;
import com.lesports.qmt.service.support.QmtService;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

/**
 * Created by lufei1 on 2016/11/4.
 */
public interface EpisodeService extends QmtService<Episode, Long> {
    /**
     * 集锦id
     *
     * @param episodeId
     * @param videoId
     * @return
     */
    boolean updateHighlightId(Long episodeId, Long videoId);

    /**
     * 选集关联
     *
     * @param episodeId
     * @param videoId
     * @return
     */
    boolean updateSelfMadeId(Long episodeId, Long videoId);

    /**
     * 更新节目关联的直播信息
     *
     * @param episodeId
     * @param live
     * @return
     */
    boolean updateLive(Long episodeId, Live live);

    /**
     * 删除节目上关联的直播
     *
     * @param episodeId
     * @param liveId
     * @return
     */
    boolean deleteLive(Long episodeId, Long liveId);

    /**
     * 通过match id创建episode
     *
     * @param matchId
     * @return 返回新创建的episode的id
     */
    List<Long> createEpisodes(long matchId);

    /**
     * 获取自制专辑下的最新一期
     *
     * @param aid
     * @return
     */
    Episode getLatestEpisodeByAid(long aid);

    /**
     * 获取直播id所关联的节目id
     *
     * @param liveId
     * @return
     */
    long getEpisodeIdByLiveId(String liveId, CallerParam caller);

    /**
     * 获取图文直播所关联的节目id
     *
     * @param textLiveId
     * @param caller
     * @return
     */
    long getEpisodeIdByTextLiveId(long textLiveId, CallerParam caller);

    /**
     * 根据比赛id获取节目
     *
     * @param mid
     * @param caller
     * @return
     */
    long getEpisodeIdByMid(long mid, CallerParam caller);


    /**
     * 根据节目id获取节目vo信息
     *
     * @param episodeId
     * @param caller
     * @return
     */
    TComboEpisode getTComboEpisodeById(long episodeId, CallerParam caller);

    /**
     * 根据节目id批量获取节目信息
     *
     * @param episodeIds
     * @param caller
     * @return
     */
    List<TComboEpisode> getTComboEpisodesByIds(List<Long> episodeIds, CallerParam caller);

    /**
     * 从当前时间开始获取节目列表
     *
     * @param p
     * @param caller
     * @param page
     * @return
     */
    List<Long> getCurrentEpisodeIds(GetCurrentEpisodesParam p, Pageable page, CallerParam caller);

    /**
     * 获取某天的节目列表
     *
     * @param p
     * @param caller
     * @param page
     * @return
     */
    List<Long> getSomeDayEpisodeIds(GetSomeDayEpisodesParam p, Pageable page, CallerParam caller);

    /**
     * 获取某个赛季，某个阶段的的比赛信息,如(第几轮的比赛)
     *
     * @param p
     * @param caller
     * @param page
     * @return
     */
    List<Long> getEpisodeIdsOfSeasonByMetaEntryId(GetEpisodesOfSeasonByMetaEntryIdParam p, Pageable page, CallerParam caller);

    /**
     * 获取和某节目相关的节目列表
     *
     * @param episodeId
     * @param count
     * @param caller
     * @return
     */
    List<Long> getEpisodeIdsRelatedWithSomeEpisode(long episodeId, int count, CallerParam caller);

    /**
     * 按天计算节目数量
     *
     * @param p
     * @param caller
     * @return
     */
    List<Long> countEpisodesByDay(CountEpisodesByDayParam p, CallerParam caller);

    /**
     * 获取某节目附近的其他节目
     *
     * @param p
     * @param caller
     * @return
     */
    Map<String, Long> getEpisodeIdsNearbySomeEpisode(GetEpisodesNearbySomeEpisodeParam p, CallerParam caller);

    /**
     * 根据专辑id获取专辑下节目（已结束或无直播）
     *
     * @param albumId
     * @param pageable
     * @param caller
     * @return
     */
    List<Long> getPassedEpisodeIdsInAlbum(Long albumId, Pageable pageable, CallerParam caller);


    List<TComboEpisode> getEpisodeByAid(long aid, Pageable pageable, CallerParam caller);

    /**
     * 根据章鱼赛程ids获取节目列表
     *
     * @param p
     * @param pageable
     * @param caller
     * @return
     */
    List<Long> getEpisodeIdsOfOctopus(GetZhangyuEpisodesParam p, Pageable pageable, CallerParam caller);

    /**
     * 根据参数这获取节目列表
     *
     * @param competitorId
     * @param p
     * @param pageable
     * @param caller
     * @return
     */
    List<Long> getCurrentEpisodeIdsByCompetitorId(long competitorId, GetEpisodesOfSeasonByMetaEntryIdParam p, Pageable pageable, CallerParam caller);

    /**
     * 获取第一期和最新一期节目
     *
     * @param aid
     * @param caller
     * @return
     */
    List<TComboEpisode> getFirstAndLatestEpisodesByAid(long aid, CallerParam caller);

    /**
     * 获取某个月的节目信息
     *
     * @param aid
     * @return
     */
    List<TComboEpisode> getEpisodeByYearAndMonth(long aid, String yearAndMonth, CallerParam caller);


    void updateEpisodeSyncToCloud(long episodeId, Boolean isSyncToCloud);


    void updateSyncToCloudOfEpisodesWithCid(long cid);

    /**
     * 清理rpc缓存
     *
     * @param id
     * @return
     */
    boolean deleteRpcCache(long id);

    /**
     * 查询运营选定的时间段内的全部桌面推荐赛程
     *
     * @param caller
     * @return
     */
    List<Long> getPeriodAppRecommendEpisodes(CallerParam caller);

    /**
     * 晋级之路
     *
     * @param cid
     * @param csid
     * @param caller
     * @return
     */
    List<Long> getTheRoadOfAdvance(long cid, long csid, CallerParam caller);

    List<TComboEpisode> getAppRecommendEpisodes(Boolean isRecommendTag, long cid, CallerParam caller);

    TSimpleEpisode getTSimpleEpisodeById(long episodeId, CallerParam caller);

    /**
     * 从当前时间开始获取会员节目列表
     *
     * @param p
     * @param caller
     * @param page
     * @return
     */
    List<Long> getMemberEpisodeIds(GetCurrentEpisodesParam p, Pageable page, CallerParam caller);

    List<String> getStartDatesBySomeDayEpisodeParam(CountEpisodesByDayParam p, CallerParam caller);

    List<Long> getTimelineEpisodesByCids(GetCurrentEpisodesParam p, Pageable pageable, CallerParam caller);

    /**
     * 按时区获取某天的节目
     *
     * @param p
     * @param page
     * @param caller
     * @return
     */
    List<Long> getSomeDayEpisodeIdsWithTimezone(GetSomeDayEpisodesParam p, Pageable page, CallerParam caller);

    /**
     * 根据直播券获取付费节目
     *
     * @param p
     * @param pageable
     * @param caller
     * @return
     */
    List<Long> getTicketEpisodeIds(GetCurrentEpisodesParam p, Pageable pageable, CallerParam caller);

}
