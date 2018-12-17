package com.lesports.qmt.sbc.service.impl;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.lesports.api.common.CallerParam;
import com.lesports.id.api.IdType;
import com.lesports.id.client.LeIdApis;
import com.lesports.mongo.repository.MongoCrudRepository;
import com.lesports.qmt.QmtOperationType;
import com.lesports.qmt.sbc.api.dto.TResourceContent;
import com.lesports.qmt.sbc.cache.TResourceContentCache;
import com.lesports.qmt.sbc.converter.TResourceContentConverter;
import com.lesports.qmt.sbc.model.QmtResource;
import com.lesports.qmt.sbc.model.ResourceContent;
import com.lesports.qmt.sbc.repository.ResourceContentRepository;
import com.lesports.qmt.sbc.repository.ResourceRepository;
import com.lesports.qmt.sbc.service.ResourceContentService;
import com.lesports.qmt.sbc.service.support.AbstractSbcService;
import com.lesports.utils.LeDateUtils;
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
import java.util.Date;
import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;
import static org.springframework.data.mongodb.core.query.Update.update;

/**
 * User: ellios
 * Time: 16-10-23 : 下午6:20
 */
@Service("resourceContentService")
public class ResourceContentServiceImpl extends AbstractSbcService<ResourceContent, Long> implements ResourceContentService {

    private static final Logger LOG = LoggerFactory.getLogger(ResourceContentServiceImpl.class);

    @Resource
    private ResourceContentRepository resourceContentRepository;
    @Resource
    private ResourceRepository resourceRepository;
    @Resource
    private TResourceContentCache resourceContentCache;
    @Resource
    private TResourceContentConverter resourceContentConverter;

    @Override
    protected boolean doCreate(ResourceContent entity) {
        entity.setId(LeIdApis.nextId(IdType.RESOURCE_CONTENT));
        entity.setDeleted(false);
        return resourceContentRepository.save(entity);
    }

    @Override
    protected boolean doAfterCreate(ResourceContent entity) {
        QmtResource qmtResource = resourceRepository.findOne(entity.getResourceId());
        qmtResource.addResourceContentId(entity.getId());
        return resourceRepository.save(qmtResource);
    }

    @Override
    protected boolean doUpdate(ResourceContent entity) {
        entity.setDeleted(false);
        return resourceContentRepository.save(entity);
    }

    @Override
    protected boolean doAfterUpdate(ResourceContent entity) {
        return resourceContentCache.delete(entity.getId());
    }

    @Override
    protected boolean doDelete(Long id) {
        Update up = update("deleted", true);
        return resourceContentRepository.update(id, up);
    }

    @Override
     public boolean upOrder(ResourceContent resourceContent,Integer increment) {
        Preconditions.checkNotNull(resourceContent);
        boolean result = true;
        Query q = query(where("resourceId").is(resourceContent.getResourceId()));
        q.addCriteria(where("order").gt(resourceContent.getOrder()));
        q.addCriteria(where("deleted").is(false));
        List<Long> ids = resourceContentRepository.findIdByQuery(null);
        for(Long id : ids){
            result &= changeOrder(id,increment);
        }
        return result;
    }

    /**
     * 1.把资源位中关联的这个资源位内容删除
     * 2.把其他的order-1
     * 3.删除缓存
     * @param entity
     * @return
     */
    @Override
    protected boolean doAfterDelete(ResourceContent entity) {
        QmtResource qmtResource = resourceRepository.findOne(entity.getResourceId());
        qmtResource.deleteResourceContentId(entity.getId());
        resourceRepository.save(qmtResource);
        upOrder(entity,-1);
        return resourceContentCache.delete(entity.getId());
    }

    @Override
    protected ResourceContent doFindExistEntity(ResourceContent entity) {
        return resourceContentRepository.findOne(entity.getId());
    }

    @Override
    protected QmtOperationType getOpreationType(ResourceContent entity) {
        if (LeNumberUtils.toLong(entity.getId()) > 0) {
            return QmtOperationType.UPDATE;
        }
        return QmtOperationType.CREATE;
    }

    @Override
    protected MongoCrudRepository getInnerRepository() {
        return resourceContentRepository;
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
        TResourceContent dto = resourceContentCache.findOne(id);
        if (dto == null) {
            ResourceContent model = resourceContentRepository.findOne(id);
            if (model == null) {
                LOG.warn("fail to getTResourceContentById, resource content no exists. id : {}, caller : {}", id, caller);
                return null;
            }else {
                LOG.info("contentType={}",model.getContentType());
            }
            dto = resourceContentConverter.toDto(model);
            if (dto == null) {
                LOG.warn("fail to getTResourceContentById, toTVo fail. id : {}, caller : {}", id, caller);
                return null;
            }else {
                LOG.info("dtp contentType={}",dto.getType());
            }
            resourceContentCache.save(dto);
        }
        return dto;
    }

    @Override
    public List<Long> getResourceContentIdsByResourceId(Long resourceId, Pageable pageable, CallerParam caller) {
        Query q = query(where("resourceId").is(resourceId));
        q.addCriteria(where("deleted").is(false));
        pageable = PageUtils.getValidPageable(pageable);
        pageable = new PageRequest(pageable.getPageNumber(), pageable.getPageSize(), Sort.Direction.ASC, "order");
        q.with(pageable);
        return resourceContentRepository.findIdByQuery(q);
    }

    @Override
    public Long countResourceContentByResourceId(Long resourceId) {
        Query q = query(where("resourceId").is(resourceId));
        q.addCriteria(where("deleted").is(false));
        return resourceContentRepository.countByQuery(q);
    }

}
