package com.lesports.qmt.transcode.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.lesports.qmt.sbc.model.Video;
import com.lesports.qmt.sbc.model.VideoMedium;
import com.lesports.qmt.transcode.client.TranscodeInternalApis;
import com.lesports.qmt.transcode.model.LiveToVideoSubTask;
import com.lesports.qmt.transcode.model.LiveToVideoTask;
import com.lesports.qmt.transcode.model.LiveToVideoTaskType;
import com.lesports.qmt.transcode.web.param.*;
import com.lesports.qmt.transcode.web.service.LiveToVideoTaskService;
import com.lesports.qmt.transcode.web.service.VideoMediumService;
import com.lesports.qmt.transcode.web.service.VideoService;
import com.lesports.spring.boot.exception.LeWebApplicationException;
import com.lesports.spring.boot.mvc.LeStatus;
import com.lesports.spring.boot.mvc.NoEnvelopeResponse;
import com.lesports.utils.LeDateUtils;
import com.lesports.utils.math.LeNumberUtils;
import com.letv.urus.liveroom.api.dto.sports.ChannelIdNameEnameDTO;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by pangchuanxiao on 2016/11/1.
 */
@RestController
@RequestMapping(value = "/")
public class LiveToVideoTaskController {
    private static final Logger LOGGER = LoggerFactory.getLogger(LiveToVideoTaskController.class);
    @Resource
    private LiveToVideoTaskService liveToVideoTaskService;
    @Resource
    private VideoService videoService;
    @Resource
    private VideoMediumService videoMediumService;

    /**
     * 片源管理单个视频 所有码率
     */
    @RequestMapping(value = "/formats", method = RequestMethod.GET)
    public LiveChannelVo getChannelDetail(@RequestParam("channel") Long channel) {
        return liveToVideoTaskService.getChannelDetail(channel);
    }


    /**
     * 片源管理单个视频 所有码率
     */
    @RequestMapping(value = "/channels", method = RequestMethod.GET)
    public List<ChannelIdNameEnameDTO> getAllChannels() {
        return liveToVideoTaskService.getLiveChannels();
    }

    /**
     * 直播转点播父任务提交
     *
     * @param param
     * @return
     */
    @RequestMapping(value = "/livetask", method = {RequestMethod.POST})
    @PreAuthorize("hasPermission('lesports.qmt.video.taskList', 'ADD')")
    public TranscodeSubmitVo submitTask(@ModelAttribute LiveTaskParam param, @ModelAttribute VideoTranscodeParam transcodeParam) {
        if (StringUtils.isEmpty(param.getTranscodeStartTime())) {
            throw new LeWebApplicationException("参数错误，开始时间为空，", LeStatus.BAD_REQUEST);
        } else if (StringUtils.isEmpty(param.getTranscodeEndTime())) {
            throw new LeWebApplicationException("参数错误，结束时间为空，", LeStatus.BAD_REQUEST);
        } else if (LeDateUtils.parseYMDHMS(param.getTranscodeStartTime()).after(LeDateUtils.parseYMDHMS(param.getTranscodeEndTime()))) {
            throw new LeWebApplicationException("参数错误，开始时间必须小于结束时间", LeStatus.BAD_REQUEST);
        }
        TranscodeSubmitVo transcodeSubmitVo = new TranscodeSubmitVo();
        transcodeSubmitVo.setSuccess(false);
        Video video = videoService.createVideo(param);
        //创建录制父任务/创建媒资视频
        LiveToVideoTask task = liveToVideoTaskService.createLiveToVideoTask(param, transcodeParam, video);


        liveToVideoTaskService.updateVideoId(task, video.getId());
        videoService.updateVideo(transcodeParam, video.getId());
        if (null == task || LeNumberUtils.toLong(task.getId()) <= 0) {
            return transcodeSubmitVo;
        }
        //创建录制子任务（必须创建mp4/mp4_350任务）
        List<LiveToVideoSubTask> subTasks = liveToVideoTaskService.createLiveToVideoSubTasks(task);
        //创建相关的视频介质
        VideoMedium videoMedium = videoMediumService.createVideoMedium(video.getId(), Lists.transform(subTasks, subTask -> subTask.getFormat()));

        if (CollectionUtils.isEmpty(subTasks)) {
            return transcodeSubmitVo;
        }

        //下发录制任务
        boolean success = liveToVideoTaskService.createLiveRecording(task, subTasks);

        if (!success) {
            return transcodeSubmitVo;
        }
        transcodeSubmitVo.setSuccess(true);

        return transcodeSubmitVo;
    }

    /**
     * 直播转点播父任务状态更新接口（直播频道任务状态更新）
     *
     * @param taskId
     * @param status
     * @return
     */
    @RequestMapping(value = "/recording/callback/parent", method = {RequestMethod.GET, RequestMethod.POST})
    @NoEnvelopeResponse
//    @PreAuthorize("hasPermission('lesports.qmt.video.taskList', 'UPDATE')")
    public RecordCallbackVo parentCallback(@RequestParam("taskId") long taskId, @RequestParam("status") int status) {
        LOGGER.info("Recording task callback : {}, status : {}", taskId, status);
        //更新父任务录制状态
        liveToVideoTaskService.updateTaskStatus(taskId, status);
        RecordCallbackVo transcodeSubmitVo = new RecordCallbackVo();
        transcodeSubmitVo.setStatus(200);
        transcodeSubmitVo.setMsg("接收成功");
        return transcodeSubmitVo;
    }

    /**
     * 直播转点播子任务状态更新接口（直播频道任务状态更新）
     *
     * @param subTask
     * @return
     */
    @RequestMapping(value = "/recording/callback/child", method = {RequestMethod.GET, RequestMethod.POST})
    @NoEnvelopeResponse
//    @PreAuthorize("hasPermission('lesports.qmt.video.taskList', 'ADD')")
    public RecordCallbackVo taskCallback(@ModelAttribute LiveToVideoSubTask subTask) {
        LOGGER.info("Recording sub task callback : {}", JSONObject.toJSONString(subTask));
        RecordCallbackVo transcodeSubmitVo = new RecordCallbackVo();

        //更新子任务录制状态
        subTask = liveToVideoTaskService.updateSubTaskStatus(subTask);
        transcodeSubmitVo.setStatus(200);
        transcodeSubmitVo.setMsg("接收成功");
        LiveToVideoTask task = TranscodeInternalApis.getEntityById(subTask.getParentId(), LiveToVideoTask.class);
        if (task.getType() == LiveToVideoTaskType.RECORDING) {
            return transcodeSubmitVo;
        }
        //下发当前码流的转封装任务
        boolean result = liveToVideoTaskService.createEncapsulationTask(subTask);
        if (!result) {
            transcodeSubmitVo.setMsg("创建转封装任务失败！");
            transcodeSubmitVo.setStatus(LeStatus.INTERNAL_SERVER_ERROR.getCode());
        }
        return transcodeSubmitVo;
    }

    /**
     * 片源管理 获取打点视频列表
     */
    @RequestMapping(value = "/liveToVideoPages", method = RequestMethod.GET)
    public Page<LiveToVideoTask> queryLiveToVideoTaskPage(
            @RequestParam(value = "user", required = false, defaultValue = "0") String user,
            @RequestParam(value = "page", required = false, defaultValue = "0") int pageNumber,
            @RequestParam(value = "count", required = false, defaultValue = "20") int pageSize) {
        Page<LiveToVideoTask> result = liveToVideoTaskService.getLiveToVideoTaskPage(user, pageNumber, pageSize);
        return result;
    }
}
