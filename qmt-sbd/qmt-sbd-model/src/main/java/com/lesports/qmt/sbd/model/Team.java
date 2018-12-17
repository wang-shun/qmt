package com.lesports.qmt.sbd.model;

import com.google.common.collect.Sets;
import com.lesports.api.common.*;
import com.lesports.qmt.model.support.QmtModel;
import com.lesports.qmt.sbd.api.common.TeamType;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * User: ellios
 * Time: 15-4-22 : 上午9:31
 */
@Document(collection = "teams")
public class Team extends QmtModel<Long> {
    private static final long serialVersionUID = 3774903684898162625L;


    //球队参加的赛事
    private Set<Long> cids = Sets.newHashSet();
    //首字母
    @Field("first_letter")
    private String firstLetter;
    //球队名称
    private String name;
    //多语言球队名称
    @Field("multi_lang_names")
    private List<LangString> multiLangNames;
    //国籍
    @Field("country_id")
    private Long countryId;
    //球队名称缩写
    private String abbreviation;
    //多语言球队名称缩写
    @Field("multi_lang_abbrs")
    private List<LangString> multiLangAbbrs;
    //昵称
    private String nickname;
    //多语言昵称
    @Field("multi_lang_nicknames")
    private List<LangString> multiLangNicknames;
    //大项
    @Field("game_f_type")
    private Long gameFType;
    //球队类型(1:俱乐部，2：国家队)
    @Field("team_type")
    private TeamType teamType;
    //是否被删除
    private Boolean deleted = false;
    //主场
    @Field("home_ground")
    private String homeGround;
    //多语言主场
    @Field("multi_lang_home_grounds")
    private List<LangString> multiLangHomeGrounds;
    //球队成立时间
    @Field("setup_time")
    private String setupTime;
    //城市
    private String city;
    @Field("multi_lang_cities")
    private List<LangString> multiLangCities;
    //联盟
    private Long conference;
    //分区
    private Long region;
    //球队logo
    @Field("logo")
    private ImageUrlExt logo;
    //按版权地区划分的logo
    @Field("multi_coun_logos")
    private List<CounString> multiCounLogos;
    //球队描述
    private String desc;
    @Field("multi_lang_desc")
    private List<LangString> multiLangDesc;
    //允许展示的国家
    @Field("allow_countries")
    private List<CountryCode> allowCountries;
    //该语言是否已上线
    @Field("online_languages")
    private List<LanguageCode> onlineLanguages;
    //当前参加的赛事id
    @Field("current_cid")
    private Long currentCid;
    //当前参加的赛季id
    @Field("current_csid")
    private Long currentCsid;
    //当前teamSeasonId
    @Field("current_tsid")
    private Long currentTsid;
    //总冠军次数
    @Field("championship_num")
    private Integer championshipNum;
    //所获荣誉
    private List<String> honors;
    //所获荣誉国际化
    @Field("multi_lang_honors")
    private Map<LanguageCode, List<String>> multiLangHonors;
    //世界排名
    private List<Rank> ranks;
    //第三方数据信息
    private List<Partner> partners;
    //TODO:以下为运营字段
    //球队png图
    @Field("super_logo")
    private ImageUrlExt superLogo;
    //背板图
    @Field("bg_logo")
    private ImageUrlExt bgLogo;
    //球队被关注的地区
    @Field("is_focused_countries")
    private List<CountryCode> isFocusedCountries;
    //阵营id
    @Field("camp_id")
    private String campId;

    /**
     * 排名
     */
    public static class Rank implements Serializable {

        private static final long serialVersionUID = -2495859787777206299L;

        //排名时间
        private String time;
        //排名
        private Integer rank;

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public Integer getRank() {
            return rank;
        }

        public void setRank(Integer rank) {
            this.rank = rank;
        }
    }

    public List<LanguageCode> getOnlineLanguages() {
        return onlineLanguages;
    }

    public void setOnlineLanguages(List<LanguageCode> onlineLanguages) {
        this.onlineLanguages = onlineLanguages;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCountryId() {
        return countryId;
    }

    public void setCountryId(Long countryId) {
        this.countryId = countryId;
    }

    public Set<Long> getCids() {
        return cids;
    }

    public void setCids(Set<Long> cids) {
        this.cids = cids;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
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

    public String getFirstLetter() {
        return firstLetter;
    }

    public void setFirstLetter(String firstLetter) {
        this.firstLetter = firstLetter;
    }

    public ImageUrlExt getSuperLogo() {
        return superLogo;
    }

    public void setSuperLogo(ImageUrlExt superLogo) {
        this.superLogo = superLogo;
    }

    public ImageUrlExt getBgLogo() {
        return bgLogo;
    }

    public void setBgLogo(ImageUrlExt bgLogo) {
        this.bgLogo = bgLogo;
    }

    public List<LangString> getMultiLangNames() {
        return multiLangNames;
    }

    public void setMultiLangNames(List<LangString> multiLangNames) {
        this.multiLangNames = multiLangNames;
    }

    public List<LangString> getMultiLangAbbrs() {
        return multiLangAbbrs;
    }

    public void setMultiLangAbbrs(List<LangString> multiLangAbbrs) {
        this.multiLangAbbrs = multiLangAbbrs;
    }

    public List<LangString> getMultiLangNicknames() {
        return multiLangNicknames;
    }

    public void setMultiLangNicknames(List<LangString> multiLangNicknames) {
        this.multiLangNicknames = multiLangNicknames;
    }

    public List<LangString> getMultiLangHomeGrounds() {
        return multiLangHomeGrounds;
    }

    public void setMultiLangHomeGrounds(List<LangString> multiLangHomeGrounds) {
        this.multiLangHomeGrounds = multiLangHomeGrounds;
    }

    public List<LangString> getMultiLangCities() {
        return multiLangCities;
    }

    public void setMultiLangCities(List<LangString> multiLangCities) {
        this.multiLangCities = multiLangCities;
    }

    public List<LangString> getMultiLangDesc() {
        return multiLangDesc;
    }

    public void setMultiLangDesc(List<LangString> multiLangDesc) {
        this.multiLangDesc = multiLangDesc;
    }

    public List<CountryCode> getAllowCountries() {
        return allowCountries;
    }

    public void setAllowCountries(List<CountryCode> allowCountries) {
        this.allowCountries = allowCountries;
    }

    public String getCampId() {
        return campId;
    }

    public void setCampId(String campId) {
        this.campId = campId;
    }

    public List<CountryCode> getIsFocusedCountries() {
        return isFocusedCountries;
    }

    public void setIsFocusedCountries(List<CountryCode> isFocusedCountries) {
        this.isFocusedCountries = isFocusedCountries;
    }

    public String getSetupTime() {
        return setupTime;
    }

    public void setSetupTime(String setupTime) {
        this.setupTime = setupTime;
    }

    public ImageUrlExt getLogo() {
        return logo;
    }

    public void setLogo(ImageUrlExt logo) {
        this.logo = logo;
    }

    public List<CounString> getMultiCounLogos() {
        return multiCounLogos;
    }

    public void setMultiCounLogos(List<CounString> multiCounLogos) {
        this.multiCounLogos = multiCounLogos;
    }

    public Long getCurrentCid() {
        return currentCid;
    }

    public void setCurrentCid(Long currentCid) {
        this.currentCid = currentCid;
    }

    public Long getCurrentCsid() {
        return currentCsid;
    }

    public void setCurrentCsid(Long currentCsid) {
        this.currentCsid = currentCsid;
    }

    public Long getCurrentTsid() {
        return currentTsid;
    }

    public void setCurrentTsid(Long currentTsid) {
        this.currentTsid = currentTsid;
    }

    public Integer getChampionshipNum() {
        return championshipNum;
    }

    public void setChampionshipNum(Integer championshipNum) {
        this.championshipNum = championshipNum;
    }

    public List<String> getHonors() {
        return honors;
    }

    public void setHonors(List<String> honors) {
        this.honors = honors;
    }

    public Map<LanguageCode, List<String>> getMultiLangHonors() {
        return multiLangHonors;
    }

    public void setMultiLangHonors(Map<LanguageCode, List<String>> multiLangHonors) {
        this.multiLangHonors = multiLangHonors;
    }

    public List<Rank> getRanks() {
        return ranks;
    }

    public void setRanks(List<Rank> ranks) {
        this.ranks = ranks;
    }

    public List<Partner> getPartners() {
        return partners;
    }

    public void setPartners(List<Partner> partners) {
        this.partners = partners;
    }

    //can not delete
    //fix error when deserialize id using fastjson because of the method in BaseModel
    public void setHelperId(Long helperId) {
        setId(helperId);
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Team{");
        sb.append("cids=").append(cids);
        sb.append(", firstLetter='").append(firstLetter).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", multiLangNames=").append(multiLangNames);
        sb.append(", countryId=").append(countryId);
        sb.append(", abbreviation='").append(abbreviation).append('\'');
        sb.append(", multiLangAbbrs=").append(multiLangAbbrs);
        sb.append(", nickname='").append(nickname).append('\'');
        sb.append(", multiLangNicknames=").append(multiLangNicknames);
        sb.append(", gameFType=").append(gameFType);
        sb.append(", teamType=").append(teamType);
        sb.append(", deleted=").append(deleted);
        sb.append(", homeGround='").append(homeGround).append('\'');
        sb.append(", multiLangHomeGrounds=").append(multiLangHomeGrounds);
        sb.append(", setupTime='").append(setupTime).append('\'');
        sb.append(", city='").append(city).append('\'');
        sb.append(", multiLangCities=").append(multiLangCities);
        sb.append(", conference=").append(conference);
        sb.append(", region=").append(region);
        sb.append(", logo=").append(logo);
        sb.append(", multiCounLogos=").append(multiCounLogos);
        sb.append(", desc='").append(desc).append('\'');
        sb.append(", multiLangDesc=").append(multiLangDesc);
        sb.append(", allowCountries=").append(allowCountries);
        sb.append(", onlineLanguages=").append(onlineLanguages);
        sb.append(", currentCid=").append(currentCid);
        sb.append(", currentCsid=").append(currentCsid);
        sb.append(", currentTsid=").append(currentTsid);
        sb.append(", championshipNum=").append(championshipNum);
        sb.append(", honors=").append(honors);
        sb.append(", multiLangHonors=").append(multiLangHonors);
        sb.append(", ranks=").append(ranks);
        sb.append(", partners=").append(partners);
        sb.append(", superLogo=").append(superLogo);
        sb.append(", bgLogo=").append(bgLogo);
        sb.append(", isFocusedCountries=").append(isFocusedCountries);
        sb.append(", campId='").append(campId).append('\'');
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Team)) return false;

        Team team = (Team) o;

        if (id != null ? !id.equals(team.id) : team.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
