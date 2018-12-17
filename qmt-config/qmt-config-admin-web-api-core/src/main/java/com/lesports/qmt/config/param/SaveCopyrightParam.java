package com.lesports.qmt.config.param;

import com.lesports.qmt.config.model.Copyright;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by zhangxudong@le.com on 2016/11/10.
 */
public class SaveCopyrightParam implements Serializable {
    private static final long serialVersionUID = 7220029539132825985L;
    private Long id;

    //发布包名称
    @NotBlank(message = "releasePackageName is required")
    private String releasePackageName;

    //直播 发布客户端
    private String clientPlatformId;

    //点播 发布客户端
    private String vodPlatformId;

    //屏蔽国家类型
    private Integer shieldAreaType;

    //屏蔽展示的国家
    private String shieldCountry;

    //上线/下线
    private boolean published = false;

    public Copyright toCopyright() {
        Copyright copyright = new Copyright();
        copyright.setId(this.getId());
        copyright.setReleasePackageName(this.getReleasePackageName());
        copyright.setClientPlatformId(this.getLongSet(this.getClientPlatformId()));
        copyright.setVodPlatformId(this.getLongSet(this.getVodPlatformId()));
        copyright.setShieldAreaType(this.getShieldAreaType());
        copyright.setShieldCountry(this.getStringSet(this.getShieldCountry()));
        copyright.setPublished(this.isPublished());
        return copyright;
    }
    //    public Copyright toCopyright(SaveCopyrightParam saveCopyrightParam) {
//        Copyright copyright = new Copyright();
//        if(null == saveCopyrightParam) return copyright;
//        copyright.setReleasePackageName(saveCopyrightParam.getReleasePackageName());
//        copyright.setClientPlatformId(this.getLongSet(saveCopyrightParam.getClientPlatformId()));
//        copyright.setVodPlatformId(this.getLongSet(saveCopyrightParam.getVodPlatformId()));
//        copyright.setShieldAreaType(saveCopyrightParam.getShieldAreaType());
//        copyright.setShieldCountry(this.getLongSet(saveCopyrightParam.getShieldCountry()));
//        copyright.setPublished(saveCopyrightParam.isPublished());
//        return copyright;
//    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReleasePackageName() {
        return releasePackageName;
    }

    public void setReleasePackageName(String releasePackageName) {
        this.releasePackageName = releasePackageName;
    }

    public String getClientPlatformId() {
        return clientPlatformId;
    }

    public void setClientPlatformId(String clientPlatformId) {
        this.clientPlatformId = clientPlatformId;
    }

    public String getVodPlatformId() {
        return vodPlatformId;
    }

    public void setVodPlatformId(String vodPlatformId) {
        this.vodPlatformId = vodPlatformId;
    }

    public Integer getShieldAreaType() {
        return shieldAreaType;
    }

    public void setShieldAreaType(Integer shieldAreaType) {
        this.shieldAreaType = shieldAreaType;
    }

    public String getShieldCountry() {
        return shieldCountry;
    }

    public void setShieldCountry(String shieldCountry) {
        this.shieldCountry = shieldCountry;
    }

    public boolean isPublished() {
        return published;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }

    private Set<String> getStringSet(String src) {
        Set<String> result = new HashSet<>();
        if (StringUtils.isEmpty(src))
            return result;
        String[] piece = src.split(",");
        for (String s : piece) {
            try {
                result.add(s);
            } catch (Exception e) {
                continue;
            }
        }
        return result;
    }

    private Set<Long> getLongSet(String src) {
        Set<Long> result = new HashSet<>();
        if (StringUtils.isEmpty(src))
            return result;
        String[] piece = src.split(",");
        for (String s : piece) {
            try {
                Long tmp = Long.parseLong(s);
                result.add(tmp);
            } catch (Exception e) {
                continue;
            }
        }
        return result;
    }
}
