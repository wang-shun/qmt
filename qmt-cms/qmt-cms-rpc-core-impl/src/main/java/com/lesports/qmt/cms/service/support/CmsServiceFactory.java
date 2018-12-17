package com.lesports.qmt.cms.service.support;

import com.lesports.qmt.cms.model.*;
import com.lesports.qmt.cms.service.*;
import com.lesports.qmt.exception.NoServiceException;
import com.lesports.qmt.service.support.QmtService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * trunk.
 *
 * @author pangchuanxiao
 * @since 2016/3/29
 */
@Component("cmsServiceFactory")
public class CmsServiceFactory {
    @Resource
    private LayoutService layoutService;
    @Resource
    private ColumnPageService columnPageService;
    @Resource
    private WidgetService widgetService;
    @Resource
    private ColumnService columnService;
    @Resource
    private MapperService mapperService;


    public QmtService getService(Class clazz) throws NoServiceException {
        if (Layout.class == clazz) {
            return layoutService;
        } else if (ColumnPage.class == clazz) {
            return columnPageService;
        } else if (Widget.class == clazz) {
            return widgetService;
        } else if (Column.class == clazz) {
            return columnService;
        } else if (Mapper.class == clazz) {
            return mapperService;
        }
        throw new NoServiceException("No service for class : " + clazz);
    }
}
