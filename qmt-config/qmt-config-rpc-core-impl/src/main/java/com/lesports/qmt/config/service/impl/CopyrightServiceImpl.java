package com.lesports.qmt.config.service.impl;

import com.lesports.id.api.IdType;
import com.lesports.id.client.LeIdApis;
import com.lesports.mongo.repository.MongoCrudRepository;
import com.lesports.qmt.QmtOperationType;
import com.lesports.qmt.config.model.Copyright;
import com.lesports.qmt.config.repository.CopyrightRepository;
import com.lesports.qmt.config.service.CopyrightService;
import com.lesports.qmt.service.support.AbstractQmtService;
import com.lesports.utils.LeDateUtils;
import com.lesports.utils.math.LeNumberUtils;
import org.apache.commons.beanutils.LeBeanUtils;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

import static org.springframework.data.mongodb.core.query.Update.update;

/**
 * Created by zhangxudong@le.com on 2015/7/2.
 */
@Service("copyrightService")
public class CopyrightServiceImpl extends AbstractQmtService<Copyright, Long> implements CopyrightService {

//    private static final Logger LOG = LoggerFactory.getLogger(CopyrightServiceImpl.class);

    @Resource
    private CopyrightRepository copyrightRepository;

    @Override
    protected MongoCrudRepository<Copyright, Long> getInnerRepository() {
        return this.copyrightRepository;
    }

    @Override
    protected QmtOperationType getOpreationType(Copyright entity) {
        if (LeNumberUtils.toLong(entity.getId()) <= 0) {
            return QmtOperationType.CREATE;
        }
        return QmtOperationType.UPDATE;
    }

    @Override
    protected boolean doCreate(Copyright entity) {
        if(null == entity) return false;
        entity.setId(LeIdApis.nextId(IdType.COPYRIGHT));
        return copyrightRepository.save(entity);
    }

    @Override
    protected boolean doAfterCreate(Copyright entity) {
        return true;
    }

    @Override
    protected boolean doUpdate(Copyright entity) {
        if(null == entity) return false;

        Copyright orig = null;
        if (null == entity.getId())
            entity.setId(LeIdApis.nextId(IdType.COPYRIGHT));
        else
            orig = copyrightRepository.findOne(entity.getId());

        boolean result = false;
        if (null != orig) {
            LeBeanUtils.copyNotEmptyPropertiesQuietly(orig, entity);
            result = copyrightRepository.save(orig);
        } else
            result = copyrightRepository.save(entity);
        return result;
    }

    @Override
    protected boolean doAfterUpdate(Copyright entity) {
        return true;
    }

    @Override
    protected boolean doDelete(Long id) {
        if(null == id || id <= 0) return false;
        String now = LeDateUtils.formatYYYYMMDDHHMMSS(new Date());
        Update up = update("deleted", true).set("update_at", now);
        return copyrightRepository.update(id, up);
    }

    @Override
    protected boolean doAfterDelete(Copyright entity) {
        return true;
    }

    @Override
    protected Copyright doFindExistEntity(Copyright entity) {
        if(null == entity) return new Copyright();
        return copyrightRepository.findOne(entity.getId());
    }
}
