package com.lesports.qmt.sbd;

import com.google.common.collect.Lists;
import com.lesports.qmt.sbd.model.Match;
import com.lesports.qmt.sbd.model.MatchAction;
import com.lesports.qmt.sbd.model.MatchStats;
import com.lesports.qmt.sbd.model.Partner;
import com.lesports.query.InternalCriteria;
import com.lesports.query.InternalQuery;
import org.apache.commons.collections.CollectionUtils;
import org.apache.thrift.TException;

import java.util.List;

/**
 * User: lufei
 * Time: 16-10-30 : 下午9:32
 */
public class SbdMatchInternalApis extends SbdInternalApis {


    public static long saveMatch(Match match) {
        return saveEntity(match, Match.class);
    }

    public static long saveMatch(Match match, boolean allowEmpty) {
        return saveEntity(match, Match.class, allowEmpty);
    }

    public static void doUpdateMatch(Match match) {
        try {
            sbdInternalService.doUpdateMatch(serialize(match));
        } catch (TException e) {
            LOG.error("fail to doUpdateMatch. match : {} . {}", match, e.getMessage(), e);
        }
    }

    public static boolean deleteMatch(Long id) {
        return deleteEntity(id, Match.class);
    }

    public static boolean deleteMatchAction(Long id) {
        return deleteEntity(id, MatchAction.class);
    }


    public static MatchStats getMatchStatsById(Long id) {
        return getEntityById(id, MatchStats.class);
    }

    public static Match getMatchById(Long id) {
        return getEntityById(id, Match.class);
    }

    public static List<Match> getMatchesByIds(List<Long> ids) {
        return getEntitiesByIds(ids, Match.class);
    }

    public static List<Match> getMatchesByQuery(InternalQuery params) {
        return getEntitiesByQuery(params, Match.class);
    }

    public static List<Long> getMatchIdsByQuery(InternalQuery params) {
        return getEntityIdsByQuery(params, Match.class);
    }

    public static Match getMatchByPartner(Partner partner) {
        InternalQuery query = new InternalQuery();
        InternalCriteria partnerCriteria = new InternalCriteria("partners");
        partnerCriteria.elemMatch(new InternalCriteria("id", "eq", partner.getId()).andOperator(new InternalCriteria("type", "eq", partner.getType())));
        query.addCriteria(partnerCriteria);
        query.addCriteria(new InternalCriteria("deleted", "eq", false));
        List<Match> match = getMatchesByQuery(query);
        if (CollectionUtils.isNotEmpty(match)) {
            return match.get(0);
        }
        return null;
    }

    public static Long getMatchIdByStartTimeAndCompetitorIds(String startDate, Long homeTeamId, Long awayTeamId) {
        InternalQuery query = new InternalQuery();
        List<Long> ids = Lists.newArrayList();
        ids.add(homeTeamId);
        ids.add(awayTeamId);
        query.addCriteria(new InternalCriteria("competitors.competitor_id", "all", ids));
        query.addCriteria(new InternalCriteria("start_date", "eq", startDate));
        query.addCriteria(new InternalCriteria("deleted", "eq", false));
        List<Match> match = getMatchesByQuery(query);
        if (CollectionUtils.isNotEmpty(match)) {
            return match.get(0).getId();
        }
        return 0L;

    }

    public static Match getMatchByStartTimeAndCompetitorIds(String startDate, Long homeTeamId, Long awayTeamId) {
        InternalQuery query = new InternalQuery();
        List<Long> ids = Lists.newArrayList();
        ids.add(homeTeamId);
        ids.add(awayTeamId);
        query.addCriteria(new InternalCriteria("competitors.competitor_id", "all", ids));
        query.addCriteria(new InternalCriteria("start_date", "eq", startDate));
        query.addCriteria(new InternalCriteria("deleted", "eq", false));
        List<Match> match = getMatchesByQuery(query);
        if (CollectionUtils.isNotEmpty(match)) {
            return match.get(0);
        }
        return null;

    }

    public static List<Match> getMatchByDateAndStatusAndPartnerType(String date, String status, Integer partenrType) {
        InternalQuery query = new InternalQuery();
        query.addCriteria(new InternalCriteria("deleted", "eq", false));
        query.addCriteria(new InternalCriteria("start_date", "eq", date));
        query.addCriteria(new InternalCriteria("status", "ne", status));
        query.addCriteria(new InternalCriteria("partner_type", "eq", partenrType));
        return getMatchesByQuery(query);
    }

    public static List<Match> getMatchByStatusAndPartnerType(String status, Integer partenrType) {
        InternalQuery query = new InternalQuery();
        query.addCriteria(new InternalCriteria("deleted", "eq", false));
        query.addCriteria(new InternalCriteria("status", "eq", status));
        query.addCriteria(new InternalCriteria("partner_type", "eq", partenrType));
        return getMatchesByQuery(query);
    }


}
