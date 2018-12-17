package com.lesports.qmt.sbc.repository.impl;

import com.lesports.mongo.repository.support.AbstractMongoRepository;
import com.lesports.qmt.sbc.model.NewsImage;
import com.lesports.qmt.sbc.repository.NewsImageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

/**
 * Created by ruiyuansheng on 2015/7/7.
 */
@Service("newsImageRepository")
public class NewsImageRepositoryImpl extends AbstractMongoRepository<NewsImage, Long> implements NewsImageRepository {

    private static final Logger LOG = LoggerFactory.getLogger(NewsRepositoryImpl.class);

    @Override
    protected Class<NewsImage> getEntityType() {
        return NewsImage.class;
    }

    @Override
    protected Long getId(NewsImage entity) {
        return entity.getId();
    }

    @Override
    public List<Long> findIdsByNewsId(Long newsId) {
        Query q = query(where("news_id").is(newsId));
        q.addCriteria(where("deleted").is(false));
        return findIdByQuery(q);
    }

    @Override
    public List<NewsImage> findByNewsId(Long newsId) {
        Query q = query(where("news_id").is(newsId));
        q.addCriteria(where("deleted").is(false));
        return findByQuery(q.with(new Sort(Sort.Direction.ASC,"show_order")));
    }
}
