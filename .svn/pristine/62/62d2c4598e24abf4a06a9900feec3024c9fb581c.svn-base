package com.lesports.qmt.sbd.service;

import com.lesports.api.common.CallerParam;
import com.lesports.qmt.sbd.api.dto.TCompetitionSeason;
import com.lesports.qmt.sbd.model.CompetitionSeason;
import com.lesports.qmt.service.support.QmtService;

import java.util.List;

/**
 * User: ellios
 * Time: 15-6-5 : 上午12:13
 */
public interface CompetitionSeasonService extends QmtService<CompetitionSeason, Long> {
    /**
     * 通过赛事id查找赛季
     * @param cid
     * @return
     */
    List<CompetitionSeason> findByCid(long cid);

    public List<CompetitionSeason> findByCid(long cid, String season);

    /**
     * 获取某赛事下最新的赛季
     * @param cid
     * @return
     */
    CompetitionSeason findLatestByCid(long cid);


    /**
     * 根据id批量获取赛季信息
     * @param ids
     * @return
     */
    List<CompetitionSeason> findByIds(List<Long> ids);

    /**
     * 通过赛事id查找赛季
     * @param cid
     * @param caller
     * @return
     */
    List<Long> getSeasonIdsOfCompetition(long cid, CallerParam caller);



    TCompetitionSeason getTCompetitionSeasonById(long id,CallerParam caller);

    /**
     * 获取某赛事下最新的赛季
     * @param cid
     * @return
     */
    TCompetitionSeason getLatestTCompetitionSeasonsByCId(long cid,CallerParam caller);

    /**
     * 获取某个年份的赛季
     * @param cid
     * @param season
     * @return
     */
    TCompetitionSeason getTCompetitionSeasonByCidAndSeason(long cid,String season,CallerParam caller);


    /**
     * 根据id批量获取赛季
     * @param ids
     * @param caller
     * @return
     */
    List<TCompetitionSeason> getTCompetitionSeasonsByIds(List<Long> ids,CallerParam caller);


}
