package com.lesports.qmt.sbd.repository;

import com.lesports.mongo.repository.MongoCrudRepository;
import com.lesports.qmt.sbd.model.CompetitionSeason;

import java.util.List;

/**
 * Created by lufei on 2016/10/24.
 */
public interface CompetitionSeasonRepository extends MongoCrudRepository<CompetitionSeason, Long> {


    List<CompetitionSeason> findByCid(long cid);

    /**
     * 正则查找某赛事下地赛季
     *
     * @param cid
     * @param season
     * @return
     */
    List<CompetitionSeason> findByCid(long cid, String season);

    /**
     * 获取某赛事下最新的赛季
     *
     * @param cid
     * @return
     */
    CompetitionSeason findLatestByCid(long cid);
}
