package com.lesports.qmt.sbd.converter;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.lesports.api.common.CallerParam;
import com.lesports.api.common.CountryCode;
import com.lesports.qmt.config.api.dto.TTag;
import com.lesports.qmt.config.client.QmtConfigDictInternalApis;
import com.lesports.qmt.config.client.QmtConfigTagInternalApis;
import com.lesports.qmt.converter.TDtoConverter;
import com.lesports.qmt.config.model.Tag;
import com.lesports.qmt.sbd.api.dto.TCompetition;
import com.lesports.qmt.sbd.model.Competition;
import com.lesports.utils.CallerUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Set;

/**
 * Created by ruiyuansheng on 2016/3/23.
 */
@Component("tCompetitionVoConverter")
public class TCompetitionVoConverter implements TDtoConverter<Competition, TCompetition> {

    @Override
    public TCompetition toDto(Competition competition) {
        Preconditions.checkNotNull(competition);
        TCompetition tCompetition = new TCompetition();
        fillTCompetition(tCompetition, competition);
        return tCompetition;
    }

    @Override
    public TCompetition adaptDto(TCompetition vo, CallerParam caller) {

        Preconditions.checkNotNull(vo);
        Preconditions.checkNotNull(caller);

        adaptVoName4Caller(vo, caller);
        adaptVoAbbr4Caller(vo, caller);
        adaptVoCont4Caller(vo, caller);
        adaptVoNation4Caller(vo, caller);
        adaptVoIntro4Caller(vo, caller);
        adaptVoIntro4Caller(vo, caller);
        adaptVoGameName4Caller(vo, caller);
        return vo;
    }

    private void adaptVoName4Caller(TCompetition vo, CallerParam caller) {
        String langName = CallerUtils.getValueOfLanguage(vo.getMultiLangNames(), caller.getLanguage());
        if (StringUtils.isNotEmpty(langName)) {
            vo.setName(langName);
        }
        vo.setMultiLangNamesIsSet(false);
    }

    private void adaptVoAbbr4Caller(TCompetition vo, CallerParam caller) {
        String langAbbr = CallerUtils.getValueOfLanguage(vo.getMultiLangAbbrs(), caller.getLanguage());
        if (StringUtils.isNotEmpty(langAbbr)) {
            vo.setAbbreviation(langAbbr);
        }
        vo.setMultiLangAbbrsIsSet(false);
    }

    private void adaptVoCont4Caller(TCompetition vo, CallerParam caller) {
        String langCont = CallerUtils.getValueOfLanguage(vo.getMultiLangConts(), caller.getLanguage());
        if (StringUtils.isNotEmpty(langCont)) {
            vo.setContinent(langCont);
        }
        vo.setMultiLangContsIsSet(false);
    }

    private void adaptVoNation4Caller(TCompetition vo, CallerParam caller) {
        String langNation = CallerUtils.getValueOfLanguage(vo.getMultiLangNations(), caller.getLanguage());
        if (StringUtils.isNotEmpty(langNation)) {
            vo.setNation(langNation);
        }
        vo.setMultiLangNationsIsSet(false);
    }

    private void adaptVoIntro4Caller(TCompetition vo, CallerParam caller) {
        String langIntro = CallerUtils.getValueOfLanguage(vo.getMultiLangIntros(), caller.getLanguage());
        if (StringUtils.isNotEmpty(langIntro)) {
            vo.setIntroduction(langIntro);
        }
        vo.setMultiLangIntrosIsSet(false);
    }

    private void adaptVoGameName4Caller(TCompetition vo, CallerParam caller) {
        String gameFName = CallerUtils.getValueOfLanguage(vo.getMultiLangGameFNames(), caller.getLanguage());
        if (StringUtils.isNotEmpty(gameFName)) {
            vo.setGameFName(gameFName);
        }
        vo.setMultiLangGameFNamesIsSet(false);

        String gameSName = CallerUtils.getValueOfLanguage(vo.getMultiLangGameSNames(), caller.getLanguage());
        if (StringUtils.isNotEmpty(gameSName)) {
            vo.setGameSName(gameSName);
        }
        vo.setMultiLangGameSNamesIsSet(false);
    }

    private void fillTCompetition(TCompetition tCompetition, Competition competition) {
        tCompetition.setId(competition.getId());
        if (competition.getGameFType() != null) {
            tCompetition.setGameFType(competition.getGameFType());
            tCompetition.setMultiLangGameFNames(QmtConfigDictInternalApis.getDictById(competition.getGameFType()).getMultiLangReadableNames());
        }
        if (competition.getGameSType() != null) {
            tCompetition.setGameSType(competition.getGameSType());
            tCompetition.setMultiLangGameSNames(QmtConfigDictInternalApis.getDictById(competition.getGameFType()).getMultiLangReadableNames());

        }
        tCompetition.setName(Strings.nullToEmpty(competition.getName()));
        if (CollectionUtils.isNotEmpty(competition.getMultiLangNames())) {
            tCompetition.setMultiLangNames(competition.getMultiLangNames());
        }
        //发现有的时候在后台添加赛事的时候不选择对阵信息,则抛出空指针
        tCompetition.setVs(competition.getVs() == null ? false : competition.getVs());
        if(competition.getLogo() != null){
            tCompetition.setLogoUrl(competition.getLogo().getUrl());
        }
        tCompetition.setMatchSystem(competition.getSystem());
        if (competition.getAbbreviation() != null) {
            tCompetition.setAbbreviation(Strings.nullToEmpty(competition.getAbbreviation()));
            if (CollectionUtils.isNotEmpty(competition.getMultiLangAbbrs())) {
                tCompetition.setMultiLangAbbrs(competition.getMultiLangAbbrs());
            }
        }
        tCompetition.setContinent(Strings.nullToEmpty(competition.getContinent()));
        if (CollectionUtils.isNotEmpty(competition.getMultiLangConts())) {
            tCompetition.setMultiLangConts(competition.getMultiLangConts());
        }
        tCompetition.setNation(Strings.nullToEmpty(competition.getNation()));
        if (CollectionUtils.isNotEmpty(competition.getMultiLangNations())) {
            tCompetition.setMultiLangNations(competition.getMultiLangNations());
        }
        tCompetition.setIntroduction(Strings.nullToEmpty(competition.getIntroduction()));
        if (CollectionUtils.isNotEmpty(competition.getMultiLangIntros())) {
            tCompetition.setMultiLangIntros(competition.getMultiLangIntros());
        }
        Set<Long> tagIds = competition.getTagIds();
        if (CollectionUtils.isNotEmpty(tagIds)) {
            for (Long tagId : tagIds) {
                Tag tag = QmtConfigTagInternalApis.getTagById(tagId);
                if (tag != null) {
                    TTag tTag = new TTag();
                    tTag.setId(tag.getId());
                    tTag.setName(tag.getName());
                    tCompetition.addToTags(tTag);
                }
            }
        }

    }
}
