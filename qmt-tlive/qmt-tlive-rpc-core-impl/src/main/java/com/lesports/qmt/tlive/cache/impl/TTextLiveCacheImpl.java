package com.lesports.qmt.tlive.cache.impl;

import com.lesports.qmt.tlive.api.common.UpDownAct;
import com.lesports.qmt.tlive.api.dto.TTextLive;
import com.lesports.qmt.tlive.cache.TTextLiveCache;
import com.lesports.qmt.tlive.cache.support.AbstractRedisCache;
import com.lesports.utils.Utf8KeyCreater;
import com.lesports.utils.math.LeNumberUtils;
import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.Map;

/**
 * Created by lufei1 on 2015/9/15.
 */
@Repository("textLiveCache")
public class TTextLiveCacheImpl extends AbstractRedisCache<TTextLive, Long> implements TTextLiveCache {

    private static final Logger LOG = LoggerFactory.getLogger(TTextLiveCacheImpl.class);
    private static final Utf8KeyCreater<Long> TEXTLIVE_KEY_CREATER = new Utf8KeyCreater<>("SMS_TEXTLIVE_TF_");

    private static final String TEXTLIVE_ONLINE = "TEXTLIVE_ONLINE_";

    private static final String TEXTLIVE_UPDOWN = "TEXTLIVE_UPDOWN_";

    private static final String UNDER_LINE = "_";


    @Override
    public String getKeyById(Long id) {
        return TEXTLIVE_KEY_CREATER.textKey(id);
    }

    @Override
    public String getKey(TTextLive entity) {
        Assert.notNull(entity);
        return TEXTLIVE_KEY_CREATER.textKey(entity.getId());
    }

    @Override
    public TTextLive getEmptyEntity() {
        return new TTextLive();
    }

    @Override
    public int addOnlineCount(long eid) {
        return LeNumberUtils.toInt(redisOp.incr(getOnlineKey(eid)));
    }

    @Override
    public int getOnlineCount(long eid) {
        return LeNumberUtils.toInt(redisOp.get(getOnlineKey(eid)));
    }

    @Override
    public boolean setOnlineCount(long eid, int onlineCount) {
        if (eid == 0) {
            return false;
        }
        return redisOp.set(getOnlineKey(eid), String.valueOf(onlineCount).getBytes());
    }

    @Override
    public int addUpDownAnchor(long textLiveId, long anchorId, UpDownAct act) {
        return LeNumberUtils.toInt(redisOp.incr(getUpDownKey(textLiveId, anchorId, act)));
    }

    @Override
    public boolean setUpDownAnchor(long textLiveId, long anchorId, Map<UpDownAct, Integer> upDownActMap) {
        if (MapUtils.isEmpty(upDownActMap) || textLiveId == 0 || anchorId == 0) {
            return false;
        }
        for (Map.Entry<UpDownAct, Integer> upDownActEntry : upDownActMap.entrySet()) {
            redisOp.set(getUpDownKey(textLiveId, anchorId, upDownActEntry.getKey()), String.valueOf(upDownActEntry.getValue()).getBytes());
        }
        return true;
    }

    @Override
    public int getAnchorUpDown(long textLiveId, long anchorId, UpDownAct act) {
        return LeNumberUtils.toInt(redisOp.get(getUpDownKey(textLiveId, anchorId, act)));
    }

    private String getOnlineKey(long eid) {
        return new StringBuffer().append(TEXTLIVE_ONLINE).append(UNDER_LINE).append(eid).toString();
    }

    private String getUpDownKey(long textLiveId, long anchorId, UpDownAct act) {
        return new StringBuffer().append(TEXTLIVE_UPDOWN).append(UNDER_LINE).append(textLiveId).append(UNDER_LINE)
                .append(anchorId).append(UNDER_LINE).append(act.getValue()).toString();
    }
}
