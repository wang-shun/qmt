package com.lesports.qmt.sbd.converter;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.lesports.api.common.CallerParam;
import com.lesports.qmt.sbd.model.Player;
import com.lesports.qmt.sbd.model.TeamSeason;
import com.lesports.qmt.sbd.service.CompetitionSeasonWebService;
import com.lesports.qmt.sbd.service.PlayerWebService;
import com.lesports.qmt.sbd.service.TeamWebService;
import com.lesports.qmt.sbd.vo.CompetitionSeasonVo;
import com.lesports.qmt.sbd.vo.TeamSeasonVo;
import com.lesports.qmt.sbd.vo.TeamVo;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by lufei1 on 2016/11/2.
 */
@Component("teamSeasonVoConverter")
public class TeamSeasonVoConverter implements VoConverter<TeamSeason, TeamSeasonVo> {

    private static final Logger LOG = LoggerFactory.getLogger(TeamSeasonVoConverter.class);

    @Resource
    private TeamWebService teamWebService;

    @Resource
    private CompetitionSeasonWebService competitionSeasonWebService;

    @Resource
    private PlayerWebService playerWebService;

    @Override
    public TeamSeasonVo toTVo(TeamSeason teamSeason, CallerParam caller) {
        Preconditions.checkArgument(teamSeason != null, "teamSeason is null");
        TeamSeasonVo teamSeasonVo = new TeamSeasonVo(teamSeason);
        TeamVo teamVo = teamWebService.findOne(teamSeason.getTid(), caller);
        if (teamVo != null) {
            teamSeasonVo.setTeamName(teamVo.getName());
        }

        CompetitionSeasonVo competitionSeasonVo = competitionSeasonWebService.findOne(teamSeasonVo.getCsid(), caller);
        if (competitionSeasonVo != null) {
            teamSeasonVo.setcName(competitionSeasonVo.getcName());
            teamSeasonVo.setSeason(competitionSeasonVo.getSeason());
            teamSeasonVo.setOverSeason(competitionSeasonVo.getOverSeason());
        }
        //教练
        Player coach = playerWebService.findOne(teamSeason.getCoachId(), caller);
        if (coach != null) {
            teamSeasonVo.setCoach(coach.getName());
        }
        //队长
        Player captain = playerWebService.findOne(teamSeason.getCurrentCaptain(), caller);
        if (captain != null) {
            teamSeasonVo.setCaptain(captain.getName());
        }
        //核心球员
        if (CollectionUtils.isNotEmpty(teamSeason.getCorePlayers())) {
            List<TeamSeasonVo.SimplePlayer> simplePlayers = Lists.newArrayList();
            for (long pid : teamSeason.getCorePlayers()) {
                Player player = playerWebService.findOne(pid, caller);
                if (player != null) {
                    TeamSeasonVo.SimplePlayer simplePlayer = new TeamSeasonVo.SimplePlayer();
                    simplePlayer.setPid(player.getId());
                    simplePlayer.setName(player.getName());
                    simplePlayers.add(simplePlayer);
                }
            }
            teamSeasonVo.setCorePlayerList(simplePlayers);
        }
        //球队球员
        List<TeamSeasonVo.SimplePlayer> playerList = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(teamSeason.getPlayers())) {
            for (TeamSeason.TeamPlayer teamPlayer : teamSeason.getPlayers()) {
                TeamSeasonVo.SimplePlayer simplePlayer = new TeamSeasonVo.SimplePlayer();
                Player player = playerWebService.findOne(teamPlayer.getPid(), caller);
                simplePlayer.setPid(teamPlayer.getPid());
                if (player != null) {
                    simplePlayer.setName(player.getName());
                }
                simplePlayer.setNumber(teamPlayer.getNumber());
                playerList.add(simplePlayer);
            }
        }
        teamSeasonVo.setPlayerList(playerList);
        return teamSeasonVo;
    }

}
