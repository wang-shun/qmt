package com.lesports.qmt.op.web.api.core.vo;

import java.io.Serializable;

/**
 * Created by lufei on 2015/11/5.
 */
public class OlyNewsVo implements Serializable {
    //新闻id
    private long id;
    //推荐类型
    private Integer type;
    //创建时间
    private String createTime;
    //图片
    private String imageUrl;
    //标题
    private String name;
    //视频播放地址
    private String videoUrl;
    //简介
    private String desc;
    //来源
    private String source;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("NewsVo{");
        sb.append("id=").append(id);
        sb.append(", type=").append(type);
        sb.append(", createTime='").append(createTime).append('\'');
        sb.append(", imageUrl=").append(imageUrl);
        sb.append(", name='").append(name).append('\'');
        sb.append(", videoUrl='").append(videoUrl).append('\'');
        sb.append(", desc='").append(desc).append('\'');
        sb.append(", source='").append(source).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
