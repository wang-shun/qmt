package com.lesports.qmt.sbc.vo;

import com.lesports.qmt.mvc.QmtVo;
import com.lesports.qmt.sbc.model.Topic;
import com.lesports.utils.LeDateUtils;
import org.apache.commons.beanutils.LeBeanUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * Created by denghui on 2016/11/2.
 */
public class TopicVo extends Topic implements QmtVo {
    private static final long serialVersionUID = 3441321750288149654L;

    private List<RelatedTagVo> relatedTags;

    public TopicVo() {
    }

    public TopicVo(Topic topic) {
        try {
            LeBeanUtils.copyProperties(this, topic);
        } catch (IllegalAccessException | InvocationTargetException e) {
        }
    }

    public List<RelatedTagVo> getRelatedTags() {
        return relatedTags;
    }

    public void setRelatedTags(List<RelatedTagVo> relatedTags) {
        this.relatedTags = relatedTags;
    }

    public Topic toTopic() {
        //直接用类型转换得到的对象会报序列化错误
        Topic topic = new Topic();
        try {
            LeBeanUtils.copyProperties(topic, this);
        } catch (IllegalAccessException | InvocationTargetException e) {
        }
        return topic;
    }

    @Override
    public TopicVo pretty() {
        this.setPublishAt(LeDateUtils.tansYYYYMMDDHHMMSSPretty(this.getPublishAt()));
        return (TopicVo) QmtVo.super.pretty();
    }
}
