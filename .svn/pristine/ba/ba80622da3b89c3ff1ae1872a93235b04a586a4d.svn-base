package com.lesports.qmt.web.api.core.vo;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.lesports.api.common.CountryCode;
import com.lesports.qmt.sbc.api.dto.TSimpleActivity;
import com.lesports.qmt.sbd.api.common.CompetitorType;
import com.lesports.qmt.sbd.api.common.GroundType;
import com.lesports.qmt.sbd.api.common.MatchSystem;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 比赛大厅的节目VO
 * Created by gengchengliang on 2015/6/23.
 */
public class HallEpisodeVo implements Serializable {

    private static final long serialVersionUID = 6688586345985429342L;

    //节目id
    private Long id;
    //赛程id
    private Long mid;
    //赛程名称
    private String name;
    //赛事id
    private Long cid;
    ////赛事名称
    private String cname;
    //赛季
    private String season;
    //比赛开始时间
    private String startTime;
    //比赛状态 0:未开始, 1:比赛中, 2:比赛结束
    private Integer matchStatus;
    //比赛状态 0:未开始, 1:比赛中, 2:比赛结束
    private Integer status;
    //图文直播状态 0:未开始, 1:比赛中, 2:比赛结束
    private Integer textLiveStatus;
    //是否对阵 0:非对阵 1:对阵
    private Integer vs;
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
    //比赛分项
    private Long disciplineId;
    private String disciplineName;
    private String disciplineUrl;
    //比赛小项
    private Long gameSType;
    //比赛小项名称
    private String gameSName;
    //直播图片集
    private Map<String, String> imageUrl;
    //是否有直播 0:无 1:有
    private Integer isLive = 0;
    //是否有图文直播 0:无 1:有
    private Integer isTextLive = 0;
    //是否有录播 0:无 1:有
    private Integer isRecorded = 0;
    //是否有集锦 0:无 1:有
    private Integer isHighlights = 0;
    //比赛所属时段
    private Integer timeSection;
    //杯赛还是联赛
    private MatchSystem matchSystem;
    //赛事logo
    private String logo;
    //比赛时段和比赛时间
    private String moment;
    //参赛者
    private List<Competitor> competitors = Lists.newArrayList();
    //参赛者排行: F1单场赛程的排行信息
    private List<CompetitorStat> competitorStats = Lists.newArrayList();
    //标签
    private Set<Tag> tags = Sets.newHashSet();
    //播放链接
    private String playLink;
    //图文直播链接
    private String tLiveLink;
    //图文直播链接-H5地址
    private String tLiveLink4H5;
    //图片集
    private Map<String, String> images;
    //期数
    private String periods;
    //描述
    private String desc;
    //时长
    private long duration;
    //类型
    private Integer type;
    //新英付费标识
    private int xinyingPay;
    //赛事阶段名称: 英超 第20轮, NBA 季后赛等
    private String showName;
    //评论id
    private String commentId;
	//是否章鱼猜球 0:无 1:有
	private Integer isOctopus;
	//是否被删除 0:无 1:有
	private Integer deleted;
	//是否是重点赛程 0:不是 1:是
	private Integer key;
    //本节目是否付费
    private Integer isEpisodePay;
	//晋级之路的order,前端通过此字段排列
	private Integer theRoadOrder;
	//app推广活动
	private List<TSimpleActivity> activities = Lists.newArrayList();
    //所属国家
    private CountryCode countryCode = CountryCode.CN;
    //名单页链接
    private String playerPageLink;
    //中国队获得的奖牌列表
    private List<Integer> medalList;
    //中国队是否参赛 0:否 1:有
    private int hasChinaFlag;
    //是否金牌赛事 0:否 1:是
    private int goldMatchFlag;
    //TV卡片类型
    private int cardType = -1;
    //第三方数据类型
    private int partnerType;
    //比赛价格
    private Double price;
    //新英MatchId
    private Long ssportsMid;
    //tv广告位
    private String tvAdImageUrl;
	//章鱼导流
	private String apiInfo;
	//章鱼导流
	private String detailChannelInfo;
	//节目来源: 0:lesports 1:lezhangyu
	private Integer episodeSource = 0;
	//直播场次id
	private String liveUniqueId;
	//会员信息
	private List<VipInfos> vipInfos = Lists.newArrayList();

    public class VipInfos implements Serializable {

		private static final long serialVersionUID = 6688586305985429343L;
		private Integer productId;
		private String name;

		public Integer getProductId() {
			return productId;
		}

		public void setProductId(Integer productId) {
			this.productId = productId;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		@Override
		public String toString() {
			final StringBuffer sb = new StringBuffer("VipInfos{");
			sb.append("productId=").append(productId);
			sb.append(", name='").append(name).append('\'');
			sb.append('}');
			return sb.toString();
		}

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            VipInfos vipInfos = (VipInfos) o;

            if (!productId.equals(vipInfos.productId)) return false;

            return true;
        }

        @Override
        public int hashCode() {
            return productId.hashCode();
        }
    }

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
		//球队全称
		private String officialName;
        //球队或球员logo
        private String logo;
        //球队pngLogo(球员暂无pngLogo)
        private String pnglogo;
        //背板图
        private String bgWebUrl;
        //得分
        private String score;
        //主客场
        private GroundType ground;
        //参赛者类型
        private CompetitorType competitorType;
        //分节比分
        private List<SectionResult> SectionResults = Lists.newArrayList();
		//是否可以被关注 0:不可以 1:可以
		private Integer isFocused;
		//阵营
		private Camp camp;
        //球队或球员的国家id
        private Long competitorCountryId;
        //球队或球员的国家图片
        private String countryImgUrl;

        public class SectionResult implements Serializable {
            private static final long serialVersionUID = 6688586345985429345L;

            //分段顺序
            private int order;
			//分段ID
			private Long sectionId;
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

			public Long getSectionId() {
				return sectionId;
			}

			public void setSectionId(Long sectionId) {
				this.sectionId = sectionId;
			}

			@Override
			public String toString() {
				final StringBuffer sb = new StringBuffer("SectionResult{");
				sb.append("order=").append(order);
				sb.append(", sectionId=").append(sectionId);
				sb.append(", section='").append(section).append('\'');
				sb.append(", result='").append(result).append('\'');
				sb.append('}');
				return sb.toString();
			}
		}

		public class Camp implements Serializable {
			//campId
			private String id;
			//campName
			private String name;
			//阵营缩略图:不支持动态缩放,固定尺寸的
			private String picture;

			public String getId() {
				return id;
			}

			public void setId(String id) {
				this.id = id;
			}

			public String getName() {
				return name;
			}

			public void setName(String name) {
				this.name = name;
			}

			public String getPicture() {
				return picture;
			}

			public void setPicture(String picture) {
				this.picture = picture;
			}

			@Override
			public String toString() {
				return "Camp{" +
						"id='" + id + '\'' +
						", name='" + name + '\'' +
						", picture='" + picture + '\'' +
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

        public List<SectionResult> getSectionResults() {
            return SectionResults;
        }

        public void setSectionResults(List<SectionResult> sectionResults) {
            SectionResults = sectionResults;
        }

        public CompetitorType getCompetitorType() {
            return competitorType;
        }

        public void setCompetitorType(CompetitorType competitorType) {
            this.competitorType = competitorType;
        }

        public String getPnglogo() {
            return pnglogo;
        }

        public void setPnglogo(String pnglogo) {
            this.pnglogo = pnglogo;
        }

        public String getBgWebUrl() {
            return bgWebUrl;
        }

        public void setBgWebUrl(String bgWebUrl) {
            this.bgWebUrl = bgWebUrl;
        }

		public Integer getIsFocused() {
			return isFocused;
		}

		public void setIsFocused(Integer isFocused) {
			this.isFocused = isFocused;
		}

		public Camp getCamp() {
			return camp;
		}

		public void setCamp(Camp camp) {
			this.camp = camp;
		}

        public Long getCompetitorCountryId() {
            return competitorCountryId;
        }

        public void setCompetitorCountryId(Long competitorCountryId) {
            this.competitorCountryId = competitorCountryId;
        }

        public String getCountryImgUrl() {
            return countryImgUrl;
        }

        public void setCountryImgUrl(String countryImgUrl) {
            this.countryImgUrl = countryImgUrl;
        }

		public String getOfficialName() {
			return officialName;
		}

		public void setOfficialName(String officialName) {
			this.officialName = officialName;
		}

		@Override
		public String toString() {
			final StringBuffer sb = new StringBuffer("Competitor{");
			sb.append("id=").append(id);
			sb.append(", name='").append(name).append('\'');
			sb.append(", officialName='").append(officialName).append('\'');
			sb.append(", logo='").append(logo).append('\'');
			sb.append(", pnglogo='").append(pnglogo).append('\'');
			sb.append(", bgWebUrl='").append(bgWebUrl).append('\'');
			sb.append(", score='").append(score).append('\'');
			sb.append(", ground=").append(ground);
			sb.append(", competitorType=").append(competitorType);
			sb.append(", SectionResults=").append(SectionResults);
			sb.append(", isFocused=").append(isFocused);
			sb.append(", camp=").append(camp);
			sb.append(", competitorCountryId=").append(competitorCountryId);
			sb.append(", countryImgUrl='").append(countryImgUrl).append('\'');
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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Map<String, String> getImages() {
        return images;
    }

    public void setImages(Map<String, String> images) {
        this.images = images;
    }

    public String getPeriods() {
        return periods;
    }

    public void setPeriods(String periods) {
        this.periods = periods;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
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

    public Map<String, String> getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(Map<String, String> imageUrl) {
        this.imageUrl = imageUrl;
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

    public Integer getVs() {
        return vs;
    }

    public void setVs(Integer vs) {
        this.vs = vs;
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

    public Integer getIsLive() {
        return isLive;
    }

    public void setIsLive(Integer isLive) {
        this.isLive = isLive;
    }

    public Integer getIsRecorded() {
        return isRecorded;
    }

    public void setIsRecorded(Integer isRecorded) {
        this.isRecorded = isRecorded;
    }

    public Integer getIsHighlights() {
        return isHighlights;
    }

    public void setIsHighlights(Integer isVid) {
        this.isHighlights = isVid;
    }

    public int getHasChinaFlag() {
        return hasChinaFlag;
    }

    public void setHasChinaFlag(int hasChinaFlag) {
        this.hasChinaFlag = hasChinaFlag;
    }

    public List<Integer> getMedalList() {
        return medalList;
    }

    public void setMedalList(List<Integer> medalList) {
        this.medalList = medalList;
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

    public Integer getTimeSection() {
        return timeSection;
    }

    public void setTimeSection(Integer timeSection) {
        this.timeSection = timeSection;
    }

    public MatchSystem getMatchSystem() {
        return matchSystem;
    }

    public void setMatchSystem(MatchSystem matchSystem) {
        this.matchSystem = matchSystem;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getMoment() {
        return moment;
    }

    public void setMoment(String moment) {
        this.moment = moment;
    }

    public List<CompetitorStat> getCompetitorStats() {
        return competitorStats;
    }

    public void setCompetitorStats(List<CompetitorStat> competitorStats) {
        this.competitorStats = competitorStats;
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

    public int getXinyingPay() {
        return xinyingPay;
    }

    public void setXinyingPay(int xinyingPay) {
        this.xinyingPay = xinyingPay;
    }

    public String getShowName() {
        return showName;
    }

    public void setShowName(String showName) {
        this.showName = showName;
    }

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    public Integer getIsTextLive() {
        return isTextLive;
    }

    public void setIsTextLive(Integer isTextLive) {
        this.isTextLive = isTextLive;
    }

    public String gettLiveLink() {
        return tLiveLink;
    }

    public void settLiveLink(String tLiveLink) {
        this.tLiveLink = tLiveLink;
    }

    public String gettLiveLink4H5() {
        return tLiveLink4H5;
    }

    public void settLiveLink4H5(String tLiveLink4H5) {
        this.tLiveLink4H5 = tLiveLink4H5;
    }

    public Integer getTextLiveStatus() {
        return textLiveStatus;
    }

    public void setTextLiveStatus(Integer textLiveStatus) {
        this.textLiveStatus = textLiveStatus;
    }

    public String getDisciplineUrl() {
        return disciplineUrl;
    }

    public void setDisciplineUrl(String disciplineUrl) {
        this.disciplineUrl = disciplineUrl;
    }

    public Long getDisciplineId() {
        return disciplineId;
    }

    public void setDisciplineId(Long disciplineId) {
        this.disciplineId = disciplineId;
    }

    public String getDisciplineName() {
        return disciplineName;
    }

    public void setDisciplineName(String disciplineName) {
        this.disciplineName = disciplineName;
    }

    public Integer getIsOctopus() {
		return isOctopus;
	}

	public void setIsOctopus(Integer isOctopus) {
		this.isOctopus = isOctopus;
	}

	public Integer getDeleted() {
		return deleted;
	}

	public void setDeleted(Integer deleted) {
		this.deleted = deleted;
	}

	public Integer getKey() {
		return key;
	}

	public void setKey(Integer key) {
		this.key = key;
	}

    public Integer getIsEpisodePay() {
        return isEpisodePay;
    }

    public void setIsEpisodePay(Integer isEpisodePay) {
        this.isEpisodePay = isEpisodePay;
    }

	public Integer getTheRoadOrder() {
		return theRoadOrder;
	}

	public void setTheRoadOrder(Integer theRoadOrder) {
		this.theRoadOrder = theRoadOrder;
	}

	public List<TSimpleActivity> getActivities() {
		return activities;
	}

	public void setActivities(List<TSimpleActivity> activities) {
		this.activities = activities;
	}

    public CountryCode getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(CountryCode countryCode) {
        this.countryCode = countryCode;
    }

    public String getPlayerPageLink() {
        return playerPageLink;
    }

    public void setPlayerPageLink(String playerPageLink) {
        this.playerPageLink = playerPageLink;
    }

    public int getGoldMatchFlag() {
        return goldMatchFlag;
    }

    public void setGoldMatchFlag(int goldMatchFlag) {
        this.goldMatchFlag = goldMatchFlag;
    }

    public int getCardType() {
        return cardType;
    }

    public void setCardType(int cardType) {
        this.cardType = cardType;
    }

    public int getPartnerType() {
        return partnerType;
    }

    public void setPartnerType(int partnerType) {
        this.partnerType = partnerType;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Long getSsportsMid() {
        return ssportsMid;
    }

    public void setSsportsMid(Long ssportsMid) {
        this.ssportsMid = ssportsMid;
    }

    public String getTvAdImageUrl() {
        return tvAdImageUrl;
    }

    public void setTvAdImageUrl(String tvAdImageUrl) {
        this.tvAdImageUrl = tvAdImageUrl;
    }

	public String getApiInfo() {
		return apiInfo;
	}

	public void setApiInfo(String apiInfo) {
		this.apiInfo = apiInfo;
	}

	public String getDetailChannelInfo() {
		return detailChannelInfo;
	}

	public void setDetailChannelInfo(String detailChannelInfo) {
		this.detailChannelInfo = detailChannelInfo;
	}

	public Integer getEpisodeSource() {
		return episodeSource;
	}

	public void setEpisodeSource(Integer episodeSource) {
		this.episodeSource = episodeSource;
	}

	public String getLiveUniqueId() {
		return liveUniqueId;
	}

	public void setLiveUniqueId(String liveUniqueId) {
		this.liveUniqueId = liveUniqueId;
	}

	public List<VipInfos> getVipInfos() {
		return vipInfos;
	}

	public void setVipInfos(List<VipInfos> vipInfos) {
		this.vipInfos = vipInfos;
	}

	@Override
	public String toString() {
		final StringBuffer sb = new StringBuffer("HallEpisodeVo{");
		sb.append("id=").append(id);
		sb.append(", mid=").append(mid);
		sb.append(", name='").append(name).append('\'');
		sb.append(", cid=").append(cid);
		sb.append(", cname='").append(cname).append('\'');
		sb.append(", season='").append(season).append('\'');
		sb.append(", startTime='").append(startTime).append('\'');
		sb.append(", matchStatus=").append(matchStatus);
		sb.append(", status=").append(status);
		sb.append(", textLiveStatus=").append(textLiveStatus);
		sb.append(", vs=").append(vs);
		sb.append(", round='").append(round).append('\'');
		sb.append(", stage='").append(stage).append('\'');
		sb.append(", group='").append(group).append('\'');
		sb.append(", gameFType=").append(gameFType);
		sb.append(", gameFName='").append(gameFName).append('\'');
		sb.append(", disciplineId=").append(disciplineId);
		sb.append(", disciplineName='").append(disciplineName).append('\'');
		sb.append(", disciplineUrl='").append(disciplineUrl).append('\'');
		sb.append(", gameSType=").append(gameSType);
		sb.append(", gameSName='").append(gameSName).append('\'');
		sb.append(", imageUrl=").append(imageUrl);
		sb.append(", isLive=").append(isLive);
		sb.append(", isTextLive=").append(isTextLive);
		sb.append(", isRecorded=").append(isRecorded);
		sb.append(", isHighlights=").append(isHighlights);
		sb.append(", timeSection=").append(timeSection);
		sb.append(", matchSystem=").append(matchSystem);
		sb.append(", logo='").append(logo).append('\'');
		sb.append(", moment='").append(moment).append('\'');
		sb.append(", competitors=").append(competitors);
		sb.append(", competitorStats=").append(competitorStats);
		sb.append(", tags=").append(tags);
		sb.append(", playLink='").append(playLink).append('\'');
		sb.append(", tLiveLink='").append(tLiveLink).append('\'');
		sb.append(", tLiveLink4H5='").append(tLiveLink4H5).append('\'');
		sb.append(", images=").append(images);
		sb.append(", periods='").append(periods).append('\'');
		sb.append(", desc='").append(desc).append('\'');
		sb.append(", duration=").append(duration);
		sb.append(", type=").append(type);
		sb.append(", xinyingPay=").append(xinyingPay);
		sb.append(", showName='").append(showName).append('\'');
		sb.append(", commentId='").append(commentId).append('\'');
		sb.append(", isOctopus=").append(isOctopus);
		sb.append(", deleted=").append(deleted);
		sb.append(", key=").append(key);
		sb.append(", isEpisodePay=").append(isEpisodePay);
		sb.append(", theRoadOrder=").append(theRoadOrder);
		sb.append(", activities=").append(activities);
		sb.append(", countryCode=").append(countryCode);
		sb.append(", playerPageLink='").append(playerPageLink).append('\'');
		sb.append(", medalList=").append(medalList);
		sb.append(", hasChinaFlag=").append(hasChinaFlag);
		sb.append(", goldMatchFlag=").append(goldMatchFlag);
		sb.append(", cardType=").append(cardType);
		sb.append(", partnerType=").append(partnerType);
		sb.append(", price=").append(price);
		sb.append(", ssportsMid=").append(ssportsMid);
		sb.append(", tvAdImageUrl='").append(tvAdImageUrl).append('\'');
		sb.append(", apiInfo='").append(apiInfo).append('\'');
		sb.append(", detailChannelInfo='").append(detailChannelInfo).append('\'');
		sb.append(", episodeSource=").append(episodeSource);
		sb.append('}');
		return sb.toString();
	}
}
