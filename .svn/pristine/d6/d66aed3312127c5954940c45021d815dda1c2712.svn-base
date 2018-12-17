package com.lesports.qmt.sbc.client;

import com.lesports.qmt.sbc.model.QmtResource;
import com.lesports.qmt.sbc.model.QmtResourceOnline;
import com.lesports.qmt.sbc.model.ResourceContent;
import com.lesports.qmt.sbc.model.ResourceContentOnline;
import com.lesports.query.InternalQuery;

import java.util.List;

/**
 * User: ellios
 * Time: 16-10-28 : 下午8:55
 */
public class QmtSbcResourceInternalApis extends QmtSbcInternalApis {

    public static long saveResource(QmtResource resource) {
        return saveEntity(resource, QmtResource.class,true);
    }

    public static long saveResourceOnline(QmtResourceOnline resourceOnline) {
        return saveEntity(resourceOnline, QmtResourceOnline.class,true);
    }

    public static List<QmtResourceOnline> findResourceOnlineByQuery(InternalQuery query) {
        return getEntitiesByQuery(query, QmtResourceOnline.class);
    }

    public static long saveResourceContent(ResourceContent resourceContent) {
        return saveEntity(resourceContent, ResourceContent.class,true);
    }

    public static long saveResourceContentOnline(ResourceContentOnline resourceContentOnline) {
        return saveEntity(resourceContentOnline, ResourceContentOnline.class,true);
    }

    public static QmtResource getResourceById(Long id) {
        return getEntityById(id, QmtResource.class);
    }

    public static List<QmtResource> getResourceListByIds(List<Long> ids) {
        return getEntitiesByIds(ids, QmtResource.class);
    }

    public static ResourceContent getResourceContentById(Long resourceId) {
        return getEntityById(resourceId, ResourceContent.class);
    }

    public static List<QmtResource> getResourcesByQuery(InternalQuery query) {
        return getEntitiesByQuery(query, QmtResource.class);
    }

    public static boolean deleteResource(Long id) {
        return deleteEntity(id, QmtResource.class);
    }

    public static boolean deleteResourceContent(Long id) {
        return deleteEntity(id, ResourceContent.class);
    }

    public static boolean changeResourceContentOrder(Long resourceContentId,int increment){
        return changeOrder(resourceContentId,increment,ResourceContent.class);
    }

    public static long getResourceCountByQuery(InternalQuery query) {
        return countByQuery(query, QmtResource.class);
    }
    public static List<QmtResource> getResourceByQuery(InternalQuery query) {
        return getEntitiesByQuery2(query, QmtResource.class);
    }
    public static List<ResourceContent> getResourceContentByQuery(InternalQuery query) {
        return getEntitiesByQuery(query, ResourceContent.class);
    }
}
