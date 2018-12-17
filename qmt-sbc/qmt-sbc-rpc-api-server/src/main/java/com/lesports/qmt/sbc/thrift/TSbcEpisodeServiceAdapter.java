package com.lesports.qmt.sbc.thrift;

import com.lesports.api.common.CallerParam;
import com.lesports.api.common.PageParam;
import com.lesports.qmt.sbc.api.dto.TComboEpisode;
import com.lesports.qmt.sbc.api.service.*;
import com.lesports.qmt.sbc.service.EpisodeService;
import com.lesports.utils.CallerUtils;
import com.lesports.utils.IdCheckUtils;
import com.lesports.utils.PageUtils;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static me.ellios.hedwig.http.mediatype.ExtendedMediaType.APPLICATION_X_THRIFT;

/**
 * User: lufei
 * Time: 16-12-13 : 下午4:39
 */
@Service("thriftSbcEpisodeService")
@Path("/sbc/episode")
@Produces({APPLICATION_X_THRIFT})
public class TSbcEpisodeServiceAdapter implements TSbcEpisodeService.Iface {

    private static final Logger LOG = LoggerFactory.getLogger(TSbcEpisodeServiceAdapter.class);

    @Resource
    private EpisodeService episodeService;


    @Override
    public List<Long> getTheRoadOfAdvance(long cid, long csid, CallerParam caller) throws TException {
        try {
            caller = CallerUtils.getValidCaller(caller);
            return episodeService.getTheRoadOfAdvance(cid, csid, caller);
        } catch (Exception e) {
            LOG.error("fail to getTheRoadOfAdvance. cid : {}, page:{}, csid : {}", cid, csid, caller, e);
        }
        return Collections.EMPTY_LIST;
    }

    @Override
    public List<Long> getCurrentEpisodeIds(GetCurrentEpisodesParam p, PageParam page, CallerParam caller) throws TException {
        try {
            caller = CallerUtils.getValidCaller(caller);
            Pageable pageable = PageUtils.convertToPageable(page);
            return episodeService.getCurrentEpisodeIds(p, pageable, caller);
        } catch (Exception e) {
            LOG.error("fail to countEpisodesByDay. p : {},page:{}, caller : {}", p, page, caller, e);
        }
        return Collections.EMPTY_LIST;
    }

    @Override
    public List<Long> getTimelineEpisodesByCids(GetCurrentEpisodesParam p, PageParam page, CallerParam caller) throws TException {
        try {
            caller = CallerUtils.getValidCaller(caller);
            Pageable pageable = PageUtils.convertToPageable(page);
            return episodeService.getTimelineEpisodesByCids(p, pageable, caller);
        } catch (Exception e) {
            LOG.error("fail to getTimelineEpisodesByCids. p : {}, page:{}, caller : {}", p, page, caller, e);
        }
        return Collections.EMPTY_LIST;
    }

    @Override
    public List<Long> countEpisodesByDay(CountEpisodesByDayParam p, CallerParam caller) throws TException {
        try {
            caller = CallerUtils.getValidCaller(caller);
            return episodeService.countEpisodesByDay(p, caller);
        } catch (Exception e) {
            LOG.error("fail to countEpisodesByDay. p : {}, caller : {}", p, caller, e);
        }
        return Collections.EMPTY_LIST;
    }

    @Override
    public List<Long> getSomeDayEpisodeIds(GetSomeDayEpisodesParam p, PageParam page, CallerParam caller) throws TException {
        try {
            caller = CallerUtils.getValidCaller(caller);
            Pageable pageable = null;
            if (page != null) {
                pageable = PageUtils.convertToPageable(page);
            }
            return episodeService.getSomeDayEpisodeIds(p, pageable, caller);
        } catch (Exception e) {
            LOG.error("fail to getSomeDayEpisodeIds. p : {},page:{] caller : {}", p, page, caller, e);
        }
        return Collections.EMPTY_LIST;
    }

    @Override
    public List<Long> getSomeDayEpisodeIdsWithTimezone(GetSomeDayEpisodesParam p, PageParam page, CallerParam caller) throws TException {
        try {
            caller = CallerUtils.getValidCaller(caller);
            Pageable pageable = null;
            if (page != null) {
                pageable = PageUtils.convertToPageable(page);
            }
            return episodeService.getSomeDayEpisodeIdsWithTimezone(p, pageable, caller);
        } catch (Exception e) {
            LOG.error("fail to getSomeDayEpisodeIdsWithTimezone. p : {},page:{] caller : {}", p, page, caller, e);
        }
        return Collections.EMPTY_LIST;
    }

    @Override
    public List<String> getStartDatesBySomeDayEpisodeParam(CountEpisodesByDayParam p, CallerParam caller) throws TException {
        caller = CallerUtils.getValidCaller(caller);
        return episodeService.getStartDatesBySomeDayEpisodeParam(p, caller);
    }

    @Override
    public List<Long> getEpisodeIdsOfSeasonByMetaEntryId(GetEpisodesOfSeasonByMetaEntryIdParam p, PageParam page, CallerParam caller) throws TException {
        try {
            caller = CallerUtils.getValidCaller(caller);
            Pageable pageable = PageUtils.convertToPageable(page);
            return episodeService.getEpisodeIdsOfSeasonByMetaEntryId(p, pageable, caller);
        } catch (Exception e) {
            LOG.error("fail to getEpisodeIdsOfSeasonByMetaEntryId. p : {}, caller : {}, page : {}", p, caller, page, e);
        }
        return Collections.EMPTY_LIST;
    }

    @Override
    public List<Long> getEpisodeIdsRelatedWithSomeEpisode(long episodeId, int count, CallerParam caller) throws TException {
        try {
            caller = CallerUtils.getValidCaller(caller);
            return episodeService.getEpisodeIdsRelatedWithSomeEpisode(episodeId, count, caller);
        } catch (Exception e) {
            LOG.error("fail to getEpisodeIdsOfSeasonByMetaEntryId. episodeId : {}, count : {}, caller : {}",
                    episodeId, count, caller, e);
        }
        return Collections.EMPTY_LIST;
    }

    @Override
    public Map<String, Long> getEpisodeIdsNearbySomeEpisode(GetEpisodesNearbySomeEpisodeParam p, CallerParam caller) throws TException {
        try {
            caller = CallerUtils.getValidCaller(caller);
            return episodeService.getEpisodeIdsNearbySomeEpisode(p, caller);
        } catch (Exception e) {
            LOG.error("fail to GetEpisodeIdsNearbySomeEpisode. p : {}, caller : {}", p, caller, e);
        }
        return Collections.EMPTY_MAP;
    }

    @Override
    public TComboEpisode getTComboEpisodeById(long id, CallerParam caller) throws TException {
        try {
            caller = CallerUtils.getValidCaller(caller);
            return episodeService.getTComboEpisodeById(id, caller);
        } catch (Exception e) {
            LOG.error("fail to getTComboEpisodeById. id : {}, caller : {}",
                    id, caller, e);
        }
        return null;
    }

    @Override
    public List<TComboEpisode> getTComboEpisodesByIds(List<Long> ids, CallerParam caller) throws TException {
        try {
            caller = CallerUtils.getValidCaller(caller);
            ids = IdCheckUtils.checkIds(ids);
            return episodeService.getTComboEpisodesByIds(ids, caller);
        } catch (Exception e) {
            LOG.error("fail to getTComboEpisodesByIds. ids : {}, caller : {}",
                    ids, caller, e);
        }
        return null;
    }

    @Override
    public List<TComboEpisode> getTComboEpisodesInAlbum(long albumId, PageParam page, CallerParam caller) throws TException {
        try {
            caller = CallerUtils.getValidCaller(caller);
            Pageable pageable = PageUtils.convertToPageable(page);
            return episodeService.getEpisodeByAid(albumId, pageable, caller);
        } catch (Exception e) {
            LOG.error("fail to getTComboEpisodesByIds. albumId : {}, page : {}, caller : {}",
                    albumId, caller, e);
        }
        return null;
    }

    @Override
    public List<TComboEpisode> getEpisodes4OlyMatchs(GetMatchsEpisodesParam p, PageParam page, CallerParam caller) throws TException {
        return null;
    }

    @Override
    public long countEpisodes4OlyMatchs(GetMatchsEpisodesParam p, CallerParam caller) throws TException {
        return 0;
    }


    @Override
    public long getEpisodeIdByLiveId(String liveId, CallerParam caller) throws TException {
        try {
            return episodeService.getEpisodeIdByLiveId(liveId, caller);
        } catch (Exception e) {
            LOG.error("fail to getEpisodeIdByLiveId. liveId : {}, caller : {}",
                    liveId, caller, e);
        }
        return 0;
    }

    @Override
    public long getEpisodeIdByTextLiveId(long textLiveId, CallerParam caller) throws TException {
        try {
            return episodeService.getEpisodeIdByTextLiveId(textLiveId, caller);
        } catch (Exception e) {
            LOG.error("fail to getEpisodeIdByTextLiveId. textLiveId : {}, caller : {}",
                    textLiveId, caller, e);
        }
        return 0;
    }

    @Override
    public long getEpisodeIdByMid(long mId, CallerParam caller) throws TException {
        try {
            return episodeService.getEpisodeIdByMid(mId, caller);
        } catch (Exception e) {
            LOG.error("fail to getEpisodeIdByMid. mid : {}, caller : {}",
                    mId, caller, e);
        }
        return 0;
    }

    @Override
    public List<Long> getPassedEpisodeIdsInAlbum(long albumId, PageParam page, CallerParam caller) throws TException {
        try {
            caller = CallerUtils.getValidCaller(caller);
            Pageable pageable = PageUtils.convertToPageable(page);
            return episodeService.getPassedEpisodeIdsInAlbum(albumId, pageable, caller);
        } catch (Exception e) {
            LOG.error("fail to getPassedEpisodesInAlbum. albumId : {}, page : {},caller : {}",
                    albumId, page, caller, e);
        }
        return Collections.EMPTY_LIST;
    }

    @Override
    public List<Long> getEpisodeIdsOfOctopus(GetZhangyuEpisodesParam p, PageParam page, CallerParam caller) throws TException {
        try {
            caller = CallerUtils.getValidCaller(caller);
            Pageable pageable = PageUtils.convertToPageable(page);
            return episodeService.getEpisodeIdsOfOctopus(p, pageable, caller);
        } catch (Exception e) {
            LOG.error("fail to getEpisodeIdsOfOctopus. p : {}, page : {},caller : {}",
                    p, page, caller, e);
        }
        return Collections.EMPTY_LIST;
    }

    @Override
    public List<Long> getCurrentEpisodeIdsByCompetitorId(long competitorId, GetEpisodesOfSeasonByMetaEntryIdParam p, PageParam page, CallerParam caller) throws TException {
        try {
            caller = CallerUtils.getValidCaller(caller);
            Pageable pageable = PageUtils.convertToPageable(page);
            return episodeService.getCurrentEpisodeIdsByCompetitorId(competitorId, p, pageable, caller);
        } catch (Exception e) {
            LOG.error("fail to getEpisodeIdsOfOctopus. competitorId:{},p : {}, page : {},caller : {}", competitorId,
                    p, page, caller, e);
        }
        return Collections.EMPTY_LIST;
    }

    @Override
    public List<Long> getLephoneDesktopChannelEpisodes(String date, long gameType, CallerParam caller) throws TException {
        try {
            caller = CallerUtils.getValidCaller(caller);
//            return episodeService.getLephoneDesktopChannelEpisodes(date, gameType, caller);
        } catch (Exception e) {
            LOG.error("fail to getLephoneDesktopChannelEpisodes. date:{}, gameType : {}, caller : {}", date,
                    gameType, caller, e);
        }
        return Collections.EMPTY_LIST;
    }

    @Override
    public List<Long> getPeriodAppRecommendEpisodes(CallerParam caller) throws TException {
        try {
            caller = CallerUtils.getValidCaller(caller);
            return episodeService.getPeriodAppRecommendEpisodes(caller);
        } catch (Exception e) {
            LOG.error("fail to getPeriodAppRecommendEpisodes. caller : {}", caller, e);
        }
        return Collections.EMPTY_LIST;
    }

    @Override
    public List<Long> getCurrentMemberEpisodeIds(GetCurrentEpisodesParam p, PageParam page, CallerParam caller) throws TException {
        try {
            caller = CallerUtils.getValidCaller(caller);
            Pageable pageable = PageUtils.convertToPageable(page);
            return episodeService.getMemberEpisodeIds(p, pageable, caller);
        } catch (Exception e) {
            LOG.error("fail to getCurrentMemberEpisodeIds. p : {},page:{}, caller : {}", p, page, caller, e);
        }
        return Collections.EMPTY_LIST;
    }

    @Override
    public List<Long> getTicketEpisodeIds(GetCurrentEpisodesParam p, PageParam page, CallerParam caller) throws TException {
        try {
            caller = CallerUtils.getValidCaller(caller);
            Pageable pageable = PageUtils.convertToPageable(page);
            return episodeService.getTicketEpisodeIds(p, pageable, caller);
        } catch (Exception e) {
            LOG.error("fail to getTicketEpisodeIds. p : {},page:{}, caller : {}", p, page, caller, e);
        }
        return Collections.EMPTY_LIST;
    }

    @Override
    public List<Long> getEpisodeIds4CommonCompetitionResource(GetEpisodes4CommonCompetitionResourceParam p, PageParam page, CallerParam caller) throws TException {
        try {
//            caller = CallerUtils.getValidCaller(caller);
//            Pageable pageable = PageUtils.convertToPageable(page);
//            return episodeService.getEpisodeIds4CommonCompetitionResource(p, pageable, caller);
        } catch (Exception e) {
            LOG.error("fail to getEpisodeIds4CommonCompetitionResource. p : {},page:{}, caller : {}", p, page, caller, e);
        }
        return Collections.EMPTY_LIST;
    }

}
