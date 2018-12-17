package com.lesports.qmt.web.api.core.creater;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.lesports.LeConstants;
import com.lesports.api.common.CallerParam;
import com.lesports.api.common.CountryCode;
import com.lesports.api.common.LiveShowStatus;
import com.lesports.qmt.config.api.dto.TCaller;
import com.lesports.qmt.config.api.dto.TDictEntry;
import com.lesports.qmt.config.api.dto.TTag;
import com.lesports.qmt.config.client.QmtConfigApis;
import com.lesports.qmt.sbc.api.dto.*;
import com.lesports.qmt.sbc.client.QmtSbcApis;
import com.lesports.qmt.sbd.api.common.Camp;
import com.lesports.qmt.sbd.api.common.GroundType;
import com.lesports.qmt.sbd.api.dto.*;
import com.lesports.qmt.sbd.client.SbdCompetitionApis;
import com.lesports.qmt.sbd.client.SbdCompetitorSeasonStatApis;
import com.lesports.qmt.web.api.core.util.Constants;
import com.lesports.qmt.web.api.core.vo.*;
import com.lesports.utils.BossMembersPackageApi;
import com.lesports.utils.LeDateUtils;
import com.lesports.utils.LeProperties;
import com.lesports.utils.math.LeNumberUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Created by gengchengliang on 2015/6/23.
 */
@Component("episodeVoCreater")
public class EpisodeVoCreater {

    private static final Logger LOG = LoggerFactory.getLogger(EpisodeVoCreater.class);

    private static final String HAS_STATS_CIDS = LeProperties.getString("has.competitor.stats.cids");

    private static final String BLANK_SPACE = " ";

    private static final String H5_LIVELINK = "http://m.lesports.com/app/match/%s.html";

    private static final List<Long> APPLICATION_CALLER_LIST = Lists.newArrayList(1003l, 1006l, 1014l, 1016l);

    /**
     * 创建最新的节目
     *
     * @param tComboEpisode
     * @return
     */
    public LatestEpisodeVo createLatestEpisodeVo(TComboEpisode tComboEpisode) {
        LatestEpisodeVo latestEpisodeVo = new LatestEpisodeVo();
        try {
            if (tComboEpisode == null) {
                return null;
            }
            if (null != tComboEpisode) {
                latestEpisodeVo.setId(tComboEpisode.getId());
                latestEpisodeVo.setName(tComboEpisode.getName());
                latestEpisodeVo.setStartTime(tComboEpisode.getStartTime());
                latestEpisodeVo.setPeriods(tComboEpisode.getPeriods());
                latestEpisodeVo.setImages(tComboEpisode.getImages());
                latestEpisodeVo.setAid(tComboEpisode.getAid());
                latestEpisodeVo.setDesc(tComboEpisode.getDesc());
                latestEpisodeVo.setDuration(tComboEpisode.getDuration());
                latestEpisodeVo.setCommentId(tComboEpisode.getCommentId());
            }

        } catch (Exception e) {
            LOG.error("createLatestEpisodeVo error ! episodeId = {}, e = {}", tComboEpisode.getId(), e.getMessage(), e);
        }
        return latestEpisodeVo;
    }


    /**
     * 为日历的episode赋值
     *
     * @param comboEpisode
     * @param current_hours
     * @return
     */
    public CalEpisodeVo createCalEpisodeVo(TComboEpisode comboEpisode, String current_hours, CallerParam caller) {
        CalEpisodeVo calEpisodeVo = new CalEpisodeVo();
        try {
            if (comboEpisode == null) {
                return null;
            }
            switch (comboEpisode.getType()) {
                case MATCH:
                    TCompetition competition = getCompetition(comboEpisode.getCid(), caller);
                    if (competition != null) {
                        calEpisodeVo.setCname(competition.getName());
                        calEpisodeVo.setLogo(competition.getLogoUrl());
                    }
                    break;
                case PROGRAM:
                    TProgramAlbum tAlbum = getAlbum(comboEpisode.getAid(), caller);
                    if (tAlbum != null) {
                        calEpisodeVo.setCname(tAlbum.getName());
                        calEpisodeVo.setLogo(comboEpisode.getLogo());
                    }
                    break;
            }
            fillEpisodeCnameAndLogo(comboEpisode, calEpisodeVo, caller);
            calEpisodeVo.setCountryCode(comboEpisode.getCountryCode());
            calEpisodeVo.setType(comboEpisode.getType().ordinal());
            calEpisodeVo.setId(comboEpisode.getId());
            calEpisodeVo.setMid(comboEpisode.getMid());
            calEpisodeVo.setCid(comboEpisode.getCid());
            calEpisodeVo.setVs(comboEpisode.isVs() ? 1 : 0);
            //香港的app和tv没有任何角标,兼容下
            if (caller.getCountry() == CountryCode.HK && Lists.newArrayList(3002L).contains(caller.getCallerId())) {
                calEpisodeVo.setIsEpisodePay(0);
            } else {
                calEpisodeVo.setIsEpisodePay(LeNumberUtils.toBoolean(comboEpisode.isIsPay()) ? 1 : 0);
            }
            calEpisodeVo.setName(comboEpisode.getName());
            calEpisodeVo.setStartTime(comboEpisode.getStartTime());
            calEpisodeVo.setStartTimeStamp(LeDateUtils.parseYYYYMMDDHHMMSS(comboEpisode.getStartTime().trim()).getTime() / 1000);
            calEpisodeVo.setRound(comboEpisode.getRound());
            calEpisodeVo.setStage(comboEpisode.getStage());
            calEpisodeVo.setGroup(comboEpisode.getGroup());
            calEpisodeVo.setGameFType(comboEpisode.getGameFType());
            calEpisodeVo.setGameSType(comboEpisode.getGameSType());
            calEpisodeVo.setGameFName(getDicName(comboEpisode.getGameFType(), caller));
            calEpisodeVo.setGameSName(getDicName(comboEpisode.getGameSType(), caller));
            calEpisodeVo.setPlayLink(comboEpisode.getPlayLink());
            calEpisodeVo.settLiveLink(comboEpisode.getTLiveLink());
            if (caller.getCountry() == CountryCode.CN) {
                calEpisodeVo.setXinyingPay(comboEpisode.isXinyingPay() ? 1 : 0);
            }
            //直播
            setLiveInfo(calEpisodeVo, comboEpisode, caller.getCallerId());
            //图文直播
            setTextLiveInfo(calEpisodeVo, comboEpisode);
            //是否章鱼猜球
            calEpisodeVo.setIsOctopus(LeNumberUtils.toBoolean(comboEpisode.isIsOctopus()) ? 1 : 0);
            calEpisodeVo.setMatchStatus(comboEpisode.getMatchStatus() == null ? -1 : comboEpisode.getMatchStatus().ordinal());
            calEpisodeVo.setStatus(comboEpisode.getStatus() == null ? -1 : comboEpisode.getStatus().ordinal());
            calEpisodeVo.setTextLiveStatus(comboEpisode.getTextLiveStatus() == null ? -1 : comboEpisode.getTextLiveStatus().ordinal());
            setTimeSection(calEpisodeVo, current_hours);
            List<TCompetitor> competitors = comboEpisode.getCompetitors();
            if (CollectionUtils.isNotEmpty(competitors)) {
                for (int i = 0; i < competitors.size(); i++) {
                    TCompetitor tCompetitor = competitors.get(i);
                    CalEpisodeVo.Competitor competitor = calEpisodeVo.new Competitor();
                    competitor.setId(tCompetitor.getId());
                    competitor.setName(tCompetitor.getName());
                    competitor.setOfficialName(tCompetitor.getOfficialName());
                    competitor.setLogo(tCompetitor.getLogoUrl());
                    if (tCompetitor.getGround() != null) {
                        competitor.setGround(tCompetitor.getGround());
                    } else {
                        if (i == 0) {
                            competitor.setGround(GroundType.HOME);
                        }
                        if (i == 1) {
                            competitor.setGround(GroundType.AWAY);
                        }
                    }
                    competitor.setScore(tCompetitor.getFinalResult());
                    calEpisodeVo.getCompetitors().add(competitor);
                }
            }
            List<TTag> tags = comboEpisode.getTags();
            if (CollectionUtils.isNotEmpty(tags)) {
                for (TTag tTag : tags) {
                    CalEpisodeVo.Tag tag = calEpisodeVo.new Tag();
                    tag.setId(tTag.getId());
                    tag.setName(tTag.getName());
                    calEpisodeVo.getTags().add(tag);
                }
            }
        } catch (Exception e) {
            LOG.error("createCalEpisodeVo error ! comboEpisodeId = {}, e = {}", comboEpisode.getId(), e.getMessage(), e);
        }
        return calEpisodeVo;
    }

    /**
     * 为比赛大厅的episode赋值
     *
     * @param comboEpisode
     * @return
     */
    public HallEpisodeVo createHallEpisodeVo(TComboEpisode comboEpisode, CallerParam caller) {
        HallEpisodeVo hallEpisodeVo = new HallEpisodeVo();
        try {
            if (comboEpisode == null) {
                return null;
            }
            fillEpisodeCnameAndLogo(comboEpisode, hallEpisodeVo, caller);
            hallEpisodeVo.setCountryCode(comboEpisode.getCountryCode());
            hallEpisodeVo.setCommentId(comboEpisode.getCommentId());
            hallEpisodeVo.setType(comboEpisode.getType().ordinal());
            if (caller.getCountry() == CountryCode.CN) {
                hallEpisodeVo.setXinyingPay(comboEpisode.isXinyingPay() ? 1 : 0);
            }
            hallEpisodeVo.setLiveUniqueId(comboEpisode.getLiveUniqueId());
            hallEpisodeVo.setId(comboEpisode.getId());
            hallEpisodeVo.setMid(comboEpisode.getMid());
            hallEpisodeVo.setDeleted(LeNumberUtils.toBoolean(comboEpisode.isDeleted()) ? 1 : 0);
            hallEpisodeVo.setCid(comboEpisode.getCid());
            hallEpisodeVo.setVs(comboEpisode.isVs() ? 1 : 0);
            hallEpisodeVo.setName(comboEpisode.getName());
            hallEpisodeVo.setStartTime(comboEpisode.getStartTime());
            //香港的app和tv没有任何角标,兼容下
            if (caller.getCountry() == CountryCode.HK && Lists.newArrayList(3002L).contains(caller.getCallerId())) {
                hallEpisodeVo.setIsEpisodePay(0);
            } else {
                hallEpisodeVo.setIsEpisodePay(LeNumberUtils.toBoolean(comboEpisode.isIsPay()) ? 1 : 0);
            }
            hallEpisodeVo.setMatchStatus(comboEpisode.getMatchStatus() == null ? -1 : comboEpisode.getMatchStatus().ordinal());
            hallEpisodeVo.setStatus(comboEpisode.getStatus() == null ? -1 : comboEpisode.getStatus().ordinal());
            hallEpisodeVo.setTextLiveStatus(comboEpisode.getTextLiveStatus() == null ? -1 : comboEpisode.getTextLiveStatus().ordinal());
            hallEpisodeVo.setRound(comboEpisode.getRound());
            hallEpisodeVo.setStage(comboEpisode.getStage());
            hallEpisodeVo.setGroup(comboEpisode.getGroup());
            hallEpisodeVo.setGameFType(comboEpisode.getGameFType());
            //大陆移动端竞猜大项ID和奥运冲突,更新为原先的大项ID
            if (APPLICATION_CALLER_LIST.contains(caller.getCallerId())) {
                if (comboEpisode.getGameFType() == 115622000) {
                    hallEpisodeVo.setGameFType(100015000l);
                }
                if (comboEpisode.getGameFType() == 115620000) {
                    hallEpisodeVo.setGameFType(100014000l);
                }
            }
            hallEpisodeVo.setDisciplineId(comboEpisode.getDisciplineId());
            hallEpisodeVo.setGameSType(comboEpisode.getGameSType());
            hallEpisodeVo.setGameFName(getDicName(comboEpisode.getGameFType(), caller));
            hallEpisodeVo.setDisciplineName(getDicName(comboEpisode.getDisciplineId(), caller));
            hallEpisodeVo.setGameSName(getDicName(comboEpisode.getGameSType(), caller));
            hallEpisodeVo.setMatchSystem(comboEpisode.getMatchSystem());
            hallEpisodeVo.setMoment(comboEpisode.getMoment());
            hallEpisodeVo.setPlayLink(comboEpisode.getPlayLink());
            hallEpisodeVo.setActivities(comboEpisode.getAppActivities());
            hallEpisodeVo.settLiveLink(comboEpisode.getTLiveLink());
            if (comboEpisode.getTheRoadOrder() > 0) {
                hallEpisodeVo.setTheRoadOrder(comboEpisode.getTheRoadOrder());
            }
            //是否章鱼猜球
            hallEpisodeVo.setIsOctopus(LeNumberUtils.toBoolean(comboEpisode.isIsOctopus()) ? 1 : 0);
            setEpisodeVideos(hallEpisodeVo, comboEpisode);
            setLiveInfo(hallEpisodeVo, comboEpisode, caller.getCallerId());
            setTextLiveInfo(hallEpisodeVo, comboEpisode);
            setTimeSection(hallEpisodeVo);
            List<TCompetitor> competitors = comboEpisode.getCompetitors();
            if (CollectionUtils.isNotEmpty(competitors)) {
                for (int i = 0; i < competitors.size(); i++) {
                    TCompetitor tCompetitor = competitors.get(i);
                    HallEpisodeVo.Competitor competitor = hallEpisodeVo.new Competitor();
                    competitor.setId(tCompetitor.getId());
                    competitor.setName(tCompetitor.getName());
                    competitor.setOfficialName(tCompetitor.getOfficialName());
                    competitor.setLogo(tCompetitor.getLogoUrl());
                    if (tCompetitor.getGround() != null) {
                        competitor.setGround(tCompetitor.getGround());
                    } else {
                        if (i == 0) {
                            competitor.setGround(GroundType.HOME);
                        }
                        if (i == 1) {
                            competitor.setGround(GroundType.AWAY);
                        }
                    }
                    competitor.setCompetitorType(tCompetitor.getCompetitorType());
                    competitor.setScore(tCompetitor.getFinalResult());
                    List<TSectionResult> sectionResults = tCompetitor.getSectionResults();
                    if (CollectionUtils.isNotEmpty(sectionResults)) {
                        for (TSectionResult tSectionResult : sectionResults) {
                            HallEpisodeVo.Competitor.SectionResult sectionResult = competitor.new SectionResult();
                            sectionResult.setOrder(tSectionResult.getOrder());
                            sectionResult.setResult(tSectionResult.getResult());
                            sectionResult.setSectionId(tSectionResult.getSectionId());
                            sectionResult.setSection(tSectionResult.getSection());
                            competitor.getSectionResults().add(sectionResult);
                        }
                    }
                    //阵营信息
                    Camp tCamp = tCompetitor.getCampEntity();
                    if (tCamp != null) {
                        HallEpisodeVo.Competitor.Camp camp = competitor.new Camp();
                        camp.setId(tCamp.getId());
                        camp.setName(tCamp.getName());
                        camp.setPicture(tCamp.getPicture());
                        competitor.setCamp(camp);
                    }
                    hallEpisodeVo.getCompetitors().add(competitor);
                }
            }
            List<TTag> tags = comboEpisode.getTags();
            if (CollectionUtils.isNotEmpty(tags)) {
                for (TTag tTag : tags) {
                    HallEpisodeVo.Tag tag = hallEpisodeVo.new Tag();
                    tag.setId(tTag.getId());
                    tag.setName(tTag.getName());
                    hallEpisodeVo.getTags().add(tag);
                }
            }
            if (StringUtils.isNotBlank(HAS_STATS_CIDS)) {
                List<String> competitions = Arrays.asList(HAS_STATS_CIDS.split("\\|"));
                if (competitions.contains(comboEpisode.getCid() + "")) {
                    try {
                        List<TCompetitorStat> allCompetitorStats = SbdCompetitorSeasonStatApis.getCompetitorStatsOfMatch(comboEpisode.getMid(), caller);
                        if (CollectionUtils.isNotEmpty(allCompetitorStats)) {
                            //根据showOrder排序
                            Collections.sort(allCompetitorStats, new Comparator<TCompetitorStat>() {
                                @Override
                                public int compare(TCompetitorStat o1, TCompetitorStat o2) {
                                    return o1.getOrder() - o2.getOrder();
                                }
                            });
                            List<TCompetitorStat> competitorStats = allCompetitorStats.size() >= 3 ? allCompetitorStats.subList(0, 3) : allCompetitorStats;
                            for (TCompetitorStat tCompetitorStat : competitorStats) {
                                HallEpisodeVo.CompetitorStat competitorStat = hallEpisodeVo.new CompetitorStat();
                                competitorStat.setId(tCompetitorStat.getId());
                                competitorStat.setName(tCompetitorStat.getName());
                                competitorStat.setCompetitorType(tCompetitorStat.getCompetitorType());
                                competitorStat.setTid(tCompetitorStat.getTid());
                                competitorStat.setTeamName(tCompetitorStat.getTeamName());
                                competitorStat.setOrder(tCompetitorStat.getOrder());
                                competitorStat.setStats(tCompetitorStat.getStats());
                                hallEpisodeVo.getCompetitorStats().add(competitorStat);
                            }
                        }
                    } catch (Exception e) {
                        LOG.error("obtain competitorStats error! mid = {} , e = {}", comboEpisode.getMid(), e.getMessage(), e);
                    }
                }
            }
        } catch (Exception e) {
            LOG.error("createHallEpisodeVo error ! comboEpisodeId = {}, e = {}", comboEpisode.getId(), e.getMessage(), e);
        }
        return hallEpisodeVo;
    }


    /**
     * 创建简单的节目
     *
     * @param comboEpisode
     * @return
     */
    public SimpleEpisodeVo createSimpleEpisodeVo(TComboEpisode comboEpisode, CallerParam caller) {
        SimpleEpisodeVo simpleEpisodeVo = new SimpleEpisodeVo();
        try {
            if (comboEpisode == null) {
                return null;
            }
            fillEpisodeCnameAndLogo(comboEpisode, simpleEpisodeVo, caller);
            simpleEpisodeVo.setType(comboEpisode.getType().ordinal());
            simpleEpisodeVo.setId(comboEpisode.getId());
            simpleEpisodeVo.setMid(comboEpisode.getMid());
            simpleEpisodeVo.setCid(comboEpisode.getCid());
            simpleEpisodeVo.setVs(comboEpisode.isVs() ? 1 : 0);
            simpleEpisodeVo.setName(comboEpisode.getName());
            simpleEpisodeVo.setStartTime(comboEpisode.getStartTime());
            simpleEpisodeVo.setStatus(comboEpisode.getStatus() == null ? -1 : comboEpisode.getStatus().ordinal());
            simpleEpisodeVo.setRound(comboEpisode.getRound());
            simpleEpisodeVo.setStage(comboEpisode.getStage());
            simpleEpisodeVo.setGroup(comboEpisode.getGroup());
            //香港的app和tv没有任何角标,兼容下
            if (caller.getCountry() == CountryCode.HK && Lists.newArrayList(3002L).contains(caller.getCallerId())) {
                simpleEpisodeVo.setIsEpisodePay(0);
            } else {
                simpleEpisodeVo.setIsEpisodePay(LeNumberUtils.toBoolean(comboEpisode.isIsPay()) ? 1 : 0);
            }
            simpleEpisodeVo.setGameFType(comboEpisode.getGameFType());
            //大陆移动端竞猜大项ID和奥运冲突,更新为原先的大项ID
            if (APPLICATION_CALLER_LIST.contains(caller.getCallerId())) {
                if (comboEpisode.getGameFType() == 115622000) {
                    simpleEpisodeVo.setGameFType(100015000l);
                }
                if (comboEpisode.getGameFType() == 115620000) {
                    simpleEpisodeVo.setGameFType(100014000l);
                }
            }
            simpleEpisodeVo.setGameSType(comboEpisode.getGameSType());
            simpleEpisodeVo.setMatchSystem(comboEpisode.getMatchSystem());
            setEpisodeVideos(simpleEpisodeVo, comboEpisode);
            List<TLiveStream> streams = comboEpisode.getStreams();
            if (CollectionUtils.isNotEmpty(streams)) {
                for (TLiveStream stream : streams) {
                    String liveId = stream.getLiveId();
                    if (StringUtils.isNotBlank(liveId)) {
                        simpleEpisodeVo.setIsLive(1);
                        simpleEpisodeVo.setLiveId(LeNumberUtils.toLong(liveId));
                        break;
                    }
                }
            }
            List<TCompetitor> competitors = comboEpisode.getCompetitors();
            if (CollectionUtils.isNotEmpty(competitors)) {
                for (int i = 0; i < competitors.size(); i++) {
                    TCompetitor tCompetitor = competitors.get(i);
                    HallEpisodeVo.Competitor competitor = simpleEpisodeVo.new Competitor();
                    competitor.setId(tCompetitor.getId());
                    competitor.setName(tCompetitor.getName());
                    competitor.setOfficialName(tCompetitor.getOfficialName());
                    competitor.setLogo(tCompetitor.getLogoUrl());
                    if (tCompetitor.getGround() != null) {
                        competitor.setGround(tCompetitor.getGround());
                    } else {
                        if (i == 0) {
                            competitor.setGround(GroundType.HOME);
                        }
                        if (i == 1) {
                            competitor.setGround(GroundType.AWAY);
                        }
                    }
                    competitor.setCompetitorType(tCompetitor.getCompetitorType());
                    competitor.setScore(tCompetitor.getFinalResult());
                    simpleEpisodeVo.getCompetitors().add(competitor);
                }
            }
        } catch (Exception e) {
            LOG.error("createHallEpisodeVo error ! comboEpisodeId = {}, e = {}", comboEpisode.getId(), e.getMessage(), e);
        }
        return simpleEpisodeVo;
    }


    /**
     * 为比赛大厅的episode赋值 - app用
     *
     * @param comboEpisode
     * @return
     */
    public HallEpisodeVo createHallEpisodeVo4App(TComboEpisode comboEpisode, CallerParam caller) {
        HallEpisodeVo hallEpisodeVo = new HallEpisodeVo();
        try {
            if (comboEpisode == null) {
                return null;
            }
            if (comboEpisode.getType() != null) {
                switch (comboEpisode.getType()) {
                    case MATCH:
                        TCompetition competition = getCompetition(comboEpisode.getCid(), caller);
                        if (competition != null) {
                            hallEpisodeVo.setCname(competition.getName());
                            hallEpisodeVo.setLogo(competition.getLogoUrl());
                        }
                        break;
                    case PROGRAM:
                        TProgramAlbum tAlbum = getAlbum(comboEpisode.getAid(), caller);
                        if (tAlbum != null) {
                            hallEpisodeVo.setCname(tAlbum.getName());
                            hallEpisodeVo.setLogo(comboEpisode.getLogo());
                        }
                        break;
                }
            }
            fillEpisodeCnameAndLogo(comboEpisode, hallEpisodeVo, caller);
            hallEpisodeVo.setCountryCode(comboEpisode.getCountryCode());
            if (caller.getCountry() == CountryCode.CN) {
                hallEpisodeVo.setXinyingPay(comboEpisode.isXinyingPay() ? 1 : 0);
            }
            hallEpisodeVo.setDeleted(LeNumberUtils.toBoolean(comboEpisode.isDeleted()) ? 1 : 0);
            //全媒体无重点比赛
            hallEpisodeVo.setKey(0);
            hallEpisodeVo.setCommentId(comboEpisode.getCommentId());
            hallEpisodeVo.setType(comboEpisode.getType() == null ? null : comboEpisode.getType().ordinal());
            //香港的app和tv没有任何角标,兼容下
            if (caller.getCountry() == CountryCode.HK && Lists.newArrayList(3002L).contains(caller.getCallerId())) {
                hallEpisodeVo.setIsEpisodePay(0);
            } else {
                hallEpisodeVo.setIsEpisodePay(LeNumberUtils.toBoolean(comboEpisode.isIsPay()) ? 1 : 0);
            }
            hallEpisodeVo.setId(comboEpisode.getId());
            hallEpisodeVo.setMid(comboEpisode.getMid());
            hallEpisodeVo.setCid(comboEpisode.getCid());
            hallEpisodeVo.setVs(comboEpisode.isVs() ? 1 : 0);
            hallEpisodeVo.setName(comboEpisode.getName());
            hallEpisodeVo.setStartTime(comboEpisode.getStartTime());
            hallEpisodeVo.setMatchStatus(comboEpisode.getMatchStatus() == null ? -1 : comboEpisode.getMatchStatus().ordinal());
            hallEpisodeVo.setStatus(comboEpisode.getStatus() == null ? -1 : comboEpisode.getStatus().ordinal());
            hallEpisodeVo.setTextLiveStatus(comboEpisode.getTextLiveStatus() == null ? -1 : comboEpisode.getTextLiveStatus().ordinal());
            hallEpisodeVo.setRound(comboEpisode.getRound());
            hallEpisodeVo.setStage(comboEpisode.getStage());
            hallEpisodeVo.setGroup(comboEpisode.getGroup());
            hallEpisodeVo.setGameFType(comboEpisode.getGameFType());
            //大陆移动端竞猜大项ID和奥运冲突,更新为原先的大项ID
            if (APPLICATION_CALLER_LIST.contains(caller.getCallerId())) {
                if (comboEpisode.getGameFType() == 115622000) {
                    hallEpisodeVo.setGameFType(100015000l);
                }
                if (comboEpisode.getGameFType() == 115620000) {
                    hallEpisodeVo.setGameFType(100014000l);
                }
            }
            hallEpisodeVo.setDisciplineId(comboEpisode.getDisciplineId());
            hallEpisodeVo.setGameSType(comboEpisode.getGameSType());
            hallEpisodeVo.setGameFName(getDicName(comboEpisode.getGameFType(), caller));
            hallEpisodeVo.setDisciplineName(getDicName(comboEpisode.getDisciplineId(), caller));
            hallEpisodeVo.setGameSName(getDicName(comboEpisode.getGameSType(), caller));
            hallEpisodeVo.setMatchSystem(comboEpisode.getMatchSystem());
            hallEpisodeVo.setMoment(comboEpisode.getMoment());
            hallEpisodeVo.setPlayLink(comboEpisode.getPlayLink());
            hallEpisodeVo.settLiveLink(comboEpisode.getTLiveLink());
            hallEpisodeVo.setActivities(comboEpisode.getAppActivities());
            //是否章鱼猜球
            hallEpisodeVo.setIsOctopus(LeNumberUtils.toBoolean(comboEpisode.isIsOctopus()) ? 1 : 0);
            setEpisodeVideos(hallEpisodeVo, comboEpisode);
            setLiveInfo(hallEpisodeVo, comboEpisode, caller.getCallerId());
            setTextLiveInfo(hallEpisodeVo, comboEpisode);
            setTimeSection(hallEpisodeVo);
            List<TCompetitor> competitors = comboEpisode.getCompetitors();
            if (CollectionUtils.isNotEmpty(competitors)) {
                for (int i = 0; i < competitors.size(); i++) {
                    TCompetitor tCompetitor = competitors.get(i);
                    HallEpisodeVo.Competitor competitor = hallEpisodeVo.new Competitor();
                    competitor.setId(tCompetitor.getId());
                    competitor.setName(tCompetitor.getName());
                    competitor.setOfficialName(tCompetitor.getOfficialName());
                    competitor.setLogo(tCompetitor.getLogoUrl());
                    if (tCompetitor.getGround() != null) {
                        competitor.setGround(tCompetitor.getGround());
                    } else {
                        if (i == 0) {
                            competitor.setGround(GroundType.HOME);
                        }
                        if (i == 1) {
                            competitor.setGround(GroundType.AWAY);
                        }
                    }
                    competitor.setCompetitorType(tCompetitor.getCompetitorType());
                    competitor.setScore(tCompetitor.getFinalResult());
                    competitor.setCompetitorCountryId(tCompetitor.getCompetitorCountryId());
                    competitor.setCountryImgUrl(tCompetitor.getCountryImgUrl() == null ? "" : tCompetitor.getCountryImgUrl());
                    hallEpisodeVo.getCompetitors().add(competitor);
                }
            }
            List<TTag> tags = comboEpisode.getTags();
            if (CollectionUtils.isNotEmpty(tags)) {
                for (TTag tTag : tags) {
                    HallEpisodeVo.Tag tag = hallEpisodeVo.new Tag();
                    tag.setId(tTag.getId());
                    tag.setName(tTag.getName());
                    hallEpisodeVo.getTags().add(tag);
                }
            }
        } catch (Exception e) {
            LOG.error("createHallEpisodeVo error ! comboEpisodeId = {}, e = {}", comboEpisode.getId(), e.getMessage(), e);
        }
        return hallEpisodeVo;
    }


	/**
	 * 为比赛大厅的episode赋值 - app用
	 *
	 * @param comboEpisode
	 * @return
	 */
	public void createHallEpisodeVo4App(HallEpisodeVo hallEpisodeVo, TComboEpisode comboEpisode, CallerParam caller) {
		try {
			if (comboEpisode == null) {
				return;
			}
			if (comboEpisode.getType() != null) {
				switch (comboEpisode.getType()) {
					case MATCH:
						TCompetition competition = getCompetition(comboEpisode.getCid(), caller);
						if (competition != null) {
							hallEpisodeVo.setCname(competition.getName());
							hallEpisodeVo.setLogo(competition.getLogoUrl());
						}
						break;
					case PROGRAM:
						TProgramAlbum tAlbum = getAlbum(comboEpisode.getAid(), caller);
						if (tAlbum != null) {
							hallEpisodeVo.setCname(tAlbum.getName());
							hallEpisodeVo.setLogo(comboEpisode.getLogo());
						}
						break;
				}
			}
			fillEpisodeCnameAndLogo(comboEpisode, hallEpisodeVo, caller);
			hallEpisodeVo.setCountryCode(comboEpisode.getCountryCode());
			if (caller.getCountry() == CountryCode.CN) {
				hallEpisodeVo.setXinyingPay(comboEpisode.isXinyingPay() ? 1 : 0);
			}
			hallEpisodeVo.setDeleted(LeNumberUtils.toBoolean(comboEpisode.isDeleted()) ? 1 : 0);
			//全媒体无重点比赛
			hallEpisodeVo.setKey(0);
			hallEpisodeVo.setCommentId(comboEpisode.getCommentId());
			hallEpisodeVo.setType(comboEpisode.getType() == null ? null : comboEpisode.getType().ordinal());
			//香港的app和tv没有任何角标,兼容下
			if (caller.getCountry() == CountryCode.HK && Lists.newArrayList(3002L).contains(caller.getCallerId())) {
				hallEpisodeVo.setIsEpisodePay(0);
			} else {
				hallEpisodeVo.setIsEpisodePay(LeNumberUtils.toBoolean(comboEpisode.isIsPay()) ? 1 : 0);
			}
			hallEpisodeVo.setId(comboEpisode.getId());
			hallEpisodeVo.setMid(comboEpisode.getMid());
			hallEpisodeVo.setCid(comboEpisode.getCid());
			hallEpisodeVo.setVs(comboEpisode.isVs() ? 1 : 0);
			hallEpisodeVo.setName(comboEpisode.getName());
			hallEpisodeVo.setStartTime(comboEpisode.getStartTime());
			hallEpisodeVo.setMatchStatus(comboEpisode.getMatchStatus() == null ? -1 : comboEpisode.getMatchStatus().ordinal());
			hallEpisodeVo.setStatus(comboEpisode.getStatus() == null ? -1 : comboEpisode.getStatus().ordinal());
			hallEpisodeVo.setTextLiveStatus(comboEpisode.getTextLiveStatus() == null ? -1 : comboEpisode.getTextLiveStatus().ordinal());
			hallEpisodeVo.setRound(comboEpisode.getRound());
			hallEpisodeVo.setStage(comboEpisode.getStage());
			hallEpisodeVo.setGroup(comboEpisode.getGroup());
			hallEpisodeVo.setGameFType(comboEpisode.getGameFType());
			//大陆移动端竞猜大项ID和奥运冲突,更新为原先的大项ID
			if (APPLICATION_CALLER_LIST.contains(caller.getCallerId())) {
				if (comboEpisode.getGameFType() == 115622000) {
					hallEpisodeVo.setGameFType(100015000l);
				}
				if (comboEpisode.getGameFType() == 115620000) {
					hallEpisodeVo.setGameFType(100014000l);
				}
			}
			hallEpisodeVo.setDisciplineId(comboEpisode.getDisciplineId());
			hallEpisodeVo.setGameSType(comboEpisode.getGameSType());
			hallEpisodeVo.setGameFName(getDicName(comboEpisode.getGameFType(), caller));
			hallEpisodeVo.setDisciplineName(getDicName(comboEpisode.getDisciplineId(), caller));
			hallEpisodeVo.setGameSName(getDicName(comboEpisode.getGameSType(), caller));
			hallEpisodeVo.setMatchSystem(comboEpisode.getMatchSystem());
			hallEpisodeVo.setMoment(comboEpisode.getMoment());
			hallEpisodeVo.setPlayLink(comboEpisode.getPlayLink());
			hallEpisodeVo.settLiveLink(comboEpisode.getTLiveLink());
			hallEpisodeVo.setActivities(comboEpisode.getAppActivities());
			//是否章鱼猜球
			hallEpisodeVo.setIsOctopus(LeNumberUtils.toBoolean(comboEpisode.isIsOctopus()) ? 1 : 0);
			setEpisodeVideos(hallEpisodeVo, comboEpisode);
			setLiveInfo(hallEpisodeVo, comboEpisode, caller.getCallerId());
			setTextLiveInfo(hallEpisodeVo, comboEpisode);
			setTimeSection(hallEpisodeVo);
			List<TCompetitor> competitors = comboEpisode.getCompetitors();
			if (CollectionUtils.isNotEmpty(competitors)) {
				for (int i = 0; i < competitors.size(); i++) {
					TCompetitor tCompetitor = competitors.get(i);
					HallEpisodeVo.Competitor competitor = hallEpisodeVo.new Competitor();
					competitor.setId(tCompetitor.getId());
					competitor.setName(tCompetitor.getName());
					competitor.setOfficialName(tCompetitor.getOfficialName());
					competitor.setLogo(tCompetitor.getLogoUrl());
					if (tCompetitor.getGround() != null) {
						competitor.setGround(tCompetitor.getGround());
					} else {
						if (i == 0) {
							competitor.setGround(GroundType.HOME);
						}
						if (i == 1) {
							competitor.setGround(GroundType.AWAY);
						}
					}
					competitor.setCompetitorType(tCompetitor.getCompetitorType());
					competitor.setScore(tCompetitor.getFinalResult());
					competitor.setCompetitorCountryId(tCompetitor.getCompetitorCountryId());
					competitor.setCountryImgUrl(tCompetitor.getCountryImgUrl() == null ? "" : tCompetitor.getCountryImgUrl());
					hallEpisodeVo.getCompetitors().add(competitor);
				}
			}
			List<TTag> tags = comboEpisode.getTags();
			if (CollectionUtils.isNotEmpty(tags)) {
				for (TTag tTag : tags) {
					HallEpisodeVo.Tag tag = hallEpisodeVo.new Tag();
					tag.setId(tTag.getId());
					tag.setName(tTag.getName());
					hallEpisodeVo.getTags().add(tag);
				}
			}
		} catch (Exception e) {
			LOG.error("createHallEpisodeVo error ! comboEpisodeId = {}, e = {}", comboEpisode.getId(), e.getMessage(), e);
		}
	}


    /**
     * 为比赛大厅的episode赋值 - tv用
     *
     * @param comboEpisode
     * @return
     */
    public HallEpisodeVo4Tv createHallEpisodeVo4Tv(TComboEpisode comboEpisode, CallerParam caller) {
        HallEpisodeVo4Tv hallEpisodeVo = new HallEpisodeVo4Tv();
        try {
            if (comboEpisode == null) {
                return null;
            }

            fillEpisodeCnameAndLogo(comboEpisode, hallEpisodeVo, caller);
            hallEpisodeVo.setId(comboEpisode.getId());
            if (caller.getCountry() == CountryCode.CN) {
                hallEpisodeVo.setXinyingPay(comboEpisode.isXinyingPay() ? 1 : 0);
            }
            hallEpisodeVo.setCid(comboEpisode.getCid());
            if (44001L == comboEpisode.getCid() || 100424001L == comboEpisode.getCid()) {
                hallEpisodeVo.setIsAt(true);
            }
            hallEpisodeVo.setVs(comboEpisode.isVs() ? 1 : 0);
            hallEpisodeVo.setName(comboEpisode.getName());
            hallEpisodeVo.setGameFType(comboEpisode.getGameFType());
            //香港使用,暂时注掉 todo
//            hallEpisodeVo.setTvAdImageUrl(comboEpisode.getTvAdImageUrl());
            //大陆移动端竞猜大项ID和奥运冲突,更新为原先的大项ID
            if (APPLICATION_CALLER_LIST.contains(caller.getCallerId())) {
                if (comboEpisode.getGameFType() == 115622000) {
                    hallEpisodeVo.setGameFType(100015000l);
                }
                if (comboEpisode.getGameFType() == 115620000) {
                    hallEpisodeVo.setGameFType(100014000l);
                }
            }
            if (comboEpisode.isIsPay() && comboEpisode.getStatus() != LiveShowStatus.LIVE_END) {
                hallEpisodeVo.setCornerMark(1);
            }
            hallEpisodeVo.setMid(comboEpisode.getMid());
            hallEpisodeVo.setStartTime(comboEpisode.getStartTime());
            hallEpisodeVo.setStatus(comboEpisode.getStatus() == null ? -1 : comboEpisode.getStatus().ordinal());
            hallEpisodeVo.setRound(comboEpisode.getRound());
            hallEpisodeVo.setMoment(comboEpisode.getMoment());
            //致新卡片
            if (hallEpisodeVo.getGameFType() == 100015000L) {
                hallEpisodeVo.setCardType(0);//足球
            } else if (115620000L == hallEpisodeVo.getGameFType()) {
                hallEpisodeVo.setCardType(1);//奥运篮球
            } else if (Constants.OLYMPIC_CID == hallEpisodeVo.getCid() && 115620000L != hallEpisodeVo.getGameFType()) {
                hallEpisodeVo.setCardType(2);//奥运其他
            } else if (100014000L == hallEpisodeVo.getGameFType()) {
                hallEpisodeVo.setCardType(4);//篮球
            } else {
                hallEpisodeVo.setCardType(3);//其他
            }
            //直播场次ID
            String liveUniqueId = comboEpisode.getLiveUniqueId();
            //如果是付费节目则添加会员信息
            if (comboEpisode.isIsPay() && StringUtils.isNotBlank(liveUniqueId)) {
                addVipInfos(hallEpisodeVo, liveUniqueId);
            }
            setLiveInfo(hallEpisodeVo, comboEpisode, caller);
            setDetailLiveInfo4Tv(hallEpisodeVo, comboEpisode);
            setTimeSection(hallEpisodeVo);
            List<TSimpleVideo> videos = comboEpisode.getVideos();
            if ((comboEpisode.getStatus() == LiveShowStatus.LIVE_NOT_START || comboEpisode.getStatus() == LiveShowStatus.NO_LIVE) && CollectionUtils.isEmpty(videos)) {
                hallEpisodeVo.setShowStatus(0);
            } else if ((comboEpisode.getStatus() == LiveShowStatus.LIVE_NOT_START || comboEpisode.getStatus() == LiveShowStatus.NO_LIVE) && CollectionUtils.isNotEmpty(videos)) {
                hallEpisodeVo.setShowStatus(1);
            } else if (comboEpisode.getStatus() == LiveShowStatus.LIVING) {
                hallEpisodeVo.setShowStatus(2);
            } else if (comboEpisode.getStatus() == LiveShowStatus.LIVE_END && CollectionUtils.isEmpty(videos)) {
                hallEpisodeVo.setShowStatus(3);
            } else if (comboEpisode.getStatus() == LiveShowStatus.LIVE_END && CollectionUtils.isNotEmpty(videos)) {
                hallEpisodeVo.setShowStatus(4);
            }
            if (CollectionUtils.isNotEmpty(videos)) {
                for (TSimpleVideo video : videos) {
                    if (video.getType().ordinal() == Constants.VIDEO_TYPE_RECORD) {
                        hallEpisodeVo.setRecordedId(video.getVid());
                        break;
                    }
                }
            }
            List<TCompetitor> competitors = comboEpisode.getCompetitors();
            if (CollectionUtils.isNotEmpty(competitors)) {
                for (int i = 0; i < competitors.size(); i++) {
                    TCompetitor tCompetitor = competitors.get(i);
                    HallEpisodeVo4Tv.Competitor competitor = hallEpisodeVo.new Competitor();
                    competitor.setId(tCompetitor.getId());
                    competitor.setName(tCompetitor.getName());
                    competitor.setLogo(tCompetitor.getLogoUrl());
                    if (tCompetitor.getGround() != null) {
                        competitor.setGround(tCompetitor.getGround());
                    } else {
                        if (i == 0) {
                            competitor.setGround(GroundType.HOME);
                        }
                        if (i == 1) {
                            competitor.setGround(GroundType.AWAY);
                        }
                    }
                    competitor.setCompetitorType(tCompetitor.getCompetitorType());
                    competitor.setScore(tCompetitor.getFinalResult());
                    hallEpisodeVo.getCompetitors().add(competitor);
                }
            }
        } catch (Exception e) {
            LOG.error("createHallEpisodeVo4Tv error ! comboEpisodeId = {}, e = {}", comboEpisode.getId(), e.getMessage(), e);
        }
        return hallEpisodeVo;
    }

//    /**
//     * 获取节目详情
//     *
//     * @param comboEpisode
//     * @return
//     */
//    public DetailEpisodeVo createDetailEpisodeVo4App(TComboEpisode comboEpisode, String uid, long callerId) {
//        DetailEpisodeVo detailEpisodeVo = createDetailEpisodeVo4App(comboEpisode, callerId);
//        try {
//            if (comboEpisode == null) {
//                return null;
//            }
//            buildXinyingPlayUrl(detailEpisodeVo, comboEpisode.getXinyingMatchId(), callerId);
//
//        } catch (Exception e) {
//            LOG.error("createDetailEpisodeVo4App error ! comboEpisodeId = {}, e = {}", comboEpisode.getId(), e.getMessage(), e);
//        }
//        return detailEpisodeVo;
//    }

//    /**
//     * 获取节目详情
//     *
//     * @param comboEpisode
//     * @return
//     */
//    public DetailEpisodeVo createDetailEpisodeVo4App(TComboEpisode comboEpisode, CallerParam caller) {
//        DetailEpisodeVo detailEpisodeVo = createDetailEpisodeVo4App(comboEpisode,caller);
//        try {
//            if (comboEpisode == null) {
//                return null;
//            }
//            setDetailLiveInfo(detailEpisodeVo, comboEpisode, caller.getCallerId());
//            setLiveInfo(detailEpisodeVo, comboEpisode, caller.getCallerId());
//        } catch (Exception e) {
//            LOG.error("createDetailEpisodeVo4App error ! comboEpisodeId = {}, e = {}", comboEpisode.getId(), e.getMessage(), e);
//        }
//        return detailEpisodeVo;
//    }

    /**
     * 获取节目详情
     *
     * @param comboEpisode
     * @return
     */
    public DetailEpisodeVo createDetailEpisodeVo4App(TComboEpisode comboEpisode, CallerParam caller) {
        DetailEpisodeVo detailEpisodeVo = new DetailEpisodeVo();
        try {
            if (comboEpisode == null) {
                return null;
            }
            detailEpisodeVo.setCountryCode(comboEpisode.getCountryCode());
            detailEpisodeVo.setCommentId(comboEpisode.getCommentId());
            if (caller.getCountry() == CountryCode.CN) {
                detailEpisodeVo.setXinyingPay(comboEpisode.isXinyingPay() ? 1 : 0);
            }
            detailEpisodeVo.setId(comboEpisode.getId());
            detailEpisodeVo.setMid(comboEpisode.getMid());
            detailEpisodeVo.setCid(comboEpisode.getCid());
            fillEpisodeCnameAndLogo(comboEpisode, detailEpisodeVo, caller);
            detailEpisodeVo.setVs(comboEpisode.isVs() ? 1 : 0);
            detailEpisodeVo.setName(comboEpisode.getName());
            detailEpisodeVo.setStartTime(comboEpisode.getStartTime());
            //香港的app和tv没有任何角标,兼容下
            if (caller.getCountry() == CountryCode.HK && Lists.newArrayList(3002L).contains(caller.getCallerId())) {
                detailEpisodeVo.setIsEpisodePay(0);
            } else {
                detailEpisodeVo.setIsEpisodePay(LeNumberUtils.toBoolean(comboEpisode.isIsPay()) ? 1 : 0);
            }
            detailEpisodeVo.setMatchStatus(comboEpisode.getMatchStatus() == null ? -1 : comboEpisode.getMatchStatus().ordinal());
            detailEpisodeVo.setStatus(comboEpisode.getStatus() == null ? -1 : comboEpisode.getStatus().ordinal());
            detailEpisodeVo.setTextLiveStatus(comboEpisode.getTextLiveStatus() == null ? -1 : comboEpisode.getTextLiveStatus().ordinal());
            detailEpisodeVo.setRound(comboEpisode.getRound());
            detailEpisodeVo.setStage(comboEpisode.getStage());
            detailEpisodeVo.setGroup(comboEpisode.getGroup());
            detailEpisodeVo.setGameFType(comboEpisode.getGameFType());
            //大陆移动端竞猜大项ID和奥运冲突,更新为原先的大项ID
            if (APPLICATION_CALLER_LIST.contains(caller.getCallerId())) {
                if (comboEpisode.getGameFType() == 115622000) {
                    detailEpisodeVo.setGameFType(100015000l);
                }
                if (comboEpisode.getGameFType() == 115620000) {
                    detailEpisodeVo.setGameFType(100014000l);
                }
            }
            detailEpisodeVo.setGameSType(comboEpisode.getGameSType());
            detailEpisodeVo.setMatchSystem(comboEpisode.getMatchSystem());
            detailEpisodeVo.setGameFName(getDicName(comboEpisode.getGameFType(), caller));
            detailEpisodeVo.setGameSName(getDicName(comboEpisode.getGameSType(), caller));
            detailEpisodeVo.setPlayLink(comboEpisode.getPlayLink());
            detailEpisodeVo.settLiveLink(comboEpisode.getTLiveLink());
            detailEpisodeVo.setMoment(comboEpisode.getMoment());
            detailEpisodeVo.setImages(comboEpisode.getImages());
            detailEpisodeVo.setDesc(comboEpisode.getDesc());
            detailEpisodeVo.setDuration(comboEpisode.getDuration());
            detailEpisodeVo.setPeriods(comboEpisode.getPeriods());
            detailEpisodeVo.setType(comboEpisode.getType().ordinal());
            detailEpisodeVo.setPartnerType(comboEpisode.getPartnerType());
            //是否章鱼猜球
            detailEpisodeVo.setIsOctopus(LeNumberUtils.toBoolean(comboEpisode.isIsOctopus()) ? 1 : 0);
            setDetailEpisodeVideos(detailEpisodeVo, comboEpisode);
            setDetailTextLiveInfo(detailEpisodeVo, comboEpisode);
            detailEpisodeVo.settLiveLink4H5(detailEpisodeVo.getIsTextLive() == 1 ? String.format(H5_LIVELINK, detailEpisodeVo.getMid()) : StringUtils.EMPTY);
            setTimeSection(detailEpisodeVo);
            List<TCompetitor> competitors = comboEpisode.getCompetitors();
            if (CollectionUtils.isNotEmpty(competitors)) {
                for (int i = 0; i < competitors.size(); i++) {
                    TCompetitor tCompetitor = competitors.get(i);
                    HallEpisodeVo.Competitor competitor = detailEpisodeVo.new Competitor();
                    competitor.setId(tCompetitor.getId());
                    competitor.setName(tCompetitor.getName());
                    competitor.setOfficialName(tCompetitor.getOfficialName());
                    competitor.setLogo(tCompetitor.getLogoUrl());
                    if (tCompetitor.getGround() != null) {
                        competitor.setGround(tCompetitor.getGround());
                    } else {
                        if (i == 0) {
                            competitor.setGround(GroundType.HOME);
                        }
                        if (i == 1) {
                            competitor.setGround(GroundType.AWAY);
                        }
                    }
                    competitor.setCompetitorType(tCompetitor.getCompetitorType());
                    competitor.setScore(tCompetitor.getFinalResult());
                    competitor.setIsFocused(LeNumberUtils.toBoolean(tCompetitor.isIsFocused()) ? 1 : 0);
                    List<TSectionResult> sectionResults = tCompetitor.getSectionResults();
                    if (CollectionUtils.isNotEmpty(sectionResults)) {
                        for (TSectionResult tSectionResult : sectionResults) {
                            HallEpisodeVo.Competitor.SectionResult sectionResult = competitor.new SectionResult();
                            sectionResult.setOrder(tSectionResult.getOrder());
                            sectionResult.setResult(tSectionResult.getResult());
                            sectionResult.setSectionId(tSectionResult.getSectionId());
                            sectionResult.setSection(tSectionResult.getSection());
                            competitor.getSectionResults().add(sectionResult);
                        }
                    }
                    detailEpisodeVo.getCompetitors().add(competitor);
                }
            }
            List<TTag> tags = comboEpisode.getTags();
            if (CollectionUtils.isNotEmpty(tags)) {
                for (TTag tTag : tags) {
                    DetailEpisodeVo.Tag tag = detailEpisodeVo.new Tag();
                    tag.setId(tTag.getId());
                    tag.setName(tTag.getName());
                    detailEpisodeVo.getTags().add(tag);
                }
            }
        } catch (Exception e) {
            LOG.error("DetailEpisodeVo error ! comboEpisodeId = {}, e = {}", comboEpisode.getId(), e.getMessage(), e);
        }
        return detailEpisodeVo;
    }


    /**
     * 新英付费播放url
     *
     * @param detailEpisodeVo
     * @param xinyingMatchId
     */
    public void buildXinyingPlayUrl(DetailEpisodeVo detailEpisodeVo, long xinyingMatchId, long callerId) {
        StringBuffer sb = new StringBuffer();
        if (callerId == LeConstants.LESPORTS_PC_CALLER_CODE) {//pc的新英付费地址
            sb.append("http://ssports.smgbb.cn/live/");
        } else {//m站的新英付费地址
            sb.append("http://m.ssports.smgbb.cn/live/");
        }
        if (xinyingMatchId != 0) {
            sb.append(xinyingMatchId).append(".html?source=lesports");
            if (callerId == LeConstants.LESPORTS_PC_CALLER_CODE) {//pc的新英付费地址
                sb.append("&subsource=lesportsPc");
            } else {//m站的新英付费地址
                sb.append("&subsource=lesportsApp");
            }
            detailEpisodeVo.setXinyingUrl(sb.toString());
        }
    }


    /**
     * 获取节目详情
     *
     * @param comboEpisode
     * @return
     */
    public DetailEpisodeVo createDetailEpisodeVo(TComboEpisode comboEpisode, CallerParam caller) {
        DetailEpisodeVo detailEpisodeVo = new DetailEpisodeVo();
        try {
            if (comboEpisode == null) {
                return null;
            }
            detailEpisodeVo.setCommentId(comboEpisode.getCommentId());
            detailEpisodeVo.setCountryCode(comboEpisode.getCountryCode());

            detailEpisodeVo.setId(comboEpisode.getId());
            detailEpisodeVo.setMid(comboEpisode.getMid());
            detailEpisodeVo.setCid(comboEpisode.getCid());
            fillEpisodeCnameAndLogo(comboEpisode, detailEpisodeVo, caller);
            detailEpisodeVo.setVs(comboEpisode.isVs() ? 1 : 0);
            detailEpisodeVo.setName(comboEpisode.getName());
            //香港的app和tv没有任何角标,兼容下
            if (caller.getCountry() == CountryCode.HK && Lists.newArrayList(3002L).contains(caller.getCallerId())) {
                detailEpisodeVo.setIsEpisodePay(0);
            } else {
                detailEpisodeVo.setIsEpisodePay(LeNumberUtils.toBoolean(comboEpisode.isIsPay()) ? 1 : 0);
            }
            //香港使用,暂时注掉 todo
//            detailEpisodeVo.setTvAdImageUrl(comboEpisode.getTvAdImageUrl());
            detailEpisodeVo.setStartTime(comboEpisode.getStartTime());
            detailEpisodeVo.setMatchStatus(comboEpisode.getMatchStatus() == null ? -1 : comboEpisode.getMatchStatus().ordinal());
            detailEpisodeVo.setStatus(comboEpisode.getStatus() == null ? -1 : comboEpisode.getStatus().ordinal());
            detailEpisodeVo.setTextLiveStatus(comboEpisode.getTextLiveStatus() == null ? -1 : comboEpisode.getTextLiveStatus().ordinal());
            detailEpisodeVo.setRound(comboEpisode.getRound());
            detailEpisodeVo.setStage(comboEpisode.getStage());
            detailEpisodeVo.setGroup(comboEpisode.getGroup());
            detailEpisodeVo.setGameFType(comboEpisode.getGameFType());
            //大陆移动端竞猜大项ID和奥运冲突,更新为原先的大项ID
            if (APPLICATION_CALLER_LIST.contains(caller.getCallerId())) {
                if (comboEpisode.getGameFType() == 115622000) {
                    detailEpisodeVo.setGameFType(100015000l);
                }
                if (comboEpisode.getGameFType() == 115620000) {
                    detailEpisodeVo.setGameFType(100014000l);
                }
            }

            //致新卡片
            if (detailEpisodeVo.getGameFType() == 100015000L) {
                detailEpisodeVo.setCardType(0);//足球
            } else if (115620000L == detailEpisodeVo.getGameFType()) {
                detailEpisodeVo.setCardType(1);//奥运篮球
            } else if (Constants.OLYMPIC_CID == detailEpisodeVo.getCid() && 115620000L != detailEpisodeVo.getGameFType()) {
                detailEpisodeVo.setCardType(2);//奥运其他
            } else if (100014000L == detailEpisodeVo.getGameFType()) {
                detailEpisodeVo.setCardType(4);//篮球
            } else {
                detailEpisodeVo.setCardType(3);//其他
            }
            detailEpisodeVo.setGameSType(comboEpisode.getGameSType());
            detailEpisodeVo.setMatchSystem(comboEpisode.getMatchSystem());
            detailEpisodeVo.setGameFName(getDicName(comboEpisode.getGameFType(), caller));
            detailEpisodeVo.setGameSName(getDicName(comboEpisode.getGameSType(), caller));
            detailEpisodeVo.setPlayLink(comboEpisode.getPlayLink());
            detailEpisodeVo.settLiveLink(comboEpisode.getTLiveLink());
            detailEpisodeVo.setMoment(comboEpisode.getMoment());
            detailEpisodeVo.setImages(comboEpisode.getImages());
            detailEpisodeVo.setDesc(comboEpisode.getDesc());
            detailEpisodeVo.setStadium(comboEpisode.getStadium());
            detailEpisodeVo.setWeather(comboEpisode.getWeather());
            detailEpisodeVo.setJudge(comboEpisode.getJudge());
            detailEpisodeVo.setDuration(comboEpisode.getDuration());
            detailEpisodeVo.setPeriods(comboEpisode.getPeriods());
            detailEpisodeVo.setType(comboEpisode.getType().ordinal());
            detailEpisodeVo.setChatRoomId(comboEpisode.getChatRoomId());
            detailEpisodeVo.setActivities(comboEpisode.getAppActivities());
            detailEpisodeVo.setLiveUniqueId(comboEpisode.getLiveUniqueId());
            detailEpisodeVo.setPartnerType(comboEpisode.getPartnerType());
            detailEpisodeVo.setPrice(comboEpisode.getPrice());
            detailEpisodeVo.setSsportsMid(comboEpisode.getXinyingMatchId());
            //是否章鱼猜球
            detailEpisodeVo.setIsOctopus(LeNumberUtils.toBoolean(comboEpisode.isIsOctopus()) ? 1 : 0);
            detailEpisodeVo.setOctopusMatchId(comboEpisode.getOctopusMatchId());
            detailEpisodeVo.setDeleted(LeNumberUtils.toBoolean(comboEpisode.isDeleted()) ? 1 : 0);
            //全媒体无重点比赛
            detailEpisodeVo.setKey(0);
            setDetailEpisodeVideos(detailEpisodeVo, comboEpisode);
            setDetailTextLiveInfo(detailEpisodeVo, comboEpisode);
            setTimeSection(detailEpisodeVo);
            detailEpisodeVo.settLiveLink4H5(detailEpisodeVo.getIsTextLive() == 1 ? String.format(H5_LIVELINK, detailEpisodeVo.getMid()) : StringUtils.EMPTY);
            List<TCompetitor> competitors = comboEpisode.getCompetitors();
            if (CollectionUtils.isNotEmpty(competitors)) {
                for (int i = 0; i < competitors.size(); i++) {
                    TCompetitor tCompetitor = competitors.get(i);
                    HallEpisodeVo.Competitor competitor = detailEpisodeVo.new Competitor();
                    competitor.setId(tCompetitor.getId());
                    competitor.setName(tCompetitor.getName());
                    competitor.setOfficialName(tCompetitor.getOfficialName());
                    competitor.setLogo(tCompetitor.getLogoUrl());
                    competitor.setPnglogo(tCompetitor.getPngLogo());
                    competitor.setBgWebUrl(tCompetitor.getBgWebUrl());
                    if (tCompetitor.getGround() != null) {
                        competitor.setGround(tCompetitor.getGround());
                    } else {
                        if (i == 0) {
                            competitor.setGround(GroundType.HOME);
                        }
                        if (i == 1) {
                            competitor.setGround(GroundType.AWAY);
                        }
                    }
                    competitor.setCompetitorType(tCompetitor.getCompetitorType());
                    competitor.setScore(tCompetitor.getFinalResult());
                    competitor.setIsFocused(LeNumberUtils.toBoolean(tCompetitor.isIsFocused()) ? 1 : 0);
                    List<TSectionResult> sectionResults = tCompetitor.getSectionResults();
                    if (CollectionUtils.isNotEmpty(sectionResults)) {
                        for (TSectionResult tSectionResult : sectionResults) {
                            HallEpisodeVo.Competitor.SectionResult sectionResult = competitor.new SectionResult();
                            sectionResult.setOrder(tSectionResult.getOrder());
                            sectionResult.setResult(tSectionResult.getResult());
                            sectionResult.setSectionId(tSectionResult.getSectionId());
                            sectionResult.setSection(tSectionResult.getSection());
                            competitor.getSectionResults().add(sectionResult);
                        }
                    }
                    //阵营信息
                    Camp tCamp = tCompetitor.getCampEntity();
                    if (tCamp != null) {
                        HallEpisodeVo.Competitor.Camp camp = competitor.new Camp();
                        camp.setId(tCamp.getId());
                        camp.setName(tCamp.getName());
                        camp.setPicture(tCamp.getPicture());
                        competitor.setCamp(camp);
                    }
                    detailEpisodeVo.getCompetitors().add(competitor);
                }
            }
            List<TTag> tags = comboEpisode.getTags();
            if (CollectionUtils.isNotEmpty(tags)) {
                for (TTag tTag : tags) {
                    DetailEpisodeVo.Tag tag = detailEpisodeVo.new Tag();
                    tag.setId(tTag.getId());
                    tag.setName(tTag.getName());
                    detailEpisodeVo.getTags().add(tag);
                }
            }
            setDetailLiveInfo(detailEpisodeVo, comboEpisode, caller.getCallerId());
            setLiveInfo(detailEpisodeVo, comboEpisode, caller.getCallerId());
            //新英只有大陆有
            if (caller.getCountry() == CountryCode.CN) {
                detailEpisodeVo.setXinyingPay(comboEpisode.isXinyingPay() ? 1 : 0);
                buildXinyingPlayUrl(detailEpisodeVo, comboEpisode.getXinyingMatchId(), caller.getCallerId());

                //兼容安卓isLive不为1的时候隐藏播放器的bug
                if (caller.getCallerId() == 1003 && comboEpisode.isXinyingPay()) {
                    detailEpisodeVo.setIsLive(1);
                }

            }
        } catch (Exception e) {
            LOG.error("DetailEpisodeVo error ! comboEpisodeId = {}, e = {}", comboEpisode.getId(), e.getMessage(), e);
        }
        return detailEpisodeVo;
    }


    /**
     * 批量刷新比分状态的接口
     *
     * @param comboEpisode
     * @return
     */
    public PollingEpisodeVo createPollingEpisodeVo(TComboEpisode comboEpisode) {
        if (comboEpisode == null) {
            return null;
        }
        PollingEpisodeVo pollingEpisodeVo = new PollingEpisodeVo();
        pollingEpisodeVo.setId(comboEpisode.getId());
        pollingEpisodeVo.setMid(comboEpisode.getMid());
        pollingEpisodeVo.setDeleted(LeNumberUtils.toBoolean(comboEpisode.isDeleted()) ? 1 : 0);
        pollingEpisodeVo.setMatchStatus(comboEpisode.getMatchStatus() == null ? -1 : comboEpisode.getMatchStatus().ordinal());
        pollingEpisodeVo.setStatus(comboEpisode.getStatus() == null ? -1 : comboEpisode.getStatus().ordinal());
        pollingEpisodeVo.setTextLiveStatus(comboEpisode.getTextLiveStatus() == null ? -1 : comboEpisode.getTextLiveStatus().ordinal());
        List<TCompetitor> competitors = comboEpisode.getCompetitors();
        if (CollectionUtils.isNotEmpty(competitors)) {
            for (int i = 0; i < competitors.size(); i++) {
                TCompetitor tCompetitor = competitors.get(i);
                PollingEpisodeVo.Competitor competitor = pollingEpisodeVo.new Competitor();
                competitor.setId(tCompetitor.getId());
                competitor.setName(tCompetitor.getName());
                if (tCompetitor.getGround() != null) {
                    competitor.setGround(tCompetitor.getGround());
                } else {
                    if (i == 0) {
                        competitor.setGround(GroundType.HOME);
                    }
                    if (i == 1) {
                        competitor.setGround(GroundType.AWAY);
                    }
                }
                competitor.setScore(tCompetitor.getFinalResult());
                List<TSectionResult> sectionResults = tCompetitor.getSectionResults();
                if (CollectionUtils.isNotEmpty(sectionResults)) {
                    for (TSectionResult tSectionResult : sectionResults) {
                        PollingEpisodeVo.Competitor.SectionResult sectionResult = competitor.new SectionResult();
                        sectionResult.setOrder(tSectionResult.getOrder());
                        sectionResult.setResult(tSectionResult.getResult());
                        sectionResult.setSectionId(tSectionResult.getSectionId());
                        sectionResult.setSection(tSectionResult.getSection());
                        competitor.getSectionResults().add(sectionResult);
                    }
                }
                pollingEpisodeVo.getCompetitors().add(competitor);
            }
        }
        if (comboEpisode.getCurrentMoment() != null) {
            PollingEpisodeVo.CurrentMoment currentMoment = pollingEpisodeVo.new CurrentMoment();
            TCurrentMoment tCurrentMoment = comboEpisode.getCurrentMoment();
            currentMoment.setSectionName(tCurrentMoment.getSectionName());
            currentMoment.setTime(tCurrentMoment.getTime());
            currentMoment.setSort(tCurrentMoment.getSort());
            pollingEpisodeVo.setCurrentMoment(currentMoment);
        }
        return pollingEpisodeVo;
    }

    /**
     * app批量刷新比分状态的接口
     *
     * @param comboEpisode
     * @return
     */
    public PollingEpisodeVo createPollingEpisodeVo4App(TComboEpisode comboEpisode) {
        if (comboEpisode == null) {
            return null;
        }
        PollingEpisodeVo pollingEpisodeVo = new PollingEpisodeVo();
        pollingEpisodeVo.setId(comboEpisode.getId());
        pollingEpisodeVo.setMid(comboEpisode.getMid());
        pollingEpisodeVo.setMatchStatus(comboEpisode.getMatchStatus() == null ? -1 : comboEpisode.getMatchStatus().ordinal());
        pollingEpisodeVo.setStatus(comboEpisode.getStatus() == null ? -1 : comboEpisode.getStatus().ordinal());
        pollingEpisodeVo.setTextLiveStatus(comboEpisode.getTextLiveStatus() == null ? -1 : comboEpisode.getTextLiveStatus().ordinal());
        List<TCompetitor> competitors = comboEpisode.getCompetitors();
        if (CollectionUtils.isNotEmpty(competitors)) {
            for (int i = 0; i < competitors.size(); i++) {
                TCompetitor tCompetitor = competitors.get(i);
                PollingEpisodeVo.Competitor competitor = pollingEpisodeVo.new Competitor();
                competitor.setId(tCompetitor.getId());
                competitor.setName(tCompetitor.getName());
                if (tCompetitor.getGround() != null) {
                    competitor.setGround(tCompetitor.getGround());
                } else {
                    if (i == 0) {
                        competitor.setGround(GroundType.HOME);
                    }
                    if (i == 1) {
                        competitor.setGround(GroundType.AWAY);
                    }
                }
                competitor.setScore(tCompetitor.getFinalResult());
                pollingEpisodeVo.getCompetitors().add(competitor);
            }

        }
        return pollingEpisodeVo;
    }

    /**
     * 通过dicId获取dicName
     *
     * @param dicId
     * @return
     */
    private String getDicName(Long dicId, CallerParam caller) {
        if (dicId == null || dicId == 0l)
            return null;
        TDictEntry dic = QmtConfigApis.getTDictEntryById(dicId, caller);
        if (dic != null) {
            return dic.getName();
        } else {
            return null;
        }
    }

    /**
     * 根据节目类型为cname和logo赋值,和app的showName
     *
     * @param comboEpisode
     * @param episode
     */
    private void fillEpisodeCnameAndLogo(TComboEpisode comboEpisode, HallEpisodeVo episode, CallerParam caller) {
        //节目上的赛事,专辑图已rpc为准,不需要web层在另赋值了
        episode.setLogo(comboEpisode.getLogo());
        if (comboEpisode.getType() != null) {
            switch (comboEpisode.getType()) {
                case MATCH:
                    TCompetition competition = getCompetition(comboEpisode.getCid(), caller);
                    if (competition == null) {
						break;
                    }
					episode.setCname(competition.getName());
                    //赛事短名称
                    String competitionName = competition.getAbbreviation();
                    if (StringUtils.isBlank(competitionName)) {
                        //如果赛事短名称是空就不拼接了
                        episode.setShowName(StringUtils.EMPTY);
                        break;
                    }
                    StringBuffer sb = new StringBuffer(competitionName);
                    if (StringUtils.isNotBlank(comboEpisode.getDiscipline())) {
                        sb.append(BLANK_SPACE).append(comboEpisode.getDiscipline());
                    }
                    if (StringUtils.isNotBlank(comboEpisode.getGroup())) {
                        sb.append(BLANK_SPACE).append(comboEpisode.getGroup());
                    }
                    if (StringUtils.isNotBlank(comboEpisode.getStage())) {
                        sb.append(BLANK_SPACE).append(comboEpisode.getStage());
                    }
                    if (StringUtils.isNotBlank(comboEpisode.getRound())) {
                        sb.append(BLANK_SPACE).append(comboEpisode.getRound());
                    }
                    episode.setShowName(sb.toString());
                    break;
                case PROGRAM:
                    TProgramAlbum tAlbum = getAlbum(comboEpisode.getAid(), caller);
                    if (tAlbum != null) {
                        episode.setCname(tAlbum.getName());
                        //episode.setLogo(tAlbum.getLogo());
                        //专辑短名称 - 全媒体专辑无短名称
                        String albumName = tAlbum.getName();
                        if (StringUtils.isBlank(albumName)) {
                            //如果专辑短名称是空就不拼接了
                            episode.setShowName(StringUtils.EMPTY);
                            break;
                        }
                        episode.setShowName(albumName);
                    } else {
                        String abbreviation = comboEpisode.getAbbreviation();
                        if (StringUtils.isNotBlank(abbreviation)) {
                            //其它节目的短名称
                            episode.setShowName(abbreviation);
                        }
                    }
                    break;
            }
        }
    }

    /**
     * 根据节目类型为cname和logo赋值,和tv的showName
     *
     * @param comboEpisode
     * @param episode
     */
    private void fillEpisodeCnameAndLogo(TComboEpisode comboEpisode, HallEpisodeVo4Tv episode, CallerParam caller) {
        //节目上的赛事,专辑图已rpc为准,不需要web层在另赋值了
        episode.setLogo(comboEpisode.getLogo());
        switch (comboEpisode.getType()) {
            case MATCH:
                TCompetition competition = getCompetition(comboEpisode.getCid(), caller);
                if (competition != null) {
                    episode.setCname(competition.getName());
                } else {
                    episode.setShowName(StringUtils.EMPTY);
                    break;
                }
                //赛事短名称
                String competitionName = competition.getAbbreviation();
                if (StringUtils.isBlank(competitionName)) {
                    //如果赛事短名称是空就不拼接了
                    episode.setShowName(StringUtils.EMPTY);
                    break;
                }
                StringBuffer sb = new StringBuffer(competitionName);
                if (StringUtils.isNotBlank(comboEpisode.getDiscipline())) {
                    sb.append(BLANK_SPACE).append(comboEpisode.getDiscipline());
                }
                if (StringUtils.isNotBlank(comboEpisode.getGroup())) {
                    sb.append(BLANK_SPACE).append(comboEpisode.getGroup());
                }
                if (StringUtils.isNotBlank(comboEpisode.getStage())) {
                    sb.append(BLANK_SPACE).append(comboEpisode.getStage());
                }
                if (StringUtils.isNotBlank(comboEpisode.getRound())) {
                    sb.append(BLANK_SPACE).append(comboEpisode.getRound());
                }
                episode.setShowName(sb.toString());
                break;
            case PROGRAM:
                TProgramAlbum tAlbum = getAlbum(comboEpisode.getAid(), caller);
                if (tAlbum != null) {
                    episode.setCname(tAlbum.getName());
                    //专辑短名称 - 全媒体中专辑无短名称
                    String albumName = tAlbum.getName();
                    if (StringUtils.isBlank(albumName)) {
                        //如果专辑短名称是空就不拼接了
                        episode.setShowName(StringUtils.EMPTY);
                        break;
                    }
                    episode.setShowName(albumName);
                } else {
                    String abbreviation = comboEpisode.getAbbreviation();
                    if (StringUtils.isNotBlank(abbreviation)) {
                        //其它节目的短名称
                        episode.setShowName(abbreviation);
                    }
                }
                break;
        }
    }


    /**
     * 获取赛事
     *
     * @param cid
     * @return
     */
    private TCompetition getCompetition(Long cid, CallerParam caller) {
        if (cid == null || cid == 0l)
            return null;
        TCompetition tCompetition = SbdCompetitionApis.getTCompetitionById(cid, caller);
        if (tCompetition != null) {
            return tCompetition;
        } else {
            return null;
        }
    }

    /**
     * 获取专辑
     *
     * @param aid
     * @return
     */
    private TProgramAlbum getAlbum(Long aid, CallerParam caller) {
        if (aid == null || aid == 0l)
            return null;
        TProgramAlbum tProgramAlbum = QmtSbcApis.getTProgramAlbumById(aid, caller);
        if (tProgramAlbum != null) {
            return tProgramAlbum;
        } else {
            return null;
        }
    }

    /**
     * 日历 - 比赛属于的时段
     *
     * @param calEpisodeVo
     * @param current_hours
     * @return
     */
    private CalEpisodeVo setTimeSection(CalEpisodeVo calEpisodeVo, String current_hours) {
        if (StringUtils.isNotBlank(current_hours) && calEpisodeVo.getStatus() == Constants.MATCH_STATUS_MATCHING) {
            calEpisodeVo.setTimeSection(Integer.parseInt(current_hours));
        } else {
            String startTime = calEpisodeVo.getStartTime();
            if (StringUtils.isNotBlank(startTime) && startTime.length() >= 10) {
                calEpisodeVo.setTimeSection(Integer.parseInt(startTime.substring(8, 10)));
            } else {
                calEpisodeVo.setTimeSection(1);
            }
        }
        return calEpisodeVo;
    }

    /**
     * 比赛大厅 - 比赛属于的时段
     *
     * @param hallEpisodeVo
     * @return
     */
    private HallEpisodeVo setTimeSection(HallEpisodeVo hallEpisodeVo) {
        String startTime = hallEpisodeVo.getStartTime();
        if (StringUtils.isNotBlank(startTime) && startTime.length() >= 10) {
            hallEpisodeVo.setTimeSection(Integer.parseInt(startTime.substring(8, 10)));
        } else {
            hallEpisodeVo.setTimeSection(1);
        }
        return hallEpisodeVo;
    }

    /**
     * 比赛大厅 - 比赛属于的时段 4TV
     *
     * @param hallEpisodeVo
     * @return
     */
    private HallEpisodeVo4Tv setTimeSection(HallEpisodeVo4Tv hallEpisodeVo) {
        String startTime = hallEpisodeVo.getStartTime();
        if (StringUtils.isNotBlank(startTime) && startTime.length() >= 10) {
            hallEpisodeVo.setTimeSection(Integer.parseInt(startTime.substring(8, 10)));
        } else {
            hallEpisodeVo.setTimeSection(1);
        }
        return hallEpisodeVo;
    }

    /**
     * 添加视频信息
     *
     * @param hallEpisodeVo
     * @param comboEpisode
     * @return
     */
    private HallEpisodeVo setEpisodeVideos(HallEpisodeVo hallEpisodeVo, TComboEpisode comboEpisode) {
        List<TSimpleVideo> videos = comboEpisode.getVideos();
        if (CollectionUtils.isNotEmpty(videos)) {
            for (TSimpleVideo video : videos) {
                if (video.getType().ordinal() == Constants.VIDEO_TYPE_RECORD) {
                    hallEpisodeVo.setIsRecorded(1);
                }
                if (video.getType().ordinal() == Constants.VIDEO_TYPE_HIGHLIGHTS) {
                    hallEpisodeVo.setIsHighlights(1);
                }
            }
        }
        return hallEpisodeVo;
    }

    /**
     * 添加视频的详细信息
     *
     * @param simpleEpisodeVo
     * @param comboEpisode
     * @return
     */
    private SimpleEpisodeVo setEpisodeVideos(SimpleEpisodeVo simpleEpisodeVo, TComboEpisode comboEpisode) {
        List<TSimpleVideo> videos = comboEpisode.getVideos();
        if (CollectionUtils.isNotEmpty(videos)) {
            Set<Long> vidSet = Sets.newHashSet();
            for (TSimpleVideo video : videos) {
                if (video.getType().ordinal() == Constants.VIDEO_TYPE_RECORD) {
                    simpleEpisodeVo.setIsRecorded(1);
                    simpleEpisodeVo.setRecordedId(video.getVid());
                } else if (video.getType().ordinal() == Constants.VIDEO_TYPE_HIGHLIGHTS) {
                    simpleEpisodeVo.setIsHighlights(1);
                    simpleEpisodeVo.setHighlightsId(video.getVid());
                } else {
                    vidSet.add(video.getVid());
                }
            }
        }
        return simpleEpisodeVo;
    }

    /**
     * 添加视频的详细信息
     *
     * @param detailEpisodeVo
     * @param comboEpisode
     * @return
     */
    private DetailEpisodeVo setDetailEpisodeVideos(DetailEpisodeVo detailEpisodeVo, TComboEpisode comboEpisode) {
        List<TSimpleVideo> videos = comboEpisode.getVideos();
        if (CollectionUtils.isNotEmpty(videos)) {
            Set<Long> vidSet = Sets.newHashSet();
            for (TSimpleVideo video : videos) {
                if (video.getType().ordinal() == Constants.VIDEO_TYPE_RECORD) {
                    detailEpisodeVo.setIsRecorded(1);
                    detailEpisodeVo.setRecordedId(video.getVid());
                    if (video.getIsPay() == 1) {
                        detailEpisodeVo.setRecordedPay(1);
                    }
                } else if (video.getType().ordinal() == Constants.VIDEO_TYPE_HIGHLIGHTS) {
                    detailEpisodeVo.setIsHighlights(1);
                    detailEpisodeVo.setHighlightsId(video.getVid());
                    if (video.getIsPay() == 1) {
                        detailEpisodeVo.setHighlightsPay(1);
                    }
                } else if (video.getType().ordinal() == Constants.VIDEO_TYPE_MATCH_REPORT) {
                    detailEpisodeVo.setReportId(video.getVid());
                } else {
                    vidSet.add(video.getVid());
                }
            }
            if (CollectionUtils.isNotEmpty(vidSet)) {
                detailEpisodeVo.setVideoIds(vidSet);
            }
        }
        return detailEpisodeVo;
    }


    private Long getSplatIdFromCaller(long callerId) {
        TCaller tCaller = QmtConfigApis.getTCallerById(callerId);
        if (null == tCaller) {
            throw new IllegalArgumentException("illegal callerId : " + callerId);
        }

        return tCaller.getSplatId();

    }

    /**
     * 添加直播的详细信息
     *
     * @param detailEpisodeVo
     * @param comboEpisode
     * @return
     */
    public DetailEpisodeVo setDetailLiveInfo(DetailEpisodeVo detailEpisodeVo, TComboEpisode comboEpisode, long callerId) {

        List<TLiveStream> streams = comboEpisode.getStreams();
        if (CollectionUtils.isNotEmpty(streams)) {
            for (TLiveStream stream : streams) {
                if (isSetLiveByCaller(callerId, stream, comboEpisode)) {
                    detailEpisodeVo.setIsLive(0);
                    break;
                }
                detailEpisodeVo.setIsLive(1);
                DetailEpisodeVo.Lives lives = detailEpisodeVo.new Lives();
                lives.setDrmFlag(stream.getDrmFlag());
                lives.setLiveId(stream.getLiveId());
                lives.setChatRoomId(stream.getChatRoomId());
                lives.setName(stream.getName());
                lives.setLiveStatus(null != stream.getStatus() ? stream.getStatus().ordinal() : 0);
                lives.setViewPic(stream.getViewPic());
                if (stream.getIsPay() == 0) {
                    lives.setIsPay(0);
                } else {
                    List<Long> platforms = stream.getPayPlatforms();
                    if (platforms.contains(getSplatIdFromCaller(callerId))) {
                        lives.setIsPay(1);
                    } else {
                        lives.setIsPay(0);
                    }
                }
                Map<String, String> images = stream.getImages();
                if (images != null) {
                    //因为此尺寸的图会导致iOS客户端崩溃,则先剔除掉
                    images.remove("pic5_1920_1080");
                    lives.setImages(stream.getImages());
                } else {
                    lives.setImages(new HashMap<>());
                }
                detailEpisodeVo.getLives().add(lives);
            }
        } else {
            detailEpisodeVo.setIsLive(0);
        }
        return detailEpisodeVo;
    }


    /**
     * 添加直播的详细信息4Tv
     *
     * @param hallEpisodeVo4Tv
     * @param comboEpisode
     * @return
     */

    private HallEpisodeVo4Tv setDetailLiveInfo4Tv(HallEpisodeVo4Tv hallEpisodeVo4Tv, TComboEpisode comboEpisode) {
        List<TLiveStream> streams = comboEpisode.getStreams();
        if (CollectionUtils.isNotEmpty(streams)) {
            for (TLiveStream stream : streams) {
                HallEpisodeVo4Tv.Lives lives = hallEpisodeVo4Tv.new Lives();
                lives.setLiveId(stream.getLiveId());
                lives.setChatRoomId(stream.getChatRoomId());
                lives.setName(stream.getName());
                if (stream.getIsPay() == 0) {
                    lives.setIsPay(0);
                } else {
                    List<Long> platforms = stream.getPayPlatforms();
                    if (platforms.contains(getSplatIdFromCaller(LeConstants.LESPORTS_TV_CALLER_CODE))) {
                        lives.setIsPay(1);
                    } else {
                        lives.setIsPay(0);
                    }
                }

                hallEpisodeVo4Tv.getLives().add(lives);
            }
        }

        return hallEpisodeVo4Tv;
    }

    /**
     * 图文直播详细信息
     *
     * @param detailEpisodeVo
     * @param comboEpisode
     * @return
     */
    private DetailEpisodeVo setDetailTextLiveInfo(DetailEpisodeVo detailEpisodeVo, TComboEpisode comboEpisode) {
        List<TSimpleTextLive> tSimpleTextLives = comboEpisode.getTextlives();
        if (CollectionUtils.isNotEmpty(tSimpleTextLives)) {
            detailEpisodeVo.setIsTextLive(1);
            for (TSimpleTextLive tSimpleTextLive : tSimpleTextLives) {
                DetailEpisodeVo.TextLive textLive = detailEpisodeVo.new TextLive();
                textLive.setTextLiveId(tSimpleTextLive.getTextLiveId());
                textLive.setType(tSimpleTextLive.getType());
                detailEpisodeVo.getTextLives().add(textLive);
            }
        } else {
            detailEpisodeVo.setIsTextLive(0);
        }
        return detailEpisodeVo;
    }

    /**
     * 图文直播信息
     *
     * @param hallEpisodeVo
     * @param comboEpisode
     * @return
     */
    private HallEpisodeVo setTextLiveInfo(HallEpisodeVo hallEpisodeVo, TComboEpisode comboEpisode) {
        List<TSimpleTextLive> tSimpleTextLives = comboEpisode.getTextlives();
        if (CollectionUtils.isNotEmpty(tSimpleTextLives)) {
            hallEpisodeVo.setIsTextLive(1);
        } else {
            hallEpisodeVo.setIsTextLive(0);
        }
        return hallEpisodeVo;
    }

    /**
     * 添加直播信息
     *
     * @param hallEpisodeVo
     * @param comboEpisode
     * @return
     */
    public HallEpisodeVo setLiveInfo(HallEpisodeVo hallEpisodeVo, TComboEpisode comboEpisode, long callerId) {
        List<TLiveStream> streams = comboEpisode.getStreams();
        Map<String, String> returnMap = Maps.newHashMap();
        if (CollectionUtils.isNotEmpty(streams)) {
            for (TLiveStream stream : streams) {
                hallEpisodeVo.setIsLive(1);
                Map<String, String> picAll = stream.getImages();
                if (picAll != null) {
                    if (picAll.get(Constants.LIVE_VIDEO_IMAGE_169) != null) {//400*225
                        returnMap.put(Constants.VIDEO_NEWS_IMAGE_169, picAll.get(Constants.LIVE_VIDEO_IMAGE_169));
                    } else {
                        returnMap.put(Constants.VIDEO_NEWS_IMAGE_169, "");
                    }
                    if (picAll.get(Constants.LIVE_VIDEO_IMAGE_BIG_169) != null) {//960*540
                        returnMap.put(Constants.VIDEO_NEWS_IMAGE_BIG_169, picAll.get(Constants.LIVE_VIDEO_IMAGE_BIG_169));
                    } else {
                        returnMap.put(Constants.VIDEO_NEWS_IMAGE_BIG_169, "");
                    }
                    if (isSetLiveByCaller(callerId, stream, comboEpisode)) {
                        hallEpisodeVo.setIsLive(0);
                        hallEpisodeVo.setImageUrl(returnMap);
                        break;
                    }
                    hallEpisodeVo.setImageUrl(returnMap);
                    break;
                } else {
                    LOG.error("live images is null ! eid = {}", comboEpisode.getId());
                }
            }
        } else {
            returnMap.put(Constants.VIDEO_NEWS_IMAGE_169, "");
            returnMap.put(Constants.VIDEO_NEWS_IMAGE_BIG_169, "");
            hallEpisodeVo.setImageUrl(returnMap);
            hallEpisodeVo.setIsLive(0);
        }
        String imageUrl = comboEpisode.getImageUrl();
        if (StringUtils.isNotBlank(imageUrl)) {
            String imageUrl169 = "";
            if (StringUtils.isNotBlank(imageUrl) && (imageUrl.endsWith(".jpg") || imageUrl.endsWith(".png"))) {
                StringBuffer sb = new StringBuffer(imageUrl.substring(0, imageUrl.length() - 4));
                sb.append("/169_400_225").append(imageUrl.substring(imageUrl.length() - 4, imageUrl.length()));
                imageUrl169 = sb.toString();
            }
            returnMap.put(Constants.VIDEO_NEWS_IMAGE_169, imageUrl169);
            returnMap.put(Constants.VIDEO_NEWS_IMAGE_BIG_169, imageUrl169.replaceAll("400_225", "960_540"));
            hallEpisodeVo.setImageUrl(returnMap);
        }
        return hallEpisodeVo;
    }


    /**
     * 添加直播信息 4TV
     *
     * @param hallEpisodeVo
     * @param comboEpisode
     * @return
     */
    private HallEpisodeVo4Tv setLiveInfo(HallEpisodeVo4Tv hallEpisodeVo, TComboEpisode comboEpisode, CallerParam caller) {
        List<TLiveStream> streams = comboEpisode.getStreams();
        Map<String, String> returnMap = Maps.newHashMap();
        if (CollectionUtils.isNotEmpty(streams)) {
            for (TLiveStream stream : streams) {
                hallEpisodeVo.setIsLive(1);
                Map<String, String> picAll = stream.getImages();
                if (picAll != null) {
                    if (picAll.get(Constants.LIVE_VIDEO_IMAGE_169) != null) {//400*225
                        returnMap.put(Constants.VIDEO_NEWS_IMAGE_169, picAll.get(Constants.LIVE_VIDEO_IMAGE_169));
                    } else {
                        returnMap.put(Constants.VIDEO_NEWS_IMAGE_169, "");
                    }
                    if (picAll.get(Constants.LIVE_VIDEO_IMAGE_BIG_169) != null) {//960*540
                        returnMap.put(Constants.VIDEO_NEWS_IMAGE_BIG_169, picAll.get(Constants.LIVE_VIDEO_IMAGE_BIG_169));
                    } else {
                        returnMap.put(Constants.VIDEO_NEWS_IMAGE_BIG_169, "");
                    }
                    if (isSetLiveByCaller(caller.getCallerId(), stream, comboEpisode)) {
                        hallEpisodeVo.setIsLive(0);
                        hallEpisodeVo.setImageUrl(returnMap);
                        break;
                    }
                    hallEpisodeVo.setImageUrl(returnMap);
                    break;
                } else {
                    LOG.error("live images is null ! eid = {}", comboEpisode.getId());
                }
            }
        } else {
            returnMap.put(Constants.VIDEO_NEWS_IMAGE_169, "");
            returnMap.put(Constants.VIDEO_NEWS_IMAGE_BIG_169, "");
            hallEpisodeVo.setImageUrl(returnMap);
            hallEpisodeVo.setIsLive(0);
        }
        String imageUrl = comboEpisode.getImageUrl();
        if (StringUtils.isNotBlank(imageUrl)) {
            String imageUrl169 = "";
            if (StringUtils.isNotBlank(imageUrl) && (imageUrl.endsWith(".jpg") || imageUrl.endsWith(".png"))) {
                StringBuffer sb = new StringBuffer(imageUrl.substring(0, imageUrl.length() - 4));
                sb.append("/169_400_225").append(imageUrl.substring(imageUrl.length() - 4, imageUrl.length()));
                imageUrl169 = sb.toString();
            }
            returnMap.put(Constants.VIDEO_NEWS_IMAGE_169, imageUrl169);
            returnMap.put(Constants.VIDEO_NEWS_IMAGE_BIG_169, imageUrl169.replaceAll("400_225", "960_540"));
            hallEpisodeVo.setImageUrl(returnMap);
        }

        //香港的app和tv没有任何角标,兼容下
        if (caller.getCountry() == CountryCode.HK && Lists.newArrayList(3002L).contains(caller.getCallerId())) {
            hallEpisodeVo.setIsEpisodePay(0);
        } else {
            hallEpisodeVo.setIsEpisodePay(LeNumberUtils.toBoolean(comboEpisode.isIsPay()) ? 1 : 0);
        }
        return hallEpisodeVo;
    }

    /**
     * mobile、tv如果直播流id为-1的话则为确认没有直播权限,则是否直播置为否
     * pc 如果直播流id为-1且有图文直播，则是否直播置为否
     *
     * @param callerId
     * @param stream
     * @param comboEpisode
     * @return
     */
    private boolean isSetLiveByCaller(long callerId, TLiveStream stream, TComboEpisode comboEpisode) {
        boolean mobileAndTvIsLive = (LeConstants.LESPORTS_PC_CALLER_CODE != callerId && stream.getLiveId().equals("-1"));
        boolean pcIsLive = LeConstants.LESPORTS_PC_CALLER_CODE == callerId && stream.getLiveId().equals("-1") && CollectionUtils.isNotEmpty(comboEpisode.getTextlives());
        return mobileAndTvIsLive || pcIsLive;
    }

    public ZhangyuEpisodeVo createZhangYuEpisode(TComboEpisode comboEpisode) {
        ZhangyuEpisodeVo hallEpisodeVo = new ZhangyuEpisodeVo();
        try {
            if (comboEpisode == null) {
                return null;
            }
            hallEpisodeVo.setId(comboEpisode.getId());
            hallEpisodeVo.setName(comboEpisode.getName());
            hallEpisodeVo.setStatus(comboEpisode.getStatus() == null ? -1 : comboEpisode.getStatus().ordinal());
            hallEpisodeVo.setStartTime(comboEpisode.getStartTime());
            hallEpisodeVo.setMoment(comboEpisode.getMoment());
            hallEpisodeVo.setPlayLink(comboEpisode.getPlayLink());
            if (StringUtils.isBlank(hallEpisodeVo.getPlayLink()) && StringUtils.isNotBlank(comboEpisode.getTLiveLink())) {
                hallEpisodeVo.setPlayLink(comboEpisode.getTLiveLink());
            }
            hallEpisodeVo.setOctopusMatchId(comboEpisode.getOctopusMatchId());
            List<TCompetitor> competitors = comboEpisode.getCompetitors();
            if (CollectionUtils.isNotEmpty(competitors)) {
                for (int i = 0; i < competitors.size(); i++) {
                    TCompetitor tCompetitor = competitors.get(i);
                    ZhangyuEpisodeVo.Competitor competitor = hallEpisodeVo.new Competitor();
                    competitor.setId(tCompetitor.getId());
                    competitor.setName(tCompetitor.getName());
                    competitor.setLogo(tCompetitor.getLogoUrl());
                    if (tCompetitor.getGround() != null) {
                        competitor.setGround(tCompetitor.getGround());
                    } else {
                        if (i == 0) {
                            competitor.setGround(GroundType.HOME);
                        }
                        if (i == 1) {
                            competitor.setGround(GroundType.AWAY);
                        }
                    }
                    competitor.setScore(tCompetitor.getFinalResult());
                    hallEpisodeVo.getCompetitors().add(competitor);
                }
            }
        } catch (Exception e) {
            LOG.error("createZhangYuEpisode error ! comboEpisodeId = {}, e = {}", comboEpisode.getId(), e.getMessage(), e);
        }
        return hallEpisodeVo;
    }


    /**
     * 添加可观看直播的会员信息
     *
     * @param hallEpisodeVo
     * @param liveUniqueId
     */
    public void addVipInfos(HallEpisodeVo hallEpisodeVo, String liveUniqueId) {
        List<BossMembersPackageApi.VipPackages> VipPackages = BossMembersPackageApi.getVipPackage(liveUniqueId);
        if (CollectionUtils.isNotEmpty(VipPackages)) {
            List<HallEpisodeVo.VipInfos> vipInfoses = Lists.newArrayList();
            for (BossMembersPackageApi.VipPackages vipPackage : VipPackages) {
                HallEpisodeVo.VipInfos vipInfo = hallEpisodeVo.new VipInfos();
                vipInfo.setName(vipPackage.getName());
                vipInfo.setProductId(vipPackage.getProductId());
                vipInfoses.add(vipInfo);
            }
            //按照会员ID从大到小排列
            if (CollectionUtils.isNotEmpty(vipInfoses)) {
                Collections.sort(vipInfoses, new Comparator<HallEpisodeVo.VipInfos>() {
                    @Override
                    public int compare(HallEpisodeVo.VipInfos o1, HallEpisodeVo.VipInfos o2) {
                        return o2.getProductId() - o1.getProductId();
                    }
                });
            }
            hallEpisodeVo.getVipInfos().addAll(vipInfoses);
        }
    }

    public void addVipInfos(HallEpisodeVo4Tv hallEpisodeVo, String liveUniqueId) {
        List<BossMembersPackageApi.VipPackages> VipPackages = BossMembersPackageApi.getVipPackage(liveUniqueId);
        if (CollectionUtils.isNotEmpty(VipPackages)) {
            for (BossMembersPackageApi.VipPackages vipPackage : VipPackages) {
                HallEpisodeVo4Tv.VipInfos vipInfo = hallEpisodeVo.new VipInfos();
                vipInfo.setName(vipPackage.getName());
                vipInfo.setProductId(vipPackage.getProductId());
                if (vipPackage.getProductId().equals(41)) {
                    vipInfo.setType(1);
                }
                if (vipPackage.getProductId().equals(22)) {
                    vipInfo.setType(2);
                }
                if (vipPackage.getProductId().equals(39)) {
                    vipInfo.setType(3);
                }
                hallEpisodeVo.getVipInfos().add(vipInfo);
            }
        }
    }
}
