package com.lesports.qmt.config.service;

import com.lesports.api.common.PageParam;
import com.lesports.qmt.config.api.dto.GetActivitiesParam;
import com.lesports.qmt.config.api.dto.TActivity;
import com.lesports.qmt.config.model.Activity;
import com.lesports.qmt.service.support.QmtService;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by denghui on 2016/12/9.
 */
public interface ActivityService extends QmtService<Activity, Long> {

    TActivity getTActivityById(long id);

    List<TActivity> getTActivitiesByIds(List<Long> ids);

    List<Long> getActivityIdsByEid(Long eid);

    List<Long> getTActivityIds4SimpleSearch(GetActivitiesParam p, Pageable pageable);
}
