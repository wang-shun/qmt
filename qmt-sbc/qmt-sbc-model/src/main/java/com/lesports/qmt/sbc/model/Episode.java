package com.lesports.qmt.sbc.model;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.lesports.api.common.*;
import com.lesports.qmt.model.support.QmtModel;
import com.lesports.qmt.sbc.api.common.EpisodeType;
import com.lesports.qmt.tlive.api.common.TextLiveType;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * Created by lufei1 on 2016/11/3.
 */
@Document(collection = "episodes")
public class Episode extends QmtModel<Long> {

    private static final long serialVersionUID = -9048819333470825837L;

    //节目名称
    private String name;
    //大项
    @Field("game_f_type")
    private Long gameFType;
    @Field("game_s_type")
    private Long gameSType;
    //赛季id
    private Long csid;
    //赛事id
    private Long cid;
    //关联的比赛id
    private Long mid;
    //生态专辑id,也就是vrs的专辑id
    @Field("leeco_aid")
    private Long leecoAid;
    //专题URL
    @Field("topic_url")
    private String topicUrl;
    //自制专辑id，qmt自身的自制专辑id
    private Long aid;
    //节目类型,比赛,自制，其他
    private EpisodeType type;
    //是否有直播
    @Field("has_live")
    private Boolean hasLive;
    //是否有图文直播
    @Field("has_text_live")
    private Boolean hasTextLive = false;
    //基准状态,如果节目是比赛类型的,以比赛状态为准
    @Field("status")
    private LiveShowStatus status;
    //图文直播的展示状态
    @Field("text_live_status")
    private LiveShowStatus textLiveStatus;
    //直播流信息
    @Field("live_streams")
    private Set<LiveStream> liveStreams = Sets.newHashSet();
    //选集关联的回放视频
    @Field("record_id")
    private Long recordId;
    //相关集锦
    @Field("highlights_id")
    private Long highlightsId;
    //图文直播信息
    @Field("text_lives")
    private Set<SimpleTextLive> textLives = Sets.newHashSet();
    //开始时间,用户数据查询,如果是比赛节目的话,以比赛开始时间
    //为准,如果是专辑的话,以直播开始时间为准
    @Field("start_time")
    private String startTime;
    @Field("end_time")
    private String endTime;
    //精确到天得比赛时间 YYYYmmDD
    @Field("start_date")
    private String startDate;
    //标识是否被删除
    private Boolean deleted;
    //参赛者id
    @Field("competitor_ids")
    private Set<Long> competitorIds = Sets.newHashSet();
    //直播唯一标识（场次ID）
    @Field("live_unique_id")
    private String liveUniqueId;
    //第几期
    private String periods;
    //相关标签id
    @Field("related_ids")
    private Set<Long> relatedIds = Sets.newHashSet();
    //是否在新英付费
    @Field("xinying_pay")
    private Boolean xinyingPay;
    //对应的新英比赛id
    @Field("xinying_match_id")
    private Long xinyingMatchId;
    //新英本场比赛的价格
    private Double price;
    //运营控制的比赛状态,如果为null或者NO_LIVE的话表示不需要运营这个状态
    //最终的比赛状态stauts 根据运营状态和比赛状态综合去计算
    @Field("bg_status")
    private LiveShowStatus opStatus;
    @Field("op_begin_time")
    //运营控制比赛的开始时间,如果为-1则不需要运营这个状态
    private String opBeginTime;
    //TODO：名称缩写 暂无用
    private String abbreviation;
    //节目描述
    private String desc;
    @Field("live_platforms")
    //播放平台
    private Set<Platform> livePlatforms = Sets.newHashSet();
    //是否是滚球精英
    @Field("is_octopus")
    private Boolean isOctopus;
    //滚球精英的matchId
    @Field("octopus_match_id")
    private Long octopusMatchId;
    //聊天室信息
    @Field("chat_room")
    private ChatRoom chatRoom;
    //节目图片，主要使用在无直播流的节目上
    private ImageUrlExt image;
    //pc节目图片，主要使用在pc直播日历
    @Field("pc_image")
    private ImageUrlExt pcImage;
    //tv详情页背景图，主要使用在大乐tv
    @Field("tv_image")
    private ImageUrlExt tvImage;
    //tv比赛大厅广告图片,主要使用在香港tv
    @Field("tv_ad_image")
    private ImageUrlExt tvAdImage;
    //相关内容(人工关联)
    @Field("related_items")
    private List<RelatedItem> relatedItems = Lists.newArrayList();
    @Field("allow_country")
    private CountryCode allowCountry;
    @Field("language_code")
    private LanguageCode languageCode;
    //分享标题
    @Field("share_name")
    private String shareName;
    //分享描述
    @Field("share_desc")
    private String shareDesc;
    //是否可同步到乐视云
    @Field("is_sync_to_cloud")
    private Boolean isSyncToCloud = true;
    //是否跳转
    @Field("has_jump")
    private Boolean hasJump = false;
    //pc跳转url
    @Field("pc_jump_url")
    private String pcJumpUrl;
    //m站跳转url
    @Field("m_jump_url")
    private String mJumpUrl;
    //发布(上线/下线)
    private Boolean online;
    //频道id
    @Field("channel_id")
    private Long channelId;
    @Field("sub_channel_id")
    private Long subChannelId;

    public static class ChatRoom implements Serializable {
        //聊天室id
        @Field("chat_room_id")
        private String chatRoomId;
        //聊天室开始时间
        @Field("start_time")
        private String startTime;
        //聊天室结束时间
        @Field("end_time")
        private String endTime;
        //聊天室开关
        @Transient
        private Boolean isOpen = true;

        public String getChatRoomId() {
            return chatRoomId;
        }

        public void setChatRoomId(String chatRoomId) {
            this.chatRoomId = chatRoomId;
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

        public Boolean getOpen() {
            return isOpen;
        }

        public void setOpen(Boolean isOpen) {
            this.isOpen = isOpen;
        }

        @Override
        public String toString() {
            final StringBuffer sb = new StringBuffer("ChatRoom{");
            sb.append("chatRoomId='").append(chatRoomId).append('\'');
            sb.append(", startTime='").append(startTime).append('\'');
            sb.append(", endTime='").append(endTime).append('\'');
            sb.append(", isOpen=").append(isOpen);
            sb.append('}');
            return sb.toString();
        }
    }

    public Boolean getOnline() {
        return online;
    }

    public void setOnline(Boolean online) {
        this.online = online;
    }

    public Boolean getOctopus() {
        return isOctopus;
    }

    public void setOctopus(Boolean octopus) {
        isOctopus = octopus;
    }


    public Boolean getSyncToCloud() {
        return isSyncToCloud;
    }

    public void setSyncToCloud(Boolean syncToCloud) {
        isSyncToCloud = syncToCloud;
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

    public Boolean getIsSyncToCloud() {
        return isSyncToCloud;
    }

    public void setIsSyncToCloud(Boolean isSyncToCloud) {
        this.isSyncToCloud = isSyncToCloud;
    }

    public String getShareName() {
        return shareName;
    }

    public void setShareName(String shareName) {
        this.shareName = shareName;
    }

    public LanguageCode getLanguageCode() {
        return languageCode;
    }

    public void setLanguageCode(LanguageCode languageCode) {
        this.languageCode = languageCode;
    }

    public CountryCode getAllowCountry() {
        return allowCountry;
    }

    public void setAllowCountry(CountryCode allowCountry) {
        this.allowCountry = allowCountry;
    }

    public LiveShowStatus getTextLiveStatus() {
        return textLiveStatus;
    }

    public void setTextLiveStatus(LiveShowStatus textLiveStatus) {
        this.textLiveStatus = textLiveStatus;
    }

    public Set<Platform> getLivePlatforms() {
        return livePlatforms;
    }

    public void setLivePlatforms(Set<Platform> livePlatforms) {
        this.livePlatforms = livePlatforms;
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

    public LiveShowStatus getOpStatus() {
        return opStatus;
    }

    public void setOpStatus(LiveShowStatus opStatus) {
        this.opStatus = opStatus;
    }

    public String getPeriods() {
        return periods;
    }

    public void setPeriods(String periods) {
        this.periods = periods;
    }

    public Long getMid() {
        return mid;
    }

    public void setMid(Long mid) {
        this.mid = mid;
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

    public Set<LiveStream> getLiveStreams() {
        return liveStreams;
    }

    public void setLiveStreams(Set<LiveStream> liveStreams) {
        this.liveStreams = liveStreams;
    }

    public void addLiveStream(LiveStream liveStream) {
        if (this.liveStreams.contains(liveStream)) {
            this.liveStreams.remove(liveStream);
        }
        this.liveStreams.add(liveStream);
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getCsid() {
        return csid;
    }

    public void setCsid(Long csid) {
        this.csid = csid;
    }

    public Long getCid() {
        return cid;
    }

    public void setCid(Long cid) {
        this.cid = cid;
    }

    public LiveShowStatus getStatus() {
        return status;
    }

    public void setStatus(LiveShowStatus status) {
        this.status = status;
    }

    public Boolean getHasLive() {
        return hasLive;
    }

    public void setHasLive(Boolean hasLive) {
        this.hasLive = hasLive;
    }

    public Long getGameFType() {
        return gameFType;
    }

    public void setGameFType(Long gameFType) {
        this.gameFType = gameFType;
    }

    public Long getGameSType() {
        return gameSType;
    }

    public void setGameSType(Long gameSType) {
        this.gameSType = gameSType;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public Set<Long> getCompetitorIds() {
        return competitorIds;
    }

    public void setCompetitorIds(Set<Long> competitorIds) {
        this.competitorIds = competitorIds;
    }

    public void addCompetitorId(Long competitorId) {
        this.competitorIds.add(competitorId);
    }

    public String getLiveUniqueId() {
        return liveUniqueId;
    }

    public void setLiveUniqueId(String liveUniqueId) {
        this.liveUniqueId = liveUniqueId;
    }

    public Long getHighlightsId() {
        return highlightsId;
    }

    public void setHighlightsId(Long highlightsId) {
        this.highlightsId = highlightsId;
    }

    public Set<Long> getRelatedIds() {
        return relatedIds;
    }

    public void setRelatedIds(Set<Long> relatedIds) {
        this.relatedIds = relatedIds;
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

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getDesc() {
        return desc;
    }

    public Set<SimpleTextLive> getTextLives() {
        return textLives;
    }

    public void setTextLives(Set<SimpleTextLive> textLives) {
        this.textLives = textLives;
    }

    public Boolean getHasTextLive() {
        return hasTextLive;
    }

    public void setHasTextLive(Boolean hasTextLive) {
        this.hasTextLive = hasTextLive;
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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public ChatRoom getChatRoom() {
        return chatRoom;
    }

    public void setChatRoom(ChatRoom chatRoom) {
        this.chatRoom = chatRoom;
    }

    public ImageUrlExt getImage() {
        return image;
    }

    public void setImage(ImageUrlExt image) {
        this.image = image;
    }

    public ImageUrlExt getPcImage() {
        return pcImage;
    }

    public void setPcImage(ImageUrlExt pcImage) {
        this.pcImage = pcImage;
    }

    public ImageUrlExt getTvImage() {
        return tvImage;
    }

    public void setTvImage(ImageUrlExt tvImage) {
        this.tvImage = tvImage;
    }

    public ImageUrlExt getTvAdImage() {
        return tvAdImage;
    }

    public void setTvAdImage(ImageUrlExt tvAdImage) {
        this.tvAdImage = tvAdImage;
    }

    public String getShareDesc() {
        return shareDesc;
    }

    public void setShareDesc(String shareDesc) {
        this.shareDesc = shareDesc;
    }

    public Long getChannelId() {
        return channelId;
    }

    public void setChannelId(Long channelId) {
        this.channelId = channelId;
    }

    public Long getSubChannelId() {
        return subChannelId;
    }

    public void setSubChannelId(Long subChannelId) {
        this.subChannelId = subChannelId;
    }

    public List<RelatedItem> getRelatedItems() {
        return relatedItems;
    }

    public void setRelatedItems(List<RelatedItem> relatedItems) {
        this.relatedItems = relatedItems;
    }

    public Long getRecordId() {
        return recordId;
    }

    public void setRecordId(Long recordId) {
        this.recordId = recordId;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Episode{");
        sb.append("name='").append(name).append('\'');
        sb.append(", gameFType=").append(gameFType);
        sb.append(", gameSType=").append(gameSType);
        sb.append(", csid=").append(csid);
        sb.append(", cid=").append(cid);
        sb.append(", mid=").append(mid);
        sb.append(", leecoAid=").append(leecoAid);
        sb.append(", topicUrl=").append(topicUrl);
        sb.append(", aid=").append(aid);
        sb.append(", type=").append(type);
        sb.append(", hasLive=").append(hasLive);
        sb.append(", hasTextLive=").append(hasTextLive);
        sb.append(", status=").append(status);
        sb.append(", textLiveStatus=").append(textLiveStatus);
        sb.append(", liveStreams=").append(liveStreams);
        sb.append(", recordId=").append(recordId);
        sb.append(", highlightsId=").append(highlightsId);
        sb.append(", textLives=").append(textLives);
        sb.append(", startTime='").append(startTime).append('\'');
        sb.append(", endTime='").append(endTime).append('\'');
        sb.append(", startDate='").append(startDate).append('\'');
        sb.append(", deleted=").append(deleted);
        sb.append(", competitorIds=").append(competitorIds);
        sb.append(", liveUniqueId='").append(liveUniqueId).append('\'');
        sb.append(", periods='").append(periods).append('\'');
        sb.append(", relatedIds=").append(relatedIds);
        sb.append(", xinyingPay=").append(xinyingPay);
        sb.append(", xinyingMatchId=").append(xinyingMatchId);
        sb.append(", price=").append(price);
        sb.append(", opStatus=").append(opStatus);
        sb.append(", opBeginTime='").append(opBeginTime).append('\'');
        sb.append(", abbreviation='").append(abbreviation).append('\'');
        sb.append(", desc='").append(desc).append('\'');
        sb.append(", livePlatforms=").append(livePlatforms);
        sb.append(", isOctopus=").append(isOctopus);
        sb.append(", octopusMatchId=").append(octopusMatchId);
        sb.append(", chatRoom=").append(chatRoom);
        sb.append(", image=").append(image);
        sb.append(", pcImage=").append(pcImage);
        sb.append(", tvImage=").append(tvImage);
        sb.append(", tvAdImage=").append(tvAdImage);
        sb.append(", relatedItems=").append(relatedItems);
        sb.append(", allowCountry=").append(allowCountry);
        sb.append(", languageCode=").append(languageCode);
        sb.append(", shareName='").append(shareName).append('\'');
        sb.append(", shareDesc='").append(shareDesc).append('\'');
        sb.append(", isSyncToCloud=").append(isSyncToCloud);
        sb.append(", hasJump=").append(hasJump);
        sb.append(", pcJumpUrl='").append(pcJumpUrl).append('\'');
        sb.append(", mJumpUrl='").append(mJumpUrl).append('\'');
        sb.append(", online=").append(online);
        sb.append(", channelId=").append(channelId);
        sb.append(", subChannelId=").append(subChannelId);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Episode)) return false;

        Episode episode = (Episode) o;

        if (id != null ? !id.equals(episode.id) : episode.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    public static class OperateRelated implements Serializable {
        private static final long serialVersionUID = -7139298903916601950L;
        @Field("vid")
        private Long vid;
        @Field("order")
        private Integer order;

        public Long getVid() {
            return vid;
        }

        public void setVid(Long vid) {
            this.vid = vid;
        }

        public Integer getOrder() {
            return order;
        }

        public void setOrder(Integer order) {
            this.order = order;
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("OperateRelated{");
            sb.append("vid=").append(vid);
            sb.append(", order=").append(order);
            sb.append('}');
            return sb.toString();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            OperateRelated that = (OperateRelated) o;

            if (vid != null ? !vid.equals(that.vid) : that.vid != null) return false;

            return true;
        }

        @Override
        public int hashCode() {
            return vid != null ? vid.hashCode() : 0;
        }
    }

    /**
     * 直播流信息
     */
    public static class LiveStream implements Serializable {
        private static final long serialVersionUID = -6499515811866888610L;
        //直播流id
        private Long id;
        //发布客户端平台
        @Field("play_platforms")
        private Set<Long> playPlatforms;
        //是否收费 0是否  1是收费
        @Field("is_pay")
        private Boolean isPay;
        //付费客户端平台
        @Field("pay_platforms")
        private Set<Long> payPlatforms;
        //直播流的展示顺序
        private Integer order;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public Integer getOrder() {
            return order;
        }

        public void setOrder(Integer order) {
            this.order = order;
        }

        public Set<Long> getPlayPlatforms() {
            return playPlatforms;
        }

        public void setPlayPlatforms(Set<Long> playPlatforms) {
            this.playPlatforms = playPlatforms;
        }

        public Boolean getIsPay() {
            return isPay;
        }

        public void setIsPay(Boolean isPay) {
            this.isPay = isPay;
        }

        public Set<Long> getPayPlatforms() {
            return payPlatforms;
        }

        public void setPayPlatforms(Set<Long> payPlatforms) {
            this.payPlatforms = payPlatforms;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof LiveStream)) return false;

            LiveStream that = (LiveStream) o;

            if (id != null ? !id.equals(that.id) : that.id != null) return false;

            return true;
        }

        @Override
        public int hashCode() {
            return id != null ? id.hashCode() : 0;
        }
    }

    /**
     * 简单图文直播
     */
    public static class SimpleTextLive implements Serializable {
        private static final long serialVersionUID = 3594645990780885057L;
        //图文直播id
        @Field("text_live_id")
        private Long textLiveId;
        //图文直播类型
        private TextLiveType type;

        public Long getTextLiveId() {
            return textLiveId;
        }

        public void setTextLiveId(Long textLiveId) {
            this.textLiveId = textLiveId;
        }

        public TextLiveType getType() {
            return type;
        }

        public void setType(TextLiveType type) {
            this.type = type;
        }

        @Override
        public String toString() {
            return "SimpleTextLive{" +
                    "textLiveId=" + textLiveId +
                    ", type=" + type +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            SimpleTextLive that = (SimpleTextLive) o;

            if (textLiveId != null ? !textLiveId.equals(that.textLiveId) : that.textLiveId != null) return false;

            return true;
        }

        @Override
        public int hashCode() {
            return textLiveId != null ? textLiveId.hashCode() : 0;
        }
    }


    public static class SimpleActivity implements Serializable {
        private static final long serialVersionUID = -3681037651940207531L;

        //推广位ID
        @Field("activity_id")
        private Long activityId;
        //展示在节目列表页还是详情页,true:列表页,false:详情页
        @Field("in_list")
        private Boolean inList;

        public Boolean getInList() {
            return inList;
        }

        public void setInList(Boolean inList) {
            this.inList = inList;
        }

        public Long getActivityId() {
            return activityId;
        }

        public void setActivityId(Long activityId) {
            this.activityId = activityId;
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("Activity{");
            sb.append("activityId=").append(activityId);
            sb.append(", inList=").append(inList);
            sb.append('}');
            return sb.toString();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            SimpleActivity activity = (SimpleActivity) o;

            if (activityId != null ? !activityId.equals(activity.activityId) : activity.activityId != null)
                return false;
            return inList != null ? inList.equals(activity.inList) : activity.inList == null;

        }

        @Override
        public int hashCode() {
            int result = activityId != null ? activityId.hashCode() : 0;
            result = 31 * result + (inList != null ? inList.hashCode() : 0);
            return result;
        }
    }
}
