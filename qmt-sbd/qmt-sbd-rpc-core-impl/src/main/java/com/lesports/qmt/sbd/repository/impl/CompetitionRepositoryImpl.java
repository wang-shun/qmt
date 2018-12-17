package com.lesports.qmt.sbd.repository.impl;

import com.google.common.collect.Maps;
import com.lesports.mongo.repository.support.AbstractMongoRepository;
import com.lesports.qmt.sbd.model.Competition;
import com.lesports.qmt.sbd.repository.CompetitionRepository;
import com.lesports.utils.PageUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

/**
 * Created by yangyu on 2015/5/29.
 */
@Repository("competitionRepository")
public class CompetitionRepositoryImpl extends AbstractMongoRepository<Competition, Long> implements CompetitionRepository {

    private static final Logger LOG = LoggerFactory.getLogger(CompetitionRepositoryImpl.class);


    @Override
    protected Class<Competition> getEntityType() {
        return Competition.class;
    }

    @Override
    protected Long getId(Competition entity) {
        return entity.getId();
    }

    @Override
    public Page<Competition> fuzzyFindByName(String name, Pageable pageable) {
        Query query = query(where("deleted").is(false));
        if(StringUtils.isNotEmpty(name)){
            query.addCriteria(where("name").regex(name));
        }
        long total = countByQuery(query);
        List<Competition> entities;
        if (total == 0) {
            //count为0,就别继续查了
            entities = Collections.emptyList();
            return new PageImpl<>(entities, null, 0);
        }

        entities = findByQuery(query.with(pageable));
        return new PageImpl<>(entities, pageable, total);
    }

    @Override
    public List<Competition> findCompetitionByFTypeAndSType(long gameFType, long gameSType) {
        Query query = query(where("deleted").is(false));
        if(gameFType != 0){
            query.addCriteria(where("game_f_type").is(gameFType));
        }
        if(gameSType != 0){
            query.addCriteria(where("game_s_type").is(gameSType));
        }
        List<Competition> entities = findByQuery(query);
        return entities;
    }

	@Override
	public List<Competition> findCompetitionByCompetitionCodeingId(String codeingId) {
		Query query = query(where("deleted").is(false));
		query.addCriteria(where("code").is(codeingId));
		return findByQuery(query);
	}

	@Override
    public Page<Competition> findAll(Pageable pageable) {
        Pageable validPageable = PageUtils.getValidPageable(pageable);
        Map<String, Object> params = Maps.newHashMap();
        params.put("deleted", false);
        return findByCondition(params, validPageable);
    }

}
