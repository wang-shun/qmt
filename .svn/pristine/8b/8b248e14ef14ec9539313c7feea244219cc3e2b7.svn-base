package com.lesports.qmt.op.web.api.core.cache.impl;

import com.lesports.qmt.config.api.dto.TTag;
import com.lesports.qmt.op.web.api.core.cache.AbstractCache;
import com.lesports.qmt.op.web.api.core.vo.GetTagCacheParam;
import com.lesports.utils.Utf8KeyCreater;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created by zhonglin on 2016/6/17.
 */
@Component
public class TagCache  extends AbstractCache {

    private static final Logger LOG = LoggerFactory.getLogger(TagCache.class);
    private static final Utf8KeyCreater<String> TAG_NAME_CREATE = new Utf8KeyCreater<>("V2_TAG_NAME_");
    private static final int EXPIRE_TIME = 60 * 60 * 24;

    public TTag getTag(GetTagCacheParam param) {
        String key = param.getCacheKey(TAG_NAME_CREATE);
        try {
            return findByKey(key,TTag.class);
        } catch (Exception e) {
            LOG.error("get tag from cbase key: " + param.getCacheKey(TAG_NAME_CREATE), e);
        }
        return null;
    }

    public boolean saveTag(GetTagCacheParam param, TTag tag) {
        String key = param.getCacheKey(TAG_NAME_CREATE);
        try {
            LOG.info("save tag : {} in cbase.", key);
            return save(key, tag, EXPIRE_TIME);
        } catch (Exception e) {
            LOG.error("save tag to cache : {}, {}", key, e.getMessage(), e);
        }
        return false;
    }

}
