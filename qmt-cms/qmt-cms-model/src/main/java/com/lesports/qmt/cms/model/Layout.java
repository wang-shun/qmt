package com.lesports.qmt.cms.model;

import com.lesports.api.common.PublishStatus;
import com.lesports.qmt.cms.api.common.PageType;
import com.lesports.qmt.model.support.QmtModel;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;

/**
 * 页面布局，模板
 * User: ellios
 * Time: 16-11-28 : 下午5:07
 */
@Document(collection = "layouts")
public class Layout extends QmtModel<Long> {

    private static final long serialVersionUID = -4089338630941866033L;

    //名称
    @Indexed(unique = true)
    private String name;
    //文件路径
    @Indexed(unique = true)
    private String path;
    //描述
    private String desc;
    //类型，PC or M
    private PageType type;
    //版本
    private String version;
    //是否被删除
    private Boolean deleted;
    //在线状态
    private PublishStatus status;
    //vm文件内容
    private String vm;
    //布局配置
    private String conf;
    //空白页面
    @Field("empty_page")
    private String emptyPage;
    //布局模板
    private String tpl;
    //该布局的其他信息
    private String other;
    @Field("example_page_id")
    //示例的页面id
    private Long examplePageId;
    //是否已经完成了
    @Field("is_finished")
    private boolean isFinished;


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

    public PageType getType() {
        return type;
    }

    public void setType(PageType type) {
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

    public String getEmptyPage() {
        return emptyPage;
    }

    public void setEmptyPage(String emptyPage) {
        this.emptyPage = emptyPage;
    }

    public String getTpl() {
        return tpl;
    }

    public void setTpl(String tpl) {
        this.tpl = tpl;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public Long getExamplePageId() {
        return examplePageId;
    }

    public void setExamplePageId(Long examplePageId) {
        this.examplePageId = examplePageId;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public void setFinished(boolean isFinished) {
        this.isFinished = isFinished;
    }
}
