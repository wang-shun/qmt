package com.lesports.qmt.sbd.cache.impl;

import com.lesports.qmt.sbd.api.dto.TMatchAction;
import com.lesports.qmt.sbd.cache.AbstractRedisCache;
import com.lesports.qmt.sbd.cache.TMatchActionCache;
import com.lesports.utils.Utf8KeyCreater;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

/**
 * User: ellios
 * Time: 15-6-23 : 下午8:15
 */
@Repository("matchActionCache")
public class TMatchActionCacheImpl extends AbstractRedisCache<TMatchAction, Long> implements TMatchActionCache {
    private static final Logger LOG = LoggerFactory.getLogger(TMatchActionCacheImpl.class);
    private static final Utf8KeyCreater<Long> MATCH_ACTION_KEY_CREATER = new Utf8KeyCreater<>("V2_SMS_MATCH_ACTION_TF_");

    @Override
    public String getKeyById(Long id) {
        return MATCH_ACTION_KEY_CREATER.textKey(id);
    }

    @Override
    public String getKey(TMatchAction entity) {
        Assert.notNull(entity);
        return MATCH_ACTION_KEY_CREATER.textKey(entity.getId());
    }

    @Override
    public TMatchAction getEmptyEntity() {
        return new TMatchAction();
    }
}
