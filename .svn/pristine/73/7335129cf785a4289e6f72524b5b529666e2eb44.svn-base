package com.lesports.qmt.sbc.param;

import com.lesports.qmt.QmtConstants;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * Created by denghui on 2016/11/15.
 */
public class SavePeriodParam implements Serializable {
    private static final long serialVersionUID = -3887493769303263926L;

    private Long id;
    //名称
    @NotBlank(message = "name is required")
    private String name;
    //期数
    private String period;
    //赛事
    private Long cid;
    //频道id
    @NotNull(message = "channel id is required")
    private Long channelId;
    //子频道
    private Long subChannelId;
    //开始时间
    @Pattern(regexp = QmtConstants.REGEX_YMDHMS, message = "invalid date time format")
    private String startTime;
    //关联视频
    private String video;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public Long getCid() {
        return cid;
    }

    public void setCid(Long cid) {
        this.cid = cid;
    }

    public Long getChannelId() {
        return channelId;
    }

    public void setChannelId(Long channelId) {
        this.channelId = channelId;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public Long getSubChannelId() {
        return subChannelId;
    }

    public void setSubChannelId(Long subChannelId) {
        this.subChannelId = subChannelId;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}
