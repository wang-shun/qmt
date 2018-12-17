package com.lesports.qmt.web.api.core.vo;

import com.google.common.collect.Lists;
import com.lesports.qmt.sbd.api.common.GroundType;

import java.io.Serializable;
import java.util.List;

/**
 * 比赛大厅的节目VO
 * Created by gengchengliang on 2015/6/23.
 */
public class ZhangyuEpisodeVo implements Serializable {

    private static final long serialVersionUID = 6688586345985429342L;

    //节目id
    private Long id;
    //赛程id
    private Long mid;
    //赛程名称
    private String name;
    //比赛开始时间
    private String startTime;
    //比赛状态 0:未开始, 1:比赛中, 2:比赛结束
    private Integer status;
    //比赛时段和比赛时间
    private String moment;
    //参赛者
    private List<Competitor> competitors = Lists.newArrayList();
    //播放链接
    private String playLink;
    //图文直播链接
    private String tLiveLink;
	//章鱼猜球的赛程id
	private Long octopusMatchId;

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
			final StringBuffer sb = new StringBuffer("Competitor{");
			sb.append("id=").append(id);
			sb.append(", name='").append(name).append('\'');
			sb.append(", logo='").append(logo).append('\'');
			sb.append(", score='").append(score).append('\'');
			sb.append(", ground=").append(ground);
			sb.append('}');
			return sb.toString();
		}
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getMid() {
		return mid;
	}

	public void setMid(Long mid) {
		this.mid = mid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getMoment() {
		return moment;
	}

	public void setMoment(String moment) {
		this.moment = moment;
	}

	public List<Competitor> getCompetitors() {
		return competitors;
	}

	public void setCompetitors(List<Competitor> competitors) {
		this.competitors = competitors;
	}

	public String getPlayLink() {
		return playLink;
	}

	public void setPlayLink(String playLink) {
		this.playLink = playLink;
	}

	public String gettLiveLink() {
		return tLiveLink;
	}

	public void settLiveLink(String tLiveLink) {
		this.tLiveLink = tLiveLink;
	}

	public Long getOctopusMatchId() {
		return octopusMatchId;
	}

	public void setOctopusMatchId(Long octopusMatchId) {
		this.octopusMatchId = octopusMatchId;
	}

	@Override
	public String toString() {
		final StringBuffer sb = new StringBuffer("ZhangyuEpisodeVo{");
		sb.append("id=").append(id);
		sb.append(", mid=").append(mid);
		sb.append(", name='").append(name).append('\'');
		sb.append(", startTime='").append(startTime).append('\'');
		sb.append(", status=").append(status);
		sb.append(", moment='").append(moment).append('\'');
		sb.append(", competitors=").append(competitors);
		sb.append(", playLink='").append(playLink).append('\'');
		sb.append(", tLiveLink='").append(tLiveLink).append('\'');
		sb.append(", octopusMatchId=").append(octopusMatchId);
		sb.append('}');
		return sb.toString();
	}
}
