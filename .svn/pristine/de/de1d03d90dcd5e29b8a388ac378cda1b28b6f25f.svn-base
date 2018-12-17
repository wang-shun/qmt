package com.lesports.qmt.sbd.cache.impl;

import com.lesports.qmt.sbd.api.dto.TPlayer;
import com.lesports.qmt.sbd.cache.AbstractRedisCache;
import com.lesports.qmt.sbd.cache.TPlayerCache;
import com.lesports.utils.Utf8KeyCreater;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

/**
 * User: ellios
 * Time: 15-6-17 : 下午11:28
 */
@Repository("playerCache")
public class TPlayerCacheImpl extends AbstractRedisCache<TPlayer, Long> implements TPlayerCache {
    private static final Logger LOG = LoggerFactory.getLogger(TPlayerCacheImpl.class);
    private static final Utf8KeyCreater<Long> PLAYER_KEY_CREATER = new Utf8KeyCreater<>("V2_SMS_PLAYER_TF_");

    @Override
    public String getKeyById(Long id) {
        return PLAYER_KEY_CREATER.textKey(id);
    }

    @Override
    public String getKey(TPlayer entity) {
        Assert.notNull(entity);
        return PLAYER_KEY_CREATER.textKey(entity.getId());
    }

    @Override
    public TPlayer getEmptyEntity() {
        return new TPlayer();
    }
}
