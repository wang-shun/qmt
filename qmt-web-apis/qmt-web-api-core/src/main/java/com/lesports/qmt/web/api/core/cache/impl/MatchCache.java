package com.lesports.qmt.web.api.core.cache.impl;

import com.lesports.LeConstants;
import com.lesports.api.common.CallerParam;
//import com.lesports.sms.api.vo.TCompetitorStat;
//import com.lesports.sms.api.vo.TTotalCompetition;
//import com.lesports.sms.api.web.core.cache.AbstractCache;
//import com.lesports.sms.api.web.core.vo.GetRealtimeCacheParam;
//import com.lesports.sms.api.web.core.vo.Squad;
import com.lesports.qmt.sbd.api.dto.TCompetitorStat;
import com.lesports.qmt.sbd.api.dto.TTotalCompetition;
import com.lesports.qmt.web.api.core.cache.AbstractCache;
import com.lesports.qmt.web.api.core.vo.GetRealtimeCacheParam;
import com.lesports.qmt.web.api.core.vo.Squad;
import com.lesports.utils.Utf8KeyCreater;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by lufei1 on 2015/7/6.
 */
@Component
public class MatchCache extends AbstractCache {
    private static final Logger LOG = LoggerFactory.getLogger(MatchCache.class);
    private static final Utf8KeyCreater<Long> MATCH_SQUADS_CREATE = new Utf8KeyCreater<>("V2_MATCH_SQUADS_");
    private static final Utf8KeyCreater<Long> MATCH_COMPETITOR_STATS_CREATE = new Utf8KeyCreater<>("V2_MATCH_COMPETITOR_STATS_");
    private static final Utf8KeyCreater<Long> MATCH_OLY_COMPETITION_CREATE = new Utf8KeyCreater<>("V2_OLY_MATCH_COMPETITION_");
    private static final int EXPIRE_TIME = 60 * 30;
    private static final int EXPIRE_TIME_DAY = 60 * 60 * 24;

    public List<Squad> getMatchSquads(GetRealtimeCacheParam param) {
        String key = param.getCacheKey(MATCH_SQUADS_CREATE);
        try {
            return findByKey(key, List.class);
        } catch (Exception e) {
            LOG.error("get match squads from cache : {}, {}", key, e.getMessage(), e);
        }
        return null;
    }

    public boolean saveMatchSquads(GetRealtimeCacheParam param, List<Squad> squads) {
        String key = param.getCacheKey(MATCH_SQUADS_CREATE);

        try {
            LOG.info("save match squads : {} in cbase.", key);
            return save(key, squads, EXPIRE_TIME);
        } catch (Exception e) {
            LOG.error("save match squads to cache : {}, {}", key, e.getMessage(), e);
        }
        return false;
    }

    public List<TTotalCompetition> getOlyTotalList(long countryId, long cid, CallerParam caller, int allDateFlag) {
        String key = this.getCacheKeyByCid(MATCH_OLY_COMPETITION_CREATE, cid, countryId, caller, allDateFlag);
        try {
            return findByKey(key, List.class);
        } catch (Exception e) {
            LOG.error("getOlyTotalList from cache : {}, {}", key, e.getMessage(), e);
        }
        return null;
    }

    public boolean saveOlyTotalList(List<TTotalCompetition> resultList, long countryId, long cid, CallerParam caller, int allDateFlag) {
        String key = this.getCacheKeyByCid(MATCH_OLY_COMPETITION_CREATE, cid, countryId, caller, allDateFlag);

        try {
            LOG.info("saveOlyTotalList : {} in cbase.", key);
            return save(key, resultList, EXPIRE_TIME);
        } catch (Exception e) {
            LOG.error("saveOlyTotalList to cache : {}, {}", key, e.getMessage(), e);
        }
        return false;
    }

    private String getCacheKeyByCid(Utf8KeyCreater<Long> keyCreater, long countryId,
                                    long cid, CallerParam caller, int allDateFlag) {
        StringBuilder keyBuilder = new StringBuilder(keyCreater.textKey(cid));
        keyBuilder.append(LeConstants.UNDERLINE);
        if (countryId > 0) {
            keyBuilder.append(countryId).append(LeConstants.UNDERLINE);
        }
        keyBuilder.append(allDateFlag).append(LeConstants.UNDERLINE);
        /*keyBuilder.append(caller.getCallerId()).append(LeConstants.UNDERLINE);
        keyBuilder.append(caller.getCountry()).append(LeConstants.UNDERLINE);
        keyBuilder.append(caller.getLanguage()).append(LeConstants.UNDERLINE);*/
        return keyBuilder.toString();
    }

    public List<TCompetitorStat> getCompetitorStats(GetRealtimeCacheParam param) {
        String key = param.getCacheKey(MATCH_COMPETITOR_STATS_CREATE);

        try {
            return findByKey(key, List.class);
        } catch (Exception e) {
            LOG.error("get match competitor stats from cache : {}, {}", key, e.getMessage(), e);
        }
        return null;
    }

    public boolean saveCompetitorStats(GetRealtimeCacheParam param, List<TCompetitorStat> competitorStats) {
        String key = param.getCacheKey(MATCH_COMPETITOR_STATS_CREATE);

        try {
            LOG.info("save match competitor stats : {} in cbase.", key);
            return save(key, competitorStats, EXPIRE_TIME);
        } catch (Exception e) {
            LOG.error("save match competitor stats to cache : {}, {}", key, e.getMessage(), e);
        }
        return false;
    }
}
