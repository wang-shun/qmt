package com.lesports.qmt.sbd.client;

import com.google.common.collect.Lists;
import com.lesports.api.common.CallerParam;
import com.lesports.api.common.PageParam;
import com.lesports.qmt.sbd.api.dto.TPlayer;
import com.lesports.qmt.sbd.api.service.GetPlayers4SimpleSearchParam;
import org.apache.commons.collections.CollectionUtils;


import java.util.Collections;
import java.util.List;


/**
 * Created by zhonglin on 2016/12/6.
 */
public class SbdPlayerApis extends SbdApis {

    public static TPlayer getTPlayerById(long id, CallerParam caller) {
        try {
            return sbdService.getTPlayerById(id, caller);
        } catch (Exception e) {
            LOG.error("fail to getTPlayerById. id:{}, caller : {}", id, caller, e);
        }
        return null;
    }


    public static List<TPlayer> getTPlayersByIds(List<Long> ids, CallerParam caller) {
        try {
            return sbdService.getTPlayersByIds(ids, caller);
        } catch (Exception e) {
            LOG.error("fail to getTPlayersByIds. ids:{}, caller : {}", ids, caller, e);
        }
        return Collections.EMPTY_LIST;
    }


    public static List<TPlayer> getPlayers4SimpleSearch(GetPlayers4SimpleSearchParam p, PageParam page, CallerParam caller) {
        try {
            List<Long> ids = sbdService.getPlayerIds4SimpleSearch(p, page, caller);
            if (CollectionUtils.isEmpty(ids)) {
                return Collections.EMPTY_LIST;
            }
            return getTPlayersByIds(ids, caller);
        } catch (Exception e) {
            LOG.error("fail to getPlayers4SimpleSearch. p:{}, page:{}, caller : {}", p, page, caller, e);
        }
        return Collections.EMPTY_LIST;
    }
}
