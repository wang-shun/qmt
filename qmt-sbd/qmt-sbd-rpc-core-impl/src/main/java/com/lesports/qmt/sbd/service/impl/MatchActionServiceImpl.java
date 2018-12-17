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
import com.lesports.qmt.sbd.api.dto.TMatchAction;
import com.lesports.qmt.sbd.api.service.GetMacthActionsParam;
import com.lesports.qmt.sbd.cache.TMatchActionCache;
import com.lesports.qmt.sbd.converter.TMatchActionVoConverter;
import com.lesports.qmt.sbd.model.MatchAction;
import com.lesports.qmt.sbd.model.MatchAction;
import com.lesports.qmt.sbd.repository.MatchActionRepository;
import com.lesports.qmt.sbd.service.MatchActionService;
import com.lesports.qmt.sbd.service.support.AbstractSbdService;
import com.lesports.utils.LeDateUtils;
import com.lesports.utils.math.LeNumberUtils;
import org.apache.commons.beanutils.LeBeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;

/**
 * User: ellios
 * Time: 15-6-7 : 上午10:41
 */
@Repository("matchActionService")
public class MatchActionServiceImpl extends AbstractSbdService<MatchAction, Long> implements MatchActionService {

    private static final Logger LOG = LoggerFactory.getLogger(MatchActionServiceImpl.class);

    @Resource
    private MatchActionRepository matchActionRepository;

    @Resource
    private TMatchActionVoConverter matchActionVoConverter;

    @Resource
    private TMatchActionCache matchActionCache;

    @Override
    public boolean save(MatchAction entity) {
        String now = LeDateUtils.formatYYYYMMDDHHMMSS(new Date());
        boolean result = false;
        if (entity.getId() == null) {
            //没有id,说明是新建的
            entity.setId(LeIdApis.nextId(IdType.MATCH_ACTION));
            entity.setCreateAt(now);
            result = matchActionRepository.save(entity);
        } else {
            MatchAction existEntity = matchActionRepository.findOne(entity.getId());
            if (existEntity == null) {
                existEntity = entity;
            } else {
                LeBeanUtils.copyNotEmptyPropertiesQuietly(existEntity, entity);
            }
            result = matchActionRepository.save(existEntity);
        }
        if (result) {
            //给消息中心发送消息
            LeMessage message = LeMessageBuilder.create().
                    setEntityId(entity.getId()).
                    setIdType(IdType.MATCH_ACTION).
                    setData(JSON.toJSONString(entity)).
                    setBusinessTypes(ActionType.UPDATE, ImmutableList.of(BusinessType.DATA_SYNC, BusinessType.SEARCH_INDEX)).
                    build();
            SwiftMessageApis.sendMsgAsync(message);
        }
        return result;
    }

    @Override
    public MatchAction findOne(Long id) {
        return matchActionRepository.findOne(id);
    }

    @Override
    public boolean delete(Long id) {
        matchActionCache.delete(id);
        return matchActionRepository.delete(id);
    }


    @Override
    public List<MatchAction> findByMid(Long mid, Pageable pageable) {
        return matchActionRepository.findByMid(mid, pageable);
    }


    @Override
    public List<TMatchAction> getTMatchActionsByIds(List<Long> ids,CallerParam caller) {
        if (CollectionUtils.isEmpty(ids)) {
            return Collections.EMPTY_LIST;
        }
        List<TMatchAction> actions = Lists.newArrayListWithExpectedSize(ids.size());
        for (Long id : ids) {
            TMatchAction action = getTMatchActionById(id,caller);
            if (action != null) {
                actions.add(action);
            }
        }
        return actions;
    }

    @Override
    public TMatchAction getTMatchActionById(long id,CallerParam caller){
        TMatchAction vo = matchActionCache.findOne(id);
        if (vo == null) {
            MatchAction matchAction = matchActionRepository.findOne(id);
            if (matchAction == null) {
                LOG.warn("fail to getTMatchActionById, match no exists. id : {}, caller : {}", id, caller);
                return null;
            }

            vo = matchActionVoConverter.toDto(matchAction);
            if (vo == null) {
                LOG.warn("fail to getTMatchActionById, convert vo fail. id : {}, caller : {}", id, caller);
                return null;
            }
            matchActionCache.save(vo);
        }
        matchActionVoConverter.adaptDto(vo, caller);
        return vo;
    }

    @Override
    public List<MatchAction> findMatchActionByMid(Long mid) {
        return matchActionRepository.findMatchActionByMid(mid);
    }

    @Override
    public List<Long> getMatchActionsOfMatch(GetMacthActionsParam p, CallerParam caller) {
        Query q = new Query();
        q.addCriteria(where("mid").is(p.getMid()));
        if (CollectionUtils.isNotEmpty(p.getTypes())) {
            q.addCriteria(where("event_type").in(p.getTypes()));
        }
        q.with(new Sort(Sort.Direction.ASC, "passed_time"));
        List<Long> ids = matchActionRepository.findIdByQuery(q);
        if (CollectionUtils.isEmpty(ids)) {
            return Collections.EMPTY_LIST;
        }
        return ids;
    }

    @Override
    protected MongoCrudRepository getInnerRepository() {
        return matchActionRepository;
    }

    @Override
    protected QmtOperationType getOpreationType(MatchAction entity) {
        if (LeNumberUtils.toLong(entity.getId()) > 0) {
            return QmtOperationType.UPDATE;
        }
        return QmtOperationType.CREATE;
    }

    @Override
    protected boolean doDelete(Long id) {
        return true;
    }

    @Override
    protected boolean doAfterUpdate(MatchAction entity) {
        LeMessage message = LeMessageBuilder.create().
                setEntityId(entity.getId()).
                setIdType(IdType.MATCH_ACTION).
                setData(JSON.toJSONString(entity)).
                setBusinessTypes(ActionType.UPDATE, ImmutableList.of(BusinessType.DATA_SYNC)).
                build();
        SwiftMessageApis.sendMsgAsync(message);
        return true;
    }

    @Override
    protected boolean doUpdate(MatchAction entity) {
        return matchActionRepository.save(entity);
    }

    @Override
    protected boolean doAfterDelete(MatchAction entity) {
        LeMessage message = LeMessageBuilder.create().
                setEntityId(entity.getId()).
                setIdType(IdType.MATCH_ACTION).
                setData(JSON.toJSONString(entity)).
                setBusinessTypes(ActionType.DELETE, ImmutableList.of(BusinessType.DATA_SYNC)).
                build();
        SwiftMessageApis.sendMsgAsync(message);
        return true;
    }

    @Override
    protected boolean doAfterCreate(MatchAction entity) {
        LeMessage message = LeMessageBuilder.create().
                setEntityId(entity.getId()).
                setIdType(IdType.MATCH_ACTION).
                setData(JSON.toJSONString(entity)).
                setBusinessTypes(ActionType.ADD, ImmutableList.of(BusinessType.DATA_SYNC)).
                build();
        SwiftMessageApis.sendMsgAsync(message);
        return true;
    }

    @Override
    protected boolean doCreate(MatchAction entity) {
        entity.setId(LeIdApis.nextId(IdType.MATCH_ACTION));
        return matchActionRepository.save(entity);
    }

    @Override
    protected MatchAction doFindExistEntity(MatchAction entity) {
        return matchActionRepository.findOne(entity.getId());
    }
}
