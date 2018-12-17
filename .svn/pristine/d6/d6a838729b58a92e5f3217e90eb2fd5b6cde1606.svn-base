package com.lesports.qmt.cms.internal.client.utils;

import com.lesports.qmt.cms.api.common.PageType;
import org.apache.commons.lang3.StringUtils;

/**
 * User: ellios
 * Time: 17-1-11 : 上午10:54
 */
public class PageTypeMappingUtils {

    /**
     * 根据页面类型的名字映射页面类型
     * @param pageTypeName
     * @return
     */
    public static PageType mappingPageType(String pageTypeName){
        pageTypeName = StringUtils.upperCase(pageTypeName);
        PageType pageType = PageType.valueOf(pageTypeName);
        if(pageType == null){
            return PageType.PC;
        }
        return pageType;
    }
}
