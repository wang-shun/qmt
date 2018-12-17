package com.lesports.qmt.sbd.client;

import com.lesports.api.common.CallerParam;
import com.lesports.qmt.sbd.api.dto.TPlayerCareerStat;
import com.lesports.qmt.sbd.api.service.GetPlayerCareerStatParam;

import java.util.List;

/**
 * Created by zhonglin on 2016/12/6.
 */
public class SbdPlayerCarerStatApis extends SbdApis {

    public static List<TPlayerCareerStat> getTPlayerCareerStats(GetPlayerCareerStatParam p, CallerParam caller) {
        try {
            return sbdService.getPlayerCareerStat(p, caller);
        } catch (Exception e) {
            LOG.error("fail to getPlayerCareerStat. p:{}, caller : {}", p, caller, e);
        }
        return null;
    }
}
