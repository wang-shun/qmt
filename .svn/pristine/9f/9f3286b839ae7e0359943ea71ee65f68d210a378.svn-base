package com.lesports.qmt.sbd.adapter;

import com.alibaba.fastjson.JSON;
import com.lesports.api.common.MatchStatus;
import com.lesports.qmt.sbd.model.Match;
import com.lesports.qmt.sbd.param.MatchParam;
import com.lesports.qmt.sbd.vo.MatchVo;
import com.lesports.utils.LeDateUtils;
import com.lesports.utils.math.LeNumberUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;

/**
 * Created by lufei1 on 2016/11/17.
 */
@Component
public class MatchAdapter {

    public MatchVo toVo(MatchParam param) {
        MatchVo vo = new MatchVo();
        vo.setId(param.getId());
        if (LeNumberUtils.toLong(param.getGameFType()) > 0) {
            vo.setGameFType(param.getGameFType());
        }
        if (StringUtils.isNotBlank(param.getName())) {
            vo.setName(param.getName());
        }
        if (LeNumberUtils.toLong(param.getCid()) > 0) {
            vo.setCid(param.getCid());
        }
        if (LeNumberUtils.toLong(param.getCsid()) > 0) {
            vo.setCsid(param.getCsid());
        }
        if (param.getLock() != null) {
            vo.setLock(param.getLock());
        }
        if (param.getVs() != null) {
            vo.setVs(param.getVs());
        }
        if (StringUtils.isNotBlank(param.getStartTime())) {
            vo.setStartTime(LeDateUtils.tansYMDHMS2YYYYMMDDHHMMSS(param.getStartTime()));
        }
        if (LeNumberUtils.toLong(param.getStage()) > 0) {
            vo.setStage(param.getStage());
        }
        if (LeNumberUtils.toLong(param.getGroup()) > 0) {
            vo.setGroup(param.getGroup());
        }
        if (LeNumberUtils.toLong(param.getRound()) > 0) {
            vo.setRound(param.getRound());
        }
        if (LeNumberUtils.toLong(param.getSubstation()) > 0) {
            vo.setSubstation(param.getSubstation());
        }
        if (StringUtils.isNotBlank(param.getCity())) {
            vo.setCity(param.getCity());
        }
        if (StringUtils.isNotBlank(param.getVenue())) {
            vo.setVenue(param.getVenue());
        }
        if (param.getStatus() != null) {
            vo.setStatus(MatchStatus.findByValue(param.getStatus()));
        }

        //参赛者
        List<Match.Competitor> competitors = JSON.parseArray(param.getCompetitors(), MatchVo.Competitor.class);
        if (CollectionUtils.isNotEmpty(competitors)) {
            vo.setCompetitors(new HashSet(competitors));
        }
        return vo;

    }
}
