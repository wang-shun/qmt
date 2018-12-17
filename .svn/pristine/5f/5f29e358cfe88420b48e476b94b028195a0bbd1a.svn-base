package com.lesports.qmt.sbc.cache.support.impl;

import com.lesports.qmt.sbc.api.dto.TVideo;
import com.lesports.qmt.sbc.cache.TVideoCache;
import com.lesports.qmt.sbc.cache.support.AbstractRedisCache;
import com.lesports.utils.Utf8KeyCreater;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 * Created by ruiyuansheng on 2015/7/11.
 */
@Component("videoCache")
public class TVideoCacheImpl extends AbstractRedisCache<TVideo, Long> implements TVideoCache {

    private static final Logger LOG = LoggerFactory.getLogger(TVideoCacheImpl.class);
    private static final Utf8KeyCreater<Long> VIDEO_KEY_CREATER = new Utf8KeyCreater<>("V2_QMT_VIDEO_TF_");

    @Override
    public String getKeyById(Long id) {
        return VIDEO_KEY_CREATER.textKey(id);
    }

    @Override
    public String getKey(TVideo entity) {
        Assert.notNull(entity);
        return VIDEO_KEY_CREATER.textKey(entity.getId());
    }

    @Override
    protected TVideo getEmptyEntity() {
        return new TVideo();
    }

}
