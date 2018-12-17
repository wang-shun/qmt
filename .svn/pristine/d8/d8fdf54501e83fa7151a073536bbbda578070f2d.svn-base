package com.lesports.qmt.web.model;

//import com.lesports.sms.api.vo.TVideo;
//import com.lesports.sms.web.model.Share;

import com.lesports.qmt.sbc.api.dto.TVideo;

/**
* Created by gengchengliang on 2015/7/17.
*/
public class VideoWebView extends TVideo {
    //视频播放时长
    private String playTime;
    //创建时间的友好时间
    private String friendTime;
    //分享链接
    private Share share;

    public String getPlayTime() {
        return playTime;
    }

    public void setPlayTime(String playTime) {
        this.playTime = playTime;
    }

    public String getFriendTime() {
        return friendTime;
    }

    public void setFriendTime(String friendTime) {
        this.friendTime = friendTime;
    }

    public Share getShare() {
        return share;
    }

    public void setShare(Share share) {
        this.share = share;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Video{");
        sb.append("playTime='").append(playTime).append('\'');
        sb.append(", friendTime='").append(friendTime).append('\'');
        sb.append(", share=").append(share);
        sb.append('}');
        return sb.toString();
    }
}
