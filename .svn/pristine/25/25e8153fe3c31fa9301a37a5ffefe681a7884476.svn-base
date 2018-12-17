package com.lesports.qmt.resource.converter;

import com.google.common.base.Preconditions;
import com.lesports.api.common.CallerParam;
import com.lesports.qmt.resource.converter.support.AbstractCvoConverter;
import com.lesports.qmt.resource.cvo.VideoCvo;
import com.lesports.qmt.sbc.api.dto.TResourceContent;
import com.lesports.qmt.sbc.api.dto.TVideo;
import com.lesports.qmt.sbc.client.QmtSbcApis;
import com.lesports.utils.math.LeNumberUtils;

/**
 * User: ellios
 * Time: 16-12-28 : 下午10:12
 */
public class VideoCvoConverter extends AbstractCvoConverter<VideoCvo, TVideo> {
    @Override
    public VideoCvo doToCvo(TResourceContent content, TVideo dto) {
        Preconditions.checkArgument(dto != null, "dto must not be null");
        VideoCvo cvo = new VideoCvo();
        cvo.setId(dto.getId() + "");
        cvo.setName(dto.getName());
//        cvo.se
        return cvo;
    }

    @Override
    public VideoCvo doToCvo(TResourceContent content, CallerParam caller) {
        TVideo dto = QmtSbcApis.getTVideoById(LeNumberUtils.toLong(content.getItemId()), caller);
        if (dto == null) {
            LOG.warn("fail to doToCvo. dto is null. itemId : {}, caller : {}", content.getItemId(), caller);
            return null;
        }
        return toCvo(content, dto);
    }

    @Override
    protected boolean supportContent(TResourceContent content) {
        switch (content.getType()) {
            case VIDEO:
                return true;
            default:
                return false;
        }
    }
}
