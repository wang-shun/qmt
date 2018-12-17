package com.lesports.qmt.config.repository.impl;

import com.lesports.mongo.repository.support.AbstractMongoRepository;
import com.lesports.qmt.config.model.Suggest;
import com.lesports.qmt.config.repository.SuggestRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by ruiyuansheng on 2015/10/19.
 */
@Repository("suggestRepository")
public class SuggestRepositoryImpl extends AbstractMongoRepository<Suggest, Long> implements SuggestRepository {

    @Override
    protected Class<Suggest> getEntityType() {
        return Suggest.class;
    }

    @Override
    protected Long getId(Suggest entity) {
        return entity.getId();
    }

}
