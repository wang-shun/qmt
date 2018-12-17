package com.lesports.qmt.sbc.repository.impl;

import com.lesports.mongo.repository.support.AbstractMongoRepository;
import com.lesports.qmt.sbc.model.LiveAreaShield;
import com.lesports.qmt.sbc.repository.LiveAreaShieldRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Created by wangjichuan on 2016/10/26.
 */
@Service("liveAreaShieldRepository")
public class LiveAreaShieldRepositoryImpl extends AbstractMongoRepository<LiveAreaShield, Long> implements LiveAreaShieldRepository {

    private static final Logger LOG = LoggerFactory.getLogger(LiveAreaShieldRepositoryImpl.class);

    @Override
    protected Class<LiveAreaShield> getEntityType() {
        return LiveAreaShield.class;
    }

    @Override
    protected Long getId(LiveAreaShield liveAreaShield) {
        return liveAreaShield.getId();
    }
}
