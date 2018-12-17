package com.lesports.qmt.web.api.core.vo;

import java.io.Serializable;

/**
 * Created by gengchengliang on 2016/4/15.
 */
public class ImeiVo implements Serializable {
	//imei
	private String imeiId;
	//定制的赛事菜单
	private Menu cMenu;
	//定制的新闻菜单
	private Menu nMenu;

	public class Menu {
		private Long id;
		private String name;

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

		@Override
		public String toString() {
			final StringBuffer sb = new StringBuffer("Menu{");
			sb.append("id=").append(id);
			sb.append(", name='").append(name).append('\'');
			sb.append('}');
			return sb.toString();
		}
	}

	public Menu getcMenu() {
		return cMenu;
	}

	public void setcMenu(Menu cMenu) {
		this.cMenu = cMenu;
	}

	public Menu getnMenu() {
		return nMenu;
	}

	public void setnMenu(Menu nMenu) {
		this.nMenu = nMenu;
	}

	public String getImeiId() {
		return imeiId;
	}

	public void setImeiId(String imeiId) {
		this.imeiId = imeiId;
	}

	@Override
	public String toString() {
		final StringBuffer sb = new StringBuffer("ImeiVo{");
		sb.append("imeiId='").append(imeiId).append('\'');
		sb.append(", cMenu=").append(cMenu);
		sb.append(", nMenu=").append(nMenu);
		sb.append('}');
		return sb.toString();
	}
}
