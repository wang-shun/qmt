package com.lesports.qmt.sbd.repository.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.lesports.api.common.CountryLangId;
import com.lesports.api.common.MatchStatus;
import com.lesports.id.api.IdType;
import com.lesports.id.client.LeIdApis;
import com.lesports.mongo.repository.support.AbstractMongoRepository;
import com.lesports.qmt.config.api.dto.DictEntryTopType;
import com.lesports.qmt.sbd.api.common.MedalType;
import com.lesports.qmt.sbd.api.dto.TMatchTotalList;
import com.lesports.qmt.sbd.helper.CompetitionSeasonHelper;
import com.lesports.qmt.sbd.helper.DictHelper;
import com.lesports.qmt.sbd.model.CompetitionSeason;
import com.lesports.qmt.sbd.model.Match;
import com.lesports.qmt.sbd.repository.MatchRepository;
import com.lesports.qmt.sbd.utils.StringUtil;
import com.lesports.utils.PageUtils;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.mapreduce.GroupBy;
import org.springframework.data.mongodb.core.mapreduce.GroupByResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.CriteriaDefinition;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

/**
 * Created by lufei on 2016/10/28.
 */
@Repository("matchRepository")
public class MatchRepositoryImpl extends AbstractMongoRepository<Match, Long> implements MatchRepository {

    private static final Logger LOG = LoggerFactory.getLogger(MatchRepositoryImpl.class);

    @Resource
    private CompetitionSeasonHelper competitionSeasonHelper;
    @Resource
    private DictHelper dictHelper;


    @Override
    public List<Match> findByCIdAndCsidByStatus(Long cid, Long csid, Boolean stated, String matchTime, MatchStatus status) {
        Query query = query(where("deleted").is(false));
        query.addCriteria(where("cid").is(cid));
        query.addCriteria(where("csid").is(csid));
        if (null != stated)
            query.addCriteria(where("stated").is(stated));
        if (StringUtils.isNotEmpty(matchTime)) {
            query.addCriteria(where("startTime").lte(matchTime));
        }
        query.addCriteria(where("status").is(status));
        return findByQuery(query);
    }



    @Override
    public boolean save(Match entity) {
        return super.save(entity);
    }

    @Override
    protected Class<Match> getEntityType() {
        return Match.class;
    }

    @Override
    protected Long getId(Match entity) {
        return entity.getId();
    }

    @Override
    public List<Match> findByCompetitorIdAndCsid(Long competitorId, Long csid) {
        Query q = query(where("deleted").is(false));
        q.addCriteria(where("competitors.competitor_id").is(competitorId));
        q.addCriteria(where("csid").is(csid));
        return findByQuery(q);
    }



    @Override
    public List<Match> findByDateAndStatusAndPartnerType(String date, String Status, Integer PartnerType) {
        Query query = query(where("deleted").is(false));
        query.addCriteria(where("start_date").is(date));
        query.addCriteria(where("status").ne(Status));
        query.addCriteria(where("partner_type").is(PartnerType));
        return findByQuery(query);
    }

    @Override
    public List<Match> findEndedMatchByPartnerType(Integer partnerType) {
        Query query = query(where("deleted").is(false));
        query.addCriteria(where("status").is("MATCH_END"));
        query.addCriteria(where("partner_type").is(partnerType));
        return findByQuery(query);
    }

    @Override
    public Match findBasicOneByQuery(Query q) {
        q.fields().exclude("competitor_stats").exclude("squads");
        return findOneByQuery(q);
    }

    @Override
    public Match findBasicOne(Long id) {
        Query q = query(where("_id").is(id));
        q.fields().exclude("competitor_stats").exclude("squads");
        return findOneByQuery(q);
    }

    @Override
    public Page<Match> findByCompetitorId(Long competitorId, Pageable pageable) {
        Pageable validPageable = PageUtils.getValidPageable(pageable);
        Map<String, Object> params = Maps.newHashMap();
        params.put("deleted", false);
        params.put("competitors.competitor_id", competitorId);
        return findByCondition(params, validPageable);
    }

    @Override
    public List<Long> findAllIdsByCompetitorId(Long competitorId) {
        Query q = query(where("deleted").is(false));
        q.addCriteria(where("competitors.competitor_id").is(competitorId));
        return findIdByQuery(q);
    }

    @Override
    public List<Match> findAllIdsByCid(Long cid) {
        Query q = query(where("deleted").is(false));
        q.addCriteria(where("cid").is(cid));
        return findByQuery(q);
    }

    @Override
    public Page<Match> findByCid(Long cid, Pageable pageable) {
        Pageable validPageable = PageUtils.getValidPageable(pageable);
        Map<String, Object> params = Maps.newHashMap();
        params.put("deleted", false);
        params.put("cid", cid);
        return findByCondition(params, validPageable);
    }

    @Override
    public List<Long> findEidListByQuery(Query query, Pageable pageable) {
        query.fields().equals("eids.id");
        Sort sort = new Sort(Sort.Direction.ASC, "start_time");
        query.with(sort);
        return this.findLongByQuery(query.with(pageable));
    }

    @Override
    public Page<Long> findEidsByQuery(Query query, Pageable pageable) {
        Long total = Long.valueOf(this.countByQuery(query));
        List ids;
        if(total == 0L) {
            ids = Collections.emptyList();
            return new PageImpl(ids, pageable, 0L);
        } else {
            query.fields().equals("eids.id");
            Sort sort = new Sort(Sort.Direction.ASC, "start_time");
            query.with(sort);
            ids = this.findLongByQuery(query.with(pageable));
            return new PageImpl(ids, pageable, total.longValue());
        }
    }

    private List<Long> findLongByQuery(Query query) {
        Assert.notNull(query);
        List entityList = this.operations.find(query, this.getEntityType());
        if(CollectionUtils.isEmpty(entityList)) {
            return Collections.EMPTY_LIST;
        } else {
            List<Long> ids = Lists.newArrayList();
            for (Object anEntityList : entityList) {
                Match entity = (Match) anEntityList;
                if (entity != null && CollectionUtils.isNotEmpty(entity.getEids())) {
                    for(CountryLangId cli:entity.getEids()){
                        ids.add(cli.getId());
                    }
                }
            }

            return ids;
        }
    }

    @Override
    public Page<Match> findByCsid(Long csid, Pageable pageable) {
        Pageable validPageable = PageUtils.getValidPageable(pageable);
        Map<String, Object> params = Maps.newHashMap();
        params.put("deleted", false);
        params.put("csid", csid);
        return findByCondition(params, validPageable);
    }

    @Override
    public Page<Match> findAll(Pageable pageable) {
        Pageable validPageable = PageUtils.getValidPageable(pageable);
        Map<String, Object> params = Maps.newHashMap();
        params.put("deleted", false);
        return findByCondition(params, validPageable);
    }

    @Override
    public List<Match> findBypartnerIdAndType(String partnerId, Integer partnerType) {
        Query query = query(where("deleted").is(false));
        query.addCriteria(where("partner_id").is(partnerId));
        query.addCriteria(where("partner_type").is(partnerType));
        return findByQuery(query);
    }

    @Override
    public List<Long> findMatchIdsOfCompetition(long cid, long csid, Pageable pageable) {
        Query q = query(where("deleted").is(false));
        q.addCriteria(where("cid").is(cid));

        boolean needPageable = true;
        if (csid > 0) {
            //获取某赛季
            q.addCriteria(where("csid").is(csid));
            needPageable = false;
        } else if (csid == 0) {
            //赛季id为0，获取最新赛季
            CompetitionSeason season = competitionSeasonHelper.getLatestSeasonByCid(cid);
            if (season != null) {
                q.addCriteria(where("csid").is(season.getId()));
                needPageable = false;
            }
        }

        if (needPageable) {
            pageable = PageUtils.getValidPageable(pageable);
            q.with(pageable);
        }

        return findIdByQuery(q);
    }

    private List<TMatchTotalList> findTotalListByCidGroupDateMedal(Long cid, Long discipline, MedalType medalType) {
        List<TMatchTotalList> medalCountList = Lists.newArrayList();
        GroupBy groupBy = GroupBy.key("local_start_date").initialDocument("{count:0}")
                .reduceFunction("function(key, values){values.count+=1;}");

        Criteria criteria = new Criteria();
        criteria.and("cid").is(cid);
        criteria.and("discipline").is(discipline);
        criteria.and("deleted").is(false);
        criteria.and("medal_type").is(medalType);
        GroupByResults<TMatchTotalList> r = operations.group(criteria, "matches", groupBy, TMatchTotalList.class);
        BasicDBList list = (BasicDBList) r.getRawResults().get("retval");
        if (list.size() > 0) {
            TMatchTotalList temp;
            for (int i = 0; i < list.size(); i++) {
                BasicDBObject obj = (BasicDBObject) list.get(i);
                temp = new TMatchTotalList();
                temp.setDate(StringUtil.valueOf(obj.get("local_start_date")));
                temp.setGoldMedalCount(((Double) obj.get("count")).intValue());
                medalCountList.add(temp);
            }
        }
        return medalCountList;
    }

    @Override
    public List<Long> findMatchIdsByCidAndMetaEntryId(long cid, long csid, long entryId, Pageable pageable) {
        Query q = query(where("deleted").is(false));
        q.addCriteria(where("cid").is(cid));

        boolean needPageable = true;
        if (csid > 0) {
            //获取某赛季
            q.addCriteria(where("csid").is(csid));
            needPageable = false;
        } else if (csid == 0) {
            //赛季id为0，获取最新赛季
            CompetitionSeason season = competitionSeasonHelper.getLatestSeasonByCid(cid);
            if (season != null) {
                q.addCriteria(where("csid").is(season.getId()));
                needPageable = false;
            }
        }

        CriteriaDefinition criteria = getCriteriaByMetaEntryId(entryId);
        if (criteria != null) {
            q.addCriteria(criteria);
        }

        if (needPageable) {
            pageable = PageUtils.getValidPageable(pageable);
            q.with(pageable);
        }

        return findIdByQuery(q);
    }

    private CriteriaDefinition getCriteriaByMetaEntryId(long entryId) {
        if (entryId <= 0) {
            return null;
        }
        IdType idType = LeIdApis.checkIdTye(entryId);
        if (idType == null || idType != IdType.DICT_ENTRY) {
            LOG.warn("illegal meta entry Id : {}", entryId);
            return null;
        }
        DictEntryTopType entryType = dictHelper.getDictEntryTopType(entryId);
        if (entryType == DictEntryTopType.GAME_FIRST_TYPE) {
            return where("game_f_type").is(entryId);
        } else if (entryType == DictEntryTopType.ROUND) {
            return where("round").is(entryId);
        } else if (entryType == DictEntryTopType.STAGE) {
            return where("stage").is(entryId);
        } else if (entryType == DictEntryTopType.STATION) {
            return where("substation").is(entryId);
        } else if (entryType == DictEntryTopType.GROUP) {
            return where("group").is(entryId);
        } else {
            LOG.warn("illegal meta entry id : {}", entryId);
            return null;
        }
    }

    @Override
    public boolean updateMSyncToCloud(long matchId, Boolean syncToCloud) {
        Update up = new Update();
        up.set("is_sync_to_Cloud", syncToCloud);
        return update(matchId, up);
    }

    @Override
    public List<Long> getTheRoadOfAdvance(long cid, long csid) {
        Query q = query(where("deleted").is(false));
        if (csid > 0) {
            //获取某赛季
            q.addCriteria(where("csid").is(csid));
        } else if (csid == 0) {
            //赛季id为0，获取最新赛季
            CompetitionSeason season = competitionSeasonHelper.getLatestSeasonByCid(cid);
            if (season != null) {
                q.addCriteria(where("csid").is(season.getId()));
            }
        }
        q.addCriteria(where("the_road_order").exists(true));
        return findIdByQuery(q);
    }

}
