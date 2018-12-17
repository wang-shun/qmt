package com.lesports.qmt.tlive.repository.impl;

import com.lesports.mongo.repository.support.AbstractMongoRepository;
import com.lesports.qmt.tlive.model.TextLiveImage;
import com.lesports.qmt.tlive.repository.TextLiveImageRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by lufei1 on 2015/9/14.
 */
@Repository("textLiveImageRepository")
public class TextLiveImageRepositoryImpl extends AbstractMongoRepository<TextLiveImage, Long> implements TextLiveImageRepository {
    @Override
    protected Class<TextLiveImage> getEntityType() {
        return TextLiveImage.class;
    }

    @Override
    protected Long getId(TextLiveImage entity) {
        return entity.getId();
    }
}
