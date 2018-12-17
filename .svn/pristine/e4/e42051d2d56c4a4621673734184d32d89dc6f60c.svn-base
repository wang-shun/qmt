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
import com.lesports.qmt.sbd.api.common.CareerScopeType;
import com.lesports.qmt.sbd.api.common.TeamType;
import com.lesports.qmt.sbd.api.dto.TPlayerCareerStat;
import com.lesports.qmt.sbd.cache.TPlayerCareerStatCache;
import com.lesports.qmt.sbd.converter.TPlayerCareerStatVoConverter;
import com.lesports.qmt.sbd.model.PlayerCareerStat;
import com.lesports.qmt.sbd.repository.PlayerCareerStatRepository;
import com.lesports.qmt.sbd.service.PlayerCareerStatService;
import com.lesports.qmt.sbd.service.support.AbstractSbdService;
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
import static org.springframework.data.mongodb.core.query.Query.query;
import static org.springframework.data.mongodb.core.query.Update.update;

/**
 * Created by lufei1 on 2016/8/16.
 */
@Service("playCareerStatService")
public class PlayerCareerStatServiceImpl extends AbstractSbdService<PlayerCareerStat, Long> implements PlayerCareerStatService {

    private static final Logger LOG = LoggerFactory.getLogger(PlayerCareerStatServiceImpl.class);

    @Resource
    private PlayerCareerStatRepository playerCareerStatRepository;

    @Resource
    private TPlayerCareerStatVoConverter tPlayerCareerStatVoConverter;

    @Resource
    private TPlayerCareerStatCache playerCareerStatCache;

    @Override
    protected MongoCrudRepository<PlayerCareerStat, Long> getInnerRepository() {
        return playerCareerStatRepository;
    }


    @Override
    public boolean save(PlayerCareerStat entity) {
        String now = LeDateUtils.formatYYYYMMDDHHMMSS(new Date());
        boolean result = false;
        if (entity.getId() == null || 0 == entity.getId()) {
            //没有id,说明是新建的
            entity.setId(LeIdApis.nextId(IdType.PLAYER_CAREER_STAT));
            entity.setCreateAt(now);
            entity.setUpdateAt(now);
            entity.setDeleted(false);
            result = playerCareerStatRepository.save(entity);
            LOG.info("insert playerCareerStat entity:{}", entity);
        } else {
            PlayerCareerStat existEntity = playerCareerStatRepository.findOne(entity.getId());
            if (existEntity == null) {
                existEntity = entity;
            } else {
                LeBeanUtils.copyNotEmptyPropertiesQuietly(existEntity, entity);
            }
            existEntity.setUpdateAt(now);
            LOG.info("update playerCareerStat entity:{}", existEntity);
            result = playerCareerStatRepository.save(existEntity);
        }
        if (result) {
            playerCareerStatCache.delete(entity.getId());
        }
        return result;
    }

    @Override
    public PlayerCareerStat findOne(Long id) {
        return playerCareerStatRepository.findOne(id);
    }

    @Override
    public boolean delete(Long aLong) {
        return false;
    }

    @Override
    public List<TPlayerCareerStat> getTPlayerCareerStats(long playerId, long scopeId, CareerScopeType scopeType, CallerParam caller) {
        Query q = query(where("deleted").is(false));
        if (playerId > 0) {
            q.addCriteria(where("player_id").is(playerId));
        }
        if (scopeId > 0) {
            q.addCriteria(where("scope_id").is(scopeId));
        }
        if (null != scopeType) {
            q.addCriteria(where("scope_type").is(scopeType));
        }
        List<Long> ids = playerCareerStatRepository.findIdByQuery(q);
        return getTPlayerCareerStatByIds(ids, caller);
    }

    @Override
    public TPlayerCareerStat getTPlayerCareerStatById(long id, CallerParam caller) {
        TPlayerCareerStat vo = playerCareerStatCache.findOne(id);
        if (vo == null) {
            PlayerCareerStat playerCareerStat = playerCareerStatRepository.findOne(id);
            if (playerCareerStat == null) {
                LOG.warn("fail to getTPlayerCareerStatById, getTPlayerCareerStatById no exists. id : {}, caller : {}", id, caller);
                return null;
            }

            vo = tPlayerCareerStatVoConverter.toDto(playerCareerStat);
            if (vo == null) {
                LOG.warn("fail to getTPlayerCareerStatById, convert vo fail. id : {}, caller : {}", id, caller);
                return null;
            }
            playerCareerStatCache.save(vo);
        }
        tPlayerCareerStatVoConverter.adaptDto(vo, caller);
        return vo;
    }

    @Override
    public List<TPlayerCareerStat> getTPlayerCareerStatByIds(List<Long> ids, CallerParam caller) {
        if (CollectionUtils.isEmpty(ids)) {
            return Collections.EMPTY_LIST;
        }
        List<TPlayerCareerStat> playerCareerStats = Lists.newArrayListWithExpectedSize(ids.size());
        for (Long id : ids) {
            TPlayerCareerStat playerCareerStat = getTPlayerCareerStatById(id, caller);
            if (playerCareerStat != null) {
                playerCareerStats.add(playerCareerStat);
            }
        }
        return playerCareerStats;
    }


    @Override
    protected QmtOperationType getOpreationType(PlayerCareerStat entity) {
        if (LeNumberUtils.toLong(entity.getId()) > 0) {
            return QmtOperationType.UPDATE;
        }
        return QmtOperationType.CREATE;
    }


    @Override
    protected PlayerCareerStat doFindExistEntity(PlayerCareerStat entity) {
        return playerCareerStatRepository.findOne(entity.getId());
    }

    @Override
    protected boolean doAfterDelete(PlayerCareerStat entity) {
        LeMessage message = LeMessageBuilder.create().
                setEntityId(entity.getId()).
                setIdType(IdType.PLAYER_CAREER_STAT).
                setData(JSON.toJSONString(entity)).
                setBusinessTypes(ActionType.DELETE, ImmutableList.of(BusinessType.DATA_SYNC, BusinessType.SEARCH_INDEX)).
                build();
        SwiftMessageApis.sendMsgAsync(message);
        return true;
    }

    @Override
    protected boolean doAfterCreate(PlayerCareerStat entity) {
        LeMessage message = LeMessageBuilder.create().
                setEntityId(entity.getId()).
                setIdType(IdType.PLAYER_CAREER_STAT).
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
        return playerCareerStatRepository.update(id, up);
    }

    @Override
    protected boolean doCreate(PlayerCareerStat entity) {
        entity.setId(LeIdApis.nextId(IdType.PLAYER_CAREER_STAT));
        return playerCareerStatRepository.save(entity);
    }

    @Override
    protected boolean doUpdate(PlayerCareerStat entity) {
        return playerCareerStatRepository.save(entity);
    }

    @Override
    protected boolean doAfterUpdate(PlayerCareerStat entity) {
        LeMessage message = LeMessageBuilder.create().
                setEntityId(entity.getId()).
                setIdType(IdType.PLAYER_CAREER_STAT).
                setData(JSON.toJSONString(entity)).
                setBusinessTypes(ActionType.UPDATE, ImmutableList.of(BusinessType.DATA_SYNC, BusinessType.SEARCH_INDEX)).
                build();
        SwiftMessageApis.sendMsgAsync(message);
        return true;
    }
}
