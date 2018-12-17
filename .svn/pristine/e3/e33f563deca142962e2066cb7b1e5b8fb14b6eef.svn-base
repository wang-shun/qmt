package com.lesports.qmt.transcode.service.impl;

import com.lesports.id.api.IdType;
import com.lesports.id.client.LeIdApis;
import com.lesports.mongo.repository.MongoCrudRepository;
import com.lesports.qmt.transcode.model.LiveToVideoSubTask;
import com.lesports.qmt.transcode.repository.LiveToVideoSubTaskRepository;
import com.lesports.qmt.transcode.service.LiveToVideoSubTaskService;
import com.lesports.qmt.transcode.service.support.AbstractTranscodeService;
import com.lesports.utils.LeDateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * User: ellios
 * Time: 16-10-23 : 下午6:20
 */
@Service("liveToVideoSubTaskServiceImpl")
public class LiveToVideoSubTaskServiceImpl extends AbstractTranscodeService<LiveToVideoSubTask, Long> implements LiveToVideoSubTaskService {

    private static final Logger LOG = LoggerFactory.getLogger(LiveToVideoSubTaskServiceImpl.class);

    @Resource
    private LiveToVideoSubTaskRepository liveToVideoSubTaskRepository;

    @Override
    protected MongoCrudRepository<LiveToVideoSubTask, Long> getInnerRepository() {
        return liveToVideoSubTaskRepository;
    }

    @Override
    public boolean save(LiveToVideoSubTask liveToVideoTask) {
        if (null == liveToVideoTask.getId()) {
            liveToVideoTask.setId(LeIdApis.nextId(IdType.TRANSCODE_LIVE_TASK));
            liveToVideoTask.setCreateAt(LeDateUtils.formatYYYYMMDDHHMMSS(new Date()));
        }
        liveToVideoTask.setUpdateAt(LeDateUtils.formatYYYYMMDDHHMMSS(new Date()));
        return liveToVideoSubTaskRepository.save(liveToVideoTask);
    }

    @Override
    public LiveToVideoSubTask findOne(Long aLong) {
        return liveToVideoSubTaskRepository.findOne(aLong);
    }

    @Override
    public boolean delete(Long aLong) {
        return liveToVideoSubTaskRepository.delete(aLong);
    }

    @Override
    public boolean changeOrder(Long aLong, int increment) {
        return true;
    }
}
