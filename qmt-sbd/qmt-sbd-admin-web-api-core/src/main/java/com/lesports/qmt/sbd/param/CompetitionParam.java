package com.lesports.qmt.sbd.param;

import com.lesports.api.common.CountryCode;
import com.lesports.qmt.sbd.api.common.GroundOrder;
import com.lesports.qmt.sbd.api.common.MatchSystem;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by lufei1 on 2016/11/8.
 */
public class CompetitionParam {

    private long id;
    //赛事名称
    @NotBlank(message = "name is required")
    private String name;
    //大项
    @NotNull(message = "gameFType is required")
    private Long gameFType;
    //频道ID
    private Long channelId;
    //项目ID 仅当频道为综合体育时可用
    private Long subChannelId;
    //是否对阵,默认是对阵
    private Boolean vs;
    //主客队顺序,默认先主后客
    private GroundOrder groundOrder;
    //官方网站
    private String officialUrl;
    //赛事logo
    private String logo;
    //赛事类型,杯赛,联赛
    private MatchSystem system;
    //赛事名称缩写
    private String abbreviation;
    //简介
    private String introduction;
    //洲
    private String continent;
    //国家
    private String nation;
    //当前最新赛季
    private String currentSeason;
    //允许展示的国家
    private List<CountryCode> allowCountries;
    //该地区是否付费
    private List<CountryCode> chargeableCountries;


    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public Boolean getVs() {
        return vs;
    }

    public void setVs(Boolean vs) {
        this.vs = vs;
    }

    public GroundOrder getGroundOrder() {
        return groundOrder;
    }

    public void setGroundOrder(GroundOrder groundOrder) {
        this.groundOrder = groundOrder;
    }

    public String getOfficialUrl() {
        return officialUrl;
    }

    public void setOfficialUrl(String officialUrl) {
        this.officialUrl = officialUrl;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public MatchSystem getSystem() {
        return system;
    }

    public void setSystem(MatchSystem system) {
        this.system = system;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getContinent() {
        return continent;
    }

    public void setContinent(String continent) {
        this.continent = continent;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getCurrentSeason() {
        return currentSeason;
    }

    public void setCurrentSeason(String currentSeason) {
        this.currentSeason = currentSeason;
    }

    public List<CountryCode> getAllowCountries() {
        return allowCountries;
    }

    public void setAllowCountries(List<CountryCode> allowCountries) {
        this.allowCountries = allowCountries;
    }

    public List<CountryCode> getChargeableCountries() {
        return chargeableCountries;
    }

    public void setChargeableCountries(List<CountryCode> chargeableCountries) {
        this.chargeableCountries = chargeableCountries;
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

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("CompetitionParam{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", gameFType=").append(gameFType);
        sb.append(", channelId=").append(channelId);
        sb.append(", subChannelId=").append(subChannelId);
        sb.append(", vs=").append(vs);
        sb.append(", groundOrder=").append(groundOrder);
        sb.append(", officialUrl='").append(officialUrl).append('\'');
        sb.append(", logo='").append(logo).append('\'');
        sb.append(", system=").append(system);
        sb.append(", abbreviation='").append(abbreviation).append('\'');
        sb.append(", introduction='").append(introduction).append('\'');
        sb.append(", continent='").append(continent).append('\'');
        sb.append(", nation='").append(nation).append('\'');
        sb.append(", currentSeason='").append(currentSeason).append('\'');
        sb.append(", allowCountries=").append(allowCountries);
        sb.append(", chargeableCountries=").append(chargeableCountries);
        sb.append('}');
        return sb.toString();
    }
}
