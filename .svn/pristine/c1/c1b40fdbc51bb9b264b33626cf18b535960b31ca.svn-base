package com.lesports.qmt.sbd.converter;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.lesports.api.common.CallerParam;
import com.lesports.qmt.config.client.QmtConfigDictInternalApis;
import com.lesports.qmt.config.client.QmtCountryInternalApis;
import com.lesports.qmt.config.model.Country;
import com.lesports.qmt.config.model.DictEntry;
import com.lesports.qmt.converter.TDtoConverter;
import com.lesports.qmt.sbd.api.dto.PlayingTeam;
import com.lesports.qmt.sbd.api.dto.TPlayer;
import com.lesports.qmt.sbd.api.dto.TSimplePlayer;
import com.lesports.qmt.sbd.model.MatchStats;
import com.lesports.qmt.sbd.model.Player;
import com.lesports.qmt.sbd.model.Team;
import com.lesports.qmt.sbd.repository.PlayerRepository;
import com.lesports.qmt.sbd.repository.TeamRepository;
import com.lesports.qmt.sbd.utils.ImageUtil;
import com.lesports.utils.CallerUtils;
import com.lesports.utils.math.LeNumberUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by denghui on 2016/3/22.
 */
@Component("tPlayerVoConverter")
public class TPlayerVoConverter implements TDtoConverter<Player, TPlayer> {
    @Resource
    private PlayerRepository playerRepository;
	@Resource
	private TeamRepository teamRepository;

	@Override
    public TPlayer toDto(Player player) {
        Preconditions.checkNotNull(player);
        TPlayer tPlayer = new TPlayer();
        fillTPlayer(tPlayer, player);
        return tPlayer;
    }

    @Override
    public TPlayer adaptDto(TPlayer vo, CallerParam caller) {
        Preconditions.checkNotNull(vo);
        Preconditions.checkNotNull(caller);
        String name = CallerUtils.getValueOfLanguage(vo.getMultiLangNames(), caller.getLanguage());
        if (StringUtils.isNotEmpty(name)) {
            vo.setName(name);
        }
        String nationality = CallerUtils.getValueOfLanguage(vo.getMultiLangNationalities(), caller.getLanguage());
        if (StringUtils.isNotEmpty(nationality)) {
            vo.setNationality(nationality);
        }
		String position = CallerUtils.getValueOfLanguage(vo.getMultiLangPositionNames(), caller.getLanguage());
		if (StringUtils.isNotEmpty(position)) {
			vo.setPosition(position);
		}
        String city = CallerUtils.getValueOfLanguage(vo.getMultiLangCities(), caller.getLanguage());
        if (StringUtils.isNotEmpty(city)) {
            vo.setCity(city);
        }
        String desc = CallerUtils.getValueOfLanguage(vo.getMultiLangDesc(), caller.getLanguage());
        if (StringUtils.isNotEmpty(desc)) {
            vo.setDesc(desc);
        }
        String nickname = CallerUtils.getValueOfLanguage(vo.getMultiLangNicknames(), caller.getLanguage());
        if (StringUtils.isNotEmpty(nickname)) {
            vo.setNickname(nickname);
        }
		String langGameFtype = CallerUtils.getValueOfLanguage(vo.getMultiLangGameFType(), caller.getLanguage());
		if(StringUtils.isNotEmpty(langGameFtype)){
			vo.setGameFType(langGameFtype);
		}
        List<PlayingTeam> playingTeams =  vo.getCareerTeams();
        if(CollectionUtils.isNotEmpty(playingTeams)){
            for(PlayingTeam playingTeam : playingTeams){
                String teamLogo = CallerUtils.getValueOfCountry(playingTeam.getMultiCounLogos(), caller.getCountry());
                if(StringUtils.isNotEmpty(teamLogo)){
                    playingTeam.setLogo(teamLogo);
                }
                String teamName = CallerUtils.getValueOfLanguage(playingTeam.getMultiLangNames(), caller.getLanguage());
                if(StringUtils.isNotEmpty(teamName)){
                    playingTeam.setName(teamName);
                }

                playingTeam.setMultiCounLogosIsSet(false);
                playingTeam.setMultiLangNamesIsSet(false);

            }
        }
		vo.setMultiLangGameFTypeIsSet(false);
        vo.setMultiLangNamesIsSet(false);
        vo.setMultiLangNationalitiesIsSet(false);
        vo.setMultiLangCitiesIsSet(false);
        vo.setMultiLangDescIsSet(false);
        vo.setMultiLangNicknamesIsSet(false);
		vo.setMultiLangPositionNamesIsSet(false);
        return vo;
    }

    private void fillTPlayer(TPlayer tPlayer, Player player) {
        tPlayer.setId(player.getId());
        tPlayer.setName(player.getName());
        tPlayer.setMultiLangNames(player.getMultiLangNames());
//        tPlayer.setNickname(player.getNickName());
//        tPlayer.setMultiLangNicknames(player.getMultiLangNicknames());
        if (player.getHeight() != null) {
            tPlayer.setHeight(player.getHeight());
        }
        if (player.getWeight() != null) {
            tPlayer.setWeight(player.getWeight());
        }
        tPlayer.setBirthday(player.getBirthday());
        if (player.getGender() != null) {
            tPlayer.setGender(player.getGender().ordinal());
        }
		Country nationality = null;
		if (player.getCountryId() != null) {
			nationality = QmtCountryInternalApis.getCountryById(player.getCountryId());
		}
        if(nationality != null){
            tPlayer.setNationality(nationality.getChineseName());
            tPlayer.setMultiLangNationalities(nationality.getMultiLangNames());
        }

        tPlayer.setCity(player.getCity());
        tPlayer.setMultiLangCities(player.getMultiLangCities());
        if(player.getImage() != null){
            tPlayer.setImageUrl(ImageUtil.addLogoSuffix(player.getImage().getUrl(), player.getImage().getPath()));
        }
        DictEntry dictEntry = QmtConfigDictInternalApis.getDictById(player.getGameFType());
        if (dictEntry != null) {
			tPlayer.setGameFTypeId(player.getGameFType());
            tPlayer.setGameFType(dictEntry.getReadableName());
			if (CollectionUtils.isNotEmpty(dictEntry.getMultiLangNames())) {
				tPlayer.setMultiLangGameFType(dictEntry.getMultiLangReadableNames());
			}
        }
        if(tPlayer.getBgWebUrl() != null){
            tPlayer.setBgWebUrl(player.getBgImage().getUrl());
        }

		if (LeNumberUtils.toInt(player.getPosition()) != 0) {
			tPlayer.setPositionId(player.getPosition());
            DictEntry entry = QmtConfigDictInternalApis.getDictById(player.getPosition());
			if (entry != null) {
				tPlayer.setPosition(entry.getReadableName());
				if (CollectionUtils.isNotEmpty(entry.getMultiLangNames())) {
					tPlayer.setMultiLangPositionNames(entry.getMultiLangReadableNames());
				}
			}
		}
		tPlayer.setCareerValue(player.getCareerValue());
		tPlayer.setMultiLangCareerValue(player.getMultiLangCareerValue());
		Map<com.lesports.qmt.sbd.api.common.TeamType, Player.PlayingTeam> teamNumber = player.getTeamNumber();
		if (MapUtils.isNotEmpty(teamNumber)) {
			for(Map.Entry<com.lesports.qmt.sbd.api.common.TeamType, Player.PlayingTeam> entry : teamNumber.entrySet()){
                Player.PlayingTeam playingTeam = entry.getValue();
                com.lesports.qmt.sbd.api.common.TeamType teamType = entry.getKey();
				if (playingTeam != null && playingTeam.getTeamId() != null &&  playingTeam.getTeamId() > 0) {
					PlayingTeam tPlayingTeam = fillPlayingTeam(playingTeam.getTeamId());
					if (tPlayingTeam != null) {
						tPlayingTeam.setNumber(playingTeam.getNumber());
						if (teamType == com.lesports.qmt.sbd.api.common.TeamType.CLUB_TEAM) {
							tPlayer.setClubTeam(tPlayingTeam);
						} else if (teamType == com.lesports.qmt.sbd.api.common.TeamType.NATIONAL_TEAM) {
							tPlayer.setNationalTeam(tPlayingTeam);
						}
					}
				}
			}
		}
		List<Player.CareerItem> careers = player.getCareer();
		if (CollectionUtils.isNotEmpty(careers)) {
			List<PlayingTeam> careersTeams = Lists.newArrayList();
			Set<Long> teamIdSet = Sets.newHashSet();
			for (Player.CareerItem career : careers) {
				Long teamId = career.getTeamId();
				if (teamId > 0) {
					PlayingTeam playingTeam = fillPlayingTeam(teamId);
					if (playingTeam != null && !teamIdSet.contains(teamId)) {
						teamIdSet.add(teamId);
						careersTeams.add(playingTeam);
					}
				}
			}
			if (CollectionUtils.isNotEmpty(careersTeams)) {
				tPlayer.setCareerTeams(careersTeams);
			}
		}
        tPlayer.setExperience(player.getExperience());
        tPlayer.setDraft(player.getDraft());
        tPlayer.setMultiLangDraft(player.getMultiLangDraftValue());
        tPlayer.setSalary(player.getSalary());
        if(StringUtils.isNotBlank(player.getSchool()) && !"none".equals(player.getSchool().toLowerCase())){
            tPlayer.setSchool(player.getSchool());
        }
	}

	private PlayingTeam fillPlayingTeam(long teamId) {
		if (teamId <= 0) {
			return null;
		}
		Team one = teamRepository.findOne(teamId);
		if (one == null) {
			return null;
		}
		PlayingTeam tPlayingTeam = new PlayingTeam();
		tPlayingTeam.setTeamId(teamId);
		tPlayingTeam.setName(one.getName());
        if(CollectionUtils.isNotEmpty(one.getMultiLangNames())) {
            tPlayingTeam.setMultiLangNames(one.getMultiLangNames());
        }
        if(one.getLogo() != null){
            tPlayingTeam.setLogo(one.getLogo().getUrl());
        }

        if(CollectionUtils.isNotEmpty(one.getMultiCounLogos())) {
            tPlayingTeam.setMultiCounLogos(one.getMultiCounLogos());
        }
		if (one.getTeamType() != null) {
			tPlayingTeam.setTeamType(one.getTeamType().ordinal());
		}
		return tPlayingTeam;
	}

    public void fillTSimplePlayer(TSimplePlayer tPlayer, MatchStats.SimplePlayer player) {
        tPlayer.setId(player.getPid());
        Player p = playerRepository.findOne(player.getPid());
        tPlayer.setName(p == null ? null : p.getName());
        tPlayer.setStarting(player.getStarting() == null ? false : player.getStarting());
        tPlayer.setPosition("" + player.getPosition());
        tPlayer.setNumber(player.getNumber() == null ? 0 : player.getNumber());
        tPlayer.setDnp(player.getDnp() == null ? 0 : player.getDnp());
        tPlayer.setSquadOrder(NumberUtils.toInt(player.getSquadOrder()));
    }
}
