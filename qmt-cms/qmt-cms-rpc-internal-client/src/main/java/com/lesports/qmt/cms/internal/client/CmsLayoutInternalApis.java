package com.lesports.qmt.cms.internal.client;

import com.lesports.qmt.cms.internal.client.support.QmtCmsInternalBaseApis;
import com.lesports.qmt.cms.model.ColumnPage;
import com.lesports.qmt.cms.model.Layout;
import com.lesports.query.InternalQuery;

import java.util.List;

/**
 * User: ellios
 * Time: 16-12-1 : 下午6:38
 */
public class CmsLayoutInternalApis extends QmtCmsInternalBaseApis {


    public static List<Layout> getLayoutsByIds(List<Long> ids) {
        return getEntitiesByIds(ids, Layout.class);
    }

    public static long saveLayout(Layout layout) {
        return saveEntity(layout, Layout.class);
    }

    public static Layout getLayoutById(Long id) {
        return getEntityById(id, Layout.class);
    }

    public static boolean deleteLayout(Long id) {
        return deleteEntity(id, Layout.class);
    }

    public static List<Layout> getLayoutsByQuery(InternalQuery query) {
        return getEntitiesByQuery(query, Layout.class);
    }

    public static long countLayoutsByQuery(InternalQuery query) {
        return countByQuery(query, Layout.class);
    }
}
