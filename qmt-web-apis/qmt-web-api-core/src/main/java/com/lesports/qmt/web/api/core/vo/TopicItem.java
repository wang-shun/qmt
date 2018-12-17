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
public class TopicItem implements Serializable{
    private static final long serialVersionUID = 1L;

    private Long id;
    private Integer type;
    private String createTime;
    private Map<String, String> imageUrl;
    private String name;//标题
    private List<TopicInfo.VideoInfo> videos;
	//评论id
	private String commentId;

    public List<TopicInfo.VideoInfo> getVideos() {
        return videos;
    }

    public void setVideos(List<TopicInfo.VideoInfo> videos) {
        this.videos = videos;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Map<String, String> getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(Map<String, String> imageUrl) {
        this.imageUrl = imageUrl;
    }

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

	public String getCommentId() {
		return commentId;
	}

	public void setCommentId(String commentId) {
		this.commentId = commentId;
	}

	@Override
	public String toString() {
		final StringBuffer sb = new StringBuffer("TopicItem{");
		sb.append("id=").append(id);
		sb.append(", type=").append(type);
		sb.append(", createTime='").append(createTime).append('\'');
		sb.append(", imageUrl=").append(imageUrl);
		sb.append(", name='").append(name).append('\'');
		sb.append(", videos=").append(videos);
		sb.append(", commentId='").append(commentId).append('\'');
		sb.append('}');
		return sb.toString();
	}
}
