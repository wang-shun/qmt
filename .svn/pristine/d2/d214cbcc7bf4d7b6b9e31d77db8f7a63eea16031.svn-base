package com.lesports.qmt.sbc.model;

import com.lesports.qmt.sbc.api.dto.TopicItemType;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;

/**
 * 突发事件专题内容
 * Created by denghui on 2016/10/13.
 */
public class TopicItem implements Serializable {
    private static final long serialVersionUID = 7383796206789200671L;

    //关联的内容ID
    private Long itemId;
    //关联的内容类型
    private TopicItemType type;
    //标题
    private String name;
    //排序
    private Integer order;
    //副标题
    @Field("sub_name")
    private String subName;

    //前端展示
    @Transient
    private String originalName;

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public TopicItemType getType() {
        return type;
    }

    public void setType(TopicItemType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubName() {
        return subName;
    }

    public void setSubName(String subName) {
        this.subName = subName;
    }

    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TopicItem)) return false;

        TopicItem topicItem = (TopicItem) o;

        if (itemId != null ? !itemId.equals(topicItem.itemId) : topicItem.itemId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return itemId != null ? itemId.hashCode() : 0;
    }
}
