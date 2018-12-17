package com.lesports.qmt.sbc.param;

import com.lesports.qmt.QmtConstants;
import com.lesports.qmt.sbc.api.dto.TopicTemplateType;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * Created by denghui on 2016/11/2.
 */
public class SaveTopicParam implements Serializable {
    private static final long serialVersionUID = 6738300745996762415L;

    private Long id;
    //专题模板
    private TopicTemplateType templateType = TopicTemplateType.EMERGENCY;
    //标题
    @NotBlank(message = "name is required")
    private String name;
    //短标题
    private String shortName;
    //分享标题
    private String shareName;
    //描述
    @NotBlank(message = "description is required")
    private String desc;
    //分享描述
    private String shareDesc;
    //频道ID
    private Long channelId;
    //项目ID
    private Long subChannelId;
    //所属赛事
    private Long cid;
    //相关赛程
    private String mids;
    //相关标签
    private String relatedTags;
    //发布名称
    private String pubName;
    //页面meta信息中的关键词
    private String keywords;
    //显示M站分享按钮
    private Boolean shareable = true;
    //播放平台
    private String platforms;
    //发布时间
    @Pattern(regexp = QmtConstants.REGEX_YMDHMS, message = "invalid date time format")
    private String publishAt;
    //Banner图JSON
    private String bannerImage;
    //焦点图JSON
    private String focusImages;
    //存为草稿
    private Boolean isDraft;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TopicTemplateType getTemplateType() {
        return templateType;
    }

    public void setTemplateType(TopicTemplateType templateType) {
        this.templateType = templateType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getShareName() {
        return shareName;
    }

    public void setShareName(String shareName) {
        this.shareName = shareName;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getShareDesc() {
        return shareDesc;
    }

    public void setShareDesc(String shareDesc) {
        this.shareDesc = shareDesc;
    }

    public Long getChannelId() {
        return channelId;
    }

    public void setChannelId(Long channelId) {
        this.channelId = channelId;
    }

    public Long getSubChannelId() {
        return subChannelId;
    }

    public void setSubChannelId(Long subChannelId) {
        this.subChannelId = subChannelId;
    }

    public Long getCid() {
        return cid;
    }

    public void setCid(Long cid) {
        this.cid = cid;
    }

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

    public String getPlatforms() {
        return platforms;
    }

    public void setPlatforms(String platforms) {
        this.platforms = platforms;
    }

    public String getPublishAt() {
        return publishAt;
    }

    public void setPublishAt(String publishAt) {
        this.publishAt = publishAt;
    }

    public String getBannerImage() {
        return bannerImage;
    }

    public void setBannerImage(String bannerImage) {
        this.bannerImage = bannerImage;
    }

    public String getFocusImages() {
        return focusImages;
    }

    public void setFocusImages(String focusImages) {
        this.focusImages = focusImages;
    }

    public Boolean getDraft() {
        return isDraft;
    }

    public void setDraft(Boolean draft) {
        isDraft = draft;
    }

    public Boolean getShareable() {
        return shareable;
    }

    public void setShareable(Boolean shareable) {
        this.shareable = shareable;
    }

    public String getMids() {
        return mids;
    }

    public void setMids(String mids) {
        this.mids = mids;
    }

    public String getRelatedTags() {
        return relatedTags;
    }

    public void setRelatedTags(String relatedTags) {
        this.relatedTags = relatedTags;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}
