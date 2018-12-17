package com.lesports.qmt.sbc.helper;

import com.google.common.base.Preconditions;
import com.lesports.api.common.*;
import com.lesports.id.api.IdType;
import com.lesports.id.client.LeIdApis;
import com.lesports.qmt.sbc.api.common.EpisodeType;
import com.lesports.qmt.sbc.model.Episode;
import com.lesports.qmt.sbc.model.Live;
import com.lesports.qmt.sbc.repository.EpisodeRepository;
import com.lesports.qmt.sbc.service.LiveService;
import com.lesports.qmt.sbd.SbdMatchInternalApis;
import com.lesports.qmt.sbd.model.Match;
import com.lesports.qmt.tlive.TextLiveInternalApis;
import com.lesports.qmt.tlive.api.common.TextLiveType;
import com.lesports.qmt.tlive.model.TextLive;
import com.lesports.utils.math.LeNumberUtils;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

/**
 * User: lufei
 * Time: 16-11-04 : 下午8:26
 */
@Component("episodeHelper")
public class EpisodeHelper {

    private static final Logger LOG = LoggerFactory.getLogger(EpisodeHelper.class);

    private final Executor executor = Executors.newFixedThreadPool(10);


    @Resource
    private EpisodeRepository episodeRepository;

    @Resource
    private LiveService liveService;


    private Episode existsEpisode(long mid, LanguageCode languageCode, CountryCode countryCode) {
        Query q = query(where("deleted").is(false));
        q.addCriteria(where("language_code").is(languageCode));
        q.addCriteria(where("allow_country").is(countryCode));
        q.addCriteria(where("mid").is(mid));
        return episodeRepository.findOneByQuery(q);
    }

    public Episode createEpisodeWithMatch(Match match, LanguageCode languageCode, CountryCode countryCode) {
        Assert.notNull(match);
        Assert.isTrue(match.getId() != null);
        Episode episode = existsEpisode(match.getId(), languageCode, countryCode);
        if (episode == null) {
            episode = new Episode();
        }
        long mid = match.getId();
        episode.setMid(mid);
        episode.setName(getNameByLanguage(languageCode, match.getMultiLangNames(), match.getName()));
        episode.setLanguageCode(languageCode);
        episode.setAllowCountry(countryCode);
        episode.setType(EpisodeType.MATCH);
        episode.setCid(match.getCid());
        episode.setCsid(match.getCsid());
        //episode.setIsOctopus(match.getIsOctopus());
        //episode.setOctopusMatchId(match.getOctopusMatchId());
        episode.setStartTime(match.getStartTime());
        episode.setStartDate(match.getStartDate());
        episode.setEndTime(match.getEndTime());
        episode.setGameFType(match.getGameFType());
        episode.setGameSType(match.getGameSType());
        episode.setDeleted(match.getDeleted());
        episode.setCreateAt(match.getCreateAt());
        if (CollectionUtils.isNotEmpty(match.getCompetitors())) {
            for (Match.Competitor competitor : match.getCompetitors()) {
                episode.addCompetitorId(competitor.getCompetitorId());
            }
        }
        // 在创建地区自动上线, 赛程上线节目自动上线
        if (CollectionUtils.isNotEmpty(match.getOnlineLanguages()) && match.getOnlineLanguages().contains(languageCode)) {
            episode.setOnline(true);
        }
        return episode;

    }

    public void resetEpisodeStatus(Episode episode) {
        if (episode == null) {
            return;
        }
//        LiveShowStatus opStatus = episode.getOpStatus();
//        if (opStatus != null && opStatus != LiveShowStatus.NO_LIVE) {
//            //状态被运营过了，直接取运营的状态
//            episode.setStatus(opStatus);
//            LOG.info("resetEpisodeStatus with opStatus : {} episode:{},show:{}",
//                    episode.getOpStatus(), episode.getId(), episode.getStatus());
//
//            return;
//        }
        if (episode.getType() == EpisodeType.PROGRAM || episode.getType() == EpisodeType.OTHER) {
            //自制节目类型,从直播上取
            LiveShowStatus liveStatus = getLiveStatus(episode);
            episode.setStatus(liveStatus);
            LOG.info("resetEpisodeStatus: episode id :{},live status:{},show:{}", episode.getId(), liveStatus, episode.getStatus());
            return;
        }
        if (episode.getType() == EpisodeType.MATCH) {
            Match match = SbdMatchInternalApis.getMatchById(episode.getMid());
            LiveShowStatus live = getLiveStatus((episode));
            MatchStatus matchStatus = match.getStatus();
            if (live != LiveShowStatus.NO_LIVE) {
                if (live == LiveShowStatus.LIVE_NOT_START && matchStatus == MatchStatus.MATCH_NOT_START) {
                    episode.setStatus(LiveShowStatus.LIVE_NOT_START);
                } else if (live == LiveShowStatus.LIVING && matchStatus == MatchStatus.MATCH_NOT_START) {
                    episode.setStatus(LiveShowStatus.LIVING);
                } else if (live == LiveShowStatus.LIVE_NOT_START && matchStatus == MatchStatus.MATCHING) {
                    episode.setStatus(LiveShowStatus.LIVING);
                } else if (live == LiveShowStatus.LIVING && matchStatus == MatchStatus.MATCHING) {
                    episode.setStatus(LiveShowStatus.LIVING);
                } else if (live == LiveShowStatus.LIVING && matchStatus == MatchStatus.MATCH_END) {
                    episode.setStatus(LiveShowStatus.LIVING);
                } else if (live == LiveShowStatus.LIVE_END && matchStatus == MatchStatus.MATCHING) {
                    episode.setStatus(LiveShowStatus.LIVING);
                } else if (live == LiveShowStatus.LIVE_END && matchStatus == MatchStatus.MATCH_END) {
                    episode.setStatus(LiveShowStatus.LIVE_END);
                } else if (live == LiveShowStatus.LIVE_END && matchStatus == MatchStatus.MATCH_NOT_START) {
                    episode.setStatus(LiveShowStatus.LIVE_END);
                } else if (live == LiveShowStatus.LIVE_NOT_START && matchStatus == MatchStatus.MATCH_END) {
                    episode.setStatus(LiveShowStatus.LIVE_END);
                }
                LOG.info("resetEpisodeStatus has live: episode :{},liveStatus:{},matchStatus:{},showStatus:{} ", episode.getId(), live, matchStatus, episode.getStatus());
            } else {
                //无直播走第三方数据
                if (matchStatus != null) {
                    episode.setStatus(matchStatus == MatchStatus.MATCH_NOT_START ? LiveShowStatus.LIVE_NOT_START : matchStatus == MatchStatus.MATCHING ? LiveShowStatus.LIVING : LiveShowStatus.LIVE_END);
                }
                LOG.info("resetEpisodeStatus no live: episode :{},liveStatus:{},matchStatus:{},showStatus:{} ", episode.getId(), live, matchStatus, episode.getStatus());
            }
        }
    }

    public LiveShowStatus getLiveStatus(Episode episode) {
        Preconditions.checkNotNull(episode);
        LOG.info("getLiveStatus, episode id :{} ", episode.getId());
        Set<Episode.LiveStream> streams = episode.getLiveStreams();

        if (CollectionUtils.isEmpty(streams)) {
            return LiveShowStatus.NO_LIVE;
        }
        int living = 0;
        int live_end = 0;
        for (Episode.LiveStream stream : streams) {
            Live live = liveService.findOne(LeNumberUtils.toLong(stream.getId()));
            if (live != null && live.getStatus() == LiveStatus.LIVING) {
                living++;
            } else if (live != null && live.getStatus() == LiveStatus.LIVE_END) {
                live_end++;
            }
        }
        if (living > 0) {
            //有一个直播流已开始则为已开始
            return LiveShowStatus.LIVING;
        }
        if (live_end == streams.size()) {
            //所有直播流结束才为已结束
            return LiveShowStatus.LIVE_END;
        }
        return LiveShowStatus.LIVE_NOT_START;
    }

    /**
     * 根据一系列的规则重置图文直播节目的状态
     *
     * @param
     */
    public void resetTextLiveEpisodeStatus(Episode episode) {
        if (episode == null) {
            return;
        }
        if (episode.getType() == EpisodeType.PROGRAM) {
            return;
        }
        LiveShowStatus opStatus = episode.getOpStatus();
        if (opStatus != null && opStatus != LiveShowStatus.NO_LIVE) {
            episode.setTextLiveStatus(opStatus);
            LOG.info("resetTextLiveEpisodeStatus with opStatus : {} episode:{},show:{}",
                    episode.getOpStatus(), episode, episode.getTextLiveStatus());
            return;
        }
        Long anchorTextLiveId = findTextLiveId(episode, TextLiveType.ANCHOR);
        Match match = SbdMatchInternalApis.getMatchById(episode.getMid());
        if (anchorTextLiveId == null) {
            if (match == null) {
                LOG.info("resetTextLiveEpisodeStatus match is not exists,episode:{},show:{}", episode, episode.getTextLiveStatus());
                return;
            }
            MatchStatus matchStatus = match.getStatus();
            episode.setTextLiveStatus(matchStatus == MatchStatus.MATCH_NOT_START ? LiveShowStatus.LIVE_NOT_START : matchStatus == MatchStatus.MATCHING ? LiveShowStatus.LIVING : LiveShowStatus.LIVE_END);
            LOG.info("resetTextLiveEpisodeStatus with play by play: episode :{},matchStatus:{},showStatus:{} ", episode, matchStatus, episode.getTextLiveStatus());
        } else {
            TextLive textLive = TextLiveInternalApis.getTextLiveById(anchorTextLiveId);
            if (textLive == null) {
                LOG.info("resetTextLiveEpisodeStatus textLive is not exists,episode:{},show:{}", episode, episode.getTextLiveStatus());
                return;
            }
            MatchStatus matchStatus = match == null ? MatchStatus.MATCH_NOT_START : match.getStatus();
            LiveStatus anchorStatus = textLive.getStatus();
            if (anchorStatus == LiveStatus.LIVE_NOT_START && matchStatus == MatchStatus.MATCH_NOT_START) {
                episode.setTextLiveStatus(LiveShowStatus.LIVE_NOT_START);
            } else if (anchorStatus == LiveStatus.LIVING && matchStatus == MatchStatus.MATCH_NOT_START) {
                episode.setTextLiveStatus(LiveShowStatus.LIVING);
            } else if (anchorStatus == LiveStatus.LIVE_NOT_START && matchStatus == MatchStatus.MATCHING) {
                episode.setTextLiveStatus(LiveShowStatus.LIVING);
            } else if (anchorStatus == LiveStatus.LIVING && matchStatus == MatchStatus.MATCHING) {
                episode.setTextLiveStatus(LiveShowStatus.LIVING);
            } else if (anchorStatus == LiveStatus.LIVING && matchStatus == MatchStatus.MATCH_END) {
                episode.setTextLiveStatus(LiveShowStatus.LIVING);
            } else if (anchorStatus == LiveStatus.LIVE_END && matchStatus == MatchStatus.MATCHING) {
                episode.setTextLiveStatus(LiveShowStatus.LIVING);
            } else if (anchorStatus == LiveStatus.LIVE_END && matchStatus == MatchStatus.MATCH_END) {
                episode.setTextLiveStatus(LiveShowStatus.LIVE_END);
            } else if (anchorStatus == LiveStatus.LIVE_END && matchStatus == MatchStatus.MATCH_NOT_START) {
                episode.setTextLiveStatus(LiveShowStatus.LIVE_END);
            } else if (anchorStatus == LiveStatus.LIVE_NOT_START && matchStatus == MatchStatus.MATCH_END) {
                episode.setTextLiveStatus(LiveShowStatus.LIVE_END);
            }
            LOG.info("resetTextLiveEpisodeStatus has textLive: episode :{},textLiveStatus:{},matchStatus:{},showStatus:{} ", episode, anchorStatus, matchStatus, episode.getStatus());
        }
        //如果只有图文直播，那么把视频的展示状态置为图文的展示状态
        if (CollectionUtils.isEmpty(episode.getLiveStreams())) {
            episode.setStatus(episode.getTextLiveStatus());
        }
    }


    private String getNameByLanguage(LanguageCode languageCode, List<LangString> langStrings, String defaultName) {
        if (CollectionUtils.isEmpty(langStrings)) {
            return defaultName;
        }
        for (LangString langString : langStrings) {
            if (langString.getLanguage() == languageCode) {
                return langString.getValue();
            }
        }
        return defaultName;
    }


    public LiveShowStatus mapLiveStatusToLiveShow(LiveStatus liveStatus) {
        switch (liveStatus) {
            case LIVE_END:
                return LiveShowStatus.LIVE_END;
            case LIVING:
                return LiveShowStatus.LIVING;
            case LIVE_NOT_START:
                return LiveShowStatus.LIVE_NOT_START;
        }
        return null;
    }


    public Long findTextLiveId(Episode episode, TextLiveType textLiveType) {
        if (CollectionUtils.isNotEmpty(episode.getTextLives())) {
            Set<Episode.SimpleTextLive> set = episode.getTextLives();
            Iterator<Episode.SimpleTextLive> iter = set.iterator();
            while (iter.hasNext()) {
                Episode.SimpleTextLive simpleTextLive = iter.next();
                if (simpleTextLive.getType() == textLiveType) {
                    return simpleTextLive.getTextLiveId();
                }
            }
        }
        return null;
    }

    public boolean isValidCompetitorId(long competitorId) {
        IdType type = LeIdApis.checkIdTye(competitorId);
        if (type == IdType.PLAYER || type == IdType.TEAM) {
            return true;
        }
        return false;
    }

}
