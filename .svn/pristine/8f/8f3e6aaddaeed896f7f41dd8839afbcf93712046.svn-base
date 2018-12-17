package com.lesports.qmt.sbd.param;

import com.lesports.qmt.sbd.api.common.Gender;
import com.lesports.qmt.sbd.api.common.PlayerType;
import org.hibernate.validator.constraints.NotBlank;

/**
 * Created by lufei1 on 2016/11/16.
 */
public class PlayerParam {

    private Long id;
    //球员名称
    @NotBlank(message = "name is required")
    private String name;
    //球员身份类型（教练，或球员）
    private PlayerType type;
    //球员简称
    private String abbreviation;
    //身高
    private Integer height;
    //体重
    private Integer weight;
    //出生日期 格式 yyyyMMdd
    private String birthday;
    //性别
    private Gender gender;
    //国籍Id
    private Long countryId;
    //出生城市
    private String city;
    //出生区域
    private String area;
    //项目种类（足球、篮球、网球等）
    private Long gameFType;
    //场上位置
    private Long position;
    //球队号码
    private String teamNumber;
    //职业生涯
    private String career;
    //球员简介
    private String desc;
    //大头像
    private String image;
    //超级手机背板图
    private String bgImage;

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

    public PlayerType getType() {
        return type;
    }

    public void setType(PlayerType type) {
        this.type = type;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
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

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Long getCountryId() {
        return countryId;
    }

    public void setCountryId(Long countryId) {
        this.countryId = countryId;
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

    public String getImage() {
        return image;
    }

    public String getTeamNumber() {
        return teamNumber;
    }

    public void setTeamNumber(String teamNumber) {
        this.teamNumber = teamNumber;
    }

    public String getCareer() {
        return career;
    }

    public void setCareer(String career) {
        this.career = career;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getBgImage() {
        return bgImage;
    }

    public void setBgImage(String bgImage) {
        this.bgImage = bgImage;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("PlayerParam{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", type=").append(type);
        sb.append(", abbreviation='").append(abbreviation).append('\'');
        sb.append(", height=").append(height);
        sb.append(", weight=").append(weight);
        sb.append(", birthday='").append(birthday).append('\'');
        sb.append(", gender=").append(gender);
        sb.append(", countryId=").append(countryId);
        sb.append(", city='").append(city).append('\'');
        sb.append(", area='").append(area).append('\'');
        sb.append(", gameFType=").append(gameFType);
        sb.append(", position=").append(position);
        sb.append(", teamNumber='").append(teamNumber).append('\'');
        sb.append(", career='").append(career).append('\'');
        sb.append(", desc='").append(desc).append('\'');
        sb.append(", image='").append(image).append('\'');
        sb.append(", bgImage='").append(bgImage).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
