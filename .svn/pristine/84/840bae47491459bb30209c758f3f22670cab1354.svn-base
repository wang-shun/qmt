package com.lesports.qmt.config.cache.impl;

import com.lesports.qmt.config.api.dto.TCaller;
import com.lesports.qmt.config.cache.TCallerCache;
import com.lesports.qmt.config.cache.support.AbstractRedisCache;
import com.lesports.utils.Utf8KeyCreater;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

/**
 * Created by zhonglin on 2016/3/15.
 */
@Repository("callerCache")
public class TCallerCacheImpl extends AbstractRedisCache<TCaller, Long> implements TCallerCache {

    private static final Logger LOG = LoggerFactory.getLogger(TCallerCacheImpl.class);
    private static final Utf8KeyCreater<Long> CALLER_KEY_CREATER = new Utf8KeyCreater<>("V2_QMT_CALLER_TF_");

    @Override
    public String getKeyById(Long id) {
        return CALLER_KEY_CREATER.textKey(id);
    }

    @Override
    public String getKey(TCaller entity) {
        Assert.notNull(entity);
        return CALLER_KEY_CREATER.textKey(entity.getId());
    }

    @Override
    public TCaller getEmptyEntity() {
        return new TCaller();
    }
}
