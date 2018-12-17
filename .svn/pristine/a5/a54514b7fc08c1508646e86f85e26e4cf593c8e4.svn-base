package com.lesports.qmt.sbc.repository.impl;

import com.lesports.mongo.repository.support.AbstractMongoRepository;
import com.lesports.qmt.sbc.model.QmtResource;
import com.lesports.qmt.sbc.repository.ResourceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

/**
 * User: ellios
 * Time: 16-10-23 : 下午6:17
 */
@Repository("resourceRepository")
public class ResourceRepositoryImpl extends AbstractMongoRepository<QmtResource, Long> implements ResourceRepository {

    private static final Logger LOG = LoggerFactory.getLogger(ResourceRepositoryImpl.class);

    @Override
    protected Class<QmtResource> getEntityType() {
        return QmtResource.class;
    }

    @Override
    protected Long getId(QmtResource entity) {
        return entity.getId();
    }

}
