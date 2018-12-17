package com.lesports.qmt.cms.admin.web.service;

import com.lesports.qmt.cms.admin.web.vo.WidgetVo;
import com.lesports.qmt.mvc.QmtWebService;

import java.util.List;
import java.util.Map;

/**
 * User: ellios
 * Time: 16-12-5 : 下午6:51
 */
public interface WidgetWebService extends QmtWebService<WidgetVo, Long> {
    Map<String, Map<String, Object>> findByIds5Fields(List<Long> ids, List<String> fields);

    Map<String, Map<String, Object>> findByNames5Fields(List<String> names, List<String> fields);

    Map<String, Map<String, Object>> findByPaths5Fields(List<String> paths, String type, List<String> fields);
}
