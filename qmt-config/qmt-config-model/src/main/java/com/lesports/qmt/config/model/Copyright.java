package com.lesports.qmt.config.model;

import com.google.common.collect.Sets;
import com.lesports.qmt.model.support.QmtModel;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import java.util.Set;

/**
 * Created by zhangxudong@le.com on 11/9/16.
 */
@Document(collection = "copyrights")
public class Copyright extends QmtModel<Long> {

    //发布包名称
    @Field("release_package_name")
    private String releasePackageName;

    //直播 发布客户端
    @Field("client_platform_id")
    private Set<Long> clientPlatformId = Sets.newHashSet();

    //点播 发布客户端
    @Field("vod_platform_id")
    private Set<Long> vodPlatformId = Sets.newHashSet();

    //屏蔽国家类型
    @Field("shield_area_type")
    private Integer shieldAreaType;

    //屏蔽展示的国家
    @Field("shield_country")
    private Set<String> shieldCountry = Sets.newHashSet();

    //上线/下线
    private boolean published = false;

    public Set<Long> getClientPlatformId() {
        return clientPlatformId;
    }

    public void setClientPlatformId(Set<Long> clientPlatformId) {
        this.clientPlatformId = clientPlatformId;
    }

    public Set<Long> getVodPlatformId() {
        return vodPlatformId;
    }

    public void setVodPlatformId(Set<Long> vodPlatformId) {
        this.vodPlatformId = vodPlatformId;
    }

    public Set<String> getShieldCountry() {
        return shieldCountry;
    }

    public void setShieldCountry(Set<String> shieldCountry) {
        this.shieldCountry = shieldCountry;
    }

    public String getReleasePackageName() {
        return releasePackageName;
    }

    public void setReleasePackageName(String releasePackageName) {
        this.releasePackageName = releasePackageName;
    }

    public Integer getShieldAreaType() {
        return shieldAreaType;
    }

    public void setShieldAreaType(Integer shieldAreaType) {
        this.shieldAreaType = shieldAreaType;
    }

    public boolean isPublished() {
        return published;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }
}
