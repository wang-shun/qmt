package com.lesports.qmt.tlive.thrift;

import com.lesports.LeConstants;
import com.lesports.qmt.model.support.QmtModel;
import com.lesports.qmt.service.support.QmtService;
import com.lesports.qmt.tlive.api.common.UpDownAct;
import com.lesports.qmt.tlive.api.dto.TAnchor;
import com.lesports.qmt.tlive.api.service.TTextLiveInternalService;
import com.lesports.qmt.tlive.model.TextLive;
import com.lesports.qmt.tlive.model.TextLiveImage;
import com.lesports.qmt.tlive.model.TextLiveMessage;
import com.lesports.qmt.tlive.model.Vote;
import com.lesports.qmt.tlive.service.TextLiveImageService;
import com.lesports.qmt.tlive.service.TextLiveService;
import com.lesports.qmt.tlive.service.VoteService;
import com.lesports.qmt.tlive.service.support.TextLiveServiceFactory;
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
import java.util.List;
import java.util.Map;

import static me.ellios.hedwig.http.mediatype.ExtendedMediaType.APPLICATION_X_THRIFT;

/**
 * Created by denghui on 2016/3/23.
 */
@Service("thriftInternalTextLiveService")
@Path("/r/qmt/textLive")
@Produces({APPLICATION_X_THRIFT})
public class TextLiveInternalServiceAdapter implements TTextLiveInternalService.Iface {
    private static final Logger LOG = LoggerFactory.getLogger(TextLiveInternalServiceAdapter.class);

    private Transcoder transcoder = new SerializingTranscoder();

    @Resource
    private TextLiveServiceFactory serviceFactory;

    private <T> List<T> checkIds(List<T> ids) {
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
    public long saveEntity(ByteBuffer bf, ByteBuffer classType) throws TException {
        long id = -1;
        Class clazz = null;
        try {
            clazz = unserialize(classType);
            QmtService textliveService = serviceFactory.getService(clazz);
            QmtModel<Long> entity = unserialize(bf);
            if (entity != null) {
                boolean result = textliveService.save(entity);
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
            QmtService textliveService = serviceFactory.getService(clazz);
            boolean result = textliveService.delete(id);
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
            QmtService textliveService = serviceFactory.getService(clazz);
            Object entity = textliveService.findOne(id);
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
            QmtService textliveService = serviceFactory.getService(clazz);
            List<?> entities = textliveService.findByIds(ids);
            ByteBuffer byteBuffer = serialize(entities);
            LOG.info("get entities by ids. class:{}, ids : {}", clazz, ids);
            return byteBuffer;
        } catch (Exception e) {
            LOG.error("fail to get objects by ids : {}, error : {}", ids, e.getMessage(), e);
        }
        return null;
    }

    @Override
    public ByteBuffer getEntitiesBytesByQuery(ByteBuffer query, ByteBuffer classType) throws TException {
        Class clazz = null;
        InternalQuery internalQuery = null;
        try {
            clazz = unserialize(classType);
            internalQuery = unserialize(query);
            QmtService textliveService = serviceFactory.getService(clazz);
            List<?> entities = textliveService.findByQuery(internalQuery);
            ByteBuffer byteBuffer = serialize(entities);
            LOG.info("get entities by query, class:{}, query:{}, size:{}", clazz, internalQuery, entities.size());
            return byteBuffer;
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
            QmtService textliveService = serviceFactory.getService(clazz);
            return textliveService.countByQuery(internalQuery);
        } catch (Exception e) {
            LOG.error("fail to countByQuery, class:{}, query:{}, error : {}", clazz, internalQuery, e.getMessage(), e);
        }
        return -1;
    }

    @Override
    public boolean setOnlineCount(long eid, int onlineCount) throws TException {
        try {
            TextLiveService textliveService = (TextLiveService) serviceFactory.getService(TextLive.class);
            return textliveService.setOnlineCount(eid, onlineCount);
        } catch (Exception e) {
            LOG.error("fail to setOnlineCount, eid:{}, onlineCount:{}, error : {}",
                    eid, onlineCount, e.getMessage(), e);
        }
        return false;
    }

    @Override
    public boolean setUpDownAnchor(long textLiveId, long anchorId, Map<UpDownAct, Integer> upDownActMap) throws TException {
        try {
            TextLiveService textliveService = (TextLiveService) serviceFactory.getService(TextLive.class);
            return textliveService.setUpDownAnchor(textLiveId, anchorId, upDownActMap);
        } catch (Exception e) {
            LOG.error("fail to setUpDownAnchor, textLiveId:{}, anchorId:{}, map:{}, error : {}",
                    textLiveId, anchorId, upDownActMap, e.getMessage(), e);
        }
        return false;
    }

    @Override
    public boolean sendImageOrVoteMessage(boolean imageOrVote, long id, ByteBuffer liveMessage) throws TException {
        try {
            TextLiveMessage msg = unserialize(liveMessage);
            if (imageOrVote) {
                TextLiveImageService imageService = (TextLiveImageService) serviceFactory.getService(TextLiveImage.class);
                imageService.sendImageMessage(id, msg);
            } else {
                VoteService voteService = (VoteService) serviceFactory.getService(Vote.class);
                voteService.sendVoteMessage(id, msg);
            }
            return true;
        } catch (Exception e) {
            LOG.error("fail to sendImageOrVoteMessage, imageOrVote:{}, id:{}, error : {}",
                    imageOrVote, id, e.getMessage(), e);
        }
        return false;
    }

    @Override
    public long getOnlineCount(long eid) throws TException {
        try {
            TextLiveService textliveService = (TextLiveService) serviceFactory.getService(TextLive.class);
            return textliveService.getOnlineCount(eid);
        } catch (Exception e) {
            LOG.error("fail to getOnlineCount. eid : {}", eid, e);
        }
        return 0L;
    }

    @Override
    public TAnchor getAnchorUpDown(long textLiveId, long anchorId) throws TException {
        try {
            TextLiveService textliveService = (TextLiveService) serviceFactory.getService(TextLive.class);
            return textliveService.getAnchorUpDown(textLiveId, anchorId);
        } catch (Exception e) {
            LOG.error("fail to getAnchorUpDown. textLiveId : {},anchorId : {}", textLiveId, anchorId, e);
        }
        return null;
    }
}
