package com.lesports.qmt.resource.converter;

import com.lesports.api.common.CallerParam;
import com.lesports.qmt.resource.converter.support.AbstractCvoConverter;
import com.lesports.qmt.resource.cvo.RankCvo;
import com.lesports.qmt.resource.cvo.SubjectCvo;
import com.lesports.qmt.sbc.api.dto.TResourceContent;
import com.lesports.qmt.sbc.api.dto.TTopic;

/**
 * User: ellios
 * Time: 16-12-28 : 下午10:09
 */
public class SubjectCvoConverter extends AbstractCvoConverter<SubjectCvo, TTopic> {
    @Override
    public SubjectCvo doToCvo(TResourceContent content, TTopic dto) {
        SubjectCvo cvo = new SubjectCvo();
        cvo.setId(content.getItemId());
        return cvo;
    }

    @Override
    public SubjectCvo doToCvo(TResourceContent content, CallerParam caller) {
        TTopic dto = null;
        return toCvo(content, dto);
    }

    @Override
    protected boolean supportContent(TResourceContent content) {
        switch (content.getType()) {
            case SUBJECT:
                return true;
            default:
                return false;
        }
    }
}
