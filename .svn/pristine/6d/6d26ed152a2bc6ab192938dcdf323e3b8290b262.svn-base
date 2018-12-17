package com.lesports.qmt.transcode.model;

import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;

/**
 * Created by pangchuanxiao on 2016/10/21.
 */
public class VideoTranscodeSubTask implements Serializable {
    private static final long serialVersionUID = -2000729691801045518L;

    private Integer format;
    @Field("format_name")
    private String formatName;
    private VideoTranscodeTaskStatus status;
    private String progress;
    //创建日期
    @Field("create_at")
    protected String createAt;
    @Field("parent_id")
    private Long parentId;

    public String getProgress() {
        return progress;
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }

    public Integer getFormat() {
        return format;
    }

    public void setFormat(Integer format) {
        this.format = format;
    }

    public String getFormatName() {
        return formatName;
    }

    public void setFormatName(String formatName) {
        this.formatName = formatName;
    }

    public VideoTranscodeTaskStatus getStatus() {
        return status;
    }

    public void setStatus(VideoTranscodeTaskStatus status) {
        this.status = status;
    }

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }
}
