package com.lesports.qmt.transcode.repository.impl;

import com.lesports.mongo.repository.support.AbstractMongoRepository;
import com.lesports.qmt.transcode.model.LiveToVideoTask;
import com.lesports.qmt.transcode.repository.LiveToVideoTaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

/**
 * User: ellios
 * Time: 16-10-23 : 下午6:17
 */
@Repository("liveToVideoTaskRepository")
public class LiveToVideoTaskRepositoryImpl extends AbstractMongoRepository<LiveToVideoTask, Long> implements LiveToVideoTaskRepository {

    private static final Logger LOG = LoggerFactory.getLogger(LiveToVideoTaskRepositoryImpl.class);

    @Override
    protected Class<LiveToVideoTask> getEntityType() {
        return LiveToVideoTask.class;
    }

    @Override
    protected Long getId(LiveToVideoTask liveToVideoTask) {
        return liveToVideoTask.getId();
    }
}
