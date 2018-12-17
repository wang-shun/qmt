package com.lesports.qmt.sbc.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.Sets;
import com.lesports.api.common.ImageUrlExt;
import com.lesports.api.common.Platform;
import com.lesports.qmt.model.support.QmtModel;
import com.lesports.qmt.sbc.api.common.ContentRating;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Map;
import java.util.Set;

/**
 * User: zhangxudong@le.com
 * Time: 16-9-10 : 下午6:13
 */
@Document(collection = "albums")
public class Album extends QmtModel<Long> {
    private static final long serialVersionUID = -7473613024269009258L;

    //生态专辑id,也就是vrs的专辑id
    @Field("leeco_aid")
    private Long leecoAid;
    //专辑名称
    private String title;
    //标签id
    @Field("related_ids")
    private Set<Long> relatedIds = Sets.newHashSet();
    //赛事id
    @Field("match_ids")
    private Set<Long> matchIds = Sets.newHashSet();
    //视频描述
    private String desc;
//    //大项/项目
//    @Field("game_f_type")
//    private Long gameFType;
    private Long channel; //频道
    @Field("sub_channel")
    private Long subChannel; //子频道
    //赛事id
    private Long cid;
    //百度收录
    @Field("baidu_collected")
    private Boolean baiduCollected = true;
    //是否是自制节目
    @Field("self_produced_program")
    private Boolean selfProducedProgram = false;
    //内容分级
    @Field("content_rating")
    private ContentRating contentRating;
    //是否支付
    @Field("is_pay")
    @JsonProperty("isPay")
    private Boolean isPay;
    //支付平台
    @Field("pay_platforms")
    private Set<Platform> payPlatforms = Sets.newHashSet();
    //视频图片
    private Map<String, ImageUrlExt> images;
    //免审理由
    @Field("reason_of_ex_review")
    private String reasonOfExReview;
    //是否删除
    private Boolean deleted = false;

    public Set<Long> getMatchIds() {
        return matchIds;
    }

    public Map<String, ImageUrlExt> getImages() {
        return images;
    }

    public void setImages(Map<String, ImageUrlExt> images) {
        this.images = images;
    }

    public void setMatchIds(Set<Long> matchIds) {
        this.matchIds = matchIds;
    }

    public Set<Long> getRelatedIds() {
        return relatedIds;
    }

    public void setRelatedIds(Set<Long> relatedIds) {
        this.relatedIds = relatedIds;
    }

    public Boolean getPay() {
        return isPay;
    }

    public void setPay(Boolean pay) {
        isPay = pay;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Long getLeecoAid() {
        return leecoAid;
    }

    public void setLeecoAid(Long leecoAid) {
        this.leecoAid = leecoAid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
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

    public Long getCid() {
        return cid;
    }

    public void setCid(Long cid) {
        this.cid = cid;
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

    public String getReasonOfExReview() {
        return reasonOfExReview;
    }

    public void setReasonOfExReview(String reasonOfExReview) {
        this.reasonOfExReview = reasonOfExReview;
    }
}