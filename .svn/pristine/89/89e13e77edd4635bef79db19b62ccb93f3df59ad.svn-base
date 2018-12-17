package com.lesports.qmt.sbc.model;

/**
 * User: ellios
 * Time: 16-10-23 : 下午2:42
 */

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.lesports.api.common.ImageUrlExt;
import com.lesports.qmt.model.support.QmtModel;
import com.lesports.qmt.sbc.api.dto.ResourceDataType;
import com.lesports.qmt.sbc.api.dto.ResourceUpdateType;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * 资源位
 * User: ellios
 * Time: 16-9-11 : 下午3:45
 */
@Document(collection = "resources_online")
public class QmtResourceOnline extends QmtResource{
    //发布日期
    @Field("publish_at")
    private String publishAt;
    //发布的版本号
    private int version;

    //从哪个id发布而来，从哪个 resourceContent id 拷贝而来
    @Field("from_id")
    private Long fromId;


    public String getPublishAt() {
        return publishAt;
    }

    public void setPublishAt(String publishAt) {
        this.publishAt = publishAt;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public Long getFromId() {
        return fromId;
    }

    public void setFromId(Long fromId) {
        this.fromId = fromId;
    }
}


