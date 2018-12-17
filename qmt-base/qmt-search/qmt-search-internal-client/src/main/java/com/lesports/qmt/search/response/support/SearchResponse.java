package com.lesports.qmt.search.response.support;

import java.io.Serializable;

/**
 * Created by denghui on 2016/10/28.
 */
public class SearchResponse implements Serializable {
    private static final long serialVersionUID = 9169946265067744967L;

    private int code;
    private String msg;
    private SearchPage data;
    private String timestamp;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public SearchPage getData() {
        return data;
    }

    public void setData(SearchPage data) {
        this.data = data;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("LeResponse{");
        sb.append("code=").append(code);
        sb.append(", msg='").append(msg).append('\'');
        sb.append(", data=").append(data);
        sb.append('}');
        return sb.toString();
    }
}
