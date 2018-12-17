package com.lesports.qmt.sbc.service.impl;

import com.lesports.api.common.CallerParam;
import com.lesports.id.api.IdType;
import com.lesports.id.client.LeIdApis;
import com.lesports.mongo.repository.MongoCrudRepository;
import com.lesports.qmt.QmtOperationType;
import com.lesports.qmt.sbc.api.dto.TResource;
import com.lesports.qmt.sbc.converter.TResourceConverter;
import com.lesports.qmt.sbc.model.QmtResourceOnline;
import com.lesports.qmt.sbc.repository.ResourceOnlineRepository;
import com.lesports.qmt.sbc.service.ResourceOnlineService;
import com.lesports.qmt.sbc.service.support.AbstractSbcService;
import com.lesports.utils.math.LeNumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * User: ellios
 * Time: 16-10-23 : 下午6:20
 */
@Service("resourceOnlineService")
public class ResourceOnlineServiceImpl extends AbstractSbcService<QmtResourceOnline, Long> implements ResourceOnlineService {

    private static final Logger LOG = LoggerFactory.getLogger(ResourceOnlineServiceImpl.class);

    @Resource
    private ResourceOnlineRepository resourceOnlineRepository;

    @Resource
    private TResourceConverter resourceConverter;

    @Override
    public TResource getTResourceOnlineByRId(long rId, CallerParam caller) {
        QmtResourceOnline qmtResourceOnline = resourceOnlineRepository.findLatestOneByRID(rId);
        if(qmtResourceOnline == null){
          return null;
         }
        return resourceConverter.toDto(qmtResourceOnline);
    }

    @Override
    protected boolean doCreate(QmtResourceOnline entity) {
        entity.setId(LeIdApis.nextId(IdType.RESOURCE));
        entity.setDeleted(false);
        return resourceOnlineRepository.save(entity);
    }


    @Override
    protected QmtResourceOnline doFindExistEntity(QmtResourceOnline entity) {
        return resourceOnlineRepository.findOne(entity.getId());
    }

    @Override
    protected boolean doAfterCreate(QmtResourceOnline entity) {
        return false;
    }

    @Override
    protected boolean doUpdate(QmtResourceOnline entity) {
        return false;
    }

    @Override
    protected boolean doAfterUpdate(QmtResourceOnline entity) {
        return false;
    }

    @Override
    protected boolean doDelete(Long aLong) {
        return false;
    }

    @Override
    protected boolean doAfterDelete(QmtResourceOnline entity) {
        return false;
    }

    @Override
    protected QmtOperationType getOpreationType(QmtResourceOnline entity) {
        if (LeNumberUtils.toLong(entity.getId()) > 0) {
            return QmtOperationType.UPDATE;
        }
        return QmtOperationType.CREATE;
    }

    @Override
    protected MongoCrudRepository getInnerRepository() {
        return resourceOnlineRepository;
    }


}
