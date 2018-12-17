package com.lesports.qmt.sbc.param;

import com.google.common.collect.Sets;
import com.lesports.api.common.LiveShowStatus;
import com.lesports.qmt.sbc.api.common.EpisodeType;

import java.util.Set;

/**
 * Created by lufei1 on 2016/11/17.
 */
public class EpisodeParam {
    private Long id;
    //节目名称
    private String name;
    //直播流
    private String liveStreams;
    //图文直播
    private String textLives;
    //大项
    private Long gameFType;
    //生态专辑id,也就是vrs的专辑id
    private Long leecoAid;
    //专题URL
    private String topicUrl;
    //专辑id，qmt自身的专辑id
    private Long aid;
    //节目类型,比赛,专辑
    private EpisodeType type;
    //是否有直播
    private Boolean hasLive;
    //是否有图文直播
    private Boolean hasTextLive = false;
    //基准状态,如果节目是比赛类型的,以比赛状态为准
    private LiveShowStatus status;
    //开始时间,用户数据查询,如果是比赛节目的话,以比赛开始时间
    //为准,如果是专辑的话,以直播开始时间为准
    private String startTime;
    private String endTime;
    //第几期
    private String periods;
    //标签id
    private Set<Long> tagIds = Sets.newHashSet();
    //是否在新英付费
    private Boolean xinyingPay;
    //对应的新英比赛id
    private Long xinyingMatchId;
    //新英本场比赛的价格
    private Double price;
    //运营控制的比赛状态,如果为null或者NO_LIVE的话表示不需要运营这个状态
    //最终的比赛状态stauts 根据运营状态和比赛状态综合去计算
    private LiveShowStatus opStatus;
    //运营控制比赛的开始时间,如果为-1则不需要运营这个状态
    private String opBeginTime;
    //名称缩写
    private String abbreviation;
    //节目描述
    private String desc;
    //是否是滚球精英
    private Boolean isOctopus;
    //滚球精英的matchId
    private Long octopusMatchId;
    //聊天室信息
    private String chatRoom;
    //节目图片，主要使用在无直播流的节目上
    private String image;
    //pc节目图片，主要使用在pc直播日历
    private String pcImage;
    //tv详情页背景图，主要使用在大乐tv
    private String tvImage;
    //tv比赛大厅广告图片,主要使用在香港tv
    private String tvAdImage;
    //分享标题
    private String shareName;
    //分享描述
    private String shareDesc;
    //是否跳转
    private Boolean hasJump = false;
    //pc跳转url
    private String pcJumpUrl;
    //m站跳转url
    private String mJumpUrl;
    //上线
    private Boolean online;
    //相关集锦
    private Long highlightsId;
    //相关标签
    private String relatedIds;
    //相关赛程
    private String relatedMids;
    //相关内容
    private String relatedItems;

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

    public Long getGameFType() {
        return gameFType;
    }

    public void setGameFType(Long gameFType) {
        this.gameFType = gameFType;
    }

    public Long getLeecoAid() {
        return leecoAid;
    }

    public void setLeecoAid(Long leecoAid) {
        this.leecoAid = leecoAid;
    }

    public String getTopicUrl() {
        return topicUrl;
    }

    public void setTopicUrl(String topicUrl) {
        this.topicUrl = topicUrl;
    }

    public Long getAid() {
        return aid;
    }

    public void setAid(Long aid) {
        this.aid = aid;
    }

    public EpisodeType getType() {
        return type;
    }

    public void setType(EpisodeType type) {
        this.type = type;
    }

    public Boolean getHasLive() {
        return hasLive;
    }

    public void setHasLive(Boolean hasLive) {
        this.hasLive = hasLive;
    }

    public Boolean getHasTextLive() {
        return hasTextLive;
    }

    public void setHasTextLive(Boolean hasTextLive) {
        this.hasTextLive = hasTextLive;
    }

    public LiveShowStatus getStatus() {
        return status;
    }

    public void setStatus(LiveShowStatus status) {
        this.status = status;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getPeriods() {
        return periods;
    }

    public void setPeriods(String periods) {
        this.periods = periods;
    }

    public Set<Long> getTagIds() {
        return tagIds;
    }

    public void setTagIds(Set<Long> tagIds) {
        this.tagIds = tagIds;
    }

    public Boolean getXinyingPay() {
        return xinyingPay;
    }

    public void setXinyingPay(Boolean xinyingPay) {
        this.xinyingPay = xinyingPay;
    }

    public Long getXinyingMatchId() {
        return xinyingMatchId;
    }

    public void setXinyingMatchId(Long xinyingMatchId) {
        this.xinyingMatchId = xinyingMatchId;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public LiveShowStatus getOpStatus() {
        return opStatus;
    }

    public void setOpStatus(LiveShowStatus opStatus) {
        this.opStatus = opStatus;
    }

    public String getOpBeginTime() {
        return opBeginTime;
    }

    public void setOpBeginTime(String opBeginTime) {
        this.opBeginTime = opBeginTime;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Boolean getIsOctopus() {
        return isOctopus;
    }

    public void setIsOctopus(Boolean isOctopus) {
        this.isOctopus = isOctopus;
    }

    public Long getOctopusMatchId() {
        return octopusMatchId;
    }

    public void setOctopusMatchId(Long octopusMatchId) {
        this.octopusMatchId = octopusMatchId;
    }

    public String getChatRoom() {
        return chatRoom;
    }

    public void setChatRoom(String chatRoom) {
        this.chatRoom = chatRoom;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPcImage() {
        return pcImage;
    }

    public void setPcImage(String pcImage) {
        this.pcImage = pcImage;
    }

    public String getTvImage() {
        return tvImage;
    }

    public void setTvImage(String tvImage) {
        this.tvImage = tvImage;
    }

    public String getTvAdImage() {
        return tvAdImage;
    }

    public void setTvAdImage(String tvAdImage) {
        this.tvAdImage = tvAdImage;
    }

    public String getShareName() {
        return shareName;
    }

    public void setShareName(String shareName) {
        this.shareName = shareName;
    }

    public String getShareDesc() {
        return shareDesc;
    }

    public void setShareDesc(String shareDesc) {
        this.shareDesc = shareDesc;
    }

    public Boolean getHasJump() {
        return hasJump;
    }

    public void setHasJump(Boolean hasJump) {
        this.hasJump = hasJump;
    }

    public String getPcJumpUrl() {
        return pcJumpUrl;
    }

    public void setPcJumpUrl(String pcJumpUrl) {
        this.pcJumpUrl = pcJumpUrl;
    }

    public String getmJumpUrl() {
        return mJumpUrl;
    }

    public void setmJumpUrl(String mJumpUrl) {
        this.mJumpUrl = mJumpUrl;
    }

    public Boolean getOnline() {
        return online;
    }

    public void setOnline(Boolean online) {
        this.online = online;
    }

    public Long getHighlightsId() {
        return highlightsId;
    }

    public void setHighlightsId(Long highlightsId) {
        this.highlightsId = highlightsId;
    }

    public String getRelatedIds() {
        return relatedIds;
    }

    public void setRelatedIds(String relatedIds) {
        this.relatedIds = relatedIds;
    }

    public String getRelatedMids() {
        return relatedMids;
    }

    public void setRelatedMids(String relatedMids) {
        this.relatedMids = relatedMids;
    }

    public String getLiveStreams() {
        return liveStreams;
    }

    public void setLiveStreams(String liveStreams) {
        this.liveStreams = liveStreams;
    }

    public String getRelatedItems() {
        return relatedItems;
    }

    public void setRelatedItems(String relatedItems) {
        this.relatedItems = relatedItems;
    }

    public String getTextLives() {
        return textLives;
    }

    public void setTextLives(String textLives) {
        this.textLives = textLives;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("EpisodeParam{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", liveStreams='").append(liveStreams).append('\'');
        sb.append(", textLives='").append(textLives).append('\'');
        sb.append(", gameFType=").append(gameFType);
        sb.append(", leecoAid=").append(leecoAid);
        sb.append(", topicUrl='").append(topicUrl).append('\'');
        sb.append(", aid=").append(aid);
        sb.append(", type=").append(type);
        sb.append(", hasLive=").append(hasLive);
        sb.append(", hasTextLive=").append(hasTextLive);
        sb.append(", status=").append(status);
        sb.append(", startTime='").append(startTime).append('\'');
        sb.append(", endTime='").append(endTime).append('\'');
        sb.append(", periods='").append(periods).append('\'');
        sb.append(", tagIds=").append(tagIds);
        sb.append(", xinyingPay=").append(xinyingPay);
        sb.append(", xinyingMatchId=").append(xinyingMatchId);
        sb.append(", price=").append(price);
        sb.append(", opStatus=").append(opStatus);
        sb.append(", opBeginTime='").append(opBeginTime).append('\'');
        sb.append(", abbreviation='").append(abbreviation).append('\'');
        sb.append(", desc='").append(desc).append('\'');
        sb.append(", isOctopus=").append(isOctopus);
        sb.append(", octopusMatchId=").append(octopusMatchId);
        sb.append(", chatRoom='").append(chatRoom).append('\'');
        sb.append(", image='").append(image).append('\'');
        sb.append(", pcImage='").append(pcImage).append('\'');
        sb.append(", tvImage='").append(tvImage).append('\'');
        sb.append(", tvAdImage='").append(tvAdImage).append('\'');
        sb.append(", shareName='").append(shareName).append('\'');
        sb.append(", shareDesc='").append(shareDesc).append('\'');
        sb.append(", hasJump=").append(hasJump);
        sb.append(", pcJumpUrl='").append(pcJumpUrl).append('\'');
        sb.append(", mJumpUrl='").append(mJumpUrl).append('\'');
        sb.append(", online=").append(online);
        sb.append(", highlightsId=").append(highlightsId);
        sb.append(", relatedIds='").append(relatedIds).append('\'');
        sb.append(", relatedMids='").append(relatedMids).append('\'');
        sb.append(", relatedItems='").append(relatedItems).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
