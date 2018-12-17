package com.lesports.qmt.sbc.vo;

import com.lesports.qmt.mvc.QmtVo;
import com.lesports.utils.LeDateUtils;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * 选集
 * Created by denghui on 2016/11/27.
 */
public class ProgramAlbumPeriodVo implements QmtVo {

    private static final long serialVersionUID = -671653455824556994L;
    private Long id;
    //选集名称
    private String name;
    //所属自制专辑id
    private Long aid;
    //期数
    private String period;
    //频道
    @Field("channel_id")
    private Long channelId;
    //子频道 当频道为综合体育时可选
    @Field("sub_channel_id")
    private Long subChannelId;
    //赛事id
    private Long cid;
    //播出时间
    @Field("start_time")
    private String startTime;
    //关联的视频
    private SimpleVideoVo video;
    //发布(上线/下线)
    private Boolean online;

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

    public Long getAid() {
        return aid;
    }

    public void setAid(Long aid) {
        this.aid = aid;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public Long getChannelId() {
        return channelId;
    }

    public void setChannelId(Long channelId) {
        this.channelId = channelId;
    }

    public Long getSubChannelId() {
        return subChannelId;
    }

    public void setSubChannelId(Long subChannelId) {
        this.subChannelId = subChannelId;
    }

    public Long getCid() {
        return cid;
    }

    public void setCid(Long cid) {
        this.cid = cid;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public SimpleVideoVo getVideo() {
        return video;
    }

    public void setVideo(SimpleVideoVo video) {
        this.video = video;
    }

    public Boolean getOnline() {
        return online;
    }

    public void setOnline(Boolean online) {
        this.online = online;
    }

    @Override
    public ProgramAlbumPeriodVo pretty() {
        this.setStartTime(LeDateUtils.tansYYYYMMDDHHMMSSPretty(this.getStartTime()));
        return (ProgramAlbumPeriodVo) QmtVo.super.pretty();
    }

    public static class SimpleVideoVo implements QmtVo {

        private static final long serialVersionUID = 3535064313234797858L;
        private Long id;
        private String name;

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
    }
}
