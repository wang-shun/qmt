package com.lesports.qmt.web.api.core.vo;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by ruiyuansheng on 2016/11/23.
 */
public class CompetitionSeasonStatVo implements Serializable {

    private static final long serialVersionUID = 6519462837978706719L;
    private Long id;
    //赛事id
    private Long cid;
    //赛季id
    private Long csid;
    //球员id
    private Long pid;
    //球员名称
    private String playerName;
    //位置
    private String position;
    //赛季统计
    private Map<String,String> stats;
    //logo
    private String logo;
    //是否守门员
    private Boolean isGoalKeeper = false;
   //国籍
    private String nationality;
    //号码
    private Integer number;

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCid() {
        return cid;
    }

    public void setCid(Long cid) {
        this.cid = cid;
    }

    public Long getCsid() {
        return csid;
    }

    public void setCsid(Long csid) {
        this.csid = csid;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Map<String, String> getStats() {
        return stats;
    }

    public void setStats(Map<String, String> stats) {
        this.stats = stats;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public Boolean getIsGoalKeeper() {
        return isGoalKeeper;
    }

    public void setIsGoalKeeper(Boolean isGoalKeeper) {
        this.isGoalKeeper = isGoalKeeper;
    }

    @Override
    public String toString() {
        return "CompetitionSeasonStatVo{" +
                "id=" + id +
                ", cid=" + cid +
                ", csid=" + csid +
                ", pid=" + pid +
                ", playerName='" + playerName + '\'' +
                ", position='" + position + '\'' +
                ", stats=" + stats +
                ", logo='" + logo + '\'' +
                ", isGoalKeeper=" + isGoalKeeper +
                ", nationality='" + nationality + '\'' +
                ", number=" + number +
                '}';
    }
}
