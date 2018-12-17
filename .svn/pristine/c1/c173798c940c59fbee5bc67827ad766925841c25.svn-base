package com.lesports.qmt.tlive.model;

import com.lesports.qmt.model.support.QmtModel;
import com.lesports.qmt.tlive.api.common.TextLiveMessageType;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;

/**
 * 图文直播信息表
 * Created by lufei1 on 2015/9/13.
 */
@Document(collection = "text_live_messages")
public class TextLiveMessage extends QmtModel<Long> {
    private static final long serialVersionUID = 7394313421543444143L;

    //图文直播id
    @Field("text_live_id")
    private Long textLiveId;
    //消息类型
    @Field("type")
    private TextLiveMessageType type;
    //消息内容
    private String content;
    //节的字典id
    private Long section;
    //比赛时间
    private String time;
    //简单直播员信息
    private SimpleAnchor anchor;
    //比赛结果
    @Field("match_result")
    private String matchResult;
    //节目id
    private long eid;
    //比赛id
    private long mid;
    //第三方来源
    @Field("partner_type")
    private Integer partnerType;
    //第三方消息id
    @Field("partner_id")
    private String partnerId;
    //是否被删除
    private Boolean deleted;


    /**
     * 直播员信息
     */
    public static class SimpleAnchor implements Serializable {
        private static final long serialVersionUID = 7002699633751824120L;

        //直播员昵称
        private String name;
        //直播员头像
        private String logo;

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

        @Override
        public String toString() {
            return "SimpleAnchor{" +
                    "name='" + name + '\'' +
                    ", logo='" + logo + '\'' +
                    '}';
        }
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTextLiveId() {
        return textLiveId;
    }

    public void setTextLiveId(Long textLiveId) {
        this.textLiveId = textLiveId;
    }

    public TextLiveMessageType getType() {
        return type;
    }

    public void setType(TextLiveMessageType type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getSection() {
        return section;
    }

    public void setSection(Long section) {
        this.section = section;
    }

    public String getMatchResult() {
        return matchResult;
    }

    public void setMatchResult(String matchResult) {
        this.matchResult = matchResult;
    }

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }

    public String getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(String updateAt) {
        this.updateAt = updateAt;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public SimpleAnchor getAnchor() {
        return anchor;
    }

    public void setAnchor(SimpleAnchor anchor) {
        this.anchor = anchor;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public long getEid() {
        return eid;
    }

    public void setEid(long eid) {
        this.eid = eid;
    }

    public long getMid() {
        return mid;
    }

    public void setMid(long mid) {
        this.mid = mid;
    }

    public Integer getPartnerType() {
        return partnerType;
    }

    public void setPartnerType(Integer partnerType) {
        this.partnerType = partnerType;
    }

    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    @Override
    public String toString() {
        return "TextLiveMessage{" +
                "id=" + id +
                ", textLiveId=" + textLiveId +
                ", type=" + type +
                ", content='" + content + '\'' +
                ", section=" + section +
                ", time='" + time + '\'' +
                ", anchor=" + anchor +
                ", matchResult='" + matchResult + '\'' +
                ", eid=" + eid +
                ", mid=" + mid +
                ", partnerType=" + partnerType +
                ", partnerId='" + partnerId + '\'' +
                ", createAt='" + createAt + '\'' +
                ", updateAt='" + updateAt + '\'' +
                ", deleted=" + deleted +
                '}';
    }
}
