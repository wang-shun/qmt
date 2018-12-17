package com.lesports.qmt.base.web.util;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * Created by zhangdeqiang on 2017/2/22.
 */
public class EmailLocalCache {
    private static final Logger LOG = LoggerFactory.getLogger(EmailLocalCache.class);
    private static final Long VALID_TIME = 1L;

    static LoadingCache<String, Integer> cache = CacheBuilder.newBuilder().refreshAfterWrite(1, TimeUnit.HOURS)// 给定时间内没有被读/写访问，则回收。
            .expireAfterAccess(VALID_TIME, TimeUnit.HOURS)// 缓存过期时间和redis缓存时长一样
            .maximumSize(10000).// 设置缓存个数
            build(new CacheLoader<String, Integer>() {
        @Override
        /** 当本地缓存命没有中时，调用load方法获取结果并将结果缓存 **/
        public Integer load(String appKey) {
            return 0;
        }
    });

    public static Integer getCacheByKey(String key) throws ExecutionException {
        LOG.info("method<getCacheBykey> key<" + key + ">");
        return cache.get(key);
    }

    public static void incrementByKey(String key) throws ExecutionException {
        Integer value = cache.get(key);
        cache.put(key, ++value);
    }

    public static void main(String[] args) throws ExecutionException {
        String key = "key1";
        incrementByKey(key);
        for (;;) {
            incrementByKey(key);
            int value = cache.get(key);
            if (value > 10) {
                break;
            }
        }
        System.out.println(cache.get(key));
    }
}
