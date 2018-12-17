package com.lesports.qmt.sbc.cache.support.impl;

import com.lesports.qmt.sbc.api.dto.TProgramAlbum;
import com.lesports.qmt.sbc.cache.TProgramAlbumCache;
import com.lesports.qmt.sbc.cache.support.AbstractRedisCache;
import com.lesports.utils.Utf8KeyCreater;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 * Created by denghui on 2016/11/15.
 */
@Component("programAlbumCache")
public class TProgramAlbumCacheImpl extends AbstractRedisCache<TProgramAlbum, Long> implements TProgramAlbumCache {

    private static final Utf8KeyCreater<Long> KEY_CREATER = new Utf8KeyCreater<>("V2_QMT_PROGRAM_ALBUM_TF_");

    @Override
    public String getKeyById(Long id) {
        return KEY_CREATER.textKey(id);
    }

    @Override
    public String getKey(TProgramAlbum entity) {
        Assert.notNull(entity);
        return KEY_CREATER.textKey(entity.getId());
    }

    @Override
    protected TProgramAlbum getEmptyEntity() {
        return new TProgramAlbum();
    }
}
