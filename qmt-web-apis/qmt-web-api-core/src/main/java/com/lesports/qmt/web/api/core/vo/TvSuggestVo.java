package com.lesports.qmt.web.api.core.vo;

import com.lesports.qmt.sbc.api.dto.ResourceItemType;

import java.io.Serializable;

/**
 * Created by ruiyuansheng on 2015/10/26.
 */
public class TvSuggestVo implements Serializable {

    private static final long serialVersionUID = -2777742805268539012L;
    private Long resourceId;

    private Integer resourceType;//资源类型，0为模块，1为频道，2为自制节目，3为节目，4为视频，5为专题

    private Integer suggestType;// 1是首页，2是桌面，3是比赛大厅

    private String title;//标题

    private Integer order;//排序

    private String imageUrl;//图片

    private Boolean showTitle;//是否显示标题，true显示，false隐藏

    private String startTime; //比赛开始时间

    private Integer status;//比赛状态 0:未开始, 1:比赛中, 2:比赛结束

    private Integer actionType;//打洞跳转的app类型，0是乐视体育客户端TV版，1是应用商店TV版

    private String action;//打洞跳转action

    private String command;//打洞跳转参数

    private String h5Url;

    private Boolean isSuggest = false;

    private Boolean isTheme = false;

    private String redirectValue;

    private String bucket;

    private String reid;

    private String area;

    private Integer cornerMark;//1:vip,2:原创，3：专题，4：图集，5，回放，6：集锦

    private ResourceItemType subType;

    public ResourceItemType getSubType() {
        return subType;
    }

    public void setSubType(ResourceItemType subType) {
        this.subType = subType;
    }

    public Integer getCornerMark() {
        return cornerMark;
    }

    public void setCornerMark(Integer cornerMark) {
        this.cornerMark = cornerMark;
    }

    public String getBucket() {
        return bucket;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }


    public String getReid() {
        return reid;
    }

    public void setReid(String reid) {
        this.reid = reid;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }


    public String getRedirectValue() {
        return redirectValue;
    }

    public void setRedirectValue(String redirectValue) {
        this.redirectValue = redirectValue;
    }

    public Boolean getIsTheme() {
        return isTheme;
    }

    public void setIsTheme(Boolean isTheme) {
        this.isTheme = isTheme;
    }

    public Boolean getIsSuggest() {
        return isSuggest;
    }

    public void setIsSuggest(Boolean isSuggest) {
        this.isSuggest = isSuggest;
    }

    public String getH5Url() {
        return h5Url;
    }

    public void setH5Url(String h5Url) {
        this.h5Url = h5Url;
    }

    public Integer getSuggestType() {
        return suggestType;
    }

    public void setSuggestType(Integer suggestType) {
        this.suggestType = suggestType;
    }

    public Integer getActionType() {
        return actionType;
    }

    public void setActionType(Integer actionType) {
        this.actionType = actionType;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Boolean getShowTitle() {
        return showTitle;
    }

    public void setShowTitle(Boolean showTitle) {
        this.showTitle = showTitle;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Long getResourceId() {
        return resourceId;
    }

    public void setResourceId(Long resourceId) {
        this.resourceId = resourceId;
    }

    public Integer getResourceType() {
        return resourceType;
    }

    public void setResourceType(Integer resourceType) {
        this.resourceType = resourceType;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    @Override
    public String toString() {
        return "TvSuggestVo{" +
                "resourceId=" + resourceId +
                ", resourceType=" + resourceType +
                ", suggestType=" + suggestType +
                ", title='" + title + '\'' +
                ", order=" + order +
                ", imageUrl='" + imageUrl + '\'' +
                ", showTitle=" + showTitle +
                ", startTime='" + startTime + '\'' +
                ", status=" + status +
                ", actionType=" + actionType +
                ", action='" + action + '\'' +
                ", command='" + command + '\'' +
                ", h5Url='" + h5Url + '\'' +
                ", isSuggest=" + isSuggest +
                ", isTheme=" + isTheme +
                ", redirectValue='" + redirectValue + '\'' +
                ", bucket='" + bucket + '\'' +
                ", reid='" + reid + '\'' +
                ", area='" + area + '\'' +
                ", cornerMark=" + cornerMark +
                ", subType=" + subType +
                '}';
    }
}
