package com.lesports.qmt.sbc.repository.impl;

import com.lesports.mongo.repository.support.AbstractMongoRepository;
import com.lesports.qmt.sbc.model.ResourceContent;
import com.lesports.qmt.sbc.repository.ResourceContentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;


import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

import java.util.List;

/**
 * User: ellios
 * Time: 16-10-23 : 下午6:17
 */
@Repository("resourceContentRepository")
public class ResourceContentRepositoryImpl extends AbstractMongoRepository<ResourceContent, Long> implements ResourceContentRepository {

    private static final Logger LOG = LoggerFactory.getLogger(ResourceContentRepositoryImpl.class);

    @Override
    protected Class<ResourceContent> getEntityType() {
        return ResourceContent.class;
    }

    @Override
    protected Long getId(ResourceContent entity) {
        return entity.getId();
    }
    @Override
    public List<ResourceContent> getResourceContentByResourceId(Long resourceId){
        Query q = query(where("resourceId").is(resourceId));
        q.addCriteria(where("deleted").is(false));
        q.with(new Sort(Sort.Direction.ASC, "order"));
        return findByQuery(q);
    }
}
