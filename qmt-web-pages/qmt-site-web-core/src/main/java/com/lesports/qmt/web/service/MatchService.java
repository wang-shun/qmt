package com.lesports.qmt.web.service;

import client.SopsApis;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.lesports.LeConstants;
import com.lesports.api.common.CallerParam;
import com.lesports.qmt.config.api.dto.ActivityResourceType;
import com.lesports.qmt.sbc.api.common.VideoContentType;
import com.lesports.qmt.sbc.api.dto.*;
import com.lesports.qmt.sbc.api.service.GetEpisodesNearbySomeEpisodeParam;
import com.lesports.qmt.sbc.api.service.GetRelatedVideosParam;
import com.lesports.qmt.sbc.client.QmtSbcApis;
import com.lesports.qmt.sbc.client.QmtSbcEpisodeApis;
import com.lesports.qmt.sbd.api.dto.*;
import com.lesports.qmt.sbd.client.SbdCompetitionApis;
import com.lesports.qmt.sbd.client.SbdMatchReviewApis;
import com.lesports.qmt.sbd.client.SbdPlayerApis;
import com.lesports.qmt.tlive.api.common.TextLiveType;
import com.lesports.qmt.tlive.api.dto.TTextLive;
import com.lesports.qmt.tlive.client.TextLiveApis;
import com.lesports.qmt.web.helper.MetaFiller;
import com.lesports.qmt.web.helper.ShareHelper;
import com.lesports.qmt.web.model.BestPlayerStatsVo;
import com.lesports.qmt.web.model.Share;
import com.lesports.qmt.web.model.VideoWebView;
import com.lesports.qmt.web.utils.HotRankApis;
import com.lesports.qmt.web.utils.KVMemcachedUtils;
import com.lesports.qmt.web.utils.ShowNameUtils;
import com.lesports.qmt.web.utils.XinYingOpUtils;
import com.lesports.sms.api.common.ConfigType;
import com.lesports.sms.api.vo.TConfig;
import com.lesports.utils.*;
import com.lesports.utils.math.LeNumberUtils;
import com.lesports.utils.pojo.Post;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by gengchengliang on 2015/7/14.
 */
@Service("matchService")
public class MatchService {

    private final static Logger LOG = LoggerFactory.getLogger(MatchService.class);

    private static final int RELATED_OTHERS_MATCHES_COUNT = 10;

	private static final Utf8KeyCreater<Long> TAG_NAME_CREATE = new Utf8KeyCreater<>("V2_EPISODE_POST_");

	private static final Integer EPISODE_POST_EXPIRE_TIME = 60 * 10;

    //跳高类，特殊处理，男子，女子跳高，撑杆跳
    private static final String ATHLETICS_HIGHJUMP_TYPE = LeProperties.getString("athletics.highjump.type","114894000,115004000,114791000,114784000");

    //投掷类，跳远类特殊处理
    private static final String ATHLETICS_LONGJUMP_TYPE = LeProperties.getString("athletics.longjump.type","114782000,115003000,114781000,114790000,114783000,114893000,114958000,114959000,114789000,114891000,114892000,115002000");

    @Resource
    private TopListService topListService;

    @Resource
    private VideoService videoService;

    @Resource
    private ShareHelper shareHelper;

    @Resource
    private MetaFiller metaFiller;


    /**
     * 填充model用于通用的比赛信息页
     */
    private void fillModel4CommonMatch(Model model, TDetailMatch match, TComboEpisode episode, CallerParam caller, Locale locale) {
        //填充赛事信息
        TCompetition competition = SbdCompetitionApis.getTCompetitionById(match.getCid(), caller);
        if (StringUtils.isNotEmpty(episode.getLogo())) {
            competition.setLogoUrl(episode.getLogo());
        }
        model.addAttribute("cname", competition);
        //填充节目信息
        model.addAttribute("episode", episode);
        //状态
        model.addAttribute("status", episode.getStatus());
        //填充阵容
        model.addAttribute("squads", getSquads(match, episode, caller));
        //填充比赛数据
        model.addAttribute("stats", match.getCompetitorStats());
        //填充榜单信息
        model.addAttribute("toplist", topListService.getSeasonTopLists(match.getCid(), match.getCsid(), caller));
        //填充比赛信息
        model.addAttribute("match", match);
        //填充相关的其他比赛
        List<Long> otherEids = QmtSbcEpisodeApis.getEpisodeIdsRelatedWithSomeEpisode(episode.getId(),
                RELATED_OTHERS_MATCHES_COUNT, caller);
        model.addAttribute("otherMatches", QmtSbcEpisodeApis.getTComboEpisodesByIds(otherEids, caller));
        //上一场,下一场
        GetEpisodesNearbySomeEpisodeParam p = new GetEpisodesNearbySomeEpisodeParam();
        p.setEpisodeId(episode.getId());
        Map<String, TComboEpisode> nearbyEpisodes = QmtSbcEpisodeApis.getTComboEpisodesNearbySomeEpisode(p, caller);
        TComboEpisode prevEpisode = nearbyEpisodes.get("prev");
        model.addAttribute("prev", prevEpisode != null && prevEpisode.getMid() != 0 ? prevEpisode.getMid() : 0);
        TComboEpisode nextEpisode = nearbyEpisodes.get("next");
        model.addAttribute("next", nextEpisode != null && nextEpisode.getMid() != 0 ? nextEpisode.getMid() : 0);
        //只有大陆pc才会有帖子信息
		if (caller.getCallerId() == LeConstants.LESPORTS_PC_CALLER_CODE) {
			//帖子详情
			fillPosts(model, episode, caller.getCallerId());
		}
		//填充meta信息
		metaFiller.fillMatchMeta(model, competition, episode, locale);
        //填充定位标签
        model.addAttribute("tags", episode.getTags());
        //填充评论id
        model.addAttribute("commentId", episode.getCommentId());
        //新英播放url
        model.addAttribute("xinyingPlay", XinYingOpUtils.buildXinyingPlayUrl(episode, caller));
		//新英赛程id
        model.addAttribute("ssportsMid", episode.getXinyingMatchId());
        //showName:比赛页赛事名称
        model.addAttribute("showName", ShowNameUtils.getShowName(episode, caller));
        //图文直播数据
        //todo 暂时不放出图文直播
//        fillModel5TextLive(model, episode, caller.getCallerId());
        //聊天室id
        model.addAttribute("chatRoomId", episode.getChatRoomId());
        //填充分享信息
        Share share = shareHelper.getShare3Episode4Match(episode, caller, Share.ShareType.MATCH, locale);
        if (share != null) {
            model.addAttribute("share", share);
        }
        //填充相关视频
        List<VideoWebView> relateVideos = videoService.getVideosRelated4Match(episode, match, caller, locale);
        model.addAttribute("relatedVideos", relateVideos);
        //如果是欧洲杯的话需要把碎片地址给出
        if (match.getCid() == 100493001l) {
            fillModel4Frag(episode, model, caller);
        }

		if (LeNumberUtils.toInt(match.getCid()) == 44001) {
			CallerParam callerParam = CallerUtils.getCallerByLocale(locale);
			if (callerParam.getCallerId() == 3001l) {
				//球队排名统计
				model.addAttribute("teamRank", topListService.getTeamTopListVos(match, caller));
				//球员数据
				model.addAttribute("playerData", getPlayerStatsOfMatch(match, episode));
				//球员最佳数据
				model.addAttribute("personalData", getBestPlayerStatsOfMatch(match, caller));
			}
		}
	}


    private void fillModel4Frag(TComboEpisode episode, Model model, CallerParam caller) {
        TConfig tConfig = SopsApis.getTConfigByTypeAndVersion(ConfigType.FRAG_MAPPING, "1", caller);
        if (tConfig != null) {
            Map<String, String> data = tConfig.getData();
            String frag = data.get(episode.getId() + "");
            model.addAttribute("frag", frag);
        }
    }

    /**
     * 如果运营添加了单场节目的帖子信息,则需要填充至模板中
     *
     * @param model
     * @param episode
     */
    private void fillPosts(Model model, TComboEpisode episode, long callerId) {
		List<Post.TopicThread> posts = Lists.newArrayList();
		String postKey = TAG_NAME_CREATE.textKey(episode.getId());
		List<Post.TopicThread> postsInCache = getPostsInCache(postKey);
		if (CollectionUtils.isNotEmpty(postsInCache)) {
			posts = postsInCache;
		} else {
			List<TSimpleActivity> appActivities = episode.getAppActivities();
			if (CollectionUtils.isNotEmpty(appActivities)) {
				for (TSimpleActivity appActivitie : appActivities) {
					if (appActivitie.getResourceType() == ActivityResourceType.POST) {
						Post.TopicThread topicThread = PostApis.postInfo(appActivitie.getResourceId());
						if (topicThread != null) {
							posts.add(topicThread);
						}
					}
				}
				if (CollectionUtils.isNotEmpty(posts)) {
					savePostsInCache(posts, postKey);
				}
			}
		}
		model.addAttribute("posts", posts);
    }

	private static List<Post.TopicThread> getPostsInCache(String postKey){
		return (List<Post.TopicThread>) KVMemcachedUtils.get(postKey);
	}

	private static boolean savePostsInCache(List<Post.TopicThread> posts, String postKey){
		return KVMemcachedUtils.set(postKey, posts, EPISODE_POST_EXPIRE_TIME);
	}

    /**
     * 填充非比赛节目的页面信息
     *
     * @param model
     * @param episode
     */
    public void fillModel4NonMatch(Model model, TComboEpisode episode, String liveId, CallerParam caller, Locale locale) {
        List<TLiveStream> streams = episode.getStreams();
        if (CollectionUtils.isNotEmpty(streams)) {
            //无直播版权直播id为-1
            if (StringUtils.isBlank(liveId) || streams.get(0).getLiveId().equals("-1")) {
                liveId = streams.get(0).getLiveId();
            }
        }
        //图文直播
        fillModel5TextLive(model, episode, caller.getCallerId());
        //添加直播id
        model.addAttribute("vid", liveId);
        //节目
        model.addAttribute("episode", episode);
        //状态
        model.addAttribute("status", episode.getStatus());
        //节目名称
        model.addAttribute("cname", episode.getName());
        //热播榜
        model.addAttribute("hotRand", HotRankApis.getHotRankData());
        //定位标签
        model.addAttribute("tags", episode.getTags());
        //添加直播页mate信息
        metaFiller.fillLiveMeta(model, episode, caller, locale);
        //评论id
        model.addAttribute("commentId", episode.getCommentId());
        //专辑id
        model.addAttribute("aid", episode.getAid());
        //showName:比赛页赛事名称
        model.addAttribute("showName", ShowNameUtils.getShowName(episode, caller));
        //获取相关视频
        List<VideoWebView> relateVideos = videoService.getVideosRelated4NonMatch(episode, caller, locale);
        //相关视频
        model.addAttribute("relatedVideos", relateVideos);
        //分享信息
        Share share = shareHelper.getShare3Episode4Album(episode, caller, Share.ShareType.ALBUM, locale);
        model.addAttribute("share", share);
        //是否有直播
        model.addAttribute("hasLive", hasLive(episode));
    }


//    public void fillModel4OlympicMatchOfMsite(Model model, TDetailMatch match, TComboEpisode episode, CallerParam caller, Locale locale) {
//        //填充赛事信息
//        TDictEntry dictEntry = QmtConfigApis.getTDictEntryById(episode.getDisciplineId(), caller);
//        TCompetition competition = SbdCompetitionApis.getTCompetitionById(match.getCid(), caller);
//        TMatchStats tMatchStats = SbdMatchApis.getTMatchStatsById(match.getId(), caller);
//
//        model.addAttribute("vs", match.isVs());//是否对阵
//        model.addAttribute("logo", dictEntry == null ? "" : dictEntry.getImageUrl() + "/11.png");
//
//        //填充节目信息
//        model.addAttribute("episode", episode);
//        //状态
//        model.addAttribute("status", episode.getStatus().getValue());
//
//        //填充定位标签
//        model.addAttribute("tags", episode.getTags());
//
//        model.addAttribute("week", getWeekOfDate(LeDateUtils.parseYYYYMMDDHHMMSS(episode.getStartTime())));
//
//        //填充比赛信息
//        model.addAttribute("match", match);
//
//        TRecordSet recordSet =  SbdsApis.getTRecordSetByGameSTypeAndCsid(episode.getGameSType(),match.getCsid(),caller);
//        List<TRecordData> recordDatas = Lists.newArrayList();
//        if(null != recordSet){
//            recordDatas = recordSet.getRecords();
//        }
//        model.addAttribute("records", recordDatas);
//
//        TOlympicLiveConfigSet olympicLiveConfigSet = BoleApis.getOlympicsConfigSet(episode.getGameSType());
//        List<OlympicConfig> olympicLineupConfigs = Lists.newArrayList();
//        List<OlympicConfig> olympicStatsConfigs = Lists.newArrayList();
//        Map<Long, Map<Long, Map<String, String>>> teamMapStats = Maps.newLinkedHashMap();
//        List<OlympicCompetitorStatsVo> teamStats = Lists.newArrayList();
//        List<TSquad> squads = Lists.newArrayList();
//        if(CollectionUtils.isNotEmpty(match.getSquads())){
//            squads = match.getSquads();
//        }
//        List<TCompetitor> competitors = Lists.newArrayList();
//        if(CollectionUtils.isNotEmpty(match.getCompetitors())){
//            competitors = match.getCompetitors();
//        }
//        List<TCompetitorStat> competitorStats = Lists.newArrayList();
//        if(null != tMatchStats) {
//            competitorStats = tMatchStats.getCompetitorStats();
//        }
//        if (match.isVs()) {
//            olympicLineupConfigs.add(new OlympicConfig("PN", "号码"));
//            olympicLineupConfigs.add(new OlympicConfig("NAME", "姓名"));
//            olympicLineupConfigs.add(new OlympicConfig("HEIGHT", "身高"));
//            olympicLineupConfigs.add(new OlympicConfig("WEIGHT", "体重"));
//            if(null != olympicLiveConfigSet) {
//                if(CollectionUtils.isNotEmpty(olympicLiveConfigSet.getPlayerExtendConfig())) {
//                    for (TOlympicConfig olympicConfig : olympicLiveConfigSet.getPlayerExtendConfig()) {
//                        olympicLineupConfigs.add(new OlympicConfig(getCodeByConfigPath(olympicConfig.getPropertyName(), olympicConfig.getElementPath()), olympicConfig.getAnnotation()));
//                    }
//                }
//                if(CollectionUtils.isNotEmpty(olympicLiveConfigSet.getPlayerStatsConfig())) {
//                    for (TOlympicConfig olympicConfig : olympicLiveConfigSet.getPlayerStatsConfig()) {
//                        olympicStatsConfigs.add(new OlympicConfig(getCodeByConfigPath(olympicConfig.getPropertyName(), olympicConfig.getElementPath()), olympicConfig.getAnnotation()));
//                    }
//
//                }
//            }
//            model.addAttribute("olympicLineupConfigs", olympicLineupConfigs);
//            model.addAttribute("olympicStatsConfigs", olympicStatsConfigs);
//            //对阵类型
//            for (TSquad squad : squads) {
//                Map<Long, Map<String, String>> playerMapStats = Maps.newLinkedHashMap();
//                List<TSimplePlayer> simplePlayers = squad.getPlayers();
//                if(CollectionUtils.isNotEmpty(simplePlayers)) {
//                    for (TSimplePlayer simplePlayer : simplePlayers) {
//                        playerMapStats.put(simplePlayer.getId(), simplePlayer.getExtendInfos());
//                    }
//                }
//                teamMapStats.put(squad.getTid(), playerMapStats);
//            }
//            if (CollectionUtils.isNotEmpty(competitorStats)) {
//                for (TCompetitorStat tCompetitorStat : competitorStats) {
//                    Map<Long, Map<String, String>> playerStats = teamMapStats.get(tCompetitorStat.getId());
//                    List<TSimplePlayer> tCompetitorStatPlayerStats = tCompetitorStat.getPlayerStats();
//                    if(CollectionUtils.isNotEmpty(tCompetitorStatPlayerStats)) {
//                        for (TSimplePlayer simplePlayer : tCompetitorStatPlayerStats) {
//                            Map<String, String> playerStat = playerStats.get(simplePlayer.getId());
//                            if (null == playerStat) {
//                                playerStat = Maps.newHashMap();
//                            }
//                            if (null != simplePlayer.getStats()) {
//                                playerStat.putAll(simplePlayer.getStats());
//                                playerStats.put(simplePlayer.getId(), playerStat);
//                            }
//                        }
//                    }
//                    teamMapStats.put(tCompetitorStat.getTid(), playerStats);
//                }
//            }
//            if(null != teamMapStats) {
//                for (Long tid : teamMapStats.keySet()){
//                    OlympicCompetitorStatsVo olympicCompetitorStatsVo = new OlympicCompetitorStatsVo();
//                    olympicCompetitorStatsVo.setTid(tid);
//                    Map<Long, Map<String, String>> maps = teamMapStats.get(tid);
//                    List<OlympicCompetitorStatsVo.OlympicPlayerStats> olympicPlayerStatsList = Lists.newArrayList();
//                    for(Long pid : maps.keySet()) {
//                        OlympicCompetitorStatsVo.OlympicPlayerStats olympicPlayerStats = new OlympicCompetitorStatsVo.OlympicPlayerStats();
//                        olympicPlayerStats.setPid(pid);
//                        olympicPlayerStats.setStats(maps.get(pid));
//                        olympicPlayerStatsList.add(olympicPlayerStats);
//                    }
//                    olympicCompetitorStatsVo.setPlayerStats(olympicPlayerStatsList);
//                    teamStats.add(olympicCompetitorStatsVo);
//                }
//            }
////            model.addAttribute("teamMapStats", teamMapStats);
//            model.addAttribute("teamStats", teamStats);
//        } else {
//            Map<Long, Map<String, String>> playerMapStats = Maps.newLinkedHashMap();
//            if (CollectionUtils.isNotEmpty(competitors)) {
//                olympicLineupConfigs.add(new OlympicConfig("ORDER", "排名"));
//                if (competitors.get(0).getCompetitorType() == CompetitorType.TEAM) {
//                    olympicLineupConfigs.add(new OlympicConfig("NATIONALITY", "国家"));
//                    if(null != olympicLiveConfigSet && CollectionUtils.isNotEmpty(olympicLiveConfigSet.getTeamExtendConfig())) {
//                        for (TOlympicConfig olympicConfig : olympicLiveConfigSet.getTeamExtendConfig()) {
//                            olympicLineupConfigs.add(new OlympicConfig(getCodeByConfigPath(olympicConfig.getPropertyName(), olympicConfig.getElementPath()), olympicConfig.getAnnotation()));
//                        }
//                    }
//                    model.addAttribute("olympicLineupConfigs", olympicLineupConfigs);
//                    olympicStatsConfigs.add(new OlympicConfig("NATIONALITY", "国家"));
//                    olympicStatsConfigs.add(new OlympicConfig("FR", "成绩"));
//                    if(null != olympicLiveConfigSet && CollectionUtils.isNotEmpty(olympicLiveConfigSet.getResultConfig())) {
//                        for (TOlympicConfig olympicConfig : olympicLiveConfigSet.getResultConfig()) {
//                            olympicStatsConfigs.add(new OlympicConfig(getCodeByConfigPath(olympicConfig.getPropertyName(), olympicConfig.getElementPath()), olympicConfig.getAnnotation()));
//                        }
//                    }
//                    if(null != olympicLiveConfigSet && CollectionUtils.isNotEmpty(olympicLiveConfigSet.getCompetitorStatsConfig())) {
//                        for (TOlympicConfig olympicConfig : olympicLiveConfigSet.getCompetitorStatsConfig()) {
//                            olympicStatsConfigs.add(new OlympicConfig(getCodeByConfigPath(olympicConfig.getPropertyName(), olympicConfig.getElementPath()), olympicConfig.getAnnotation()));
//                        }
//                    }
//                    model.addAttribute("olympicStatsConfigs", olympicStatsConfigs);
//                } else {
//                    olympicLineupConfigs.add(new OlympicConfig("PN", "号码"));
//                    olympicLineupConfigs.add(new OlympicConfig("NATIONALITY", "国家"));
//                    olympicLineupConfigs.add(new OlympicConfig("NAME", "姓名"));
//                    olympicLineupConfigs.add(new OlympicConfig("HEIGHT", "身高"));
//                    olympicLineupConfigs.add(new OlympicConfig("WEIGHT", "体重"));
//                    if(null != olympicLiveConfigSet && CollectionUtils.isNotEmpty(olympicLiveConfigSet.getPlayerExtendConfig())) {
//                        for (TOlympicConfig olympicConfig : olympicLiveConfigSet.getPlayerExtendConfig()) {
//                            olympicLineupConfigs.add(new OlympicConfig(getCodeByConfigPath(olympicConfig.getPropertyName(), olympicConfig.getElementPath()), olympicConfig.getAnnotation()));
//                        }
//                    }
//                    model.addAttribute("olympicLineupConfigs", olympicLineupConfigs);
//                    olympicStatsConfigs.add(new OlympicConfig("NATIONALITY", "国家"));
//                    olympicStatsConfigs.add(new OlympicConfig("NAME", "姓名"));
//                    olympicStatsConfigs.add(new OlympicConfig("FR", "成绩"));
//                    if(null != olympicLiveConfigSet && CollectionUtils.isNotEmpty(olympicLiveConfigSet.getResultConfig())) {
//                        for (TOlympicConfig olympicConfig : olympicLiveConfigSet.getResultConfig()) {
//                            olympicStatsConfigs.add(new OlympicConfig(getCodeByConfigPath(olympicConfig.getPropertyName(), olympicConfig.getElementPath()), olympicConfig.getAnnotation()));
//                        }
//                    }
//                    if(null != olympicLiveConfigSet && CollectionUtils.isNotEmpty(olympicLiveConfigSet.getPlayerStatsConfig())) {
//                        for (TOlympicConfig olympicConfig : olympicLiveConfigSet.getPlayerStatsConfig()) {
//                            olympicLineupConfigs.add(new OlympicConfig(getCodeByConfigPath(olympicConfig.getPropertyName(), olympicConfig.getElementPath()), olympicConfig.getAnnotation()));
//                        }
//                    }
//                    model.addAttribute("olympicStatsConfigs", olympicStatsConfigs);
//                }
//                for (TCompetitor competitor : competitors) {
//                    if (competitor.getCompetitorType() == CompetitorType.TEAM) {
//                        Map<String,String> competitorEx = competitor.getExtendInfos();
//                        if(null == competitorEx) {
//                            competitorEx = Maps.newHashMap();
//                        }
//                        competitorEx.put("ORDER", competitor.getOrder()+"");
//                        competitorEx.put("FR", competitor.getFinalResult());
//                        playerMapStats.put(competitor.getId(), competitorEx);
//                    } else {
//                        for (TSquad squad : squads) {
//                            List<TSimplePlayer> simplePlayers = squad.getPlayers();
//                            if(CollectionUtils.isNotEmpty(simplePlayers)) {
//                                TSimplePlayer simplePlayer = simplePlayers.get(0);
//                                if(competitor.getId() == simplePlayer.getId()) {
//                                    Map<String, String> extendInfo = simplePlayer.getExtendInfos();
//                                    if(null == extendInfo) {
//                                        extendInfo = Maps.newHashMap();
//                                    }
//                                    playerMapStats.put(simplePlayer.getId(), extendInfo);
//                                    extendInfo.put("ORDER", competitor.getOrder()+"");
//                                    extendInfo.put("FR", competitor.getFinalResult());
//                                    if(null != simplePlayer.getResultExtendInfos()) {
//                                        extendInfo.putAll(simplePlayer.getResultExtendInfos());
//                                    }
//                                    playerMapStats.put(simplePlayer.getId(), extendInfo);
//                                    break;
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//            if (CollectionUtils.isNotEmpty(competitorStats)) {
//                for (TCompetitorStat tCompetitorStat : competitorStats) {
//                    Map<String, String> playerStat = playerMapStats.get(tCompetitorStat.getId());
//                    if(null == playerStat) {
//                        playerStat = Maps.newHashMap();
//                    }
//                    if (null != tCompetitorStat.getStats()) {
//                        playerStat.putAll(tCompetitorStat.getStats());
//                        playerMapStats.put(tCompetitorStat.getId(), playerStat);
//                    }
//                }
//            }
//            model.addAttribute("playerStats", playerMapStats.entrySet());
//
//        }
//
//
//    }


//    /**
//     * 填充model用于奥运参赛人员列表页面
//     *
//     * @param model
//     * @param match
//     * @param episode
//     */
//    public void fillModel4OlympicMatch(Model model, TDetailMatch match, TComboEpisode episode, CallerParam caller, Locale locale, boolean isLineUp) {
//        //填充赛事信息
//        TDictEntry dictEntry = QmtConfigApis.getTDictEntryById(episode.getDisciplineId(), caller);
//        TCompetition competition = SbdCompetitionApis.getTCompetitionById(match.getCid(), caller);
//
//        TMatchStats tMatchStats = SbdMatchApis.getTMatchStatsById(match.getId(), caller);
//        int vs = 0;
//        if(match.isVs()){
//            vs = 0;
//        }else{
//            vs = 1;
//            if(ATHLETICS_HIGHJUMP_TYPE.contains(episode.getGameSType()+"")){
//                vs = 2;
//            }else if(ATHLETICS_LONGJUMP_TYPE.contains(episode.getGameSType()+"")){
//                vs = 3;
//            }
//        }
//        model.addAttribute("vs",vs);//是否对阵
//        model.addAttribute("logo", dictEntry == null ? "" : dictEntry.getImageUrl() + "/11.png");
//
//        //填充节目信息
//        model.addAttribute("episode", episode);
//        //状态
//        model.addAttribute("status", episode.getStatus().getValue());
//        //填充比赛信息
//        model.addAttribute("match", match);
////        填充定位标签
//        model.addAttribute("tags", episode.getTags());
//
//        model.addAttribute("week", getWeekOfDate(LeDateUtils.parseYYYYMMDDHHMMSS(episode.getStartTime())));
//
//        List<String> standards = Lists.newArrayList();
//        if(2 == vs) {
//            Map<String,String> matchExtendInfo = match.getExtendInfos();
//            if(null != matchExtendInfo) {
//                standards = LeStringUtils.commaString2StringList(matchExtendInfo.get("INTERMEDIATE").replaceAll(" ","").replace("[","").replace("]",""));
//                model.addAttribute("standards", standards);//跳高或撑杆跳起跳标准
//            }
//        }
//
//        TRecordSet recordSet =  SbdsApis.getTRecordSetByGameSTypeAndCsid(episode.getGameSType(),match.getCsid(),caller);
//        List<TRecordData> recordDatas = Lists.newArrayList();
//        if(null != recordSet){
//            recordDatas = recordSet.getRecords();
//        }
//        model.addAttribute("records", recordDatas);
//
//        TOlympicLiveConfigSet olympicLiveConfigSet = BoleApis.getOlympicsConfigSet(episode.getGameSType());
////        Map<String, String> matchStatsConfig = Maps.newLinkedHashMap();
//        List<OlympicConfig> olympicConfigs = Lists.newArrayList();
//        Map<Long, Map<Long, Map<String, String>>> teamMapStats = Maps.newLinkedHashMap();
//        List<OlympicCompetitorStatsVo> teamStats = Lists.newArrayList();
//        List<TSquad> squads = Lists.newArrayList();
//        if(CollectionUtils.isNotEmpty(match.getSquads())){
//            squads = match.getSquads();
//        }
//        List<TCompetitor> competitors = Lists.newArrayList();
//        if(CollectionUtils.isNotEmpty(match.getCompetitors())){
//            competitors = match.getCompetitors();
//        }
//        List<TCompetitorStat> competitorStats =Lists.newArrayList();
//        if(null != tMatchStats && CollectionUtils.isNotEmpty(tMatchStats.getCompetitorStats())){
//            competitorStats =tMatchStats.getCompetitorStats();
//        }
//        int maxSize = 0;
//        if (0 == vs) {
//            olympicConfigs.add(new OlympicConfig("PN", "号码"));
//            olympicConfigs.add(new OlympicConfig("NAME", "姓名"));
//            olympicConfigs.add(new OlympicConfig("HEIGHT", "身高"));
//            olympicConfigs.add(new OlympicConfig("WEIGHT", "体重"));
//            if(null != olympicLiveConfigSet) {
//                if(CollectionUtils.isNotEmpty(olympicLiveConfigSet.getPlayerExtendConfig())) {
//                    for (TOlympicConfig olympicConfig : olympicLiveConfigSet.getPlayerExtendConfig()) {
//                        olympicConfigs.add(new OlympicConfig(getCodeByConfigPath(olympicConfig.getPropertyName(), olympicConfig.getElementPath()), olympicConfig.getAnnotation()));
//                    }
//                }
//                if (!isLineUp) {
//                    if(CollectionUtils.isNotEmpty(olympicLiveConfigSet.getPlayerStatsConfig())) {
//                        for (TOlympicConfig olympicConfig : olympicLiveConfigSet.getPlayerStatsConfig()) {
//                            olympicConfigs.add(new OlympicConfig(getCodeByConfigPath(olympicConfig.getPropertyName(), olympicConfig.getElementPath()), olympicConfig.getAnnotation()));
//                        }
//                    }
//                }
//            }
//            model.addAttribute("matchStatsConfig", olympicConfigs);
//            //对阵类型
//            for (TSquad squad : squads) {
//                Map<Long, Map<String, String>> playerMapStats = Maps.newLinkedHashMap();
//                List<TSimplePlayer> simplePlayers = squad.getPlayers();
//                if(CollectionUtils.isNotEmpty(simplePlayers)) {
//                    for (TSimplePlayer simplePlayer : simplePlayers) {
//                        playerMapStats.put(simplePlayer.getId(), simplePlayer.getExtendInfos());
//                    }
//                }
//                teamMapStats.put(squad.getTid(), playerMapStats);
//            }
//            if (!isLineUp) {
//                if (CollectionUtils.isNotEmpty(competitorStats)) {
//                    for (TCompetitorStat tCompetitorStat : competitorStats) {
//                        Map<Long, Map<String, String>> playerStats = teamMapStats.get(tCompetitorStat.getId());
//                        List<TSimplePlayer> tCompetitorStatPlayerStats = tCompetitorStat.getPlayerStats();
//                        if(CollectionUtils.isNotEmpty(tCompetitorStatPlayerStats)) {
//                            for (TSimplePlayer simplePlayer : tCompetitorStatPlayerStats) {
//                                Map<String, String> playerStat = playerStats.get(simplePlayer.getId());
//                                if (null != simplePlayer.getStats()) {
//                                    playerStat.putAll(simplePlayer.getStats());
//                                    playerStats.put(simplePlayer.getId(), playerStat);
//                                }
//                            }
//                        }
//                        teamMapStats.put(tCompetitorStat.getTid(), playerStats);
//                    }
//                }
//            }
//            if(null != teamMapStats) {
//                for (Long tid : teamMapStats.keySet()){
//                    OlympicCompetitorStatsVo olympicCompetitorStatsVo = new OlympicCompetitorStatsVo();
//                    olympicCompetitorStatsVo.setTid(tid);
//                    Map<Long, Map<String, String>> maps = teamMapStats.get(tid);
//                    List<OlympicCompetitorStatsVo.OlympicPlayerStats> olympicPlayerStatsList = Lists.newArrayList();
//                    for(Long pid : maps.keySet()) {
//                        OlympicCompetitorStatsVo.OlympicPlayerStats olympicPlayerStats = new OlympicCompetitorStatsVo.OlympicPlayerStats();
//                        olympicPlayerStats.setPid(pid);
//                        olympicPlayerStats.setStats(maps.get(pid));
//                        olympicPlayerStatsList.add(olympicPlayerStats);
//                    }
//                    olympicCompetitorStatsVo.setPlayerStats(olympicPlayerStatsList);
//                    teamStats.add(olympicCompetitorStatsVo);
//                }
//            }
////            model.addAttribute("teamMapStats", teamMapStats);
//            model.addAttribute("teamStats", teamStats);
//        } else {
//            Map<Long, Map<String, String>> playerMapStats = Maps.newLinkedHashMap();
//            if (CollectionUtils.isNotEmpty(competitors)) {
//                olympicConfigs.add(new OlympicConfig("ORDER", "排名"));
//                if (competitors.get(0).getCompetitorType() == CompetitorType.TEAM) {
//                    olympicConfigs.add(new OlympicConfig("NATIONALITY", "国家"));
//                    if(null != olympicLiveConfigSet && CollectionUtils.isNotEmpty(olympicLiveConfigSet.getTeamExtendConfig())) {
//                        for (TOlympicConfig olympicConfig : olympicLiveConfigSet.getTeamExtendConfig()) {
//                            olympicConfigs.add(new OlympicConfig(getCodeByConfigPath(olympicConfig.getPropertyName(), olympicConfig.getElementPath()), olympicConfig.getAnnotation()));
//                        }
//                    }
//                    if (!isLineUp) {
//                        olympicConfigs.add(new OlympicConfig("FR", "成绩"));
//                        if(null != olympicLiveConfigSet && CollectionUtils.isNotEmpty(olympicLiveConfigSet.getResultConfig())) {
//                            for (TOlympicConfig olympicConfig : olympicLiveConfigSet.getResultConfig()) {
//                                olympicConfigs.add(new OlympicConfig(getCodeByConfigPath(olympicConfig.getPropertyName(), olympicConfig.getElementPath()), olympicConfig.getAnnotation()));
//                            }
//                        }
//                        if(null != olympicLiveConfigSet && CollectionUtils.isNotEmpty(olympicLiveConfigSet.getCompetitorStatsConfig())) {
//                            for (TOlympicConfig olympicConfig : olympicLiveConfigSet.getCompetitorStatsConfig()) {
//                                olympicConfigs.add(new OlympicConfig(getCodeByConfigPath(olympicConfig.getPropertyName(), olympicConfig.getElementPath()), olympicConfig.getAnnotation()));
//                            }
//                        }
//                    }
//                } else {
//                    olympicConfigs.add(new OlympicConfig("PN", "号码"));
//                    olympicConfigs.add(new OlympicConfig("NATIONALITY", "国家"));
//                    olympicConfigs.add(new OlympicConfig("NAME", "姓名"));
//                    olympicConfigs.add(new OlympicConfig("HEIGHT", "身高"));
//                    olympicConfigs.add(new OlympicConfig("WEIGHT", "体重"));
//                    if(null != olympicLiveConfigSet && CollectionUtils.isNotEmpty(olympicLiveConfigSet.getPlayerExtendConfig())) {
//                        for (TOlympicConfig olympicConfig : olympicLiveConfigSet.getPlayerExtendConfig()) {
//                            olympicConfigs.add(new OlympicConfig(getCodeByConfigPath(olympicConfig.getPropertyName(), olympicConfig.getElementPath()), olympicConfig.getAnnotation()));
//                        }
//                    }
//                    if (!isLineUp) {
//                        olympicConfigs.add(new OlympicConfig("FR", "成绩"));
//                        if(null != olympicLiveConfigSet && CollectionUtils.isNotEmpty(olympicLiveConfigSet.getResultConfig())) {
//                            for (TOlympicConfig olympicConfig : olympicLiveConfigSet.getResultConfig()) {
//                                if ((2 == vs || 3 == vs) && "INTERMEDIATE".equals(getCodeByConfigPath(olympicConfig.getPropertyName(), olympicConfig.getElementPath()))) {
//                                    continue;
//                                } else if (3 == vs && ("AFTER_INTERMEDIATE_RANK".equals(getCodeByConfigPath(olympicConfig.getPropertyName(), olympicConfig.getElementPath())) || ("AFTER_ATTEMPT_BEST".equals(getCodeByConfigPath(olympicConfig.getPropertyName(), olympicConfig.getElementPath()))))) {
//                                    continue;
//                                }
//                                olympicConfigs.add(new OlympicConfig(getCodeByConfigPath(olympicConfig.getPropertyName(), olympicConfig.getElementPath()), olympicConfig.getAnnotation()));
//
//                            }
//                        }
//                        if(null != olympicLiveConfigSet && CollectionUtils.isNotEmpty(olympicLiveConfigSet.getPlayerStatsConfig())) {
//                            for (TOlympicConfig olympicConfig : olympicLiveConfigSet.getPlayerStatsConfig()) {
//                                olympicConfigs.add(new OlympicConfig(getCodeByConfigPath(olympicConfig.getPropertyName(), olympicConfig.getElementPath()), olympicConfig.getAnnotation()));
//                            }
//                        }
//                    }
//
//                }
//                for (TCompetitor competitor : competitors) {
//                    if (competitor.getCompetitorType() == CompetitorType.TEAM) {
//                        Map<String,String> competitorEx = competitor.getExtendInfos();
//                        if(null == competitorEx) {
//                            competitorEx = Maps.newHashMap();
//                        }
//                        competitorEx.put("COUNTRYLOGO", competitor.getCountryImgUrl());
//                        competitorEx.put("ORDER", competitor.getOrder() + "");
//                        playerMapStats.put(competitor.getId(), competitorEx);
//                        if (!isLineUp) {
//                            competitorEx.put("FR", competitor.getFinalResult());
//                            playerMapStats.put(competitor.getId(), competitorEx);
//                        }
//                    } else {
//                        for (TSquad squad : squads) {
//                            List<TSimplePlayer> simplePlayers = squad.getPlayers();
//                            if(CollectionUtils.isNotEmpty(simplePlayers)){
//                                TSimplePlayer simplePlayer = simplePlayers.get(0);
//                                if(competitor.getId() == simplePlayer.getId()) {
//                                    Map<String, String> extendInfo = simplePlayer.getExtendInfos();
//                                    if(null == extendInfo) {
//                                        extendInfo = Maps.newHashMap();
//                                    }
//                                    extendInfo.put("COUNTRYLOGO", simplePlayer.getCountryImgUrl());
//                                    extendInfo.put("ORDER", competitor.getOrder() + "");
//                                    playerMapStats.put(simplePlayer.getId(), extendInfo);
//                                    if (!isLineUp) {
//                                        extendInfo.put("FR", competitor.getFinalResult());
//                                        Map<String, String> resultExtends = simplePlayer.getResultExtendInfos();
//                                        if(null == resultExtends) {
//                                            resultExtends = Maps.newHashMap();
//                                        }
//                                        if (2 == vs) {
//                                            String intermediate = resultExtends.get("INTERMEDIATE").replaceAll(" ", "").replace("[", "").replace("]", "");
//                                            List<String> intermediates = LeStringUtils.commaString2StringList(intermediate);
//                                            if (CollectionUtils.isNotEmpty(intermediates)) {
//                                                for (int i = 0; i < standards.size(); i++) {
//                                                    if (i >= intermediates.size()) {
//                                                        resultExtends.put(standards.get(i), "");
//                                                    } else {
//                                                        resultExtends.put(standards.get(i), intermediates.get(i));
//                                                    }
//                                                }
//                                            }
//                                        } else if (3 == vs) {
//                                            String intermediate = resultExtends.get("INTERMEDIATE").replaceAll(" ", "").replace("[", "").replace("]", "");
//                                            List<String> intermediates = LeStringUtils.commaString2StringList(intermediate);
//                                            if (CollectionUtils.isNotEmpty(intermediates)) {
//                                                maxSize = intermediates.size() > maxSize ? intermediates.size() : maxSize;
//                                                for (int i = 1; i <= intermediates.size(); i++) {
//                                                    resultExtends.put("第" + i + "次", intermediates.get(i - 1));
//                                                }
//                                            }
//                                        }
//                                        if (null != resultExtends) {
//                                            extendInfo.putAll(resultExtends);
//                                        }
//                                        playerMapStats.put(competitor.getId(), extendInfo);
//                                    }
//                                 break;
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//            if (!isLineUp) {
//                if (CollectionUtils.isNotEmpty(competitorStats)) {
//                    for (TCompetitorStat tCompetitorStat : competitorStats) {
//                        Map<String, String> playerStat = playerMapStats.get(tCompetitorStat.getId());
//                        if(null == playerStat) {
//                            playerStat = Maps.newHashMap();
//                        }
//                        if (null != tCompetitorStat.getStats()) {
//                            playerStat.putAll(tCompetitorStat.getStats());
//                            playerMapStats.put(tCompetitorStat.getId(), playerStat);
//                        }
//                    }
//                }
//            }
//            if(vs == 3 && maxSize > 0){
//                for(int i = 1;i <= maxSize;i++){
//                    olympicConfigs.add(new OlympicConfig("第"+i+"次", "第"+i+"次"));
//                }
//            }
//            model.addAttribute("matchStatsConfig", olympicConfigs);
//            model.addAttribute("playerStats", playerMapStats.entrySet());
//
//        }
//
//
//    }

    private String getWeekOfDate(Date date) {

        String[] weekOfDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};

        Calendar calendar = Calendar.getInstance();

        if(date != null){

            calendar.setTime(date);

        }

        int w = calendar.get(Calendar.DAY_OF_WEEK) - 1;

        if (w < 0){

            w = 0;

        }

        return weekOfDays[w];

    }

    private String getCodeByConfigPath(String code, String path) {
//        if (path == null || path.contains("?")) return code;
//        List<String> nameList = Lists.newArrayList();
//        String regex = "@Code='(\\w+)";
//        Pattern p = Pattern.compile(regex);
//        Matcher m = p.matcher(path);
//        while (m.find()) {
//            nameList.add(m.group(1));
//        }
//        String key = nameList.size() == 2 ? nameList.get(0) + "_" + nameList.get(1) : nameList.get(0);
//        return key;
        return code;
    }


    /**
     * 填充model用于比赛前页面
     *
     * @param model
     * @param match
     * @param episode
     */
    public void fillModel4NotStartMatch(Model model, TDetailMatch match, TComboEpisode episode, CallerParam caller, Locale locale) {
        fillModel4CommonMatch(model, match, episode, caller, locale);

        //如果是中超的话,添加赛前页的数据
        if (match.getCid() == 47001l) {
//            TMatchReview matchReview = SbdsApis.getTMatchReviewById(match.getId(), caller);
            TMatchReview matchReview = SbdMatchReviewApis.getTMatchReviewById(match.getId(), caller);
            if (matchReview != null) {
                //历史对阵
                model.addAttribute("confrontations", matchReview.getConfrontations());
                //历史对阵
                model.addAttribute("matchInfos", matchReview.getMatchInfos());
            }
        }
    }


    /**
     * 填充model用于比赛中页面
     *
     * @param model
     * @param match
     * @param episode
     */
    public void fillModel4Matching(Model model, TDetailMatch match, TComboEpisode episode, CallerParam caller, Locale locale) {
        fillModel4CommonMatch(model, match, episode, caller, locale);

        model.addAttribute("hasLive", hasLive(episode));
    }

    /**
     * 填充model用于比赛后页面
     *
     * @param model
     * @param match
     * @param episode
     */
    public void fillModel4EndMatch(Model model, TDetailMatch match, TComboEpisode episode, CallerParam caller, Locale locale) {
        fillModel4CommonMatch(model, match, episode, caller, locale);
        //填充本场速递视频
        fillModel5EpisodeVideos(model, episode, caller, locale);
        //比赛数据
        model.addAttribute("stats", match.getCompetitorStats());

    }


    /**
     * 填充model用于通用的图文直播页
     *
     * @param model
     * @param match
     * @param episode
     * @param caller
     */
    public void fillModel4TextLive(Model model, TDetailMatch match, TComboEpisode episode, CallerParam caller, Locale locale) {
        fillModel4CommonMatch(model, match, episode, caller, locale);
        //相关视频
        model.addAttribute("relatedVideos", videoService.getVideosRelated4Match(episode, match, caller, locale));
        //球队排名统计
        model.addAttribute("teamRank", topListService.getTeamTopListVos(match, caller));
        //球员数据
        model.addAttribute("playerData", getPlayerStatsOfMatch(match, episode));
        //球员最佳数据
        model.addAttribute("personalData", getBestPlayerStatsOfMatch(match, caller));

    }

    /**
     * 填充图文直播信息到model中
     *
     * @param model
     * @param episode
     */
    private void fillModel5TextLive(Model model, TComboEpisode episode, long callerId) {
        TTextLive tTextLive = TextLiveApis.getMainTTextLiveByEid(episode.getId());
        model.addAttribute("textLive", tTextLive);
        //帖子详情
		//只有大陆pc才会有帖子信息
		if (callerId == LeConstants.LESPORTS_PC_CALLER_CODE) {
			fillPosts(model, episode, callerId);
		}
        List<TSimpleTextLive> textlives = episode.getTextlives();
        if (CollectionUtils.isNotEmpty(textlives)) {
            //是否有图文直播 0:无 1:有
            model.addAttribute("hasTextLive", 1);
            //图文直播地址
            model.addAttribute("tLiveLink", episode.getTLiveLink());
            //图文直播直播流信息
            model.addAttribute("textlives", textlives);
            //图文直播各路流
            for (TSimpleTextLive textlive : textlives) {
                if (textlive.getType() == TextLiveType.ANCHOR) {
                    model.addAttribute("anchor", textlive);
                }
                if (textlive.getType() == TextLiveType.PLAY_BY_PLAY) {
                    model.addAttribute("playByPlay", textlive);
                }
            }
        } else {
            model.addAttribute("hasTextLive", 0);
        }
    }

    /**
     * 把节目上的视频填充都model中,即页面上的本场速递
     *
     * @param model
     * @param episode
     * @param caller
     */
    private void fillModel5EpisodeVideos(Model model, TComboEpisode episode, CallerParam caller, Locale locale) {
        GetRelatedVideosParam p = new GetRelatedVideosParam();
        p.setRelatedId(episode.getId());
        TVideoInfo videoInfo = QmtSbcApis.getTVideoInfoByRelatedIdAndType(p, PageUtils.createPageParam(0,100),CallerUtils.getDefaultCaller());

        if (videoInfo == null || CollectionUtils.isEmpty(videoInfo.getVideos())) {
            return;
        }
        Map<Long, VideoContentType> videoAndTypeMap = Maps.newHashMap();
        for (TVideo video : videoInfo.getVideos()) {
            videoAndTypeMap.put(video.getId(), video.getContentType());
        }
        List<TVideo> videos = QmtSbcApis.getTVideosByIds(Lists.newArrayList(videoAndTypeMap.keySet()), caller);
        if (CollectionUtils.isEmpty(videos)) {
            LOG.warn("fail to fillModel5EpisodeVideos. videos empty. vids : {}, caller : {}",
                    videoAndTypeMap.keySet(), caller);
            return;
        }
        //整场回放的集合(多种语言的回放合集),页面会有不同的icon进行展示
        List<VideoWebView> recordVideos = Lists.newArrayList();
        //集锦
        List<VideoWebView> highligths = Lists.newArrayList();
        //其它视频
        List<VideoWebView> others = Lists.newArrayList();
		//mVideos: m站改版,把所有视频放在一个list中
		List<VideoWebView> mVideos = Lists.newArrayList();
        for (TVideo video : videos) {
            if (video.isCloneVideo()) {
                continue;
            }
			//过滤视频的地区 暂时先不过滤语言
			if (CollectionUtils.isEmpty(video.getAllowCountries()) || !video.getAllowCountries().contains(caller.getCountry())) {
				continue;
			}
			VideoWebView videoWebView = videoService.buildVideoWebViewByTVideo(video, caller, locale);
            switch (videoAndTypeMap.get(video.getId())) {
                case RECORD:
                    recordVideos.add(videoWebView);
                    continue;
                case HIGHLIGHTS:
                    highligths.add(videoWebView);
                    continue;
                default:
                    others.add(videoWebView);
            }
        }
        if (CollectionUtils.isNotEmpty(recordVideos)) {
            //对录播排序
            Collections.sort(recordVideos, new Comparator<VideoWebView>() {
                @Override
                public int compare(VideoWebView o1, VideoWebView o2) {
                    return o2.getCreateAt().compareTo(o1.getCreateAt());
                }
            });
			mVideos.addAll(recordVideos);
        }
        if (CollectionUtils.isNotEmpty(highligths)) {
            //对集锦排序
            Collections.sort(highligths, new Comparator<VideoWebView>() {
                @Override
                public int compare(VideoWebView o1, VideoWebView o2) {
                    return o2.getCreateAt().compareTo(o1.getCreateAt());
                }
            });
			mVideos.addAll(highligths);
        }
        if (CollectionUtils.isNotEmpty(others)) {
            //对其它视频排序
            Collections.sort(others, new Comparator<VideoWebView>() {
                @Override
                public int compare(VideoWebView o1, VideoWebView o2) {
                    return o2.getCreateAt().compareTo(o1.getCreateAt());
                }
            });
            highligths.addAll(others);
			mVideos.addAll(others);
        }

        //回放
        model.addAttribute("recordVideos", recordVideos);
        //集锦
        model.addAttribute("highlights", highligths);
		//获取相关视频,如果相关视频不为空,给到mVideos里面
		Map<String, Object> map = model.asMap();
		List<VideoWebView> relatedVideos = (List) map.get("relatedVideos");
		if (CollectionUtils.isNotEmpty(relatedVideos)) {
			mVideos.addAll(relatedVideos);
		}
		//mVideos
		model.addAttribute("mVideos", mVideos);
    }

    /**
     * 获取比赛阵容
     *
     * @param match
     * @param episode
     * @return
     */
    public Object getSquads(TDetailMatch match, TComboEpisode episode, CallerParam caller) {
        if (match == null || episode == null) {
            return Collections.EMPTY_LIST;
        }
        List<TSquad> squads = match.getSquads();
        if (CollectionUtils.isEmpty(squads)) {
            return Collections.EMPTY_LIST;
        }
        if (episode.isVs()) {
            Map<Long, Object> squadMap = Maps.newHashMap();
            for (TSquad squad : squads) {
                List playerList = Lists.newArrayList();
                List<TSimplePlayer> players = squad.getPlayers();
                if (CollectionUtils.isNotEmpty(players)) {
                    //首发阵容
                    List<TPlayer> startingList = Lists.newArrayList();
                    //替补阵容
                    List<TPlayer> alternateList = Lists.newArrayList();
                    for (TSimplePlayer simplePlayer : players) {
                        if (simplePlayer.isStarting()) {
                            TPlayer player = SbdPlayerApis.getTPlayerById(simplePlayer.getId(), caller);
                            if (player != null) {
                                player.setNumber(simplePlayer.getNumber());
                                player.setPosition(simplePlayer.getPosition());
								player.setPositionId(simplePlayer.getPositionId());
                                startingList.add(player);
                            }
                        } else {
                            TPlayer player = SbdPlayerApis.getTPlayerById(simplePlayer.getId(), caller);
                            if (player != null) {
                                player.setNumber(simplePlayer.getNumber());
                                player.setPosition(simplePlayer.getPosition());
								player.setPositionId(simplePlayer.getPositionId());
                                alternateList.add(player);
                            }
                        }
                    }
					if (CollectionUtils.isNotEmpty(startingList)) {
						Collections.sort(startingList, new sortPlayers());
					}
					if (CollectionUtils.isNotEmpty(alternateList)) {
						Collections.sort(alternateList, new sortPlayers());
					}
                    playerList.add(0, startingList);
                    playerList.add(1, alternateList);
                    squadMap.put(squad.getTid(), playerList);
                }
            }
            return squadMap;
        } else {
            return squads;
        }
    }

	/**按照球员位置从后到前的顺序进行排序
		100125000	守门员
		104676000	门将
		100126000	后卫
		104680000	中后卫
		104678000	边后卫
		104682000	左后卫
		116597000	右后卫
		100127000	中场
		104677000	后腰
		104681000	中前卫
		104679000	前腰
		100129000	前卫
		116598000	右前卫
		116497000	右边锋
		100128000	前锋
		104655000	中锋
	 */
	public static class sortPlayers implements Comparator<TPlayer> {

		List<Long> positionList = Lists.newArrayList(100125000l, 104676000l, 100126000l, 100120000l, 104680000l, 104678000l, 104682000l, 116597000l, 100127000l, 104677000l,
				104681000l, 104679000l, 100129000l, 116598000l, 116497000l, 100128000l, 100117000l, 104655000l, 100121000l, 0l);

		@Override
		public int compare(TPlayer o1, TPlayer o2) {
			return positionList.indexOf(o1.getPositionId()) - 	positionList.indexOf(o2.getPositionId());
		}
	}


    /**
     * 获取球员统计数据
     *
     * @param match
     * @param episode
     * @return
     */
    private Map<Long, List<List<TSimplePlayer>>> getPlayerStatsOfMatch(TDetailMatch match, TComboEpisode episode) {
        if (match == null || episode == null) {
            return Maps.newHashMap();
        }
        if (!episode.isVs()) {
            return Maps.newHashMap();
        }
        List<TSquad> squads = match.getSquads();
        if (CollectionUtils.isEmpty(squads)) {
            return Maps.newHashMap();
        }
        Map<Long, List<List<TSimplePlayer>>> squadMap = Maps.newHashMap();
        for (TSquad squad : squads) {
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
                sortPlayerList(startingList, alternateList, match);
                playerList.add(0, startingList);
                playerList.add(1, alternateList);
                squadMap.put(squad.getTid(), playerList);
            }
        }
        return squadMap;
    }

    /**
     * 对球员做排序
     *
     * @param startingList
     * @param alternateList
     * @param match
     */
    private void sortPlayerList(List<TSimplePlayer> startingList, List<TSimplePlayer> alternateList, final TDetailMatch match) {
        //以球员号码排序
        Collections.sort(startingList, new Comparator<TSimplePlayer>() {
            @Override
            public int compare(TSimplePlayer p1, TSimplePlayer p2) {
                //nba首发球员按位置排名
                if (match.getCid() == 44001) {
                    if (StringUtils.isNotBlank(p2.getPosition()) && StringUtils.isNotBlank(p1.getPosition())) {
                        return p2.getPosition().compareTo(p1.getPosition());
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
                if (match.getCid() == 44001) {
                    Double p1Minutes = LeNumberUtils.toDouble(p1.getStats().get("minutes"), 0);
                    Double p2Minutes = LeNumberUtils.toDouble(p2.getStats().get("minutes"), 0);
                    return p2Minutes.compareTo(p1Minutes);
                } else {
                    return p1.getNumber() - p2.getNumber();
                }
            }
        });
    }


    /**
     * 判断节目上是否有直播
     *
     * @param episode
     * @return
     */
    private boolean hasLive(TComboEpisode episode) {
        boolean hasLive = false;
        List<TLiveStream> streams = episode.getStreams();
        if (CollectionUtils.isNotEmpty(streams)) {
            TLiveStream tLiveStream = streams.get(0);
            if (tLiveStream != null && !tLiveStream.getLiveId().equals("-1")) {
                hasLive = true;
            }
        }
        return hasLive;
    }

    /**
     * 获取本场最佳球员统计
     *
     * @param match
     * @return
     */
    public Map<Long, List<BestPlayerStatsVo>> getBestPlayerStatsOfMatch(TDetailMatch match, CallerParam caller) {
        Map<Long, List<BestPlayerStatsVo>> result = Maps.newHashMap();
        List<TSquad> tSquads = match.getBestPlayerStats();
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


}
