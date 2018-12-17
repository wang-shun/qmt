package com.lesports.qmt.web.api.core.vo;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * lesports-projects
 *
 * @author pangchuanxiao
 * @since 16-3-14
 */
public class GetLiveRoomCacheParam {
    private String liveId;
    private String splatId;

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(liveId).append(splatId).toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        boolean equals = false;
        if (null != obj && obj instanceof GetLiveRoomCacheParam) {
            GetLiveRoomCacheParam getLiveRoomCacheParam = (GetLiveRoomCacheParam) obj;
            equals = new EqualsBuilder().append(liveId, getLiveRoomCacheParam.getLiveId())
                    .append(splatId, getLiveRoomCacheParam.getSplatId())
                    .isEquals();
        }
        return equals;
    }

    public String getLiveId() {
        return liveId;
    }

    public void setLiveId(String liveId) {
        this.liveId = liveId;
    }

    public String getSplatId() {
        return splatId;
    }

    public void setSplatId(String splatId) {
        this.splatId = splatId;
    }

    public GetLiveRoomCacheParam(String liveId, String splatId) {

        this.liveId = liveId;
        this.splatId = splatId;
    }
}
