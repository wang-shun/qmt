package com.lesports.qmt.sbc.model;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.lesports.api.common.ImageUrlExt;
import com.lesports.qmt.model.support.QmtModel;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Map;
import java.util.Set;

/**
 * 自制专辑
 * Created by denghui on 2016/11/14.
 */
@Document(collection = "program_albums")
public class ProgramAlbum extends QmtModel<Long> {
    private static final long serialVersionUID = 5559485388572136338L;

    //专辑名称
    private String name;
    //是否被删除
    private Boolean deleted;
    //首期选集
    @Field("first_period_id")
    private Long firstPeriodId;
    //最新选集
    @Field("latest_period_id")
    private Long latestPeriodId;
    //相关id 赛程 球队 球员 自制专辑 自制节目 其他节目 标签 字典
    @Field("related_ids")
    private Set<Long> relatedIds = Sets.newHashSet();
    //自制专辑logo
    private Map<String, ImageUrlExt> logo = Maps.newHashMap();
    //是否推荐
    private Boolean recommend;
    //顺序
    @Field("recommend_order")
    private Integer recommendOrder;

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

    public Long getFirstPeriodId() {
        return firstPeriodId;
    }

    public void setFirstPeriodId(Long firstPeriodId) {
        this.firstPeriodId = firstPeriodId;
    }

    public Long getLatestPeriodId() {
        return latestPeriodId;
    }

    public void setLatestPeriodId(Long latestPeriodId) {
        this.latestPeriodId = latestPeriodId;
    }

    public Set<Long> getRelatedIds() {
        return relatedIds;
    }

    public void setRelatedIds(Set<Long> relatedIds) {
        this.relatedIds = relatedIds;
    }

    public Map<String, ImageUrlExt> getLogo() {
        return logo;
    }

    public void setLogo(Map<String, ImageUrlExt> logo) {
        this.logo = logo;
    }

    public void addRelatedId(Long id) {
        if (id != null) {
            this.relatedIds.add(id);
        }
    }

    public Boolean getRecommend() {
        return recommend;
    }

    public void setRecommend(Boolean recommend) {
        this.recommend = recommend;
    }

    public Integer getRecommendOrder() {
        return recommendOrder;
    }

    public void setRecommendOrder(Integer recommendOrder) {
        this.recommendOrder = recommendOrder;
    }
}
