package com.lesports.qmt.sbc.service.impl;

import com.google.common.collect.Lists;
import com.lesports.api.common.CallerParam;
import com.lesports.id.api.IdType;
import com.lesports.id.client.LeIdApis;
import com.lesports.mongo.repository.MongoCrudRepository;
import com.lesports.qmt.QmtOperationType;
import com.lesports.qmt.sbc.api.dto.TResource;
import com.lesports.qmt.sbc.api.dto.TResourceContent;
import com.lesports.qmt.sbc.cache.TResourceCache;
import com.lesports.qmt.sbc.cache.TResourceContentCache;
import com.lesports.qmt.sbc.converter.TResourceContentConverter;
import com.lesports.qmt.sbc.converter.TResourceConverter;
import com.lesports.qmt.sbc.model.QmtResource;
import com.lesports.qmt.sbc.model.ResourceContent;
import com.lesports.qmt.sbc.model.ResourceContentOnline;
import com.lesports.qmt.sbc.repository.ResourceContentOnlineRepository;
import com.lesports.qmt.sbc.repository.ResourceContentRepository;
import com.lesports.qmt.sbc.repository.ResourceRepository;
import com.lesports.qmt.sbc.service.ResourceContentOnlineService;
import com.lesports.qmt.sbc.service.support.AbstractSbcService;
import com.lesports.utils.PageUtils;
import com.lesports.utils.math.LeNumberUtils;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;
import static org.springframework.data.mongodb.core.query.Update.update;

/**
 * User: ellios
 * Time: 16-10-23 : 下午6:20
 */
@Service("resourceContentOnlineService")
public class ResourceContentOnlineServiceImpl extends AbstractSbcService<ResourceContentOnline, Long> implements ResourceContentOnlineService {

    private static final Logger LOG = LoggerFactory.getLogger(ResourceContentOnlineServiceImpl.class);

    @Resource
    private ResourceContentOnlineRepository resourceContentOnlineRepository;

    @Resource
    private TResourceContentCache resourceContentCache;

    @Resource
    private TResourceContentConverter resourceContentConverter;


    @Override
    public List<Long> getResourceContentIdsByResourceId(Long resourceId, Pageable pageable, CallerParam caller) {
        Query q = query(where("resourceId").is(resourceId));
//        q.addCriteria(where("deleted").is(false));
        pageable = PageUtils.getValidPageable(pageable);
        pageable = new PageRequest(pageable.getPageNumber(), pageable.getPageSize(), Sort.Direction.ASC, "order");
        q.with(pageable);
        return resourceContentOnlineRepository.findIdByQuery(q);
    }

    @Override
    public List<TResourceContent> getTResourceContentsByIds(List<Long> ids, CallerParam caller) {
        if (CollectionUtils.isEmpty(ids)) {
            return Collections.EMPTY_LIST;
        }
        List<TResourceContent> tResourceContents = Lists.newArrayListWithExpectedSize(ids.size());
        for (Long id : ids) {
            TResourceContent tResourceContent = getTResourceContentById(id, caller);
            if (tResourceContent != null) {
                tResourceContents.add(tResourceContent);
            }
        }
        return tResourceContents;
    }

    @Override
    public TResourceContent getTResourceContentById(long id, CallerParam caller) {
        ResourceContentOnline model = resourceContentOnlineRepository.findOne(id);
        if (model == null) {
            LOG.warn("fail to getTResourceContentById, resource content no exists. id : {}, caller : {}", id, caller);
            return null;
        }else {
            LOG.info("contentType={}",model.getContentType());
        }
       return resourceContentConverter.toDto(model);
    }

    @Override
    protected boolean doCreate(ResourceContentOnline entity) {
        entity.setId(LeIdApis.nextId(IdType.RESOURCE));
        entity.setDeleted(false);
        return resourceContentOnlineRepository.save(entity);
    }


    @Override
    protected ResourceContentOnline doFindExistEntity(ResourceContentOnline entity) {
        return resourceContentOnlineRepository.findOne(entity.getId());
    }

    @Override
    protected boolean doAfterCreate(ResourceContentOnline entity) {
        return false;
    }

    @Override
    protected boolean doUpdate(ResourceContentOnline entity) {
        return false;
    }

    @Override
    protected boolean doAfterUpdate(ResourceContentOnline entity) {
        return false;
    }

    @Override
    protected boolean doDelete(Long aLong) {
        return false;
    }

    @Override
    protected boolean doAfterDelete(ResourceContentOnline entity) {
        return false;
    }

    @Override
    protected QmtOperationType getOpreationType(ResourceContentOnline entity) {
        if (LeNumberUtils.toLong(entity.getId()) > 0) {
            return QmtOperationType.UPDATE;
        }
        return QmtOperationType.CREATE;
    }

    @Override
    protected MongoCrudRepository getInnerRepository() {
        return resourceContentOnlineRepository;
    }


}
