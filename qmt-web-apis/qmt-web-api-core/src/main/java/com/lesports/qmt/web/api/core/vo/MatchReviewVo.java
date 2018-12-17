package com.lesports.qmt.web.api.core.vo;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.lesports.qmt.sbd.api.common.CompetitorType;
import com.lesports.qmt.sbd.api.common.GroundType;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * Created by zhonglin on 2016/2/26.
 */
public class MatchReviewVo  implements Serializable {

    private Long id;
    //比赛名称
    private String name;
    //历史交锋数据
    private List<HistoryMatch> confrontations = Lists.newArrayList();
    //最近及未来比赛信息
    private Set<MatchInfo> matchInfos = Sets.newHashSet();

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

    public List<HistoryMatch> getConfrontations() {
        return confrontations;
    }

    public void setConfrontations(List<HistoryMatch> confrontations) {
        this.confrontations = confrontations;
    }

    public Set<MatchInfo> getMatchInfos() {
        return matchInfos;
    }

    public void setMatchInfos(Set<MatchInfo> matchInfos) {
        this.matchInfos = matchInfos;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("MatchReview{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", confrontations=").append(confrontations);
        sb.append(", matchInfos=").append(matchInfos);
        sb.append('}');
        return sb.toString();
    }




    /**
     * 历史交锋数据
     */
    public static class HistoryMatch {
        //比赛id
        private Long mid;
        //赛季id
        private Long csid;
        //赛事id
        private Long cid;
        //比赛阶段
        private String stage;
        //比赛轮次
        private String round;
        //比赛开始时间
        private String startTime;
        //精确到天的比赛开始时间,为了方便按天查询格式:yyyyMMdd
        private String startDate;
        //比赛名称
        private String name;
        //参赛者信息
        private Set<Competitor> competitors = Sets.newHashSet();


        public class Competitor {
            //球队或球员id
            private Long id;
            //球队或球员名称
            private String name;
            //球队或球员logo
            private String logo;
            //得分
            private String score;


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

            public String getLogo() {
                return logo;
            }

            public void setLogo(String logo) {
                this.logo = logo;
            }

            public String getScore() {
                return score;
            }

            public void setScore(String score) {
                this.score = score;
            }

            @Override
            public String toString() {
                final StringBuffer sb = new StringBuffer("Competitor{");
                sb.append("id=").append(id);
                sb.append(", name='").append(name).append('\'');
                sb.append(", logo='").append(logo).append('\'');
                sb.append(", score='").append(score).append('\'');
                sb.append('}');
                return sb.toString();
            }
        }

        public String getRound() {
            return round;
        }

        public void setRound(String round) {
            this.round = round;
        }

        public Long getMid() {
            return mid;
        }

        public void setMid(Long mid) {
            this.mid = mid;
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

        public String getStage() {
            return stage;
        }

        public void setStage(String stage) {
            this.stage = stage;
        }

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public String getStartDate() {
            return startDate;
        }

        public void setStartDate(String startDate) {
            this.startDate = startDate;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Set<Competitor> getCompetitors() {
            return competitors;
        }

        public void setCompetitors(Set<Competitor> competitors) {
            this.competitors = competitors;
        }

        @Override
        public String toString() {
            final StringBuffer sb = new StringBuffer("MatchHistory{");
            sb.append("mid=").append(mid);
            sb.append(", csid=").append(csid);
            sb.append(", cid=").append(cid);
            sb.append(", stage=").append(stage);
            sb.append(", startTime='").append(startTime).append('\'');
            sb.append(", startDate='").append(startDate).append('\'');
            sb.append(", name='").append(name).append('\'');
            sb.append(", competitors=").append(competitors);
            sb.append('}');
            return sb.toString();
        }
    }

    public static class MatchInfo{

        //队伍or队员id
        private Long competitorId;
        //主客场
        private GroundType ground;
        //是队伍还是队员
        private CompetitorType type;
        //最近比赛列表
        private List<HistoryMatch> nearMatches = Lists.newArrayList();
        //未来比赛列表
        private List<HistoryMatch> afterMatches = Lists.newArrayList();


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

        public List<HistoryMatch> getNearMatches() {
            return nearMatches;
        }

        public void setNearMatches(List<HistoryMatch> nearMatches) {
            this.nearMatches = nearMatches;
        }

        public List<HistoryMatch> getAfterMatches() {
            return afterMatches;
        }

        public void setAfterMatches(List<HistoryMatch> afterMatches) {
            this.afterMatches = afterMatches;
        }

        @Override
        public String toString() {
            return "MatchInfo{" +
                    "competitorId=" + competitorId +
                    ", ground=" + ground +
                    ", type=" + type +
                    ", nearMatches=" + nearMatches +
                    ", afterMatches=" + afterMatches +
                    '}';
        }
    }
}
