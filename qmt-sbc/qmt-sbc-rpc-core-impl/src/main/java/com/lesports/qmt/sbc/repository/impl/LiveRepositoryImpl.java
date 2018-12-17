package com.lesports.qmt.sbc.repository.impl;

import com.lesports.mongo.repository.support.AbstractMongoRepository;
import com.lesports.qmt.sbc.model.Live;
import com.lesports.qmt.sbc.model.News;
import com.lesports.qmt.sbc.repository.LiveRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Created by wangjichuan on 2016/10/26.
 */
@Service("liveRepository")
public class LiveRepositoryImpl extends AbstractMongoRepository<Live, Long> implements LiveRepository {

    private static final Logger LOG = LoggerFactory.getLogger(LiveRepositoryImpl.class);

    @Override
    protected Class<Live> getEntityType() {
        return Live.class;
    }

    @Override
    protected Long getId(Live entity) {
        return entity.getId();
    }



}
