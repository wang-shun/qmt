package com.lesports.qmt.msg.cache;

import com.lesports.qmt.msg.util.SerializeUtil;
import me.ellios.jedis.RedisClientFactory;
import me.ellios.jedis.RedisOp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.Set;

/**
 * User: ellios
 * Time: 13-9-18 : 上午11:28
 */
@Repository
public class RedisCache {
    private static final Logger LOG = LoggerFactory.getLogger(RedisCache.class);

    protected static RedisOp redisOp;

    private static final int MAX_TRY_COUNT = 3;

    /**
     * 获取redis客户端
     *
     * @return
     */
    public static RedisOp getRedisOp() {
        if (redisOp == null) {
            synchronized (redisOp) {
                redisOp = RedisClientFactory.getRedisClient("rpc_sms_cache");
            }
        }
        return redisOp;
    }

    private boolean saveEntity(String key, Object entity, int expire) {
        boolean result = true;
        if (expire > 0) {
            result &= getRedisOp().set(key, SerializeUtil.serialize(entity),expire);
        } else {
            result &= getRedisOp().set(key, SerializeUtil.serialize(entity));
        }
        return result;
    }


    private boolean deleteEntity(String key) {
        boolean result = true;

        result &= getRedisOp().del(key);

        return result;
    }

    /**
     * @param key
     * @param value
     * @param member
     * @return
     */
    public boolean zadd(String key, double value, String member) {
        Assert.notNull(key);
        Assert.notNull(member);
        RedisOp redisOp = getRedisOp();
        return redisOp.zadd(key, value, member);
    }

    /**
     * @param key
     * @param start
     * @param end
     * @return
     */
    public Set<String> zrange(String key, long start, long end) {
        Assert.notNull(key);
        RedisOp redisOp = getRedisOp();
        return redisOp.zrange(key, start, end);
    }

    public Set<String> smembers(String key) {
        Set<String> ids = getRedisOp().smembers(key);
        if (null == ids) {
            return null;
        }
        LOG.info("get {} ids for key : {}.", ids.size(), key);
        return ids;
    }

    public void srem(String key, String... keys) {
        try {
            if (null == key) {
                return;
            }
            if (null == keys) {
                return;
            }
            Long num = getRedisOp().srem(key, keys);
            if (null == num) {
                return;
            }
            LOG.info("delete {} keys for key : {}, keys : {}.", num, key, keys);
        } catch (Exception e) {
            LOG.error("{}", e.getMessage(), e);
        }

    }

}
