package com.lesports.qmt.sbc.param;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * Created by zhangxudong@le.com on 2016/10/21.
 */
public class ReuploadVideoParam {

    @NotBlank(message = "ids is required")
    private String ids;
    private String nameCn;
    @NotNull(message = "userId is required")
    private Long userId;
    @NotNull(message = "uploadId is required")
    private Long uploadId;
    private String transcodeBeginTime;
    private String transcodeEndTime;
    private Integer specialBitstream;
    private Integer modifiedAspectRadio;
    private String tag;
    private String description;
    private Long gameFType;
    private Long cid;
    private String videoAlbumRelations;
    private String episode;
    private String playPlatforms;
    @NotNull(message = "isPay is required")
    private Boolean isPay;
    private String payPlatforms;
    private Integer contentRating;
    private Integer disableType;
    private String country;
    @NotNull(message = "commentFlag is required")
    private Boolean commentFlag;
    @NotNull(message = "barrageFlag is required")
    private Boolean barrageFlag;
    @NotNull(message = "searchFlag is required")
    private Boolean searchFlag;
    private String blackSide;
    private String coverMarks;

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public String getNameCn() {
        return nameCn;
    }

    public void setNameCn(String nameCn) {
        this.nameCn = nameCn;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getUploadId() {
        return uploadId;
    }

    public void setUploadId(Long uploadId) {
        this.uploadId = uploadId;
    }

    public String getTranscodeBeginTime() {
        return transcodeBeginTime;
    }

    public void setTranscodeBeginTime(String transcodeBeginTime) {
        this.transcodeBeginTime = transcodeBeginTime;
    }

    public String getTranscodeEndTime() {
        return transcodeEndTime;
    }

    public void setTranscodeEndTime(String transcodeEndTime) {
        this.transcodeEndTime = transcodeEndTime;
    }

    public Integer getSpecialBitstream() {
        return specialBitstream;
    }

    public void setSpecialBitstream(Integer specialBitstream) {
        this.specialBitstream = specialBitstream;
    }

    public Integer getModifiedAspectRadio() {
        return modifiedAspectRadio;
    }

    public void setModifiedAspectRadio(Integer modifiedAspectRadio) {
        this.modifiedAspectRadio = modifiedAspectRadio;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getGameFType() {
        return gameFType;
    }

    public void setGameFType(Long gameFType) {
        this.gameFType = gameFType;
    }

    public Long getCid() {
        return cid;
    }

    public void setCid(Long cid) {
        this.cid = cid;
    }

    public String getVideoAlbumRelations() {
        return videoAlbumRelations;
    }

    public void setVideoAlbumRelations(String videoAlbumRelations) {
        this.videoAlbumRelations = videoAlbumRelations;
    }

    public String getEpisode() {
        return episode;
    }

    public void setEpisode(String episode) {
        this.episode = episode;
    }

    public String getPlayPlatforms() {
        return playPlatforms;
    }

    public void setPlayPlatforms(String playPlatforms) {
        this.playPlatforms = playPlatforms;
    }

    public Boolean getPay() {
        return isPay;
    }

    public void setPay(Boolean pay) {
        isPay = pay;
    }

    public String getPayPlatforms() {
        return payPlatforms;
    }

    public void setPayPlatforms(String payPlatforms) {
        this.payPlatforms = payPlatforms;
    }

    public Integer getContentRating() {
        return contentRating;
    }

    public void setContentRating(Integer contentRating) {
        this.contentRating = contentRating;
    }

    public Integer getDisableType() {
        return disableType;
    }

    public void setDisableType(Integer disableType) {
        this.disableType = disableType;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
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

    public String getBlackSide() {
        return blackSide;
    }

    public void setBlackSide(String blackSide) {
        this.blackSide = blackSide;
    }

    public String getCoverMarks() {
        return coverMarks;
    }

    public void setCoverMarks(String coverMarks) {
        this.coverMarks = coverMarks;
    }
}
