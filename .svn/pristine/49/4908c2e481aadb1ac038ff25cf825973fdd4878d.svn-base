package com.lesports.qmt.sbd.repository.impl;

import com.lesports.mongo.repository.support.AbstractMongoRepository;
import com.lesports.qmt.sbd.model.CompetitorSeasonStat;
import com.lesports.qmt.sbd.repository.CompetitorSeasonStatRepository;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

/**
 * User: ellios
 * Time: 15-6-7 : 上午10:07
 */
@Repository("competitorSeasonStatRepository")
public class CompetitorSeasonStatRepositotyImpl extends AbstractMongoRepository<CompetitorSeasonStat, Long>
        implements CompetitorSeasonStatRepository {


    @Override
    protected Class<CompetitorSeasonStat> getEntityType() {
        return CompetitorSeasonStat.class;
    }

    @Override
    protected Long getId(CompetitorSeasonStat entity) {
        return entity.getId();
    }

    @Override
    public List<CompetitorSeasonStat> findByCompetitorId(Long competitorId) {
        Query q = query(where("competitor_id").is(competitorId));

        return findByQuery(q);
    }

    @Override
    public List<Long> findByCompetitorIdAndCsid(Long competitorId,Long csid) {
        Query q = query(where("competitor_id").is(competitorId));
        q.addCriteria(where("csid").is(csid));
        return findIdByQuery(q);
    }

    @Override
    public CompetitorSeasonStat findOneByCsidAndCompetitorId(Long csid, Long competitorId) {
        Query q = query(where("competitor_id").is(competitorId));
        q.addCriteria(where("csid").is(csid));

        return findOneByQuery(q);
    }

    @Override
    public List<Long> findOldByCompetitorIdAndCsid(Long competitorId,Long csid) {
        Query q = query(where("competitor_id").is(competitorId));
        q.addCriteria(where("csid").ne(csid));
        return findIdByQuery(q);
    }
}
