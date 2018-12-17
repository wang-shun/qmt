package com.lesports.qmt.sbc.service;

import com.lesports.api.common.CallerParam;
import com.lesports.qmt.sbc.api.dto.TResource;
import com.lesports.qmt.sbc.model.QmtResource;
import com.lesports.qmt.service.support.QmtService;

import java.util.List;

/**
 * 资源位服务
 * User: ellios
 * Time: 15-6-5 : 下午2:02
 */
public interface ResourceService extends QmtService<QmtResource, Long> {

    List<TResource> getTResourcesByIds(List<Long> ids, CallerParam caller);

    TResource getTResourceById(long id, CallerParam caller);

}
