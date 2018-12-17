package com.lesports.qmt.web.api.core.vo;

import com.google.common.collect.Lists;
import com.lesports.qmt.sbd.api.common.GroundType;
import com.lesports.qmt.sbd.api.common.TimeSort;

import java.io.Serializable;
import java.util.List;

/**
 * Created by gengchengliang on 2015/6/23.
 */
public class PollingEpisodeVo implements Serializable {
	private static final long serialVersionUID = 6688586345985429347L;

	//节目id
	private Long id;
	//赛程id
	private Long mid;
	//比赛状态 0:未开始, 1:比赛中, 2:比赛结束
	private Integer matchStatus;
	//比赛显示状态 0:未开始, 1:比赛中, 2:比赛结束
	private Integer status;
    //图文直播状态 0:未开始, 1:比赛中, 2:比赛结束
    private Integer textLiveStatus;
	//是否被删除 0:无 1:有
	private Integer deleted;
	//参赛者
	private List<Competitor> competitors = Lists.newArrayList();
    //比赛进行时刻
    private CurrentMoment currentMoment;

	public class Competitor implements Serializable {
		private static final long serialVersionUID = 6688586345985429348L;

		//球队或球员id
		private Long id;
		//球队或球员名称
		private String name;
		//主客场
		private GroundType ground;
		//比分
		private String score;
		//分节比分
		private List<SectionResult> SectionResults = Lists.newArrayList();

		public class SectionResult implements Serializable {
			private static final long serialVersionUID = 6688586345985429349L;

			//分段顺序
			private int order;
			//分段名称
			private String section;
			//分段比赛结果
			private String result;
            //分段id
            private Long sectionId;

			public int getOrder() {
				return order;
			}

			public void setOrder(int order) {
				this.order = order;
			}

			public String getResult() {
				return result;
			}

			public void setResult(String result) {
				this.result = result;
			}

			public String getSection() {
				return section;
			}

			public void setSection(String section) {
				this.section = section;
			}

            public Long getSectionId() {
                return sectionId;
            }

            public void setSectionId(Long sectionId) {
                this.sectionId = sectionId;
            }

            @Override
            public String toString() {
                return "SectionResult{" +
                        "order=" + order +
                        ", section='" + section + '\'' +
                        ", result='" + result + '\'' +
                        ", sectionId='" + sectionId + '\'' +
                        '}';
            }
        }
		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getScore() {
			return score;
		}

		public void setScore(String score) {
			this.score = score;
		}

		public List<SectionResult> getSectionResults() {
			return SectionResults;
		}

		public void setSectionResults(List<SectionResult> sectionResults) {
			SectionResults = sectionResults;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
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
			sb.append(", ground=").append(ground);
			sb.append(", score='").append(score).append('\'');
			sb.append(", SectionResults=").append(SectionResults);
			sb.append('}');
			return sb.toString();
		}
	}

    public class CurrentMoment implements Serializable {
		private static final long serialVersionUID = 6688586345985429349L;

		//比赛进行的阶段
        private String sectionName;
        //比赛进行的时间,精确到秒
        private double time;
        //比赛时间顺序
        private TimeSort sort;

        public String getSectionName() {
            return sectionName;
        }

        public void setSectionName(String sectionName) {
            this.sectionName = sectionName;
        }

        public double getTime() {
            return time;
        }

        public void setTime(double time) {
            this.time = time;
        }

        public TimeSort getSort() {
            return sort;
        }

        public void setSort(TimeSort sort) {
            this.sort = sort;
        }

        @Override
        public String toString() {
            return "CurrentMoment{" +
                    "sectionName='" + sectionName + '\'' +
                    ", time=" + time +
                    ", sort=" + sort +
                    '}';
        }
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getMatchStatus() {
		return matchStatus;
	}

	public void setMatchStatus(Integer matchStatus) {
		this.matchStatus = matchStatus;
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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

    public CurrentMoment getCurrentMoment() {
        return currentMoment;
    }

    public void setCurrentMoment(CurrentMoment currentMoment) {
        this.currentMoment = currentMoment;
    }

    public Integer getTextLiveStatus() {
        return textLiveStatus;
    }

    public void setTextLiveStatus(Integer textLiveStatus) {
        this.textLiveStatus = textLiveStatus;
    }

	public Integer getDeleted() {
		return deleted;
	}

	public void setDeleted(Integer deleted) {
		this.deleted = deleted;
	}

	@Override
	public String toString() {
		final StringBuffer sb = new StringBuffer("PollingEpisodeVo{");
		sb.append("id=").append(id);
		sb.append(", mid=").append(mid);
		sb.append(", matchStatus=").append(matchStatus);
		sb.append(", status=").append(status);
		sb.append(", textLiveStatus=").append(textLiveStatus);
		sb.append(", deleted=").append(deleted);
		sb.append(", competitors=").append(competitors);
		sb.append(", currentMoment=").append(currentMoment);
		sb.append('}');
		return sb.toString();
	}
}
