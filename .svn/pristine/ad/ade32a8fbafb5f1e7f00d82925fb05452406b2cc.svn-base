package com.lesports.qmt.op.web.api.core.service;

import com.lesports.api.common.CallerParam;
import com.lesports.api.common.PageParam;
import com.lesports.qmt.op.web.api.core.vo.NewsVo;
import com.lesports.qmt.op.web.api.core.vo.OlyNewsVo;

import java.util.List;

/**
 * Created by lufei on 2015/11/5.
 */
public interface NewsService {

    /**
     * 获取推荐视频列表
     *
     * @param page
     * @return
     */
    List<NewsVo> getRecommendNews(PageParam page, CallerParam caller);


    /**
     * 获取最新视频列表
     *
     * @param page
     * @return
     */
    List<NewsVo> getTheLatestVideos(CallerParam caller, PageParam page);

    /**
     * 根据标签获取新闻
     *
     * @param page
     * @return
     */
    List<OlyNewsVo> getNewsByTag(String tag, String type, CallerParam caller, PageParam page);

}
