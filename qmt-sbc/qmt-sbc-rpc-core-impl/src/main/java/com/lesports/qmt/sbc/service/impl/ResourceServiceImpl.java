package com.lesports.qmt.sbc.service.impl;

import com.google.common.collect.Lists;
import com.lesports.api.common.CallerParam;
import com.lesports.id.api.IdType;
import com.lesports.id.client.LeIdApis;
import com.lesports.mongo.repository.MongoCrudRepository;
import com.lesports.qmt.QmtOperationType;
import com.lesports.qmt.sbc.api.dto.TResource;
import com.lesports.qmt.sbc.cache.TResourceCache;
import com.lesports.qmt.sbc.converter.TResourceConverter;
import com.lesports.qmt.sbc.model.QmtResource;
import com.lesports.qmt.sbc.repository.ResourceContentRepository;
import com.lesports.qmt.sbc.repository.ResourceRepository;
import com.lesports.qmt.sbc.service.ResourceService;
import com.lesports.qmt.sbc.service.support.AbstractSbcService;
import com.lesports.utils.math.LeNumberUtils;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

import static org.springframework.data.mongodb.core.query.Update.update;

/**
 * User: ellios
 * Time: 16-10-23 : 下午6:20
 */
@Service("resourceService")
public class ResourceServiceImpl extends AbstractSbcService<QmtResource, Long> implements ResourceService {

    private static final Logger LOG = LoggerFactory.getLogger(ResourceServiceImpl.class);

    @Resource
    private ResourceRepository resourceRepository;
    @Resource
    private ResourceContentRepository resourceContentRepository;
    @Resource
    private TResourceConverter resourceConverter;
    @Resource
    private TResourceCache resourceCache;

    @Override
    protected boolean doCreate(QmtResource entity) {
        entity.setId(LeIdApis.nextId(IdType.RESOURCE));
        entity.setDeleted(false);
        return resourceRepository.save(entity);
    }

    @Override
    protected boolean doAfterCreate(QmtResource entity) {
        return resourceCache.delete(entity.getId());
    }

    @Override
    protected boolean doUpdate(QmtResource entity) {
        entity.setDeleted(false);
        QmtResource qmtResource = doFindExistEntity(entity);
        entity.setGroup(qmtResource.getGroup());
        if(entity.getPIds().isEmpty()){
            entity.addPIds(qmtResource.getPIds());
        }
        entity.setResourceContentIds(qmtResource.getResourceContentIds());
        return resourceRepository.save(entity);
    }

    @Override
    protected boolean doAfterUpdate(QmtResource entity) {
        return resourceCache.delete(entity.getId());
    }

    @Override
    protected boolean doDelete(Long id) {
        Update up = update("deleted", true);
        return resourceRepository.update(id, up);
    }

    @Override
    protected boolean doAfterDelete(QmtResource entity) {
        return resourceCache.delete(entity.getId());
    }

    @Override
    protected QmtResource doFindExistEntity(QmtResource entity) {
        return resourceRepository.findOne(entity.getId());
    }

/*    @Override
    public boolean delete(Long id) {
        Update up = update("deleted", true);
        return resourceRepository.update(id, up);
    }*/

    @Override
    protected QmtOperationType getOpreationType(QmtResource entity) {
        if (LeNumberUtils.toLong(entity.getId()) > 0) {
            return QmtOperationType.UPDATE;
        }
        return QmtOperationType.CREATE;
    }

    @Override
    public List<TResource> getTResourcesByIds(List<Long> ids, CallerParam caller) {
        if (CollectionUtils.isEmpty(ids)) {
            return Collections.EMPTY_LIST;
        }
        List<TResource> tResources = Lists.newArrayListWithExpectedSize(ids.size());
        for (Long id : ids) {
            TResource tResource = getTResourceById(id, caller);
            if (tResource != null) {
                tResources.add(tResource);
            }
        }
        return tResources;
    }

    @Override
    public TResource getTResourceById(long id, CallerParam caller) {
        TResource tResource = resourceCache.findOne(id);
        if (tResource == null) {
            QmtResource resource = resourceRepository.findOne(id);
            if (resource == null) {
                LOG.warn("fail to getTResourceById, resource no exists. id : {}, caller : {}", id, caller);
                return null;
            }
            tResource = resourceConverter.toDto(resource);
            if (tResource == null) {
                LOG.warn("fail to getTResourceById, toTVo fail. id : {}, caller : {}", id, caller);
                return null;
            }
            resourceCache.save(tResource);
        }
        return tResource;
    }

    @Override
    protected MongoCrudRepository getInnerRepository() {
        return resourceRepository;
    }


}
