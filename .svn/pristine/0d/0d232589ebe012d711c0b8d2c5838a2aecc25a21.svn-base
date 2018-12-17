package com.lesports.qmt.config.model;

import com.lesports.api.common.CountryCode;
import com.lesports.api.common.EcoCompany;
import com.lesports.api.common.Platform;
import com.lesports.qmt.model.support.QmtModel;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * Created by zhangxudong@le.com on 11/9/16.
 */
@Document(collection = "client_platforms")
public class ClientPlatform extends QmtModel<Long> {

    //地区
    @Field("client_area")
    private CountryCode country;
    //客户端类型
    @Field("platform")
    private Platform platform;
    //公司
    private EcoCompany company;
    //发布平台id
    @Field("client_id")
    private String clientId;
    //发布平台名称
    private String name;

    @Field("deleted")
    private Boolean deleted = false;

    public CountryCode getCountry() {
        return country;
    }

    public void setCountry(CountryCode country) {
        this.country = country;
    }

    public Platform getPlatform() {
        return platform;
    }

    public void setPlatform(Platform platform) {
        this.platform = platform;
    }

    public EcoCompany getCompany() {
        return company;
    }

    public void setCompany(EcoCompany company) {
        this.company = company;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }
}
