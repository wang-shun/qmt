package com.lesports.qmt.op.web.api.core.vo;


import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.lesports.qmt.sbd.api.common.GroundType;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * Created by gengchengliang on 2015/6/22.
 */
public class CalEpisodeVo implements Serializable {

	//节目id
	private Long id;
	//赛程id
	private Long mid;
	//赛程名称
	private String name;
	//赛季id
	private Long cid;
	//赛季名称
	private String cname;
	//赛季
	private String season;
	//所属时间段
	private Integer timeSection;
	//比赛开始时间
	private String startTime;
	//开始时间的时间戳
	private Long startTimeStamp;
	//比赛状态 0:未开始, 1:比赛中, 2:比赛结束
	private Integer matchStatus;
	//直播状态 0:未开始, 1:比赛中, 2:比赛结束
	private Integer status;
	//是否对阵 0:非对阵 1:对阵
	private Integer vs;
	//赛事logo
	private String logo;
	//轮次
	private String round;
	//比赛阶段
	private String stage;
	//分组
	private String group;
	//比赛大项
	private Long gameFType;
	//比赛大项名称
	private String gameFName;
	//比赛小项
	private Long gameSType;
	//比赛小项名称
	private String gameSName;
	//参赛者
	private List<Competitor> competitors = Lists.newArrayList();
	//标签
	private Set<Tag> tags = Sets.newHashSet();
	//播放链接
	private String playLink;
	//类型
	private Integer type;

	public class Tag {
		//tagId
		private Long id;
		//tagName
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
			final StringBuffer sb = new StringBuffer("Tag{");
			sb.append("id=").append(id);
			sb.append(", name='").append(name).append('\'');
			sb.append('}');
			return sb.toString();
		}
	}

	public class Competitor {
		//球队或球员id
		private Long id;
		//球队或球员名称
		private String name;
		//球队或球员logo
		private String logo;
		//得分
		private String score;
		//主客场
		private GroundType ground;

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

		public String getLogo() {
			return logo;
		}

		public void setLogo(String logo) {
			this.logo = logo;
		}

		public String getScore() {
			return score;
		}

		public void setScore(String score) {
			this.score = score;
		}

		public GroundType getGround() {
			return ground;
		}

		public void setGround(GroundType ground) {
			this.ground = ground;
		}

		@Override
		public String toString() {
			return "Competitor{" +
					"id=" + id +
					", name='" + name + '\'' +
					", logo='" + logo + '\'' +
					", score=" + score +
					", ground=" + ground +
					'}';
		}
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

	public Long getCid() {
		return cid;
	}

	public void setCid(Long cid) {
		this.cid = cid;
	}

	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

	public String getSeason() {
		return season;
	}

	public void setSeason(String season) {
		this.season = season;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public Integer getMatchStatus() {
		return matchStatus;
	}

	public void setMatchStatus(Integer matchStatus) {
		this.matchStatus = matchStatus;
	}

	public Integer getTimeSection() {
		return timeSection;
	}

	public void setTimeSection(Integer timeSection) {
		this.timeSection = timeSection;
	}

	public Integer getVs() {
		return vs;
	}

	public void setVs(Integer vs) {
		this.vs = vs;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public List<Competitor> getCompetitors() {
		return competitors;
	}

	public void setCompetitors(List<Competitor> competitors) {
		this.competitors = competitors;
	}

	public Long getMid() {
		return mid;
	}

	public void setMid(Long mid) {
		this.mid = mid;
	}

	public Long getStartTimeStamp() {
		return startTimeStamp;
	}

	public void setStartTimeStamp(Long startTimeStamp) {
		this.startTimeStamp = startTimeStamp;
	}

	public String getRound() {
		return round;
	}

	public void setRound(String round) {
		this.round = round;
	}

	public String getStage() {
		return stage;
	}

	public void setStage(String stage) {
		this.stage = stage;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public Long getGameFType() {
		return gameFType;
	}

	public void setGameFType(Long gameFType) {
		this.gameFType = gameFType;
	}

	public String getGameFName() {
		return gameFName;
	}

	public void setGameFName(String gameFName) {
		this.gameFName = gameFName;
	}

	public Long getGameSType() {
		return gameSType;
	}

	public void setGameSType(Long gameSType) {
		this.gameSType = gameSType;
	}

	public String getGameSName() {
		return gameSName;
	}

	public void setGameSName(String gameSName) {
		this.gameSName = gameSName;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Set<Tag> getTags() {
		return tags;
	}

	public void setTags(Set<Tag> tags) {
		this.tags = tags;
	}

	public String getPlayLink() {
		return playLink;
	}

	public void setPlayLink(String playLink) {
		this.playLink = playLink;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	@Override
	public String toString() {
		final StringBuffer sb = new StringBuffer("CalEpisodeVo{");
		sb.append("id=").append(id);
		sb.append(", mid=").append(mid);
		sb.append(", name='").append(name).append('\'');
		sb.append(", cid=").append(cid);
		sb.append(", cname='").append(cname).append('\'');
		sb.append(", season='").append(season).append('\'');
		sb.append(", timeSection=").append(timeSection);
		sb.append(", startTime='").append(startTime).append('\'');
		sb.append(", startTimeStamp=").append(startTimeStamp);
		sb.append(", matchStatus=").append(matchStatus);
		sb.append(", status=").append(status);
		sb.append(", vs=").append(vs);
		sb.append(", logo='").append(logo).append('\'');
		sb.append(", round='").append(round).append('\'');
		sb.append(", stage='").append(stage).append('\'');
		sb.append(", group='").append(group).append('\'');
		sb.append(", gameFType=").append(gameFType);
		sb.append(", gameFName='").append(gameFName).append('\'');
		sb.append(", gameSType=").append(gameSType);
		sb.append(", gameSName='").append(gameSName).append('\'');
		sb.append(", competitors=").append(competitors);
		sb.append(", tags=").append(tags);
		sb.append(", playLink='").append(playLink).append('\'');
		sb.append(", type=").append(type);
		sb.append('}');
		return sb.toString();
	}
}
