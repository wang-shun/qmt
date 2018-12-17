package com.lesports.qmt.sbc.vo;

import com.lesports.qmt.sbc.model.TopicItem;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.util.List;

/**
 * Created by denghui on 2016/11/30.
 */
public class TopicItemsVo implements Serializable {
    private static final long serialVersionUID = -2757725931805228912L;

    private Long topicId;
    private String topicName;
    private String packageName;
    private List<TopicItem> items;

    public TopicItemsVo() {
    }

    public Long getTopicId() {
        return topicId;
    }

    public void setTopicId(Long topicId) {
        this.topicId = topicId;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public List<TopicItem> getItems() {
        return items;
    }

    public void setItems(List<TopicItem> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}
