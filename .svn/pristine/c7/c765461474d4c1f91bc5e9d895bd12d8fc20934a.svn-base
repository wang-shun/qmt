package com.lesports.qmt.sbc.model;

import com.google.common.collect.Lists;
import com.lesports.api.common.CountryCode;
import com.lesports.api.common.LanguageCode;
import com.lesports.qmt.model.support.QmtModel;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

/**
 * 突发事件专题内容包
 * Created by denghui on 2016/10/13.
 */
@Document(collection = "topic_item_packages")
public class TopicItemPackage extends QmtModel<Long> {
    private static final long serialVersionUID = 4090784336288894365L;

    //突发事件专题ID
    @Field("topic_id")
    private Long topicId;
    //内容包名称
    private String name;
    //内容包顺序
    private Integer order;
    //是否删除
    private Boolean deleted;
    //发布国家
    @Field("allow_country")
    private CountryCode allowCountry;
    //发布语言
    @Field("language_code")
    private LanguageCode languageCode;
    //专题包内容
    private List<TopicItem> items = Lists.newArrayList();

    public Long getTopicId() {
        return topicId;
    }

    public void setTopicId(Long topicId) {
        this.topicId = topicId;
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

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public CountryCode getAllowCountry() {
        return allowCountry;
    }

    public void setAllowCountry(CountryCode allowCountry) {
        this.allowCountry = allowCountry;
    }

    public LanguageCode getLanguageCode() {
        return languageCode;
    }

    public void setLanguageCode(LanguageCode languageCode) {
        this.languageCode = languageCode;
    }

    public List<TopicItem> getItems() {
        return items;
    }

    public void setItems(List<TopicItem> items) {
        this.items = items;
    }

    //can not delete
    //fix error when deserialize id using fastjson because of the method in BaseModel
    public void setHelperId(Long helperId) {
        setId(helperId);
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return this.id;
    }

    public void addTopicItem(TopicItem topicItem) {
        this.items.add(topicItem);
    }
}
