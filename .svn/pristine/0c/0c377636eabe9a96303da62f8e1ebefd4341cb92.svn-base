package com.lesports.qmt.tlive.repository.impl.redis;

import me.ellios.jedis.RedisClientFactory;
import me.ellios.jedis.RedisOp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by lufei1 on 2015/10/14.
 */
abstract public class AbstractRedisRepository {
    private static final Logger LOG = LoggerFactory.getLogger(AbstractRedisRepository.class);

    protected final RedisOp redisOp = RedisClientFactory.getRedisClient("rpc_tlive_repo");
}
