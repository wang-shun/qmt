package com.lesports.qmt.sbc.cache.support.impl;

import com.lesports.qmt.sbc.api.dto.TResource;
import com.lesports.qmt.sbc.cache.TResourceCache;
import com.lesports.qmt.sbc.cache.support.AbstractRedisCache;
import com.lesports.utils.Utf8KeyCreater;
import org.springframework.stereotype.Component;

/**
 * User: ellios
 * Time: 16-10-27 : 下午9:25
 */
@Component("resourceCache")
public class TResourceCacheImpl extends AbstractRedisCache<TResource, Long> implements TResourceCache {
    private static final Utf8KeyCreater<Long> KEY_CREATER = new Utf8KeyCreater<>("V2_QMT_RESOURCE_TF_");


    @Override
    public String getKeyById(Long id) {
        return KEY_CREATER.textKey(id);
    }

    @Override
    public String getKey(TResource entity) {
        return KEY_CREATER.textKey(entity.getId());
    }

    @Override
    protected TResource getEmptyEntity() {
        return new TResource();
    }

}
