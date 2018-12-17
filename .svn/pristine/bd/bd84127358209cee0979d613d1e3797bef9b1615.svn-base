package com.lesports.qmt.sbd.service;

import com.lesports.api.common.CallerParam;
import com.lesports.qmt.sbd.api.dto.TTeamSeason;
import com.lesports.qmt.sbd.api.service.GetTeamSeasonsParam;
import com.lesports.qmt.sbd.model.TeamSeason;
import com.lesports.qmt.service.support.QmtService;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by lufei on 2016/10/24.
 */
public interface TeamSeasonService extends QmtService<TeamSeason, Long> {

    TTeamSeason getTTeamSeasonById(Long id, CallerParam caller);

    List<Long> getTeamSeasonIds(GetTeamSeasonsParam p, Pageable pageable, CallerParam caller);

    List<TTeamSeason> getTTeamSeasonsByIds(List<Long> ids, CallerParam caller);

    public void addTeamPlayer(Long tsid, Long pid, Long pnumber);
}
