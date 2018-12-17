package com.lesports.qmt.sbc.service.impl;

import com.lesports.id.api.IdType;
import com.lesports.id.client.LeIdApis;
import com.lesports.mongo.repository.MongoCrudRepository;
import com.lesports.qmt.QmtOperationType;
import com.lesports.qmt.sbc.model.VideoMedium;
import com.lesports.qmt.sbc.repository.VideoMediumRepository;
import com.lesports.qmt.sbc.service.VideoMediumService;
import com.lesports.qmt.sbc.service.support.AbstractSbcService;
import com.lesports.utils.LeDateUtils;
import com.lesports.utils.math.LeNumberUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Created by zhangxudong@le.com on 2016/10/25.
 */
@Service
public class VideoMediumServiceImpl extends AbstractSbcService<VideoMedium, Long> implements VideoMediumService {
    @Resource
    private VideoMediumRepository videoMediumRepository;

    @Override
    protected MongoCrudRepository<VideoMedium, Long> getInnerRepository() {
        return videoMediumRepository;
    }

    @Override
    protected QmtOperationType getOpreationType(VideoMedium entity) {
        if (null == entity || LeNumberUtils.toLong(entity.getId()) <= 0) {
            return QmtOperationType.CREATE;
        }
        return QmtOperationType.UPDATE;
    }

    @Override
    protected boolean doCreate(VideoMedium entity) {
        entity.setId(LeIdApis.nextId(IdType.VIDEO_MEDIUM));
        boolean result = videoMediumRepository.save(entity);

        LOG.info("[create video medium][video id={}, result={}]", entity.getVideoId(), result);
        return result;
    }

    @Override
    protected boolean doAfterCreate(VideoMedium entity) {
        return true;
    }

    @Override
    protected boolean doUpdate(VideoMedium entity) {
        entity.setUpdateAt(LeDateUtils.formatYYYYMMDDHHMMSS(new Date()));
        return videoMediumRepository.save(entity);
    }

    @Override
    protected boolean doAfterUpdate(VideoMedium entity) {
        return true;
    }

    @Override
    protected boolean doDelete(Long aLong) {
        return true;
    }

    @Override
    protected boolean doAfterDelete(VideoMedium entity) {
        return true;
    }

    @Override
    protected VideoMedium doFindExistEntity(VideoMedium entity) {
        return videoMediumRepository.findOne(entity.getId());
    }
}