package com.lesports.qmt.web.api.core.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.lesports.api.common.CallerParam;
import com.lesports.qmt.sbc.api.dto.TComboEpisode;
import com.lesports.qmt.sbc.api.service.GetEpisodesOfSeasonByMetaEntryIdParam;
import com.lesports.qmt.sbc.api.service.LiveShowStatusParam;
import com.lesports.qmt.sbc.client.QmtSbcEpisodeApis;
import com.lesports.qmt.sbd.api.common.CompetitorType;
import com.lesports.qmt.sbd.api.common.ScopeType;
import com.lesports.qmt.sbd.api.service.GetTeamSeasonsParam;
import com.lesports.qmt.sbd.client.*;
import com.lesports.qmt.web.api.core.creater.EpisodeVoCreater;
import com.lesports.qmt.web.api.core.creater.TeamVoCreater;
import com.lesports.qmt.web.api.core.service.EpisodeService;
import com.lesports.qmt.web.api.core.service.TeamService;
import com.lesports.qmt.web.api.core.util.Constants;
import com.lesports.qmt.web.api.core.vo.HallEpisodeVo;
import com.lesports.qmt.web.api.core.vo.TeamMixedVo;
import com.lesports.qmt.sbd.api.dto.*;
import com.lesports.qmt.sbd.api.service.GetSeasonTopListsParam;
import com.lesports.qmt.web.api.core.vo.TeamVo;
import com.lesports.utils.PageUtils;
import com.lesports.utils.math.LeNumberUtils;
import org.apache.commons.beanutils.LeBeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by gengchengliang on 2016/8/17.
 */
@Service("teamService")
public class TeamServiceImpl implements TeamService {

    private static final Logger LOG = LoggerFactory.getLogger(TeamServiceImpl.class);

	@Resource
	private EpisodeService episodeService;
    @Resource
    private EpisodeVoCreater episodeVoCreater;

    @Resource
    TeamVoCreater teamVoCreater;

    @Override
    public TeamVo getTeamById(Long id,CallerParam caller) {
        TTeam tTeam = SbdTeamApis.getTTeamById(id, caller);

        return teamVoCreater.createrTeamVo(tTeam,caller);


    }

	@Override
	public TeamMixedVo getTeamMixed(long teamId, CallerParam caller) {
		TeamMixedVo teamMixedVo = new TeamMixedVo();
		TTeam tTeam = SbdTeamApis.getTTeamById(teamId, caller);
		if (tTeam == null) {
			return null;
		}
		teamMixedVo.setTeam(tTeam);
		if (tTeam.getGameFTypeId() == 100015000l) {//足球
			fillFootballMixed(tTeam, teamMixedVo, caller);
		} else if (tTeam.getGameFTypeId() == 100014000l) {//篮球
			fillBasketballMixed(tTeam, teamMixedVo, caller);
		}
		return teamMixedVo;
	}

	/**
	 * 足球信息
	 * @param tTeam
	 * @param teamMixedVo
	 * @param caller
	 */
	private void fillFootballMixed(TTeam tTeam, TeamMixedVo teamMixedVo, CallerParam caller) {
		//当前赛事的赛程
		if (tTeam.getCurrentCid() > 0) {
			fillEpisodes2Mixed(teamMixedVo, tTeam, caller);
		}
		if (tTeam.getCurrentTsid() > 0) {
			fillPlayers2Mixed(teamMixedVo, tTeam, caller);
		}
	}

	private void fillEpisodes2Mixed (TeamMixedVo teamMixedVo, TTeam tTeam, CallerParam caller) {
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
		List<HallEpisodeVo> hallEpisodes = episodeService.createHallEpisodes(episodes, caller);
		if (CollectionUtils.isNotEmpty(hallEpisodes)) {
			teamMixedVo.setEpisodes(hallEpisodes);
		}
	}

	private void fillPlayers2Mixed (TeamMixedVo teamMixedVo, TTeam tTeam, CallerParam caller) {
		Map<Long, List<TTeamPlayer>> playerMap = Maps.newHashMap();
		List<TTeamSeason> tTeamSeasons = SbdTeamSeasonApis.getTTeamSeasonsByIds(Arrays.asList(tTeam.getCurrentTsid()), caller);
		if (CollectionUtils.isNotEmpty(tTeamSeasons)) {
			TTeamSeason tTeamSeason = tTeamSeasons.get(0);
			List<TTeamPlayer> players = tTeamSeason.getPlayers();
			if (CollectionUtils.isNotEmpty(players)) {
				for (TTeamPlayer player : players) {
					if (player.getPositionId() > 0) {
						List<TTeamPlayer> tPlayers = playerMap.get(player.getPositionId());
						if (CollectionUtils.isEmpty(tPlayers)) {
							List<TTeamPlayer> positionPlayers = Lists.newArrayList();
							positionPlayers.add(player);
							playerMap.put(player.getPositionId(), positionPlayers);
						} else {
							tPlayers.add(player);
						}
					}
				}
			}
			if (MapUtils.isNotEmpty(playerMap)) {
				teamMixedVo.setPlayers(playerMap);
			}
		}
	}

	/**
	 * 篮球信息
	 * @param tTeam
	 * @param teamMixedVo
	 * @param caller
	 */
	private void fillBasketballMixed(TTeam tTeam, TeamMixedVo teamMixedVo, CallerParam caller) {
		if (tTeam.getCurrentCid() <= 0) {
			return;
		}
		//获取球队的榜单信息
		GetSeasonTopListsParam p = createGetSeasonTopListsParam(tTeam);
		List<TTopList> seasonTopLists = SbdTopListApis.getSeasonTopLists(p, null, caller);
		if (CollectionUtils.isNotEmpty(seasonTopLists)) {
			TTopList tTopList = seasonTopLists.get(0);
			List<TTopListItem> items = tTopList.getItems();
			if (CollectionUtils.isNotEmpty(items)) {
				for (TTopListItem item : items) {
					if (item.getCompetitorId() == tTeam.getId()) {
                        Map<String,String> stats = item.getStats();
                        if(stats.get("wins")==null && stats.get("winMatch") != null){
                            stats.put("wins",stats.get("winMatch"));
                        }
                        if(stats.get("losses")==null && stats.get("lossMatch") != null){
                            stats.put("losses",stats.get("lossMatch"));
                        }
                        item.setStats(stats);
						teamMixedVo.setTeamToplist(item);
						break;
					}
				}
			}
		}
		//NBA球队分项榜单信息
		if (tTeam.getCurrentCid() == 44001l || tTeam.getCurrentCid() == 39001L) {
			GetSeasonTopListsParam itemizedQ = createItemizedGetSeasonTopListsParam(tTeam);
			List<TTopList> itemizedTopLists = SbdTopListApis.getSeasonTopLists(itemizedQ, null, caller);
			if (CollectionUtils.isNotEmpty(itemizedTopLists)) {
				Iterator<TTopList> topListIterator = itemizedTopLists.iterator();
				while (topListIterator.hasNext()) {
					TTopList tTopList = topListIterator.next();
					List<TTopListItem> items = tTopList.getItems();
					if (CollectionUtils.isNotEmpty(items)) {
						Iterator<TTopListItem> itemIterator = items.iterator();
						while (itemIterator.hasNext()) {
							TTopListItem item = itemIterator.next();
							if (item.getCompetitorId() != tTeam.getId()) {
								itemIterator.remove();
							}
						}
					}
					if (CollectionUtils.isEmpty(items)) {
						topListIterator.remove();
					}
				}
				if (CollectionUtils.isNotEmpty(itemizedTopLists)) {
					teamMixedVo.setItemizedToplist(itemizedTopLists);
				}
			}

            //球队数据排行前三的球员
            Map<Long,String> typeMap = Constants.NBA_TOP_LIST_MAP;
            Map<String,Object> dataTopList = Maps.newHashMap();
            for(Long type:typeMap.keySet()){
                GetSeasonTopListsParam p1 = new GetSeasonTopListsParam();
                p1.setCid(tTeam.getCurrentCid());
                p1.setCsid(tTeam.getCurrentCsid());
                p1.setCompetitorType(CompetitorType.PLAYER);
                p1.setScope(tTeam.getId());
                p1.setScopeType(ScopeType.TEAM);
                p1.setType(type);

                List<TTopList> topLists = SbdTopListApis.getCompetitorTTopLists(p1,PageUtils.createPageParam(0,1),caller);
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
                        dataTopList.put(typeMap.get(type),topListItems);
                    }
                }
            }
            teamMixedVo.setDataToplist(dataTopList);

            //球队技术统计
            List<TCompetitorSeasonStat> competitorSeasonStats = SbdCompetitorSeasonStatApis.getTCompetitorSeasonStats(tTeam.getId(), tTeam.getCurrentCid(), 0);
            if (CollectionUtils.isNotEmpty(competitorSeasonStats)) {
                Collections.sort(competitorSeasonStats, new Comparator<TCompetitorSeasonStat>() {
                    @Override
                    public int compare(TCompetitorSeasonStat o1, TCompetitorSeasonStat o2) {
                        return o2.getSeason().compareTo(o1.getSeason())  ;
                    }
                });
                List<Map<String,String>> historyStats = Lists.newArrayList();
                for (TCompetitorSeasonStat tCompetitorSeasonStat : competitorSeasonStats) {
                    LOG.info("cssId: {}, csid1:{} ,csid2:{} ",tCompetitorSeasonStat.getId(),tCompetitorSeasonStat.getCsid(),tTeam.getCurrentCsid());
                    if(tCompetitorSeasonStat.getCsid() != tTeam.getCurrentCsid()){
                        Map<String,String> avgStats = tCompetitorSeasonStat.getAvgStats();
                        avgStats.put("season",tCompetitorSeasonStat.getSeason());
                        avgStats.put("csid",tCompetitorSeasonStat.getCsid()+"");
                        historyStats.add(avgStats);
                    }
                }
                teamMixedVo.setHistoryStats(historyStats);
            }


            //下场要打的比赛
            GetEpisodesOfSeasonByMetaEntryIdParam p1 = new GetEpisodesOfSeasonByMetaEntryIdParam();
//            p1.setCid(tTeam.getCurrentCid());
            p1.setCsid(tTeam.getCurrentCsid());
            p1.setLiveShowStatusParam(LiveShowStatusParam.LIVE_NOT_START);
            List<TComboEpisode> tComboEpisodes =  QmtSbcEpisodeApis.getCurrentEpisodesByCompetitorId(tTeam.getId(), p1, PageUtils.createPageParam(0,1), caller);

            if(CollectionUtils.isNotEmpty(tComboEpisodes)){
                TComboEpisode episode = tComboEpisodes.get(0);
                TeamMixedVo.NextMatch nextMatch = teamMixedVo.new NextMatch();
                nextMatch.setId(episode.getMid());
                nextMatch.setStartTime(episode.getStartTime());

                List<TCompetitor> competitors = episode.getCompetitors();
                if(CollectionUtils.isNotEmpty(competitors)){
                    for(TCompetitor competitor:competitors){
                        if(competitor.getId() != tTeam.getId()){
                            nextMatch.setOpponent(competitor.getName());
                            nextMatch.setOpponentLogo(competitor.getLogoUrl());
                            nextMatch.setOpponentId(competitor.getId());
                            break;
                        }
                    }
                }

                HallEpisodeVo hallEpisodeVo = episodeVoCreater.createHallEpisodeVo4App(episode, caller);
                teamMixedVo.setNextMatch(hallEpisodeVo);
            }
		}

		//球队球员阵容
		GetTeamSeasonsParam playerQ = createGetTeamSeasonsParam(tTeam);
		List<TTeamSeason> tTeamSeasons = SbdTeamSeasonApis.getTTeamSeasons(playerQ, null, caller);
		if (CollectionUtils.isEmpty(tTeamSeasons)) {
			return;
		}

		TTeamSeason tTeamSeason = tTeamSeasons.get(0);
		List<TTeamPlayer> players = tTeamSeason.getPlayers();
		if (CollectionUtils.isEmpty(players)) {
			return;
		}

		List<Long> pids = Lists.newArrayList();
		for (TTeamPlayer player : players) {
			pids.add(player.getPid());
		}
		List<TPlayer> tPlayers = SbdPlayerApis.getTPlayersByIds(pids, caller);
		if (CollectionUtils.isEmpty(tPlayers)) {
			return;
		}
		//获取球员的赛季统计
		List<TCompetitorSeasonStat> tCompetitorSeasonStats = SbdCompetitorSeasonStatApis.getTCompetitorSeasonStats(pids, tTeam.getCurrentCid(), tTeam.getCurrentCsid());
		//teamSeason
		TTeamSeason teamSeason = SbdTeamSeasonApis.getTTeamSeason(tTeam.getId(), tTeam.getCurrentCsid(), caller);
		List<TeamMixedVo.BasketballPlayer> bPlayers = Lists.newArrayList();
		for (TPlayer tPlayer : tPlayers) {
			TeamMixedVo.BasketballPlayer basketballPlayer = teamMixedVo.new BasketballPlayer();
			LeBeanUtils.copyNotEmptyPropertiesQuietly(basketballPlayer, tPlayer);
            if(tTeam.getCurrentCid() == 39001L){
                basketballPlayer.setExperience(null);
            }
			if (CollectionUtils.isNotEmpty(tCompetitorSeasonStats)) {
				for (TCompetitorSeasonStat tCompetitorSeasonStat : tCompetitorSeasonStats) {
					if (tPlayer.getId() == tCompetitorSeasonStat.getCompetitorId()) {
						basketballPlayer.setStat(tCompetitorSeasonStat);
					}
				}
			}
			if (teamSeason != null && CollectionUtils.isNotEmpty(teamSeason.getPlayers())) {
				for (TTeamPlayer player : players) {
					if (player.getPid() == tPlayer.getId()) {
						basketballPlayer.setNumber(LeNumberUtils.toInt(player.getNumber()));
						break;
					}
				}
			}
            bPlayers.add(basketballPlayer);
		}

        if(tTeam.getCocah() == null && teamSeason.getCocahId()>0 ){
            TTeamPlayer coach = new TTeamPlayer();
            coach.setName(teamSeason.getCocahName());
            coach.setNumber(0);
            coach.setPid(teamSeason.getCocahId());
            tTeam.setCocah(coach);
            teamMixedVo.setTeam(tTeam);
        }


		teamMixedVo.setBasketballPlayers(bPlayers);
	}

	private GetTeamSeasonsParam createGetTeamSeasonsParam(TTeam tTeam) {
		GetTeamSeasonsParam p = new GetTeamSeasonsParam();
		p.setCsid(tTeam.getCurrentCsid());
		p.setTid(tTeam.getId());
		return p;
	}

	private GetSeasonTopListsParam createItemizedGetSeasonTopListsParam(TTeam tTeam) {
		GetSeasonTopListsParam p = new GetSeasonTopListsParam();
		p.setCid(tTeam.getCurrentCid());
		p.setCsid(tTeam.getCurrentCsid());
		p.setCompetitorType(CompetitorType.TEAM);
		p.setScopeType(ScopeType.CONFERENCE);
		return p;
	}

	private GetSeasonTopListsParam createGetSeasonTopListsParam (TTeam tTeam) {
		GetSeasonTopListsParam p = new GetSeasonTopListsParam();
		p.setCid(tTeam.getCurrentCid());
		p.setCsid(tTeam.getCurrentCsid());
		if (p.getCid() == 44001l) {
			if (tTeam.getConferenceId() == 100131000l) {//西部联盟type
				p.setType(100204000l);
			} else if (tTeam.getConferenceId() == 100130000l) {//东部联盟type
				p.setType(100205000l);
			}
		}
		return p;
	}


    @Override
    public List<TPlayer> getPlayersByTidAndCid(Long tid,Long cid, CallerParam callerParam){
        List<TPlayer> players = Lists.newArrayList();

        TCompetitionSeason tCompetitionSeason = SbdCompetitonSeasonApis.getLatestTCompetitionSeasonsByCid(cid,callerParam);
        if(tCompetitionSeason == null){
            return players;
        }

        TTeamSeason tTeamSeason = SbdTeamSeasonApis.getTTeamSeason(tid,tCompetitionSeason.getId(),callerParam);
        if(tTeamSeason == null || CollectionUtils.isEmpty(tTeamSeason.getPlayers())){
            return  players;
        }

        List<Long> ids = Lists.newArrayList();
        for(TTeamPlayer tTeamPlayer:tTeamSeason.getPlayers()){
            ids.add(tTeamPlayer.getPid());
        }

        players = SbdPlayerApis.getTPlayersByIds(ids,callerParam);
        return players;
    }
}
