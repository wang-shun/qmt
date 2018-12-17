package com.lesports.qmt.web.api.core.vo;

import com.lesports.api.common.CallerParam;
import com.lesports.utils.Utf8KeyCreater;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * lesports-projects
 *
 * @author pangchuanxiao
 * @since 16-3-14
 */
public class GetRealtimeCacheParam {
    private Long episodeId;
    private CallerParam caller;

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(episodeId).append(caller).toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        boolean equals = false;
        if (null != obj && obj instanceof GetRealtimeCacheParam) {
            GetRealtimeCacheParam getDetailEpisodeVoParam = (GetRealtimeCacheParam)obj;
            equals = new EqualsBuilder().append(episodeId, getDetailEpisodeVoParam.getEpisodeId())
                    .append(caller.getCallerId(), getDetailEpisodeVoParam.getCaller().getCallerId())
                    .append(caller.getCountry(), getDetailEpisodeVoParam.getCaller().getCountry())
                    .append(caller.getLanguage(), getDetailEpisodeVoParam.getCaller().getLanguage())
                    .isEquals();
        }
        return equals;
    }

    public GetRealtimeCacheParam() {
    }

    public GetRealtimeCacheParam(Long episodeId, CallerParam caller) {
        this.episodeId = episodeId;
        this.caller = caller;
    }

    public Long getEpisodeId() {
        return episodeId;
    }

    public void setEpisodeId(Long episodeId) {
        this.episodeId = episodeId;
    }

    public CallerParam getCaller() {
        return caller;
    }

    public void setCaller(CallerParam caller) {
        this.caller = caller;
    }

    public String getCacheKey(Utf8KeyCreater<Long> keyCreater) {
        return keyCreater.textKey(getEpisodeId()) + "_" + getCaller().getCallerId()
                + "_" + getCaller().getCountry() + "_" + getCaller().getLanguage();
    }
}
