package com.lesports.qmt.sbd.service;

import com.lesports.api.common.CallerParam;
import com.lesports.qmt.sbd.service.support.SbdWebService;
import com.lesports.qmt.sbd.vo.TopListVo;

import java.util.List;
import java.util.Map;

/**
 * Created by lufei1 on 2016/10/25.
 */
public interface TopListWebService extends SbdWebService<TopListVo, Long> {

    List<Map<String, Object>> listTopListItems(long id, CallerParam callerParam);

    boolean saveTopListItem(long id, TopListVo.TopListItem topListItem);

    boolean deleteTopListItem(long id, TopListVo.TopListItem topListItem);

    boolean publish(long id, CallerParam caller);
}
