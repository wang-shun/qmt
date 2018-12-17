package com.lesports.qmt.web.datacenter.vo;

import com.google.common.collect.Lists;
import com.lesports.api.common.CountryCode;
import com.lesports.qmt.sbd.api.common.MatchSystem;
import com.lesports.qmt.sbd.api.dto.TCompetitor;

import java.util.List;
import java.util.Map;

/**
 * create by wangjichuan  date:16-12-20 time:16:45
 */
public class EpisodeVo extends ContentBaseVo{
    //赛程id
    private Long mid;
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
    //比赛小项
    private Long gameSType;
    //比赛小项名称
    private String gameSName;
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
    private List<TCompetitor> competitors = Lists.newArrayList();
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
/*    //类型
    private Integer type;*/
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
    //所属国家
    private CountryCode countryCode = CountryCode.CN;


    public Long getMid() {
        return mid;
    }

    public void setMid(Long mid) {
        this.mid = mid;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getTextLiveStatus() {
        return textLiveStatus;
    }

    public void setTextLiveStatus(Integer textLiveStatus) {
        this.textLiveStatus = textLiveStatus;
    }

    public Integer getVs() {
        return vs;
    }

    public void setVs(Integer vs) {
        this.vs = vs;
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

    public Integer getIsTextLive() {
        return isTextLive;
    }

    public void setIsTextLive(Integer isTextLive) {
        this.isTextLive = isTextLive;
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

    public void setIsHighlights(Integer isHighlights) {
        this.isHighlights = isHighlights;
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

    public List<TCompetitor> getCompetitors() {
        return competitors;
    }

    public void setCompetitors(List<TCompetitor> competitors) {
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

    public String gettLiveLink4H5() {
        return tLiveLink4H5;
    }

    public void settLiveLink4H5(String tLiveLink4H5) {
        this.tLiveLink4H5 = tLiveLink4H5;
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

/*    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }*/

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

    public CountryCode getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(CountryCode countryCode) {
        this.countryCode = countryCode;
    }
}
