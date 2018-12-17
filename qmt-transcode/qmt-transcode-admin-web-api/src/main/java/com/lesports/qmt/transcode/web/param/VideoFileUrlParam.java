package com.lesports.qmt.transcode.web.param;

/**
 * Created by pangchuanxiao on 2016/10/24.
 */
public class VideoFileUrlParam {
    //视频媒资id
    private Long leecoVid;
//    private String url;
    private Integer type;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Long getLeecoVid() {
        return leecoVid;
    }

    public void setLeecoVid(Long leecoVid) {
        this.leecoVid = leecoVid;
    }
//
//    public String getUrl() {
//        return url;
//    }
//
//    public void setUrl(String url) {
//        this.url = url;
//    }
}
