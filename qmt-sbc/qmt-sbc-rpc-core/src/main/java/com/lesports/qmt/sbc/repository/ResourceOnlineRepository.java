package com.lesports.qmt.sbc.repository;

import com.lesports.mongo.repository.MongoCrudRepository;
import com.lesports.qmt.sbc.model.QmtResourceOnline;
import com.lesports.qmt.sbc.model.ResourceContentOnline;

/**
 * User: ellios
 * Time: 16-10-23 : 下午5:21
 */
public interface ResourceOnlineRepository extends MongoCrudRepository<QmtResourceOnline, Long> {
    public QmtResourceOnline findLatestOneByRID(Long rId);
}
