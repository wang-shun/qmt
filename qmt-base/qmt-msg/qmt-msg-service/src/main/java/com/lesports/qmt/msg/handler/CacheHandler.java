package com.lesports.qmt.msg.handler;

import com.lesports.qmt.msg.cache.NgxCmsMemCache;
import com.lesports.qmt.msg.cache.SmsMemCache;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Created by zhangdeqiang on 2016/11/10.
 */
@Component
public class CacheHandler {
    private static final Logger LOG = LoggerFactory.getLogger(CacheHandler.class);

    @Resource
    private SmsMemCache smsMemCache;

    @Resource
    private NgxCmsMemCache ngxCmsMemCache;

    public void addWebCbaseCache(String key, Object entity, int expire) {
        if (StringUtils.isBlank(key)) {
            return;
        }
        smsMemCache.save(key, entity, expire);
    }

    public void deleteWebCbaseCache(String key) {
        if (StringUtils.isBlank(key)) {
            return;
        }
        smsMemCache.delete(key);
    }

    /**
     * 删除cms页面缓存
     * @param key
     */
    public void deleteCmsWebCbaseCache(String key) {
        if (StringUtils.isBlank(key)) {
            return;
        }
        ngxCmsMemCache.delete(key);
    }
}
