package com.lesports.qmt.tlive.repository.impl;

import com.lesports.mongo.repository.support.AbstractMongoRepository;
import com.lesports.qmt.tlive.model.TextLive;
import com.lesports.qmt.tlive.repository.TextLiveRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by lufei1 on 2015/9/14.
 */
@Repository("textLiveRepository")
public class TextLiveRepositoryImpl extends AbstractMongoRepository<TextLive, Long> implements TextLiveRepository {
    @Override
    protected Class<TextLive> getEntityType() {
        return TextLive.class;
    }

    @Override
    protected Long getId(TextLive entity) {
        return entity.getId();
    }
}
