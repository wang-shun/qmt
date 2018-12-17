package com.lesports.qmt.msg.handler.impl;

import com.lesports.qmt.msg.core.LeMessage;
import com.lesports.qmt.msg.handler.AbstractMessageHandler;
import com.lesports.qmt.msg.handler.IMessageHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * User: qiaohongxin
 * Time: 15-11-10 : 下午2:52
 */
@Component
public class CompetitorSeasonStatHandler extends AbstractMessageHandler implements IMessageHandler {
    private static final Logger LOG = LoggerFactory.getLogger(CompetitorSeasonStatHandler.class);

    @Override
    public boolean handleSync(LeMessage message) {
        return false;
        /*long matchId = message.getEntityId();
        long csid = Long.valueOf(message.getContent().getFromMsgBody("csid"));
        MatchStats matchStats = SbdsInternalApis.getMatchStatsById(matchId);
        if (matchStats == null) {
            LOG.warn("fail to handle message : {}, matchId : {} no exists.");
        }
        boolean result = updatePlayerBest(csid, matchStats);
        if (result) {
            LOG.info("update bole competition season : {}, result : {}", message.getEntityId(), true);
        }
        return result;*/
    }


    /*private boolean updatePlayerBest(Long csid, MatchStats matchStats) {
        return false;
        List<MatchStats.CompetitorStat> competitorStatList = matchStats.getCompetitorStats();
        List<MatchStats.PlayerStat> playerStats = Lists.newArrayList();
        playerStats.addAll(competitorStatList.get(0).getPlayerStats());
        playerStats.addAll(competitorStatList.get(1).getPlayerStats());
        for (MatchStats.PlayerStat playerStat : playerStats) {
            if (playerStat == null) continue;
            CompetitorSeasonStat currentStat = SbdsInternalApis.getCompetitorSeasonStatByCsidAndCompetitor(csid, playerStat.getPlayerId(), CompetitorType.PLAYER);
            if (currentStat == null) {
                LOG.warn(" the players competitionseason:{}, is not exist result : {}", playerStat.getPlayerId(), true);
                continue;
            }
            Map map = currentStat.getTopStats() == null ? Maps.newHashMap() : currentStat.getTopStats();
            for (String key : Constants.playerBestStatics) {
                if (map.get(key) == null || (playerStat.getStats().get(key) != null && (StringToDouble(map.get(key)).compareTo(
                        StringToDouble(playerStat.getStats().get(key))) < 0))) {
                    map.put(key, playerStat.getStats().get(key));
                }
            }
            currentStat.setTopStats(map);
            SbdsInternalApis.saveCompetitorSeasonStat(currentStat);
            LOG.info("update  the players best result : {}, result : {}", currentStat.getId(), true);
        }
        LOG.info("update all the players in the match : {}, result : {}", matchStats.getId(), true);
        return true;
    }*/

    private Double StringToDouble(Object value) {
        try {
            Double valueNew = Double.valueOf(value.toString());
            return valueNew;
        } catch (Exception e) {
            return new Double(0.0);
        }
    }

    @Override
    protected Logger getLogger() {
        return LOG;
    }
}
