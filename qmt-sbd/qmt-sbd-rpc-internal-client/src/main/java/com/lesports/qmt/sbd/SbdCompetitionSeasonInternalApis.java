package com.lesports.qmt.sbd;

import com.lesports.qmt.sbd.model.CompetitionSeason;
import com.lesports.query.InternalCriteria;
import com.lesports.query.InternalQuery;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;

/**
 * User: lufei
 * Time: 16-10-30 : 下午9:32
 */
public class SbdCompetitionSeasonInternalApis extends SbdInternalApis {


    public static long saveCompetitionSeason(CompetitionSeason competitionSeason) {
        return saveEntity(competitionSeason, CompetitionSeason.class);
    }

    public static boolean deleteCompetitionSeason(Long id) {
        return deleteEntity(id, CompetitionSeason.class);
    }

    public static CompetitionSeason getCompetitionSeasonById(Long id) {
        return getEntityById(id, CompetitionSeason.class);
    }

    public static List<CompetitionSeason> getCompetitionSeasonsByIds(List<Long> ids) {
        return getEntitiesByIds(ids, CompetitionSeason.class);
    }

    public static List<CompetitionSeason> getCompetitionSeasonsByQuery(InternalQuery params) {
        return getEntitiesByQuery(params, CompetitionSeason.class);
    }

    public static long countCompetitionSeasonsByQuery(InternalQuery params) {
        return countEntitiesByQuery(params, CompetitionSeason.class);
    }

    public static List<CompetitionSeason> getCompetitionSeasonsByCid(long cid) {
        InternalQuery query = new InternalQuery();
        query.addCriteria(new InternalCriteria("cid", "eq", cid));
        List<Long> ids = getEntityIdsByQuery(query, CompetitionSeason.class);
        if (CollectionUtils.isEmpty(ids)) {
            return null;
        }
        return getEntitiesByIds(ids, CompetitionSeason.class);
    }

    public static CompetitionSeason getLatestCompetitionSeasonByCid(long cid) {
        InternalQuery query = new InternalQuery();
        query.addCriteria(new InternalCriteria("cid", "eq", cid));
        query.setSort(new Sort(Sort.Direction.DESC, "season"));
        PageRequest pageRequest = new PageRequest(0, 1);
        query.with(pageRequest);
        List<CompetitionSeason> competitionSeasonsList = getEntitiesByQuery(query, CompetitionSeason.class);
        if (CollectionUtils.isEmpty(competitionSeasonsList)) {
            return null;
        }
        return competitionSeasonsList.get(0);
    }

    public static CompetitionSeason getCompetitionSeasonByCidAndSeason(long cid, String season) {
        InternalQuery query = new InternalQuery();
        query.addCriteria(new InternalCriteria("cid", "eq", cid));
        query.addCriteria(new InternalCriteria("season", "eq", season));
        PageRequest pageRequest = new PageRequest(0, 1);
        query.with(pageRequest);
        List<CompetitionSeason> competitionSeasonsList = getEntitiesByQuery(query, CompetitionSeason.class);
        if (CollectionUtils.isEmpty(competitionSeasonsList)) {
            return null;
        }
        return competitionSeasonsList.get(0);
    }

}
