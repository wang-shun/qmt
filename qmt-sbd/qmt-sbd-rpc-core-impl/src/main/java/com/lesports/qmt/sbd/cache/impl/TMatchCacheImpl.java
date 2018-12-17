package com.lesports.qmt.sbd.cache.impl;

import com.lesports.qmt.sbd.api.dto.TMatch;
import com.lesports.qmt.sbd.cache.AbstractRedisCache;
import com.lesports.qmt.sbd.cache.TMatchCache;
import com.lesports.utils.Utf8KeyCreater;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

/**
 * User: ellios
 * Time: 15-6-14 : 下午6:12
 */
@Repository("matchCache")
public class TMatchCacheImpl extends AbstractRedisCache<TMatch, Long> implements TMatchCache {
    private static final Logger LOG = LoggerFactory.getLogger(TMatchCacheImpl.class);
    private static final Utf8KeyCreater<Long> MATCH_KEY_CREATER = new Utf8KeyCreater<>("V2_SMS_MATCH_TF_");

    @Override
    public String getKeyById(Long id) {
        return MATCH_KEY_CREATER.textKey(id);
    }

    @Override
    public String getKey(TMatch entity) {
        Assert.notNull(entity);
        return MATCH_KEY_CREATER.textKey(entity.getId());
    }

    @Override
    public TMatch getEmptyEntity() {
        return new TMatch();
    }
}
