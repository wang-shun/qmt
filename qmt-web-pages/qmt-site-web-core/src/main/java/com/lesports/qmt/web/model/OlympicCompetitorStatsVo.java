package com.lesports.qmt.web.model;

import java.util.List;
import java.util.Map;

/**
 * Created by ruiyuansheng on 2016/7/4.
 */
public class OlympicCompetitorStatsVo {

    private Long tid;

    private List<OlympicPlayerStats> playerStats;


    public Long getTid() {
        return tid;
    }

    public void setTid(Long tid) {
        this.tid = tid;
    }

    public List<OlympicPlayerStats> getPlayerStats() {
        return playerStats;
    }

    public void setPlayerStats(List<OlympicPlayerStats> playerStats) {
        this.playerStats = playerStats;
    }

    public static class OlympicPlayerStats{

        private Long pid;

        private Map<String,String> stats;

        public Long getPid() {
            return pid;
        }

        public void setPid(Long pid) {
            this.pid = pid;
        }

        public Map<String, String> getStats() {
            return stats;
        }

        public void setStats(Map<String, String> stats) {
            this.stats = stats;
        }
    }


}
