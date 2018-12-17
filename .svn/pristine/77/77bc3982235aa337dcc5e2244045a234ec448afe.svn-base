package com.lesports.qmt.sbd.converter;

import com.google.common.collect.Maps;
import com.lesports.api.common.CallerParam;
import com.lesports.qmt.sbd.model.Competition;
import com.lesports.qmt.sbd.model.CompetitionSeason;
import com.lesports.qmt.sbd.service.CompetitionWebService;
import com.lesports.qmt.sbd.service.TeamSeasonWebService;
import com.lesports.qmt.sbd.vo.CompetitionSeasonVo;
import com.lesports.qmt.sbd.vo.TeamSeasonVo;
import com.lesports.utils.CallerUtils;
import com.lesports.utils.LeDateUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by lufei1 on 2016/11/2.
 */
@Component("competitionSeasonVoConverter")
public class CompetitionSeasonVoConverter implements VoConverter<CompetitionSeason, CompetitionSeasonVo> {

    private static final Logger LOG = LoggerFactory.getLogger(TeamSeasonVoConverter.class);

    @Resource
    private CompetitionWebService competitionWebService;
    @Resource
    private TeamSeasonWebService teamSeasonWebService;

    @Override
    public CompetitionSeasonVo toTVo(CompetitionSeason competitionSeason, CallerParam caller) {
        CompetitionSeasonVo competitionSeasonVo = new CompetitionSeasonVo(competitionSeason);

        String city = CallerUtils.getValueOfLanguage(competitionSeason.getMultiLangCitys(), caller.getLanguage());
        competitionSeasonVo.setCity(StringUtils.isBlank(city) ? competitionSeason.getCity() : city);

        Competition competition = competitionWebService.findOne(competitionSeason.getCid(), caller);
        if (competition != null) {
            competitionSeasonVo.setcName(competition.getName());
        }
        competitionSeasonVo.setStartTime(LeDateUtils.tansYYYYMMDDHHMMSSPretty(competitionSeason.getStartTime()));
        competitionSeasonVo.setEndTime(LeDateUtils.tansYYYYMMDDHHMMSSPretty(competitionSeason.getEndTime()));

        Map<String, Object> params = Maps.newHashMap();
        params.put("csid", competitionSeasonVo.getId());
        List<TeamSeasonVo> teamSeasonVos = teamSeasonWebService.findByParams(params, caller);
        if (CollectionUtils.isNotEmpty(teamSeasonVos)) {
            competitionSeasonVo.setTeamSeasons(teamSeasonVos);
        }
        return competitionSeasonVo;
    }

}
