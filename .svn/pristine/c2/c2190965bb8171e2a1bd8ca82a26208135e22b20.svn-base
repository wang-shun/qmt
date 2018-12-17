package com.lesports.qmt.sbd.service.impl;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.lesports.id.api.IdType;
import com.lesports.id.client.LeIdApis;
import com.lesports.mongo.repository.MongoCrudRepository;
import com.lesports.qmt.QmtOperationType;
import com.lesports.qmt.msg.core.ActionType;
import com.lesports.qmt.msg.core.BusinessType;
import com.lesports.qmt.msg.core.LeMessage;
import com.lesports.qmt.msg.core.LeMessageBuilder;
import com.lesports.qmt.msg.producer.SwiftMessageApis;
import com.lesports.qmt.sbd.api.dto.TCompetitorSeasonStat;
import com.lesports.qmt.sbd.api.service.GetCompetitorSeasonStatsParam;
import com.lesports.qmt.sbd.cache.TCompetitorSeasonStatCache;
import com.lesports.qmt.sbd.converter.TCompetitorSeasonStatVoConverter;
import com.lesports.qmt.sbd.model.CompetitorSeasonStat;
import com.lesports.qmt.sbd.model.Player;
import com.lesports.qmt.sbd.repository.CompetitorSeasonStatRepository;
import com.lesports.qmt.sbd.service.CompetitorSeasonStatService;
import com.lesports.qmt.sbd.service.support.AbstractSbdService;
import com.lesports.qmt.service.support.QmtService;
import com.lesports.query.InternalQuery;
import com.lesports.utils.LeDateUtils;
import com.lesports.utils.math.LeNumberUtils;
import org.apache.commons.beanutils.LeBeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Update.update;

/**
 * User: ellios
 * Time: 15-6-7 : 上午10:31
 */
@Service("competitorSeasonStatService")
public class CompetitorSeasonStatServiceImpl  extends AbstractSbdService<CompetitorSeasonStat,Long> implements CompetitorSeasonStatService {

    protected static final Logger LOG = LoggerFactory.getLogger(CompetitorSeasonStatServiceImpl.class);

    @Resource
    private CompetitorSeasonStatRepository competitorSeasonStatRepository;

    @Resource
    private TCompetitorSeasonStatVoConverter tCompetitorSeasonStatVoConverter;

    @Resource
    private TCompetitorSeasonStatCache competitorSeasonStatCache;


    @Override
    public boolean save(CompetitorSeasonStat entity) {
        String now = LeDateUtils.formatYYYYMMDDHHMMSS(new Date());
        boolean result = false;
        if (entity.getId() == null) {
            //没有id,说明是新建的
            entity.setId(LeIdApis.nextId(IdType.COMPETITOR_SEASON_STAT));
            entity.setCreateAt(now);
            entity.setUpdateAt(now);
            result = competitorSeasonStatRepository.save(entity);
        } else {
            CompetitorSeasonStat existEntity = competitorSeasonStatRepository.findOne(entity.getId());
            if (existEntity == null) {
                existEntity = entity;
            } else {
                LeBeanUtils.copyNotEmptyPropertiesQuietly(existEntity, entity);
            }
            existEntity.setUpdateAt(now);
            result = competitorSeasonStatRepository.save(existEntity);
            if (result) {
                competitorSeasonStatCache.delete(existEntity.getId());
            }
        }
        return result;
    }

    @Override
    public CompetitorSeasonStat findOne(Long id) {
        return competitorSeasonStatRepository.findOne(id);
    }

    @Override
    public boolean delete(Long id) {
        competitorSeasonStatCache.delete(id);
        return competitorSeasonStatRepository.delete(id);
    }

    @Override
    public List<CompetitorSeasonStat> findByCompetitorId(Long competitorId) {
        return competitorSeasonStatRepository.findByCompetitorId(competitorId);
    }

    @Override
    public CompetitorSeasonStat findOneByCsidAndCompetitorId(Long csid, Long competitorId) {
        return competitorSeasonStatRepository.findOneByCsidAndCompetitorId(csid, competitorId);
    }

    @Override
    public List<Long> getCompetitorSeasonStatIds(GetCompetitorSeasonStatsParam p) {

        Query q = new Query();
        if(p.getCompetitorId()>0){
            q.addCriteria(where("competitor_id").is(p.getCompetitorId()));
        }
        if (p.getCid() > 0) {
            q.addCriteria(where("cid").is(p.getCid()));
        }
        if (p.getCsid() > 0) {
            q.addCriteria(where("csid").is(p.getCsid()));
        }
		if (CollectionUtils.isNotEmpty(p.getCompetitorIds())) {
			q.addCriteria(where("competitor_id").in(p.getCompetitorIds()));
		}
		return competitorSeasonStatRepository.findIdByQuery(q);
    }

    @Override
    public List<TCompetitorSeasonStat> getTCompetitorSeasonStatsByIds(List<Long> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return Collections.EMPTY_LIST;
        }
        List<TCompetitorSeasonStat> tCompetitorSeasonStatses = Lists.newArrayListWithExpectedSize(ids.size());
        for (Long id : ids) {
            TCompetitorSeasonStat tCompetitorSeasonStats = getTCompetitorSeasonStatById(id);
            if (tCompetitorSeasonStats != null) {
                tCompetitorSeasonStatses.add(tCompetitorSeasonStats);
            }
        }
        return tCompetitorSeasonStatses;
    }

    private TCompetitorSeasonStat getTCompetitorSeasonStatById(long id) {

        TCompetitorSeasonStat tCompetitorSeasonStat = competitorSeasonStatCache.findOne(id);
        if (tCompetitorSeasonStat == null) {
            CompetitorSeasonStat competitorSeasonStat = competitorSeasonStatRepository.findOne(id);
            if (null == competitorSeasonStat) {
                LOG.warn("fail to getTCompetitorSeasonStatById, competitorSeasonStat no exists. id : {}", id);
                return null;
            }
            tCompetitorSeasonStat = tCompetitorSeasonStatVoConverter.toDto(competitorSeasonStat);
            if (tCompetitorSeasonStat == null) {
                LOG.warn("fail to getTCompetitorSeasonStatById, toTCompetitorSeasonStat fail. id : {}", id);
                return null;
            }
            competitorSeasonStatCache.save(tCompetitorSeasonStat);
        }

        return tCompetitorSeasonStat;

    }

    @Override
    protected MongoCrudRepository getInnerRepository() {
        return competitorSeasonStatRepository;
    }

    @Override
    public List<CompetitorSeasonStat> findByIds(List<Long> longs) {
        return null;
    }

    @Override
    public List<Long> findIdsByQuery(InternalQuery internalQuery) {
        return null;
    }

    @Override
    public List<CompetitorSeasonStat> findByQuery(InternalQuery internalQuery) {
        return null;
    }

    @Override
    public long countByQuery(InternalQuery internalQuery) {
        return 0;
    }

    @Override
    protected QmtOperationType getOpreationType(CompetitorSeasonStat entity) {
        if (LeNumberUtils.toLong(entity.getId()) > 0) {
            return QmtOperationType.UPDATE;
        }
        return QmtOperationType.CREATE;
    }



    @Override
    protected CompetitorSeasonStat doFindExistEntity(CompetitorSeasonStat entity) {
        return competitorSeasonStatRepository.findOne(entity.getId());
    }

    @Override
    protected boolean doAfterDelete(CompetitorSeasonStat entity) {
        LeMessage message = LeMessageBuilder.create().
                setEntityId(entity.getId()).
                setIdType(IdType.COMPETITOR_SEASON_STAT).
                setData(JSON.toJSONString(entity)).
                setBusinessTypes(ActionType.DELETE, ImmutableList.of(BusinessType.DATA_SYNC, BusinessType.SEARCH_INDEX)).
                build();
        SwiftMessageApis.sendMsgAsync(message);
        return true;
    }

    @Override
    protected boolean doAfterCreate(CompetitorSeasonStat entity) {
        LeMessage message = LeMessageBuilder.create().
                setEntityId(entity.getId()).
                setIdType(IdType.COMPETITOR_SEASON_STAT).
                setData(JSON.toJSONString(entity)).
                setBusinessTypes(ActionType.ADD, ImmutableList.of(BusinessType.DATA_SYNC, BusinessType.SEARCH_INDEX)).
                build();
        SwiftMessageApis.sendMsgAsync(message);
        return true;
    }

    @Override
    protected boolean doDelete(Long id) {
        String now = LeDateUtils.formatYYYYMMDDHHMMSS(new Date());
        Update up = update("deleted", true).set("update_at", now);
        return competitorSeasonStatRepository.update(id, up);
    }

    @Override
    protected boolean doCreate(CompetitorSeasonStat entity) {
        entity.setId(LeIdApis.nextId(IdType.COMPETITOR_SEASON_STAT));
        return competitorSeasonStatRepository.save(entity);
    }

    @Override
    protected boolean doUpdate(CompetitorSeasonStat entity) {
        return competitorSeasonStatRepository.save(entity);
    }

    @Override
    protected boolean doAfterUpdate(CompetitorSeasonStat entity) {
        LeMessage message = LeMessageBuilder.create().
                setEntityId(entity.getId()).
                setIdType(IdType.COMPETITOR_SEASON_STAT).
                setData(JSON.toJSONString(entity)).
                setBusinessTypes(ActionType.UPDATE, ImmutableList.of(BusinessType.DATA_SYNC, BusinessType.SEARCH_INDEX)).
                build();
        SwiftMessageApis.sendMsgAsync(message);
        return true;
    }
}
