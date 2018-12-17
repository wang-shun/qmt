package com.lesports.qmt.config.repository.impl;

import com.lesports.mongo.repository.support.AbstractMongoRepository;
import com.lesports.qmt.config.model.Tag;
import org.springframework.stereotype.Repository;
import com.lesports.qmt.config.repository.TagRepository;

/**
 * User: ellios
 * Time: 15-7-21 : 下午10:16
 */
@Repository("tagRepository")
public class TagRepositoryImpl extends AbstractMongoRepository<Tag, Long> implements TagRepository {
    @Override
    protected Class<Tag> getEntityType() {
        return Tag.class;
    }

    @Override
    protected Long getId(Tag entity) {
        return entity.getId();
    }

}
