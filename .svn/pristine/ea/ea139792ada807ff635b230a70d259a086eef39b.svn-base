package com.lesports.qmt.transcode.model;

import com.lesports.qmt.model.support.QmtModel;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * trunk.
 *
 * @author pangchuanxiao
 * @since 2016/10/18
 */
@Document(collection = "live_to_video_tasks")
public class LiveToVideoTask extends QmtModel<Long> {
    private static final long serialVersionUID = -2000729691801045518L;

    //id 直播频道id
    private Long channelId;
    private String taskName;
    private String streamName;
    private LiveToVideoTaskType type;
    //转码开始时间
    private String transcodeStartTime;
    //转码结束时间
    private String transcodeEndTime;
    private LiveToVideoTaskStatus status;
    private Long videoId;
    //媒资vid
    private Long leecoVid;
    //媒资mid
    private Long leecoMid;
    private List<String> formatNames;
    private RecordingType recordingType;
    private String subUrl;
    private Integer drmFlag;
    //全景
    private Integer panoramicView;
    //是否删除
    private Boolean deleted;
    //是否加急
    private Boolean isEnhance;
    //下载地址
    private String downloadUrl;
    //修正宽高比
    private String modifiedAspectRadio;

    public String getModifiedAspectRadio() {
        return modifiedAspectRadio;
    }

    public void setModifiedAspectRadio(String modifiedAspectRadio) {
        this.modifiedAspectRadio = modifiedAspectRadio;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public Boolean getIsEnhance() {
        return isEnhance;
    }

    public void setIsEnhance(Boolean isEnhance) {
        this.isEnhance = isEnhance;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Integer getPanoramicView() {
        return panoramicView;
    }

    public void setPanoramicView(Integer panoramicView) {
        this.panoramicView = panoramicView;
    }

    public Integer getDrmFlag() {
        return drmFlag;
    }

    public void setDrmFlag(Integer drmFlag) {
        this.drmFlag = drmFlag;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public Long getChannelId() {
        return channelId;
    }

    public void setChannelId(Long channelId) {
        this.channelId = channelId;
    }

    public String getStreamName() {
        return streamName;
    }

    public void setStreamName(String streamName) {
        this.streamName = streamName;
    }

    public LiveToVideoTaskType getType() {
        return type;
    }

    public void setType(LiveToVideoTaskType type) {
        this.type = type;
    }

    public String getTranscodeStartTime() {
        return transcodeStartTime;
    }

    public void setTranscodeStartTime(String transcodeStartTime) {
        this.transcodeStartTime = transcodeStartTime;
    }

    public String getTranscodeEndTime() {
        return transcodeEndTime;
    }

    public void setTranscodeEndTime(String transcodeEndTime) {
        this.transcodeEndTime = transcodeEndTime;
    }

    public LiveToVideoTaskStatus getStatus() {
        return status;
    }

    public void setStatus(LiveToVideoTaskStatus status) {
        this.status = status;
    }

    public Long getVideoId() {
        return videoId;
    }

    public void setVideoId(Long videoId) {
        this.videoId = videoId;
    }

    public Long getLeecoVid() {
        return leecoVid;
    }

    public void setLeecoVid(Long leecoVid) {
        this.leecoVid = leecoVid;
    }

    public Long getLeecoMid() {
        return leecoMid;
    }

    public void setLeecoMid(Long leecoMid) {
        this.leecoMid = leecoMid;
    }

    public List<String> getFormatNames() {
        return formatNames;
    }

    public void setFormatNames(List<String> formatNames) {
        this.formatNames = formatNames;
    }

    public RecordingType getRecordingType() {
        return recordingType;
    }

    public void setRecordingType(RecordingType recordingType) {
        this.recordingType = recordingType;
    }

    public String getSubUrl() {
        return subUrl;
    }

    public void setSubUrl(String subUrl) {
        this.subUrl = subUrl;
    }
}
