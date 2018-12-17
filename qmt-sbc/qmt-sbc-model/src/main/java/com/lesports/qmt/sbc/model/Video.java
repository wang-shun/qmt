package com.lesports.qmt.sbc.model;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.lesports.api.common.ImageUrlExt;
import com.lesports.api.common.LanguageCode;
import com.lesports.api.common.Platform;
import com.lesports.qmt.model.support.QmtModel;
import com.lesports.qmt.sbc.api.common.ContentRating;
import com.lesports.qmt.sbc.api.common.TvLicence;
import com.lesports.qmt.sbc.api.common.VideoContentType;
import com.lesports.qmt.sbc.api.common.VideoOnlineStatus;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * User: ellios
 * Time: 16-9-10 : 下午6:13
 */
@Document(collection = "videos")
public class Video extends QmtModel<Long> {
    private static final long serialVersionUID = -7473613024269009258L;

    //生态vid,也就是vrs的视频id
    @Field("leeco_vid")
    private Long leecoVid;
    @Field("leeco_mid")
    private Long leecoMid;
    //介质id
    private Long vmid;
    //视频名称
    private String title;
    //视频名称
    @Field("short_title")
    private String shortTitle;
    //视频描述
    private String desc;
    //视频描述
    @Field("share_desc")
    private String shareDesc;
    //频道
    private Long channel;
    //综合体育频道的子频道
    @Field("sub_channel")
    private Long subChannel;
    //赛事id
    private Long cid;
    //视频内容类型
    @Field("video_type")
    private VideoContentType videoType;
    //该视频是否是drm视频: 0:不是
    @Field("drm_flag")
    private Integer drmFlag = 0;
    //是否全景
    @Field("is_overview")
    private Boolean isOverView;
    //是否手动配置过包ID
    @Field("publish_setting")
    private Integer publishSetting = 0;
    //屏蔽国家类型 全部允许 -> 1; 部分允许 -> 2; 部分屏蔽 -> 3;
    @Field("shield_area_type")
    private Integer shieldAreaType;
    //屏蔽展示的国家
    @Field("shield_countries")
    private Set<String> shieldCountries = Sets.newHashSet();
    //是否在推荐展示到首页
    @Field("recommend_2_homepage")
    private Boolean recommend2Homepage;
    //关联到专辑id
    private Long aid = 0L;
    @Transient
    private String albumName;
    //克隆到专辑id
    @Field("clone_aids")
    private List<Long> cloneAids = Lists.newArrayList();
    //是否是克隆视频
    @Field("is_clone")
    private Boolean isClone = false;
    //被克隆视频的ID
    @Field("main_id")
    private Long mainId = -1L;
    //相关的实体ID，里面可以有match,team,player,episode,
    @Field("related_ids")
    private Set<Long> relatedIds = Sets.newHashSet();
    //相关的内容，里面可以有news topic
    @Field("related_contents")
    private List<Long> relatedContents;
    @Field("match_ids")
    private Set<Long> matchIds = Sets.newHashSet();
    //是否删除
    private Boolean deleted = false;
    //播放平台
    @Field("play_platforms")
    private Set<Platform> playPlatforms = Sets.newHashSet(); //platfrom
    //大乐视播放平台
    @Field("vrs_play_platforms")
    private Set<Platform> vrsPlayPlatforms = Sets.newHashSet(); //platfrom
    //视频图片
    private Map<String, ImageUrlExt> images;
    //转码图片
    @Field("all_images")
    private List<String> allImages;
    //作为回放的ID，从live上取
    @Transient
    private Long recordId = -1L;
    //作为集锦的ID
    @Transient
    private Long highlightId = -1L;
    //关联选集
    @Transient
    private Long selfProducedProgramId = -1L;
    //时长
    private Integer duration;
    @Field("language_code")
    private LanguageCode languageCode;
    //期数
    private String period = "";
    //内容分级
    @Field("content_rating")
    private ContentRating contentRating;
    //是否付费，媒资付费视频在boss修改，第一期不做改变
    @Field("is_pay")
    private Boolean isPay;
    //支付平台
    @Field("pay_platforms")
    private Set<Platform> payPlatforms = Sets.newHashSet();
    //上线时间
    @Field("publish_time")
    private String publishTime;
    //免审理由
    @Field("reason_of_ex_review")
    private String reasonOfExReview;
    //支持的TV牌照方
    @Field("support_licences")
    private Set<TvLicence> supportLicences = Sets.newHashSet();
    //星级
    @Field("star_level")
    private Long starLevel;
    //来源
    @Field("source")
    private String source;
    //作者
    @Field("video_author")
    private String videoAuthor = "";
    //声明
    private List<String> statements = Lists.newArrayList();
    //评论
    @Field("comment_flag")
    private Boolean commentFlag = true;
    //下载平台
    @Field("download_platforms")
    private Set<Platform> downloadPlatforms = Sets.newHashSet(Platform.MOBILE, Platform.MSITE, Platform.PAD, Platform.PC, Platform.TV);
    //程序写入
    @Field("online_status")
    private VideoOnlineStatus onlineStatus;
    //是否有大图
    @Field("has_big_image")
    private Boolean hasBigImage;

    public List<String> getAllImages() {
        return allImages;
    }

    public void setAllImages(List<String> allImages) {
        this.allImages = allImages;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public Set<Platform> getVrsPlayPlatforms() {
        return vrsPlayPlatforms;
    }

    public void setVrsPlayPlatforms(Set<Platform> vrsPlayPlatforms) {
        this.vrsPlayPlatforms = vrsPlayPlatforms;
    }

    public Set<TvLicence> getSupportLicences() {
        return supportLicences;
    }

    public void setSupportLicences(Set<TvLicence> supportLicences) {
        this.supportLicences = supportLicences;
    }

    public VideoOnlineStatus getOnlineStatus() {
        return onlineStatus;
    }

    public void setOnlineStatus(VideoOnlineStatus onlineStatus) {
        this.onlineStatus = onlineStatus;
    }

    public List<Long> getRelatedContents() {
        return relatedContents;
    }

    public void setRelatedContents(List<Long> relatedContents) {
        this.relatedContents = relatedContents;
    }

    public Set<Long> getMatchIds() {
        return matchIds;
    }

    public void setMatchIds(Set<Long> matchIds) {
        this.matchIds = matchIds;
    }

    public Long getSubChannel() {
        return subChannel;
    }

    public void setSubChannel(Long subChannel) {
        this.subChannel = subChannel;
    }

    public Long getLeecoVid() {
        return leecoVid;
    }

    public void setLeecoVid(Long leecoVid) {
        this.leecoVid = leecoVid;
    }

    public Long getLeecoMid() {
        return leecoMid;
    }

    public void setLeecoMid(Long leecoMid) {
        this.leecoMid = leecoMid;
    }

    public Long getVmid() {
        return vmid;
    }

    public void setVmid(Long vmid) {
        this.vmid = vmid;
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

    public VideoContentType getVideoType() {
        return videoType;
    }

    public void setVideoType(VideoContentType videoType) {
        this.videoType = videoType;
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

    public Integer getPublishSetting() {
        return publishSetting;
    }

    public void setPublishSetting(Integer publishSetting) {
        this.publishSetting = publishSetting;
    }

    public Integer getShieldAreaType() {
        return shieldAreaType;
    }

    public void setShieldAreaType(Integer shieldAreaType) {
        this.shieldAreaType = shieldAreaType;
    }

    public Set<String> getShieldCountries() {
        return shieldCountries;
    }

    public void setShieldCountries(Set<String> shieldCountries) {
        this.shieldCountries = shieldCountries;
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

    public List<Long> getCloneAids() {
        return cloneAids;
    }

    public void setCloneAids(List<Long> cloneAids) {
        this.cloneAids = cloneAids;
    }

    public Boolean getIsClone() {
        return isClone;
    }

    public void setIsClone(Boolean isClone) {
        this.isClone = isClone;
    }

    public Long getMainId() {
        return mainId;
    }

    public void setMainId(Long mainId) {
        this.mainId = mainId;
    }

    public Set<Long> getRelatedIds() {
        return relatedIds;
    }

    public void setRelatedIds(Set<Long> relatedIds) {
        this.relatedIds = relatedIds;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Set<Platform> getPlayPlatforms() {
        return playPlatforms;
    }

    public void setPlayPlatforms(Set<Platform> playPlatforms) {
        this.playPlatforms = playPlatforms;
    }

    public Map<String, ImageUrlExt> getImages() {
        return images;
    }

    public void setImages(Map<String, ImageUrlExt> images) {
        this.images = images;
    }

    public Long getRecordId() {
        return recordId;
    }

    public void setRecordId(Long recordId) {
        this.recordId = recordId;
    }

    public Long getHighlightId() {
        return highlightId;
    }

    public void setHighlightId(Long highlightId) {
        this.highlightId = highlightId;
    }

    public Long getSelfProducedProgramId() {
        return selfProducedProgramId;
    }

    public void setSelfProducedProgramId(Long selfProducedProgramId) {
        this.selfProducedProgramId = selfProducedProgramId;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public LanguageCode getLanguageCode() {
        return languageCode;
    }

    public void setLanguageCode(LanguageCode languageCode) {
        this.languageCode = languageCode;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public ContentRating getContentRating() {
        return contentRating;
    }

    public void setContentRating(ContentRating contentRating) {
        this.contentRating = contentRating;
    }

    public Boolean getIsPay() {
        return isPay;
    }

    public void setIsPay(Boolean isPay) {
        this.isPay = isPay;
    }

    public Set<Platform> getPayPlatforms() {
        return payPlatforms;
    }

    public void setPayPlatforms(Set<Platform> payPlatforms) {
        this.payPlatforms = payPlatforms;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

    public String getReasonOfExReview() {
        return reasonOfExReview;
    }

    public void setReasonOfExReview(String reasonOfExReview) {
        this.reasonOfExReview = reasonOfExReview;
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

    public String getVideoAuthor() {
        return videoAuthor;
    }

    public void setVideoAuthor(String videoAuthor) {
        this.videoAuthor = videoAuthor;
    }

    public List<String> getStatements() {
        return statements;
    }

    public void setStatements(List<String> statements) {
        this.statements = statements;
    }

    public Boolean getCommentFlag() {
        return commentFlag;
    }

    public void setCommentFlag(Boolean commentFlag) {
        this.commentFlag = commentFlag;
    }

    public Set<Platform> getDownloadPlatforms() {
        return downloadPlatforms;
    }

    public void setDownloadPlatforms(Set<Platform> downloadPlatforms) {
        this.downloadPlatforms = downloadPlatforms;
    }

    public Boolean getHasBigImage() {
        return hasBigImage;
    }

    public void setHasBigImage(Boolean hasBigImage) {
        this.hasBigImage = hasBigImage;
    }
}