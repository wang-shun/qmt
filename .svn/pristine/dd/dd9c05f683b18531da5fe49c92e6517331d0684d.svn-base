package com.lesports.qmt.transcode.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.lesports.qmt.sbc.api.common.TranscodeStatus;
import com.lesports.qmt.sbc.api.common.VideoOnlineStatus;
import com.lesports.qmt.sbc.client.QmtSbcVideoInternalApis;
import com.lesports.qmt.sbc.model.Video;
import com.lesports.qmt.sbc.model.VideoFormat;
import com.lesports.qmt.sbc.model.VideoMedium;
import com.lesports.qmt.transcode.client.TranscodeInternalApis;
import com.lesports.qmt.transcode.model.*;
import com.lesports.qmt.transcode.web.param.AddVideoFormatParam;
import com.lesports.qmt.transcode.web.param.MmsMessage;
import com.lesports.qmt.transcode.web.param.TranscodeSubmitVo;
import com.lesports.qmt.transcode.web.service.LiveToVideoTaskService;
import com.lesports.qmt.transcode.web.service.VideoTranscodeTaskService;
import com.lesports.qmt.transcode.web.support.TranscodeConstants;
import com.lesports.qmt.transcode.web.support.VideoCodeRateText;
import com.lesports.utils.LeStringUtils;
import com.lesports.utils.math.LeNumberUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * trunk.
 *
 * @author pangchuanxiao
 * @since 2016/11/18
 */
@RestController
@RequestMapping(value = "/")
public class VideoSourceController {
    private static final Logger LOGGER = LoggerFactory.getLogger(VideoSourceController.class);
    @Resource
    private VideoTranscodeTaskService videoTranscodeTaskService;

    @Resource
    private LiveToVideoTaskService liveToVideoTaskService;

    /**
     * 片源管理单个视频 追加码率
     */
    @RequestMapping(value = "/addFormatToVideoTask", method = RequestMethod.PUT)
    public TranscodeSubmitVo addliveToVideoTask(AddVideoFormatParam videoSourceParam) {
        if (null == VideoCodeRateText.getCodeTextByText(videoSourceParam.getFormat())) {
            TranscodeSubmitVo vo = new TranscodeSubmitVo();
            vo.setSuccess(false);
            vo.setMsg("码率输入不正确");
            return vo;
        }
        try {
            VideoTranscodeTask task = TranscodeInternalApis.getEntityById(videoSourceParam.getTaskId(), VideoTranscodeTask.class);
            if (null != task) {
                videoTranscodeTaskService.addFormat(task, videoSourceParam);
            } else {
                LiveToVideoTask liveToVideoTask = TranscodeInternalApis.getEntityById(videoSourceParam.getTaskId(), LiveToVideoTask.class);
                if (null != liveToVideoTask) {
                    liveToVideoTaskService.addliveToVideoTask(liveToVideoTask, videoSourceParam);
                } else {
                    TranscodeSubmitVo vo = new TranscodeSubmitVo();
                    vo.setSuccess(false);
                    vo.setMsg("没有找到对应的task。");
                    return vo;
                }
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            TranscodeSubmitVo vo = new TranscodeSubmitVo();
            vo.setSuccess(false);
            vo.setMsg(e.getMessage());
            return vo;
        }
        TranscodeSubmitVo vo = new TranscodeSubmitVo();
        vo.setSuccess(true);
        return vo;
    }

    /**
     * 获取打点视频详情
     */
    @RequestMapping(value = "/videotasks", method = RequestMethod.GET)
    public List<Map> queryVideoTranscodeTaskPage(
            @RequestParam(value = "ids", required = false) String leecoVids) {
        List<Long> leecoVidList = LeStringUtils.commaString2LongList(leecoVids);
        List<Map> taskList = new ArrayList<>();
        for (long leecoVid : leecoVidList) {
            Map taskInfo = videoTranscodeTaskService.getVideoTranscodeTask(leecoVid);
            taskList.add(taskInfo);
        }
        return taskList;
    }

    @RequestMapping(value = "/notifyTranscode", method = RequestMethod.PUT)
    public TranscodeSubmitVo notifyTranscode(
            @RequestParam(value = "mmsMessage", required = true) String mmsMessage) {
        LOGGER.info("Receive transcode message : {}", mmsMessage);
        MmsMessage message = JSONObject.parseObject(mmsMessage, MmsMessage.class);
        if (null == message) {
            return new TranscodeSubmitVo(false, "解析json数据失败");
        }
        if (TranscodeConstants.TRANSCODE_TASK_FORMAT_STATUS_MAPPING.get(message.getStatus()) == VideoTranscodeTaskStatus.TASK_FINISH) {
            updatePic(message.getOutkey(), message);
        }
        //todo 更新转码状态
        VideoTranscodeTask task = TranscodeInternalApis.getVideoTaskByLeecoVid(message.getOutkey());
        if (null != task) {
            int res = updateVideoMediumStatus(task.getVideoId(), message);
            if (0 == res) {
                return new TranscodeSubmitVo(false, "获取VideoMedium数据失败");
            }

            if (CollectionUtils.isNotEmpty(task.getSubTasks())) {
                for (VideoTranscodeSubTask videoTranscodeSubTask : task.getSubTasks()) {
                    if (null == videoTranscodeSubTask) continue;
                    // sub status
                    if (null != videoTranscodeSubTask.getFormat() && LeNumberUtils.toInt(videoTranscodeSubTask.getFormat()) == LeNumberUtils.toInt(message.getVtype())) {
                        videoTranscodeSubTask.setStatus(TranscodeConstants.TRANSCODE_TASK_FORMAT_STATUS_MAPPING.get(message.getStatus()));
                        TranscodeInternalApis.saveEntity(task, VideoTranscodeTask.class);
                    }
                }
            }
            if (2 == res) {
                task.setStatus(VideoTranscodeTaskStatus.TASK_FINISH);
                TranscodeInternalApis.saveEntity(task, VideoTranscodeTask.class);
                onlineVideo(message.getOutkey());
            }
        } else {
            LiveToVideoTask liveToVideoTask = TranscodeInternalApis.getLiveToVideoTaskByLeecoVid(message.getOutkey());
            if (null == liveToVideoTask) {
                return new TranscodeSubmitVo(false, "获取LiveToVideoTask数据失败");
            }
            Integer updateFlag = updateVideoMediumStatus(liveToVideoTask.getVideoId(), message);
            if (0 == updateFlag) return new TranscodeSubmitVo(false, "获取VideoMedium数据失败");

            LiveToVideoSubTask subTask = TranscodeInternalApis.getLiveToVideoSubTask(liveToVideoTask.getId(), message.getVtype());
            if (null != subTask) {
                subTask.setTaskStatus(LiveToVideoTaskStatus.TASK_FINISH);
                TranscodeInternalApis.saveEntity(subTask, LiveToVideoSubTask.class);
            }
            if (2 == updateFlag) {
                liveToVideoTask.setStatus(LiveToVideoTaskStatus.TASK_FINISH);
                TranscodeInternalApis.saveEntity(liveToVideoTask, LiveToVideoTask.class);
                onlineVideo(message.getOutkey());
            }
        }

        return new TranscodeSubmitVo(true, "更新成功");
    }

    /**
     * 从媒资更新图片
     * @param leecoVid
     * @param message
     */
    private void updatePic(long leecoVid, MmsMessage message) {
        Video video = QmtSbcVideoInternalApis.getVideoByLeecoVid(leecoVid);
        if (null != video && StringUtils.isNotEmpty(message.getImgprefix_http())) {
            List<String> allImages = new ArrayList<>(8);
            for (int index = 1; index <= 8; index++) {
                allImages.add(message.getImgprefix_http() + "/thumb/" + index + ".jpg");
            }
            video.setAllImages(allImages);
            QmtSbcVideoInternalApis.saveVideo(video);
        }

    }

    private void onlineVideo(long leecoVid) {
        Video video = QmtSbcVideoInternalApis.getVideoByLeecoVid(leecoVid);
        if (null != video) {
            video.setOnlineStatus(VideoOnlineStatus.ONLINE);
            QmtSbcVideoInternalApis.saveVideo(video);
        }
    }

    /**
     * @param videoId
     * @param message
     * @return 0 -> unfound; 1 -> one task finished; 2 -> all tasks finished; 3 --> sub tasks failed  4: all tasks failed
     */
    private Integer updateVideoMediumStatus(Long videoId, MmsMessage message) {
        //
        VideoMedium videoMedium = QmtSbcVideoInternalApis.getVideoMediumByVideoId(videoId);
        if (null == videoMedium) return 0;

        boolean allFinish = true;
        if (CollectionUtils.isNotEmpty(videoMedium.getFormats())) {
            for (VideoFormat videoFormat : videoMedium.getFormats()) {
                // sub status
                if (null != videoFormat.getCodeRate()
                        && LeNumberUtils.toInt(videoFormat.getCodeRate()) == LeNumberUtils.toInt(message.getVtype())) {
                    videoFormat.setStatus(TranscodeConstants.TRANSCODE_FORMAT_STATUS_MAPPING.get(message.getStatus()));
                    videoFormat.setStoreUri(message.getStorepath());
                    videoFormat.setFullpath(message.getFullpath());
                    videoFormat.setSize(message.getSize());
                    videoFormat.setDuration(message.getDuration());
                    videoFormat.setDsturl(message.getDsturl());
                    QmtSbcVideoInternalApis.saveVideoMedium(videoMedium);
                    break;
                }
            }
            for (VideoFormat videoFormat : videoMedium.getFormats()) {
                if (videoFormat.getStatus() == TranscodeStatus.TRANSCODING_FAIL) {
                    videoMedium.setStatus(TranscodeStatus.TRANSCODING_FAIL);
                    QmtSbcVideoInternalApis.saveVideoMedium(videoMedium);
                    return 3;
                } else if (videoFormat.getStatus() != TranscodeStatus.TRANSCODING_SUCCESS
                        && videoFormat.getStatus() != TranscodeStatus.DISPATCH_SUCCESS) {
                    allFinish = false;
                }
            }
        }
        if (allFinish) {
            videoMedium.setStatus(TranscodeStatus.DISPATCH_SUCCESS);
            QmtSbcVideoInternalApis.saveVideoMedium(videoMedium);
            return 2;
        }
        return 1;
    }
}
