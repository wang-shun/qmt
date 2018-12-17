package com.lesports.qmt.transcode.model;

import com.lesports.qmt.model.support.QmtModel;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Map;

/**
 * Created by pangchuanxiao on 2016/11/3.
 * <p>
 * http://wiki.letv.cn/pages/viewpage.action?pageId=60097258
 */
@Document(collection = "live_to_video_sub_tasks")
public class LiveToVideoSubTask extends QmtModel<Long> {
    private static final long serialVersionUID = -2000729691801045518L;
    @Field("parent_id")
    private Long parentId;
    private LiveToVideoTaskStatus taskStatus;
    @Transient
    private String status;
    @Transient
    private Long subtaskId;
    private String downloadUrl;
    private String appkey;
    private Long timestamp;
    private String recordIp;
    private String recordDownloadUrl;
    private String recordtime;
    private String uploadtime;
    private String filemd5;
    private String width;
    private String height;
    private String videoFrameCount;
    private String videoFrameRate;
    private String videoDuration;
    //视频格式mpegts
    private String videoFormat;
    private Integer format;
    private String size;
    private Integer nodeId;
    private Long uploadId;
    private String errorCode;
    private String errorMsg;
    private String streamName;
    private String formatName;
    private Map transcodeArgs;
    private String progress;

    public String getProgress() {
        return progress;
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }

    public Map getTranscodeArgs() {
        return transcodeArgs;
    }

    public void setTranscodeArgs(Map transcodeArgs) {
        this.transcodeArgs = transcodeArgs;
    }

    public String getFormatName() {
        return formatName;
    }

    public void setFormatName(String formatName) {
        this.formatName = formatName;
    }

    public String getStreamName() {
        return streamName;
    }

    public void setStreamName(String streamName) {
        this.streamName = streamName;
    }

    public LiveToVideoTaskStatus getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(LiveToVideoTaskStatus taskStatus) {
        this.taskStatus = taskStatus;
    }

    public Integer getFormat() {
        return format;
    }

    public void setFormat(Integer format) {
        this.format = format;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getSubtaskId() {
        return subtaskId;
    }

    public void setSubtaskId(Long subtaskId) {
        this.subtaskId = subtaskId;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getAppkey() {
        return appkey;
    }

    public void setAppkey(String appkey) {
        this.appkey = appkey;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getRecordIp() {
        return recordIp;
    }

    public void setRecordIp(String recordIp) {
        this.recordIp = recordIp;
    }

    public String getRecordDownloadUrl() {
        return recordDownloadUrl;
    }

    public void setRecordDownloadUrl(String recordDownloadUrl) {
        this.recordDownloadUrl = recordDownloadUrl;
    }

    public String getRecordtime() {
        return recordtime;
    }

    public void setRecordtime(String recordtime) {
        this.recordtime = recordtime;
    }

    public String getUploadtime() {
        return uploadtime;
    }

    public void setUploadtime(String uploadtime) {
        this.uploadtime = uploadtime;
    }

    public String getFilemd5() {
        return filemd5;
    }

    public void setFilemd5(String filemd5) {
        this.filemd5 = filemd5;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getVideoFrameCount() {
        return videoFrameCount;
    }

    public void setVideoFrameCount(String videoFrameCount) {
        this.videoFrameCount = videoFrameCount;
    }

    public String getVideoFrameRate() {
        return videoFrameRate;
    }

    public void setVideoFrameRate(String videoFrameRate) {
        this.videoFrameRate = videoFrameRate;
    }

    public String getVideoDuration() {
        return videoDuration;
    }

    public void setVideoDuration(String videoDuration) {
        this.videoDuration = videoDuration;
    }

    public String getVideoFormat() {
        return videoFormat;
    }

    public void setVideoFormat(String videoFormat) {
        this.videoFormat = videoFormat;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }


    public Integer getNodeId() {
        return nodeId;
    }

    public void setNodeId(Integer nodeId) {
        this.nodeId = nodeId;
    }

    public Long getUploadId() {
        return uploadId;
    }

    public void setUploadId(Long uploadId) {
        this.uploadId = uploadId;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

}
