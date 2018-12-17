package com.lesports.qmt.sbd.converter;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.lesports.LeConstants;
import com.lesports.api.common.CallerParam;
import com.lesports.qmt.config.api.dto.TTag;
import com.lesports.qmt.config.client.QmtConfigDictInternalApis;
import com.lesports.qmt.config.client.QmtConfigTagInternalApis;
import com.lesports.qmt.config.model.DictEntry;
import com.lesports.qmt.config.model.Tag;
import com.lesports.qmt.converter.TDtoConverter;
import com.lesports.qmt.sbd.api.common.CompetitorType;
import com.lesports.qmt.sbd.api.dto.*;
import com.lesports.qmt.sbd.helper.MatchHelper;
import com.lesports.qmt.sbd.model.Competition;
import com.lesports.qmt.sbd.model.Match;
import com.lesports.qmt.sbd.model.MatchStats;
import com.lesports.qmt.sbd.repository.CompetitionRepository;
import com.lesports.qmt.sbd.repository.MatchStatsRepository;
import com.lesports.utils.CallerUtils;
import com.lesports.utils.math.LeNumberUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by ruiyuansheng on 2016/3/22.
 */
@Component("detailMatchVoConverter")
public class TDetailMatchVoConverter implements TDtoConverter<Match, TDetailMatch> {


    @Resource
    private MatchHelper matchHelper;
    @Resource
    private MatchStatsRepository matchStatsRepository;
    @Resource
    private CompetitionRepository competitionRepository;

    @Override
    public TDetailMatch toDto(Match match) {
        if (match == null) {
            return null;
        }
        TDetailMatch tMatch = new TDetailMatch();
        fillTDetailMatchWithMatch(tMatch, match);
        return tMatch;
    }

    @Override
    public TDetailMatch adaptDto(TDetailMatch vo, CallerParam caller) {
        Preconditions.checkNotNull(vo);
        Preconditions.checkNotNull(caller);
        adaptVoCompetitors4Caller(vo, caller);
        adaptVoName4Caller(vo, caller);
        adaptVoVenue4Caller(vo, caller);
        adaptVoMoment4Caller(vo, caller);
        adaptVoWeather4Caller(vo, caller);
        adaptVoJudge4Caller(vo, caller);
        adaptVoEids4Caller(vo, caller);
        adaptVoPlayerAndCoach4Caller(vo, caller);
		adaptVoBestPlayerStats4Caller(vo, caller);
        return vo;
    }



	public List<TDetailMatch> fillTDetailMatchList(Page<Match> matches) {
        List<TDetailMatch> resultList = Lists.newArrayList();
        if (matches.getSize() > 0) {
            for (Match match : matches) {
                resultList.add(this.convertTDetailMatchLess(match));
//                resultList.add(this.toTVo(match));
            }
        }
        return resultList;
    }
    private TDetailMatch convertTDetailMatchLess(Match match) {
        TDetailMatch tMatch = new TDetailMatch();
        tMatch.setId(match.getId());
        // eid -> eids
        tMatch.setEids(match.getEids());
        tMatch.setName(Strings.nullToEmpty(match.getName()));
        if (CollectionUtils.isNotEmpty(match.getMultiLangNames())) {
            tMatch.setMultiLangNames(match.getMultiLangNames());
        }
        tMatch.setCid(LeNumberUtils.toLong(match.getCid()));
        tMatch.setCsid(LeNumberUtils.toLong(match.getCsid()));
//        tMatch.setVs(null == match.getVs() ? false : match.getVs());
        if (MapUtils.isNotEmpty(match.getExtendInfos())) {
            for (Map.Entry<String, Object> entry : match.getExtendInfos().entrySet()) {
                tMatch.putToExtendInfos(entry.getKey(), String.valueOf(entry.getValue()));
            }
        }
        tMatch.setStartTime(match.getStartTime());
        tMatch.setEndTime(match.getEndTime());
        tMatch.setVenue(Strings.nullToEmpty(match.getVenue()));
        if (CollectionUtils.isNotEmpty(match.getMultiLangVenues())) {
            tMatch.setMultiLangVenues(match.getMultiLangVenues());
        }
        tMatch.setStatus(match.getStatus());
//        tMatch.setMoment(Strings.nullToEmpty(match.getMoment()));
//        if (CollectionUtils.isNotEmpty(match.getMultiLangMoments())) {
//            tMatch.setMultiLangMoments(match.getMultiLangMoments());
//        }
        //比赛天气
        tMatch.setWeather(Strings.nullToEmpty(match.getWeather()));
        if (CollectionUtils.isNotEmpty(match.getMultiLangWeathers())) {
            tMatch.setMultiLangWeathers(match.getMultiLangWeathers());
        }
        //比赛主裁判
        tMatch.setJudge(Strings.nullToEmpty(match.getJudge()));
        if (CollectionUtils.isNotEmpty(match.getMultiLangJudges())) {
            tMatch.setMultiLangJudges(match.getMultiLangJudges());
        }
        tMatch.setNumber(LeNumberUtils.toInt(match.getNumber()));
        Set<Match.Competitor> competitors = match.getCompetitors();
        if (CollectionUtils.isNotEmpty(competitors)) {
            for (Match.Competitor competitor : competitors) {
                tMatch.addToCompetitors(matchHelper.createTCompetitor(competitor));
            }
        }

        tMatch.setGameFType(LeNumberUtils.toLong(match.getGameFType()));
        tMatch.setDisciplineId(LeNumberUtils.toLong(match.getDiscipline()));
        tMatch.setGameSType(LeNumberUtils.toLong(match.getGameSType()));
//        tMatch.setPartnerType(LeNumberUtils.toInt(match.getPartners().get()));
        tMatch.setStage(match.getStage()+"");
        return tMatch;
    }

	private void adaptVoBestPlayerStats4Caller(TDetailMatch vo, CallerParam caller) {
		Iterator<TSquad> bestPlayerStatsIterator = vo.getBestPlayerStatsIterator();
		if (null != bestPlayerStatsIterator) {
			while (bestPlayerStatsIterator.hasNext()) {
				TSquad tSquad = bestPlayerStatsIterator.next();
				if (tSquad != null) {
					//球员以及球员位置
					Iterator<TSimplePlayer> playersIterator = tSquad.getPlayersIterator();
					if (null != playersIterator) {
						while (playersIterator.hasNext()) {
							TSimplePlayer tSimplePlayer = playersIterator.next();
							if (null != tSimplePlayer) {
								adaptVoSimplePlayerAndPosition4Caller(caller, tSimplePlayer);
							}
						}
					}
				}
			}
		}
	}

    private void adaptVoPlayerAndCoach4Caller(TDetailMatch vo, CallerParam caller) {
        Iterator<TSquad> squadsIterator = vo.getSquadsIterator();
        if (null != squadsIterator) {
            while (squadsIterator.hasNext()) {
                TSquad tSquad = squadsIterator.next();
                if (tSquad != null) {
                    //球员以及球员位置
                    Iterator<TSimplePlayer> playersIterator = tSquad.getPlayersIterator();
                    if (null != playersIterator) {
                        while (playersIterator.hasNext()) {
                            TSimplePlayer tSimplePlayer = playersIterator.next();
                            if (null != tSimplePlayer) {
                                adaptVoSimplePlayerAndPosition4Caller(caller, tSimplePlayer);
                            }
                        }
                    }
                    //教练
                    Iterator<TSimpleCoach> coachIterator = tSquad.getCoachesIterator();
                    if (null != coachIterator) {
                        while (coachIterator.hasNext()) {
                            TSimpleCoach tSimpleCoach = coachIterator.next();
                            if (null != tSimpleCoach) {
                                adaptVoSimpleCoach4Caller(caller, tSimpleCoach);
                            }
                        }
                    }
                }
            }
        }
    }

    private void adaptVoSimplePlayerAndPosition4Caller(CallerParam caller, TSimplePlayer tSimplePlayer) {
        String langName = CallerUtils.getValueOfLanguage(tSimplePlayer.getMultiLangPosition(), caller.getLanguage());
        if (StringUtils.isNotEmpty(langName)) {
            tSimplePlayer.setPosition(langName);
        }
        tSimplePlayer.setMultiLangPositionIsSet(false);
        String playerLangName = CallerUtils.getValueOfLanguage(tSimplePlayer.getMultiLangNames(), caller.getLanguage());
        if (StringUtils.isNotEmpty(playerLangName)) {
            tSimplePlayer.setName(playerLangName);
        }
        tSimplePlayer.setMultiLangNamesIsSet(false);
    }

    private void adaptVoSimpleCoach4Caller(CallerParam caller, TSimpleCoach tSimpleCoach) {
        String playerLangName = CallerUtils.getValueOfLanguage(tSimpleCoach.getMultiLangNames(), caller.getLanguage());
        if (StringUtils.isNotEmpty(playerLangName)) {
            tSimpleCoach.setName(playerLangName);
        }
        tSimpleCoach.setMultiLangNamesIsSet(false);
    }

    private void adaptVoCompetitors4Caller(TDetailMatch vo, CallerParam caller) {
        Iterator<TCompetitor> competitorsIterator = vo.getCompetitorsIterator();
        if (null != competitorsIterator) {
            while (competitorsIterator.hasNext()) {
                TCompetitor tCompetitor = competitorsIterator.next();
                if (null != tCompetitor) {
                    adaptVoCompetitor4Caller(caller, tCompetitor);
                }
            }
        }
    }

    private void adaptVoCompetitor4Caller(CallerParam caller, TCompetitor tCompetitor) {
        String langName = CallerUtils.getValueOfLanguage(tCompetitor.getMultiLangNames(), caller.getLanguage());
        if (StringUtils.isNotEmpty(langName)) {
            tCompetitor.setName(langName);
        }
        tCompetitor.setMultiLangNamesIsSet(false);

        if(tCompetitor.getCompetitorType() == CompetitorType.TEAM) {
            String teamLogo = CallerUtils.getValueOfCountry(tCompetitor.getMultiCounLogos(), caller.getCountry());
            if (StringUtils.isNotEmpty(teamLogo)) {
                tCompetitor.setLogoUrl(teamLogo);
            }
            tCompetitor.setMultiCounLogosIsSet(false);
        }

        String officialName = CallerUtils.getValueOfLanguage(tCompetitor.getMultiLangOfficialNames(), caller.getLanguage());
        if (StringUtils.isNotEmpty(officialName)) {
            tCompetitor.setOfficialName(officialName);
        }
        tCompetitor.setMultiLangOfficialNamesIsSet(false);
    }

    private void adaptVoEids4Caller(TDetailMatch vo, CallerParam caller) {
        long eid = CallerUtils.getIdofCountryAndLanguage(vo.getEids(), caller.getCountry(), caller.getLanguage());
        if (eid > 0) {
            vo.setEid(eid);
        }
    }

    private void adaptVoName4Caller(TDetailMatch vo, CallerParam caller) {
        String langName = CallerUtils.getValueOfLanguage(vo.getMultiLangNames(), caller.getLanguage());
        if (StringUtils.isNotEmpty(langName)) {
            vo.setName(langName);
        }
        vo.setMultiLangNamesIsSet(false);
    }

    private void adaptVoVenue4Caller(TDetailMatch vo, CallerParam caller) {
        String langVenue = CallerUtils.getValueOfLanguage(vo.getMultiLangVenues(), caller.getLanguage());
        if (StringUtils.isNotEmpty(langVenue)) {
            vo.setVenue(langVenue);
        }
        vo.setMultiLangVenuesIsSet(false);
    }

    private void adaptVoMoment4Caller(TDetailMatch vo, CallerParam caller) {
        String langMoment = CallerUtils.getValueOfLanguage(vo.getMultiLangMoments(), caller.getLanguage());
        if (StringUtils.isNotEmpty(langMoment)) {
            vo.setMoment(langMoment);
        }
        vo.setMultiLangVenuesIsSet(false);
    }

    private void adaptVoWeather4Caller(TDetailMatch vo, CallerParam caller) {
        String langWeather = CallerUtils.getValueOfLanguage(vo.getMultiLangWeathers(), caller.getLanguage());
        if (StringUtils.isNotEmpty(langWeather)) {
            vo.setWeather(langWeather);
        }
        vo.setMultiLangWeathersIsSet(false);
    }

    private void adaptVoJudge4Caller(TDetailMatch vo, CallerParam caller) {
        String langJudge = CallerUtils.getValueOfLanguage(vo.getMultiLangJudges(), caller.getLanguage());
        if (StringUtils.isNotEmpty(langJudge)) {
            vo.setJudge(langJudge);
        }
        vo.setMultiLangVenuesIsSet(false);
    }

    private void fillTDetailMatchWithMatch(TDetailMatch tMatch, Match match) {
        tMatch.setId(match.getId());
        // eid -> eids
        tMatch.setEids(match.getEids());
        tMatch.setName(Strings.nullToEmpty(match.getName()));
        if (CollectionUtils.isNotEmpty(match.getMultiLangNames())) {
            tMatch.setMultiLangNames(match.getMultiLangNames());
        }
        tMatch.setCid(LeNumberUtils.toLong(match.getCid()));
        tMatch.setCsid(LeNumberUtils.toLong(match.getCsid()));
//        tMatch.setVs(null == match.getVs() ? false : match.getVs());
        tMatch.setGameSType(LeNumberUtils.toLong(match.getGameSType()));
        tMatch.setGameFType(LeNumberUtils.toLong(match.getGameFType()));
        if (MapUtils.isNotEmpty(match.getExtendInfos())) {
            for (Map.Entry<String, Object> entry : match.getExtendInfos().entrySet()) {
                tMatch.putToExtendInfos(entry.getKey(), String.valueOf(entry.getValue()));
            }
        }
        tMatch.setStartTime(match.getStartTime());
        tMatch.setEndTime(match.getEndTime());
        tMatch.setVenue(Strings.nullToEmpty(match.getVenue()));
        if (CollectionUtils.isNotEmpty(match.getMultiLangVenues())) {
            tMatch.setMultiLangVenues(match.getMultiLangVenues());
        }
        tMatch.setStatus(match.getStatus());
//        tMatch.setMoment(Strings.nullToEmpty(match.getMoment()));
//        if (CollectionUtils.isNotEmpty(match.getMultiLangMoments())) {
//            tMatch.setMultiLangMoments(match.getMultiLangMoments());
//        }
        //比赛天气
        tMatch.setWeather(Strings.nullToEmpty(match.getWeather()));
        if (CollectionUtils.isNotEmpty(match.getMultiLangWeathers())) {
            tMatch.setMultiLangWeathers(match.getMultiLangWeathers());
        }
        //比赛主裁判
        tMatch.setJudge(Strings.nullToEmpty(match.getJudge()));
        if (CollectionUtils.isNotEmpty(match.getMultiLangJudges())) {
            tMatch.setMultiLangJudges(match.getMultiLangJudges());
        }
        long cid = tMatch.getCid();
        Competition competition = competitionRepository.findOne(cid);
        if (competition != null) {
            Set<Long> tagIds = competition.getTagIds();
            if (CollectionUtils.isNotEmpty(tagIds)) {
                for (Long tagId : tagIds) {
                    Tag tag = QmtConfigTagInternalApis.getTagById(tagId);
                    if (tag != null) {
                        TTag tTag = new TTag();
                        tTag.setId(tag.getId());
                        tTag.setName(tag.getName());
                        tMatch.addToTags(tTag);
                    }
                }
            }
        }
        if (match.getRound() != null) {
            DictEntry dictEntry = QmtConfigDictInternalApis.getDictById(match.getRound());
            if (dictEntry != null) {
                tMatch.setRound(dictEntry.getReadableName());
            }
        }
        if (match.getStage() != null) {
            DictEntry dictEntry = QmtConfigDictInternalApis.getDictById(match.getStage());
            if (dictEntry != null) {
                tMatch.setStage(dictEntry.getReadableName());
            }
        }
        if (match.getGroup() != null) {
            DictEntry dictEntry = QmtConfigDictInternalApis.getDictById(match.getGroup());
            if (dictEntry != null) {
                tMatch.setGroup(dictEntry.getReadableName());
            }
        }
        if (match.getSubstation() != null) {
            DictEntry dictEntry = QmtConfigDictInternalApis.getDictById(match.getSubstation());
            if (dictEntry != null) {
                tMatch.setSubstation(dictEntry.getReadableName());
            }
        }
        tMatch.setNumber(LeNumberUtils.toInt(match.getNumber()));
        Set<Match.Competitor> competitors = match.getCompetitors();
        if (CollectionUtils.isNotEmpty(competitors)) {
            for (Match.Competitor competitor : competitors) {
                tMatch.addToCompetitors(matchHelper.createTCompetitor(competitor));
            }
            if (CollectionUtils.isNotEmpty(tMatch.getCompetitors())) {
                if (LeConstants.REVERSE_HOME_AWAY_TEAMS_CIDS.contains(tMatch.getCid())) {
                    Collections.sort(tMatch.getCompetitors(), new Comparator<TCompetitor>() {
                        @Override
                        public int compare(TCompetitor o1, TCompetitor o2) {
                            return o2.getGround().ordinal() - o1.getGround().ordinal();
                        }
                    });
//                } else if (Constants.OLYMPIC_PARTNER_TYPE.equals(match.getPartnerType())) {
//                    Collections.sort(tMatch.getCompetitors(), new Comparator<TCompetitor>() {
//                        @Override
//                        public int compare(TCompetitor o1, TCompetitor o2) {
//                            return o1.getOrder() - o2.getOrder();
//                        }
//                    });
                } else {
                    Collections.sort(tMatch.getCompetitors(), new Comparator<TCompetitor>() {
                        @Override
                        public int compare(TCompetitor o1, TCompetitor o2) {
                            return o1.getGround().ordinal() - o2.getGround().ordinal();
                        }
                    });
                }
            }
        }
        MatchStats matchStats = matchStatsRepository.findOne(match.getId());

        if(null != matchStats){
            List<MatchStats.Squad> squads = matchStats.getSquads();
            if (CollectionUtils.isNotEmpty(squads)) {
                for (MatchStats.Squad squad : squads) {
                    tMatch.addToSquads(matchHelper.createTSquad(squad));
                }
            }

            Set<MatchStats.CompetitorStat> competitorStats = matchStats.getCompetitorStats();
            if (CollectionUtils.isNotEmpty(competitorStats)) {
                for (MatchStats.CompetitorStat stat : competitorStats) {
                    //如果统计数据不为空在返回数据
                    if (stat.getStats() != null && !stat.getStats().isEmpty()) {
                        tMatch.addToCompetitorStats(matchHelper.createTCompetitorStat(stat));
                    }
                }

                if (CollectionUtils.isNotEmpty(tMatch.getCompetitorStats())) {
                    Collections.sort(tMatch.getCompetitorStats(), new Comparator<TCompetitorStat>() {
                        @Override
                        public int compare(TCompetitorStat o1, TCompetitorStat o2) {
                            return o1.getOrder() - o2.getOrder();
                        }
                    });
                }
            }


            Set<MatchStats.BestPlayerStat> bestPlayerStats = matchStats.getBestPlayerStats();
            if (CollectionUtils.isNotEmpty(bestPlayerStats)) {
                for (MatchStats.BestPlayerStat stat : bestPlayerStats) {
                    tMatch.addToBestPlayerStats(matchHelper.createBestPlayerStat(stat));
                }
            }
        }

        if(match.getCurrentMoment() != null){
            tMatch.setCurrentMoment(matchHelper.createTCurrentMoment(match.getCurrentMoment()));
        }
    }
}
