package com.lesports.qmt.tlive.repository.impl.redis;

import com.google.common.collect.Maps;
import com.lesports.qmt.tlive.repository.redis.TextLiveMessageRedisRepository;
import com.lesports.utils.math.LeNumberUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by lufei1 on 2015/10/14.
 */
@Repository("textLiveMessageRedisRepository")
public class TextLiveMessageRedisRepositoryImpl extends AbstractRedisRepository implements TextLiveMessageRedisRepository {

    private static final String LIVEMESSAGE_INDEX = "LIVEMESSAGE_INDEX_";

    private static final String LIVEMESSAGE_PAGE = "LIVEMESSAGE_PAGE_";

    private static final String LIVEMESSAGE_ID_PAGE = "LIVEMESSAGE_ID_PAGE_";


    @Override
    public int generateMessageIndex(long textLiveId, long section) {
        return LeNumberUtils.toInt(redisOp.incr(getMessageIndexKey(textLiveId, section)));
    }

    @Override
    public int getMessageIndex(long textLiveId, long section) {
        return LeNumberUtils.toInt(redisOp.get(getMessageIndexKey(textLiveId, section)));
    }

    @Override
    public List<String> getIdByPage(long textLiveId, long section, int pageId) {
        String key = getMessagePageKey(textLiveId, section, pageId);
        return redisOp.lrange(key, 0, -1);
    }

    @Override
    public Long addIdToPage(long textLiveId, long section, int pageId, long messageId) {
        String key = getMessagePageKey(textLiveId, section, pageId);
        return redisOp.lpush(key, LeNumberUtils.valueOf(messageId));
    }

    @Override
    public Boolean saveIdPageMapping(long textLiveId, long section, long messageId, int pageId) {
        Map<String, String> idPageMap = redisOp.hgetAll(getIdPageMapKey(textLiveId, section));
        if (MapUtils.isEmpty(idPageMap)) {
            idPageMap = Maps.newHashMap();
        }
        idPageMap.put(String.valueOf(messageId), String.valueOf(pageId));
        return redisOp.hmset(getIdPageMapKey(textLiveId, section), idPageMap);
    }

    @Override
    public int getPageById(long textLiveId, long section, long messageId) {
        List<String> pages = redisOp.hmget(getIdPageMapKey(textLiveId, section), String.valueOf(messageId));
        if (CollectionUtils.isNotEmpty(pages)) {
            return LeNumberUtils.toInt(pages.get(0));
        }
        return 0;
    }


    private String getMessageIndexKey(long textLiveId, long section) {
        StringBuffer sb = new StringBuffer();
        sb.append(LIVEMESSAGE_INDEX).append(textLiveId);
        if (section != 0) {
            sb.append(section);
        }
        return sb.toString();
    }

    private String getMessagePageKey(long textLiveId, long section, int pageId) {
        StringBuffer sb = new StringBuffer();
        sb.append(LIVEMESSAGE_PAGE).append(textLiveId);
        if (section > 0) {
            sb.append(section);
        }
        return sb.append(pageId).toString();
    }

    private String getIdPageMapKey(long textLiveId, long section) {
        StringBuffer sb = new StringBuffer();
        sb.append(LIVEMESSAGE_ID_PAGE).append(textLiveId);
        if (section > 0) {
            sb.append(section);
        }
        return sb.toString();
    }

}
