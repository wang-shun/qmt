package com.lesports.qmt.sbc.param;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created by denghui on 2016/11/30.
 */
public class SaveTopicItemPackageParam implements Serializable {
    private static final long serialVersionUID = 2468676315142486444L;

    //专题id
    @NotNull(message = "topic id is required")
    private Long topicId;
    //名称
    @NotBlank(message = "name is required")
    private String name;
    //专题包id
    private Long id;
    //内容JSON
    private String items;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getTopicId() {
        return topicId;
    }

    public void setTopicId(Long topicId) {
        this.topicId = topicId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getItems() {
        return items;
    }

    public void setItems(String items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}
