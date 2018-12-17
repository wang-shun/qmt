package com.lesports.qmt.msg.handler.impl;

import com.lesports.qmt.msg.constant.Constants;
import com.lesports.qmt.msg.core.AreaType;
import com.lesports.qmt.msg.core.LeMessage;
import com.lesports.qmt.msg.handler.AbstractMessageHandler;
import com.lesports.qmt.msg.handler.IMessageHandler;
import com.lesports.qmt.msg.producer.CloudSwiftMessageApis;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * lesports-projects.
 *
 * @author pangchuanxiao
 * @since 2015/8/14
 */
@Component
public class TeamSeasonMessageHandler extends AbstractMessageHandler implements IMessageHandler {
    private static final Logger LOG = LoggerFactory.getLogger(TeamSeasonMessageHandler.class);

    @Override
    protected Logger getLogger() {
        return LOG;
    }

    @Override
    public boolean handleSync(LeMessage message) {
        if (AreaType.CN.equals(Constants.areaType)) {
            Future<Boolean> future = CloudSwiftMessageApis.sendMsgAsync(message);
            if (future.isDone()) {
                try {
                    return future.get();
                } catch (Exception e) {
                    LOG.error("teamSeason to cloud message body : {}", message, e);
                    return false;
                }
            }
            LOG.info("teamSeason to cloud message body : {}", message);
        }
        return true;
    }
}
