package com.lesports.qmt.sbc.param;

import com.lesports.qmt.sbc.api.dto.TopicItemType;
import com.lesports.qmt.sbc.model.TopicItem;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created by denghui on 2016/11/30.
 */
public class SaveTopicItemParam implements Serializable {
    private static final long serialVersionUID = 8742763354191625116L;

    @NotNull(message = "item id is required")
    private Long itemId;
    @NotNull(message = "name is required")
    private String name;
    @NotNull(message = "type is required")
    private TopicItemType type;
    //默认插入到最前
    private Boolean head = true;

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TopicItemType getType() {
        return type;
    }

    public void setType(TopicItemType type) {
        this.type = type;
    }

    public Boolean getHead() {
        return head;
    }

    public void setHead(Boolean head) {
        this.head = head;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    public TopicItem toModel() {
        TopicItem topicItem = new TopicItem();
        topicItem.setItemId(getItemId());
        topicItem.setName(getName());
        topicItem.setType(getType());
        return topicItem;
    }
}
