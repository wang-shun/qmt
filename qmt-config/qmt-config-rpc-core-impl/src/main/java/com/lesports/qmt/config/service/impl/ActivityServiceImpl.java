package com.lesports.qmt.config.service.impl;

import com.google.common.collect.Lists;
import com.lesports.id.api.IdType;
import com.lesports.id.client.LeIdApis;
import com.lesports.mongo.repository.MongoCrudRepository;
import com.lesports.qmt.QmtOperationType;
import com.lesports.qmt.config.api.dto.ActivityType;
import com.lesports.qmt.config.api.dto.GetActivitiesParam;
import com.lesports.qmt.config.api.dto.TActivity;
import com.lesports.qmt.config.cache.TActivityCache;
import com.lesports.qmt.config.converter.TActivityConverter;
import com.lesports.qmt.config.model.Activity;
import com.lesports.qmt.config.repository.ActivityRepository;
import com.lesports.qmt.config.service.ActivityService;
import com.lesports.qmt.service.support.AbstractQmtService;
import com.lesports.utils.LeDateUtils;
import com.lesports.utils.PageUtils;
import com.lesports.utils.math.LeNumberUtils;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;
import static org.springframework.data.mongodb.core.query.Update.update;

/**
 * Created by yangyu on 2015/7/1.
 */
@Service("activityService")
public class ActivityServiceImpl extends AbstractQmtService<Activity, Long> implements ActivityService {

	private static final Logger LOG = LoggerFactory.getLogger(ActivityServiceImpl.class);

	@Resource
	private TActivityCache activityCache;

	@Resource
	private ActivityRepository activityRepository;

    @Resource
    private TActivityConverter activityConverter;

	@Override
	protected QmtOperationType getOpreationType(Activity entity) {
		if (LeNumberUtils.toLong(entity.getId()) <= 0) {
			return QmtOperationType.CREATE;
		}
		return QmtOperationType.UPDATE;
	}

	@Override
	protected boolean doCreate(Activity entity) {
		entity.setId(LeIdApis.nextId(IdType.ACTIVITY));
		entity.setOnline(true);
		entity.setDeleted(false);
		return activityRepository.save(entity);
	}

	@Override
	protected boolean doAfterCreate(Activity entity) {
		return activityCache.delete(entity.getId());
	}

	@Override
	protected boolean doUpdate(Activity entity) {
		return activityRepository.save(entity);
	}

	@Override
	protected boolean doAfterUpdate(Activity entity) {
		return activityCache.delete(entity.getId());
	}

	@Override
	protected boolean doDelete(Long id) {
		String now = LeDateUtils.formatYYYYMMDDHHMMSS(new Date());
		Update up = update("deleted", true).set("update_at", now);
		return activityRepository.update(id, up);
	}

	@Override
	protected boolean doAfterDelete(Activity entity) {
		return activityCache.delete(entity.getId());
	}

	@Override
	protected Activity doFindExistEntity(Activity entity) {
		return findOne(entity.getId());
	}

	@Override
	protected MongoCrudRepository getInnerRepository() {
		return activityRepository;
	}

	@Override
    public List<TActivity> getTActivitiesByIds(List<Long> ids){
        if (CollectionUtils.isEmpty(ids)) {
            return Collections.EMPTY_LIST;
        }
        List<TActivity> returnList = Lists.newArrayListWithExpectedSize(ids.size());
        for(Long id : ids){
            TActivity tActivity = getTActivityById(id);
            if(tActivity != null){
                returnList.add(tActivity);
            }
        }
        return returnList;
    }

	@Override
	public List<Long> getActivityIdsByEid(Long eid) {
		Query query = Query.query(Criteria.where("deleted").is(false));
		query.addCriteria(Criteria.where("type").is(ActivityType.APP_ACTIVITY));
		query.addCriteria(Criteria.where("eids").is(eid));
		return activityRepository.findIdByQuery(query);
	}

	@Override
	public List<Long> getTActivityIds4SimpleSearch(GetActivitiesParam p, Pageable pageable) {
		Query q = query(where("deleted").is(false));
		q.addCriteria(where("online").is(true));
		if (p != null) {
			if (p.getActivityType() != null) {
				q.addCriteria(where("type").is(p.getActivityType()));
			}
		}

		pageable = PageUtils.getValidPageable(pageable);
		q.with(pageable);
		List<Long> ids = activityRepository.findIdByQuery(q);
		return ids;
	}

	@Override
	public TActivity getTActivityById(long id) {
		TActivity tActivity = activityCache.findOne(id);
		if (tActivity == null) {
			Activity activity = activityRepository.findOne(id);
			if (activity == null) {
				LOG.warn("fail to getTActivityById, activity no exists. id : {}", id);
				return null;
			}
			tActivity = activityConverter.toDto(activity);
			if (tActivity == null) {
				LOG.warn("fail to getTActivityById, toTVo fail. id : {}", id);
				return null;
			}
			activityCache.save(tActivity);
		}
		return tActivity;
	}

}
