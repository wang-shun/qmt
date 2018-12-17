package com.lesports.qmt.cms.model;

import com.lesports.api.common.PublishStatus;
import com.lesports.qmt.model.support.QmtModel;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * User: ellios
 * Time: 16-11-28 : 下午5:21
 */
@Document(collection = "columns")
public class Column extends QmtModel<Long> {

    private static final long serialVersionUID = -475077168740515326L;

    //栏目名称
    private String name;
    //栏目标题
    private String title;
    //关键词
    private String keyword;
    //描述
    private String desc;
    //url中的路径名称
    private String path;
    @Field("full_path")
    //完成的url路径，带父级的url
    private String fullPath;
    //父级栏目路径
    @Field("parent_id")
    private Long parentId;
    //频道id
    @Field("channel_id")
    private Long channelId;
    //子频道id
    @Field("sub_channel_id")
    private Long subChannelId;
    //是否被删除
    private Boolean deleted;
    //在线状态
    private PublishStatus status;

    //M站 模版路径
    @Field("m_template_url")
    private String mTemplateUrl;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public PublishStatus getStatus() {
        return status;
    }

    public void setStatus(PublishStatus status) {
        this.status = status;
    }

    public Long getChannelId() {
        return channelId;
    }

    public void setChannelId(Long channelId) {
        this.channelId = channelId;
    }

    public String getFullPath() {
        return fullPath;
    }

    public void setFullPath(String fullPath) {
        this.fullPath = fullPath;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getSubChannelId() {
        return subChannelId;
    }

    public void setSubChannelId(Long subChannelId) {
        this.subChannelId = subChannelId;
    }

    public String getmTemplateUrl() {
        return mTemplateUrl;
    }

    public void setmTemplateUrl(String mTemplateUrl) {
        this.mTemplateUrl = mTemplateUrl;
    }
}
