package com.lesports.qmt.sbd.cache.impl;

import com.lesports.qmt.sbd.api.dto.TCompetitorSeasonStat;
import com.lesports.qmt.sbd.cache.AbstractRedisCache;
import com.lesports.qmt.sbd.cache.TCompetitorSeasonStatCache;
import com.lesports.utils.Utf8KeyCreater;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

/**
 * Created by zhonglin on 2016/3/15.
 */
@Repository("competitorSeasonStatCache")
public class TCompetitorSeasonStatsCacheImpl extends AbstractRedisCache<TCompetitorSeasonStat, Long> implements TCompetitorSeasonStatCache {

    private static final Logger LOG = LoggerFactory.getLogger(TCompetitorSeasonStatsCacheImpl.class);
    private static final Utf8KeyCreater<Long> COMPETITOR_SEASON_STATS_KEY_CREATER = new Utf8KeyCreater<>("V2_SMS_COMPETITOR_SEASON_STATS_TF_");

    @Override
    public String getKeyById(Long id) {
        return COMPETITOR_SEASON_STATS_KEY_CREATER.textKey(id);
    }

    @Override
    public String getKey(TCompetitorSeasonStat entity) {
        Assert.notNull(entity);
        return COMPETITOR_SEASON_STATS_KEY_CREATER.textKey(entity.getId());
    }

    @Override
    public TCompetitorSeasonStat getEmptyEntity() {
        return new TCompetitorSeasonStat();
    }
}

