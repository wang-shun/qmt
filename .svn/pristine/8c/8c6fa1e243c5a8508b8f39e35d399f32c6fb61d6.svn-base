package com.lesports.qmt.cms.internal.client;

import com.lesports.qmt.cms.internal.client.support.QmtCmsInternalBaseApis;
import com.lesports.qmt.cms.model.ColumnPage;
import com.lesports.query.InternalCriteria;
import com.lesports.query.InternalQuery;

import java.util.List;

/**
 * User: ellios
 * Time: 16-12-1 : 下午6:40
 */
public class CmsColumnPageInternalApis extends QmtCmsInternalBaseApis {

    public static List<ColumnPage> getColumnPagesByIds(List<Long> ids) {
        return getEntitiesByIds(ids, ColumnPage.class);
    }

    public static long saveColumnPage(ColumnPage page) {
        return saveEntity(page, ColumnPage.class);
    }

    public static ColumnPage getColumnPageById(Long id) {
        return getEntityById(id, ColumnPage.class);
    }

    public static boolean deleteColumnPage(Long id) {
        return deleteEntity(id, ColumnPage.class);
    }

    public static List<ColumnPage> getColumnPagesByQuery(InternalQuery query) {
        return getEntitiesByQuery(query, ColumnPage.class);
    }

    public static long countNewsByQuery(InternalQuery query) {
        return countByQuery(query, ColumnPage.class);
    }

    /**
     * 获取column下的页面
     *
     * @param columnId
     * @return
     */
    public static List<ColumnPage> getColumnPagesByColumnId(long columnId) {
        InternalQuery query = new InternalQuery();
        query.addCriteria(InternalCriteria.where("column_id").is(columnId));
        query.addCriteria(InternalCriteria.where("deleted").is(false));
        return getEntitiesByQuery(query, ColumnPage.class);
    }


}
