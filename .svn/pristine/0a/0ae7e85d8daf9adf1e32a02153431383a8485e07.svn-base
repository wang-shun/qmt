package com.lesports.qmt.sbd.converter;

import com.google.common.base.Preconditions;
import com.lesports.api.common.CallerParam;
import com.lesports.qmt.config.client.QmtConfigDictInternalApis;
import com.lesports.qmt.config.model.DictEntry;
import com.lesports.qmt.converter.TDtoConverter;
import com.lesports.qmt.sbd.api.common.TeamType;
import com.lesports.qmt.sbd.api.dto.TTeamPlayer;
import com.lesports.qmt.sbd.api.dto.TTeamSeason;
import com.lesports.qmt.sbd.model.Player;
import com.lesports.qmt.sbd.model.Team;
import com.lesports.qmt.sbd.model.TeamSeason;
import com.lesports.qmt.sbd.repository.PlayerRepository;
import com.lesports.qmt.sbd.repository.TeamRepository;
import com.lesports.qmt.sbd.utils.ImageUtil;
import com.lesports.qmt.sbd.utils.StringUtil;
import com.lesports.utils.CallerUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by denghui on 2016/3/22.
 */
@Component("tTeamSeasonVoConverter")
public class TTeamSeasonVoConverter implements TDtoConverter<TeamSeason, TTeamSeason> {

    private static final Logger LOG = LoggerFactory.getLogger(TTeamSeasonVoConverter.class);

    @Resource
    private PlayerRepository playerRepository;

    @Resource
    private TeamRepository teamRepository;

    @Override
    public TTeamSeason toDto(TeamSeason teamSeason) {
        Preconditions.checkNotNull(teamSeason);
        TTeamSeason tTeamSeason = new TTeamSeason();
        fillTTeamSeason(tTeamSeason, teamSeason);
        return tTeamSeason;
    }

    @Override
    public TTeamSeason adaptDto(TTeamSeason vo, CallerParam caller) {
        Preconditions.checkNotNull(vo);
        Preconditions.checkNotNull(caller);
        String cocahName = CallerUtils.getValueOfLanguage(vo.getMultiLangCocahnames(), caller.getLanguage());
        if (StringUtils.isNotEmpty(cocahName)) {
            vo.setCocahName(cocahName);
        }
        String teamName = CallerUtils.getValueOfLanguage(vo.getMultiLangTeamNames(), caller.getLanguage());
        if (StringUtils.isNotEmpty(teamName)) {
            vo.setTeamName(teamName);
        }
        for (TTeamPlayer player : vo.getPlayers()) {
            String name = CallerUtils.getValueOfLanguage(player.getMultiLangNames(), caller.getLanguage());
            if (StringUtils.isNotEmpty(name)) {
                player.setName(name);
            }
            String posName = CallerUtils.getValueOfLanguage(player.getMultiLangPositionNames(), caller.getLanguage());
            if (StringUtils.isNotEmpty(posName)) {
                player.setPositionName(posName);
            }
            player.setMultiLangNamesIsSet(false);
            player.setMultiLangPositionNamesIsSet(false);
        }
        vo.setMultiLangCocahnamesIsSet(false);
        vo.setMultiLangTeamNamesIsSet(false);
        return vo;
    }

    private void fillTTeamSeason(TTeamSeason tTeamSeason, TeamSeason teamSeason) {
        tTeamSeason.setId(teamSeason.getId());
        tTeamSeason.setCsid(teamSeason.getCsid());
        tTeamSeason.setTid(teamSeason.getTid());
        Team team = teamRepository.findOne(teamSeason.getTid());
        if(null != team){
            tTeamSeason.setTeamName(team.getName());
            tTeamSeason.setMultiLangTeamNames(team.getMultiLangNames());
            if(team.getLogo() != null){
                tTeamSeason.setTeamLogo(ImageUtil.addLogoSuffix(team.getLogo().getUrl(), team.getLogo().getPath()));
            }

        }
        if(teamSeason.getCoachId()!=null){
            Player coach = playerRepository.findOne(teamSeason.getCoachId());
            if(coach != null){
                tTeamSeason.setCocahId(teamSeason.getCoachId());
                tTeamSeason.setCocahName(coach.getName());
                tTeamSeason.setMultiLangCocahnames(coach.getMultiLangNames());
            }

        }

        Player coach = playerRepository.findOne(teamSeason.getCoachId());
        if(null != coach && coach.getImage() != null) {
            tTeamSeason.setCocahLogo(coach.getImage().getUrl());
        }
        List<TTeamPlayer> tTeamPlayers = new ArrayList<TTeamPlayer>();
        final List<TeamSeason.TeamPlayer> teamPlayers = teamSeason.getPlayers();
        if (CollectionUtils.isNotEmpty(teamPlayers)) {
            for (TeamSeason.TeamPlayer teamPlayer : teamPlayers) {
                if (null != teamPlayer) {
                    TTeamPlayer tTeamPlayer = new TTeamPlayer();
                    if (null != teamPlayer.getPid()) {
                        Player player = playerRepository.findOne(teamPlayer.getPid());
                        if(StringUtils.isNotEmpty(player.getName())) {
                            tTeamPlayer.setName(player.getName());
							tTeamPlayer.setMultiLangNames(player.getMultiLangNames());
                        }
                        tTeamPlayer.setPid(teamPlayer.getPid());
                        DictEntry entry = QmtConfigDictInternalApis.getDictById(player.getPosition());
                        if(entry != null) {
                            tTeamPlayer.setPositionName(entry.getName());
							tTeamPlayer.setMultiLangPositionNames(entry.getMultiLangNames());
                        }
//                        if(null != player.getPostionOrder()) {
//                            tTeamPlayer.setPositionOrder(teamPlayer.getPostionOrder());
//                        }else{
//                            tTeamPlayer.setPositionOrder(0);
//                        }
						if (player.getPosition() != null) {
							tTeamPlayer.setPositionId(player.getPosition());
						}
						if(null != player && player.getImage() != null) {
                            tTeamPlayer.setLogo(player.getImage().getUrl());
                        }
                        if (null != player.getTeamNumber()) {
                            for(TeamType teamType:player.getTeamNumber().keySet()){
                                if(player.getTeamNumber().get(teamType).getTeamId() == teamSeason.getTid()){
                                    tTeamPlayer.setNumber(player.getTeamNumber().get(teamType).getNumber());
                                }
                            }
                        }

                        if (StringUtil.isNotBlank(teamPlayer.getNumber())) {
                            tTeamPlayer.setNumber(Long.parseLong(teamPlayer.getNumber()));
                        }
                        tTeamPlayers.add(tTeamPlayer);
                    }
                }
            }
            try {
                Collections.sort(tTeamPlayers, new Comparator<TTeamPlayer>() {
                    @Override
                    public int compare(TTeamPlayer o1, TTeamPlayer o2) {
                        return o1.getPositionOrder() - o2.getPositionOrder();
                    }
                });
            } catch (Exception e) {
                LOG.error("Sorting teamPlayers error! tid = {}, e = {}", teamSeason.getId(), e.getMessage(), e);
            }
        }

        tTeamSeason.setPlayers(tTeamPlayers);
    }
}
