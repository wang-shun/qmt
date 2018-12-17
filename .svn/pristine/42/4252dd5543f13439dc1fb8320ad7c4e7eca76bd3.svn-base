package com.lesports.qmt.sbd.model;

import com.lesports.api.common.CountryCode;
import com.lesports.qmt.model.support.QmtModel;
import com.lesports.qmt.sbd.api.common.CareerScopeType;
import com.lesports.qmt.sbd.api.common.CareerStatType;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;
import java.util.Map;

/**
 * 球员生涯统计
 * Created by lufei1 on 2016/8/15.
 */
@Document(collection = "player_career_stats")
public class PlayerCareerStat extends QmtModel<Long> {

    private static final long serialVersionUID = -7829883608966320801L;
    //球员id
    @Field("player_id")
    private Long playerId;
    //生涯统计范围类型，可以是俱乐部，可以是国家队，也可以是赛事，是某一球队
    @Field("scope_type")
    private CareerScopeType scopeType;
    //生涯统计范围的具体Id
    @Field("scope_id")
    private Long scopeId;
    //技术统计的类型，可以是总计，也可以是生涯最佳
    @Field("stat_type")
    private CareerStatType statType;
    @Field("stats")
    private Map<String, Object> stats;
    //是否被删除
    private Boolean deleted;
    //允许展示的国家
    @Field("allow_countries")
    private List<CountryCode> allowCountries;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Long playerId) {
        this.playerId = playerId;
    }

    public CareerStatType getStatType() {
        return statType;
    }

    public void setStatType(CareerStatType statType) {
        this.statType = statType;
    }

    public Map<String, Object> getStats() {
        return stats;
    }

    public void setStats(Map<String, Object> stats) {
        this.stats = stats;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
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

    public List<CountryCode> getAllowCountries() {
        return allowCountries;
    }

    public void setAllowCountries(List<CountryCode> allowCountries) {
        this.allowCountries = allowCountries;
    }

    public CareerScopeType getScopeType() {
        return scopeType;
    }

    public void setScopeType(CareerScopeType scopeType) {
        this.scopeType = scopeType;
    }

    public Long getScopeId() {
        return scopeId;
    }

    public void setScopeId(Long scopeId) {
        this.scopeId = scopeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PlayerCareerStat that = (PlayerCareerStat) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("PlayerCareerStat{");
        sb.append("playerId=").append(playerId);
        sb.append(", scopeType=").append(scopeType);
        sb.append(", scopeId=").append(scopeId);
        sb.append(", statType=").append(statType);
        sb.append(", stats=").append(stats);
        sb.append(", deleted=").append(deleted);
        sb.append(", allowCountries=").append(allowCountries);
        sb.append('}');
        return sb.toString();
    }
}
