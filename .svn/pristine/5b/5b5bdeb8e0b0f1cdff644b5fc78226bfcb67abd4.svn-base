package com.lesports.qmt.web.api.core.creater;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.lesports.api.common.CallerParam;
import com.lesports.qmt.config.client.QmtConfigApis;
import com.lesports.qmt.web.api.core.vo.HistoryMatch;
import com.lesports.qmt.web.api.core.vo.MatchAction;
import com.lesports.qmt.config.api.dto.TDictEntry;
import com.lesports.qmt.sbd.api.dto.*;
import com.lesports.qmt.sbd.client.SbdPlayerApis;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by gengchengliang on 2015/10/21.
 */
@Component("matchVoCreater")
public class MatchVoCreater {

    private static final Logger LOG = LoggerFactory.getLogger(MatchVoCreater.class);

    public MatchAction createMatchAction(TMatchAction tMatchAction,CallerParam caller) {
        MatchAction matchAction = new MatchAction();
        try {
            if (tMatchAction == null) {
                return null;
            }
            matchAction.setId(tMatchAction.getId());
            matchAction.setMid(tMatchAction.getMid());
            matchAction.setTid(tMatchAction.getTid());
            matchAction.setPid(tMatchAction.getPid());
            matchAction.setPassedTime(tMatchAction.getPassedTime());
            matchAction.setType(tMatchAction.getType());
            matchAction.setTypeId(tMatchAction.getTypeId());
            matchAction.setTeamName(tMatchAction.getTeamName());
            matchAction.setTeamImageUrl(tMatchAction.getTeamImageUrl());
            matchAction.setPlayerName(tMatchAction.getPlayerName());
            matchAction.setPlayerImageUrl(tMatchAction.getPlayerImageUrl());
            if (tMatchAction.getTypeId() == 104656000l) {
                String content = tMatchAction.getContent();
                if (StringUtils.isNotBlank(content)) {
                    Map playerMap = new HashMap<>();
                    JSONObject jsonObject = JSON.parseObject(content);
                    Long playerOutId = jsonObject.getLong("playerOut");
                    MatchAction.SimplerPlayer outSimplePlayer = getSimplePlayer(playerOutId, caller);
                    playerMap.put("down", outSimplePlayer);
                    Long playerInId = jsonObject.getLong("playerIn");
                    MatchAction.SimplerPlayer inSimplePlayer = getSimplePlayer(playerInId, caller);
                    playerMap.put("up", inSimplePlayer);
                    matchAction.setContent(playerMap);
                }
            } else if (tMatchAction.getTypeId() == 100159000l) {
                matchAction.setContent(tMatchAction.getContent());
                String content = tMatchAction.getContent();
                if (StringUtils.isNotBlank(content)) {
                    Map goalMap = new HashMap<>();
                    JSONObject jsonObject = JSON.parseObject(content);
                    Long playerOutId = jsonObject.getLong("goalType");
                    if (playerOutId == 0) {
                        TDictEntry tDictEntry = QmtConfigApis.getTDictEntryById(100159000l, caller);
                        goalMap.put("goalType", tDictEntry.getName());
                        goalMap.put("goalTypeId",tDictEntry.getId());
                        matchAction.setContent(goalMap);
                    } else if (playerOutId > 0) {
                        TDictEntry tDictEntry = QmtConfigApis.getTDictEntryById(playerOutId, caller);
                        if (tDictEntry != null) {
                            goalMap.put("goalType", tDictEntry.getName());
                            goalMap.put("goalTypeId",tDictEntry.getId());
                            matchAction.setContent(goalMap);
                        }
                    }
                }
            }
        } catch (Exception e) {
            LOG.error("createLatestEpisodeVo error ! tMatchAction = {}, e = {}", tMatchAction.getId(), e.getMessage(), e);
        }
        return matchAction;
    }

    private MatchAction.SimplerPlayer getSimplePlayer(Long pid,CallerParam caller) {
        MatchAction.SimplerPlayer simplerPlayer = new MatchAction.SimplerPlayer();
        TPlayer tPlayer = SbdPlayerApis.getTPlayerById(pid, caller);
        simplerPlayer.setPlayerName(tPlayer.getName());
        simplerPlayer.setPlayerImageUrl(tPlayer.getImageUrl());
        return simplerPlayer;
    }

    public List<HistoryMatch> getHistoryMatch(List<THistoryMatch> tHistoryMatches) {
        if (CollectionUtils.isEmpty(tHistoryMatches)) {
            return Collections.EMPTY_LIST;
        }
        List<HistoryMatch> returnList = Lists.newArrayList();
        for (THistoryMatch tHistoryMatch : tHistoryMatches) {
            HistoryMatch historyMatch = fillHistoryMatch(tHistoryMatch);
            returnList.add(historyMatch);
        }
        return returnList;
    }

    private HistoryMatch fillHistoryMatch(THistoryMatch tHistoryMatch) {
        HistoryMatch historyMatch = new HistoryMatch();
        historyMatch.setMid(tHistoryMatch.getMid());
        historyMatch.setCid(tHistoryMatch.getCid());
        historyMatch.setCsid(tHistoryMatch.getCsid());
        historyMatch.setStage(tHistoryMatch.getStage());
        historyMatch.setRound(tHistoryMatch.getRound());
        historyMatch.setStartTime(tHistoryMatch.getStartTime());
        historyMatch.setStartDate(tHistoryMatch.getStartDate());
        historyMatch.setName(tHistoryMatch.getName());
        historyMatch.setAbbreviation(tHistoryMatch.getAbbreviation());
        List<TCompetitor> competitors = tHistoryMatch.getCompetitors();
        if (CollectionUtils.isNotEmpty(competitors)) {
            for (TCompetitor tCompetitor : competitors) {
                HistoryMatch.Competitor competitor = historyMatch.new Competitor();
                competitor.setId(tCompetitor.getId());
                competitor.setName(tCompetitor.getName());
                competitor.setLogo(tCompetitor.getLogoUrl());
                competitor.setGround(tCompetitor.getGround().getValue());
                competitor.setFinalResult(tCompetitor.getFinalResult());
                List<TSectionResult> sectionResults = tCompetitor.getSectionResults();
                if (CollectionUtils.isNotEmpty(sectionResults)) {
                    for (TSectionResult tSectionResult : sectionResults) {
                        HistoryMatch.Competitor.SectionResult sectionResult = competitor.new SectionResult();
                        sectionResult.setOrder(tSectionResult.getOrder());
                        sectionResult.setResult(tSectionResult.getResult());
                        sectionResult.setSection(tSectionResult.getSection());
//                        sectionResult.setSectionId(tSectionResult.getSectionId());
                        competitor.getSectionResults().add(sectionResult);
                    }
                }
                historyMatch.getCompetitors().add(competitor);
            }
        }
        return historyMatch;
    }
}
