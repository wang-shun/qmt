package com.lesports.qmt.sbc.param;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotBlank;

/**
 * Created by zhangxudong@le.com on 2016/10/21.
 */
public class AlbumParam {

    private Long id;
    @NotBlank(message = "title is required")
    private String title; //视频名称
    private String relatedIds;
    private String matchIds; //赛事id
    private String desc;
    private Long channel; //频道
    private Long subChannel; //子频道
    private Long cid; //赛事id
    @JsonProperty("baiduCollected")
    private Boolean baiduCollected = true;
    @JsonProperty("selfProducedProgram")
    private Boolean selfProducedProgram = false;
    private Integer contentRating; //内容分级
    @JsonProperty("isPay")
    private Boolean isPay = false;
    private String payPlatforms;
    private String reasonOfExReview;
    //视频图片
    private String images;

    public String getMatchIds() {
        return matchIds;
    }

    public void setMatchIds(String matchIds) {
        this.matchIds = matchIds;
    }

    public String getRelatedIds() {
        return relatedIds;
    }

    public void setRelatedIds(String relatedIds) {
        this.relatedIds = relatedIds;
    }

    public Boolean getBaiduCollected() {
        return baiduCollected;
    }

    public void setBaiduCollected(Boolean baiduCollected) {
        this.baiduCollected = baiduCollected;
    }

    public Boolean getSelfProducedProgram() {
        return selfProducedProgram;
    }

    public void setSelfProducedProgram(Boolean selfProducedProgram) {
        this.selfProducedProgram = selfProducedProgram;
    }

    public Boolean getPay() {
        return isPay;
    }

    public void setPay(Boolean pay) {
        isPay = pay;
    }

    public boolean getIsPay() {
        return this.isPay;
    }
    public void setIsPay(boolean isPay) {
        this.isPay = isPay;
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

    public Long getChannel() {
        return channel;
    }

    public void setChannel(Long channel) {
        this.channel = channel;
    }

    public Long getSubChannel() {
        return subChannel;
    }

    public void setSubChannel(Long subChannel) {
        this.subChannel = subChannel;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Long getCid() {
        return cid;
    }

    public void setCid(Long cid) {
        this.cid = cid;
    }

    public String getPayPlatforms() {
        return payPlatforms;
    }

    public void setPayPlatforms(String payPlatforms) {
        this.payPlatforms = payPlatforms;
    }

    public String getReasonOfExReview() {
        return reasonOfExReview;
    }

    public void setReasonOfExReview(String reasonOfExReview) {
        this.reasonOfExReview = reasonOfExReview;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public Integer getContentRating() {
        return contentRating;
    }

    public void setContentRating(Integer contentRating) {
        this.contentRating = contentRating;
    }
}
