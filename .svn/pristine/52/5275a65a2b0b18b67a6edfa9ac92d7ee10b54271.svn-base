package com.lesports.qmt.tlive.repository.impl;

import com.lesports.mongo.repository.support.AbstractMongoRepository;
import com.lesports.qmt.tlive.model.Vote;
import com.lesports.qmt.tlive.repository.VoteRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by lufei1 on 2015/9/14.
 */
@Repository("voteRepository")
public class VoteRepositoryImpl extends AbstractMongoRepository<Vote, Long> implements VoteRepository {
    @Override
    protected Class<Vote> getEntityType() {
        return Vote.class;
    }

    @Override
    protected Long getId(Vote entity) {
        return entity.getId();
    }
}
