package com.lesports.qmt.sbd.service;

import com.lesports.qmt.sbd.api.dto.TCompetitorSeasonStat;
import com.lesports.qmt.sbd.api.service.GetCompetitorSeasonStatsParam;
import com.lesports.qmt.sbd.model.CompetitorSeasonStat;
import com.lesports.qmt.service.support.QmtService;

import java.util.List;

/**
 * User: ellios
 * Time: 15-6-7 : 上午10:26
 */
public interface CompetitorSeasonStatService extends QmtService<CompetitorSeasonStat, Long> {

    List<CompetitorSeasonStat> findByCompetitorId(Long competitorId);

    CompetitorSeasonStat findOneByCsidAndCompetitorId(Long csid, Long competitorId);

    List<Long> getCompetitorSeasonStatIds(GetCompetitorSeasonStatsParam p);

    /**
     * 批量获取参赛者赛季统计
     *
     * @param ids
     * @return
     */
    List<TCompetitorSeasonStat> getTCompetitorSeasonStatsByIds(List<Long> ids);

}
