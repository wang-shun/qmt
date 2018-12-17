package com.lesports.qmt.web.api.core.vo;


import java.io.Serializable;
import java.util.List;

/**
 * Created by ruiyuansheng on 2016/1/12.
 */
public class TvTopicInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    private long id;
    private String type;
    private String name;
    private int cid;
    private String desc;
    private String cover;
    private List<PackageInfo> packageList;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public List<PackageInfo> getPackageList() {
        return packageList;
    }

    public void setPackageList(List<PackageInfo> packageList) {
        this.packageList = packageList;
    }

    public static class PackageInfo implements Serializable {
        private static final long serialVersionUID = 1L;
        //名称
        private String name;
        //专辑列表
        private List<TopicDataInfo> albumList;
        //视频列表
        private List<TopicDataInfo> videoList;
        //节目列表
        private List<TopicDataInfo> episodeList;
        //影片时长
        private int order;
        //上线时间
        private int type;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<TopicDataInfo> getAlbumList() {
            return albumList;
        }

        public void setAlbumList(List<TopicDataInfo> albumList) {
            this.albumList = albumList;
        }

        public List<TopicDataInfo> getVideoList() {
            return videoList;
        }

        public void setVideoList(List<TopicDataInfo> videoList) {
            this.videoList = videoList;
        }

        public List<TopicDataInfo> getEpisodeList() {
            return episodeList;
        }

        public void setEpisodeList(List<TopicDataInfo> episodeList) {
            this.episodeList = episodeList;
        }

        public int getOrder() {
            return order;
        }

        public void setOrder(int order) {
            this.order = order;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }





    }

}
