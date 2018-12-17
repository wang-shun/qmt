package com.lesports.qmt.sbd.param;

import com.lesports.api.common.CounString;
import com.lesports.qmt.sbd.api.common.TeamType;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by lufei1 on 2016/11/16.
 */
public class TeamParam {

    private long id;
    //球队名称
    @NotBlank(message = "name is required")
    private String name;
    //国籍
    private Long countryId;
    //球队名称缩写
    private String abbreviation;
    //大项
    @NotNull(message = "gameFType is required")
    private Long gameFType;
    //球队类型(1:俱乐部，2：国家队)
    @NotNull(message = "teamType is required")
    private TeamType teamType;
    //主场
    private String homeGround;
    //城市
    private String city;
    //联盟
    private Long conference;
    //分区
    private Long region;
    //球队logo
    private String logo;
    //球队logo
    private String superLogo;
    //球队logo
    private String bgLogo;
    //按版权地区划分的logo
    private List<CounString> multiCounLogos;
    //球队描述
    private String desc;
    //所获荣誉
    private String honors;

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

    public Long getCountryId() {
        return countryId;
    }

    public void setCountryId(Long countryId) {
        this.countryId = countryId;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public Long getGameFType() {
        return gameFType;
    }

    public void setGameFType(Long gameFType) {
        this.gameFType = gameFType;
    }

    public TeamType getTeamType() {
        return teamType;
    }

    public void setTeamType(TeamType teamType) {
        this.teamType = teamType;
    }

    public String getHomeGround() {
        return homeGround;
    }

    public void setHomeGround(String homeGround) {
        this.homeGround = homeGround;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Long getConference() {
        return conference;
    }

    public void setConference(Long conference) {
        this.conference = conference;
    }

    public Long getRegion() {
        return region;
    }

    public void setRegion(Long region) {
        this.region = region;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public List<CounString> getMultiCounLogos() {
        return multiCounLogos;
    }

    public void setMultiCounLogos(List<CounString> multiCounLogos) {
        this.multiCounLogos = multiCounLogos;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getHonors() {
        return honors;
    }

    public void setHonors(String honors) {
        this.honors = honors;
    }

    public String getSuperLogo() {
        return superLogo;
    }

    public void setSuperLogo(String superLogo) {
        this.superLogo = superLogo;
    }

    public String getBgLogo() {
        return bgLogo;
    }

    public void setBgLogo(String bgLogo) {
        this.bgLogo = bgLogo;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("TeamParam{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", countryId=").append(countryId);
        sb.append(", abbreviation='").append(abbreviation).append('\'');
        sb.append(", gameFType=").append(gameFType);
        sb.append(", teamType=").append(teamType);
        sb.append(", homeGround='").append(homeGround).append('\'');
        sb.append(", city='").append(city).append('\'');
        sb.append(", conference=").append(conference);
        sb.append(", region=").append(region);
        sb.append(", logo='").append(logo).append('\'');
        sb.append(", superLogo='").append(superLogo).append('\'');
        sb.append(", bgLogo='").append(bgLogo).append('\'');
        sb.append(", multiCounLogos=").append(multiCounLogos);
        sb.append(", desc='").append(desc).append('\'');
        sb.append(", honors='").append(honors).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
