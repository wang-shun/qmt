package com.lesports.qmt.sbd.converter;


import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.lesports.LeConstants;
import com.lesports.api.common.CallerParam;
import com.lesports.qmt.config.client.QmtConfigDictInternalApis;
import com.lesports.qmt.config.model.DictEntry;
import com.lesports.qmt.converter.TDtoConverter;
import com.lesports.qmt.sbd.api.common.CompetitorType;
import com.lesports.qmt.sbd.api.common.GroundType;
import com.lesports.qmt.sbd.api.common.MatchResult;
import com.lesports.qmt.sbd.api.dto.TCompetitor;
import com.lesports.qmt.sbd.api.dto.THistoryMatch;
import com.lesports.qmt.sbd.api.dto.TMatchInfo;
import com.lesports.qmt.sbd.api.dto.TMatchReview;
import com.lesports.qmt.sbd.helper.MatchHelper;
import com.lesports.qmt.sbd.model.Competition;
import com.lesports.qmt.sbd.model.Match;
import com.lesports.qmt.sbd.model.MatchReview;
import com.lesports.qmt.sbd.repository.CompetitionRepository;
import com.lesports.qmt.sbd.repository.MatchRepository;
import com.lesports.utils.CallerUtils;
import com.lesports.utils.math.LeNumberUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by ruiyuansheng on 2016/3/25.
 */
@Component("matchReviewVoConverter")
public class TMatchReviewVoConverter implements TDtoConverter<MatchReview,TMatchReview> {

    @Resource
    private MatchHelper matchHelper;
    @Resource
    private CompetitionRepository competitionRepository;
    @Resource
    private MatchRepository matchRepository;

    @Override
    public TMatchReview toDto(MatchReview matchReview) {
        Preconditions.checkNotNull(matchReview);

        TMatchReview tMatchReview = new TMatchReview();
        fillTMatchReviewWithMatchReview(tMatchReview, matchReview);
        return tMatchReview;
    }

    @Override
    public TMatchReview adaptDto(TMatchReview vo, CallerParam caller) {
        Preconditions.checkNotNull(vo);
        Preconditions.checkNotNull(caller);

        adaptVoName4Caller(vo,caller);
        adaptVoMatchInfo4Caller(vo,caller);

        return vo;
    }

    private void adaptVoName4Caller(TMatchReview vo, CallerParam caller){
        String langName = CallerUtils.getValueOfLanguage(vo.getMultiLangNames(), caller.getLanguage());
        if(StringUtils.isNotEmpty(langName)){
            vo.setName(langName);
        }
        vo.setMultiLangNamesIsSet(false);
    }

    private void adaptVoHistoryMatch4Caller(THistoryMatch vo,CallerParam caller){
        String langName = CallerUtils.getValueOfLanguage(vo.getMultiLangNames(), caller.getLanguage());
        if(StringUtils.isNotEmpty(langName)){
            vo.setName(langName);
        }
        String langAbbr = CallerUtils.getValueOfLanguage(vo.getMultiLangAbbrs(), caller.getLanguage());
        if(StringUtils.isNotEmpty(langAbbr)){
            vo.setAbbreviation(langAbbr);
        }
        String langStage = CallerUtils.getValueOfLanguage(vo.getMultiLangStages(), caller.getLanguage());
        if(StringUtils.isNotEmpty(langStage)){
            vo.setStage(langStage);
        }
        String langRound = CallerUtils.getValueOfLanguage(vo.getMultiLangRounds(), caller.getLanguage());
        if(StringUtils.isNotEmpty(langRound)){
            vo.setRound(langRound);
        }
		adaptVoCompetitors4Caller(vo, caller);
        vo.setMultiLangNamesIsSet(false);
        vo.setMultiLangAbbrsIsSet(false);
        vo.setMultiLangRoundsIsSet(false);
        vo.setMultiLangStagesIsSet(false);
    }

	private void adaptVoCompetitors4Caller(THistoryMatch vo, CallerParam caller){
		Iterator<TCompetitor> competitorsIterator = vo.getCompetitorsIterator();
		if(null != competitorsIterator) {
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
		if(StringUtils.isNotEmpty(langName)){
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
		if(StringUtils.isNotEmpty(officialName)){
			tCompetitor.setOfficialName(officialName);
		}
		tCompetitor.setMultiLangOfficialNamesIsSet(false);
        adaptVoFocused(tCompetitor,caller);
	}


    private void adaptVoFocused(TCompetitor vo, CallerParam caller) {
        //如果可被关注,可被关注的国家列表为空,则直接返回不可被关注
        if (CollectionUtils.isEmpty(vo.getIsFocusedCountries())) {
            vo.setIsFocused(false);
        } else {
            if (vo.getIsFocusedCountries().contains(caller.getCountry())) {
                vo.setIsFocused(true);
            } else {
                vo.setIsFocused(false);
            }
        }
    }

    private void adaptVoMatchInfo4Caller(TMatchReview vo, CallerParam caller) {

        List<THistoryMatch> confrontations = vo.getConfrontations();
        if (CollectionUtils.isNotEmpty(confrontations)) {
            for (THistoryMatch tHistoryMatch : confrontations) {
                adaptVoHistoryMatch4Caller(tHistoryMatch, caller);
            }
        }

        List<TMatchInfo> matchInfos = vo.getMatchInfos();
        if (CollectionUtils.isNotEmpty(matchInfos)) {
            for (TMatchInfo matchInfo : matchInfos) {
                List<THistoryMatch> afterMatchs = matchInfo.getAfterMatches();
                if (CollectionUtils.isNotEmpty(afterMatchs)) {
                    for (THistoryMatch afterMatch : afterMatchs) {
                        adaptVoHistoryMatch4Caller(afterMatch, caller);
                    }
                }
                List<THistoryMatch> nearMatchs = matchInfo.getNearMatches();
                if (CollectionUtils.isNotEmpty(nearMatchs)) {
                    for (THistoryMatch nearMatch : nearMatchs) {
                        adaptVoHistoryMatch4Caller(nearMatch, caller);
                    }
                }
            }
        }
    }

    private void fillTMatchReviewWithMatchReview(TMatchReview tMatchReview, MatchReview matchReview) {
        tMatchReview.setId(matchReview.getId());
        tMatchReview.setName(matchReview.getName());
        if(CollectionUtils.isNotEmpty(matchReview.getMultiLangNames())) {
            tMatchReview.setMultiLangNames(matchReview.getMultiLangNames());
        }
        //最近更新时间
        tMatchReview.setUpdateAt(matchReview.getUpdateAt());
        //历史交锋数据统计
        Map<String, String> tStatsMap = Maps.newHashMap();
        if (MapUtils.isNotEmpty(matchReview.getStats())) {
            for (Map.Entry<String, Object> entry : matchReview.getStats().entrySet()) {
                tStatsMap.put(entry.getKey(), String.valueOf(entry.getValue()));
            }
        }
        tMatchReview.setStats(tStatsMap);

        //历史交锋数据
        if (CollectionUtils.isNotEmpty(matchReview.getConfrontations())) {
            List<Match> confrontations = matchRepository.findByIds(matchReview.getConfrontations());
            List<THistoryMatch> tConfrontations = Lists.newArrayList();
            for(Match confrontation:confrontations){
                tConfrontations.add(createHistoryMatch(confrontation,0L));
            }
            tMatchReview.setConfrontations(tConfrontations);
        }
        //最近及未来比赛信息
        Set<MatchReview.MatchInfo> matchInfos = matchReview.getMatchInfos();
                if (CollectionUtils.isNotEmpty(matchInfos)) {
                    List<TMatchInfo> tMatchInfos =  Lists.newArrayList();
                    for(MatchReview.MatchInfo matchInfo : matchInfos){
                        TMatchInfo tMatchInfo = new TMatchInfo();
                        tMatchInfo.setCompetitorId(matchInfo.getCompetitorId());
                        tMatchInfo.setType(matchInfo.getType());
                        tMatchInfo.setGround(matchInfo.getGround());
                        //最近比赛统计
                        Map<String, String> tStats = Maps.newHashMap();
                        if (MapUtils.isNotEmpty(matchInfo.getStats())) {
                            for (Map.Entry<String, Object> entry : matchInfo.getStats().entrySet()) {
                                tStats.put(entry.getKey(), String.valueOf(entry.getValue()));
                            }
                            tMatchInfo.setStats(tStats);
                        }

                        if (CollectionUtils.isNotEmpty(matchInfo.getAfterMatches())) {
                            List<Match> afterMatchs = matchRepository.findByIds(matchInfo.getAfterMatches());
                            List<THistoryMatch> tAfterMatchs = Lists.newArrayList();
                            for(Match afterMatch:afterMatchs){
                                tAfterMatchs.add(createHistoryMatch(afterMatch,matchInfo.getCompetitorId()));
                            }
                            tMatchInfo.setAfterMatches(tAfterMatchs);
                        }

                        if (CollectionUtils.isNotEmpty(matchInfo.getNearMatches())) {
                            List<Match> nearMatchs = matchRepository.findByIds(matchInfo.getNearMatches());
                            List<THistoryMatch> tNearMatchs =  Lists.newArrayList();
                            for(Match nearMatch:nearMatchs){
                                tNearMatchs.add(createHistoryMatch(nearMatch,matchInfo.getCompetitorId()));
                            }
                            tMatchInfo.setNearMatches(tNearMatchs);
                        }
                tMatchInfos.add(tMatchInfo);
            }
            tMatchReview.setMatchInfos(tMatchInfos);
        }
    }

    private THistoryMatch createHistoryMatch(Match historyMatch,Long teamId) {
        THistoryMatch tHistoryMatch = new THistoryMatch();
        tHistoryMatch.setMid(historyMatch.getId());
        tHistoryMatch.setName(historyMatch.getName());
        if(CollectionUtils.isNotEmpty(historyMatch.getMultiLangNames())) {
            tHistoryMatch.setMultiLangNames(historyMatch.getMultiLangNames());
        }
        tHistoryMatch.setCsid(historyMatch.getCsid());
        tHistoryMatch.setCid(historyMatch.getCid());
        if (historyMatch.getStage() != null) {
            DictEntry dictEntry = QmtConfigDictInternalApis.getDictById(historyMatch.getStage());
            if (dictEntry != null) {
                tHistoryMatch.setStage(dictEntry.getReadableName());
                if(CollectionUtils.isNotEmpty(dictEntry.getMultiLangNames())) {
                    tHistoryMatch.setMultiLangStages(dictEntry.getMultiLangNames());
                }
            }
        }
        if (historyMatch.getRound() != null) {
            DictEntry dictEntry = QmtConfigDictInternalApis.getDictById(historyMatch.getRound());
            if (dictEntry != null) {
                tHistoryMatch.setRound(dictEntry.getReadableName());
                if(CollectionUtils.isNotEmpty(dictEntry.getMultiLangNames())) {
                    tHistoryMatch.setMultiLangRounds(dictEntry.getMultiLangNames());
                }
            }
        }
        tHistoryMatch.setStartTime(historyMatch.getStartTime());
        tHistoryMatch.setStartDate(historyMatch.getStartDate());
        Competition competition = competitionRepository.findOne(historyMatch.getCid());
        if(competition != null){
            tHistoryMatch.setAbbreviation(competition.getAbbreviation());
            if(CollectionUtils.isNotEmpty(competition.getMultiLangAbbrs())){
                tHistoryMatch.setMultiLangAbbrs(competition.getMultiLangAbbrs());
            }
        }
        Set<Match.Competitor> competitors = historyMatch.getCompetitors();
        if (CollectionUtils.isNotEmpty(competitors)) {
            List<TCompetitor> tCompetitors =  Lists.newArrayList();
            int homeScore = 0;
            int awayScore = 0;
            for (Match.Competitor competitor : competitors) {
                tCompetitors.add(matchHelper.createTCompetitor(competitor));
                if (competitor.getCompetitorId() == teamId) {
                    homeScore = LeNumberUtils.toInt(competitor.getFinalResult());
                } else {
                    awayScore = LeNumberUtils.toInt(competitor.getFinalResult());
                }
            }
            //比赛结果
            if(teamId > 0){
                if (homeScore > awayScore) {
                    tHistoryMatch.setResult(MatchResult.WIN);
                } else if (homeScore == awayScore) {
                    tHistoryMatch.setResult(MatchResult.FLAT);
                } else if (homeScore < awayScore) {
                    tHistoryMatch.setResult(MatchResult.LOSE);
                }
            }

            tHistoryMatch.setCompetitors(tCompetitors);
			if (CollectionUtils.isNotEmpty(tHistoryMatch.getCompetitors())) {
				if (LeConstants.REVERSE_HOME_AWAY_TEAMS_CIDS.contains(tHistoryMatch.getCid())) {
					Collections.sort(tHistoryMatch.getCompetitors(), new Comparator<TCompetitor>() {
						@Override
						public int compare(TCompetitor o1, TCompetitor o2) {
							return o2.getGround().ordinal() - o1.getGround().ordinal();
						}
					});
				} else {
					Collections.sort(tHistoryMatch.getCompetitors(), new Comparator<TCompetitor>() {
						@Override
						public int compare(TCompetitor o1, TCompetitor o2) {
							return o1.getGround().ordinal() - o2.getGround().ordinal();
						}
					});
				}
			}
        }

        return tHistoryMatch;
    }
}
