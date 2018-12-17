package com.lesports.qmt.sbc.repository.impl;

import com.lesports.mongo.repository.support.AbstractMongoRepository;
import com.lesports.qmt.sbc.model.QmtResourceOnline;
import com.lesports.qmt.sbc.model.ResourceContentOnline;
import com.lesports.qmt.sbc.repository.ResourceContentOnlineRepository;
import com.lesports.qmt.sbc.repository.ResourceOnlineRepository;
import com.lesports.query.InternalCriteria;
import com.lesports.query.InternalQuery;
import com.lesports.utils.QueryUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * User: ellios
 * Time: 16-10-23 : 下午6:17
 */
@Repository("resourceOnlineRepository")
public class ResourceOnlineRepositoryImpl extends AbstractMongoRepository<QmtResourceOnline, Long> implements ResourceOnlineRepository {

    private static final Logger LOG = LoggerFactory.getLogger(ResourceOnlineRepositoryImpl.class);

    @Override
    public QmtResourceOnline findLatestOneByRID(Long rId) {
        InternalQuery query = new InternalQuery();
        query.getCriterias().add(new InternalCriteria("fromId").is(rId));
        query.setSort(new Sort(Sort.Direction.DESC, "version"));
        query.setLimit(1);
        List<QmtResourceOnline> qmtResourceOnlineList = findByQuery(QueryUtils.buildQuery(query));
        if(qmtResourceOnlineList != null && !qmtResourceOnlineList.isEmpty()){
            return qmtResourceOnlineList.get(0);
        }
        return null;
    }

    @Override
    protected Class<QmtResourceOnline> getEntityType() {
        return QmtResourceOnline.class;
    }

    @Override
    protected Long getId(QmtResourceOnline entity) {
        return entity.getId();
    }

}
