package com.lesports.qmt.web.creater;

import com.google.common.collect.Maps;
import com.lesports.LeConstants;
import com.lesports.api.common.CallerParam;
import com.lesports.qmt.config.api.dto.TDictEntry;
import com.lesports.qmt.config.api.dto.TTag;
import com.lesports.qmt.config.client.QmtConfigApis;
import com.lesports.qmt.sbc.api.dto.*;
import com.lesports.qmt.sbc.client.QmtSbcApis;
import com.lesports.qmt.sbc.client.QmtSbcEpisodeApis;
import com.lesports.qmt.sbd.api.dto.TCompetition;
import com.lesports.qmt.sbd.api.dto.TCompetitor;
import com.lesports.qmt.sbd.client.SbdCompetitionApis;
import com.lesports.qmt.web.model.HallEpisodeVo;
import com.lesports.utils.LeDateUtils;
import com.lesports.utils.pojo.SearchData;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.*;

/**
* Created by pangchuanxiao on 2015/6/30.
*/
@Component
public class SearchResultAdapter {

    private static final String BLANK_SPACE = " ";

    //回放
    public static final Integer VIDEO_TYPE_RECORD = 0;
    //集锦
    public static final Integer VIDEO_TYPE_HIGHLIGHTS = 1;

    //视频新闻的图片4:3
    public static final String VIDEO_NEWS_IMAGE_43 = "400*300";
    //视频图片 PC用 16:9的
    public static final String VIDEO_NEWS_IMAGE_169 = "400*225";
    //视频图片 需要确认大图 16:9的
    public static final String VIDEO_NEWS_IMAGE_BIG_169 = "960*540";
    //直播图片 PC用 16:9的
    public static final String LIVE_VIDEO_IMAGE_169 = "pic3_400_225";
    //直播图片 16:9的
    public static final String LIVE_VIDEO_IMAGE_BIG_169 = "pic2_960_540";

    private static final Logger LOG = LoggerFactory.getLogger(SearchResultAdapter.class);

    public HallEpisodeVo adapt(SearchData.LiveData.GameInfo gameInfo, CallerParam callerParam) {
        HallEpisodeVo episodeVo = null;
        long episodeId = QmtSbcEpisodeApis.getEpisodeIdByLiveId(gameInfo.getId(), callerParam);
        if (episodeId > 0) {
            TComboEpisode episode = QmtSbcEpisodeApis.getTComboEpisodeById(episodeId,callerParam);
            if (null != episode) {
                episodeVo = createHallEpisodeVo(episode, callerParam);
            }
        }
        return episodeVo;
    }

    /**
     * 获取赛事
     *
     * @param cid
     * @return
     */
    private TCompetition getCompetition(Long cid,CallerParam callerParam) {
        if (cid == null && cid == 0)
            return null;
        TCompetition tCompetition = SbdCompetitionApis.getTCompetitionById(cid, callerParam);
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
    private TProgramAlbum getAlbum(Long aid,CallerParam callerParam) {
        if (aid == null && aid == 0)
            return null;
        TProgramAlbum tAlbum = QmtSbcApis.getTProgramAlbumById(aid, callerParam);
        if (tAlbum != null) {
            return tAlbum;
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
    private void fillEpisodeCnameAndLogo(TComboEpisode comboEpisode, HallEpisodeVo episode,CallerParam callerParam) {
        switch (comboEpisode.getType()) {
            case MATCH:
                TCompetition competition = getCompetition(comboEpisode.getCid(),callerParam);
                if (competition != null) {
                    episode.setCname(competition.getName());
                    episode.setLogo(competition.getLogoUrl());
                }
                //赛事短名称
                String competitionName = competition.getAbbreviation();
                if (StringUtils.isBlank(competitionName)) {
                    //如果赛事短名称是空就不拼接了
                    episode.setShowName(StringUtils.EMPTY);
                    break;
                }
                StringBuffer sb = new StringBuffer(competitionName);
                if (comboEpisode.getMatchSystem() != null) {
                    switch (comboEpisode.getMatchSystem()) {
                        case CUP:
                            if (StringUtils.isNotBlank(comboEpisode.getStage())) {
                                sb.append(BLANK_SPACE).append(comboEpisode.getStage());
                                if (StringUtils.isNotBlank(comboEpisode.getGroup())) {
                                    sb.append(BLANK_SPACE).append(comboEpisode.getGroup());
                                }
                                episode.setShowName(sb.toString());
                                break;
                            }
                            episode.setShowName(StringUtils.EMPTY);
                        case LEAGUE:
                            if (StringUtils.isNotBlank(comboEpisode.getRound())) {
                                sb.append(BLANK_SPACE).append(comboEpisode.getRound());
                                episode.setShowName(sb.toString());
                            }
                            episode.setShowName(sb.toString());
                            break;
                    }
                } else {
                    if (StringUtils.isNotBlank(comboEpisode.getStage())) {
                        sb.append(BLANK_SPACE).append(comboEpisode.getStage());
                    }
                    if (StringUtils.isNotBlank(comboEpisode.getGroup())) {
                        sb.append(BLANK_SPACE).append(comboEpisode.getGroup());
                    }
                    if (StringUtils.isNotBlank(comboEpisode.getSubstation())) {
                        sb.append(BLANK_SPACE).append(comboEpisode.getSubstation());
                    }
                    episode.setShowName(sb.toString());
                    break;
                }
                break;
            case PROGRAM:
                TProgramAlbum tAlbum = getAlbum(comboEpisode.getAid(),callerParam);
                if (tAlbum != null) {
                    episode.setCname(tAlbum.getName());
                    //episode.setLogo(tAlbum.getLogo());
                    //专辑短名称
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
     * 通过dicId获取dicName
     *
     * @param dicId
     * @return
     */
    private String getDicName(Long dicId,CallerParam callerParam) {
        if (dicId == null && dicId == 0)
            return null;
        TDictEntry dic = QmtConfigApis.getTDictEntryById(dicId, callerParam);
        if (dic != null) {
            return dic.getName();
        } else {
            return null;
        }
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
                if (video.getType().ordinal() == VIDEO_TYPE_RECORD) {
                    hallEpisodeVo.setIsRecorded(1);
                }
                if (video.getType().ordinal() == VIDEO_TYPE_HIGHLIGHTS) {
                    hallEpisodeVo.setIsHighlights(1);
                }
            }
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
    private HallEpisodeVo setLiveInfo(HallEpisodeVo hallEpisodeVo, TComboEpisode comboEpisode, long callerId) {
        List<TLiveStream> streams = comboEpisode.getStreams();
        Map<String, String> returnMap = Maps.newHashMap();
        if (CollectionUtils.isNotEmpty(streams)) {
            for (TLiveStream stream : streams) {
                hallEpisodeVo.setIsLive(1);
                Map<String, String> picAll = stream.getImages();
                if (picAll != null) {
                    if (picAll.get(LIVE_VIDEO_IMAGE_169) != null) {//400*225
                        returnMap.put(VIDEO_NEWS_IMAGE_169, picAll.get(LIVE_VIDEO_IMAGE_169));
                    } else {
                        returnMap.put(VIDEO_NEWS_IMAGE_169, "");
                    }
                    if (picAll.get(LIVE_VIDEO_IMAGE_BIG_169) != null) {//960*540
                        returnMap.put(VIDEO_NEWS_IMAGE_BIG_169, picAll.get(LIVE_VIDEO_IMAGE_BIG_169));
                    } else {
                        returnMap.put(VIDEO_NEWS_IMAGE_BIG_169, "");
                    }
                    //如果直播流id为-1的话则为确认没有直播权限,则是否直播置为否

                    if ((LeConstants.LESPORTS_MOBILE_APP_CALLER_CODE == callerId || LeConstants.LESPORTS_TV_CALLER_CODE == callerId) &&
                            stream.getLiveId().equals("-1")) {
                        hallEpisodeVo.setIsLive(0);
                        hallEpisodeVo.setImageUrl(returnMap);
                        break;
                    }
                    hallEpisodeVo.setImageUrl(returnMap);
                    break;
                }
            }
        } else {
            returnMap.put(VIDEO_NEWS_IMAGE_169, "");
            returnMap.put(VIDEO_NEWS_IMAGE_BIG_169, "");
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
            returnMap.put(VIDEO_NEWS_IMAGE_169, imageUrl169);
            returnMap.put(VIDEO_NEWS_IMAGE_BIG_169, imageUrl169.replaceAll("400_225", "960_540"));
            hallEpisodeVo.setImageUrl(returnMap);
        }
        return hallEpisodeVo;
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

    private String getWeek(String startDate){
        Date date = LeDateUtils.parseYYYYMMDD(startDate);
        String[] weeks = {"周日","周一","周二","周三","周四","周五","周六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int week_index = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if(week_index<0){
            week_index = 0;
        }
        return weeks[week_index];
    }

    /**
     * 为直播数据的episode赋值
     *
     * @param comboEpisode
     * @return
     */
    public HallEpisodeVo createHallEpisodeVo(TComboEpisode comboEpisode, CallerParam callerParam) {
        HallEpisodeVo hallEpisodeVo = new HallEpisodeVo();
        try {
            if (comboEpisode == null) {
                return null;
            }
            fillEpisodeCnameAndLogo(comboEpisode, hallEpisodeVo,callerParam);
            hallEpisodeVo.setXinyingPay(comboEpisode.isXinyingPay() ? 1 : 0);
            hallEpisodeVo.setCommentId(comboEpisode.getCommentId());
            hallEpisodeVo.setType(comboEpisode.getType().ordinal());
            hallEpisodeVo.setId(comboEpisode.getId());
            hallEpisodeVo.setMid(comboEpisode.getMid());
            hallEpisodeVo.setCid(comboEpisode.getCid());
            hallEpisodeVo.setVs(comboEpisode.isVs() ? 1 : 0);
            hallEpisodeVo.setName(comboEpisode.getName());
            hallEpisodeVo.setStartTime(comboEpisode.getStartTime());
            hallEpisodeVo.setWeek(getWeek(comboEpisode.getStartTime().substring(0,8)));
            hallEpisodeVo.setMatchStatus(comboEpisode.getMatchStatus() == null ? -1 : comboEpisode.getMatchStatus().ordinal());
            hallEpisodeVo.setStatus(comboEpisode.getStatus() == null ? -1 : comboEpisode.getStatus().ordinal());
            hallEpisodeVo.setTextLiveStatus(comboEpisode.getTextLiveStatus() == null ? -1 : comboEpisode.getTextLiveStatus().ordinal());
            hallEpisodeVo.setRound(comboEpisode.getRound());
            hallEpisodeVo.setStage(comboEpisode.getStage());
            hallEpisodeVo.setGroup(comboEpisode.getGroup());
            hallEpisodeVo.setGameFType(comboEpisode.getGameFType());
            hallEpisodeVo.setGameSType(comboEpisode.getGameSType());
            hallEpisodeVo.setGameFName(getDicName(comboEpisode.getGameFType(),callerParam));
            hallEpisodeVo.setGameSName(getDicName(comboEpisode.getGameSType(),callerParam));
            hallEpisodeVo.setMatchSystem(comboEpisode.getMatchSystem());
            hallEpisodeVo.setMoment(comboEpisode.getMoment());
            hallEpisodeVo.setPlayLink(comboEpisode.getPlayLink());
            hallEpisodeVo.settLiveLink(comboEpisode.getTLiveLink());
            setEpisodeVideos(hallEpisodeVo, comboEpisode);
            setLiveInfo(hallEpisodeVo, comboEpisode, callerParam.getCallerId());
            setTextLiveInfo(hallEpisodeVo, comboEpisode);
            setTimeSection(hallEpisodeVo);
            List<TCompetitor> competitors = comboEpisode.getCompetitors();
            if (CollectionUtils.isNotEmpty(competitors)) {
                for (TCompetitor tCompetitor : competitors) {
                    HallEpisodeVo.Competitor competitor = hallEpisodeVo.new Competitor();
                    competitor.setId(tCompetitor.getId());
                    competitor.setName(tCompetitor.getName());
                    competitor.setLogo(tCompetitor.getLogoUrl());
                    competitor.setGround(tCompetitor.getGround());
                    competitor.setCompetitorType(tCompetitor.getCompetitorType());
                    competitor.setScore(tCompetitor.getFinalResult());
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
//			}
        } catch (Exception e) {
            LOG.error("createHallEpisodeVo error ! comboEpisodeId = {}, e = {}", comboEpisode.getId(), e);
        }
        return hallEpisodeVo;
    }

}
