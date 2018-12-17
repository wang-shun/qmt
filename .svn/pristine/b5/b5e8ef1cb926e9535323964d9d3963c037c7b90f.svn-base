package com.lesports.qmt.sbc.service.impl;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.lesports.api.common.CallerParam;
import com.lesports.api.common.PublishStatus;
import com.lesports.qmt.mvc.QmtPage;
import com.lesports.qmt.mvc.QmtPageParam;
import com.lesports.qmt.sbc.client.QmtSbcTopicInternalApis;
import com.lesports.qmt.sbc.converter.TopicVoConverter;
import com.lesports.qmt.sbc.model.Topic;
import com.lesports.qmt.sbc.service.TopicWebService;
import com.lesports.qmt.sbc.service.support.AbstractSbcWebService;
import com.lesports.qmt.sbc.utils.QmtSbcUtils;
import com.lesports.qmt.sbc.vo.TopicVo;
import com.lesports.query.InternalCriteria;
import com.lesports.query.InternalQuery;
import com.lesports.utils.CallerUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by denghui on 2016/11/2.
 */
@Service("topicWebService")
public class TopicWebServiceImpl extends AbstractSbcWebService implements TopicWebService {

    private static final Logger LOG = LoggerFactory.getLogger(TopicWebServiceImpl.class);
    private static final Function<Topic,TopicVo> VO_FUNCTION = new Function<Topic,TopicVo>() {
        @Nullable
        @Override
        public TopicVo apply(@Nullable Topic input) {
            if (input == null) {
                return null;
            }
            TopicVo topicVo = new TopicVo();
            topicVo.setId(input.getId());
            topicVo.setName(input.getName());
            topicVo.setUpdateAt(input.getUpdateAt());
            return topicVo;
        }
    };

    @Resource
    private TopicVoConverter topicVoConverter;

    @Override
    public boolean updatePublishStatus(long id, PublishStatus status) {
        Topic topic = QmtSbcTopicInternalApis.getTopicById(id);
        topic.setOnline(status);
        topic.setUpdater(getOperator());
        return QmtSbcTopicInternalApis.saveTopic(topic) > 0;
    }

    @Override
    public Long saveWithId(TopicVo vo) {
        if (vo.getId() == null) {
            return doInsert(vo);
        } else {
            return doUpdate(vo);
        }
    }

    private Long doInsert(TopicVo vo) {
        vo.setCreator(getOperator());
        long id = QmtSbcTopicInternalApis.saveTopic(vo.toTopic());
        vo.setId(id);
        return id;
    }

    private Long doUpdate(TopicVo vo) {
        Topic existsTopic = QmtSbcTopicInternalApis.getTopicById(vo.getId());
        existsTopic = topicVoConverter.copyEditableProperties(existsTopic, vo);
        existsTopic.setUpdater(getOperator());
        long id = QmtSbcTopicInternalApis.saveTopic(existsTopic, true);
        return id;
    }

    @Override
    public TopicVo findOne(Long id) {
        return findOne(id, CallerUtils.getDefaultCaller());
    }

    @Override
    public TopicVo findOne(Long id, CallerParam caller) {
        Topic topic = QmtSbcTopicInternalApis.getTopicById(id);
        if (topic == null) {
            return null;
        }
        TopicVo topicVo = new TopicVo(topic);
        topicVo.setRelatedTags(QmtSbcUtils.getRelatedTagVosByRelatedIds(topic.getRelatedIds(), caller));
        return topicVo;
    }

    @Override
    public boolean delete(Long id) {
        return QmtSbcTopicInternalApis.deleteTopic(id);
    }

    @Override
    public QmtPage<TopicVo> list(Map<String, Object> params, QmtPageParam pageParam) {
        InternalQuery query = new InternalQuery();
        query.addCriteria(InternalCriteria.where("deleted").is(false));
        addParamsCriteriaToQuery(query, params, pageParam);

        long total = QmtSbcTopicInternalApis.countTopicByQuery(query);
        if (total <= 0) {
            return new QmtPage(Collections.EMPTY_LIST, pageParam, 0);
        }
        List<Topic> topics = QmtSbcTopicInternalApis.getTopicsByQuery(query);
        return new QmtPage<>(Lists.transform(topics, VO_FUNCTION), pageParam, total);
    }

}

