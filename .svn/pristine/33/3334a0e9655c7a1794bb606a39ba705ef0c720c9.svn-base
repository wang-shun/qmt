package com.lesports.qmt.sbc.converter;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Sets;
import com.lesports.api.common.ImageUrlExt;
import com.lesports.api.common.Platform;
import com.lesports.qmt.mvc.QmtVoConverter;
import com.lesports.qmt.sbc.model.Topic;
import com.lesports.qmt.sbc.param.SaveTopicParam;
import com.lesports.qmt.sbc.vo.RelatedTagVo;
import com.lesports.qmt.sbc.vo.TopicVo;
import com.lesports.utils.LeDateUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Created by denghui on 2016/11/2.
 */
@Component
public class TopicVoConverter implements QmtVoConverter<Topic, TopicVo> {

    @Override
    public TopicVo toVo(Object object) {
        SaveTopicParam saveTopicParam = (SaveTopicParam) object;
        TopicVo topicVo = new TopicVo();
        topicVo.setId(saveTopicParam.getId());
        topicVo.setTemplateType(saveTopicParam.getTemplateType());

        topicVo.setName(saveTopicParam.getName());
        topicVo.setShortName(saveTopicParam.getShortName());
        topicVo.setShareName(saveTopicParam.getShareName());
        topicVo.setDesc(saveTopicParam.getDesc());
        topicVo.setShareDesc(saveTopicParam.getShareDesc());
        topicVo.setChannelId(saveTopicParam.getChannelId());
        topicVo.setSubChannelId(saveTopicParam.getSubChannelId());

        topicVo.setPubName(saveTopicParam.getPubName());
        topicVo.setKeywords(saveTopicParam.getKeywords());
        topicVo.setShareable(saveTopicParam.getShareable());
        topicVo.setCid(saveTopicParam.getCid());
        topicVo.setPlatforms(Sets.newHashSet(JSON.parseArray(saveTopicParam.getPlatforms(), Platform.class)));
        topicVo.setPublishAt(LeDateUtils.tansYMDHMS2YYYYMMDDHHMMSS(saveTopicParam.getPublishAt()));
        topicVo.setDraft(saveTopicParam.getDraft());
        List<RelatedTagVo> relatedTags = JSON.parseArray(saveTopicParam.getRelatedTags(), RelatedTagVo.class);
        if (CollectionUtils.isNotEmpty(relatedTags)) {
            for (RelatedTagVo relatedTag : relatedTags) {
                topicVo.addRelatedId(relatedTag.getId());
            }
        }

        //图片
        if (StringUtils.isNotEmpty(saveTopicParam.getBannerImage())) {
            topicVo.setBannerImage(JSON.parseObject(saveTopicParam.getBannerImage(), ImageUrlExt.class));
        }
        Map<String, ImageUrlExt> focusImages = JSON.parseObject(saveTopicParam.getFocusImages(), Map.class);
        if (MapUtils.isNotEmpty(focusImages)) {
            topicVo.setFocusImages(focusImages);
        }
        return topicVo;
    }

    @Override
    public Topic copyEditableProperties(Topic existsEntity, TopicVo vo) {
        existsEntity.setTemplateType(vo.getTemplateType());
        existsEntity.setName(vo.getName());
        existsEntity.setShortName(vo.getShortName());
        existsEntity.setShareName(vo.getShareName());
        existsEntity.setDesc(vo.getDesc());
        existsEntity.setShareDesc(vo.getShareDesc());
        existsEntity.setChannelId(vo.getChannelId());
        existsEntity.setSubChannelId(vo.getSubChannelId());

        existsEntity.setPubName(vo.getPubName());
        existsEntity.setKeywords(vo.getKeywords());
        existsEntity.setShareable(vo.getShareable());
        existsEntity.setCid(vo.getCid());
        existsEntity.setPlatforms(vo.getPlatforms());
        if (StringUtils.isNotEmpty(vo.getPublishAt())) {
            existsEntity.setPublishAt(vo.getPublishAt());
        }
        existsEntity.setDraft(vo.getDraft());
        existsEntity.setRelatedIds(vo.getRelatedIds());

        //图片
        existsEntity.setBannerImage(vo.getBannerImage());
        existsEntity.setFocusImages(vo.getFocusImages());
        return existsEntity;
    }
}
