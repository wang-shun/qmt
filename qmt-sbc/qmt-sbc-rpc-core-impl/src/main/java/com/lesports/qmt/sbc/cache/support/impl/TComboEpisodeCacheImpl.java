package com.lesports.qmt.sbc.cache.support.impl;

import com.google.common.base.Function;
import com.google.common.collect.Ordering;
import com.lesports.qmt.sbc.api.dto.TComboEpisode;
import com.lesports.qmt.sbc.cache.TComboEpisodeCache;
import com.lesports.qmt.sbc.cache.support.AbstractRedisCache;
import com.lesports.utils.Utf8KeyCreater;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;
import redis.clients.jedis.Tuple;

import javax.annotation.Nullable;

/**
 * User: ellios
 * Time: 15-6-7 : 下午5:48
 */
@Repository("comboEpisodeCache")
public class TComboEpisodeCacheImpl extends AbstractRedisCache<TComboEpisode, Long> implements TComboEpisodeCache {


    private static final Logger LOG = LoggerFactory.getLogger(TComboEpisodeCacheImpl.class);
    private static final Utf8KeyCreater<Long> COMBO_EPISODE_KEY_CREATER = new Utf8KeyCreater<Long>("V2_QMT_COMBO_EPISODE_TF_");
    private static final String NOT_END_EPISODE_IDS_KEY = "NOT_END_EPISODE_IDS";
    private static final String LIVING_EPISODE_IDS_KEY = "LIVING_EPISODE_IDS";

    public static final Function<Tuple, Double> TUPLE_SORT_FUNCTIONS = new Function<Tuple, Double>() {
        @Nullable
        @Override
        public Double apply(@Nullable Tuple tuple) {
            return tuple.getScore();
        }
    };

    private final Ordering<Tuple> tupleOrdering = Ordering.natural()
            .onResultOf(TUPLE_SORT_FUNCTIONS);

    @Override
    public String getKeyById(Long id) {
        return COMBO_EPISODE_KEY_CREATER.textKey(id);
    }

    @Override
    public String getKey(TComboEpisode entity) {
        Assert.notNull(entity);
        return COMBO_EPISODE_KEY_CREATER.textKey(entity.getId());
    }

    @Override
    public TComboEpisode getEmptyEntity() {
        return new TComboEpisode();
    }
}
