package com.lesports.qmt.sbd.service.support;

import com.lesports.qmt.exception.NoServiceException;
import com.lesports.qmt.sbd.model.*;
import com.lesports.qmt.sbd.service.*;
import com.lesports.qmt.service.support.QmtService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * trunk.
 *
 * @author pangchuanxiao
 * @since 2016/3/29
 */
@Component("sbdServiceFactory")
public class SbdServiceFactory {
    @Resource
    private CompetitionSeasonService competitionSeasonService;
    @Resource
    private CompetitionService competitionService;
    @Resource
    private TeamSeasonService teamSeasonService;
    @Resource
    private TeamService teamService;
    @Resource
    private PlayerService playerService;
    @Resource
    private TopListService topListService;
    @Resource
    private MatchService matchService;
    @Resource
    private MatchStatsService matchStatsService;
    @Resource
    private LiveEventService liveEventService;
    @Resource
    private DataImportConfigService dataImportConfigService;


    public QmtService getService(Class clazz) throws NoServiceException {
        if (Competition.class == clazz) {
            return competitionService;
        } else if (CompetitionSeason.class == clazz) {
            return competitionSeasonService;
        } else if (TeamSeason.class == clazz) {
            return teamSeasonService;
        } else if (Team.class == clazz) {
            return teamService;
        } else if (Player.class == clazz) {
            return playerService;
        } else if (TopList.class == clazz) {
            return topListService;
        } else if (Match.class == clazz) {
            return matchService;
        } else if (MatchStats.class == clazz) {
            return matchStatsService;
        } else if (LiveEvent.class == clazz) {
            return liveEventService;
        } else if (DataImportConfig.class == clazz) {
            return dataImportConfigService;
        }

        throw new NoServiceException("No service for class : " + clazz);
    }
}
