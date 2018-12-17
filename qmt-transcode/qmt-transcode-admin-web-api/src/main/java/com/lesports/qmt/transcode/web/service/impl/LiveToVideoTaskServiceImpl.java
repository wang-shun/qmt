package com.lesports.qmt.transcode.web.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.lesports.id.api.IdType;
import com.lesports.model.LiveRecordingCreateResult;
import com.lesports.model.VideoTranscodeRequest;
import com.lesports.model.VideoTranscodeResult;
import com.lesports.qmt.log.LogContent;
import com.lesports.qmt.log.LoggerHandler;
import com.lesports.qmt.sbc.client.QmtSbcVideoInternalApis;
import com.lesports.qmt.sbc.model.Video;
import com.lesports.qmt.transcode.client.TranscodeInternalApis;
import com.lesports.qmt.transcode.model.*;
import com.lesports.qmt.transcode.web.param.AddVideoFormatParam;
import com.lesports.qmt.transcode.web.param.LiveChannelVo;
import com.lesports.qmt.transcode.web.param.LiveTaskParam;
import com.lesports.qmt.transcode.web.param.VideoTranscodeParam;
import com.lesports.qmt.transcode.web.service.LiveToVideoTaskService;
import com.lesports.qmt.transcode.web.support.TranscodeConstants;
import com.lesports.qmt.transcode.web.support.VideoCodeRateText;
import com.lesports.query.InternalQuery;
import com.lesports.utils.LeDateUtils;
import com.lesports.utils.LeStringUtils;
import com.lesports.utils.LiveApis;
import com.lesports.utils.MD5Util;
import com.lesports.utils.math.LeNumberUtils;
import com.letv.urus.liveroom.api.dto.StreamDTO;
import com.letv.urus.liveroom.api.dto.sports.ChannelIdNameEnameDTO;
import com.letv.urus.liveroom.api.sports.LiveRoomSportsQueryAPI;
import com.letv.urus.liveroom.base.UrusAuth;
import com.letv.urus.liveroom.exception.UrusLiveRoomException;
import com.letv.urus.streamchannel.bo.Channel;
import com.letv.urus.streamchannel.bo.Stream;
import org.apache.commons.beanutils.LeBeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;

/**
 * trunk.
 *
 * @author pangchuanxiao
 * @since 2016/10/29
 */
@Service("liveToVideoTaskService")
public class LiveToVideoTaskServiceImpl implements LiveToVideoTaskService {

    @Resource
    private LiveRoomSportsQueryAPI liveRoomQueryAPI;

    private static final Logger LOGGER = LoggerFactory.getLogger(LiveToVideoTaskServiceImpl.class);
    //1080p >720p>1300 >1000>800
    //需要有这三路流才能发布
    private static final String TRANSCODE_BASECODE_360 = "159,160,161";
    private static UrusAuth urusAuth;

    static {
        urusAuth = new UrusAuth();
        urusAuth.setUrusAppId("id");
        urusAuth.setUrusSecret("secret");
        urusAuth.setRequestId("123");
        urusAuth.setUrusUserId("dabingge");
    }

    @Override
    public boolean createLiveRecording(LiveToVideoTask liveToVideoTask, List<LiveToVideoSubTask> subTasks) {
        Video video = QmtSbcVideoInternalApis.getVideoById(liveToVideoTask.getVideoId());
        Map<String, Object> param = new HashMap<>();
        Map<String, String> input = new HashMap<>();
        input.put("taskId", String.valueOf(liveToVideoTask.getId()));
        input.put("taskName", video.getTitle());
        input.put("recordType", String.valueOf(liveToVideoTask.getRecordingType().getValue()));
        input.put("taskType", String.valueOf(liveToVideoTask.getType().getValue()));
        input.put("bizId", TranscodeConstants.LIVE_RECORDING_BIZ_ID);
        String time = "";
        String start = LeDateUtils.formatYYYYMMDDTHHMMSSZ(LeDateUtils.parseYMDHMS(liveToVideoTask.getTranscodeStartTime()));
        String end = LeDateUtils.formatYYYYMMDDTHHMMSSZ(LeDateUtils.parseYMDHMS(liveToVideoTask.getTranscodeEndTime()));
        time += start + "," + end + ";";
        input.put("timeParts", time);

        param.put("input", input);
        List<Map<String, String>> output = new ArrayList<>();
        for (LiveToVideoSubTask subTask : subTasks) {
            Map<String, String> taskMap = new HashMap();
            taskMap.put("subTaskId", String.valueOf(subTask.getId()));
            taskMap.put("streamName", liveToVideoTask.getStreamName());
            taskMap.put("rateType", subTask.getFormatName());
            output.add(taskMap);
        }
        param.put("output", output);
        LiveRecordingCreateResult result = LiveApis.createRecordingTask(param);
        LOGGER.info("Create recording task for : {}, result : {}, param : {}", liveToVideoTask.getId(), JSONObject.toJSONString(result), JSONObject.toJSONString(param));
        if (null != result && "200".equals(result.getStatus())) {
            return true;
        }
        return false;
    }

    @Override
    public boolean createEncapsulationTask(LiveToVideoSubTask subTask) {
        LiveToVideoTask task = TranscodeInternalApis.getEntityById(subTask.getParentId(), LiveToVideoTask.class);
        Map transcodeArgs = createTranscodeArgs(task, subTask);
        subTask.setTranscodeArgs(transcodeArgs);
        VideoTranscodeRequest request = toLiveRequest(subTask);
        VideoTranscodeResult result = LiveApis.postEncapsulation(request);
        LOGGER.info("Create encapsulation task : {}, result : {}", JSONObject.toJSONString(request), JSONObject.toJSONString(result));
        if (null != result && 200 == LeNumberUtils.toInt(result.getStatus())) {
            subTask.setTaskStatus(LiveToVideoTaskStatus.TRANSCODING);
            TranscodeInternalApis.saveEntity(subTask, LiveToVideoSubTask.class);
            return true;
        } else {
            LogContent logContent = new LogContent();
            logContent.setEntryId(String.valueOf(subTask.getId()));
            logContent.setMethodPath("/transcode/encapsulation");
            logContent.setOperator(task.getUpdater());
            logContent.setIdType(IdType.TRANSCODE_LIVE_TASK); //索引类型
            logContent.setSendTo("pangchuanxiao@le.com"); //多个邮件用逗号分隔
            logContent.setSubject("直播大厅保存到云失败"); //邮件标题
            logContent.setMemo("创建转封装任务失败, 参数：" + JSONObject.toJSONString(request)
                    + "，结果：" + JSONObject.toJSONString(result)); //邮件内容
            LoggerHandler.sendEMail(logContent);
        }
        return false;
    }

    @Override
    public LiveToVideoTask updateTaskStatus(long taskId, int status) {
        LiveToVideoTask task = TranscodeInternalApis.getEntityById(taskId, LiveToVideoTask.class);
        if(null != task && task.getType() == LiveToVideoTaskType.RECORDING) {
            task.setStatus(LiveToVideoTaskStatus.findByValue(status));
        }
        String downloadUrl = "";
        List<LiveToVideoSubTask> subTasks = TranscodeInternalApis.getLiveToVideoSubTasks(taskId);
        if (!CollectionUtils.isEmpty(subTasks)) {
            LiveToVideoSubTask max = subTasks.get(0);
            for (LiveToVideoSubTask subTask : subTasks) {
                if (subTask.getFormat() > max.getFormat()) {
                    max = subTask;
                }
            }
            downloadUrl = max.getDownloadUrl();
        }
        task.setDownloadUrl(downloadUrl);
        TranscodeInternalApis.saveEntity(task, LiveToVideoTask.class);
        return task;
    }

    @Override
    public LiveToVideoSubTask updateSubTaskStatus(LiveToVideoSubTask param) {
        long id = param.getSubtaskId();
        LiveToVideoSubTask subTask = TranscodeInternalApis.getEntityById(id, LiveToVideoSubTask.class);
        LeBeanUtils.copyNotEmptyPropertiesQuietly(subTask, param);
        subTask.setTaskStatus(LiveToVideoTaskStatus.findByValue(LeNumberUtils.toInt(param.getStatus())));
        TranscodeInternalApis.saveEntity(subTask, LiveToVideoSubTask.class);
        return subTask;
    }

    @Override
    public List<LiveToVideoSubTask> createLiveToVideoSubTasks(LiveToVideoTask parent) {
        List<String> formats = parent.getFormatNames();
        List<LiveToVideoSubTask> subTasks = new ArrayList<>();
        for (String format : formats) {
            LiveToVideoSubTask subTask = new LiveToVideoSubTask();
            int f = LeNumberUtils.toInt(convertFormatNameToFormat(format));
            subTask.setFormat(f);
            subTask.setParentId(parent.getId());
            subTask.setFormatName(format);
            long id = TranscodeInternalApis.saveEntity(subTask, LiveToVideoSubTask.class);
            subTask.setId(id);
            subTasks.add(subTask);
        }
        return subTasks;
    }

    @Override
    public LiveToVideoTask createLiveToVideoTask(LiveTaskParam param, VideoTranscodeParam transcodeParam, Video video) {
        LiveToVideoTask task = new LiveToVideoTask();
        task.setChannelId(param.getChannelId());
        task.setStreamName(param.getStreamName());
        task.setTaskName(param.getTaskName());
        task.setIsEnhance(transcodeParam.getIsEnhance());
        task.setModifiedAspectRadio(transcodeParam.getModifiedAspectRadio());
        task.setDeleted(false);
        List<String> formatNames = new ArrayList<>();
        TranscodeConstants.DEFAULT_FORMAT.forEach(a -> formatNames.add(a.getCodeText()));
        task.setFormatNames(formatNames);
        ChannelIdNameEnameDTO channel = null;
        try {
            channel = liveRoomQueryAPI.getLiveChannelById(urusAuth, task.getChannelId());
        } catch (UrusLiveRoomException e) {
            LOGGER.error("{}", e.getMessage(), e);
        }
        task.setDrmFlag(channel.getDrmFlag());
        task.setPanoramicView(channel.getPanoramicView());
        task.setTranscodeEndTime(param.getTranscodeEndTime());
        task.setTranscodeStartTime(param.getTranscodeStartTime());
        task.setStatus(LiveToVideoTaskStatus.RECORDING);
        if (null != param.getType()) {
            task.setType(LiveToVideoTaskType.findByValue(param.getType()));
        }
        task.setRecordingType(RecordingType.TS);

//        VideoCreateResult res = MmsApis.createVideo(toMmsParam(task));
        task.setLeecoVid(video.getLeecoVid());
        task.setLeecoMid(video.getLeecoMid());
        long id = TranscodeInternalApis.saveEntity(task, LiveToVideoTask.class);
        task.setId(id);

        return task;
    }

    /**
     * 获取最高码率的stream name
     *
     * @param streamList
     * @return
     */
    private String getMaxFormatName(List<StreamDTO> streamList) {
        Stream max = streamList.get(0);
        for (Stream stream : streamList) {
            if (stream.getRate() > max.getRate()) {
                max = stream;
            }
        }
        return max.getStreamName();
    }

    private Map toMmsParam(LiveToVideoTask task) {

        Map<String, Object> videoUploadInitRequest = new HashMap<>();
        videoUploadInitRequest.put("nameCn", task.getTaskName());
        videoUploadInitRequest.put("description", "default desc");
        videoUploadInitRequest.put("tag", "default tag");
        videoUploadInitRequest.put("platform", TranscodeConstants.MMS_LESPORTS_PLATFORM);
        videoUploadInitRequest.put("playPlatform", "");
        videoUploadInitRequest.put("downPlatform", "");
        videoUploadInitRequest.put("drmFlag", LeNumberUtils.toInt(task.getDrmFlag()) == 0 ? "0" : String.valueOf(task.getDrmFlag()));
        videoUploadInitRequest.put("videoType", "180001");
        videoUploadInitRequest.put("description", "default desc");
        videoUploadInitRequest.put("pid", "");
        videoUploadInitRequest.put("coprightSite", "");
        videoUploadInitRequest.put("site", "");
        videoUploadInitRequest.put("disableType", "");
        videoUploadInitRequest.put("controlAreas", null);

        videoUploadInitRequest.put("tag", "defalut tag");
        videoUploadInitRequest.put("token", MD5Util.md5(TranscodeConstants.MMS_LESPORTS_PLATFORM + TranscodeConstants.MMS_LESPORTS_PRIVATE_KEY));

        return videoUploadInitRequest;
    }

    private Integer convertFormatNameToFormat(String formatName) {
        VideoCodeRateText videoCodeRateText = VideoCodeRateText.getCodeTextByText(formatName);
        if (null != videoCodeRateText) {
            return videoCodeRateText.getCodeRate();
        }
        return -1;
    }

    /**
     * fid转换成码率编号 转换srcRateType 为转码系统中对应的数字值
     * 2015-08-14 增加转码全景码率支持
     *
     * @param rate
     * @return
     */
    public static String convertRateTypeToConvertid(String rate, int panoramicView, int drmFlag) {
        String re = "";
        if (642003 == drmFlag) {
            // Cisco DRM支持打点转封装
            if ("flv_4k".equals(rate) && panoramicView == 1) {
                re = "242";
            } else if ("flv_2k".equals(rate) && panoramicView == 1) {
                re = "241";
            } else if ("flv_1080p3m".equals(rate) && panoramicView == 1) {
                re = "240";
            } else if ("flv_720p".equals(rate) && panoramicView == 1) {
                re = "239";
            } else if ("flv_1300".equals(rate) && panoramicView == 1) {
                re = "238";
            } else if ("flv_800".equals(rate) && panoramicView == 1) {
                re = "237";
            } else if ("flv_1300".equals(rate)) {
                re = "191";
            } else if ("flv_1000".equals(rate)) {
                re = "190";
            } else if ("flv_350".equals(rate)) {
                re = "189";
            } else if ("flv_720p".equals(rate)) {
                re = "192";
            } else if ("flv_1080p".equals(rate)) {
                re = "194";
            } else if ("flv_1080p3m".equals(rate)) {
                re = "193";
            } else if ("flv_2k".equals(rate)) {
                re = "196";
            } else if ("flv_4k".equals(rate)) {
                re = "197";
            } else if ("flv_4k_h265".equals(rate)) {
                re = "208";
            }
        } else {
            if ("flv_4k".equals(rate) && panoramicView == 1) {
                re = "167";
            } else if ("flv_2k".equals(rate) && panoramicView == 1) {
                re = "166";
            } else if ("flv_1080p3m".equals(rate) && panoramicView == 1) {
                re = "165";
            } else if ("flv_720p".equals(rate) && panoramicView == 1) {
                re = "164";
            } else if ("flv_1300".equals(rate) && panoramicView == 1) {
                re = "163";
            } else if ("flv_800".equals(rate) && panoramicView == 1) {
                re = "162";
            } else if ("flv_1300".equals(rate)) {
                re = "17";
            } else if ("flv_1000".equals(rate)||"flv_800".equals(rate)) {
                re = "16";
            } else if ("flv_350".equals(rate)) {
                re = "1";
            } else if ("flv_180".equals(rate)) {
                re = "58";
            } else if ("flv_720p".equals(rate)) {
                re = "18";
            } else if ("flv_1080p".equals(rate)) {
                re = "20";
            } else if ("flv_1080p3m".equals(rate)) {
                re = "35";
            } else if ("flv_2k".equals(rate)) {
                re = "137";
            } else if ("flv_4k".equals(rate)) {
                re = "54";
            } else if ("flv_4k_h265".equals(rate)) {
                re = "136";
            }
        }

        return re;
    }

    /**
     * fid转换成码率编号 转换destRateType 为转码系统中对应的数字值
     * 2015-08-14 增加转码全景码率支持，里面会存在重复码率对应值问题，转码系统已经做去重，暂时不做修改
     *
     * @param fid
     * @return
     */
    public static String convertFidToConvertid(String fid, int drmFlag) {
        String[] fids = fid.split(",");
        String re = "";
        if (642003 == drmFlag) {
            for (int i = 0; i < fids.length; i++) {
                if ("hls_4k_360".equals(fids[i])) {
                    re += TRANSCODE_BASECODE_360 + ",167,";
                } else if ("hls_2k_360".equals(fids[i])) {
                    re += TRANSCODE_BASECODE_360 + ",166,";
                } else if ("hls_1080p_360".equals(fids[i])) {
                    re += TRANSCODE_BASECODE_360 + ",165,";
                } else if ("hls_720p_360".equals(fids[i])) {
                    re += TRANSCODE_BASECODE_360 + ",164,";
                } else if ("hls_1300_360".equals(fids[i])) {
                    re += TRANSCODE_BASECODE_360 + ",163,";
                } else if ("hls_800_360".equals(fids[i])) {
                    re += TRANSCODE_BASECODE_360 + ",162,";
                } else if ("flv_1000".equals(fids[i])) {
                    re += "13,9,21,190,187,189,";
                } else if ("flv_1300".equals(fids[i])) {
                    re += "22,9,21,191,187,189,";
                } else if ("flv_720p".equals(fids[i])) {
                    re += "51,9,21,192,187,189,";
                } else if ("flv_1080p".equals(fids[i])) {
                    re += "53,9,21,194,187,189,";
                } else if ("flv_1080p3m".equals(fids[i])) {
                    re += "52,9,21,193,187,189,";
                } else if ("flv_350".equals(fids[i])) {
                    re += "9,21,187,189,";
                } else if ("flv_2k".equals(fids[i])) {
                    re += "9,21,187,189,137,196,";
                } else if ("flv_4k".equals(fids[i])) {
                    re += "9,21,187,189,54,197,";
                } else if ("flv_4k_h265".equals(fids[i])) {
                    re += "9,21,187,189,136,208,";
                }
            }
        } else {
            for (int i = 0; i < fids.length; i++) {
                if ("hls_4k_360".equals(fids[i])) {
                    re += TRANSCODE_BASECODE_360 + ",167,";
                } else if ("hls_2k_360".equals(fids[i])) {
                    re += TRANSCODE_BASECODE_360 + ",166,";
                } else if ("hls_1080p_360".equals(fids[i])) {
                    re += TRANSCODE_BASECODE_360 + ",165,";
                } else if ("hls_720p_360".equals(fids[i])) {
                    re += TRANSCODE_BASECODE_360 + ",164,";
                } else if ("hls_1300_360".equals(fids[i])) {
                    re += TRANSCODE_BASECODE_360 + ",163,";
                } else if ("hls_800_360".equals(fids[i])) {
                    re += TRANSCODE_BASECODE_360 + ",162,";
                } else if ("flv_1000".equals(fids[i])||"flv_800".equals(fids[i])) {
                    re += "13,9,21,";
                } else if ("flv_1300".equals(fids[i])) {
                    re += "22,9,21,";
                } else if ("flv_720p".equals(fids[i])) {
                    re += "51,9,21,";
                } else if ("flv_1080p".equals(fids[i])) {
                    re += "53,9,21,";
                } else if ("flv_1080p3m".equals(fids[i])) {
                    re += "52,9,21,";
                } else if ("flv_350".equals(fids[i])) {
                    re += "9,21,";
                } else if ("flv_180".equals(fids[i])) {
                    re += "58,";
                } else if ("flv_2k".equals(fids[i])) {
                    re += "9,21,137,";
                } else if ("flv_4k".equals(fids[i])) {
                    re += "9,21,54,";
                } else if ("flv_4k_h265".equals(fids[i])) {
                    re += "9,21,136,";
                }
            }
        }
        if (StringUtils.isNotEmpty(re) && re.contains(",")) {
            re = re.substring(0, re.lastIndexOf(","));
        }
        return re;
    }

    private VideoTranscodeRequest toLiveRequest(LiveToVideoSubTask task) {
        VideoTranscodeRequest request = new VideoTranscodeRequest();

        request.setUploadId(task.getUploadId());
        request.setTranscodeArgs(JSONObject.toJSONString(task.getTranscodeArgs()));

        return request;
    }

    private Map createTranscodeArgs(LiveToVideoTask task, LiveToVideoSubTask subTask) {
        Video video = QmtSbcVideoInternalApis.getVideoById(task.getVideoId());
        Map<String, Object> result = new HashMap<>();
        result.put("appKey", TranscodeConstants.TRANSCODE_APP_KEY);
        result.put("clientAddress", subTask.getRecordIp());
        result.put("multiType", "L|4");
        result.put("nodeId", subTask.getNodeId());
        result.put("uploadId", subTask.getUploadId());
        result.put("user", TranscodeConstants.VIDEO_TRANSCODE_USER);

        result.put("version", "livenew_normal_01");
        result.put("vid", task.getLeecoVid());
        result.put("mid", task.getLeecoMid());
//        result.put("liveId", task.());
        result.put("videoName", video.getTitle());
        result.put("channel", TranscodeConstants.MMS_LESPORTS_CATEGORY);
        result.put("srcCodeRate", convertRateTypeToConvertid(LeStringUtils.replace(subTask.getFormatName(), "mp4", "flv"),
                task.getPanoramicView(), task.getDrmFlag()));
        Map<String, Object> mediaMap = new HashMap<>();
        mediaMap.put("width", subTask.getWidth());
        mediaMap.put("video_FrameCount", subTask.getVideoFrameCount());
        mediaMap.put("video_FrameRate", subTask.getVideoFrameRate());
        mediaMap.put("general_Duration", subTask.getVideoDuration());
        mediaMap.put("height", subTask.getHeight());
        mediaMap.put("video_Format", subTask.getVideoFormat());
        result.put("srcMediaInfo", mediaMap);
        if (task.getType() == LiveToVideoTaskType.RECORDING) {
            result.put("src", subTask.getRecordDownloadUrl());
        } else {
            result.put("src", subTask.getDownloadUrl());
        }
//        result.put("subUrl", task.getSubUrl());
        Map<String, Object> transcodeInfo = new HashMap<>();
        transcodeInfo.put("transcodeType", 7);
        transcodeInfo.put("destCodeRate", convertFidToConvertid(LeStringUtils.replace(subTask.getFormatName(), "mp4", "flv"),
                task.getDrmFlag()));
        if ( StringUtils.isEmpty((String)transcodeInfo.get("destCodeRate"))) {
            LOGGER.error("No dest code rate : {}", JSONObject.toJSONString(subTask));
        }
//          --crop=0:100:1:1 --inpaint=10:5:100:100
        String transcodeParam = " --crop=0:0:0:0 ";
        transcodeInfo.put("transcodeParam", transcodeParam);

        Map<String, Object> logoConfig = new HashMap<>();
//        logoConfig.put("shotFrom", 1);
        logoConfig.put("logoType", 0);
        transcodeInfo.put("logoUrl", "");
        transcodeInfo.put("logoConfig", logoConfig);

        Map<String, Object> shotImgConfig = new HashMap<>();
        shotImgConfig.put("shotFrom", 1);
        shotImgConfig.put("isShot", 1);
//        shotImgConfig.put("withCodeRate", "21");
        transcodeInfo.put("shotImgConfig", shotImgConfig);

//        transcodeInfo.put("scheduleTags", Lists.newArrayList("-e", "-genbo"));
        transcodeInfo.put("scheduleTags", Lists.newArrayList());

        transcodeInfo.put("transcodeTags", Collections.emptyList());
        transcodeInfo.put("videoTags", Collections.emptyList());

        result.put("transcodeInfo", transcodeInfo);

        Calendar calendar = Calendar.getInstance();
        Long timestamp = calendar.getTimeInMillis();
        String sign = MD5Util.md5(TranscodeConstants.TRANSCODE_SECRET_KEY + video.getTitle()
                + timestamp + TranscodeConstants.TRANSCODE_SECRET_KEY);

        Map<String, Object> uploadConfig = new HashMap<>();
        uploadConfig.put("autotranscode", "1");
        uploadConfig.put("outkey", String.valueOf(task.getLeecoVid()));
        uploadConfig.put("from", "leSports");
        uploadConfig.put("sign", sign);
        uploadConfig.put("timestamp", timestamp);
        uploadConfig.put("type", "1");
        uploadConfig.put("uploadtype", "0");
//        uploadConfig.put("clientIp", subTask.getRecordIp());
        result.put("uploadConfig", uploadConfig);

        return result;
    }

    @Override
    public Page<LiveToVideoTask> getLiveToVideoTaskPage(String user, int pageNumber, int pageSize) {
        Pageable pageable = new PageRequest(pageNumber, pageSize, new Sort(new Sort.Order(Sort.Direction.DESC, "_id")));
        InternalQuery query = new InternalQuery().with(pageable);
        List<LiveToVideoTask> result = TranscodeInternalApis.getEntitiesByQuery(query, LiveToVideoTask.class);

        return new PageImpl<>(result, pageable, result.size());

    }

    @Override
    public boolean addliveToVideoTask(LiveToVideoTask task, AddVideoFormatParam videoSourceParam) {
        int f = LeNumberUtils.toInt(convertFormatNameToFormat(videoSourceParam.getFormat()));

        List<LiveToVideoSubTask> subTasks = TranscodeInternalApis.getLiveToVideoSubTasks(task.getId());
        if (!CollectionUtils.isEmpty(subTasks)) {
            for (LiveToVideoSubTask subTask : subTasks) {
                if (LeNumberUtils.toInt(subTask.getFormat()) == f) {
                    throw new RuntimeException("任务已存在，请勿重复添加！");
                }
            }
        }

        LiveToVideoSubTask subTask = new LiveToVideoSubTask();
        subTask.setFormat(f);
        subTask.setParentId(task.getId());
        subTask.setFormatName(videoSourceParam.getFormat());
        long id = TranscodeInternalApis.saveEntity(subTask, LiveToVideoSubTask.class);
        subTask.setId(id);
        createLiveRecording(task, Lists.newArrayList(subTask));
        task.setStatus(LiveToVideoTaskStatus.RECORDING);
        TranscodeInternalApis.saveEntity(task, LiveToVideoTask.class);
        return true;
    }

    @Override
    public List<ChannelIdNameEnameDTO> getLiveChannels() {
        try {
            return liveRoomQueryAPI.getIdNameEnameOfNormalChannel(urusAuth);
        } catch (UrusLiveRoomException e) {
            LOGGER.error("{}", e.getMessage(), e);
        }
        return null;
    }

    @Override
    public LiveToVideoTask updateVideoId(LiveToVideoTask task, long videoId) {
        task.setVideoId(videoId);
        TranscodeInternalApis.saveEntity(task, LiveToVideoTask.class);
        return task;
    }

    @Override
    public LiveChannelVo getChannelDetail(Long channel) {
        try {
            LiveChannelVo liveChannelVo = new LiveChannelVo();
            Channel channelDetail = liveRoomQueryAPI.getChannelById(urusAuth, channel);
            List<StreamDTO> streams = liveRoomQueryAPI.getStreamListByChannelId(urusAuth, LeNumberUtils.toLong(channel));
            liveChannelVo.setChannelDetail(channelDetail);
            liveChannelVo.setStreams(streams);
            String streamName = getMaxFormatName(streams);
            liveChannelVo.setStreamName(streamName);
            return liveChannelVo;
        } catch (UrusLiveRoomException e) {
            LOGGER.error("{}", e.getMessage(), e);
        }
        return null;
    }
}
