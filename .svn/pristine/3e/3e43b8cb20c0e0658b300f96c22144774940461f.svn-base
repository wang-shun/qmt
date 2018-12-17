package com.lesports.qmt.web.api.core.vo;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * lesports-projects
 *
 * @author pangchuanxiao
 * @since 15-6-27
 */
public class TopicInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	private long id;
	private String title;
	private int channelId;
	private String desc;
	private String mobileFocusPic;
	private String shareThumbnail;
	private String mSiteUrl;
	private List<VideoInfo> videos;
	//创建时间
	private String createTime;
	//更新时间
	private String updateTime;
	//评论id
	private String commentId;
	//图片集合 - 因为app那边把原来的展现形式改了,所以需要添加960*540的图
	private Map<String, String> imageUrl;

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getChannelId() {
		return channelId;
	}

	public void setChannelId(int channelId) {
		this.channelId = channelId;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getMobileFocusPic() {
		return mobileFocusPic;
	}

	public void setMobileFocusPic(String mobileFocusPic) {
		this.mobileFocusPic = mobileFocusPic;
	}

	public String getShareThumbnail() {
		return shareThumbnail;
	}

	public void setShareThumbnail(String shareThumbnail) {
		this.shareThumbnail = shareThumbnail;
	}

	public String getmSiteUrl() {
		return mSiteUrl;
	}

	public void setmSiteUrl(String mSiteUrl) {
		this.mSiteUrl = mSiteUrl;
	}

	public List<VideoInfo> getVideos() {
		return videos;
	}

	public void setVideos(List<VideoInfo> videos) {
		this.videos = videos;
	}

	public String getCommentId() {
		return commentId;
	}

	public void setCommentId(String commentId) {
		this.commentId = commentId;
	}

	public Map<String, String> getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(Map<String, String> imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	@Override
	public String toString() {
		final StringBuffer sb = new StringBuffer("TopicInfo{");
		sb.append("id=").append(id);
		sb.append(", title='").append(title).append('\'');
		sb.append(", channelId=").append(channelId);
		sb.append(", desc='").append(desc).append('\'');
		sb.append(", mobileFocusPic='").append(mobileFocusPic).append('\'');
		sb.append(", shareThumbnail='").append(shareThumbnail).append('\'');
		sb.append(", mSiteUrl='").append(mSiteUrl).append('\'');
		sb.append(", videos=").append(videos);
		sb.append(", createTime='").append(createTime).append('\'');
		sb.append(", updateTime='").append(updateTime).append('\'');
		sb.append(", commentId='").append(commentId).append('\'');
		sb.append(", imageUrl=").append(imageUrl);
		sb.append('}');
		return sb.toString();
	}

	public static class VideoInfo implements Serializable {
		private static final long serialVersionUID = 1L;
		//id
		private long id;
		//视频名称
		private String name;
		//图片地址
		private Map<String, String> imageUrl;
		//影片时长
		private int duration;
		//上线时间
		private String releaseDate;
		//创建时间
		private String createTime;
		//更新时间
		private String updateTime;

		public long getId() {
			return id;
		}

		public void setId(long id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getDuration() {
			return duration;
		}

		public void setDuration(int duration) {
			this.duration = duration;
		}

		public String getReleaseDate() {
			return releaseDate;
		}

		public void setReleaseDate(String releaseDate) {
			this.releaseDate = releaseDate;
		}

		public Map<String, String> getImageUrl() {
			return imageUrl;
		}

		public void setImageUrl(Map<String, String> imageUrl) {
			this.imageUrl = imageUrl;
		}

		public String getCreateTime() {
			return createTime;
		}

		public void setCreateTime(String createTime) {
			this.createTime = createTime;
		}

		public String getUpdateTime() {
			return updateTime;
		}

		public void setUpdateTime(String updateTime) {
			this.updateTime = updateTime;
		}
	}
}
