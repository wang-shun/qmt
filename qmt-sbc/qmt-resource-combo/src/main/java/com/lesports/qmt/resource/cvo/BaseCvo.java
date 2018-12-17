package com.lesports.qmt.resource.cvo;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.lesports.qmt.sbc.api.dto.ResourceItemType;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * User: ellios
 * Time: 16-12-28 : 上午11:00
 */
public class BaseCvo implements Serializable {

    private String id;
    private ResourceItemType type;
    private String name;
    private Map<String, String> imageUrl = Maps.newHashMap();
    private String desc;
    private String createTime;
    private Integer order;
    private String startTime;
    private String endTime;
    //评论id
    private String commentId;

    //开机图持续时间
    private int durationTime;

    private String mobileImg;

    private String ipadImg;

    private String tvImg;

    //已结束的场次数目
    private Integer overdRound;

    //正在直播的场次数目
    private Integer livingRound;

    //未开始场次数目
    private Integer toStartRound;

    private List<Long> tagIds;

    //链接地址
    private String url;


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
        if (StringUtils.isNotEmpty(name)) {
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BaseCvo)) return false;

        BaseCvo that = (BaseCvo) o;

        if (!id.equals(that.id)) return false;
        if (type != that.type) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + (type != null ? type.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
//        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}
