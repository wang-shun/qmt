package com.lesports.qmt.web.api.core.vo;

import com.lesports.qmt.web.api.core.vo.LatestEpisodeVo;
import com.lesports.qmt.web.api.core.vo.VideoVo;
import com.lesports.utils.math.LeNumberUtils;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Map;

/**
 * Created by ruiyuansheng on 2015/7/30.
 */
public class AlbumVo implements Serializable {

    //专辑id
    private long id;
    //专辑名称
    private String name;
    //专辑简称
    private String abbreviation;
    //专辑封面logo
    private String logo;
    //TV专用专辑封面
    private String imageTvUrl;
    //最新一期视频
    private VideoVo latestVideoVo;
    //最新一期节目
    private LatestEpisodeVo latestEpisodeVo;
    //专辑来源，0表示sms专辑
    private int albumType = 0;
    private Map<String,String> images;

    private int order;
    //最新一期的创建时间
    private String latestTime;

    public String getLatestTime() {
        return latestTime;
    }

    public void setLatestTime(String latestTime) {
        this.latestTime = latestTime;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public Map<String, String> getImages() {
        return images;
    }

    public void setImages(Map<String, String> images) {
        this.images = images;
    }

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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public LatestEpisodeVo getLatestEpisodeVo() {
        return latestEpisodeVo;
    }

    public void setLatestEpisodeVo(LatestEpisodeVo latestEpisodeVo) {
        this.latestEpisodeVo = latestEpisodeVo;
    }

    public String getImageTvUrl() {
        return imageTvUrl;
    }

    public void setImageTvUrl(String imageTvUrl) {
        this.imageTvUrl = imageTvUrl;
    }

    @Override
    public String toString() {
        return "AlbumVo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", abbreviation='" + abbreviation + '\'' +
                ", logo='" + logo + '\'' +
                ", imageTvUrl='" + imageTvUrl + '\'' +
                ", latestVideoVo=" + latestVideoVo +
                ", latestEpisodeVo=" + latestEpisodeVo +
                ", albumType=" + albumType +
                ", images=" + images +
                ", order=" + order +
                ", latestTime='" + latestTime + '\'' +
                '}';
    }
}
