package com.lesports.qmt.transcode.repository.impl;

import com.lesports.mongo.repository.support.AbstractMongoRepository;
import com.lesports.qmt.transcode.model.LiveToVideoSubTask;
import com.lesports.qmt.transcode.model.LiveToVideoTask;
import com.lesports.qmt.transcode.repository.LiveToVideoSubTaskRepository;
import com.lesports.qmt.transcode.repository.LiveToVideoTaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

/**
 * User: ellios
 * Time: 16-10-23 : 下午6:17
 */
@Repository("liveToVideoSubTaskRepository")
public class LiveToVideoSubTaskRepositoryImpl extends AbstractMongoRepository<LiveToVideoSubTask, Long> implements LiveToVideoSubTaskRepository {

    private static final Logger LOG = LoggerFactory.getLogger(LiveToVideoSubTaskRepositoryImpl.class);

    @Override
    protected Class<LiveToVideoSubTask> getEntityType() {
        return LiveToVideoSubTask.class;
    }

    @Override
    protected Long getId(LiveToVideoSubTask liveToVideoTask) {
        return liveToVideoTask.getId();
    }
}
