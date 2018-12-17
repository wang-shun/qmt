package com.lesports.qmt.transcode.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.lesports.id.api.IdType;
import com.lesports.id.client.LeIdApis;
import com.lesports.model.VideoCreateResult;
import com.lesports.mongo.repository.MongoCrudRepository;
import com.lesports.qmt.msg.core.ActionType;
import com.lesports.qmt.msg.core.BusinessType;
import com.lesports.qmt.msg.core.LeMessage;
import com.lesports.qmt.msg.core.LeMessageBuilder;
import com.lesports.qmt.msg.producer.SwiftMessageApis;
import com.lesports.qmt.transcode.model.VideoTranscodeTask;
import com.lesports.qmt.transcode.repository.VideoTranscodeTaskRepository;
import com.lesports.qmt.transcode.service.VideoTranscodeTaskService;
import com.lesports.qmt.transcode.service.support.AbstractTranscodeService;
import com.lesports.utils.LeDateUtils;
import com.lesports.utils.LiveApis;
import com.lesports.utils.MmsApis;
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
@Service("videoTranscodeServiceImpl")
public class VideoTranscodeServiceImpl extends AbstractTranscodeService<VideoTranscodeTask,Long> implements VideoTranscodeTaskService {

    private static final Logger LOG = LoggerFactory.getLogger(VideoTranscodeServiceImpl.class);

    @Resource
    private VideoTranscodeTaskRepository videoTranscodeTaskRepository;

    @Override
    protected MongoCrudRepository<VideoTranscodeTask, Long> getInnerRepository() {
        return videoTranscodeTaskRepository;
    }

    @Override
    public boolean save(VideoTranscodeTask videoTranscodeTask) {

        if (null == videoTranscodeTask.getId()) {
            videoTranscodeTask.setId(LeIdApis.nextId(IdType.TRANSCODE_VIDEO_TASK));
            videoTranscodeTask.setCreateAt(LeDateUtils.formatYYYYMMDDHHMMSS(new Date()));
        }
        videoTranscodeTask.setUpdateAt(LeDateUtils.formatYYYYMMDDHHMMSS(new Date()));
        videoTranscodeTaskRepository.save(videoTranscodeTask);
        List<BusinessType> businessTypes = Lists.newArrayList();
        if (!indexEntity(videoTranscodeTask.getId(), IdType.TRANSCODE_VIDEO_TASK.toString())) {
            businessTypes.add(BusinessType.SEARCH_INDEX);
        }
        LeMessage message = LeMessageBuilder.create()
                .setEntityId(videoTranscodeTask.getId())
                .setIdType(IdType.TRANSCODE_VIDEO_TASK)
                .setBusinessTypes(ActionType.ADD, businessTypes)
                .build();
        SwiftMessageApis.sendMsgAsync(message);
        return true;
    }

    @Override
    public VideoTranscodeTask findOne(Long aLong) {
        return videoTranscodeTaskRepository.findOne(aLong);
    }

    @Override
    public boolean delete(Long aLong) {
        return videoTranscodeTaskRepository.delete(aLong);
    }

    @Override
    public boolean changeOrder(Long aLong, int increment) {
        return true;
    }
}
