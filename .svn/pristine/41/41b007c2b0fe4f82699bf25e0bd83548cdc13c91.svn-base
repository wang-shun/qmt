package com.lesports.qmt.web.api.core.service;

import com.lesports.api.common.CallerParam;
import com.lesports.api.common.PageParam;
import com.lesports.qmt.sbc.api.dto.TComboEpisode;
import com.lesports.qmt.sbc.api.service.*;
import com.lesports.qmt.web.api.core.vo.*;

import java.util.List;
import java.util.Map;

/**
 * Created by gengchengliang on 2015/6/18.
 */
public interface EpisodeService {

    /**
     * 获取某天的节目列表
     *
     * @param p
     * @param page
     * @param caller
     * @param isNeedSection
     * @return
     */
    List<CalEpisodeVo> getDayEpisodes(GetSomeDayEpisodesParam p, PageParam page, CallerParam caller, Boolean isNeedSection);


	List<HallEpisodeVo> getResourceContentEpisodes(long resourceId, PageParam page, CallerParam caller);


    /**
     * pc 直播日历 自带服务降级
     *
     * @param isNeedSection
     * @return
     */
    List<CalEpisodeVo> getDayEpisodesWithFallback(GetSomeDayEpisodesParam p, PageParam page, CallerParam caller, Boolean isNeedSection);

    /**
     * 获取N天的节目信息
     *
     * @param p
     * @param page
     * @param caller
     * @return
     */
    List<CalEpisodeVo> getSomedayEpisodes(String startDate, int days, GetSomeDayEpisodesParam p, PageParam page, CallerParam caller);


    /**
     * 通赛事id和元数据字典id获取专题用的节目列表
     *
     * @param p
     * @param page
     * @param caller
     * @return
     */
    List<SimpleEpisodeVo> getTopicEpisodesByCidAndMetaEntryId(GetEpisodesOfSeasonByMetaEntryIdParam p, PageParam page, CallerParam caller);


    /**
     * * 日历 count接口
     *
     * @param p
     * @param callerParam
     * @return
     */
    Map<String, Object> countEpisodesByDay(CountEpisodesByDayParam p, CallerParam callerParam);

    /**
     * 获取某天的所有节目，为比赛大厅提供的接口
     *
     * @param p
     * @param page
     * @param caller
     * @return
     */
    List<HallEpisodeVo> getSomeDayEpisodes(GetSomeDayEpisodesParam p, PageParam page, CallerParam caller);

    /**
     * 获取某天的所有节目，为比赛大厅提供的接口
     *
     * @param p
     * @param page
     * @param caller
     * @return
     */
    List<HallEpisodeVo> getSomeDayEpisodesWithFallback(GetSomeDayEpisodesParam p, PageParam page, CallerParam caller);

    /**
     * 获取赛程详情
     *
     * @param id
     * @param callerParam
     * @return
     */
    DetailEpisodeVo getEpisodeById(long id, CallerParam callerParam);

    /**
     * 获取赛程详情 - app用
     *
     * @param id
     * @return
     */
    DetailEpisodeVo getEpisodeById4App(long id, CallerParam caller);

    /**
     * 获取赛程详情实时
     *
     * @param id
     * @return
     */
    DetailEpisodeVo getEpisodeByIdRealtime(long id, CallerParam caller);

    /**
     * 获取赛程详情实时 - app用
     *
     * @param id
     * @return
     */
    DetailEpisodeVo getEpisodeById4AppRealtime(long id, CallerParam caller);

    /**
     * 获取大厅的节目列表
     *
     * @return
     */
    List<HallEpisodeVo> getHallEpisodes4App(GetCurrentEpisodesParam p, PageParam pageParam, CallerParam callerParam);


    /**
     * 获取
     *
     * @param p
     * @param page
     * @param caller
     * @return
     */
    List<HallEpisodeVo> getHallEpisodes4AppWithFallback(GetCurrentEpisodesParam p, PageParam page, CallerParam caller);

	List<HallEpisodeVo> getTimelineEpisodesByCids(GetCurrentEpisodesParam p, PageParam page, CallerParam caller);

    /**
     * 获取大厅的节目列表For Tv
     *
     * @param p
     * @param page
     * @param caller
     * @return
     */
    List<HallEpisodeVo4Tv> getHallEpisodes4Tv(GetSomeDayEpisodesParam p, PageParam page, CallerParam caller);

    /**
     * 轮询比分和状态的接口
     *
     * @param ids
     * @return
     */
    List<PollingEpisodeVo> refreshEpisodesByIds(String ids, CallerParam caller);

    /**
     * 轮询比分和状态的接口real time web里使用
     *
     * @param ids
     * @return
     */
    List<PollingEpisodeVo> refreshEpisodesByIdsRealtime(String ids, CallerParam caller);

    /**
     * app定制的轮询比分和状态的接口
     *
     * @param ids
     * @return
     */
    List<PollingEpisodeVo> refreshEpisodesByIds4App(String ids, CallerParam caller);

    /**
     * 位app定制的轮询比分和状态的接口for real time
     *
     * @param ids
     * @return
     */
    List<PollingEpisodeVo> refreshEpisodesByIds4AppRealtime(String ids, CallerParam caller);

    /**
     * 通过参赛者id获取节目
     * id 参赛者id
     * csid 赛季id
     * status 比赛状态: -1:全部 0:未开始 1:比赛中 2:已结束 3:未结束
     * contain_type 节目包含的属性 0:只要直播 1:直播和重点赛程 2:所有节目
     * pageRequest 分页
     */
    List<HallEpisodeVo> getEpisodesByCompetitorId(long competitorId, GetEpisodesOfSeasonByMetaEntryIdParam p, PageParam page, CallerParam caller);

    /**
     * 批量查询赛程信息
     *
     * @param ids
     * @return
     */
    List<HallEpisodeVo> getMultiEpisodesByIds(String ids, CallerParam caller);

    /**
     * 批量查询赛程信息代理接口使用for real time
     *
     * @param ids
     * @return
     */
    List<HallEpisodeVo> getMultiEpisodesByIdsRealtime(String ids, CallerParam caller);

    /**
     * 批量查询赛程信息
     *
     * @param ids
     * @return
     */
    List<HallEpisodeVo> getMultiEpisodesByIds4App(String ids, CallerParam caller);

    /**
     * 批量查询赛程信息代理使用for real time
     *
     * @param ids
     * @return
     */
    List<HallEpisodeVo> getMultiEpisodesByIds4AppRealtime(String ids, CallerParam caller);


    List<HallEpisodeVo> getEpisodesByCids4Ipad(GetSomeDayEpisodesParam p, PageParam pageParam, CallerParam callerParam);


    List<HallEpisodeVo4Tv> getEpisodesByCids4TV(GetSomeDayEpisodesParam p, PageParam pageParam, CallerParam callerParam);
    /**
     * 获取赛事节目信息
     *
     * @param p cid 赛季id
     *          live_type 节目包含的属性 0:只要直播 1:直播和重点赛程 2:所有节目
     *          page 页码
     *          count 每页条数
     * @return
     */
    List<HallEpisodeVo> getMatchEpisodesByCidAndMetaEntryId(GetEpisodesOfSeasonByMetaEntryIdParam p, PageParam page, CallerParam caller);

    /**
     * 根据专辑id获取往期节目
     *
     * @return
     */
    List<LatestEpisodeVo> getPreviousEpisodesById(Long id, PageParam page, CallerParam caller);

    /**
     * 根据专辑aid获取节目列表
     *
     * @param aid
     * @return
     */
    List<LatestEpisodeVo> getEpisodeByAid(long aid, PageParam page, CallerParam caller);


    /**
     * 根据轮次获取赛事赛程(TV)
     *
     * @param p
     * @param page
     * @param caller
     * @return
     */
    List<HallEpisodeVo4Tv> getEpisodesByCidAndRound4TV(GetEpisodesOfSeasonByMetaEntryIdParam p, PageParam page, CallerParam caller);


    /**
     * 获取上一场下一场
     *
     * @param p
     * @param callerParam
     * @return
     */
    Map<String, DetailEpisodeVo> getNearbyEpisodes(GetEpisodesNearbySomeEpisodeParam p, CallerParam callerParam);

    /**
     * 根据直播id获取节目信息
     *
     * @param liveId
     * @return
     */
    HallEpisodeVo getEpisodeByLiveId(String liveId, CallerParam callerParam);


    List<ZhangyuEpisodeVo> getZhangyuEpisodesByMids(GetZhangyuEpisodesParam p, PageParam page, CallerParam caller);


	List<HallEpisodeVo> createHallEpisodes(List<TComboEpisode> episodes, CallerParam caller);

	List<HallEpisodeVo> getZhangyuEpisodesByCids(GetCurrentEpisodesParam p, PageParam page, CallerParam caller);

    /**
     * 获取奥运赛程
     */
//    Map<String, Object>  getEpisodes4OlyMatchs(GetMatchsEpisodesParam p, PageParam pageParam, CallerParam callerParam);

    /**
     * 根据直播id获取节目详情
     *
     * @param liveId
     * @param callerParam
     * @return
     */
    DetailEpisodeVo getDetailEpisodeByLiveId(String liveId, CallerParam callerParam);

    RoundVo getRoundVo(long cid, long csid, CallerParam callerParam);

//	List<HallEpisodeVo> getLephoneDesktopEpisodes(String date, long gameType, CallerParam callerParam);

	List<HallEpisodeVo> getPeriodAppRecommendEpisodes(CallerParam callerParam);

	List<HallEpisodeVo> getTimelineEpisodes(LiveTypeParam liveTypeParam, CallerParam callerParam, int page, int count);

//	List<HallEpisodeVo> getTheRoadOfAdvance(long cid, long csid, CallerParam callerParam);

    /**
     * 获取会员视频
     *
     * @param p
     * @param page
     * @param caller
     * @return
     */
    List<HallEpisodeVo> getHallMemberEpisodes(GetCurrentEpisodesParam p, PageParam page, CallerParam caller);

    /**
     * 获取会员视频 for TV
     *
     * @param p
     * @param page
     * @param caller
     * @return
     */
    List<HallEpisodeVo4Tv> getHallMemberEpisodesForTv(GetCurrentEpisodesParam p, PageParam page, CallerParam caller);

    List<HallEpisodeVo4Tv> getHallEpisodes4TvWithTimezone(GetSomeDayEpisodesParam p, PageParam page, CallerParam caller);

    Map<String,List<HallEpisodeVo4Tv>> getCurrentEpisodes4Tv(GetCurrentEpisodesParam p, PageParam pageParam, CallerParam callerParam);

	List<HallEpisodeVo> getTicketEpisodes(GetCurrentEpisodesParam p, PageParam pageParam, CallerParam callerParam);

	List<HallEpisodeVo> getLivingEpisodes(GetCurrentEpisodesParam p, PageParam pageParam, CallerParam callerParam);

	List<HallEpisodeVo> getHomePageEpisodes(String cids, CallerParam callerParam);
}
