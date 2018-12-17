package com.lesports.qmt.cms.service;

import com.lesports.qmt.cms.api.dto.TLayout;
import com.lesports.qmt.cms.model.Layout;
import com.lesports.qmt.service.support.QmtService;

/**
 * User: ellios
 * Time: 16-11-29 : 下午4:32
 */
public interface LayoutService extends QmtService<Layout, Long> {
    TLayout getTLayoutById(long id);

    TLayout getTLayoutByPath(String path);
}
