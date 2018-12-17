package com.lesports.qmt.sbd.converter;

import com.lesports.api.common.CallerParam;
import com.lesports.qmt.config.client.QmtConfigDictInternalApis;
import com.lesports.qmt.config.client.QmtCountryInternalApis;
import com.lesports.qmt.config.model.Country;
import com.lesports.qmt.config.model.DictEntry;
import com.lesports.qmt.sbd.model.Team;
import com.lesports.qmt.sbd.vo.TeamVo;
import com.lesports.utils.CallerUtils;
import com.lesports.utils.math.LeNumberUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by lufei1 on 2016/11/10.
 */
@Component("teamVoConverter")
public class TeamVoConverter implements VoConverter<Team, TeamVo> {

    @Override
    public TeamVo toTVo(Team team, CallerParam caller) {
        TeamVo teamVo = new TeamVo(team);
        String name = CallerUtils.getValueOfLanguage(team.getMultiLangNames(), caller.getLanguage());
        teamVo.setName(StringUtils.isBlank(name) ? team.getName() : name);

        String abbreviation = CallerUtils.getValueOfLanguage(team.getMultiLangAbbrs(), caller.getLanguage());
        teamVo.setAbbreviation(StringUtils.isBlank(abbreviation) ? team.getAbbreviation() : abbreviation);

        String desc = CallerUtils.getValueOfLanguage(team.getMultiLangDesc(), caller.getLanguage());
        teamVo.setDesc(StringUtils.isBlank(desc) ? team.getDesc() : desc);

        String city = CallerUtils.getValueOfLanguage(team.getMultiLangCities(), caller.getLanguage());
        teamVo.setCity(StringUtils.isBlank(city) ? team.getCity() : city);

        String homeGround = CallerUtils.getValueOfLanguage(team.getMultiLangHomeGrounds(), caller.getLanguage());
        teamVo.setHomeGround(StringUtils.isBlank(homeGround) ? team.getHomeGround() : homeGround);

        List<String> honors = team.getMultiLangHonors() == null ? null : team.getMultiLangHonors().get(caller.getLanguage());
        teamVo.setHonors(CollectionUtils.isEmpty(honors) ? team.getHonors() : honors);

        DictEntry dictEntry = QmtConfigDictInternalApis.getDictById(teamVo.getGameFType());
        if (dictEntry != null) {
            teamVo.setGameFName(dictEntry.getName());
        }

        if (LeNumberUtils.toLong(team.getCountryId()) > 0) {
            Country country = QmtCountryInternalApis.getCountryById(team.getCountryId());
            if (country != null) {
                teamVo.setCountry(country.getChineseName());

            }
        }

        teamVo.setOnline(CallerUtils.isOnline(team.getOnlineLanguages(), caller.getLanguage()));
        return teamVo;
    }


}
