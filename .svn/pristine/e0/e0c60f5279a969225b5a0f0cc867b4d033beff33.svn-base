package com.lesports.qmt.web.api.core.vo;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by gengchengliang on 2015/6/19.
 */
public class VideoVo extends TopicDataInfo implements Serializable  {

	//视频id
    private Long id;
    //视频类型
    private Integer type;
    //新闻创建时间
    private String createTime;
    //图片集
    private Map<String, String> imageUrl;
    //标题
    private String name;
    //描述
    private String desc;
    //播放时长
    private String duration;
    //视频id
    private Long vid;
    //评论id
    private String commentId;
    //是否付费
    private Integer isPay = 0;

    public Integer getIsPay() {
        return isPay;
    }

    public void setIsPay(Integer isPay) {
        this.isPay = isPay;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public Map getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(Map<String, String> imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Long getVid() {
		return vid;
	}

	public void setVid(Long vid) {
		this.vid = vid;
	}

	public String getCommentId() {
		return commentId;
	}

	public void setCommentId(String commentId) {
		this.commentId = commentId;
	}

	@Override
	public String toString() {
		final StringBuffer sb = new StringBuffer("VideoVo{");
		sb.append("id=").append(id);
		sb.append(", type=").append(type);
		sb.append(", createTime='").append(createTime).append('\'');
		sb.append(", imageUrl=").append(imageUrl);
		sb.append(", name='").append(name).append('\'');
		sb.append(", desc='").append(desc).append('\'');
		sb.append(", duration='").append(duration).append('\'');
		sb.append(", vid=").append(vid);
        sb.append(", isPay=").append(isPay);
		sb.append(", commentId='").append(commentId).append('\'');
		sb.append('}');
		return sb.toString();
	}
}
