package com.lesports.qmt.cms.repository.impl;

import com.lesports.mongo.repository.support.AbstractMongoRepository;
import com.lesports.qmt.cms.model.Column;
import com.lesports.qmt.cms.repository.ColumnRepository;
import org.springframework.stereotype.Repository;

/**
 * User: ellios
 * Time: 16-11-29 : 下午5:39
 */
@Repository("columnRepository")
public class ColumnRepositoryImpl extends AbstractMongoRepository<Column, Long> implements ColumnRepository {

    @Override
    protected Class<Column> getEntityType() {
        return Column.class;
    }

    @Override
    protected Long getId(Column entity) {
        return entity.getId();
    }
}
