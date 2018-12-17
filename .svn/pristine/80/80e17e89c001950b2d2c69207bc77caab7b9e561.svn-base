package com.lesports.qmt.op.web.api.core.creater;

import com.google.common.collect.Maps;
import com.lesports.api.common.CallerParam;
import com.lesports.api.common.CountryCode;
import com.lesports.qmt.QmtConstants;
import com.lesports.qmt.config.api.dto.TDictEntry;
import com.lesports.qmt.config.api.dto.TTag;
import com.lesports.qmt.config.client.QmtConfigApis;
import com.lesports.qmt.op.web.api.core.util.Constants;
import com.lesports.qmt.op.web.api.core.util.ImageUtils;
import com.lesports.qmt.op.web.api.core.vo.CalEpisodeVo;
import com.lesports.qmt.op.web.api.core.vo.DetailEpisodeVo;
import com.lesports.qmt.op.web.api.core.vo.HallEpisodeVo;
import com.lesports.qmt.op.web.api.core.vo.baidu.BaiduEpisodeVo;
import com.lesports.qmt.sbc.api.common.VideoContentType;
import com.lesports.qmt.sbc.api.dto.*;
import com.lesports.qmt.sbc.client.QmtSbcResourceApis;
import com.lesports.qmt.sbd.api.common.GroundType;
import com.lesports.qmt.sbd.api.dto.TCompetition;
import com.lesports.qmt.sbd.api.dto.TCompetitor;
import com.lesports.qmt.sbd.api.dto.TSectionResult;
import com.lesports.qmt.sbd.client.SbdCompetitionApis;
import com.lesports.utils.LeDateUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component("episodeVoCreater")
public class EpisodeVoCreater {

    private static final Logger LOG = LoggerFactory.getLogger(EpisodeVoCreater.class);

    private static final String H5_LIVELINK = "http://m.lesports.com/app/match/%s.html";


    /**
     * @param tComboEpisode
     * @return
     */
    public BaiduEpisodeVo createBaiduEpisodeVo(TComboEpisode tComboEpisode) {
        BaiduEpisodeVo baiduEpisodeVo = new BaiduEpisodeVo();
        try {
            if (null != tComboEpisode) {
                baiduEpisodeVo.setTitle(tComboEpisode.getName());
                baiduEpisodeVo.setGame_type(tComboEpisode.getCompetitionName());
                baiduEpisodeVo.setStart_time(LeDateUtils.formatYMDHMS(LeDateUtils.parseYYYYMMDDHHMMSS(tComboEpisode.getStartTime())));
                baiduEpisodeVo.setMatch_state(tComboEpisode.getStatus().getValue());
                fillCompetitors(tComboEpisode, baiduEpisodeVo);
                baiduEpisodeVo.setPay_view(tComboEpisode.isXinyingPay() ? 1 : 0);
                baiduEpisodeVo.setGame_round(tComboEpisode.getRound());
                baiduEpisodeVo.setVs(tComboEpisode.isVs() ? 1 : 0);
                List<TLiveStream> tLiveStreams = tComboEpisode.getStreams();
                String liveUrl = "";
                String highlightUrl = "";
                String recordUrl = "";
                if (CollectionUtils.isNotEmpty(tLiveStreams)) {
                    TLiveStream tLiveStream = tLiveStreams.get(0);
                    liveUrl = "http://live.letv.com/sports/play/index.shtml?id=" + tLiveStream.getLiveId();
                }
                List<TSimpleVideo> tSimpleVideos = tComboEpisode.getVideos();
                if (CollectionUtils.isNotEmpty(tSimpleVideos)) {
                    for (TSimpleVideo tSimpleVideo : tSimpleVideos) {
                        if (tSimpleVideo.getType().equals(VideoContentType.HIGHLIGHTS)) {
                            highlightUrl = "http://www.letv.com/ptv/vplay/" + tSimpleVideo.getVid() + ".html";
                        } else if (tSimpleVideo.getType().equals(VideoContentType.RECORD)) {
                            recordUrl = "http://www.letv.com/ptv/vplay/" + tSimpleVideo.getVid() + ".html";
                        }
                    }
                }
                baiduEpisodeVo.setLive_url(liveUrl);
                baiduEpisodeVo.setHighlight_url(highlightUrl);
                baiduEpisodeVo.setGame_record_url(recordUrl);
            }

        } catch (Exception e) {
            LOG.error("createBaiduEpisodeVo error ! episodeId = {}, e = {}", tComboEpisode.getId(), e);
        }
        return baiduEpisodeVo;
    }

    private void fillCompetitors(TComboEpisode tComboEpisode, BaiduEpisodeVo baiduEpisodeVo) {
        List<TCompetitor> competitors = tComboEpisode.getCompetitors();
        if (CollectionUtils.isNotEmpty(competitors)) {
            for (TCompetitor competitor : competitors) {
                if (competitor.getGround().equals(GroundType.HOME)) {
                    baiduEpisodeVo.setHome_team(competitor.getName());
                    baiduEpisodeVo.setHome_logo(ImageUtils.joinImageLogo(competitor.getLogoUrl(), "11_75_75"));
                    baiduEpisodeVo.setHome_score(competitor.getFinalResult());
                } else if (competitor.getGround().equals(GroundType.AWAY)) {
                    baiduEpisodeVo.setGuest_team(competitor.getName());
                    baiduEpisodeVo.setGuest_logo(ImageUtils.joinImageLogo(competitor.getLogoUrl(), "11_75_75"));
                    baiduEpisodeVo.setGuest_score(competitor.getFinalResult());
                }
            }
        }
    }


    public CalEpisodeVo createCalEpisodeVo(TComboEpisode comboEpisode) {
        CalEpisodeVo calEpisodeVo = new CalEpisodeVo();
        try {
            if (comboEpisode == null) {
                return null;
            }
            switch (comboEpisode.getType()) {
                case MATCH:
                    calEpisodeVo.setCname(comboEpisode.getCompetitionName());
                    break;
                case PROGRAM:
                    calEpisodeVo.setCname(comboEpisode.getAlbumName());
                    break;
            }
            calEpisodeVo.setType(comboEpisode.getType().ordinal());
            calEpisodeVo.setId(comboEpisode.getId());
            calEpisodeVo.setMid(comboEpisode.getMid());
            calEpisodeVo.setCid(comboEpisode.getCid());
            calEpisodeVo.setVs(comboEpisode.isVs() ? 1 : 0);
            calEpisodeVo.setName(comboEpisode.getName());
            calEpisodeVo.setStartTime(comboEpisode.getStartTime());
            calEpisodeVo.setStartTimeStamp(LeDateUtils.parseYYYYMMDDHHMMSS(comboEpisode.getStartTime().trim()).getTime() / 1000);
            calEpisodeVo.setRound(comboEpisode.getRound());
            calEpisodeVo.setStage(comboEpisode.getStage());
            calEpisodeVo.setGroup(comboEpisode.getGroup());
            calEpisodeVo.setGameFType(comboEpisode.getGameFType());
            calEpisodeVo.setGameSType(comboEpisode.getGameSType());
            calEpisodeVo.setPlayLink(comboEpisode.getPlayLink());
            calEpisodeVo.setMatchStatus(comboEpisode.getMatchStatus() == null ? -1 : comboEpisode.getMatchStatus().ordinal());
            calEpisodeVo.setStatus(comboEpisode.getStatus() == null ? -1 : comboEpisode.getStatus().ordinal());
            List<TCompetitor> competitors = comboEpisode.getCompetitors();
            if (CollectionUtils.isNotEmpty(competitors)) {
                for (TCompetitor tCompetitor : competitors) {
                    CalEpisodeVo.Competitor competitor = calEpisodeVo.new Competitor();
                    competitor.setId(tCompetitor.getId());
                    competitor.setName(tCompetitor.getName());
                    competitor.setLogo(tCompetitor.getLogoUrl());
                    competitor.setGround(tCompetitor.getGround());
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
            LOG.error("createCalEpisodeVo error ! comboEpisodeId = {}, e = {}", comboEpisode.getId(), e);
        }
        return calEpisodeVo;
    }

    /**
     * 为比赛大厅的episode赋值
     *
     * @param comboEpisode
     * @return
     */
    public HallEpisodeVo createHallEpisodeVo(TComboEpisode comboEpisode,CallerParam caller) {
        HallEpisodeVo hallEpisodeVo = new HallEpisodeVo();
        try {
            if (comboEpisode == null) {
                return null;
            }
            hallEpisodeVo.setType(comboEpisode.getType().ordinal());
//            fillEpisodeCnameAndLogo(comboEpisode, hallEpisodeVo,caller);
			if (caller.getCountry() == CountryCode.CN) {
				hallEpisodeVo.setXinyingPay(comboEpisode.isXinyingPay() ? 1 : 0);
			}
            hallEpisodeVo.setId(comboEpisode.getId());
            hallEpisodeVo.setCid(comboEpisode.getCid());
            hallEpisodeVo.setVs(comboEpisode.isVs() ? 1 : 0);
            hallEpisodeVo.setName(comboEpisode.getName());
            hallEpisodeVo.setStartTime(comboEpisode.getStartTime());
            hallEpisodeVo.setStatus(comboEpisode.getStatus() == null ? -1 : comboEpisode.getStatus().ordinal());
            hallEpisodeVo.setRound(comboEpisode.getRound());
            hallEpisodeVo.setStage(comboEpisode.getStage());
            hallEpisodeVo.setGroup(comboEpisode.getGroup());
            hallEpisodeVo.setGameFName(getDicName(comboEpisode.getGameFType(),caller));
            hallEpisodeVo.setGameSName(getDicName(comboEpisode.getGameSType(),caller));
            hallEpisodeVo.setMatchSystem(comboEpisode.getMatchSystem());
            hallEpisodeVo.setMoment(comboEpisode.getMoment());
            hallEpisodeVo.setPlayLink(comboEpisode.getPlayLink());
            hallEpisodeVo.settLiveLink(comboEpisode.getTLiveLink());
            hallEpisodeVo.setLogo(comboEpisode.getLogo());
            setEpisodeVideos(hallEpisodeVo, comboEpisode);
            setLiveInfo(hallEpisodeVo, comboEpisode);
            setTextLiveInfo(hallEpisodeVo, comboEpisode);

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
                    List<TSectionResult> sectionResults = tCompetitor.getSectionResults();
                    if (CollectionUtils.isNotEmpty(sectionResults)) {
                        for (TSectionResult tSectionResult : sectionResults) {
                            HallEpisodeVo.Competitor.SectionResult sectionResult = competitor.new SectionResult();
                            sectionResult.setOrder(tSectionResult.getOrder());
                            sectionResult.setResult(tSectionResult.getResult());
                            sectionResult.setSection(tSectionResult.getSection());
                            competitor.getSectionResults().add(sectionResult);
                        }
                    }
                    hallEpisodeVo.getCompetitors().add(competitor);
                }
            }
        } catch (Exception e) {
            LOG.error("createHallEpisodeVo error ! comboEpisodeId = {}, e = {}", comboEpisode.getId(), e);
        }
        return hallEpisodeVo;
    }

//    /**
//     * 根据节目类型为cname和logo赋值,和app的showName
//     *
//     * @param comboEpisode
//     * @param episode
//     */
//    private void fillEpisodeCnameAndLogo(TComboEpisode comboEpisode, HallEpisodeVo episode,CallerParam caller) {
//        switch (comboEpisode.getType()) {
//            case MATCH:
//                TCompetition competition = getCompetition(comboEpisode.getCid(),caller);
//                if (competition != null) {
//                    episode.setCname(competition.getName());
//                    episode.setLogo(competition.getLogoUrl());
//
//                    //赛事短名称
//                    String competitionName = competition.getAbbreviation();
//                    if (StringUtils.isBlank(competitionName)) {
//                        //如果赛事短名称是空就不拼接了
//                        episode.setShowName(StringUtils.EMPTY);
//                        break;
//                    }
//                    episode.setShowName(competitionName);
//                }
//
//                break;
//            case PROGRAM:
//                TAlbum tAlbum = getAlbum(comboEpisode.getAid(),caller);
//                if (tAlbum != null) {
//                    episode.setCname(tAlbum.getName());
//                    //专辑短名称
//                    String albumName = tAlbum.getAbbreviation();
//                    if (StringUtils.isBlank(albumName)) {
//                        //如果专辑短名称是空就不拼接了
//                        episode.setShowName(StringUtils.EMPTY);
//                        break;
//                    }
//                    episode.setShowName(albumName);
//                } else {
//                    String abbreviation = comboEpisode.getAbbreviation();
//                    if (StringUtils.isNotBlank(abbreviation)) {
//                        //其它节目的短名称
//                        episode.setShowName(abbreviation);
//                    }
//                }
//                break;
//        }
//    }


    /**
     * 获取赛事
     *
     * @param cid
     * @return
     */
    private TCompetition getCompetition(Long cid,CallerParam caller) {
        if (cid == null || cid == 0)
            return null;
        TCompetition tCompetition = SbdCompetitionApis.getTCompetitionById(cid,caller);
        if (tCompetition != null) {
            return tCompetition;
        } else {
            return null;
        }
    }

//    /**
//     * 获取专辑
//     *
//     * @param aid
//     * @return
//     */
//    private TAlbum getAlbum(Long aid,CallerParam caller) {
//        if (aid == null || aid == 0)
//            return null;
//        TAlbum tAlbum = QmtSbcResourceApis.getTAlbumById(aid, caller);
//        if (tAlbum != null) {
//            return tAlbum;
//        } else {
//            return null;
//        }
//    }


    /**
     * 获取节目详情
     *
     * @param comboEpisode
     * @return
     */
    public DetailEpisodeVo createDetailEpisodeVo(TComboEpisode comboEpisode,CallerParam caller) {
        DetailEpisodeVo detailEpisodeVo = new DetailEpisodeVo();
        try {
            if (comboEpisode == null) {
                return null;
            }
			if (caller.getCountry() == CountryCode.CN) {
				detailEpisodeVo.setXinyingPay(comboEpisode.isXinyingPay() ? 1 : 0);
			}
            detailEpisodeVo.setId(comboEpisode.getId());
            detailEpisodeVo.setCid(comboEpisode.getCid());

            if (comboEpisode.getCid() != 0) {
                detailEpisodeVo.setCname(comboEpisode.getCompetitionName());
            } else if (comboEpisode.getAid() != 0) {
                detailEpisodeVo.setCname(comboEpisode.getAlbumName());
            }
            detailEpisodeVo.setVs(comboEpisode.isVs() ? 1 : 0);
            detailEpisodeVo.setName(comboEpisode.getName());
            detailEpisodeVo.setStartTime(comboEpisode.getStartTime());
            detailEpisodeVo.setStatus(comboEpisode.getStatus() == null ? -1 : comboEpisode.getStatus().ordinal());
            detailEpisodeVo.setRound(comboEpisode.getRound());
            detailEpisodeVo.setStage(comboEpisode.getStage());
            detailEpisodeVo.setGroup(comboEpisode.getGroup());
            detailEpisodeVo.setMatchSystem(comboEpisode.getMatchSystem());
            detailEpisodeVo.setGameFName(getDicName(comboEpisode.getGameFType(),caller));
            detailEpisodeVo.setGameSName(getDicName(comboEpisode.getGameSType(),caller));
            detailEpisodeVo.setPlayLink(comboEpisode.getPlayLink());
            detailEpisodeVo.setMoment(comboEpisode.getMoment());
            detailEpisodeVo.setImages(comboEpisode.getImages());
            detailEpisodeVo.setDesc(comboEpisode.getDesc());
            detailEpisodeVo.setDuration(comboEpisode.getDuration());
            detailEpisodeVo.setPeriods(comboEpisode.getPeriods());
            detailEpisodeVo.setType(comboEpisode.getType().ordinal());
            setDetailEpisodeVideos(detailEpisodeVo, comboEpisode);
            setDetailLiveInfo(detailEpisodeVo, comboEpisode);
            setLiveInfo(detailEpisodeVo, comboEpisode);
            List<TCompetitor> competitors = comboEpisode.getCompetitors();
            if (CollectionUtils.isNotEmpty(competitors)) {
                for (TCompetitor tCompetitor : competitors) {
                    HallEpisodeVo.Competitor competitor = detailEpisodeVo.new Competitor();
                    competitor.setId(tCompetitor.getId());
                    competitor.setName(tCompetitor.getName());
                    competitor.setLogo(tCompetitor.getLogoUrl());
                    competitor.setPnglogo(tCompetitor.getPngLogo());
                    competitor.setBgWebUrl(tCompetitor.getBgWebUrl());
                    competitor.setGround(tCompetitor.getGround());
                    competitor.setCompetitorType(tCompetitor.getCompetitorType());
                    competitor.setScore(tCompetitor.getFinalResult());
                    List<TSectionResult> sectionResults = tCompetitor.getSectionResults();
                    if (CollectionUtils.isNotEmpty(sectionResults)) {
                        for (TSectionResult tSectionResult : sectionResults) {
                            HallEpisodeVo.Competitor.SectionResult sectionResult = competitor.new SectionResult();
                            sectionResult.setOrder(tSectionResult.getOrder());
                            sectionResult.setResult(tSectionResult.getResult());
                            sectionResult.setSection(tSectionResult.getSection());
                            competitor.getSectionResults().add(sectionResult);
                        }
                    }
                    detailEpisodeVo.getCompetitors().add(competitor);
                }
            }
        } catch (Exception e) {
            LOG.error("DetailEpisodeVo error ! comboEpisodeId = {}, e = {}", comboEpisode.getId(), e);
        }
        return detailEpisodeVo;
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
            for (TSimpleVideo video : videos) {
                DetailEpisodeVo.SimpleVideo simpleVideo = detailEpisodeVo.new SimpleVideo();
                simpleVideo.setName(video.getName());
                simpleVideo.setType(video.getType());
                simpleVideo.setVid(video.getVid());
                simpleVideo.setImageUrl(video.getImageUrl());
                detailEpisodeVo.getVideos().add(simpleVideo);
            }
        }
        return detailEpisodeVo;
    }

    /**
     * 添加直播的详细信息
     *
     * @param detailEpisodeVo
     * @param comboEpisode
     * @return
     */
    private DetailEpisodeVo setDetailLiveInfo(DetailEpisodeVo detailEpisodeVo, TComboEpisode comboEpisode) {
        List<TLiveStream> streams = comboEpisode.getStreams();
        if (CollectionUtils.isEmpty(streams)) {
            detailEpisodeVo.setIsLive(0);
            return detailEpisodeVo;
        }
        TLiveStream stream = streams.get(0);
        if (stream.getLiveId().equals("-1")) {
            detailEpisodeVo.setIsLive(0);
            return detailEpisodeVo;
        }
        detailEpisodeVo.setIsLive(1);
        DetailEpisodeVo.Lives lives = detailEpisodeVo.new Lives();
        lives.setLiveId(stream.getLiveId());
        lives.setName(stream.getName());
        lives.setLiveStatus(stream.getStatus().ordinal());
        lives.setImages(stream.getImages());
        detailEpisodeVo.getLives().add(lives);
        return detailEpisodeVo;
    }

    /**
     * 添加直播信息
     *
     * @param hallEpisodeVo
     * @param comboEpisode
     * @return
     */
    private HallEpisodeVo setLiveInfo(HallEpisodeVo hallEpisodeVo, TComboEpisode comboEpisode) {
        List<TLiveStream> streams = comboEpisode.getStreams();
        Map<String, String> returnMap = Maps.newHashMap();
        if (CollectionUtils.isEmpty(streams) || streams.get(0).getLiveId().equals("-1")) {
            returnMap.put(Constants.VIDEO_NEWS_IMAGE_169, "");
            returnMap.put(Constants.VIDEO_NEWS_IMAGE_BIG_169, "");
            hallEpisodeVo.setImageUrl(returnMap);
            hallEpisodeVo.setIsLive(0);
            return hallEpisodeVo;
        }

        for (TLiveStream stream : streams) {
            if (stream.getLiveId().equals("-1")) {
                hallEpisodeVo.setIsLive(0);
                returnMap.put(Constants.VIDEO_NEWS_IMAGE_169, "");
                returnMap.put(Constants.VIDEO_NEWS_IMAGE_BIG_169, "");
                hallEpisodeVo.setImageUrl(returnMap);
                break;
            }
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
                hallEpisodeVo.setImageUrl(returnMap);
                break;
            } else {
                LOG.error("live images is null ! eid = {}", comboEpisode.getId());
            }
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
     * 添加视频信息
     *
     * @param hallEpisodeVo
     * @param comboEpisode
     * @return
     */
    private HallEpisodeVo setEpisodeVideos(HallEpisodeVo hallEpisodeVo, TComboEpisode comboEpisode) {
        //如果是NBA 不传录播和回放视频
        if(hallEpisodeVo.getCid()==44001){
            return hallEpisodeVo;
        }
        List<TSimpleVideo> videos = comboEpisode.getVideos();
        if (CollectionUtils.isNotEmpty(videos)) {
            for (TSimpleVideo video : videos) {
                if (video.getType().ordinal() == Constants.VIDEO_TYPE_RECORD) {
                    hallEpisodeVo.setIsRecorded(1);
                    hallEpisodeVo.setRecordedId(video.getVid());
                }
                if (video.getType().ordinal() == Constants.VIDEO_TYPE_HIGHLIGHTS) {
                    hallEpisodeVo.setIsHighlights(1);
                    hallEpisodeVo.setHighlightsId(video.getVid());
                }
            }
        }
        return hallEpisodeVo;
    }

    /**
     * 通过dicId获取dicName
     *
     * @param dicId
     * @return
     */
    private String getDicName(Long dicId,CallerParam caller) {
        if (dicId == null || dicId == 0)
            return null;

        TDictEntry dic = QmtConfigApis.getTDictEntryById(dicId, caller);
        if (dic != null) {
            return dic.getName();
        } else {
            return null;
        }
    }

}
