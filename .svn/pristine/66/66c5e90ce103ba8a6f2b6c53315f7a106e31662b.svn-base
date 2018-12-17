package com.lesports.qmt.sbd.client;

import com.lesports.api.common.CallerParam;
import com.lesports.api.common.PageParam;
import com.lesports.qmt.sbd.api.dto.TCompetition;
import com.lesports.qmt.sbd.api.service.GetCompetitionsParam;
import org.apache.commons.collections.CollectionUtils;

import java.util.Collections;
import java.util.List;

/**
 * Created by zhonglin on 2016/12/6.
 */
public class SbdCompetitionApis extends SbdApis {

    public static TCompetition getTCompetitionById(long id, CallerParam caller) {
        try {
            return sbdService.getTCompetitionById(id, caller);
        } catch (Exception e) {
            LOG.error("fail to getCompetitionById. id:{}, caller : {}", id, caller, e);
        }
        return null;
    }


    public static TCompetition getTCompetitionByCode(String code, CallerParam caller) {
        try {
            return sbdService.getTCompetitionByCode(code, caller);
        } catch (Exception e) {
            LOG.error("fail to getTCompetitionByCode. code:{}, caller : {}", code, caller, e);
        }
        return null;
    }


    public static List<TCompetition> getTCompetitionByIds(List<Long> ids, CallerParam caller) {
        try {
            return sbdService.getTCompetitionByIds(ids, caller);
        } catch (Exception e) {
            LOG.error("fail to getCompetitionByIds. ids:{}, caller : {}", ids, caller, e);
        }
        return Collections.EMPTY_LIST;
    }

    public static List<TCompetition> getTCompetitons4SimpleSearch(GetCompetitionsParam p, PageParam page, CallerParam caller) {
        try {
            List<Long> ids = sbdService.getTCompetitonIds4SimpleSearch(p, page, caller);
            if (CollectionUtils.isEmpty(ids)) {
                return Collections.EMPTY_LIST;
            }
            return getTCompetitionByIds(ids, caller);
        } catch (Exception e) {
            LOG.error("fail to getTCompetitons4SimpleSearch. p:{}, page:{},caller : {}", p, page, caller, e);
        }
        return Collections.EMPTY_LIST;
    }
}
