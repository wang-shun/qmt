package com.lesports.qmt.config.repository.impl;

import com.lesports.mongo.repository.support.AbstractMongoRepository;
import com.lesports.qmt.config.model.ClientPlatform;
import com.lesports.qmt.config.repository.ClientPlatformRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by zhangxudong@le.com on 2015/7/2.
 */
@Repository("clientPlatformRepository")
public class ClientPlatformRepositoryImpl extends AbstractMongoRepository<ClientPlatform, Long> implements ClientPlatformRepository {

    @Override
    protected Class<ClientPlatform> getEntityType() {
        return ClientPlatform.class;
    }

    @Override
    protected Long getId(ClientPlatform entity) {
        return entity.getId();
    }
}
