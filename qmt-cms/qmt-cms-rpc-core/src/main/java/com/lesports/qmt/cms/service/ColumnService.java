package com.lesports.qmt.cms.service;

import com.lesports.qmt.cms.api.dto.TColumn;
import com.lesports.qmt.cms.model.Column;
import com.lesports.qmt.service.support.QmtService;

/**
 * User: ellios
 * Time: 16-11-29 : 下午4:32
 */
public interface ColumnService extends QmtService<Column, Long> {
    TColumn getTColumnById(long id);

    TColumn getTColumnByFullPath(String path);
}
