package com.lesports.qmt.sbc.repository.impl;

import com.lesports.mongo.repository.support.AbstractMongoRepository;
import com.lesports.qmt.sbc.model.Video;
import com.lesports.qmt.sbc.repository.VideoRepository;
import org.springframework.stereotype.Service;

/**
 * Created by zhangxudong@le.com on 2016/10/26.
 */
@Service("videoRepository")
public class VideoRepositoryImpl extends AbstractMongoRepository<Video, Long> implements VideoRepository {

    @Override
    protected Class<Video> getEntityType() {
        return Video.class;
    }

    @Override
    protected Long getId(Video entity) {
        return entity.getId();
    }
}
