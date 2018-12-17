package com.lesports.qmt.tlive;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.lesports.LeConstants;
import com.lesports.qmt.tlive.api.common.UpDownAct;
import com.lesports.qmt.tlive.api.dto.TAnchor;
import com.lesports.qmt.tlive.api.service.TTextLiveInternalService;
import com.lesports.qmt.tlive.model.TextLive;
import com.lesports.qmt.tlive.model.TextLiveImage;
import com.lesports.qmt.tlive.model.TextLiveMessage;
import com.lesports.qmt.tlive.model.Vote;
import com.lesports.query.InternalCriteria;
import com.lesports.query.InternalQuery;
import com.lesports.utils.PageUtils;
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
import java.util.Map;

/**
 * User: ellios
 * Time: 15-6-3 : 下午9:32
 */
public class TextLiveInternalApis {

    private static final Logger LOG = LoggerFactory.getLogger(TextLiveInternalApis.class);

    private static final Transcoder TRANSCODER = new SerializingTranscoder();

    private static TTextLiveInternalService.Iface textLiveService = new ClientBuilder<TTextLiveInternalService.Iface>()
            .serviceType(ServiceType.THRIFT).serviceFace(TTextLiveInternalService.Iface.class).build();

    public static TTextLiveInternalService.Iface getTextLiveService() {
        return textLiveService;
    }

    private static <T> ByteBuffer serialize(T entity) {
        return ByteBuffer.wrap(TRANSCODER.encode(entity).getFullData());
    }

    private static <T> T unserialize(ByteBuffer bf) {
        if (bf == null) {
            return null;
        }
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

    private static <T> long saveEntity(T entity, Class<T> tClass) {
        try {
            ByteBuffer bf = serialize(entity);
            return textLiveService.saveEntity(bf, serialize(tClass));
        } catch (TException e) {
            LOG.error("fail to save entity : {}, type : {}. {}", entity, tClass, e.getMessage(), e);
        }
        return -1;
    }

    public static TAnchor getAnchorUpDown(long textLiveId, long anchorId) {
        try {
            return textLiveService.getAnchorUpDown(textLiveId, anchorId);
        } catch (TException e) {
            LOG.error("fail to getAnchorUpDown. textLiveId : {},anchorId : {}", textLiveId, anchorId, e);
        }
        return null;
    }

    public static long getOnlineCount(long eid) {
        try {
            return textLiveService.getOnlineCount(eid);
        } catch (TException e) {
            LOG.error("fail to getOnlineCount. eid : {}", eid, e);
        }
        return 0L;
    }

    private static <T> boolean deleteEntity(long id, Class<T> tClass) {
        try {
            return textLiveService.deleteEntity(id, serialize(tClass));
        } catch (TException e) {
            LOG.error("fail to delete entity. id : {}, type : {}. {}", id, tClass, e.getMessage(), e);
        }
        return false;
    }

    private static <T> T getEntityById(long id, Class<T> clazz) {
        try {
            ByteBuffer bf = textLiveService.getEntityBytesById(id, serialize(clazz));
            return unserialize(bf);
        } catch (TException e) {
            LOG.error("fail to getEntityById. id : {}, type : {}. {}", id, clazz, e.getMessage(), e);
        }
        return null;
    }

    private static <T> List<T> getEntitiesByIds(List<Long> ids, final Class<T> clazz) {
        try {
            final ByteBuffer byteBuffer = serialize(clazz);
            return doGet(clazz, ids, new BatchGetter<Long>() {
                @Override
                public ByteBuffer get(List<Long> longs) throws TException {
                    return textLiveService.getEntityBytesByIds(longs, byteBuffer);
                }
            });
        } catch (TException e) {
            LOG.error("fail to getEntitiesByIds. ids : {}, type : {}. {}", ids, clazz, e.getMessage(), e);
        }
        return Collections.EMPTY_LIST;
    }

    private static <T> List<T> getEntitiesByQuery(InternalQuery query, Class clazz) {
        Preconditions.checkNotNull(query);
        List<T> result = Lists.newArrayList();
        List<T> entityPage = null;
        int pageSize = query.getPageable() == null?Integer.MAX_VALUE:query.getPageable().getPageSize();
        if (query.getLimit() > 0) {
            pageSize = Math.min(pageSize, query.getLimit());
        }
        try {
            do {
                query.with(PageUtils.getValidPageable(query.getPageable()));
                int page = query.getPageable().getPageNumber();
                ByteBuffer paramsBf = serialize(query);
                ByteBuffer entitiesBf = textLiveService.getEntitiesBytesByQuery(paramsBf, serialize(clazz));
                entityPage = unserialize(entitiesBf);
                if (CollectionUtils.isNotEmpty(entityPage)) {
                    result.addAll(entityPage);
                }
                if (result.size() >= pageSize) {
                    result = result.subList(0,pageSize);
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

    private static long countEntitiesByQuery(InternalQuery params, Class clazz) {
        Preconditions.checkNotNull(params);
        try {
            ByteBuffer paramsBf = serialize(params);
            return textLiveService.countByQuery(paramsBf, serialize(clazz));
        } catch (TException e) {
            LOG.error("fail to countEntitiesByQuery. params : {}, type : {}. {}", params, clazz, e.getMessage(), e);
        }
        return -1;
    }

    public static long saveTextLiveMessage(TextLiveMessage textLiveMessage) {
        return saveEntity(textLiveMessage, TextLiveMessage.class);
    }

    public static TextLive getTextLiveById(Long id) {
        return getEntityById(id, TextLive.class);
    }

    public static long saveTextLive(TextLive entity) {
        return saveEntity(entity,TextLive.class);
    }

    public static boolean deleteTextLive(Long id) {
        return deleteEntity(id, TextLive.class);
    }

    public static List<TextLive> getTextLivesByQuery(InternalQuery query) {
        return getEntitiesByQuery(query,TextLive.class);
    }

    public static long countTextLivesByQuery(InternalQuery query) {
        return countEntitiesByQuery(query,TextLive.class);
    }


    public static TextLiveImage getTextLiveImageById(Long id) {
        return getEntityById(id, TextLiveImage.class);
    }

    public static long saveTextLiveImage(TextLiveImage entity) {
        return saveEntity(entity,TextLiveImage.class);
    }

    public static TextLiveMessage getTextLiveMessageById(Long id) {
        return getEntityById(id, TextLiveMessage.class);
    }

    public static boolean deleteTextLiveMessage(Long id) {
        return deleteEntity(id, TextLiveMessage.class);
    }

    public static long countTextLiveMessagesByQuery(InternalQuery query) {
        return countEntitiesByQuery(query,TextLiveMessage.class);
    }

    public static List<TextLiveMessage> getTextLiveMessagesByQuery(InternalQuery query) {
        return getEntitiesByQuery(query,TextLiveMessage.class);
    }

    public static long saveVote(Vote entity) {
        return saveEntity(entity, Vote.class);
    }

    public static TextLive getPBPTTextLiveByEid(long eid) {
        InternalQuery query = new InternalQuery();
        query.addCriteria(new InternalCriteria("eid", "eq", eid));
        query.addCriteria(new InternalCriteria("type", "eq", "PLAY_BY_PLAY"));
        List<TextLive> lives = getEntitiesByQuery(query, TextLive.class);
        if (CollectionUtils.isEmpty(lives)) {
            return null;
        }
        return lives.get(0);
    }

    public static TextLiveMessage getLiveMessagesByLiveIdAndPartnerId(long textLiveId, String partnerId) {
        InternalQuery query = new InternalQuery();
        query.addCriteria(new InternalCriteria("text_live_id", "eq", textLiveId));
        query.addCriteria(new InternalCriteria("partner_id", "eq", partnerId));
        List<TextLiveMessage> ids = getEntitiesByQuery(query, TextLiveMessage.class);
        if (CollectionUtils.isEmpty(ids)) {
            return null;
        }
        return ids.get(0);
    }

    public static void setUpDownAnchor(long textLiveId, Long anchorId, Map<UpDownAct, Integer> upDownActMap) {
        try {
            textLiveService.setUpDownAnchor(textLiveId,anchorId,upDownActMap);
        } catch (TException e) {
            LOG.error("fail to setUpDownAnchor. textLiveId : {}, anchorId : {}, map:{}. {}",
                    textLiveId, anchorId, upDownActMap,e.getMessage(), e);
        }
    }

    public static void setOnlineCount(long eid, int value) {
        try {
            textLiveService.setOnlineCount(eid, value);
        } catch (TException e) {
            LOG.error("fail to setUpDownAnchor. eid : {}, value : {}. {}", eid, value, e.getMessage(), e);
        }
    }


    public static void sendImageOrVoteMessage(boolean imageOrVote, Long id, TextLiveMessage liveMessage) {
        try {
            // true: image, false: vote
            textLiveService.sendImageOrVoteMessage(imageOrVote, id, serialize(liveMessage));
        } catch (TException e) {
            LOG.error("fail to sendImageOrVoteMessage. imageOrVote:{}, id : {}, liveMessage : {}. {}",
                    imageOrVote, id, liveMessage, e.getMessage(), e);
        }
    }

}
