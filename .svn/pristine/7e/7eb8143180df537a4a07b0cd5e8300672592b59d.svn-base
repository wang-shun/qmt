package com.lesports.qmt.web.interceptor;

import com.lesports.utils.math.LeNumberUtils;
import me.ellios.jedis.RedisClient;
import me.ellios.jedis.RedisClientFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * lesports-projects.
 *
 * @author pangchuanxiao
 * @since 2015/9/1
 */
public class LogCacheUrlInterceptor extends HandlerInterceptorAdapter {
    private static final Logger LOG = LoggerFactory.getLogger(LogCacheUrlInterceptor.class);
    private static final int EXPIRE = 2 * 60 * 60;
    private static final Pattern pattern = Pattern.compile("[0-9]+");
    private static RedisClient client = null;

    static {
        try {
            client = RedisClientFactory.getRedisClient("web_clean_url_cache");
        } catch (Exception e) {
            LOG.error("{}", e.getMessage(), e);
        }
    }

    private static Executor executor = Executors.newFixedThreadPool(10);

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        try {
            final String cacheKey = getCacheKey(request);
            if (null == cacheKey) {
                return;
            }
            final Long id = getId(request);
            if (null == id || id <= 0) {
                return;
            }
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    int retry = 0;
                    while (retry < 2) {
                        try {
                            Long size = client.sadd(getIdKey(id), cacheKey);
                            client.expire(getIdKey(id), EXPIRE);
                            LOG.info("add cache key {} to id {}, exists before : {}", cacheKey, id, null != size && 0 == size);
                            break;
                        } catch (Throwable e) {
                            LOG.error("fail to add cache key {} to id {}, {}, retry {}", cacheKey, id, e.getMessage(), retry, e);
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e1) {
                                LOG.error("", e);
                            }
                            retry++;
                        }
                    }
                }
            });
        } catch (Exception e) {
            LOG.error("{}", e.getMessage(), e);
        }
    }

    private Long getId(HttpServletRequest request) {
        String path = request.getRequestURI();
        Matcher matcher = pattern.matcher(path);
        if (matcher.find()) {
            return LeNumberUtils.toLong(matcher.group(0));
        }
        return null;
    }

    private String getCacheKey(HttpServletRequest request) {
        return request.getHeader("cache_key");
    }

    private String getIdKey(long id) {
        return "id_" + id + "_page";
    }
}
