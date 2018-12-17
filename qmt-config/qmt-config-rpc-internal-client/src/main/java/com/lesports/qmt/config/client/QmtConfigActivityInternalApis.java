package com.lesports.qmt.config.client;

import com.lesports.qmt.config.model.Activity;
import com.lesports.query.InternalQuery;

import java.util.List;

/**
 * Created by denghui on 2016/12/9.
 */
public class QmtConfigActivityInternalApis extends QmtConfigInternalApis {

    public static long saveActivity(Activity tag) {
        return saveActivity(tag, false);
    }

    public static long saveActivity(Activity tag, boolean allowEmpty) {
        return saveEntity(tag, Activity.class, allowEmpty);
    }

    public static boolean deleteActivity(Long id) {
        return deleteEntity(id, Activity.class);
    }

    public static Activity getActivityById(Long id) {
        return getEntityById(id, Activity.class);
    }

    public static List<Activity> getActivitysByIds(List<Long> ids) {
        return getEntitiesByIds(ids, Activity.class);
    }

    public static long countActivityByQuery(InternalQuery query) {
        return countByQuery(query, Activity.class);
    }

    public static List<Long> getActivityIdsByQuery(InternalQuery query) {
        return getEntityIdsByQuery(query, Activity.class);
    }

    public static List<Activity> getActivitysByQuery(InternalQuery query) {
        return getEntitiesByQuery(query, Activity.class);
    }

}
