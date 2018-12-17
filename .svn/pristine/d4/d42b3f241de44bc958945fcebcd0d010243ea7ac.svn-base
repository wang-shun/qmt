package com.lesports.qmt.sbc.cache.support.impl;

import com.lesports.qmt.sbc.api.dto.TAlbum;
import com.lesports.qmt.sbc.cache.TAlbumCache;
import com.lesports.qmt.sbc.cache.support.AbstractRedisCache;
import com.lesports.utils.Utf8KeyCreater;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 * Created by zhangxudong@le.com on 2015/7/11.
 */
@Component("albumCache")
public class TAlbumCacheImpl extends AbstractRedisCache<TAlbum, Long> implements TAlbumCache {

    //    private static final Logger LOG = LoggerFactory.getLogger(TAlbumCacheImpl.class);
    private static final Utf8KeyCreater<Long> Album_KEY_CREATER = new Utf8KeyCreater<>("V2_QMT_Album_TF_");

    @Override
    public String getKeyById(Long id) {
        return Album_KEY_CREATER.textKey(id);
    }

    @Override
    public String getKey(TAlbum entity) {
        Assert.notNull(entity);
        return Album_KEY_CREATER.textKey(entity.getId());
    }

    @Override
    protected TAlbum getEmptyEntity() {
        return new TAlbum();
    }

}
