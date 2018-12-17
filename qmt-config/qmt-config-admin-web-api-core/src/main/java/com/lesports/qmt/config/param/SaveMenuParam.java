package com.lesports.qmt.config.param;

import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

/**
 * Created by denghui on 2016/12/11.
 */
public class SaveMenuParam implements Serializable {

    private static final long serialVersionUID = -6686872947322486160L;
    private Long id;
    @NotBlank(message = "name is required")
    private String name;
    @NotBlank(message = "platform rs required")
    private String platforms;

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

    public String getPlatforms() {
        return platforms;
    }

    public void setPlatforms(String platforms) {
        this.platforms = platforms;
    }

}
