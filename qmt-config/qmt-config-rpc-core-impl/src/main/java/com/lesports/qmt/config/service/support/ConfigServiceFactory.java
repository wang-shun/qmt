package com.lesports.qmt.config.service.support;

import com.lesports.qmt.config.model.*;
import com.lesports.qmt.config.service.*;
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
@Component("configServiceFactory")
public class ConfigServiceFactory {
    @Resource
    private CallerService callerService;
    @Resource
    private TagService tagService;
    @Resource
    private DictService dictService;
    @Resource
    private MenuService menuService;
    @Resource
    private ClientPlatformService clientPlatformService;
    @Resource
    private CopyrightService copyrightService;
    @Resource
    private CountryService countryService;
    @Resource
    private ActivityService activityService;
    @Resource
    private SuggestService suggestService;

    public QmtService getService(Class clazz) throws NoServiceException {
        if (Caller.class == clazz) {
            return callerService;
        } else if (Tag.class == clazz) {
            return tagService;
        } else if (DictEntry.class == clazz) {
            return dictService;
        } else if (Menu.class == clazz) {
            return menuService;
        } else if (ClientPlatform.class == clazz) {
            return clientPlatformService;
        } else if(Copyright.class == clazz) {
            return copyrightService;
        } else if(Country.class == clazz) {
            return countryService;
        } else if(Activity.class == clazz) {
            return activityService;
        } else if(Suggest.class == clazz) {
            return suggestService;
        }
        throw new NoServiceException("No service for class : " + clazz);
    }
}
