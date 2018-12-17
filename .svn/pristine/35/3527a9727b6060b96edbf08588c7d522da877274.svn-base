package com.lesports.qmt.web.api.core.vo;

import com.lesports.model.VodReq;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * lesports-projects
 *
 * @author pangchuanxiao
 * @since 16-3-14
 */
public class GetVrsChainCacheParam {
    private String mmsid;
    private String tss;
    private String vType;
    private Integer platId;
    private Integer splatId;
    private String clientIp;
    private Integer bc;

    public GetVrsChainCacheParam(VodReq vodReq, String mmsid) {
        this.tss = vodReq.getTss();
        this.vType = vodReq.getVtype();
        this.platId = vodReq.getPlatid();
        this.splatId = vodReq.getSplatid();
        this.clientIp = vodReq.getClientip();
        this.bc = vodReq.getBc();
        this.mmsid = mmsid;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(mmsid).append(tss).append(vType).append(platId).append(clientIp).append(splatId).append(bc).toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        boolean equals = false;
        if (null != obj && obj instanceof GetVrsChainCacheParam) {
            GetVrsChainCacheParam getLiveStreamCacheParam = (GetVrsChainCacheParam) obj;
            equals = new EqualsBuilder().append(mmsid, getLiveStreamCacheParam.getMmsid())
                    .append(splatId, getLiveStreamCacheParam.getSplatId())
                    .append(tss, getLiveStreamCacheParam.getTss())
                    .append(platId, getLiveStreamCacheParam.getPlatId())
                    .append(clientIp, getLiveStreamCacheParam.getClientIp())
                    .append(vType, getLiveStreamCacheParam.getvType())
                    .append(bc, getLiveStreamCacheParam.getBc())
                    .isEquals();
        }
        return equals;
    }

    public String getMmsid() {
        return mmsid;
    }

    public void setMmsid(String mmsid) {
        this.mmsid = mmsid;
    }

    public String getTss() {
        return tss;
    }

    public void setTss(String tss) {
        this.tss = tss;
    }

    public String getvType() {
        return vType;
    }

    public void setvType(String vType) {
        this.vType = vType;
    }

    public Integer getPlatId() {
        return platId;
    }

    public void setPlatId(Integer platId) {
        this.platId = platId;
    }

    public Integer getSplatId() {
        return splatId;
    }

    public void setSplatId(Integer splatId) {
        this.splatId = splatId;
    }

    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }

    public Integer getBc() {
        return bc;
    }

    public void setBc(Integer bc) {
        this.bc = bc;
    }
}
