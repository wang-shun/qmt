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
public class SmsMemCache extends AbstractMemCache{
    private static final Logger LOG = LoggerFactory.getLogger(SmsMemCache.class);
    public static MemcachedOp MEMCACHED_CLIENT;

    @Override
    MemcachedOp memcachedOp() {
        if (MEMCACHED_CLIENT == null) {
            synchronized (MEMCACHED_CLIENT) {
                try {
                    MEMCACHED_CLIENT = HedwigMemcachedClientFactory.getMemcachedClient("web_sms_cache");
                } catch (Exception e) {
                    LOG.error("Fail to init memcached web_sms_cache", e);
                }
            }
        }
        return MEMCACHED_CLIENT;
    }

    @Override
    String memcachedName() {
        return "BEIJING-SMS-API-WEB";
    }

    @Override
    Logger logger() {
        return LOG;
    }
}
