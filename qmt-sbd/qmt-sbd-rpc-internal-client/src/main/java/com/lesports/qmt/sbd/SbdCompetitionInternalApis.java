package com.lesports.qmt.sbd;

import com.lesports.qmt.sbd.model.Competition;
import com.lesports.query.InternalCriteria;
import com.lesports.query.InternalQuery;

import java.util.List;

/**
 * User: lufei
 * Time: 16-10-30 : 下午9:32
 */
public class SbdCompetitionInternalApis extends SbdInternalApis {


    public static long saveCompetition(Competition competition) {
        return saveEntity(competition, Competition.class);
    }

    public static boolean deleteCompetition(Long id) {
        return deleteEntity(id, Competition.class);
    }

    public static Competition getCompetitionById(Long id) {
        return getEntityById(id, Competition.class);
    }

    public static List<Competition> getCompetitionsByIds(List<Long> ids) {
        return getEntitiesByIds(ids, Competition.class);
    }

    public static List<Long> getCompetitionIdsByQuery(InternalQuery query) {
        return getEntityIdsByQuery(query, Competition.class);
    }

    public static List<Competition> getCompetitionsByQuery(InternalQuery params) {
        return getEntitiesByQuery(params, Competition.class);
    }

    public static long countCompetitionsByQuery(InternalQuery params) {
        return countEntitiesByQuery(params, Competition.class);
    }

    public static List<Competition> getCompetitionByNameAndGameFType(String name, long gameFType) {
        InternalQuery query = new InternalQuery();
        query.addCriteria(new InternalCriteria("name", "regex", name));
        query.addCriteria(new InternalCriteria("game_f_type", "eq", gameFType));
        return getCompetitionsByQuery(query);
    }
}
