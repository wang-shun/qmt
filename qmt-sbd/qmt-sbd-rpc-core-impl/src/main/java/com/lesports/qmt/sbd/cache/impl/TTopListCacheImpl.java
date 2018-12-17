package com.lesports.qmt.sbd.cache.impl;

import com.lesports.qmt.sbd.api.dto.TTopList;
import com.lesports.qmt.sbd.cache.AbstractRedisCache;
import com.lesports.qmt.sbd.cache.TTopListCache;
import com.lesports.utils.Utf8KeyCreater;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

/**
 * User: ellios
 * Time: 15-6-24 : 下午10:56
 */
@Repository("topListCache")
public class TTopListCacheImpl extends AbstractRedisCache<TTopList, Long> implements TTopListCache {
    private static final Logger LOG = LoggerFactory.getLogger(TTopListCacheImpl.class);
    private static final Utf8KeyCreater<Long> TOP_LIST_KEY_CREATER = new Utf8KeyCreater<>("V2_SMS_TOP_LIST_TF_");

    @Override
    public String getKeyById(Long id) {
        return TOP_LIST_KEY_CREATER.textKey(id);
    }

    @Override
    public String getKey(TTopList entity) {
        Assert.notNull(entity);
        return TOP_LIST_KEY_CREATER.textKey(entity.getId());
    }

    @Override
    public TTopList getEmptyEntity() {
        return new TTopList();
    }
}
