package com.lesports.qmt.sbd.model;

/**
 * Created by lufei1 on 2016/12/6.
 */
public enum PartnerType {
    SPORTRADAR("469"), SODA("45"), OLYMPIC("498"), STATS("499"), ZHANGYU("345"), QQ("100");

    private String name;
    private String code;


    public static PartnerType findByCode(String code) {
        if ("469".equals(code)) {
            return SPORTRADAR;
        } else if ("45".equals(code)) {
            return SODA;
        } else if ("498".equals(code)) {
            return OLYMPIC;
        } else if ("499".equals(code)) {
            return STATS;
        } else if ("345".equals(code)) {
            return ZHANGYU;
        } else if ("100".equals(code)) {
            return QQ;
        }
        return null;
    }

    PartnerType(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
