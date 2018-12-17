package com.lesports.qmt.web.datacenter.vo;

import com.google.common.collect.Lists;
import com.lesports.qmt.config.api.dto.TTag;
import com.lesports.qmt.sbc.api.common.NewsType;
import com.lesports.qmt.sbc.api.dto.TNewsImage;

import java.util.List;

/**
 * 新闻VO
 * Created by denghui on 2016/12/18.
 */
public class NewsVo extends ContentBaseVo{
    //新闻类型
    private NewsType newsType;
    //播放时长
    private Long duration;
    //如果是视频新闻的话为视频id
    private Long vid;
    //图集图片
    private List<TNewsImage> images;
    //标签
    private List<TTag> tags = Lists.newArrayList();
    //是否付费
    private Integer isPay = 0;

    public NewsType getNewsType() {
        return newsType;
    }

    public void setNewsType(NewsType newsType) {
        this.newsType = newsType;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public Long getVid() {
        return vid;
    }

    public void setVid(Long vid) {
        this.vid = vid;
    }

    public List<TNewsImage> getImages() {
        return images;
    }

    public void setImages(List<TNewsImage> images) {
        this.images = images;
    }

    public List<TTag> getTags() {
        return tags;
    }

    public void setTags(List<TTag> tags) {
        this.tags = tags;
    }

    public Integer getIsPay() {
        return isPay;
    }

    public void setIsPay(Integer isPay) {
        this.isPay = isPay;
    }
}
