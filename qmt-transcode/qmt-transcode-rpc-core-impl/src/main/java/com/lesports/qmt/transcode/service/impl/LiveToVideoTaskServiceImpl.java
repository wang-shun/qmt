package com.lesports.qmt.transcode.service.impl;

import com.google.common.collect.Lists;
import com.lesports.id.api.IdType;
import com.lesports.id.client.LeIdApis;
import com.lesports.mongo.repository.MongoCrudRepository;
import com.lesports.qmt.msg.core.ActionType;
import com.lesports.qmt.msg.core.BusinessType;
import com.lesports.qmt.msg.core.LeMessage;
import com.lesports.qmt.msg.core.LeMessageBuilder;
import com.lesports.qmt.msg.producer.SwiftMessageApis;
import com.lesports.qmt.transcode.model.LiveToVideoTask;
import com.lesports.qmt.transcode.repository.LiveToVideoTaskRepository;
import com.lesports.qmt.transcode.service.LiveToVideoTaskService;
import com.lesports.qmt.transcode.service.support.AbstractTranscodeService;
import com.lesports.utils.LeDateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * User: ellios
 * Time: 16-10-23 : 下午6:20
 */
@Service("liveToVideoTaskServiceImpl")
public class LiveToVideoTaskServiceImpl extends AbstractTranscodeService<LiveToVideoTask, Long> implements LiveToVideoTaskService {

    private static final Logger LOG = LoggerFactory.getLogger(LiveToVideoTaskServiceImpl.class);

    @Resource
    private LiveToVideoTaskRepository liveToVideoTaskRepository;

    @Override
    protected MongoCrudRepository<LiveToVideoTask, Long> getInnerRepository() {
        return liveToVideoTaskRepository;
    }

    @Override
    public boolean save(LiveToVideoTask liveToVideoTask) {
        if (null == liveToVideoTask.getId()) {
            liveToVideoTask.setId(LeIdApis.nextId(IdType.TRANSCODE_LIVE_TASK));
            liveToVideoTask.setCreateAt(LeDateUtils.formatYYYYMMDDHHMMSS(new Date()));
        }
        liveToVideoTask.setUpdateAt(LeDateUtils.formatYYYYMMDDHHMMSS(new Date()));

        liveToVideoTaskRepository.save(liveToVideoTask);
        List<BusinessType> businessTypes = Lists.newArrayList();
        if (!indexEntity(liveToVideoTask.getId(), IdType.TRANSCODE_LIVE_TASK.toString())) {
            businessTypes.add(BusinessType.SEARCH_INDEX);
        }
        LeMessage message = LeMessageBuilder.create()
                .setEntityId(liveToVideoTask.getId())
                .setIdType(IdType.TRANSCODE_LIVE_TASK)
                .setBusinessTypes(ActionType.ADD, businessTypes)
                .build();
        SwiftMessageApis.sendMsgAsync(message);
        return true;
    }

    @Override
    public LiveToVideoTask findOne(Long aLong) {
        return liveToVideoTaskRepository.findOne(aLong);
    }

    @Override
    public boolean delete(Long aLong) {
        return liveToVideoTaskRepository.delete(aLong);
    }

    @Override
    public boolean changeOrder(Long aLong, int increment) {
        return true;
    }
}
