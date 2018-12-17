package com.lesports.qmt.config.client;

import com.lesports.qmt.config.model.Caller;
import com.lesports.query.InternalCriteria;
import com.lesports.query.InternalQuery;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;

/**
 * Created by denghui on 2016/11/21.
 */
public class QmtConfigCallerInternalApis extends QmtConfigInternalApis {

    public static long saveCaller(Caller caller) {
        return saveEntity(caller, Caller.class);
    }
    
    public static boolean deleteCaller(Long id) {
        return deleteEntity(id, Caller.class);
    }

    public static Caller getCallerById(Long id) {
        return getEntityById(id, Caller.class);
    }

    public static List<Caller> getCallersByIds(List<Long> ids) {
        return getEntitiesByIds(ids, Caller.class);
    }

    public static List<Long> getCallerIdsByQuery(InternalQuery query) {
        return getEntityIdsByQuery(query, Caller.class);
    }

    public static Caller getCallerBySplatId(long splatId) {
        InternalQuery query = new InternalQuery();
        query.addCriteria(InternalCriteria.where("splat_id").is(splatId));
        List<Long> ids = getCallerIdsByQuery(query);
        if (CollectionUtils.isEmpty(ids)) {
            return null;
        }
        return getCallerById(ids.get(0));
    }
}
