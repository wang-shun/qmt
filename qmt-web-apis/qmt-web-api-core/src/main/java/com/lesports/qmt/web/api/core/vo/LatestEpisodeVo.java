package com.lesports.qmt.web.api.core.vo;

import java.util.Map;

/**
 * Created by ruiyuansheng on 2015/7/30.
 */
public class LatestEpisodeVo {

    //节目id
    private long id;
    //专辑id
    private long aid;
    //评论id
    private String commentId;
    //节目开始时间
    private String startTime;
    //图片集
    private Map<String, String> images;
    //标题
    private String name;
    //期数
    private String periods;
    //描述
    private String desc;
    //时长
    private long duration;

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public long getAid() {
        return aid;
    }

    public void setAid(long aid) {
        this.aid = aid;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public Map<String, String> getImages() {
        return images;
    }

    public void setImages(Map<String, String> images) {
        this.images = images;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}
