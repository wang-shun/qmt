package com.lesports.qmt.sbd.model;

import com.lesports.api.common.LangString;
import com.lesports.qmt.model.support.QmtModel;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

/**
 * Created by qiaohongxin on 2016/12/15.
 */
@Document(collection = "live_events")
public class LiveEvent extends QmtModel<Long> {
    private static final long serialVersionUID = 5880557446807800136L;
    //数据提供方对应的事件标示
    @Field("partners")
    private List<Partner> partners;
    //事件所属类型ID
    @Field("parent_type")
    private Long parentType;
    //事件名
    @Field("name")
    private String name;
    //事件名称多语言
    @Field("multi_lang_names")
    private List<LangString> multiLangNames;
    //是否被删除
    private Boolean deleted;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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


    public List<LangString> getMultiLangNames() {
        return multiLangNames;
    }

    public void setMultiLangNames(List<LangString> multiLangNames) {
        this.multiLangNames = multiLangNames;
    }

    public List<Partner> getPartners() {
        return partners;
    }

    public void setPartners(List<Partner> partners) {
        this.partners = partners;
    }

    public Long getParentType() {
        return parentType;
    }

    public void setParentType(Long parentType) {
        this.parentType = parentType;
    }

    @Override
    public String toString() {
        return "LiveEvents{" +
                "id=" + id +
                ", partners='" + partners + '\'' +
                ", name='" + name + '\'' +
                ", createAt='" + createAt + '\'' +
                ", updateAt='" + updateAt + '\'' +
                ", deleted=" + deleted +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LiveEvent)) return false;
        LiveEvent event = (LiveEvent) o;
        if (id != null ? !id.equals(event.id) : event.id != null) return false;
        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }


}
