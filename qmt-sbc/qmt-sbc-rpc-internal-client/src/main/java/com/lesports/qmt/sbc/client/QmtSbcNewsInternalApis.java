package com.lesports.qmt.sbc.client;

import com.lesports.qmt.sbc.model.News;
import com.lesports.qmt.sbc.model.NewsImage;
import com.lesports.query.InternalCriteria;
import com.lesports.query.InternalQuery;
import org.springframework.data.domain.Sort;

import java.util.List;

/**
 * User: ellios
 * Time: 16-10-28 : 下午9:11
 */
public class QmtSbcNewsInternalApis extends QmtSbcInternalApis{

    public static long saveNews(News news) {
        return saveNews(news, false);
    }

    public static long saveNews(News news, boolean allowEmpty) {
        return saveEntity(news, News.class, allowEmpty);
    }

    public static News getNewsById(Long id) {
        return getEntityById(id, News.class);
    }

    public static List<News> getNewsByIds(List<Long> ids) {
        return getEntitiesByIds(ids, News.class);
    }

    public static boolean deleteNews(Long id) {
        return deleteEntity(id, News.class);
    }

    @Deprecated
    public static long countNewsByQuery(InternalQuery query) {
        return countByQuery(query, News.class);
    }

    @Deprecated
    public static List<Long> getNewsIdsByQuery(InternalQuery query) {
        return getEntityIdsByQuery(query, News.class);
    }
    
    public static long saveNewsImage(NewsImage newsImage) {
        return saveNewsImage(newsImage, false);
    }

    public static long saveNewsImage(NewsImage newsImage, boolean allowEmpty) {
        return saveEntity(newsImage, NewsImage.class, allowEmpty);
    }

    public static boolean deleteNewsImage(Long id) {
        return deleteEntity(id, NewsImage.class);
    }

    public static NewsImage getNewsImageById(Long id) {
        return getEntityById(id, NewsImage.class);
    }

    public static List<NewsImage> getNewsImageByIds(List<Long> ids) {
        return getEntitiesByIds(ids, NewsImage.class);
    }

    public static List<Long> getNewsImageIdsByQuery(InternalQuery query) {
        return getEntityIdsByQuery(query, NewsImage.class);
    }

    public static List<NewsImage> getNewsImagesByQuery(InternalQuery query) {
        return getEntitiesByQuery(query, NewsImage.class);
    }

    public static List<NewsImage> getNewsImagesByNewsId(Long newsId) {
        InternalQuery query = new InternalQuery();
        query.addCriteria(InternalCriteria.where("deleted").is(false));
        query.addCriteria(InternalCriteria.where("news_id").is(newsId));
        query.with(new Sort(Sort.Direction.ASC, "show_order"));
        return QmtSbcNewsInternalApis.getNewsImagesByQuery(query);
    }

    @Deprecated
    public static List<News> getNewsByQuery(InternalQuery query) {
        return getEntitiesByQuery(query, News.class);
    }
}
