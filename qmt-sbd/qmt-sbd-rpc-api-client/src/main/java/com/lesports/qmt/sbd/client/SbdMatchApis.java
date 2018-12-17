package com.lesports.qmt.sbd.client;


import com.lesports.api.common.CallerParam;
import com.lesports.api.common.MatchStatus;
import com.lesports.api.common.PageParam;
import com.lesports.qmt.sbd.api.dto.TCompetitorStat;
import com.lesports.qmt.sbd.api.dto.TDetailMatch;
import com.lesports.qmt.sbd.api.dto.TMatch;
import com.lesports.qmt.sbd.api.dto.TMatchStats;
import com.lesports.qmt.sbd.api.service.GetPlayerMatchesParam;
import org.apache.commons.collections.CollectionUtils;

import java.util.Collections;
import java.util.List;

/**
 * Created by zhonglin on 2016/12/9.
 */
public class SbdMatchApis extends SbdApis {

    public static List<TMatch> getTMatchesByIds(List<Long> ids, CallerParam caller) {
        try {
            return sbdService.getTMatchesByIds(ids, caller);
        } catch (Exception e) {
            LOG.error("fail to getMatchesByIds. ids:{}, caller : {}", ids, caller, e);
        }
        return Collections.EMPTY_LIST;
    }


    /**
     * 获取比赛详情
     */

    public static TDetailMatch getTDetailMatchById(long id, CallerParam caller) {

        try {
            return sbdService.getTDetailMatchById(id, caller);
        } catch (Exception e) {
            LOG.error("fail to getDetailMatchById. id:{}, caller : {}", id, caller, e);
        }
        return null;
    }

    /**
     * 获取比赛基本信息
     */

    public static TMatch getTMatchById(long id, CallerParam caller) {
        if (id <= 0) {
            LOG.warn("fail to getTMatchById. illegal match id : {}", id);
            return null;
        }
        try {
            return sbdService.getTMatchById(id, caller);
        } catch (Exception e) {
            LOG.error("fail to getDetailMatchById. id:{}, caller : {}", id, caller, e);
        }
        return null;
    }

    /**
     * 获取比赛基本信息
     */

    public static TMatchStats getTMatchStatsById(long id, CallerParam caller) {
        if (id <= 0) {
            LOG.warn("fail to getTMatchById. illegal match id : {}", id);
            return null;
        }
        try {
            return sbdService.getTMatchStatsById(id, caller);
        } catch (Exception e) {
            LOG.error("fail to getTMatchStatsById. id:{}, caller : {}", id, caller, e);
        }
        return null;
    }


    public static List<TDetailMatch> getTDetailMatchesByPid(long pid, MatchStatus status, PageParam page, CallerParam caller) {
        try {
            return sbdService.getTDetailMatchesByPid(pid, status, page, caller);
        } catch (Exception e) {
            LOG.error("fail to getTDetailMatchesByPid.", e);
            throw new RuntimeException("fail to getTDetailMatchesByPid.");
        }
    }

    public static List<TDetailMatch> getTDetailMatches(GetPlayerMatchesParam p, PageParam page, CallerParam caller) {
        try {
            return sbdService.getTDetailMatches(p, page, caller);
        } catch (Exception e) {
            LOG.error("fail to getTDetailMatches.", e);
            throw new RuntimeException("fail to getTDetailMatches.");
        }
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

    public static List<Long> getMatchIdsByCidAndMetaEntryId(long cid, long csid, long entryId, PageParam pageParam) {
        try {
            return sbdService.getMatchIdsByCidAndMetaEntryId(cid, csid, entryId, pageParam);
        } catch (Exception e) {
            LOG.error("fail to getMatchIdsByCidAndMetaEntryId.", e);
        }
        return Collections.EMPTY_LIST;
    }
}
