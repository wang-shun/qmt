package com.lesports.qmt.web.api.core.service;

import com.lesports.api.common.CallerParam;
import com.lesports.qmt.web.api.core.vo.PlayerMixedVo;
import com.lesports.qmt.sbd.api.dto.TPlayer;
import com.lesports.qmt.web.api.core.vo.PlayerVo;

import java.util.List;
import java.util.Map;

/**
 * Created by lufei1 on 2016/8/19.
 */
public interface PlayerService {
    PlayerMixedVo getPlayerMixed(long playerId, CallerParam caller);

    PlayerMixedVo getPlayerMixed(long playerId, long cid, long csid, CallerParam caller);

    List<Map<String,String>> getPlayerStats(long playerId, long cid, long csid, CallerParam caller);




    /**
     * 获取当前赛事的热门球员
     * @param callerParam
     * @return
     */
    List<TPlayer> getHotPlayersByCid(Long cid, CallerParam callerParam);

    /**
     * 获取当前pk的球员赛季技术统计
     * @param pid
     * @param pkId
     * @param cid
     * @param callerParam
     * @return
     */
    Map<String,Object> getPKPlayersStats(Long pid, Long pkId, Long cid, CallerParam callerParam);

    PlayerVo getPlayerById(Long id,CallerParam callerParam);

    Map<String,Object> getPlayerTeamMates(Long id,CallerParam callerParam);
}
