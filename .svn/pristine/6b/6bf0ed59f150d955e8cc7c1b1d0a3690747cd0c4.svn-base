package com.lesports.qmt.sbd.param;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * Created by lufei1 on 2016/11/17.
 */
public class MatchParam {

    private Long id;
    //赛季id
    @NotNull(message = "csid is required")
    private Long csid;
    //赛事id
    @NotNull(message = "cid is required")
    private Long cid;
    //大项
    private Long gameFType;
    //分组，A组、B组等(字典表)
    private Long group;
    //比赛阶段（小组赛、淘汰赛、常规赛、季后赛）(字典表)
    private Long stage;
    //比赛状态
    @NotNull(message = "status is required")
    private Integer status;
    //比赛开始时间
    @NotBlank(message = "startTime is required")
    private String startTime;
    //比赛结束时间
    private String endTime;
    //比赛城市
    private String city;
    //比赛场馆
    private String venue;
    //比赛名称
    @NotBlank(message = "name is required")
    private String name;
    //赛程加锁（false: 未锁,true: 已锁）
    private Boolean lock;
    //轮次
    private Long round;
    //分站
    private Long substation;
    //参赛者信息
    private String competitors;
    //对阵信息
    private Boolean vs;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCsid() {
        return csid;
    }

    public void setCsid(Long csid) {
        this.csid = csid;
    }

    public Long getCid() {
        return cid;
    }

    public void setCid(Long cid) {
        this.cid = cid;
    }

    public Long getGameFType() {
        return gameFType;
    }

    public void setGameFType(Long gameFType) {
        this.gameFType = gameFType;
    }

    public Long getGroup() {
        return group;
    }

    public void setGroup(Long group) {
        this.group = group;
    }

    public Long getStage() {
        return stage;
    }

    public void setStage(Long stage) {
        this.stage = stage;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getLock() {
        return lock;
    }

    public void setLock(Boolean lock) {
        this.lock = lock;
    }

    public Long getRound() {
        return round;
    }

    public void setRound(Long round) {
        this.round = round;
    }

    public Long getSubstation() {
        return substation;
    }

    public void setSubstation(Long substation) {
        this.substation = substation;
    }

    public String getCompetitors() {
        return competitors;
    }

    public void setCompetitors(String competitors) {
        this.competitors = competitors;
    }

    public Boolean getVs() {
        return vs;
    }

    public void setVs(Boolean vs) {
        this.vs = vs;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("MatchParam{");
        sb.append("id=").append(id);
        sb.append(", csid=").append(csid);
        sb.append(", cid=").append(cid);
        sb.append(", gameFType=").append(gameFType);
        sb.append(", group=").append(group);
        sb.append(", stage=").append(stage);
        sb.append(", status=").append(status);
        sb.append(", startTime='").append(startTime).append('\'');
        sb.append(", endTime='").append(endTime).append('\'');
        sb.append(", city='").append(city).append('\'');
        sb.append(", venue='").append(venue).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", lock=").append(lock);
        sb.append(", round=").append(round);
        sb.append(", substation=").append(substation);
        sb.append(", competitors='").append(competitors).append('\'');
        sb.append(", vs=").append(vs);
        sb.append('}');
        return sb.toString();
    }
}
