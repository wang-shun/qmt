package com.lesports.qmt.sbd.model;

import com.google.common.collect.Sets;
import com.lesports.api.common.CountryCode;
import com.lesports.api.common.ImageUrlExt;
import com.lesports.api.common.LangString;
import com.lesports.api.common.LanguageCode;
import com.lesports.qmt.model.support.QmtModel;
import com.lesports.qmt.sbd.api.common.GroundOrder;
import com.lesports.qmt.sbd.api.common.MatchSystem;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;
import java.util.Set;

/**
 * 赛事
 * Created by yangyu on 2015/5/29.
 */
@Document(collection = "competitions")
public class Competition extends QmtModel<Long> {
    private static final long serialVersionUID = 2297182959603471035L;

    //赛事名称
    @Indexed(name = "name_1", unique = true)
    private String name;
    //多语言赛事名称
    @Field("multi_lang_names")
    private List<LangString> multiLangNames;
    //赛事状态:（1 有效 0 无效）
    private Boolean deleted;
    //大项
    @Field("game_f_type")
    private Long gameFType;
    //小项
    @Field("game_s_type")
    private Long gameSType;
    //频道ID
    @Field("channel_id")
    private Long channelId;
    //项目ID 仅当频道为综合体育时可用
    @Field("sub_channel_id")
    private Long subChannelId;
    //是否对阵,默认是对阵
    @Field("vs")
    private Boolean vs;
    //主客队顺序,默认先主后客
    @Field("ground_order")
    private GroundOrder groundOrder;
    //官方网站
    @Field("official_url")
    private String officialUrl;
    //赛事logo
    private ImageUrlExt logo;
    //赛事类型,杯赛,联赛
    private MatchSystem system;
    //赛事名称缩写
    private String abbreviation;
    //多语言缩写
    @Field("multi_lang_abbrs")
    private List<LangString> multiLangAbbrs;
    //简介
    private String introduction;
    //多语言简介
    @Field("multi_lang_intros")
    private List<LangString> multiLangIntros;
    //洲
    private String continent;
    //多语言洲
    @Field("multi_lang_conts")
    private List<LangString> multiLangConts;
    //国家
    private String nation;
    //多语言国家
    @Field("multi_lang_nations")
    private List<LangString> multiLangNations;
    //当前最新赛季
    @Field("current_season")
    private String currentSeason;

    //以下为运营字段
    //直播大项（多语言）
    @Field("live_f_type")
    private String liveFType;
    //直播小项
    @Field("live_s_type")
    private String liveSType;
    //标签id
    @Field("tag_ids")
    private Set<Long> tagIds = Sets.newHashSet();
    //允许展示的国家
    @Field("allow_countries")
    private List<CountryCode> allowCountries;
    //该语言是否已上线
    @Field("online_languages")
    private List<LanguageCode> onlineLanguages;
    //该地区是否付费
    @Field("chargeable_countries")
    private List<CountryCode> chargeableCountries;
    //4位赛事编码
    private String code;

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

    public String getCurrentSeason() {
        return currentSeason;
    }

    public void setCurrentSeason(String currentSeason) {
        this.currentSeason = currentSeason;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<LangString> getMultiLangNames() {
        return multiLangNames;
    }

    public void setMultiLangNames(List<LangString> multiLangNames) {
        this.multiLangNames = multiLangNames;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
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

    public String getCreateAt() {
        return createAt;
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

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }

    public String getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(String updateAt) {
        this.updateAt = updateAt;
    }

    public String getOfficialUrl() {
        return officialUrl;
    }

    public void setOfficialUrl(String officialUrl) {
        this.officialUrl = officialUrl;
    }

    public Long getGameSType() {
        return gameSType;
    }

    public void setGameSType(Long gameSType) {
        this.gameSType = gameSType;
    }

    public ImageUrlExt getLogo() {
        return logo;
    }

    public void setLogo(ImageUrlExt logo) {
        this.logo = logo;
    }

    public String getContinent() {
        return continent;
    }

    public void setContinent(String continent) {
        this.continent = continent;
    }

    public List<LangString> getMultiLangConts() {
        return multiLangConts;
    }

    public void setMultiLangConts(List<LangString> multiLangConts) {
        this.multiLangConts = multiLangConts;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public List<LangString> getMultiLangNations() {
        return multiLangNations;
    }

    public void setMultiLangNations(List<LangString> multiLangNations) {
        this.multiLangNations = multiLangNations;
    }

    public List<CountryCode> getChargeableCountries() {
        return chargeableCountries;
    }

    public void setChargeableCountries(List<CountryCode> chargeableCountries) {
        this.chargeableCountries = chargeableCountries;
    }

    public GroundOrder getGroundOrder() {
        return groundOrder;
    }

    public void setGroundOrder(GroundOrder groundOrder) {
        this.groundOrder = groundOrder;
    }

    public MatchSystem getSystem() {
        return system;
    }

    public void setSystem(MatchSystem system) {
        this.system = system;
    }

    public String getLiveFType() {
        return liveFType;
    }

    public void setLiveFType(String liveFType) {
        this.liveFType = liveFType;
    }

    public String getLiveSType() {
        return liveSType;
    }

    public void setLiveSType(String liveSType) {
        this.liveSType = liveSType;
    }

    public Set<Long> getTagIds() {
        return tagIds;
    }

    public void setTagIds(Set<Long> tagIds) {
        this.tagIds = tagIds;
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

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public List<LangString> getMultiLangIntros() {
        return multiLangIntros;
    }

    public void setMultiLangIntros(List<LangString> multiLangIntros) {
        this.multiLangIntros = multiLangIntros;
    }

    public List<CountryCode> getAllowCountries() {
        return allowCountries;
    }

    public void setAllowCountries(List<CountryCode> allowCountries) {
        this.allowCountries = allowCountries;
    }

    public List<LanguageCode> getOnlineLanguages() {
        return onlineLanguages;
    }

    public void setOnlineLanguages(List<LanguageCode> onlineLanguages) {
        this.onlineLanguages = onlineLanguages;
    }


    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Competition{");
        sb.append("name='").append(name).append('\'');
        sb.append(", multiLangNames=").append(multiLangNames);
        sb.append(", deleted=").append(deleted);
        sb.append(", gameFType=").append(gameFType);
        sb.append(", gameSType=").append(gameSType);
        sb.append(", channelId=").append(channelId);
        sb.append(", subChannelId=").append(subChannelId);
        sb.append(", vs=").append(vs);
        sb.append(", groundOrder=").append(groundOrder);
        sb.append(", officialUrl='").append(officialUrl).append('\'');
        sb.append(", logo=").append(logo);
        sb.append(", system=").append(system);
        sb.append(", abbreviation='").append(abbreviation).append('\'');
        sb.append(", multiLangAbbrs=").append(multiLangAbbrs);
        sb.append(", introduction='").append(introduction).append('\'');
        sb.append(", multiLangIntros=").append(multiLangIntros);
        sb.append(", continent='").append(continent).append('\'');
        sb.append(", multiLangConts=").append(multiLangConts);
        sb.append(", nation='").append(nation).append('\'');
        sb.append(", multiLangNations=").append(multiLangNations);
        sb.append(", currentSeason='").append(currentSeason).append('\'');
        sb.append(", liveFType='").append(liveFType).append('\'');
        sb.append(", liveSType='").append(liveSType).append('\'');
        sb.append(", tagIds=").append(tagIds);
        sb.append(", allowCountries=").append(allowCountries);
        sb.append(", onlineLanguages=").append(onlineLanguages);
        sb.append(", chargeableCountries=").append(chargeableCountries);
        sb.append(", code='").append(code).append('\'');
        sb.append('}');
        return sb.toString();
    }

    /**
     * 根据赛事ID生成4位赛事ID
     *
     * @param cid
     * @return
     */
    public static String createCode(long cid) {
        cid /= 1000;
        if (cid > 9999) {
            cid %= 10000;
            cid += 1000;
        }
        return substituteZero(cid + "", 4);
    }

    private static String substituteZero(String cid, int number) {
        for (int i = cid.length(); i < number; i++) {
            cid = "0" + cid;
        }
        return cid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Competition)) return false;

        Competition that = (Competition) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
