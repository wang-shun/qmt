package com.lesports.qmt.web.api.core.cache;

import me.ellios.memcached.HedwigMemcachedClientFactory;
import me.ellios.memcached.MemcachedOp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

/**
 * Created by pangchuanxiao on 2015/6/30.
 */
public abstract class AbstractCache {
    private static final Logger LOG = LoggerFactory.getLogger(AbstractCache.class);
    public static final MemcachedOp MEMCACHED_CLIENT = HedwigMemcachedClientFactory.getMemcachedClient("web_qmt_cache");

    public <T> T findByKey(String key, Class<T> clazz) {
        T result = MEMCACHED_CLIENT.get(key);
        if (null == result) {
            LOG.warn("key {} miss in cache.", key);
        }
        return result;
    }

   public boolean delete(String key) {
        return MEMCACHED_CLIENT.delete(key);
    }

    public <T> boolean save(String key, T entity) {
        return save(key, entity, 0);
    }

    public <T> boolean save(String key, T entity, int expire) {
        Assert.notNull(entity);
        return MEMCACHED_CLIENT.set(key, expire, entity);
    }

}
