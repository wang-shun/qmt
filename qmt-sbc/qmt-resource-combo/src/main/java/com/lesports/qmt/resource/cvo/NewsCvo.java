package com.lesports.qmt.resource.cvo;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.lesports.qmt.config.api.dto.TTag;
import com.lesports.qmt.sbc.api.common.NewsType;
import com.lesports.qmt.sbc.api.dto.TNewsImage;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 新闻VO
 * Created by denghui on 2016/12/18.
 */
public class NewsCvo extends BaseCvo {

    private static final long serialVersionUID = -4895418772080551558L;

    //新闻类型
    private NewsType newsType;
    //播放时长
    private Long duration;
    //如果是视频新闻的话为视频id
    private Long vid;
    //图集图片
    private List<TNewsImage> images;

    //标签
    private Set<TTag> tags = Sets.newHashSet();
    //是否付费
    private Integer isPay = 0;

    //发布时间
    private String pubtime;

    private long starLevel;

    private Map<String,String> videoImages = Maps.newHashMap();

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

    public Set<TTag> getTags() {
        return tags;
    }

    public void setTags(Set<TTag> tags) {
        this.tags = tags;
    }

    public Integer getIsPay() {
        return isPay;
    }

    public void setIsPay(Integer isPay) {
        this.isPay = isPay;
    }

    public String getPubtime() {
        return pubtime;
    }

    public void setPubtime(String pubtime) {
        this.pubtime = pubtime;
    }

    public long getStarLevel() {
        return starLevel;
    }

    public void setStarLevel(long starLevel) {
        this.starLevel = starLevel;
    }

    public Map<String, String> getVideoImages() {
        return videoImages;
    }

    public void setVideoImages(Map<String, String> videoImages) {
        this.videoImages = videoImages;
    }
}
