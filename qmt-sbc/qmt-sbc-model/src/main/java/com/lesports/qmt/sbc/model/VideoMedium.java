package com.lesports.qmt.sbc.model;

import com.lesports.qmt.model.support.QmtModel;
import com.lesports.qmt.sbc.api.common.TranscodeStatus;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * 视频介质
 * User: ellios
 * Time: 16-9-10 : 下午6:13
 */
@Document(collection = "video_mediums")
public class VideoMedium extends QmtModel<Long> {

    private static final long serialVersionUID = 3296578588198883856L;

    //名称
    private String name;
    //描述
    private String desc;
    //源文件的md5唯一校验标志
    private String md5;
    // 上传到服务器的时间点
    private String uploadAt;
    // 分发成功并上线的时间点
    private String publishAt;
    // 视频所包含的码率, 如1. flv_350 16. flv_1000 13.mp4_800
    private String bitRate;
    // 视频时长
    private Long duration;
    // 文件大小
    private Long fileSize;
    //状态
    private TranscodeStatus status;
    // 默认0，1是重传视频
    private Integer flag;
    //乐视网mid
    private Long leecoMid;
    //副标题
    private String subtitle;
    //视频码流
    private List<VideoFormat> formats = new ArrayList<>();
    //我们的vid
    private Long videoId;

    public Long getLeecoMid() {
        return leecoMid;
    }

    public void setLeecoMid(Long leecoMid) {
        this.leecoMid = leecoMid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public String getUploadAt() {
        return uploadAt;
    }

    public void setUploadAt(String uploadAt) {
        this.uploadAt = uploadAt;
    }

    public String getPublishAt() {
        return publishAt;
    }

    public void setPublishAt(String publishAt) {
        this.publishAt = publishAt;
    }

    public String getBitRate() {
        return bitRate;
    }

    public void setBitRate(String bitRate) {
        this.bitRate = bitRate;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public TranscodeStatus getStatus() {
        return status;
    }

    public void setStatus(TranscodeStatus status) {
        this.status = status;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public List<VideoFormat> getFormats() {
        return formats;
    }

    public void setFormats(List<VideoFormat> formats) {
        this.formats = formats;
    }

    public Long getVideoId() {
        return videoId;
    }

    public void setVideoId(Long videoId) {
        this.videoId = videoId;
    }
}
