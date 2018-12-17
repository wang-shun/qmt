package com.lesports.qmt.sbc.client;

import com.google.common.base.Function;
import com.google.common.collect.Maps;
import com.lesports.api.common.CallerParam;
import com.lesports.api.common.PageParam;
import com.lesports.qmt.sbc.api.dto.TComboEpisode;
import com.lesports.qmt.sbc.api.service.*;
import com.lesports.utils.CallerUtils;
import com.lesports.utils.LeConcurrentUtils;
import com.lesports.utils.math.LeNumberUtils;
import me.ellios.hedwig.common.exceptions.HedwigException;
import me.ellios.hedwig.rpc.client.ClientBuilder;
import me.ellios.hedwig.rpc.core.ServiceType;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by lufei1 on 2016/12/19.
 */
public class QmtSbcEpisodeApis {

    private static final Logger LOG = LoggerFactory.getLogger(QmtSbcResourceApis.class);

    private static TSbcEpisodeService.Iface sbcEpisodeService = new ClientBuilder<TSbcEpisodeService.Iface>()
            .serviceType(ServiceType.THRIFT).serviceFace(TSbcEpisodeService.Iface.class).build();

    public static List<TComboEpisode> getCurrentEpisodes(GetCurrentEpisodesParam p, PageParam page, CallerParam caller) {
        try {
            caller = CallerUtils.getValidCaller(caller);
            List<Long> ids = sbcEpisodeService.getCurrentEpisodeIds(p, page, caller);
            if (CollectionUtils.isEmpty(ids)) {
                return Collections.EMPTY_LIST;
            }
            return getTComboEpisodesByIds(ids, caller);
        } catch (Exception e) {
            LOG.error("fail to getCurrentEpisodes. p : {}, caller : {}, page : {}", p, caller, page, e);
        }
        return Collections.EMPTY_LIST;
    }

    public static List<Long> countEpisodesByDay(CountEpisodesByDayParam p, CallerParam caller) {
        try {
            return sbcEpisodeService.countEpisodesByDay(p, caller);
        } catch (Exception e) {
            LOG.error("fail to countEpisodesByDay. p : {}, caller : {}", p, caller, e);
        }
        return Collections.EMPTY_LIST;
    }

    public static List<TComboEpisode> getCurrentMemberEpisodes(GetCurrentEpisodesParam p, PageParam page, CallerParam caller) {
        try {
            caller = CallerUtils.getValidCaller(caller);
            List<Long> ids = sbcEpisodeService.getCurrentMemberEpisodeIds(p, page, caller);
            if (CollectionUtils.isEmpty(ids)) {
                return Collections.EMPTY_LIST;
            }
            return getTComboEpisodesByIds(ids, caller);
        } catch (TException e) {
            LOG.error("fail to getCurrentMemberEpisodes. p : {},page:{}, caller : {}", p, page, caller, e);
            throw new HedwigException("Fail to getCurrentMemberEpisodes");
        }
    }

    public static List<TComboEpisode> getTicketEpisodes(GetCurrentEpisodesParam p, PageParam page, CallerParam caller) {
        try {
            caller = CallerUtils.getValidCaller(caller);
            List<Long> ids = sbcEpisodeService.getTicketEpisodeIds(p, page, caller);
            if (CollectionUtils.isEmpty(ids)) {
                return Collections.EMPTY_LIST;
            }
            return getTComboEpisodesByIds(ids, caller);
        } catch (TException e) {
            LOG.error("fail to getTicketEpisodes. p : {},page:{}, caller : {}", p, page, caller, e);
            throw new HedwigException("Fail to getTicketEpisodes");
        }
    }

	public static List<TComboEpisode> getSomeDayEpisodes(GetSomeDayEpisodesParam p, PageParam page, CallerParam caller) {
        try {
            caller = CallerUtils.getValidCaller(caller);
            List<Long> ids = sbcEpisodeService.getSomeDayEpisodeIds(p, page, caller);
            if (CollectionUtils.isEmpty(ids)) {
                return Collections.EMPTY_LIST;
            }
            return getTComboEpisodesByIds(ids, caller);
        } catch (TException e) {
            LOG.error("fail to getSomeDayEpisodeIds. p : {},page:{}, caller : {}", p, page, caller, e);
            throw new HedwigException("Fail to getSomeDayEpisodeIds");
        }
    }

    public static List<TComboEpisode> getSomeDayEpisodeIdsWithTimezone(GetSomeDayEpisodesParam p, PageParam page, CallerParam caller) {
        try {
            caller = CallerUtils.getValidCaller(caller);
            List<Long> ids = sbcEpisodeService.getSomeDayEpisodeIdsWithTimezone(p, page, caller);
            if (CollectionUtils.isEmpty(ids)) {
                return Collections.EMPTY_LIST;
            }
            return getTComboEpisodesByIds(ids, caller);
        } catch (TException e) {
            LOG.error("fail to getSomeDayEpisodeIdsWithTimezone. p : {},page:{}, caller : {}", p, page, caller, e);
            throw new HedwigException("Fail to getSomeDayEpisodeIdsWithTimezone");
        }
    }

    public static List<String> getStartDatesBySomeDayEpisodeParam(CountEpisodesByDayParam p, CallerParam caller) {
        try {
            caller = CallerUtils.getValidCaller(caller);
            List<String> startDates = sbcEpisodeService.getStartDatesBySomeDayEpisodeParam(p, caller);
            if (CollectionUtils.isEmpty(startDates)) {
                return Collections.EMPTY_LIST;
            }
            return startDates;
        } catch (TException e) {
            LOG.error("fail to getStartDatesBySomeDayEpisodeParam. p : {}, caller : {}", p, caller, e);
            throw new HedwigException("Fail to getStartDatesBySomeDayEpisodeParam");
        }
    }


    public static List<TComboEpisode> getEpisodesOfSeasonByMetaEntryId(GetEpisodesOfSeasonByMetaEntryIdParam p, PageParam page, CallerParam caller) {
        try {
            caller = CallerUtils.getValidCaller(caller);
            List<Long> ids = sbcEpisodeService.getEpisodeIdsOfSeasonByMetaEntryId(p, page, caller);
            if (CollectionUtils.isEmpty(ids)) {
                return Collections.EMPTY_LIST;
            }
            return getTComboEpisodesByIds(ids, caller);
        } catch (Exception e) {
            LOG.error("fail to getEpisodeIdsOfSeasonByMetaEntryId. p : {}, caller : {}, page : {}", p, caller, page, e);
        }
        return Collections.EMPTY_LIST;
    }

    public static List<Long> getEpisodeIdsRelatedWithSomeEpisode(long episodeId, int count, CallerParam caller) {
        try {
            return sbcEpisodeService.getEpisodeIdsRelatedWithSomeEpisode(episodeId, count, caller);
        } catch (Exception e) {
            LOG.error("fail to getEpisodeIdsRelatedWithSomeEpisode. episodeId : {}, count : {}, caller : {}",
                    episodeId, count, caller, e);
        }
        return Collections.EMPTY_LIST;
    }

    public static TComboEpisode getTComboEpisodeById(long id, CallerParam caller) {
        if (id <= 0) {
            LOG.error("fail to getTComboEpisodeById. id : {} illegal", id);
            return null;
        }
        try {
            return sbcEpisodeService.getTComboEpisodeById(id, caller);
        } catch (Exception e) {
            LOG.error("fail to getTComboEpisodeById. id : {}, caller : {}",
                    id, caller, e);
        }
        return null;
    }

    public static TComboEpisode getEpisodeByLiveId(String liveId, CallerParam caller) {
        if (StringUtils.isEmpty(liveId)) {
            LOG.warn("fail to getEpisodeByLiveId. liveId : {} illegal", liveId);
            return null;
        }
        try {
            long id = sbcEpisodeService.getEpisodeIdByLiveId(liveId, caller);
            return getTComboEpisodeById(id, caller);
        } catch (Exception e) {
            LOG.error("fail to getEpisodeByLiveId. liveId : {}, caller : {}",
                    liveId, caller, e);
        }
        return null;
    }

    public static long getEpisodeIdByLiveId(String liveId, CallerParam caller) {
        if (StringUtils.isEmpty(liveId)) {
            LOG.warn("fail to getEpisodeIdByLiveId. liveId : {} illegal", liveId);
            return 0;
        }
        try {
            return sbcEpisodeService.getEpisodeIdByLiveId(liveId, caller);
        } catch (Exception e) {
            LOG.error("fail to getEpisodeIdByLiveId. liveId : {}, caller : {}",
                    liveId, caller, e);
        }
        return 0;
    }

    public static long getEpisodeIdByTextLiveId(long textLiveId, CallerParam caller) {
        if (textLiveId <= 0) {
            LOG.warn("fail to getEpisodeIdByTextLiveId. textiveId : {} illegal", textLiveId);
            return 0;
        }
        try {
            return sbcEpisodeService.getEpisodeIdByTextLiveId(textLiveId, caller);
        } catch (Exception e) {
            LOG.error("fail to getEpisodeIdByTextLiveId. liveId : {}, caller : {}",
                    textLiveId, caller, e);
        }
        return 0;
    }

    public static TComboEpisode getEpisodeByMid(long mid, CallerParam caller) {
        if (mid <= 0) {
            LOG.warn("fail to getEpisodeByMid. mid : {} illegal", mid);
            return null;
        }
        try {
            long id = sbcEpisodeService.getEpisodeIdByMid(mid, caller);
            return getTComboEpisodeById(id, caller);
        } catch (Exception e) {
            LOG.error("fail to getEpisodeByMid. mId : {}, caller : {}",
                    mid, caller, e);
        }
        return null;
    }

    public static List<Long> getPassedEpisodeIdsInAlbum(long albumId, PageParam page, CallerParam caller) {
        if (albumId <= 0) {
            LOG.error("fail to getPassedEpisodesInAlbum. albumId : {} illegal", albumId);
            return Collections.EMPTY_LIST;
        }
        try {
            return sbcEpisodeService.getPassedEpisodeIdsInAlbum(albumId, page, caller);
        } catch (Exception e) {
            LOG.error("fail to getPassedEpisodesInAlbum. albumId : {},page:{}, caller : {}",
                    albumId, page, caller, e);
        }
        return null;
    }

    public static Map<String, TComboEpisode> getTComboEpisodesNearbySomeEpisode(GetEpisodesNearbySomeEpisodeParam p, CallerParam caller) {
        try {
            Map<String, Long> nearbyEpisodeIdMap = sbcEpisodeService.getEpisodeIdsNearbySomeEpisode(p, caller);
            if (MapUtils.isEmpty(nearbyEpisodeIdMap)) {
                return Collections.EMPTY_MAP;
            }
            Map<String, TComboEpisode> results = Maps.newHashMap();
            for (Map.Entry<String, Long> entry : nearbyEpisodeIdMap.entrySet()) {
                TComboEpisode episode = getTComboEpisodeById(LeNumberUtils.toLong(entry.getValue()), caller);
                if (episode == null) {
                    continue;
                }
                results.put(entry.getKey(), episode);
            }
            return results;
        } catch (Exception e) {
            LOG.error("fail to getEpisodeIdsNearbySomeEpisode. p : {}, caller : {}", p, caller, e);
        }
        return Collections.EMPTY_MAP;
    }


    public static List<TComboEpisode> getTComboEpisodesByIds(List<Long> ids, final CallerParam caller) {
        if (CollectionUtils.isEmpty(ids)) {
            LOG.error("fail to getTComboEpisodesByIds. ids is empty.");
            return Collections.EMPTY_LIST;
        }

        return LeConcurrentUtils.parallelApplyforBatch(ids, new Function<List<Long>, List<TComboEpisode>>() {
            @Nullable
            @Override
            public List<TComboEpisode> apply(List<Long> input) {
                try {
                    return sbcEpisodeService.getTComboEpisodesByIds(input, caller);
                } catch (Exception e) {
                    LOG.error("fail to getTComboEpisodesByIds. ids : {}, caller : {}",
                            input, caller, e);
                }
                return Collections.emptyList();
            }
        });
    }


    public static List<TComboEpisode> getTComboEpisodesInAlbum(long albumId, PageParam page, CallerParam caller) {
        try {
            return sbcEpisodeService.getTComboEpisodesInAlbum(albumId, page, caller);
        } catch (Exception e) {
            LOG.error("fail to getTComboEpisodesInAlbum. ids : {}, page : {}, caller : {}",
                    albumId, page, caller, e);
        }
        return Collections.EMPTY_LIST;
    }

    public static List<TComboEpisode> getEpisodesOfOctopus(GetZhangyuEpisodesParam p, PageParam page, CallerParam caller) {
        try {
            List<Long> ids = sbcEpisodeService.getEpisodeIdsOfOctopus(p, page, caller);
            if (CollectionUtils.isEmpty(ids)) {
                return Collections.EMPTY_LIST;
            }
            return getTComboEpisodesByIds(ids, caller);
        } catch (Exception e) {
            LOG.error("fail to getEpisodesOfOctopus. p : {}, page : {}, caller : {}",
                    p, page, caller, e);
        }
        return Collections.EMPTY_LIST;
    }


    public static List<TComboEpisode> getCurrentEpisodesByCompetitorId(long competitorId, GetEpisodesOfSeasonByMetaEntryIdParam p, PageParam page, CallerParam caller) {
        try {
            List<Long> ids = sbcEpisodeService.getCurrentEpisodeIdsByCompetitorId(competitorId, p, page, caller);
            if (CollectionUtils.isEmpty(ids)) {
                return Collections.EMPTY_LIST;
            }
            return getTComboEpisodesByIds(ids, caller);
        } catch (Exception e) {
            LOG.error("fail to getCurrentEpisodesByCompetitorId. competitorId:{},p : {}, page : {},caller : {}", competitorId,
                    p, page, caller, e);
        }
        return Collections.EMPTY_LIST;
    }

    public static List<TComboEpisode> getEpisode4CommonCompetitionResource(GetEpisodes4CommonCompetitionResourceParam p, PageParam page, CallerParam caller) {
        try {
            List<Long> ids = sbcEpisodeService.getEpisodeIds4CommonCompetitionResource(p, page, caller);
            if (CollectionUtils.isEmpty(ids)) {
                return Collections.EMPTY_LIST;
            }
            return getTComboEpisodesByIds(ids, caller);
        } catch (Exception e) {
            LOG.error("fail to getEpisode4CommonCompetitionResource. p:{}, page : {},caller : {}",
                    p, page, caller, e);
        }
        return Collections.EMPTY_LIST;
    }
}
