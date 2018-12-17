package com.lesports.qmt.web.api.core.vo;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * lesports-projects
 *
 * @author pangchuanxiao
 * @since 16-3-14
 */
public class GetLiveStreamCacheParam {
    private String selectId;
    private String splatId;
    private String withCibnStreams;
    private String liveId;

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(selectId).append(splatId).append(withCibnStreams).append(liveId).toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        boolean equals = false;
        if (null != obj && obj instanceof GetLiveStreamCacheParam) {
            GetLiveStreamCacheParam getLiveStreamCacheParam = (GetLiveStreamCacheParam) obj;
            equals = new EqualsBuilder().append(selectId, getLiveStreamCacheParam.getSelectId())
                    .append(splatId, getLiveStreamCacheParam.getSplatId())
                    .append(withCibnStreams, getLiveStreamCacheParam.getWithCibnStreams())
                    .append(liveId, getLiveStreamCacheParam.getLiveId())
                    .isEquals();
        }
        return equals;
    }

    public String getSelectId() {
        return selectId;
    }

    public void setSelectId(String selectId) {
        this.selectId = selectId;
    }

    public String getSplatId() {
        return splatId;
    }

    public void setSplatId(String splatId) {
        this.splatId = splatId;
    }

	public String getLiveId() {
		return liveId;
	}

	public void setLiveId(String liveId) {
		this.liveId = liveId;
	}

	public String getWithCibnStreams() {
		return withCibnStreams;
	}

	public void setWithCibnStreams(String withCibnStreams) {
		this.withCibnStreams = withCibnStreams;
	}

	public GetLiveStreamCacheParam(String selectId, String splatId, String withCibnStreams, String liveId) {

        this.selectId = selectId;
        this.splatId = splatId;
        this.withCibnStreams = withCibnStreams;
		this.liveId = liveId;
    }
}
