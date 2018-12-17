package com.lesports.qmt.sbd.model;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.lesports.api.common.CountryCode;
import com.lesports.qmt.model.support.QmtModel;
import com.lesports.qmt.sbd.api.dto.CoachType;
import com.lesports.qmt.sbd.api.common.CompetitorType;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by yangyu on 2015/5/30.
 */
@Document(collection = "match_stats")
public class MatchStats extends QmtModel<Long> {
    private static final long serialVersionUID = -3003677463822831255L;

    //参赛者统计信息
    @Field("competitor_stats")
    private Set<CompetitorStat> competitorStats = Sets.newHashSet();
    //球队阵容及球员统计
    private List<Squad> squads = Lists.newArrayList();
    //最佳球员统计
    @Field("best_player_stats")
    private Set<BestPlayerStat> bestPlayerStats = Sets.newHashSet();
    //是否被删除
    private Boolean deleted;
    //允许展示的国家
    @Field("allow_countries")
    private List<CountryCode> allowCountries;

    public Set<CompetitorStat> getCompetitorStats() {
        return competitorStats;
    }

    public void setCompetitorStats(Set<CompetitorStat> competitorStats) {
        this.competitorStats = competitorStats;
    }

    public List<Squad> getSquads() {
        return squads;
    }

    public void setSquads(List<Squad> squads) {
        this.squads = squads;
    }

    public Set<BestPlayerStat> getBestPlayerStats() {
        return bestPlayerStats;
    }

    public void setBestPlayerStats(Set<BestPlayerStat> bestPlayerStats) {
        this.bestPlayerStats = bestPlayerStats;
    }

    /**
     * 出场阵容
     */
    public static class Squad implements Serializable {
        private static final long serialVersionUID = -6048819333470825837L;

        //队伍id
        private Long tid;
        //教练名单
        private List<SimpleCoach> coaches;
        //人员名单
        private List<SimplePlayer> players;
        //阵型
        private String formation;

        public List<SimpleCoach> getCoaches() {
            return coaches;
        }

        public void setCoaches(List<SimpleCoach> coaches) {
            this.coaches = coaches;
        }

        public Long getTid() {
            return tid;
        }

        public void setTid(Long tid) {
            this.tid = tid;
        }


        public List<SimplePlayer> getPlayers() {
            return players;
        }

        public void setPlayers(List<SimplePlayer> players) {
            this.players = players;
        }

        public String getFormation() {
            return formation;
        }

        public void setFormation(String formation) {
            this.formation = formation;
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("Squad{");
            sb.append("tid=").append(tid);
            sb.append(", players=").append(players);
            sb.append(", formation=").append(formation);
            sb.append('}');
            return sb.toString();
        }
    }

    public static class CompetitorStat implements Serializable {
        private static final long serialVersionUID = -3048819333470825837L;
        //队伍or队员id
        @Field("competitor_id")
        private Long competitorId;
        @Field("competitor_type")
        private CompetitorType competitorType;
        //球队id，如果参赛者是球员时候需要所属球队
        @Field("team_id")
        private Long teamId;
        //参赛者本场的统计信息
        private Map<String, Object> stats;
        //展示顺序,方便运营调整顺序
        private Integer showOrder;
        //分节统计数据
        @Field("section_stats")
        private List<SectionStat> sectionStats;

        public Integer getShowOrder() {
            return showOrder;
        }

        public void setShowOrder(Integer showOrder) {
            this.showOrder = showOrder;
        }

        public Long getCompetitorId() {
            return competitorId;
        }

        public void setCompetitorId(Long competitorId) {
            this.competitorId = competitorId;
        }

        public Map<String, Object> getStats() {
            return stats;
        }

        public void setStats(Map<String, Object> stats) {
            this.stats = stats;
        }


        public CompetitorType getCompetitorType() {
            return competitorType;
        }

        public void setCompetitorType(CompetitorType competitorType) {
            this.competitorType = competitorType;
        }

        public Long getTeamId() {
            return teamId;
        }

        public void setTeamId(Long teamId) {
            this.teamId = teamId;
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("CompetitorStat{");
            sb.append("competitorId=").append(competitorId);
            sb.append("competitorType=").append(competitorType);
            sb.append("teamId=").append(teamId);
            sb.append(", stats=").append(stats);
            sb.append(", sectionStats=").append(sectionStats);
            sb.append('}');
            return sb.toString();
        }

        @Override
        public int hashCode() {
            return competitorId != null ? competitorId.hashCode() : 0;
        }
    }


    //出场教练基本信息
    public static class SimpleCoach implements Serializable {
        private static final long serialVersionUID = -5048819333470825837L;

        //id
        private Long coachId;
        //教练类型
        private CoachType type;
        //扩展信息
        @Field("extend_infos")
        private Map<String, Object> extendInfos;

        public Map<String, Object> getExtendInfos() {
            return extendInfos;
        }

        public Long getCoachId() {
            return coachId;
        }

        public void setCoachId(Long coachId) {
            this.coachId = coachId;
        }

        public CoachType getType() {
            return type;
        }

        public void setType(CoachType type) {
            this.type = type;
        }

        public void setExtendInfos(Map<String, Object> extendInfos) {
            this.extendInfos = extendInfos;
        }

        @Override
        public String toString() {
            return "SimpleCoach{" +
                    "coachId=" + coachId +
                    ", type=" + type +
                    ", extendInfos=" + extendInfos +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof SimpleCoach)) return false;

            SimpleCoach that = (SimpleCoach) o;

            if (coachId != null ? !coachId.equals(that.coachId) : that.coachId != null) return false;

            return true;
        }

        @Override
        public int hashCode() {
            return coachId != null ? coachId.hashCode() : 0;
        }
    }

    /**
     * 基本球员信息
     */
    public static class SimplePlayer implements Serializable {
        private static final long serialVersionUID = -4048819333470825837L;

        //id
        private Long pid;
        //是否首发
        private Boolean starting;
        //数据字典,位置
        private Long position;
        //球员号码
        private Integer number;
        //是否未上场
        private Integer dnp;
        //是否在场
        private String isOnCourt;
        //首发的场上位置编号
        private String squadOrder;
        //场上基础扩展信息
        @Field("extend_infos")
        private Map<String, Object> extendInfos;
        //球员统计数据
        private Map<String, Object> stats;


        public Map<String, Object> getExtendInfos() {
            return extendInfos;
        }

        public void setExtendInfos(Map<String, Object> extendInfos) {
            this.extendInfos = extendInfos;
        }

        public Long getPid() {
            return pid;
        }

        public void setPid(Long pid) {
            this.pid = pid;
        }

        public Boolean getStarting() {
            return starting;
        }

        public void setStarting(Boolean starting) {
            this.starting = starting;
        }

        public Long getPosition() {
            return position;
        }

        public void setPosition(Long position) {
            this.position = position;
        }

        public Map<String, Object> getStats() {
            return stats;
        }

        public void setStats(Map<String, Object> stats) {
            this.stats = stats;
        }

        public Integer getNumber() {
            return number;
        }

        public void setNumber(Integer number) {
            this.number = number;
        }

        public String getSquadOrder() {
            return squadOrder;
        }

        public void setSquadOrder(String squadOrder) {
            this.squadOrder = squadOrder;
        }

        public Integer getDnp() {
            return dnp;
        }

        public void setDnp(Integer dnp) {
            this.dnp = dnp;
        }

        public String getIsOnCourt() {
            return isOnCourt;
        }

        public void setIsOnCourt(String isOnCourt) {
            this.isOnCourt = isOnCourt;
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("SimplePlayer{");
            sb.append("pid=").append(pid);
            sb.append(", starting=").append(starting);
            sb.append(", position=").append(position);
            sb.append(", stats=").append(stats);
            sb.append(", squadOrder=").append(squadOrder);
            sb.append('}');
            return sb.toString();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof SimplePlayer)) return false;

            SimplePlayer that = (SimplePlayer) o;

            if (pid != null ? !pid.equals(that.pid) : that.pid != null) return false;

            return true;
        }

        @Override
        public int hashCode() {
            return pid != null ? pid.hashCode() : 0;
        }
    }

    /**
     * 最佳球员统计
     */
    public static class BestPlayerStat implements Serializable {
        private static final long serialVersionUID = -48819333470825837L;

        //队伍id
        private Long tid;
        //最佳球员统计
        private List<SimplePlayer> players;

        public Long getTid() {
            return tid;
        }

        public void setTid(Long tid) {
            this.tid = tid;
        }

        public List<SimplePlayer> getPlayers() {
            return players;
        }

        public void setPlayers(List<SimplePlayer> players) {
            this.players = players;
        }
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<CountryCode> getAllowCountries() {
        return allowCountries;
    }

    public void setAllowCountries(List<CountryCode> allowCountries) {
        this.allowCountries = allowCountries;
    }


    /**
     * 分节的统计数据
     */
    public static class SectionStat implements Serializable {

        private static final long serialVersionUID = -3003677463822831257L;

        //节的字典id
        private Long section;
        //本节的顺序
        private int order;
        //统计数据
        private Map<String, Object> stats;

        public Long getSection() {
            return section;
        }

        public void setSection(Long section) {
            this.section = section;
        }

        public int getOrder() {
            return order;
        }

        public void setOrder(int order) {
            this.order = order;
        }

        public Map<String, Object> getStats() {
            return stats;
        }

        public void setStats(Map<String, Object> stats) {
            this.stats = stats;
        }

        @Override
        public String toString() {
            return "SectionStat{" +
                    "section=" + section +
                    ", order=" + order +
                    ", stats=" + stats +
                    '}';
        }
    }

    /**
     * 球员数据统计
     */
    public static class PlayerStat implements Serializable {
        private static final long serialVersionUID = -68819333470825837L;

        //队员id
        private Long playerId;
        //统计数据
        private Map<String, Object> stats;

        public Long getPlayerId() {
            return playerId;
        }

        public void setPlayerId(Long playerId) {
            this.playerId = playerId;
        }

        public Map<String, Object> getStats() {
            return stats;
        }

        public void setStats(Map<String, Object> stats) {
            this.stats = stats;
        }
    }
}
