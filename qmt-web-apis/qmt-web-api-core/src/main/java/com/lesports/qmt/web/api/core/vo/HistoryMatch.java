package com.lesports.qmt.web.api.core.vo;

import com.google.common.collect.Lists;
import com.lesports.qmt.sbd.api.common.CompetitorType;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * 比赛大厅的节目VO
 * Created by gengchengliang on 2015/6/23.
 */
public class HistoryMatch implements Serializable {

    private static final long serialVersionUID = 6688586345985429342L;

    //赛程id
    private Long mid;
    //赛程名称
    private String name;
    //赛事id
    private Long cid;
	//赛季id
	private Long csid;
	//比赛开始日期
	private String startDate;
    //比赛开始时间
    private String startTime;
    //轮次
    private String round;
    //比赛阶段
    private String stage;
	//赛事简称
    private String abbreviation;
    //参赛者
    private List<Competitor> competitors = Lists.newArrayList();

    public class Tag implements Serializable {
        private static final long serialVersionUID = 6688586345985429343L;

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

    public class Competitor implements Serializable {
        private static final long serialVersionUID = 6688586345985429344L;

        //球队或球员id
        private Long id;
        //球队或球员名称
        private String name;
        //球队或球员logo
        private String logo;
        //得分
        private String finalResult;
        //主客场
        private Integer ground;
        //分节比分
        private List<SectionResult> SectionResults = Lists.newArrayList();

        public class SectionResult implements Serializable {
            private static final long serialVersionUID = 6688586345985429345L;

            //分段顺序
            private int order;
            //分段名称
            private String section;
            //分段比赛结果
            private String result;

            public int getOrder() {
                return order;
            }

            public void setOrder(int order) {
                this.order = order;
            }

            public String getSection() {
                return section;
            }

            public void setSection(String section) {
                this.section = section;
            }

            public String getResult() {
                return result;
            }

            public void setResult(String result) {
                this.result = result;
            }

            @Override
            public String toString() {
                return "SectionResult{" +
                        "order=" + order +
                        ", section='" + section + '\'' +
                        ", result='" + result + '\'' +
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

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

		public String getFinalResult() {
			return finalResult;
		}

		public void setFinalResult(String finalResult) {
			this.finalResult = finalResult;
		}

		public Integer getGround() {
            return ground;
        }

        public void setGround(Integer ground) {
            this.ground = ground;
        }

        public List<SectionResult> getSectionResults() {
            return SectionResults;
        }

        public void setSectionResults(List<SectionResult> sectionResults) {
            SectionResults = sectionResults;
        }

		@Override
		public String toString() {
			final StringBuffer sb = new StringBuffer("Competitor{");
			sb.append("id=").append(id);
			sb.append(", name='").append(name).append('\'');
			sb.append(", logo='").append(logo).append('\'');
			sb.append(", finalResult='").append(finalResult).append('\'');
			sb.append(", ground=").append(ground);
			sb.append(", SectionResults=").append(SectionResults);
			sb.append('}');
			return sb.toString();
		}
	}

    public class CompetitorStat implements Serializable {
        private static final long serialVersionUID = 6688586345985429346L;

        //球队或球员id
        private Long id;
        //球队或球员名称
        private String name;
        //参赛者类型
        private CompetitorType competitorType;
        //排序
        private Integer order;
        //如果参赛者是球员时,请添加参赛者所属队伍id
        private Long tid;
        //如果参赛者是球员时,请添加参赛者所属队伍名称
        private String teamName;
        //参赛者技术统计
        private Map<String, String> stats = Collections.EMPTY_MAP;

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

        public CompetitorType getCompetitorType() {
            return competitorType;
        }

        public void setCompetitorType(CompetitorType competitorType) {
            this.competitorType = competitorType;
        }

        public String getTeamName() {
            return teamName;
        }

        public void setTeamName(String teamName) {
            this.teamName = teamName;
        }

        public Map<String, String> getStats() {
            return stats;
        }

        public void setStats(Map<String, String> stats) {
            this.stats = stats;
        }

        public Integer getOrder() {
            return order;
        }

        public void setOrder(Integer order) {
            this.order = order;
        }

        public Long getTid() {
            return tid;
        }

        public void setTid(Long tid) {
            this.tid = tid;
        }

        @Override
        public String toString() {
            return "CompetitorStat{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", competitorType=" + competitorType +
                    ", order=" + order +
                    ", tid=" + tid +
                    ", teamName='" + teamName + '\'' +
                    ", stats=" + stats +
                    '}';
        }
    }

	public Long getCsid() {
		return csid;
	}

	public void setCsid(Long csid) {
		this.csid = csid;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
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

    public List<Competitor> getCompetitors() {
        return competitors;
    }

    public void setCompetitors(List<Competitor> competitors) {
        this.competitors = competitors;
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

    public Long getMid() {
        return mid;
    }

    public void setMid(Long mid) {
        this.mid = mid;
    }

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getAbbreviation() {
		return abbreviation;
	}

	public void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation;
	}

	@Override
	public String toString() {
		final StringBuffer sb = new StringBuffer("HallEpisodeVo{");
		sb.append(", mid=").append(mid);
		sb.append(", name='").append(name).append('\'');
		sb.append(", cid=").append(cid);
		sb.append(", startTime='").append(startTime).append('\'');
		sb.append(", round='").append(round).append('\'');
		sb.append(", stage='").append(stage).append('\'');
		sb.append(", competitors=").append(competitors);
		sb.append('}');
		return sb.toString();
	}
}
