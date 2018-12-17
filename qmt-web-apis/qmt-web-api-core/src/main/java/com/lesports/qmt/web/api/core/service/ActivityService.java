package com.lesports.qmt.web.api.core.service;

import com.lesports.api.common.CallerParam;
import com.lesports.api.common.PageParam;
import com.lesports.jersey.support.model.CallerBean;
import com.lesports.jersey.support.model.PageBean;
import com.lesports.qmt.config.api.dto.ActivityType;
import com.lesports.qmt.config.api.dto.GetActivitiesParam;
import com.lesports.qmt.config.api.dto.TActivity;
import com.lesports.qmt.web.api.core.vo.Activity;

import java.util.List;

/**
 * Created by ruiyuansheng on 2016/3/28.
 */
public interface ActivityService {

    List<Activity> getActivities(Integer activityType, String resourceType, PageParam pageParam, CallerParam caller);

    TActivity getActivity(ActivityType activityType, String resourceTypeString);

    /**
     * 获取活动列表
     * @param p
     * @return
     */
    TActivity getActivity(GetActivitiesParam p);
}
