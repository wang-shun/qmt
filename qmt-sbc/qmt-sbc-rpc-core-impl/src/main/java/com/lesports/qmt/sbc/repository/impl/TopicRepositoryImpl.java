package com.lesports.qmt.sbc.repository.impl;

import com.lesports.mongo.repository.support.AbstractMongoRepository;
import com.lesports.qmt.sbc.model.Topic;
import com.lesports.qmt.sbc.repository.TopicRepository;
import org.springframework.stereotype.Service;

/**
 * Created by denghui on 2016/11/1.
 */
@Service("topicRepository")
public class TopicRepositoryImpl extends AbstractMongoRepository<Topic, Long> implements TopicRepository {

    @Override
    protected Class<Topic> getEntityType() {
        return Topic.class;
    }

    @Override
    protected Long getId(Topic entity) {
        return entity.getId();
    }
}
