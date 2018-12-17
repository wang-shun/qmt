package com.lesports.qmt.web.api.core.vo;


import com.lesports.qmt.config.api.dto.TTag;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;

/**
 * Created by gengchengliang on 2015/6/19.
 */
public class NewsVo implements Serializable {

	//新闻id
	private long id;
	//新闻类型
	private Integer newsType;
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
	//如果是视频新闻的话为视频id
	private Long vid;
	//评论id
	private String commentId;
	//是否被推荐 0:未推荐 1:推荐
	private Integer recommend;
	//图集图片的总数
	private Integer imagesCount;
    //标签
    private Set<TTag> tags;
	//是否付费
    private Integer isPay = 0;
    //大项
    private String gameFTypeName;

    public String getGameFTypeName() {
        return gameFTypeName;
    }

    public void setGameFTypeName(String gameFTypeName) {
        this.gameFTypeName = gameFTypeName;
    }


    public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Integer getNewsType() {
		return newsType;
	}

	public void setNewsType(Integer newsType) {
		this.newsType = newsType;
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

	public Integer getRecommend() {
		return recommend;
	}

	public void setRecommend(Integer recommend) {
		this.recommend = recommend;
	}

	public Integer getImagesCount() {
		return imagesCount;
	}

	public void setImagesCount(Integer imagesCount) {
		this.imagesCount = imagesCount;
	}

	public Set<TTag> getTags() {
		return tags;
	}

	public void setTags(Set<TTag> tags) {
		this.tags = tags;
	}

	public Integer getIsPay() {
		return isPay;
	}

	public void setIsPay(Integer isPay) {
		this.isPay = isPay;
	}

	@Override
	public String toString() {
		final StringBuffer sb = new StringBuffer("NewsVo{");
		sb.append("id=").append(id);
		sb.append(", newsType=").append(newsType);
		sb.append(", createTime='").append(createTime).append('\'');
		sb.append(", imageUrl=").append(imageUrl);
		sb.append(", name='").append(name).append('\'');
		sb.append(", desc='").append(desc).append('\'');
		sb.append(", duration='").append(duration).append('\'');
		sb.append(", vid=").append(vid);
		sb.append(", commentId='").append(commentId).append('\'');
		sb.append(", recommend=").append(recommend);
		sb.append(", imagesCount=").append(imagesCount);
		sb.append(", tags=").append(tags);
		sb.append(", isPay=").append(isPay);
        sb.append(", gameFTypeName=").append(gameFTypeName);
		sb.append('}');
		return sb.toString();
	}
}
