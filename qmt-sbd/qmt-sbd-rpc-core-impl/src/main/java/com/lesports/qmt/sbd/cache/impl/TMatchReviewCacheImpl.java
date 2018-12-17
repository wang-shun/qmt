package com.lesports.qmt.sbd.cache.impl;

import com.lesports.qmt.sbd.api.dto.TMatchReview;
import com.lesports.qmt.sbd.cache.AbstractRedisCache;
import com.lesports.qmt.sbd.cache.TMatchReviewCache;
import com.lesports.utils.Utf8KeyCreater;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

/**
 * Created by zhonglin on 2016/2/29.
 */
@Repository("matchReviewCache")
public class TMatchReviewCacheImpl  extends AbstractRedisCache<TMatchReview, Long> implements TMatchReviewCache {
    private static final Logger LOG = LoggerFactory.getLogger(TMatchReviewCacheImpl.class);
    private static final Utf8KeyCreater<Long> MATCH_REVIEW_KEY_CREATER = new Utf8KeyCreater<>("V2_SMS_MATCH_REVIEW_TF_");

    @Override
    public String getKeyById(Long id) {
        return MATCH_REVIEW_KEY_CREATER.textKey(id);
    }

    @Override
    public String getKey(TMatchReview entity) {
        Assert.notNull(entity);
        return MATCH_REVIEW_KEY_CREATER.textKey(entity.getId());
    }

    @Override
    public TMatchReview getEmptyEntity() {
        return new TMatchReview();
    }
}
