package com.lesports.qmt.cms.internal.client;

import com.lesports.qmt.cms.api.common.PageType;
import com.lesports.qmt.cms.api.common.WidgetType;
import com.lesports.qmt.cms.internal.client.support.QmtCmsInternalBaseApis;
import com.lesports.qmt.cms.internal.client.utils.PageTypeMappingUtils;
import com.lesports.qmt.cms.model.Layout;
import com.lesports.qmt.cms.model.Widget;
import com.lesports.query.InternalCriteria;
import com.lesports.query.InternalQuery;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;

/**
 * User: ellios
 * Time: 16-12-1 : 下午6:40
 */
public class CmsWidgetInternalApis extends QmtCmsInternalBaseApis {

    public static List<Widget> getWidgetsByIds(List<Long> ids) {
        return getEntitiesByIds(ids, Widget.class);
    }

    public static long saveWidget(Widget widget) {
        return saveEntity(widget, Widget.class);
    }

    public static Widget getWidgetById(Long id) {
        return getEntityById(id, Widget.class);
    }

    /**
     * 通过name获取widget
     * @param name
     * @return
     */
    public static Widget getWidgetByName(String name) {
        InternalQuery query = new InternalQuery();
        query.addCriteria(InternalCriteria.where("name").is(name));
        query.with(new PageRequest(0, 1, Sort.Direction.DESC, "create_at"));
        List<Widget> widgets = getEntitiesByQuery(query, Widget.class);
        if(CollectionUtils.isEmpty(widgets)){
            return null;
        }
        return widgets.get(0);
    }

    /**
     * 通过路径获取widget
     * @param path
     * @param pageTypeName
     * @return
     */
    public static Widget getWidgetByPath(String path, String pageTypeName) {
        InternalQuery query = new InternalQuery();
        query.addCriteria(InternalCriteria.where("path").is(path));
        query.with(new PageRequest(0, 1, Sort.Direction.DESC, "create_at"));
        List<Widget> widgets = getEntitiesByQuery(query, Widget.class);
        if(CollectionUtils.isEmpty(widgets)){
            return null;
        }
        for(Widget widget : widgets){
            if(widget.getPageType() == PageTypeMappingUtils.mappingPageType(pageTypeName)){
                return widget;
            }
        }
        return null;
    }

    public static boolean deleteWidget(Long id) {
        return deleteEntity(id, Widget.class);
    }

    public static List<Widget> getWidgetsByQuery(InternalQuery query) {
        return getEntitiesByQuery(query, Widget.class);
    }

    public static long countWidgetsByQuery(InternalQuery query) {
        return countByQuery(query, Widget.class);
    }
}
