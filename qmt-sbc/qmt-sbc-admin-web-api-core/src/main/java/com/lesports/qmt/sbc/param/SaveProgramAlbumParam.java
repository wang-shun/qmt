package com.lesports.qmt.sbc.param;

import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

/**
 * Created by denghui on 2016/12/6.
 */
public class SaveProgramAlbumParam implements Serializable {
    private static final long serialVersionUID = 4778486160325090688L;

    private Long id;
    //自制专辑名称
    @NotBlank(message = "name is required")
    private String name;
    //自制专辑图片
    private String logo;
    //是否推荐
    private Boolean recommend;

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

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public Boolean getRecommend() {
        return recommend;
    }

    public void setRecommend(Boolean recommend) {
        this.recommend = recommend;
    }
}
