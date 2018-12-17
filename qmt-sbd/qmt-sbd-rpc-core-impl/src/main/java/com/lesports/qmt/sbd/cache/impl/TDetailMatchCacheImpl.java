package com.lesports.qmt.sbd.cache.impl;

import com.lesports.qmt.sbd.api.dto.TDetailMatch;
import com.lesports.qmt.sbd.cache.AbstractRedisCache;
import com.lesports.qmt.sbd.cache.TDetailMatchCache;
import com.lesports.utils.Utf8KeyCreater;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

/**
 * User: ellios
 * Time: 15-6-17 : 下午10:24
 */
@Repository("detailMatchCache")
public class TDetailMatchCacheImpl extends AbstractRedisCache<TDetailMatch, Long> implements TDetailMatchCache {
    private static final Logger LOG = LoggerFactory.getLogger(TDetailMatchCacheImpl.class);
    private static final Utf8KeyCreater<Long> DETAIL_MATCH_KEY_CREATER = new Utf8KeyCreater<>("V2_SMS_DETAIL_MATCH_TF_");

    @Override
    public String getKeyById(Long id) {
        return DETAIL_MATCH_KEY_CREATER.textKey(id);
    }

    @Override
    public String getKey(TDetailMatch entity) {
        Assert.notNull(entity);
        return DETAIL_MATCH_KEY_CREATER.textKey(entity.getId());
    }

    @Override
    public TDetailMatch getEmptyEntity() {
        return new TDetailMatch();
    }

}
