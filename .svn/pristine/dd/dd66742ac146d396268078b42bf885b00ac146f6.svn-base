package com.lesports.qmt.sbc.model;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.lesports.api.common.ImageUrlExt;
import com.lesports.qmt.model.support.QmtModel;
import com.lesports.qmt.sbc.api.dto.ResourceItemType;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * create by wangjichuan  date:16-11-22 time:10:58
 */
public class ResourceContent extends QmtModel<Long>{
    private static final long serialVersionUID = -8823801996303025408L;

    //资源位id
    private Long resourceId;
    //资源项对应的内容id,如果没有对应内容为0
    @Field("item_id")
    private String itemId;
    //顺序
    private Integer order;
    //名称
    private String name;
    //短名称
    @Field("short_name")
    private String shortName;
    //各种规格的图片url
    @Field("cover_image")
    private Map<String, ImageUrlExt> coverImage = Maps.newHashMap();
    //链接地址
    @Field("link_url")
    private List<QmtResource.ResourceLink> links = Lists.newArrayList();

    @Field("content_items")
    private List<ContentItem> contentItems;

    //是否删除
    private Boolean deleted;

    @Field("tag_ids")
    private List<Long> tagIds = Lists.newArrayList();//标签id   赛事id

    //标签类型
    @Field("tag_type")
    private String tagType;

    //比赛赛季  数据榜单需要
    private String season;

    @Field("overd_round")
    private Integer overdRound;//已结束的场次数目

    @Field("living_round")
    private Integer livingRound;//正在直播的场次数目

    @Field("to_start_round")
    private Integer toStartRound;//未开始场次数目

    private String content;

    @Field("content_type")
    private ResourceItemType contentType;//内容类型 RICHTEXT正文 IMAGE_ALBUM图集 TOPIC专题  榜单类型

    @Field("data_search")
    private List<ResourceItemType> dataSearch;//里面也是内容类型，只是搜索数据的条件

    @Field("sub_content_type")
    private ResourceItemType subContentType;

    @Field("time_order")
    private int timeOrder;//时间排序

    @Field("star_level")
    private int starLevel;//正文星级   1:3星 2:2星及以上  -1:全部

    @Field("content_sum")
    private int contentSum;//内容数目

    @Field("order_type")
    private int orderType;//1月访问量2周访问量3日访问量

    @Field("pc_img")
    private String pcImg;

    @Field("mobile_img")
    private String mobileImg;

    @Field("ipad_img")
    private String ipadImg;

    @Field("tv_img")
    private String tvImg;

    //开机图开始时间 yyyyMMddHHmmss
    @Field("start_time")
    private String startTime;
    //开机图结束时间 yyyyMMddHHmmss
    @Field("end_time")
    private String endTime;

    @Field("duration_time")
    private int durationTime;//开机图持续时间

    @Field("show_title")
    private boolean showTitle;//是否显示标题，true是显示  false不显示

    @Field("h5_url")
    private String h5Url;

    @Field("other_content")
    private String otherContent;//tv内容版块  选择其他，之后填的数据，可能json，可能文字，字符串类型

    public List<ResourceItemType> getDataSearch() {
        return dataSearch;
    }

    public void setDataSearch(List<ResourceItemType> dataSearch) {
        this.dataSearch = dataSearch;
    }

    public static class ContentItem implements Serializable {
        private static final long serialVersionUID = 524837580225657947L;
        private String title;
        @Field("show_title")
        private boolean showTitle;
        private ResourceItemType type;
        private String value;
        private String image;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public boolean isShowTitle() {
            return showTitle;
        }

        public void setShowTitle(boolean showTitle) {
            this.showTitle = showTitle;
        }

        public ResourceItemType getType() {
            return type;
        }

        public void setType(ResourceItemType type) {
            this.type = type;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public Map<String, ImageUrlExt> getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(Map<String, ImageUrlExt> coverImage) {
        this.coverImage = coverImage;
    }

    public List<ContentItem> getContentItems() {
        return contentItems;
    }

    public void setContentItems(List<ContentItem> contentItems) {
        this.contentItems = contentItems;
    }

    public Long getResourceId() {
        return resourceId;
    }

    public void setResourceId(Long resourceId) {
        this.resourceId = resourceId;
    }

    public List<Long> getTagIds() {
        return tagIds;
    }

    public void setTagIds(List<Long> tagIds) {
        this.tagIds = tagIds;
    }

    public Integer getOverdRound() {
        return overdRound;
    }

    public void setOverdRound(Integer overdRound) {
        this.overdRound = overdRound;
    }

    public Integer getLivingRound() {
        return livingRound;
    }

    public void setLivingRound(Integer livingRound) {
        this.livingRound = livingRound;
    }

    public Integer getToStartRound() {
        return toStartRound;
    }

    public void setToStartRound(Integer toStartRound) {
        this.toStartRound = toStartRound;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTvImg() {
        return tvImg;
    }

    public void setTvImg(String tvImg) {
        this.tvImg = tvImg;
    }

    public String getPcImg() {
        return pcImg;
    }

    public void setPcImg(String pcImg) {
        this.pcImg = pcImg;
    }

    public String getMobileImg() {
        return mobileImg;
    }

    public void setMobileImg(String mobileImg) {
        this.mobileImg = mobileImg;
    }

    public String getIpadImg() {
        return ipadImg;
    }

    public void setIpadImg(String ipadImg) {
        this.ipadImg = ipadImg;
    }

    public int getOrderType() {
        return orderType;
    }

    public void setOrderType(int orderType) {
        this.orderType = orderType;
    }

    public int getContentSum() {
        return contentSum;
    }

    public void setContentSum(int contentSum) {
        this.contentSum = contentSum;
    }

    public int getStarLevel() {
        return starLevel;
    }

    public void setStarLevel(int starLevel) {
        this.starLevel = starLevel;
    }

    public int getTimeOrder() {
        return timeOrder;
    }

    public void setTimeOrder(int timeOrder) {
        this.timeOrder = timeOrder;
    }

    public ResourceItemType getContentType() {
        return contentType;
    }

    public void setContentType(ResourceItemType contentType) {
        this.contentType = contentType;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public List<QmtResource.ResourceLink> getLinks() {
        return links;
    }

    public void setLinks(List<QmtResource.ResourceLink> links) {
        this.links = links;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public int getDurationTime() {
        return durationTime;
    }

    public void setDurationTime(int durationTime) {
        this.durationTime = durationTime;
    }

    public boolean isShowTitle() {
        return showTitle;
    }

    public void setShowTitle(boolean showTitle) {
        this.showTitle = showTitle;
    }

    public String getH5Url() {
        return h5Url;
    }

    public void setH5Url(String h5Url) {
        this.h5Url = h5Url;
    }

    public String getOtherContent() {
        return otherContent;
    }

    public void setOtherContent(String otherContent) {
        this.otherContent = otherContent;
    }

    public ResourceItemType getSubContentType() {
        return subContentType;
    }

    public void setSubContentType(ResourceItemType subContentType) {
        this.subContentType = subContentType;
    }

    public String getTagType() {
        return tagType;
    }

    public void setTagType(String tagType) {
        this.tagType = tagType;
    }
}
