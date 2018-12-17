package com.lesports.qmt.web.api.core.vo;

import java.util.List;

/**
 * Created by gengchengliang on 2016/2/29.
 */
public class NewsInfo {
	//新闻总数
	private Long total;
	//新闻集合
	private List<NewsVo> newses;

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	public List<NewsVo> getNewses() {
		return newses;
	}

	public void setNewses(List<NewsVo> newses) {
		this.newses = newses;
	}

	@Override
	public String toString() {
		final StringBuffer sb = new StringBuffer("NewsInfo{");
		sb.append("total=").append(total);
		sb.append(", newses=").append(newses);
		sb.append('}');
		return sb.toString();
	}
}
