package com.lesports.qmt.web.api.core.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.lesports.api.common.CallerParam;
import com.lesports.qmt.sbd.api.dto.TCompetitor;
import com.lesports.qmt.sbd.api.dto.TCompetitorStat;
import com.lesports.qmt.sbd.api.dto.TDetailMatch;
import com.lesports.qmt.sbd.api.dto.THistoryMatch;
import com.lesports.qmt.sbd.api.dto.TMatch;
import com.lesports.qmt.sbd.api.dto.TMatchAction;
import com.lesports.qmt.sbd.api.dto.TMatchInfo;
import com.lesports.qmt.sbd.api.dto.TPlayer;
import com.lesports.qmt.sbd.api.dto.TSimplePlayer;
import com.lesports.qmt.sbd.api.dto.TSquad;
import com.lesports.qmt.sbd.client.*;
import com.lesports.qmt.sbd.api.dto.*;
import com.lesports.qmt.sbd.api.service.GetMacthActionsParam;
import com.lesports.qmt.web.api.core.cache.impl.MatchCache;
import com.lesports.qmt.web.api.core.creater.MatchVoCreater;
import com.lesports.qmt.web.api.core.service.MatchService;
import com.lesports.qmt.web.api.core.vo.*;
import com.lesports.utils.math.LeNumberUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by gengchengliang on 2015/6/16.
 */
@Service("matchService")
public class MatchServiceImpl extends AbstractService implements MatchService {

	private static final Logger LOG = LoggerFactory.getLogger(MatchServiceImpl.class);

	@Resource
	private MatchVoCreater matchVoCreater;
    @Resource
    private MatchCache matchCache;

    @Override
    public List getMatchActions(GetMacthActionsParam p, CallerParam callerParam) {
        List returnList = Lists.newArrayList();
        TMatch tMatch = SbdMatchApis.getTMatchById(p.getMid(), callerParam);
        if (tMatch != null) {
            List<TCompetitor> competitors = tMatch.getCompetitors();
            if (CollectionUtils.isNotEmpty(competitors) && competitors.size() == 2) {
                List<TMatchAction> homeActions = Lists.newArrayList();
                List<TMatchAction> awayActions = Lists.newArrayList();
                Long homeId = competitors.get(0).getId();
                Long awayId = competitors.get(1).getId();
                List<TMatchAction> tMatchActions = SbdMatchActionApis.getMatchActionsOfMatch(p, callerParam);
                if (CollectionUtils.isNotEmpty(tMatchActions)) {
                    for (TMatchAction tMatchAction : tMatchActions) {
                        if (homeId.equals(tMatchAction.getTid())) {
                            homeActions.add(tMatchAction);
                        }
                        if (awayId.equals(tMatchAction.getTid())) {
                            awayActions.add(tMatchAction);
                        }
                    }
                    returnList.add(0, homeActions);
                    returnList.add(1, awayActions);
                }
            }
        } else {
            LOG.warn("this match is be null ! mid = {} ", p.getMid());
        }
        return returnList;
    }

    @Override
    public List<MatchAction> getMatchActionsByTimeLine(GetMacthActionsParam p, CallerParam callerParam) {

        List<MatchAction> matchActions = Lists.newArrayList();
        List<TMatchAction> tMatchActions = SbdMatchActionApis.getMatchActionsOfMatch(p, callerParam);
        if (CollectionUtils.isNotEmpty(tMatchActions)) {
            for (TMatchAction tMatchAction : tMatchActions) {
                matchActions.add(matchVoCreater.createMatchAction(tMatchAction, callerParam));
            }
        }
        return matchActions;
    }

    @Override
    public List getMatchActionsByTypes(GetMacthActionsParam p, CallerParam callerParam) {
        List returnList = Lists.newArrayList();
        TMatch tMatch = SbdMatchApis.getTMatchById(p.getMid(), callerParam);
        if (tMatch != null) {
            List<TCompetitor> competitors = tMatch.getCompetitors();
            if (CollectionUtils.isNotEmpty(competitors) && competitors.size() == 2) {
                List<MatchAction> homeActions = Lists.newLinkedList();
                List<MatchAction> awayActions = Lists.newLinkedList();
                Long homeId = competitors.get(0).getId();
                Long awayId = competitors.get(1).getId();
                List<TMatchAction> tMatchActions = SbdMatchActionApis.getMatchActionsOfMatch(p, callerParam);
                if (CollectionUtils.isNotEmpty(tMatchActions)) {
                    List<MatchAction> redCard4home = Lists.newLinkedList();
                    List<MatchAction> yellowCard4home = Lists.newLinkedList();
                    List<MatchAction> substitution4home = Lists.newLinkedList();
                    List<MatchAction> goal4home = Lists.newLinkedList();
                    List<MatchAction> redCard4away = Lists.newLinkedList();
                    List<MatchAction> yellowCard4away = Lists.newLinkedList();
                    List<MatchAction> substitution4away = Lists.newLinkedList();
                    List<MatchAction> goal4away = Lists.newLinkedList();
                    for (TMatchAction tMatchAction : tMatchActions) {
                        MatchAction matchAction = matchVoCreater.createMatchAction(tMatchAction, callerParam);
                        if (homeId.equals(tMatchAction.getTid())) {
                            if (tMatchAction.getTypeId() == 100159000l) {
                                goal4home.add(matchAction);
                            }
                            if (tMatchAction.getTypeId() == 100157000l) {
                                redCard4home.add(matchAction);
                            }
                            if (tMatchAction.getTypeId() == 100158000l) {
                                yellowCard4home.add(matchAction);
                            }
                            if (tMatchAction.getTypeId() == 104656000l) {
                                substitution4home.add(matchAction);
                            }
                        }
                        if (awayId.equals(tMatchAction.getTid())) {
                            if (tMatchAction.getTypeId() == 100159000l) {
                                goal4away.add(matchAction);
                            }
                            if (tMatchAction.getTypeId() == 100157000l) {
                                redCard4away.add(matchAction);
                            }
                            if (tMatchAction.getTypeId()  == 100158000l) {
                                yellowCard4away.add(matchAction);
                            }
                            if (tMatchAction.getTypeId()  == 104656000l) {
                                substitution4away.add(matchAction);
                            }
                        }
                    }
                    homeActions.addAll(goal4home);
                    homeActions.addAll(redCard4home);
                    homeActions.addAll(yellowCard4home);
                    homeActions.addAll(substitution4home);
                    awayActions.addAll(goal4away);
                    awayActions.addAll(redCard4away);
                    awayActions.addAll(yellowCard4away);
                    awayActions.addAll(substitution4away);
                    returnList.add(0, homeActions);
                    returnList.add(1, awayActions);
                }
            }
        } else {
            LOG.warn("this match is be null ! mid = {} ", p.getMid());
        }
        return returnList;
    }

    @Override
    public Map<Long, List<List<TSimplePlayer>>> getPlayerStatsOfMatch(long mid, CallerParam callerParam) {
        TMatch tMatch = SbdMatchApis.getTMatchById(mid, callerParam);
        TDetailMatch tDetailMatch = SbdMatchApis.getTDetailMatchById(mid, callerParam);
        if (null == tDetailMatch) {
            return Maps.newHashMap();
        }
        List<TSquad> tSquads = tDetailMatch.getSquads();
        if (CollectionUtils.isEmpty(tSquads)) {
            return Maps.newHashMap();
        }
        Map<Long, List<List<TSimplePlayer>>> squadMap = Maps.newHashMap();
        for (TSquad squad : tSquads) {
            List playerList = Lists.newArrayList();
            List<TSimplePlayer> players = squad.getPlayers();
            if (CollectionUtils.isNotEmpty(players)) {
                //首发
                List<TSimplePlayer> startingList = Lists.newArrayList();
                //替补
                List<TSimplePlayer> alternateList = Lists.newArrayList();
                for (TSimplePlayer simplePlayer : players) {
                    if (simplePlayer.isStarting()) {
                        startingList.add(simplePlayer);
                    } else {
                        alternateList.add(simplePlayer);
                    }
                }
                sortPlayerList(startingList, alternateList, tMatch.getCid());
                playerList.add(0, startingList);
                playerList.add(1, alternateList);
                squadMap.put(squad.getTid(), playerList);
            }
        }
        return squadMap;
    }

    @Override
    public Map<Long, List<BestPlayerStatsVo>> getBestPlayerStatsOfMatch(long mid, CallerParam caller) {
        Map<Long, List<BestPlayerStatsVo>> result = Maps.newHashMap();
        TDetailMatch tDetailMatch = SbdMatchApis.getTDetailMatchById(mid, caller);
        if (null == tDetailMatch) {
            return Maps.newHashMap();
        }
        List<TSquad> tSquads = tDetailMatch.getBestPlayerStats();
        if (CollectionUtils.isEmpty(tSquads)) {
            return result;
        }
        for (TSquad tSquad : tSquads) {
            List<BestPlayerStatsVo> bestPlayerStatsVos = Lists.newArrayList();
            List<TSimplePlayer> tSimplePlayerList = tSquad.getPlayers();
            if (CollectionUtils.isEmpty(tSimplePlayerList)) {
                continue;
            }
            for (TSimplePlayer tSimplePlayer : tSimplePlayerList) {
                BestPlayerStatsVo bestPlayerStatsVo = new BestPlayerStatsVo();
                TPlayer player = SbdPlayerApis.getTPlayerById(tSimplePlayer.getId(), caller);
                bestPlayerStatsVo.setPid(tSimplePlayer.getId());
                if (player != null) {
                    bestPlayerStatsVo.setLogo(player.getImageUrl() != null ? player.getImageUrl() : "");
                }
                bestPlayerStatsVo.setName(tSimplePlayer.getName());
                bestPlayerStatsVo.setNumber(tSimplePlayer.getNumber());
                bestPlayerStatsVo.setPosition(tSimplePlayer.getPosition() != null ? tSimplePlayer.getPosition() : "");
                bestPlayerStatsVo.setStarting(tSimplePlayer.isStarting());
                bestPlayerStatsVo.setStats(tSimplePlayer.getStats());
                bestPlayerStatsVos.add(bestPlayerStatsVo);
            }
            result.put(tSquad.getTid(), bestPlayerStatsVos);
        }
        return result;
    }


    @Override
    public List<TCompetitorStat> getNBAPlayerStatsOfMatch(long mid, CallerParam callerParam) {
        List<TCompetitorStat> tCompetitorStatList = Lists.newArrayList();
        TMatch tMatch = SbdMatchApis.getTMatchById(mid, callerParam);
        TDetailMatch tDetailMatch = SbdMatchApis.getTDetailMatchById(mid, callerParam);
        if (null == tDetailMatch) {
            return Collections.EMPTY_LIST;
        }
        List<TSquad> tSquads = tDetailMatch.getSquads();
        if (CollectionUtils.isEmpty(tSquads)) {
            return Collections.EMPTY_LIST;
        }
        Map<Long, List<TSimplePlayer>> squadMap = Maps.newHashMap();
        for (TSquad squad : tSquads) {
            List<TSimplePlayer> players = squad.getPlayers();
            if (CollectionUtils.isNotEmpty(players)) {
                //首发
                List<TSimplePlayer> startingList = Lists.newArrayList();
                //替补
                List<TSimplePlayer> alternateList = Lists.newArrayList();
                for (TSimplePlayer simplePlayer : players) {
                    simplePlayer.setStats(convertStats(simplePlayer.getStats()));
                    if (simplePlayer.isStarting()) {
                        startingList.add(simplePlayer);
                    } else {
                        alternateList.add(simplePlayer);
                    }
                }
                sortPlayerList(startingList, alternateList, tMatch.getCid());
                startingList.addAll(alternateList);
                squadMap.put(squad.getTid(), startingList);
            }
        }

        if (CollectionUtils.isNotEmpty(tDetailMatch.getCompetitorStats())) {
            for(TCompetitorStat tCompetitorStat : tDetailMatch.getCompetitorStats()){
                long tid = tCompetitorStat.getId();
                tCompetitorStat.setStats(convertStats(tCompetitorStat.getStats()));
                tCompetitorStat.setPlayerStats(squadMap.get(tid));
                tCompetitorStatList.add(tCompetitorStat);
            }
        }
        return tCompetitorStatList;
    }


    private void sortPlayerList(List<TSimplePlayer> startingList, List<TSimplePlayer> alternateList, final long cid) {
        //以球员号码排序
        Collections.sort(startingList, new Comparator<TSimplePlayer>() {
            @Override
            public int compare(TSimplePlayer p1, TSimplePlayer p2) {
                //nba首发球员按位置排名
                if (cid == 44001) {
                    if (p2.getPositionId() > 0 && p1.getPositionId() > 0) {
                        return LeNumberUtils.toInt(p2.getPositionId()) - LeNumberUtils.toInt(p1.getPositionId());
                    }
                }
                return p1.getNumber() - p2.getNumber();
            }
        });
        //上场时间排序
        Collections.sort(alternateList, new Comparator<TSimplePlayer>() {
            @Override
            public int compare(TSimplePlayer p1, TSimplePlayer p2) {
                //nba替补球员按上场时间排名
                if (cid == 44001) {
                    Double p1Minutes = LeNumberUtils.toDouble(p1.getStats().get("minutes"), 0);
                    Double p2Minutes = LeNumberUtils.toDouble(p2.getStats().get("minutes"), 0);
                    return p2Minutes.compareTo(p1Minutes);
                } else {
                    return p1.getNumber() - p2.getNumber();
                }
            }
        });
    }

    private Map<String,String> convertStats(Map<String,String> stats){
        Map<String,String> nbaStats = Maps.newHashMap();
        nbaStats.put("TREB",convertToString(stats.get("total_rebounds")));
        nbaStats.put("ASSIST",convertToString(stats.get("assists")));
        nbaStats.put("TO",convertToString(stats.get("turnovers")));
        nbaStats.put("P3",convertToString(stats.get("threepoint_made"))+"/"+convertToString(stats.get("threepoint_attempted")));
        nbaStats.put("OR",convertToString(stats.get("offensive_rebounds")));
        nbaStats.put("P2",(int)(LeNumberUtils.toFloat(stats.get("fieldgoals_made")) - LeNumberUtils.toFloat(stats.get("threepoint_made")))+"/"+(int)(LeNumberUtils.toFloat(stats.get("fieldgoals_attempted")) - LeNumberUtils.toFloat(stats.get("threepoint_attempted"))));
        nbaStats.put("FT",convertToString(stats.get("freethrows_made"))+"/"+convertToString(stats.get("freethrows_attempted")));
        nbaStats.put("DR",convertToString(stats.get("defensive_rebounds")));
        nbaStats.put("BLC",convertToString(stats.get("blockedshots")));
        nbaStats.put("PF",convertToString(stats.get("personalfouls")));
        nbaStats.put("MINS",stats.get("minutes"));
        nbaStats.put("PTS",convertToString(stats.get("points")));
        nbaStats.put("FG",convertToString(stats.get("fieldgoals_made"))+"/"+convertToString(stats.get("fieldgoals_attempted")));

        return nbaStats;

    }

    private String convertToString(String stat){
        return (int)LeNumberUtils.toFloat(stat)+"";
    }

    @Override
    public List<Squad> getSquadsRealtime(long matchId, CallerParam caller) {
        return getFromCache(matchSquadsLoadingCache, matchId, caller);
    }

    @Override
    public List<Squad> getSquads(long matchId, CallerParam caller) {
        List<Squad> returnList = Lists.newArrayList();
        TDetailMatch match = SbdMatchApis.getTDetailMatchById(matchId, caller);
        List<TSquad> squads = match.getSquads();
        List<TCompetitor> competitors = match.getCompetitors();
        if (CollectionUtils.isEmpty(squads) || CollectionUtils.isEmpty(competitors)) {
            return Collections.EMPTY_LIST;
        }
        for (TSquad tSquad : squads) {
            //如果阵容字段缺失,则认为本场比赛直播文件中无阵容数据
            if (StringUtils.isBlank(tSquad.getFormation())) {
                return Collections.EMPTY_LIST;
            }
            Squad squad = new Squad();
            List<TSimplePlayer> players = tSquad.getPlayers();
            if (CollectionUtils.isNotEmpty(players)) {
                //首发阵容
                List<TPlayer> startingList = Lists.newArrayList();
                //替补阵容
                List<TPlayer> alternateList = Lists.newArrayList();
                for (TSimplePlayer simplePlayer : players) {
                    TPlayer player = SbdPlayerApis.getTPlayerById(simplePlayer.getId(), caller);
                    if (player != null) {
                        player.setNumber(simplePlayer.getNumber());
                        player.setPosition(simplePlayer.getPosition());
                        player.setPositionId(simplePlayer.getPositionId());
                        player.setSquadOrder(simplePlayer.getSquadOrder());
                        if (simplePlayer.isStarting()) {
                            startingList.add(player);
                        } else {
                            alternateList.add(player);
                        }

                    }
                }
                //比赛阵容以球员号码排序
                Collections.sort(startingList, new Comparator<TPlayer>() {
                    @Override
                    public int compare(TPlayer o1, TPlayer o2) {
                        return o1.getSquadOrder() - o2.getSquadOrder();
                    }
                });
                Collections.sort(alternateList, new Comparator<TPlayer>() {
                    @Override
                    public int compare(TPlayer o1, TPlayer o2) {
                        return o1.getSquadOrder() - o2.getSquadOrder();
                    }
                });
                squad.setTid(tSquad.getTid());
                squad.setStarting(startingList);
                squad.setSubstitution(alternateList);
                squad.setFormation(tSquad.getFormation());
                for (TCompetitor competitor : competitors) {
                    if (competitor.getId() == tSquad.getTid()) {
                        //主客场
                        squad.setGround(competitor.getGround());
                        break;
                    }
                }
                returnList.add(squad);
            }
        }
        return returnList;
    }

    @Override
    public MatchAgainst getMatchAgainst(long id, CallerParam caller) {
        TMatchReview tMatchReview = SbdMatchReviewApis.getTMatchReviewById(id, caller);
        if (tMatchReview == null) {
            return null;
        }
        TDetailMatch match = SbdMatchApis.getTDetailMatchById(id, caller);
        if (match == null || CollectionUtils.isEmpty(match.getCompetitors())) {
            return null;
        }
        MatchAgainst matchAgainst = new MatchAgainst();
        matchAgainst.setUpdateAt(tMatchReview.getUpdateAt());
        //历史对阵
        if (CollectionUtils.isNotEmpty(tMatchReview.getConfrontations())) {
            MatchAgainst.Confrontations confrontations = matchAgainst.new Confrontations();
            confrontations.setMatches(matchVoCreater.getHistoryMatch(tMatchReview.getConfrontations()));
            confrontations.setStats(tMatchReview.getStats());
            matchAgainst.setConfrontations(confrontations);
        }
        //近期和未来赛程
        List<TMatchInfo> matchInfos = tMatchReview.getMatchInfos();
        //按照主客队整合数据
        if (CollectionUtils.isNotEmpty(matchInfos)) {
            List<TCompetitor> competitors = match.getCompetitors();
            for (TCompetitor competitor : competitors) {
                MatchAgainst.CompetitorMatchs near = matchAgainst.new CompetitorMatchs();
                MatchAgainst.CompetitorMatchs future = matchAgainst.new CompetitorMatchs();
                for (TMatchInfo matchInfo : matchInfos) {
                    if (matchInfo.getCompetitorId() == competitor.getId()) {
                        //近期赛程
                        List<THistoryMatch> nearMatches = matchInfo.getNearMatches();
                        if (CollectionUtils.isNotEmpty(nearMatches)) {
                            near.setCompetitorId(matchInfo.getCompetitorId());
                            near.setName(competitor.getName());
                            near.setGround(competitor.getGround());
                            near.setMatches(matchVoCreater.getHistoryMatch(nearMatches));
                            near.setStats(matchInfo.getStats());
                            matchAgainst.getNear().add(near);
                        }
                        //未来比赛
                        List<THistoryMatch> afterMatches = matchInfo.getAfterMatches();
                        if (CollectionUtils.isNotEmpty(afterMatches)) {
                            future.setCompetitorId(matchInfo.getCompetitorId());
                            future.setName(competitor.getName());
                            future.setGround(competitor.getGround());
                            future.setMatches(matchVoCreater.getHistoryMatch(afterMatches));
                            matchAgainst.getFuture().add(future);
                        }
                    }
                }
            }
        }
        return matchAgainst;
    }


    private LoadingCache<GetRealtimeCacheParam, List<Squad>> matchSquadsLoadingCache = CacheBuilder.newBuilder()
            .maximumSize(1000)
            .expireAfterWrite(5, TimeUnit.SECONDS)
            .build(new CacheLoader<GetRealtimeCacheParam, List<Squad>>() {
                @Override
                public List<Squad> load(GetRealtimeCacheParam key) throws Exception {
                    List<Squad> squads = matchCache.getMatchSquads(key);
                    if (null == squads) {
                        squads = getSquads(key.getEpisodeId(), key.getCaller());
                        if (CollectionUtils.isNotEmpty(squads)) {
                            matchCache.saveMatchSquads(key, squads);
                        }
                    }
                    LOG.info("save match squads : {} in memory cache.", key);

                    return squads;
                }
            });

    @Override
    public List<TCompetitorStat> getCompetitorStatsRealtime(long mid, CallerParam caller) {
        return getFromCache(competitorStatsLoadingCache, mid, caller);
    }

    private LoadingCache<GetRealtimeCacheParam, List<TCompetitorStat>> competitorStatsLoadingCache = CacheBuilder.newBuilder()
            .maximumSize(1000)
            .expireAfterWrite(5, TimeUnit.SECONDS)
            .build(new CacheLoader<GetRealtimeCacheParam, List<TCompetitorStat>>() {
                @Override
                public List<TCompetitorStat> load(GetRealtimeCacheParam key) throws Exception {
                    List<TCompetitorStat> competitorStats = matchCache.getCompetitorStats(key);
                    if (null == competitorStats) {
                        competitorStats = SbdMatchApis.getCompetitorStatsOfMatch(key.getEpisodeId(), key.getCaller());
                        if (CollectionUtils.isNotEmpty(competitorStats)) {
                            matchCache.saveCompetitorStats(key, competitorStats);
                        }
                    }
                    LOG.info("save match competitor stats : {}, {} in memory cache.",
                            key, JSONObject.toJSONString(competitorStats));

                    return competitorStats;
                }
            });

}
