package com.lesports.qmt.sbd.client;

import com.lesports.api.common.CallerParam;
import com.lesports.qmt.sbd.api.dto.TCompetitorSeasonStat;
import com.lesports.qmt.sbd.api.dto.TCompetitorStat;
import com.lesports.qmt.sbd.api.dto.TDetailMatch;
import com.lesports.qmt.sbd.api.service.GetCompetitorSeasonStatsParam;
import org.apache.commons.collections.CollectionUtils;

import java.util.Collections;
import java.util.List;

/**
 * Created by zhonglin on 2016/12/6.
 */
public class SbdCompetitorSeasonStatApis extends SbdApis {

    /**
     * 按赛季获取参赛者的赛季统计
     *
     * @return
     */
    public static List<TCompetitorSeasonStat> getTCompetitorSeasonStats(long competitorId, long cid, long csid) {
        try {
            GetCompetitorSeasonStatsParam p = new GetCompetitorSeasonStatsParam();
            p.setCompetitorId(competitorId);
            p.setCid(cid);
            p.setCsid(csid);
            List<Long> ids = sbdService.getCompetitorSeasonStatIds(p);
            if (CollectionUtils.isEmpty(ids)) {
                return Collections.EMPTY_LIST;
            }
            return sbdService.getTCompetitorSeasonStatsByIds(ids);
        } catch (Exception e) {
            LOG.error("fail to getTCompetitorSeasonStats. competitorId : {}, cid : {}, csid : {}",
                    competitorId, cid, csid, e);
        }
        return null;
    }

    /**
     * 按赛季获取参赛者的赛季统计
     *
     * @return
     */
    public static List<TCompetitorSeasonStat> getTCompetitorSeasonStats(GetCompetitorSeasonStatsParam p) {
        try {

            List<Long> ids = sbdService.getCompetitorSeasonStatIds(p);
            if (CollectionUtils.isEmpty(ids)) {
                return Collections.EMPTY_LIST;
            }
            return sbdService.getTCompetitorSeasonStatsByIds(ids);
        } catch (Exception e) {
            LOG.error("fail to getTCompetitorSeasonStats. GetCompetitorSeasonStatsParam : {}",
                    p, e);
        }
        return null;
    }

    /**
     * 按赛季获取参赛者的赛季统计
     *
     * @return
     */
    public static List<TCompetitorSeasonStat> getTCompetitorSeasonStats(List<Long> competitorIds, long cid, long csid) {
        try {
            GetCompetitorSeasonStatsParam p = new GetCompetitorSeasonStatsParam();
            p.setCompetitorIds(competitorIds);
            p.setCid(cid);
            p.setCsid(csid);
            List<Long> ids = sbdService.getCompetitorSeasonStatIds(p);
            if (CollectionUtils.isEmpty(ids)) {
                return Collections.EMPTY_LIST;
            }
            return sbdService.getTCompetitorSeasonStatsByIds(ids);
        } catch (Exception e) {
            LOG.error("fail to getTCompetitorSeasonStats. competitorIds : {}, cid : {}, csid : {}",
                    competitorIds, cid, csid, e);
        }
        return null;
    }


    public static List<TCompetitorStat> getCompetitorStatsOfMatch(long mid, CallerParam caller) {
        if (mid <= 0) {
            LOG.warn("fail to getCompetitorStatsOfMatch. illegal match id : {}", mid);
            return Collections.EMPTY_LIST;
        }
        try {
            TDetailMatch tDetailMatch = sbdService.getTDetailMatchById(mid, caller);
            if (null != tDetailMatch && CollectionUtils.isNotEmpty(tDetailMatch.getCompetitorStats())) {
                return tDetailMatch.getCompetitorStats();
            }
        } catch (Exception e) {
            LOG.error("fail to getCompetitorStatsOfMatch. id:{}, caller : {}", mid, caller, e);
        }
        return Collections.EMPTY_LIST;
    }
}
