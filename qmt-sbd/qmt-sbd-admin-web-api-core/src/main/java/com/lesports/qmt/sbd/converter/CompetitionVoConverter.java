package com.lesports.qmt.sbd.converter;

import com.lesports.api.common.CallerParam;
import com.lesports.qmt.config.client.QmtConfigDictInternalApis;
import com.lesports.qmt.config.model.DictEntry;
import com.lesports.qmt.sbd.model.Competition;
import com.lesports.qmt.sbd.vo.CompetitionVo;
import com.lesports.utils.CallerUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

/**
 * Created by lufei1 on 2016/11/9.
 */
@Component("competitionVoConverter")
public class CompetitionVoConverter implements VoConverter<Competition, CompetitionVo> {

    @Override
    public CompetitionVo toTVo(Competition competition, CallerParam caller) {
        CompetitionVo competitionVo = new CompetitionVo(competition);

        String name = CallerUtils.getValueOfLanguage(competition.getMultiLangNames(), caller.getLanguage());
        competitionVo.setName(StringUtils.isBlank(name) ? competition.getName() : name);

        String abbr = CallerUtils.getValueOfLanguage(competition.getMultiLangAbbrs(), caller.getLanguage());
        competitionVo.setAbbreviation(StringUtils.isBlank(abbr) ? competition.getAbbreviation() : abbr);

        String intro = CallerUtils.getValueOfLanguage(competition.getMultiLangIntros(), caller.getLanguage());
        competitionVo.setIntroduction(StringUtils.isBlank(intro) ? competition.getIntroduction() : intro);

        String con = CallerUtils.getValueOfLanguage(competition.getMultiLangConts(), caller.getLanguage());
        competitionVo.setContinent(StringUtils.isBlank(con) ? competition.getContinent() : con);

        String nation = CallerUtils.getValueOfLanguage(competition.getMultiLangNations(), caller.getLanguage());
        competitionVo.setNation(StringUtils.isBlank(nation) ? competition.getNation() : nation);

        DictEntry dictEntry = QmtConfigDictInternalApis.getDictById(competitionVo.getGameFType());
        if (dictEntry != null) {
            competitionVo.setGameFName(dictEntry.getName());
        }
        return competitionVo;
    }

}
