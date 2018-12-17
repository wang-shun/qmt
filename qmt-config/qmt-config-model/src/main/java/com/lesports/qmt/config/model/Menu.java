package com.lesports.qmt.config.model;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.lesports.api.common.*;
import com.lesports.qmt.config.api.dto.MenuResourceType;
import com.lesports.qmt.config.api.dto.ScheduleType;
import com.lesports.qmt.model.support.QmtModel;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by lufei1 on 2015/7/2.
 */
@Document(collection = "menus")
public class Menu extends QmtModel<Long> {

    private static final long serialVersionUID = -9046933983291766006L;

    //菜单名称
    private String name;
    //菜单适用平台
    private Set<Platform> platforms = Sets.newHashSet();
    //内容
    private List<MenuItem> items = Lists.newArrayList();
    //是否删除
    private boolean deleted;
    //允许展示的国家
    @Field("allow_country")
    private CountryCode allowCountry;
	//允许展示的语言
	@Field("language_code")
	private LanguageCode languageCode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Platform> getPlatforms() {
        return platforms;
    }

    public void setPlatforms(Set<Platform> platforms) {
        this.platforms = platforms;
    }

    public List<MenuItem> getItems() {
        return items;
    }

    public void setItems(List<MenuItem> items) {
        this.items = items;
    }

    public void addItem(MenuItem item){
        this.items.add(item);
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CountryCode getAllowCountry() {
        return allowCountry;
    }

    public void setAllowCountry(CountryCode allowCountry) {
        this.allowCountry = allowCountry;
    }

	public LanguageCode getLanguageCode() {
		return languageCode;
	}

	public void setLanguageCode(LanguageCode languageCode) {
		this.languageCode = languageCode;
	}

    /**
     * 菜单项
     */
    public static class MenuItem implements Serializable{

        private static final long serialVersionUID = 7045711239633690789L;
        //菜单项id
        private Long index;
        @Field("parent_index")
        //父级菜单项id
        private Long parentIndex;
        //菜单项名称
        private String name;
        //层级
        private Integer level;
        //排序
        private Integer order;
        //菜单元素类型
        @Field("resource_type")
        private MenuResourceType resourceType;
        //菜单元素数据源ID, 如轮播台
        @Field("resource_id")
        private Long resourceId;
        //关联数据: 新闻手动资源位
        @Field("news_resource_id")
        private Long newsResourceId;
        //关联数据: 新闻自动资源位
        @Field("news_auto_resource_id")
        private Long newsAutoResourceId;
        //关联数据: 比赛卡片资源位
        @Field("match_resource_id")
        private Long matchResourceId;
        //关联数据: 轮播图资源位
        @Field("carousel_resource_id")
        private Long carouselResourceId;
        //关联数据: 榜单
        @Field("toplist_cid")
        private Long toplistCid;
        //关联数据: 赛程
        @Field("match_cid")
        private Long matchCid;
        //关联数据: 比赛 展示方式(TV) 1是轮次，2是日期
        @Field("schedule_type")
        private ScheduleType scheduleType;
        //关联数据: url
        private String url;
        //M站菜单url
        @Field("m_url")
        private String mUrl;
        @Field("team_id")
        private Long teamId;
        //关联数据: 社区阵营
        @Field("camp_id")
        private String campId;
        //关联数据: 社区阵营分类
        @Field("camp_category_id")
        private String campCategoryId;
        //展示: 新增
        @Field("is_new")
        private Boolean isNew;
        //展示: 热门
        @Field("is_hot")
        private Boolean isHot;
        //TV渠道
        @Field("pub_channels")
        private List<PubChannel> pubChannels = Lists.newArrayList();
        //图片
        private Map<String, ImageUrlExt> images = Maps.newHashMap();
        //是否被推荐
        @Field("is_recommend")
        private Boolean isRecommend = false;
        //控制勾选了isRecommend字段的二级菜单的顺序
        @Field("recommend_order")
        private Integer recommendOrder;
        //是否被强制推荐
        @Field("is_force_recommend")
        private Boolean isForceRecommend = false;
        //强制推荐顺序
        @Field("force_recommend_order")
        private Integer forceRecommendOrder;
        //TV是否展示球队数据
        @Field("is_team_data")
        private Boolean isTeamData = false;
        //TV是否展示球元数据
        @Field("is_player_data")
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

        public Integer getOrder() {
            return order;
        }

        public void setOrder(Integer order) {
            this.order = order;
        }

        public MenuResourceType getResourceType() {
            return resourceType;
        }

        public void setResourceType(MenuResourceType resourceType) {
            this.resourceType = resourceType;
        }

        public Long getResourceId() {
            return resourceId;
        }

        public void setResourceId(Long resourceId) {
            this.resourceId = resourceId;
        }

        public Long getNewsResourceId() {
            return newsResourceId;
        }

        public void setNewsResourceId(Long newsResourceId) {
            this.newsResourceId = newsResourceId;
        }

        public Long getNewsAutoResourceId() {
            return newsAutoResourceId;
        }

        public void setNewsAutoResourceId(Long newsAutoResourceId) {
            this.newsAutoResourceId = newsAutoResourceId;
        }

        public Long getMatchResourceId() {
            return matchResourceId;
        }

        public void setMatchResourceId(Long matchResourceId) {
            this.matchResourceId = matchResourceId;
        }

        public Long getCarouselResourceId() {
            return carouselResourceId;
        }

        public void setCarouselResourceId(Long carouselResourceId) {
            this.carouselResourceId = carouselResourceId;
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

        public ScheduleType getScheduleType() {
            return scheduleType;
        }

        public void setScheduleType(ScheduleType scheduleType) {
            this.scheduleType = scheduleType;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
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

        public List<PubChannel> getPubChannels() {
            return pubChannels;
        }

        public void setPubChannels(List<PubChannel> pubChannels) {
            this.pubChannels = pubChannels;
        }

        public Map<String, ImageUrlExt> getImages() {
            return images;
        }

        public void setImages(Map<String, ImageUrlExt> images) {
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

		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (o == null || getClass() != o.getClass()) return false;

			MenuItem menuItem = (MenuItem) o;

			if (index != null ? !index.equals(menuItem.index) : menuItem.index != null) return false;

			return true;
		}

		@Override
		public int hashCode() {
			return index != null ? index.hashCode() : 0;
		}
	}
}
