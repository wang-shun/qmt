package com.lesports.qmt.sbd.repository.impl;

import com.lesports.mongo.repository.support.AbstractMongoRepository;
import com.lesports.qmt.sbd.model.Team;
import com.lesports.qmt.sbd.repository.TeamRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

/**
 * Created by lufei on 2016/10/28.
 */
@Repository("teamRepository")
public class TeamRepositoryImpl extends AbstractMongoRepository<Team, Long> implements TeamRepository {

    private static final Logger LOG = LoggerFactory.getLogger(TeamRepositoryImpl.class);


    @Override
    protected Class<Team> getEntityType() {
        return Team.class;
    }

    @Override
    protected Long getId(Team entity) {
        return entity.getId();
    }


}
