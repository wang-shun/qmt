package com.lesports.qmt.cms.service.impl;

import com.lesports.id.api.IdType;
import com.lesports.id.client.LeIdApis;
import com.lesports.mongo.repository.MongoCrudRepository;
import com.lesports.qmt.QmtOperationType;
import com.lesports.qmt.cms.model.Mapper;
import com.lesports.qmt.cms.repository.MapperRepository;
import com.lesports.qmt.cms.service.MapperService;
import com.lesports.qmt.cms.service.support.AbstractCmsService;
import com.lesports.utils.math.LeNumberUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * User: ellios
 * Time: 17-1-10 : 下午4:01
 */
@Service("mapperService")
public class MapperServiceImpl extends AbstractCmsService<Mapper, Long> implements MapperService {

    @Resource
    private MapperRepository mapperRepository;

    @Override
    protected MongoCrudRepository<Mapper, Long> getInnerRepository() {
        return mapperRepository;
    }

    @Override
    protected QmtOperationType getOpreationType(Mapper entity) {
        if (null == entity || LeNumberUtils.toLong(entity.getId()) <= 0) {
            return QmtOperationType.CREATE;
        }
        return QmtOperationType.UPDATE;
    }

    @Override
    protected boolean doCreate(Mapper entity) {
        entity.setId(LeIdApis.nextId(IdType.DICT_ENTRY));
        return mapperRepository.save(entity);
    }

    @Override
    protected boolean doAfterCreate(Mapper entity) {
        return false;
    }

    @Override
    protected boolean doUpdate(Mapper entity) {
        return mapperRepository.save(entity);
    }

    @Override
    protected boolean doAfterUpdate(Mapper entity) {
        return false;
    }

    @Override
    protected boolean doDelete(Long aLong) {
        return false;
    }

    @Override
    protected boolean doAfterDelete(Mapper entity) {
        return false;
    }

    @Override
    protected Mapper doFindExistEntity(Mapper entity) {
        return mapperRepository.findOne(entity.getId());
    }
}
