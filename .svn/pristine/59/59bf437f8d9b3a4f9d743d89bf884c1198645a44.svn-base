package com.lesports.qmt.sbd.model;

import com.lesports.api.common.CountryCode;
import com.lesports.api.common.LangString;
import com.lesports.qmt.model.support.QmtModel;
import com.lesports.qmt.sbd.api.common.TimeSort;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

/**
 * 比赛实况
 * Created by yangyu on 2015/5/30.
 */
@Document(collection = "match_actions")
public class MatchAction extends QmtModel<Long> {

    private static final long serialVersionUID = 8175164267546398049L;


    //比赛id
    private Long mid;
    //队伍id
    private Long tid;
    //当前场上队员id
    @Field("first_pid")
    private Long firstPid;
    //被换下球员Id
    @Field("second_pid")
    private Long secondPid;
    //助攻球员Id
    @Field("third_pid")
    private Long thirdPid;
    //比赛进行的阶段，节的字典idXQ
    private Long section;
    //阶段进行的时间,精确到秒
    private double time;
    //比赛进行的时间,精确到秒
    @Field("passed_time")
    private double passedTime;
    //比赛时间顺序
    private TimeSort sort;
    //liveEventId,行为类型,如射门,红牌，黄牌，得分,投中，未投中，失误，犯规，违规
    @Field("event_type")
    private Long eventType;
    //liveEventId行为类型,如 射门（点球，头球，乌龙球）三分投中，三分未命中，跳投投中，跳投未中，24秒违规，3秒违规
    @Field("event_detail_type")
    private Long eventDetailType;
    //事件发生的场上坐标轴，x
    @Field("coordinate_x")
    private String coordinateX;
    //事件发生的场上坐标轴，y
    @Field("coordinate_y")
    private String coordinateY;
    //json 格式的事件内容
    private String content;
    //多语言事件内容
    @Field("multi_lang_content")
    private List<LangString> multiLangContent;
    //允许展示的国家
    @Field("allow_countries")
    private List<CountryCode> allowCountries;
    private List<Partner> partners;

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


    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getTid() {
        return tid;
    }

    public void setTid(Long tid) {
        this.tid = tid;
    }

    public Long getFirstPid() {
        return firstPid;
    }

    public void setFirstPid(Long firstPid) {
        this.firstPid = firstPid;
    }

    public Long getSecondPid() {
        return secondPid;
    }

    public void setSecondPid(Long secondPid) {
        this.secondPid = secondPid;
    }

    public Long getThirdPid() {
        return thirdPid;
    }

    public void setThirdPid(Long thirdPid) {
        this.thirdPid = thirdPid;
    }

    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = time;
    }

    public double getPassedTime() {
        return passedTime;
    }

    public void setPassedTime(double passedTime) {
        this.passedTime = passedTime;
    }

    public TimeSort getSort() {
        return sort;
    }

    public void setSort(TimeSort sort) {
        this.sort = sort;
    }

    public Long getEventType() {
        return eventType;
    }

    public void setEventType(Long eventType) {
        this.eventType = eventType;
    }

    public Long getEventDetailType() {
        return eventDetailType;
    }

    public void setEventDetailType(Long eventDetailType) {
        this.eventDetailType = eventDetailType;
    }

    public List<CountryCode> getAllowCountries() {
        return allowCountries;
    }

    public void setAllowCountries(List<CountryCode> allowCountries) {
        this.allowCountries = allowCountries;
    }

    public List<Partner> getPartners() {
        return partners;
    }

    public void setPartners(List<Partner> partners) {
        this.partners = partners;
    }


    public Long getSection() {
        return section;
    }

    public void setSection(Long section) {
        this.section = section;
    }


    public String getCoordinateX() {
        return coordinateX;
    }

    public void setCoordinateX(String coordinateX) {
        this.coordinateX = coordinateX;
    }

    public String getCoordinateY() {
        return coordinateY;
    }

    public void setCoordinateY(String coordinateY) {
        this.coordinateY = coordinateY;
    }


    public List<LangString> getMultiLangContent() {
        return multiLangContent;
    }

    public void setMultiLangContent(List<LangString> multiLangContent) {
        this.multiLangContent = multiLangContent;
    }

    @Override
    public String toString() {
        return "MatchAction{" +
                "mid=" + mid +
                ", tid=" + tid +
                ", firstPid=" + firstPid +
                ", secondPid=" + secondPid +
                ", thirdPid=" + thirdPid +
                ", section=" + section +
                ", time=" + time +
                ", passedTime=" + passedTime +
                ", sort=" + sort +
                ", eventType=" + eventType +
                ", eventDetailType=" + eventDetailType +
                ", coordinateX='" + coordinateX + '\'' +
                ", coordinateY='" + coordinateY + '\'' +
                ", content='" + content + '\'' +
                ", multiLangContent=" + multiLangContent +
                ", allowCountries=" + allowCountries +
                ", partners=" + partners +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MatchAction)) return false;

        MatchAction that = (MatchAction) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
