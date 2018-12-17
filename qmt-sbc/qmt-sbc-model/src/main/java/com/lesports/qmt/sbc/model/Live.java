package com.lesports.qmt.sbc.model;

import com.google.common.collect.Maps;
import com.lesports.api.common.CountryCode;
import com.lesports.api.common.ImageUrlExt;
import com.lesports.api.common.LiveStatus;
import com.lesports.qmt.model.support.QmtModel;
import com.lesports.qmt.sbc.api.common.ShieldType;
import com.lesports.qmt.sbc.api.dto.StreamSourceType;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * trunk.
 *
 * @author pangchuanxiao
 * @since 2016/10/18
 */
@Document(collection = "lives")
public class Live extends QmtModel<Long> {
    private static final long serialVersionUID = -2000729691801075518L;

    //节目id
    @Field("eid")
    private Long eid;
    //直播开始时间 yyyyMMddHHmmss
    @Field("start_time")
    private String startTime;
    //直播结束时间 yyyyMMddHHmmss
    @Field("end_time")
    private String endTime;
    @Field("stream_source_type")
    private StreamSourceType streamSourceType;//直播流来源  0是乐视网  1是第三方   当前体育就这两个来源
    @Field("third_live_page_url")
    private String thirdLivePageUrl;//第三方的直播地址  如果直播流来源选的是第三方，则该字段必须有值
    @Field("channel_id")
    private Long channelId; //直播频道id
    @Field("cibn_channel_id")
    private Long cibnChannelId;//国广监控频道id
    private Integer specialLive;//特殊直播   对应大乐视那边直播后台的 分支类型    1是多视角 2是多解说  0是无
    @Field("is_drm")
    private Boolean isDrm;//是否是版权保护
    @Field("commentary_language")
    private String commentaryLanguage;//直播解说语言   相当于直播的title吧  譬如 詹俊解说/粤语解说
    //关联专题链接
    private Integer weight;//直播权重
    @Field("is_pay")
    private Boolean isPay;//是否收费 0是否  1是收费
    @Field("pay_begin_time")
    private String payBeginTime;//付费开始时间
    //付费客户端平台
    @Field("pay_platforms")
    private Set<Long> payPlatforms;
    @Field("is_manual")
    private Integer isManual;//是否是手动选择
    @Field("copyright_id")
    private Long copyrightId;//版权包id，如果是手动选择，该id则没有，或者有也没有用。
    //发布客户端平台
    @Field("play_platforms")
    private Set<Long> playPlatforms;
    @Field("shield_type")
    private ShieldType shieldType;//屏蔽类型  0全部允许，1部分允许，2部分屏蔽
    @Field("shield_region")
    private List<Long> shieldRegion;//屏蔽地区，如果屏蔽类型是1，那这个地区是允许播放的地区，如果是2则是屏蔽的地区，0的话该字段无效
    //乐视云地域屏蔽id
    @Field("shield_row_id")
    private Long shieldRowId;
    @Field("belong_area")
    private CountryCode belongArea;//直播所属地区 100:中国大陆 101:中国香港:102 美国
    //直播状态
    private LiveStatus status;
    @Field("op_status")
    private LiveStatus opStatus;
    @Field("cover_image")
    private ImageUrlExt coverImage;
    //关联id
    @Field("related_ids")
    private List<Long> relatedIds;
    //是否删除
    private Boolean deleted;
    //回放视频id
    @Field("record_id")
    private Long recordId;

    public Long getRecordId() {
        return recordId;
    }

    public void setRecordId(Long recordId) {
        this.recordId = recordId;
    }

    public LiveStatus getOpStatus() {
        return opStatus;
    }

    public void setOpStatus(LiveStatus opStatus) {
        this.opStatus = opStatus;
    }

    public Long getShieldRowId() {
        return shieldRowId;
    }

    public void setShieldRowId(Long shieldRowId) {
        this.shieldRowId = shieldRowId;
    }

    public Long getEid() {
        return eid;
    }

    public void setEid(Long eid) {
        this.eid = eid;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public StreamSourceType getStreamSourceType() {
        return streamSourceType;
    }

    public void setStreamSourceType(StreamSourceType streamSourceType) {
        this.streamSourceType = streamSourceType;
    }

    public String getThirdLivePageUrl() {
        return thirdLivePageUrl;
    }

    public void setThirdLivePageUrl(String thirdLivePageUrl) {
        this.thirdLivePageUrl = thirdLivePageUrl;
    }

    public Long getChannelId() {
        return channelId;
    }

    public void setChannelId(Long channelId) {
        this.channelId = channelId;
    }

    public Long getCibnChannelId() {
        return cibnChannelId;
    }

    public void setCibnChannelId(Long cibnChannelId) {
        this.cibnChannelId = cibnChannelId;
    }

    public Integer getSpecialLive() {
        return specialLive;
    }

    public void setSpecialLive(Integer specialLive) {
        this.specialLive = specialLive;
    }

    public Boolean getIsDrm() {
        return isDrm;
    }

    public void setIsDrm(Boolean isDrm) {
        this.isDrm = isDrm;
    }

    public String getCommentaryLanguage() {
        return commentaryLanguage;
    }

    public void setCommentaryLanguage(String commentaryLanguage) {
        this.commentaryLanguage = commentaryLanguage;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Boolean getIsPay() {
        return isPay;
    }

    public void setIsPay(Boolean isPay) {
        this.isPay = isPay;
    }

    public String getPayBeginTime() {
        return payBeginTime;
    }

    public void setPayBeginTime(String payBeginTime) {
        this.payBeginTime = payBeginTime;
    }

    public Set<Long> getPayPlatforms() {
        return payPlatforms;
    }

    public void setPayPlatforms(Set<Long> payPlatforms) {
        this.payPlatforms = payPlatforms;
    }

    public Integer getIsManual() {
        return isManual;
    }

    public void setIsManual(Integer isManual) {
        this.isManual = isManual;
    }

    public Long getCopyrightId() {
        return copyrightId;
    }

    public void setCopyrightId(Long copyrightId) {
        this.copyrightId = copyrightId;
    }

    public Set<Long> getPlayPlatforms() {
        return playPlatforms;
    }

    public void setPlayPlatforms(Set<Long> playPlatforms) {
        this.playPlatforms = playPlatforms;
    }

    public ShieldType getShieldType() {
        return shieldType;
    }

    public void setShieldType(ShieldType shieldType) {
        this.shieldType = shieldType;
    }

    public List<Long> getShieldRegion() {
        return shieldRegion;
    }

    public void setShieldRegion(List<Long> shieldRegion) {
        this.shieldRegion = shieldRegion;
    }

    public CountryCode getBelongArea() {
        return belongArea;
    }

    public void setBelongArea(CountryCode belongArea) {
        this.belongArea = belongArea;
    }

    public LiveStatus getStatus() {
        return status;
    }

    public void setStatus(LiveStatus status) {
        this.status = status;
    }

    public ImageUrlExt getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(ImageUrlExt coverImage) {
        this.coverImage = coverImage;
    }

    public List<Long> getRelatedIds() {
        return relatedIds;
    }

    public void setRelatedIds(List<Long> relatedIds) {
        this.relatedIds = relatedIds;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }
}
