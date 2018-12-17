package com.lesports.qmt.web.api.core.service.impl;

import com.google.common.cache.LoadingCache;
import com.google.common.collect.Lists;
import com.lesports.api.common.CallerParam;
import com.lesports.api.common.Platform;
import com.lesports.qmt.config.api.dto.TCaller;
import com.lesports.qmt.config.client.QmtConfigApis;
import com.lesports.qmt.web.api.core.util.CollectionTools;
import com.lesports.qmt.web.api.core.vo.GetRealtimeCacheParam;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * aa.
 *
 * @author pangchuanxiao
 * @since 2016/3/9
 */
public abstract class AbstractService {
    private static final Logger LOG = LoggerFactory.getLogger(AbstractService.class);

    /**
     * 通过ids批量获取数据 本地缓存--》cbase--》rpc构造
     *
     * @param cache
     * @param ids
     * @param <O>
     * @return
     */
    <O> List<O> get(LoadingCache<GetRealtimeCacheParam, O> cache, String ids, CallerParam callerBean) {
        List<Long> idList = CollectionTools.string2List(ids);
        List<O> returnList = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(idList)) {
            for (Long id : idList) {
                O resultObj = getFromCache(cache, id, callerBean);
                if (null != resultObj) {
                    returnList.add(resultObj);
                }
            }
            return returnList;
        }
        return Collections.EMPTY_LIST;
    }

    <O> O getFromCache(LoadingCache<GetRealtimeCacheParam, O> cache, Long id, CallerParam callerBean) {
        O resultObj = null;
        try {
            resultObj = cache.get(new GetRealtimeCacheParam(id, callerBean));
        } catch (ExecutionException e) {
            LOG.error("{}", e.getMessage(), e);
        } catch (Exception e) {
            LOG.warn("fail to get data by id : {}.", id);
        }
        return resultObj;
    }

    Platform getPlatFormFromCaller(long callerId) {
        TCaller tCaller = QmtConfigApis.getTCallerById(callerId);
        if (null == tCaller) {
            throw new IllegalArgumentException("illegal callerId : " + callerId);
        }
        return tCaller.getPlatform();
    }
}
