package com.lesports.qmt.sbd.service;

import com.lesports.api.common.CallerParam;
import com.lesports.qmt.sbd.service.support.SbdWebService;
import com.lesports.qmt.sbd.vo.TeamSeasonVo;

/**
 * Created by lufei1 on 2016/10/25.
 */
public interface TeamSeasonWebService extends SbdWebService<TeamSeasonVo, Long> {


    TeamSeasonVo.TeamPlayer getTeamPlayerByTsidAndPid(long tsid, long pid, CallerParam caller);

    /**
     * 根据teamIds更新teamSeason信息
     *
     * @param csid
     * @param tids
     * @param caller
     * @return
     */
    boolean updateTeamSeason(long csid, String tids, CallerParam caller);

    void addTeamPlayer(long tsid, long pid, String pnumber);

    void deleteTeamPlayer(long tsid, long pid);

}
