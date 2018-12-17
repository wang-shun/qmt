package com.lesports.qmt.sbd.cache.impl;

import com.lesports.qmt.sbd.api.dto.TPlayer;
import com.lesports.qmt.sbd.api.dto.TPlayerCareerStat;
import com.lesports.qmt.sbd.cache.AbstractRedisCache;
import com.lesports.qmt.sbd.cache.TPlayerCache;
import com.lesports.qmt.sbd.cache.TPlayerCareerStatCache;
import com.lesports.utils.Utf8KeyCreater;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

/**
 * Created by zhonglin on 2016/12/2.
 */
@Repository("playerCareerStatCache")
public class TPlayerCareerStatCacheImpl extends AbstractRedisCache<TPlayerCareerStat, Long> implements TPlayerCareerStatCache {
    private static final Logger LOG = LoggerFactory.getLogger(TPlayerCareerStatCacheImpl.class);
    private static final Utf8KeyCreater<Long> PLAYER_KEY_CREATER = new Utf8KeyCreater<>("V2_SMS_PLAYER_CAREER_STAT_TF_");


    @Override
    public String getKeyById(Long id) {
        return PLAYER_KEY_CREATER.textKey(id);
    }

    @Override
    public String getKey(TPlayerCareerStat entity) {
        Assert.notNull(entity);
        return PLAYER_KEY_CREATER.textKey(entity.getId());
    }

    @Override
    public TPlayerCareerStat getEmptyEntity() {
        return new TPlayerCareerStat();
    }
}