package com.lesports.qmt.sbd.repository.impl;

import com.lesports.mongo.repository.support.AbstractMongoRepository;
import com.lesports.qmt.sbd.model.TeamSeason;
import com.lesports.qmt.sbd.repository.TeamSeasonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

/**
 * Created by lufei on 2016/10/28.
 */
@Repository("teamSeasonRepository")
public class TeamSeasonRepositoryImpl extends AbstractMongoRepository<TeamSeason, Long> implements TeamSeasonRepository {

    private static final Logger LOG = LoggerFactory.getLogger(TeamSeasonRepositoryImpl.class);


    @Override
    protected Class<TeamSeason> getEntityType() {
        return TeamSeason.class;
    }

    @Override
    protected Long getId(TeamSeason entity) {
        return entity.getId();
    }


}
