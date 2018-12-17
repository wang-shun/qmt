package com.lesports.qmt.sbd.repository.impl;

import com.lesports.mongo.repository.support.AbstractMongoRepository;
import com.lesports.qmt.sbd.model.MatchStats;
import com.lesports.qmt.sbd.repository.MatchStatsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

/**
 * Created by yangyu on 2015/5/30.
 */
@Repository("matchStatsRepository")
public class MatchStatsRepositoryImpl extends AbstractMongoRepository<MatchStats, Long> implements MatchStatsRepository {
    private static final Logger LOG = LoggerFactory.getLogger(MatchStatsRepositoryImpl.class);

    @Override
    protected Class<MatchStats> getEntityType() {
        return MatchStats.class;
    }

    @Override
    protected Long getId(MatchStats matchStats) {
        return matchStats.getId();
    }
}
