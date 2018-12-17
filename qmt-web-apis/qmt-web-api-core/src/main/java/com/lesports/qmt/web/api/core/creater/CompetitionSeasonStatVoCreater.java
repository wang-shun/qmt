package com.lesports.qmt.web.api.core.creater;

import com.lesports.qmt.sbd.api.dto.TCompetitorSeasonStat;
import com.lesports.qmt.web.api.core.vo.CompetitionSeasonStatVo;
import org.springframework.stereotype.Component;

/**
 * Created by ruiyuansheng on 2016/11/23.
 */
@Component("competitionSeasonStatCreater")
public class CompetitionSeasonStatVoCreater {

    public CompetitionSeasonStatVo competitionSeasonStatVoCreater(TCompetitorSeasonStat competitorSeasonStat){
        CompetitionSeasonStatVo competitionSeasonStatVo = new CompetitionSeasonStatVo();
        competitionSeasonStatVo.setId(competitorSeasonStat.getId());
        competitionSeasonStatVo.setCid(competitorSeasonStat.getCid());
        competitionSeasonStatVo.setCsid(competitorSeasonStat.getCsid());
        competitionSeasonStatVo.setPid(competitorSeasonStat.getCompetitorId());
        competitionSeasonStatVo.setStats(competitorSeasonStat.getStats());

        return competitionSeasonStatVo;


    }



}
