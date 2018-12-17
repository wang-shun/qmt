package com.lesports.qmt.web.api.core.vo;

import java.util.Map;

/**
 * Created by lufei1 on 2016/5/26.
 */
public class PcPlayVo {
    //播放状态 1:为允许播放 0:禁播
    private Map<String, Object> playstatus;
    //播放相关地址
    private Map<String, Object> playurl;
    //返回状态码 1001：成功，1002：必填参数为空，1003：参数不合法，2001：系统内部异常,1005:版本错误
    private int statuscode;
    //试看标识 0：免费 1:收费
    private String trylook;
    //drm 标示
    private int drmFlag;
    //付费 标示
    private int isPay;
    //boss鉴权数据
    private Map<String, Object> yuanxian;

    public Map<String, Object> getPlaystatus() {
        return playstatus;
    }

    public void setPlaystatus(Map<String, Object> playstatus) {
        this.playstatus = playstatus;
    }

    public Map<String, Object> getPlayurl() {
        return playurl;
    }

    public void setPlayurl(Map<String, Object> playurl) {
        this.playurl = playurl;
    }

    public int getStatuscode() {
        return statuscode;
    }

    public void setStatuscode(int statuscode) {
        this.statuscode = statuscode;
    }

    public String getTrylook() {
        return trylook;
    }

    public void setTrylook(String trylook) {
        this.trylook = trylook;
    }

    public Map<String, Object> getYuanxian() {
        return yuanxian;
    }

    public void setYuanxian(Map<String, Object> yuanxian) {
        this.yuanxian = yuanxian;
    }

    public int getDrmFlag() {
        return drmFlag;
    }

    public void setDrmFlag(int drmFlag) {
        this.drmFlag = drmFlag;
    }

    public int getIsPay() {
        return isPay;
    }

    public void setIsPay(int isPay) {
        this.isPay = isPay;
    }


    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("PcPlayVo{");
        sb.append("playstatus=").append(playstatus);
        sb.append(", playurl=").append(playurl);
        sb.append(", statuscode=").append(statuscode);
        sb.append(", trylook='").append(trylook).append('\'');
        sb.append(", drmFlag=").append(drmFlag);
        sb.append(", isPay=").append(isPay);
        sb.append(", yuanxian=").append(yuanxian);
        sb.append('}');
        return sb.toString();
    }
}
