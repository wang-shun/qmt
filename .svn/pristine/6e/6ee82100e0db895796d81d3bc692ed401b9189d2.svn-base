package com.lesports.qmt.web.api.core.vo;

import com.google.common.collect.Lists;
import com.lesports.qmt.sbd.api.common.GroundType;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by gengchengliang on 2016/3/4.
 */
public class MatchAgainst implements Serializable {

	//更新时间
	private String updateAt;
	//历史对阵
	private Confrontations confrontations;
	//近期赛程
	private List<CompetitorMatchs> near = Lists.newArrayList();
	//未来比赛
	private List<CompetitorMatchs> future = Lists.newArrayList();

	public class Confrontations {
		//历史对阵数据
		private Map<String, String> stats;
		//历史对阵
		private List<HistoryMatch> matches;

		public Map<String, String> getStats() {
			return stats;
		}

		public void setStats(Map<String, String> stats) {
			this.stats = stats;
		}

		public List<HistoryMatch> getMatches() {
			return matches;
		}

		public void setMatches(List<HistoryMatch> matches) {
			this.matches = matches;
		}

		@Override
		public String toString() {
			final StringBuffer sb = new StringBuffer("Confrontations{");
			sb.append("stats=").append(stats);
			sb.append(", matches=").append(matches);
			sb.append('}');
			return sb.toString();
		}
	}

	public class CompetitorMatchs {
		//参赛者id
		private Long competitorId;
		//参赛者名称
		private String name;
		//主客场
		private GroundType ground;
		//赛程信息
		List<HistoryMatch> matches;
		//历史对阵数据
		private Map<String, String> stats;

		public Long getCompetitorId() {
			return competitorId;
		}

		public void setCompetitorId(Long competitorId) {
			this.competitorId = competitorId;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public List<HistoryMatch> getMatches() {
			return matches;
		}

		public void setMatches(List<HistoryMatch> matches) {
			this.matches = matches;
		}

		public GroundType getGround() {
			return ground;
		}

		public void setGround(GroundType ground) {
			this.ground = ground;
		}

		public Map<String, String> getStats() {
			return stats;
		}

		public void setStats(Map<String, String> stats) {
			this.stats = stats;
		}

		@Override
		public String toString() {
			final StringBuffer sb = new StringBuffer("CompetitorMatchs{");
			sb.append("competitorId=").append(competitorId);
			sb.append(", name='").append(name).append('\'');
			sb.append(", ground=").append(ground);
			sb.append(", matches=").append(matches);
			sb.append(", stats=").append(stats);
			sb.append('}');
			return sb.toString();
		}
	}

	public List<CompetitorMatchs> getNear() {
		return near;
	}

	public void setNear(List<CompetitorMatchs> near) {
		this.near = near;
	}

	public List<CompetitorMatchs> getFuture() {
		return future;
	}

	public void setFuture(List<CompetitorMatchs> future) {
		this.future = future;
	}

	public String getUpdateAt() {
		return updateAt;
	}

	public void setUpdateAt(String updateAt) {
		this.updateAt = updateAt;
	}

	public Confrontations getConfrontations() {
		return confrontations;
	}

	public void setConfrontations(Confrontations confrontations) {
		this.confrontations = confrontations;
	}

	@Override
	public String toString() {
		final StringBuffer sb = new StringBuffer("MatchAgainst{");
		sb.append("updateAt='").append(updateAt).append('\'');
		sb.append(", confrontations=").append(confrontations);
		sb.append(", near=").append(near);
		sb.append(", future=").append(future);
		sb.append('}');
		return sb.toString();
	}
}
