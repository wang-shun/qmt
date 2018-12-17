package com.lesports.qmt.transcode.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.lesports.model.VideoTranscodeProgressResult;
import com.lesports.qmt.sbc.model.Video;
import com.lesports.qmt.sbc.model.VideoMedium;
import com.lesports.qmt.transcode.model.VideoTranscodeTask;
import com.lesports.qmt.transcode.web.param.*;
import com.lesports.qmt.transcode.web.service.VideoMediumService;
import com.lesports.qmt.transcode.web.service.VideoService;
import com.lesports.qmt.transcode.web.service.VideoTranscodeTaskService;
import com.lesports.spring.boot.exception.LeWebApplicationException;
import com.lesports.spring.boot.mvc.LeStatus;
import com.lesports.spring.security.authentication.LeCasAuthenticationToken;
import com.lesports.utils.LiveApis;
import com.lesports.utils.math.LeNumberUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * User: pangchuanxiao
 * Time: 16-10-17 : 下午3:23
 */
@RestController
@RequestMapping(value = "/")
public class VideoTranscodeTaskController {
    private static final Logger LOGGER = LoggerFactory.getLogger(VideoTranscodeTaskController.class);
    @Resource
    private VideoTranscodeTaskService videoTranscodeTaskService;
    @Resource
    private VideoService videoService;
    @Resource
    private VideoMediumService videoMediumService;

    /**
     * 视频上传初始化
     * 1. 检查md5是否在库里存在
     * 2. 没有存在，请求媒资接口新建视频，获取媒资vid
     * 3. 没有存在，构造转码任务数据
     * 4. 没有存在，通过vid请求媒体云初始化接口，初始化视频上传接口，获取上传地址
     * 5. 存在，调用媒体云断点续传接口，获取上传地址
     * 6. 调用rpc更新任务数据
     * 7. 将上传地址返回给前端
     *
     * @param param
     * @return
     */
    @RequestMapping(value = "/uploadinit", method = RequestMethod.POST)
    @PreAuthorize("hasPermission('lesports.qmt.video.taskList', 'ADD')")
    public VideoUploadInitVo uploadInit(@ModelAttribute VideoUploadInitParam param) {
        //创建关联的视频
        Video video = videoService.createVideo(param);

        //创建转码任务
        VideoTranscodeTask videoTranscodeTask = videoTranscodeTaskService.createVideoTranscodeTask(param, video);
        LeCasAuthenticationToken authenticationToken = (LeCasAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        videoTranscodeTask.setCreator(authenticationToken.getName());
        //更新任务的视频id
        videoTranscodeTaskService.updateVideoId(videoTranscodeTask, video.getId());

        //构造返回数据
        VideoUploadInitVo videoUploadInitVo = new VideoUploadInitVo();
        videoUploadInitVo.setUploadUrl(videoTranscodeTask.getUploadUrl());
        videoUploadInitVo.setLeecoVid(videoTranscodeTask.getLeecoVid());
        videoUploadInitVo.setSlice(videoTranscodeTask.getSlice());
        videoUploadInitVo.setVideoId(videoTranscodeTask.getVideoId());
        videoUploadInitVo.setLeecoMid(videoTranscodeTask.getLeecoMid());
        videoUploadInitVo.setNodeId(videoTranscodeTask.getNodeId());
        videoUploadInitVo.setReupload(0);
        videoUploadInitVo.setUploadId(videoTranscodeTask.getUploadId());
        return videoUploadInitVo;
    }

    @RequestMapping(value = "/uploadSubTitleInit", method = RequestMethod.POST)
    @PreAuthorize("hasPermission('lesports.qmt.video.taskList', 'ADD')")
    public VideoUploadInitVo uploadSubTitleInit(@RequestParam long leecoVid, @RequestParam String fileName) {
        VideoUploadInitVo videoUploadInitVo = videoTranscodeTaskService.uploadSubTitleInit(leecoVid, fileName);

        return videoUploadInitVo;

    }

    /**
     * 视频转码
     * 1. 调用rpc保存转码参数
     * 2. 调用媒体云接口，上传转码参数
     * 3. 修改数据库转码任务
     *
     * @param param
     * @return
     */
    @RequestMapping(value = "/posttrans", method = RequestMethod.POST)
    @PreAuthorize("hasPermission('lesports.qmt.video.taskList', 'ADD')")
    public TranscodeSubmitVo postTrans(@ModelAttribute VideoTranscodeParam param) {
        TranscodeSubmitVo videoTranscodeVo = new TranscodeSubmitVo();

        try {
            LOGGER.info("Video transcode task post trans, param : {}.", JSONObject.toJSONString(param));
            //提交转码参数并更新转码任务
            VideoTranscodeTask task = videoTranscodeTaskService.submitVideoTranscodeTask(param);
            Video video = videoService.updateVideo(param, task.getVideoId());
            List<Integer> formats = new ArrayList<>();
            if (CollectionUtils.isNotEmpty(task.getSubTasks())) {
                formats = Lists.transform(task.getSubTasks(), subTask ->  subTask.getFormat());
            }
            //创建相关的视频介质
            VideoMedium videoMedium = videoMediumService.createVideoMedium(video.getId(), formats);
            videoTranscodeVo.setSuccess(true);

        } catch (Exception e) {
            videoTranscodeVo.setSuccess(false);
            videoTranscodeVo.setMsg(e.getMessage());
        }


        return videoTranscodeVo;
    }

    /**
     * 视频重新上传
     *
     * @param param
     * @return
     */
    @RequestMapping(value = "/reupload", method = RequestMethod.POST)
    @PreAuthorize("hasPermission('lesports.qmt.video.taskList', 'ADD')")
    public VideoUploadInitVo reUpload(@ModelAttribute VideoUploadInitParam param) {
        LOGGER.info("Video transcode task reupload, leecovid : {}.", param.getLeecoVid());

        VideoTranscodeTask videoTranscodeTask = videoTranscodeTaskService.reUpload(param);
        VideoUploadInitVo videoUploadInitVo = new VideoUploadInitVo();
        videoUploadInitVo.setUploadUrl(videoTranscodeTask.getUploadUrl());
        videoUploadInitVo.setLeecoVid(videoTranscodeTask.getLeecoVid());
        videoUploadInitVo.setSlice(videoTranscodeTask.getSlice());
        videoUploadInitVo.setVideoId(videoTranscodeTask.getVideoId());
        return videoUploadInitVo;
    }

    /**
     * 上传完成后回调
     *
     * @param param
     * @return
     */
    @RequestMapping(value = "/finish", method = RequestMethod.POST)
    @PreAuthorize("hasPermission('lesports.qmt.video.taskList', 'ADD')")
    public TranscodeSubmitVo updateSourceFileUrl(@ModelAttribute VideoFileUrlParam param) {
        LOGGER.info("Video transcode task upload finish, leecovid : {}.", param.getLeecoVid());
        VideoTranscodeTask task = videoTranscodeTaskService.updateFileUrl(param);

        TranscodeSubmitVo videoTranscodeVo = new TranscodeSubmitVo();
        if (StringUtils.isEmpty(task.getSrcUrl())) {
            videoTranscodeVo.setSuccess(false);
        } else {
            videoTranscodeVo.setSuccess(true);
        }

        return videoTranscodeVo;
    }

    /**
     * 查询转码进度
     *
     * @param ids leecoVid列表
     * @return
     */
    @RequestMapping(value = "/progress", method = RequestMethod.GET)
    public List<VideoTranscodeProgressVo> progress(@RequestParam("ids") String ids) {
        List<VideoTranscodeProgressVo> vos = new ArrayList<>();
        String[] leecoVids = ids.split(",");
        if (null != leecoVids) {
            for (String id : leecoVids) {
                VideoTranscodeProgressResult result = LiveApis.getTranscodeProgress(LeNumberUtils.toLong(id));
                VideoTranscodeProgressResult.Data data = result.getData().get(0);
                VideoTranscodeProgressVo transcodeProgressVo = new VideoTranscodeProgressVo();
                transcodeProgressVo.setCheckCode(data.getCheckCode());
                transcodeProgressVo.setCheckMsg(data.getCheckMsg());
                transcodeProgressVo.setErrCode(data.getErrCode());
                transcodeProgressVo.setErrMsg(data.getErrMsg());
                List<VideoTranscodeProgressVo.FormatInfo> formatInfos = new ArrayList<>();
                if (CollectionUtils.isNotEmpty(data.getBusCellInfos())) {
                    for (VideoTranscodeProgressResult.BusCellInfo busCellInfo : data.getBusCellInfos()) {
                        VideoTranscodeProgressVo.FormatInfo formatInfo = new VideoTranscodeProgressVo.FormatInfo();
                        formatInfo.setErrMsg(busCellInfo.getErrMsg());
                        formatInfo.setErrCode(busCellInfo.getErrCode());
                        formatInfo.setCheckMsg(busCellInfo.getCheckMsg());
                        formatInfo.setCheckCode(busCellInfo.getCheckCode());
                        formatInfo.setProgress(busCellInfo.getProgress());
                        formatInfo.setVtype(busCellInfo.getVtype());
                        formatInfo.setFormatName(busCellInfo.getCodeRate() + "");
                        formatInfos.add(formatInfo);
                    }
                }
                transcodeProgressVo.setFormatInfos(formatInfos);
                vos.add(transcodeProgressVo);
            }
        }

        return vos;
    }

    /**
     * 片源管理单个视频 码率管理
     */
    @RequestMapping(value = "/{id}/singleVideoDetail", method = RequestMethod.GET)
    public SingleTaskDetailParam getSingleVideoDetail(@PathVariable("id") long id) {
        try {
            return videoTranscodeTaskService.getSingleTaskDetailParamByVideoTranscodeTaskId(id);
        } catch (LeWebApplicationException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }


    /**
     * 历史上传 获取上传视频列表
     */
    @RequestMapping(value = "/videoTranscodePages", method = RequestMethod.GET)
    public Page<VideoTranscodeTask> queryVideoTranscodeTaskPage(
            @RequestParam(value = "user", required = false, defaultValue = "0") String user,
            @RequestParam(value = "status", required = false, defaultValue = "0") int status,
            @RequestParam(value = "page", required = false, defaultValue = "0") int pageNumber,
            @RequestParam(value = "count", required = false, defaultValue = "20") int pageSize) {
        LeCasAuthenticationToken authenticationToken = (LeCasAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

        Page<VideoTranscodeTask> result = videoTranscodeTaskService.getVideoTranscodeTaskPage(authenticationToken.getName(),
                status, pageNumber, pageSize);
        return result;
    }


}
