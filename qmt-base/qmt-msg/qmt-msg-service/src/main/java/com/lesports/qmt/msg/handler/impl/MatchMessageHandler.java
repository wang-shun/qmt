package com.lesports.qmt.msg.handler.impl;

import com.lesports.qmt.msg.core.ActionType;
import com.lesports.qmt.msg.core.LeMessage;
import com.lesports.qmt.msg.handler.AbstractMessageHandler;
import com.lesports.qmt.msg.handler.IMessageHandler;
import com.lesports.qmt.msg.service.EpisodeService;
import com.lesports.qmt.msg.service.MatchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * lesports-projects.
 *
 * @author: pangchuanxiao
 * @since: 2015/7/10
 */
@Component
public class MatchMessageHandler extends AbstractMessageHandler implements IMessageHandler {
    private static final Logger LOG = LoggerFactory.getLogger(MatchMessageHandler.class);

    @Resource
    private EpisodeService episodeService;

    @Resource
    private MatchService matchService;

    @Override
    protected Logger getLogger() {
        return LOG;
    }

    @Override
    public boolean handleSync(LeMessage message) {
        Long matchId = message.getEntityId();
        if (matchId == null || matchId < 1) {
            return false;
        }

        if (ActionType.SYNC_EPISODE.equals(message.getActionType())) {
            //同步episode
            episodeService.syncEpisode(matchId);
        }

        return true;
    }

}
