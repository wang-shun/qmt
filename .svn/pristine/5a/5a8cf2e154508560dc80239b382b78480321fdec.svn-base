package com.lesports.qmt.config.vo;

import com.lesports.api.common.MmsPlatform;
import com.lesports.api.common.Platform;
import com.lesports.qmt.config.QmtConfigConstants;
import com.lesports.qmt.config.client.QmtClientPlatformInternalApis;
import com.lesports.qmt.config.model.ClientPlatform;
import com.lesports.utils.math.LeNumberUtils;
import org.apache.commons.collections.CollectionUtils;

import java.util.HashSet;
import java.util.Set;

/**
 * trunk.
 *
 * @author pangchuanxiao
 * @since 2016/12/5
 */
public class IdNameItemVo {
    private Long id;
    private String name;

    public static Set<IdNameItemVo> convertClientPlatform(Set<Long> ids) {
        if (CollectionUtils.isNotEmpty(ids)) {
            Set<IdNameItemVo> clientPlatforms = new HashSet<>();
            for (long id : ids) {
                ClientPlatform clientPlatform = QmtClientPlatformInternalApis.getClientPlatformById(id);
                IdNameItemVo idNameItemVo = new IdNameItemVo(clientPlatform.getId(), clientPlatform.getName());
                clientPlatforms.add(idNameItemVo);
            }
            return clientPlatforms;
        }
        return null;
    }

    public static Set<IdNameItemVo> convertVodPlatform(Set<Long> ids) {
        if (CollectionUtils.isNotEmpty(ids)) {
            Set<IdNameItemVo> clientPlatforms = new HashSet<>();
            for (Long id : ids) {
                if (LeNumberUtils.toInt(id) < 100) {
                    Platform clientPlatform = Platform.findByValue(id.intValue());
                    IdNameItemVo idNameItemVo = new IdNameItemVo(id, QmtConfigConstants.LESPORTS_PLATFORM_MAPPING.get(clientPlatform));
                    clientPlatforms.add(idNameItemVo);
                } else {
                    MmsPlatform clientPlatform = MmsPlatform.findByValue(id.intValue());
                    IdNameItemVo idNameItemVo = new IdNameItemVo(id, QmtConfigConstants.MMS_PLATFORM_MAPPING.get(clientPlatform));
                    clientPlatforms.add(idNameItemVo);
                }
            }
            return clientPlatforms;
        }
        return null;
    }
    public IdNameItemVo() {
    }

    public IdNameItemVo(Long id, String name) {
        this.id = id;
        this.name = name;
    }

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
}
