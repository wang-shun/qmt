package com.lesports.qmt.sbd.client;

import com.lesports.api.common.CallerParam;
import com.lesports.qmt.sbd.api.dto.TMatchAction;
import com.lesports.qmt.sbd.api.service.GetMacthActionsParam;
import org.apache.commons.collections.CollectionUtils;

import java.util.Collections;
import java.util.List;

/**
 * Created by zhonglin on 2016/12/13.
 */
public class SbdMatchActionApis extends SbdApis {

    public static List<TMatchAction> getTMatchActionsByIds(List<Long> ids, CallerParam caller) {
        try {
            return sbdService.getTMatchActionsByIds(ids, caller);
        } catch (Exception e) {
            LOG.error("fail to getTMatchActionsByIds. ids:{}, caller : {}", ids, caller, e);
        }
        return Collections.EMPTY_LIST;
    }

    public static TMatchAction getTMatchActionById(long id, CallerParam caller) {
        try {
            return sbdService.getTMatchActionById(id, caller);
        } catch (Exception e) {
            LOG.error("fail to getTMatchActionById. id:{}, caller : {}", id, caller, e);
        }
        return null;
    }


    public static List<TMatchAction> getMatchActionsOfMatch(GetMacthActionsParam p, CallerParam caller) {
        try {
            List<Long> ids = sbdService.getMatchActionsOfMatch(p, caller);
            if (CollectionUtils.isEmpty(ids)) {
                return Collections.EMPTY_LIST;
            }
            return getTMatchActionsByIds(ids, caller);
        } catch (Exception e) {
            LOG.error("fail to getMatchActionsOfMatch. p:{},caller : {}", p, caller, e);
        }
        return Collections.EMPTY_LIST;
    }
}
