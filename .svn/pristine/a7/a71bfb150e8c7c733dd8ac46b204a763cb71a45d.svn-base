package com.lesports.qmt.web.api.core.vo;

import com.google.common.collect.Lists;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by gengchengliang on 2015/7/23.
 */
public class RecommendVo implements Serializable {
	//推荐id
	private Long id;
	//推荐展现顺序
	private Integer order;
	//推荐类型
	private Integer contentType;
	//新闻创建时间
	private String createTime;
	//新闻更新时间
	private String updateTime;
	//新闻类型
	private Integer newsType;
	//推荐名称
	private String name;
	//推荐内容
	private String content;
	//帖子id
	private String postId;
	//阵营id
	private String campId;
	//h5地址
	private String h5;
	//发帖人名称
	private String uname;
	//发帖人头像
	private String figurl;
	//图片集合
	private Map<String, String> imageUrl;
	//广告标题
	private String adTitle;
	//如果推荐类型是专题,则返回3个专题视频
	private List<VideoInfo> videos = Lists.newArrayList();
	//图集图片的总数
	private Integer imagesCount;
	//版块内容id - ipad用于本地数据库的存储主键
	private Long blockId;
	//是否付费
	private Integer isPay = 0;

	public class VideoInfo implements Serializable {
		private static final long serialVersionUID = 1L;
		//视频id
		private long id;
		//标题
		private String name;
		//图片集合
		private Map<String, String> imageUrl;

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

		public Map<String, String> getImageUrl() {
			return imageUrl;
		}

		public void setImageUrl(Map<String, String> imageUrl) {
			this.imageUrl = imageUrl;
		}
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	public Integer getContentType() {
		return contentType;
	}

	public void setContentType(Integer contentType) {
		this.contentType = contentType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Map<String, String> getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(Map<String, String> imageUrl) {
		this.imageUrl = imageUrl;
	}

	public List<VideoInfo> getVideos() {
		return videos;
	}

	public void setVideos(List<VideoInfo> videos) {
		this.videos = videos;
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

	public String getPostId() {
		return postId;
	}

	public void setPostId(String postId) {
		this.postId = postId;
	}

	public String getH5() {
		return h5;
	}

	public void setH5(String h5) {
		this.h5 = h5;
	}

	public String getCampId() {
		return campId;
	}

	public void setCampId(String campId) {
		this.campId = campId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getUname() {
		return uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}

	public String getFigurl() {
		return figurl;
	}

	public void setFigurl(String figurl) {
		this.figurl = figurl;
	}

	public String getAdTitle() {
		return adTitle;
	}

	public void setAdTitle(String adTitle) {
		this.adTitle = adTitle;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getImagesCount() {
		return imagesCount;
	}

	public void setImagesCount(Integer imagesCount) {
		this.imagesCount = imagesCount;
	}

	public Long getBlockId() {
		return blockId;
	}

	public void setBlockId(Long blockId) {
		this.blockId = blockId;
	}

	public Integer getIsPay() {
		return isPay;
	}

	public void setIsPay(Integer isPay) {
		this.isPay = isPay;
	}

	@Override
	public String toString() {
		final StringBuffer sb = new StringBuffer("RecommendVo{");
		sb.append("id=").append(id);
		sb.append(", order=").append(order);
		sb.append(", contentType=").append(contentType);
		sb.append(", createTime='").append(createTime).append('\'');
		sb.append(", updateTime='").append(updateTime).append('\'');
		sb.append(", newsType=").append(newsType);
		sb.append(", name='").append(name).append('\'');
		sb.append(", content='").append(content).append('\'');
		sb.append(", postId='").append(postId).append('\'');
		sb.append(", campId='").append(campId).append('\'');
		sb.append(", h5='").append(h5).append('\'');
		sb.append(", uname='").append(uname).append('\'');
		sb.append(", figurl='").append(figurl).append('\'');
		sb.append(", imageUrl=").append(imageUrl);
		sb.append(", adTitle='").append(adTitle).append('\'');
		sb.append(", videos=").append(videos);
		sb.append(", imagesCount=").append(imagesCount);
		sb.append(", blockId=").append(blockId);
		sb.append(", isPay=").append(isPay);
		sb.append('}');
		return sb.toString();
	}
}
