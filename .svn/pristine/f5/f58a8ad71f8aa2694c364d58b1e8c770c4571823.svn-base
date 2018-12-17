package com.lesports.qmt.sbd.repository.impl;

import com.lesports.mongo.repository.support.AbstractMongoRepository;
import com.lesports.qmt.sbd.model.TopList;
import com.lesports.qmt.sbd.repository.TopListRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

/**
 * Created by lufei on 2016/10/28.
 */
@Repository("topListRepository")
public class TopListRepositoryImpl extends AbstractMongoRepository<TopList, Long> implements TopListRepository {

    private static final Logger LOG = LoggerFactory.getLogger(TopListRepositoryImpl.class);


    @Override
    protected Class<TopList> getEntityType() {
        return TopList.class;
    }

    @Override
    protected Long getId(TopList entity) {
        return entity.getId();
    }


}
