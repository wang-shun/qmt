package com.lesports.qmt.sbc.service.impl;

import com.google.common.collect.Lists;
import com.lesports.api.common.CallerParam;
import com.lesports.id.api.IdType;
import com.lesports.id.client.LeIdApis;
import com.lesports.mongo.repository.MongoCrudRepository;
import com.lesports.qmt.QmtOperationType;
import com.lesports.qmt.sbc.api.dto.TTopicItemPackage;
import com.lesports.qmt.sbc.cache.TTopicItemPackageCache;
import com.lesports.qmt.sbc.converter.TTopicItemPackageConverter;
import com.lesports.qmt.sbc.model.TopicItemPackage;
import com.lesports.qmt.sbc.repository.TopicItemPackageRepository;
import com.lesports.qmt.sbc.service.TopicItemPackageService;
import com.lesports.qmt.sbc.service.support.AbstractSbcService;
import com.lesports.utils.LeDateUtils;
import com.lesports.utils.math.LeNumberUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.springframework.data.mongodb.core.query.Update.update;

/**
 * Created by denghui on 2016/11/2.
 */
@Service("topicItemPackageService")
public class TopicItemPackageServiceImpl extends AbstractSbcService<TopicItemPackage, Long> implements TopicItemPackageService {

    @Resource
    protected TopicItemPackageRepository packageRepository;
    @Resource
    protected TTopicItemPackageCache packageCache;
    @Resource
    private TTopicItemPackageConverter packageConverter;

    @Override
    protected MongoCrudRepository<TopicItemPackage, Long> getInnerRepository() {
        return packageRepository;
    }

    @Override
    protected QmtOperationType getOpreationType(TopicItemPackage entity) {
        if (LeNumberUtils.toLong(entity.getId()) <= 0) {
            return QmtOperationType.CREATE;
        }
        return QmtOperationType.UPDATE;
    }

    @Override
    protected boolean doCreate(TopicItemPackage entity) {
        entity.setId(LeIdApis.nextId(IdType.TOPIC_ITEM_PACKAGE));
        entity.setDeleted(false);
        return packageRepository.save(entity);
    }

    @Override
    protected boolean doAfterCreate(TopicItemPackage entity) {
        return packageCache.delete(entity.getId());
    }

    @Override
    protected boolean doUpdate(TopicItemPackage entity) {
        return packageRepository.save(entity);
    }

    @Override
    protected boolean doAfterUpdate(TopicItemPackage entity) {
        return packageCache.delete(entity.getId());
    }

    @Override
    protected boolean doDelete(Long id) {
        String now = LeDateUtils.formatYYYYMMDDHHMMSS(new Date());
        Update up = update("deleted", true).set("update_at", now);
        return packageRepository.update(id, up);
    }

    @Override
    protected boolean doAfterDelete(TopicItemPackage entity) {
        return packageCache.delete(entity.getId());
    }

    @Override
    protected TopicItemPackage doFindExistEntity(TopicItemPackage entity) {
        return findOne(entity.getId());
    }

    @Override
    public TTopicItemPackage getTTopicItemPackageById(long id, CallerParam caller) {
        TTopicItemPackage dto = packageCache.findOne(id);
        if (dto == null) {
            TopicItemPackage model = packageRepository.findOne(id);
            if (model == null) {
                LOG.warn("fail to getTTopicItemPackageById, topic item package no exists. id : {}, caller : {}", id, caller);
                return null;
            }
            dto = packageConverter.toDto(model);
            if (dto == null) {
                LOG.warn("fail to getTTopicItemPackageById, toTVo fail. id : {}, caller : {}", id, caller);
                return null;
            }
            packageCache.save(dto);
        }
        return dto;
    }

    @Override
    public List<TTopicItemPackage> getTTopicItemPackagesByIds(List<Long> ids, CallerParam caller) {
        if (CollectionUtils.isEmpty(ids)) {
            return Collections.EMPTY_LIST;
        }
        List<TTopicItemPackage> tTopicItemPackages = Lists.newArrayListWithExpectedSize(ids.size());
        for (final Long id : ids) {
            TTopicItemPackage tTopicItemPackage = getTTopicItemPackageById(id, caller);
            if (tTopicItemPackage != null) {
                tTopicItemPackages.add(tTopicItemPackage);
            }
        }
        return tTopicItemPackages;
    }

    @Override
    public List<Long> getTopicItemPackageIdsByTopicId(long topicId, CallerParam caller) {
        return packageRepository.findIdsByTopicId(topicId);
    }

}
