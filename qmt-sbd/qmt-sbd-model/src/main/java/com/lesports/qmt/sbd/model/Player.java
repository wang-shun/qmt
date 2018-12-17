package com.lesports.qmt.sbd.model;

import com.google.common.collect.Sets;
import com.lesports.api.common.CountryCode;
import com.lesports.api.common.ImageUrlExt;
import com.lesports.api.common.LangString;
import com.lesports.api.common.LanguageCode;
import com.lesports.qmt.model.support.QmtModel;
import com.lesports.qmt.sbd.api.common.Gender;
import com.lesports.qmt.sbd.api.common.PlayerType;
import com.lesports.qmt.sbd.api.common.TeamType;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by yangyu on 2015/5/27.
 */
@Document(collection = "players")
public class Player extends QmtModel<Long> {
    private static final long serialVersionUID = -3381516751419930840L;


    //球员参加的赛事
    private Set<Long> cids = Sets.newHashSet();
    //首字母
    @Field("first_letter")
    private String firstLetter;
    //球员名称
    private String name;
    //多语言球员名称
    @Field("multi_lang_names")
    private List<LangString> multiLangNames;
    //球员身份类型（教练，或球员）
    private PlayerType type;
    //球员英文名
    @Field("english_name")
    private String englishName;
    //球员简称
    private String abbreviation;
    //多语言球员简称
    @Field("multi_lang_abbrs")
    private List<LangString> multiLangAbbrs;
    //身高
    private Integer height;
    //体重
    private Integer weight;
    //出生日期 格式 yyyyMMdd
    private String birthday;
    //性别
    private Gender gender;
    //国籍Id
    @Field("country_id")
    private Long countryId;
    //出生城市
    private String city;
    //多语言出生城市
    @Field("multi_lang_cities")
    private List<LangString> multiLangCities;
    //出生区域
    private String area;
    //多语言出生地
    @Field("multi_lang_areas")
    private List<LangString> multiLangAreas;
    //项目种类（足球、篮球、网球等）
    @Field("game_f_type")
    private Long gameFType;
    //场上位置
    private Long position;
    //是否被删除
    private Boolean deleted;
    //大头像
    @Field("image")
    private ImageUrlExt image;
    //允许展示的国家
    @Field("allow_countries")
    private List<CountryCode> allowCountries;
    //该语言是否已上线
    @Field("online_languages")
    private List<LanguageCode> onlineLanguages;
    //惯用脚
    @Field("heavy_foot")
    private String heavyFoot;
    @Field("multi_lang_heavy_foot")
    private List<LangString> multiLangHeavyFoot;
    //身价
    @Field("career_value")
    private String careerValue;
    @Field("multi_lang_career_value")
    private List<LangString> multiLangCareerValue;
    //球员简介
    private String desc;
    @Field("multi_lang_desc")
    private List<LangString> multiLangDesc;
    //第三方数据信息
    private List<Partner> partners;
    //所属球队及号码信息（TeamType-PlayingTeam）
    @Field("team_number")
    private Map<TeamType, PlayingTeam> teamNumber;
    //职业生涯
    private List<CareerItem> career;
    //毕业学校
    private String school;
    //NBA球龄
    private String experience;
    //选秀情况
    private String draft;
    @Field("multi_lang_draft_value")
    private List<LangString> multiLangDraftValue;
    //年薪
    private String salary;

    //以下为运营字段
    //超级手机背板图
    @Field("bg_url")
    private ImageUrlExt bgImage;


    /**
     * 效力球队
     */
    public static class PlayingTeam implements Serializable {
        private static final long serialVersionUID = -8912833800131431853L;
        //球队id
        @Field("team_id")
        private Long teamId;
        //号码
        private Integer number;

        public Long getTeamId() {
            return teamId;
        }

        public void setTeamId(Long teamId) {
            this.teamId = teamId;
        }

        public Integer getNumber() {
            return number;
        }

        public void setNumber(Integer number) {
            this.number = number;
        }

        @Override
        public String toString() {
            final StringBuffer sb = new StringBuffer("PlayingTeam{");
            sb.append("teamId=").append(teamId);
            sb.append(", number=").append(number);
            sb.append('}');
            return sb.toString();
        }
    }

    /**
     * 职业生涯
     */
    public static class CareerItem implements Serializable {
        private static final long serialVersionUID = 1568797704609439794L;
        //赛季（字符串年份）
        private String season;
        //球队id
        @Field("team_id")
        private Long teamId;
        @Transient
        private String teamName;

        public Long getTeamId() {
            return teamId;
        }

        public void setTeamId(Long teamId) {
            this.teamId = teamId;
        }

        public String getSeason() {
            return season;
        }

        public void setSeason(String season) {
            this.season = season;
        }

        public String getTeamName() {
            return teamName;
        }

        public void setTeamName(String teamName) {
            this.teamName = teamName;
        }
    }


    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getHeavyFoot() {
        return heavyFoot;
    }

    public void setHeavyFoot(String heavyFoot) {
        this.heavyFoot = heavyFoot;
    }

    public String getCareerValue() {
        return careerValue;
    }

    public void setCareerValue(String careerValue) {
        this.careerValue = careerValue;
    }

    public List<CareerItem> getCareer() {
        return career;
    }

    public void setCareer(List<CareerItem> career) {
        this.career = career;
    }

    public Long getCountryId() {
        return countryId;
    }

    public void setCountryId(Long countryId) {
        this.countryId = countryId;
    }

    public String getEnglishName() {
        return englishName;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }


    public Set<Long> getCids() {
        return cids;
    }

    public void setCids(Set<Long> cids) {
        this.cids = cids;
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

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }


    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public Long getGameFType() {
        return gameFType;
    }

    public void setGameFType(Long gameFType) {
        this.gameFType = gameFType;
    }

    public Long getPosition() {
        return position;
    }

    public void setPosition(Long position) {
        this.position = position;
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

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public String getFirstLetter() {
        return firstLetter;
    }

    public void setFirstLetter(String firstLetter) {
        this.firstLetter = firstLetter;
    }

    public PlayerType getType() {
        return type;
    }

    public void setType(PlayerType type) {
        this.type = type;
    }

    public List<LangString> getMultiLangNames() {
        return multiLangNames;
    }

    public void setMultiLangNames(List<LangString> multiLangNames) {
        this.multiLangNames = multiLangNames;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public List<LangString> getMultiLangAbbrs() {
        return multiLangAbbrs;
    }

    public void setMultiLangAbbrs(List<LangString> multiLangAbbrs) {
        this.multiLangAbbrs = multiLangAbbrs;
    }

    public List<LangString> getMultiLangCities() {
        return multiLangCities;
    }

    public void setMultiLangCities(List<LangString> multiLangCities) {
        this.multiLangCities = multiLangCities;
    }

    public List<LangString> getMultiLangAreas() {
        return multiLangAreas;
    }

    public void setMultiLangAreas(List<LangString> multiLangAreas) {
        this.multiLangAreas = multiLangAreas;
    }

    public List<CountryCode> getAllowCountries() {
        return allowCountries;
    }

    public void setAllowCountries(List<CountryCode> allowCountries) {
        this.allowCountries = allowCountries;
    }

    public List<LangString> getMultiLangHeavyFoot() {
        return multiLangHeavyFoot;
    }

    public void setMultiLangHeavyFoot(List<LangString> multiLangHeavyFoot) {
        this.multiLangHeavyFoot = multiLangHeavyFoot;
    }

    public List<LangString> getMultiLangCareerValue() {
        return multiLangCareerValue;
    }

    public void setMultiLangCareerValue(List<LangString> multiLangCareerValue) {
        this.multiLangCareerValue = multiLangCareerValue;
    }

    public List<LangString> getMultiLangDesc() {
        return multiLangDesc;
    }

    public void setMultiLangDesc(List<LangString> multiLangDesc) {
        this.multiLangDesc = multiLangDesc;
    }


    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public ImageUrlExt getImage() {
        return image;
    }

    public void setImage(ImageUrlExt image) {
        this.image = image;
    }

    public List<LanguageCode> getOnlineLanguages() {
        return onlineLanguages;
    }

    public void setOnlineLanguages(List<LanguageCode> onlineLanguages) {
        this.onlineLanguages = onlineLanguages;
    }

    public List<Partner> getPartners() {
        return partners;
    }

    public void setPartners(List<Partner> partners) {
        this.partners = partners;
    }

    public Map<TeamType, PlayingTeam> getTeamNumber() {
        return teamNumber;
    }

    public void setTeamNumber(Map<TeamType, PlayingTeam> teamNumber) {
        this.teamNumber = teamNumber;
    }

    public ImageUrlExt getBgImage() {
        return bgImage;
    }

    public void setBgImage(ImageUrlExt bgImage) {
        this.bgImage = bgImage;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getDraft() {
        return draft;
    }

    public void setDraft(String draft) {
        this.draft = draft;
    }

    public List<LangString> getMultiLangDraftValue() {
        return multiLangDraftValue;
    }

    public void setMultiLangDraftValue(List<LangString> multiLangDraftValue) {
        this.multiLangDraftValue = multiLangDraftValue;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    //can not delete
    //fix error when deserialize id using fastjson because of the method in BaseModel
    public void setHelperId(Long helperId) {
        setId(helperId);
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Player{");
        sb.append("cids=").append(cids);
        sb.append(", firstLetter='").append(firstLetter).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", multiLangNames=").append(multiLangNames);
        sb.append(", type=").append(type);
        sb.append(", englishName='").append(englishName).append('\'');
        sb.append(", abbreviation='").append(abbreviation).append('\'');
        sb.append(", multiLangAbbrs=").append(multiLangAbbrs);
        sb.append(", height=").append(height);
        sb.append(", weight=").append(weight);
        sb.append(", birthday='").append(birthday).append('\'');
        sb.append(", gender=").append(gender);
        sb.append(", countryId=").append(countryId);
        sb.append(", city='").append(city).append('\'');
        sb.append(", multiLangCities=").append(multiLangCities);
        sb.append(", area='").append(area).append('\'');
        sb.append(", multiLangAreas=").append(multiLangAreas);
        sb.append(", gameFType=").append(gameFType);
        sb.append(", position=").append(position);
        sb.append(", deleted=").append(deleted);
        sb.append(", image=").append(image);
        sb.append(", allowCountries=").append(allowCountries);
        sb.append(", onlineLanguages=").append(onlineLanguages);
        sb.append(", heavyFoot='").append(heavyFoot).append('\'');
        sb.append(", multiLangHeavyFoot=").append(multiLangHeavyFoot);
        sb.append(", careerValue='").append(careerValue).append('\'');
        sb.append(", multiLangCareerValue=").append(multiLangCareerValue);
        sb.append(", school='").append(school).append('\'');
        sb.append(", experience='").append(experience).append('\'');
        sb.append(", draft='").append(draft).append('\'');
        sb.append(", multiLangDraftValue=").append(multiLangDraftValue);
        sb.append(", salary='").append(salary).append('\'');
        sb.append(", career=").append(career);
        sb.append(", multiLangCareerValue=").append(multiLangCareerValue);
        sb.append(", desc='").append(desc).append('\'');
        sb.append(", multiLangDesc=").append(multiLangDesc);
        sb.append(", partners=").append(partners);
        sb.append(", teamNumber=").append(teamNumber);
        sb.append(", career=").append(career);
        sb.append(", bgImage=").append(bgImage);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Player)) return false;

        Player player = (Player) o;

        if (id != null ? !id.equals(player.id) : player.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    public void addCids(Long cid) {
        if (this.cids.contains(cid)) {
            this.cids.remove(cid);
        }
        this.cids.add(cid);
    }

}
