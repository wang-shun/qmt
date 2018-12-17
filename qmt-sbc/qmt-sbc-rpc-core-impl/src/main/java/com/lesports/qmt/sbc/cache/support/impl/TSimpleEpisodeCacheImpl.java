package com.lesports.qmt.sbc.cache.support.impl;

import com.google.common.base.Function;
import com.google.common.collect.Ordering;
import com.lesports.qmt.sbc.api.dto.TSimpleEpisode;
import com.lesports.qmt.sbc.cache.TSimpleEpisodeCache;
import com.lesports.qmt.sbc.cache.support.AbstractRedisCache;
import com.lesports.utils.Utf8KeyCreater;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;
import redis.clients.jedis.Tuple;

import javax.annotation.Nullable;

/**
 * Created by ruiyuansheng on 2015/11/11.
 */
@Repository("simpleEpisodeCache")
public class TSimpleEpisodeCacheImpl extends AbstractRedisCache<TSimpleEpisode, Long> implements TSimpleEpisodeCache {

    private static final Logger LOG = LoggerFactory.getLogger(TSimpleEpisodeCacheImpl.class);
    private static final Utf8KeyCreater<Long> SIMPLE_EPISODE_KEY_CREATER = new Utf8KeyCreater<>("V2_QMT_SIMPLE_EPISODE_TF_");

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
        return SIMPLE_EPISODE_KEY_CREATER.textKey(id);
    }

    @Override
    public String getKey(TSimpleEpisode entity) {
        Assert.notNull(entity);
        return SIMPLE_EPISODE_KEY_CREATER.textKey(entity.getId());
    }

    @Override
    public TSimpleEpisode getEmptyEntity() {
        return new TSimpleEpisode();
    }


}
