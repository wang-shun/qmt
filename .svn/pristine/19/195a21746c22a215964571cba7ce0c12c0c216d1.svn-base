package com.lesports.qmt.web.api.core.vo;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.lesports.qmt.sbd.api.common.CompetitorType;
import com.lesports.qmt.sbd.api.common.GroundType;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by ruiyuansheng on 2015/12/17.
 */
public class HallEpisodeVo4Tv extends TopicDataInfo implements Serializable {


    private static final long serialVersionUID = 6688586345985429342L;

    //节目id
    private Long id;
    //赛程名称
    private String name;
    //赛事id
    private Long cid;
    ////赛事名称
    private String cname;
    //比赛开始时间
    private String startTime;
    //比赛状态 0:未开始, 1:比赛中, 2:比赛结束
    private Integer status;
    //是否对阵 0:非对阵 1:对阵
    private Integer vs;
    //轮次
    private String round;
    //是否有直播 0:无 1:有
    private Integer isLive = 0;
    //比赛所属时段
    private Integer timeSection;
    //赛事logo
    private String logo;
    //比赛时段和比赛时间
    private String moment;
    //参赛者
    private List<Competitor> competitors = Lists.newArrayList();
    //赛事阶段名称: 英超 第20轮, NBA 季后赛等
    private String showName;
    //直播信息
    private List<Lives> lives = Lists.newArrayList();
    //录播id
    private Long recordedId;
    //直播图片集
    private Map<String, String> imageUrl;
    //新英付费标识
    private Integer xinyingPay;
    //本节目是否付费，所有直播流中，只要有一场免费就显示免费
    private Integer isEpisodePay;

    private String tvBackgroudPic;
    //比赛大项
    private Long gameFType;
    //比赛id
    private Long mid;
    //tv广告位
    private String tvAdImageUrl;
    //香港TV展示状态,0:未開始比賽，且未主動關聯視頻，1：预告（未開始比賽，主動關聯視頻），2： 进行中，3：已結束比賽，且未主動關聯視頻 ，4：重播（已結束比賽，主動關聯視頻）
    private Integer showStatus;

    private Boolean  isAt = false;

    private Integer cornerMark;//1:vip,2:原创，3：专题，4：图集，5，回放，6：集锦

    //直播场次id
    private String liveUniqueId;
    //会员信息
    private Set<VipInfos> vipInfos = Sets.newHashSet();

    //TV卡片类型
    private int cardType = -1;

    public class VipInfos implements Serializable {

        private static final long serialVersionUID = 6688586305985429343L;
        private Integer productId;
        private String name;
        private Integer type;//1：超级体育会员，2：英超会员，3：NBA会员

        public Integer getType() {
            return type;
        }

        public void setType(Integer type) {
            this.type = type;
        }

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
            sb.append(", type='").append(type).append('\'');
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

    public int getCardType() {
        return cardType;
    }

    public void setCardType(int cardType) {
        this.cardType = cardType;
    }

    public String getLiveUniqueId() {
        return liveUniqueId;
    }

    public void setLiveUniqueId(String liveUniqueId) {
        this.liveUniqueId = liveUniqueId;
    }

    public Set<VipInfos> getVipInfos() {
        return vipInfos;
    }

    public void setVipInfos(Set<VipInfos> vipInfos) {
        this.vipInfos = vipInfos;
    }

    public Integer getCornerMark() {
        return cornerMark;
    }

    public void setCornerMark(Integer cornerMark) {
        this.cornerMark = cornerMark;
    }

    public Boolean getIsAt() {
        return isAt;
    }

    public void setIsAt(Boolean isAt) {
        this.isAt = isAt;
    }

    public Integer getShowStatus() {
        return showStatus;
    }

    public void setShowStatus(Integer showStatus) {
        this.showStatus = showStatus;
    }

    public Long getMid() {
        return mid;
    }

    public void setMid(Long mid) {
        this.mid = mid;
    }

    public Long getGameFType() {
        return gameFType;
    }

    public void setGameFType(Long gameFType) {
        this.gameFType = gameFType;
    }

    public String getTvBackgroudPic() {
        return tvBackgroudPic;
    }

    public void setTvBackgroudPic(String tvBackgroudPic) {
        this.tvBackgroudPic = tvBackgroudPic;
    }

    public Integer getXinyingPay() {
        return xinyingPay;
    }

    public void setXinyingPay(Integer xinyingPay) {
        this.xinyingPay = xinyingPay;
    }

    public Integer getIsEpisodePay() {
        return isEpisodePay;
    }

    public void setIsEpisodePay(Integer isEpisodePay) {
        this.isEpisodePay = isEpisodePay;
    }

    public Map<String, String> getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(Map<String, String> imageUrl) {
        this.imageUrl = imageUrl;
    }

    public List<Lives> getLives() {
        return lives;
    }

    public void setLives(List<Lives> lives) {
        this.lives = lives;
    }

    public Long getRecordedId() {
        return recordedId;
    }

    public void setRecordedId(Long recordedId) {
        this.recordedId = recordedId;
    }

    public String getTvAdImageUrl() {
        return tvAdImageUrl;
    }

    public void setTvAdImageUrl(String tvAdImageUrl) {
        this.tvAdImageUrl = tvAdImageUrl;
    }

    public class Lives {
        private String liveId;
        private String chatRoomId;
        private String name;
        private Integer isPay;//是否付费

        public Integer getIsPay() {
            return isPay;
        }

        public void setIsPay(Integer isPay) {
            this.isPay = isPay;
        }

        public String getLiveId() {
            return liveId;
        }

        public void setLiveId(String liveId) {
            this.liveId = liveId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }


        public String getChatRoomId() {
            return chatRoomId;
        }

        public void setChatRoomId(String chatRoomId) {
            this.chatRoomId = chatRoomId;
        }

        @Override
        public String toString() {
            final StringBuffer sb = new StringBuffer("Lives{");
            sb.append("liveId='").append(liveId).append('\'');
            sb.append(", chatRoomId='").append(chatRoomId).append('\'');
            sb.append(", name='").append(name).append('\'');
            sb.append(", IsPay='").append(isPay).append('\'');
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
        //参赛者类型
        private CompetitorType competitorType;

        public GroundType getGround() {
            return ground;
        }

        public void setGround(GroundType ground) {
            this.ground = ground;
        }

        public CompetitorType getCompetitorType() {
            return competitorType;
        }

        public void setCompetitorType(CompetitorType competitorType) {
            this.competitorType = competitorType;
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

        @Override
        public String toString() {
            final StringBuffer sb = new StringBuffer("Competitor{");
            sb.append("id=").append(id);
            sb.append(", name='").append(name).append('\'');
            sb.append(", logo='").append(logo).append('\'');
            sb.append(", score='").append(score).append('\'');
            sb.append(", ground='").append(ground).append('\'');
            sb.append(", competitorType='").append(competitorType).append('\'');
            sb.append('}');
            return sb.toString();
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


    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
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


    public Integer getIsLive() {
        return isLive;
    }

    public void setIsLive(Integer isLive) {
        this.isLive = isLive;
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


    public String getShowName() {
        return showName;
    }

    public void setShowName(String showName) {
        this.showName = showName;
    }

    @Override
    public String toString() {
        return "HallEpisodeVo4Tv{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", cid=" + cid +
                ", cname='" + cname + '\'' +
                ", startTime='" + startTime + '\'' +
                ", status=" + status +
                ", vs=" + vs +
                ", round='" + round + '\'' +
                ", isLive=" + isLive +
                ", timeSection=" + timeSection +
                ", logo='" + logo + '\'' +
                ", moment='" + moment + '\'' +
                ", competitors=" + competitors +
                ", showName='" + showName + '\'' +
                ", lives=" + lives +
                ", recordedId=" + recordedId +
                ", imageUrl=" + imageUrl +
                ", xinyingPay=" + xinyingPay +
                ", isEpisodePay=" + isEpisodePay +
                ", tvBackgroudPic='" + tvBackgroudPic + '\'' +
                ", gameFType=" + gameFType +
                ", mid=" + mid +
                ", tvAdImageUrl='" + tvAdImageUrl + '\'' +
                ", showStatus=" + showStatus +
                ", isAt=" + isAt +
                ", cornerMark=" + cornerMark +
                ", liveUniqueId='" + liveUniqueId + '\'' +
                ", vipInfos=" + vipInfos +
                ", cardType=" + cardType +
                '}';
    }
}
