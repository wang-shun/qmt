package com.lesports.qmt.sbd.helper;

import com.lesports.id.api.IdType;
import com.lesports.id.client.LeIdApis;
import com.lesports.qmt.sbd.model.Competition;
import com.lesports.qmt.sbd.model.CompetitionSeason;
import com.lesports.utils.LeDateUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.Date;

/**
 * User: ellios
 * Time: 15-6-9 : 下午6:12
 */
@Component("competitionHelper")
public class CompetitionHelper {

    /**
     * 用赛事信息填充赛季信息
     *
     * @param cseason
     * @param competition
     * @return
     */
    public boolean fillSeasonWithCompetition(CompetitionSeason cseason, Competition competition) {
        Assert.notNull(competition);
        Assert.notNull(cseason);

        cseason.setGameFType(competition.getGameFType());
//        cseason.setGameSType(competition.getGameSType());
        return true;
    }

    public CompetitionSeason createSeasonWithCompetition(Competition competition) {
        Assert.notNull(competition);
        CompetitionSeason season = new CompetitionSeason();
        season.setId(LeIdApis.nextId(IdType.COMPETITION_SEASON));
        season.setCid(competition.getId());
        season.setCreateAt(competition.getCreateAt());
        season.setGameFType(competition.getGameFType());
//        season.setGameSType(competition.getGameSType());
        season.setSeason(LeDateUtils.formatYYYY(new Date()));
//        season.setLogoUrl(competition.getLogoUrl());
//        season.setLivePlatforms(Sets.newHashSet(Platform.values()));
        season.setDeleted(false);
//        season.setLivePlatforms(Sets.newHashSet(Platform.values()));
        season.setAllowCountries(competition.getAllowCountries());
        return season;
    }
}
