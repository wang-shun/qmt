package com.lesports.qmt.tlive.cache.impl;

import com.lesports.qmt.tlive.api.dto.TVote;
import com.lesports.qmt.tlive.cache.TVoteCache;
import com.lesports.qmt.tlive.cache.support.AbstractRedisCache;
import com.lesports.utils.Utf8KeyCreater;
import com.lesports.utils.math.LeNumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

/**
 * Created by lufei1 on 2015/9/16.
 */
@Repository("voteCache")
public class TVoteCacheImpl extends AbstractRedisCache<TVote, Long> implements TVoteCache {

    private static final Logger LOG = LoggerFactory.getLogger(TVoteCacheImpl.class);
    private static final Utf8KeyCreater<Long> VOTE_KEY_CREATER = new Utf8KeyCreater<>("SMS_VOTE_TF_");
    private static final String VOTE = "VOTE_";

    private static final String UNDER_LINE = "_";


    @Override
    public String getKeyById(Long id) {
        return VOTE_KEY_CREATER.textKey(id);
    }

    @Override
    public String getKey(TVote entity) {
        Assert.notNull(entity);
        return VOTE_KEY_CREATER.textKey(entity.getId());
    }

    @Override
    public TVote getEmptyEntity() {
        return new TVote();
    }

    @Override
    public int incrVoteNum(long voteId, long optionId) {
        return LeNumberUtils.toInt(redisOp.incr(getKey(voteId, optionId)));
    }

    @Override
    public int getVoteNum(long voteId, long optionId) {
        return LeNumberUtils.toInt(redisOp.get(getKey(voteId, optionId)));
    }


    private String getKey(long voteId, long optionId) {
        return new StringBuffer().append(VOTE).append(UNDER_LINE).append(voteId).append(UNDER_LINE).append(optionId).toString();
    }
}
