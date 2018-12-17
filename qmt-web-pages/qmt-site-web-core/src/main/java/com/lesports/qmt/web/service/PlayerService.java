package com.lesports.qmt.web.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.lesports.api.common.*;
import com.lesports.api.common.MatchStatus;
import com.lesports.qmt.sbd.api.common.*;
import com.lesports.qmt.sbd.api.dto.*;
import com.lesports.qmt.sbd.api.service.*;
import com.lesports.qmt.sbd.client.*;
import com.lesports.qmt.web.helper.MetaFiller;
import com.lesports.qmt.web.utils.Constants;
import com.lesports.utils.LeDateUtils;
import com.lesports.utils.PageUtils;
import com.lesports.utils.math.LeNumberUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
* Created by zhonglin on 2016/3/10.
*/
@Service("playerService")
public class PlayerService {

    private static final Logger LOG = LoggerFactory.getLogger(PlayerService.class);
    private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM月dd日");

    @Resource
    private MetaFiller metaFiller;

    /**
     * 新的通用页面填充球员信息
     *
     * @param model
     * @param tPlayer
     */
    public void fillModelWithPlayer(Model model, TPlayer tPlayer, CallerParam caller,Locale locale) {


        //球队参加过的赛事的最新赛季
        Map<Long,TCompetitionSeason> competitionSeasonMap = Maps.newHashMap();
        //数据统计
        Map<Long,TTeamSeason> teamSeasonMap = Maps.newHashMap();
        //球员号码
        Map<String,String> numberMap = Maps.newHashMap();
        //数据统计
        Map<String,Object> statsMap = Maps.newHashMap();

        //根据球队id获取所有球队赛季的数据
        GetTeamSeasonsParam getTeamSeasonsParam = new GetTeamSeasonsParam();
        getTeamSeasonsParam.setPid(tPlayer.getId());

        PageParam page = new PageParam();
        page.setPage(1);
        page.setCount(100);
        List<TTeamSeason> tTeamSeasons = SbdTeamSeasonApis.getTTeamSeasons(getTeamSeasonsParam, page, caller);

        //获取每个赛事最新的赛季
        if(CollectionUtils.isNotEmpty(tTeamSeasons)){
            for(TTeamSeason tTeamSeason:tTeamSeasons){
                TCompetitionSeason tCompetitionSeason = SbdCompetitonSeasonApis.getTCompetitionSeasonById(tTeamSeason.getCsid(), caller);
                if(tCompetitionSeason==null)continue;
                TCompetitionSeason existTCompetitionSeason = competitionSeasonMap.get(tCompetitionSeason.getCid());
                if(existTCompetitionSeason != null){
                    //如果是更新的赛季，替换旧的
                    if(LeNumberUtils.toLong(tCompetitionSeason.getSeason()) > LeNumberUtils.toLong(existTCompetitionSeason.getSeason())){
                        competitionSeasonMap.put(tCompetitionSeason.getCid(),tCompetitionSeason);
                        teamSeasonMap.put(tCompetitionSeason.getCid(),tTeamSeason);
                    }
                }else {
                    competitionSeasonMap.put(tCompetitionSeason.getCid(), tCompetitionSeason);
                    teamSeasonMap.put(tCompetitionSeason.getCid(),tTeamSeason);
                }
            }
        }
        String competitionSeasonName = "";
        if(MapUtils.isNotEmpty(competitionSeasonMap)){
            for (Long cid : competitionSeasonMap.keySet()) {
                //赛事
                TCompetition tCompetition = SbdCompetitionApis.getTCompetitionById(cid, caller);
                //赛季
                TCompetitionSeason tCompetitionSeason = competitionSeasonMap.get(cid);

                if(StringUtils.isBlank(competitionSeasonName)){
                    competitionSeasonName = tCompetitionSeason.getSeason()+tCompetition.getName();
                }
                else{
                    competitionSeasonName += "," + tCompetitionSeason.getSeason()+tCompetition.getName();
                }

                //球队统计
                List<TCompetitorSeasonStat> competitorSeasonStatses = SbdCompetitorSeasonStatApis.getTCompetitorSeasonStats(tPlayer.getId(), cid, 0);
                if(CollectionUtils.isNotEmpty(competitorSeasonStatses)){
                    for(TCompetitorSeasonStat tCompetitorSeasonStats:competitorSeasonStatses){
                        String year = tCompetitorSeasonStats.getStats().get("year");
                        List<TCompetitorSeasonStat> competitorSeasonStatsList;
                        if(StringUtils.isBlank(year))continue;

                        if(statsMap.get(year) == null){
                            competitorSeasonStatsList = Lists.newArrayList();
                        }
                        else{
                            competitorSeasonStatsList = (List<TCompetitorSeasonStat>)statsMap.get(year);
                        }

                        competitorSeasonStatsList.add(tCompetitorSeasonStats);
                        statsMap.put(year,competitorSeasonStatsList);
                    }
                }

                //获得当前赛季
                TTeamSeason tTeamSeason = teamSeasonMap.get(cid);
                //当前赛季阵容
                if(tTeamSeason!=null && tTeamSeason.getPlayers()!=null){
                    //寻找当前球员
                    for(TTeamPlayer tTeamPlayer:tTeamSeason.getPlayers()){
                        //不是当前球员
                        if(tTeamPlayer.getPid()!=tPlayer.getId())continue;

                        //获取当前球员的信息
                        TTeam tTeam = SbdTeamApis.getTTeamById(tTeamSeason.getTid(), caller);
                        if(tTeam.getTeamType() ==1 ){
                            numberMap.put("俱乐部",tTeamPlayer.getNumber()+"");
                        }
                        else{
                            numberMap.put("国家队",tTeamPlayer.getNumber()+"");
                        }
                        break;
                    }
                }
            }

        }

        metaFiller.fillPlayerMeta(model, tPlayer,competitionSeasonName,locale);

        //球员基本信息
        model.addAttribute("player",tPlayer);

        //球员统计
        model.addAttribute("playerSeasonStatses",statsMap);

        //球员号码
        model.addAttribute("playerNumber",numberMap);
    }

    /**
     * 通用球员页面填充球员信息
     *
     * @param model
     * @param tPlayer
     */
    public void fillModelWithPlayerNew(Model model, TPlayer tPlayer, CallerParam caller,Locale locale) {
        String competitionSeasonName = "";
        //球队统计
        List<TCompetitorSeasonStat> competitorSeasonStatList = SbdCompetitorSeasonStatApis.getTCompetitorSeasonStats(tPlayer.getId(), 0, 0);
        LOG.info("competitorSeasonStatList: {} ",competitorSeasonStatList);
        if(CollectionUtils.isNotEmpty(competitorSeasonStatList)){
            Collections.sort(competitorSeasonStatList, new Comparator<TCompetitorSeasonStat>() {
                @Override
                public int compare(TCompetitorSeasonStat o1, TCompetitorSeasonStat o2) {
                    return o2.getSeason().compareTo(o1.getSeason());
                }
            });
        }

        GetPlayerCareerStatParam p = new GetPlayerCareerStatParam();
        p.setPlayerId(tPlayer.getId());
        //TODO qmt中采用枚举 scopeType
//        p.setTeamType(-1);
        p.setScopeType(null);

        List<TPlayerCareerStat> tPlayerCareerStats = SbdPlayerCarerStatApis.getTPlayerCareerStats(p,caller);

        //seo信息
        metaFiller.fillPlayerMeta(model, tPlayer, competitionSeasonName, locale);

        //球员基本信息
        model.addAttribute("player",tPlayer);
        //球员统计
        model.addAttribute("playerStats",competitorSeasonStatList);
        //球员职业生涯
        model.addAttribute("careerStats",tPlayerCareerStats);

    }


    /**
     * 篮球通用球员页面填充信息
     *
     * @param model
     * @param tPlayer
     */
    public void fillModelWithBkPlayer(Model model, TPlayer tPlayer, CallerParam caller,Locale locale) {

        Map<String,Object> teamPlayerToplist = Maps.newHashMap();
        List<Map<String,String>> regularStats = Lists.newArrayList();
        List<Map<String,String>> playoffStats = Lists.newArrayList();
        List<TCompetitorSeasonStat> historyStats = Lists.newArrayList();
        Map<String,String> currentStats = Maps.newHashMap();
        List<TCompetitionSeason> competitionSeasons = Lists.newArrayList();



        Long csid = 0L;
        Long cid = 0L;


        //这里获取最新的nba赛季id
        GetTeamSeasonsParam p = new GetTeamSeasonsParam();
        p.setPid(tPlayer.getId());
        PageParam page = new PageParam();
        page.setPage(0);
        page.setCount(20);
//        Sort sort = new Sort();
//        sort.setOrders(Lists.newArrayList(new Order("create_at", Direction.DESC)));
        List<TTeamSeason> teamSeasons = SbdTeamSeasonApis.getTTeamSeasons(p,page,caller);
        //过滤出俱乐部的数据
        for(TTeamSeason tTeamSeason:teamSeasons){
            TTeam tTeam = SbdTeamApis.getTTeamById(tTeamSeason.getTid(),caller);
            if(tTeam.getTeamType() == TeamType.NATIONAL_TEAM.getValue()){
                TCompetitionSeason competitionSeason = SbdCompetitonSeasonApis.getTCompetitionSeasonById(tTeamSeason.getCsid(),caller);
                competitionSeasons.add(competitionSeason);
            }
        }

        //按年份排序
        if(CollectionUtils.isNotEmpty(competitionSeasons)){
            Collections.sort(competitionSeasons, new Comparator<TCompetitionSeason>() {
                @Override
                public int compare(TCompetitionSeason o1, TCompetitionSeason o2) {
                    return o2.getSeason().compareTo(o1.getSeason());
                }
            });
            csid = competitionSeasons.get(0).getId();
            cid = competitionSeasons.get(0).getCid();
        }

        //球员历史统计
        List<TCompetitorSeasonStat> competitorSeasonStatList = SbdCompetitorSeasonStatApis.getTCompetitorSeasonStats(tPlayer.getId(), cid, 0);
        if(CollectionUtils.isNotEmpty(competitorSeasonStatList)){
            Collections.sort(competitorSeasonStatList, new Comparator<TCompetitorSeasonStat>() {
                @Override
                public int compare(TCompetitorSeasonStat o1, TCompetitorSeasonStat o2) {
                    return o2.getSeason().compareTo(o1.getSeason());
                }
            });

            currentStats = competitorSeasonStatList.get(0).getAvgStats();

            //当前赛季有技术统计，历史技术统计中去掉当前赛季的数据
            if(competitorSeasonStatList.get(0).getCsid() == csid){
                if(competitorSeasonStatList.size()>1){
                    competitorSeasonStatList = competitorSeasonStatList.subList(1,competitorSeasonStatList.size()-1);
                }
                else{
                    competitorSeasonStatList = Lists.newArrayList();
                }
            }
            else{
                csid = competitorSeasonStatList.get(0).getCsid();
            }

            if(CollectionUtils.isNotEmpty(competitorSeasonStatList)){
                for(TCompetitorSeasonStat competitorSeasonStat:competitorSeasonStatList){
                    Map<String,String> stats = competitorSeasonStat.getStats();
                    Map<String,String> avgStats = competitorSeasonStat.getAvgStats();
                    TTeamSeason teamSeason = SbdTeamSeasonApis.getTTeamSeason(competitorSeasonStat.getCompetitorId(),competitorSeasonStat.getCsid(),caller);
                    if(teamSeason != null){
                        TTeam tTeam = SbdTeamApis.getTTeamById(teamSeason.getTid(),caller);
                        stats.put("teamName",tTeam.getName());
                        avgStats.put("teamName",tTeam.getName());
                    }
                    else{
                        stats.put("teamName","");
                        avgStats.put("teamName","");
                    }
                    competitorSeasonStat.setStats(stats);
                    competitorSeasonStat.setAvgStats(avgStats);
                    historyStats.add(competitorSeasonStat);
                }
            }
        }
        Map<String,String> avgStats = getPlayerAvgStats(historyStats);

        LOG.info("csid: {} ",csid);

        //数据排行
        Map<Long,String> typeMap = Constants.NBA_TOPLIST_MAP;
        for(Long type:typeMap.keySet()){
            LOG.info("type: {} ", type);
            TTopList topList = SbdTopListApis.getSomeTypeTopListOfSeason(cid,csid,type,caller);
            List<TTopListItem> tTopListItems = getTopListItemOfSomeTeamAndType(topList,tPlayer.getId(),currentStats,typeMap.get(type));
            teamPlayerToplist.put(typeMap.get(type),tTopListItems);
        }

        //常规赛比赛
        GetPlayerMatchesParam mp = new GetPlayerMatchesParam();
        mp.setPid(tPlayer.getId());
        mp.setCsid(csid);
        mp.setStage(Constants.BK_REGULAR_ID);
        mp.setStatus(MatchStatus.MATCH_END);
        LOG.info("mp: " + mp);
        List<TDetailMatch> matches = SbdMatchApis.getTDetailMatches(mp, PageUtils.createPageParam(0, 100), caller);
        for(TDetailMatch match:matches){
            Map<String,String> matchStats = getPlayerMatchStats(match,tPlayer.getId());
            if(null != matchStats){
                regularStats.add(matchStats);
            }
        }

        //季后赛统计
        mp = new GetPlayerMatchesParam();
        mp.setPid(tPlayer.getId());
        mp.setCsid(csid);
        mp.setStage(Constants.BK_PLAYOFF_ID);
        mp.setStatus(MatchStatus.MATCH_END);
        matches = SbdMatchApis.getTDetailMatches(mp, PageUtils.createPageParam(0, 100), caller);
        for(TDetailMatch match:matches){
            Map<String,String> matchStats = getPlayerMatchStats(match,tPlayer.getId());
            if(null != matchStats){
                playoffStats.add(matchStats);
            }
        }



        //seo信息
        metaFiller.fillPlayerMeta(model, tPlayer,"NBA",locale);
        //球员基本信息
        model.addAttribute("player",tPlayer);
        //球员统计
        model.addAttribute("playerStats",historyStats);
        //球员常规赛技术统计
        model.addAttribute("regularStats",regularStats);
        //球员季后赛技术统计
        model.addAttribute("playoffStats",playoffStats);
        //当前赛季统计
        model.addAttribute("currentStats",currentStats);
        //球员数据排行榜
        model.addAttribute("teamPlayerToplist",teamPlayerToplist);
        //球员生涯平均统计
        model.addAttribute("avgStats",avgStats);

    }

    /**
     * 获取某项技术统计中排行前五的球员信息，以及当前球员的排名
     *
     * @param topList
     * @param playerId
     * @return
     */
    public List<TTopListItem> getTopListItemOfSomeTeamAndType(TTopList topList,long playerId,Map<String,String> currentStats,String type) {
        List<TTopListItem> topListItems = Lists.newArrayList();
        if (topList == null || CollectionUtils.isEmpty(topList.getItems())) {
            return topListItems;
        }

        if(MapUtils.isEmpty(currentStats)){
            topListItems.subList(0,5);
        }

        TTopListItem curPlayerItem = null;
        boolean existFlag = false;
        for (TTopListItem item : topList.getItems()) {
            //如果当当前球员了
            if(item.getCompetitorId() == playerId){
                curPlayerItem = item;
                currentStats.put(type+"_order",item.getShowOrder()+"");
            }

            //废数据
            if(item.getShowOrder()<1){
                continue;
            }
            topListItems.add(item);



            //如果已经包含当前球员，而且超过5个人
            if(curPlayerItem!=null && topListItems.size()>=5){
                //如果5名开外
                if(curPlayerItem.getShowOrder() > 5 || curPlayerItem.getShowOrder() == 0){
                    topListItems = topListItems.subList(0,4);
                    topListItems.add(curPlayerItem);
                }
                else{
                    topListItems = topListItems.subList(0,5);
                }
            }
        }
        return topListItems;
    }

    /**
     * 获取本赛季各项技术统计和排名
     *
     * @param teamCurrentStats
     * @return
     */
    public Map<String,String> getCurrentStats(Map<String,String> teamCurrentStats,Long pid,long cid,long csid,CallerParam caller) {
        Map<Long,String> topListType = Constants.NBA_PLAYER_STATS_MAP;

        for(Long type:topListType.keySet()){
            GetSeasonTopListsParam p = new GetSeasonTopListsParam();
            p.setCid(cid);
            p.setCsid(csid);
            p.setType(type);
            p.setCompetitorType(CompetitorType.PLAYER);
            p.setScopeType(ScopeType.CONFERENCE);
            List<TTopList> topLists = SbdTopListApis.getCompetitorTTopLists(p,PageUtils.createPageParam(0,20),caller);

            if(CollectionUtils.isNotEmpty(topLists)){
                List<TTopListItem> topListItems = topLists.get(0).getItems();
                for(TTopListItem topListItem:topListItems){
                    if(topListItem.getCompetitorId() == pid){
                        teamCurrentStats.put(topListType.get(type),topListItem.getStats().get(topListType.get(type)));
                        teamCurrentStats.put(topListType.get(type)+"_order",topListItem.getShowOrder()+"");
                        break;
                    }
                }
            }
        }
        return teamCurrentStats;
    }


    /**
     * 获取球员职业生涯平均技术统计
     *
     * @param competitorSeasonStats
     * @return
     */
    public Map<String,String> getPlayerAvgStats(List<TCompetitorSeasonStat> competitorSeasonStats) {
        if(CollectionUtils.isEmpty(competitorSeasonStats)){
            LOG.info("competitorSeasonStats is null");
            return null;
        }
        LOG.info("getPlayerAvgStats begin");
        Map<String,String> playerStatsMap = Maps.newHashMap();
        Map<String,String>   statsMap   = Constants.NBA_COMPETITION_SEASON_STATS_MAP;
        DecimalFormat df  = new DecimalFormat("#.00");
        for(String key:statsMap.keySet()){
            double total = 0d;
            int size = 0;
            for(TCompetitorSeasonStat competitorSeasonStat:competitorSeasonStats){
                if(StringUtils.isNotBlank(competitorSeasonStat.getAvgStats().get(key))){
                    size ++;
                    total += Double.parseDouble(competitorSeasonStat.getAvgStats().get(key));
                }
            }
            if("1".equals(statsMap.get(key))){
                playerStatsMap.put(key,String .format("%.2f",(total/size)));
            }
            else{
                playerStatsMap.put(key,(new BigDecimal((total/size)+"").setScale(0, BigDecimal.ROUND_HALF_UP)).intValue()+"");
            }
            LOG.info("player stats type: {}, total: {} ,size: {} ",key,total,size);
        }
        return playerStatsMap;
    }

    /**
     * 球员聚合页
     *
     * @param model
     */
    public void fillModelWithPlayerList(Model model,CallerParam caller,Locale locale) {
        GetPlayers4SimpleSearchParam p = new GetPlayers4SimpleSearchParam();
        p.setFirstLetter("A");
        p.setCid(44001L);
        p.setPartnerType(499);
        List<TPlayer> tPlayers =  SbdPlayerApis.getPlayers4SimpleSearch(p,PageUtils.createPageParam(0,100),caller);
        model.addAttribute("teamList",tPlayers);
    }


    /**
     * 获取球员职业生涯平均技术统计
     *
     * @param match
     * @param pid
     * @return
     */
    public Map<String,String> getPlayerMatchStats(TDetailMatch match,long pid) {
        List<TSquad> squads = match.getSquads();
        List<TCompetitor> competitors = match.getCompetitors();
        TCompetitor homeCompetitor = null;
        TCompetitor awayCompetitor = null;

        for(TCompetitor competitor:competitors){
            if(competitor.getGround().equals(GroundType.HOME)){
                homeCompetitor = competitor;
            }
            else{
                awayCompetitor = competitor;
            }
        }
        Map<String,String> stats = null;

        for(TSquad squad:squads){
            for(TSimplePlayer simplePlayer:squad.getPlayers()){
                if(simplePlayer.getId() == pid){
                    stats = simplePlayer.getStats();
                    //拼接其他的统计数据
                    stats.put("matchTime",simpleDateFormat.format(LeDateUtils.parseYYYYMMDDHHMMSS(match.getStartTime())));
                    //主队
                    if(squad.getTid() == homeCompetitor.getId()){
                        stats.put("opponent",awayCompetitor.getName());
                        if(homeCompetitor.getFinalResult().compareTo(awayCompetitor.getFinalResult())>0){
                            stats.put("result","win");
                        }
                        else{
                            stats.put("result","lose");
                        }

                    }
                    else{
                        stats.put("opponent",homeCompetitor.getName());
                        if(awayCompetitor.getFinalResult().compareTo(homeCompetitor.getFinalResult())>0){
                            stats.put("result","win");
                        }
                        else{
                            stats.put("result","lose");
                        }
                    }

                    stats.put("vs",homeCompetitor.getFinalResult()+"-"+awayCompetitor.getFinalResult());
                }
            }
        }
        return stats;
    }

}
