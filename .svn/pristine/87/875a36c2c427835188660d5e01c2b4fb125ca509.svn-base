package com.lesports.qmt.sbc.repository.impl;

import com.lesports.mongo.repository.support.AbstractMongoRepository;
import com.lesports.qmt.sbc.model.News;
import com.lesports.qmt.sbc.repository.NewsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Created by ruiyuansheng on 2015/7/2.
 */
@Service("newsRepository")
public class NewsRepositoryImpl extends AbstractMongoRepository<News, Long> implements NewsRepository {

    private static final Logger LOG = LoggerFactory.getLogger(NewsRepositoryImpl.class);

    @Override
    protected Class<News> getEntityType() {
        return News.class;
    }

    @Override
    protected Long getId(News entity) {
        return entity.getId();
    }

}
