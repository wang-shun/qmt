package com.lesports.qmt.sbc.model;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.lesports.api.common.ImageUrlExt;
import com.lesports.qmt.model.support.QmtModel;
import com.lesports.qmt.sbc.api.dto.ResourceItemType;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * create by wangjichuan  date:16-11-22 time:10:58
 */
public class ResourceContentOnline extends ResourceContent{
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
