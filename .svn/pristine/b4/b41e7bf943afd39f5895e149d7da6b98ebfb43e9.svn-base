package com.lesports.qmt.web.datacenter.vo;

import com.google.common.collect.Lists;
import com.lesports.qmt.sbc.api.dto.ResourceDataType;
import com.lesports.qmt.sbc.api.dto.ResourceUpdateType;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * create by wangjichuan  date:16-12-13 time:15:45
 * 资源位数据的Vo
 */
public class ResourceVo {
    private String resourceName;
    private String resourceDesc;
    private String resourceLinkUrl;
    private ResourceDataType type;
    private ResourceUpdateType updateType;
    //资源位上附加的一些链接位置
    private List<LinkVo> links = Lists.newArrayList();
    private long count;
    private int page;
    private List<ContentBaseVo> contents;

    private List<LinkVo> resourceContentLinks = Lists.newArrayList();


    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public String getResourceDesc() {
        return resourceDesc;
    }

    public void setResourceDesc(String resourceDesc) {
        this.resourceDesc = resourceDesc;
    }

    public String getResourceLinkUrl() {
        return resourceLinkUrl;
    }

    public void setResourceLinkUrl(String resourceLinkUrl) {
        this.resourceLinkUrl = resourceLinkUrl;
    }

    public ResourceDataType getType() {
        return type;
    }

    public void setType(ResourceDataType type) {
        this.type = type;
    }

    public ResourceUpdateType getUpdateType() {
        return updateType;
    }

    public void setUpdateType(ResourceUpdateType updateType) {
        this.updateType = updateType;
    }

    public List<LinkVo> getLinks() {
        return links;
    }

    public void setLinks(List<LinkVo> links) {
        this.links = links;
    }

    public List<LinkVo> getResourceContentLinks() {
        return resourceContentLinks;
    }

    public void setResourceContentLinks(List<LinkVo> resourceContentLinks) {
        this.resourceContentLinks = resourceContentLinks;
    }

    public List<ContentBaseVo> getContents() {
        return contents;
    }

    public void setContents(List<ContentBaseVo> contents) {
        this.contents = contents;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }
}
