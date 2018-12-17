package com.lesports.qmt.resource.converter;

import com.lesports.api.common.CallerParam;
import com.lesports.qmt.resource.converter.support.AbstractCvoConverter;
import com.lesports.qmt.resource.cvo.H5Cvo;
import com.lesports.qmt.sbc.api.dto.TResourceContent;

/**
 * User: ellios
 * Time: 16-12-28 : 下午6:45
 */
public class H5CvoConverter extends AbstractCvoConverter<H5Cvo, String> {
    @Override
    public H5Cvo doToCvo(TResourceContent content, String dto) {
        H5Cvo cvo = new H5Cvo();
        cvo.setUrl(content.getH5Url());
        return cvo;
    }

    @Override
    public H5Cvo doToCvo(TResourceContent content, CallerParam caller) {
        String str = null;
        return toCvo(content, str);
    }

    @Override
    protected boolean supportContent(TResourceContent content) {
        switch (content.getType()) {
            case H5:
                return true;
            default:
                return false;
        }
    }
}
