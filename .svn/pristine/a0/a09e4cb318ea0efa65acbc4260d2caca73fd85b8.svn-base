package com.lesports.qmt.sbd.cache.impl;

import com.lesports.qmt.sbd.api.dto.TTeam;
import com.lesports.qmt.sbd.cache.AbstractRedisCache;
import com.lesports.qmt.sbd.cache.TTeamCache;
import com.lesports.utils.Utf8KeyCreater;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

/**
 * User: ellios
 * Time: 15-6-17 : 下午11:27
 */
@Repository("teamCache")
public class TTeamCacheImpl extends AbstractRedisCache<TTeam, Long> implements TTeamCache {
    private static final Logger LOG = LoggerFactory.getLogger(TTeamCacheImpl.class);
    private static final Utf8KeyCreater<Long> TEAM_KEY_CREATER = new Utf8KeyCreater<>("V2_SMS_TEAM_TF_");

    @Override
    public String getKeyById(Long id) {
        return TEAM_KEY_CREATER.textKey(id);
    }

    @Override
    public String getKey(TTeam entity) {
        Assert.notNull(entity);
        return TEAM_KEY_CREATER.textKey(entity.getId());
    }

    @Override
    public TTeam getEmptyEntity() {
        return new TTeam();
    }
}
