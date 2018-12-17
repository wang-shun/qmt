package com.lesports.qmt.transcode.model;

import com.lesports.qmt.model.support.QmtModel;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Map;

/**
 * Created by pangchuanxiao on 2016/10/21.
 */
@Document(collection = "video_transcode_tasks")
public class VideoTranscodeTask extends QmtModel<Long> {
    private static final long serialVersionUID = -2000729691801045518L;
    //文件md5
    private String md5;
    //是否分片
    private Boolean slice;
    //操作人用户名
    private String user;
    //任务来源
    private String from;
    //初始化完成后返回token
    private String token;
    //文件大小
    private Long fileSize;
    //屏幕尺寸
    private String screenSize;
    //文件时长
    private Integer duration;
    //屏幕比例
    private String screenRatio;
    //上传地址
    private String uploadUrl;
    //上传id
    private Long uploadId;
    //上传节点id
    private Integer nodeId;
    //是否使用字幕
    private Boolean useSub;
    //字幕文件地址
    private String subUrl;
    //字幕文件名称
    private String subFileName;
    //上传成功后的片源地址
    private String srcUrl;
    //视频本地名称
    private String localName;
    //关联的视频id
    private Long videoId;
    //源文件宽高比
    private String aspectRatio;
    //媒资vid
    private Long leecoVid;
    //媒资mid
    private Long leecoMid;
    //转码优先级
    private Integer priority;
    //转码开始时间
    private String transcodeStartTime;
    //转码结束时间
    private String transcodeEndTime;
    //是否自动遮黑边
    private Boolean autoBlackSide;
    //修正宽高比
    private String modifiedAspectRadio;
    //子任务
    private List<VideoTranscodeSubTask> subTasks;
    //转码参数
    private Map transcodeArgs;
    //转码状态
    private VideoTranscodeTaskStatus status;
    //是否重传
    private Boolean isReUpload;
    //是否删除
    private Boolean deleted;
    //视频转码 加急
    private Boolean isEnhance = true;
    //是否推送到首页
    private Boolean recommend2Homepage;
    //转码完成时间
    private String transcodeFinishTime;

    public String getTranscodeFinishTime() {
        return transcodeFinishTime;
    }

    public void setTranscodeFinishTime(String transcodeFinishTime) {
        this.transcodeFinishTime = transcodeFinishTime;
    }

    public Boolean getUseSub() {
        return useSub;
    }

    public void setUseSub(Boolean useSub) {
        this.useSub = useSub;
    }

    public String getSubFileName() {
        return subFileName;
    }

    public void setSubFileName(String subFileName) {
        this.subFileName = subFileName;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public Boolean getSlice() {
        return slice;
    }

    public void setSlice(Boolean slice) {
        this.slice = slice;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public String getScreenSize() {
        return screenSize;
    }

    public void setScreenSize(String screenSize) {
        this.screenSize = screenSize;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getScreenRatio() {
        return screenRatio;
    }

    public void setScreenRatio(String screenRatio) {
        this.screenRatio = screenRatio;
    }

    public String getUploadUrl() {
        return uploadUrl;
    }

    public void setUploadUrl(String uploadUrl) {
        this.uploadUrl = uploadUrl;
    }

    public Long getUploadId() {
        return uploadId;
    }

    public void setUploadId(Long uploadId) {
        this.uploadId = uploadId;
    }

    public Integer getNodeId() {
        return nodeId;
    }

    public void setNodeId(Integer nodeId) {
        this.nodeId = nodeId;
    }

    public String getSubUrl() {
        return subUrl;
    }

    public void setSubUrl(String subUrl) {
        this.subUrl = subUrl;
    }

    public String getSrcUrl() {
        return srcUrl;
    }

    public void setSrcUrl(String srcUrl) {
        this.srcUrl = srcUrl;
    }

    public String getLocalName() {
        return localName;
    }

    public void setLocalName(String localName) {
        this.localName = localName;
    }

    public Long getVideoId() {
        return videoId;
    }

    public void setVideoId(Long videoId) {
        this.videoId = videoId;
    }

    public String getAspectRatio() {
        return aspectRatio;
    }

    public void setAspectRatio(String aspectRatio) {
        this.aspectRatio = aspectRatio;
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

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
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

    public Boolean getAutoBlackSide() {
        return autoBlackSide;
    }

    public void setAutoBlackSide(Boolean autoBlackSide) {
        this.autoBlackSide = autoBlackSide;
    }

    public String getModifiedAspectRadio() {
        return modifiedAspectRadio;
    }

    public void setModifiedAspectRadio(String modifiedAspectRadio) {
        this.modifiedAspectRadio = modifiedAspectRadio;
    }

    public List<VideoTranscodeSubTask> getSubTasks() {
        return subTasks;
    }

    public void setSubTasks(List<VideoTranscodeSubTask> subTasks) {
        this.subTasks = subTasks;
    }

    public Map getTranscodeArgs() {
        return transcodeArgs;
    }

    public void setTranscodeArgs(Map transcodeArgs) {
        this.transcodeArgs = transcodeArgs;
    }

    public VideoTranscodeTaskStatus getStatus() {
        return status;
    }

    public void setStatus(VideoTranscodeTaskStatus status) {
        this.status = status;
    }

    public Boolean getIsReUpload() {
        return isReUpload;
    }

    public void setIsReUpload(Boolean isReUpload) {
        this.isReUpload = isReUpload;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Boolean getIsEnhance() {
        return isEnhance;
    }

    public void setIsEnhance(Boolean isEnhance) {
        this.isEnhance = isEnhance;
    }

    public Boolean getRecommend2Homepage() {
        return recommend2Homepage;
    }

    public void setRecommend2Homepage(Boolean recommend2Homepage) {
        this.recommend2Homepage = recommend2Homepage;
    }
}
