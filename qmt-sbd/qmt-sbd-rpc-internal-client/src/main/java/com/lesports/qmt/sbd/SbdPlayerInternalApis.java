package com.lesports.qmt.sbd;

import com.lesports.qmt.sbd.api.common.CareerScopeType;
import com.lesports.qmt.sbd.api.common.CareerStatType;
import com.lesports.qmt.sbd.model.Partner;
import com.lesports.qmt.sbd.model.Player;
import com.lesports.qmt.sbd.model.PlayerCareerStat;
import com.lesports.query.InternalCriteria;
import com.lesports.query.InternalQuery;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.data.domain.PageRequest;

import java.util.List;

/**
 * User: lufei
 * Time: 16-10-30 : 下午9:32
 */
public class SbdPlayerInternalApis extends SbdInternalApis {


    public static long savePlayer(Player player) {
        return saveEntity(player, Player.class);
    }

    public static boolean deletePlayer(Long id) {
        return deleteEntity(id, Player.class);
    }

    public static long savePlayer(Player player, boolean allowEmpty) {
        return saveEntity(player, Player.class, allowEmpty);
    }

    public static Player getPlayerById(Long id) {
        return getEntityById(id, Player.class);
    }

    public static List<Player> getPlayersByIds(List<Long> ids) {
        return getEntitiesByIds(ids, Player.class);
    }

    public static List<Player> getPlayersByQuery(InternalQuery params) {
        return getEntitiesByQuery(params, Player.class);
    }

    public static List<Long> getPlayerIdsByQuery(InternalQuery params) {
        return getEntityIdsByQuery(params, Player.class);
    }

    public static long savePlayerCareerStat(PlayerCareerStat playerCareerStat) {
        return saveEntity(playerCareerStat, PlayerCareerStat.class);
    }

    public static List<PlayerCareerStat> getPlayerCareerStatsByQuery(InternalQuery params) {
        return getEntitiesByQuery(params, PlayerCareerStat.class);
    }

    public static long countPlayersByQuery(InternalQuery params) {
        return countEntitiesByQuery(params, Player.class);
    }

    public static Player getPlayerByPartner(Partner partner) {
        InternalQuery query = new InternalQuery();
        InternalCriteria partnerCriteria = new InternalCriteria("partners");
        partnerCriteria.elemMatch(new InternalCriteria("id", "eq", partner.getId()).andOperator(new InternalCriteria("type", "eq", partner.getType())));
        query.addCriteria(partnerCriteria);
        query.addCriteria(new InternalCriteria("deleted", "eq", false));
        List<Player> players = getPlayersByQuery(query);
        if (CollectionUtils.isNotEmpty(players)) {
            return players.get(0);
        }
        return null;
    }

    public static Player getPlayerByPartnerIdAndPartnerType(String partnerId, int partnerType) {
        InternalQuery query = new InternalQuery();
        query.addCriteria(new InternalCriteria("deleted", "eq", false));
        query.addCriteria(new InternalCriteria("partner_id", "eq", partnerId));
        query.addCriteria(new InternalCriteria("partner_type", "eq", partnerType));
        PageRequest pageRequest = new PageRequest(0, 1);
        query.with(pageRequest);
        List<Long> ids = getEntityIdsByQuery(query, Player.class);
        if (CollectionUtils.isEmpty(ids)) {
            return null;
        }
        return getEntityById(ids.get(0), Player.class);
    }

    public static List<PlayerCareerStat> getPlayerCareerStatByPidAndScopeAndScopeIdAndType(Long pid, CareerScopeType scopeType, Long scope, CareerStatType type) {
        InternalQuery query = new InternalQuery();
        query.addCriteria(new InternalCriteria("deleted", "eq", false));
        query.addCriteria(new InternalCriteria("player_id", "is", pid));
        query.addCriteria(new InternalCriteria("scope_type", "eq", scopeType));
        query.addCriteria(new InternalCriteria("scope_id", "is", pid));
        query.addCriteria(new InternalCriteria("stat_type", "eq", scopeType));
        PageRequest pageRequest = new PageRequest(0, 1);
        query.with(pageRequest);
        List<PlayerCareerStat> stats = getPlayerCareerStatsByQuery(query);
        return stats;
    }

}
