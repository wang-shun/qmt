package com.lesports.qmt.resource;

import com.lesports.api.common.CallerParam;
import com.lesports.api.common.PageParam;
import com.lesports.qmt.resource.constants.DataType;
import com.lesports.qmt.resource.core.ComboResource;
import com.lesports.qmt.resource.core.ComboResourceCreater;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * User: ellios
 * Time: 16-12-28 : 上午10:59
 */
public class ComboResourceCreaters {

    private static final Logger LOG = LoggerFactory.getLogger(ComboResourceCreaters.class);

    private static final ComboResourceCreater creater = new QmtComboResourceCreater();

    public static Object getComboResources(Long resourceId, PageParam page, CallerParam caller) {
        return getComboResources(resourceId, page, caller, DataType.VIEW);
    }
    public static Object getComboResources(Long resourceId, PageParam page, CallerParam caller,int dataType) {
        return creater.createComboResources(resourceId, page, caller,dataType);
    }

    public static ComboResource getComboResource(Long resourceId, PageParam page, CallerParam caller) {
        return creater.createComboResource(resourceId, page, caller);
    }

}
