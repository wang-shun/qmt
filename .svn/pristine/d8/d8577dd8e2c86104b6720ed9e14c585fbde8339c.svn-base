package com.lesports.qmt.op.web.api.core.vo;

import com.google.common.collect.Lists;
import com.lesports.qmt.sbd.api.common.CompetitorType;
import com.lesports.qmt.sbd.api.common.GroundType;
import com.lesports.qmt.sbd.api.common.MatchSystem;


import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by gengchengliang on 2015/6/23.
 */
public class HallEpisodeVo implements Serializable {

    private static final long serialVersionUID = 6688586345985429342L;

    //节目id
    private Long id;
    //赛程id
    private Long mid;
    //赛程名称
    private String name;
    //赛事id
    private Long cid;
    ////赛事名称
    private String cname;
    //赛季
    private String season;
    //比赛开始时间
    private String startTime;
    //比赛状态 0:未开始, 1:比赛中, 2:比赛结束
    private Integer matchStatus;
    //比赛状态 0:未开始, 1:比赛中, 2:比赛结束
    private Integer status;
    //是否对阵 0:非对阵 1:对阵
    private Integer vs;
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
    //直播图片集
    private Map<String, String> imageUrl;
    //是否有直播 0:无 1:有
    private Integer isLive = 0;
    //是否有图文直播 0:无 1:有
    private Integer isTextLive = 0;
    //是否有录播 0:无 1:有
    private Integer isRecorded = 0;
    //是否有集锦 0:无 1:有
    private Integer isHighlights = 0;
    //比赛所属时段
    private Integer timeSection;
    //比赛所属轮次id
    private Long roundId;
    //杯赛还是联赛
    private MatchSystem matchSystem;
    //赛事logo
    private String logo;
    //比赛时段和比赛时间
    private String moment;
    //参赛者
    private List<Competitor> competitors = Lists.newArrayList();
    //参赛者排行: F1单场赛程的排行信息
    private List<CompetitorStat> competitorStats;
    //标签
    private Set<Tag> tags;
    //播放链接
    private String playLink;
    //图文直播链接
    private String tLiveLink;
    //图文直播链接-H5地址
    private String tLiveLink4H5;
    //图片集
    private Map<String, String> images;
    //期数
    private String periods;
    //描述
    private String desc;
    //时长
    private long duration;
    //类型
    private Integer type;
    //新英付费标识
    private int xinyingPay;
    //赛事阶段名称: 英超 第20轮, NBA 季后赛等
    private String showName;
    //评论id
    private String commentId;

    //录播id
    private Long recordedId;
    //集锦id
    private Long highlightsId;
    //m站播放链接
    private String mPlayLink;

    public Long getRecordedId() {
        return recordedId;
    }

    public void setRecordedId(Long recordedId) {
        this.recordedId = recordedId;
    }

    public Long getHighlightsId() {
        return highlightsId;
    }

    public void setHighlightsId(Long highlightsId) {
        this.highlightsId = highlightsId;
    }

    public class Tag {
        //tagId
        private Long id;
        //tagName
        private String name;

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

        @Override
        public String toString() {
            final StringBuffer sb = new StringBuffer("Tag{");
            sb.append("id=").append(id);
            sb.append(", name='").append(name).append('\'');
            sb.append('}');
            return sb.toString();
        }
    }

    public class Competitor {
        //球队或球员id
        private Long id;
        //球队或球员名称
        private String name;
        //球队或球员logo
        private String logo;
        //球队pngLogo(球员暂无pngLogo)
        private String pnglogo;
        //背板图
        private String bgWebUrl;
        //得分
        private String score;
        //主客场
        private GroundType ground;
        //参赛者类型
        private CompetitorType competitorType;
        //分节比分
        private List<SectionResult> SectionResults = Lists.newArrayList();

        public class SectionResult {
            //分段顺序
            private int order;
            //分段名称
            private String section;
            //分段比赛结果
            private String result;

            public int getOrder() {
                return order;
            }

            public void setOrder(int order) {
                this.order = order;
            }

            public String getSection() {
                return section;
            }

            public void setSection(String section) {
                this.section = section;
            }

            public String getResult() {
                return result;
            }

            public void setResult(String result) {
                this.result = result;
            }

            @Override
            public String toString() {
                return "SectionResult{" +
                        "order=" + order +
                        ", section='" + section + '\'' +
                        ", result='" + result + '\'' +
                        '}';
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

        public GroundType getGround() {
            return ground;
        }

        public void setGround(GroundType ground) {
            this.ground = ground;
        }

        public List<SectionResult> getSectionResults() {
            return SectionResults;
        }

        public void setSectionResults(List<SectionResult> sectionResults) {
            SectionResults = sectionResults;
        }

        public CompetitorType getCompetitorType() {
            return competitorType;
        }

        public void setCompetitorType(CompetitorType competitorType) {
            this.competitorType = competitorType;
        }

        public String getPnglogo() {
            return pnglogo;
        }

        public void setPnglogo(String pnglogo) {
            this.pnglogo = pnglogo;
        }

        public String getBgWebUrl() {
            return bgWebUrl;
        }

        public void setBgWebUrl(String bgWebUrl) {
            this.bgWebUrl = bgWebUrl;
        }

        @Override
        public String toString() {
            final StringBuffer sb = new StringBuffer("Competitor{");
            sb.append("id=").append(id);
            sb.append(", name='").append(name).append('\'');
            sb.append(", logo='").append(logo).append('\'');
            sb.append(", pnglogo='").append(pnglogo).append('\'');
            sb.append(", bgWebUrl='").append(bgWebUrl).append('\'');
            sb.append(", score='").append(score).append('\'');
            sb.append(", ground=").append(ground);
            sb.append(", competitorType=").append(competitorType);
            sb.append(", SectionResults=").append(SectionResults);
            sb.append('}');
            return sb.toString();
        }
    }

    public class CompetitorStat {
        //球队或球员id
        private Long id;
        //球队或球员名称
        private String name;
        //参赛者类型
        private CompetitorType competitorType;
        //排序
        private Integer order;
        //如果参赛者是球员时,请添加参赛者所属队伍id
        private Long tid;
        //如果参赛者是球员时,请添加参赛者所属队伍名称
        private String teamName;
        //参赛者技术统计
        private Map<String, String> stats = Collections.EMPTY_MAP;

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

        public CompetitorType getCompetitorType() {
            return competitorType;
        }

        public void setCompetitorType(CompetitorType competitorType) {
            this.competitorType = competitorType;
        }

        public String getTeamName() {
            return teamName;
        }

        public void setTeamName(String teamName) {
            this.teamName = teamName;
        }

        public Map<String, String> getStats() {
            return stats;
        }

        public void setStats(Map<String, String> stats) {
            this.stats = stats;
        }

        public Integer getOrder() {
            return order;
        }

        public void setOrder(Integer order) {
            this.order = order;
        }

        public Long getTid() {
            return tid;
        }

        public void setTid(Long tid) {
            this.tid = tid;
        }

        @Override
        public String toString() {
            return "CompetitorStat{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", competitorType=" + competitorType +
                    ", order=" + order +
                    ", tid=" + tid +
                    ", teamName='" + teamName + '\'' +
                    ", stats=" + stats +
                    '}';
        }
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Map<String, String> getImages() {
        return images;
    }

    public void setImages(Map<String, String> images) {
        this.images = images;
    }

    public String getPeriods() {
        return periods;
    }

    public void setPeriods(String periods) {
        this.periods = periods;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
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

    public Long getCid() {
        return cid;
    }

    public void setCid(Long cid) {
        this.cid = cid;
    }

    public String getCname() {
        return cname;
    }

    public Map<String, String> getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(Map<String, String> imageUrl) {
        this.imageUrl = imageUrl;
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

    public Integer getVs() {
        return vs;
    }

    public void setVs(Integer vs) {
        this.vs = vs;
    }

    public List<Competitor> getCompetitors() {
        return competitors;
    }

    public void setCompetitors(List<Competitor> competitors) {
        this.competitors = competitors;
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

    public Integer getIsLive() {
        return isLive;
    }

    public void setIsLive(Integer isLive) {
        this.isLive = isLive;
    }

    public Integer getIsRecorded() {
        return isRecorded;
    }

    public void setIsRecorded(Integer isRecorded) {
        this.isRecorded = isRecorded;
    }

    public Integer getIsHighlights() {
        return isHighlights;
    }

    public void setIsHighlights(Integer isVid) {
        this.isHighlights = isVid;
    }

    public Long getMid() {
        return mid;
    }

    public void setMid(Long mid) {
        this.mid = mid;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getTimeSection() {
        return timeSection;
    }

    public void setTimeSection(Integer timeSection) {
        this.timeSection = timeSection;
    }

    public Long getRoundId() {
        return roundId;
    }

    public void setRoundId(Long roundId) {
        this.roundId = roundId;
    }

    public MatchSystem getMatchSystem() {
        return matchSystem;
    }

    public void setMatchSystem(MatchSystem matchSystem) {
        this.matchSystem = matchSystem;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getMoment() {
        return moment;
    }

    public void setMoment(String moment) {
        this.moment = moment;
    }

    public List<CompetitorStat> getCompetitorStats() {
        return competitorStats;
    }

    public void setCompetitorStats(List<CompetitorStat> competitorStats) {
        this.competitorStats = competitorStats;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    public String getPlayLink() {
        return playLink;
    }

    public void setPlayLink(String playLink) {
        this.playLink = playLink;
    }

    public int getXinyingPay() {
        return xinyingPay;
    }

    public void setXinyingPay(int xinyingPay) {
        this.xinyingPay = xinyingPay;
    }

    public String getShowName() {
        return showName;
    }

    public void setShowName(String showName) {
        this.showName = showName;
    }

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    public Integer getIsTextLive() {
        return isTextLive;
    }

    public void setIsTextLive(Integer isTextLive) {
        this.isTextLive = isTextLive;
    }

    public String gettLiveLink() {
        return tLiveLink;
    }

    public void settLiveLink(String tLiveLink) {
        this.tLiveLink = tLiveLink;
    }

    public String gettLiveLink4H5() {
        return tLiveLink4H5;
    }

    public void settLiveLink4H5(String tLiveLink4H5) {
        this.tLiveLink4H5 = tLiveLink4H5;
    }

    public String getMPlayLink() {
        return mPlayLink;
    }

    public void setMPlayLink(String mPlayLink) {
        this.mPlayLink = mPlayLink;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("HallEpisodeVo{");
        sb.append("id=").append(id);
        sb.append(", mid=").append(mid);
        sb.append(", name='").append(name).append('\'');
        sb.append(", cid=").append(cid);
        sb.append(", cname='").append(cname).append('\'');
        sb.append(", season='").append(season).append('\'');
        sb.append(", startTime='").append(startTime).append('\'');
        sb.append(", matchStatus=").append(matchStatus);
        sb.append(", status=").append(status);
        sb.append(", vs=").append(vs);
        sb.append(", round='").append(round).append('\'');
        sb.append(", stage='").append(stage).append('\'');
        sb.append(", group='").append(group).append('\'');
        sb.append(", gameFType=").append(gameFType);
        sb.append(", gameFName='").append(gameFName).append('\'');
        sb.append(", gameSType=").append(gameSType);
        sb.append(", gameSName='").append(gameSName).append('\'');
        sb.append(", imageUrl=").append(imageUrl);
        sb.append(", isLive=").append(isLive);
        sb.append(", isRecorded=").append(isRecorded);
        sb.append(", isHighlights=").append(isHighlights);
        sb.append(", timeSection=").append(timeSection);
        sb.append(", roundId=").append(roundId);
        sb.append(", matchSystem=").append(matchSystem);
        sb.append(", logo='").append(logo).append('\'');
        sb.append(", moment='").append(moment).append('\'');
        sb.append(", competitors=").append(competitors);
        sb.append(", competitorStats=").append(competitorStats);
        sb.append(", tags=").append(tags);
        sb.append(", playLink='").append(playLink).append('\'');
        sb.append(", images=").append(images);
        sb.append(", periods='").append(periods).append('\'');
        sb.append(", desc='").append(desc).append('\'');
        sb.append(", duration=").append(duration);
        sb.append(", type=").append(type);
        sb.append(", xinyingPay=").append(xinyingPay);
        sb.append(", showName='").append(showName).append('\'');
        sb.append(", commentId='").append(commentId).append('\'');
        sb.append(", isTextLive='").append(isTextLive).append('\'');
        sb.append(", tLiveLink='").append(tLiveLink).append('\'');
        sb.append(", tLiveLink4H5='").append(tLiveLink4H5).append('\'');
        sb.append(", recordedId='").append(recordedId).append('\'');
        sb.append(", highlightsId='").append(highlightsId).append('\'');
        sb.append(", mPlayLink='").append(mPlayLink).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
