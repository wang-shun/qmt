package com.lesports.qmt.sbd.converter;

import com.lesports.api.common.CallerParam;
import com.lesports.qmt.config.client.QmtConfigDictInternalApis;
import com.lesports.qmt.config.client.QmtCountryInternalApis;
import com.lesports.qmt.config.model.Country;
import com.lesports.qmt.config.model.DictEntry;
import com.lesports.qmt.sbd.api.common.TeamType;
import com.lesports.qmt.sbd.model.Player;
import com.lesports.qmt.sbd.model.Team;
import com.lesports.qmt.sbd.service.TeamWebService;
import com.lesports.qmt.sbd.vo.PlayerVo;
import com.lesports.utils.CallerUtils;
import com.lesports.utils.math.LeNumberUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Created by lufei1 on 2016/11/10.
 */
@Component("playerVoConverter")
public class PlayerVoConverter implements VoConverter<Player, PlayerVo> {

    @Resource
    private TeamWebService teamWebService;

    @Override
    public PlayerVo toTVo(Player player, CallerParam caller) {
        PlayerVo playerVo = new PlayerVo(player);

        String name = CallerUtils.getValueOfLanguage(player.getMultiLangNames(), caller.getLanguage());
        playerVo.setName(StringUtils.isBlank(name) ? player.getName() : name);

        String city = CallerUtils.getValueOfLanguage(player.getMultiLangCities(), caller.getLanguage());
        playerVo.setCity(StringUtils.isBlank(city) ? player.getCity() : city);

        String area = CallerUtils.getValueOfLanguage(player.getMultiLangAreas(), caller.getLanguage());
        playerVo.setArea(StringUtils.isBlank(area) ? player.getArea() : area);

        String abbreviation = CallerUtils.getValueOfLanguage(player.getMultiLangAbbrs(), caller.getLanguage());
        playerVo.setAbbreviation(StringUtils.isBlank(abbreviation) ? player.getAbbreviation() : abbreviation);

        if (LeNumberUtils.toLong(player.getCountryId()) > 0) {
            Country country = QmtCountryInternalApis.getCountryById(player.getCountryId());
            if (country != null) {
                playerVo.setCountry(country.getChineseName());

            }
        }

        DictEntry dictEntry = QmtConfigDictInternalApis.getDictById(player.getGameFType());
        if (dictEntry != null) {
            playerVo.setGameFName(dictEntry.getName());
        }

        //俱乐部号码
        PlayerVo.PlayingTeam clubPlayingTeam = new PlayerVo.PlayingTeam();
        Map<TeamType, Player.PlayingTeam> playingTeamMap = player.getTeamNumber();
        if (MapUtils.isNotEmpty(playingTeamMap)) {
            Player.PlayingTeam playingTeam = playingTeamMap.get(TeamType.CLUB_TEAM);
            if (playingTeam != null) {
                clubPlayingTeam.setTeamId(playingTeam.getTeamId());
                clubPlayingTeam.setNumber(playingTeam.getNumber());
                Team team = teamWebService.findOne(playingTeam.getTeamId(), caller);
                if (team != null) {
                    clubPlayingTeam.setName(team.getName());
                }
            }
        }
        playerVo.setClubPlayingTeam(clubPlayingTeam);

        if (CollectionUtils.isNotEmpty(player.getCareer())) {
            for (Player.CareerItem careerItem : player.getCareer()) {
                Team team = teamWebService.findOne(careerItem.getTeamId(), caller);
                if (team != null) {
                    careerItem.setTeamName(team.getName());
                }
            }
        }

        playerVo.setOnline(CallerUtils.isOnline(player.getOnlineLanguages(), caller.getLanguage()));
        return playerVo;
    }

}
