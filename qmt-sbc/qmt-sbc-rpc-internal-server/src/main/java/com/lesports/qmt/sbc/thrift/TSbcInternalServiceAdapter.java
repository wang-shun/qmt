package com.lesports.qmt.sbc.thrift;

import com.lesports.LeConstants;
import com.lesports.qmt.model.support.QmtModel;
import com.lesports.qmt.sbc.api.service.SaveResult;
import com.lesports.qmt.sbc.api.service.TSbcInternalService;
import com.lesports.qmt.sbc.model.Live;
import com.lesports.qmt.sbc.service.EpisodeService;
import com.lesports.qmt.sbc.service.LiveService;
import com.lesports.qmt.sbc.service.VideoService;
import com.lesports.qmt.sbc.service.support.SbcServiceFactory;
import com.lesports.qmt.service.support.QmtService;
import com.lesports.query.InternalQuery;
import com.lesports.utils.math.LeNumberUtils;
import com.lesports.utils.transcoders.CachedData;
import com.lesports.utils.transcoders.SerializingTranscoder;
import com.lesports.utils.transcoders.Transcoder;
import me.ellios.hedwig.common.exceptions.HedwigException;
import org.apache.commons.collections.CollectionUtils;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.List;

import static me.ellios.hedwig.http.mediatype.ExtendedMediaType.APPLICATION_X_THRIFT;

/**
 * User: ellios
 * Time: 15-5-17 : 下午4:13
 */
@Service("thriftSbcInternalService")
@Path("/r/sbc")
@Produces({APPLICATION_X_THRIFT})
public class TSbcInternalServiceAdapter implements TSbcInternalService.Iface {

    private static final Logger LOG = LoggerFactory.getLogger(TSbcInternalServiceAdapter.class);

    private Transcoder transcoder = new SerializingTranscoder();

    @Resource
    private SbcServiceFactory serviceFactory;
    @Inject
    private EpisodeService episodeService;
    @Resource
    private LiveService liveService;
    @Resource
    private VideoService videoService;

    private List<Long> checkIds(List<Long> ids) {
        if (CollectionUtils.isNotEmpty(ids) && ids.size() > LeConstants.MAX_BATCH_SIZE) {
            ids = ids.subList(0, LeConstants.MAX_BATCH_SIZE);
        }
        return ids;
    }

    private <T> T unserialize(ByteBuffer bf) {
        return transcoder.decode(new CachedData(bf.array()));
    }

    private <T> ByteBuffer serialize(T entity) {
        if (entity == null) {
            return null;
        }
        return ByteBuffer.wrap(transcoder.encode(entity).getFullData());
    }

    @Override
    public SaveResult saveEntityWithResult(ByteBuffer bf, ByteBuffer classType, boolean allowEmpty) throws TException {
        SaveResult saveResult = new SaveResult();
        try {
            long id = saveEntity(bf, classType, allowEmpty);
            if (id > 0) {
                saveResult.setSuccess(true);
            } else {
                saveResult.setSuccess(false);
            }
            saveResult.setId(id);
        } catch (HedwigException e) {
            saveResult.setSuccess(false);
            saveResult.setMsg(e.getMessage());
        }
        return saveResult;
    }

    @Override
    public long saveEntity(ByteBuffer bf, ByteBuffer classType, boolean allowEmpty) throws TException {
        long id = -1;
        Class clazz = null;
        try {
            clazz = unserialize(classType);
            QmtService sbcService = serviceFactory.getService(clazz);
            QmtModel<Long> entity = unserialize(bf);
            if (entity != null) {
                boolean result = sbcService.save(entity, allowEmpty);
                id = LeNumberUtils.toLong(entity.getId());
                LOG.info("save entity. class:{}, id : {}, result : {}", clazz, id, result);
            } else {
                LOG.warn("unserialized to null of class:{}", clazz);
            }
        } catch (HedwigException e) {
            throw e;
        } catch (Exception e) {
            LOG.error("fail to save entity. id : {}, class:{}, error : {}", id, clazz, e.getMessage(), e);
        }
        return id;
    }

    @Override
    public boolean deleteEntity(long id, ByteBuffer classType) throws TException {
        try {
            Class clazz = unserialize(classType);
            QmtService sbcService = serviceFactory.getService(clazz);
            boolean result = sbcService.delete(id);
            LOG.info("delete entity. class:{}, id : {}, result : {}", clazz, id, result);
            return result;
        } catch (Exception e) {
            LOG.error("fail to delete object id : {}, error : {}", id, e.getMessage(), e);
        }
        return false;
    }

    @Override
    public boolean schedulerUpdateLiveStatus() throws TException {
        try {
            liveService.updateLiveStatus();
            return true;
        } catch (Exception e) {
            LOG.error("fail to update entity. class:{}, error : {}", Live.class, e.getMessage(), e);
        }
        return false;
    }

    @Override
    public boolean cloneVideoToAlbum(long videoId, List<Long> albumIds) throws TException {
        try {
            return videoService.cloneVideoToAlbum(videoId, albumIds);
        } catch (Exception e) {
            LOG.error("fail to update entity. class:{}, error : {}", Live.class, e.getMessage(), e);
        }
        return false;
    }

    @Override
    public ByteBuffer getEntityBytesById(long id, ByteBuffer classType) throws TException {
        try {
            Class clazz = unserialize(classType);
            QmtService sbcService = serviceFactory.getService(clazz);
            Object entity = sbcService.findOne(id);
            ByteBuffer byteBuffer = serialize(entity);
            LOG.info("get entity by id class:{}, id : {},entity={}", clazz, id, entity);
            return byteBuffer;
        } catch (Exception e) {
            LOG.error("fail to get object by id : {}, error : {}", id, e.getMessage(), e);
        }
        return null;
    }

    @Override
    public ByteBuffer getEntityBytesById2(String id, ByteBuffer classType) throws TException {
        return null;
    }

    @Override
    public ByteBuffer getEntityBytesByIds(List<Long> ids, ByteBuffer classType) throws TException {
        try {
            ids = checkIds(ids);
            Class clazz = unserialize(classType);
            QmtService sbcService = serviceFactory.getService(clazz);
            List<?> entities = sbcService.findByIds(ids);
            ByteBuffer byteBuffer = serialize(entities);
            LOG.info("get entities by ids. class:{}, ids : {}", clazz, ids);
            return byteBuffer;
        } catch (Exception e) {
            LOG.error("fail to get objects by ids : {}, error : {}", ids, e.getMessage(), e);
        }
        return null;
    }

    @Override
    public List<Long> getEntityIdsByQuery(ByteBuffer query, ByteBuffer classType) throws TException {
        Class clazz = null;
        InternalQuery internalQuery = null;
        try {
            clazz = unserialize(classType);
            internalQuery = unserialize(query);
            QmtService sbcService = serviceFactory.getService(clazz);
            List<Long> ids = sbcService.findIdsByQuery(internalQuery);
            LOG.info("get entity ids by query, class:{}, query:{}, size:{}", clazz, internalQuery, ids.size());
            return ids;
        } catch (Exception e) {
            LOG.error("fail to getEntityIdsByQuery, class:{}, query:{}, error : {}", clazz, internalQuery, e.getMessage(), e);
        }
        return Collections.EMPTY_LIST;
    }

    @Override
    public ByteBuffer getEntitiesBytesByQuery(ByteBuffer query, ByteBuffer classType) throws TException {
        Class clazz = unserialize(classType);
        QmtService qmtService = serviceFactory.getService(clazz);
        InternalQuery internalQuery = unserialize(query);
        try {
            return serialize(qmtService.findByQuery(internalQuery));
        } catch (Exception e) {
            LOG.error("fail to getEntitiesBytesByQuery, class:{}, query:{}, error : {}", clazz, internalQuery, e.getMessage(), e);
        }
        return null;
    }

    @Override
    public long countByQuery(ByteBuffer query, ByteBuffer classType) throws TException {
        Class clazz = null;
        InternalQuery internalQuery = null;
        try {
            clazz = unserialize(classType);
            internalQuery = unserialize(query);
            QmtService sbcService = serviceFactory.getService(clazz);
            return sbcService.countByQuery(internalQuery);
        } catch (Exception e) {
            LOG.error("fail to countByQuery, class:{}, query:{}, error : {}", clazz, internalQuery, e.getMessage(), e);
        }
        return -1;
    }

    @Override
    public List<Long> createEpisodes(long mid) throws TException {
        try {
            return episodeService.createEpisodes(mid);
        } catch (Exception e) {
            LOG.error("fail to create episodes. match id : {}", mid, e);
        }
        return Collections.emptyList();
    }

    @Override
    public boolean changeOrder(long id, int increment, ByteBuffer classType) throws TException {
        Class clazz = unserialize(classType);
        QmtService qmtService = serviceFactory.getService(clazz);
        return qmtService.changeOrder(id,increment);
    }


}
