package com.lesports.qmt.web.api.core.vo;

import com.google.common.collect.Lists;
import com.lesports.qmt.tlive.api.common.TextLiveType;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by gengchengliang on 2015/6/23.
 */
public class DetailEpisodeVo extends HallEpisodeVo implements Serializable {
    private static final long serialVersionUID = 668858634598542927L;

    //章鱼猜球的赛程id
    private Long octopusMatchId;
    //直播信息
    private List<Lives> lives = Lists.newArrayList();
    //录播id
    private Long recordedId;
    //集锦id
    private Long highlightsId;
    //战报id
    private Long reportId;
    //赛程相关的视频集合
    private Set<Long> videoIds;
    //新英付费url
    private String xinyingUrl;//新英播放URL
    //图文直播
    private List<TextLive> textLives = Lists.newArrayList();
    //聊天室id
    private String chatRoomId;
    //比赛场馆
    private String stadium;
    //比赛天气
    private String weather;
    //比赛主裁判
    private String judge;
	//直播场次id
    private String liveUniqueId;
	//录播是否付费
	private Integer recordedPay = 0;
	//点播是否付费
	private Integer highlightsPay = 0;

    public class Lives implements Serializable {
		private static final long serialVersionUID = 668858634598592927L;
        private String liveId;
        private String chatRoomId;
        private String name;
        private Integer liveStatus;
        private Integer drmFlag;
        private Map<String, String> images;
        private Integer isPay;//是否付费
        private String viewPic;

        public Integer getIsPay() {
            return isPay;
        }

        public void setIsPay(Integer isPay) {
            this.isPay = isPay;
        }

        public String getLiveId() {
            return liveId;
        }

        public void setLiveId(String liveId) {
            this.liveId = liveId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getLiveStatus() {
            return liveStatus;
        }

        public void setLiveStatus(Integer liveStatus) {
            this.liveStatus = liveStatus;
        }

        public Map<String, String> getImages() {
            return images;
        }

        public void setImages(Map<String, String> images) {
            this.images = images;
        }

        public String getChatRoomId() {
            return chatRoomId;
        }

        public void setChatRoomId(String chatRoomId) {
            this.chatRoomId = chatRoomId;
        }

		public Integer getDrmFlag() {
			return drmFlag;
		}

		public void setDrmFlag(Integer drmFlag) {
			this.drmFlag = drmFlag;
		}

        public String getViewPic() {
            return viewPic;
        }

        public void setViewPic(String viewPic) {
            this.viewPic = viewPic;
        }

        @Override
		public String toString() {
			final StringBuffer sb = new StringBuffer("Lives{");
			sb.append("liveId='").append(liveId).append('\'');
			sb.append(", chatRoomId='").append(chatRoomId).append('\'');
			sb.append(", name='").append(name).append('\'');
			sb.append(", liveStatus=").append(liveStatus);
			sb.append(", drmFlag=").append(drmFlag);
			sb.append(", images=").append(images);
			sb.append(", isPay=").append(isPay);
            sb.append(", viewPic=").append(viewPic);
			sb.append('}');
			return sb.toString();
		}
	}

	public String getLiveUniqueId() {
		return liveUniqueId;
	}

	public void setLiveUniqueId(String liveUniqueId) {
		this.liveUniqueId = liveUniqueId;
	}

	public Long getOctopusMatchId() {
        return octopusMatchId;
    }

    public void setOctopusMatchId(Long octopusMatchId) {
        this.octopusMatchId = octopusMatchId;
    }

    public List<Lives> getLives() {
        return lives;
    }

    public void setLives(List<Lives> lives) {
        this.lives = lives;
    }

    public Long getRecordedId() {
        return recordedId;
    }

    public void setRecordedId(Long recordedId) {
        this.recordedId = recordedId;
    }

    public Long getHighlightsId() {
        return highlightsId;
    }

    public void setHighlightsId(Long highlightsId) {
        this.highlightsId = highlightsId;
    }

    public Long getReportId() {
        return reportId;
    }

    public void setReportId(Long reportId) {
        this.reportId = reportId;
    }

    public Set<Long> getVideoIds() {
        return videoIds;
    }

    public void setVideoIds(Set<Long> videoIds) {
        this.videoIds = videoIds;
    }

    public String getXinyingUrl() {
        return xinyingUrl;
    }

    public void setXinyingUrl(String xinyingUrl) {
        this.xinyingUrl = xinyingUrl;
    }

    public String getChatRoomId() {
        return chatRoomId;
    }

    public void setChatRoomId(String chatRoomId) {
        this.chatRoomId = chatRoomId;
    }

    public String getStadium() {
        return stadium;
    }

    public void setStadium(String stadium) {
        this.stadium = stadium;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public String getJudge() {
        return judge;
    }

    public void setJudge(String judge) {
        this.judge = judge;
    }

	public Integer getRecordedPay() {
		return recordedPay;
	}

	public void setRecordedPay(Integer recordedPay) {
		this.recordedPay = recordedPay;
	}

	public Integer getHighlightsPay() {
		return highlightsPay;
	}

	public void setHighlightsPay(Integer highlightsPay) {
		this.highlightsPay = highlightsPay;
	}

	@Override
	public String toString() {
		final StringBuffer sb = new StringBuffer("DetailEpisodeVo{");
		sb.append("octopusMatchId=").append(octopusMatchId);
		sb.append(", lives=").append(lives);
		sb.append(", recordedId=").append(recordedId);
		sb.append(", highlightsId=").append(highlightsId);
		sb.append(", reportId=").append(reportId);
		sb.append(", videoIds=").append(videoIds);
		sb.append(", xinyingUrl='").append(xinyingUrl).append('\'');
		sb.append(", textLives=").append(textLives);
		sb.append(", chatRoomId='").append(chatRoomId).append('\'');
		sb.append(", stadium='").append(stadium).append('\'');
		sb.append(", weather='").append(weather).append('\'');
		sb.append(", judge='").append(judge).append('\'');
		sb.append(", liveUniqueId='").append(liveUniqueId).append('\'');
		sb.append(", recordedPay=").append(recordedPay);
		sb.append(", highlightsPay=").append(highlightsPay);
		sb.append('}');
		return sb.toString();
	}

	public class TextLive implements Serializable {
		private static final long serialVersionUID = 668858634598592922L;
        //图文直播id
        private Long textLiveId;
        //图文直播类型
        private TextLiveType type;

        public Long getTextLiveId() {
            return textLiveId;
        }

        public void setTextLiveId(Long textLiveId) {
            this.textLiveId = textLiveId;
        }

        public TextLiveType getType() {
            return type;
        }

        public void setType(TextLiveType type) {
            this.type = type;
        }

        @Override
        public String toString() {
            return "TextLive{" +
                    "textLiveId=" + textLiveId +
                    ", type=" + type +
                    '}';
        }
    }

    public List<TextLive> getTextLives() {
        return textLives;
    }

    public void setTextLives(List<TextLive> textLives) {
        this.textLives = textLives;
    }
}
