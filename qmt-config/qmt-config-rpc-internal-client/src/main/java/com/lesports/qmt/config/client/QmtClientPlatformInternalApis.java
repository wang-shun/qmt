package com.lesports.qmt.config.client;

import com.lesports.qmt.config.model.ClientPlatform;
import com.lesports.query.InternalQuery;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * Created by zhangxudong@le.com on 2016/11/7.
 */
public class QmtClientPlatformInternalApis extends QmtConfigInternalApis {
    public static boolean deleteClientPlatform(long id) {
        return deleteEntity(id, ClientPlatform.class);
    }

    public static ClientPlatform getClientPlatformById(long id) {
        return getEntityById(id, ClientPlatform.class);
    }

    public static List<ClientPlatform> getClientPlatformsByQuery(InternalQuery query) {
        List<Long> ids = getCopyrightIdsByQuery(query);
        if (true == CollectionUtils.isEmpty(ids)) return null;
        return getEntitiesByIds(ids, ClientPlatform.class);
    }

    public static List<ClientPlatform> getClientPlatformsByIds(List<Long> ids) {
        return getEntitiesByIds(ids, ClientPlatform.class);
    }

    public static long countClientPlatformByQuery(InternalQuery query) {
        return countByQuery(query, ClientPlatform.class);
    }

    public static List<Long> getCopyrightIdsByQuery(InternalQuery query) {
        return getEntityIdsByQuery(query, ClientPlatform.class);
    }
}
