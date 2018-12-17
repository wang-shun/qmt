package com.lesports.qmt.sbd.converter;

import com.lesports.api.common.CallerParam;
import com.lesports.qmt.sbd.api.common.CompetitorType;
import com.lesports.qmt.sbd.model.Match;
import com.lesports.qmt.sbd.model.Player;
import com.lesports.qmt.sbd.model.Team;
import com.lesports.qmt.sbd.service.CompetitionWebService;
import com.lesports.qmt.sbd.service.PlayerWebService;
import com.lesports.qmt.sbd.service.TeamWebService;
import com.lesports.qmt.sbd.vo.CompetitionVo;
import com.lesports.qmt.sbd.vo.MatchVo;
import com.lesports.utils.CallerUtils;
import com.lesports.utils.LeDateUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Set;

/**
 * Created by lufei1 on 2016/11/10.
 */
@Component("matchVoConverter")
public class MatchVoConverter implements VoConverter<Match, MatchVo> {

    @Resource
    private CompetitionWebService competitionWebService;

    @Resource
    private TeamWebService teamWebService;

    @Resource
    private PlayerWebService playerWebService;

    @Override
    public MatchVo toTVo(Match match, CallerParam caller) {
        MatchVo matchVo = new MatchVo(match);

        String name = CallerUtils.getValueOfLanguage(match.getMultiLangNames(), caller.getLanguage());
        matchVo.setName(StringUtils.isBlank(name) ? match.getName() : name);

        String city = CallerUtils.getValueOfLanguage(match.getMultiLangCitys(), caller.getLanguage());
        matchVo.setCity(StringUtils.isBlank(city) ? match.getCity() : city);

        String judge = CallerUtils.getValueOfLanguage(match.getMultiLangJudges(), caller.getLanguage());
        matchVo.setJudge(StringUtils.isBlank(judge) ? match.getJudge() : judge);


        String venue = CallerUtils.getValueOfLanguage(match.getMultiLangVenues(), caller.getLanguage());
        matchVo.setVenue(StringUtils.isBlank(venue) ? match.getVenue() : venue);

        String weather = CallerUtils.getValueOfLanguage(match.getMultiLangWeathers(), caller.getLanguage());
        matchVo.setWeather(StringUtils.isBlank(weather) ? match.getWeather() : weather);

        matchVo.setStartTime(LeDateUtils.tansYYYYMMDDHHMMSSPretty(match.getStartTime()));

        if (CollectionUtils.isNotEmpty(match.getEids())) {
            matchVo.setEid(CallerUtils.getIdofCountryAndLanguage(match.getEids(), caller.getCountry(), caller.getLanguage()));
        }

        if (match.getStatus() != null) {
            matchVo.setMatchStatus(match.getStatus().getValue());
        }

        Set<Match.Competitor> competitors = match.getCompetitors();
        if (CollectionUtils.isNotEmpty(competitors)) {
            for (Match.Competitor competitor : competitors) {
                if (CompetitorType.TEAM.equals(competitor.getType())) {
                    Team team = teamWebService.findOne(competitor.getCompetitorId());
                    if (team != null) {
                        competitor.setCompetitorName(team.getName());
                    }
                } else if (CompetitorType.PLAYER.equals(competitor.getType())) {
                    Player player = playerWebService.findOne(competitor.getCompetitorId());
                    if (player != null) {
                        competitor.setCompetitorName(player.getName());
                    }
                }
            }
        }

        CompetitionVo competition = competitionWebService.findOne(match.getCid(), caller);
        if (competition != null) {
            matchVo.setCname(competition.getName());
            matchVo.setGameFName(competition.getName());
        }

        matchVo.setOnline(CallerUtils.isOnline(match.getOnlineLanguages(), caller.getLanguage()));
        return matchVo;
    }
}
