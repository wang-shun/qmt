package com.lesports.qmt.sbc.cache.support.impl;

import com.lesports.qmt.sbc.api.dto.TNews;
import com.lesports.qmt.sbc.cache.TNewsCache;
import com.lesports.qmt.sbc.cache.support.AbstractRedisCache;
import com.lesports.utils.Utf8KeyCreater;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 * Created by ruiyuansheng on 2015/7/11.
 */
@Component("newsCache")
public class TNewsCacheImpl extends AbstractRedisCache<TNews, Long> implements TNewsCache {

    private static final Logger LOG = LoggerFactory.getLogger(TNewsCacheImpl.class);
    private static final Utf8KeyCreater<Long> NEWS_KEY_CREATER = new Utf8KeyCreater<>("V2_QMT_NEWS_TF_");

    @Override
    public String getKeyById(Long id) {
        return NEWS_KEY_CREATER.textKey(id);
    }

    @Override
    public String getKey(TNews entity) {
        Assert.notNull(entity);
        return NEWS_KEY_CREATER.textKey(entity.getId());
    }

    @Override
    protected TNews getEmptyEntity() {
        return new TNews();
    }

}
