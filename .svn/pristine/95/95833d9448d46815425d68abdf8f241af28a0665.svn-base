package com.lesports.qmt.tlive.service.impl;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.lesports.id.api.IdType;
import com.lesports.id.client.LeIdApis;
import com.lesports.mongo.repository.MongoCrudRepository;
import com.lesports.qmt.QmtConstants;
import com.lesports.qmt.QmtOperationType;
import com.lesports.qmt.msg.core.ActionType;
import com.lesports.qmt.msg.core.LeMessage;
import com.lesports.qmt.msg.core.LeMessageBuilder;
import com.lesports.qmt.msg.producer.SwiftMessageApis;
import com.lesports.qmt.tlive.api.common.TextLiveMessageType;
import com.lesports.qmt.tlive.api.dto.TTextLiveMessage;
import com.lesports.qmt.tlive.cache.TTextLiveMessageCache;
import com.lesports.qmt.tlive.converter.TextLiveMessageVoCreator;
import com.lesports.qmt.tlive.model.TextLiveMessage;
import com.lesports.qmt.tlive.query.TextLiveMessageQueryParams;
import com.lesports.qmt.tlive.repository.TextLiveMessageRepository;
import com.lesports.qmt.tlive.repository.redis.TextLiveMessageRedisRepository;
import com.lesports.qmt.tlive.service.TextLiveMessageService;
import com.lesports.qmt.tlive.service.support.AbstractTextLiveService;
import com.lesports.utils.LeDateUtils;
import com.lesports.utils.math.LeNumberUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import javax.annotation.Resource;
import java.util.*;
import java.util.regex.Pattern;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;
import static org.springframework.data.mongodb.core.query.Update.update;

/**
 * Created by lufei1 on 2015/9/14.
 */
@Service("textLiveMessageService")
public class TextLiveMessageServiceImpl extends AbstractTextLiveService<TextLiveMessage, Long> implements TextLiveMessageService {

    private static final Logger LOG = LoggerFactory.getLogger(TextLiveMessageServiceImpl.class);

    @Resource
    private TextLiveMessageRepository textLiveMessageRepository;
    @Resource
    private TTextLiveMessageCache textLiveMessageCache;
    @Resource
    private TextLiveMessageVoCreator textLiveMessageVoCreator;
    @Resource
    private TextLiveMessageRedisRepository textLiveMessageRedisRepository;


    @Override
    protected QmtOperationType getOpreationType(TextLiveMessage entity) {
        if (LeNumberUtils.toLong(entity.getId()) > 0) {
            return QmtOperationType.UPDATE;
        }
        return QmtOperationType.CREATE;
    }

    @Override
    protected boolean doCreate(TextLiveMessage entity) {
        entity.setId(LeIdApis.nextId(IdType.LIVE_MESSAGE));
        entity.setDeleted(false);
        return textLiveMessageRepository.save(entity);
    }

    @Override
    protected boolean doAfterCreate(TextLiveMessage entity) {
        //写入redis
        TTextLiveMessage liveMessage = textLiveMessageVoCreator.createTLiveMessage(entity.getId());
        if (liveMessage != null) {
            textLiveMessageCache.save(liveMessage);
        }
        //id分页
        processPageAfterAddSuccess(entity);
        //给消息中心发送消息
        LeMessage message = LeMessageBuilder.create().setEntityId(entity.getId())
                .setIdType(IdType.LIVE_MESSAGE).setBusinessTypes(ActionType.ADD, null).build();
        SwiftMessageApis.sendMsgAsync(message);
        LeMessage textLive = LeMessageBuilder.create().setEntityId(entity.getTextLiveId())
                .setIdType(IdType.TEXT_LIVE).setBusinessTypes(ActionType.UPDATE, null).build();
        SwiftMessageApis.sendMsgAsync(textLive);
        return true;
    }

    @Override
    protected boolean doUpdate(TextLiveMessage entity) {
        return textLiveMessageRepository.save(entity);
    }

    @Override
    protected boolean doAfterUpdate(TextLiveMessage entity) {
        //写入redis
        TTextLiveMessage liveMessage = textLiveMessageVoCreator.createTLiveMessage(entity.getId());
        if (liveMessage != null) {
            textLiveMessageCache.save(liveMessage);
        }
        //给消息中心发送消息
        LeMessage message = LeMessageBuilder.create().setEntityId(entity.getId())
                .setIdType(IdType.LIVE_MESSAGE).setBusinessTypes(ActionType.ADD, null).build();
        SwiftMessageApis.sendMsgAsync(message);
        LeMessage textLive = LeMessageBuilder.create().setEntityId(entity.getTextLiveId())
                .setIdType(IdType.TEXT_LIVE).setBusinessTypes(ActionType.UPDATE, null).build();
        SwiftMessageApis.sendMsgAsync(textLive);
        return true;
    }

    @Override
    protected boolean doDelete(Long id) {
        String now = LeDateUtils.formatYYYYMMDDHHMMSS(new Date());
        Update up = update("deleted", true).set("update_at", now);
        return textLiveMessageRepository.update(id, up);
    }

    @Override
    protected boolean doAfterDelete(TextLiveMessage entity) {
        textLiveMessageCache.delete(entity.getId());
        //给消息中心发送消息
        LeMessage message = LeMessageBuilder.create().setEntityId(entity.getTextLiveId())
                .setIdType(IdType.TEXT_LIVE).setBusinessTypes(ActionType.UPDATE, null).build();
        SwiftMessageApis.sendMsgAsync(message);
        return true;
    }

    @Override
    protected TextLiveMessage doFindExistEntity(TextLiveMessage entity) {
        return textLiveMessageRepository.findOne(entity.getId());
    }

    @Override
    public TTextLiveMessage getTLiveMessageId(long id) {
        TTextLiveMessage liveMessage = textLiveMessageCache.findOne(id);
        if (liveMessage == null) {
            liveMessage = textLiveMessageVoCreator.createTLiveMessage(id);
            if (liveMessage != null) {
                textLiveMessageCache.save(liveMessage);
            }
        }
        return liveMessage;
    }

    @Override
    public List<TTextLiveMessage> getTLiveMessageByIds(List<Long> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return Collections.EMPTY_LIST;
        }
        List<TTextLiveMessage> liveMessages = Lists.newArrayListWithExpectedSize(ids.size());
        for (Long id : ids) {
            TTextLiveMessage liveMessage = getTLiveMessageId(id);
            if (liveMessage != null) {
                liveMessages.add(liveMessage);
            }
        }
        return liveMessages;
    }

    @Override
    public List<String> getLiveMessageIdsByTextLiveId(final long textLiveId) {
        Set<String> liveMessageIdSet = textLiveMessageCache.zrange(textLiveId, null);
        if (CollectionUtils.isNotEmpty(liveMessageIdSet)) {
            return new ArrayList<String>(liveMessageIdSet);
        }
        LOG.info("get liveMessageIds from redis is null. textLiveId:{}", textLiveId);
        Query q = query(where("deleted").is(false));
        q.addCriteria(where("text_live_id").is(textLiveId));
        List<Long> ids = textLiveMessageRepository.findIdByQuery(q.with(new Sort(Sort.Direction.ASC, "id")));
        List<String> liveMessageIds = Lists.transform(ids, new Function<Long, String>() {
            @Nullable
            @Override
            public String apply(@Nullable Long id) {
                textLiveMessageCache.zadd(textLiveId, null, id);
                return String.valueOf(id);
            }
        });
        return liveMessageIds;
    }

    @Override
    public List<TextLiveMessage> findLiveMessageIdsByParams(TextLiveMessageQueryParams liveMessageQueryParams) {
        Query q = query(where("deleted").is(false));
        if (liveMessageQueryParams.getTextLiveId() != null) {
            q.addCriteria(where("text_live_id").is(liveMessageQueryParams.getTextLiveId()));
        }
        if (StringUtils.isNotBlank(liveMessageQueryParams.getContent())) {
            Pattern pattern = Pattern.compile("^.*" + liveMessageQueryParams.getContent().trim() + ".*$", Pattern.CASE_INSENSITIVE);
            q.addCriteria(Criteria.where("content").regex(pattern));
        }
        if (liveMessageQueryParams.getType() != null) {
            q.addCriteria(where("type").is(liveMessageQueryParams.getType()));
        }
        if (liveMessageQueryParams.getIsLimit()) {
            if (liveMessageQueryParams.getId() != null) {
                q.addCriteria(where("_id").lt(liveMessageQueryParams.getId()));
            }
            return textLiveMessageRepository.findByQuery(q.with(new Sort(Sort.Direction.DESC, "id")).limit(40));
        } else {
            if (liveMessageQueryParams.getId() != null) {
                q.addCriteria(where("_id").gt(liveMessageQueryParams.getId()));
            }
            return textLiveMessageRepository.findByQuery(q.with(new Sort(Sort.Direction.DESC, "id")));
        }
    }

    @Override
    public List<String> getLiveMessagesIds(final long textLiveId, final long section, TextLiveMessageType type) {
        Set<String> liveMessageIdSet = textLiveMessageCache.zrange(textLiveId, section);
        if (CollectionUtils.isNotEmpty(liveMessageIdSet)) {
            return new ArrayList<String>(liveMessageIdSet);
        }
        LOG.info("getTLiveMessagesIds from redis is null. textLiveId:{},section:{}", textLiveId, section);

        Query q = query(where("deleted").is(false));
        if (textLiveId != 0) {
            q.addCriteria(where("text_live_id").is(textLiveId));
        }
        if (section != 0) {
            q.addCriteria(where("section").is(section));
        }
        if (type != null) {
            q.addCriteria(where("type").is(type));
        }
        List<Long> ids = textLiveMessageRepository.findIdByQuery(q);
        List<String> liveMessageIds = Lists.transform(ids, new Function<Long, String>() {
            @Nullable
            @Override
            public String apply(@Nullable Long id) {
                textLiveMessageCache.zadd(textLiveId, section, id);
                return String.valueOf(id);
            }
        });
        return liveMessageIds;
    }

    @Override
    public int getLatestIndex(long textLiveId, long section) {
        return textLiveMessageRedisRepository.getMessageIndex(textLiveId, section);
    }

    @Override
    public List<String> getIdByPage(long textLiveId, long section, int page) {
        return textLiveMessageRedisRepository.getIdByPage(textLiveId, section, page);
    }

    @Override
    public List<TTextLiveMessage> getTLiveMessageByPage(long textLiveId, long section, int page) {
        List<TTextLiveMessage> liveMessages = Lists.newArrayList();
        List<String> ids = textLiveMessageRedisRepository.getIdByPage(textLiveId, section, page);
        if (CollectionUtils.isEmpty(ids)) {
            return liveMessages;
        }
        for (String id : ids) {
            TTextLiveMessage liveMessage = getTLiveMessageId(Long.parseLong(id));
            liveMessages.add(liveMessage);
        }
        return liveMessages;
    }


    private void processPageAfterAddSuccess(TextLiveMessage liveMessage) {
        int tLiveIndex = textLiveMessageRedisRepository.generateMessageIndex(liveMessage.getTextLiveId(), 0);
        int tLivePageId = getPage(tLiveIndex);
        LOG.info("process message Page. textLiveId:{} ,tLiveIndex:{}, tLivePageId:{}", liveMessage.getTextLiveId(), tLiveIndex, tLivePageId);
        textLiveMessageRedisRepository.addIdToPage(liveMessage.getTextLiveId(), 0, tLivePageId, liveMessage.getId());

        if (liveMessage.getSection() != null && liveMessage.getSection() != 0) {
            int tLiveSectionIndex = textLiveMessageRedisRepository.generateMessageIndex(liveMessage.getTextLiveId(), liveMessage.getSection());
            int tLiveSectionPageId = getPage(tLiveSectionIndex);
            LOG.info("process section message Page. textLiveId:{} ,tLiveSectionIndex:{}, tLiveSectionPageId:{}", liveMessage.getTextLiveId(), tLiveSectionIndex, tLiveSectionPageId);
            textLiveMessageRedisRepository.addIdToPage(liveMessage.getTextLiveId(), liveMessage.getSection(), tLiveSectionPageId, liveMessage.getId());
        }
    }

    private int getPage(int index) {
        if (index <= 0) {
            return 0;
        }
        return ((index - 1) / QmtConstants.LIVE_MESSAGE_PAGE_SIZE) + 1;
    }

    @Override
    protected MongoCrudRepository<TextLiveMessage, Long> getInnerRepository() {
        return textLiveMessageRepository;
    }
}
