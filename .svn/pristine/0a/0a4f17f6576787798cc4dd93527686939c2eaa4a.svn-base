package com.lesports.qmt.web.api.core.vo;


import com.lesports.qmt.sbd.api.dto.TCompetitorSeasonStat;
import com.lesports.qmt.sbd.api.dto.TPlayer;
import com.lesports.qmt.sbd.api.dto.TPlayerCareerStat;

import java.util.List;
import java.util.Map;

/**
 * Created by lufei1 on 2016/8/19.
 */
public class PlayerMixedVo {
    //球员基本信息
    private TPlayer player;
    //国家队or俱乐部统计
    private Map<String, PlayerTeamStat> stats;
    //篮球技术统计
    private Map<String,Object> basketballStats;


    /**
     * 球员球队统计
     */
    public static class PlayerTeamStat {
        //职业生涯统计
        private TPlayerCareerStat playerCareerStat;
        //历史数据统计
        private List<TCompetitorSeasonStat> historyStats;

        public TPlayerCareerStat getPlayerCareerStat() {
            return playerCareerStat;
        }

        public void setPlayerCareerStat(TPlayerCareerStat playerCareerStat) {
            this.playerCareerStat = playerCareerStat;
        }

        public List<TCompetitorSeasonStat> getHistoryStats() {
            return historyStats;
        }

        public void setHistoryStats(List<TCompetitorSeasonStat> historyStats) {
            this.historyStats = historyStats;
        }
    }

    public TPlayer getPlayer() {
        return player;
    }

    public void setPlayer(TPlayer player) {
        this.player = player;
    }

    public Map<String, PlayerTeamStat> getStats() {
        return stats;
    }

    public void setStats(Map<String, PlayerTeamStat> stats) {
        this.stats = stats;
    }

    public Map<String, Object> getBasketballStats() {
        return basketballStats;
    }

    public void setBasketballStats(Map<String, Object> basketballStats) {
        this.basketballStats = basketballStats;
    }
}
