package com.lesports.qmt.sbd.repository.impl;

import com.lesports.mongo.repository.support.AbstractMongoRepository;
import com.lesports.qmt.sbd.model.CompetitionSeason;
import com.lesports.qmt.sbd.repository.CompetitionSeasonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

/**
 * Created by yangyu on 2015/5/29.
 */
@Repository("competitionSeasonRepository")
public class CompetitionSeasonRepositoryImpl extends AbstractMongoRepository<CompetitionSeason, Long> implements CompetitionSeasonRepository {

    private static final Logger LOG = LoggerFactory.getLogger(CompetitionSeasonRepositoryImpl.class);

    @Override
    protected Class<CompetitionSeason> getEntityType() {
        return CompetitionSeason.class;
    }

    @Override
    protected Long getId(CompetitionSeason entity) {
        return entity.getId();
    }

    @Override
    public List<CompetitionSeason> findByCid(long cid) {
        Query query = query(where("deleted").is(false));
        query.addCriteria(where("cid").is(cid));
        return findByQuery(query);
    }

    @Override
    public List<CompetitionSeason> findByCid(long cid, String season) {
        Query query = query(where("deleted").is(false));
        query.addCriteria(where("cid").is(cid));
        query.addCriteria(where("season").regex(season));
        return findByQuery(query);
    }

    /**
     * 请使用 competitionSeasonHelper 中的 getLatestSeasonByCid()
     *
     * @param cid
     * @return
     */
    @Override
    public CompetitionSeason findLatestByCid(long cid) {
        Query q = query(where("deleted").is(false));
        q.addCriteria(where("cid").is(cid));
        q.with(new Sort(Sort.Direction.DESC, "season"));
        return findOneByQuery(q);
    }
}
