package com.lesports.qmt.sbd.repository.impl;

import com.lesports.mongo.repository.support.AbstractMongoRepository;
import com.lesports.qmt.sbd.model.MatchAction;
import com.lesports.qmt.sbd.repository.MatchActionRepository;
import com.lesports.utils.PageUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

/**
 * Created by yangyu on 2015/6/1.
 */
@Repository("matchActionRepository")
public class MatchActionRepositoryImpl extends AbstractMongoRepository<MatchAction, Long> implements MatchActionRepository {

    private static final Logger LOG = LoggerFactory.getLogger(MatchActionRepositoryImpl.class);

    @Override
    protected Class<MatchAction> getEntityType() {
        return MatchAction.class;
    }

    @Override
    protected Long getId(MatchAction entity) {
        return entity.getId();
    }


    @Override
    public List<MatchAction> findByMid(Long mid, Pageable pageable) {

        Pageable validPageable = PageUtils.getValidPageable(pageable);
        Query q = query(where("mid").is(mid));
        q.with(validPageable);
        return findByQuery(q);
    }

    @Override
    public List<MatchAction> findMatchActionByMid(Long mid) {
        Query query = query(where("mid").is(mid));
        List<MatchAction> entities = findByQuery(query);
        return entities;
    }
}
