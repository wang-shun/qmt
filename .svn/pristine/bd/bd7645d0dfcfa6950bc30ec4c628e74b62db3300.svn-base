package com.lesports.qmt.sbd.service.impl;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.ImmutableList;
import com.lesports.id.api.IdType;
import com.lesports.id.client.LeIdApis;
import com.lesports.mongo.repository.MongoCrudRepository;
import com.lesports.qmt.QmtOperationType;
import com.lesports.qmt.msg.core.ActionType;
import com.lesports.qmt.msg.core.BusinessType;
import com.lesports.qmt.msg.core.LeMessage;
import com.lesports.qmt.msg.core.LeMessageBuilder;
import com.lesports.qmt.msg.producer.SwiftMessageApis;
import com.lesports.qmt.sbd.model.Competition;
import com.lesports.qmt.sbd.model.CompetitorSeasonStat;
import com.lesports.qmt.sbd.model.LiveEvent;
import com.lesports.qmt.sbd.model.Match;
import com.lesports.qmt.sbd.repository.LiveEventRepository;
import com.lesports.qmt.sbd.service.LiveEventService;
import com.lesports.qmt.sbd.service.support.AbstractSbdService;
import com.lesports.utils.LeDateUtils;
import com.lesports.utils.math.LeNumberUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Created by qiaohongxin on 2015/9/22.
 */

@Service("LiveEventService")
public class LiveEventServiceImpl extends AbstractSbdService<LiveEvent, Long> implements LiveEventService {
    @Resource
    LiveEventRepository liveEventRepository;

    public Page<LiveEvent> findAll(Pageable pageable) {
        return liveEventRepository.findAll(pageable);
    }


    @Override
    public LiveEvent findOne(Long id) {
        return liveEventRepository.findOne(id);
    }

    @Override
    protected boolean doUpdate(LiveEvent entity) {
        return liveEventRepository.save(entity);
    }

    @Override
    protected LiveEvent doFindExistEntity(LiveEvent entity) {
        return liveEventRepository.findOne(entity.getId());
    }

    @Override
    protected boolean doCreate(LiveEvent entity) {
        return liveEventRepository.save(entity);
    }

    @Override
    protected boolean doDelete(Long entity) {
        return liveEventRepository.delete(entity);
    }

    @Override
    protected boolean doAfterUpdate(LiveEvent entity) {
        return true;
    }

    @Override
    protected QmtOperationType getOpreationType(LiveEvent entity) {
        if (LeNumberUtils.toLong(entity.getId()) > 0) {
            return QmtOperationType.UPDATE;
        }
        if (entity.getId() == null) {
            entity.setId(LeIdApis.nextId(IdType.DICT_ENTRY));
        }
        return QmtOperationType.CREATE;
    }


    @Override
    protected boolean doAfterCreate(LiveEvent entity) {
        return true;
    }

    @Override
    protected boolean doAfterDelete(LiveEvent entity) {
        return true;
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }

    @Override
    protected MongoCrudRepository<LiveEvent, Long> getInnerRepository() {
        return liveEventRepository;
    }
}
