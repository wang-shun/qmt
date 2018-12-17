package com.lesports.qmt.sbd.repository.impl;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import com.lesports.mongo.repository.support.AbstractMongoRepository;
import com.lesports.qmt.sbd.model.LiveEvent;
import com.lesports.qmt.sbd.repository.LiveEventRepository;
import com.lesports.utils.PageUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Map;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

/**
 * Created by qiaohongxin on 2015/9/22.
 */
@Repository("liveEventRepositoryImpl")
public class LiveEventRepositoryImpl extends AbstractMongoRepository<LiveEvent, Long> implements LiveEventRepository {

    private static final Logger LOG = LoggerFactory.getLogger(LiveEventRepositoryImpl.class);

    @Override
    protected Class<LiveEvent> getEntityType() {
        return LiveEvent.class;
    }

    @Override
    protected Long getId(LiveEvent entity) {
        return entity.getId();
    }

    @Override
    public Page<LiveEvent> findAll(Pageable pageable) {
        Pageable validPageable = PageUtils.getValidPageable(pageable);
        Map<String, Object> params = Maps.newHashMap();
        params.put("deleted", false);
        return findByCondition(params, validPageable);
    }


}