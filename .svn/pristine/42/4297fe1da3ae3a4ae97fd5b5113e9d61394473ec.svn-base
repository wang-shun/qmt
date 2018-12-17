package com.lesports.qmt.web.api.core.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.lesports.api.common.CallerParam;
import com.lesports.qmt.sbc.api.dto.TComboEpisode;
import com.lesports.qmt.sbc.api.service.GetEpisodesOfSeasonByMetaEntryIdParam;
import com.lesports.qmt.sbc.api.service.LiveShowStatusParam;
import com.lesports.qmt.sbd.api.service.GetTeamSeasonsParam;
import com.lesports.qmt.web.api.core.creater.PlayerVoCreater;
import com.lesports.qmt.web.api.core.service.PlayerService;
import com.lesports.qmt.web.api.core.util.Constants;
import com.lesports.qmt.web.api.core.vo.PlayerMixedVo;
import com.lesports.qmt.sbd.api.common.CompetitorType;
import com.lesports.qmt.sbd.api.common.GroundType;
import com.lesports.qmt.sbd.api.common.ScopeType;
import com.lesports.qmt.sbd.api.common.TeamType;
import com.lesports.qmt.sbd.api.dto.*;
import com.lesports.qmt.sbd.api.service.GetPlayerCareerStatParam;
import com.lesports.qmt.sbd.api.service.GetSeasonTopListsParam;
import com.lesports.qmt.sbd.client.*;
import com.lesports.qmt.web.api.core.vo.PlayerVo;
import com.lesports.utils.LeProperties;
import com.lesports.utils.PageUtils;
import com.lesports.utils.math.LeNumberUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * Created by lufei1 on 2016/8/19.
 */
@Service("playerService")
public class PlayerServiceImpl implements PlayerService {

    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(PlayerServiceImpl.class);


    @Resource
    private PlayerVoCreater playerVoCreater;

    @Override
    public PlayerVo getPlayerById(Long id,CallerParam callerParam){

        TPlayer player = SbdPlayerApis.getTPlayerById(id, callerParam);
        return playerVoCreater.createrPlayerVo(player,callerParam);

    }

    @Override
    public Map<String,Object> getPlayerTeamMates(Long id,CallerParam callerParam){
        Map<String,Object> maps = Maps.newHashMap();
        TPlayer player = SbdPlayerApis.getTPlayerById(id, callerParam);
        if(null == player){
            return maps;
        }
        PlayingTeam clubTeam = player.getClubTeam();
        PlayingTeam nationalTeam = player.getNationalTeam();
        List<PlayerVo> clubPlayers = Lists.newArrayList();
        List<PlayerVo> nationalPlayers = Lists.newArrayList();

        if(null != clubTeam) {
            GetTeamSeasonsParam getTeamSeasonsParam = new GetTeamSeasonsParam();
            getTeamSeasonsParam.setTid(clubTeam.getTeamId());
            getTeamSeasonsParam.setCsid(clubTeam.getCurrentCsid());
            List<TTeamSeason> tTeamSeasons = SbdTeamSeasonApis.getTTeamSeasons(getTeamSeasonsParam, null, callerParam);
            if(CollectionUtils.isNotEmpty(tTeamSeasons)){
                TTeamSeason clubTeamSeason = tTeamSeasons.get(0);
                for (TTeamPlayer tTeamPlayer : clubTeamSeason.getPlayers()) {
                    if(tTeamPlayer.getPid() > 0) {
                        PlayerVo playerVo = new PlayerVo();
                        playerVo.setId(tTeamPlayer.getPid());
                        playerVo.setName(tTeamPlayer.getName());
                        playerVo.setLogo(tTeamPlayer.getLogo());
                        playerVo.setNumber(LeNumberUtils.toInt(tTeamPlayer.getNumber()));
                        clubPlayers.add(playerVo);
                    }
                }
            }
            maps.put("clubName",clubTeam.getName());
            maps.put("club",clubPlayers);
        }
        if(null != nationalTeam) {
            GetTeamSeasonsParam getTeamSeasonsParam2 = new GetTeamSeasonsParam();
            getTeamSeasonsParam2.setTid(nationalTeam.getTeamId());
            getTeamSeasonsParam2.setCsid(nationalTeam.getCurrentCsid());
            List<TTeamSeason> nationalTeamSeasons = SbdTeamSeasonApis.getTTeamSeasons(getTeamSeasonsParam2, null, callerParam);
            if (CollectionUtils.isNotEmpty(nationalTeamSeasons)) {
                TTeamSeason nationalTeamSeason = nationalTeamSeasons.get(0);
                for (TTeamPlayer tTeamPlayer : nationalTeamSeason.getPlayers()) {
                    if(tTeamPlayer.getPid() > 0) {
                        PlayerVo playerVo = new PlayerVo();
                        playerVo.setId(tTeamPlayer.getPid());
                        playerVo.setName(tTeamPlayer.getName());
                        playerVo.setLogo(tTeamPlayer.getLogo());
                        playerVo.setNumber(LeNumberUtils.toInt(tTeamPlayer.getNumber()));
                        nationalPlayers.add(playerVo);
                    }
                }
            }
            maps.put("countryName",nationalTeam.getName());
            maps.put("country",nationalPlayers);

        }

        return maps;

    }




    @Override
    public PlayerMixedVo getPlayerMixed(long playerId, CallerParam caller) {
        PlayerMixedVo playerMixedVo = new PlayerMixedVo();
        TPlayer tPlayer = SbdPlayerApis.getTPlayerById(playerId, caller);
        if (null == tPlayer) {
            return null;
        }
        playerMixedVo.setPlayer(tPlayer);
        fillFootballMixed(tPlayer, playerMixedVo, caller);
        return playerMixedVo;
    }

    @Override
    public PlayerMixedVo getPlayerMixed(long playerId,long cid, long csid, CallerParam caller) {
        PlayerMixedVo playerMixedVo = new PlayerMixedVo();
        TPlayer tPlayer = SbdPlayerApis.getTPlayerById(playerId, caller);
        if (null == tPlayer) {
            return null;
        }
        playerMixedVo.setPlayer(tPlayer);
        if (tPlayer.getGameFTypeId() == 100015000l) {//足球
            fillFootballMixed(tPlayer, playerMixedVo, caller);
        } else if (tPlayer.getGameFTypeId() == 100014000l) {//篮球
            fillBasketballMixed(tPlayer,cid,csid, playerMixedVo, caller);
        }
        return playerMixedVo;
    }

    @Override
    public List<Map<String,String>> getPlayerStats(long playerId,long cid, long csid, CallerParam caller) {
        TCompetitionSeason tCompetitionSeason = null;
        TTeam tTeam = null;
        List<Map<String,String>> seasonStats = Lists.newArrayList();

        //球员参见的赛事
        if(cid <= 0 ){
            return seasonStats;
        }
        if(csid <=0 ){
            tCompetitionSeason = SbdCompetitonSeasonApis.getLatestTCompetitionSeasonsByCid(cid, caller);
            csid = tCompetitionSeason.getId();
        }


        //球队阵容
        TTeamSeason tTeamSeason = SbdTeamSeasonApis.getTTeamSeason(playerId, csid, caller);
        if(tTeamSeason == null){
            return seasonStats;
        }
        tTeam = SbdTeamApis.getTTeamById(tTeamSeason.getTid(),caller);
        if(tTeam == null){
            return seasonStats;
        }


        //球队最近技术统计
        GetEpisodesOfSeasonByMetaEntryIdParam endParam = new GetEpisodesOfSeasonByMetaEntryIdParam();
        endParam.setCid(tTeam.getCurrentCid());
        endParam.setCsid(tTeam.getCurrentCsid());
        endParam.setLiveShowStatusParam(LiveShowStatusParam.LIVE_END);

        int page = 1;
//        while(true){
//            List<TComboEpisode> endTComboEpisodes = SopsApis.getCurrentEpisodesByCompetitorId(tTeam.getId(), endParam, PageUtils.createPageParam(page, 100), caller);
//            if(CollectionUtils.isEmpty(endTComboEpisodes)){
//                break;
//            }
//            for(TComboEpisode tComboEpisode:endTComboEpisodes){
//                TDetailMatch tDetailMatch = SbdMatchApis.getTDetailMatchById(tComboEpisode.getMid(),caller);
//                if(tDetailMatch == null){
//                    continue;
//                }
//                Map<String,String> matchStats = getPlayerMatchStats(tDetailMatch,playerId,tTeam.getId());
//                if(matchStats != null){
//                    seasonStats.add(matchStats);
//                }
//            }
//            page ++;
//        }
        return seasonStats;
    }

    /**
     * 足球信息
     * @param tPlayer
     * @param playerMixedVo
     * @param caller
     */
    private void fillFootballMixed(TPlayer tPlayer, PlayerMixedVo playerMixedVo, CallerParam caller) {
        Map<String, PlayerMixedVo.PlayerTeamStat> playerTeamStatMap = Maps.newHashMap();
        PlayerMixedVo.PlayerTeamStat nationStat = new PlayerMixedVo.PlayerTeamStat();
        PlayerMixedVo.PlayerTeamStat clubStat = new PlayerMixedVo.PlayerTeamStat();

        //生涯统计
        GetPlayerCareerStatParam param = new GetPlayerCareerStatParam();
        param.setPlayerId(tPlayer.getId());
        param.setScopeType(null);
        List<TPlayerCareerStat> playerCareerStats = SbdPlayerCarerStatApis.getTPlayerCareerStats(param, caller);
        if (CollectionUtils.isNotEmpty(playerCareerStats)) {
            for (TPlayerCareerStat playerCareerStat : playerCareerStats) {
                PlayingTeam nationalPlayingTeam = tPlayer.getNationalTeam();
                PlayingTeam clubPlayingTeam = tPlayer.getClubTeam();
                if (playerCareerStat != null && playerCareerStat.getTeamId() == nationalPlayingTeam.getTeamId()) {
                    nationStat.setPlayerCareerStat(playerCareerStat);
                } else if (playerCareerStat != null && playerCareerStat.getTeamId() == clubPlayingTeam.getTeamId()) {
                    clubStat.setPlayerCareerStat(playerCareerStat);
                }
            }
        }

        //历史数据统计
        List<TCompetitorSeasonStat> competitorSeasonStats = SbdCompetitorSeasonStatApis.getTCompetitorSeasonStats(tPlayer.getId(), 0, 0);
        List<TCompetitorSeasonStat> nationHistoryStats = Lists.newLinkedList();
        List<TCompetitorSeasonStat> clubHistoryStats = Lists.newLinkedList();
        if (CollectionUtils.isNotEmpty(competitorSeasonStats)) {
            Collections.sort(competitorSeasonStats, new Comparator<TCompetitorSeasonStat>() {
                @Override
                public int compare(TCompetitorSeasonStat o1, TCompetitorSeasonStat o2) {
                    return LeNumberUtils.toInt(o2.getSeason()) - LeNumberUtils.toInt(o1.getSeason());
                }
            });
            for (TCompetitorSeasonStat competitorSeasonStat : competitorSeasonStats) {
                if (competitorSeasonStat != null && TeamType.NATIONAL_TEAM.getValue() == competitorSeasonStat.getTeamType()) {
                    nationHistoryStats.add(competitorSeasonStat);
                } else if (competitorSeasonStat != null && TeamType.CLUB_TEAM.getValue() == competitorSeasonStat.getTeamType()) {
                    clubHistoryStats.add(competitorSeasonStat);
                }
            }
            nationStat.setHistoryStats(nationHistoryStats);
            clubStat.setHistoryStats(clubHistoryStats);
        }
        playerTeamStatMap.put("nation", nationStat);
        playerTeamStatMap.put("club", clubStat);
        playerMixedVo.setStats(playerTeamStatMap);
    }


    /**
     * 篮球信息
     * @param tPlayer
     * @param playerMixedVo
     * @param caller
     */
    private void fillBasketballMixed(TPlayer tPlayer,long cid,long csid, PlayerMixedVo playerMixedVo, CallerParam caller) {
        TCompetitionSeason tCompetitionSeason = null;
        TTeam tTeam = null;
        List<Map<String,String>> matchesStats = Lists.newArrayList();
        List<Map<String,String>> historyStats = Lists.newArrayList();
        Map<String,String> currentStats = null;
        Map<String,String> currentTopStats = null;
        Map<String,Object> basketballStats = Maps.newConcurrentMap();

        //球员参加的赛事
        if(cid <= 0 ){
            return ;
        }
        if(csid <=0 ){
            tCompetitionSeason = SbdCompetitonSeasonApis.getLatestTCompetitionSeasonsByCid(cid, caller);
            csid = tCompetitionSeason.getId();
        }


        if(csid <= 0){
            return;
        }

        //球队阵容
        TTeamSeason tTeamSeason = SbdTeamSeasonApis.getTTeamSeason(tPlayer.getId(),csid,caller);
        if(tTeamSeason == null){
            return;
        }
        tTeam = SbdTeamApis.getTTeamById(tTeamSeason.getTid(),caller);
        if(tTeam == null){
            return;
        }

        for(TTeamPlayer tTeamPlayer:tTeamSeason.getPlayers()){
            if(tTeamPlayer.getPid() == tPlayer.getId()){
                PlayingTeam playingTeam = new PlayingTeam();
                playingTeam.setTeamId(tTeamSeason.getTid());
                playingTeam.setName(tTeam.getName());
                playingTeam.setMultiLangNames(tTeam.getMultiLangNames());
                playingTeam.setNumber((int)tTeamPlayer.getNumber());
                playingTeam.setLogo(tTeam.getLogoUrl());
                playingTeam.setTeamType(tTeam.getTeamType());
                playingTeam.setMultiCounLogos(tTeam.getMultiCounLogos());
                tPlayer.setClubTeam(playingTeam);
                if(cid == 39001){
                    tPlayer.setExperience(null);
                }
                playerMixedVo.setPlayer(tPlayer);
            }
        }



        //球队最近技术统计
        GetEpisodesOfSeasonByMetaEntryIdParam endParam = new GetEpisodesOfSeasonByMetaEntryIdParam();
        endParam.setCid(tTeam.getCurrentCid());
        endParam.setCsid(tTeam.getCurrentCsid());
        endParam.setLiveShowStatusParam(LiveShowStatusParam.LIVE_END);
//        List<TComboEpisode> endTComboEpisodes = SopsApis.getCurrentEpisodesByCompetitorId(tTeam.getId(), endParam, PageUtils.createPageParam(0, 6), caller);
//
//        for(TComboEpisode tComboEpisode:endTComboEpisodes){
//            TDetailMatch tDetailMatch = SbdMatchApis.getTDetailMatchById(tComboEpisode.getMid(),caller);
//            Map<String,String> matchStats = getPlayerMatchStats(tDetailMatch,tPlayer.getId(),tTeam.getId());
//            if(matchStats != null){
//                matchesStats.add(matchStats);
//                matchesStats.add(matchStats);
//            }
//        }
        basketballStats.put("matches", matchesStats);


        //获取球员的赛季统计
        List<TCompetitorSeasonStat> tCompetitorSeasonStats = SbdCompetitorSeasonStatApis.getTCompetitorSeasonStats(tPlayer.getId(), cid, 0);
        if(CollectionUtils.isNotEmpty(tCompetitorSeasonStats)){
            Collections.sort(tCompetitorSeasonStats, new Comparator<TCompetitorSeasonStat>() {
                @Override
                public int compare(TCompetitorSeasonStat o1, TCompetitorSeasonStat o2) {
                    return o2.getSeason().compareTo(o1.getSeason());
                }
            });
        }
        for (TCompetitorSeasonStat tCompetitorSeasonStat : tCompetitorSeasonStats) {
            Map<String,String> avgStats = tCompetitorSeasonStat.getAvgStats();
            Map<String,String> stats = tCompetitorSeasonStat.getAvgStats();
            avgStats.put("season",tCompetitorSeasonStat.getSeason());
            avgStats.put("csid",tCompetitorSeasonStat.getCsid()+"");
            avgStats.put("teamName",tCompetitorSeasonStat.getTeamName());
            if(tCompetitorSeasonStat.getCsid() == csid){
                currentStats = avgStats;
                currentTopStats = tCompetitorSeasonStat.getTopStats();
            }
            else{
                historyStats.add(avgStats);
            }
        }

        if(currentStats == null && CollectionUtils.isNotEmpty(tCompetitorSeasonStats)){
            currentStats = historyStats.get(0);
            currentTopStats = tCompetitorSeasonStats.get(0).getTopStats();
        }

        if(currentStats != null){
            //获得统计项最大值
            Map<Long,String> typeMap = Constants.NBA_DATE_LIST_MAP;
            Map<Long,String> typeDefaultMap = Constants.DEFAULT_DATE_LIST_MAP;
            for(Long type:typeMap.keySet()){
                GetSeasonTopListsParam p = new GetSeasonTopListsParam();
                p.setCid(cid);
                p.setCsid(tTeam.getCurrentCsid());
                p.setCompetitorType(CompetitorType.PLAYER);
                p.setType(type);
                p.setScopeType(ScopeType.CONFERENCE);
                List<TTopList> topLists = SbdTopListApis.getSeasonTopLists(p, PageUtils.createPageParam(0, 1), caller);
                if(CollectionUtils.isEmpty(topLists)){
                    currentStats.put(typeMap.get(type),typeDefaultMap.get(type));
                    continue;
                }
                TTopListItem firstTopListItem = getFirstTopListItem(topLists.get(0));
                if(firstTopListItem != null){
                    currentStats.put(typeMap.get(type),firstTopListItem.getStats().get(typeMap.get(type)));
                }
            }
            //赛季最高
            if(currentTopStats != null){
                currentStats.put("seasonTop",currentTopStats.get("points"));
            }
            //生涯最高
            GetPlayerCareerStatParam param = new GetPlayerCareerStatParam();
            param.setPlayerId(tPlayer.getId());
            param.setScopeType(null);
            List<TPlayerCareerStat> playerCareerStats = SbdPlayerCarerStatApis.getTPlayerCareerStats(param, caller);
            if(CollectionUtils.isNotEmpty(playerCareerStats)){
                TPlayerCareerStat playerCareerStat = playerCareerStats.get(0);
                currentStats.put("careerTop",playerCareerStat.getStats().get("points"));
            }


            basketballStats.put("current",currentStats);
        }

        basketballStats.put("history",historyStats);
        playerMixedVo.setBasketballStats(basketballStats);
    }

    @Override
    public List<TPlayer> getHotPlayersByCid(Long cid, CallerParam callerParam){
        List<TPlayer> players = Lists.newArrayList();

        String hotId = LeProperties.getString("player.pk.hot."+cid);
        if(StringUtils.isBlank(hotId)){
            return players;
        }

        String[] ids = hotId.split("\\|");

        for(String pid:ids){
            TPlayer tPlayer = SbdPlayerApis.getTPlayerById(Long.parseLong(pid),callerParam);
            if(tPlayer != null){
                players.add(tPlayer);
            }
        }
        return players;
    }

    @Override
    public Map<String,Object> getPKPlayersStats(Long pid,Long pkId,Long cid, CallerParam callerParam){
        Map<String,Object> result = null;

        TCompetitionSeason competitionSeason = SbdCompetitonSeasonApis.getLatestTCompetitionSeasonsByCid(cid,callerParam);
        if(competitionSeason == null){
            return result;
        }

        TPlayer player = SbdPlayerApis.getTPlayerById(pid,callerParam);
        if(player == null){
            return result;
        }
        TPlayer pkPlayer = SbdPlayerApis.getTPlayerById(pkId,callerParam);


        TCompetitionSeason tCompetitionSeason = SbdCompetitonSeasonApis.getLatestTCompetitionSeasonsByCid(cid, callerParam);
        if(tCompetitionSeason == null){
            return result;
        }
        result = Maps.newHashMap();
        List<TCompetitorSeasonStat> tCompetitorSeasonStats = SbdCompetitorSeasonStatApis.getTCompetitorSeasonStats(pid,cid,tCompetitionSeason.getId());
        if(CollectionUtils.isNotEmpty(tCompetitorSeasonStats)){
            result.put("playerStat",tCompetitorSeasonStats.get(0).getAvgStats());
        }
        else{
            result.put("playerStat",null);
        }
        result.put("player",player);

        List<TCompetitorSeasonStat> tCompetitorSeasonStats1 = SbdCompetitorSeasonStatApis.getTCompetitorSeasonStats(pkId,cid,tCompetitionSeason.getId());
        if(pkPlayer!=null && CollectionUtils.isNotEmpty(tCompetitorSeasonStats1)){
            result.put("pkPlayerStat",tCompetitorSeasonStats1.get(0).getAvgStats());
        }
        else{
            result.put("pkPlayerStat",null);
        }
        result.put("pkPlayer",pkPlayer);



        //获得统计项最大值
        Map<Long,String> typeMap = Constants.NBA_DATE_LIST_MAP;
        Map<Long,String> typeDefaultMap = Constants.DEFAULT_DATE_LIST_MAP;
        Map<String,Object> topStat = Maps.newHashMap();
        for(Long type:typeMap.keySet()){
            GetSeasonTopListsParam p = new GetSeasonTopListsParam();
            p.setCid(cid);
            p.setCsid(competitionSeason.getId());
            p.setCompetitorType(CompetitorType.PLAYER);
            p.setType(type);
            p.setScopeType(ScopeType.CONFERENCE);
            List<TTopList> topLists = SbdTopListApis.getSeasonTopLists(p, PageUtils.createPageParam(0, 1), callerParam);
            if(CollectionUtils.isEmpty(topLists)){
                topStat.put(typeMap.get(type),typeDefaultMap.get(type));
                continue;
            }
            TTopListItem firstTopListItem = getFirstTopListItem(topLists.get(0));
            if(firstTopListItem != null){
                topStat.put(typeMap.get(type),firstTopListItem.getStats().get(typeMap.get(type)));
            }
        }

        result.put("topStat",topStat);

        return result;
    }



    /**
     * 获取球员职业生涯平均技术统计
     *
     * @param match
     * @param pid
     * @return
     */
    public Map<String,String> getPlayerMatchStats(TDetailMatch match,long pid,long tid) {
        List<TSquad> squads = match.getSquads();
        Map<String,String> stats = null;
        String opponentName = "";
        String homeScore = "";
        String awayScore = "";
        String ground = "";
        if(CollectionUtils.isEmpty(match.getSquads())){
            return stats;
        }


        for(TCompetitor competitor:match.getCompetitors()){
            if(competitor.getId() != tid){
                opponentName = competitor.getName();
            }
            else{
                ground = competitor.getGround().name();
            }

            if(competitor.getGround().equals(GroundType.HOME)){
                homeScore = competitor.getFinalResult();
            }
            else{
                awayScore = competitor.getFinalResult();
            }
        }
        for(TSquad squad:squads){
            if(squad.getTid() != tid){
                continue;
            }
            for(TSimplePlayer simplePlayer:squad.getPlayers()){
                if(simplePlayer.getId() == pid){
                    stats = simplePlayer.getStats();
                    //拼接其他的统计数据
                    stats.put("matchTime",match.getStartTime());
                    stats.put("opponentName",opponentName);
                    stats.put("homeScore",homeScore);
                    stats.put("awayScore",awayScore);
                    stats.put("ground",ground);
                    break;
                }
            }
        }
        return stats;
    }


    /**
     * 获取某项技术统计中第一的数据
     *
     * @param topList
     * @return
     */
    public TTopListItem getFirstTopListItem(TTopList topList) {
        if (topList == null || CollectionUtils.isEmpty(topList.getItems())) {
            return null;
        }
        for(TTopListItem topListItem:topList.getItems()){
            if(topListItem.getShowOrder()>0){
                return topListItem;
            }
        }
        return null;
    }
}
