package com.lesports.qmt.config.client;

import com.lesports.qmt.config.model.Copyright;
import com.lesports.query.InternalQuery;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * Created by zhangxudong@le.com on 2016/11/7.
 */
public class QmtCopyrightInternalApis extends QmtConfigInternalApis {
    public static boolean deleteCopyright(long id) {
        return deleteEntity(id, Copyright.class);
    }

    public static Copyright getCopyrightById(long id) {
        return getEntityById(id, Copyright.class);
    }

    public static List<Copyright> getCopyrightByQuery(InternalQuery query) {
        List<Long> ids = getEntityIdsByQuery(query, Copyright.class);
        if (true == CollectionUtils.isEmpty(ids)) return null;
        return getEntitiesByIds(ids, Copyright.class);
    }


    public static long saveCopyright(Copyright copyright) {
        return saveEntity(copyright, Copyright.class, true);
    }

//    public static List<Copyright> getCopyrightByIds(List<Long> ids) {
//        return getEntitiesByIds(ids, Copyright.class);
//    }

    public static long countCopyrightByQuery(InternalQuery query) {
        return countByQuery(query, Copyright.class);
    }
}
