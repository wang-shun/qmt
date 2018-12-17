package com.lesports.qmt.web.api.core.vo;

import java.util.List;

/**
 * Created by ruiyuansheng on 2016/10/20.
 */
public class ReCommendTheme {

    private String name;

    private List<VideoVo> videoVos;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<VideoVo> getVideoVos() {
        return videoVos;
    }

    public void setVideoVos(List<VideoVo> videoVos) {
        this.videoVos = videoVos;
    }

    @Override
    public String toString() {
        return "ReCommendTheme{" +
                "name='" + name + '\'' +
                ", videoVos=" + videoVos +
                '}';
    }
}
