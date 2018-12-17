package com.lesports.qmt.sbc.model;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.lesports.api.common.*;
import com.lesports.qmt.model.support.QmtModel;
import com.lesports.qmt.sbc.api.common.NewsType;
import com.lesports.qmt.sbc.api.common.TvLicence;
import com.lesports.qmt.sbc.api.dto.JumpUrlType;
import com.lesports.utils.math.LeNumberUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 新闻
 * User: ellios
 * Time: 15-7-20 : 下午3:18
 */
@Document(collection = "news")
public class News extends QmtModel<Long> {
    private static final long serialVersionUID = 7287463012334469369L;

    /*************************一般属性*****************************/
    //是否转链接
    @Field("is_jump")
    private Boolean isJump;
    //转链接地址类型
    @Field("jump_url_type")
    private JumpUrlType jumpUrlType;
    //转链接地址
    @Field("jump_url")
    private String jumpUrl;
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
    //星级
    @Field("star_level")
    private Long starLevel;
    //来源
    private String source;
    //作者
    private String author;
    //所属赛事
    private Long cid;
    //上线状态
    private PublishStatus online;
    //新闻类型
    private NewsType type;
    //播放平台
    private Set<Platform> platforms = Sets.newHashSet();
    //新闻内容
    private String content;
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
    //声明
    private List<String> statements = Lists.newArrayList();
    //是否允许评论
    @Field("allow_comment")
    private Boolean allowComment;
    //富文本封面图 1610(16:10) 169(16:9) 43(4:3) 34(3:4) 21(2:1) 11(1:1)
    @Field("cover_image")
    private Map<String, ImageUrlExt> coverImage = Maps.newHashMap();
    //相关id 赛事 赛程 球队 球员 自制专辑 自制节目 其他节目 标签 频道 项目
    @Field("related_ids")
    private Set<Long> relatedIds = Sets.newHashSet();
    //相关内容
    @Field("related_items")
    private List<RelatedItem> relatedItems = Lists.newArrayList();

    /*************************视频新闻属性*****************************/
    //视频ID
    private Long vid;
    //媒资视频ID
    @Field("leeco_vid")
    private Long leecoVid;
    //支持的TV牌照方
    @Field("support_licences")
    private Set<TvLicence> supportLicences = Sets.newHashSet();
    //是否付费
    @Field("is_pay")
    private Boolean isPay;
    //付费平台
    @Field("pay_platforms")
    private Set<Platform> payPlatforms = Sets.newHashSet();
    //该视频新闻是否是drm视频: 0:不是 1:是
    @Field("drm_flag")
    private Integer drmFlag = 0;

    /*************************其他属性*****************************/
    //新增时表示是否保存为草稿
    @Transient
    private Boolean isDraft;


    public Boolean getJump() {
        return isJump;
    }

    public void setJump(Boolean jump) {
        isJump = jump;
    }

    public JumpUrlType getJumpUrlType() {
        return jumpUrlType;
    }

    public void setJumpUrlType(JumpUrlType jumpUrlType) {
        this.jumpUrlType = jumpUrlType;
    }

    public String getJumpUrl() {
        return jumpUrl;
    }

    public void setJumpUrl(String jumpUrl) {
        this.jumpUrl = jumpUrl;
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

    public Long getStarLevel() {
        return starLevel;
    }

    public void setStarLevel(Long starLevel) {
        this.starLevel = starLevel;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public PublishStatus getOnline() {
        return online;
    }

    public void setOnline(PublishStatus online) {
        this.online = online;
    }

    public NewsType getType() {
        return type;
    }

    public void setType(NewsType type) {
        this.type = type;
    }

    public Set<Platform> getPlatforms() {
        return platforms;
    }

    public void setPlatforms(Set<Platform> platforms) {
        this.platforms = platforms;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public List<String> getStatements() {
        return statements;
    }

    public void setStatements(List<String> statements) {
        this.statements = statements;
    }

    public Boolean getAllowComment() {
        return allowComment;
    }

    public void setAllowComment(Boolean allowComment) {
        this.allowComment = allowComment;
    }

    public Map<String, ImageUrlExt> getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(Map<String, ImageUrlExt> coverImage) {
        this.coverImage = coverImage;
    }

    public List<RelatedItem> getRelatedItems() {
        return relatedItems;
    }

    public void setRelatedItems(List<RelatedItem> relatedItems) {
        this.relatedItems = relatedItems;
    }

    public Long getVid() {
        return vid;
    }

    public void setVid(Long vid) {
        this.vid = vid;
    }

    public Long getLeecoVid() {
        return leecoVid;
    }

    public void setLeecoVid(Long leecoVid) {
        this.leecoVid = leecoVid;
    }

    public Boolean getPay() {
        return isPay;
    }

    public void setPay(Boolean pay) {
        isPay = pay;
    }

    public Set<Platform> getPayPlatforms() {
        return payPlatforms;
    }

    public void setPayPlatforms(Set<Platform> payPlatforms) {
        this.payPlatforms = payPlatforms;
    }

    public Boolean getDraft() {
        return isDraft;
    }

    public void setDraft(Boolean draft) {
        isDraft = draft;
    }

    public Integer getDrmFlag() {
        return drmFlag;
    }

    public void setDrmFlag(Integer drmFlag) {
        this.drmFlag = drmFlag;
    }

    public Long getCid() {
        return cid;
    }

    public void setCid(Long cid) {
        this.cid = cid;
    }

    public void addStatement(String statement) {
        if (StringUtils.isNotBlank(statement)) {
            this.statements.add(statement);
        }
    }

    public Set<TvLicence> getSupportLicences() {
        return supportLicences;
    }

    public void setSupportLicences(Set<TvLicence> supportLicences) {
        this.supportLicences = supportLicences;
    }

    public Set<Long> getRelatedIds() {
        return relatedIds;
    }

    public void setRelatedIds(Set<Long> relatedIds) {
        this.relatedIds = relatedIds;
    }

    public void addRelatedId(Long id) {
        if (LeNumberUtils.toLong(id) > 0) {
            this.relatedIds.add(id);
        }
    }

}
