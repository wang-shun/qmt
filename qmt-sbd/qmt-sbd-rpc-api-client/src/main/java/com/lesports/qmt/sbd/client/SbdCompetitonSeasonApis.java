package com.lesports.qmt.sbd.client;

import com.lesports.api.common.CallerParam;
import com.lesports.qmt.sbd.api.dto.TCompetitionSeason;
import org.apache.commons.collections.CollectionUtils;

import java.util.Collections;
import java.util.List;

/**
 * Created by zhonglin on 2016/12/6.
 */
public class SbdCompetitonSeasonApis  extends SbdApis {

    public static TCompetitionSeason getTCompetitionSeasonById(long id, CallerParam caller) {

        try {
            return sbdService.getTCompetitionSeasonById(id, caller);
        } catch (Exception e) {
            LOG.error("fail to getTCompetitionSeasonById. id:{}, caller : {}", id, caller, e);
        }
        return null;
    }


    public static List<TCompetitionSeason> getTCompetitionSeasonsByIds(List<Long> ids, CallerParam caller) {
        try {
            return sbdService.getTCompetitionSeasonsByIds(ids, caller);
        } catch (Exception e) {
            LOG.error("fail to getTCompetitionSeasonsByIds. ids:{}, caller : {}", ids, caller, e);
        }
        return Collections.EMPTY_LIST;
    }


    public static List<TCompetitionSeason> getSeasonsOfCompetition(long cid, CallerParam caller) {
        try {
            List<Long> ids = sbdService.getSeasonIdsOfCompetition(cid, caller);
            if (CollectionUtils.isEmpty(ids)) {
                return Collections.EMPTY_LIST;
            }
            return getTCompetitionSeasonsByIds(ids, caller);
        } catch (Exception e) {
            LOG.error("fail to getSeasonsOfCompetition. cid:{}, caller : {}", cid, caller, e);
        }
        return Collections.EMPTY_LIST;

    }


    public static TCompetitionSeason getLatestTCompetitionSeasonsByCid(long cid, CallerParam caller) {
        try {
            return sbdService.getLatestTCompetitionSeasonsByCid(cid, caller);
        } catch (Exception e) {
            LOG.error("fail to getLatestCompetitionSeasonByCid. cid:{}, caller : {}", cid, caller, e);
        }
        return null;
    }

    public static TCompetitionSeason getTCompetitionSeasonByCidAndSeason(long cid, String season, CallerParam caller) {
        try {
            return sbdService.getTCompetitionSeasonByCidAndSeason(cid,season, caller);
        } catch (Exception e) {
            LOG.error("fail to getLatestCompetitionSeasonByCid. cid:{}, season:{} , caller : {}", cid, season, caller, e);
        }
        return null;
    }
}
