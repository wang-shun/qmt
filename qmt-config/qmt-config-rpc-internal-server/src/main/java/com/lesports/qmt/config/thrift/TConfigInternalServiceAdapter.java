package com.lesports.qmt.config.thrift;

import com.lesports.LeConstants;
import com.lesports.qmt.config.api.service.TConfigInternalService;
import com.lesports.qmt.config.model.Menu;
import com.lesports.qmt.config.service.MenuService;
import com.lesports.qmt.config.service.support.ConfigServiceFactory;
import com.lesports.qmt.model.support.QmtModel;
import com.lesports.qmt.service.support.QmtService;
import com.lesports.query.InternalQuery;
import com.lesports.utils.math.LeNumberUtils;
import com.lesports.utils.transcoders.CachedData;
import com.lesports.utils.transcoders.SerializingTranscoder;
import com.lesports.utils.transcoders.Transcoder;
import org.apache.commons.collections.CollectionUtils;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
@Service("thriftConfigInternalService")
@Path("/r/config")
@Produces({APPLICATION_X_THRIFT})
public class TConfigInternalServiceAdapter implements TConfigInternalService.Iface {

    private static final Logger LOG = LoggerFactory.getLogger(TConfigInternalServiceAdapter.class);

    private Transcoder transcoder = new SerializingTranscoder();

    @Resource
    private ConfigServiceFactory serviceFactory;

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
    public ByteBuffer getEntityBytesById(long id, ByteBuffer classType) throws TException {
        try {
            Class clazz = unserialize(classType);
            QmtService sbcService = serviceFactory.getService(clazz);
            Object entity = sbcService.findOne(id);
            ByteBuffer byteBuffer = serialize(entity);
            LOG.info("get entity by id. class:{}, id : {}", clazz, id);
            return byteBuffer;
        } catch (Exception e) {
            LOG.error("fail to get object by id : {}, error : {}", id, e.getMessage(), e);
        }
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
    public boolean publishMenu(long id) throws TException {
        try {
            MenuService menuService = (MenuService) serviceFactory.getService(Menu.class);
            return menuService.publishMenu(id);
        } catch (Exception e) {
            LOG.error("fail to publishMenu, id:{}, error:{}", id, e.getMessage(), e);
        }
        return false;
    }

}
