package com.lesports.qmt.web.api.core.vo;

import com.google.common.collect.Lists;

import java.io.Serializable;
import java.util.List;

/**
 * Created by gengchengliang on 2015/7/23.
 */
public class DetailNews extends NewsVo implements Serializable{

	//新闻内容
	private String content;
	//图文新闻的图片集
 	private List<NewsImage> images = Lists.newArrayList();
	//新闻来源名称
	private String source;
	//作者
	private String author;

	public class NewsImage {
		//图片id
		private Long id;
		//图片名称
		private String name;
		//图片描述
		private String desc;
		//图片地址
		private String image;
		//是否是封面图 0:不是 1:是
		private Integer cover;
		//展示顺序
		private Integer order;

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

		public String getDesc() {
			return desc;
		}

		public void setDesc(String desc) {
			this.desc = desc;
		}

		public Integer getCover() {
			return cover;
		}

		public void setCover(Integer cover) {
			this.cover = cover;
		}

		public Integer getOrder() {
			return order;
		}

		public void setOrder(Integer order) {
			this.order = order;
		}

		public String getImage() {
			return image;
		}

		public void setImage(String image) {
			this.image = image;
		}

		@Override
		public String toString() {
			final StringBuffer sb = new StringBuffer("NewsImage{");
			sb.append("id=").append(id);
			sb.append(", name='").append(name).append('\'');
			sb.append(", desc='").append(desc).append('\'');
			sb.append(", image='").append(image).append('\'');
			sb.append(", cover=").append(cover);
			sb.append(", order=").append(order);
			sb.append('}');
			return sb.toString();
		}
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public List<NewsImage> getImages() {
		return images;
	}

	public void setImages(List<NewsImage> images) {
		this.images = images;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	@Override
	public String toString() {
		final StringBuffer sb = new StringBuffer("DetailNews{");
		sb.append("content='").append(content).append('\'');
		sb.append(", images=").append(images);
		sb.append(", source='").append(source).append('\'');
		sb.append(", author='").append(author).append('\'');
		sb.append('}');
		return sb.toString();
	}
}
