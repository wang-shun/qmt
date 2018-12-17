package com.lesports.qmt.sbd.repository.impl;

import com.lesports.mongo.repository.support.AbstractMongoRepository;
import com.lesports.qmt.sbd.model.PlayerCareerStat;
import com.lesports.qmt.sbd.repository.PlayerCareerStatRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by lufei1 on 2016/8/16.
 */
@Repository("playerCareerStatRepository")
public class PlayerCareerStatRepositoryImpl extends AbstractMongoRepository<PlayerCareerStat, Long> implements PlayerCareerStatRepository {
    @Override
    protected Class<PlayerCareerStat> getEntityType() {
        return PlayerCareerStat.class;
    }

    @Override
    protected Long getId(PlayerCareerStat entity) {
        return entity.getId();
    }
}
