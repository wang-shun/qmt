package com.lesports.qmt.transcode.client;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.lesports.LeConstants;
import com.lesports.api.common.Alarm;
import com.lesports.qmt.transcode.model.LiveToVideoSubTask;
import com.lesports.qmt.transcode.model.LiveToVideoTask;
import com.lesports.qmt.transcode.model.VideoTranscodeSubTask;
import com.lesports.qmt.transcode.model.VideoTranscodeTask;
import com.lesports.qmt.transcode.service.TTranscodeInternalService;
import com.lesports.query.InternalCriteria;
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
 * User: ellios
 * Time: 15-6-3 : 下午9:32
 */
public class TranscodeInternalApis {

    private static final Logger LOG = LoggerFactory.getLogger(TranscodeInternalApis.class);

    private static final Transcoder TRANSCODER = new SerializingTranscoder();

    private static TTranscodeInternalService.Iface transcodeInternalService = new ClientBuilder<TTranscodeInternalService.Iface>()
            .serviceType(ServiceType.THRIFT).serviceFace(TTranscodeInternalService.Iface.class).build();

    private static <T> ByteBuffer serialize(T entity) {
        return ByteBuffer.wrap(TRANSCODER.encode(entity).getFullData());
    }

    private static <T> T unserialize(ByteBuffer bf) {
        if (bf == null) {
            LOG.warn("fail to unserialize, bf is null.");
            return null;
        }
        LOG.info("bytes size : {}", TBaseHelper.byteBufferToByteArray(bf).length);
        return TRANSCODER.decode(new CachedData(TBaseHelper.byteBufferToByteArray(bf)));
    }

    private static <T, ID> List<T> doGet(Class<T> clazz, List<ID> ids, BatchGetter<ID> batchGetter) throws TException {
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

    public static <T> long saveEntity(T entity, Class<T> tClass) {
        try {
            ByteBuffer bf = serialize(entity);
            long id = transcodeInternalService.saveEntity(bf, serialize(tClass));
//            LOG.info("Save id : {}, entity : {}.", id, JSONObject.toJSONString(entity));
            return id;
        } catch (TException e) {
            LOG.error("fail to save entity : {}, type : {}. {}", entity, tClass, e.getMessage(), e);
        }
        return -1;
    }

    public static <T> boolean deleteEntity(Long id, Class<T> tClass) {
        if (id != null) {
            try {
                return transcodeInternalService.deleteEntity(id, serialize(tClass));
            } catch (TException e) {
                LOG.error("fail to delete entity. id : {}, type : {}. {}", id, tClass, e.getMessage(), e);
            }
        }
        return false;
    }

    public static <T> T getEntityById(Long id, Class<T> clazz) {
        if (LeNumberUtils.toLong(id) > 0) {
            try {
                LOG.info("try to getEntityById. id : {}, clazz : {}", id, clazz);
                ByteBuffer bf = transcodeInternalService.getEntityBytesById(id, serialize(clazz));
                LOG.info("getEntityById,id:{},clazz:{},bf:{}", id, clazz, bf);
                return unserialize(bf);
            } catch (TException e) {
                LOG.error("fail to getEntityById. id : {}, type : {}. {}", id, clazz, e.getMessage(), e);
            }
        } else {
            LOG.error("fail to getEntityById. id : {}", id);
        }
        return null;
    }

    public static <T> List<T> getEntitiesByIds(List<Long> ids, final Class<T> clazz) {
        try {
            final ByteBuffer byteBuffer = serialize(clazz);
            return doGet(clazz, ids, new BatchGetter<Long>() {
                @Override
                public ByteBuffer get(List<Long> longs) throws TException {
                    return transcodeInternalService.getEntityBytesByIds(longs, byteBuffer);
                }
            });
        } catch (TException e) {
            LOG.error("fail to getEntitiesByIds. ids : {}, type : {}. {}", ids, clazz, e.getMessage(), e);
        }
        return Collections.EMPTY_LIST;
    }

    public static <T> List<T> getEntitiesByQuery(InternalQuery query, Class clazz) {
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
                ByteBuffer entitiesBf = transcodeInternalService.getEntitiesBytesByQuery(paramsBf, serialize(clazz));
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

    public static List<Long> getEntityIdsByQuery(InternalQuery query, Class clazz) {
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
                idPage = transcodeInternalService.getEntityIdsByQuery(paramsBf, serialize(clazz));
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

    public static long countEntitiesByQuery(InternalQuery params, Class clazz) {
        Preconditions.checkNotNull(params);
        try {
            ByteBuffer paramsBf = serialize(params);
            return transcodeInternalService.countByQuery(paramsBf, serialize(clazz));
        } catch (TException e) {
            LOG.error("fail to countEntitiesByQuery. params : {}, type : {}. {}", params, clazz, e.getMessage(), e);
        }
        return -1;
    }

    public static VideoTranscodeTask getTaskByMd5(String md5) {
        InternalQuery internalQuery = new InternalQuery();
        internalQuery.addCriteria(new InternalCriteria("md5", "eq", md5));
        List<VideoTranscodeTask> tasks = getEntitiesByQuery(internalQuery, VideoTranscodeTask.class);
        if (CollectionUtils.isNotEmpty(tasks)) {
            return tasks.get(0);
        }
        return null;
    }

    public static VideoTranscodeTask getVideoTaskByLeecoVid(Long leecoVid) {
        InternalQuery internalQuery = new InternalQuery();
        internalQuery.addCriteria(new InternalCriteria("leecoVid", "eq", leecoVid));
        internalQuery.addCriteria(new InternalCriteria("deleted", "eq", false));
        List<VideoTranscodeTask> tasks = getEntitiesByQuery(internalQuery, VideoTranscodeTask.class);
        if (CollectionUtils.isNotEmpty(tasks)) {
            return tasks.get(0);
        }
        return null;
    }

    public static LiveToVideoTask getLiveToVideoTaskByLeecoVid(Long leecoVid) {
        InternalQuery internalQuery = new InternalQuery();
        internalQuery.addCriteria(new InternalCriteria("leecoVid", "eq", leecoVid));
        internalQuery.addCriteria(new InternalCriteria("deleted", "eq", false));
        List<LiveToVideoTask> tasks = getEntitiesByQuery(internalQuery, LiveToVideoTask.class);
        if (CollectionUtils.isNotEmpty(tasks)) {
            return tasks.get(0);
        }
        return null;
    }

    public static List<VideoTranscodeSubTask> getVideoTranscodeSubTasks(Long taskId) {
        VideoTranscodeTask task = getEntityById(taskId, VideoTranscodeTask.class);
        return task.getSubTasks();
    }

    public static List<LiveToVideoSubTask> getLiveToVideoSubTasks(Long taskId) {
        InternalQuery internalQuery = new InternalQuery();
        internalQuery.addCriteria(new InternalCriteria("parent_id", "eq", taskId));
        return getEntitiesByQuery(internalQuery, LiveToVideoSubTask.class);
    }

    public static LiveToVideoSubTask getLiveToVideoSubTask(Long taskId, Integer format) {
        InternalQuery internalQuery = new InternalQuery();
        internalQuery.addCriteria(new InternalCriteria("parent_id", "eq", taskId));
        internalQuery.addCriteria(new InternalCriteria("format", "eq", format));
        List<LiveToVideoSubTask> subTasks = getEntitiesByQuery(internalQuery, LiveToVideoSubTask.class);
        if (CollectionUtils.isNotEmpty(subTasks)) {
            return subTasks.get(0);
        }
        return null;
    }

    public static Alarm checkTranscodeStatus() {
        try {
            return transcodeInternalService.checkTranscodeStatus();
        } catch (TException e) {
            LOG.error("{}", e.getMessage(), e);
        }
        return null;
    }
}
