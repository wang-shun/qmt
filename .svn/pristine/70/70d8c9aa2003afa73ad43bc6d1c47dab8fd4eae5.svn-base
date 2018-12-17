package com.lesports.qmt.sbd.cache.impl;

import com.lesports.qmt.sbd.api.dto.TMatchStats;
import com.lesports.qmt.sbd.cache.AbstractRedisCache;
import com.lesports.qmt.sbd.cache.TMatchStatsCache;
import com.lesports.utils.Utf8KeyCreater;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

/**
 * Created by PANGCHUANXIAO on 2016/2/29.
 */
@Repository("matchStatsCache")
public class TMatchStatsCacheImpl extends AbstractRedisCache<TMatchStats, Long> implements TMatchStatsCache {
    private static final Logger LOG = LoggerFactory.getLogger(TMatchStatsCacheImpl.class);
    private static final Utf8KeyCreater<Long> MATCH_STATS_KEY_CREATER = new Utf8KeyCreater<>("V2_SMS_MATCH_STATS_TF_");

    @Override
    public String getKeyById(Long id) {
        return MATCH_STATS_KEY_CREATER.textKey(id);
    }

    @Override
    public String getKey(TMatchStats entity) {
        Assert.notNull(entity);
        return MATCH_STATS_KEY_CREATER.textKey(entity.getId());
    }

    @Override
    public TMatchStats getEmptyEntity() {
        return new TMatchStats();
    }
}
