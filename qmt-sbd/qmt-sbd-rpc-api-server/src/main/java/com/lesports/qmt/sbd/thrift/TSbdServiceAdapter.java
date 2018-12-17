package com.lesports.qmt.sbd.thrift;

import com.lesports.api.common.CallerParam;
import com.lesports.api.common.MatchStatus;
import com.lesports.api.common.PageParam;
import com.lesports.qmt.sbd.api.dto.*;
import com.lesports.qmt.sbd.api.service.*;
import com.lesports.qmt.sbd.service.*;
import com.lesports.utils.CallerUtils;
import com.lesports.utils.IdCheckUtils;
import com.lesports.utils.PageUtils;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static me.ellios.hedwig.http.mediatype.ExtendedMediaType.APPLICATION_X_THRIFT;

/**
 * Created by zhonglin on 2016/12/1.
 */
@Service("thriftSbdService")
@Path("/sbd/")
@Produces({APPLICATION_X_THRIFT})
public class TSbdServiceAdapter implements TSbdService.Iface {

    private static final Logger LOG = LoggerFactory.getLogger(TSbdServiceAdapter.class);


    @Resource
    private PlayerService playerService;
    @Resource
    private TeamService teamService;
    @Resource
    private TeamSeasonService teamSeasonService;
    @Resource
    private CompetitionService competitionService;
    @Resource
    private CompetitionSeasonService competitionSeasonService;
    @Resource
    private TopListService topListService;
    @Resource
    private CompetitorSeasonStatService competitorSeasonStatService;
    @Resource
    private PlayerCareerStatService playCareerStatService;
    @Resource
    private MatchService matchService;
    @Resource
    private MatchStatsService matchStatsService;
    @Resource
    private MatchActionService matchActionService;
    @Resource
    private MatchReviewService matchReviewService;


    @Override
    public TPlayer getTPlayerById(long id, CallerParam caller) throws TException {
        try {
            caller = CallerUtils.getValidCaller(caller);
            return playerService.getTPlayerById(id, caller);
        } catch (Exception e) {
            LOG.error("fail to getTPlayerById. id:{}, caller : {}", id, caller, e);
        }
        return null;
    }

    @Override
    public List<TPlayer> getTPlayersByIds(List<Long> ids, CallerParam caller) throws TException {
        try {
            caller = CallerUtils.getValidCaller(caller);
            ids = IdCheckUtils.checkIds(ids);
            return playerService.getTPlayersByIds(ids, caller);
        } catch (Exception e) {
            LOG.error("fail to getTPlayersByIds. ids:{}, caller : {}", ids, caller, e);
        }
        return Collections.EMPTY_LIST;
    }

    @Override
    public List<Long> getPlayerIds4SimpleSearch(GetPlayers4SimpleSearchParam p, PageParam page, CallerParam caller) throws TException {
        try {
            Pageable pageable = (Pageable) PageUtils.convertToPageable(page);
            caller = CallerUtils.getValidCaller(caller);
            return playerService.getPlayerIds4SimpleSearch(p, pageable, caller);
        } catch (Exception e) {
            LOG.error("fail to getPlayerIds4SimpleSearch. p:{}, page:{}, caller : {}", p, page, caller, e);
        }
        return Collections.EMPTY_LIST;
    }

    @Override
    public List<TPlayerCareerStat> getPlayerCareerStat(GetPlayerCareerStatParam p, CallerParam caller) throws TException {
        try {
            caller = CallerUtils.getValidCaller(caller);
            return playCareerStatService.getTPlayerCareerStats(p.getPlayerId(), p.getScopeId(), p.getScopeType(), caller);
        } catch (Exception e) {
            LOG.error("fail to getTPlayerCareerStats. p:{}, caller : {}", p, caller, e);
        }
        return Collections.EMPTY_LIST;
    }

    @Override
    public TTeam getTTeamById(long id, CallerParam caller) throws TException {
        try {
            caller = CallerUtils.getValidCaller(caller);
            return teamService.getTTeamById(id, caller);
        } catch (Exception e) {
            LOG.error("fail to getTTeamById. id:{}, caller : {}", id, caller, e);
        }
        return null;
    }

    @Override
    public TTeam getTTeamByCampId(long id, CallerParam caller) throws TException {
        try {
            caller = CallerUtils.getValidCaller(caller);
            return teamService.getTTeamByCampId(id, caller);
        } catch (Exception e) {
            LOG.error("fail to getTTeamByCampId. id:{}, caller : {}", id, caller, e);
        }
        return null;
    }

    @Override
    public List<TTeam> getTTeamsByIds(List<Long> ids, CallerParam caller) throws TException {
        try {
            ids = IdCheckUtils.checkIds(ids);
            caller = CallerUtils.getValidCaller(caller);
            return teamService.getTTeamsByIds(ids, caller);
        } catch (Exception e) {
            LOG.error("fail to getTTeamsByIds. ids:{}, caller : {}", ids, caller, e);
        }
        return Collections.EMPTY_LIST;
    }

    @Override
    public List<Long> getTeamIds4SimpleSearch(GetTeams4SimpleSearchParam p, PageParam page, CallerParam caller) throws TException {
        try {
            Pageable pageable = (Pageable) PageUtils.convertToPageable(page);
            caller = CallerUtils.getValidCaller(caller);
            return teamService.getTeamIds4SimpleSearch(p, pageable, caller);
        } catch (Exception e) {
            LOG.error("fail to getTeamIds4SimpleSearch. p:{}, page:{}, caller : {}", p, page, caller, e);
        }
        return Collections.EMPTY_LIST;
    }

    @Override
    public List<Long> getTeamIdsOfSeason(GetTeamsOfSeasonParam p, PageParam page, CallerParam caller) throws TException {
        try {
            Pageable pageable = (Pageable) PageUtils.convertToPageable(page);
            caller = CallerUtils.getValidCaller(caller);
            return teamService.getTeamIdsOfSeason(p, pageable, caller);
        } catch (Exception e) {
            LOG.error("fail to getTeamIdsOfSeason. p:{}, page:{}, caller : {}", p, page, caller, e);
        }
        return Collections.EMPTY_LIST;
    }

    @Override
    public List<Long> getTeamIdsOfCompetition(GetTeamsOfCompetitionParam p, PageParam page, CallerParam caller) throws TException {
        try {
            Pageable pageable = (Pageable) PageUtils.convertToPageable(page);
            caller = CallerUtils.getValidCaller(caller);
            return teamService.getTeamIdsOfCompetition(p, pageable, caller);
        } catch (Exception e) {
            LOG.error("fail to getTeamIdsOfCompetition. p:{}, page:{}, caller : {}", p, page, caller, e);
        }
        return Collections.EMPTY_LIST;
    }

    @Override
    public TTeamSeason getTTeamSeasonById(long id, CallerParam caller) throws TException {
        try {
            caller = CallerUtils.getValidCaller(caller);
            return teamSeasonService.getTTeamSeasonById(id, caller);
        } catch (Exception e) {
            LOG.error("fail to getTTeamSeasonById. id:{}, caller : {}", id, caller, e);
        }
        return null;
    }

    @Override
    public List<TTeamSeason> getTTeamSeasonsByIds(List<Long> ids, CallerParam caller) throws TException {
        try {
            ids = IdCheckUtils.checkIds(ids);
            caller = CallerUtils.getValidCaller(caller);
            return teamSeasonService.getTTeamSeasonsByIds(ids, caller);
        } catch (Exception e) {
            LOG.error("fail to getTTeamSeasonsByIds. ids:{}, caller : {}", ids, caller, e);
        }
        return Collections.EMPTY_LIST;
    }

    @Override
    public List<Long> getTeamSeasonIds(GetTeamSeasonsParam p, PageParam page, CallerParam caller) throws TException {
        try {
            Pageable pageable = (Pageable) PageUtils.convertToPageable(page);
            caller = CallerUtils.getValidCaller(caller);
            return teamSeasonService.getTeamSeasonIds(p, pageable, caller);
        } catch (Exception e) {
            LOG.error("fail to getTeamSeasonIds. p:{}, page:{}, caller : {}", p, page, caller, e);
        }
        return Collections.EMPTY_LIST;
    }


    @Override
    public TCompetition getTCompetitionById(long id, CallerParam caller) throws TException {
        try {
            caller = CallerUtils.getValidCaller(caller);
            return competitionService.getTCompetitionById(id, caller);
        } catch (Exception e) {
            LOG.error("fail to getCompetitionById. id:{}, caller : {}", id, caller, e);
        }
        return null;
    }

    @Override
    public List<TCompetition> getTCompetitionByIds(List<Long> ids, CallerParam caller) throws TException {
        try {
            caller = CallerUtils.getValidCaller(caller);
            ids = IdCheckUtils.checkIds(ids);
            return competitionService.getTCompetitionByIds(ids, caller);
        } catch (Exception e) {
            LOG.error("fail to getCompetitionByIds. ids:{}, caller : {}", ids, caller, e);
        }
        return Collections.EMPTY_LIST;
    }

    @Override
    public List<Long> getTCompetitonIds4SimpleSearch(GetCompetitionsParam p, PageParam page, CallerParam caller) throws TException {
        try {
            caller = CallerUtils.getValidCaller(caller);
            Pageable pageable = (Pageable) PageUtils.convertToPageable(page);
            return competitionService.getTCompetitonIds(p, pageable, caller);
        } catch (Exception e) {
            LOG.error("fail to getTCompetitonIds. p:{}, page:{}, caller:{}", p, page, caller, e);
        }
        return Collections.EMPTY_LIST;
    }

    @Override
    public TCompetition getTCompetitionByCode(String code, CallerParam caller) throws TException {
        try {
            return competitionService.getTCompetitionByCode(code, caller);
        } catch (Exception e) {
            LOG.error("fail to getTCompetitionByCode. code:{}, caller:{}", code, caller, e);
        }
        return null;
    }

    @Override
    public TCompetitionSeason getTCompetitionSeasonById(long id, CallerParam caller) throws TException {

        try {
            caller = CallerUtils.getValidCaller(caller);
            return competitionSeasonService.getTCompetitionSeasonById(id, caller);
        } catch (Exception e) {
            LOG.error("fail to getTCompetitionSeasonById. id:{}, caller : {}", id, caller, e);
        }
        return null;
    }

    @Override
    public List<TCompetitionSeason> getTCompetitionSeasonsByIds(List<Long> ids, CallerParam caller) throws TException {
        try {
            ids = IdCheckUtils.checkIds(ids);
            caller = CallerUtils.getValidCaller(caller);
            return competitionSeasonService.getTCompetitionSeasonsByIds(ids, caller);
        } catch (Exception e) {
            LOG.error("fail to getTCompetitionSeasonsByIds. ids:{}, caller : {}", ids, caller, e);
        }
        return Collections.EMPTY_LIST;
    }

    @Override
    public TCompetitionSeason getLatestTCompetitionSeasonsByCid(long cid, CallerParam caller) throws TException {
        try {
            caller = CallerUtils.getValidCaller(caller);
            return competitionSeasonService.getLatestTCompetitionSeasonsByCId(cid, caller);
        } catch (Exception e) {
            LOG.error("fail to getLatestCompetitionSeasonByCid. cid:{}, caller : {}", cid, caller, e);
        }
        return null;
    }

    @Override
    public TCompetitionSeason getTCompetitionSeasonByCidAndSeason(long cid, String season, CallerParam caller) throws TException {
        try {
            caller = CallerUtils.getValidCaller(caller);
            return competitionSeasonService.getTCompetitionSeasonByCidAndSeason(cid, season, caller);
        } catch (Exception e) {
            LOG.error("fail to getLatestCompetitionSeasonByCid. cid:{}, caller : {}", cid, caller, e);
        }
        return null;
    }

    @Override
    public List<Long> getSeasonIdsOfCompetition(long cid, CallerParam caller) throws TException {
        try {
            caller = CallerUtils.getValidCaller(caller);
            return competitionSeasonService.getSeasonIdsOfCompetition(cid, caller);
        } catch (Exception e) {
            LOG.error("fail to getTCompetitonIds. cid:{}, caller:{}", cid, caller, e);
        }
        return Collections.EMPTY_LIST;
    }


    @Override
    public TTopList getTTopListById(long id, CallerParam caller) throws TException {
        try {
            caller = CallerUtils.getValidCaller(caller);
            return topListService.getTTopListById(id, caller);
        } catch (Exception e) {
            LOG.error("fail to getTTopListById. id:{}, caller : {}", id, caller, e);
        }
        return null;
    }

    @Override
    public List<TTopList> getTTopListsByIds(List<Long> ids, CallerParam caller) throws TException {
        try {
            ids = IdCheckUtils.checkIds(ids);
            caller = CallerUtils.getValidCaller(caller);
            return topListService.getTTopListsByIds(ids, caller);
        } catch (Exception e) {
            LOG.error("fail to getTTopListsByIds. ids:{}, caller : {}", ids, caller, e);
        }
        return Collections.EMPTY_LIST;
    }

    @Override
    public List<Long> getSeasonTopListIds(GetSeasonTopListsParam p, PageParam page, CallerParam caller) throws TException {
        try {
            Pageable pageable = (Pageable) PageUtils.convertToPageable(page);
            caller = CallerUtils.getValidCaller(caller);
            return topListService.getSeasonTopListIds(p, pageable, caller);
        } catch (Exception e) {
            LOG.error("fail to getSeasonTopListIds. p : {}, page : {}, caller : {}", p, page, caller, e);
        }
        return Collections.EMPTY_LIST;
    }

    @Override
    public List<TCompetitorSeasonStat> getTCompetitorSeasonStatsByIds(List<Long> ids) throws TException {
        try {
            ids = IdCheckUtils.checkIds(ids);
            return competitorSeasonStatService.getTCompetitorSeasonStatsByIds(ids);
        } catch (Exception e) {
            LOG.error("fail to getTCompetitorSeasonStatsByIds. ids:{}", ids, e);
        }
        return Collections.EMPTY_LIST;
    }

    @Override
    public List<Long> getCompetitorSeasonStatIds(GetCompetitorSeasonStatsParam p) throws TException {
        try {
            return competitorSeasonStatService.getCompetitorSeasonStatIds(p);
        } catch (Exception e) {
            LOG.error("fail to getCompetitorSeasonStatIds. p:{}", p, e);
        }
        return Collections.EMPTY_LIST;
    }


    @Override
    public List<TTopList> getCompetitorTTopLists(GetSeasonTopListsParam p, PageParam page, CallerParam caller) throws TException {
        try {
            caller = CallerUtils.getValidCaller(caller);
            return topListService.getCompetitorTTopLists(p, page, caller);
        } catch (Exception e) {
            LOG.error("fail to getCompetitorTTopLists. p:{},caller:{}", p, caller, e);
        }
        return null;
    }

    @Override
    public List<TMatch> getTMatchesByIds(List<Long> ids, CallerParam caller) throws TException {
        try {
            ids = IdCheckUtils.checkIds(ids);
            caller = CallerUtils.getValidCaller(caller);
            return matchService.getTMatchesByIds(ids, caller);
        } catch (Exception e) {
            LOG.error("fail to getMatchesByIds. ids:{}, caller : {}", ids, caller, e);
        }
        return Collections.EMPTY_LIST;
    }

    @Override
    public Map<String, String> syncZhangyuGames() throws TException {
        try {
//            return matchService.syncZhangyuGameMatches();
        } catch (Exception e) {
            LOG.error("fail to syncZhangyuGames.", e);
        }
        return Collections.EMPTY_MAP;
    }

    /**
     * 获取比赛详情
     */
    @Override
    public TDetailMatch getTDetailMatchById(long id, CallerParam caller) throws TException {

        try {
            caller = CallerUtils.getValidCaller(caller);
            return matchService.getTDetailMatchById(id, caller);
        } catch (Exception e) {
            LOG.error("fail to getDetailMatchById. id:{}, caller : {}", id, caller, e);
        }
        return null;
    }

    /**
     * 获取比赛基本信息
     */
    @Override
    public TMatch getTMatchById(long id, CallerParam caller) throws TException {
        try {
            caller = CallerUtils.getValidCaller(caller);
            return matchService.getTMatchById(id, caller);
        } catch (Exception e) {
            LOG.error("fail to getTMatchById. id:{}, caller : {}", id, caller, e);
        }
        return null;
    }

    @Override
    public List<TMatch> getMatchesByCompetitorId(long competitorId, int csid, PageParam pageParam, CallerParam caller) throws TException {
        try {
            Pageable pageable = PageUtils.convertToPageable(pageParam);
            caller = CallerUtils.getValidCaller(caller);
            return matchService.getMatchesByCompetitorId(competitorId, csid, pageable, caller);
        } catch (Exception e) {
            LOG.error("fail to getMatchesByCompetitorId. competitorId : {}, csid : {}, page param : {}, caller : {}, {}",
                    competitorId, csid, pageParam, caller, e.getMessage(), e);
        }
        return null;
    }

    @Override
    public List<TDetailMatch> getTDetailMatchesByPid(long pid, MatchStatus status, PageParam page, CallerParam caller) throws TException {
        try {
            caller = CallerUtils.getValidCaller(caller);
            return matchService.getTDetailMatchesByPid(pid, status, page, caller);
        } catch (Exception e) {
            LOG.error("fail to getTDetailMatchesByPid. pid:{},status:{},caller:{}", pid, status, caller, e);
        }
        return null;
    }

    @Override
    public List<TDetailMatch> getTDetailMatches(GetPlayerMatchesParam p, PageParam page, CallerParam caller) throws TException {
        try {
            caller = CallerUtils.getValidCaller(caller);
            return matchService.getTDetailMatches(p, page, caller);
        } catch (Exception e) {
            LOG.error("fail to getTDetailMatches. p:{},caller:{}", p, caller, e);
        }
        return null;
    }

    @Override
    public List<Long> getMatchIdsByCidAndMetaEntryId(long cid, long csid, long entryId, PageParam pageParam) throws TException {
        try {
            return matchService.getMatchIdsByCidAndMetaEntryId(cid, csid, entryId, pageParam);
        } catch (Exception e) {
            LOG.error("fail to getMatchIdsByCidAndMetaEntryId. cid:{},csid:{},entryId:{},pageParam:{}", cid, csid, entryId, pageParam, e);
        }
        return null;
    }

    @Override
    public TMatchStats getTMatchStatsById(long id, CallerParam caller) throws TException {
        try {
            caller = CallerUtils.getValidCaller(caller);
            return matchStatsService.getTMatchStatsByMatchId(id, caller);
        } catch (Exception e) {
            LOG.error("fail to getTMatchStatsById. id:{}", id, e);
        }
        return null;
    }

    @Override
    public TMatchAction getTMatchActionById(long id, CallerParam caller) throws TException {

        try {
            caller = CallerUtils.getValidCaller(caller);
            return matchActionService.getTMatchActionById(id, caller);
        } catch (Exception e) {
            LOG.error("fail to getDetailMatchById. id:{}, caller : {}", id, caller, e);
        }
        return null;
    }

    @Override
    public List<TMatchAction> getTMatchActionsByIds(List<Long> ids, CallerParam caller) throws TException {
        try {
            ids = IdCheckUtils.checkIds(ids);
            caller = CallerUtils.getValidCaller(caller);
            return matchActionService.getTMatchActionsByIds(ids, caller);
        } catch (Exception e) {
            LOG.error("fail to getMatchesByIds. ids:{}, caller : {}", ids, caller, e);
        }
        return Collections.EMPTY_LIST;
    }

    @Override
    public List<Long> getMatchActionsOfMatch(GetMacthActionsParam p, CallerParam caller) throws TException {
        try {
            caller = CallerUtils.getValidCaller(caller);
            return matchActionService.getMatchActionsOfMatch(p, caller);
        } catch (Exception e) {
            LOG.error("fail to getMatchActionsOfMatch. p:{}, caller:{}", p, caller, e);
        }
        return Collections.EMPTY_LIST;
    }

    @Override
    public TMatchReview getTMatchReviewById(long id, CallerParam caller) throws TException {
        try {
            caller = CallerUtils.getValidCaller(caller);
            return matchReviewService.getTMatchReviewById(id, caller);
        } catch (Exception e) {
            LOG.error("fail to getTMatchReviewById. id:{}, caller : {}", id, caller, e);
        }
        return null;
    }

}
