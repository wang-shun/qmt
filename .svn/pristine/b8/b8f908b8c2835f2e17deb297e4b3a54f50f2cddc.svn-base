package com.lesports.qmt.sbd.model;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.lesports.api.common.*;
import com.lesports.api.common.CountryCode;
import com.lesports.api.common.LanguageCode;
import com.lesports.api.common.MatchStatus;
import com.lesports.qmt.model.support.QmtModel;
import com.lesports.qmt.sbd.api.common.*;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by yangyu on 2015/5/30.
 */
@Document(collection = "matches")
public class Match extends QmtModel<Long> {
    private static final long serialVersionUID = -8048819333470825837L;

    //赛季id
    private Long csid;
    //赛事id
    private Long cid;
    //大项
    @Field("game_f_type")
    private Long gameFType;
    //分项
    @Field("discipline")
    private Long discipline;
    //小项
    @Field("game_s_type")
    private Long gameSType;
    //分组，A组、B组等(字典表)
    private Long group;
    //比赛阶段（小组赛、淘汰赛、常规赛、季后赛）(字典表)
    private Long stage;
    //比赛状态
    private MatchStatus status;
    //比赛开始时间
    @Field("start_time")
    private String startTime;
    //精确到天的比赛开始时间,为了方便按天查询格式:yyyyMMdd
    @Field("start_date")
    private String startDate;
    //比赛所在地区开始时间
    @Field("local_start_time")
    private String localStartTime;
    //比赛所在地区开始日期,为了方便按天查询格式:yyyyMMdd
    @Field("local_start_date")
    private String localStartDate;
    //比赛结束时间
    @Field("end_time")
    private String endTime;
    //比赛城市
    private String city;
    //多语言城市名
    @Field("multi_lang_citys")
    private List<LangString> multiLangCitys;
    //比赛场馆
    @Field("venue")
    private String venue;
    //多语言场馆名
    @Field("multi_lang_venues")
    private List<LangString> multiLangVenues;
    //第三方数据信息
    private List<Partner> partners;
    //是否被删除
    private Boolean deleted;
    //比赛名称
    private String name;
    //多语言比赛名称
    @Field("multi_lang_names")
    private List<LangString> multiLangNames;
    //官方比赛顺序
    private Integer number;
    //赛程加锁（false: 未锁,true: 已锁）
    private Boolean lock;
    //轮次
    private Long round;
    //分站
    private Long substation;
    //参赛者信息
    private Set<Competitor> competitors = Sets.newHashSet();
    //是否指定奖牌类型的比赛
    @Field("medal_type")
    private MedalType medalType;
    //比赛进行到什么时刻
    @Field("current_moment")
    private CurrentMoment currentMoment;
    //比赛结果是否官方确认
    @Field("is_offical")
    private Boolean isOffical;
    //比赛天气
    private String weather;
    //多语言天气
    @Field("multi_lang_weathers")
    private List<LangString> multiLangWeathers;
    //比赛主裁判
    private String judge;
    //多语言比赛裁判
    @Field("multi_lang_judges")
    private List<LangString> multiLangJudges;
    //扩展信息
    @Field("extend_infos")
    private Map<String, Object> extendInfos;
    //允许展示的国家
    @Field("allow_countries")
    private List<CountryCode> allowCountries;
    //该语言是否已上线
    @Field("online_languages")
    private List<LanguageCode> onlineLanguages;
    //表示赛程在该地区创建
    @Field("create_country")
    private CountryCode createCountry;
    //表示赛程以该语言创建
    @Field("create_languages")
    private List<LanguageCode> createLanguages;
    //是否对阵,默认是对阵
    @Field("vs")
    private Boolean vs;

    //以下为运营字段
    //一个赛程对应多个节目
    private List<CountryLangId> eids;
    //分地区付费直播id
    @Field("muti_live_unique_ids")
    private List<CounString> mutiLiveUniqueIds;
    //标签id
    @Field("tag_ids")
    private Set<Long> tagIds = Sets.newHashSet();
    //晋级之路的order,前端通过此字段排列
    @Field("the_road_order")
    private Integer theRoadOrder;

    public Match() {
    }


    /**
     * 参赛队伍信息
     */
    public static class Competitor implements Serializable {
        private static final long serialVersionUID = -7048819333470825837L;

        //队伍or队员id
        @Field("competitor_id")
        private Long competitorId;
        //队伍or队员名称
        @Transient
        private String competitorName;
        //队伍or队员id  国籍Id
        @Field("competitor_country_id")
        private Long competitorCountryId;
        //比分,结果
        @Field("final_result")
        private String finalResult = "";
        //分节的结果
        @Field("section_results")
        private List<SectionResult> sectionResults = Lists.newArrayList();
        //主客场
        private GroundType ground;
        //是队伍还是队员
        private CompetitorType type;
        //展示顺序
        private Integer order;
        //扩展信息
        @Field("extend_infos")
        private Map<String, Object> extendInfos;

        public Map<String, Object> getExtendInfos() {
            return extendInfos;
        }

        public void setExtendInfos(Map<String, Object> extendInfos) {
            this.extendInfos = extendInfos;
        }

        public Long getCompetitorId() {
            return competitorId;
        }

        public void setCompetitorId(Long competitorId) {
            this.competitorId = competitorId;
        }

        public String getFinalResult() {
            return finalResult;
        }

        public void setFinalResult(String finalResult) {
            this.finalResult = finalResult;
        }

        public List<SectionResult> getSectionResults() {
            return sectionResults;
        }

        public void setSectionResults(List<SectionResult> sectionResults) {
            this.sectionResults = sectionResults;
        }

        public void addSectionResult(SectionResult sectionResult) {
            this.sectionResults.add(sectionResult);
        }


        public GroundType getGround() {
            return ground;
        }

        public void setGround(GroundType ground) {
            this.ground = ground;
        }

        public CompetitorType getType() {
            return type;
        }

        public void setType(CompetitorType type) {
            this.type = type;
        }

        public Integer getOrder() {
            return order;
        }

        public void setOrder(Integer order) {
            this.order = order;
        }

        public Long getCompetitorCountryId() {
            return competitorCountryId;
        }

        public void setCompetitorCountryId(Long competitorCountryId) {
            this.competitorCountryId = competitorCountryId;
        }

        public String getCompetitorName() {
            return competitorName;
        }

        public void setCompetitorName(String competitorName) {
            this.competitorName = competitorName;
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("Competitor{");
            sb.append("competitorId=").append(competitorId);
            sb.append(", finalResult='").append(finalResult).append('\'');
            sb.append(", sectionResults=").append(sectionResults);
            sb.append(", ground=").append(ground);
            sb.append(", type=").append(type);
            sb.append(", order=").append(order);
            sb.append('}');
            return sb.toString();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Competitor)) return false;

            Competitor that = (Competitor) o;

            if (competitorId != null ? !competitorId.equals(that.competitorId) : that.competitorId != null)
                return false;

            return true;
        }

        @Override
        public int hashCode() {
            return competitorId != null ? competitorId.hashCode() : 0;
        }
    }


    public static class PayInfo implements Serializable {
        //付费方
        private Long section;


    }

    /**
     * 分节的比分
     */
    public static class SectionResult implements Serializable {
        private static final long serialVersionUID = -1048819333470825837L;

        //节的字典id
        private Long section;
        //本节的顺序
        private int order;
        //本节比赛结果
        private String result;


        public Long getSection() {
            return section;
        }

        public void setSection(Long section) {
            this.section = section;
        }

        public int getOrder() {
            return order;
        }

        public void setOrder(int order) {
            this.order = order;
        }

        public String getResult() {
            return result;
        }

        public void setResult(String result) {
            this.result = result;
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("SectionResult{");
            sb.append("section=").append(section);
            sb.append(", order=").append(order);
            sb.append(", result='").append(result).append('\'');
            sb.append('}');
            return sb.toString();
        }
    }

    /**
     * 比赛当前阶段时间
     */
    public static class CurrentMoment implements Serializable {
        private static final long serialVersionUID = -38819333470825837L;

        //比赛进行的阶段，节的字典id
        private Long section;
        //比赛进行的时间,精确到秒
        private double time;
        //比赛时间顺序
        private TimeSort sort;

        public Long getSection() {
            return section;
        }

        public void setSection(Long section) {
            this.section = section;
        }

        public double getTime() {
            return time;
        }

        public void setTime(double time) {
            this.time = time;
        }

        public TimeSort getSort() {
            return sort;
        }

        public void setSort(TimeSort sort) {
            this.sort = sort;
        }

    }

    public CurrentMoment getCurrentMoment() {
        return currentMoment;
    }

    public void setCurrentMoment(CurrentMoment currentMoment) {
        this.currentMoment = currentMoment;
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

    public Long getGameFType() {
        return gameFType;
    }

    public void setGameFType(Long gameFType) {
        this.gameFType = gameFType;
    }

    public Long getDiscipline() {
        return discipline;
    }

    public void setDiscipline(Long discipline) {
        this.discipline = discipline;
    }

    public Long getGameSType() {
        return gameSType;
    }

    public void setGameSType(Long gameSType) {
        this.gameSType = gameSType;
    }

    public Long getGroup() {
        return group;
    }

    public void setGroup(Long group) {
        this.group = group;
    }

    public Boolean getVs() {
        return vs;
    }

    public void setVs(Boolean vs) {
        this.vs = vs;
    }

    public Long getStage() {
        return stage;
    }

    public void setStage(Long stage) {
        this.stage = stage;
    }

    public MatchStatus getStatus() {
        return status;
    }

    public void setStatus(MatchStatus status) {
        this.status = status;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getLocalStartTime() {
        return localStartTime;
    }

    public void setLocalStartTime(String localStartTime) {
        this.localStartTime = localStartTime;
    }

    public String getLocalStartDate() {
        return localStartDate;
    }

    public void setLocalStartDate(String localStartDate) {
        this.localStartDate = localStartDate;
    }

    public String getEndTime() {
        return endTime;
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

    public List<LangString> getMultiLangCitys() {
        return multiLangCitys;
    }

    public void setMultiLangCitys(List<LangString> multiLangCitys) {
        this.multiLangCitys = multiLangCitys;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public List<LangString> getMultiLangVenues() {
        return multiLangVenues;
    }

    public void setMultiLangVenues(List<LangString> multiLangVenues) {
        this.multiLangVenues = multiLangVenues;
    }

    public List<Partner> getPartners() {
        return partners;
    }

    public void setPartners(List<Partner> partners) {
        this.partners = partners;
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

    public List<LangString> getMultiLangNames() {
        return multiLangNames;
    }

    public void setMultiLangNames(List<LangString> multiLangNames) {
        this.multiLangNames = multiLangNames;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Boolean getLock() {
        return lock;
    }

    public void setLock(Boolean lock) {
        this.lock = lock;
    }

    public Long getRound() {
        return round;
    }

    public void setRound(Long round) {
        this.round = round;
    }

    public Long getSubstation() {
        return substation;
    }

    public void setSubstation(Long substation) {
        this.substation = substation;
    }

    public Set<Competitor> getCompetitors() {
        return competitors;
    }

    public void setCompetitors(Set<Competitor> competitors) {
        this.competitors = competitors;
    }

    public MedalType getMedalType() {
        return medalType;
    }

    public void setMedalType(MedalType medalType) {
        this.medalType = medalType;
    }

    public Boolean getIsOffical() {
        return isOffical;
    }

    public void setIsOffical(Boolean isOffical) {
        this.isOffical = isOffical;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public List<LangString> getMultiLangWeathers() {
        return multiLangWeathers;
    }

    public void setMultiLangWeathers(List<LangString> multiLangWeathers) {
        this.multiLangWeathers = multiLangWeathers;
    }

    public String getJudge() {
        return judge;
    }

    public void setJudge(String judge) {
        this.judge = judge;
    }

    public List<LangString> getMultiLangJudges() {
        return multiLangJudges;
    }

    public void setMultiLangJudges(List<LangString> multiLangJudges) {
        this.multiLangJudges = multiLangJudges;
    }

    public Map<String, Object> getExtendInfos() {
        return extendInfos;
    }

    public void setExtendInfos(Map<String, Object> extendInfos) {
        this.extendInfos = extendInfos;
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

    public CountryCode getCreateCountry() {
        return createCountry;
    }

    public void setCreateCountry(CountryCode createCountry) {
        this.createCountry = createCountry;
    }

    public List<LanguageCode> getCreateLanguages() {
        return createLanguages;
    }

    public void setCreateLanguages(List<LanguageCode> createLanguages) {
        this.createLanguages = createLanguages;
    }

    public List<CounString> getMutiLiveUniqueIds() {
        return mutiLiveUniqueIds;
    }

    public void setMutiLiveUniqueIds(List<CounString> mutiLiveUniqueIds) {
        this.mutiLiveUniqueIds = mutiLiveUniqueIds;
    }

    public Set<Long> getTagIds() {
        return tagIds;
    }

    public void setTagIds(Set<Long> tagIds) {
        this.tagIds = tagIds;
    }

    public Integer getTheRoadOrder() {
        return theRoadOrder;
    }

    public void setTheRoadOrder(Integer theRoadOrder) {
        this.theRoadOrder = theRoadOrder;
    }

    public List<CountryLangId> getEids() {
        return eids;
    }

    public void setEids(List<CountryLangId> eids) {
        this.eids = eids;
    }

    //can not delete
    //fix error when deserialize id using fastjson because of the method in BaseModel
    public void setHelperId(Long helperId) {
        setId(helperId);
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Match{");
        sb.append("csid=").append(csid);
        sb.append(", cid=").append(cid);
        sb.append(", gameFType=").append(gameFType);
        sb.append(", discipline=").append(discipline);
        sb.append(", gameSType=").append(gameSType);
        sb.append(", group=").append(group);
        sb.append(", stage=").append(stage);
        sb.append(", status=").append(status);
        sb.append(", startTime='").append(startTime).append('\'');
        sb.append(", startDate='").append(startDate).append('\'');
        sb.append(", localStartTime='").append(localStartTime).append('\'');
        sb.append(", localStartDate='").append(localStartDate).append('\'');
        sb.append(", endTime='").append(endTime).append('\'');
        sb.append(", city='").append(city).append('\'');
        sb.append(", multiLangCitys=").append(multiLangCitys);
        sb.append(", venue='").append(venue).append('\'');
        sb.append(", multiLangVenues=").append(multiLangVenues);
        sb.append(", partners=").append(partners);
        sb.append(", deleted=").append(deleted);
        sb.append(", name='").append(name).append('\'');
        sb.append(", multiLangNames=").append(multiLangNames);
        sb.append(", number=").append(number);
        sb.append(", lock=").append(lock);
        sb.append(", round=").append(round);
        sb.append(", eids=").append(eids);
        sb.append(", substation=").append(substation);
        sb.append(", competitors=").append(competitors);
        sb.append(", medalType=").append(medalType);
        sb.append(", currentMoment=").append(currentMoment);
        sb.append(", isOffical=").append(isOffical);
        sb.append(", weather='").append(weather).append('\'');
        sb.append(", multiLangWeathers=").append(multiLangWeathers);
        sb.append(", judge='").append(judge).append('\'');
        sb.append(", multiLangJudges=").append(multiLangJudges);
        sb.append(", extendInfos=").append(extendInfos);
        sb.append(", allowCountries=").append(allowCountries);
        sb.append(", onlineLanguages=").append(onlineLanguages);
        sb.append(", createCountry=").append(createCountry);
        sb.append(", createLanguages=").append(createLanguages);
        sb.append(", mutiLiveUniqueIds=").append(mutiLiveUniqueIds);
        sb.append(", tagIds=").append(tagIds);
        sb.append(", theRoadOrder=").append(theRoadOrder);
        sb.append('}');
        return sb.toString();
    }
}
