package com.lesports.qmt.web.api.core.cache.impl;

import com.lesports.qmt.web.api.core.cache.AbstractCache;
import com.lesports.qmt.web.api.core.vo.TextLiveMessageVo;
import com.lesports.utils.Utf8KeyCreater;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created by lufei1 on 2015/7/6.
 */
@Component
public class TextLiveCache extends AbstractCache {
    private static final Logger LOG = LoggerFactory.getLogger(TextLiveCache.class);
    private static final Utf8KeyCreater<Long> TEXT_LIVE_MESSAGE_CREATE = new Utf8KeyCreater<>("V2_TEXT_LIVE_MESSAGE_");
    private static final int EXPIRE_TIME = 60 * 30;

    public TextLiveMessageVo getTextLiveMessageVo(long id) {
        try {
            return findByKey(TEXT_LIVE_MESSAGE_CREATE.textKey(id), TextLiveMessageVo.class);
        } catch (Exception e) {
            LOG.error("get text live message from cache : {}, {}", id, e.getMessage(), e);
        }
        return null;
    }

    public boolean saveTextLiveMessageVo(long id, TextLiveMessageVo textLiveMessageVo) {
        try {
            LOG.info("save text live message : {} in cbase.", id);
            return save(TEXT_LIVE_MESSAGE_CREATE.textKey(id), textLiveMessageVo, EXPIRE_TIME);
        } catch (Exception e) {
            LOG.error("save text live message to cache : {}, {}", id, e.getMessage(), e);
        }
        return false;
    }


}
