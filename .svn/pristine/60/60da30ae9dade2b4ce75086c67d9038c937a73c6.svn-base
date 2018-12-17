package com.lesports.qmt.sbc.client;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.lesports.LeConstants;
import com.lesports.qmt.model.support.QmtModel;
import com.lesports.qmt.sbc.api.service.SaveResult;
import com.lesports.qmt.sbc.api.service.TSbcInternalService;
import com.lesports.query.InternalQuery;
import com.lesports.utils.PageUtils;
import com.lesports.utils.math.LeNumberUtils;
import com.lesports.utils.transcoders.CachedData;
import com.lesports.utils.transcoders.SerializingTranscoder;
import com.lesports.utils.transcoders.Transcoder;
import me.ellios.hedwig.rpc.client.ClientBuilder;
import me.ellios.hedwig.rpc.core.ServiceType;
import org.apache.commons.collections.CollectionUtils;
import org.apache.thrift.TBaseHelper;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.List;

/**
 * Created by denghui on 2016/10/24.
 */
public class QmtSbcInternalApis {

    protected static final Logger LOG = LoggerFactory.getLogger(QmtSbcInternalApis.class);

    protected static final Transcoder TRANSCODER = new SerializingTranscoder();

    protected static TSbcInternalService.Iface sbcInternalService = new ClientBuilder<TSbcInternalService.Iface>()
            .serviceType(ServiceType.THRIFT).serviceFace(TSbcInternalService.Iface.class).build();

    protected static <T> ByteBuffer serialize(T entity) {
        return ByteBuffer.wrap(TRANSCODER.encode(entity).getFullData());
    }

    protected static <T> T unserialize(ByteBuffer bf) {
        if (bf == null) {
            return null;
        }
        return TRANSCODER.decode(new CachedData(TBaseHelper.byteBufferToByteArray(bf)));
    }

    protected static <T, ID> List<T> doGet(Class<T> clazz, List<ID> ids, BatchGetter<ID> batchGetter) throws TException {
        if (CollectionUtils.isEmpty(ids)) {
            LOG.warn("fail to getByIds. ids empty. ids : {}", ids);
            return Collections.EMPTY_LIST;
        }

        int size = ids.size();
        List<T> results = Lists.newArrayListWithCapacity(size);

        int toIndex = LeConstants.MAX_BATCH_SIZE;
        for (int i = 0; i < size; i += LeConstants.MAX_BATCH_SIZE, toIndex += LeConstants.MAX_BATCH_SIZE) {
            if (size < toIndex) {
                toIndex = size;
            }
            List<ID> batchIds = ids.subList(i, toIndex);

            ByteBuffer bf = batchGetter.get(batchIds);
            if (bf == null) {
                continue;
            }
            List<T> batchEntities = unserialize(bf);
            if (CollectionUtils.isNotEmpty(batchEntities)) {
                results.addAll(batchEntities);
            }
            LOG.info("success to getByIds for {}. id size : {}", clazz, batchIds.size());
        }

        return results;
    }

    interface BatchGetter<ID> {
        ByteBuffer get(List<ID> ids) throws TException;
    }

    protected static <T> long saveEntity(T entity, Class<T> tClass) {
        return saveEntity(entity, tClass, false);
    }

    protected static <T> long saveEntity(T entity, Class<T> tClass, boolean allowEmpty) {
        try {
            ByteBuffer bf = serialize(entity);
            return LeNumberUtils.toLong(sbcInternalService.saveEntity(bf, serialize(tClass), allowEmpty));
        } catch (NullPointerException e) {
        } catch (TException e) {
            LOG.error("fail to save entity : {}, type : {}. {}", entity, tClass, e.getMessage(), e);
        }
        return -1;
    }

    protected static <T> SaveResult saveEntityWithResult(T entity, Class<T> tClass, boolean allowEmpty) {
        SaveResult saveResult = new SaveResult();
        saveResult.setSuccess(false);
        try {
            ByteBuffer bf = serialize(entity);
            return sbcInternalService.saveEntityWithResult(bf, serialize(tClass), allowEmpty);
        } catch (TException e) {
            saveResult.setMsg(e.getMessage());
            LOG.error("fail to save entity : {}, type : {}. {}", entity, tClass, e.getMessage(), e);
        }

        return saveResult;
    }

    protected static <T> boolean deleteEntity(Long id, Class<T> tClass) {
        if (id != null) {
            try {
                return LeNumberUtils.toBoolean(sbcInternalService.deleteEntity(id, serialize(tClass)));
            } catch (NullPointerException e) {
            } catch (TException e) {
                LOG.error("fail to delete entity. id : {}, type : {}. {}", id, tClass, e.getMessage(), e);
            }
        }
        return false;
    }

    protected static <T> T getEntityById(Long id, Class<T> clazz) {
        if (id != null) {
            try {
                ByteBuffer bf = sbcInternalService.getEntityBytesById(id, serialize(clazz));
                return unserialize(bf);
            } catch (TException e) {
                LOG.error("fail to getEntityById. id : {}, type : {}. {}", id, clazz, e.getMessage(), e);
            }
        }
        return null;
    }

    protected static <T> List<T> getEntitiesByIds(List<Long> ids, final Class<T> clazz) {
        try {
            final ByteBuffer byteBuffer = serialize(clazz);
            return doGet(clazz, ids, new BatchGetter<Long>() {
                @Override
                public ByteBuffer get(List<Long> longs) throws TException {
                    return sbcInternalService.getEntityBytesByIds(longs, byteBuffer);
                }
            });
        } catch (TException e) {
            LOG.error("fail to getEntitiesByIds. ids : {}, type : {}. {}", ids, clazz, e.getMessage(), e);
        }
        return Collections.EMPTY_LIST;
    }

    protected static <T extends QmtModel> List<T> getEntitiesByQuery(InternalQuery query, Class clazz) {
        Preconditions.checkNotNull(query);
        List<Long> ids = getEntityIdsByQuery(query, clazz);
        if (CollectionUtils.isEmpty(ids)) {
            return Collections.EMPTY_LIST;
        }
        List<T> entities = getEntitiesByIds(ids, clazz);
        if ((query.getPageable() != null && query.getPageable().getSort() != null) || query.getSort() != null) {
            //getByIds不保证顺序
            Collections.sort(entities, (o1, o2) -> ids.indexOf(o1.getId()) - ids.indexOf(o2.getId()));
        }
        return entities;
    }

    protected static List<Long> getEntityIdsByQuery(InternalQuery query, Class clazz) {
        Preconditions.checkNotNull(query);
        List<Long> result = Lists.newArrayList();
        List<Long> idPage = null;
        int pageSize = query.getPageable() == null ? Integer.MAX_VALUE : query.getPageable().getPageSize();
        if (query.getLimit() > 0) {
            pageSize = Math.min(pageSize, query.getLimit());
        }
        try {
            do {
                query.with(PageUtils.getValidPageable(query.getPageable()));
                int page = query.getPageable().getPageNumber();
                ByteBuffer paramsBf = serialize(query);
                idPage = sbcInternalService.getEntityIdsByQuery(paramsBf, serialize(clazz));
                if (CollectionUtils.isNotEmpty(idPage)) {
                    result.addAll(idPage);
                }
                if (result.size() >= pageSize) {
                    result = result.subList(0, pageSize);
                    break;
                }
                Pageable valid = new PageRequest(++page, query.getPageable().getPageSize(), query.getPageable().getSort());
                query.with(valid);
            } while (CollectionUtils.isNotEmpty(idPage));
        } catch (TException e) {
            LOG.error("fail to getEntitiesByQuery. query : {}, type : {}. {}", query, clazz, e.getMessage(), e);
        }

        return result;
    }

    public static <T> List<T> getEntitiesByQuery2(InternalQuery query, Class clazz) {
        Preconditions.checkNotNull(query);
        List<T> result = Lists.newArrayList();
        List<T> entityPage = null;
        int pageSize = query.getPageable() == null ? Integer.MAX_VALUE : query.getPageable().getPageSize();
        if (query.getLimit() > 0) {
            pageSize = Math.min(pageSize, query.getLimit());
        }
        try {
            do {
                query.with(PageUtils.getValidPageable(query.getPageable()));
                int page = query.getPageable().getPageNumber();
                ByteBuffer paramsBf = serialize(query);
                ByteBuffer entitiesBf = sbcInternalService.getEntitiesBytesByQuery(paramsBf, serialize(clazz));
                entityPage = unserialize(entitiesBf);
                if (CollectionUtils.isNotEmpty(entityPage)) {
                    result.addAll(entityPage);
                }
                if (result.size() >= pageSize) {
                    result = result.subList(0, pageSize);
                    break;
                }
                Pageable valid = new PageRequest(++page, query.getPageable().getPageSize(), query.getPageable().getSort());
                query.with(valid);
            } while (CollectionUtils.isNotEmpty(entityPage));
        } catch (TException e) {
            LOG.error("fail to getEntitiesByQuery. query : {}, type : {}. {}", query, clazz, e.getMessage(), e);
        }
        return result;
    }

    /**
     * 根据条件查询数量
     *
     * @param params
     * @param clazz
     * @return
     */
    public static long countByQuery(InternalQuery params, Class clazz) {
        Preconditions.checkNotNull(params);
        try {
            ByteBuffer paramsBf = serialize(params);
            return sbcInternalService.countByQuery(paramsBf, serialize(clazz));
        } catch (NullPointerException e) {
        } catch (TException e) {
            LOG.error("fail to countByQuery. params : {}, type : {}. {}", params, clazz, e.getMessage(), e);
        }
        return -1;
    }

    public static boolean schedulerUpdateLiveStatus() {
        try {
            return sbcInternalService.schedulerUpdateLiveStatus();
        } catch (NullPointerException e) {
        } catch (TException e) {
            LOG.error("fail to update entity. class:Live, error : {}", e.getMessage(), e);
        }
        return false;
    }

    public static boolean cloneVideoToAlbum(long videoId, List<Long> albumIds) {
        try {
            return sbcInternalService.cloneVideoToAlbum(videoId, albumIds);
        } catch (TException e) {
            LOG.error("fail to clone video to album. class:Video, error : {}", e.getMessage(), e);
        }
        return false;
    }

    public static boolean changeOrder(long id,int increment, Class clazz) {
        try {
            return sbcInternalService.changeOrder(id,increment,serialize(clazz));
        } catch (TException e) {
            LOG.error("fail to clone video to album. class:Video, error : {}", e.getMessage(), e);
        }
        return false;
    }
}
