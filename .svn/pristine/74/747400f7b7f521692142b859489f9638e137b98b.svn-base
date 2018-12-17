package com.lesports.qmt.web.api.core.vo;


import com.lesports.qmt.sbd.api.common.GroundType;
import com.lesports.qmt.sbd.api.dto.TPlayer;

import java.io.Serializable;
import java.util.List;

/**
 * Created by gengchengliang on 2015/11/18.
 */
public class Squad implements Serializable {

	//球队id
	private Long tid;
	//主客队
	private GroundType ground;
	//首发阵容
	private List<TPlayer> starting;
	//替补阵容
	private List<TPlayer> substitution;
	//阵型
	private String formation;

	public Long getTid() {
		return tid;
	}

	public void setTid(Long tid) {
		this.tid = tid;
	}

	public GroundType getGround() {
		return ground;
	}

	public void setGround(GroundType ground) {
		this.ground = ground;
	}

	public List<TPlayer> getStarting() {
		return starting;
	}

	public void setStarting(List<TPlayer> starting) {
		this.starting = starting;
	}

	public List<TPlayer> getSubstitution() {
		return substitution;
	}

	public void setSubstitution(List<TPlayer> substitution) {
		this.substitution = substitution;
	}

	public String getFormation() {
		return formation;
	}

	public void setFormation(String formation) {
		this.formation = formation;
	}

	@Override
	public String toString() {
		return "Squad{" +
				"tid=" + tid +
				", ground=" + ground +
				", starting=" + starting +
				", substitution=" + substitution +
				", formation='" + formation + '\'' +
				'}';
	}
}
