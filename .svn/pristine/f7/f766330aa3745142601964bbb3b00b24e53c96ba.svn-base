package com.lesports.qmt.transcode.server;

import com.lesports.api.common.Alarm;
import com.lesports.qmt.model.support.QmtModel;
import com.lesports.qmt.service.support.QmtService;
import com.lesports.qmt.transcode.service.TTranscodeInternalService;
import com.lesports.qmt.transcode.service.support.TranscodeServiceFactory;
import com.lesports.query.InternalQuery;
import com.lesports.utils.transcoders.CachedData;
import com.lesports.utils.transcoders.SerializingTranscoder;
import com.lesports.utils.transcoders.Transcoder;
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
 * Created by denghui on 2016/3/23.
 */
@Service("thriftSopsInternalService")
@Path("/r/trans")
@Produces({APPLICATION_X_THRIFT})
public class TTranscodeInternalServiceAdapter implements TTranscodeInternalService.Iface {
    private static final Logger LOG = LoggerFactory.getLogger(TTranscodeInternalServiceAdapter.class);

    private Transcoder transcoder = new SerializingTranscoder();
    @Resource
    private TranscodeServiceFactory serviceFactory;


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
    public long saveEntity(ByteBuffer bf, ByteBuffer classType) throws TException {
        long id = -1;
        Class clazz = unserialize(classType);
        QmtService qmtService = serviceFactory.getService(clazz);
        try {
            QmtModel<Long> model = unserialize(bf);
            qmtService.save(model);
            return model.getId();
        } catch (Exception e) {
            LOG.error("fail to save entity. id : {}, class:{}, error : {}", id, clazz, e.getMessage(), e);
        }
        return id;
    }


    @Override
    public boolean deleteEntity(long id, ByteBuffer classType) throws TException {
        Class clazz = unserialize(classType);
        QmtService qmtService = serviceFactory.getService(clazz);
        try {
            return qmtService.delete(id);
        } catch (Exception e) {
            LOG.error("fail to delete object id : {}, error : {}", id, e.getMessage(), e);
        }
        return false;
    }


    @Override
    public ByteBuffer getEntityBytesById(long id, ByteBuffer classType) throws TException {
        Class clazz = unserialize(classType);
        QmtService qmtService = serviceFactory.getService(clazz);
        try {
            return serialize(qmtService.findOne(id));
        } catch (Exception e) {
            LOG.error("fail to get object by id : {}, error : {}", id, e.getMessage(), e);
        }
        return null;
    }


    @Override
    public ByteBuffer getEntityBytesByIds(List<Long> ids, ByteBuffer classType) throws TException {
        Class clazz = unserialize(classType);
        QmtService qmtService = serviceFactory.getService(clazz);
        try {
            return serialize(qmtService.findByIds(ids));
        } catch (Exception e) {
            LOG.error("fail to get objects by ids : {}, error : {}", ids, e.getMessage(), e);
        }
        return null;
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
        Class clazz = unserialize(classType);
        QmtService qmtService = serviceFactory.getService(clazz);
        InternalQuery internalQuery = unserialize(query);
        try {
            return qmtService.countByQuery(internalQuery);
        } catch (Exception e) {
            LOG.error("fail to countByQuery, class:{}, query:{}, error : {}", clazz, internalQuery, e.getMessage(), e);
        }
        return -1;
    }

    @Override
    public List<Long> getEntityIdsByQuery(ByteBuffer query, ByteBuffer classType) throws TException {
        Class clazz = unserialize(classType);
        QmtService qmtService = serviceFactory.getService(clazz);
        InternalQuery internalQuery = unserialize(query);
        try {
            return qmtService.findIdsByQuery(internalQuery);
        } catch (Exception e) {
            LOG.error("fail to getEntityIdsByQuery, class:{}, query:{}, error : {}", clazz, internalQuery, e.getMessage(), e);
        }
        return Collections.EMPTY_LIST;
    }

    @Override
    public Alarm checkTranscodeStatus() throws TException {
        return null;
    }
}
