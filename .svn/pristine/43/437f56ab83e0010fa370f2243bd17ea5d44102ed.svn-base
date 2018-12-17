package com.lesports.qmt.msg.handler.impl;

import com.google.common.base.Function;
import com.lesports.qmt.msg.core.LeMessage;
import com.lesports.qmt.msg.handler.AbstractMessageHandler;
import com.lesports.qmt.msg.handler.IMessageHandler;
import com.lesports.qmt.msg.service.TextLiveService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import javax.annotation.Resource;

/**
 * Created by lufei1 on 2015/10/19.
 */
@Component
public class TextLiveHandler extends AbstractMessageHandler implements IMessageHandler {

    private static final Logger logger = LoggerFactory.getLogger(TextLiveHandler.class);

    @Resource
    private TextLiveService textLiveService;

    @Override
    protected Logger getLogger() {
        return logger;
    }

    @Override
    public boolean handleSync(LeMessage message) {
        long textLiveId = message.getEntityId();
        boolean result = deleteTextLiveCache(textLiveId);
        if (result) {
            logger.info("success to handle textLive message {}", textLiveId);
        } else {
            logger.error("fail to handler textLive message {}", textLiveId);
        }
        return result;
    }


    private boolean deleteTextLiveCache(long textLiveId) {
        return execute(textLiveId, "textLive", new Function<Long, Boolean>() {
            @Nullable
            @Override
            public Boolean apply(Long aLong) {
                return textLiveService.deleteTextLiveApiCache(aLong);
            }
        });
    }
}
