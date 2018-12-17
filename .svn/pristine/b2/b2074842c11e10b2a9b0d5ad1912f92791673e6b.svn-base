package com.lesports.qmt.msg.cache;

import me.ellios.memcached.HedwigMemcachedClientFactory;
import me.ellios.memcached.MemcachedOp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

/**
 * Created by pangchuanxiao on 2015/6/30.
 */
@Repository
public class NgxApiMemCache extends AbstractMemCache{
    private static final Logger LOG = LoggerFactory.getLogger(NgxApiMemCache.class);
    public static MemcachedOp MEMCACHED_CLIENT;

    @Override
    MemcachedOp memcachedOp() {
        if (MEMCACHED_CLIENT == null) {
            synchronized (MEMCACHED_CLIENT) {
                MEMCACHED_CLIENT = HedwigMemcachedClientFactory.getMemcachedClient("ngx_sms_cache");
            }
        }
        return MEMCACHED_CLIENT;
    }

    @Override
    Logger logger() {
        return LOG;
    }

    @Override
    String memcachedName() {
        return "BEIJING-HP-API-NGINX";
    }
}
