package com.lesports.qmt.config.param;

import com.lesports.qmt.config.api.dto.ActivityResourceType;
import com.lesports.qmt.config.api.dto.ActivityType;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created by denghui on 2016/12/9.
 */
public class SaveActivityParam implements Serializable {

    private static final long serialVersionUID = 6435829687664355779L;

    private Long id;
    //标题
    @NotBlank(message = "name is required")
    private String name;
    //关联的资源id
    private String resourceId;
    //关联的资源url
    private String resourceUrl;
    //关联的资源类型
    private ActivityResourceType resourceType;
    //分类
    @NotNull(message = "type is required")
    private ActivityType type;
    //图片
    private String image;
    //节目id集合 - 该活动所推广的节目集合
    private String eids;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getEids() {
        return eids;
    }

    public void setEids(String eids) {
        this.eids = eids;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}
