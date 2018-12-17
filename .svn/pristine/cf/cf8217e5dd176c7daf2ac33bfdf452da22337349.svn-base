package com.lesports.qmt.resource.converter;

import com.google.common.base.Preconditions;
import com.lesports.api.common.CallerParam;
import com.lesports.qmt.resource.converter.support.AbstractCvoConverter;
import com.lesports.qmt.resource.cvo.TopicCvo;
import com.lesports.qmt.sbc.api.dto.TResourceContent;
import com.lesports.qmt.sbc.api.dto.TTopic;
import com.lesports.qmt.sbc.client.QmtSbcApis;
import com.lesports.utils.math.LeNumberUtils;

/**
 * Created by denghui on 2017/1/3.
 */
public class TopicCvoConverter extends AbstractCvoConverter<TopicCvo, TTopic> {
    @Override
    protected TopicCvo doToCvo(TResourceContent content, TTopic dto) {
//        Preconditions.checkArgument(dto != null, "dto must not be null");
        TopicCvo cvo = new TopicCvo();
        if(dto != null){
            cvo.setId(dto.getId()+"");
            cvo.setName(dto.getName());
        }
        return cvo;
    }

    @Override
    protected TopicCvo doToCvo(TResourceContent content, CallerParam caller) {
        TTopic dto = QmtSbcApis.getTTopicById(LeNumberUtils.toLong(content.getItemId()),caller);
        if (dto == null) {
            LOG.warn("fail to doToCvo. dto is null. itemId : {}, caller : {}", content.getItemId(), caller);
//            return null;
        }
        return toCvo(content, dto);
    }

    @Override
    protected boolean supportContent(TResourceContent content) {
        switch (content.getType()) {
            case TOPIC:
                return true;
            default:
                return false;
        }
    }
}
