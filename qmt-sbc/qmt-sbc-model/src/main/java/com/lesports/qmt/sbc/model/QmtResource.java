package com.lesports.qmt.sbc.model;

/**
 * User: ellios
 * Time: 16-10-23 : 下午2:42
 */

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.lesports.api.common.ImageUrlExt;
import com.lesports.qmt.model.support.QmtModel;
import com.lesports.qmt.sbc.api.dto.ResourceDataType;
import com.lesports.qmt.sbc.api.dto.ResourceUpdateType;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * 资源位
 * User: ellios
 * Time: 16-9-11 : 下午3:45
 */
@Document(collection = "resources")
public class QmtResource extends QmtModel<Long> {

    private static final long serialVersionUID = -5808869531832888406L;
    //资源位类型
    private ResourceDataType type;
    //更新方式,自动or手动
    @Field("update_type")
    private ResourceUpdateType updateType;
    //名称
    private String name;
    //短名称
    @Field("short_name")
    private String shortName;
    //描述
    private String desc;
    //是否是资源位组
    private Boolean group = false;
    //是否被删除
    private Boolean deleted;
    //图片url
    @Field("image_url")
    private ImageUrlExt imageUrl;
    //资源位下的内容
    @Field("resource_content_ids")
    private Set<Long> resourceContentIds = Sets.newHashSet();
    //资源位上附加的一些链接位置
    private List<ResourceLink> links = Lists.newArrayList();
    //资源位自身的链接地址
    private String url;

    //TV渠道 类型   LETV  通用
    @Field("tv_channel")
    private String tvChannel;
    //资源位父id
    @Field("p_ids")
    private Set<Long> pIds = Sets.newHashSet();
    //资源位组的子id
    @Field("child_ids")
    private  Set<Long> childIds = Sets.newLinkedHashSet();

//    private

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Boolean getGroup() {
        return group;
    }

    public void setGroup(Boolean group) {
        this.group = group;
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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getTvChannel() {
        return tvChannel;
    }

    public void setTvChannel(String tvChannel) {
        this.tvChannel = tvChannel;
    }

    public ImageUrlExt getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(ImageUrlExt imageUrl) {
        this.imageUrl = imageUrl;
    }

    public ResourceUpdateType getUpdateType() {
        return updateType;
    }

    public void setUpdateType(ResourceUpdateType updateType) {
        this.updateType = updateType;
    }

    public Set<Long> getResourceContentIds() {
        return resourceContentIds;
    }

    public void setResourceContentIds(Set<Long> resourceContentIds) {
        this.resourceContentIds = resourceContentIds;
    }

    public void addResourceContentId(Long resourceContentId) {
        this.resourceContentIds.add(resourceContentId);
    }

    public void deleteResourceContentId(Long resourceContentId) {
        this.resourceContentIds.remove(resourceContentId);
    }

    public List<ResourceLink> getLinks() {
        return links;
    }

    public void setLinks(List<ResourceLink> links) {
        this.links = links;
    }

    public void addLink(ResourceLink link) {
        this.links.add(link);
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public ResourceDataType getType() {
        return type;
    }

    public void setType(ResourceDataType type) {
        this.type = type;
    }

    public Set<Long> getPIds() {
        return pIds;
    }

    public void setPIds(Set<Long> pIds) {
        this.pIds = pIds;
    }

    public void addPIds(Set<Long> pIds) {
        this.pIds.addAll(pIds);
    }
    public void addPIds(Long pId) {
        this.pIds.add(pId);
    }
    public void deletePIds(Long pId) {
        this.pIds.remove(pId);
        if(pIds.isEmpty()){
            pIds.add(1L); //如果空了就添加一个1，站位用
        }
    }
    public Set<Long> getChildIds() {
        return childIds;
    }

    public void setChildIds(Set<Long> childIds) {
        this.childIds = childIds;
    }

    public void addChildIds(Set<Long> pIds) {
        this.childIds.addAll(pIds);
    }
    public void deleteChildIds(Set<Long> pIds) {
        this.childIds.removeAll(pIds);
    }

    public static class PublishStatus implements  Serializable{
//        private static final long serialVersionUID = SlL;

    }


    /**
     * 资源位上的链接信息
     */
    public static class ResourceLink implements Serializable {
        private static final long serialVersionUID = 1785069483032143205L;
        //链接名称
        private String name;
        //链接
        private String url;
        //顺序
        private Integer order;

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

        public Integer getOrder() {
            return order;
        }

        public void setOrder(Integer order) {
            this.order = order;
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .append("name", name)
                    .append("url", url)
                    .append("order", order)
                    .toString();
        }
    }
}


