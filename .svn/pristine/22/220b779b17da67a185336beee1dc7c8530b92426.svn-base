package com.lesports.qmt.sbc.service.impl;

import com.lesports.mongo.repository.MongoCrudRepository;
import com.lesports.qmt.QmtOperationType;
import com.lesports.qmt.sbc.model.LiveAreaShield;
import com.lesports.qmt.sbc.repository.LiveAreaShieldRepository;
import com.lesports.qmt.sbc.service.LiveAreaShieldService;
import com.lesports.qmt.sbc.service.support.AbstractSbcService;
import com.lesports.utils.math.LeNumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by wangjichuan on 2016/10/26.
 */
@Service("liveAreaShieldService")
public class LiveAreaShieldServiceImpl extends AbstractSbcService<LiveAreaShield, Long> implements LiveAreaShieldService {

    protected static final Logger LOG = LoggerFactory.getLogger(LiveAreaShieldServiceImpl.class);

    @Resource
    protected LiveAreaShieldRepository liveAreaShieldRepository;

    @Override
    protected MongoCrudRepository<LiveAreaShield, Long> getInnerRepository() {
        return liveAreaShieldRepository;
    }

    @Override
    protected QmtOperationType getOpreationType(LiveAreaShield entity) {
        return LeNumberUtils.toLong(entity.getId()) > 0 ? QmtOperationType.UPDATE : QmtOperationType.CREATE;
    }

    @Override
    protected boolean doCreate(LiveAreaShield entity) {
        return liveAreaShieldRepository.save(entity);
    }

    @Override
    protected boolean doAfterCreate(LiveAreaShield entity) {
        return true;
    }

    @Override
    protected boolean doUpdate(LiveAreaShield entity) {
        return liveAreaShieldRepository.save(entity);
    }

    @Override
    protected boolean doAfterUpdate(LiveAreaShield entity) {
        return true;
    }

    @Override
    protected boolean doDelete(Long aLong) {
        LiveAreaShield shield = liveAreaShieldRepository.findOne(aLong);
        shield.setDeleted(true);
        return liveAreaShieldRepository.save(shield);
    }

    @Override
    protected boolean doAfterDelete(LiveAreaShield entity) {
        return true;
    }

    @Override
    protected LiveAreaShield doFindExistEntity(LiveAreaShield entity) {
        return liveAreaShieldRepository.findOne(entity.getId());
    }
}
