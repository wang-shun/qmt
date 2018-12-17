package com.lesports.qmt.sbc.param;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by zhangxudong@le.com on 2016/10/21.
 */
public class VideoParam {

    private Long id;
//    //生态vid,也就是vrs的视频id
//    private Long leecoVid;
//    //生态专辑id,也就是vrs的专辑id
//    private Long leecoAid;
//    //介质id
//    private Long vmid;
    //视频名称
    @NotBlank(message = "short name is required")
    @Size(max = 60)
    private String title;
    //视频名称
    @NotBlank(message = "short name is required")
    @Size(max = 60)
    private String shortTitle;
    //视频描述
    @NotBlank(message = "short name is required")
    @Size(max = 180)
    private String desc;
    //视频描述
    @NotBlank(message = "short name is required")
    @Size(max = 180)
    private String shareDesc;
    //频道
    @NotNull(message = "template is required")
    private Long channel;
    private Long subChannel;
    //赛事id
    @NotNull(message = "template is required")
    private Long cid;
    //视频内容类型
    @NotNull(message = "template is required")
    private Integer videoType;

    //特殊码流//该视频是否是drm视频: 0:不是
    private Integer drmFlag = 0;
    @JsonProperty("isOverView")
    private Boolean isOverView;
    //修正宽高比
    private String modifiedAspectRadio;
    //是否手动配置过包ID
    @NotNull(message = "template is required")
    private Integer publishSetting = 0;
    //播放平台
//    @NotNull(message = "template is required")
//    private String publishPlatforms;
    //屏蔽国家类型
    @NotNull(message = "template is required")
    private Integer shieldAreaType;
    //允许展示的国家
    private String shieldCountries;
    //是否在推荐展示到首页
    @NotNull(message = "template is required")
    @JsonProperty("recommend2Homepage")
    private Boolean recommend2Homepage;
    //专辑id
    private Long aid;
    //相关的实体ID，里面可以有match,team,player,episode,album,
    private String relatedIds;
    //是否删除
    @JsonProperty("deleted")
    private Boolean deleted = false;
    //播放平台
    @NotNull(message = "template is required")
    private String playPlatforms;
    //播放平台
    @NotNull(message = "template is required")
    private String vrsPlayPlatforms;
    //视频图片/图集图片JSON
    private String images;
    //是否是克隆视频
    @JsonProperty("clone")
    private Boolean clone;
    //被克隆视频的ID
    private Long cloneId;
    //克隆到专辑的专辑id
    @JsonProperty("cloneAids")
    private String cloneAids;
    //时长
    private Integer duration;
    //期数
    private String period = "";
    //内容分级
    @NotNull(message = "template is required")
    private Integer contentRating;
    //是否支付
    @NotNull(message = "template is required")
    @JsonProperty("isPay")
    private Boolean isPay;
    //支付平台
//    @NotNull(message = "template is required")
    private String payPlatforms;
    //上线时间
//    @NotNull(message = "template is required")
    private String publishTime;
    //站点标示
    private String markCountry;
    //免审理由
    private String reasonOfExReview;
    //星级
//    @NotNull(message = "template is required")
    private Long starLevel;
    //来源
//    @NotNull(message = "template is required")
    private String source;
    //作者
    private String videoAuthor = "";
    //声明
    @NotNull(message = "template is required")
    private String statement;
    @NotNull(message = "template is required")
    private String statement2;
    //评论
    @NotNull(message = "template is required")
    @JsonProperty("commentFlag")
    private boolean commentFlag = true;
    //下载平台
    private String downloadPlatforms;
    //作为回放的ID
    private Long playbackId = -1L;
    //作为集锦的ID
    private Long highlightId = -1L;
    //作为自制节目的ID
    private Long selfProducedProgramId = -1L;
    private String matchIds;
    private String relatedContents;
    private String allImages;

    public String getAllImages() {
        return allImages;
    }

    public void setAllImages(String allImages) {
        this.allImages = allImages;
    }

    public String getVrsPlayPlatforms() {
        return vrsPlayPlatforms;
    }

    public void setVrsPlayPlatforms(String vrsPlayPlatforms) {
        this.vrsPlayPlatforms = vrsPlayPlatforms;
    }

    public String getRelatedContents() {
        return relatedContents;
    }

    public void setRelatedContents(String relatedContents) {
        this.relatedContents = relatedContents;
    }

    public String getMatchIds() {
        return matchIds;
    }

    public void setMatchIds(String matchIds) {
        this.matchIds = matchIds;
    }

    public Long getPlaybackId() {
        return playbackId;
    }

    public String getCloneAids() {
        return cloneAids;
    }

    public void setCloneAids(String cloneAids) {
        this.cloneAids = cloneAids;
    }

    public void setPlaybackId(Long playbackId) {
        this.playbackId = playbackId;
    }

    public Long getHighlightId() {
        return highlightId;
    }

    public void setHighlightId(Long highlightId) {
        this.highlightId = highlightId;
    }

    public Long getSubChannel() {
        return subChannel;
    }

    public void setSubChannel(Long subChannel) {
        this.subChannel = subChannel;
    }

    public Long getSelfProducedProgramId() {
        return selfProducedProgramId;
    }

    public void setSelfProducedProgramId(Long selfProducedProgramId) {
        this.selfProducedProgramId = selfProducedProgramId;
    }

    public String getStatement() {
        return statement;
    }

    public Integer getDrmFlag() {
        return drmFlag;
    }

    public void setDrmFlag(Integer drmFlag) {
        this.drmFlag = drmFlag;
    }

    public Boolean getIsOverView() {
        return isOverView;
    }

    public void setIsOverView(Boolean isOverView) {
        this.isOverView = isOverView;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public void setStatement(String statement) {
        this.statement = statement;
    }

    public String getStatement2() {
        return statement2;
    }

    public void setStatement2(String statement2) {
        this.statement2 = statement2;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getShortTitle() {
        return shortTitle;
    }

    public void setShortTitle(String shortTitle) {
        this.shortTitle = shortTitle;
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

    public Long getChannel() {
        return channel;
    }

    public void setChannel(Long channel) {
        this.channel = channel;
    }

    public Long getCid() {
        return cid;
    }

    public void setCid(Long cid) {
        this.cid = cid;
    }

    public Integer getVideoType() {
        return videoType;
    }

    public void setVideoType(Integer videoType) {
        this.videoType = videoType;
    }


    public String getModifiedAspectRadio() {
        return modifiedAspectRadio;
    }

    public void setModifiedAspectRadio(String modifiedAspectRadio) {
        this.modifiedAspectRadio = modifiedAspectRadio;
    }

    public Integer getPublishSetting() {
        return publishSetting;
    }

    public void setPublishSetting(Integer publishSetting) {
        this.publishSetting = publishSetting;
    }

//    public String getPublishPlatforms() {
//        return publishPlatforms;
//    }
//
//    public void setPublishPlatforms(String publishPlatforms) {
//        this.publishPlatforms = publishPlatforms;
//    }

    public Integer getShieldAreaType() {
        return shieldAreaType;
    }

    public void setShieldAreaType(Integer shieldAreaType) {
        this.shieldAreaType = shieldAreaType;
    }

    public void setIsOverView(boolean isOverView) {
        this.isOverView = isOverView;
    }

    public String getShieldCountries() {
        return shieldCountries;
    }

    public void setShieldCountries(String shieldCountries) {
        this.shieldCountries = shieldCountries;
    }

    public Boolean getIsPay() {
        return isPay;
    }

    public void setIsPay(Boolean isPay) {
        this.isPay = isPay;
    }

    public Boolean getRecommend2Homepage() {
        return recommend2Homepage;
    }

    public void setRecommend2Homepage(Boolean recommend2Homepage) {
        this.recommend2Homepage = recommend2Homepage;
    }


    public Long getAid() {
        return aid;
    }

    public void setAid(Long aid) {
        this.aid = aid;
    }

    public String getRelatedIds() {
        return relatedIds;
    }

    public void setRelatedIds(String relatedIds) {
        this.relatedIds = relatedIds;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public String getPlayPlatforms() {
        return playPlatforms;
    }

    public void setPlayPlatforms(String playPlatforms) {
        this.playPlatforms = playPlatforms;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public Boolean getClone() {
        return clone;
    }

    public void setClone(Boolean clone) {
        this.clone = clone;
    }

    public Long getCloneId() {
        return cloneId;
    }

    public void setCloneId(Long cloneId) {
        this.cloneId = cloneId;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public Integer getContentRating() {
        return contentRating;
    }

    public void setContentRating(Integer contentRating) {
        this.contentRating = contentRating;
    }

    public String getPayPlatforms() {
        return payPlatforms;
    }

    public void setPayPlatforms(String payPlatforms) {
        this.payPlatforms = payPlatforms;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

    public String getMarkCountry() {
        return markCountry;
    }

    public void setMarkCountry(String markCountry) {
        this.markCountry = markCountry;
    }

    public String getReasonOfExReview() {
        return reasonOfExReview;
    }

    public void setReasonOfExReview(String reasonOfExReview) {
        this.reasonOfExReview = reasonOfExReview;
    }

    public String getVideoAuthor() {
        return videoAuthor;
    }

    public void setVideoAuthor(String videoAuthor) {
        this.videoAuthor = videoAuthor;
    }

    public boolean isCommentFlag() {
        return commentFlag;
    }

    public void setCommentFlag(boolean comment_flag) {
        this.commentFlag = comment_flag;
    }

    public String getDownloadPlatforms() {
        return downloadPlatforms;
    }

    public void setDownloadPlatforms(String downloadPlatforms) {
        this.downloadPlatforms = downloadPlatforms;
    }

    public Long getStarLevel() {
        return starLevel;
    }

    public void setStarLevel(Long starLevel) {
        this.starLevel = starLevel;
    }
}
