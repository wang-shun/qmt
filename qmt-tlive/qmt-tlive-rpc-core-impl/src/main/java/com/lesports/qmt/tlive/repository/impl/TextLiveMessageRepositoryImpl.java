package com.lesports.qmt.tlive.repository.impl;

import com.lesports.mongo.repository.support.AbstractMongoRepository;
import com.lesports.qmt.tlive.model.TextLiveMessage;
import com.lesports.qmt.tlive.repository.TextLiveMessageRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by lufei1 on 2015/9/14.
 */
@Repository("textLiveMessageRepository")
public class TextLiveMessageRepositoryImpl extends AbstractMongoRepository<TextLiveMessage, Long> implements TextLiveMessageRepository {
    @Override
    protected Class<TextLiveMessage> getEntityType() {
        return TextLiveMessage.class;
    }

    @Override
    protected Long getId(TextLiveMessage entity) {
        return entity.getId();
    }
}
