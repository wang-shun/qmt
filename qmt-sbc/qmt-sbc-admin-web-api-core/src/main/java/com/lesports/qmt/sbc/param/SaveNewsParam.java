package com.lesports.qmt.sbc.param;

import com.lesports.qmt.QmtConstants;
import com.lesports.qmt.sbc.api.common.NewsType;
import com.lesports.qmt.sbc.api.dto.JumpUrlType;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * Created by denghui on 2016/10/21.
 */
public class SaveNewsParam implements Serializable {

    private static final long serialVersionUID = -3949896482310906698L;
    private Long id;
    //是否转链接
    private Boolean isJump;
    //转链接地址类型
    private JumpUrlType jumpUrlType;
    //转链接地址
    @URL
    private String jumpUrl;
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
    //项目ID 仅当频道为综合体育时可用
    private Long subChannelId;
    //星级
    private Long starLevel;
    //来源
    private String source;
    //作者
    private String author;
    //所属赛事
    private Long cid;
    //新闻类型
    @NotNull(message = "type is required")
    private NewsType type;
    //播放平台
    private String platforms;
    //新闻内容
    private String content;
    //发布时间
    @Pattern(regexp = QmtConstants.REGEX_YMDHMS, message = "invalid date time format")
    private String publishAt;
    //声明1
    private String statement;
    //是否允许评论 默认允许
    private Boolean allowComment = true;
    //图集图片JSON
    private String images;
    //富文本封面图JSON
    private String coverImage;
    //相关赛程
    private String mids;
    //相关内容
    private String relatedItems;
    //发布资源位id
    private String rsids;
    //存为草稿
    private Boolean isDraft;
    //相关标签
    private String relatedTags;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Long getCid() {
        return cid;
    }

    public void setCid(Long cid) {
        this.cid = cid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPublishAt() {
        return publishAt;
    }

    public void setPublishAt(String publishAt) {
        this.publishAt = publishAt;
    }

    public String getStatement() {
        return statement;
    }

    public void setStatement(String statement) {
        this.statement = statement;
    }

    public Boolean getAllowComment() {
        return allowComment;
    }

    public void setAllowComment(Boolean allowComment) {
        this.allowComment = allowComment;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String getRsids() {
        return rsids;
    }

    public void setRsids(String rsids) {
        this.rsids = rsids;
    }

    public NewsType getType() {
        return type;
    }

    public void setType(NewsType type) {
        this.type = type;
    }

    public Boolean getDraft() {
        return isDraft;
    }

    public void setDraft(Boolean draft) {
        isDraft = draft;
    }

    public String getPlatforms() {
        return platforms;
    }

    public void setPlatforms(String platforms) {
        this.platforms = platforms;
    }

    public String getMids() {
        return mids;
    }

    public void setMids(String mids) {
        this.mids = mids;
    }

    public String getRelatedItems() {
        return relatedItems;
    }

    public void setRelatedItems(String relatedItems) {
        this.relatedItems = relatedItems;
    }

    public String getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
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
