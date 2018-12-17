package com.lesports.qmt.tlive.model;

import com.lesports.qmt.model.support.QmtModel;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * Created by lufei1 on 2015/9/13.
 */
@Document(collection = "text_live_images")
public class TextLiveImage extends QmtModel<Long> {
    private static final long serialVersionUID = -4746629846270707986L;

    //关联的图文直播消息id
    @Field("live_message_id")
    private Long liveMessageId;
    //图片标题
    private String name;
    //图片描述
    private String desc;
    //本地图片存储路径
    @Field("image_path")
    private String imagePath;
    //图片url
    @Field("image_url")
    private String imageUrl;
    //展示顺序
    @Field("show_order")
    private Integer showOrder;
    //是否被删除
    private Boolean deleted;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getLiveMessageId() {
        return liveMessageId;
    }

    public void setLiveMessageId(Long liveMessageId) {
        this.liveMessageId = liveMessageId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Integer getShowOrder() {
        return showOrder;
    }

    public void setShowOrder(Integer showOrder) {
        this.showOrder = showOrder;
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

    @Override
    public String toString() {
        return "TextLiveImage{" +
                "id=" + id +
                ", liveMessageId=" + liveMessageId +
                ", name='" + name + '\'' +
                ", desc='" + desc + '\'' +
                ", imagePath='" + imagePath + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", showOrder=" + showOrder +
                ", createAt='" + createAt + '\'' +
                ", updateAt='" + updateAt + '\'' +
                ", deleted=" + deleted +
                '}';
    }
}
