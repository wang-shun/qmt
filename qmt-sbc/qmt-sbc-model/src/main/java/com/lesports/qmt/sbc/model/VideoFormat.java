package com.lesports.qmt.sbc.model;

import com.lesports.qmt.sbc.api.common.TranscodeStatus;

import java.io.Serializable;

/**
 * User: ellios
 * Time: 16-9-10 : 下午6:19
 */
public class VideoFormat implements Serializable {
    private static final long serialVersionUID = 3296578588198873856L;

    //   视频码率, 如1. flv_350  16. flv_1000  13.mp4_800
    private Integer codeRate;
    //   源文件的 md5 唯一校验标志
    private String md5;
    //   文件大小
    private Long fileSize;
    private TranscodeStatus status;
    //时长
    private Long duration;
    //   分发后的CDN短地址
    private String storeUri;
    //   分发成功并上线的时间点
    private String publishAt;
    //描述
    private String desc;
    // 云转码具体码流的转码描述
    private String checkDesc;
    //furl,转码后成品地址
    private String dsturl;
    //
    private String fullpath;
    //size
    private Long size;

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public String getDsturl() {
        return dsturl;
    }

    public void setDsturl(String dsturl) {
        this.dsturl = dsturl;
    }

    public String getFullpath() {
        return fullpath;
    }

    public void setFullpath(String fullpath) {
        this.fullpath = fullpath;
    }

    public Integer getCodeRate() {
        return codeRate;
    }

    public void setCodeRate(Integer codeRate) {
        this.codeRate = codeRate;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
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

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public String getStoreUri() {
        return storeUri;
    }

    public void setStoreUri(String storeUri) {
        this.storeUri = storeUri;
    }

    public String getPublishAt() {
        return publishAt;
    }

    public void setPublishAt(String publishAt) {
        this.publishAt = publishAt;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getCheckDesc() {
        return checkDesc;
    }

    public void setCheckDesc(String checkDesc) {
        this.checkDesc = checkDesc;
    }
}
