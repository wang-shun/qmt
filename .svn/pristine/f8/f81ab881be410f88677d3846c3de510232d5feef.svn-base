package com.lesports.qmt.resource.core;

import com.google.common.collect.Lists;
import com.lesports.qmt.resource.cvo.BaseCvo;
import com.lesports.qmt.sbc.api.dto.ResourceDataType;
import com.lesports.qmt.sbc.api.dto.ResourceUpdateType;
import com.lesports.qmt.sbc.api.dto.TComboEpisode;
import com.lesports.qmt.sbc.api.dto.TResourceLink;

import java.io.Serializable;
import java.util.List;

/**
 * User: ellios
 * Time: 16-12-28 : 上午10:59
 */
public class ComboResource implements Serializable{

    private static final long serialVersionUID = -8197945337617014771L;

    private String resourceName;
    private String resourceDesc;
    private String resourceLinkUrl;
    private String resourceShortName;
    private ResourceDataType type;
    private ResourceUpdateType updateType;
    //前端标识用，后端没用
    private String idf;
    //资源位上附加的一些链接位置
    private List<TResourceLink> links = Lists.newArrayList();
    //自动资源位内容的链接
    private List<TResourceLink> contentLinks = Lists.newArrayList();
    private long count;
    private int page;
    private List<BaseCvo> contents;
    private TComboEpisode episode;

    public String getIdf() {
        return idf;
    }

    public void setIdf(String idf) {
        this.idf = idf;
    }

    //    private List<TResourceLink> resourceContentLinks = Lists.newArrayList();

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

    public List<TResourceLink> getLinks() {
        return links;
    }

    public void setLinks(List<TResourceLink> links) {
        this.links = links;
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

    public List<BaseCvo> getContents() {
        return contents;
    }

    public void setContents(List<BaseCvo> contents) {
        this.contents = contents;
    }

    public TComboEpisode getEpisode() {
        return episode;
    }

    public void setEpisode(TComboEpisode episode) {
        this.episode = episode;
    }

    public List<TResourceLink> getContentLinks() {
        return contentLinks;
    }

    public void setContentLinks(List<TResourceLink> contentLinks) {
        this.contentLinks = contentLinks;
    }

    public String getResourceShortName() {
        return resourceShortName;
    }

    public void setResourceShortName(String resourceShortName) {
        this.resourceShortName = resourceShortName;
    }
}
