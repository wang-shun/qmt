package com.lesports.qmt.sbd.param;

import com.lesports.qmt.sbd.api.common.CompetitorType;

import javax.validation.constraints.NotNull;

/**
 * Created by lufei1 on 2016/11/17.
 */
public class TopListItemParam {
    private Long topListId;
    @NotNull(message = "competitorType is required")
    private CompetitorType competitorType;
    @NotNull(message = "competitorId is required")
    private Long competitorId;
    private Long teamId;
    private Integer rank;
    //统计
    private String stats;

    public Long getTopListId() {
        return topListId;
    }

    public void setTopListId(Long topListId) {
        this.topListId = topListId;
    }

    public CompetitorType getCompetitorType() {
        return competitorType;
    }

    public void setCompetitorType(CompetitorType competitorType) {
        this.competitorType = competitorType;
    }

    public Long getCompetitorId() {
        return competitorId;
    }

    public void setCompetitorId(Long competitorId) {
        this.competitorId = competitorId;
    }

    public Long getTeamId() {
        return teamId;
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public String getStats() {
        return stats;
    }

    public void setStats(String stats) {
        this.stats = stats;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("TopListItemParam{");
        sb.append("topListId=").append(topListId);
        sb.append(", competitorType=").append(competitorType);
        sb.append(", competitorId=").append(competitorId);
        sb.append(", teamId=").append(teamId);
        sb.append(", rank=").append(rank);
        sb.append(", stats='").append(stats).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
