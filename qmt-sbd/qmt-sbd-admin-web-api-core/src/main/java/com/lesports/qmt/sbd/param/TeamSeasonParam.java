package com.lesports.qmt.sbd.param;

import javax.validation.constraints.NotNull;

/**
 * Created by lufei1 on 2016/11/16.
 */
public class TeamSeasonParam {

    private Long id;
    //队伍id
    @NotNull(message = "tid is required")
    private Long tid;
    //球队教练id
    private Long coachId;
    //赛季id
    @NotNull(message = "csid is required")
    private Long csid;
    //队员信息
    private String players;
    //现任队长
    private Long currentCaptain;
    //核心球员
    private String corePlayers;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTid() {
        return tid;
    }

    public void setTid(Long tid) {
        this.tid = tid;
    }

    public Long getCoachId() {
        return coachId;
    }

    public void setCoachId(Long coachId) {
        this.coachId = coachId;
    }

    public Long getCsid() {
        return csid;
    }

    public void setCsid(Long csid) {
        this.csid = csid;
    }

    public String getPlayers() {
        return players;
    }

    public void setPlayers(String players) {
        this.players = players;
    }

    public Long getCurrentCaptain() {
        return currentCaptain;
    }

    public void setCurrentCaptain(Long currentCaptain) {
        this.currentCaptain = currentCaptain;
    }

    public String getCorePlayers() {
        return corePlayers;
    }

    public void setCorePlayers(String corePlayers) {
        this.corePlayers = corePlayers;
    }
}
