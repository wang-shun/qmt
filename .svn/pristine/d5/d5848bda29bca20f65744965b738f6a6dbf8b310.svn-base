package com.lesports.qmt.web.api.core.vo;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * lesports-projects
 *
 * @author pangchuanxiao
 * @since 16-3-14
 */
public class GetTVideoCacheParam {
    private Long vid;
    private long caller;

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(vid).append(caller).toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        boolean equals = false;
        if (null != obj && obj instanceof GetTVideoCacheParam) {
            GetTVideoCacheParam getDetailEpisodeVoParam = (GetTVideoCacheParam) obj;
            equals = new EqualsBuilder().append(vid, getDetailEpisodeVoParam.getVid())
                    .append(caller, getDetailEpisodeVoParam.getCaller())
                    .isEquals();
        }
        return equals;
    }

    public GetTVideoCacheParam(Long vid, long caller) {
        this.vid = vid;
        this.caller = caller;
    }

    public GetTVideoCacheParam() {
    }

    public Long getVid() {
        return vid;
    }

    public void setVid(Long vid) {
        this.vid = vid;
    }

    public long getCaller() {
        return caller;
    }

    public void setCaller(long caller) {
        this.caller = caller;
    }
}
