package com.lesports.qmt.sbc.service;

import com.lesports.api.common.CallerParam;
import com.lesports.qmt.sbc.api.common.NewsType;
import com.lesports.qmt.sbc.api.dto.TNews;
import com.lesports.qmt.sbc.api.service.GetRelatedNewsParam;
import com.lesports.qmt.sbc.model.News;
import com.lesports.qmt.sbc.model.Video;
import com.lesports.qmt.service.support.QmtService;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

/**
 * Created by ruiyuansheng on 2015/7/2.
 */
public interface NewsService extends QmtService<News, Long> {

    /**
     * 根据视频创建视频新闻
     * @param video
     * @return
     */
    boolean saveWithVideo(Video video);

    TNews getTNewsById(long id, CallerParam caller);

    List<TNews> getTNewsByIds(List<Long> ids, CallerParam caller);

    TNews getTNewsByVid(long vid, CallerParam caller);

    List<Long> getRelatedNewsIds(GetRelatedNewsParam p, Pageable pageable, CallerParam caller);

    long countRelatedNews(GetRelatedNewsParam p, CallerParam caller);

    List<Long> getNewsIdsRelatedWithSomeNews(long newsId, List<NewsType> types, Pageable pageable, CallerParam caller);

    Map<String,Long> getNewsIdsNearbySomeNews(long newsId, NewsType type, CallerParam caller);
}
