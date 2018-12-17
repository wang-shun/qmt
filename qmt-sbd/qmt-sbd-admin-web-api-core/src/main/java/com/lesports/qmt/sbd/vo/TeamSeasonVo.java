package com.lesports.qmt.sbd.vo;

import com.lesports.qmt.mvc.QmtVo;
import com.lesports.qmt.sbd.model.TeamSeason;
import org.apache.commons.beanutils.LeBeanUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * Created by lufei1 on 2016/11/2.
 */
public class TeamSeasonVo extends TeamSeason implements QmtVo {

    //赛事名称
    private String cName;
    //赛季
    private String season;
    //赛季
    private Boolean overSeason;
    //主教练
    private String coach;
    //队长
    private String captain;
    //球员信息
    private List<SimplePlayer> playerList;
    //核心球员
    private List<SimplePlayer> corePlayerList;
    //队伍名称
    private String teamName;


    public static class SimplePlayer {
        //球员id
        private Long pid;
        //球员名称
        private String name;
        //号码
        private String number;

        public Long getPid() {
            return pid;
        }

        public void setPid(Long pid) {
            this.pid = pid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }
    }

    public List<SimplePlayer> getPlayerList() {
        return playerList;
    }

    public void setPlayerList(List<SimplePlayer> playerList) {
        this.playerList = playerList;
    }

    public List<SimplePlayer> getCorePlayerList() {
        return corePlayerList;
    }

    public void setCorePlayerList(List<SimplePlayer> corePlayerList) {
        this.corePlayerList = corePlayerList;
    }

    public String getcName() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName = cName;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public String getCoach() {
        return coach;
    }

    public void setCoach(String coach) {
        this.coach = coach;
    }

    public String getCaptain() {
        return captain;
    }

    public void setCaptain(String captain) {
        this.captain = captain;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public Boolean getOverSeason() {
        return overSeason;
    }

    public void setOverSeason(Boolean overSeason) {
        this.overSeason = overSeason;
    }


    public TeamSeasonVo() {
    }


    public TeamSeasonVo(TeamSeason teamSeason) {
        try {
            LeBeanUtils.copyProperties(this, teamSeason);
        } catch (IllegalAccessException | InvocationTargetException e) {
        }
    }

    public TeamSeason toModel() {
        //直接用类型转换得到的对象会报序列化错误
        TeamSeason teamSeason = new TeamSeason();
        try {
            LeBeanUtils.copyProperties(teamSeason, this);
        } catch (IllegalAccessException | InvocationTargetException e) {
        }
        return teamSeason;
    }
}
