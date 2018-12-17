package com.lesports.qmt.web.datacenter.vo;

import com.google.common.collect.Lists;
import com.lesports.qmt.sbc.api.dto.ResourceItemType;

import java.util.List;

/**
 * 包含TV内容 TV桌面推荐主题
 * Created by denghui on 2016/12/21.
 */
public class TvContentVo extends ContentBaseVo {

    private ResourceItemType subType;
    //是否显示标题
    private Boolean showTitle;
    //其他类型对应的值
    private String otherContent;
    //图片url
    private String image;
    //H5
    private String h5Url;
    //TV桌面推荐主题的子内容
    private List<TvContentVo> items = Lists.newArrayList();

    public ResourceItemType getSubType() {
        return subType;
    }

    public void setSubType(ResourceItemType subType) {
        this.subType = subType;
    }

    public Boolean getShowTitle() {
        return showTitle;
    }

    public void setShowTitle(Boolean showTitle) {
        this.showTitle = showTitle;
    }

    public String getOtherContent() {
        return otherContent;
    }

    public void setOtherContent(String otherContent) {
        this.otherContent = otherContent;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<TvContentVo> getItems() {
        return items;
    }

    public void setItems(List<TvContentVo> items) {
        this.items = items;
    }

    public String getH5Url() {
        return h5Url;
    }

    public void setH5Url(String h5Url) {
        this.h5Url = h5Url;
    }
}
