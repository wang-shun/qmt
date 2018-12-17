package com.lesports.qmt.sbd.model;


import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.lesports.api.common.CountryCode;
import com.lesports.api.common.LangString;
import com.lesports.qmt.model.support.QmtModel;
import com.lesports.qmt.sbd.api.common.CompetitorType;
import com.lesports.qmt.sbd.api.common.GroundType;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 历史交锋数据
 * Created by lufei1 on 2016/2/23.
 */
@Document(collection = "match_reviews")
public class MatchReview extends QmtModel<Long> {

    private static final long serialVersionUID = -2712199661979762402L;


    private String name;
    //多语言比赛名
    @Field("multi_lang_names")
    private List<LangString> multiLangNames;
    //历史交锋数据
    private List<Long> confrontations = Lists.newArrayList();
    //最近及未来比赛信息
    @Field("match_infos")
    private Set<MatchInfo> matchInfos = Sets.newHashSet();
    //是否被删除
    private Boolean deleted;
    //统计
    private Map<String, Object> stats;
    //允许展示的国家
    @Field("allow_countries")
    private List<CountryCode> allowCountries;

    public static class MatchInfo implements Serializable {

        private static final long serialVersionUID = -2712199661979763502L;

        //队伍or队员id
        @Field("competitor_id")
        private Long competitorId;
        //主客场
        private GroundType ground;
        //是队伍还是队员
        private CompetitorType type;
        //最近比赛列表
        @Field("near_matches")
        private List<Long> nearMatches = Lists.newArrayList();
        //未来比赛列表
        @Field("after_matches")
        private List<Long> afterMatches = Lists.newArrayList();
        //最近比赛统计
        private Map<String, Object> stats;

        public Map<String, Object> getStats() {
            return stats;
        }

        public void setStats(Map<String, Object> stats) {
            this.stats = stats;
        }

        public Long getCompetitorId() {
            return competitorId;
        }

        public void setCompetitorId(Long competitorId) {
            this.competitorId = competitorId;
        }

        public GroundType getGround() {
            return ground;
        }

        public void setGround(GroundType ground) {
            this.ground = ground;
        }

        public CompetitorType getType() {
            return type;
        }

        public void setType(CompetitorType type) {
            this.type = type;
        }


        public List<Long> getNearMatches() {
            return nearMatches;
        }

        public void setNearMatches(List<Long> nearMatches) {
            this.nearMatches = nearMatches;
        }

        public List<Long> getAfterMatches() {
            return afterMatches;
        }

        public void setAfterMatches(List<Long> afterMatches) {
            this.afterMatches = afterMatches;
        }

        @Override
        public String toString() {
            final StringBuffer sb = new StringBuffer("MatchInfo{");
            sb.append("competitorId=").append(competitorId);
            sb.append(", ground=").append(ground);
            sb.append(", type=").append(type);
            sb.append(", nearMatches=").append(nearMatches);
            sb.append(", afterMatches=").append(afterMatches);
            sb.append(", stats=").append(stats);
            sb.append('}');
            return sb.toString();
        }
    }




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

    public List<Long> getConfrontations() {
        return confrontations;
    }

    public void setConfrontations(List<Long> confrontations) {
        this.confrontations = confrontations;
    }

    public Set<MatchInfo> getMatchInfos() {
        return matchInfos;
    }

    public void setMatchInfos(Set<MatchInfo> matchInfos) {
        this.matchInfos = matchInfos;
    }

    public Map<String, Object> getStats() {
        return stats;
    }

    public void setStats(Map<String, Object> stats) {
        this.stats = stats;
    }

    public List<LangString> getMultiLangNames() {
        return multiLangNames;
    }

    public void setMultiLangNames(List<LangString> multiLangNames) {
        this.multiLangNames = multiLangNames;
    }

    public List<CountryCode> getAllowCountries() {
        return allowCountries;
    }

    public void setAllowCountries(List<CountryCode> allowCountries) {
        this.allowCountries = allowCountries;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("MatchReview{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", multiLangNames=").append(multiLangNames);
        sb.append(", confrontations=").append(confrontations);
        sb.append(", matchInfos=").append(matchInfos);
        sb.append(", deleted=").append(deleted);
        sb.append(", createAt='").append(createAt).append('\'');
        sb.append(", updateAt='").append(updateAt).append('\'');
        sb.append(", stats=").append(stats);
        sb.append(", allowCountries=").append(allowCountries);
        sb.append('}');
        return sb.toString();
    }
}
