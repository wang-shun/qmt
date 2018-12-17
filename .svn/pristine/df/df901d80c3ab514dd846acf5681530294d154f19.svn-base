package com.lesports.qmt.cms.repository.impl;

import com.lesports.mongo.repository.support.AbstractMongoRepository;
import com.lesports.qmt.cms.model.Mapper;
import com.lesports.qmt.cms.repository.MapperRepository;
import org.springframework.stereotype.Repository;

/**
 * User: ellios
 * Time: 17-1-10 : 下午4:00
 */
@Repository("mapperRepository")
public class MapperRepositoryImpl extends AbstractMongoRepository<Mapper, Long> implements MapperRepository {

    @Override
    protected Class<Mapper> getEntityType() {
        return Mapper.class;
    }

    @Override
    protected Long getId(Mapper entity) {
        return entity.getId();
    }
}
