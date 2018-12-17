package com.lesports.qmt.sbc.repository.impl;

import com.lesports.mongo.repository.support.AbstractMongoRepository;
import com.lesports.qmt.sbc.model.TopicItemPackage;
import com.lesports.qmt.sbc.repository.TopicItemPackageRepository;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

/**
 * Created by denghui on 2016/11/1.
 */
@Service("topicItemPackageRepository")
public class TopicItemPackageRepositoryImpl extends AbstractMongoRepository<TopicItemPackage, Long> implements TopicItemPackageRepository {

    @Override
    protected Class<TopicItemPackage> getEntityType() {
        return TopicItemPackage.class;
    }

    @Override
    protected Long getId(TopicItemPackage entity) {
        return entity.getId();
    }

    @Override
    public List<Long> findIdsByTopicId(Long topicId) {
        Query q = query(where("topic_id").is(topicId));
        q.addCriteria(where("deleted").is(false));
        q.with(new Sort(Sort.Direction.ASC, "order"));
        return findIdByQuery(q);
    }
}
