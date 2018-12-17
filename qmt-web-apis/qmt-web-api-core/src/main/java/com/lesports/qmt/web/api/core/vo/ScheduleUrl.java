package com.lesports.qmt.web.api.core.vo;

/**
 * Created by lufei1 on 2015/3/27.
 */
public class ScheduleUrl {
    private String mainUrl;
    private String backUrl0;
    private String backUrl1;
    private String backUrl2;
    private String token;
    private int code;//是否可播放；0：不可播放；1：可播放

    public String getMainUrl() {
        return mainUrl;
    }

    public void setMainUrl(String mainUrl) {
        this.mainUrl = mainUrl;
    }

    public String getBackUrl0() {
        return backUrl0;
    }

    public void setBackUrl0(String backUrl0) {
        this.backUrl0 = backUrl0;
    }

    public String getBackUrl1() {
        return backUrl1;
    }

    public void setBackUrl1(String backUrl1) {
        this.backUrl1 = backUrl1;
    }

    public String getBackUrl2() {
        return backUrl2;
    }

    public void setBackUrl2(String backUrl2) {
        this.backUrl2 = backUrl2;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "ScheduleUrl{" +
                "mainUrl='" + mainUrl + '\'' +
                ", backUrl0='" + backUrl0 + '\'' +
                ", backUrl1='" + backUrl1 + '\'' +
                ", backUrl2='" + backUrl2 + '\'' +
                ", token='" + token + '\'' +
                ", code=" + code +
                '}';
    }
}
