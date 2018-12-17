package com.lesports.qmt.sbc.utils;

import me.ellios.jedis.RedisClientFactory;
import me.ellios.jedis.RedisOp;
import org.apache.commons.lang3.StringUtils;

/**
 * 通用的kv键值访问工具类
 * User: ellios
 * Time: 15-8-24 : 上午11:23
 */
public class KVCacheUtils {

    private static final RedisOp REDIS_OP = RedisClientFactory.getRedisClient("rpc_sbc_cache");

    //默认缓存1小时
    private static final int DEFAULT_EXPIRE_TIME = 3600;

    public static Object get(String key) {
        if (StringUtils.isEmpty(key)) {
            return null;
        }
        return REDIS_OP.getObject(key);
    }

    public static void set(String key, Object obj, int exp) {
        if (StringUtils.isEmpty(key) || obj == null) {
            return;
        }
        if (exp <= 0) {
            exp = DEFAULT_EXPIRE_TIME;
        }
        REDIS_OP.setObject(key, obj, exp);
    }

    public static void delete(String key) {
        REDIS_OP.del(key);
    }
}
