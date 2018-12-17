package com.lesports.qmt.sbc.repository.impl;

import com.lesports.mongo.repository.support.AbstractMongoRepository;
import com.lesports.qmt.sbc.model.VideoMedium;
import com.lesports.qmt.sbc.repository.VideoMediumRepository;
import org.springframework.stereotype.Service;

/**
 * Created by zhangxudong@le.com on 2016/10/26.
 */
@Service("videoMediumRepository")
public class VideoMediumRepositoryImpl extends AbstractMongoRepository<VideoMedium, Long> implements VideoMediumRepository {

    @Override
    protected Class<VideoMedium> getEntityType() {
        return VideoMedium.class;
    }

    @Override
    protected Long getId(VideoMedium entity) {
        return entity.getId();
    }
}
