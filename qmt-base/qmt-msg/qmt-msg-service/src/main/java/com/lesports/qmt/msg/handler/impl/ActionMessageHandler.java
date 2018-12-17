package com.lesports.qmt.msg.handler.impl;

import com.lesports.qmt.msg.core.LeMessage;
import com.lesports.qmt.msg.handler.AbstractMessageHandler;
import com.lesports.qmt.msg.handler.IMessageHandler;
import com.lesports.qmt.msg.service.ActionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * lesports-projects.
 *
 * @author: pangchuanxiao
 * @since: 2015/7/23
 */
@Component
public class ActionMessageHandler extends AbstractMessageHandler implements IMessageHandler {
    private static final Logger LOG = LoggerFactory.getLogger(ActionMessageHandler.class);
    @Resource
    private ActionService actionService;

    @Override
    protected Logger getLogger() {
        return LOG;
    }

    @Override
    public boolean handleSync(LeMessage message) {
        return actionService.submitAction(message);
    }


}
