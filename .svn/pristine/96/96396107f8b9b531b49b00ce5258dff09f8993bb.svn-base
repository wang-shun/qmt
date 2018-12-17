package com.lesports.qmt.web.api.core.vo;

import com.lesports.qmt.sbd.api.dto.*;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by gengchengliang on 2016/5/12.
 */
public class TeamMixedVo implements Serializable{

    private static final long serialVersionUID = -3169765313942320382L;

	//球队详情
	private TTeam team;
    //近期动态
	private List<HallEpisodeVo> episodes;
	//足球-球队阵容
	private Map<Long, List<TTeamPlayer>> players;
	//球队联盟中排行
	private TTopListItem teamToplist;
	//球队全联盟分项榜单
	private List<TTopList> itemizedToplist;
	//篮球-球队阵容
	private List<BasketballPlayer> basketballPlayers;
    //篮球-球队内部排行前三的球员
    private Map<String,Object> dataToplist;
    //球队历史赛季技术统计
    private List<Map<String,String>> historyStats;
    //下场比赛
//    private NextMatch nextMatch;
    private HallEpisodeVo nextMatch;

	public TTeam getTeam() {
		return team;
	}

	public void setTeam(TTeam team) {
		this.team = team;
	}

	public List<HallEpisodeVo> getEpisodes() {
		return episodes;
	}

	public void setEpisodes(List<HallEpisodeVo> episodes) {
		this.episodes = episodes;
	}

	public Map<Long, List<TTeamPlayer>> getPlayers() {
		return players;
	}

	public void setPlayers(Map<Long, List<TTeamPlayer>> players) {
		this.players = players;
	}

	public TTopListItem getTeamToplist() {
		return teamToplist;
	}

	public void setTeamToplist(TTopListItem teamToplist) {
		this.teamToplist = teamToplist;
	}

	public List<TTopList> getItemizedToplist() {
		return itemizedToplist;
	}

	public void setItemizedToplist(List<TTopList> itemizedToplist) {
		this.itemizedToplist = itemizedToplist;
	}

	public List<BasketballPlayer> getBasketballPlayers() {
		return basketballPlayers;
	}

	public void setBasketballPlayers(List<BasketballPlayer> basketballPlayers) {
		this.basketballPlayers = basketballPlayers;
	}

    public Map<String, Object> getDataToplist() {
        return dataToplist;
    }

    public void setDataToplist(Map<String, Object> dataToplist) {
        this.dataToplist = dataToplist;
    }

    public List<Map<String, String>> getHistoryStats() {
        return historyStats;
    }

    public void setHistoryStats(List<Map<String, String>> historyStats) {
        this.historyStats = historyStats;
    }

//    public NextMatch getNextMatch() {
//        return nextMatch;
//    }
//
//    public void setNextMatch(NextMatch nextMatch) {
//        this.nextMatch = nextMatch;
//    }


    public HallEpisodeVo getNextMatch() {
        return nextMatch;
    }

    public void setNextMatch(HallEpisodeVo nextMatch) {
        this.nextMatch = nextMatch;
    }

    @Override
	public String toString() {
		final StringBuffer sb = new StringBuffer("TeamMixedVo{");
		sb.append("team=").append(team);
		sb.append(", episodes=").append(episodes);
		sb.append(", players=").append(players);
		sb.append(", teamToplist=").append(teamToplist);
		sb.append(", itemizedToplist=").append(itemizedToplist);
		sb.append(", basketballPlayers=").append(basketballPlayers);
        sb.append(", dataToplist=").append(dataToplist);
        sb.append(", historyStats=").append(historyStats);
        sb.append(", nextMatch=").append(nextMatch);
		sb.append('}');
		return sb.toString();
	}

	public class BasketballPlayer implements Serializable {

		private static final long serialVersionUID = -3169765413942320382L;

		private Long id;
		//球员名称
		private String name;
		//球员英文名
		private String englishName;
		//球员昵称
		private String nickName;
		//身高
		private Integer height;
		//体重
		private Integer weight;
		//出生日期
		private String birthday;
		//性别
		private Integer gender;
		private Integer number;
		//国籍
		private String nationality;
		//出生城市
		private String city;
		//项目种类（足球、篮球、网球等）
		private Long gameFTypeId;
		private String gameFType;
		//场上位置
		private Long positionId;
		private String position;
		//大头像
		private String imageUrl;
		//身价
		private String careerValue;
		//毕业学校
		private String school;
		//NBA球龄
		private String experience;
		//选秀情况
		private String draft;
		//年薪
		private String salary;
		//球员数据
		private TCompetitorSeasonStat stat;

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

		public String getEnglishName() {
			return englishName;
		}

		public void setEnglishName(String englishName) {
			this.englishName = englishName;
		}

		public String getNickName() {
			return nickName;
		}

		public void setNickName(String nickName) {
			this.nickName = nickName;
		}

		public Integer getHeight() {
			return height;
		}

		public void setHeight(Integer height) {
			this.height = height;
		}

		public Integer getWeight() {
			return weight;
		}

		public void setWeight(Integer weight) {
			this.weight = weight;
		}

		public String getBirthday() {
			return birthday;
		}

		public void setBirthday(String birthday) {
			this.birthday = birthday;
		}

		public Integer getGender() {
			return gender;
		}

		public void setGender(Integer gender) {
			this.gender = gender;
		}

		public Integer getNumber() {
			return number;
		}

		public void setNumber(Integer number) {
			this.number = number;
		}

		public String getNationality() {
			return nationality;
		}

		public void setNationality(String nationality) {
			this.nationality = nationality;
		}

		public String getCity() {
			return city;
		}

		public void setCity(String city) {
			this.city = city;
		}

		public Long getGameFTypeId() {
			return gameFTypeId;
		}

		public void setGameFTypeId(Long gameFTypeId) {
			this.gameFTypeId = gameFTypeId;
		}

		public String getGameFType() {
			return gameFType;
		}

		public void setGameFType(String gameFType) {
			this.gameFType = gameFType;
		}

		public Long getPositionId() {
			return positionId;
		}

		public void setPositionId(Long positionId) {
			this.positionId = positionId;
		}

		public String getPosition() {
			return position;
		}

		public void setPosition(String position) {
			this.position = position;
		}

		public String getImageUrl() {
			return imageUrl;
		}

		public void setImageUrl(String imageUrl) {
			this.imageUrl = imageUrl;
		}

		public String getCareerValue() {
			return careerValue;
		}

		public void setCareerValue(String careerValue) {
			this.careerValue = careerValue;
		}

		public String getSchool() {
			return school;
		}

		public void setSchool(String school) {
			this.school = school;
		}

		public String getExperience() {
			return experience;
		}

		public void setExperience(String experience) {
			this.experience = experience;
		}

		public TCompetitorSeasonStat getStat() {
			return stat;
		}

		public void setStat(TCompetitorSeasonStat stat) {
			this.stat = stat;
		}

		public String getDraft() {
			return draft;
		}

		public void setDraft(String draft) {
			this.draft = draft;
		}

		public String getSalary() {
			return salary;
		}

		public void setSalary(String salary) {
			this.salary = salary;
		}

		@Override
		public String toString() {
			final StringBuffer sb = new StringBuffer("BasketballPlayer{");
			sb.append("id=").append(id);
			sb.append(", name='").append(name).append('\'');
			sb.append(", englishName='").append(englishName).append('\'');
			sb.append(", nickName='").append(nickName).append('\'');
			sb.append(", height=").append(height);
			sb.append(", weight=").append(weight);
			sb.append(", birthday='").append(birthday).append('\'');
			sb.append(", gender=").append(gender);
			sb.append(", number=").append(number);
			sb.append(", nationality='").append(nationality).append('\'');
			sb.append(", city='").append(city).append('\'');
			sb.append(", gameFTypeId=").append(gameFTypeId);
			sb.append(", gameFType='").append(gameFType).append('\'');
			sb.append(", positionId=").append(positionId);
			sb.append(", position='").append(position).append('\'');
			sb.append(", imageUrl='").append(imageUrl).append('\'');
			sb.append(", careerValue='").append(careerValue).append('\'');
			sb.append(", school='").append(school).append('\'');
			sb.append(", experience='").append(experience).append('\'');
			sb.append(", draft='").append(draft).append('\'');
			sb.append(", salary='").append(salary).append('\'');
			sb.append(", stat=").append(stat);
			sb.append('}');
			return sb.toString();
		}
	}

    public class NextMatch implements Serializable {
        private static final long serialVersionUID = 3108169948605674229L;

        //比赛id
        private Long id;
        //比赛开始时间
        private String startTime;
        //比赛对手
        private String opponent;
        //对手Logo
        private String opponentLogo;
        //对手id
        private Long opponentId;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public String getOpponent() {
            return opponent;
        }

        public void setOpponent(String opponent) {
            this.opponent = opponent;
        }

        public String getOpponentLogo() {
            return opponentLogo;
        }

        public void setOpponentLogo(String opponentLogo) {
            this.opponentLogo = opponentLogo;
        }

        public Long getOpponentId() {
            return opponentId;
        }

        public void setOpponentId(Long opponentId) {
            this.opponentId = opponentId;
        }

        @Override
        public String toString() {
            final StringBuffer sb = new StringBuffer("NextMatch{");
            sb.append("id=").append(id);
            sb.append(", startTime='").append(startTime).append('\'');
            sb.append(", opponent='").append(opponent).append('\'');
            sb.append(", opponentLogo='").append(opponentLogo).append('\'');
            sb.append(", opponentId=").append(opponentId);
            sb.append('}');
            return sb.toString();
        }
    }
}
