package com.lesports.qmt.web.api.core.creater;

import com.lesports.api.common.CallerParam;
import com.lesports.qmt.sbd.api.dto.PlayingTeam;
import com.lesports.qmt.sbd.api.dto.TPlayer;
import com.lesports.qmt.sbd.api.dto.TPlayerCareerStat;
import com.lesports.qmt.sbd.api.service.GetPlayerCareerStatParam;
import com.lesports.qmt.sbd.client.SbdPlayerCarerStatApis;
import com.lesports.qmt.web.api.core.vo.PlayerVo;
import com.lesports.sms.api.common.TeamType;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by ruiyuansheng on 2016/11/25.
 */
@Component("playerVoCreater")
public class PlayerVoCreater {

    public PlayerVo createrPlayerVo(TPlayer tPlayer,CallerParam caller) {
        PlayerVo playerVo = new PlayerVo();
        if(null == tPlayer){
            return playerVo;
        }
        playerVo.setId(tPlayer.getId());
        playerVo.setName(tPlayer.getName());
        playerVo.setLogo(tPlayer.getImageUrl());
        if(tPlayer.getPositionId() == 100125000L || 104676000L == tPlayer.getPositionId()){
            playerVo.setIsGoalKeeper(true);
        }
        playerVo.setPosition(tPlayer.getPosition());
        playerVo.setBirth(tPlayer.getBirthday());
        playerVo.setHeight(tPlayer.getHeight());
        playerVo.setNationality(tPlayer.getNationality());
        playerVo.setNumber(tPlayer.getNumber());
        GetPlayerCareerStatParam p = new GetPlayerCareerStatParam();
        p.setPlayerId(tPlayer.getId());
        p.setScopeType(null);
        List<TPlayerCareerStat> tPlayerCareerStats = SbdPlayerCarerStatApis.getTPlayerCareerStats(p, caller);
        for (TPlayerCareerStat tPlayerCareerStat : tPlayerCareerStats) {
            if (tPlayerCareerStat.getTeamType() == TeamType.CLUB_TEAM.getValue()) {
                PlayerVo.Team club = new PlayerVo.Team();
                club.setTid(tPlayerCareerStat.getTeamId());
                club.setName(tPlayerCareerStat.getTeamName());
                club.setStats(tPlayerCareerStat.getStats());
                club.setLogo(tPlayerCareerStat.getTeamLogo());
                PlayingTeam clubTeam = tPlayer.getClubTeam();
                if(null != clubTeam) {
                    club.setNumber(clubTeam.getNumber());
                }
                playerVo.setClub(club);
                continue;
            }
            if (tPlayerCareerStat.getTeamType() == TeamType.NATIONAL_TEAM.getValue()) {
                PlayerVo.Team country = new PlayerVo.Team();
                country.setTid(tPlayerCareerStat.getTeamId());
                country.setName(tPlayerCareerStat.getTeamName());
                country.setStats(tPlayerCareerStat.getStats());
                country.setLogo(tPlayerCareerStat.getTeamLogo());
                PlayingTeam nationalTeam = tPlayer.getClubTeam();
                if(null != nationalTeam) {
                    country.setNumber(nationalTeam.getNumber());
                }
                playerVo.setCountry(country);
            }
        }

        return playerVo;
    }


}
