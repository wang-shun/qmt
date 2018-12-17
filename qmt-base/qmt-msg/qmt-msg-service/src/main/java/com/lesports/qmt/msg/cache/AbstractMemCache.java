package com.lesports.qmt.msg.cache;

import com.lesports.utils.MD5Util;
import me.ellios.memcached.MemcachedOp;
import org.slf4j.Logger;
import org.springframework.util.Assert;

/**
 * lesports-projects.
 *
 * @author: pangchuanxiao
 * @since: 2015/7/23
 */
public abstract class AbstractMemCache {

    public boolean delete(String key, long id) {
        if (null == memcachedOp()) {
            return true;
        }
        int retry = 0;
        while (retry < 2) {
            try {
                boolean exists = memcachedOp().delete(key);
                logger().info("delete key : {} for id {}, in memcached {}, exists before : {}, retry : {}", key, id, memcachedName(), exists, retry);
                return true;
            } catch (Exception e) {
                logger().error("delete key : {} for id {}, in memcached {} failed, retry : {}, error : {}",
                        key, id, memcachedName(), retry, e.getMessage(), e);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e1) {
                    logger().error("{}", e.getMessage(), e);
                }
            }
            retry++;
        }

        return false;
    }

    public boolean save(String key, Object entity, int expire) {
        Assert.notNull(entity);
        MemcachedOp memOp = memcachedOp();
        boolean result;
        if (expire > 0) {
            result = memOp.set(key, expire, entity);
        } else {
            result = memOp.set(key, 0, entity);
        }
        return result;
    }

    public boolean delete(String key) {
        if (null == memcachedOp()) {
            return true;
        }
        int retry = 0;
        while (retry < 2) {
            try {
                boolean exists = memcachedOp().delete(key);
                logger().info("delete key : {} in memcached {}, exists before : {}, retry : {}", key, memcachedName(), exists, retry);
                return true;
            } catch (Exception e) {
                logger().error("delete key : {} in memcached {} failed, retry : {}, error : {}",
                        key, memcachedName(), retry, e.getMessage(), e);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e1) {
                    logger().error("{}", e.getMessage(), e);
                }
            }
            retry++;
        }

        return false;
    }

    public boolean deleteAfterMD5(String key) {
        if (null == memcachedOp()) {
            return true;
        }
        String md5Key = MD5Util.md5(key);
        boolean result = memcachedOp().delete(md5Key);
        logger().info("delete md5 key : {} in memcached {}, result : {}, raw key is  {}", md5Key, memcachedName(), result, key);
        return true;
    }

    abstract MemcachedOp memcachedOp();

    abstract String memcachedName();

    abstract Logger logger();
}
