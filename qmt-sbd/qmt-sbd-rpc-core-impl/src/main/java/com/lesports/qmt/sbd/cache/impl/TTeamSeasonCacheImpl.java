package com.lesports.qmt.sbd.cache.impl;

import com.lesports.qmt.sbd.api.dto.TTeamSeason;
import com.lesports.qmt.sbd.cache.AbstractRedisCache;
import com.lesports.qmt.sbd.cache.TTeamSeasonCache;
import com.lesports.utils.Utf8KeyCreater;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

/**
 * Created by zhonglin on 2016/3/15.
 */
@Repository("teamSeasonCache")
public class TTeamSeasonCacheImpl extends AbstractRedisCache<TTeamSeason, Long> implements TTeamSeasonCache {
    private static final Logger LOG = LoggerFactory.getLogger(TTeamSeasonCacheImpl.class);
    private static final Utf8KeyCreater<Long> TEAM_SEASON_KEY_CREATER = new Utf8KeyCreater<>("V2_SMS_TEAM_SEASON_TF_");

    @Override
    public String getKeyById(Long id) {
        return TEAM_SEASON_KEY_CREATER.textKey(id);
    }

    @Override
    public String getKey(TTeamSeason entity) {
        Assert.notNull(entity);
        return TEAM_SEASON_KEY_CREATER.textKey(entity.getId());
    }

    @Override
    public TTeamSeason getEmptyEntity() {
        return new TTeamSeason();
    }
}

