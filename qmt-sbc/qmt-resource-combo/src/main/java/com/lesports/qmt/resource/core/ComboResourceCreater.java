package com.lesports.qmt.resource.core;

import com.lesports.api.common.CallerParam;
import com.lesports.api.common.PageParam;

import java.util.List;

/**
 * User: ellios
 * Time: 16-12-28 : 下午5:13
 */
public interface ComboResourceCreater {

    /**
     * 获取combo resource
     * @param resourceId
     * @param page
     * @param caller
     * @param dataType  数据类型，是预发布的还是已经发布的   1是已经发布的  2是预发布
     * @return
     */
    Object createComboResources(Long resourceId, PageParam page, CallerParam caller,int dataType);

    Object createComboResources(Long resourceId, PageParam page, CallerParam caller);

    ComboResource createComboResource(Long resourceId, PageParam page, CallerParam caller);
}
