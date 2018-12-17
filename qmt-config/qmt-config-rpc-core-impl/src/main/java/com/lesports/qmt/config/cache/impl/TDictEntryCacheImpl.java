package com.lesports.qmt.config.cache.impl;

import com.lesports.qmt.config.cache.TDictEntryCache;
import com.lesports.qmt.config.api.dto.TDictEntry;
import com.lesports.qmt.config.cache.support.AbstractRedisCache;
import com.lesports.utils.Utf8KeyCreater;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

/**
 * Created by zhonglin on 2016/3/15.
 */
@Repository("dictEntryCache")
public class TDictEntryCacheImpl extends AbstractRedisCache<TDictEntry, Long> implements TDictEntryCache {

    private static final Logger LOG = LoggerFactory.getLogger(TDictEntryCacheImpl.class);
    private static final Utf8KeyCreater<Long> DICTENTRY_KEY_CREATER = new Utf8KeyCreater<>("V2_QMT_DICTENTRY_TF_");

    @Override
    public String getKeyById(Long id) {
        return DICTENTRY_KEY_CREATER.textKey(id);
    }

    @Override
    public String getKey(TDictEntry entity) {
        Assert.notNull(entity);
        return DICTENTRY_KEY_CREATER.textKey(entity.getId());
    }

    @Override
    public TDictEntry getEmptyEntity() {
        return new TDictEntry();
    }
}
