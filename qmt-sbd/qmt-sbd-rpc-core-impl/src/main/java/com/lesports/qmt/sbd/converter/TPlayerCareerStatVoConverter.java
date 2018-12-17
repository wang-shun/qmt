package com.lesports.qmt.sbd.converter;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import com.lesports.api.common.CallerParam;
import com.lesports.qmt.converter.TDtoConverter;
import com.lesports.qmt.sbd.api.common.CareerScopeType;
import com.lesports.qmt.sbd.api.dto.TPlayerCareerStat;
import com.lesports.qmt.sbd.model.Player;
import com.lesports.qmt.sbd.model.PlayerCareerStat;
import com.lesports.qmt.sbd.model.Team;
import com.lesports.qmt.sbd.repository.PlayerCareerStatRepository;
import com.lesports.qmt.sbd.repository.PlayerRepository;
import com.lesports.qmt.sbd.repository.TeamRepository;
import com.lesports.qmt.sbd.utils.ImageUtil;
import com.lesports.utils.CallerUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Created by lufei on 2016/8/16.
 */
@Component("tPlayerCareerStatVoConverter")
public class TPlayerCareerStatVoConverter implements TDtoConverter<PlayerCareerStat, TPlayerCareerStat> {

    @Resource
    private PlayerCareerStatRepository playerCareerStatRepository;

    @Resource
    private PlayerRepository playerRepository;

    @Resource
    private TeamRepository teamRepository;


    @Override
    public TPlayerCareerStat toDto(PlayerCareerStat playerCareerStat) {
        Preconditions.checkNotNull(playerCareerStat);
        TPlayerCareerStat tPlayerCareerStat = new TPlayerCareerStat();
        fillTPlayerCareerStat(tPlayerCareerStat, playerCareerStat);
        return tPlayerCareerStat;
    }

    @Override
    public TPlayerCareerStat adaptDto(TPlayerCareerStat vo, CallerParam caller) {
        Preconditions.checkNotNull(vo);
        Preconditions.checkNotNull(caller);
        Player player = playerRepository.findOne(vo.getPlayerId());
        Team team = teamRepository.findOne(vo.getTeamId());
        vo.setTeamLogo(ImageUtil.addLogoSuffix(team.getLogo().getUrl(), team.getLogo().getPath()));
        String playerName = CallerUtils.getValueOfLanguage(player.getMultiLangNames(), caller.getLanguage());
        if (StringUtils.isNotEmpty(playerName)) {
            vo.setName(playerName);
        }
        if(null != team){
            String teamName = CallerUtils.getValueOfLanguage(team.getMultiLangNames(), caller.getLanguage());
            if (StringUtils.isNotEmpty(teamName)) {
                vo.setTeamName(teamName);
            }
            if (0 == vo.getTeamType()) {
                vo.setTeamType(team.getTeamType() != null ? team.getTeamType().getValue() : 0);
            }
        }
        return vo;
    }

    private void fillTPlayerCareerStat(TPlayerCareerStat tPlayerCareerStat, PlayerCareerStat playerCareerStat) {
        tPlayerCareerStat.setId(playerCareerStat.getId());
        tPlayerCareerStat.setPlayerId(playerCareerStat.getPlayerId());

        if (null != playerCareerStat.getScopeType()) {
            tPlayerCareerStat.setTeamType(playerCareerStat.getScopeType().getValue());
            if(!playerCareerStat.getScopeType().equals(CareerScopeType.COMPETITION)){
                tPlayerCareerStat.setTeamId(playerCareerStat.getScopeId());
            }
        }
        Map<String, String> tStatsMap = Maps.newHashMap();
        if (MapUtils.isNotEmpty(playerCareerStat.getStats())) {
            for (Map.Entry<String, Object> entry : playerCareerStat.getStats().entrySet()) {
                tStatsMap.put(entry.getKey(), String.valueOf(entry.getValue()));
            }
        }
        tPlayerCareerStat.setStats(tStatsMap);
    }
}
