package com.lesports.qmt.cms.service.impl;

import com.lesports.id.api.IdType;
import com.lesports.id.client.LeIdApis;
import com.lesports.mongo.repository.MongoCrudRepository;
import com.lesports.qmt.QmtOperationType;
import com.lesports.qmt.cms.api.dto.TLayout;
import com.lesports.qmt.cms.cache.TLayOutCache;
import com.lesports.qmt.cms.converter.LayoutConverter;
import com.lesports.qmt.cms.model.Layout;
import com.lesports.qmt.cms.repository.LayoutRepository;
import com.lesports.qmt.cms.service.LayoutService;
import com.lesports.qmt.cms.service.support.AbstractCmsService;
import com.lesports.utils.LeDateUtils;
import com.lesports.utils.math.LeNumberUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;
import static org.springframework.data.mongodb.core.query.Update.update;

/**
 * User: ellios
 * Time: 16-11-29 : 下午5:39
 */
@Service("layoutService")
public class LayoutServiceImpl extends AbstractCmsService<Layout, Long> implements LayoutService {

    @Resource
    protected LayoutRepository layoutRepository;

    @Resource
    private LayoutConverter layoutConverter;

    @Resource
    private TLayOutCache layOutCache;

    @Override
    protected MongoCrudRepository<Layout, Long> getInnerRepository() {
        return layoutRepository;
    }

    @Override
    public Layout findOne(Long id) {
        if (null == id) return null;
        return layoutRepository.findOne(id);
    }

    @Override
    protected QmtOperationType getOpreationType(Layout entity) {
        if (null == entity || LeNumberUtils.toLong(entity.getId()) <= 0) {
            return QmtOperationType.CREATE;
        }
        return QmtOperationType.UPDATE;
    }

    @Override
    protected boolean doCreate(Layout entity) {
        entity.setId(LeIdApis.nextId(IdType.CMS_LAYOUT));
        String now = LeDateUtils.formatYYYYMMDDHHMMSS(new Date());
        entity.setDeleted(false);
        entity.setCreateAt(now);
        entity.setUpdateAt(now);
        return layoutRepository.save(entity);
    }

    @Override
    protected boolean doAfterCreate(Layout entity) {
        return true;
    }

    @Override
    protected boolean doUpdate(Layout entity) {
        layOutCache.delete(entity.getId());
        return layoutRepository.save(entity);
    }

    @Override
    protected boolean doAfterUpdate(Layout entity) {
        return true;
    }

    @Override
    protected boolean doDelete(Long id) {
        layOutCache.delete(id);
        Update up = update("deleted", true);
        return layoutRepository.update(id, up);
    }

    @Override
    protected boolean doAfterDelete(Layout entity) {
        return true;
    }

    @Override
    protected Layout doFindExistEntity(Layout entity) {
        return layoutRepository.findOne(entity.getId());
    }

    @Override
    public TLayout getTLayoutById(long id) {
        Layout model = layoutRepository.findOne(id);
        if(model == null){
            LOG.warn("fail to getTLayoutById, entity no exist! id : {}", id);
            return null;
        }
        TLayout dto = layoutConverter.toDto(model);
        return dto;
    }

    @Override
    public TLayout getTLayoutByPath(String path) {
        Query q = query(where("deleted").is(false));
        q.addCriteria(where("path").is(path));
        Long id = layoutRepository.findOneIdByQuery(q);
        if(LeNumberUtils.toLong(id) <= 0){
            LOG.warn("fail to getTLayoutByPath, entity no exist! path : {}", path);
            return null;
        }
        TLayout tLayout = layOutCache.findOne(id);
        if(tLayout == null){
            tLayout = getTLayoutById(id);
            layOutCache.save(tLayout);
        }
        return tLayout;
    }
}
