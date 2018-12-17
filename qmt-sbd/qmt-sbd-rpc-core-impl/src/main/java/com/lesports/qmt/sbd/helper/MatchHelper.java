package com.lesports.qmt.sbd.helper;

import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import com.lesports.qmt.config.client.QmtConfigDictInternalApis;
import com.lesports.qmt.config.client.QmtCountryInternalApis;
import com.lesports.qmt.config.model.Country;
import com.lesports.qmt.config.model.DictEntry;
import com.lesports.qmt.sbd.api.common.Camp;
import com.lesports.qmt.sbd.api.common.CompetitorType;
import com.lesports.qmt.sbd.api.dto.*;
import com.lesports.qmt.sbd.model.Match;
import com.lesports.qmt.sbd.model.MatchStats;
import com.lesports.qmt.sbd.model.Player;
import com.lesports.qmt.sbd.model.Team;
import com.lesports.qmt.sbd.repository.PlayerRepository;
import com.lesports.qmt.sbd.repository.TeamRepository;
import com.lesports.qmt.sbd.utils.CampUtil;
import com.lesports.qmt.sbd.utils.ImageUtil;
import com.lesports.utils.CallerUtils;
import com.lesports.utils.math.LeNumberUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Created by ruiyuansheng on 2016/3/22.
 */
@Component("matchHelper")
public class MatchHelper {
    private static final Logger LOG = LoggerFactory.getLogger(MatchHelper.class);

    @Resource
    private TeamRepository teamRepository;

    @Resource
    private PlayerRepository playerRepository;


    public TCompetitor createTCompetitor(Match.Competitor competitor) {
        TCompetitor tCompetitor = new TCompetitor();
        tCompetitor.setId(LeNumberUtils.toLong(competitor.getCompetitorId()));
        tCompetitor.setGround(competitor.getGround());
        tCompetitor.setFinalResult(competitor.getFinalResult());


        if (CollectionUtils.isNotEmpty(competitor.getSectionResults())) {
            for (Match.SectionResult result : competitor.getSectionResults()) {
                tCompetitor.addToSectionResults(createTSectionResult(result));
            }
        }
        tCompetitor.setOrder(LeNumberUtils.toInt(competitor.getOrder()));
        tCompetitor.setCompetitorType(competitor.getType());
        if (MapUtils.isNotEmpty(competitor.getExtendInfos())) {
            for (Map.Entry<String, Object> entry : competitor.getExtendInfos().entrySet()) {
                tCompetitor.putToExtendInfos(entry.getKey(), null == entry.getValue() ? "" : String.valueOf(entry.getValue()));
            }
        }
        switch (competitor.getType()) {
            case PLAYER:
                Player player = playerRepository.findOne(competitor.getCompetitorId());
                if (player != null) {
                    if(player.getImage() != null){
                        tCompetitor.setLogoUrl(ImageUtil.addLogoSuffix(player.getImage().getUrl(), player.getImage().getPath()));
                    }
                    tCompetitor.setName(Strings.nullToEmpty(player.getName()));
                    if (CollectionUtils.isNotEmpty(player.getMultiLangNames())) {
                        tCompetitor.setMultiLangNames(player.getMultiLangNames());
                    }
                    if(player.getBgImage() != null){
                        tCompetitor.setBgWebUrl(player.getBgImage().getUrl());
                    }
                } else {
                    LOG.error("this player is null! playerId = {}", competitor.getCompetitorId());
                }
                break;
            case TEAM:
                Team team = teamRepository.findOne(competitor.getCompetitorId());
                if (team != null) {
                    if(team.getLogo() != null){
                        tCompetitor.setLogoUrl(ImageUtil.addLogoSuffix(team.getLogo().getUrl(), team.getLogo().getPath()));
                    }
                    if(CollectionUtils.isNotEmpty(team.getMultiCounLogos())){
                        tCompetitor.setMultiCounLogos(team.getMultiCounLogos());
                    }
                    tCompetitor.setName(Strings.nullToEmpty(team.getName()));
                    if (CollectionUtils.isNotEmpty(team.getMultiLangNames())) {
                        tCompetitor.setMultiLangNames(team.getMultiLangNames());
                    }
					//球队简称
					tCompetitor.setOfficialName(Strings.nullToEmpty(team.getName()));
					if (CollectionUtils.isNotEmpty(team.getMultiLangNames())) {
						tCompetitor.setMultiLangOfficialNames(team.getMultiLangNames());
					}
                    if(team.getBgLogo() != null){
                        tCompetitor.setBgWebUrl(team.getBgLogo().getUrl());
                    }
                    if(team.getSuperLogo() != null){
                        tCompetitor.setPngLogo(team.getSuperLogo().getUrl());
                    }

					tCompetitor.setIsFocusedCountries(team.getIsFocusedCountries());

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
							tCompetitor.setCampEntity(tCamp);
						}
					}
                } else {
                    LOG.error("this team is null! teamId = {}", competitor.getCompetitorId());
                }
        }
        return tCompetitor;
    }

    public TSectionResult createTSectionResult(Match.SectionResult result) {
        TSectionResult tResult = new TSectionResult();
        tResult.setOrder(result.getOrder());
        if (result.getSection() != null) {
            tResult.setSectionId(result.getSection());
            DictEntry dictEntry = QmtConfigDictInternalApis.getDictById(result.getSection());
			if (null != dictEntry) {
				tResult.setSection(dictEntry.getReadableName());
				if (CollectionUtils.isNotEmpty(dictEntry.getMultiLangNames())) {
					tResult.setMultiLangSection(dictEntry.getMultiLangReadableNames());
				}
			}
        }
        tResult.setResult(result.getResult());
        return tResult;
    }

    public TCompetitorStat createTCompetitorStat(MatchStats.CompetitorStat stat) {
        TCompetitorStat tStat = new TCompetitorStat();
        tStat.setId(stat.getCompetitorId());
        tStat.setCompetitorType(stat.getCompetitorType());
        if (stat.getCompetitorType() != null && stat.getCompetitorType().equals(CompetitorType.PLAYER)) {
            Player player = playerRepository.findOne(stat.getCompetitorId());
            if (player != null) {
                tStat.setName(Strings.nullToEmpty(player.getName()));
                if (CollectionUtils.isNotEmpty(player.getMultiLangNames())) {
                    tStat.setMultiLangNames(player.getMultiLangNames());
                }
                Team team = teamRepository.findOne(stat.getTeamId());
                if (team != null) {
                    tStat.setTid(stat.getTeamId());
                    tStat.setTeamName(Strings.nullToEmpty(team.getName()));
                    if (CollectionUtils.isNotEmpty(team.getMultiLangNames())) {
                        tStat.setMultiLangTeamNames(team.getMultiLangNames());
                    }
                }
            }
        }
        if (stat.getCompetitorType() != null && stat.getCompetitorType().equals(CompetitorType.TEAM)) {
            Team team = teamRepository.findOne(stat.getCompetitorId());
            if (team != null) {
                tStat.setName(Strings.nullToEmpty(team.getName()));
                if (CollectionUtils.isNotEmpty(team.getMultiLangNames())) {
                    tStat.setMultiLangNames(team.getMultiLangNames());
                }
                tStat.setTid(stat.getCompetitorId());
                tStat.setTeamName(Strings.nullToEmpty(team.getName()));
                if (CollectionUtils.isNotEmpty(team.getMultiLangNames())) {
                    tStat.setMultiLangTeamNames(team.getMultiLangNames());
                }
            }
        }
        if (stat.getShowOrder() != null) {
            tStat.setOrder(stat.getShowOrder());
        }
        if (MapUtils.isNotEmpty(stat.getStats())) {
            for (Map.Entry<String, Object> entry : stat.getStats().entrySet()) {
                tStat.putToStats(entry.getKey(), null == entry.getValue() ? "" : String.valueOf(entry.getValue()));
            }
        }
//        if (CollectionUtils.isNotEmpty(stat.getSectionStats())) {
//            for (Match.SectionStat sectionStat : stat.getSectionStats()) {
//                tStat.addToSectionStats(createTCompetitorSectionStat(sectionStat));
//            }
//        }
        return tStat;
    }



    public TCurrentMoment createTCurrentMoment(Match.CurrentMoment currentMoment) {
        TCurrentMoment tCurrentMoment = new TCurrentMoment();
        DictEntry dictEntry = QmtConfigDictInternalApis.getDictById(currentMoment.getSection());
        if(dictEntry != null){
            tCurrentMoment.setSectionName(dictEntry.getName());
            if(CollectionUtils.isNotEmpty(dictEntry.getMultiLangNames())){
                tCurrentMoment.setMultiLangSectionNames(dictEntry.getMultiLangNames());
            }
        }

        tCurrentMoment.setTime(currentMoment.getTime());
        tCurrentMoment.setSort(currentMoment.getSort());
        return tCurrentMoment;
    }

    public TSquad createTSquad(MatchStats.Squad squad) {
        TSquad tSquad = new TSquad();
        tSquad.setTid(squad.getTid());
        //阵型
        tSquad.setFormation(squad.getFormation());
        if (CollectionUtils.isNotEmpty(squad.getPlayers())) {
            for (MatchStats.SimplePlayer simplePlayer : squad.getPlayers()) {
                tSquad.addToPlayers(createSimplePlayer(simplePlayer));
            }
        }
        if (CollectionUtils.isNotEmpty(squad.getCoaches())) { //教练
            for (MatchStats.SimpleCoach simpleCoach : squad.getCoaches()) {
                tSquad.addToCoaches(createSimpleCoach(simpleCoach));
            }
        }
        return tSquad;
    }


    public TSquad createBestPlayerStat(MatchStats.BestPlayerStat bestPlayerStat) {
        TSquad tSquad = new TSquad();
        tSquad.setTid(bestPlayerStat.getTid());
        if (CollectionUtils.isNotEmpty(bestPlayerStat.getPlayers())) {
            for (MatchStats.SimplePlayer simplePlayer : bestPlayerStat.getPlayers()) {
                tSquad.addToPlayers(createSimplePlayer(simplePlayer));
            }
        }
        return tSquad;
    }

    private TSimpleCoach createSimpleCoach(MatchStats.SimpleCoach simpleCoach) {
        Player player = playerRepository.findOne(simpleCoach.getCoachId());
        TSimpleCoach tSimpleCoach = new TSimpleCoach();
        if (player != null) {
            tSimpleCoach.setId(simpleCoach.getCoachId());
            tSimpleCoach.setName(player.getName());
			if (CollectionUtils.isNotEmpty(player.getMultiLangNames())) {
				tSimpleCoach.setMultiLangNames(player.getMultiLangNames());
			}
            tSimpleCoach.setEnglishName(player.getEnglishName());
            tSimpleCoach.setCountryId(player.getCountryId());
            if (player.getCountryId() != null && player.getCountryId() > 0) {
                Country country = QmtCountryInternalApis.getCountryById(player.getCountryId());
                if (country != null) {
                    tSimpleCoach.setCountryImgUrl(country.getNationalFlag() + ImageUtil.OLY_PNG_SUFFIX);
                }
            }
            if(player.getImage() != null){
                tSimpleCoach.setImageUrl(player.getImage().getUrl());
            }
            tSimpleCoach.setCoachType(simpleCoach.getType() == null ? null : CoachType.findByValue(simpleCoach.getType().getValue()));
            if (MapUtils.isNotEmpty(simpleCoach.getExtendInfos())) {
                for (Map.Entry<String, Object> entry : simpleCoach.getExtendInfos().entrySet()) {
                    tSimpleCoach.putToExtendInfos(entry.getKey(), null == entry.getValue() ? "" : String.valueOf(entry.getValue()));
                }
            }
        }
        return tSimpleCoach;
    }

    private TSimplePlayer createSimplePlayer(MatchStats.SimplePlayer simplePlayer) {
        Player player = playerRepository.findOne(simplePlayer.getPid());
        TSimplePlayer tSimplePlayer = new TSimplePlayer();
        String countryName = "";
        if (player != null) {
            tSimplePlayer.setId(simplePlayer.getPid());
			tSimplePlayer.setName(player.getName());
			if (CollectionUtils.isNotEmpty(player.getMultiLangNames())) {
				tSimplePlayer.setMultiLangNames(player.getMultiLangNames());
			}
            if (player.getCountryId() != null && player.getCountryId() > 0) {
                Country country = QmtCountryInternalApis.getCountryById(player.getCountryId());
                if (country != null) {
                    tSimplePlayer.setCountryImgUrl(country.getNationalFlag() + ImageUtil.OLY_PNG_SUFFIX);
                    countryName = country.getChineseName();
                }
            }
            if (simplePlayer.getStarting() != null) {
                tSimplePlayer.setStarting(simplePlayer.getStarting());
            }
            tSimplePlayer.setNumber(LeNumberUtils.toInt(simplePlayer.getNumber()));
            if (LeNumberUtils.toInt(simplePlayer.getPosition()) != 0) {
				tSimplePlayer.setPositionId(simplePlayer.getPosition());
                DictEntry entry = QmtConfigDictInternalApis.getDictById(simplePlayer.getPosition());
                if (entry != null) {
                    tSimplePlayer.setPosition(entry.getReadableName());
					if (CollectionUtils.isNotEmpty(entry.getMultiLangNames())) {
						tSimplePlayer.setMultiLangPosition(entry.getMultiLangReadableNames());
					}
                } else {
                    LOG.error("entry is null! simplePlayerId = {}", simplePlayer.getPid());
                }
            }
            if (StringUtils.isNotBlank(simplePlayer.getSquadOrder())) {
                tSimplePlayer.setSquadOrder(LeNumberUtils.toInt(simplePlayer.getSquadOrder()));
            }
            Map<String, String> tStatsMap = Maps.newHashMap();
            if (MapUtils.isNotEmpty(simplePlayer.getStats())) {
                for (Map.Entry<String, Object> entry : simplePlayer.getStats().entrySet()) {
                    tStatsMap.put(entry.getKey(), null == entry.getValue() ? "" : String.valueOf(entry.getValue()));
                }
            }
            tSimplePlayer.setStats(tStatsMap);
            if (MapUtils.isNotEmpty(simplePlayer.getExtendInfos())) {
                for (Map.Entry<String, Object> entry : simplePlayer.getExtendInfos().entrySet()) {
                    tSimplePlayer.putToExtendInfos(entry.getKey(), null == entry.getValue() ? "" : String.valueOf(entry.getValue()));
                }
            }

            tSimplePlayer.putToExtendInfos("PN",String.valueOf(tSimplePlayer.getNumber()));//号码
            tSimplePlayer.putToExtendInfos("NAME",tSimplePlayer.getName());//名字
            tSimplePlayer.putToExtendInfos("HEIGHT",String.valueOf(LeNumberUtils.toInt(player.getHeight())));//身高
            tSimplePlayer.putToExtendInfos("WEIGHT",String.valueOf(LeNumberUtils.toInt(player.getWeight())));//体重
            tSimplePlayer.putToExtendInfos("NATIONALITY",StringUtils.isEmpty(countryName) ? "" : countryName);//国家
            tSimplePlayer.setDnp(LeNumberUtils.toInt(simplePlayer.getDnp()));
        }
        return tSimplePlayer;
    }
}
