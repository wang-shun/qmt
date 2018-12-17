package com.lesports.qmt.transcode.web.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.lesports.model.*;
import com.lesports.qmt.sbc.api.common.TranscodeStatus;
import com.lesports.qmt.sbc.client.QmtSbcVideoInternalApis;
import com.lesports.qmt.sbc.model.Video;
import com.lesports.qmt.sbc.model.VideoFormat;
import com.lesports.qmt.sbc.model.VideoMedium;
import com.lesports.qmt.transcode.client.TranscodeInternalApis;
import com.lesports.qmt.transcode.model.*;
import com.lesports.qmt.transcode.web.param.*;
import com.lesports.qmt.transcode.web.service.VideoTranscodeTaskService;
import com.lesports.qmt.transcode.web.support.TranscodeConstants;
import com.lesports.qmt.transcode.web.support.TranscodeUtils;
import com.lesports.qmt.transcode.web.support.VideoCodeRateText;
import com.lesports.query.InternalCriteria;
import com.lesports.query.InternalQuery;
import com.lesports.utils.LeDateUtils;
import com.lesports.utils.LiveApis;
import com.lesports.utils.MD5Util;
import com.lesports.utils.math.LeNumberUtils;
import org.apache.commons.beanutils.LeBeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * trunk.
 *
 * @author pangchuanxiao
 * @since 2016/10/29
 */
@Service("videoTranscodeTaskService")
public class VideoTranscodeTaskServiceImpl implements VideoTranscodeTaskService {
    private static final Logger LOGGER = LoggerFactory.getLogger(VideoTranscodeTaskServiceImpl.class);
    private static final ExecutorService EXECUTOR_SERVICE = Executors.newScheduledThreadPool(10);

    @Override
    public VideoTranscodeTask updateVideoId(VideoTranscodeTask task, long videoId) {
        task.setVideoId(videoId);
        TranscodeInternalApis.saveEntity(task, VideoTranscodeTask.class);
        return task;
    }

    @Override
    public VideoTranscodeTask updateFileUrl(VideoFileUrlParam param) {
        VideoTranscodeTask videoTranscodeTask = TranscodeInternalApis.getVideoTaskByLeecoVid(param.getLeecoVid());

        updateTaskWhenUploadSuccess(videoTranscodeTask, param.getType());
        return videoTranscodeTask;
    }

    @Override
    public VideoTranscodeTask submitVideoTranscodeTask(VideoTranscodeParam param) {
        VideoTranscodeTask videoTranscodeTask = TranscodeInternalApis.getVideoTaskByLeecoVid(param.getLeecoVid());
        if (null == videoTranscodeTask) {
            return null;
        }
        updateTask(param, videoTranscodeTask);
        postTrans(videoTranscodeTask);
        return videoTranscodeTask;
    }

    @Override
    public VideoTranscodeTask reUpload(VideoUploadInitParam param) {
        VideoTranscodeTask oldTask = TranscodeInternalApis.getVideoTaskByLeecoVid(param.getLeecoVid());
        VideoTranscodeTask task = new VideoTranscodeTask();
        LeBeanUtils.copyNotEmptyPropertiesQuietly(task, oldTask);
        task.setId(null);
        task.setLocalName(param.getLocalName());
        task.setMd5(param.getMd5());
        task.setFileSize(param.getFileSize());
        task.setFrom(param.getFrom());
        task.setUser(param.getUser());
        task.setIsReUpload(true);
        task.setStatus(VideoTranscodeTaskStatus.NEW);
        task.setDeleted(false);
        //web端文件大小大于10M，进行分片
        if (TranscodeConstants.WEB_FROM.equals(param.getFrom())
                && param.getFileSize() > 10 * 1024 * 1024) {
            task.setSlice(true);
        } else {
            task.setSlice(false);
        }
        //请求媒体云，获取地址
        VideoUploadInitResult result = LiveApis.videoUploadInit(toLiveInitRequest(task, 1));
        LOGGER.info("video upload init result : {}", JSONObject.toJSONString(result));
        String token = result.getResult().getToken();
        String uploadUrl = result.getResult().getUploadUrl();
        task.setToken(token);
        task.setUploadUrl(uploadUrl);
        task.setUploadId(result.getResult().getUploadId());
        long id = TranscodeInternalApis.saveEntity(task, VideoTranscodeTask.class);
        task.setId(id);
        oldTask.setDeleted(true);
        TranscodeInternalApis.saveEntity(oldTask, VideoTranscodeTask.class);
        return task;
    }

    @Override
    public VideoTranscodeTask createVideoTranscodeTask(VideoUploadInitParam param, Video video) {
        VideoTranscodeTask videoTranscodeTask = new VideoTranscodeTask();
        VideoUploadInitResult result;

        videoTranscodeTask.setFileSize(param.getFileSize());
        videoTranscodeTask.setIsReUpload(false);
        videoTranscodeTask.setDeleted(false);
        videoTranscodeTask.setUser(param.getUser());
        videoTranscodeTask.setLocalName(param.getLocalName());
        videoTranscodeTask.setMd5(param.getMd5());
        //web端文件大小大于10M，进行分片
        if (TranscodeConstants.WEB_FROM.equals(param.getFrom())
                && param.getFileSize() > 10 * 1024 * 1024) {
            videoTranscodeTask.setSlice(true);
        } else {
            videoTranscodeTask.setSlice(false);
        }
        videoTranscodeTask.setStatus(VideoTranscodeTaskStatus.NEW);
        //请求媒资获取视频id
//        VideoCreateResult res = MmsApis.createVideo(toMmsParam(videoTranscodeTask));
        videoTranscodeTask.setLeecoVid(video.getLeecoVid());
        videoTranscodeTask.setLeecoMid(video.getLeecoMid());

        //请求媒体云，获取地址
        result = LiveApis.videoUploadInit(toLiveInitRequest(videoTranscodeTask, 1));
        LOGGER.info("video upload init result : {}", JSONObject.toJSONString(result));
        String token = result.getResult().getToken();
        String uploadUrl = result.getResult().getUploadUrl();
        Integer nodeId = result.getResult().getNodeId();
        videoTranscodeTask.setNodeId(nodeId);
        videoTranscodeTask.setToken(token);
        videoTranscodeTask.setUploadUrl(uploadUrl);
        videoTranscodeTask.setUploadId(result.getResult().getUploadId());
        long id = TranscodeInternalApis.saveEntity(videoTranscodeTask, VideoTranscodeTask.class);
        videoTranscodeTask.setId(id);
        return videoTranscodeTask;
    }

    private void updateTaskWhenUploadSuccess(VideoTranscodeTask videoTranscodeTask, Integer type) {
        if(null == videoTranscodeTask || null == type) return ;
        EXECUTOR_SERVICE.execute(() -> {
            int retry = 0;
            VideoTranscodeDownloadUrlResult result = LiveApis.getDownloadUrl(videoTranscodeTask.getToken());
            while (retry < 10 && (null == result || null == result.getDownloadUrl())) {
                try {
                    TimeUnit.SECONDS.sleep(10);
                } catch (InterruptedException e) {
                    LOGGER.error("{}", e.getMessage(), e);
                }
                result = LiveApis.getDownloadUrl(videoTranscodeTask.getToken());
                retry++;
            }
            if (null != result && null != result.getDownloadUrl()) {
                videoTranscodeTask.setSrcUrl(result.getDownloadUrl());
                if (videoTranscodeTask.getStatus() == VideoTranscodeTaskStatus.NEW) {
                    videoTranscodeTask.setStatus(VideoTranscodeTaskStatus.UPLOAD_SUCCESS);
                    TranscodeInternalApis.saveEntity(videoTranscodeTask, VideoTranscodeTask.class);
                } else {
                    postTrans(videoTranscodeTask);
                }
            } else {
                videoTranscodeTask.setStatus(VideoTranscodeTaskStatus.GET_DOWNLOAD_URL_FAIL);
                TranscodeInternalApis.saveEntity(videoTranscodeTask, VideoTranscodeTask.class);
            }
        });
    }

    /**
     * 构造转码上传接口的参数
     *
     * @param task     转码任务
     * @param fileType 文件类型 1视频 2字母
     * @return
     */
    private VideoUploadInitRequest toLiveInitRequest(VideoTranscodeTask task, int fileType) {

        VideoUploadInitRequest videoUploadInitRequest = new VideoUploadInitRequest();
        videoUploadInitRequest.setClientIp("");
        videoUploadInitRequest.setTimestamp(new DateTime().getMillis());
        videoUploadInitRequest.setName(task.getLocalName());
        videoUploadInitRequest.setMd5(task.getMd5());
        if (fileType == 2) {
            videoUploadInitRequest.setUploadtype(0);
        } else if (task.getSlice()) {
            videoUploadInitRequest.setUploadtype(1);
        } else {
            videoUploadInitRequest.setUploadtype(0);
        }
        return videoUploadInitRequest;
    }

    private VideoTranscodeRequest toLiveRequest(VideoTranscodeTask task) {
        VideoTranscodeRequest request = new VideoTranscodeRequest();

        request.setUploadId(task.getUploadId());
        request.setTranscodeArgs(JSONObject.toJSONString(task.getTranscodeArgs()));

        return request;
    }

    private Map toMmsParam(VideoTranscodeTask task) {
        Map<String, Object> videoUploadInitRequest = new HashMap<>();

        videoUploadInitRequest.put("nameCn", task.getLocalName());
        videoUploadInitRequest.put("controlAreas", null);
        videoUploadInitRequest.put("playPlatform", "");
        videoUploadInitRequest.put("downPlatform", "");
        videoUploadInitRequest.put("drmFlag", "");
        videoUploadInitRequest.put("coprightSite", "");
        videoUploadInitRequest.put("site", "");
        videoUploadInitRequest.put("disableType", "");
        videoUploadInitRequest.put("tag", "");
        videoUploadInitRequest.put("pid", "");
        videoUploadInitRequest.put("videoType", "180001");
        videoUploadInitRequest.put("description", "default desc");
        videoUploadInitRequest.put("tag", "defalut tag");
        videoUploadInitRequest.put("platform", TranscodeConstants.MMS_LESPORTS_PLATFORM);
        videoUploadInitRequest.put("token", MD5Util.md5(TranscodeConstants.MMS_LESPORTS_PLATFORM + TranscodeConstants.MMS_LESPORTS_PRIVATE_KEY));

        return videoUploadInitRequest;
    }

    private void updateTask(VideoTranscodeParam param, VideoTranscodeTask videoTranscodeTask) {
        videoTranscodeTask.setAutoBlackSide(param.getAutoBlackSide());
        videoTranscodeTask.setScreenRatio(param.getScreenRatio());
        videoTranscodeTask.setScreenSize(param.getScreenSize());
        videoTranscodeTask.setDuration(param.getDuration());
        videoTranscodeTask.setIsEnhance(param.getIsEnhance());
        videoTranscodeTask.setRecommend2Homepage(param.getRecommend2Homepage());
        videoTranscodeTask.setTranscodeStartTime(param.getTranscodeStartTime());
        videoTranscodeTask.setTranscodeEndTime(param.getTranscodeEndTime());
        videoTranscodeTask.setUseSub(param.getUseSub());
        String[] formats = StringUtils.split(param.getFormats(), ",");
        if (null != formats) {
            List<VideoTranscodeSubTask> formatList = new ArrayList<>();
            for (VideoCodeRateText defaultRate : TranscodeConstants.DEFAULT_FORMAT) {
                VideoTranscodeSubTask subTask = new VideoTranscodeSubTask();
                subTask.setFormat(defaultRate.getCodeRate());
                subTask.setStatus(VideoTranscodeTaskStatus.TRANSCODING);
                subTask.setParentId(videoTranscodeTask.getId());
                subTask.setFormatName(defaultRate.getCodeText());
                subTask.setCreateAt(LeDateUtils.formatYYYYMMDDHHMMSS(new Date()));
                formatList.add(subTask);
            }
            for (String format : formats) {
                VideoTranscodeSubTask subTask = new VideoTranscodeSubTask();
                VideoCodeRateText videoCodeRateText = VideoCodeRateText.getCodeTextByText(format);
                if (null == videoCodeRateText) {
                    continue;
                }
                subTask.setFormat(videoCodeRateText.getCodeRate());
                subTask.setStatus(VideoTranscodeTaskStatus.TRANSCODING);
                subTask.setParentId(videoTranscodeTask.getId());
                subTask.setFormatName(format);
                subTask.setCreateAt(LeDateUtils.formatYYYYMMDDHHMMSS(new Date()));
                formatList.add(subTask);
            }
            videoTranscodeTask.setSubTasks(formatList);
        }
        videoTranscodeTask.setModifiedAspectRadio(param.getModifiedAspectRadio());
        videoTranscodeTask.setPriority(param.getPriority());
        if (TranscodeConstants.CLIENT_FROM.equals(param.getFrom())) {
            videoTranscodeTask.setTranscodeArgs(JSONObject.parseObject(param.getTranscodeArgs()));
        } else {
            Map transcodeArgs = createDefaultTranscodeArgs(param, videoTranscodeTask);
            videoTranscodeTask.setTranscodeArgs(transcodeArgs);
        }
        TranscodeInternalApis.saveEntity(videoTranscodeTask, VideoTranscodeTask.class);
    }


    private Map createDefaultTranscodeArgs(VideoTranscodeParam param, VideoTranscodeTask task) {
        Map<String, Object> result = new HashMap<>();
        result.put("appKey", TranscodeConstants.TRANSCODE_APP_KEY);
        result.put("multiType", "L|4");
        result.put("nodeId", task.getNodeId());
        result.put("uploadId", task.getUploadId());
        result.put("src", task.getSrcUrl());
        result.put("subUrl", task.getSubUrl());
        result.put("user", TranscodeConstants.VIDEO_TRANSCODE_USER);
        result.put("version", "client_normal_01");
        result.put("vid", task.getLeecoVid());
        result.put("mid", task.getLeecoMid());
        result.put("videoName", param.getTitle());
        result.put("channel", TranscodeConstants.MMS_LESPORTS_CATEGORY);
        result.put("srcMediaInfo", new HashMap<>());
        Map<String, Object> transcodeInfo = new HashMap<>();
        transcodeInfo.put("transcodeType", 1);
        StringBuffer stringBuffer = new StringBuffer();
        task.getSubTasks().stream().forEach((subTask) -> stringBuffer.append(subTask.getFormat()).append(","));
        transcodeInfo.put("destCodeRate", stringBuffer.toString());
//          --crop=0:100:1:1 --inpaint=10:5:100:100
        String transcodeParam = " --crop=0:0:0:0 ";
        int start = TranscodeUtils.formatToSeconds(param.getTranscodeStartTime());
        int end = TranscodeUtils.formatToSeconds(param.getTranscodeEndTime());
        if (start <= 0 && end > 0) {
            transcodeParam = transcodeParam + " --start-at duration:0 --stop-at duration:" + end;
        } else if (start > 0 && end > 0) {
            transcodeParam = transcodeParam + " --start-at duration:" + start + " --stop-at duration:" + end;
        }
        if (StringUtils.isNotEmpty(param.getModifiedAspectRadio())) {
            transcodeParam = transcodeParam + " --custom anamorphic " + param.getModifiedAspectRadio() + " --pixel-aspect=1:1";
        }
        transcodeInfo.put("transcodeParam", transcodeParam);

        Map<String, Object> logoConfig = new HashMap<>();
        logoConfig.put("shotFrom", 1);
        logoConfig.put("logoType", 0);
        transcodeInfo.put("logoUrl", "");
        transcodeInfo.put("logoConfig", logoConfig);

        Map<String, Object> shotImgConfig = new HashMap<>();
        shotImgConfig.put("shotFrom", 1);
        shotImgConfig.put("isShot", 1);
        shotImgConfig.put("withCodeRate", "21");
        transcodeInfo.put("shotImgConfig", shotImgConfig);

        transcodeInfo.put("scheduleTags", Lists.newArrayList("-e", "-genbo"));
        transcodeInfo.put("scheduleTags", Lists.newArrayList());

        transcodeInfo.put("transcodeTags", Collections.emptyList());
        transcodeInfo.put("videoTags", Collections.emptyList());

        result.put("transcodeInfo", transcodeInfo);


        return result;
    }

    @Override
    public VideoTranscodeSubTask addFormat(VideoTranscodeTask task, AddVideoFormatParam param) {
        for (VideoTranscodeSubTask videoTranscodeSubTask : task.getSubTasks()) {
            if (LeNumberUtils.toInt(videoTranscodeSubTask.getFormat()) == VideoCodeRateText.getCodeTextByText(param.getFormat()).getCodeRate()) {
                throw new RuntimeException("任务已存在，请勿重复添加！");
            }
        }
        VideoTranscodeSubTask subTask = new VideoTranscodeSubTask();
        subTask.setCreateAt(LeDateUtils.formatYYYYMMDDHHMMSS(new Date()));
        subTask.setParentId(task.getId());
        subTask.setFormatName(param.getFormat());
        subTask.setFormat(VideoCodeRateText.getCodeTextByText(param.getFormat()).getCodeRate());
        subTask.setStatus(VideoTranscodeTaskStatus.TRANSCODE_CONFIG_COMMIT);
        task.getSubTasks().add(subTask);
        String destCodeRate = (String) ((Map) task.getTranscodeArgs().get("transcodeInfo")).get("destCodeRate");
        if (StringUtils.isNotEmpty(destCodeRate)) {
            destCodeRate = destCodeRate + subTask.getFormat() + ",";
        } else {
            destCodeRate = subTask.getFormat() + ",";
        }
        ((Map) task.getTranscodeArgs().get("transcodeInfo")).put("destCodeRate", destCodeRate);
        TranscodeInternalApis.saveEntity(task, VideoTranscodeTask.class);
        postTransAdd(task, subTask.getFormat());

        return subTask;
    }

    @Override
    public SingleTaskDetailParam getSingleTaskDetailParamByVideoTranscodeTaskId(long id) {
        if (0 == id) return null;
        VideoTranscodeTask videoTranscodeTask = TranscodeInternalApis.getEntityById(id, VideoTranscodeTask.class);
        if (null == videoTranscodeTask) return null;
        InternalQuery query = new InternalQuery().with(new PageRequest(0, Integer.MAX_VALUE));
        query.getCriterias().add(new InternalCriteria("parent_id").is(id));
        List<VideoTranscodeSubTask> videoTranscodeSubTasks = TranscodeInternalApis.getEntitiesByQuery(query, VideoTranscodeSubTask.class);
        return new SingleTaskDetailParam(videoTranscodeTask, videoTranscodeSubTasks);
    }

    @Override
    public VideoUploadInitVo uploadSubTitleInit(long leecoVid, String fileName) {
        VideoTranscodeTask task = TranscodeInternalApis.getVideoTaskByLeecoVid(leecoVid);
        if (null == task) {
            throw new RuntimeException("该视频不存在转码任务！");
        }
        task.setSubFileName(fileName);
        task.setUseSub(true);
        VideoUploadInitResult result = LiveApis.videoUploadInit(toLiveInitRequest(task, 2));
        if (null == result || null == result.getResult() || StringUtils.isEmpty(result.getResult().getUploadUrl())) {
            throw new RuntimeException("获取上传地址失败");
        }
        TranscodeInternalApis.saveEntity(task, VideoTranscodeTask.class);
        VideoUploadInitVo videoUploadInitVo = new VideoUploadInitVo();
        videoUploadInitVo.setUploadUrl(result.getResult().getUploadUrl());
        videoUploadInitVo.setLeecoVid(task.getLeecoVid());
        videoUploadInitVo.setVideoId(task.getVideoId());
        videoUploadInitVo.setNodeId(result.getResult().getNodeId());
        videoUploadInitVo.setReupload(0);
        videoUploadInitVo.setUploadId(result.getResult().getUploadId());
        return videoUploadInitVo;
    }

    @Override
    public Map getVideoTranscodeTask(long leecoVid) {
        Map<Integer, String> progress = getProgress(leecoVid);
        Map<String, Object> result = new HashMap<>();
        VideoTranscodeTask videoTranscodeTask = TranscodeInternalApis.getVideoTaskByLeecoVid(leecoVid);
        if (null != videoTranscodeTask) {
            result.put("task", videoTranscodeTask);
            videoTranscodeTask.setSubTasks(null);
            List<VideoTranscodeSubTask> subTasks = TranscodeInternalApis.getVideoTranscodeSubTasks(videoTranscodeTask.getId());
            subTasks.forEach(subTask -> {
                subTask.setProgress(progress.get(subTask.getFormat()));
            });
            result.put("subTasks", subTasks);
            Video video = QmtSbcVideoInternalApis.getVideoById(videoTranscodeTask.getVideoId());
            result.put("video", video);
        } else {
            LiveToVideoTask liveToVideoTask = TranscodeInternalApis.getLiveToVideoTaskByLeecoVid(leecoVid);
            result.put("task", liveToVideoTask);
            List<LiveToVideoSubTask> subTasks = TranscodeInternalApis.getLiveToVideoSubTasks(liveToVideoTask.getId());
            subTasks.forEach(subTask -> {
                subTask.setProgress(progress.get(subTask.getFormat()));
            });
            result.put("subTasks", subTasks);
            Video video = QmtSbcVideoInternalApis.getVideoById(liveToVideoTask.getVideoId());
            result.put("video", video);
        }

        return result;
    }

    @Override
    public Page<VideoTranscodeTask> getVideoTranscodeTaskPage(String username, int status, int pageNumber, int pageSize) {
        InternalQuery query = new InternalQuery();
        if (StringUtils.isNotEmpty(username)) {
            query.addCriteria(new InternalCriteria("creator").is(username));
        }
        if (status == 1) {
            query.addCriteria(new InternalCriteria("status").in(Lists.newArrayList(
                    VideoTranscodeTaskStatus.TRANSCODING, VideoTranscodeTaskStatus.UPLOAD_SUCCESS)));
        } else if (status == 2) {
            query.addCriteria(new InternalCriteria("status").in(Lists.newArrayList(VideoTranscodeTaskStatus.TASK_FINISH,
                    VideoTranscodeTaskStatus.TRANSCODING_FAIL, VideoTranscodeTaskStatus.UPLOAD_FAIL)));
        }
        query.addCriteria(new InternalCriteria("deleted", "eq", false));
        long count = TranscodeInternalApis.countEntitiesByQuery(query, VideoTranscodeTask.class);

        Pageable pageable = new PageRequest(pageNumber, pageSize);
        query.with(pageable);
        query.setSort(new Sort(Sort.Direction.DESC, "update_at"));
        List<VideoTranscodeTask> result = TranscodeInternalApis.getEntitiesByQuery(query, VideoTranscodeTask.class);

        return new PageImpl<>(result, pageable, count);

    }

    private Map<Integer, String> getProgress(Long leecoVid) {
        Map<Integer, String> result = new HashMap<>();
        VideoTranscodeProgressResult progress = LiveApis.getTranscodeProgress(LeNumberUtils.toLong(leecoVid));
        if (null != progress && CollectionUtils.isNotEmpty(progress.getData())) {
            VideoTranscodeProgressResult.Data data = progress.getData().get(0);
            if (null != data) {
                for (VideoTranscodeProgressResult.BusCellInfo busCellInfo : data.getBusCellInfos()) {
                    result.put(LeNumberUtils.toInt(busCellInfo.getCodeRate()), busCellInfo.getProgress());
                }
            }
        }
        return result;
    }

    /**
     * 给直播云提交转码任务
     *
     * @param videoTranscodeTask
     */
    private void postTrans(VideoTranscodeTask videoTranscodeTask) {
        if (videoTranscodeTask.getStatus() == VideoTranscodeTaskStatus.TRANSCODING) {
            throw new RuntimeException("正在转码中，请勿重复提交！");
        }
        VideoTranscodeResult result = LiveApis.postTrans(toLiveRequest(videoTranscodeTask));
        if (null != result && LeNumberUtils.toInt(result.getStatus()) == 200) {
            videoTranscodeTask.setStatus(VideoTranscodeTaskStatus.TRANSCODING);
            TranscodeInternalApis.saveEntity(videoTranscodeTask, VideoTranscodeTask.class);
        } else {
            videoTranscodeTask.setStatus(VideoTranscodeTaskStatus.TRANSCODE_CONFIG_COMMIT);
            TranscodeInternalApis.saveEntity(videoTranscodeTask, VideoTranscodeTask.class);
            throw new RuntimeException("提交转码任务失败！");
        }
        LOGGER.info("transcode task : {}, result : {}.", videoTranscodeTask.getUploadId(), JSONObject.toJSONString(result));
    }

    private void postTransAdd(VideoTranscodeTask videoTranscodeTask, int format) {
        long mid = videoTranscodeTask.getLeecoMid();
        VideoTranscodeSubTask subTask = null;
        for (VideoTranscodeSubTask transcodeSubTask : videoTranscodeTask.getSubTasks()) {
            if (format == LeNumberUtils.toInt(transcodeSubTask.getFormat())) {
                subTask = transcodeSubTask;
            }
        }
        VideoTranscodeResult result = LiveApis.addRate(mid, format);
        if (null != result && LeNumberUtils.toInt(result.getStatus()) == 200) {
            videoTranscodeTask.setStatus(VideoTranscodeTaskStatus.TRANSCODING);
            subTask.setStatus(VideoTranscodeTaskStatus.TRANSCODING);
            TranscodeInternalApis.saveEntity(videoTranscodeTask, VideoTranscodeTask.class);
            VideoMedium videoMedium = QmtSbcVideoInternalApis.getVideoMediumByVideoId(videoTranscodeTask.getVideoId());
            VideoFormat videoFormat = new VideoFormat();
            videoFormat.setCodeRate(format);
            videoFormat.setStatus(TranscodeStatus.TRANSCODING);
            videoMedium.getFormats().add(videoFormat);
            QmtSbcVideoInternalApis.saveVideoMedium(videoMedium);
        } else {
            videoTranscodeTask.setStatus(VideoTranscodeTaskStatus.TRANSCODE_CONFIG_COMMIT);
            TranscodeInternalApis.saveEntity(videoTranscodeTask, VideoTranscodeTask.class);
            throw new RuntimeException("提交追加到直播云任务失败！");
        }
        LOGGER.info("transcode additional task : {}, format : {}, result : {}.", videoTranscodeTask.getUploadId(), format, JSONObject.toJSONString(result));
    }
}
