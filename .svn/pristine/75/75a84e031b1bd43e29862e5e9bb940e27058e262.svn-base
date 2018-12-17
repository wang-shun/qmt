package com.lesports.qmt.cms.internal.client;

import com.lesports.qmt.cms.internal.client.support.QmtCmsInternalBaseApis;
import com.lesports.qmt.cms.model.Column;
import com.lesports.qmt.cms.model.Layout;
import com.lesports.query.InternalQuery;

import java.util.List;

/**
 * User: ellios
 * Time: 16-12-1 : 下午6:38
 */
public class CmsColumnInternalApis extends QmtCmsInternalBaseApis {

    public static List<Column> getColumnsByIds(List<Long> ids) {
        return getEntitiesByIds(ids, Column.class);
    }

    public static long saveColumn(Column column) {
        return saveEntity(column, Column.class);
    }

    public static Column getColumnById(Long id) {
        return getEntityById(id, Column.class);
    }

    public static boolean deleteColumn(Long id) {
        return deleteEntity(id, Column.class);
    }


    public static List<Column> getColumnsByQuery(InternalQuery query) {
        return getEntitiesByQuery(query, Column.class);
    }

    public static long countColumnsByQuery(InternalQuery query) {
        return countByQuery(query, Column.class);
    }
}
