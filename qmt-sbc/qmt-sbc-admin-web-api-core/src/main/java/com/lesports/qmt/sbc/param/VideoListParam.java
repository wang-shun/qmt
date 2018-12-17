package com.lesports.qmt.sbc.param;

import com.lesports.qmt.sbc.api.common.VideoContentType;

/**
 * Created by zhangxudong@le.com on 2016/10/21.
 */
public class VideoListParam {

    private Long id;
    //视频名称
    private String title;
    //视频预览图片
    private String previewImageUrl;
    //视频内容类型
    private VideoContentType type;
    //生态vid,也就是vrs的视频id
    private Long leecoVid;

    public Long getLeecoVid() {
        return leecoVid;
    }

    public void setLeecoVid(Long leecoVid) {
        this.leecoVid = leecoVid;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPreviewImageUrl() {
        return previewImageUrl;
    }

    public void setPreviewImageUrl(String previewImageUrl) {
        this.previewImageUrl = previewImageUrl;
    }

    public VideoContentType getType() {
        return type;
    }

    public void setType(VideoContentType type) {
        this.type = type;
    }
}
