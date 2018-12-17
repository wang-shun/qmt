package com.lesports.qmt.web.api.core.cache.impl;

import com.lesports.qmt.web.api.core.cache.AbstractCache;
import com.lesports.utils.KeyAssembleUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by lufei1 on 2016/3/17.
 */
@Component
public class FallbackCache extends AbstractCache {

    private static final Logger LOG = LoggerFactory.getLogger(AbstractCache.class);

    public <T> boolean save(String from, String method, Map<String, String> params, T entity) {
        String key = getKey(from, method, params);
        LOG.info("save fallback key : {}", key);
        return save(key, entity, 0);
    }

    public <T> boolean save(String from, String method, Map<String, String> params, T entity,int expire) {
        String key = getKey(from, method, params);
        LOG.info("save fallback key : {}", key);
        return save(key, entity, 0);
    }


    public <T> T findByKey(String from, String method, Map<String, String> params, Class<T> clazz) {
        String key = getKey(from, method, params);
        LOG.info("get fallback key : {}", key);
        T result = MEMCACHED_CLIENT.get(key);
        if (null == result) {
            LOG.warn("key {} miss in cache.", key);
        }
        return result;
    }


    private String getKey(String from, String method, Map<String, String> params) {
        return KeyAssembleUtils.genKey(from, method, params);
    }
}
