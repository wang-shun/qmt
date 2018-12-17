package com.lesports.qmt.sbd.model;

import java.io.Serializable;

/**
 * Created by lufei1 on 2016/12/6.
 */
public class Partner implements Serializable {
    private static final long serialVersionUID = -2596471703517279927L;
    //合作方类型
    private PartnerType type;
    //合作方实体id
    private String id;
    //合作方实体code
    private String code;


    public PartnerType getType() {
        return type;
    }

    public void setType(PartnerType type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
