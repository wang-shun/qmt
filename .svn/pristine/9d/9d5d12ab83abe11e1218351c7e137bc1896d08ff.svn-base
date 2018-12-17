package com.lesports.qmt.sbd.converter;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.lesports.api.common.CallerParam;
import com.lesports.qmt.config.client.QmtConfigDictInternalApis;
import com.lesports.qmt.converter.TDtoConverter;
import com.lesports.qmt.sbd.api.dto.TCompetitionSeason;
import com.lesports.qmt.sbd.model.Competition;
import com.lesports.qmt.sbd.model.CompetitionSeason;
import com.lesports.qmt.sbd.repository.CompetitionRepository;
import com.lesports.qmt.sbd.service.MatchService;
import com.lesports.utils.CallerUtils;
import com.lesports.utils.math.LeNumberUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Created by ruiyuansheng on 2016/3/23.
 */
@Component("tCompetitionSeasonVoConverter")
public class TCompetitionSeasonVoConverter implements TDtoConverter<CompetitionSeason, TCompetitionSeason> {


    @Resource
    private CompetitionRepository competitionRepository;


    @Resource
    private MatchService matchService;

    @Override
    public TCompetitionSeason toDto(CompetitionSeason competitionSeason) {
        Preconditions.checkNotNull(competitionSeason);
        TCompetitionSeason tcs = new TCompetitionSeason();
        fillTCompetitionSeason(tcs, competitionSeason);
        return tcs;
    }

    @Override
    public TCompetitionSeason adaptDto(TCompetitionSeason vo, CallerParam caller) {
        Preconditions.checkNotNull(vo);
        Preconditions.checkNotNull(caller);

        adaptVoName4Caller(vo,caller);
        adaptVoCity4Caller(vo, caller);
        return vo;
    }

    private void adaptVoName4Caller(TCompetitionSeason vo, CallerParam caller){
        String langName = CallerUtils.getValueOfLanguage(vo.getMultiLangNames(), caller.getLanguage());
        if(StringUtils.isNotEmpty(langName)){
            vo.setName(langName);
        }
        vo.setMultiLangNamesIsSet(false);
    }

    private void adaptVoCity4Caller(TCompetitionSeason vo, CallerParam caller){
        String langCity = CallerUtils.getValueOfLanguage(vo.getMultiLangCitys(), caller.getLanguage());
        if(StringUtils.isNotEmpty(langCity)){
            vo.setCity(langCity);
        }
        vo.setMultiLangCitysIsSet(false);
    }

    private void fillTCompetitionSeason(TCompetitionSeason tCompetitionSeason, CompetitionSeason competitionSeason) {
        Competition competition = competitionRepository.findOne(competitionSeason.getCid());
//        tCompetitionSeason.setId(competitionSeason.getId());
//        tCompetitionSeason.setName(Strings.nullToEmpty(competition.getName()));
//        if (CollectionUtils.isNotEmpty(competition.getMultiLangNames())) {
//            tCompetitionSeason.setMultiLangNames(competition.getMultiLangNames());
//        }
        tCompetitionSeason.setCid(competitionSeason.getCid());
        if (competitionSeason.getGameFType() != null) {
            tCompetitionSeason.setGameFType(competitionSeason.getGameFType());
            tCompetitionSeason.setGameFName(QmtConfigDictInternalApis.getDictById(competitionSeason.getGameFType()).getReadableName());
        }
//        if (competition.getGameSType() != null) {
//            tCompetitionSeason.setGameSType(competition.getGameSType());
//            tCompetitionSeason.setGameSName(QmtConfigDictInternalApis.getDictById(competition.getGameSType()).getReadableName());
//        }
        tCompetitionSeason.setSeason(competitionSeason.getSeason());
        if(competition.getLogo() != null){
            tCompetitionSeason.setLogoUrl(competition.getLogo().getUrl());
        }

        tCompetitionSeason.setCity(Strings.nullToEmpty(competitionSeason.getCity()));
        if (CollectionUtils.isNotEmpty(competitionSeason.getMultiLangCitys())) {
            tCompetitionSeason.setMultiLangCitys(competitionSeason.getMultiLangCitys());
        }
        if (null != competition) {
            tCompetitionSeason.setVs(LeNumberUtils.toBoolean(competition.getVs()));
        }
        tCompetitionSeason.setStartTime(competitionSeason.getStartTime());
        tCompetitionSeason.setEndTime(competitionSeason.getEndTime());
        tCompetitionSeason.setMatchSystem(competition.getSystem());
        if(null != competitionSeason.getCurrentRound()){
            tCompetitionSeason.setCurrentRound(competitionSeason.getCurrentRound());
        }else{
//            tCompetitionSeason.setCurrentRound(matchService.getMatchCurrentRound(competitionSeason.getCid(), competitionSeason.getId()));
        }
        if(null != competitionSeason.getTotalRound()){
            tCompetitionSeason.setTotalRound(competitionSeason.getTotalRound());
        }else{
//            tCompetitionSeason.setTotalRound(matchService.getMatchFullRound(competitionSeason.getCid(), competitionSeason.getId()));
        }
        if(null != competitionSeason.getCurrentRoundId()) {
            tCompetitionSeason.setCurrentRoundId(competitionSeason.getCurrentRoundId());
        }
    }
}
