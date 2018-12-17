package com.lesports.qmt.web.datacenter.vo;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.lesports.qmt.config.api.dto.TTag;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by denghui on 2016/12/18.
 */
public class TopicVo extends ContentBaseVo {

    private String pubName;
    //页面meta信息中的关键词
    private String keywords;
    //显示M站分享按钮
    private Boolean shareable;
    //banner图
    private String bannerImage;
    //焦点图
    private Map<String, String> focusImages = Maps.newHashMap();
    //新闻发布时间
    private String publishAt;
    //标签
    private Set<TTag> tags = Sets.newHashSet();

    public String getPubName() {
        return pubName;
    }

    public void setPubName(String pubName) {
        this.pubName = pubName;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public Boolean getShareable() {
        return shareable;
    }

    public void setShareable(Boolean shareable) {
        this.shareable = shareable;
    }

    public String getBannerImage() {
        return bannerImage;
    }

    public void setBannerImage(String bannerImage) {
        this.bannerImage = bannerImage;
    }

    public Map<String, String> getFocusImages() {
        return focusImages;
    }

    public void setFocusImages(Map<String, String> focusImages) {
        this.focusImages = focusImages;
    }

    public String getPublishAt() {
        return publishAt;
    }

    public void setPublishAt(String publishAt) {
        this.publishAt = publishAt;
    }

    public Set<TTag> getTags() {
        return tags;
    }

    public void setTags(Set<TTag> tags) {
        this.tags = tags;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
