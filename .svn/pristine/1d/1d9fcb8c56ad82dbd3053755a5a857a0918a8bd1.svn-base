package com.lesports.qmt.sbd.cache.impl;

import com.lesports.qmt.sbd.api.dto.TCompetitionSeason;
import com.lesports.qmt.sbd.cache.AbstractRedisCache;
import com.lesports.qmt.sbd.cache.TCompetitionSeasonCache;
import com.lesports.utils.Utf8KeyCreater;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

/**
 * User: ellios
 * Time: 15-6-17 : 下午11:25
 */
@Repository("competitionSeasonCache")
public class TCompetitionSeasonCacheImpl extends AbstractRedisCache<TCompetitionSeason, Long> implements TCompetitionSeasonCache {
    private static final Logger LOG = LoggerFactory.getLogger(TCompetitionSeasonCacheImpl.class);
    private static final Utf8KeyCreater<Long> COMPETITION_SEASON_KEY_CREATER = new Utf8KeyCreater<>("V2_SMS_COMPETITION_SEASON_TF_");

    @Override
    public String getKeyById(Long id) {
        return COMPETITION_SEASON_KEY_CREATER.textKey(id);
    }

    @Override
    public String getKey(TCompetitionSeason entity) {
        Assert.notNull(entity);
        return COMPETITION_SEASON_KEY_CREATER.textKey(entity.getId());
    }

    @Override
    public TCompetitionSeason getEmptyEntity() {
        return new TCompetitionSeason();
    }
}
