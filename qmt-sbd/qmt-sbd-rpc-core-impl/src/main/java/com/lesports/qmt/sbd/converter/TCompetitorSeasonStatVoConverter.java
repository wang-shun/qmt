package com.lesports.qmt.sbd.converter;

import com.google.common.collect.Maps;
import com.lesports.api.common.CallerParam;
import com.lesports.qmt.converter.TDtoConverter;
import com.lesports.qmt.sbd.api.dto.TCompetitorSeasonStat;
import com.lesports.qmt.sbd.model.Competition;
import com.lesports.qmt.sbd.model.CompetitionSeason;
import com.lesports.qmt.sbd.model.CompetitorSeasonStat;
import com.lesports.qmt.sbd.model.Team;
import com.lesports.qmt.sbd.repository.CompetitionRepository;
import com.lesports.qmt.sbd.repository.CompetitionSeasonRepository;
import com.lesports.qmt.sbd.repository.TeamRepository;
import com.lesports.utils.math.LeNumberUtils;
import org.apache.commons.collections.MapUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;

/**
 * User: ellios
 * Time: 16-4-13 : 下午6:10
 */
@Component("tCompetitorSeasonStatVoConverter")
public class TCompetitorSeasonStatVoConverter implements TDtoConverter<CompetitorSeasonStat, TCompetitorSeasonStat> {


    @Resource
    private CompetitionSeasonRepository competitionSeasonRepository;
    @Resource
    private CompetitionRepository competitionRepository;
    @Resource
    private TeamRepository teamRepository;

    @Override
    public TCompetitorSeasonStat toDto(CompetitorSeasonStat competitorSeasonStat) {
        TCompetitorSeasonStat tCompetitorSeasonStat = new TCompetitorSeasonStat();
        fillTCompetitorSeasonStat(tCompetitorSeasonStat, competitorSeasonStat);
        return tCompetitorSeasonStat;
    }

    @Override
    public TCompetitorSeasonStat adaptDto(TCompetitorSeasonStat vo, CallerParam caller) {
        return null;
    }

    private void fillTCompetitorSeasonStat(TCompetitorSeasonStat tCompetitorSeasonStat, CompetitorSeasonStat competitorSeasonStat) {
        tCompetitorSeasonStat.setCid(LeNumberUtils.toLong(competitorSeasonStat.getCid()));
        tCompetitorSeasonStat.setCsid(LeNumberUtils.toLong(competitorSeasonStat.getCsid()));
        Competition competition = competitionRepository.findOne(competitorSeasonStat.getCid());
        tCompetitorSeasonStat.setCName(competition.getName());
        tCompetitorSeasonStat.setCompetitorId(LeNumberUtils.toLong(competitorSeasonStat.getCompetitorId()));
        tCompetitorSeasonStat.setType(competitorSeasonStat.getType());
        tCompetitorSeasonStat.setTeamType(competitorSeasonStat.getTeamType() != null ? competitorSeasonStat.getTeamType().getValue() : -1);
        Map<String, String> tStats = Maps.newHashMap();
        Map<String, Object> status = competitorSeasonStat.getStats();
        if (MapUtils.isNotEmpty(status)) {
            for (Map.Entry<String, Object> entry : status.entrySet()) {
                if (entry.getValue() != null) {
                    tStats.put(entry.getKey(), entry.getValue().toString());
                } else {
                    tStats.put(entry.getKey(), "");
                }
            }
            tCompetitorSeasonStat.setStats(tStats);
        }

        Map<String, String> tAvgStats = Maps.newHashMap();
        Map<String, Object> avgStats = competitorSeasonStat.getAvgStats();
        if (MapUtils.isNotEmpty(avgStats)) {
            for (Map.Entry<String, Object> entry : avgStats.entrySet()) {
                if (entry.getValue() != null) {
                    tAvgStats.put(entry.getKey(), entry.getValue().toString());
                } else {
                    tAvgStats.put(entry.getKey(), "");
                }
            }
            tCompetitorSeasonStat.setAvgStats(tAvgStats);
        }

        Map<String, String>  tTopStats = Maps.newHashMap();
        Map<String, Object> topStats = competitorSeasonStat.getTopStats();
        if (MapUtils.isNotEmpty(topStats)) {
            for (Map.Entry<String, Object> entry : topStats.entrySet()) {
                if (entry.getValue() != null) {
                    tTopStats.put(entry.getKey(), entry.getValue().toString());
                } else {
                    tTopStats.put(entry.getKey(), "");
                }
            }
            tCompetitorSeasonStat.setTopStats(tTopStats);
        }

        CompetitionSeason cseason = competitionSeasonRepository.findOne(competitorSeasonStat.getCsid());
        tCompetitorSeasonStat.setSeason(cseason.getSeason());

        if(competitorSeasonStat.getTid() != null && competitorSeasonStat.getTid() > 0){
            tCompetitorSeasonStat.setTid(competitorSeasonStat.getTid());
            Team team = teamRepository.findOne(competitorSeasonStat.getTid());
            if(team != null){
                tCompetitorSeasonStat.setTeamName(team.getName());
            }
        }
    }
}
