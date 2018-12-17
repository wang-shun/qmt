package com.lesports.qmt.config.model;

import com.google.common.collect.Sets;
import com.lesports.api.common.ImageUrlExt;
import com.lesports.qmt.config.api.dto.ActivityResourceType;
import com.lesports.qmt.config.api.dto.ActivityType;
import com.lesports.qmt.model.support.QmtModel;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Set;

/**
 * 单场推广位/条款
 * Created by yangyu on 2015/7/1.
 */
@Document(collection = "activities")
public class Activity extends QmtModel<Long> {

    private static final long serialVersionUID = -7577278508303849611L;

    //标题
    private String name;
    //关联的资源id
    @Field("resource_id")
    private String resourceId;
    //关联的资源url
    @Field("resource_url")
    private String resourceUrl;
    //关联的资源类型
    @Field("resource_type")
    private ActivityResourceType resourceType;
    //分类
    private ActivityType type;
    //浮球图片
    private ImageUrlExt image;
    //上下线状态
    private Boolean online;
    //是否删除
    private Boolean deleted;
	//节目id集合 - 该活动所推广的节目集合
	private Set<Long> eids = Sets.newHashSet();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public String getResourceUrl() {
        return resourceUrl;
    }

    public void setResourceUrl(String resourceUrl) {
        this.resourceUrl = resourceUrl;
    }

    public ActivityResourceType getResourceType() {
        return resourceType;
    }

    public void setResourceType(ActivityResourceType resourceType) {
        this.resourceType = resourceType;
    }

    public ActivityType getType() {
        return type;
    }

    public void setType(ActivityType type) {
        this.type = type;
    }

    public ImageUrlExt getImage() {
        return image;
    }

    public void setImage(ImageUrlExt image) {
        this.image = image;
    }

    public Boolean getOnline() {
        return online;
    }

    public void setOnline(Boolean online) {
        this.online = online;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Set<Long> getEids() {
        return eids;
    }

    public void setEids(Set<Long> eids) {
        this.eids = eids;
    }
}
