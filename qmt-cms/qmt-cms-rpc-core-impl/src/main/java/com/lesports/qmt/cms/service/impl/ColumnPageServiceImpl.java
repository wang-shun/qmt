package com.lesports.qmt.cms.service.impl;

import com.lesports.id.api.IdType;
import com.lesports.id.client.LeIdApis;
import com.lesports.mongo.repository.MongoCrudRepository;
import com.lesports.qmt.QmtOperationType;
import com.lesports.qmt.cms.api.dto.TColumn;
import com.lesports.qmt.cms.api.dto.TColumnPage;
import com.lesports.qmt.cms.cache.TColumnPageCache;
import com.lesports.qmt.cms.converter.ColumnPageConverter;
import com.lesports.qmt.cms.model.Column;
import com.lesports.qmt.cms.model.ColumnPage;
import com.lesports.qmt.cms.repository.ColumnPageRepository;
import com.lesports.qmt.cms.service.ColumnPageService;
import com.lesports.qmt.cms.service.support.AbstractCmsService;
import com.lesports.utils.LeDateUtils;
import com.lesports.utils.math.LeNumberUtils;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

import static org.springframework.data.mongodb.core.query.Update.update;

/**
 * User: ellios
 * Time: 16-11-30 : 下午6:55
 */
@Service("columnPageService")
public class ColumnPageServiceImpl extends AbstractCmsService<ColumnPage, Long> implements ColumnPageService {

    @Resource
    protected ColumnPageRepository columnPageRepository;

    @Resource
    private ColumnPageConverter columnPageConverter;

    @Resource
    private TColumnPageCache columnPageCache;

    @Override
    protected MongoCrudRepository<ColumnPage, Long> getInnerRepository() {
        return columnPageRepository;
    }

    @Override
    public ColumnPage findOne(Long id) {
        if (null == id) return null;
        return columnPageRepository.findOne(id);
    }

    @Override
    protected QmtOperationType getOpreationType(ColumnPage entity) {
        if (null == entity || LeNumberUtils.toLong(entity.getId()) <= 0) {
            return QmtOperationType.CREATE;
        }
        return QmtOperationType.UPDATE;
    }

    @Override
    protected boolean doCreate(ColumnPage entity) {
        entity.setId(LeIdApis.nextId(IdType.CMS_PAGE));
        String now = LeDateUtils.formatYYYYMMDDHHMMSS(new Date());
        entity.setDeleted(false);
        entity.setCreateAt(now);
        entity.setUpdateAt(now);
        return columnPageRepository.save(entity);
    }

    @Override
    protected boolean doAfterCreate(ColumnPage entity) {
        columnPageCache.delete(entity.getId());
        return true;
    }

    @Override
    protected boolean doUpdate(ColumnPage entity) {
        return columnPageRepository.save(entity);
    }

    @Override
    protected boolean doAfterUpdate(ColumnPage entity) {
        columnPageCache.delete(entity.getId());
        return true;
    }

    @Override
    protected boolean doDelete(Long id) {
        columnPageCache.delete(id);
        Update up = update("deleted", true);
        return columnPageRepository.update(id, up);
    }

    @Override
    protected boolean doAfterDelete(ColumnPage entity) {
        return true;
    }

    @Override
    protected ColumnPage doFindExistEntity(ColumnPage entity) {
        return columnPageRepository.findOne(entity.getId());
    }

    @Override
    public TColumnPage getTColumnPageById(long id) {
        ColumnPage model = columnPageRepository.findOne(id);
        if(model == null){
            LOG.warn("fail to getTColumnPageById, entity no exist! id : {}", id);
            return null;
        }
        TColumnPage dto = columnPageConverter.toDto(model);
        return dto;
    }
}
