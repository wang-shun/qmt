package com.lesports.qmt.sbd.service;

import com.lesports.api.common.CallerParam;
import com.lesports.qmt.sbd.api.dto.TTeam;
import com.lesports.qmt.sbd.api.service.GetTeams4SimpleSearchParam;
import com.lesports.qmt.sbd.api.service.GetTeamsOfCompetitionParam;
import com.lesports.qmt.sbd.api.service.GetTeamsOfSeasonParam;
import com.lesports.qmt.sbd.model.Team;
import com.lesports.qmt.service.support.QmtService;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by lufei on 2016/10/24.
 */
public interface TeamService extends QmtService<Team, Long> {

    TTeam getTTeamById(long id, CallerParam caller);

    List<TTeam> getTTeamsByIds(List<Long> ids, CallerParam caller);

    List<Long> getTeamIds4SimpleSearch(GetTeams4SimpleSearchParam p, Pageable pageable, CallerParam caller);

    List<Long> getTeamIdsOfSeason(GetTeamsOfSeasonParam p, Pageable pageable, CallerParam caller);

    List<Long> getTeamIdsOfCompetition(GetTeamsOfCompetitionParam p, Pageable pageable, CallerParam caller);

    TTeam getTTeamByCampId(long id, CallerParam caller);

}
