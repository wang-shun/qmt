package com.lesports.qmt.web.model;

import java.io.Serializable;

/**
 * Created by wangjichuan on 15-6-17.
 */
public class HotRank implements Serializable {
    private Long id;
    private Long pid;
    private Integer cid;
    private String name;
    private String url;
    private String playCount;
    private String picurl;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPlayCount() {
        return playCount;
    }

    public void setPlayCount(String playCount) {
        this.playCount = playCount;
    }

    public String getPicurl() {
        return picurl;
    }

    public void setPicurl(String picurl) {
        this.picurl = picurl;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("HotRank{");
        sb.append("id=").append(id);
        sb.append(", pid=").append(pid);
        sb.append(", cid=").append(cid);
        sb.append(", name='").append(name).append('\'');
        sb.append(", url='").append(url).append('\'');
        sb.append(", playCount='").append(playCount).append('\'');
        sb.append(", picurl='").append(picurl).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
