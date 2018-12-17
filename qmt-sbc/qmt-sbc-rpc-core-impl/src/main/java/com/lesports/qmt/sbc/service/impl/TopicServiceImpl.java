package com.lesports.qmt.sbc.service.impl;

import com.google.common.collect.Lists;
import com.lesports.api.common.CallerParam;
import com.lesports.api.common.PublishStatus;
import com.lesports.id.api.IdType;
import com.lesports.id.client.LeIdApis;
import com.lesports.mongo.repository.MongoCrudRepository;
import com.lesports.qmt.QmtOperationType;
import com.lesports.qmt.msg.core.ActionType;
import com.lesports.qmt.msg.core.BusinessType;
import com.lesports.qmt.msg.core.LeMessage;
import com.lesports.qmt.msg.core.LeMessageBuilder;
import com.lesports.qmt.msg.producer.SwiftMessageApis;
import com.lesports.qmt.sbc.api.dto.TTopic;
import com.lesports.qmt.sbc.cache.TTopicCache;
import com.lesports.qmt.sbc.converter.TTopicConverter;
import com.lesports.qmt.sbc.model.Topic;
import com.lesports.qmt.sbc.repository.TopicRepository;
import com.lesports.qmt.sbc.service.TopicService;
import com.lesports.qmt.sbc.service.support.AbstractSbcService;
import com.lesports.utils.LeDateUtils;
import com.lesports.utils.math.LeNumberUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.springframework.data.mongodb.core.query.Update.update;

/**
 * Created by denghui on 2016/11/2.
 */
@Service("topicService")
public class TopicServiceImpl extends AbstractSbcService<Topic, Long> implements TopicService {

    @Resource
    protected TopicRepository topicRepository;
    @Resource
    protected TTopicCache topicCache;
    @Resource
    protected TTopicConverter topicConverter;

    @Override
    protected MongoCrudRepository<Topic, Long> getInnerRepository() {
        return topicRepository;
    }

    @Override
    protected QmtOperationType getOpreationType(Topic entity) {
        if (LeNumberUtils.toLong(entity.getId()) <= 0 || findOne(entity.getId()) == null) {
            return QmtOperationType.CREATE;
        }
        return QmtOperationType.UPDATE;
    }

    @Override
    protected boolean doCreate(Topic entity) {
        if (entity.getId() == null) {
            entity.setId(LeIdApis.nextId(IdType.TOPIC));
        }
        entity.setOnline(LeNumberUtils.toBoolean(entity.getDraft())?PublishStatus.OFFLINE:PublishStatus.ONLINE);
        if (entity.getOnline() == PublishStatus.ONLINE) {
            entity.setPublishAt(entity.getCreateAt());
        }
        entity.setDeleted(false);
        addRelatedTags(entity);
        return topicRepository.save(entity);
    }

    @Override
    protected boolean doAfterCreate(Topic entity) {
        topicCache.delete(entity.getId());
        List<BusinessType> businessTypes = Lists.newArrayList();
        if (!indexEntity(entity.getId(), IdType.TOPIC.toString())) {
            businessTypes.add(BusinessType.SEARCH_INDEX);
        }
        LeMessage message = LeMessageBuilder.create()
                .setEntityId(entity.getId())
                .setIdType(IdType.TOPIC)
                .setBusinessTypes(ActionType.ADD, businessTypes)
                .build();
        SwiftMessageApis.sendMsgAsync(message);
        return true;
    }

    @Override
    protected boolean doUpdate(Topic entity) {
        Topic existsEntity = doFindExistEntity(entity);
        if (existsEntity.getOnline() == PublishStatus.OFFLINE && entity.getOnline() == PublishStatus.ONLINE) {
            //发布状态改了,更新发布时间
            entity.setPublishAt(entity.getUpdateAt());
        }
        addRelatedTags(entity);
        return topicRepository.save(entity);
    }

    private void addRelatedTags(Topic entity) {
        // 选择频道、项目、赛事时需要自动打标签
        entity.addRelatedId(entity.getCid());
        entity.addRelatedId(entity.getChannelId());
        entity.addRelatedId(entity.getSubChannelId());
    }

    @Override
    protected boolean doAfterUpdate(Topic entity) {
        topicCache.delete(entity.getId());
        List<BusinessType> businessTypes = Lists.newArrayList();
        if (!indexEntity(entity.getId(), IdType.TOPIC.toString())) {
            businessTypes.add(BusinessType.SEARCH_INDEX);
        }
        LeMessage message = LeMessageBuilder.create()
                .setEntityId(entity.getId())
                .setIdType(IdType.TOPIC)
                .setBusinessTypes(ActionType.UPDATE, businessTypes)
                .build();
        SwiftMessageApis.sendMsgAsync(message);
        return true;
    }

    @Override
    protected boolean doDelete(Long id) {
        String now = LeDateUtils.formatYYYYMMDDHHMMSS(new Date());
        Update up = update("deleted", true).set("update_at", now);
        return topicRepository.update(id, up);
    }

    @Override
    protected boolean doAfterDelete(Topic entity) {
        topicCache.delete(entity.getId());
        List<BusinessType> businessTypes = Lists.newArrayList();
        if (!indexEntity(entity.getId(), IdType.TOPIC.toString())) {
            businessTypes.add(BusinessType.SEARCH_INDEX);
        }
        LeMessage message = LeMessageBuilder.create()
                .setEntityId(entity.getId())
                .setIdType(IdType.TOPIC)
                .setBusinessTypes(ActionType.DELETE, businessTypes)
                .build();
        SwiftMessageApis.sendMsgAsync(message);
        return true;
    }

    @Override
    protected Topic doFindExistEntity(Topic entity) {
        return findOne(entity.getId());
    }

    @Override
    public TTopic getTTopicById(long id, CallerParam caller) {
        TTopic tTopic = topicCache.findOne(id);
        if (tTopic == null) {
            Topic news = topicRepository.findOne(id);
            if (news == null) {
                LOG.warn("fail to getTTopicById, topic no exists. id : {}, caller : {}", id, caller);
                return null;
            }
            if (news.getDeleted()) {
                LOG.warn("fail to getTTopicById, topic is deleted. id : {}, caller : {}", id, caller);
                return null;
            }
            if (news.getOnline().equals(PublishStatus.OFFLINE)) {
                LOG.warn("fail to getTTopicById, topic is offline. id : {}, caller : {}", id, caller);
                return null;
            }
            tTopic = topicConverter.toDto(news);
            if (tTopic == null) {
                LOG.warn("fail to getTTopicById, toTVo fail. id : {}, caller : {}", id, caller);
                return null;
            }
            topicCache.save(tTopic);
        }
        return tTopic;
    }

    @Override
    public List<TTopic> getTTopicsByIds(List<Long> ids, CallerParam caller) {
        if (CollectionUtils.isEmpty(ids)) {
            return Collections.EMPTY_LIST;
        }
        List<TTopic> tTopics = Lists.newArrayListWithExpectedSize(ids.size());
        for (final Long id : ids) {
            TTopic tTopic = getTTopicById(id, caller);
            if (tTopic != null) {
                tTopics.add(tTopic);
            }
        }
        return tTopics;
    }
}
