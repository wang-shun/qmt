package com.lesports.qmt.web.api.core.service;

import com.lesports.api.common.CallerParam;
import com.lesports.api.common.PageParam;
import com.lesports.qmt.sbc.api.common.NewsType;
import com.lesports.qmt.sbc.api.service.GetRecommendParam;
import com.lesports.qmt.sbc.api.service.GetRelatedNewsParam;
import com.lesports.qmt.web.api.core.vo.DetailNews;
import com.lesports.qmt.web.api.core.vo.NewsVo;
import com.lesports.qmt.web.api.core.vo.RecommendVo;

import java.util.List;

/**
 * Created by gengchengliang on 2015/6/17.
 */
public interface NewsService {

    /**
     * @param p
     * @param pageParam
     * @param callerParam
     * @return
     */
    public List<NewsVo> getNewsListByRelatedId(GetRelatedNewsParam p, PageParam pageParam, CallerParam callerParam);


	List<NewsVo> getRecommendNewsByRelatedIdAndTypes(String type, GetRelatedNewsParam p, PageParam pageParam, CallerParam callerParam);

	public List<NewsVo> getNewsListByRelatedIdWithFallback(String type, GetRelatedNewsParam p, Integer isNeedRecommends, PageParam pageParam, CallerParam callerParam);


//    /** todo 元生确实是否已经无用
//     * 获取24小时的新闻列表
//     *
//     * @param pageParam
//     * @param callerParam
//     * @return
//     */
//    List<NewsVo> get24HoursNews(PageParam pageParam, CallerParam callerParam);

    DetailNews getNewsById(long id, CallerParam caller, int idType);

//    List<RecommendVo> getRecommendsByType(String type, PageParam page, CallerParam caller);

//    List<RecommendVo> getRecommendsByTypeWithFallback(String type, PageParam page, CallerParam caller);

	List<RecommendVo> getRecommendsAndNews(GetRecommendParam p, PageParam pageParam, CallerParam callerParam);

	List<RecommendVo> getRecommendsAndNews4App(GetRecommendParam p, PageParam pageParam, CallerParam callerParam);

    List<NewsVo> getRelatedNews4App(long newsId, List<NewsType> types, PageParam page, CallerParam caller);

    List<RecommendVo> getCmsFocusNews4APP(long id, String type, PageParam pageParam, CallerParam caller);

    List<RecommendVo> getFocusNews4AppWithFallback(String type, PageParam pageParam, CallerParam caller);

	//todo 元生确认下是否还有用
//    List<NewsVo> getNews4TvCard(GetRecommendParam p, PageParam pageParam, CallerParam callerParam);

    List<NewsVo> getRelatedNews4TV(long newsId, List<NewsType> types, PageParam page, CallerParam caller);

    DetailNews getNewsById4TV(long id, CallerParam caller, int idType);

	List<NewsVo> getMultiNews(List<Long> ids, CallerParam callerParam);
}
