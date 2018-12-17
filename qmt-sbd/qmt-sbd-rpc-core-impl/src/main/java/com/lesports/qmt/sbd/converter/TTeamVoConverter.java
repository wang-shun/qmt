package com.lesports.qmt.sbd.converter;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.lesports.api.common.CallerParam;
import com.lesports.qmt.config.client.QmtConfigDictInternalApis;
import com.lesports.qmt.config.model.DictEntry;
import com.lesports.qmt.converter.TDtoConverter;
import com.lesports.qmt.sbd.api.common.Camp;
import com.lesports.qmt.sbd.api.dto.TRank;
import com.lesports.qmt.sbd.api.dto.TTeam;
import com.lesports.qmt.sbd.api.dto.TTeamPlayer;
import com.lesports.qmt.sbd.model.Player;
import com.lesports.qmt.sbd.model.Team;
import com.lesports.qmt.sbd.model.TeamSeason;
import com.lesports.qmt.sbd.repository.PlayerRepository;
import com.lesports.qmt.sbd.repository.TeamSeasonRepository;
import com.lesports.qmt.sbd.utils.CampUtil;
import com.lesports.qmt.sbd.utils.ImageUtil;
import com.lesports.utils.CallerUtils;
import com.lesports.utils.math.LeNumberUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by denghui on 2016/3/22.
 */
@Component("tTeamVoConverter")
public class TTeamVoConverter implements TDtoConverter<Team, TTeam> {



    @Resource
    private TeamSeasonRepository teamSeasonRepository;

    @Resource
    private PlayerRepository playerRepository;

    @Override
    public TTeam toDto(Team team) {
        Preconditions.checkNotNull(team);
        TTeam tTeam = new TTeam();
        fillTTeam(tTeam, team);
        return tTeam;
    }

    @Override
    public TTeam adaptDto(TTeam vo, CallerParam caller) {
        Preconditions.checkNotNull(vo);
        Preconditions.checkNotNull(caller);
        if (vo.isIsFocused()) {
            adaptVoFocused(vo, caller);
        }
        String name = CallerUtils.getValueOfLanguage(vo.getMultiLangNames(), caller.getLanguage());
        if (StringUtils.isNotEmpty(name)) {
            vo.setName(name);
        }
        String official = CallerUtils.getValueOfLanguage(vo.getMultiLangOfficialNames(), caller.getLanguage());
        if (StringUtils.isNotEmpty(official)) {
            vo.setOfficialName(official);
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
        String homeGround = CallerUtils.getValueOfLanguage(vo.getMultiLangHomeplaces(), caller.getLanguage());
        if (StringUtils.isNotEmpty(homeGround)) {
            vo.setHomeplace(homeGround);
        }
        String gameFType = CallerUtils.getValueOfLanguage(vo.getMultiLangGameFType(), caller.getLanguage());
        if (StringUtils.isNotEmpty(gameFType)) {
            vo.setGameFirstType(gameFType);
        }
        String langLogo = CallerUtils.getValueOfCountry(vo.getMultiCounLogos(), caller.getCountry());
        if (StringUtils.isNotEmpty(langLogo)) {
            vo.setLogoUrl(langLogo);
        }
		String conference = CallerUtils.getValueOfLanguage(vo.getMultiLangConferences(), caller.getLanguage());
		if (StringUtils.isNotEmpty(conference)) {
			vo.setConference(conference);
		}
		String region = CallerUtils.getValueOfLanguage(vo.getMultiLangRegions(), caller.getLanguage());
		if (StringUtils.isNotEmpty(region)) {
			vo.setRegion(region);
		}
        vo.setMultiLangConferencesIsSet(false);
        vo.setMultiLangRegionsIsSet(false);
        vo.setMultiLangGameFTypeIsSet(false);
        vo.setMultiLangNamesIsSet(false);
        vo.setMultiLangAbbrsIsSet(false);
        vo.setMultiLangCitiesIsSet(false);
        vo.setMultiLangDescIsSet(false);
        vo.setMultiLangNicknamesIsSet(false);
        vo.setMultiLangHomeplacesIsSet(false);
        vo.setMultiCounLogosIsSet(false);
        vo.setMultiLangOfficialNamesIsSet(false);
        TTeamPlayer cocah = vo.getCocah();
        if (cocah != null && cocah.getMultiLangNames()!=null) {
            String cocahName = CallerUtils.getValueOfLanguage(cocah.getMultiLangNames(), caller.getLanguage());
            if (StringUtils.isNotEmpty(cocahName)) {
                cocah.setName(cocahName);
            }
            cocah.setMultiLangNamesIsSet(false);
        }
        return vo;
    }

    private void adaptVoFocused(TTeam vo, CallerParam caller) {
        //如果可被关注,可被关注的国家列表为空,则直接返回不可被关注
        if (CollectionUtils.isEmpty(vo.getIsFocusedCountries())) {
            vo.setIsFocused(false);
        } else {
            if (vo.getIsFocusedCountries().contains(caller.getCountry())) {
                vo.setIsFocused(true);
            } else {
                vo.setIsFocused(false);
            }
        }
    }

    private void fillTTeam(TTeam tTeam, Team team) {
        tTeam.setId(team.getId());
        tTeam.setName(team.getName());
        tTeam.setMultiLangNames(team.getMultiLangNames());
        tTeam.setOfficialName(team.getName());
        tTeam.setMultiLangOfficialNames(team.getMultiLangNames());
        tTeam.setNickname(team.getNickname());
        tTeam.setMultiLangNicknames(team.getMultiLangNicknames());
        tTeam.setHomeplace(team.getHomeGround());
        tTeam.setMultiLangHomeplaces(team.getMultiLangHomeGrounds());
        tTeam.setCity(team.getCity());
        tTeam.setMultiLangCities(team.getMultiLangCities());

        if (CollectionUtils.isNotEmpty(team.getIsFocusedCountries())) {
            tTeam.setIsFocusedCountries(team.getIsFocusedCountries());
        }
        if (team.getCurrentCid() != null && team.getCurrentCid() > 0) {
            tTeam.setCurrentCid(team.getCurrentCid());
        }
        if (team.getCurrentCsid() != null && team.getCurrentCsid() > 0) {
            tTeam.setCurrentCsid(team.getCurrentCsid());
        }
        if (team.getCurrentTsid() != null && team.getCurrentTsid() > 0) {
            buildTeamWithTeamSeason(team, tTeam);
        }
        tTeam.setDesc(team.getDesc());
        tTeam.setMultiLangDesc(team.getMultiLangDesc());
        if(team.getLogo() != null){
            if(team.getLogo() != null){
                tTeam.setLogoUrl(ImageUtil.addLogoSuffix(team.getLogo().getUrl(), team.getLogo().getPath()));
            }
        }
        if(CollectionUtils.isNotEmpty(team.getMultiCounLogos())) {
            tTeam.setMultiCounLogos(team.getMultiCounLogos());
        }
        if (team.getGameFType() != null && team.getGameFType() > 0) {
            DictEntry dictEntry = QmtConfigDictInternalApis.getDictById(team.getGameFType());
            if (dictEntry != null) {
                tTeam.setGameFirstType(dictEntry.getReadableName());
                tTeam.setGameFTypeId(team.getGameFType());
                tTeam.setMultiLangGameFType(dictEntry.getMultiLangNames());
            }
        }
        if (team.getTeamType() != null) {
            tTeam.setTeamType(team.getTeamType().ordinal());
        }
        if(team.getBgLogo() != null){
            tTeam.setBgWebUrl(team.getBgLogo().getUrl());
        }
        if(team.getSuperLogo() != null){
            tTeam.setPngLogo(team.getSuperLogo().getUrl());
        }

        if (team.getConference() != null && team.getConference() > 0) {
            DictEntry dictEntry = QmtConfigDictInternalApis.getDictById(team.getConference());
			if (dictEntry != null) {
				tTeam.setConference(dictEntry.getReadableName());
				tTeam.setConferenceId(team.getConference());
				tTeam.setMultiLangConferences(dictEntry.getMultiLangNames());
			}
        }
        if (team.getRegion() != null && team.getRegion() > 0) {
            DictEntry dictEntry = QmtConfigDictInternalApis.getDictById(team.getRegion());
			if (dictEntry != null) {
				tTeam.setRegion(dictEntry.getReadableName());
				tTeam.setRegionId(team.getRegion());
				tTeam.setMultiLangRegions(dictEntry.getMultiLangNames());
			}
        }

        if (CollectionUtils.isNotEmpty(team.getHonors())) {
            tTeam.setHonors(team.getHonors());
        }
        List<Team.Rank> ranks = team.getRanks();
        if (CollectionUtils.isNotEmpty(ranks)) {
            List<TRank> tRanks = Lists.newArrayList();
            for (Team.Rank rank : ranks) {
                TRank tRank = new TRank();
                if (StringUtils.isNotBlank(rank.getTime()) && rank.getRank() > 0) {
                    tRank.setTime(rank.getTime());
                    tRank.setRank(rank.getRank());
                    tRanks.add(tRank);
                }
            }
            if (CollectionUtils.isNotEmpty(tRanks)) {
                tTeam.setRanks(tRanks);
            }
        }
        //阵营信息
        String campId = team.getCampId();
        if (StringUtils.isNotBlank(campId)) {
            com.lesports.utils.pojo.Camp camp = CampUtil.getCamp(campId);
            //如果有阵营信息则填充阵营
            if (camp != null) {
                Camp tCamp = new Camp();
                tCamp.setId(camp.getId());
                tCamp.setName(camp.getName());
                tCamp.setPicture(camp.getPicture());
                tTeam.setCampEntity(tCamp);
            }
        }

        tTeam.setChampionNum(team.getChampionshipNum()+"");
    }

    private void buildTeamWithTeamSeason(Team team, TTeam tTeam) {
        TeamSeason teamSeason = teamSeasonRepository.findOne(team.getCurrentTsid());
        if (teamSeason == null) {
            return;
        }
        tTeam.setCurrentTsid(team.getCurrentTsid());

        if (teamSeason.getCurrentCaptain() != null && teamSeason.getCurrentCaptain() > 0) {
            TTeamPlayer captain = new TTeamPlayer();
            Player captainPlayer = playerRepository.findOne(teamSeason.getCurrentCaptain());
            if (null != captainPlayer) {
                if(captain.getLogo() != null){
                    captain.setLogo(captainPlayer.getImage().getUrl());
                }
                captain.setPid(captainPlayer.getId());
                captain.setName(captainPlayer.getName());
            }
            tTeam.setCaptain(captain);
        }
        if (CollectionUtils.isNotEmpty(teamSeason.getCorePlayers())) {
            List<TTeamPlayer> corePlayers = Lists.newArrayList();
            for (Long id : teamSeason.getCorePlayers()) {
                TTeamPlayer tTeamPlayer = new TTeamPlayer();
                Player player = playerRepository.findOne(id);
                if (null != player) {
                    if(player.getImage() != null){
                        tTeamPlayer.setLogo(player.getImage().getUrl());
                    }
                    tTeamPlayer.setPid(player.getId());
                    tTeamPlayer.setName(player.getName());
                    corePlayers.add(tTeamPlayer);
                }
            }
            tTeam.setCorePlayers(corePlayers);
        }
    }
}
