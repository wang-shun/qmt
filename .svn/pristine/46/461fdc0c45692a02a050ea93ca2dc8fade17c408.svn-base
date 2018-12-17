package com.lesports.qmt.web.api.core.creater;

import com.lesports.api.common.CallerParam;
import com.lesports.qmt.sbd.api.dto.TCompetitionSeason;
import com.lesports.qmt.sbd.api.dto.TTeam;
import com.lesports.qmt.sbd.api.dto.TTopList;
import com.lesports.qmt.sbd.api.dto.TTopListItem;
import com.lesports.qmt.sbd.api.service.GetSeasonTopListsParam;
import com.lesports.qmt.sbd.client.SbdCompetitonSeasonApis;
import com.lesports.qmt.sbd.client.SbdTopListApis;
import com.lesports.qmt.web.api.core.vo.TeamVo;
import com.lesports.sms.api.common.CompetitorType;
import com.lesports.utils.math.LeNumberUtils;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Created by ruiyuansheng on 2016/11/21.
 */
@Component("teamVoCreater")
public class TeamVoCreater {

    private static final Logger LOG = LoggerFactory.getLogger(TeamVoCreater.class);

    public TeamVo createrTeamVo(TTeam tTeam,CallerParam caller){

        TeamVo teamVo = new TeamVo();
        if(null != tTeam){
            teamVo.setId(tTeam.getId());
            teamVo.setName(tTeam.getName());
            teamVo.setCid(tTeam.getCurrentCid());
            teamVo.setCsid(tTeam.getCurrentCsid());
            TCompetitionSeason tCompetitionSeason = SbdCompetitonSeasonApis.getTCompetitionSeasonById(tTeam.getCurrentCsid(), caller);
            if(null != tCompetitionSeason) {
                teamVo.setCsName(tCompetitionSeason.getName());
            }
            teamVo.setLogo(tTeam.getLogoUrl());
            GetSeasonTopListsParam p = new GetSeasonTopListsParam();
            p.setCid(tTeam.getCurrentCid());
            p.setCsid(tTeam.getCurrentCsid());
            List<TTopList> tTopLists = SbdTopListApis.getSeasonTopLists(p, null, caller);
            if(CollectionUtils.isNotEmpty(tTopLists)) {
                for(TTopList topList : tTopLists){
                    List<TTopListItem> items = topList.getItems();
                    if(CollectionUtils.isNotEmpty(items)){
                        for(TTopListItem tTopListItem : items){
                            //积分榜
                            if(tTopListItem.getCompetitorType().equals(CompetitorType.TEAM) && tTopListItem.getCompetitorId() == tTeam.getId() && topList.getType() == 100162000L){
                                TeamVo.Rank rank = new TeamVo.Rank();
                                rank.setRank(tTopListItem.getRank());
                                Map<String, String> stats = tTopListItem.getStats();
                                rank.setWin(LeNumberUtils.toInt(stats.get("winMatch")));
                                rank.setFlat(LeNumberUtils.toInt(stats.get("flatMatch")));
                                rank.setLoss(LeNumberUtils.toInt(stats.get("lossMatch")));
                                teamVo.setRank(rank);
                                break;
                            }
                            //射手榜
                            if(tTopListItem.getCompetitorType().equals(CompetitorType.PLAYER) && tTopListItem.getTeamId() == tTeam.getId()  && topList.getType() == 100160000L){

                                TeamVo.Player goaler = new TeamVo.Player();
                                goaler.setPid(tTopListItem.getCompetitorId());
                                goaler.setName(tTopListItem.getCompetitorName());
                                Map<String, String> goalStats = tTopListItem.getStats();
                                goaler.setNumber(LeNumberUtils.toInt(goalStats.get("goals")));
                                goaler.setLogo(tTopListItem.getLogoUrl());
                                teamVo.setGoaler(goaler);
                                break;
                            }
                            //助攻榜
                            if(tTopListItem.getCompetitorType().equals(CompetitorType.PLAYER) && tTopListItem.getTeamId() == tTeam.getId() && topList.getType() == 100161000L){
                                TeamVo.Player assitant = new TeamVo.Player();
                                assitant.setPid(tTopListItem.getCompetitorId());
                                assitant.setName(tTopListItem.getCompetitorName());
                                Map<String, String> assistStats = tTopListItem.getStats();
                                assitant.setNumber(LeNumberUtils.toInt(assistStats.get("assists")));
                                assitant.setLogo(tTopListItem.getLogoUrl());
                                teamVo.setAssistant(assitant);
                                break;
                            }
                        }
                    }
                }
            }



        }

        return teamVo;


    }

}
