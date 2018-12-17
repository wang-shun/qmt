package com.lesports.qmt.transcode.web.param;

import javax.validation.constraints.NotNull;

/**
 * Created by pangchuanxiao on 2016/10/24.
 */
public class VideoTranscodeParam {
    private Long leecoVid;
    //客户端转码参数
    private String transcodeArgs;
    //码率
    private String formats;
    //转码优先级
    private Integer priority;
    //转码开始时间
    private String transcodeStartTime;
    //转码结束时间
    private String transcodeEndTime;
    //片源尺寸
    private String screenSize;
    //时长
    private int duration;
    //视频图片
    private String images;
    //屏幕比例
    private String screenRatio;
    private String from;//web client
    //视频名称
    private String title;
    //视频短标题
    private String shortTitle;
    //免审理由
    private String reasonOfExReview;
    //屏蔽类型
    private int shieldAreaType;
    //屏蔽国家
    private String shieldCountries;
    //简介
    private String desc;
    //频道
    private long channel;
    private Long subChannel;
    //赛事id
    private long cid;
    //视频类型
    private int videoType;
    //视频期数
    private String period;
    //内容分级
    private int contentRating;
    //媒资版权站点
    private int copyRightSite;
    //是否付费
    private boolean isPay;
    //付费平台
    private String payPlatforms;
    //播放平台
    private String playPlatforms;
    //播放平台
    private String vrsPlayPlatforms;
    //下载平台
    private String downloadPlatforms;
    //专辑id
    private Long aid;
    //上线时间
    private String publishTime;
    //是否推送到国广
    private Boolean toCibn;
    //是否使用字幕
    private Boolean useSub;

    //特殊码流
    private Boolean specialFormat;
    //drm标示
    private Integer drmFlag;
    //是否评论
    private Boolean commentFlag;
    //是否弹幕
    private Boolean barrageFlag;
    //是否搜索
    private Boolean searchFlag;
    //分享描述
    private String shareDesc;
    //是否是全景码流
    private Boolean isOverView;
    //视频转码 加急
    private Boolean isEnhance = true;
    private Boolean autoBlackSide;
    //修正宽高比
    private String modifiedAspectRadio;
    //星级
    private Long starLevel;
    //作为回放的ID
    private Long playbackId = -1L;
    //作为集锦的ID
    private Long highlightId = -1L;
    //作为自制节目的ID
    private Long selfProducedProgramId = -1L;
    //是否推送到首页
    private Boolean recommend2Homepage;
    //相关的实体ID，里面可以有match,team,player,episode,album,
    private String relatedIds;
    //声明
    private Long declarationId;
    //来源
    private String source;
    //作者
    private String videoAuthor;
    //声明
    @NotNull(message = "template is required")
    private String statement;
    @NotNull(message = "template is required")
    private String statement2;
    private String matchIds;
    private String relatedContents;

    public String getVrsPlayPlatforms() {
        return vrsPlayPlatforms;
    }

    public void setVrsPlayPlatforms(String vrsPlayPlatforms) {
        this.vrsPlayPlatforms = vrsPlayPlatforms;
    }

    public Boolean getUseSub() {
        return useSub;
    }

    public void setUseSub(Boolean useSub) {
        this.useSub = useSub;
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

    public Long getSubChannel() {
        return subChannel;
    }

    public void setSubChannel(Long subChannel) {
        this.subChannel = subChannel;
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

    public String getStatement() {
        return statement;
    }

    public void setStatement(String statement) {
        this.statement = statement;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String getStatement2() {
        return statement2;
    }

    public void setStatement2(String statement2) {
        this.statement2 = statement2;
    }

    public Long getDeclarationId() {
        return declarationId;
    }

    public void setDeclarationId(Long declarationId) {
        this.declarationId = declarationId;
    }

    public String getShareDesc() {
        return shareDesc;
    }

    public void setShareDesc(String shareDesc) {
        this.shareDesc = shareDesc;
    }

    public String getRelatedIds() {
        return relatedIds;
    }

    public void setRelatedIds(String relatedIds) {
        this.relatedIds = relatedIds;
    }

    public Long getLeecoVid() {
        return leecoVid;
    }

    public void setLeecoVid(Long leecoVid) {
        this.leecoVid = leecoVid;
    }

    public String getTranscodeArgs() {
        return transcodeArgs;
    }

    public void setTranscodeArgs(String transcodeArgs) {
        this.transcodeArgs = transcodeArgs;
    }

    public String getScreenSize() {
        return screenSize;
    }

    public void setScreenSize(String screenSize) {
        this.screenSize = screenSize;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getScreenRatio() {
        return screenRatio;
    }

    public void setScreenRatio(String screenRatio) {
        this.screenRatio = screenRatio;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
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

    public String getReasonOfExReview() {
        return reasonOfExReview;
    }

    public void setReasonOfExReview(String reasonOfExReview) {
        this.reasonOfExReview = reasonOfExReview;
    }

    public int getShieldAreaType() {
        return shieldAreaType;
    }

    public void setShieldAreaType(int shieldAreaType) {
        this.shieldAreaType = shieldAreaType;
    }

    public String getShieldCountries() {
        return shieldCountries;
    }

    public void setShieldCountries(String shieldCountries) {
        this.shieldCountries = shieldCountries;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public long getChannel() {
        return channel;
    }

    public void setChannel(long channel) {
        this.channel = channel;
    }

    public long getCid() {
        return cid;
    }

    public void setCid(long cid) {
        this.cid = cid;
    }

    public int getVideoType() {
        return videoType;
    }

    public void setVideoType(int videoType) {
        this.videoType = videoType;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public int getContentRating() {
        return contentRating;
    }

    public void setContentRating(int contentRating) {
        this.contentRating = contentRating;
    }

    public int getCopyRightSite() {
        return copyRightSite;
    }

    public void setCopyRightSite(int copyRightSite) {
        this.copyRightSite = copyRightSite;
    }

    public boolean isPay() {
        return isPay;
    }

    public void setIsPay(boolean isPay) {
        this.isPay = isPay;
    }

    public String getPayPlatforms() {
        return payPlatforms;
    }

    public void setPayPlatforms(String payPlatforms) {
        this.payPlatforms = payPlatforms;
    }

    public String getPlayPlatforms() {
        return playPlatforms;
    }

    public void setPlayPlatforms(String playPlatforms) {
        this.playPlatforms = playPlatforms;
    }

    public String getDownloadPlatforms() {
        return downloadPlatforms;
    }

    public void setDownloadPlatforms(String downloadPlatforms) {
        this.downloadPlatforms = downloadPlatforms;
    }

    public Long getAid() {
        return aid;
    }

    public void setAid(Long aid) {
        this.aid = aid;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

    public Boolean getToCibn() {
        return toCibn;
    }

    public void setToCibn(Boolean toCibn) {
        this.toCibn = toCibn;
    }

    public Boolean getRecommend2Homepage() {
        return recommend2Homepage;
    }

    public void setRecommend2Homepage(Boolean recommend2Homepage) {
        this.recommend2Homepage = recommend2Homepage;
    }

    public String getFormats() {
        return formats;
    }

    public void setFormats(String formats) {
        this.formats = formats;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public String getTranscodeStartTime() {
        return transcodeStartTime;
    }

    public void setTranscodeStartTime(String transcodeStartTime) {
        this.transcodeStartTime = transcodeStartTime;
    }

    public String getTranscodeEndTime() {
        return transcodeEndTime;
    }

    public void setTranscodeEndTime(String transcodeEndTime) {
        this.transcodeEndTime = transcodeEndTime;
    }

    public Boolean getSpecialFormat() {
        return specialFormat;
    }

    public void setSpecialFormat(Boolean specialFormat) {
        this.specialFormat = specialFormat;
    }

    public Integer getDrmFlag() {
        return drmFlag;
    }

    public void setDrmFlag(Integer drmFlag) {
        this.drmFlag = drmFlag;
    }

    public Boolean getCommentFlag() {
        return commentFlag;
    }

    public void setCommentFlag(Boolean commentFlag) {
        this.commentFlag = commentFlag;
    }

    public Boolean getBarrageFlag() {
        return barrageFlag;
    }

    public void setBarrageFlag(Boolean barrageFlag) {
        this.barrageFlag = barrageFlag;
    }

    public Boolean getSearchFlag() {
        return searchFlag;
    }

    public void setSearchFlag(Boolean searchFlag) {
        this.searchFlag = searchFlag;
    }

    public Boolean getIsOverView() {
        return isOverView;
    }

    public void setIsOverView(Boolean isOverView) {
        this.isOverView = isOverView;
    }

    public Boolean getIsEnhance() {
        return isEnhance;
    }

    public void setIsEnhance(Boolean isEnhance) {
        this.isEnhance = isEnhance;
    }

    public Boolean getAutoBlackSide() {
        return autoBlackSide;
    }

    public void setAutoBlackSide(Boolean autoBlackSide) {
        this.autoBlackSide = autoBlackSide;
    }

    public String getModifiedAspectRadio() {
        return modifiedAspectRadio;
    }

    public void setModifiedAspectRadio(String modifiedAspectRadio) {
        this.modifiedAspectRadio = modifiedAspectRadio;
    }

    public Long getStarLevel() {
        return starLevel;
    }

    public void setStarLevel(Long starLevel) {
        this.starLevel = starLevel;
    }

    public Long getPlaybackId() {
        return playbackId;
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

    public Long getSelfProducedProgramId() {
        return selfProducedProgramId;
    }

    public void setSelfProducedProgramId(Long selfProducedProgramId) {
        this.selfProducedProgramId = selfProducedProgramId;
    }
}
