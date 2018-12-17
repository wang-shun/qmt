package com.lesports.qmt.config.model;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.lesports.api.common.LangString;
import com.lesports.qmt.model.support.QmtModel;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;
import java.util.Set;

/**
 * 字典目录项
 * Created by yangyu on 2015/5/27.
 */
@Document(collection = "dict")
public class DictEntry extends QmtModel<Long> {
    private static final long serialVersionUID = -28819333470825837L;

    //名称
    private String name;
    //多语言字典名称
    @Field("multi_lang_names")
    private List<LangString> multiLangNames;
    //父ID
    @Field("parent_id")
    private Long parentId;
    //是否被删除
    private Boolean deleted;
    //顶级父id
    @Field("top_parent_id")
    private Long topParentId;
    //字典项适用的项目
    @Field("game_f_types")
    private Set<Long> gameFTypes = Sets.newHashSet();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<LangString> getMultiLangNames() {
        return multiLangNames;
    }

    public void setMultiLangNames(List<LangString> multiLangNames) {
        this.multiLangNames = multiLangNames;
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

    public Long getTopParentId() {
        return topParentId;
    }

    public void setTopParentId(Long topParentId) {
        this.topParentId = topParentId;
    }

    public Set<Long> getGameFTypes() {
        return gameFTypes;
    }

    public void setGameFTypes(Set<Long> gameFTypes) {
        this.gameFTypes = gameFTypes;
    }

    public String getReadableName() {
        return getReadableName(this.name);
    }

    private String getReadableName(String name) {
        if (name.startsWith("[") && name.indexOf("]") > 0) {
            int index = name.indexOf("]");
            return name.substring(index + 1, name.length());
        }
        return name;
    }

    public List<LangString> getMultiLangReadableNames() {
        if (multiLangNames == null) {
            return  null;
        }
        List<LangString> readables = Lists.newArrayList();
        for (LangString lang : multiLangNames) {
            LangString readable = new LangString();
            readable.setLanguage(lang.getLanguage());
            readable.setValue(getReadableName(lang.getValue()));
            readables.add(readable);
        }
        return readables;
    }
}
