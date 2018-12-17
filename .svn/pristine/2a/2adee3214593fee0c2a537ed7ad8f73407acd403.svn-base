package com.lesports.qmt.sbc.thrift;

import com.lesports.api.common.CallerParam;
import com.lesports.api.common.PageParam;
import com.lesports.qmt.sbc.api.dto.TResource;
import com.lesports.qmt.sbc.api.dto.TResourceContent;
import com.lesports.qmt.sbc.api.service.TSbcResourceService;
import com.lesports.qmt.sbc.service.ResourceContentOnlineService;
import com.lesports.qmt.sbc.service.ResourceContentService;
import com.lesports.qmt.sbc.service.ResourceOnlineService;
import com.lesports.qmt.sbc.service.ResourceService;
import com.lesports.utils.CallerUtils;
import com.lesports.utils.PageUtils;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.Collections;
import java.util.List;

import static me.ellios.hedwig.http.mediatype.ExtendedMediaType.APPLICATION_X_THRIFT;

/**
 * User: ellios
 * Time: 16-3-20 : 下午4:39
 */
@Service("thriftSbcResourceService")
@Path("/sbc/resources")
@Produces({APPLICATION_X_THRIFT})
public class TSbcResourceServiceAdapter implements TSbcResourceService.Iface {

    private static final Logger LOG = LoggerFactory.getLogger(TSbcResourceServiceAdapter.class);

    @Resource
    private ResourceService resourceService;
    @Resource
    private ResourceOnlineService resourceOnlineService;
    @Resource
    private ResourceContentService resourceContentService;
    @Resource
    private ResourceContentOnlineService resourceContentOnlineService;

    @Override
    public TResource getTResourceById(long id, CallerParam caller) throws TException {
        try {
            caller = CallerUtils.getValidCaller(caller);
            return resourceService.getTResourceById(id, caller);
        } catch (Exception e) {
            LOG.error("fail to getTResourceById. id:{}, caller : {}", id, caller, e);
        }
        return null;
    }

    @Override
    public TResource getLastVersionTResourceById(long rId, CallerParam caller) throws TException {
        try {
            caller = CallerUtils.getValidCaller(caller);
            return resourceOnlineService.getTResourceOnlineByRId(rId,caller);
        } catch (Exception e) {
            LOG.error("fail to getLastVersionTResourceById. id:{}, caller : {}", rId, caller, e);
        }
        return null;
    }

    @Override
    public List<Long> getOnlineResourceContentIdsByResourceId(long resourceId, PageParam page, CallerParam caller) throws TException {
        return resourceContentOnlineService.getResourceContentIdsByResourceId(resourceId,PageUtils.convertToPageable(page),caller);
    }

    @Override
    public List<TResource> getTResourcesByIds(List<Long> ids, CallerParam caller) throws TException {
        try {
            caller = CallerUtils.getValidCaller(caller);
            return resourceService.getTResourcesByIds(ids, caller);
        } catch (Exception e) {
            LOG.error("fail to getTResourcesByIds. ids:{}, caller : {}", ids, caller, e);
        }
        return Collections.EMPTY_LIST;
    }

    @Override
    public TResourceContent getTResourceContentById(long id, CallerParam caller) throws TException {
        try {
            caller = CallerUtils.getValidCaller(caller);
            return resourceContentService.getTResourceContentById(id, caller);
        } catch (Exception e) {
            LOG.error("fail to getTResourceContentById. id:{}, caller : {}", id, caller, e);
        }
        return null;
    }

    @Override
    public List<TResourceContent> getTResourceContentsByIds(List<Long> ids, CallerParam caller) throws TException {
        try {
            caller = CallerUtils.getValidCaller(caller);
            return resourceContentService.getTResourceContentsByIds(ids, caller);
        } catch (Exception e) {
            LOG.error("fail to getTResourceContentsByIds. ids:{}, caller : {}", ids, caller, e);
        }
        return Collections.EMPTY_LIST;
    }

    @Override
    public List<TResourceContent> getOnlineTResourceContentsByIds(List<Long> ids, CallerParam caller) throws TException {
        try {
            caller = CallerUtils.getValidCaller(caller);
            return resourceContentOnlineService.getTResourceContentsByIds(ids, caller);
        } catch (Exception e) {
            LOG.error("fail to getTResourceContentsByIds. ids:{}, caller : {}", ids, caller, e);
        }
        return Collections.EMPTY_LIST;
    }

    @Override
    public List<Long> getResourceContentIdsByResourceId(long resourceId, PageParam page, CallerParam caller) throws TException {
        try {
            caller = CallerUtils.getValidCaller(caller);
            Pageable pageable = PageUtils.convertToPageable(page);
            return resourceContentService.getResourceContentIdsByResourceId(resourceId, pageable, caller);
        } catch (Exception e) {
            LOG.error("fail to getResourceContentIdsByResourceId. resourceId:{}, page : {}, caller : {}", resourceId, page, caller, e);
        }
        return Collections.EMPTY_LIST;
    }

    @Override
    public long countResourceContentByResourceId(long resourceId, CallerParam caller) throws TException {
        return resourceContentService.countResourceContentByResourceId(resourceId);
    }
}
