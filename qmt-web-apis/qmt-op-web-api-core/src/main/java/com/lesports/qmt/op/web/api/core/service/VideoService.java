package com.lesports.qmt.op.web.api.core.service;

import com.lesports.api.common.CallerParam;
import com.lesports.api.common.PageParam;
import com.lesports.qmt.op.web.api.core.vo.VideoVo;
import com.lesports.qmt.op.web.api.core.vo.mbaidu.ShortVideos;

import java.util.List;

/**
 * Created by lufei1 on 2015/11/20.
 */
public interface VideoService {

    /**
     * 按类型获取短视频 1：赛事 2：球队 3：球员
     *
     * @param type
     * @return
     */
    public ShortVideos getShortVideosByType(long type, CallerParam caller);

    /**
     * 根据相关id获取视频
     * @param relatedId
     * @param page
     * @return
     */
    public List<VideoVo> getVideosByRelatedId(long relatedId, CallerParam caller, PageParam page);

    /**
     * 根据相关id获取中超视频
     * @param relatedId
     * @param page
     * @return
     */
    public List<VideoVo> getCslVideosByRelatedId(long relatedId, CallerParam caller, PageParam page);

    /**
     * huoqusuoyoushipin
     * @param page
     * @return
     */
    public List<VideoVo> getVideos(CallerParam caller, PageParam page);
    }
