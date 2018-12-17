package com.lesports.qmt.resource.converter;

import com.lesports.api.common.CallerParam;
import com.lesports.qmt.resource.converter.support.AbstractCvoConverter;
import com.lesports.qmt.resource.cvo.RankCvo;
import com.lesports.qmt.sbc.api.dto.TResourceContent;

/**
 * User: ellios
 * Time: 16-12-28 : 下午9:58
 */
public class RankCvoConverter extends AbstractCvoConverter<RankCvo, String> {
    @Override
    public RankCvo doToCvo(TResourceContent content, String dto) {
        RankCvo cvo = new RankCvo();
        cvo.setId(content.getItemId());
        return cvo;
    }

    @Override
    public RankCvo doToCvo(TResourceContent content, CallerParam caller) {
        return toCvo(content, "");
    }

    @Override
    protected boolean supportContent(TResourceContent content) {
        switch (content.getType()) {
            case OTHER:
                return true;
            default:
                return false;
        }
    }
}
