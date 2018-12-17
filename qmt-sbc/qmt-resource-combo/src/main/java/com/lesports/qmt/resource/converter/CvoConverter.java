package com.lesports.qmt.resource.converter;

import com.lesports.api.common.CallerParam;
import com.lesports.qmt.model.support.QmtModel;
import com.lesports.qmt.resource.cvo.BaseCvo;
import com.lesports.qmt.sbc.api.dto.TResource;
import com.lesports.qmt.sbc.api.dto.TResourceContent;
import org.apache.thrift.TBase;

/**
 * User: ellios
 * Time: 16-3-21 : 下午2:07
 */
public interface CvoConverter<V extends BaseCvo, T> {

    /**
     * 将dto和resource content转化为内容vo对象
     *
     * @param content
     * @param dto
     * @return
     */
    V toCvo(TResourceContent content, T dto);

    /**
     * 将resource content转化为内容vo对象
     *
     * @param content
     * @param caller
     * @return
     */
    V toCvo(TResourceContent content, CallerParam caller);

}
