package com.lesports.qmt.cms.admin.web.service;

import com.lesports.qmt.cms.admin.web.param.ColumnPageCopyParam;
import com.lesports.qmt.cms.admin.web.vo.ColumnPageVo;
import com.lesports.qmt.cms.model.ColumnPage;
import com.lesports.qmt.mvc.QmtWebService;

import java.util.List;
import java.util.Map;

/**
 * User: ellios
 * Time: 16-12-1 : 下午7:17
 */
public interface ColumnPageWebService extends QmtWebService<ColumnPageVo, Long> {
    long copyColumnPage(ColumnPageCopyParam param);
    void copyColumnPage(Long srcColumnId,Long desColumnId,String name);
    List<ColumnPage> findByParam(Map<String, Object> params);
    void publish(Long columnPageId);
}
