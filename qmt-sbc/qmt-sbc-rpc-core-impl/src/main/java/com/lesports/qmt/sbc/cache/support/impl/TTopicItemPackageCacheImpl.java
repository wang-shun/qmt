package com.lesports.qmt.sbc.cache.support.impl;

import com.lesports.qmt.sbc.api.dto.TTopicItemPackage;
import com.lesports.qmt.sbc.cache.TTopicItemPackageCache;
import com.lesports.qmt.sbc.cache.support.AbstractRedisCache;
import com.lesports.utils.Utf8KeyCreater;
import org.springframework.stereotype.Component;

/**
 * Created by denghui on 2016/11/1.
 */
@Component("topicItemPackageCache")
public class TTopicItemPackageCacheImpl extends AbstractRedisCache<TTopicItemPackage, Long> implements TTopicItemPackageCache {

    private static final Utf8KeyCreater<Long> KEY_CREATER = new Utf8KeyCreater<>("V2_QMT_TOPIC_PACKAGE_TF_");

    @Override
    public String getKeyById(Long id) {
        return KEY_CREATER.textKey(id);
    }

    @Override
    public String getKey(TTopicItemPackage entity) {
        return KEY_CREATER.textKey(entity.getId());
    }

    @Override
    protected TTopicItemPackage getEmptyEntity() {
        return new TTopicItemPackage();
    }

}
