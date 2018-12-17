package com.lesports.qmt.config.repository.impl;

import com.lesports.mongo.repository.support.AbstractMongoRepository;
import com.lesports.qmt.config.model.Copyright;
import com.lesports.qmt.config.repository.CopyrightRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by zhangxudong@le.com on 2015/7/2.
 */
@Repository("copyrightRepository")
public class CopyrightRepositoryImpl extends AbstractMongoRepository<Copyright, Long> implements CopyrightRepository {

    @Override
    protected Class<Copyright> getEntityType() {
        return Copyright.class;
    }

    @Override
    protected Long getId(Copyright copyright) {
        return copyright.getId();
    }
}
