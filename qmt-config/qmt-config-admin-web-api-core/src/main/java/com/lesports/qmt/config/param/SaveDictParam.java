package com.lesports.qmt.config.param;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

/**
 * Created by denghui on 2016/12/8.
 */
public class SaveDictParam implements Serializable {

    private Long id;
    @NotBlank(message = "name is required")
    private String name;
    private Long parentId;
    private String gameFTypes;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getGameFTypes() {
        return gameFTypes;
    }

    public void setGameFTypes(String gameFTypes) {
        this.gameFTypes = gameFTypes;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}
