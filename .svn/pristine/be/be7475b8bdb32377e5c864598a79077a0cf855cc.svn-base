package com.lesports.qmt.sbd.repository.impl;

import com.lesports.mongo.repository.support.AbstractMongoRepository;
import com.lesports.qmt.sbd.model.DataImportConfig;
import com.lesports.qmt.sbd.repository.DataImportConfigRepository;
import org.springframework.stereotype.Repository;


/**
 * Created by qiaohongxin on 2017/02/07
 */
@Repository("dataImportConfigRepository")
public class DataImportConfigRepositoryImpl extends AbstractMongoRepository<DataImportConfig, Long> implements DataImportConfigRepository {
    @Override
    protected Class<DataImportConfig> getEntityType() {
        return DataImportConfig.class;
    }

    @Override
    protected Long getId(DataImportConfig entity) {
        return entity.getId();
    }

}
