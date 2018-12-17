package com.lesports.qmt.sbd.client;

import com.lesports.api.common.CallerParam;
import com.lesports.api.common.PageParam;
import com.lesports.qmt.sbd.api.dto.TTeam;
import com.lesports.qmt.sbd.api.service.GetTeams4SimpleSearchParam;
import com.lesports.qmt.sbd.api.service.GetTeamsOfSeasonParam;
import org.apache.commons.collections.CollectionUtils;

import java.util.Collections;
import java.util.List;

/**
 * Created by zhonglin on 2016/12/6.
 */
public class SbdTeamApis  extends SbdApis {

    public static TTeam getTTeamById(long id, CallerParam caller) {
        try {
            return sbdService.getTTeamById(id, caller);
        } catch (Exception e) {
            LOG.error("fail to getTTeamById. id:{}, caller : {}", id, caller, e);
        }
        return null;
    }


    public static TTeam getTTeamByCampId(long id, CallerParam caller) {
        try {
            return sbdService.getTTeamByCampId(id, caller);
        } catch (Exception e) {
            LOG.error("fail to getTTeamByCampId. id:{}, caller : {}", id, caller, e);
        }
        return null;
    }


    public static List<TTeam> getTTeamsByIds(List<Long> ids, CallerParam caller) {
        try {
            return sbdService.getTTeamsByIds(ids, caller);
        } catch (Exception e) {
            LOG.error("fail to getTTeamsByIds. ids:{}, caller : {}", ids, caller, e);
        }
        return Collections.EMPTY_LIST;
    }


    public static List<TTeam> getTeams4SimpleSearch(GetTeams4SimpleSearchParam p, PageParam page, CallerParam caller) {
        try {
            List<Long> ids = sbdService.getTeamIds4SimpleSearch(p, page, caller);
            if (CollectionUtils.isEmpty(ids)) {
                return Collections.EMPTY_LIST;
            }
            return getTTeamsByIds(ids, caller);
        } catch (Exception e) {
            LOG.error("fail to getTeams4SimpleSearch. p:{}, page:{}, caller : {}", p, page, caller, e);
        }
        return Collections.EMPTY_LIST;
    }


    public static List<TTeam> getTeamsOfSeason(GetTeamsOfSeasonParam p, PageParam page, CallerParam caller) {
        try {
            List<Long> ids = sbdService.getTeamIdsOfSeason(p, page, caller);
            if (CollectionUtils.isEmpty(ids)) {
                return Collections.EMPTY_LIST;
            }
            return getTTeamsByIds(ids, caller);
        } catch (Exception e) {
            LOG.error("fail to getTeamsOfSeason. p:{}, page:{}, caller : {}", p, page, caller, e);
        }
        return Collections.EMPTY_LIST;
    }
}
