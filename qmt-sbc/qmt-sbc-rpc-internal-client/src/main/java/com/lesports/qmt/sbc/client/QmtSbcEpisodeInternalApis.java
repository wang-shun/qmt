package com.lesports.qmt.sbc.client;

import com.lesports.api.common.CountryCode;
import com.lesports.api.common.LanguageCode;
import com.lesports.qmt.sbc.model.Episode;
import com.lesports.query.InternalCriteria;
import com.lesports.query.InternalQuery;
import org.apache.commons.collections.CollectionUtils;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;

import java.util.Collections;
import java.util.List;

/**
 * Created by lufei1 on 2016/11/4.
 */
public class QmtSbcEpisodeInternalApis extends QmtSbcInternalApis {

    protected static final Logger LOG = LoggerFactory.getLogger(QmtSbcInternalApis.class);

    public static long saveEpisode(Episode episode) {
        return saveEntity(episode, Episode.class);
    }

    public static long saveEpisode(Episode episode, boolean allowEmpty) {
        return saveEntity(episode, Episode.class, allowEmpty);
    }

    public static boolean deleteEpisode(Long id) {
        return deleteEntity(id, Episode.class);
    }

    public static Episode getEpisodeById(Long id) {
        return getEntityById(id, Episode.class);
    }

    public static List<Long> getEpisodeIdsByQuery(InternalQuery query) {
        return getEntityIdsByQuery(query, Episode.class);
    }

    public static List<Episode> getEpisodesByIds(List<Long> ids) {
        return getEntitiesByIds(ids, Episode.class);
    }

    public static List<Episode> getEpisodesByQuery(InternalQuery params) {
        return getEntitiesByQuery(params, Episode.class);
    }

    public static List<Episode> getEpisodesByMid(long id) {
        InternalQuery internalQuery = new InternalQuery();
        internalQuery.addCriteria(new InternalCriteria("deleted", "is", false));
        internalQuery.addCriteria(new InternalCriteria("mid", "is", id));
        return getEpisodesByQuery(internalQuery);
    }

    public static Episode getEpisodesByMidAndCountryAndLanguage(long mid, CountryCode allowCountry, LanguageCode language) {
        InternalQuery internalQuery = new InternalQuery();
        internalQuery.addCriteria(new InternalCriteria("deleted", "is", false));
        internalQuery.addCriteria(new InternalCriteria("mid", "is", mid));
        internalQuery.addCriteria(new InternalCriteria("allow_country", "is", allowCountry));
        internalQuery.addCriteria(new InternalCriteria("language_code", "is", language));
        List<Episode> lists = getEpisodesByQuery(internalQuery);
        if (CollectionUtils.isEmpty(lists)) return null;
        return lists.get(0);
    }

    public static long countEpisodesByQuery(InternalQuery params) {
        return countByQuery(params, Episode.class);
    }


    public static long getEpisodeIdByLiveId(String liveId) {
        InternalQuery query = new InternalQuery();

        query.addCriteria(new InternalCriteria("live_streams.live_id", "eq", liveId));
        query.addCriteria(new InternalCriteria("deleted", "eq", false));
        PageRequest pageRequest = new PageRequest(0, 1);
        query.with(pageRequest);
        List<Long> ids = getEntityIdsByQuery(query, Episode.class);
        if (CollectionUtils.isEmpty(ids)) {
            return -1;
        }
        return ids.get(0);
    }

    public static Episode getMainEpisodeByMid(long mid) {
        InternalQuery query = new InternalQuery();

        query.addCriteria(new InternalCriteria("mid", "eq", mid));
        PageRequest pageRequest = new PageRequest(0, 1);
        query.with(pageRequest);
        List<Long> ids = getEntityIdsByQuery(query, Episode.class);
        if (CollectionUtils.isEmpty(ids)) {
            return null;
        }
        return getEpisodeById(ids.get(0));
    }

    public static List<Long> getEpisodeIdsByVideoId(long vid) {
        InternalQuery query = new InternalQuery();

        query.addCriteria(new InternalCriteria("videos.vid", "eq", vid));
        List<Long> ids = getEntityIdsByQuery(query, Episode.class);
        if (CollectionUtils.isEmpty(ids)) {
            return null;
        }
        return ids;
    }

    public static boolean deleteEpisodesWithMachId(long matchId) {
        InternalQuery criteria = new InternalQuery();
        criteria.addCriteria(new InternalCriteria("mid", "is", matchId));
        List<Episode> episodes = getEpisodesByQuery(criteria);
        if (CollectionUtils.isEmpty(episodes)) {
            return true;
        }
        boolean result = true;
        for (Episode episode : episodes) {
            result = result & deleteEpisode(episode.getId());
        }
        return result;
    }


    public static List<Long> createEpisodes(long matchId) {
        try {
            return sbcInternalService.createEpisodes(matchId);
        } catch (TException e) {
            LOG.error("fail to create episodes. match id : {}", matchId, e);
        }
        return Collections.emptyList();
    }

}
