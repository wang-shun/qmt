package com.lesports.qmt.config.repository.impl;

import com.lesports.mongo.repository.support.AbstractMongoRepository;
import com.lesports.qmt.config.model.DictEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import com.lesports.qmt.config.repository.DictRepository;

/**
 * Created by yangyu on 2015/5/27.
 */
@Repository("dictRepository")
public class DictRepositoryImpl extends AbstractMongoRepository<DictEntry, Long> implements DictRepository {

    private static final Logger LOG = LoggerFactory.getLogger(DictRepositoryImpl.class);

    @Override
    protected Class<DictEntry> getEntityType() {
        return DictEntry.class;
    }

    @Override
    protected Long getId(DictEntry entity) {
        return entity.getId();
    }

}
