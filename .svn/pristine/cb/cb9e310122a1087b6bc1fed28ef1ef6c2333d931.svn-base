package com.lesports.qmt.play.api.resources;

import com.alibaba.fastjson.JSONObject;
import com.lesports.jersey.AlternateMediaType;
import com.lesports.utils.LeDateUtils;
import com.lesports.utils.http.FallbackStatistics;
import me.ellios.jedis.RedisClient;
import me.ellios.jedis.RedisClientFactory;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.Date;
import java.util.Set;

/**
 * trunk.
 *
 * @author pangchuanxiao
 * @since 2016/7/18
 */
@Path("/sms/v1/play/fallback")
@Component("fallbackResource")
public class FallbackResource {
    private final static RedisClient redisClient = RedisClientFactory.getRedisClient("rpc_sbd_cache");
    private final static String FALLBACK_MEMBERS_KEY = "FALLBACK_SMS_PLAY_";
    private static final Logger LOG = LoggerFactory.getLogger(FallbackResource.class);

    @GET
    @Path("/result")
    @Produces(AlternateMediaType.UTF_8_APPLICATION_JSON)
    public Set<String> getFallback() {
        Set<String> fallbacks = redisClient.smembers(getFallbackMembersKey());
        return fallbacks;
    }

    public void reportFallback() {
        Set<String> fallbacks = FallbackStatistics.getFallbacks();
        if (CollectionUtils.isEmpty(fallbacks)) {
            return;
        }
        LOG.info("Reporting fallback : {}.", JSONObject.toJSONString(fallbacks));
        for (String fallback : fallbacks) {
            redisClient.sadd(getFallbackMembersKey(), fallback);
        }
    }

    private String getFallbackMembersKey() {
        return FALLBACK_MEMBERS_KEY + "_" + LeDateUtils.formatYYYYMMDDHHMMSS(new Date()).substring(0, 12);
    }

}
