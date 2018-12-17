package com.lesports.qmt.sbd.repository.impl;

import com.lesports.mongo.repository.support.AbstractMongoRepository;
import com.lesports.qmt.sbd.model.MatchReview;
import com.lesports.qmt.sbd.repository.MatchReviewRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by lufei1 on 2016/2/23.
 */
@Repository("matchReviewRepository")
public class MatchReviewRepositoryImpl extends AbstractMongoRepository<MatchReview, Long> implements MatchReviewRepository {
    @Override
    protected Class<MatchReview> getEntityType() {
        return MatchReview.class;
    }

    @Override
    protected Long getId(MatchReview entity) {
        return entity.getId();
    }

}
