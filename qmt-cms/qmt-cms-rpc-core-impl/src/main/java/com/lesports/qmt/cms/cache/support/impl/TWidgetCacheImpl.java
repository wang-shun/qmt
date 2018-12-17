package com.lesports.qmt.cms.cache.support.impl;

import com.lesports.qmt.cms.api.dto.TLayout;
import com.lesports.qmt.cms.api.dto.TWidget;
import com.lesports.qmt.cms.cache.TLayOutCache;
import com.lesports.qmt.cms.cache.TWidgetCache;
import com.lesports.qmt.cms.cache.support.AbstractRedisCache;
import com.lesports.utils.Utf8KeyCreater;
import org.springframework.stereotype.Component;

/**
 * User: ellios
 * Time: 16-10-27 : 下午9:25
 */
@Component("widgetCache")
public class TWidgetCacheImpl extends AbstractRedisCache<TWidget,Long> implements TWidgetCache {
    private static final Utf8KeyCreater<Long> KEY_CREATER = new Utf8KeyCreater<>("V2_QMT_WIDGET_TF_");
    @Override
    public String getKeyById(Long id) {
        return KEY_CREATER.textKey(id);
    }
    @Override
    public String getKey(TWidget entity) {
        return KEY_CREATER.textKey(entity.getId());
    }
    @Override
    protected TWidget getEmptyEntity() {
        return new TWidget();
    }

}
