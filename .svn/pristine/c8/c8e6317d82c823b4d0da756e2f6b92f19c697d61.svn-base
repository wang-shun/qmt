package com.lesports.qmt.tlive.cache.impl;

import com.lesports.qmt.tlive.api.dto.TTextLiveMessage;
import com.lesports.qmt.tlive.cache.TTextLiveMessageCache;
import com.lesports.qmt.tlive.cache.support.AbstractRedisCache;
import com.lesports.utils.Utf8KeyCreater;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.Set;

/**
 * Created by lufei1 on 2015/9/15.
 */
@Repository("textLiveMessageCache")
public class TTextLiveMessageCacheImpl extends AbstractRedisCache<TTextLiveMessage, Long> implements TTextLiveMessageCache {

    private static final Utf8KeyCreater<Long> LIVEMESSAGE_KEY_CREATER = new Utf8KeyCreater<>("SMS_LIVEMESSAGE_TF_");

    private static final String LIVEMESSAGE_IDS_KEY = "SMS_LIVEMESSAGE_IDS_";

    @Override
    public String getKeyById(Long id) {
        return LIVEMESSAGE_KEY_CREATER.textKey(id);
    }

    @Override
    public String getKey(TTextLiveMessage entity) {
        Assert.notNull(entity);
        return LIVEMESSAGE_KEY_CREATER.textKey(entity.getId());
    }

    @Override
    public TTextLiveMessage getEmptyEntity() {
        return new TTextLiveMessage();
    }


    public boolean zadd(Long textLiveId, Long section, Long id) {
        Assert.notNull(textLiveId);
        Assert.notNull(id);
        String liveMessageIdsKey = getKey(textLiveId, section);
        return redisOp.zadd(liveMessageIdsKey, id, String.valueOf(id));
    }


    public Set<String> zrange(Long textLiveId, Long section) {
        Assert.notNull(textLiveId);
        String liveMessageIdsKey = getKey(textLiveId, section);
        return redisOp.zrange(liveMessageIdsKey, 0, -1);
    }

    private String getKey(Long textLiveId, Long section) {
        Assert.notNull(textLiveId);
        StringBuilder sb = new StringBuilder(LIVEMESSAGE_IDS_KEY);
        sb.append(textLiveId);
        if (section != null && section != 0) {
            sb.append("_").append(section);
        }
        return sb.toString();
    }
}
