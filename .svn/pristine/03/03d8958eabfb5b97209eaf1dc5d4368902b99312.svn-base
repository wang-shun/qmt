package com.lesports.qmt.sbd.cache.impl;

import com.lesports.qmt.sbd.api.dto.TCompetition;
import com.lesports.qmt.sbd.cache.AbstractRedisCache;
import com.lesports.qmt.sbd.cache.TCompetitionCache;
import com.lesports.utils.Utf8KeyCreater;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

/**
 * User: ellios
 * Time: 15-6-17 : 下午11:23
 */
@Repository("competitionCache")
public class TCompetitionCacheImpl extends AbstractRedisCache<TCompetition, Long> implements TCompetitionCache {
    private static final Logger LOG = LoggerFactory.getLogger(TCompetitionCacheImpl.class);
    private static final Utf8KeyCreater<Long> COMPETITION_KEY_CREATER = new Utf8KeyCreater<>("V2_SMS_COMPETITION_TF_");

    @Override
    public String getKeyById(Long id) {
        return COMPETITION_KEY_CREATER.textKey(id);
    }

    @Override
    public String getKey(TCompetition entity) {
        Assert.notNull(entity);
        return COMPETITION_KEY_CREATER.textKey(entity.getId());
    }

    @Override
    public TCompetition getEmptyEntity() {
        return new TCompetition();
    }

}
