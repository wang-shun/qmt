package com.lesports.qmt.sbd.repository;

import com.lesports.mongo.repository.MongoCrudRepository;
import com.lesports.qmt.sbd.model.CompetitorSeasonStat;


import java.util.List;

/**
 * User: ellios
 * Time: 15-6-7 : 上午9:44
 */
public interface CompetitorSeasonStatRepository extends MongoCrudRepository<CompetitorSeasonStat, Long> {
    List<CompetitorSeasonStat> findByCompetitorId(Long competitorId);

    List<Long> findByCompetitorIdAndCsid(Long competitorId, Long csid);

    CompetitorSeasonStat findOneByCsidAndCompetitorId(Long csid, Long competitorId);

    List<Long> findOldByCompetitorIdAndCsid(Long competitorId, Long csid) ;
}
