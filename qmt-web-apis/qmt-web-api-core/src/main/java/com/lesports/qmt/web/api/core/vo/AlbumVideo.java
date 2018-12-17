package com.lesports.qmt.web.api.core.vo;


import com.lesports.qmt.web.model.Share;

import java.io.Serializable;

/**
* Created by gengchengliang on 2015/8/4.
*/
public class AlbumVideo extends VideoVo implements Serializable {
	//视频播放时长
	private String playTime;
	//创建时间的友好时间
	private String friendTime;
	//分享
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
		return "AlbumVideo{" +
				"playTime='" + playTime + '\'' +
				", friendTime='" + friendTime + '\'' +
				", share=" + share +
				'}';
	}
}
