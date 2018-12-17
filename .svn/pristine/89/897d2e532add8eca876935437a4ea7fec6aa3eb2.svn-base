package com.lesports.qmt.web.api.core.cache.impl;

import com.lesports.LeConstants;
import com.lesports.api.common.CallerParam;
import com.lesports.api.common.PageParam;
import com.lesports.qmt.sbc.api.service.GetMatchsEpisodesParam;
import com.lesports.qmt.web.api.core.cache.AbstractCache;
import com.lesports.qmt.web.api.core.vo.DetailEpisodeVo;
import com.lesports.qmt.web.api.core.vo.GetRealtimeCacheParam;
import com.lesports.qmt.web.api.core.vo.HallEpisodeVo;
import com.lesports.qmt.web.api.core.vo.PollingEpisodeVo;
import com.lesports.utils.Utf8KeyCreater;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by lufei1 on 2015/7/6.
 */
@Component
public class EpisodeCache extends AbstractCache {
    private static final Logger LOG = LoggerFactory.getLogger(EpisodeCache.class);

    private static final String EPISODE_MATCHES_KEY_PREFIX = "V2_OLY_EPISODE_MATCHES_";
    private static final Utf8KeyCreater<Long> HALL_EPISODE_CREATE = new Utf8KeyCreater<>("V2_HALL_EPISODE_");
    private static final Utf8KeyCreater<Long> HALL_EPISODE_APP_CREATE = new Utf8KeyCreater<>("V2_HALL_EPISODE_APP_");
    private static final Utf8KeyCreater<Long> POLLING_EPISODE_CREATE = new Utf8KeyCreater<>("V2_POLLING_EPISODE_");
    private static final Utf8KeyCreater<Long> POLLING_EPISODE_APP_CREATE = new Utf8KeyCreater<>("V2_POLLING_EPISODE_APP_");
    private static final Utf8KeyCreater<Long> DETAIL_EPISODE_CREATE = new Utf8KeyCreater<>("V2_DETAIL_EPISODE_");
    private static final Utf8KeyCreater<Long> DETAIL_EPISODE_APP_CREATE = new Utf8KeyCreater<>("V2_DETAIL_EPISODE_APP_");
    private static final int EXPIRE_TIME = 60 * 30;
    private static final int EXPIRE_TIME_MINUTE = 60 * 1;

    public HallEpisodeVo getHallEpisodeVo(GetRealtimeCacheParam param) {
        String key = param.getCacheKey(HALL_EPISODE_CREATE);
        try {
            return findByKey(key, HallEpisodeVo.class);
        } catch (Exception e) {
            LOG.error("get hall episode from cache : {}, {}", key, e.getMessage(), e);
        }
        return null;
    }

    public HallEpisodeVo getHallEpisode4AppVo(GetRealtimeCacheParam param) {
        String key = param.getCacheKey(HALL_EPISODE_APP_CREATE);
        try {
            return findByKey(key, HallEpisodeVo.class);
        } catch (Exception e) {
            LOG.error("get hall episode for app from cache : {}, {}", key, e.getMessage(), e);
        }
        return null;
    }

    public PollingEpisodeVo getPollingEpisodeVo(GetRealtimeCacheParam param) {
        String key = param.getCacheKey(POLLING_EPISODE_CREATE);
        try {
            return findByKey(key, PollingEpisodeVo.class);
        } catch (Exception e) {
            LOG.error("get polling episode from cache : {}, {}", key, e.getMessage(), e);
        }
        return null;
    }

    public PollingEpisodeVo getPollingEpisode4AppVo(GetRealtimeCacheParam param) {
        String key = param.getCacheKey(POLLING_EPISODE_APP_CREATE);
        try {
            return findByKey(key, PollingEpisodeVo.class);
        } catch (Exception e) {
            LOG.error("get polling episode from cache : {}, {}", key, e.getMessage(), e);
        }
        return null;
    }

    public boolean saveHallEpisodeVo(GetRealtimeCacheParam param, HallEpisodeVo hallEpisodeVo) {
        String key = param.getCacheKey(HALL_EPISODE_CREATE);

        try {
            LOG.info("save hall episode key : {} in cbase.", key);
            return save(key, hallEpisodeVo, EXPIRE_TIME);
        } catch (Exception e) {
            LOG.error("save hall episode to cache : {}, {}", key, e.getMessage(), e);
        }
        return false;
    }

    public boolean saveHallEpisode4AppVo(GetRealtimeCacheParam param, HallEpisodeVo hallEpisodeVo) {
        String key = param.getCacheKey(HALL_EPISODE_APP_CREATE);

        try {
            LOG.info("save hall episode for app, key : {} in cbase.", key);
            return save(key, hallEpisodeVo, EXPIRE_TIME);
        } catch (Exception e) {
            LOG.error("save hall episode for app to cache : {}, {}", key, e.getMessage(), e);
        }
        return false;
    }

    public boolean savePollingEpisodeVo(GetRealtimeCacheParam param, PollingEpisodeVo pollingEpisodeVo) {
        String key = param.getCacheKey(POLLING_EPISODE_CREATE);

        try {
            LOG.info("save polling episode key : {} in cbase.", key);
            return save(key, pollingEpisodeVo, EXPIRE_TIME);
        } catch (Exception e) {
            LOG.error("save polling episode to cache : {}, {}", key, e.getMessage(), e);
        }
        return false;
    }

    public boolean savePollingEpisode4AppVo(GetRealtimeCacheParam param, PollingEpisodeVo pollingEpisodeVo) {
        String key = param.getCacheKey(POLLING_EPISODE_APP_CREATE);

        try {
            LOG.info("save polling episode for app key : {} in cbase.", key, key);
            return save(key, pollingEpisodeVo, EXPIRE_TIME);
        } catch (Exception e) {
            LOG.error("save polling episode for app to cache : {}, {}", key, e.getMessage(), e);
        }
        return false;
    }

    public DetailEpisodeVo getDetailEpisodeVo(GetRealtimeCacheParam param) {
        String key = param.getCacheKey(DETAIL_EPISODE_CREATE);

        try {
            return findByKey(key, DetailEpisodeVo.class);
        } catch (Exception e) {
            LOG.error("get detail episode from cache : {}, {}", key, e.getMessage(), e);
        }
        return null;
    }

    public DetailEpisodeVo getDetailEpisode4AppVo(GetRealtimeCacheParam param) {
        String key = param.getCacheKey(DETAIL_EPISODE_APP_CREATE);

        try {
            return findByKey(key, DetailEpisodeVo.class);
        } catch (Exception e) {
            LOG.error("get detail episode for app from cache : {}, {}", key, e.getMessage(), e);
        }
        return null;
    }

    public Map<String, Object> getEpisodes4OlyMatchs(GetMatchsEpisodesParam p, PageParam pageParam, CallerParam callerParam) {
        String key = createOlyKey(EPISODE_MATCHES_KEY_PREFIX, p, pageParam, callerParam);
        try {
            return findByKey(key, Map.class);
        } catch (Exception e) {
            LOG.error("getEpisodes4OlyMatchs from cache : {}, {}", key, e.getMessage(), e);
        }
        return null;
    }

    private String createOlyKey(String prefix, GetMatchsEpisodesParam p, PageParam page, CallerParam caller) {
        StringBuilder keyBuilder = new StringBuilder(prefix);
        keyBuilder.append(p.getCid()).append(LeConstants.UNDERLINE);
        if (p.getCountryId() > 0) {
            keyBuilder.append(p.getCountryId()).append(LeConstants.UNDERLINE);
        }
        if (StringUtils.isNotEmpty(p.getDate())) {
            keyBuilder.append(p.getDate()).append(LeConstants.UNDERLINE);
        }
        if (p.getGameFType() > 0) {
            keyBuilder.append(p.getGameFType()).append(LeConstants.UNDERLINE);
        }
        if (p.getDiscpilineId() > 0) {
            keyBuilder.append(p.getDiscpilineId()).append(LeConstants.UNDERLINE);
        }
        if (p.getGameSType() > 0) {
            keyBuilder.append(p.getGameSType()).append(LeConstants.UNDERLINE);
        }
        if (StringUtils.isNotEmpty(p.getStartTime())) {
            keyBuilder.append(p.getStartTime()).append(LeConstants.UNDERLINE);
        }
        if (p.getMedalTypeParam() != null) {
            keyBuilder.append(p.getMedalTypeParam()).append(LeConstants.UNDERLINE);
        }
        keyBuilder.append(page.getPage()).append(LeConstants.UNDERLINE);
        keyBuilder.append(page.getCount()).append(LeConstants.UNDERLINE);

        //keyBuilder.append(caller.getCallerId());
        return keyBuilder.toString();
    }

    public boolean saveEpisodes4OlyMatchs(Map<String, Object> entities, GetMatchsEpisodesParam p, PageParam pageParam, CallerParam callerParam) {
        if(entities == null){
            return false;
        }

        String key = createOlyKey(EPISODE_MATCHES_KEY_PREFIX, p, pageParam, callerParam);
        try {
            LOG.info("saveEpisodes4OlyMatchs . size : {}, p : {}, page : {}, caller : {}",
                    entities.size(), p, pageParam, callerParam);
            return save(key, entities, EXPIRE_TIME_MINUTE); //缓存一分钟
        } catch (Exception e) {
            LOG.error("fail to saveEpisodes4OlyMatchs  . size : {}, p : {}, page : {}, caller : {}",
                    entities.size(), p, pageParam, callerParam, e);
        }
        return false;
    }

    public boolean saveDetailEpisodeVo(GetRealtimeCacheParam param, DetailEpisodeVo detailEpisodeVo) {
        String key = param.getCacheKey(DETAIL_EPISODE_CREATE);

        try {
            LOG.info("save detail episode key : {} in cbase.", key);
            return save(key, detailEpisodeVo, EXPIRE_TIME);
        } catch (Exception e) {
            LOG.error("save detail episode to cache : {}, {}", key, e.getMessage(), e);
        }
        return false;
    }

    public boolean saveDetailEpisodeVo4AppVo(GetRealtimeCacheParam param, DetailEpisodeVo detailEpisodeVo) {
        String key = param.getCacheKey(DETAIL_EPISODE_APP_CREATE);

        try {
            LOG.info("save detail episode for app key : {} in cbase.", key);
            return save(key, detailEpisodeVo, EXPIRE_TIME);
        } catch (Exception e) {
            LOG.error("save detail episode for app to cache : {}, {}", key, e.getMessage(), e);
        }
        return false;
    }

}
