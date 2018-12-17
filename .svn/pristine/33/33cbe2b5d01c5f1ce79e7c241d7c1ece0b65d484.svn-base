package com.lesports.qmt.web.utils;

import com.lesports.LeConstants;
import me.ellios.memcached.HedwigMemcachedClientFactory;
import me.ellios.memcached.MemcachedOp;
import org.apache.commons.lang3.StringUtils;

/**
 * 通用的kv memcached键值访问工具类
 * User: ellios
 * Time: 15-8-24 : 上午11:23
 */
public class KVMemcachedUtils {

    private static final MemcachedOp MEMCACHED_OP = HedwigMemcachedClientFactory.getMemcachedClient("page_qmt_cache");

    //默认缓存1小时
    private static final int DEFAULT_EXPIRE_TIME = 3600;

    public static Object get(String key) {
        if (StringUtils.isEmpty(key)) {
            return null;
        }
        return MEMCACHED_OP.get(key);
    }

    public static boolean set(String key, Object obj, int exp) {
        if (StringUtils.isEmpty(key) || obj == null) {
            return false;
        }
        if (exp <= 0) {
            exp = DEFAULT_EXPIRE_TIME;
        }
        return MEMCACHED_OP.set(key, exp, obj, LeConstants.DEFAULT_OP_TIMEOUT_SECONDS);
    }

    public static boolean delete(String key) {
        return MEMCACHED_OP.delete(key);
    }
}
