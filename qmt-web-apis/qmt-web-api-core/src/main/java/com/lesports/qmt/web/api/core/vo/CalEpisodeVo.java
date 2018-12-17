package com.lesports.qmt.web.api.core.vo;

import java.io.Serializable;

/**
 * 直播日历的节目vo
 * Created by gengchengliang on 2015/6/22.
 */
public class CalEpisodeVo extends HallEpisodeVo implements Serializable {

    //节目id
    private Long id;
    //赛程id
    private Long mid;
    //赛程名称
    private String name;
    //赛季id
    private Long cid;
    //赛季名称
    private String cname;
    //赛季
    private String season;
    //所属时间段
    private Integer timeSection;
    //比赛开始时间
    private String startTime;
    //开始时间的时间戳
    private Long startTimeStamp;
    //比赛状态 0:未开始, 1:比赛中, 2:比赛结束
    private Integer matchStatus;
    //直播状态 0:未开始, 1:比赛中, 2:比赛结束
    private Integer status;
    //是否对阵 0:非对阵 1:对阵
    private Integer vs;
    //赛事logo
    private String logo;
    //轮次
    private String round;
    //比赛阶段
    private String stage;
    //分组
    private String group;
    //比赛大项
    private Long gameFType;
    //比赛大项名称
    private String gameFName;
    //比赛小项
    private Long gameSType;
    //比赛小项名称
    private String gameSName;
    //播放链接
    private String playLink;
    //图文直播链接
    private String tLiveLink;
    //类型
    private Integer type;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getCid() {
        return cid;
    }

    public void setCid(Long cid) {
        this.cid = cid;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public Integer getMatchStatus() {
        return matchStatus;
    }

    public void setMatchStatus(Integer matchStatus) {
        this.matchStatus = matchStatus;
    }

    public Integer getTimeSection() {
        return timeSection;
    }

    public void setTimeSection(Integer timeSection) {
        this.timeSection = timeSection;
    }

    public Integer getVs() {
        return vs;
    }

    public void setVs(Integer vs) {
        this.vs = vs;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public Long getMid() {
        return mid;
    }

    public void setMid(Long mid) {
        this.mid = mid;
    }

    public Long getStartTimeStamp() {
        return startTimeStamp;
    }

    public void setStartTimeStamp(Long startTimeStamp) {
        this.startTimeStamp = startTimeStamp;
    }

    public String getRound() {
        return round;
    }

    public void setRound(String round) {
        this.round = round;
    }

    public String getStage() {
        return stage;
    }

    public void setStage(String stage) {
        this.stage = stage;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public Long getGameFType() {
        return gameFType;
    }

    public void setGameFType(Long gameFType) {
        this.gameFType = gameFType;
    }

    public String getGameFName() {
        return gameFName;
    }

    public void setGameFName(String gameFName) {
        this.gameFName = gameFName;
    }

    public Long getGameSType() {
        return gameSType;
    }

    public void setGameSType(Long gameSType) {
        this.gameSType = gameSType;
    }

    public String getGameSName() {
        return gameSName;
    }

    public void setGameSName(String gameSName) {
        this.gameSName = gameSName;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getPlayLink() {
        return playLink;
    }

    public void setPlayLink(String playLink) {
        this.playLink = playLink;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String gettLiveLink() {
        return tLiveLink;
    }

    public void settLiveLink(String tLiveLink) {
        this.tLiveLink = tLiveLink;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("CalEpisodeVo{");
        sb.append("id=").append(id);
        sb.append(", mid=").append(mid);
        sb.append(", name='").append(name).append('\'');
        sb.append(", cid=").append(cid);
        sb.append(", cname='").append(cname).append('\'');
        sb.append(", season='").append(season).append('\'');
        sb.append(", timeSection=").append(timeSection);
        sb.append(", startTime='").append(startTime).append('\'');
        sb.append(", startTimeStamp=").append(startTimeStamp);
        sb.append(", matchStatus=").append(matchStatus);
        sb.append(", status=").append(status);
        sb.append(", vs=").append(vs);
        sb.append(", logo='").append(logo).append('\'');
        sb.append(", round='").append(round).append('\'');
        sb.append(", stage='").append(stage).append('\'');
        sb.append(", group='").append(group).append('\'');
        sb.append(", gameFType=").append(gameFType);
        sb.append(", gameFName='").append(gameFName).append('\'');
        sb.append(", gameSType=").append(gameSType);
        sb.append(", gameSName='").append(gameSName).append('\'');
        sb.append(", playLink='").append(playLink).append('\'');
        sb.append(", tLiveLink='").append(tLiveLink).append('\'');
        sb.append(", type=").append(type);
        sb.append('}');
        return sb.toString();
    }
}
