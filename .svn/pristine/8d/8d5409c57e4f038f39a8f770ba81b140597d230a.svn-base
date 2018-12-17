package com.lesports.qmt.sbc.client;

import com.lesports.api.common.CallerParam;
import com.lesports.api.common.PageParam;
import com.lesports.qmt.sbc.api.dto.ResourceUpdateType;
import com.lesports.qmt.sbc.api.dto.TResource;
import com.lesports.qmt.sbc.api.dto.TResourceContent;
import com.lesports.qmt.sbc.api.service.TSbcResourceService;
import me.ellios.hedwig.rpc.client.ClientBuilder;
import me.ellios.hedwig.rpc.core.ServiceType;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;

/**
 * 资源位接口调用
 * Created by denghui on 2016/10/24.
 */
public class QmtSbcResourceApis {

    private static final Logger LOG = LoggerFactory.getLogger(QmtSbcResourceApis.class);

    private static TSbcResourceService.Iface sbcResourceService = new ClientBuilder<TSbcResourceService.Iface>()
            .serviceType(ServiceType.THRIFT).serviceFace(TSbcResourceService.Iface.class).build();

    /**
     * 根据id获取资源位详情
     *
     * @param id
     * @param caller
     * @return
     */
    public static TResource getTResourceById(long id, CallerParam caller,int dataType) {
        try {
            TResource tResource = sbcResourceService.getTResourceById(id, caller);
            if(tResource != null && (tResource.getUpdateType() == ResourceUpdateType.AUTO || dataType == 2)){
                //如果是手动的，或者 dataType = 2 则直接返回
                return tResource;
            }
            if(dataType == 1){
                tResource = sbcResourceService.getLastVersionTResourceById(id,caller);
            }
            return tResource;
        } catch (Exception e) {
            LOG.error("fail to getTResourceById. id : {}, caller : {}", id, caller, e);
        }
        return null;
    }

    public static List<TResource> getTResourcesByIds(List<Long> ids, CallerParam caller) {
        try {
            return sbcResourceService.getTResourcesByIds(ids, caller);
        } catch (Exception e) {
            LOG.error("fail to getTResourcesByIds. ids : {}, caller : {}", ids, caller, e);
        }
        return Collections.EMPTY_LIST;
    }

    public static TResourceContent getTResourceContentById(long id, CallerParam caller) {
        try {
            return sbcResourceService.getTResourceContentById(id, caller);
        } catch (Exception e) {
            LOG.error("fail to getTResourceContentById. id : {}, caller : {}", id, caller, e);
        }
        return null;
    }

    public static List<TResourceContent> getTResourceContentsByIds(List<Long> ids, CallerParam caller) {
        try {
            return sbcResourceService.getTResourceContentsByIds(ids, caller);
        } catch (Exception e) {
            LOG.error("fail to getTResourceContentsByIds. ids : {}, caller : {}", ids, caller, e);
        }
        return Collections.EMPTY_LIST;
    }


    public static List<TResourceContent> getOnlineTResourceContentsByIds(List<Long> ids, CallerParam caller) {
        try {
            return sbcResourceService.getOnlineTResourceContentsByIds(ids, caller);
        } catch (Exception e) {
            LOG.error("fail to getTResourceContentsByIds. ids : {}, caller : {}", ids, caller, e);
        }
        return Collections.EMPTY_LIST;
    }
    
    public static List<TResourceContent> getTResourceContentsByResourceId(long resourceId, PageParam page, CallerParam caller,int dataType) {
        try {
            List<Long> ids = null;
            if(dataType == 1){
                ids = sbcResourceService.getOnlineResourceContentIdsByResourceId(resourceId,page,caller);
            }else {
                ids = sbcResourceService.getResourceContentIdsByResourceId(resourceId, page, caller);
            }
            if (CollectionUtils.isEmpty(ids)) {
                return Collections.EMPTY_LIST;
            }
            if(dataType == 1){
               return getOnlineTResourceContentsByIds(ids,caller);
            }else {
                return getTResourceContentsByIds(ids, caller);
            }


        } catch (Exception e) {
            LOG.error("fail to getTResourceContentsByResourceId. resourceId:{}, page : {}, caller : {}", resourceId, page, caller, e);
        }
        return Collections.EMPTY_LIST;
    }
    public static Long countResourceContentByResourceId(long resourceId, CallerParam caller) {
        try {
            return sbcResourceService.countResourceContentByResourceId(resourceId,caller);
        } catch (Exception e) {
            LOG.error("fail to countResourceContentByResourceId. resourceId:{},caller : {}", resourceId, caller, e);
        }
        return -1L;
    }

}
