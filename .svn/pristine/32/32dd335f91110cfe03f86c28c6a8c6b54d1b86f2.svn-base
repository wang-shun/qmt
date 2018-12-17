package com.lesports.qmt.cms.cache.support.impl;

import com.lesports.qmt.cms.api.dto.TColumnPage;
import com.lesports.qmt.cms.api.dto.TLayout;
import com.lesports.qmt.cms.cache.TColumnPageCache;
import com.lesports.qmt.cms.cache.TLayOutCache;
import com.lesports.qmt.cms.cache.support.AbstractRedisCache;
import com.lesports.utils.Utf8KeyCreater;
import org.springframework.stereotype.Component;

/**
 * User: ellios
 * Time: 16-10-27 : 下午9:25
 */
@Component("layOutCache")
public class TLayOutCacheImpl extends AbstractRedisCache<TLayout,Long> implements TLayOutCache {
    private static final Utf8KeyCreater<Long> KEY_CREATER = new Utf8KeyCreater<>("V2_QMT_LAYOUT_TF_");
    @Override
    public String getKeyById(Long id) {
        return KEY_CREATER.textKey(id);
    }
    @Override
    public String getKey(TLayout entity) {
        return KEY_CREATER.textKey(entity.getId());
    }
    @Override
    protected TLayout getEmptyEntity() {
        return new TLayout();
    }

}
