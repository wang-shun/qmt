package com.lesports.qmt.sbc.cache.support.impl;

import com.lesports.qmt.sbc.api.dto.TTopic;
import com.lesports.qmt.sbc.cache.TTopicCache;
import com.lesports.qmt.sbc.cache.support.AbstractRedisCache;
import com.lesports.utils.Utf8KeyCreater;
import org.springframework.stereotype.Component;

/**
 * Created by denghui on 2016/11/1.
 */
@Component("topicCache")
public class TTopicCacheImpl extends AbstractRedisCache<TTopic, Long> implements TTopicCache{

    private static final Utf8KeyCreater<Long> KEY_CREATER = new Utf8KeyCreater<>("V2_QMT_TOPIC_TF_");

    @Override
    public String getKeyById(Long id) {
        return KEY_CREATER.textKey(id);
    }

    @Override
    public String getKey(TTopic entity) {
        return KEY_CREATER.textKey(entity.getId());
    }

    @Override
    protected TTopic getEmptyEntity() {
        return new TTopic();
    }

}
