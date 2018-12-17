package com.lesports.qmt.sbd;

import com.lesports.qmt.sbd.model.Partner;
import com.lesports.qmt.sbd.model.Team;
import com.lesports.query.InternalCriteria;
import com.lesports.query.InternalQuery;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;

/**
 * User: lufei
 * Time: 16-10-30 : 下午9:32
 */
public class SbdTeamInternalApis extends SbdInternalApis {


    public static long saveTeam(Team team) {
        return saveEntity(team, Team.class);
    }

    public static long saveTeam(Team team, boolean allowEmpty) {
        return saveEntity(team, Team.class, allowEmpty);
    }

    public static boolean deleteTeam(Long id) {
        return deleteEntity(id, Team.class);
    }

    public static Team getTeamById(Long id) {
        return getEntityById(id, Team.class);
    }

    public static List<Team> getTeamsByIds(List<Long> ids) {
        return getEntitiesByIds(ids, Team.class);
    }

    public static List<Team> getTeamsByQuery(InternalQuery params) {
        return getEntitiesByQuery(params, Team.class);
    }

    public static List<Long> getTeamIdsByQuery(InternalQuery params) {
        return getEntityIdsByQuery(params, Team.class);
    }

    public static long countTeamsByQuery(InternalQuery params) {
        return countEntitiesByQuery(params, Team.class);
    }

    public static Team getTeamByPartner(Partner partner) {
        InternalQuery query = new InternalQuery();
        InternalCriteria partnerCriteria = new InternalCriteria("partners");
        partnerCriteria.elemMatch(new InternalCriteria("id", "eq", partner.getId()).andOperator(new InternalCriteria("type", "eq", partner.getType())));
        query.addCriteria(partnerCriteria);
        query.addCriteria(new InternalCriteria("deleted", "eq", false));
        List<Team> teams = getTeamsByQuery(query);
        if (CollectionUtils.isNotEmpty(teams)) {
            return teams.get(0);
        }
        return null;
    }

    public static Team getTeamByPartnerIdAndPartnerType(String partnerId, int partnerType) {
        InternalQuery query = new InternalQuery();
        query.addCriteria(new InternalCriteria("deleted", "eq", false));
        query.addCriteria(new InternalCriteria("partner_id", "eq", partnerId));
        query.addCriteria(new InternalCriteria("partner_type", "eq", partnerType));
        List<Team> teams = getTeamsByQuery(query);
        if (CollectionUtils.isEmpty(teams)) return null;
        return teams.get(0);
    }


}
