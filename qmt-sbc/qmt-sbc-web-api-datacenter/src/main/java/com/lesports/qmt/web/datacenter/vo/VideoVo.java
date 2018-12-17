package com.lesports.qmt.web.datacenter.vo;

import com.google.common.collect.Maps;
import com.lesports.qmt.sbc.api.common.VideoContentType;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by denghui on 2016/12/18.
 */
public class VideoVo extends ContentBaseVo{
    //视频类型
    private VideoContentType videoContentType;
    //播放时长
    private String duration;
    //是否付费
    private Integer isPay = 0;

    public VideoContentType getVideoContentType() {
        return videoContentType;
    }

    public void setVideoContentType(VideoContentType videoContentType) {
        this.videoContentType = videoContentType;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public Integer getIsPay() {
        return isPay;
    }

    public void setIsPay(Integer isPay) {
        this.isPay = isPay;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
