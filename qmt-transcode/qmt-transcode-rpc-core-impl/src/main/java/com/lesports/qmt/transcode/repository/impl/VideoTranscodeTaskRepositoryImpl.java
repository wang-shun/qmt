package com.lesports.qmt.transcode.repository.impl;

import com.lesports.mongo.repository.support.AbstractMongoRepository;
import com.lesports.qmt.transcode.model.VideoTranscodeTask;
import com.lesports.qmt.transcode.repository.VideoTranscodeTaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Created by ruiyuansheng on 2015/7/7.
 */
@Service("videoTranscodeTaskRepository")
public class VideoTranscodeTaskRepositoryImpl extends AbstractMongoRepository<VideoTranscodeTask, Long> implements VideoTranscodeTaskRepository {

    private static final Logger LOG = LoggerFactory.getLogger(VideoTranscodeTaskRepositoryImpl.class);

    @Override
    protected Class<VideoTranscodeTask> getEntityType() {
        return VideoTranscodeTask.class;
    }

    @Override
    protected Long getId(VideoTranscodeTask videoTranscodeTask) {
        return videoTranscodeTask.getId();
    }
}
