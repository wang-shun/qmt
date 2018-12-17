package com.lesports.qmt.cms.repository.impl;

import com.lesports.mongo.repository.support.AbstractMongoRepository;
import com.lesports.qmt.cms.model.ColumnPage;
import com.lesports.qmt.cms.repository.ColumnPageRepository;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

/**
 * User: ellios
 * Time: 16-11-29 : 下午5:41
 */
@Repository("columnPageRepository")
public class ColumnPageRepositoryImpl extends AbstractMongoRepository<ColumnPage, Long> implements ColumnPageRepository {

    @Override
    protected Class<ColumnPage> getEntityType() {
        return ColumnPage.class;
    }

    @Override
    protected Long getId(ColumnPage entity) {
        return entity.getId();
    }

    @Override
    public List<ColumnPage> findPagesByColumnId(long columnId) {
        if(columnId <= 0){
            LOG.error("fail to findPagesByColumnId, illegal columnId : {}", columnId);
            return Collections.EMPTY_LIST;
        }
        Query q = query(where("deleted").is(false));
        q.addCriteria(where("columnId").is(columnId));
        return findByQuery(q);
    }
}
