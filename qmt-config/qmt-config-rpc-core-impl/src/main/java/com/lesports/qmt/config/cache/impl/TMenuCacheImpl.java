package com.lesports.qmt.config.cache.impl;

import com.lesports.qmt.config.api.dto.TMenu;
import com.lesports.qmt.config.cache.TMenuCache;
import com.lesports.qmt.config.cache.support.AbstractRedisCache;
import com.lesports.utils.Utf8KeyCreater;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 * Created by lufei1 on 2015/7/2.
 */
@Component("menuCache")
public class TMenuCacheImpl extends AbstractRedisCache<TMenu, Long> implements TMenuCache {

    private static final Logger LOG = LoggerFactory.getLogger(TMenuCacheImpl.class);
    private static final Utf8KeyCreater<Long> MENU_KEY_CREATER = new Utf8KeyCreater<>("V2_QMT_MENU_TF_");

    @Override
    public String getKeyById(Long id) {
        return MENU_KEY_CREATER.textKey(id);
    }

    @Override
    public String getKey(TMenu entity) {
        Assert.notNull(entity);
        return MENU_KEY_CREATER.textKey(entity.getId());
    }

    @Override
    public TMenu getEmptyEntity() {
        return new TMenu();
    }
}
