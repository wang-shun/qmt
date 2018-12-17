package com.lesports.qmt.transcode.service.support;

import com.lesports.qmt.exception.NoServiceException;
import com.lesports.qmt.service.support.QmtService;
import com.lesports.qmt.transcode.model.LiveToVideoSubTask;
import com.lesports.qmt.transcode.model.LiveToVideoTask;
import com.lesports.qmt.transcode.model.VideoTranscodeTask;
import com.lesports.qmt.transcode.service.LiveToVideoSubTaskService;
import com.lesports.qmt.transcode.service.LiveToVideoTaskService;
import com.lesports.qmt.transcode.service.VideoTranscodeTaskService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * trunk.
 *
 * @author pangchuanxiao
 * @since 2016/3/29
 */
@Component("sbcServiceFactory")
public class TranscodeServiceFactory {
    @Resource
    private LiveToVideoTaskService liveToVideoTaskService;
    @Resource
    private VideoTranscodeTaskService videoTranscodeTaskService;
    @Resource
    private LiveToVideoSubTaskService liveToVideoSubTaskService;

    public QmtService getService(Class clazz) throws NoServiceException {
        if (LiveToVideoTask.class == clazz) {
            return liveToVideoTaskService;
        } else if (VideoTranscodeTask.class == clazz) {
            return videoTranscodeTaskService;
        } else if (LiveToVideoSubTask.class == clazz) {
            return liveToVideoSubTaskService;
        }
        throw new NoServiceException("No service for class : " + clazz);
    }
}
