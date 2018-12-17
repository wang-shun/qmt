package com.lesports.qmt.config.repository.impl;

import com.lesports.mongo.repository.support.AbstractMongoRepository;
import com.lesports.qmt.config.model.Caller;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import com.lesports.qmt.config.repository.CallerRepository;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

/**
 * Created by gengchengliang on 2015/9/19.
 */
@Repository("callerRepository")
public class CallerRepositoryImpl extends AbstractMongoRepository<Caller, Long> implements CallerRepository {

	@Override
	protected Class<Caller> getEntityType() {
		return Caller.class;
	}

	@Override
	protected Long getId(Caller entity) {
		return entity.getId();
	}

	@Override
	public Caller findByCallerId(long callerId) {
		if (callerId <= 0) {
			return null;
		}
		Query q = query(where("deleted").is(false));
		q.addCriteria(where("caller_id").is(callerId));
		return findOneByQuery(q);
	}
}
