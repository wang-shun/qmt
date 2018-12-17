package com.lesports.qmt.userinfo.repository.impl;

import com.lesports.mongo.repository.support.AbstractMongoRepository;
import com.lesports.qmt.userinfo.model.Workbench;
import com.lesports.qmt.userinfo.repository.WorkbenchRepository;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

/**
 * Created by denghui on 2017/2/15.
 */
@Repository("workbenchRepository")
public class WorkbenchRepositoryImpl extends AbstractMongoRepository<Workbench, Long> implements WorkbenchRepository {
    @Override
    protected Class<Workbench> getEntityType() {
        return Workbench.class;
    }

    @Override
    protected Long getId(Workbench entity) {
        return entity.getId();
    }

    @Override
    public Workbench getByCreator(String creator) {
        Query q = query(where("creator").is(creator));
        return findOneByQuery(q);
    }
}
