package com.lesports.qmt.web.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.lesports.api.common.CallerParam;
import com.lesports.api.common.PageParam;
import com.lesports.qmt.sbc.api.dto.TComboEpisode;
import com.lesports.qmt.sbc.api.service.GetEpisodesOfSeasonByMetaEntryIdParam;
import com.lesports.qmt.sbc.api.service.LiveShowStatusParam;
import com.lesports.qmt.sbc.client.QmtSbcEpisodeApis;
import com.lesports.qmt.sbd.api.common.CompetitorType;
//import com.lesports.qmt.sbd.api.common.LiveShowStatus;
import com.lesports.qmt.sbd.api.common.ScopeType;
import com.lesports.qmt.sbd.api.dto.*;
import com.lesports.qmt.sbd.api.service.GetSeasonTopListsParam;
import com.lesports.qmt.sbd.api.service.GetTeamSeasonsParam;
import com.lesports.qmt.sbd.client.*;
import com.lesports.qmt.web.helper.MetaFiller;
import com.lesports.qmt.web.model.TeamPlayerStatsVo;
import com.lesports.qmt.web.model.TeamTopListVo;
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
import java.util.*;

/**
* Created by zhonglin on 2016/3/8.
*/
@Service("teamService")
public class TeamService {

    private static final Logger LOG = LoggerFactory.getLogger(TeamService.class);

    //中超球队排行榜类型
    public static final Long TOPLIST_TYPE = 100162000L;
    @Resource
    private MetaFiller metaFiller;
    @Resource
    private TopListService topListService;


    /**
     * 通用页面填充球队信息
     *
     * @param model
     * @param tTeam
     */
    public void fillModelWithTeam(Model model, TTeam tTeam, CallerParam caller,Locale locale) {

        //球队参加过的赛事的最新赛季
        Map<Long,TCompetitionSeason> competitionSeasonMap = Maps.newHashMap();

        //球队排名
        Map<String,Object> showOrderMap = Maps.newHashMap();
        //近期赛程
        Map<String,Object> teamEpisodesMap = Maps.newHashMap();
        //数据统计
        Map<String,Object> statsMap = Maps.newHashMap();
        //赛季
        Map<Long,TTeamSeason> teamSeasonMap = Maps.newHashMap();
        //球队阵容
        TTeamSeason curTeamSeason = null;
        //赛事名称
        String competitionSeasonName = "";


        //根据球队id获取所有球队赛季的数据
        GetTeamSeasonsParam getTeamSeasonsParam = new GetTeamSeasonsParam();
        getTeamSeasonsParam.setTid(tTeam.getId());

        PageParam page = new PageParam();
        page.setPage(0);
        page.setCount(100);
        List<TTeamSeason> tTeamSeasons = SbdTeamSeasonApis.getTTeamSeasons(getTeamSeasonsParam, page, caller);


        //获取每个赛事最新的赛季
        if(CollectionUtils.isNotEmpty(tTeamSeasons)){
            for(TTeamSeason tTeamSeason:tTeamSeasons){
                TCompetitionSeason tCompetitionSeason = SbdCompetitonSeasonApis.getTCompetitionSeasonById(tTeamSeason.getCsid(), caller);
                if(tCompetitionSeason == null) continue;
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

        //根据赛事和最新赛季获取相关数据
        if(MapUtils.isNotEmpty(competitionSeasonMap)){
            for (Long cid : competitionSeasonMap.keySet()) {
                //赛事
                TCompetition tCompetition = SbdCompetitionApis.getTCompetitionById(cid, caller);
                //赛季
                TCompetitionSeason tCompetitionSeason = competitionSeasonMap.get(cid);

                //当前赛事的赛程
                GetEpisodesOfSeasonByMetaEntryIdParam getEpisodesOfSeasonByMetaEntryIdParam = new GetEpisodesOfSeasonByMetaEntryIdParam();
                getEpisodesOfSeasonByMetaEntryIdParam.setCid(cid);
                getEpisodesOfSeasonByMetaEntryIdParam.setCsid(tCompetitionSeason.getId());


                List<TComboEpisode>  tComboEpisodes =  QmtSbcEpisodeApis.getCurrentEpisodesByCompetitorId(tTeam.getId(), getEpisodesOfSeasonByMetaEntryIdParam, page, caller);


                //判断赛季是否进行中
                boolean ongoingFlag = true;
                if(CollectionUtils.isNotEmpty(tComboEpisodes)){
                    TComboEpisode firstEpisodes = tComboEpisodes.get(0);
                    TComboEpisode lastEpisodes = tComboEpisodes.get(tComboEpisodes.size()-1);

                    //赛季没有开始或者已经结束
                    if(firstEpisodes.getStatus().equals(LiveShowStatusParam.LIVE_NOT_START) || lastEpisodes.getStatus().equals(LiveShowStatusParam.LIVE_END)){
                        ongoingFlag = false;
                    }
                }
                else{
                    ongoingFlag = false;
                }

                //球队统计
                List<TCompetitorSeasonStat> competitorSeasonStatses = SbdCompetitorSeasonStatApis.getTCompetitorSeasonStats(tTeam.getId(), cid, 0);
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

                //赛季阵容
                TTeamSeason tTeamSeason = teamSeasonMap.get(cid);
                if(tTeamSeason != null && CollectionUtils.isNotEmpty(tTeamSeason.getPlayers())){
                    if(curTeamSeason == null && tTeamSeason.getPlayers().size()> 11){
                        curTeamSeason = tTeamSeason;
                    }
                    else if(tTeamSeason.getCocahName()!=null && tTeamSeason.getPlayers().size()>11){
                        curTeamSeason = tTeamSeason;
                    }
                }



                //赛事不是进行中的就不显示赛程和排名数据
                if(!ongoingFlag){
                    continue;
                }

                //近期赛程
                if(CollectionUtils.isNotEmpty(tComboEpisodes)){
                    teamEpisodesMap.put(tCompetitionSeason.getSeason()+tCompetition.getName(),tComboEpisodes);
                    if(StringUtils.isBlank(competitionSeasonName)){
                        competitionSeasonName = tCompetitionSeason.getSeason()+tCompetition.getName();
                    }
                    else{
                        competitionSeasonName += "," + tCompetitionSeason.getSeason()+tCompetition.getName();
                    }
                }



                //排名
                TTopList tTopList = SbdTopListApis.getLatestSeasonTopList(cid, TOPLIST_TYPE, 0, caller);
                if(tTopList!=null){
                    List<TTopListItem> tTopListItems = tTopList.getItems();
                    if(CollectionUtils.isNotEmpty(tTopListItems)){
                        for(TTopListItem topListItem:tTopListItems){
                            if(topListItem.getCompetitorId()==tTeam.getId()){
                                //排名
                                showOrderMap.put(tCompetition.getName(),topListItem.getShowOrder());
                            }
                        }
                    }
                }
            }
        }

        //球队基本信息

        metaFiller.fillTeamMeta(model, tTeam, competitionSeasonName,locale);
        model.addAttribute("team",tTeam);
        if(curTeamSeason!=null){
            model.addAttribute("teamSeasons",curTeamSeason);
        }
        model.addAttribute("showOrder",showOrderMap);
        model.addAttribute("teamEpisodes",teamEpisodesMap);
        model.addAttribute("competitorSeasonStatses",statsMap);
    }

    /**
     * 球队通用页面填充球队信息
     *
     * @param model
     * @param tTeam
     */
    public void fillModelWithTeamNew(Model model, TTeam tTeam, CallerParam caller,Locale locale) {


        //球队阵容
        TTeamSeason curTeamSeason = null;
        //赛事名称
        String competitionSeasonName = "";

        //取得当前赛季的赛季阵容
        GetTeamSeasonsParam p = new GetTeamSeasonsParam();
        p.setTid(tTeam.getId());
        p.setCsid(tTeam.getCurrentCsid());
        List<TTeamSeason> tTeamSeasons = SbdTeamSeasonApis.getTTeamSeasons(p,PageUtils.createPageParam(0,10),caller);
        if(CollectionUtils.isNotEmpty(tTeamSeasons)){
            curTeamSeason = tTeamSeasons.get(0);
        }

        //近期赛程

        //三场未结束的赛程
        GetEpisodesOfSeasonByMetaEntryIdParam param = new GetEpisodesOfSeasonByMetaEntryIdParam();
        param.setCid(tTeam.getCurrentCid());
        if (tTeam.getCurrentCsid() > 0) {
            param.setCsid(tTeam.getCurrentCsid());
        }
        param.setLiveShowStatusParam(LiveShowStatusParam.LIVE_NOT_END);
        List<TComboEpisode> tComboEpisodes =  QmtSbcEpisodeApis.getCurrentEpisodesByCompetitorId(tTeam.getId(), param, PageUtils.createPageParam(0, 3), caller);

        //五场已结束的赛程
        GetEpisodesOfSeasonByMetaEntryIdParam endParam = new GetEpisodesOfSeasonByMetaEntryIdParam();
        endParam.setCid(tTeam.getCurrentCid());
        if (tTeam.getCurrentCsid() > 0) {
            endParam.setCsid(tTeam.getCurrentCsid());
        }
        endParam.setLiveShowStatusParam(LiveShowStatusParam.LIVE_END);
        List<TComboEpisode> endTComboEpisodes = QmtSbcEpisodeApis.getCurrentEpisodesByCompetitorId(tTeam.getId(), endParam, PageUtils.createPageParam(0, 5), caller);

        List<TComboEpisode> episodes = Lists.newArrayList();
        Collections.reverse(tComboEpisodes);
        episodes.addAll(tComboEpisodes);
        episodes.addAll(endTComboEpisodes);

        //球队统计
        List<TCompetitorSeasonStat> competitorSeasonStatList = SbdCompetitorSeasonStatApis.getTCompetitorSeasonStats(tTeam.getId(), 0, 0);
        LOG.info("competitorSeasonStatList: {} ",competitorSeasonStatList);
        if(CollectionUtils.isNotEmpty(competitorSeasonStatList)){
            Collections.sort(competitorSeasonStatList, new Comparator<TCompetitorSeasonStat>() {
                @Override
                public int compare(TCompetitorSeasonStat o1, TCompetitorSeasonStat o2) {
                    return o2.getSeason().compareTo(o1.getSeason())  ;
                }
            });
        }

        //meta
        metaFiller.fillTeamMeta(model, tTeam, competitionSeasonName,locale);

        //球队页基本信息
        model.addAttribute("team",tTeam);
        model.addAttribute("teamSeasons",curTeamSeason);
        model.addAttribute("teamEpisodes",episodes);
        model.addAttribute("teamStats",competitorSeasonStatList);
    }


    /**
     * 篮球通用球队页面填充球队信息
     *
     * @param model
     * @param tTeam
     */
    public void fillModelWithBkTeam(Model model, TTeam tTeam, CallerParam caller,Locale locale) {

        Long cid = tTeam.getCurrentCid();
        Long csid = tTeam.getCurrentCsid();
        Long curCsid = tTeam.getCurrentCsid();

        LOG.info("csid: " + csid);
        TCompetitionSeason competitionSeason = SbdCompetitonSeasonApis.getTCompetitionSeasonById(csid,caller);


        //球队历史赛事统计
        List<TCompetitorSeasonStat> competitorSeasonStatList = SbdCompetitorSeasonStatApis.getTCompetitorSeasonStats(tTeam.getId(), cid, 0);
        List<TCompetitorSeasonStat> historyStatList = Lists.newArrayList();

        //如果有当前赛季的统计，从历史数据中去掉，否则把当前赛季换成上一个赛季
        if(CollectionUtils.isNotEmpty(competitorSeasonStatList)){
            Collections.sort(competitorSeasonStatList, new Comparator<TCompetitorSeasonStat>() {
                @Override
                public int compare(TCompetitorSeasonStat o1, TCompetitorSeasonStat o2) {
                    return o2.getSeason().compareTo(o1.getSeason())  ;
                }
            });
            if(competitorSeasonStatList.get(0).getCsid() == curCsid){
                if(competitorSeasonStatList.size()>1){
                    historyStatList = competitorSeasonStatList.subList(1,competitorSeasonStatList.size());
                }
                else{
                    historyStatList = Lists.newArrayList();
                }
            }
            else{
                //当前赛季id
                LOG.info("cid:{} ,competitionSeason: {}",cid,competitionSeason);
                csid = SbdCompetitonSeasonApis.getTCompetitionSeasonByCidAndSeason(cid,(Integer.parseInt(competitionSeason.getSeason())-1)+"",caller).getId();
                historyStatList = competitorSeasonStatList;
            }
        }


        Map<String,Object> dataToplist = Maps.newHashMap();
        List<TeamPlayerStatsVo> curTeamSeason = Lists.newArrayList();

        //取得当前赛季的赛季阵容
        GetTeamSeasonsParam p = new GetTeamSeasonsParam();
        p.setTid(tTeam.getId());
        p.setCsid(csid);
        List<TTeamSeason> tTeamSeasons = SbdTeamSeasonApis.getTTeamSeasons(p,PageUtils.createPageParam(0,10),caller);
        if(CollectionUtils.isNotEmpty(tTeamSeasons)){
            TTeamSeason teamSeason = tTeamSeasons.get(0);
            if(teamSeason.getCocahId()> 0 ){
                TTeamPlayer coach = new TTeamPlayer();
                coach.setPid(teamSeason.getCocahId());
                coach.setLogo(teamSeason.getCocahLogo());
                coach.setName(teamSeason.getCocahName());
                tTeam.setCocah(coach);
            }
            if(CollectionUtils.isNotEmpty(teamSeason.getPlayers())){
                for(TTeamPlayer teamPlayer:teamSeason.getPlayers()){
                    TeamPlayerStatsVo teamPlayerStatsVo = new TeamPlayerStatsVo();
                    TPlayer player = SbdPlayerApis.getTPlayerById(teamPlayer.getPid(),caller);
                    if(player == null){
                        continue;
                    }
                    teamPlayerStatsVo.setAge(getCurrentAgeByBirthdate(player.getBirthday())+"");
                    teamPlayerStatsVo.setHeight(player.getHeight()+"");
                    teamPlayerStatsVo.setLogo(player.getImageUrl());
                    teamPlayerStatsVo.setName(player.getName());
                    teamPlayerStatsVo.setNumber(new Long(teamPlayer.getNumber()).intValue() );
                    teamPlayerStatsVo.setPid(teamPlayer.getPid());
                    teamPlayerStatsVo.setPosition(teamPlayer.getPositionName());
                    teamPlayerStatsVo.setWeight(player.getWeight()+"");
                    teamPlayerStatsVo.setYear(player.getExperience());
                    List<TCompetitorSeasonStat> competitorSeasonStatLists = SbdCompetitorSeasonStatApis.getTCompetitorSeasonStats(player.getId(), 0, csid);
                    if(CollectionUtils.isNotEmpty(competitorSeasonStatLists)){
                        teamPlayerStatsVo.setStats(competitorSeasonStatLists.get(0).getStats());
                        teamPlayerStatsVo.setAvgStats(competitorSeasonStatLists.get(0).getAvgStats());
                    }
                    curTeamSeason.add(teamPlayerStatsVo);
                }

            }
        }

        //近期赛程
        //TODO GetEpisodesOfSeasonByMetaEntryIdParam尚未暴露到client
        GetEpisodesOfSeasonByMetaEntryIdParam param = new GetEpisodesOfSeasonByMetaEntryIdParam();
        param.setCsid(tTeam.getCurrentCsid());
        param.setLiveShowStatusParam(LiveShowStatusParam.LIVE_NOT_END);
        List<TComboEpisode> tComboEpisodes =  QmtSbcEpisodeApis.getCurrentEpisodesByCompetitorId(tTeam.getId(), param, PageUtils.createPageParam(0, 5), caller);

        //新赛季没有赛程，用已结束的赛程
        if(CollectionUtils.isEmpty(tComboEpisodes)){
            LOG.info("csid: {} ，tid: {}",tTeam.getCurrentCsid(),tTeam.getId());
            param = new GetEpisodesOfSeasonByMetaEntryIdParam();
            param.setCsid(csid);
            param.setLiveShowStatusParam(LiveShowStatusParam.LIVE_END);
            tComboEpisodes =  QmtSbcEpisodeApis.getCurrentEpisodesByCompetitorId(tTeam.getId(), param, PageUtils.createPageParam(0, 5), caller);
        }

        //球队当前赛季的技术统计
        Map<String,String> teamCurrentStats = Maps.newHashMap();
        teamCurrentStats = getCurrentStats(teamCurrentStats,tTeam.getId(),cid,csid,caller);



        //排行榜数据
        TeamTopListVo teamTopListVo = new TeamTopListVo();
        teamTopListVo.setTid(tTeam.getId());
        teamTopListVo.setName(tTeam.getName());
        teamTopListVo.setConference(tTeam.getConference());
        teamTopListVo.setRegion(tTeam.getRegion());

        //获取联盟统计信息
        TTopList regionToplist = null;
        TTopList conferenceToplist = null;
        Long standingType = Constants.NBA_REGION_TOPLIST_MAP.get(tTeam.getConferenceId());
        if (standingType != null) {
            conferenceToplist = SbdTopListApis.getSomeTypeTopListOfSeason(cid, csid, standingType, caller);
            teamTopListVo.setConferenceItem(topListService.getTopListItemOfSomeTeam(conferenceToplist, tTeam.getId()));
        }
        //获取分区统计信息
        standingType = Constants.NBA_REGION_TOPLIST_MAP.get(tTeam.getRegionId());

        if (standingType != null) {
            regionToplist = SbdTopListApis.getSomeTypeTopListOfSeason(cid, csid, standingType, caller);
            teamTopListVo.setRegionItem(topListService.getTopListItemOfSomeTeam(regionToplist, tTeam.getId()));
        }

        //数据王
        Map<Long,String> typeMap = Constants.NBA_TOPLIST_MAP;
        for(Long type:typeMap.keySet()){
            GetSeasonTopListsParam p1 = new GetSeasonTopListsParam();
            p1.setCid(cid);
            p1.setCsid(csid);
            p1.setCompetitorType(CompetitorType.PLAYER);
            p1.setScope(tTeam.getId());
            p1.setScopeType(ScopeType.TEAM);
            p1.setType(type);

            List<TTopList> topLists = SbdTopListApis.getCompetitorTTopLists(p1,PageUtils.createPageParam(0,20),caller);
            if(CollectionUtils.isNotEmpty(topLists)){
                if(CollectionUtils.isNotEmpty(topLists.get(0).getItems())){
                    List<TTopListItem> topListItems = Lists.newArrayList();
                    for(TTopListItem topListItem:topLists.get(0).getItems()){
                        if(topListItem.getShowOrder()<=0){
                            continue;
                        }
                        topListItems.add(topListItem);
                        if(topListItems.size()==3){
                            break;
                        }
                    }
                    dataToplist.put(typeMap.get(type),topListItems);
                }
            }
        }

        //meta
//        metaFiller.fillTeamMeta(model, tTeam, competitionSeason.getName(),locale);

        //球队页基本信息
        model.addAttribute("team",tTeam);
        model.addAttribute("teamSeasons",curTeamSeason);
        model.addAttribute("teamEpisodes",tComboEpisodes);
        model.addAttribute("teamStats",historyStatList);
        model.addAttribute("toplist",teamTopListVo);
        model.addAttribute("dataToplist",dataToplist);
        model.addAttribute("regionToplist",regionToplist);
        model.addAttribute("currentStats",teamCurrentStats);
    }

    /**
     * 球队聚合页
     *
     * @param model
     */
    public void fillModelWithTeamList(Model model,CallerParam caller,Locale locale) {
        List<Map<String,Object>> teamList = Lists.newArrayList();
        Map<Long,String> divisionMap = Constants.NBA_REGION_NAME_MAP;
        Map<Long,String> conferenceMap = Constants.NBA_CONFERENCE_REGION_NAME_MAP;
        Map<Long,Long> regionToplistMap = Constants.NBA_REGION_TOPLIST_MAP;

        for(Long regionId:divisionMap.keySet()){
            Map<String,Object> regionTeamsMap = Maps.newHashMap();
            List<Map<String,String>> regionTeamList = Lists.newArrayList();
            TTopList regionTopList = SbdTopListApis.getLatestSeasonTopList(Constants.NBA_COMPETITION_ID,regionToplistMap.get(regionId),0,caller);
            if(regionTopList!=null && CollectionUtils.isNotEmpty(regionTopList.getItems())){
                LOG.info("regionId:{} ,topListItem: {}",regionId,regionTopList.getItems());
                for(TTopListItem item:regionTopList.getItems()){
                    TTeam tTeam = SbdTeamApis.getTTeamById(item.getCompetitorId(),caller);
                    if(tTeam == null) continue;
                    Map<String,String> team = Maps.newHashMap();
                    team.put("teamName",item.getCompetitorName());
                    team.put("teamLogo",item.getLogoUrl());
                    team.put("tid",item.getCompetitorId()+"");
                    team.put("win",item.getStats().get("wins"));
                    team.put("loss",item.getStats().get("losses"));
                    regionTeamList.add(team);
                }
                Collections.sort(regionTeamList, new Comparator<Map<String,String>>() {
                    @Override
                    public int compare(Map<String,String> o1, Map<String,String> o2) {
                        return o2.get("tid").compareTo(o1.get("tid"))  ;
                    }
                });
                regionTeamsMap.put("region",divisionMap.get(regionId));
                regionTeamsMap.put("conference",conferenceMap.get(regionId));
                regionTeamsMap.put("list",regionTeamList);
                teamList.add(regionTeamsMap);
            }
        }
        model.addAttribute("teamList",teamList);
    }




    public static int getCurrentAgeByBirthdate(String brithday){
        try {
            if(StringUtils.isBlank(brithday)){
                return 0;
            }
            int curYear = Integer.parseInt(LeDateUtils.formatYYYY(new Date()));
            int birYear = Integer.parseInt(brithday.substring(0,4));
            return curYear - birYear;
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * 获取分区排名，包含本球队的排名
     *
     * @param topList
     * @param teamId
     * @return
     */
    public List<TTopListItem> getTopListItem(TTopList topList,long teamId) {
        List<TTopListItem> topListItems = Lists.newArrayList();
        if (topList == null || CollectionUtils.isEmpty(topList.getItems())) {
            return topListItems;
        }
        int order = 1;
        for (TTopListItem item : topList.getItems()) {
            //到当前球队
            if(item.getCompetitorId() == teamId){
                //排名前5
                if(order<=5){
                    topListItems = topList.getItems().subList(0,5);
                }
                //排名5名开外
                else{
                    topListItems = topList.getItems().subList(0,4);
                    topListItems.add(item);
                }
                break;
            }
            order ++;
        }
        return topListItems;
    }

    /**
     * 获取本赛季各项技术统计和排名
     *
     * @param teamCurrentStats
     * @return
     */
    public Map<String,String> getCurrentStats(Map<String,String> teamCurrentStats,Long teamId,long cid,long csid,CallerParam caller) {
        Map<Long,String> topListType = Constants.NBA_TEAM_STATS_MAP;

        for(Long type:topListType.keySet()){
            GetSeasonTopListsParam p = new GetSeasonTopListsParam();
            p.setCid(cid);
            p.setCsid(csid);
            p.setType(type);
            p.setCompetitorType(CompetitorType.TEAM);
            p.setScopeType(ScopeType.CONFERENCE);
            p.setScope(Constants.NBA_CONFERENCE_ID);
            List<TTopList> topLists = SbdTopListApis.getCompetitorTTopLists(p,PageUtils.createPageParam(0,20),caller);

            if(CollectionUtils.isNotEmpty(topLists)){
                List<TTopListItem> topListItems = topLists.get(0).getItems();
                for(TTopListItem topListItem:topListItems){
                    if(topListItem.getCompetitorId() == teamId){
                        teamCurrentStats.put(topListType.get(type),topListItem.getStats().get(topListType.get(type)));
                        teamCurrentStats.put(topListType.get(type)+"_order",topListItem.getShowOrder()+"");
                        break;
                    }
                }
            }
        }
        LOG.info("currentStats: " + teamCurrentStats);
        return teamCurrentStats;
    }

}
