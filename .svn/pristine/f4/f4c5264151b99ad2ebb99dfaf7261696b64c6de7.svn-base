package com.lesports.qmt.sbc.cache.support.impl;

import com.lesports.qmt.sbc.api.dto.TResourceContent;
import com.lesports.qmt.sbc.cache.TResourceContentCache;
import com.lesports.qmt.sbc.cache.support.AbstractRedisCache;
import com.lesports.utils.Utf8KeyCreater;
import org.springframework.stereotype.Component;

/**
 * Created by denghui on 2016/12/18.
 */
@Component("resourceContentCache")
public class TResourceContentCacheImpl extends AbstractRedisCache<TResourceContent, Long> implements TResourceContentCache {
    private static final Utf8KeyCreater<Long> KEY_CREATER = new Utf8KeyCreater<>("V2_QMT_RESOURCE_CONTENT_TF_");

    @Override
    public String getKeyById(Long id) {
        return KEY_CREATER.textKey(id);
    }

    @Override
    public String getKey(TResourceContent entity) {
        return KEY_CREATER.textKey(entity.getId());
    }

    @Override
    protected TResourceContent getEmptyEntity() {
        return new TResourceContent();
    }
}
