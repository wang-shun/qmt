package com.lesports.qmt.msg.handler.impl;

import com.lesports.qmt.msg.core.ActionType;
import com.lesports.qmt.msg.core.LeMessage;
import com.lesports.qmt.msg.handler.AbstractMessageHandler;
import com.lesports.qmt.msg.handler.IMessageHandler;
import com.lesports.qmt.msg.service.CompetitionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * User: ellios
 * Time: 15-11-10 : 下午2:52
 */
@Component
public class CompetitionMessageHandler extends AbstractMessageHandler implements IMessageHandler{
    private static final Logger LOG = LoggerFactory.getLogger(CompetitionMessageHandler.class);
	@Resource
	private CompetitionService competitionService;

    @Override
    public boolean handleSync(LeMessage message) {
        boolean result = true;
        if (message.getActionType() == ActionType.SYNC_TO_CLOUD_UPDATE){
            long cid = message.getEntityId();
            result = competitionService.changeSyncToCloudOfEpisodesWithCid(cid);
        }
        return result;
    }

    @Override
    protected Logger getLogger() {
        return LOG;
    }
}
