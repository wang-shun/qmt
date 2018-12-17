package com.lesports.qmt.sbd.model;

import com.lesports.api.common.CountryCode;
import com.lesports.api.common.LangString;
import com.lesports.api.common.LanguageCode;
import com.lesports.qmt.model.support.QmtModel;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

/**
 * Created by yangyu on 2015/5/29.
 */
@Document(collection = "competition_seasons")
public class CompetitionSeason extends QmtModel<Long> {

    private static final long serialVersionUID = -2969820022042318072L;

    //赛事id
    private Long cid;
    //赛季信息如:2014
    private String season;
    //是否跨赛季
    @Field("over_season")
    private Boolean overSeason;
    //赛季开始时间
    @Field("start_time")
    private String startTime;
    //赛季结束时间
    @Field("end_time")
    private String endTime;
    //举办城市
    private String city;
    //多语言所在城市
    @Field("multi_lang_cities")
    private List<LangString> multiLangCitys;
    //标识是否被删除
    private Boolean deleted;
    //大项
    @Field("game_f_type")
    private Long gameFType;
    //当前轮次
    @Field("current_round")
    private Integer currentRound;
    //当前轮次的字典id
    @Field("current_round_id")
    private Long currentRoundId;
    //总轮次
    @Field("total_round")
    private Integer totalRound;
    //允许展示的国家
    @Field("allow_countries")
    private List<CountryCode> allowCountries;
    //该语言是否已上线
    @Field("online_languages")
    private List<LanguageCode> onlineLanguages;

    public List<LanguageCode> getOnlineLanguages() {
        return onlineLanguages;
    }

    public void setOnlineLanguages(List<LanguageCode> onlineLanguages) {
        this.onlineLanguages = onlineLanguages;
    }

    public List<CountryCode> getAllowCountries() {
        return allowCountries;
    }

    public void setAllowCountries(List<CountryCode> allowCountries) {
        this.allowCountries = allowCountries;
    }

    public Integer getCurrentRound() {
        return currentRound;
    }

    public void setCurrentRound(Integer currentRound) {
        this.currentRound = currentRound;
    }

    public Integer getTotalRound() {
        return totalRound;
    }

    public void setTotalRound(Integer totalRound) {
        this.totalRound = totalRound;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCid() {
        return cid;
    }

    public void setCid(Long cid) {
        this.cid = cid;
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


    public String getEndTime() {
        return endTime;
    }

    public Boolean getOverSeason() {
        return overSeason;
    }

    public void setOverSeason(Boolean overSeason) {
        this.overSeason = overSeason;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }


    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
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

    public Long getGameFType() {
        return gameFType;
    }

    public void setGameFType(Long gameFType) {
        this.gameFType = gameFType;
    }

    public Long getCurrentRoundId() {
        return currentRoundId;
    }

    public void setCurrentRoundId(Long currentRoundId) {
        this.currentRoundId = currentRoundId;
    }


    public List<LangString> getMultiLangCitys() {
        return multiLangCitys;
    }

    public void setMultiLangCitys(List<LangString> multiLangCitys) {
        this.multiLangCitys = multiLangCitys;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("CompetitionSeason{");
        sb.append("cid=").append(cid);
        sb.append(", season='").append(season).append('\'');
        sb.append(", overSeason=").append(overSeason);
        sb.append(", startTime='").append(startTime).append('\'');
        sb.append(", endTime='").append(endTime).append('\'');
        sb.append(", city='").append(city).append('\'');
        sb.append(", multiLangCitys=").append(multiLangCitys);
        sb.append(", deleted=").append(deleted);
        sb.append(", gameFType=").append(gameFType);
        sb.append(", currentRound=").append(currentRound);
        sb.append(", currentRoundId=").append(currentRoundId);
        sb.append(", totalRound=").append(totalRound);
        sb.append(", allowCountries=").append(allowCountries);
        sb.append(", onlineLanguages=").append(onlineLanguages);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CompetitionSeason)) return false;

        CompetitionSeason that = (CompetitionSeason) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
