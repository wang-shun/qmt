package com.lesports.qmt.sbc.model;

import com.lesports.qmt.sbc.api.common.RelatedType;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.data.annotation.Transient;

import java.io.Serializable;

/**
 * 相关内容条目
 * Created by denghui on 2016/11/25.
 */
public class RelatedItem implements Serializable {

    //Long型id 新闻 视频等
    private Long id;
    //相关内容类型
    private RelatedType type;
    //名称
    @Transient
    private String name;
    //序号
    private Integer order;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RelatedType getType() {
        return type;
    }

    public void setType(RelatedType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

}
