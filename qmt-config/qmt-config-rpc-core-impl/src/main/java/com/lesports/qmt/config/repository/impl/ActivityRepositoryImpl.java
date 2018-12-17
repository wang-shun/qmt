package com.lesports.qmt.config.repository.impl;

import com.lesports.mongo.repository.support.AbstractMongoRepository;
import com.lesports.qmt.config.model.Activity;
import com.lesports.qmt.config.repository.ActivityRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by denghui on 2016/12/9.
 */
@Repository("activityRepository")
public class ActivityRepositoryImpl extends AbstractMongoRepository<Activity, Long> implements ActivityRepository {

    @Override
    protected Class<Activity> getEntityType() {
        return Activity.class;
    }

    @Override
    protected Long getId(Activity entity) {
        return entity.getId();
    }
}
