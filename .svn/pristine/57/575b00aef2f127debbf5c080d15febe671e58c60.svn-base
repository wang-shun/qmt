package com.lesports.qmt.cms.admin.web.service;

import com.lesports.qmt.cms.admin.web.vo.MapperVo;
import com.lesports.qmt.mvc.QmtWebService;

import java.util.Map;

/**
 * User: ellios
 * Time: 17-1-10 : 下午5:29
 */
public interface MapperWebService extends QmtWebService<MapperVo, Long> {
    Long saveWithId(Map<String, Object> res, String type, String version);

    MapperVo findByTypeAndVersion(String type, String version);
}
