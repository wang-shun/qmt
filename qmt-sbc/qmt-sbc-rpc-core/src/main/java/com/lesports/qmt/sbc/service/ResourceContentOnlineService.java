package com.lesports.qmt.sbc.service;

import com.lesports.api.common.CallerParam;
import com.lesports.qmt.sbc.api.dto.TResourceContent;
import com.lesports.qmt.sbc.model.ResourceContent;
import com.lesports.qmt.sbc.model.ResourceContentOnline;
import com.lesports.qmt.service.support.QmtService;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 资源位服务
 * User: ellios
 * Time: 15-6-5 : 下午2:02
 */
public interface ResourceContentOnlineService extends QmtService<ResourceContentOnline, Long> {
    List<Long> getResourceContentIdsByResourceId(Long resourceId, Pageable pageable, CallerParam caller);
    List<TResourceContent> getTResourceContentsByIds(List<Long> ids, CallerParam caller);
    public TResourceContent getTResourceContentById(long id, CallerParam caller);
}
