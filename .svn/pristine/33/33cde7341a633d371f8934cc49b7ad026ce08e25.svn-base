package com.lesports.qmt.op.web.api.core.vo;


import com.google.common.collect.Lists;
import com.lesports.qmt.sbc.api.common.VideoContentType;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by gengchengliang on 2015/6/23.
 */
public class DetailEpisodeVo extends HallEpisodeVo implements Serializable {

	//直播信息
	private List<Lives> lives = Lists.newArrayList();
	private List<SimpleVideo> videos = Lists.newArrayList();
	//新英付费url
	private String xinyingUrl;//新英播放URL


    public class SimpleVideo{
        private Long vid;
        private String name;
        private VideoContentType type;
        //默认图片
        private String imageUrl;

        public Long getVid() {
            return vid;
        }

        public void setVid(Long vid) {
            this.vid = vid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public VideoContentType getType() {
            return type;
        }

        public void setType(VideoContentType type) {
            this.type = type;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("SimpleVideo{");
            sb.append("vid=").append(vid);
            sb.append(", name='").append(name).append('\'');
            sb.append(", type=").append(type);
            sb.append(", imageUrl='").append(imageUrl).append('\'');
            sb.append('}');
            return sb.toString();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof SimpleVideo)) return false;

            SimpleVideo that = (SimpleVideo) o;

            if (vid != null ? !vid.equals(that.vid) : that.vid != null) return false;

            return true;
        }

        @Override
        public int hashCode() {
            return vid != null ? vid.hashCode() : 0;
        }
    }

	public class Lives {
		private String liveId;
		private String name;
		private Integer liveStatus;
		private Map<String, String> images;

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

		@Override
		public String toString() {
			return "Lives{" +
					"liveId='" + liveId + '\'' +
					", name='" + name + '\'' +
					", liveStatus=" + liveStatus +
					", images=" + images +
					'}';
		}
	}

	public List<Lives> getLives() {
		return lives;
	}

	public void setLives(List<Lives> lives) {
		this.lives = lives;
	}

    public List<SimpleVideo> getVideos() {
        return videos;
    }

    public void setVideos(List<SimpleVideo> videos) {
        this.videos = videos;
    }

    public String getXinyingUrl() {
        return xinyingUrl;
    }

    public void setXinyingUrl(String xinyingUrl) {
        this.xinyingUrl = xinyingUrl;
    }

    @Override
    public String toString() {
        return "DetailEpisodeVo{" +
                "lives=" + lives +
                ", videos=" + videos +
                ", xinyingUrl='" + xinyingUrl + '\'' +
                '}';
    }
}
