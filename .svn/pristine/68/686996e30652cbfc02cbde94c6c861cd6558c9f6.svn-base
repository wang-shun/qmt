package com.lesports.qmt.sbd.model;

import com.lesports.api.common.CountryCode;
import com.lesports.qmt.model.support.QmtModel;
import com.lesports.qmt.sbd.api.common.CompetitorType;
import com.lesports.qmt.sbd.api.common.TeamType;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;
import java.util.Map;

/**
 * 参赛者赛季统计,
 * User: ellios
 * Time: 15-6-6 : 上午9:56
 */
@Document(collection = "competitor_season_stats")
public class CompetitorSeasonStat extends QmtModel<Long> {
    private static final long serialVersionUID = -2000729691001075518L;

    @Field("competitor_id")
    //队伍或者队员的id
    private Long competitorId;
    //是队伍还是队员
    private CompetitorType type;
    //是俱乐部还是国家队
    @Field("team_type")
    private TeamType teamType;
    //赛季id
    private Long csid;
    //赛事id
    private Long cid;
    //阶段
    private Long stage;
    //本赛季统计汇总
    private Map<String, Object> stats;
    //本赛季场均技术统计（适用于篮球)
    @Field("avg_stats")
    private Map<String, Object> avgStats;
    //赛季最高技术统计(适用于篮球)
    @Field("top_stats")
    private Map<String, Object> topStats;
    //允许展示的国家
    @Field("allow_countries")
    private List<CountryCode> allowCountries;
    //所在球队id
    private Long tid;

    public List<CountryCode> getAllowCountries() {
        return allowCountries;
    }

    public void setAllowCountries(List<CountryCode> allowCountries) {
        this.allowCountries = allowCountries;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCompetitorId() {
        return competitorId;
    }

    public void setCompetitorId(Long competitorId) {
        this.competitorId = competitorId;
    }

    public CompetitorType getType() {
        return type;
    }

    public void setType(CompetitorType type) {
        this.type = type;
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

    public Long getStage() {
        return stage;
    }

    public void setStage(Long stage) {
        this.stage = stage;
    }

    public Map<String, Object> getStats() {
        return stats;
    }

    public void setStats(Map<String, Object> stats) {
        this.stats = stats;
    }

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }

    public String getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(String updateAt) {
        this.updateAt = updateAt;
    }

    public Long getTid() {
        return tid;
    }

    public void setTid(Long tid) {
        this.tid = tid;
    }

    public TeamType getTeamType() {
        return teamType;
    }

    public void setTeamType(TeamType teamType) {
        this.teamType = teamType;
    }

    public Map<String, Object> getAvgStats() {
        return avgStats;
    }

    public void setAvgStats(Map<String, Object> avgStats) {
        this.avgStats = avgStats;
    }

    public Map<String, Object> getTopStats() {
        return topStats;
    }

    public void setTopStats(Map<String, Object> topStats) {
        this.topStats = topStats;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("CompetitorSeasonStat{");
        sb.append("id=").append(id);
        sb.append(", competitorId=").append(competitorId);
        sb.append(", type=").append(type);
        sb.append(", teamType=").append(teamType);
        sb.append(", csid=").append(csid);
        sb.append(", cid=").append(cid);
        sb.append(", stage=").append(stage);
        sb.append(", stats=").append(stats);
        sb.append(", avgStats=").append(avgStats);
        sb.append(", topStats=").append(topStats);
        sb.append(", createAt='").append(createAt).append('\'');
        sb.append(", updateAt='").append(updateAt).append('\'');
        sb.append(", allowCountries=").append(allowCountries);
        sb.append(", tid=").append(tid);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CompetitorSeasonStat)) return false;

        CompetitorSeasonStat that = (CompetitorSeasonStat) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
