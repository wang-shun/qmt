package com.lesports.qmt.msg.cache;

import me.ellios.memcached.HedwigMemcachedClientFactory;
import me.ellios.memcached.MemcachedOp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

/**
 * lesports-projects.
 *
 * @author: pangchuanxiao
 * @since: 2015/7/23
 */
@Repository
public class NgxCmsMemCache extends AbstractMemCache{
    private static final Logger LOG = LoggerFactory.getLogger(NgxCmsMemCache.class);
    public static MemcachedOp MEMCACHED_CLIENT;

    @Override
    MemcachedOp memcachedOp() {
        if (MEMCACHED_CLIENT == null) {
            synchronized (NgxCmsMemCache.class) {
                try {
                    MEMCACHED_CLIENT = HedwigMemcachedClientFactory.getMemcachedClient("ngx_cms_cache");
                } catch (Exception e) {
                    LOG.error("Fail to init memcached ngx_cms_cache", e);
                }
            }
        }
        return MEMCACHED_CLIENT;
    }
    @Override
    String memcachedName() {
        return "BEIJING-CMS-NGINX";
    }

    @Override
    Logger logger() {
        return LOG;
    }
}
