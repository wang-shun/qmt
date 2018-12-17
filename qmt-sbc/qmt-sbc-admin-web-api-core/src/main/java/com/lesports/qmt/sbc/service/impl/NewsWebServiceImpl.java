package com.lesports.qmt.sbc.service.impl;

import com.google.common.collect.Lists;
import com.lesports.api.common.CallerParam;
import com.lesports.api.common.PublishStatus;
import com.lesports.id.api.IdType;
import com.lesports.id.client.LeIdApis;
import com.lesports.qmt.mvc.QmtPage;
import com.lesports.qmt.mvc.QmtPageParam;
import com.lesports.qmt.sbc.api.common.NewsType;
import com.lesports.qmt.sbc.client.QmtSbcNewsInternalApis;
import com.lesports.qmt.sbc.converter.NewsVoConverter;
import com.lesports.qmt.sbc.model.News;
import com.lesports.qmt.sbc.model.NewsImage;
import com.lesports.qmt.sbc.model.RelatedItem;
import com.lesports.qmt.sbc.service.NewsWebService;
import com.lesports.qmt.sbc.service.support.AbstractSbcWebService;
import com.lesports.qmt.sbc.utils.QmtSbcUtils;
import com.lesports.qmt.sbc.vo.NewsVo;
import com.lesports.query.InternalCriteria;
import com.lesports.query.InternalQuery;
import com.lesports.utils.CallerUtils;
import com.lesports.utils.LeDateUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by denghui on 2016/10/21.
 */
@Service("newsWebService")
public class NewsWebServiceImpl extends AbstractSbcWebService implements NewsWebService {

    private static final Logger LOG = LoggerFactory.getLogger(NewsWebServiceImpl.class);

    @Resource
    private NewsVoConverter newsVoConverter;

    private Long doInsert(NewsVo newsVo) {
        newsVo.setCreator(getOperator());
        newsVo.setId(LeIdApis.nextId(IdType.NEWS));
        // 保存图集图片
        if (newsVo.getType() == NewsType.IMAGE_ALBUM) {
            for (NewsImage newsImage : newsVo.getImages()) {
                newsImage.setNewsId(newsVo.getId());
                QmtSbcNewsInternalApis.saveNewsImage(newsImage);
            }
        }
        // 保存新闻
        long id = QmtSbcNewsInternalApis.saveNews(newsVo.toNews());
        newsVo.setId(id);
        return id;
    }

    private Long doUpdate(NewsVo newsVo) {
        News existsNews = QmtSbcNewsInternalApis.getNewsById(newsVo.getId());
        // 更新图集图片
        if (existsNews.getType() == NewsType.IMAGE_ALBUM) {
            //先删掉之前的图片
            List<NewsImage> existsNewsImages = QmtSbcNewsInternalApis.getNewsImagesByNewsId(newsVo.getId());
            for (NewsImage existsNewsImage : existsNewsImages) {
                existsNewsImage.setDeleted(true);
                QmtSbcNewsInternalApis.saveNewsImage(existsNewsImage);
            }

            for (NewsImage newsImage : newsVo.getImages()) {
                newsImage.setNewsId(newsVo.getId());
                newsImage.setDeleted(false);
                QmtSbcNewsInternalApis.saveNewsImage(newsImage);
            }
        }
        // 保存新闻
        existsNews = newsVoConverter.copyEditableProperties(existsNews, newsVo);
        existsNews.setUpdater(getOperator());
        return QmtSbcNewsInternalApis.saveNews(existsNews, true);
    }

    @Override
    public boolean updatePublishStatus(long id, PublishStatus status) {
        News news = QmtSbcNewsInternalApis.getNewsById(id);
        news.setOnline(status);
        news.setUpdater(getOperator());
        return QmtSbcNewsInternalApis.saveNews(news) > 0;
    }

    @Override
    @Deprecated
    public QmtPage<NewsVo> list(Map<String, Object> params, QmtPageParam pageParam) {
        InternalQuery query = new InternalQuery();
        query.addCriteria(InternalCriteria.where("deleted").is(false));
        addParamsCriteriaToQuery(query, params, pageParam);
        addNewsCriteriaToQuery(query, params);

        long total = QmtSbcNewsInternalApis.countNewsByQuery(query);
        if (total <= 0) {
            return new QmtPage(Collections.EMPTY_LIST, pageParam, 0);
        }
        List<News> newsList = QmtSbcNewsInternalApis.getNewsByQuery(query);
        List<NewsVo> content = Lists.newArrayList();
        for (News news : newsList) {
            NewsVo newsVo = new NewsVo();
            newsVo.setId(news.getId());
            newsVo.setName(news.getName());
            newsVo.setSource(news.getSource());
            newsVo.setType(news.getType());
            newsVo.setAuthor(news.getAuthor());
            newsVo.setCreateAt(news.getCreateAt());
            newsVo.setUpdateAt(news.getUpdateAt());
            newsVo.setOnline(news.getOnline());
            content.add(newsVo);
        }

        return new QmtPage<>(content, pageParam, total);
    }

    private void addNewsCriteriaToQuery(InternalQuery query, Map<String, Object> params) {
        String type = MapUtils.getString(params, "type", null);
        String status = MapUtils.getString(params, "status", null);
        String startTime = MapUtils.getString(params, "startTime", null);
        String endTime = MapUtils.getString(params, "endTime", null);
        if (StringUtils.isNotBlank(type)) {
            query.addCriteria(InternalCriteria.where("type").is(type));
        }
        if (StringUtils.isNotBlank(status)) {
            query.addCriteria(InternalCriteria.where("online").is(status));
        }
        if (StringUtils.isNotBlank(startTime)) {
            startTime = LeDateUtils.tansYMDHMS2YYYYMMDDHHMMSS(startTime);
            query.addCriteria(InternalCriteria.where("publish_at").gte(startTime));
        }
        if (StringUtils.isNotBlank(endTime)) {
            endTime = LeDateUtils.tansYMDHMS2YYYYMMDDHHMMSS(endTime);
            query.addCriteria(InternalCriteria.where("publish_at").lte(endTime));
        }
    }

    @Override
    public Long saveWithId(NewsVo entity) {
        if (entity == null) {
            return -1l;
        }
        if (entity.getId() == null) {
            return doInsert(entity);
        } else {
            return doUpdate(entity);
        }
    }

    @Override
    public NewsVo findOne(Long id) {
        return findOne(id, CallerUtils.getDefaultCaller());
    }

    @Override
    public NewsVo findOne(Long id, CallerParam caller) {
        News news = QmtSbcNewsInternalApis.getNewsById(id);
        if (news == null) {
            return null;
        }
        List<NewsImage> images = QmtSbcNewsInternalApis.getNewsImagesByNewsId(id);
        NewsVo newsVo = new NewsVo(news);
        newsVo.setImages(images);
        newsVo.setRelatedTags(QmtSbcUtils.getRelatedTagVosByRelatedIds(news.getRelatedIds(), caller));
        if (CollectionUtils.isNotEmpty(newsVo.getRelatedItems())) {
            for (RelatedItem relatedItem : newsVo.getRelatedItems()) {
                String name = QmtSbcUtils.getNameByEntityId(relatedItem.getId(), CallerUtils.getDefaultCaller());
                relatedItem.setName(name);
            }
        }
        return newsVo;
    }

    @Override
    public boolean delete(Long id) {
        return QmtSbcNewsInternalApis.deleteNews(id);
    }
}
