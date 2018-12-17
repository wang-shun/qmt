package com.lesports.qmt.config.model;

import com.lesports.api.common.Platform;
import com.lesports.qmt.model.support.QmtModel;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * Created by gengchengliang on 2015/9/18.
 */
@Document(collection = "callers")
public class Caller extends QmtModel<Long> {

    private static final long serialVersionUID = -815070746269019124L;

    //名称
    private String name;
    //caller对应的平台
    private Platform platform;
    //对应直播后台的产品线id
    @Field("splat_id")
    private Long splatId;

    public Long getSplatId() {
        return splatId;
    }

    public void setSplatId(Long splatId) {
        this.splatId = splatId;
    }

    public Platform getPlatform() {
        return platform;
    }

    public void setPlatform(Platform platform) {
        this.platform = platform;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Caller{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", platform=").append(platform);
        sb.append(", splatId='").append(splatId).append('\'');
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Caller)) return false;

        Caller caller = (Caller) o;

        if (id != null ? !id.equals(caller.id) : caller.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
