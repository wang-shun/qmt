package com.lesports.qmt.sbd.client;

import com.lesports.api.common.CallerParam;
import com.lesports.api.common.PageParam;
import com.lesports.id.api.IdType;
import com.lesports.id.client.LeIdApis;
import com.lesports.qmt.sbd.api.dto.TTeamSeason;
import com.lesports.qmt.sbd.api.service.GetTeamSeasonsParam;
import com.lesports.utils.PageUtils;
import org.apache.commons.collections.CollectionUtils;

import java.util.Collections;
import java.util.List;

/**
 * Created by zhonglin on 2016/12/6.
 */
public class SbdTeamSeasonApis  extends SbdApis {
    public static List<TTeamSeason> getTTeamSeasons(GetTeamSeasonsParam p, PageParam page, CallerParam caller) {
        try {
            List<Long> ids = sbdService.getTeamSeasonIds(p, page, caller);
            if (CollectionUtils.isEmpty(ids)) {
                return Collections.EMPTY_LIST;
            }
            return sbdService.getTTeamSeasonsByIds(ids, caller);
        } catch (Exception e) {

            LOG.error("fail to getTeamSeasons. p : {}, page : {}, caller : {}", p, page, caller, e);
        }
        return null;
    }

    public static List<TTeamSeason> getTTeamSeasonsByIds(List<Long> ids, CallerParam caller) {
        try {
            if (CollectionUtils.isEmpty(ids)) {
                return Collections.EMPTY_LIST;
            }
            return sbdService.getTTeamSeasonsByIds(ids, caller);
        } catch (Exception e) {
            LOG.error("fail to getTTeamSeasonsByIds. ids : {}, caller : {}",ids, caller, e);
        }
        return Collections.EMPTY_LIST;
    }

    public static TTeamSeason getTTeamSeason(long pidOrTid, long csid, CallerParam caller) {
        try {
            GetTeamSeasonsParam p = new GetTeamSeasonsParam();
            IdType type = LeIdApis.checkIdTye(pidOrTid);
            if (type == IdType.PLAYER) {
                p.setPid(pidOrTid);
            } else if (type == IdType.TEAM) {
                p.setTid(pidOrTid);
            } else {
                LOG.warn("fail to getTTeamSeason. illegal pidOrTid : {}", pidOrTid);
                return null;
            }
            p.setCsid(csid);
            List<Long> ids = sbdService.getTeamSeasonIds(p, PageUtils.createPageParam(0, 1), caller);
            if (CollectionUtils.isEmpty(ids)) {
                return null;
            }
            return sbdService.getTTeamSeasonById(ids.get(0), caller);
        } catch (Exception e) {
            LOG.error("fail to getTTeamSeason. pidOrTid : {}, csid : {}, caller : {}", pidOrTid, csid, caller, e);
        }
        return null;
    }
}
