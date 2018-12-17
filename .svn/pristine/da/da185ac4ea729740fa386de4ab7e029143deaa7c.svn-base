package com.lesports.qmt.msg.handler.impl;

import com.lesports.qmt.msg.core.LeMessage;
import com.lesports.qmt.msg.handler.AbstractMessageHandler;
import com.lesports.qmt.msg.handler.IMessageHandler;
import com.lesports.qmt.msg.service.CompetitionSeasonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * User: ellios
 * Time: 15-11-10 : 下午2:52
 */
@Component
public class CompetitionSeasonHandler extends AbstractMessageHandler implements IMessageHandler {
    private static final Logger LOG = LoggerFactory.getLogger(CompetitionSeasonHandler.class);
    @Resource
    private CompetitionSeasonService competitionSeasonService;

    @Override
    public boolean handleSync(LeMessage message) {
        return false;
        /*if (message.getActionType() == ActionType.LIVE_PLATFORM_UPDATE) {
            long csid = message.getEntityId();
            CompetitionSeason cseason = SbdsInternalApis.getCompetitionSeasonById(csid);
            if (cseason == null) {
                LOG.warn("fail to handle message : {}, cseason : {} no exists.");
            }
            return competitionSeasonService.changeLivePlatformsOfEpisodesRelatedWithCSeason(csid, cseason.getLivePlatforms());

        }
        return true;*/
    }

    @Override
    protected Logger getLogger() {
        return LOG;
    }
}
