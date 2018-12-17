package com.lesports.qmt.web.api.core.vo;

import com.google.common.collect.Lists;
import com.lesports.qmt.sbd.api.common.CompetitorType;
import com.lesports.qmt.sbd.api.common.GroundType;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by ruiyuansheng on 2015/10/30.
 */
public class TVHallEpisodeVo {

    //节目id
    private Long id;
    //赛程id
    private Long mid;
    //赛程名称
    private String name;
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
    //直播图片集
    private Map<String, String> imageUrl;
    //是否有直播 0:无 1:有
    private Integer isLive = 0;
    //是否有录播 0:无 1:有
    private Integer isRecorded = 0;
    //是否有集锦 0:无 1:有
    private Integer isHighlights = 0;
    //赛事logo
    private String logo;
    //参赛者
    private List<Competitor> competitors = Lists.newArrayList();
    //图片集
    private Map<String, String> images;
    //类型
    private Integer type;
    //直播信息
    private List<Lives> lives = Lists.newArrayList();
    //录播id
    private Long recordedId;
    //集锦id
    private Long highlightsId;
    //战报id
    private Long reportId;
    //赛程相关的视频集合
    private Set<Long> videoIds;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMid() {
        return mid;
    }

    public void setMid(Long mid) {
        this.mid = mid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getVs() {
        return vs;
    }

    public void setVs(Integer vs) {
        this.vs = vs;
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

    public Map<String, String> getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(Map<String, String> imageUrl) {
        this.imageUrl = imageUrl;
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

    public void setIsHighlights(Integer isHighlights) {
        this.isHighlights = isHighlights;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public List<Competitor> getCompetitors() {
        return competitors;
    }

    public void setCompetitors(List<Competitor> competitors) {
        this.competitors = competitors;
    }

    public Map<String, String> getImages() {
        return images;
    }

    public void setImages(Map<String, String> images) {
        this.images = images;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public List<Lives> getLives() {
        return lives;
    }

    public void setLives(List<Lives> lives) {
        this.lives = lives;
    }

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

    public Long getReportId() {
        return reportId;
    }

    public void setReportId(Long reportId) {
        this.reportId = reportId;
    }

    public Set<Long> getVideoIds() {
        return videoIds;
    }

    public void setVideoIds(Set<Long> videoIds) {
        this.videoIds = videoIds;
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

    public class Lives {
        private String liveId;
        private String chatRoomId;
        private String name;
        private Integer liveStatus;
        private Map<String, String> images;

        public String getLiveId() {
            return liveId;
        }

        public void setLiveId(String liveId) {
            this.liveId = liveId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getLiveStatus() {
            return liveStatus;
        }

        public void setLiveStatus(Integer liveStatus) {
            this.liveStatus = liveStatus;
        }

        public Map<String, String> getImages() {
            return images;
        }

        public void setImages(Map<String, String> images) {
            this.images = images;
        }

        public String getChatRoomId() {
            return chatRoomId;
        }

        public void setChatRoomId(String chatRoomId) {
            this.chatRoomId = chatRoomId;
        }

        @Override
        public String toString() {
            final StringBuffer sb = new StringBuffer("Lives{");
            sb.append("liveId='").append(liveId).append('\'');
            sb.append(", chatRoomId='").append(chatRoomId).append('\'');
            sb.append(", name='").append(name).append('\'');
            sb.append(", liveStatus=").append(liveStatus);
            sb.append(", images=").append(images);
            sb.append('}');
            return sb.toString();
        }
    }

}
