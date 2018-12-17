package com.lesports.qmt.cms.cache.support.impl;

import com.lesports.qmt.cms.api.dto.TColumnPage;
import com.lesports.qmt.cms.cache.TColumnPageCache;
import com.lesports.qmt.cms.cache.support.AbstractRedisCache;
import com.lesports.utils.Utf8KeyCreater;
import org.springframework.stereotype.Component;

/**
 * User: ellios
 * Time: 16-10-27 : 下午9:25
 */
@Component("columnPageCache")
public class TColumnPageCacheImpl extends AbstractRedisCache<TColumnPage, Long> implements TColumnPageCache {
    private static final Utf8KeyCreater<Long> KEY_CREATER = new Utf8KeyCreater<>("V2_QMT_COLUMNPAGE_TF_");
    @Override
    public String getKeyById(Long id) {
        return KEY_CREATER.textKey(id);
    }
    @Override
    public String getKey(TColumnPage entity) {
        return KEY_CREATER.textKey(entity.getId());
    }
    @Override
    protected TColumnPage getEmptyEntity() {
        return new TColumnPage();
    }

}
