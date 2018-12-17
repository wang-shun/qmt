package com.lesports.qmt.sbc.helper;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.lesports.api.common.Platform;
import com.lesports.qmt.config.api.dto.TCaller;
import com.lesports.qmt.config.client.QmtConfigApis;
import com.lesports.qmt.sbc.api.dto.TComboEpisode;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.Collection;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * Created by gengchengliang on 2015/10/14.
 */
@Component("callerHelper")
public class CallerHelper {

    private static final Logger LOG = LoggerFactory.getLogger(CallerHelper.class);

    private LoadingCache<Long, TCaller> callerCache = CacheBuilder.newBuilder()
            .maximumSize(500)
            .expireAfterWrite(60, TimeUnit.MINUTES)
            .build(new CacheLoader<Long, TCaller>() {
                @Override
                public TCaller load(Long callerId) throws Exception {
                    return QmtConfigApis.getTCallerById(callerId);
                }
            });

    /**
     * 获取Caller所属的平台
     *
     * @param callerId
     * @return
     */
    public Platform getPlatformOfCaller(long callerId) {
        if (callerId <= 0) {
            return null;
        }
        TCaller caller = null;
        try {
            caller = callerCache.get(callerId);
        } catch (ExecutionException e) {
            LOG.error("fail to get caller. callerId : {}", callerId, e);
        }
        if (caller == null) {
            return null;
        }
        return caller.getPlatform();
    }

    /**
     * 获取Caller的SplatId
     *
     * @param callerId
     * @return
     */
    public Long getSplatIdOfCaller(long callerId) {
        if (callerId <= 0) {
            return null;
        }
        TCaller caller = null;
        try {
            caller = callerCache.get(callerId);
        } catch (ExecutionException e) {
            LOG.error("fail to get caller. callerId : {}", callerId, e);
        }
        if (caller == null) {
            return null;
        }
        return caller.getSplatId();
    }

    /**
     * caller所属平台是否在目标平台内
     * @param callerId
     * @param platforms
     * @return
     */
    public boolean allowCallerInPlatforms(long callerId, Collection<Platform> platforms){
        if(CollectionUtils.isEmpty(platforms)){
            //平台为空，那就允许吧
            return true;
        }
        Platform platform = getPlatformOfCaller(callerId);
        return platforms.contains(platform);
    }

    public boolean isEpisodeAllowed4Caller(TComboEpisode episode, long callerId) {
        Assert.notNull(episode);
        Platform platform = getPlatformOfCaller(callerId);
        if (platform == null) {
            //caller平台没定义，抛异常
            throw new IllegalArgumentException("illegal callerId : " + callerId);
        }
        if (CollectionUtils.isEmpty(episode.getLivePlatforms())) {
            //没定义平台，那就都不能播放
            return false;
        }
        if (episode.getLivePlatforms().contains(platform)) {
            return true;
        }
        return false;
    }

    public boolean needFilterCaller(long callerId) {
        return callerId > 0;
    }

}
