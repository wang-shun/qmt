package com.lesports.qmt.config.cache.support;

//import com.lesports.qmt.msg.core.*;
//import com.lesports.qmt.msg.producer.SwiftMessageApis;

import com.lesports.repository.LeCrudRepository;
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
abstract public class AbstractRedisCache<T extends TBase, ID extends Serializable> implements LeCrudRepository<T, ID> {

    private static final Logger LOG = LoggerFactory.getLogger(AbstractRedisCache.class);

    protected static final RedisOp redisOp = RedisClientFactory.getRedisClient("rpc_config_cache");

    private static final int MAX_TRY_COUNT = 3;

    /**
     * 获取redis客户端
     * @return
     */
    public RedisOp getRedisOp() {
        return redisOp;
    }

    @Override
    public boolean save(T entity) {
        Assert.notNull(entity);
        return saveEntity(entity, 0);
    }

    public boolean save(T entity, int expire) {
        Assert.notNull(entity);
        return saveEntity(entity, expire);
    }

    private boolean saveEntity(T entity, int expire) {
        boolean result = true;
        if (expire > 0) {
            result &= getRedisOp().set(getKey(entity), serialize(entity), expire);
        } else {
            result &= getRedisOp().set(getKey(entity), serialize(entity));
        }
        return result;
    }

    public boolean saveByKey(String key, T entity, int expire) {
        Assert.notNull(entity);
        boolean result = true;
        if (expire > 0) {
            result &= getRedisOp().set(key, serialize(entity), expire);
        } else {
            result &= getRedisOp().set(key, serialize(entity));
        }
        return result;
    }

    public boolean deleteByKey(String key) {
        return getRedisOp().del(key);
    }

    public T findByKey(String key) {
        RedisOp redisOp = getRedisOp();
        byte[] data = redisOp.getBytes(key);
        if (ArrayUtils.isEmpty(data)) {
            LOG.info("entity no exist. key : {}", key);
            return null;
        }
        T entity = getEmptyEntity();
        deserialize(entity, data);
        return entity;
    }

    private boolean deleteEntity(ID id) {
        boolean result = true;
        String key = getKeyById(id);
        result &= getRedisOp().del(key);
//        MessageContent messageContent = new MessageContent();
//        messageContent.addToMsgBody(MessageConstants.KEY, key).addToMsgBody(MessageConstants.CACHE_TYPE, MessageConstants.REDIS);
//
//        // TODO: 2016/11/18 check this
//        LeMessage leMessage = new LeMessageBuilder()
//                .setActionType(ActionType.DELETE_CACHE)
//                .setContent(messageContent)
//                .build();
//        SwiftMessageApis.sendMsgAsync(leMessage);
        return result;
    }

    @Override
    public T findOne(ID id) {
        RedisOp redisOp = getRedisOp();
        byte[] data = redisOp.getBytes(getKeyById(id));
        if (ArrayUtils.isEmpty(data)) {
            LOG.info("entity no exist. key : {}", getKeyById(id));
            LOG.warn("entity no exist. id : {}", id);
            return null;
        }
        T entity = getEmptyEntity();
        deserialize(entity, data);
        return entity;
    }

    @Override
    public boolean delete(ID id) {
        int tryCount = 0;
        boolean isdeleted = false;
        while(isdeleted == false && tryCount++ < MAX_TRY_COUNT) {
            try {
                isdeleted = deleteEntity(id);
            } catch (Exception e) {
                LOG.error("fail to deleted cache. id : {}. sleep and try again.", id, e);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e1) {
                }
            }
        }
        return isdeleted;
    }

    /**
     *
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
     *
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
    abstract public T getEmptyEntity();

    /**
     * 获取过期时间，单位毫秒
     *
     * @return
     */
    public int getExpire() {
        return 0;
    }
}
