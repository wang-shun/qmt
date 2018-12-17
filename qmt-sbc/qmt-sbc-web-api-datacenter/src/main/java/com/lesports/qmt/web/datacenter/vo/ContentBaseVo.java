package com.lesports.qmt.web.datacenter.vo;

import com.google.common.collect.Maps;
import com.lesports.qmt.sbc.api.dto.ResourceItemType;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;
import java.util.Map;

/**
 * 一些公共的字段以及资源位内容里面会有的字段
 * create by wangjichuan  date:16-12-16 time:17:31
 */
public class ContentBaseVo {
    private String id;
    private ResourceItemType type;
    private String name;
    private Map<String,String> imageUrl = Maps.newHashMap();
    private String desc;
    private String createTime;
    private Integer order;
    private String startTime;
    private String endTime;
    //评论id
    private String commentId;

    private int durationTime;//开机图持续时间

    private String mobileImg;

    private String ipadImg;

    private String tvImg;

    private Integer overdRound;//已结束的场次数目

    private Integer livingRound;//正在直播的场次数目

    private Integer toStartRound;//未开始场次数目

    private List<Long> tagIds;

    private String url;//链接地址

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ResourceItemType getType() {
        return type;
    }

    public void setType(ResourceItemType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if(StringUtils.isNotEmpty(name)){
            this.name = name;
        }
    }

    public Map<String, String> getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(Map<String, String> imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    public int getDurationTime() {
        return durationTime;
    }

    public void setDurationTime(int durationTime) {
        this.durationTime = durationTime;
    }

    public String getMobileImg() {
        return mobileImg;
    }

    public void setMobileImg(String mobileImg) {
        this.mobileImg = mobileImg;
    }

    public String getIpadImg() {
        return ipadImg;
    }

    public void setIpadImg(String ipadImg) {
        this.ipadImg = ipadImg;
    }

    public String getTvImg() {
        return tvImg;
    }

    public void setTvImg(String tvImg) {
        this.tvImg = tvImg;
    }

    public Integer getOverdRound() {
        return overdRound;
    }

    public void setOverdRound(Integer overdRound) {
        this.overdRound = overdRound;
    }

    public Integer getLivingRound() {
        return livingRound;
    }

    public void setLivingRound(Integer livingRound) {
        this.livingRound = livingRound;
    }

    public Integer getToStartRound() {
        return toStartRound;
    }

    public void setToStartRound(Integer toStartRound) {
        this.toStartRound = toStartRound;
    }

    public List<Long> getTagIds() {
        return tagIds;
    }

    public void setTagIds(List<Long> tagIds) {
        this.tagIds = tagIds;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
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

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}
