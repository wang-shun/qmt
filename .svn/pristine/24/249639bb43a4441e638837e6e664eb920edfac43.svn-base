package com.lesports.qmt.sbd.model;

import com.google.common.collect.Lists;
import com.lesports.api.common.CountryCode;
import com.lesports.api.common.LangString;
import com.lesports.api.common.LanguageCode;
import com.lesports.qmt.model.support.QmtModel;
import com.lesports.qmt.sbd.api.common.CompetitorType;
import com.lesports.qmt.sbd.api.common.ScopeType;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 榜单数据
 * User: ellios
 * Time: 15-6-7 : 上午12:07
 */
@Document(collection = "top_lists")
public class TopList extends QmtModel<Long> {
    private static final long serialVersionUID = 1498268129319006601L;

    //赛事
    private Long cid;
    //赛季
    private Long csid;
    //榜单类型,字典id
    private Long type;
    //作用域的Id(全联盟Id，东部联盟Id，西部联盟Id，分区Id，小组Id，分站Id 球队Id)
    @Field("scope_id")
    private Long scopeId;
    //作用域的类型（联盟，分区，分站，球队，小组）
    @Field("scope_type")
    private ScopeType scopeType;
    //榜单中item项的类型，球员或者球队
    @Field("competitor_type")
    private CompetitorType competitorType;
    //标识是否删除
    private Boolean deleted = false;
    @Field("items")
    private List<TopListItem> items = Lists.newArrayList();
    //是否最新的榜单数据
    private Boolean latest;
    //url
    private String url;
    //多语言urls
    private List<LangString> multiLangUrls;
    //描述
    private String description;
    @Field("multi_lang_desc")
    private List<LangString> multiLangDesc;
    //名称
    private String name;
    @Field("multi_lang_names")
    private List<LangString> multiLangNames;
    //app榜单的排序字段
    private Integer order;
    //允许展示的国家
    @Field("allow_countries")
    private List<CountryCode> allowCountries;
    //该语言是否已上线
    @Field("online_languages")
    private List<LanguageCode> onlineLanguages;

    public Long getScopeId() {
        return scopeId;
    }

    public void setScopeId(Long scopeId) {
        this.scopeId = scopeId;
    }

    public ScopeType getScopeType() {
        return scopeType;
    }

    public void setScopeType(ScopeType scopeType) {
        this.scopeType = scopeType;
    }

    public CompetitorType getCompetitorType() {
        return competitorType;
    }

    public void setCompetitorType(CompetitorType competitorType) {
        this.competitorType = competitorType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public Long getCsid() {
        return csid;
    }

    public void setCsid(Long csid) {
        this.csid = csid;
    }

    public Long getType() {
        return type;
    }

    public void setType(Long type) {
        this.type = type;
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

    public List<TopListItem> getItems() {
        return items;
    }

    public void setItems(List<TopListItem> items) {
        this.items = items;
    }

    public void addItem(TopListItem item) {
        this.items.add(item);
    }

    public Boolean getLatest() {
        return latest;
    }

    public void setLatest(Boolean latest) {
        this.latest = latest;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public List<LangString> getMultiLangDesc() {
        return multiLangDesc;
    }

    public void setMultiLangDesc(List<LangString> multiLangDesc) {
        this.multiLangDesc = multiLangDesc;
    }

    public List<LangString> getMultiLangNames() {
        return multiLangNames;
    }

    public void setMultiLangNames(List<LangString> multiLangNames) {
        this.multiLangNames = multiLangNames;
    }

    public List<CountryCode> getAllowCountries() {
        return allowCountries;
    }

    public void setAllowCountries(List<CountryCode> allowCountries) {
        this.allowCountries = allowCountries;
    }

    public List<LangString> getMultiLangUrls() {
        return multiLangUrls;
    }

    public void setMultiLangUrls(List<LangString> multiLangUrls) {
        this.multiLangUrls = multiLangUrls;
    }

    public List<LanguageCode> getOnlineLanguages() {
        return onlineLanguages;
    }

    public void setOnlineLanguages(List<LanguageCode> onlineLanguages) {
        this.onlineLanguages = onlineLanguages;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TopList{");
        sb.append("id=").append(id);
        sb.append(", cid=").append(cid);
        sb.append(", csid=").append(csid);
        sb.append(", type=").append(type);
        sb.append(", deleted=").append(deleted);
        sb.append(", createAt='").append(createAt).append('\'');
        sb.append(", updateAt='").append(updateAt).append('\'');
        sb.append(", items=").append(items);
        sb.append(", latest=").append(latest);
        sb.append(", url='").append(url).append('\'');
        sb.append(", multiLangUrls=").append(multiLangUrls);
        sb.append(", description='").append(description).append('\'');
        sb.append(", multiLangDesc=").append(multiLangDesc);
        sb.append(", name='").append(name).append('\'');
        sb.append(", multiLangNames=").append(multiLangNames);
        sb.append(", order=").append(order);
        sb.append(", allowCountries=").append(allowCountries);
        sb.append(", onlineLanguages=").append(onlineLanguages);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TopList)) return false;

        TopList topList = (TopList) o;

        if (id != null ? !id.equals(topList.id) : topList.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    /**
     * 榜单内容项
     */
    public static class TopListItem implements Serializable {
        private static final long serialVersionUID = -18819333470825837L;

        @Field("competitor_id")
        //队伍或者是队员的id
        private Long competitorId;
        @Field("competitor_type")
        private CompetitorType competitorType;
        //球队id，如果参赛者是球员时候需要所属球队
        @Field("team_id")
        private Long teamId;
        //在榜单中得排名
        private Integer rank;
        //展示顺序,方便运营调整顺序
        private Integer showOrder;
        //统计的内容
        private Map<String, Object> stats;

        public Long getCompetitorId() {
            return competitorId;
        }

        public void setCompetitorId(Long competitorId) {
            this.competitorId = competitorId;
        }

        public CompetitorType getCompetitorType() {
            return competitorType;
        }

        public void setCompetitorType(CompetitorType competitorType) {
            this.competitorType = competitorType;
        }

        public Integer getRank() {
            return rank;
        }

        public void setRank(Integer rank) {
            this.rank = rank;
        }

        public Integer getShowOrder() {
            return showOrder;
        }

        public void setShowOrder(Integer showOrder) {
            this.showOrder = showOrder;
        }

        public Map<String, Object> getStats() {
            return stats;
        }

        public void setStats(Map<String, Object> stats) {
            this.stats = stats;
        }

        public Long getTeamId() {
            return teamId;
        }

        public void setTeamId(Long teamId) {
            this.teamId = teamId;
        }

        @Override
        public String toString() {
            return "TopListItem{" +
                    "competitorId=" + competitorId +
                    ", competitorType=" + competitorType +
                    ", teamId=" + teamId +
                    ", rank=" + rank +
                    ", showOrder=" + showOrder +
                    ", stats=" + stats +
                    '}';
        }


        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof TopListItem)) return false;

            TopListItem that = (TopListItem) o;

            if (competitorId != null ? !competitorId.equals(that.competitorId) : that.competitorId != null)
                return false;

            return true;
        }

        @Override
        public int hashCode() {
            return competitorId != null ? competitorId.hashCode() : 0;
        }
    }

}




