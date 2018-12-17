package com.lesports.qmt.transcode.web.param;

/**
 * Created by pangchuanxiao on 2016/10/21.
 */
public class LiveTaskParam {

    //id 直播频道id
    private Long channelId;
    //码率名称
    private String streamName;
    //任务名称
    private String taskName;
    //录制 转封装
    private Integer type;
    //转码开始时间
    private String transcodeStartTime;
    //转码结束时间
    private String transcodeEndTime;

    public Long getChannelId() {
        return channelId;
    }

    public void setChannelId(Long channelId) {
        this.channelId = channelId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
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

    public String getStreamName() {
        return streamName;
    }

    public void setStreamName(String streamName) {
        this.streamName = streamName;
    }

    public void setTranscodeEndTime(String transcodeEndTime) {
        this.transcodeEndTime = transcodeEndTime;
    }
}
