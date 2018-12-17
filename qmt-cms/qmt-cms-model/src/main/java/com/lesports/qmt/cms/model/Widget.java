package com.lesports.qmt.cms.model;

import com.lesports.api.common.PublishStatus;
import com.lesports.qmt.cms.api.common.PageType;
import com.lesports.qmt.cms.api.common.WidgetType;
import com.lesports.qmt.model.support.QmtModel;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * User: ellios
 * Time: 16-11-28 : 下午5:07
 */
@Document(collection = "widgets")
public class Widget extends QmtModel<Long> {
    private static final long serialVersionUID = 7862322575343443374L;

    //组件名称
    private String name;
    //文件路径
    @Indexed(unique = true)
    private String path;
    //描述
    private String desc;
    //Widget类型
    private String type;
    @Field("page_type")
    //组件可以用于的页面类型
    private PageType pageType;
    //版本
    private String version;
    //是否被删除
    private Boolean deleted;
    //在线状态
    private PublishStatus status;
    //vm文件内容
    private String vm;
    //该组件的配置,json字符串
    private String conf;
    //该组件的其他信息,json字符串
    private String other;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
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

    public String getVm() {
        return vm;
    }

    public void setVm(String vm) {
        this.vm = vm;
    }

    public String getConf() {
        return conf;
    }

    public void setConf(String conf) {
        this.conf = conf;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public PageType getPageType() {
        return pageType;
    }

    public void setPageType(PageType pageType) {
        this.pageType = pageType;
    }
}