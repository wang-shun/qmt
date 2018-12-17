package com.lesports.qmt.sbd;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.lesports.LeConstants;
import com.lesports.qmt.sbd.api.common.CompetitorType;
import com.lesports.qmt.sbd.api.service.TSbdInternalService;
import com.lesports.qmt.sbd.model.*;
import com.lesports.query.InternalCriteria;
import com.lesports.query.InternalQuery;
import com.lesports.utils.PageUtils;
import com.lesports.utils.transcoders.CachedData;
import com.lesports.utils.transcoders.SerializingTranscoder;
import com.lesports.utils.transcoders.Transcoder;
import me.ellios.hedwig.rpc.client.ClientBuilder;
import me.ellios.hedwig.rpc.core.ServiceType;
import org.apache.commons.collections.CollectionUtils;
import org.apache.thrift.TBaseHelper;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.List;

/**
 * User: ellios
 * Time: 15-6-3 : 下午9:32
 */
public class SbdInternalApis {

    protected static final Logger LOG = LoggerFactory.getLogger(SbdInternalApis.class);

    protected static final Transcoder TRANSCODER = new SerializingTranscoder();

    protected static TSbdInternalService.Iface sbdInternalService = new ClientBuilder<TSbdInternalService.Iface>()
            .serviceType(ServiceType.THRIFT).serviceFace(TSbdInternalService.Iface.class).build();

    public static TSbdInternalService.Iface getSbdInternalService() {
        return sbdInternalService;
    }

    protected static <T> ByteBuffer serialize(T entity) {
        return ByteBuffer.wrap(TRANSCODER.encode(entity).getFullData());
    }


    protected static <T> T unserialize(ByteBuffer bf) {
        if (bf == null) {
            return null;
        }
        return TRANSCODER.decode(new CachedData(TBaseHelper.byteBufferToByteArray(bf)));
    }

    protected static <T, ID> List<T> doGet(Class<T> clazz, List<ID> ids, BatchGetter<ID> batchGetter) throws TException {
        if (CollectionUtils.isEmpty(ids)) {
            LOG.warn("fail to getByIds. ids empty. ids : {}", ids);
            return Collections.EMPTY_LIST;
        }

        int size = ids.size();
        List<T> results = Lists.newArrayListWithCapacity(size);

        int toIndex = LeConstants.MAX_BATCH_SIZE;
        for (int i = 0; i < size; i += LeConstants.MAX_BATCH_SIZE, toIndex += LeConstants.MAX_BATCH_SIZE) {
            if (size < toIndex) {
                toIndex = size;
            }
            List<ID> batchIds = ids.subList(i, toIndex);

            ByteBuffer bf = batchGetter.get(batchIds);
            if (bf == null) {
                continue;
            }
            List<T> batchEntities = unserialize(bf);
            if (CollectionUtils.isNotEmpty(batchEntities)) {
                results.addAll(batchEntities);
            }
            LOG.info("success to getByIds for {}. id size : {}", clazz, batchIds.size());
        }

        return results;
    }

    interface BatchGetter<ID> {
        ByteBuffer get(List<ID> ids) throws TException;
    }

    protected static <T> long saveEntity(T entity, Class<T> tClass) {
        return saveEntity(entity, tClass, false);
    }

    protected static <T> long saveEntity(T entity, Class<T> tClass, boolean allowEmpty) {
        try {
            ByteBuffer bf = serialize(entity);
            return sbdInternalService.saveEntity(bf, serialize(tClass), allowEmpty);
        } catch (TException e) {
            LOG.error("fail to save entity : {}, type : {}. {}", entity, tClass, e.getMessage(), e);
        }
        return -1;
    }

    protected static <T> boolean deleteEntity(Long id, Class<T> tClass) {
        if (id != null) {
            try {
                return sbdInternalService.deleteEntity(id, serialize(tClass));
            } catch (TException e) {
                LOG.error("fail to delete entity. id : {}, type : {}. {}", id, tClass, e.getMessage(), e);
            }
        }
        return false;
    }

    protected static <T> T getEntityById(Long id, Class<T> clazz) {
        if (id != null) {
            try {
                ByteBuffer bf = sbdInternalService.getEntityBytesById(id, serialize(clazz));
                return unserialize(bf);
            } catch (TException e) {
                LOG.error("fail to getEntityById. id : {}, type : {}. {}", id, clazz, e.getMessage(), e);
            }
        }
        return null;
    }

    protected static <T> List<T> getEntitiesByIds(List<Long> ids, final Class<T> clazz) {
        try {
            final ByteBuffer byteBuffer = serialize(clazz);
            return doGet(clazz, ids, new BatchGetter<Long>() {
                @Override
                public ByteBuffer get(List<Long> longs) throws TException {
                    return sbdInternalService.getEntityBytesByIds(longs, byteBuffer);
                }
            });
        } catch (TException e) {
            LOG.error("fail to getEntitiesByIds. ids : {}, type : {}. {}", ids, clazz, e.getMessage(), e);
        }
        return Collections.EMPTY_LIST;
    }

    protected static <T> List<T> getEntitiesByQuery(InternalQuery query, Class clazz) {
        Preconditions.checkNotNull(query);
        List<T> result = Lists.newArrayList();
        List<T> entityPage = null;
        int pageSize = query.getPageable() == null ? Integer.MAX_VALUE : query.getPageable().getPageSize();
        if (query.getLimit() > 0) {
            pageSize = Math.min(pageSize, query.getLimit());
        }
        try {
            do {
                query.with(PageUtils.getValidPageable(query.getPageable()));
                int page = query.getPageable().getPageNumber();
                ByteBuffer paramsBf = serialize(query);
                ByteBuffer entitiesBf = sbdInternalService.getEntitiesBytesByQuery(paramsBf, serialize(clazz));
                entityPage = unserialize(entitiesBf);
                if (CollectionUtils.isNotEmpty(entityPage)) {
                    result.addAll(entityPage);
                }
                if (result.size() >= pageSize) {
                    result = result.subList(0, pageSize);
                    break;
                }
                Pageable valid = new PageRequest(++page, query.getPageable().getPageSize(), query.getPageable().getSort());
                query.with(valid);
            } while (CollectionUtils.isNotEmpty(entityPage));
        } catch (TException e) {
            LOG.error("fail to getEntitiesByQuery. query : {}, type : {}. {}", query, clazz, e.getMessage(), e);
        }

        return result;
    }

    protected static List<Long> getEntityIdsByQuery(InternalQuery query, Class clazz) {
        Preconditions.checkNotNull(query);
        List<Long> result = Lists.newArrayList();
        List<Long> idPage = null;
        int pageSize = query.getPageable() == null ? Integer.MAX_VALUE : query.getPageable().getPageSize();
        if (query.getLimit() > 0) {
            pageSize = Math.min(pageSize, query.getLimit());
        }
        try {
            do {
                query.with(PageUtils.getValidPageable(query.getPageable()));
                int page = query.getPageable().getPageNumber();
                ByteBuffer paramsBf = serialize(query);
                idPage = sbdInternalService.getEntityIdsByQuery(paramsBf, serialize(clazz));
                if (CollectionUtils.isNotEmpty(idPage)) {
                    result.addAll(idPage);
                }
                if (result.size() >= pageSize) {
                    result = result.subList(0, pageSize);
                    break;
                }
                Pageable valid = new PageRequest(++page, query.getPageable().getPageSize(), query.getPageable().getSort());
                query.with(valid);
            } while (CollectionUtils.isNotEmpty(idPage));
        } catch (TException e) {
            LOG.error("fail to getEntitiesByQuery. query : {}, type : {}. {}", query, clazz, e.getMessage(), e);
        }

        return result;
    }

    protected static long countEntitiesByQuery(InternalQuery params, Class clazz) {
        Preconditions.checkNotNull(params);
        try {
            ByteBuffer paramsBf = serialize(params);
            return sbdInternalService.countByQuery(paramsBf, serialize(clazz));
        } catch (TException e) {
            LOG.error("fail to countEntitiesByQuery. params : {}, type : {}. {}", params, clazz, e.getMessage(), e);
        }
        return -1;
    }


    public static long saveTeamSeason(TeamSeason teamSeason) {
        return saveEntity(teamSeason, TeamSeason.class);
    }

    public static boolean deleteTeamSeason(Long id) {
        return deleteEntity(id, TeamSeason.class);
    }

    public static TeamSeason getTeamSeasonById(Long id) {
        return getEntityById(id, TeamSeason.class);
    }

    public static List<TeamSeason> getTeamSeasonsByIds(List<Long> ids) {
        return getEntitiesByIds(ids, TeamSeason.class);
    }

    public static List<TeamSeason> getTeamSeasonsByQuery(InternalQuery params) {
        return getEntitiesByQuery(params, TeamSeason.class);
    }

    public static long countTeamSeasonsByQuery(InternalQuery params) {
        return countEntitiesByQuery(params, TeamSeason.class);
    }


    public static long countMatchesByQuery(InternalQuery params) {
        return countEntitiesByQuery(params, Match.class);
    }

    public static long saveMatchAction(MatchAction matchAction) {
        return saveEntity(matchAction, MatchAction.class);
    }

    public static MatchAction getMatchActionById(Long id) {
        return getEntityById(id, MatchAction.class);
    }

    public static List<MatchAction> getMatchActionsByIds(List<Long> ids) {
        return getEntitiesByIds(ids, MatchAction.class);
    }

    public static List<MatchAction> getMatchActionsByQuery(InternalQuery params) {
        return getEntitiesByQuery(params, MatchAction.class);
    }

    public static long saveCompetitorSeasonStat(CompetitorSeasonStat competitorSeasonStat) {
        return saveEntity(competitorSeasonStat, CompetitorSeasonStat.class);
    }

    public static CompetitorSeasonStat getCompetitorSeasonStatById(Long id) {
        return getEntityById(id, CompetitorSeasonStat.class);
    }

    public static List<CompetitorSeasonStat> getCompetitorSeasonStatByQuery(InternalQuery params) {
        return getEntitiesByQuery(params, CompetitorSeasonStat.class);
    }

    public static List<CompetitorSeasonStat> getCompetitorSeasonsByIds(List<Long> ids) {
        return getEntitiesByIds(ids, CompetitorSeasonStat.class);
    }

    public static List<CompetitorSeasonStat> getCompetitorSeasonsStatByQuery(InternalQuery params) {
        return getEntitiesByQuery(params, CompetitorSeasonStat.class);
    }

    public static List<Long> getCompetitorSeasonIdsByQuery(InternalQuery params) {
        return getEntityIdsByQuery(params, CompetitorSeasonStat.class);
    }


    public static long saveMatchReview(MatchReview match) {
        return saveEntity(match, MatchReview.class);
    }

    public static boolean deleteMatchReview(Long id) {
        return deleteEntity(id, MatchReview.class);
    }

    public static MatchReview getMatchReviewById(Long id) {
        return getEntityById(id, MatchReview.class);
    }


    public static List<MatchReview> getMatchReviewsByIds(List<Long> ids) {
        return getEntitiesByIds(ids, MatchReview.class);
    }


    public static long saveMatchStats(MatchStats matchStats) {
        return saveEntity(matchStats, MatchStats.class);
    }


    public static CompetitorSeasonStat getCompetitorSeasonStatByCsidAndCompetitor(Long csid, Long competitorId, CompetitorType type) {
        InternalQuery query = new InternalQuery();
        query.addCriteria(new InternalCriteria("csid", "eq", csid));
        query.addCriteria(new InternalCriteria("competitor_id", "eq", competitorId));
        query.addCriteria(new InternalCriteria("type", "eq", type));
        List<CompetitorSeasonStat> lists = getCompetitorSeasonsStatByQuery(query);
        MatchAction matchAction=new MatchAction();

        if (CollectionUtils.isNotEmpty(lists)) {
            return lists.get(0);
        }
        return null;
    }


    public static Player getPlayerBySodaId(String sodaId) {
        InternalQuery query = new InternalQuery();
        query.addCriteria(new InternalCriteria("deleted", "eq", false));
        query.addCriteria(new InternalCriteria("soda_id", "eq", sodaId));
        PageRequest pageRequest = new PageRequest(0, 1);
        query.with(pageRequest);
        List<Long> ids = getEntityIdsByQuery(query, Player.class);
        if (CollectionUtils.isEmpty(ids)) {
            LOG.warn("fail to getPlayer By sodaId : {}", sodaId);
            return null;
        }
        return getEntityById(ids.get(0), Player.class);
    }

    public static Player getPlayerByStatsId(String statsId) {
        InternalQuery query = new InternalQuery();
        query.addCriteria(new InternalCriteria("deleted", "eq", false));
        query.addCriteria(new InternalCriteria("stats_id", "eq", statsId));
        PageRequest pageRequest = new PageRequest(0, 1);
        query.with(pageRequest);
        List<Long> ids = getEntityIdsByQuery(query, Player.class);
        if (CollectionUtils.isEmpty(ids)) {
            return null;
        }
        return getEntityById(ids.get(0), Player.class);
    }


    public static Match getMatchByStatsId(String statsId) {
        InternalQuery query = new InternalQuery();
        query.addCriteria(new InternalCriteria("deleted", "eq", false));
        query.addCriteria(new InternalCriteria("stats_id", "eq", statsId));
        PageRequest pageRequest = new PageRequest(0, 1);
        query.with(pageRequest);
        List<Long> ids = getEntityIdsByQuery(query, Match.class);
        if (CollectionUtils.isEmpty(ids)) {
            return null;
        }
        return getEntityById(ids.get(0), Match.class);
    }

    public static Match getMatchBySodaId(String sodaId) {
        InternalQuery query = new InternalQuery();
        query.addCriteria(new InternalCriteria("deleted", "eq", false));
        query.addCriteria(new InternalCriteria("soda_id", "eq", sodaId));
        PageRequest pageRequest = new PageRequest(0, 1);
        query.with(pageRequest);
        List<Long> ids = getEntityIdsByQuery(query, Match.class);
        if (CollectionUtils.isEmpty(ids)) {
            return null;
        }
        return getEntityById(ids.get(0), Match.class);
    }

    public static Match getMatchByPartnerIdAndType(String partnerId, int partnerType) {
        InternalQuery query = new InternalQuery();
        query.addCriteria(new InternalCriteria("deleted", "eq", false));
        query.addCriteria(new InternalCriteria("partner_id", "eq", partnerId));
        query.addCriteria(new InternalCriteria("partner_type", "eq", partnerType));
        PageRequest pageRequest = new PageRequest(0, 1);
        query.with(pageRequest);
        List<Match> matches = getEntitiesByQuery(query, Match.class);
        if (CollectionUtils.isEmpty(matches)) {
            return null;
        }
        return matches.get(0);
    }


    public static List<MatchAction> getMatchActionsByMid(long mid) {
        InternalQuery query = new InternalQuery();

        query.addCriteria(new InternalCriteria("mid", "eq", mid));
        List<Long> ids = getEntityIdsByQuery(query, MatchAction.class);
        if (CollectionUtils.isEmpty(ids)) {
            return Collections.EMPTY_LIST;
        }
        return getEntitiesByIds(ids, MatchAction.class);
    }


    public static List<TeamSeason> getTeamSeasonsByTeamIdAndCSId(long teamId, long csid) {
        InternalQuery query = new InternalQuery();

        query.addCriteria(new InternalCriteria("tid", "eq", teamId));
        query.addCriteria(new InternalCriteria("csid", "eq", csid));
        List<Long> ids = getEntityIdsByQuery(query, TeamSeason.class);
        if (CollectionUtils.isEmpty(ids)) {
            return null;
        }
        return getEntitiesByIds(ids, TeamSeason.class);
    }

}
