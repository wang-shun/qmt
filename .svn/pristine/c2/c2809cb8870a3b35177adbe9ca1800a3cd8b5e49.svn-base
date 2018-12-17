package com.lesports.qmt.web.api.core.service;

import com.lesports.api.common.CallerParam;
import com.lesports.qmt.web.api.core.vo.TeamMixedVo;
import com.lesports.qmt.sbd.api.dto.TPlayer;
import com.lesports.qmt.web.api.core.vo.TeamVo;

import java.util.List;

/**
 * Created by gengchengliang on 2016/8/17.
 */
public interface TeamService {

	TeamMixedVo getTeamMixed(long teamId, CallerParam caller);

    /**
     * 获取球队当前所有球员
     * @param tid
     * @param cid
     * @param callerParam
     * @return
     */
    List<TPlayer> getPlayersByTidAndCid(Long tid, Long cid, CallerParam callerParam);


    TeamVo getTeamById(Long id,CallerParam caller);
}
