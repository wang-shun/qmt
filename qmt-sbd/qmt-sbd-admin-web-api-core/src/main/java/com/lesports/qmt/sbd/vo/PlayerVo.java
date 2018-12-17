package com.lesports.qmt.sbd.vo;

import com.lesports.qmt.mvc.QmtVo;
import com.lesports.qmt.sbd.model.Player;
import org.apache.commons.beanutils.LeBeanUtils;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by lufei1 on 2016/10/31.
 */
public class PlayerVo extends Player implements QmtVo {

    //大项名称
    private String gameFName;
    //国家
    private String country;
    //俱乐部球队
    private PlayingTeam clubPlayingTeam;

    private Boolean online;


    /**
     * 效力球队
     */
    public static class PlayingTeam {
        //球队id
        private Long teamId;
        //球队名称
        private String name;
        //号码
        private Integer number;

        public Long getTeamId() {
            return teamId;
        }

        public void setTeamId(Long teamId) {
            this.teamId = teamId;
        }

        public Integer getNumber() {
            return number;
        }

        public void setNumber(Integer number) {
            this.number = number;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            final StringBuffer sb = new StringBuffer("PlayingTeam{");
            sb.append("teamId=").append(teamId);
            sb.append(", name='").append(name).append('\'');
            sb.append(", number=").append(number);
            sb.append('}');
            return sb.toString();
        }
    }

    public PlayingTeam getClubPlayingTeam() {
        return clubPlayingTeam;
    }

    public void setClubPlayingTeam(PlayingTeam clubPlayingTeam) {
        this.clubPlayingTeam = clubPlayingTeam;
    }

    public String getGameFName() {
        return gameFName;
    }

    public void setGameFName(String gameFName) {
        this.gameFName = gameFName;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Boolean getOnline() {
        return online;
    }

    public void setOnline(Boolean online) {
        this.online = online;
    }

    public PlayerVo() {
    }

    public PlayerVo(Player player) {
        try {
            LeBeanUtils.copyProperties(this, player);
        } catch (IllegalAccessException | InvocationTargetException e) {
        }
    }

    public Player toModel() {
        //直接用类型转换得到的对象会报序列化错误
        Player player = new Player();
        try {
            LeBeanUtils.copyProperties(player, this);
        } catch (IllegalAccessException | InvocationTargetException e) {
        }
        return player;
    }


}
