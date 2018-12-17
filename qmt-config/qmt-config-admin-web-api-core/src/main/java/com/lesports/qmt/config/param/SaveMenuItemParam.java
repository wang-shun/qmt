package com.lesports.qmt.config.param;

import com.alibaba.fastjson.JSON;
import com.lesports.api.common.ImageUrlExt;
import com.lesports.api.common.PubChannel;
import com.lesports.qmt.config.api.dto.MenuResourceType;
import com.lesports.qmt.config.api.dto.ScheduleType;
import com.lesports.qmt.config.model.Menu;
import com.lesports.utils.math.LeNumberUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Map;

/**
 * Created by denghui on 2016/11/10.
 */
public class SaveMenuItemParam implements Serializable {
    private static final long serialVersionUID = 7220029539132825985L;

    //菜单项id
    private Long index;
    //父级菜单项id
    private Long parentIndex;
    //菜单项名称
    @NotBlank(message = "menu item name is required")
    private String name;
    //层级
    @NotNull(message = "level is required")
    private Integer level;
    //菜单元素类型
    private MenuResourceType resourceType;
    //菜单元素数据源ID, 如轮播台
    private Long resourceId;
    //关联数据: 新闻手动资源位
    private Long newsResourceId;
    //关联数据: 新闻自动资源位
    private Long newsAutoResourceId;
    //关联数据: 比赛卡片资源位
    private Long matchResourceId;
    //关联数据: 轮播图资源位
    private Long carouselResourceId;
    //关联数据: 榜单
    private Long toplistCid;
    //关联数据: 赛程
    private Long matchCid;
    //关联数据: 比赛 展示方式(TV) 1是轮次，2是日期
    private ScheduleType scheduleType;
    //关联数据: url
    private String url;
    //M站菜单url
    private String mUrl;
    //关联数据: 球队id
    private Long teamId;
    //关联数据: 社区阵营
    private String campId;
    //关联数据: 社区阵营分类
    private String campCategoryId;
    //展示: 新增
    private Boolean isNew;
    //展示: 热门
    private Boolean isHot;
    //TV渠道
    private String pubChannels;
    //图片
    private String images;
    //是否被推荐
    private Boolean isRecommend = false;
    //控制勾选了isRecommend字段的二级菜单的顺序
    private Integer recommendOrder;
    //是否被强制推荐
    private Boolean isForceRecommend = false;
    //强制推荐顺序
    private Integer forceRecommendOrder;
    //TV是否展示球队数据
    private Boolean isTeamData = false;
    //TV是否展示球元数据
    private Boolean isPlayerData = false;

    public Long getIndex() {
        return index;
    }

    public void setIndex(Long index) {
        this.index = index;
    }

    public Long getParentIndex() {
        return parentIndex;
    }

    public void setParentIndex(Long parentIndex) {
        this.parentIndex = parentIndex;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Long getResourceId() {
        return resourceId;
    }

    public void setResourceId(Long resourceId) {
        this.resourceId = resourceId;
    }

    public ScheduleType getScheduleType() {
        return scheduleType;
    }

    public void setScheduleType(ScheduleType scheduleType) {
        this.scheduleType = scheduleType;
    }

    public Long getCarouselResourceId() {
        return carouselResourceId;
    }

    public void setCarouselResourceId(Long carouselResourceId) {
        this.carouselResourceId = carouselResourceId;
    }

    public String getCampId() {
        return campId;
    }

    public void setCampId(String campId) {
        this.campId = campId;
    }

    public String getCampCategoryId() {
        return campCategoryId;
    }

    public void setCampCategoryId(String campCategoryId) {
        this.campCategoryId = campCategoryId;
    }

    public Boolean getNew() {
        return isNew;
    }

    public void setNew(Boolean aNew) {
        isNew = aNew;
    }

    public Boolean getHot() {
        return isHot;
    }

    public void setHot(Boolean hot) {
        isHot = hot;
    }

    public String getPubChannels() {
        return pubChannels;
    }

    public void setPubChannels(String pubChannels) {
        this.pubChannels = pubChannels;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public Boolean getRecommend() {
        return isRecommend;
    }

    public void setRecommend(Boolean recommend) {
        isRecommend = recommend;
    }

    public Integer getRecommendOrder() {
        return recommendOrder;
    }

    public void setRecommendOrder(Integer recommendOrder) {
        this.recommendOrder = recommendOrder;
    }

    public Boolean getForceRecommend() {
        return isForceRecommend;
    }

    public void setForceRecommend(Boolean forceRecommend) {
        isForceRecommend = forceRecommend;
    }

    public Integer getForceRecommendOrder() {
        return forceRecommendOrder;
    }

    public void setForceRecommendOrder(Integer forceRecommendOrder) {
        this.forceRecommendOrder = forceRecommendOrder;
    }

    public MenuResourceType getResourceType() {
        return resourceType;
    }

    public void setResourceType(MenuResourceType resourceType) {
        this.resourceType = resourceType;
    }

    public Long getNewsResourceId() {
        return newsResourceId;
    }

    public void setNewsResourceId(Long newsResourceId) {
        this.newsResourceId = newsResourceId;
    }

    public Long getMatchResourceId() {
        return matchResourceId;
    }

    public void setMatchResourceId(Long matchResourceId) {
        this.matchResourceId = matchResourceId;
    }

    public Long getToplistCid() {
        return toplistCid;
    }

    public void setToplistCid(Long toplistCid) {
        this.toplistCid = toplistCid;
    }

    public Long getMatchCid() {
        return matchCid;
    }

    public void setMatchCid(Long matchCid) {
        this.matchCid = matchCid;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Boolean getTeamData() {
        return isTeamData;
    }

    public void setTeamData(Boolean teamData) {
        isTeamData = teamData;
    }

    public Boolean getPlayerData() {
        return isPlayerData;
    }

    public void setPlayerData(Boolean playerData) {
        isPlayerData = playerData;
    }

    public Long getTeamId() {
        return teamId;
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }

    public Long getNewsAutoResourceId() {
        return newsAutoResourceId;
    }

    public void setNewsAutoResourceId(Long newsAutoResourceId) {
        this.newsAutoResourceId = newsAutoResourceId;
    }

    public String getmUrl() {
        return mUrl;
    }

    public void setmUrl(String mUrl) {
        this.mUrl = mUrl;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    public Menu.MenuItem toMenuItem() {
        Menu.MenuItem item = new Menu.MenuItem();
        item.setIndex(getIndex());
        item.setName(getName());
        item.setLevel(getLevel());
        if (LeNumberUtils.toInt(item.getLevel()) == 1) {
            item.setParentIndex(0l);
        } else if (LeNumberUtils.toInt(item.getLevel()) == 2) {
            if (LeNumberUtils.toLong(getParentIndex()) <= 0) {
                throw new IllegalArgumentException("parent index is required for level two menu");
            }
            item.setParentIndex(getParentIndex());
        } else {
            throw new IllegalArgumentException("unsupported menu level: " + getLevel());
        }
        item.setResourceType(getResourceType());
        item.setResourceId(getResourceId());
        item.setNewsResourceId(getNewsResourceId());
        item.setNewsAutoResourceId(getNewsAutoResourceId());
        item.setMatchResourceId(getMatchResourceId());
        item.setToplistCid(getToplistCid());
        item.setMatchCid(getMatchCid());
        item.setScheduleType(getScheduleType());
        item.setCarouselResourceId(getCarouselResourceId());
        item.setUrl(getUrl());
        item.setmUrl(getmUrl());
        item.setTeamId(getTeamId());
        item.setCampId(getCampId());
        item.setCampCategoryId(getCampCategoryId());
        item.setNew(getNew());
        item.setHot(getHot());
        if (StringUtils.isNotEmpty(getPubChannels())) {
            item.setPubChannels(JSON.parseArray(getPubChannels(), PubChannel.class));
        }
        if (StringUtils.isNotEmpty(getImages())) {
            Map<String, ImageUrlExt> images = JSON.parseObject(getImages(), Map.class);
            item.setImages(images);
        }
        item.setRecommend(getRecommend());
        item.setRecommendOrder(getRecommendOrder());
        item.setForceRecommend(getForceRecommend());
        item.setForceRecommendOrder(getForceRecommendOrder());
        item.setTeamData(getTeamData());
        item.setPlayerData(getPlayerData());
        return item;
    }
}
