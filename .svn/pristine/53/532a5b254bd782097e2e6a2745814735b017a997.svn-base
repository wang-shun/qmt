package com.lesports.qmt.config.client;

import com.lesports.qmt.config.model.Menu;
import com.lesports.query.InternalQuery;
import org.apache.thrift.TException;

import java.util.List;

/**
 * Created by denghui on 2016/11/9.
 */
public class QmtConfigMenuInternalApis extends QmtConfigInternalApis {

    public static long saveMenu(Menu menu) {
        return saveMenu(menu, false);
    }

    public static long saveMenu(Menu menu, boolean allowEmpty) {
        return saveEntity(menu, Menu.class, allowEmpty);
    }

    public static boolean deleteMenu(Long id) {
        return deleteEntity(id, Menu.class);
    }

    public static Menu getMenuById(Long id) {
        return getEntityById(id, Menu.class);
    }

    public static List<Menu> getMenusByIds(List<Long> ids) {
        return getEntitiesByIds(ids, Menu.class);
    }

    public static long countMenuByQuery(InternalQuery query) {
        return countByQuery(query, Menu.class);
    }

    public static List<Long> getMenuIdsByQuery(InternalQuery query) {
        return getEntityIdsByQuery(query, Menu.class);
    }

    public static boolean publishMenu(long id) {
        try {
            return configInternalService.publishMenu(id);
        } catch (TException e) {
            LOG.error("fail to publishMenu. id : {}. {}", id, e.getMessage(), e);
        }
        return false;
    }

    public static List<Menu> getMenusByQuery(InternalQuery query) {
        return getEntitiesByQuery(query, Menu.class);
    }
}
