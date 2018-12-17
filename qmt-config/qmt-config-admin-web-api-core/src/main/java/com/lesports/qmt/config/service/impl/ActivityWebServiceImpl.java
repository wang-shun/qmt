package com.lesports.qmt.config.service.impl;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.lesports.qmt.config.client.QmtConfigActivityInternalApis;
import com.lesports.qmt.config.converter.ActivityVoConverter;
import com.lesports.qmt.config.model.Activity;
import com.lesports.qmt.config.service.ActivityWebService;
import com.lesports.qmt.config.service.impl.support.AbstractConfigWebService;
import com.lesports.qmt.config.vo.ActivityVo;
import com.lesports.qmt.mvc.QmtPage;
import com.lesports.qmt.mvc.QmtPageParam;
import com.lesports.query.InternalCriteria;
import com.lesports.query.InternalQuery;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by denghui on 2016/12/9.
 */
@Service("activityWebService")
public class ActivityWebServiceImpl extends AbstractConfigWebService implements ActivityWebService {

    private static final Logger LOG = LoggerFactory.getLogger(ActivityWebServiceImpl.class);

    private static final Function<Activity, ActivityVo> VO_FUNCTION = new Function<Activity, ActivityVo>() {
        @Nullable
        @Override
        public ActivityVo apply(@Nullable Activity input) {
            return input == null ? null : new ActivityVo(input);
        }
    };

    @Resource
    private ActivityVoConverter activityVoConverter;

    @Override
    public Long saveWithId(ActivityVo entity) {
        if (entity.getId() == null) {
            return doInsert(entity);
        } else {
            return doUpdate(entity);
        }
    }

    private Long doInsert(ActivityVo entity) {
        entity.setCreator(getOperator());
        long id = QmtConfigActivityInternalApis.saveActivity(entity.toModel());
        entity.setId(id);
        return id;
    }

    private Long doUpdate(ActivityVo entity) {
        Activity existsActivity = QmtConfigActivityInternalApis.getActivityById(entity.getId());
        activityVoConverter.copyEditableProperties(existsActivity, entity);

        existsActivity.setUpdater(getOperator());
        long id = QmtConfigActivityInternalApis.saveActivity(existsActivity, true);
        entity.setId(id);
        return id;
    }

    @Override
    public ActivityVo findOne(Long id) {
        Activity activity = QmtConfigActivityInternalApis.getActivityById(id);
        return activity == null ? null : new ActivityVo(activity);
    }

    @Override
    public boolean delete(Long id) {
        return QmtConfigActivityInternalApis.deleteActivity(id);
    }

    @Override
    public QmtPage<ActivityVo> list(Map<String, Object> params, QmtPageParam pageParam) {
        InternalQuery query = new InternalQuery();
        query.addCriteria(InternalCriteria.where("deleted").is(false));
        addParamsCriteriaToQuery(query, params, pageParam);

        String type = MapUtils.getString(params, "type", null);
        if (StringUtils.isNotEmpty(type)) {
            query.addCriteria(InternalCriteria.where("type").is(type));
        }

        long total = QmtConfigActivityInternalApis.countActivityByQuery(query);
        if (total <= 0) {
            return new QmtPage<>(Collections.EMPTY_LIST, pageParam, 0);
        }
        List<Activity> activities = QmtConfigActivityInternalApis.getActivitysByQuery(query);
        return new QmtPage<>(Lists.transform(activities, VO_FUNCTION), pageParam, total);
    }

    @Override
    public boolean updateOnlineStatus(long id, Boolean online) {
        Activity activity = QmtConfigActivityInternalApis.getActivityById(id);
        if (activity == null) {
            LOG.warn("activity not exists, id:{}", id);
            return false;
        }
        activity.setOnline(online);
        return QmtConfigActivityInternalApis.saveActivity(activity) > 0;
    }
}
