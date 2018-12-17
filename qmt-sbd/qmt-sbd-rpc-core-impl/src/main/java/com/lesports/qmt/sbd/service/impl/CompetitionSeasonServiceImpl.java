package com.lesports.qmt.sbd.service.impl;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.lesports.api.common.CallerParam;
import com.lesports.id.api.IdType;
import com.lesports.id.client.LeIdApis;
import com.lesports.mongo.repository.MongoCrudRepository;
import com.lesports.qmt.QmtOperationType;
import com.lesports.qmt.msg.core.ActionType;
import com.lesports.qmt.msg.core.BusinessType;
import com.lesports.qmt.msg.core.LeMessage;
import com.lesports.qmt.msg.core.LeMessageBuilder;
import com.lesports.qmt.msg.producer.SwiftMessageApis;
import com.lesports.qmt.sbd.api.dto.TCompetition;
import com.lesports.qmt.sbd.api.dto.TCompetitionSeason;
import com.lesports.qmt.sbd.cache.TCompetitionSeasonCache;
import com.lesports.qmt.sbd.converter.TCompetitionSeasonVoConverter;
import com.lesports.qmt.sbd.helper.CompetitionSeasonHelper;
import com.lesports.qmt.sbd.model.CompetitionSeason;
import com.lesports.qmt.sbd.repository.CompetitionSeasonRepository;
import com.lesports.qmt.sbd.service.CompetitionSeasonService;
import com.lesports.qmt.sbd.service.support.AbstractSbdService;
import com.lesports.utils.LeDateUtils;
import com.lesports.utils.math.LeNumberUtils;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;
import static org.springframework.data.mongodb.core.query.Update.update;

/**
 * Created by lufei on 2016/10/24.
 */
@Service("competitionSeasonService")
public class CompetitionSeasonServiceImpl extends AbstractSbdService<CompetitionSeason, Long> implements CompetitionSeasonService {

    private static final Logger LOG = LoggerFactory.getLogger(CompetitionSeasonServiceImpl.class);

    @Resource
    private CompetitionSeasonRepository competitionSeasonRepository;
    @Resource
    private TCompetitionSeasonCache competitionSeasonCache;
    @Resource
    private TCompetitionSeasonVoConverter tCompetitionSeasonVoConverter;
    @Resource
    private CompetitionSeasonHelper competitionSeasonHelper;


    @Override
    public CompetitionSeason findOne(Long id) {
        return competitionSeasonRepository.findOne(id);
    }

    @Override
    protected MongoCrudRepository<CompetitionSeason, Long> getInnerRepository() {
        return competitionSeasonRepository;
    }

    @Override
    protected QmtOperationType getOpreationType(CompetitionSeason entity) {
        if (LeNumberUtils.toLong(entity.getId()) > 0) {
            return QmtOperationType.UPDATE;
        }
        return QmtOperationType.CREATE;
    }

    @Override
    protected boolean doCreate(CompetitionSeason entity) {
        entity.setId(LeIdApis.nextId(IdType.COMPETITION_SEASON));
        entity.setDeleted(false);
        return competitionSeasonRepository.save(entity);
    }

    @Override
    protected boolean doAfterCreate(CompetitionSeason entity) {
        LeMessage message = LeMessageBuilder.create().
                setEntityId(entity.getId()).
                setIdType(IdType.COMPETITION_SEASON).
                setData(JSON.toJSONString(entity)).
                setBusinessTypes(ActionType.ADD, ImmutableList.of(BusinessType.DATA_SYNC)).
                build();
        SwiftMessageApis.sendMsgAsync(message);
        return true;
    }

    @Override
    protected boolean doUpdate(CompetitionSeason entity) {
        return competitionSeasonRepository.save(entity);
    }

    @Override
    protected boolean doAfterUpdate(CompetitionSeason entity) {
        LeMessage message = LeMessageBuilder.create().
                setEntityId(entity.getId()).
                setIdType(IdType.COMPETITION_SEASON).
                setData(JSON.toJSONString(entity)).
                setBusinessTypes(ActionType.UPDATE, ImmutableList.of(BusinessType.DATA_SYNC)).
                build();
        SwiftMessageApis.sendMsgAsync(message);
        return true;
    }

    @Override
    protected boolean doDelete(Long id) {
        String now = LeDateUtils.formatYYYYMMDDHHMMSS(new Date());
        Update up = update("deleted", true).set("update_at", now);
        return competitionSeasonRepository.update(id, up);
    }

    @Override
    protected boolean doAfterDelete(CompetitionSeason entity) {
        LeMessage message = LeMessageBuilder.create().
                setEntityId(entity.getId()).
                setIdType(IdType.COMPETITION_SEASON).
                setData(JSON.toJSONString(entity)).
                setBusinessTypes(ActionType.DELETE, ImmutableList.of(BusinessType.DATA_SYNC)).
                build();
        SwiftMessageApis.sendMsgAsync(message);
        return true;
    }

    @Override
    protected CompetitionSeason doFindExistEntity(CompetitionSeason entity) {
        return competitionSeasonRepository.findOne(entity.getId());
    }

    @Override
    public List<CompetitionSeason> findByCid(long cid) {
        return competitionSeasonRepository.findByCid(cid);
    }

    @Override
    public List<CompetitionSeason> findByCid(long cid, String season) {
        return competitionSeasonRepository.findByCid(cid, season);
    }

    @Override
    public CompetitionSeason findLatestByCid(long cid) {
        return competitionSeasonHelper.getLatestSeasonByCid(cid);
    }

    @Override
    public List<Long> getSeasonIdsOfCompetition(long cid, CallerParam caller) {
        return null;
    }


    @Override
    public List<TCompetitionSeason> getTCompetitionSeasonsByIds(List<Long> ids, CallerParam caller) {
        if (CollectionUtils.isEmpty(ids)) {
            return Collections.EMPTY_LIST;
        }
        List<TCompetitionSeason> seasons = Lists.newArrayListWithExpectedSize(ids.size());
        for (Long id : ids) {
            TCompetitionSeason season = getTCompetitionSeasonById(id, caller);
            if (season != null) {
                seasons.add(season);
            }
        }
        return seasons;
    }

    @Override
    public TCompetitionSeason getTCompetitionSeasonById(long id, CallerParam caller) {
        TCompetitionSeason vo = competitionSeasonCache.findOne(id);
        if (vo == null) {
            CompetitionSeason competitionSeason = competitionSeasonRepository.findOne(id);
            if (competitionSeason == null) {
                LOG.warn("fail to getTCompetitionById, competition no exists. id : {}, caller : {}", id, caller);
                return null;
            }
            vo = tCompetitionSeasonVoConverter.toDto(competitionSeason);
            if (vo == null) {
                LOG.warn("fail to getTCompetitionById, convert vo fail. id : {}, caller : {}", id, caller);
                return null;
            }
            competitionSeasonCache.save(vo);
        }
        tCompetitionSeasonVoConverter.adaptDto(vo, caller);
        return vo;
    }

    @Override
    public TCompetitionSeason getLatestTCompetitionSeasonsByCId(long cid, CallerParam caller) {
        CompetitionSeason competitionSeason = findLatestByCid(cid);
        if (competitionSeason == null) {
            return null;
        }
        return getTCompetitionSeasonById(competitionSeason.getId(), caller);
    }

    @Override
    public TCompetitionSeason getTCompetitionSeasonByCidAndSeason(long cid,String season, CallerParam caller) {
        Query q = query(where("cid").is(cid));
        q.addCriteria(where("season").is(season));
        addInternationalCriteriaToQuery(q, caller);
        List<Long> ids = competitionSeasonRepository.findIdByQuery(q);
        if (CollectionUtils.isEmpty(ids)) {
            return null;
        }
        return getTCompetitionSeasonById(ids.get(0),caller);
    }


}
