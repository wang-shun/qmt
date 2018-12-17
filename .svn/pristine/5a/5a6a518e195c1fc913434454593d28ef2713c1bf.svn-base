package com.lesports.qmt.sbd;

import com.lesports.qmt.sbd.api.common.CompetitorType;
import com.lesports.qmt.sbd.api.common.ScopeType;
import com.lesports.qmt.sbd.model.TopList;
import com.lesports.query.InternalCriteria;
import com.lesports.query.InternalQuery;
import org.apache.thrift.TException;

import java.util.List;

/**
 * Created by lufei1 on 2016/11/2.
 */
public class SbdTopListInternalApis extends SbdInternalApis {

    public static long saveTopList(TopList competition) {
        return saveEntity(competition, TopList.class);
    }

    public static boolean deleteTopList(Long id) {
        return deleteEntity(id, TopList.class);
    }

    public static TopList getTopListById(Long id) {
        return getEntityById(id, TopList.class);
    }

    public static List<TopList> getTopListsByIds(List<Long> ids) {
        return getEntitiesByIds(ids, TopList.class);
    }

    public static List<TopList> getTopListsByQuery(InternalQuery params) {
        return getEntitiesByQuery(params, TopList.class);
    }


    public static long countTopListsByQuery(InternalQuery params) {
        return countEntitiesByQuery(params, TopList.class);
    }

    public static boolean deleteTopListItem(Long id, TopList.TopListItem topListItem) {
        try {
            sbdInternalService.deleteTopListItem(id, serialize(topListItem));
            return true;
        } catch (TException e) {
            LOG.error("fail to deleteTopListItem. id : {}, item : {}. {}", id, topListItem, e.getMessage(), e);
        }
        return false;
    }

    public static List<TopList> getTopListsByCsidAndScopeAndCompetitorType(Long csid, Long type, Long scope, CompetitorType competitorType) {
        InternalQuery query = new InternalQuery();
        query.addCriteria(new InternalCriteria("deleted", "eq", false));
        query.addCriteria(new InternalCriteria("csid", "eq", csid));
        query.addCriteria(new InternalCriteria("type", "eq", type));
        query.addCriteria(new InternalCriteria("scope", "eq", scope));
        query.addCriteria(new InternalCriteria("competitor_type", "eq", csid));
        return getTopListsByQuery(query);

    }

    public static List<TopList> getTopListsByCsidAndType(Long csid, Long type) {
        InternalQuery query = new InternalQuery();
        query.addCriteria(new InternalCriteria("deleted", "eq", false));
        query.addCriteria(new InternalCriteria("csid", "eq", csid));
        query.addCriteria(new InternalCriteria("type", "eq", type));
        return getTopListsByQuery(query);

    }

    public static List<TopList> getTopListsByCsidAndTypeAndGroup(Long csid, Long type, Long group) {
        InternalQuery query = new InternalQuery();
        query.addCriteria(new InternalCriteria("deleted", "eq", false));
        query.addCriteria(new InternalCriteria("csid", "eq", csid));
        query.addCriteria(new InternalCriteria("type", "eq", type));
        query.addCriteria(new InternalCriteria("group", "eq", group));
        return getTopListsByQuery(query);

    }
}
