package com.lesports.qmt.web.model;

/**
 * Created by ruiyuansheng on 2016/7/1.
 */
public class OlympicConfig {

    private String key;

    private String value;

    public OlympicConfig(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
