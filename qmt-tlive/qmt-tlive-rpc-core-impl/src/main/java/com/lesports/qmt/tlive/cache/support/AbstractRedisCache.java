package com.lesports.qmt.tlive.cache.support;

import com.lesports.repository.LeCrudCache;
import me.ellios.jedis.RedisClientFactory;
import me.ellios.jedis.RedisOp;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.thrift.TBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.Set;

import static com.lesports.utils.thrift.ThriftSerializeUtils.deserialize;
import static com.lesports.utils.thrift.ThriftSerializeUtils.serialize;

/**
 * User: ellios
 * Time: 13-9-18 : 上午11:28
 */
abstract public class AbstractRedisCache<T extends TBase, ID extends Serializable> implements LeCrudCache<T, ID> {

    private static final Logger LOG = LoggerFactory.getLogger(AbstractRedisCache.class);

    protected static final RedisOp redisOp = RedisClientFactory.getRedisClient("rpc_tlive_cache");

    private static final int MAX_TRY_COUNT = 3;

    /**
     * 获取redis客户端
     *
     * @return
     */
    public static RedisOp getRedisOp() {
        return redisOp;
    }

    @Override
    public boolean save(T entity) {
        Assert.notNull(entity);
        return save(entity, 0);
    }

    @Override
    public boolean save(T entity, int expire) {
        Assert.notNull(entity);
        boolean result = true;
        String key = getKey(entity);
        if (expire > 0) {
            result &= redisOp.set(key, serialize(entity), expire);
        } else {
            result &= redisOp.set(key, serialize(entity));
        }
        return result;
    }

    @Override
    public T findOne(ID id) {
        RedisOp redisOp = getRedisOp();
        byte[] data = redisOp.getBytes(getKeyById(id));
        if (ArrayUtils.isEmpty(data)) {
            LOG.warn("entity no exist. key : {}", getKeyById(id));
            return null;
        }
        T entity = getEmptyEntity();
        deserialize(entity, data);
        return entity;
    }

    @Override
    public boolean delete(ID id) {
        String key = getKeyById(id);

        int tryCount = 0;
        boolean deleted = false;
        while (deleted == false && tryCount++ < MAX_TRY_COUNT) {
            try {
                deleted = redisOp.del(key);
            } catch (Exception e) {
                LOG.error("fail to delete key : {} from cache, sleep and try again.", id, e);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e1) {
                }
            }
        }
        return deleted;
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

    /**
     * 通过ID获取对应的key
     *
     * @param id
     * @return
     */
    abstract public String getKeyById(ID id);

    /**
     * 通过entity获取在redis中的key
     *
     * @param entity
     * @return
     */
    abstract public String getKey(T entity);

    /**
     * 获取一个空的entity
     *
     * @return
     */
    abstract protected T getEmptyEntity();

}
