package com.lesports.qmt.web.api.core.vo;

import java.util.Map;

/**
 * Created by ruiyuansheng on 2016/1/20.
 */
public class TopicDataInfo {

    private Long id;//"id": 20010695,
    private String name;//        "nameCn": "中国90后攀登上海中心大厦挑战"都市忍者"",// 中文名称
    private String desc;// "description":"描述",
    private Map<String, String> imageUrl;//图片列表
    private String subTitle;//副标题
    private int albumType = 1;//专辑数据来源，1表示媒资专辑
    private VideoVo latestVideoVo;//最新一期视频


    public int getAlbumType() {
        return albumType;
    }

    public void setAlbumType(int albumType) {
        this.albumType = albumType;
    }

    public VideoVo getLatestVideoVo() {
        return latestVideoVo;
    }

    public void setLatestVideoVo(VideoVo latestVideoVo) {
        this.latestVideoVo = latestVideoVo;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Map<String, String> getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(Map<String, String> imageUrl) {
        this.imageUrl = imageUrl;
    }
}
