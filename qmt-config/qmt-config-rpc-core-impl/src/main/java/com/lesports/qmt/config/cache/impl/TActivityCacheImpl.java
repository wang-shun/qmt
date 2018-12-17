package com.lesports.qmt.config.cache.impl;

import com.lesports.qmt.config.api.dto.TActivity;
import com.lesports.qmt.config.cache.TActivityCache;
import com.lesports.qmt.config.cache.support.AbstractRedisCache;
import com.lesports.utils.Utf8KeyCreater;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

/**
 * User: ellios
 * Time: 15-6-27 : 下午6:42
 */
@Repository("activityCache")
public class TActivityCacheImpl extends AbstractRedisCache<TActivity, Long> implements TActivityCache {
    private static final Logger LOG = LoggerFactory.getLogger(TActivityCacheImpl.class);
    private static final Utf8KeyCreater<Long> ACTIVITY_KEY_CREATER = new Utf8KeyCreater<>("V2_QMT_ACTIVITY_TF_");

    @Override
    public String getKeyById(Long id) {
        return ACTIVITY_KEY_CREATER.textKey(id);
    }

    @Override
    public String getKey(TActivity entity) {
        Assert.notNull(entity);
        return ACTIVITY_KEY_CREATER.textKey(entity.getId());
    }

    @Override
    public TActivity getEmptyEntity() {
        return new TActivity();
    }
}
