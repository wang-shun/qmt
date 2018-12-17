package com.lesports.qmt.sbc.converter;

import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.lesports.api.common.CallerParam;
import com.lesports.api.common.ImageUrlExt;
import com.lesports.api.common.Platform;
import com.lesports.id.client.LeIdApis;
import com.lesports.qmt.QmtConstants;
import com.lesports.qmt.config.api.dto.TActivity;
import com.lesports.qmt.config.api.dto.TCaller;
import com.lesports.qmt.config.client.QmtConfigApis;
import com.lesports.qmt.sbc.api.common.TRelatedItem;
import com.lesports.qmt.sbc.api.common.VideoContentType;
import com.lesports.qmt.sbc.api.dto.*;
import com.lesports.qmt.sbc.converter.support.AbstractSbcTDtoConverter;
import com.lesports.qmt.sbc.helper.RelatedHelper;
import com.lesports.qmt.sbc.model.*;
import com.lesports.qmt.sbc.repository.EpisodeRepository;
import com.lesports.qmt.sbc.repository.LiveRepository;
import com.lesports.qmt.sbc.repository.ProgramAlbumRepository;
import com.lesports.qmt.sbc.repository.VideoRepository;
import com.lesports.qmt.sbc.utils.CommentIdUtils;
import com.lesports.qmt.sbd.api.common.CompetitorType;
import com.lesports.qmt.sbd.api.dto.TCompetition;
import com.lesports.qmt.sbd.api.dto.TCompetitor;
import com.lesports.qmt.sbd.api.dto.TMatch;
import com.lesports.qmt.sbd.api.dto.TSectionResult;
import com.lesports.qmt.sbd.client.SbdCompetitionApis;
import com.lesports.qmt.sbd.client.SbdMatchApis;
import com.lesports.utils.CallerUtils;
import com.lesports.utils.LeProperties;
import com.lesports.utils.math.LeNumberUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import javax.annotation.Resource;
import java.util.*;

/**
 * Created by ruiyuansheng on 2016/3/22.
 */
@Component("comboEpisodeVoConverter")
public class TComboEpisodeConverter extends AbstractSbcTDtoConverter<Episode, TComboEpisode> {
    private static final Logger LOG = LoggerFactory.getLogger(TComboEpisodeConverter.class);

    @Resource
    private EpisodeRepository episodeRepository;
    @Resource
    private VideoRepository videoRepository;
    @Resource
    private ProgramAlbumRepository programAlbumRepository;
    @Resource
    private LiveRepository liveRepository;
    @Resource
    private RelatedHelper relatedHelper;

    private void adaptVoName4Caller(TComboEpisode vo, CallerParam caller) {
        String langName = CallerUtils.getValueOfLanguage(vo.getMultiLangNames(), caller.getLanguage());
        if (StringUtils.isNotEmpty(langName)) {
            vo.setName(langName);
        }
        vo.setMultiLangNamesIsSet(false);
    }


    private void adaptVoCompetitor4Caller(CallerParam caller, TCompetitor tCompetitor) {
        String langName = CallerUtils.getValueOfLanguage(tCompetitor.getMultiLangNames(), caller.getLanguage());
        if (StringUtils.isNotEmpty(langName)) {
            tCompetitor.setName(langName);
        }
        tCompetitor.setMultiLangNamesIsSet(false);

        if (tCompetitor.getCompetitorType() == CompetitorType.TEAM) {
            String teamLogo = CallerUtils.getValueOfCountry(tCompetitor.getMultiCounLogos(), caller.getCountry());
            if (StringUtils.isNotEmpty(teamLogo)) {
                tCompetitor.setLogoUrl(teamLogo);
            }
            tCompetitor.setMultiCounLogosIsSet(false);
        }


        String officialName = CallerUtils.getValueOfLanguage(tCompetitor.getMultiLangOfficialNames(), caller.getLanguage());
        if (StringUtils.isNotEmpty(officialName)) {
            tCompetitor.setOfficialName(officialName);
        }
        tCompetitor.setMultiLangOfficialNamesIsSet(false);
        //球队是否可以被关注
        if (LeNumberUtils.toBoolean(tCompetitor.isIsFocused())) {
            adaptVoFocused(tCompetitor, caller);
        }
        //分节比分
        Iterator<TSectionResult> sectionResultsIterator = tCompetitor.getSectionResultsIterator();
        if (null != sectionResultsIterator) {
            while (sectionResultsIterator.hasNext()) {
                TSectionResult sectionResults = sectionResultsIterator.next();
                if (null != sectionResults) {
                    adaptVoSectionResult4Caller(caller, sectionResults);
                }
            }
        }
    }

    private void adaptVoSectionResult4Caller(CallerParam caller, TSectionResult sectionResults) {
        String langSectionResult = CallerUtils.getValueOfLanguage(sectionResults.getMultiLangSection(), caller.getLanguage());
        if (StringUtils.isNotEmpty(langSectionResult)) {
            sectionResults.setSection(langSectionResult);
        }
        sectionResults.setMultiLangSectionIsSet(false);
    }

    private void adaptVoFocused(TCompetitor tCompetitor, CallerParam caller) {
        //如果可被关注,可被关注的国家列表为空,则直接返回不可被关注
        if (CollectionUtils.isEmpty(tCompetitor.getIsFocusedCountries())) {
            tCompetitor.setIsFocused(false);
        } else {
            if (tCompetitor.getIsFocusedCountries().contains(caller.getCountry())) {
                tCompetitor.setIsFocused(true);
            } else {
                tCompetitor.setIsFocused(false);
            }
        }
    }

    private void adaptVoDict4Caller(TComboEpisode vo, CallerParam caller) {
        String langStage = CallerUtils.getValueOfLanguage(vo.getMultiLangStage(), caller.getLanguage());
        if (StringUtils.isNotEmpty(langStage)) {
            vo.setStage(langStage);
        }
        vo.setMultiLangStageIsSet(false);

        String langGroup = CallerUtils.getValueOfLanguage(vo.getMultiLangGroup(), caller.getLanguage());
        if (StringUtils.isNotEmpty(langGroup)) {
            vo.setGroup(langGroup);
        }
        vo.setMultiLangGroupIsSet(false);

        String langRound = CallerUtils.getValueOfLanguage(vo.getMultiLangRound(), caller.getLanguage());
        if (StringUtils.isNotEmpty(langRound)) {
            vo.setRound(langRound);
        }
        vo.setMultiLangRoundIsSet(false);

        String langDiscipline = CallerUtils.getValueOfLanguage(vo.getMultiLangDiscipline(), caller.getLanguage());
        if (StringUtils.isNotEmpty(langDiscipline)) {
            vo.setDiscipline(langDiscipline);
        }
        vo.setMultiLangDisciplineIsSet(false);

        String langSubstation = CallerUtils.getValueOfLanguage(vo.getMultiLangSubstation(), caller.getLanguage());
        if (StringUtils.isNotEmpty(langSubstation)) {
            vo.setSubstation(langSubstation);
        }
        vo.setMultiLangSubstationIsSet(false);
    }

    private void fillTComboEpisodeWithEpisode(TComboEpisode comboEpisode, Episode episode) {
        comboEpisode.setId(episode.getId());
        comboEpisode.setName(episode.getName());
        comboEpisode.setType(episode.getType());
        comboEpisode.setStartTime(episode.getStartTime());
        comboEpisode.setLiveUniqueId(episode.getLiveUniqueId());
        comboEpisode.setShareName(episode.getShareName());
        comboEpisode.setMid(LeNumberUtils.toLong(episode.getMid()));
		comboEpisode.setAid(LeNumberUtils.toLong(episode.getAid()));
		if (episode.getGameFType() != null && episode.getGameFType() > 0) {
			comboEpisode.setGameFType(episode.getGameFType());
		}
        if (LeNumberUtils.toLong(episode.getLeecoAid()) > 0) {
            comboEpisode.setLeecoAid(episode.getLeecoAid());
        }
        comboEpisode.setTopicUrl(episode.getTopicUrl());
        //add allowCountry
        if (null != episode.getAllowCountry()) {
            comboEpisode.setCountryCode(episode.getAllowCountry());
        }
        //TODO：qmt 暂时不支持
        comboEpisode.setIsUrlRedirect(LeNumberUtils.toBoolean(episode.getHasJump()));
        if (comboEpisode.isIsUrlRedirect()) {
            comboEpisode.setPcRedirectUrl(episode.getPcJumpUrl());
            comboEpisode.setMRedirectUrl(episode.getmJumpUrl());
        }
        //是否可同步到云平台
        comboEpisode.setIsSyncToCloud(LeNumberUtils.toBoolean(episode.getIsSyncToCloud()));
        //只有图文直播以图文直播状态为准
        if (BooleanUtils.isTrue(episode.getHasTextLive()) && !BooleanUtils.isTrue(episode.getHasLive()) && episode.getTextLiveStatus() != null) {
            comboEpisode.setStatus(episode.getTextLiveStatus());
        } else {
            comboEpisode.setStatus(episode.getStatus());
        }
        comboEpisode.setLivePlatforms(episode.getLivePlatforms());
        comboEpisode.setTextLiveStatus(episode.getTextLiveStatus());
        comboEpisode.setCommentId(CommentIdUtils.create(episode.getId()));
        comboEpisode.setPeriods(StringUtils.isEmpty(episode.getPeriods()) ? "" : episode.getPeriods());
        comboEpisode.setXinyingPay(episode.getXinyingPay() == null ? false : episode.getXinyingPay());
        comboEpisode.setPrice(episode.getPrice() != null ? episode.getPrice() : 0.00);
        comboEpisode.setOctopusMatchId(episode.getOctopusMatchId() == null ? 0 : episode.getOctopusMatchId());
        comboEpisode.setIsOctopus(episode.getIsOctopus() == null ? false : episode.getIsOctopus());
        if (episode.getChatRoom() != null) {
            comboEpisode.setChatRoomId(episode.getChatRoom().getChatRoomId());
        }
        if (episode.getXinyingMatchId() != null && episode.getXinyingMatchId() != 0) {
            comboEpisode.setXinyingMatchId(episode.getXinyingMatchId());
        }
        if (episode.getMid() == null && episode.getAid() == null && StringUtils.isNotBlank(episode.getAbbreviation())) {
            comboEpisode.setAbbreviation(episode.getAbbreviation());
        }

        List<TLiveStream> tLiveStreams = Lists.newArrayList();
        boolean isPay = false;
        if (CollectionUtils.isNotEmpty(episode.getLiveStreams())) {
            for (Episode.LiveStream stream : episode.getLiveStreams()) {
                TLiveStream tLiveStream = new TLiveStream();
                Live live = liveRepository.findOne(stream.getId());
                if (live == null) {
                    LOG.warn("the episode:{} for live not exists.", episode.getId(), stream.getId());
                    continue;
                }
                tLiveStream.setLiveId(LeNumberUtils.valueOf(live.getId()));
                tLiveStream.setChatRoomId(comboEpisode.getChatRoomId());
                tLiveStream.setName(live.getCommentaryLanguage());
                tLiveStream.setStatus(live.getStatus());
//                tLiveStream.setImages(Maps.transformValues(live.getCoverImage(), IMAGE_URL_FUNCTION));
                tLiveStream.setOrder(stream.getOrder());
                tLiveStream.setDrmFlag(LeNumberUtils.toBoolean(live.getIsDrm()) ? 1 : 0);
                tLiveStream.setPlatforms(Lists.newArrayList(live.getPlayPlatforms()));
                //TODO:香港需求需要预览图，qmt后台暂未考虑此需求
//                tLiveStream.setViewPic(liveStream.getViewPic());
                tLiveStream.setIsPay(LeNumberUtils.toBoolean(live.getIsPay()) ? 1 : 0);
                if (tLiveStream.getIsPay() == 1) {
                    tLiveStream.setPayPlatforms(Lists.newArrayList(live.getPayPlatforms()));
                }
                tLiveStreams.add(tLiveStream);
            }

            //如果有一路流是免费的则该节目就是免费的,另一种情况在 getTComboEpisodeById 中判断

            for (TLiveStream tLiveStream : tLiveStreams) {
                if (LeNumberUtils.toBoolean(tLiveStream.getIsPay())) {
                    isPay = true;
                } else {
                    isPay = false;
                    break;
                }
            }
            //对各路流通过order进行排序
            try {
                Collections.sort(tLiveStreams, new Comparator<TLiveStream>() {
                    @Override
                    public int compare(TLiveStream o1, TLiveStream o2) {
                        return o1.getOrder() - o2.getOrder();
                    }
                });
            } catch (Exception e) {
                LOG.error("Sorting stream error! eid = {}, e = {}", episode.getId(), e.getMessage(), e);
            }
            comboEpisode.setStreams(tLiveStreams);
        }
        //节目是否付费
        comboEpisode.setIsPay(isPay);
        if (comboEpisode.getType() == com.lesports.qmt.sbc.api.common.EpisodeType.MATCH) {
            //只有图文直播的时候不返回比赛页地址
            if (!(CollectionUtils.isNotEmpty(episode.getTextLives()) && !LeNumberUtils.toBoolean(episode.getHasLive()))) {
                LOG.info("play.url.match:{} ", LeProperties.getString("play.url.match"));
                comboEpisode.setPlayLink(String.format(LeProperties.getString("play.url.match"), episode.getMid()));
            }
        } else if (comboEpisode.getType() == com.lesports.qmt.sbc.api.common.EpisodeType.PROGRAM) {
            if (CollectionUtils.isNotEmpty(tLiveStreams)) {
                String liveId = "";
                for (TLiveStream stream : tLiveStreams) {
                    liveId = stream.getLiveId();
                    if (StringUtils.isNotBlank(liveId)) {
                        comboEpisode.setPlayLink(String.format(LeProperties.getString("play.url.live"), liveId));
                        break;
                    }
                }
            }
        }
        //在后台获取描述信息
        if (StringUtils.isNotBlank(episode.getDesc())) {
            comboEpisode.setDesc(episode.getDesc());
        }
        //comboEpisode的视频集合:集锦、回放（live上）、正片（自制专辑）
        Map<VideoContentType, Long> videoMap = Maps.newHashMap();
        //正片（自制专辑）
        if (LeNumberUtils.toLong(episode.getRecordId()) > 0) {
            try {
                videoMap.put(VideoContentType.FEATURE, episode.getRecordId());
            } catch (Exception e) {
                LOG.error("invaliad video id,id:{}",episode.getRecordId());
            }
        }
        //集锦
        if (LeNumberUtils.toLong(episode.getHighlightsId()) > 0) {
            try {
                videoMap.put(VideoContentType.HIGHLIGHTS, episode.getHighlightsId());
            } catch (Exception e) {
                LOG.error("invaliad video id,id:{}", episode.getHighlightsId());
            }
        }
        //回放,默认取第一路直播的回放作为节目回放
        if(CollectionUtils.isNotEmpty(comboEpisode.getStreams())){
            for (TLiveStream liveStream : comboEpisode.getStreams()) {
                if (liveStream.getOrder() == 1) {
                    Live live = liveRepository.findOne(LeNumberUtils.toLong(liveStream.getLiveId()));
                    try {
                        videoMap.put(VideoContentType.RECORD, live.getRecordId());
                    } catch (Exception e) {
                        LOG.error("invaliad video id,id:{}", live.getRecordId());
                    }
                   break;
                }
            }
        }

        List<TSimpleVideo> tSimpleVideos = Lists.newArrayList();
        if (MapUtils.isNotEmpty(videoMap)) {
            for (Map.Entry<VideoContentType, Long> videoEntry : videoMap.entrySet()) {
                Video video = videoRepository.findOne(videoEntry.getValue());
                if (video != null) {
                    TSimpleVideo tSimpleVideo = new TSimpleVideo();
                    try {
                        tSimpleVideo.setVid(LeIdApis.convertLeSportsVideoIdToMmsVideoId(videoEntry.getValue()));
                    } catch (Exception e) {
                        LOG.error("invaliad vid,id:{}", videoEntry.getValue());
                        continue;
                    }
                    tSimpleVideo.setName(video.getTitle());
                    tSimpleVideo.setType(videoEntry.getKey());
                    if (null != video.getDuration()) {
                        tSimpleVideo.setDuration(video.getDuration());
                    }
                    if (MapUtils.isNotEmpty(video.getImages())) {
                        if (video.getImages().get(QmtConstants.IMAGE_RATE_43) != null) {
                            tSimpleVideo.setImageUrl(video.getImages().get(QmtConstants.IMAGE_RATE_43).getUrl());
                        }
                        //太多图没用，只取200*150的
                        ImageUrlExt imageUrl = video.getImages().get(QmtConstants.IMAGE_RATE_43);
                        if (imageUrl != null) {
                            tSimpleVideo.putToImages(QmtConstants.IMAGE_SIZE_200_150, video.getImages().get(QmtConstants.IMAGE_RATE_43).getUrl());

                        }
                    }
                    tSimpleVideo.setPlatforms(video.getPlayPlatforms());
                    tSimpleVideo.setPayPlatforms(video.getPayPlatforms());
                    tSimpleVideo.setCreateTime(video.getCreateAt());
                    //自制节目使用
                    if (videoEntry.getKey().equals(VideoContentType.FEATURE)) {
                        if (video.getImages().get(QmtConstants.IMAGE_RATE_43) != null) {
                            comboEpisode.putToImages(QmtConstants.VIDEO_NEWS_IMAGE_43, video.getImages().get(QmtConstants.IMAGE_RATE_43).getUrl());

                        }
                        if (video.getImages().get(QmtConstants.IMAGE_RATE_169) != null) {
                            comboEpisode.putToImages(QmtConstants.VIDEO_NEWS_IMAGE_169, video.getImages().get(QmtConstants.IMAGE_RATE_169).getUrl());

                        }
                        comboEpisode.setDuration(LeNumberUtils.toLong(video.getDuration()));
                        //如果后台没有添加描述,则在从回放上取一下
                        if (StringUtils.isBlank(comboEpisode.getDesc())) {
                            comboEpisode.setDesc(video.getDesc());
                        }
                    }
                    tSimpleVideos.add(tSimpleVideo);
                }
            }

            if (CollectionUtils.isNotEmpty(tSimpleVideos)) {
                comboEpisode.setVideos(tSimpleVideos);
            }
        }
        //图文直播
        List<Episode.SimpleTextLive> textLives = Lists.newArrayList(episode.getTextLives());
        if (CollectionUtils.isNotEmpty(textLives)) {
            comboEpisode.setTLiveLink(String.format(LeProperties.getString("tlive.url.match"), episode.getMid()));
            Collections.sort(textLives, new Comparator<Episode.SimpleTextLive>() {
                @Override
                public int compare(Episode.SimpleTextLive o1, Episode.SimpleTextLive o2) {
                    return o1.getType().getValue() - o2.getType().getValue();
                }
            });
            comboEpisode.setTextlives(Lists.transform(textLives, new Function<Episode.SimpleTextLive, TSimpleTextLive>() {
                @Nullable
                @Override
                public TSimpleTextLive apply(@Nullable Episode.SimpleTextLive textLive) {
                    TSimpleTextLive tSimpleTextLive = new TSimpleTextLive();
                    tSimpleTextLive.setTextLiveId(textLive.getTextLiveId());
                    tSimpleTextLive.setType(textLive.getType());
                    return tSimpleTextLive;
                }
            }));
        }

        //单场推广位
        List<TActivity> tActivities = QmtConfigApis.getTActivitiesByEid(episode.getId());

        if (CollectionUtils.isNotEmpty(tActivities)) {
            List<TSimpleActivity> activities = Lists.newArrayList();
            for (TActivity tActivity : tActivities) {
                TSimpleActivity tSimpleActivity = new TSimpleActivity();
                tSimpleActivity.setName(tActivity.getName());
                tSimpleActivity.setActivityId(tActivity.getId());
                tSimpleActivity.setResourceType(tActivity.getResourceType());
                tSimpleActivity.setResourceUrl(tActivity.getResourceUrl());
                tSimpleActivity.setResourceId(tActivity.getResourceId());
                activities.add(tSimpleActivity);
            }
            if (CollectionUtils.isNotEmpty(activities)) {
                comboEpisode.setAppActivities(activities);
            }
        }

        //节目图片，主要使用在无直播流的节目上
        if (episode.getImage() != null) {
            comboEpisode.setImageUrl(episode.getImage().getUrl());
        }
        //节目是否被删除
        comboEpisode.setDeleted(LeNumberUtils.toBoolean(episode.getDeleted()));
        //相关内容
        fillRelatedItems(comboEpisode, episode.getRelatedItems());
        //pc比赛日历logo
        if (episode.getPcImage() != null) {
            comboEpisode.setLogo(episode.getPcImage().getUrl());
        }
        //TODO:TV广告位
//        comboEpisode.setTvAdImageUrl(episode.getTvAdImageUrl());
    }

    private void fillTComboEpisodeWithMatch(TComboEpisode comboEpisode, TMatch match) {
        if (CollectionUtils.isNotEmpty(match.getMultiLangNames())) {
            comboEpisode.setMultiLangNames(match.getMultiLangNames());
        }
        comboEpisode.setMid(match.getId());
//        comboEpisode.setPartnerType(LeNumberUtils.toInt(match.getPartnerType()));
        comboEpisode.setStartTime(match.getStartTime());
        comboEpisode.setMatchStatus(match.getStatus());
        comboEpisode.setMoment(match.getMoment());
        comboEpisode.setTheRoadOrder(LeNumberUtils.toInt(match.getTheRoadOrder()));
        TCompetition competition = SbdCompetitionApis.getTCompetitionById(match.getCid(), CallerUtils.getDefaultCaller());
        if (competition != null) {
            comboEpisode.setMatchSystem(competition.getMatchSystem());
            if (LeNumberUtils.toBoolean(competition.isVs()) || LeNumberUtils.toBoolean(match.isVs())) {
                comboEpisode.setVs(true);
            } else {
                comboEpisode.setVs(false);
            }
            comboEpisode.setCid(LeNumberUtils.toLong(competition.getId()));
            //pc比赛日历的logo以节目上的为准
            if (StringUtils.isBlank(comboEpisode.getLogo())) {
                comboEpisode.setLogo(competition.getLogoUrl());
            }
            comboEpisode.setAbbreviation(competition.getAbbreviation());
            comboEpisode.setCompetitionName(competition.getName());
//            comboEpisode.setGameFType(comboEpisode.getGameFType());
//            comboEpisode.setGameSType(LeNumberUtils.toLong(match.getGameSType()));
//            comboEpisode.setDisciplineId(LeNumberUtils.toLong(match.getDiscipline()));

        }
        if (StringUtils.isNotBlank(match.getRound())) {
            comboEpisode.setRound(match.getRound());
        }
//        if (match.getDiscipline() != null) {
//            DictEntry dictEntry = dictRepository.findOne(match.getDiscipline());
//            if (null != dictEntry) {
//                comboEpisode.setDiscipline(dictEntry.getReadableName());
//                if (CollectionUtils.isNotEmpty(dictEntry.getMultiLangNames())) {
//                    comboEpisode.setMultiLangDiscipline(dictEntry.getMultiLangReadableNames());
//                }
//            }
//        }
        if (StringUtils.isNotBlank(match.getStage())) {
            comboEpisode.setStage(match.getStage());
        }
        if (StringUtils.isNotBlank(match.getGroup())) {
            comboEpisode.setGroup(match.getGroup());
        }
        if (StringUtils.isNotBlank(match.getSubstation())) {
            comboEpisode.setSubstation(match.getSubstation());
        }
        if (CollectionUtils.isNotEmpty(match.getCompetitors())) {
            comboEpisode.setCompetitors(match.getCompetitors());
        }
        if (match.getCurrentMoment() != null) {
            comboEpisode.setCurrentMoment(match.getCurrentMoment());
        }
        //比赛场馆
        comboEpisode.setStadium(match.getVenue());
        //比赛天气
//        comboEpisode.setWeather(match.getWeather());
        //比赛主裁判
//        comboEpisode.setJudge(match.getJudge());
        //分项图片
//        if (match.getDiscipline() != null && match.getDiscipline() > 0) {
//            DictEntry dictEntry = dictRepository.findOne(match.getDiscipline());
//            if (dictEntry != null) {
//                if (StringUtils.isNotBlank(dictEntry.getImgUrl())) {
//                    comboEpisode.setItemUrl(dictEntry.getImgUrl() + ImageUtil.OLY_PNG_SUFFIX); //项目图片
//                }
//            }
//        }
//        comboEpisode.setMedalType(match.getMedalType());
    }

    private void fillTComboEpisodeWithAlbum(TComboEpisode comboEpisode, ProgramAlbum album) {
        comboEpisode.setVs(false);
        if (StringUtils.isBlank(comboEpisode.getLogo())) {
            if (MapUtils.isNotEmpty(album.getLogo())) {
                ImageUrlExt imageUrlExt = (ImageUrlExt) CollectionUtils.get(album.getLogo().values(), 0);
                comboEpisode.setLogo(imageUrlExt.getPath());
            }
        }
//        if (StringUtils.isBlank(comboEpisode.getAbbreviation())) {
//            comboEpisode.setAbbreviation(album.getAbbreviation());
//        }
        comboEpisode.setAlbumName(album.getName());

    }


//    private Map<String, String> getVideoImageMap(Video video) {
//        Map<String, String> returnMap = Maps.newHashMap();
//        Map<String, String> picAll = video.getImages();
//        if (picAll != null) {
//            if (picAll.get(SmsConstants.VIDEO_NEWS_IMAGE_43) != null) {
//                returnMap.put(SmsConstants.VIDEO_NEWS_IMAGE_43, picAll.get(SmsConstants.VIDEO_NEWS_IMAGE_43));
//            } else {
//                returnMap.put(SmsConstants.VIDEO_NEWS_IMAGE_43, "");
//            }
//            if (picAll.get(SmsConstants.VIDEO_NEWS_IMAGE_BIG_169) != null) {//960*540
//                returnMap.put(SmsConstants.VIDEO_NEWS_IMAGE_BIG_169, picAll.get(SmsConstants.VIDEO_NEWS_IMAGE_BIG_169));
//            } else {
//                returnMap.put(SmsConstants.VIDEO_NEWS_IMAGE_BIG_169, "");
//            }
//            if (picAll.get(SmsConstants.VIDEO_NEWS_IMAGE_169) != null) {//400*225
//                returnMap.put(SmsConstants.VIDEO_NEWS_IMAGE_169, picAll.get(SmsConstants.VIDEO_NEWS_IMAGE_169));
//            } else {
//                returnMap.put(SmsConstants.VIDEO_NEWS_IMAGE_169, "");
//            }
//        }
//        return returnMap;
//    }


    @Override
    protected void fillDto(TComboEpisode comboEpisode, Episode episode) {
        fillTComboEpisodeWithEpisode(comboEpisode, episode);
        if (episode.getType() == com.lesports.qmt.sbc.api.common.EpisodeType.MATCH) {
            //是比赛的主节目的话,用比赛信息填充
            TMatch match = SbdMatchApis.getTMatchById(episode.getMid(), null);
            //这里就不判断null了，主节目一定能有对应的比赛
            if (null != match) {
                fillTComboEpisodeWithMatch(comboEpisode, match);
            }
        } else if (episode.getType() == com.lesports.qmt.sbc.api.common.EpisodeType.PROGRAM) {
            ProgramAlbum album = programAlbumRepository.findOne(episode.getAid());
            if (album != null) {
                fillTComboEpisodeWithAlbum(comboEpisode, album);
            }
        }
    }

    @Override
    protected TComboEpisode createEmptyDto() {
        return new TComboEpisode();
    }

    @Override
    public TComboEpisode adaptDto(TComboEpisode vo, CallerParam caller) {
        Preconditions.checkNotNull(vo);
        Preconditions.checkNotNull(caller);
        adaptVoDict4Caller(vo, caller);
        adaptVoName4Caller(vo, caller);
        TCaller tCaller = QmtConfigApis.getTCallerById(caller.getCallerId());

        if (CollectionUtils.isNotEmpty(vo.getStreams())) {
            //根据上线终端过滤直播流
            List<TLiveStream> streams = Lists.newLinkedList(vo.getStreams());
            for (TLiveStream stream : vo.getStreams()) {
                List<Long> platforms = stream.getPlatforms();
                if (CollectionUtils.isNotEmpty(platforms) && !platforms.contains(LeNumberUtils.toLong(tCaller.getSplatId()))) {
                    streams.remove(stream);
                }
            }
            vo.setStreams(streams);
        }
        if (CollectionUtils.isNotEmpty(vo.getVideos())) {
            List<TSimpleVideo> videos = vo.getVideos();
            Iterator<TSimpleVideo> itemIterator = videos.iterator();
            while (itemIterator.hasNext()) {
                TSimpleVideo video = itemIterator.next();
                //根据caller的platForm判断是否付费
                video.setIsPay(0);
                if (CollectionUtils.isNotEmpty(video.getPayPlatforms())) {
                    Platform platform = tCaller.getPlatform();
                    if (video.getPayPlatforms().contains(platform)) {
                        video.setIsPay(1);
                    }
                }
                //根据caller的platForm过滤视频
                Set<Platform> videoPlatforms = video.getPlatforms();
                if (CollectionUtils.isEmpty(videoPlatforms) || !videoPlatforms.contains(tCaller.getPlatform())) {
                    itemIterator.remove();
                }
            }
            vo.setVideos(videos);
        }


        //如果是付费的则去判断此callerId是否付费,只要有一路免费,则为免费
//		if (vo.isIsPay()) {
        if (CollectionUtils.isNotEmpty(vo.getStreams())) {
            for (TLiveStream stream : vo.getStreams()) {
                List<Long> platforms = stream.getPayPlatforms();
                if (CollectionUtils.isNotEmpty(platforms) && platforms.contains(tCaller.getSplatId())) {
                    vo.setIsPay(true);
                } else {
                    vo.setIsPay(false);
                    break;
                }
            }
        } else {
            vo.setIsPay(false);
        }
//		}

        //过滤直播
//        if (!callerHelper.isEpisodeAllowed4Caller(vo, caller.getCallerId())) {
//            //直播流平台不含调用者所属平台,过滤掉直播流
//            if (CollectionUtils.isNotEmpty(vo.getStreams())) {
//                TLiveStream stream = vo.getStreams().get(0);
//                stream.setLiveId("-1");
//                vo.setStreams(Lists.newArrayList(stream));
//            }
//        }

        return vo;
    }

    private void fillRelatedItems(TComboEpisode vo, List<RelatedItem> relatedItems) {
        List<TRelatedItem> tRelatedItems = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(relatedItems)) {
            for (RelatedItem relatedItem : relatedItems) {
                TRelatedItem tRelatedItem = new TRelatedItem();
                tRelatedItem.setId(LeNumberUtils.toLong(relatedItem.getId()));
                tRelatedItem.setType(relatedItem.getType());
                String name = relatedHelper.getNameByEntityId(relatedItem.getId(), CallerUtils.getDefaultCaller());
                tRelatedItem.setName(name);
                tRelatedItem.setOrder(LeNumberUtils.toInt(relatedItem.getOrder()));
                tRelatedItems.add(tRelatedItem);
            }
            Collections.sort(tRelatedItems, (o1, o2) -> LeNumberUtils.toInt(o1.getOrder()) - LeNumberUtils.toInt(o2.getOrder()));
        }
        vo.setRelatedItems(tRelatedItems);
    }
}
