package com.lesports.qmt.sbd.adapter;

import com.alibaba.fastjson.JSON;
import com.lesports.qmt.sbd.model.TeamSeason;
import com.lesports.qmt.sbd.param.TeamSeasonParam;
import com.lesports.qmt.sbd.vo.TeamSeasonVo;
import com.lesports.utils.LeStringUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by lufei1 on 2016/11/16.
 */
@Component
public class TeamSeasonAdapter {

    public TeamSeasonVo toVo(TeamSeasonParam param) {
        TeamSeasonVo vo = new TeamSeasonVo();
        vo.setId(param.getId());
        vo.setTid(param.getTid());
        vo.setCsid(param.getCsid());
        vo.setCoachId(param.getCoachId());
        vo.setCorePlayers(LeStringUtils.commaString2LongList(param.getCorePlayers()));
        vo.setCurrentCaptain(param.getCurrentCaptain());
        List<TeamSeason.TeamPlayer> players = JSON.parseArray(param.getPlayers(), TeamSeason.TeamPlayer.class);
        if (CollectionUtils.isNotEmpty(players)) {
            vo.setPlayers(players);
        }
        return vo;

    }
}
