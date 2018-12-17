package com.lesports.qmt.cms.service.impl;

import com.lesports.id.api.IdType;
import com.lesports.id.client.LeIdApis;
import com.lesports.mongo.repository.MongoCrudRepository;
import com.lesports.qmt.QmtOperationType;
import com.lesports.qmt.cms.api.dto.TColumn;
import com.lesports.qmt.cms.api.dto.TLayout;
import com.lesports.qmt.cms.converter.ColumnConverter;
import com.lesports.qmt.cms.model.Column;
import com.lesports.qmt.cms.model.Layout;
import com.lesports.qmt.cms.repository.ColumnRepository;
import com.lesports.qmt.cms.service.ColumnService;
import com.lesports.qmt.cms.service.support.AbstractCmsService;
import com.lesports.utils.LeDateUtils;
import com.lesports.utils.math.LeNumberUtils;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;
import static org.springframework.data.mongodb.core.query.Update.update;

/**
 * User: ellios
 * Time: 16-11-30 : 下午6:52
 */
@Service("columnService")
public class ColumnServiceImpl extends AbstractCmsService<Column, Long> implements ColumnService {

    @Resource
    protected ColumnRepository columnRepository;

    @Resource
    private ColumnConverter columnConverter;

    @Override
    protected MongoCrudRepository<Column, Long> getInnerRepository() {
        return columnRepository;
    }

    @Override
    public Column findOne(Long id) {
        if (null == id) return null;
        return columnRepository.findOne(id);
    }

    @Override
    protected QmtOperationType getOpreationType(Column entity) {
        if (null == entity || LeNumberUtils.toLong(entity.getId()) <= 0) {
            return QmtOperationType.CREATE;
        }
        return QmtOperationType.UPDATE;
    }

    @Override
    protected boolean doCreate(Column entity) {
        entity.setId(LeIdApis.nextId(IdType.CMS_COLUMN));
        String now = LeDateUtils.formatYYYYMMDDHHMMSS(new Date());
        entity.setDeleted(false);
        entity.setCreateAt(now);
        entity.setUpdateAt(now);
        return columnRepository.save(entity);
    }

    @Override
    protected boolean doAfterCreate(Column entity) {
        return true;
    }

    @Override
    protected boolean doUpdate(Column entity) {
        return columnRepository.save(entity);
    }

    @Override
    protected boolean doAfterUpdate(Column entity) {
        return true;
    }

    @Override
    protected boolean doDelete(Long id) {
        Update up = update("deleted", true);
        return columnRepository.update(id, up);
    }

    @Override
    protected boolean doAfterDelete(Column entity) {
        return true;
    }

    @Override
    protected Column doFindExistEntity(Column entity) {
        return columnRepository.findOne(entity.getId());
    }

    @Override
    public TColumn getTColumnById(long id) {
        Column model = columnRepository.findOne(id);
        if(model == null){
            LOG.warn("fail to getTColumnById, entity no exist! id : {}", id);
            return null;
        }
        TColumn dto = columnConverter.toDto(model);
        return dto;
    }

    @Override
    public TColumn getTColumnByFullPath(String path) {
        Query q = query(where("deleted").is(false));
        q.addCriteria(where("full_path").is(path));
        Long id = columnRepository.findOneIdByQuery(q);
        if(LeNumberUtils.toLong(id) <= 0){
            LOG.warn("fail to getTColumnByFullPath, entity no exist! path : {}", path);
            return null;
        }
        return getTColumnById(id);
    }
}
