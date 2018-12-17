package com.lesports.qmt.web.api.core.service.impl;

import com.google.common.collect.Lists;
import com.lesports.qmt.sbd.api.dto.TCompetitorSeasonStat;
import com.lesports.qmt.sbd.api.dto.TTeamPlayer;
import com.lesports.qmt.sbd.api.service.GetCompetitorSeasonStatsParam;
import com.lesports.qmt.sbd.client.SbdCompetitorSeasonStatApis;
import com.lesports.qmt.web.api.core.creater.CompetitionSeasonStatVoCreater;
import com.lesports.qmt.web.api.core.service.CompetitionSeasonStatService;
import com.lesports.qmt.web.api.core.vo.CompetitionSeasonStatVo;
import com.lesports.utils.math.LeNumberUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by ruiyuansheng on 2016/11/23.
 */
@Service("competitionSeasonStatService")
public class CompetitionSeasonStatServiceImpl implements CompetitionSeasonStatService {

    @Resource
    private CompetitionSeasonStatVoCreater competitionSeasonStatVoCreater;

    @Override
    public List<CompetitionSeasonStatVo> getCompetitorSeasonStats(GetCompetitorSeasonStatsParam p, Map<Long, TTeamPlayer> playerMap){
        List<CompetitionSeasonStatVo> competitionSeasonStatVos = Lists.newArrayList();
        List<TCompetitorSeasonStat> competitorSeasonStats = SbdCompetitorSeasonStatApis.getTCompetitorSeasonStats(p);
        if(CollectionUtils.isNotEmpty(competitorSeasonStats)) {
            for (TCompetitorSeasonStat competitorSeasonStat : competitorSeasonStats) {
                CompetitionSeasonStatVo competitionSeasonStatVo = competitionSeasonStatVoCreater.competitionSeasonStatVoCreater(competitorSeasonStat);
                TTeamPlayer teamPlayer = playerMap.get(competitorSeasonStat.getCompetitorId());
                competitionSeasonStatVo.setPlayerName(teamPlayer.getName());
                competitionSeasonStatVo.setPosition(teamPlayer.getPositionName());
                competitionSeasonStatVo.setLogo(teamPlayer.getLogo());
                competitionSeasonStatVo.setNumber(LeNumberUtils.toInt(teamPlayer.getNumber()));
                competitionSeasonStatVo.setNationality(teamPlayer.getNationality());
                if(teamPlayer.getPositionId() == 100125000L || 104676000L == teamPlayer.getPositionId()){
                    competitionSeasonStatVo.setIsGoalKeeper(true);
                }
                competitionSeasonStatVos.add(competitionSeasonStatVo);
            }
        }

        return competitionSeasonStatVos;

    }


}
