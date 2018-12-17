package com.lesports.qmt.web.api.core.service;

import com.lesports.api.common.CallerParam;
import com.lesports.qmt.sbd.api.dto.TCompetitorStat;
import com.lesports.qmt.sbd.api.dto.TSimplePlayer;
import com.lesports.qmt.sbd.api.service.GetMacthActionsParam;
import com.lesports.qmt.web.api.core.vo.BestPlayerStatsVo;
import com.lesports.qmt.web.api.core.vo.MatchAction;
import com.lesports.qmt.web.api.core.vo.MatchAgainst;
import com.lesports.qmt.web.api.core.vo.Squad;

import java.util.List;
import java.util.Map;

/**
 * Created by gengchengliang on 2015/6/16.
 */
public interface MatchService {

    /**
     * 按单个action类型获取技术统计
     * @param p
     * @param callerParam
     * @return
     */
    List getMatchActions(GetMacthActionsParam p,CallerParam callerParam);

    /**
     * 获取球员统计数据
     *
     * @param mid
     * @return
     */
    Map<Long, List<List<TSimplePlayer>>> getPlayerStatsOfMatch(long mid,CallerParam callerParam) ;


    /**
     * 获取最佳球员数据
     *
     * @param mid
     * @return
     */
    Map<Long, List<BestPlayerStatsVo>> getBestPlayerStatsOfMatch(long mid,CallerParam caller);

    /**
     * 按action类型分类获取技术统计
     * @param p
     * @param callerParam
     * @return
     */
    List getMatchActionsByTypes(GetMacthActionsParam p,CallerParam callerParam);


    /**
     * 按时间轴获取技术统计
     * @param p
     * @param callerParam
     * @return
     */
    List<MatchAction> getMatchActionsByTimeLine(GetMacthActionsParam p, CallerParam callerParam);

    List<TCompetitorStat> getNBAPlayerStatsOfMatch(long mid, CallerParam callerParam);

    List<TCompetitorStat> getCompetitorStatsRealtime(long mid, CallerParam caller);

    List<Squad> getSquadsRealtime(long matchId, CallerParam caller);

    List<Squad> getSquads(long matchId, CallerParam caller);

    MatchAgainst getMatchAgainst(long id,CallerParam caller);
}
