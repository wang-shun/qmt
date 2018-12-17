package com.lesports.qmt.cms.model;

import com.lesports.api.common.PublishStatus;
import com.lesports.qmt.cms.api.common.PageType;
import com.lesports.qmt.model.support.QmtModel;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

/**
 * 栏目页
 * User: ellios
 * Time: 16-11-28 : 下午5:07
 */
@Document(collection = "column_pages")
public class ColumnPage extends QmtModel<Long> {

    private static final long serialVersionUID = 2408305406214026389L;

    //标题
    private String name;
    //布局id
    @Field("layout_id")
    private Long layoutId;
    //栏目id
    @Field("column_id")
    private Long columnId;
    //页面地址
    private String url;
    //json格式的数据内容
    private String data;
    //m站json格式的数据内容
    @Field("m_data")
    private String mData;
    //json格式的区域组件布局数据
    private String conf;
    //是否被删除
    private Boolean deleted;
    //发布状态
    private PublishStatus status;
    //页面类型 PC or MOBILE
    private PageType type;
    //vm文件内容
    private String vm;
    //页面上所有widget的id列表
    @Field("widget_paths")
    private List<String> widgetPaths;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getLayoutId() {
        return layoutId;
    }

    public void setLayoutId(Long layoutId) {
        this.layoutId = layoutId;
    }

    public Long getColumnId() {
        return columnId;
    }

    public void setColumnId(Long columnId) {
        this.columnId = columnId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getConf() {
        return conf;
    }

    public void setConf(String conf) {
        this.conf = conf;
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

    public PageType getType() {
        return type;
    }

    public void setType(PageType type) {
        this.type = type;
    }

    public String getVm() {
        return vm;
    }

    public void setVm(String vm) {
        this.vm = vm;
    }

    public List<String> getWidgetPaths() {
        return widgetPaths;
    }

    public void setWidgetPaths(List<String> widgetPaths) {
        this.widgetPaths = widgetPaths;
    }

    public String getmData() {
        return mData;
    }

    public void setmData(String mData) {
        this.mData = mData;
    }
}
