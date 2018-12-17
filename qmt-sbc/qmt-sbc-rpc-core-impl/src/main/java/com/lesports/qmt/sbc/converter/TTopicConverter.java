package com.lesports.qmt.sbc.converter;

import com.google.common.collect.Maps;
import com.lesports.qmt.config.api.dto.TDictEntry;
import com.lesports.qmt.config.api.dto.TTag;
import com.lesports.qmt.config.client.QmtConfigApis;
import com.lesports.qmt.sbc.api.dto.TTopic;
import com.lesports.qmt.sbc.converter.support.AbstractSbcTDtoConverter;
import com.lesports.qmt.sbc.helper.RelatedHelper;
import com.lesports.qmt.sbc.model.Topic;
import com.lesports.utils.CallerUtils;
import com.lesports.utils.math.LeNumberUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Created by denghui on 2016/11/5.
 */
@Component("topicConverter")
public class TTopicConverter extends AbstractSbcTDtoConverter<Topic, TTopic> {

    @Resource
    private RelatedHelper relatedHelper;

    @Override
    protected void fillDto(TTopic dto, Topic model) {
        dto.setId(model.getId());
        dto.setTemplateType(model.getTemplateType());
        dto.setName(model.getName());
        dto.setShortName(model.getShortName());
        dto.setShareName(model.getShareName());
        dto.setDesc(model.getDesc());
        dto.setShareDesc(model.getShareDesc());

        if (LeNumberUtils.toLong(model.getChannelId()) > 0) {
            TDictEntry channelEntry = QmtConfigApis.getTDictEntryById(model.getChannelId(), null);
            if (channelEntry == null) {
                //channel必须有
                LOG.error("fail to fill resource dto, channel entry must not be null");
                throw new IllegalArgumentException("channel entry must not be null");
            }
            dto.setChannel(convertDictToChannel(channelEntry));
        }
        if (LeNumberUtils.toLong(model.getSubChannelId()) > 0) {
            TDictEntry subChannelEntry = QmtConfigApis.getTDictEntryById(model.getSubChannelId(), null);
            dto.setSubChannel(convertDictToChannel(subChannelEntry));
        }

        dto.setCid(LeNumberUtils.toLong(model.getCid()));
        dto.setPubName(model.getPubName());
        dto.setKeywords(model.getKeywords());
        dto.setShareable(LeNumberUtils.toBoolean(model.getShareable()));
        dto.setOnline(model.getOnline());
        dto.setPlatforms(model.getPlatforms());
        if (model.getBannerImage() != null) {
            dto.setBannerImage(model.getBannerImage().getUrl());
        }
        if (MapUtils.isNotEmpty(model.getFocusImages())) {
            dto.setFocusImages(Maps.transformValues(model.getFocusImages(), IMAGE_URL_FUNCTION));
        }
        if (CollectionUtils.isNotEmpty(model.getRelatedIds())) {
            for (Long relatedId : model.getRelatedIds()) {
                String name = relatedHelper.getNameByEntityId(relatedId, CallerUtils.getDefaultCaller());
                if (StringUtils.isNotEmpty(name)) {
                    TTag tTag = new TTag();
                    tTag.setId(relatedId);
                    tTag.setName(name);
                    dto.addToTags(tTag);
                }
            }
        }
        dto.setPublishAt(model.getPublishAt());
    }

    @Override
    protected TTopic createEmptyDto() {
        return new TTopic();
    }
}
