package com.lesports.qmt.base.web.entry;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by zhangdeqiang on 2017/2/16.
 */
public class EmailParam implements Serializable {
    private static final long serialVersionUID = 6160363915694407023L;

    private Integer type;
    private String key;
    private String re;
    private String to;
    private String value;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getRe() {
        return re;
    }

    public void setRe(String re) {
        this.re = re;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
