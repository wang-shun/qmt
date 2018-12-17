package com.lesports.qmt.sbd.param;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * Created by lufei1 on 2016/11/17.
 */
public class CompetitionSeasonParam {
    private Long id;
    //赛事id
    @NotNull(message = "cid is required")
    private Long cid;
    //赛季信息如:2014
    @NotBlank(message = "season is required")
    private String season;
    //是否跨赛季
    @NotNull(message = "overSeason is required")
    private Boolean overSeason;
    //赛季开始时间
    private String startTime;
    //赛季结束时间
    private String endTime;
    //当前轮次字典Id
    private Long currentRoundId;
    //总轮次
    private Integer totalRound;
    //球队Id
    private String tids;

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

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public Boolean getOverSeason() {
        return overSeason;
    }

    public void setOverSeason(Boolean overSeason) {
        this.overSeason = overSeason;
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

    public Long getCurrentRoundId() {
        return currentRoundId;
    }

    public void setCurrentRoundId(Long currentRoundId) {
        this.currentRoundId = currentRoundId;
    }

    public Integer getTotalRound() {
        return totalRound;
    }

    public void setTotalRound(Integer totalRound) {
        this.totalRound = totalRound;
    }

    public String getTids() {
        return tids;
    }

    public void setTids(String tids) {
        this.tids = tids;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("CompetitionSeasonParam{");
        sb.append("id=").append(id);
        sb.append(", cid=").append(cid);
        sb.append(", season='").append(season).append('\'');
        sb.append(", overSeason=").append(overSeason);
        sb.append(", startTime='").append(startTime).append('\'');
        sb.append(", endTime='").append(endTime).append('\'');
        sb.append(", currentRoundId=").append(currentRoundId);
        sb.append(", totalRound=").append(totalRound);
        sb.append(", tids='").append(tids).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
