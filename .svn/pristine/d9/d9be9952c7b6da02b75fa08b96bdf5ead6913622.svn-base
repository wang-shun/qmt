package com.lesports.qmt.sbc.model;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.lesports.api.common.*;
import com.lesports.qmt.model.support.QmtModel;
import com.lesports.qmt.sbc.api.dto.TopicTemplateType;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Map;
import java.util.Set;

/**
 * 突发事件专题
 * Created by denghui on 2016/10/13.
 */
@Document(collection = "topics")
public class Topic extends QmtModel<Long> {
    private static final long serialVersionUID = 7300793330105708730L;

    //模板类型
    @Field("template_type")
    private TopicTemplateType templateType;
    //标题
    private String name;
    //短标题
    @Field("short_name")
    private String shortName;
    //分享标题
    @Field("share_name")
    private String shareName;
    //描述
    private String desc;
    //分享描述
    @Field("short_desc")
    private String shareDesc;
    //频道ID
    @Field("channel_id")
    private Long channelId;
    //项目ID 仅当频道为综合体育时可用
    @Field("sub_channel_id")
    private Long subChannelId;
    //所属赛事
    private Long cid;
    //URL发布名称
    @Field("pub_name")
    private String pubName;
    //页面meta信息中的关键词
    private String keywords;
    //显示M站分享按钮
    private Boolean shareable;
    //上线状态
    private PublishStatus online;
    //相关id 赛程 球队 球员 自制专辑 自制节目 其他节目 标签 字典
    @Field("related_ids")
    private Set<Long> relatedIds = Sets.newHashSet();
    //推送平台
    private Set<Platform> platforms = Sets.newHashSet();
    //Banner图
    @Field("banner_image")
    private ImageUrlExt bannerImage;
    //焦点图
    @Field("focus_images")
    private Map<String, ImageUrlExt> focusImages = Maps.newHashMap();
    //是否删除
    private Boolean deleted;
    //发布时间
    @Field("publish_at")
    private String publishAt;
    //发布国家
    @Field("allow_country")
    private CountryCode allowCountry;
    //发布语言
    @Field("language_code")
    private LanguageCode languageCode;

    /*************************其他属性*****************************/
    //新增时表示是否保存为草稿
    @Transient
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

    public Set<Long> getRelatedIds() {
        return relatedIds;
    }

    public void setRelatedIds(Set<Long> relatedIds) {
        this.relatedIds = relatedIds;
    }

    public ImageUrlExt getBannerImage() {
        return bannerImage;
    }

    public void setBannerImage(ImageUrlExt bannerImage) {
        this.bannerImage = bannerImage;
    }

    public Map<String, ImageUrlExt> getFocusImages() {
        return focusImages;
    }

    public void setFocusImages(Map<String, ImageUrlExt> focusImages) {
        this.focusImages = focusImages;
    }

    public Set<Platform> getPlatforms() {
        return platforms;
    }

    public void setPlatforms(Set<Platform> platforms) {
        this.platforms = platforms;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public String getPublishAt() {
        return publishAt;
    }

    public void setPublishAt(String publishAt) {
        this.publishAt = publishAt;
    }

    public CountryCode getAllowCountry() {
        return allowCountry;
    }

    public void setAllowCountry(CountryCode allowCountry) {
        this.allowCountry = allowCountry;
    }

    public LanguageCode getLanguageCode() {
        return languageCode;
    }

    public void setLanguageCode(LanguageCode languageCode) {
        this.languageCode = languageCode;
    }

    public PublishStatus getOnline() {
        return online;
    }

    public void setOnline(PublishStatus online) {
        this.online = online;
    }

    public Boolean getDraft() {
        return isDraft;
    }

    public void setDraft(Boolean draft) {
        isDraft = draft;
    }

    public Long getCid() {
        return cid;
    }

    public void setCid(Long cid) {
        this.cid = cid;
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

    public void addRelatedId(Long id) {
        if (id != null) {
            this.relatedIds.add(id);
        }
    }
}
