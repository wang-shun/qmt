package com.lesports.qmt.web.api.core.vo;


import com.lesports.qmt.sbd.api.common.GroundType;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * lesports-projects
 * 直播赛事、专题、新闻
 *
 * @author pangchuanxiao
 * @since 15-6-27
 */
public class SearchResultItem implements Serializable {
    //赛事id
    private Long id;
    //赛事名称
    private String name;
    //logo图片地址
    private Map<String, String> imageUrl;
    //赛事简介
    private String desc;
    //是否是对阵
    private Integer vs;
    //比赛状态 0:未开始, 1:比赛中, 2:比赛结束
    private Integer status;
    private Long recordingId;
    //比赛开始时间
    private String startTime;
    //直播流地址
    private Map<String, String> liveUrl;
    private List<TopicInfo.VideoInfo> videos;
    //新闻的类型
    private Integer newsType;
    //对阵双方
    private List<Competitor> competitors;
    //创建时间
    private String createTime;
    //播放时长
    private String duration;
    //如果是视频新闻的话为视频id
    private Long vid;
    //评论id
    private String commentId;

    private Integer cornerMark;//1:vip,2:原创，3：专题，4：图集，5，回放，6：集锦

    public Integer getCornerMark() {
        return cornerMark;
    }

    public void setCornerMark(Integer cornerMark) {
        this.cornerMark = cornerMark;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public static class Competitor {
        //球队或球员名称
        private String name;
        //球队或球员logo
        private String logo;
        //得分
        private String score;
        //主客场
        private GroundType ground;

        public GroundType getGround() {
            return ground;
        }

        public void setGround(GroundType ground) {
            this.ground = ground;
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
    }

    public List<Competitor> getCompetitors() {
        return competitors;
    }

    public void setCompetitors(List<Competitor> competitors) {
        this.competitors = competitors;
    }

    public Integer getNewsType() {
        return newsType;
    }

    public void setNewsType(Integer newsType) {
        this.newsType = newsType;
    }

    public List<TopicInfo.VideoInfo> getVideos() {
        return videos;
    }

    public void setVideos(List<TopicInfo.VideoInfo> videos) {
        this.videos = videos;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public Long getRecordingId() {
        return recordingId;
    }

    public void setRecordingId(Long recordingId) {
        this.recordingId = recordingId;
    }

    public Long getVid() {
        return vid;
    }

    public void setVid(Long vid) {
        this.vid = vid;
    }

    public Map<String, String> getLiveUrl() {
        return liveUrl;
    }

    public void setLiveUrl(Map<String, String> liveUrl) {
        this.liveUrl = liveUrl;
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

    public Map<String, String> getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(Map<String, String> imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Integer getVs() {
        return vs;
    }

    public void setVs(Integer vs) {
        this.vs = vs;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
