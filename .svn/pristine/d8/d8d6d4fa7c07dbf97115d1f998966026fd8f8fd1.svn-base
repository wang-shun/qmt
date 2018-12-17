package com.lesports.qmt.sbd.adapter;

import com.lesports.qmt.sbd.param.CompetitionSeasonParam;
import com.lesports.qmt.sbd.vo.CompetitionSeasonVo;
import com.lesports.utils.LeDateUtils;
import org.springframework.stereotype.Component;

/**
 * Created by lufei1 on 2016/11/17.
 */
@Component
public class CompetitionSeasonAdapter {

    public CompetitionSeasonVo toVo(CompetitionSeasonParam param) {
        CompetitionSeasonVo vo = new CompetitionSeasonVo();
        vo.setId(param.getId());
        vo.setCid(param.getCid());
        vo.setStartTime(LeDateUtils.tansYMDHMS2YYYYMMDDHHMMSS(param.getStartTime()));
        vo.setEndTime(LeDateUtils.tansYMDHMS2YYYYMMDDHHMMSS(param.getEndTime()));
        vo.setSeason(param.getSeason());
        vo.setOverSeason(param.getOverSeason());
        vo.setCurrentRoundId(param.getCurrentRoundId());


//        DictEntry dictEntry = QmtConfigDictInternalApis.getDictById(param.getCurrentRoundId());
//        if (dictEntry != null) {
//            Pattern p = Pattern.compile("[^0-9]");
//            Matcher m = p.matcher(dictEntry.getName());
//            Integer currentRound = Integer.valueOf(m.replaceAll(""));
//            vo.setCurrentRound(currentRound);
//            if (currentRound > rounds.size() || currentRound < 1) {
//                throw  new RuntimeException("the current round ");
//            }
//        }

        vo.setTotalRound(param.getTotalRound());
        vo.setTids(param.getTids());
        return vo;
    }
}
