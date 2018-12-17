package com.lesports.qmt.sbc.repository.impl;

import com.lesports.mongo.repository.support.AbstractMongoRepository;
import com.lesports.qmt.sbc.model.ResourceContentOnline;
import com.lesports.qmt.sbc.repository.ResourceContentOnlineRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

/**
 * User: ellios
 * Time: 16-10-23 : 下午6:17
 */
@Repository("resourceContentOnlineRepository")
public class ResourceContentOnlineRepositoryImpl extends AbstractMongoRepository<ResourceContentOnline, Long> implements ResourceContentOnlineRepository {

    private static final Logger LOG = LoggerFactory.getLogger(ResourceContentOnlineRepositoryImpl.class);

    @Override
    protected Class<ResourceContentOnline> getEntityType() {
        return ResourceContentOnline.class;
    }

    @Override
    protected Long getId(ResourceContentOnline entity) {
        return entity.getId();
    }

}
