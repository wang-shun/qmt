package com.lesports.qmt.tlive.service.impl;

import com.google.common.collect.Lists;
import com.lesports.api.common.LiveShowStatus;
import com.lesports.id.api.IdType;
import com.lesports.id.client.LeIdApis;
import com.lesports.mongo.repository.MongoCrudRepository;
import com.lesports.qmt.QmtConstants;
import com.lesports.qmt.QmtOperationType;
import com.lesports.qmt.msg.core.ActionType;
import com.lesports.qmt.msg.core.LeMessage;
import com.lesports.qmt.msg.core.LeMessageBuilder;
import com.lesports.qmt.msg.producer.SwiftMessageApis;
import com.lesports.qmt.sbc.client.QmtSbcEpisodeApis;
import com.lesports.qmt.sbc.client.QmtSbcEpisodeInternalApis;
import com.lesports.qmt.sbc.model.Episode;
import com.lesports.qmt.tlive.api.common.TextLiveType;
import com.lesports.qmt.tlive.api.common.UpDownAct;
import com.lesports.qmt.tlive.api.dto.TAnchor;
import com.lesports.qmt.tlive.api.dto.TTextLive;
import com.lesports.qmt.tlive.cache.TTextLiveCache;
import com.lesports.qmt.tlive.converter.TextLiveVoCreator;
import com.lesports.qmt.tlive.model.TextLive;
import com.lesports.qmt.tlive.repository.TextLiveRepository;
import com.lesports.qmt.tlive.service.TextLiveService;
import com.lesports.qmt.tlive.service.support.AbstractTextLiveService;
import com.lesports.utils.CallerUtils;
import com.lesports.utils.LeDateUtils;
import com.lesports.utils.math.LeNumberUtils;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;
import static org.springframework.data.mongodb.core.query.Update.update;

/**
 * Created by lufei1 on 2015/9/14.
 */
@Service("textLiveService")
public class TextLiveServiceImpl extends AbstractTextLiveService<TextLive, Long> implements TextLiveService {

    private static final Logger LOG = LoggerFactory.getLogger(TextLiveServiceImpl.class);


    @Resource
    private TextLiveRepository textLiveRepository;
    @Resource
    private TTextLiveCache textLiveCache;
    @Resource
    private TextLiveVoCreator textLiveVoCreator;


    @Override
    protected QmtOperationType getOpreationType(TextLive entity) {
        if (LeNumberUtils.toLong(entity.getId()) > 0) {
            return QmtOperationType.UPDATE;
        }
        return QmtOperationType.CREATE;
    }

    @Override
    protected boolean doCreate(TextLive entity) {
        entity.setId(LeIdApis.nextId(IdType.TEXT_LIVE));
        entity.setDeleted(false);
        return textLiveRepository.save(entity);
    }

    @Override
    protected boolean doAfterCreate(TextLive entity) {
        addTextLiveToEpisode(entity);
        //给消息中心发送消息
        LeMessage message = LeMessageBuilder.create().setEntityId(entity.getId())
                .setIdType(IdType.TEXT_LIVE).setBusinessTypes(ActionType.UPDATE, null).build();
        SwiftMessageApis.sendMsgAsync(message);
        return true;
    }

    @Override
    protected boolean doUpdate(TextLive entity) {
        return textLiveRepository.save(entity);
    }

    @Override
    protected boolean doAfterUpdate(TextLive entity) {
        textLiveCache.delete(entity.getId());
        addTextLiveToEpisode(entity);
        //给消息中心发送消息
        LeMessage message = LeMessageBuilder.create().setEntityId(entity.getId())
                .setIdType(IdType.TEXT_LIVE).setBusinessTypes(ActionType.UPDATE, null).build();
        SwiftMessageApis.sendMsgAsync(message);
        return true;
    }

    @Override
    protected boolean doDelete(Long id) {
        String now = LeDateUtils.formatYYYYMMDDHHMMSS(new Date());
        Update up = update("deleted", true).set("update_at", now);
        return textLiveRepository.update(id, up);
    }

    @Override
    protected boolean doAfterDelete(TextLive entity) {
        deleteTextLiveToEpisode(entity);
        return true;
    }

    @Override
    protected TextLive doFindExistEntity(TextLive entity) {
        return textLiveRepository.findOne(entity.getId());
    }

    @Override
    public TTextLive getTTextLiveById(long id) {
        TTextLive textLive = textLiveCache.findOne(id);
        if (textLive == null) {
            textLive = textLiveVoCreator.createTTextLive(id);
            if (textLive != null) {
                textLiveCache.save(textLive);
            }
        }
        fillTTextLiveWithAnchorUpDown(textLive);
        return textLive;
    }

    @Override
    public List<TTextLive> getTTextLiveByIds(List<Long> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return Collections.EMPTY_LIST;
        }
        if (ids.size() > QmtConstants.MAX_BATCH_SIZE) {
            ids = ids.subList(0, QmtConstants.MAX_BATCH_SIZE);
        }
        List<TTextLive> textLives = Lists.newArrayListWithExpectedSize(ids.size());
        for (Long id : ids) {
            TTextLive textLive = getTTextLiveById(id);
            if (textLive != null) {
                textLives.add(textLive);
            }
        }
        return textLives;
    }

    @Override
    public TextLive findTextLive(long eid, TextLiveType textLiveType) {
        Query q = query(where("deleted").is(false));
        q.addCriteria(where("eid").is(eid));
        if (textLiveType != null) {
            q.addCriteria(where("type").is(textLiveType));
        }
        List<TextLive> textLives = textLiveRepository.findByQuery(q);
        if (CollectionUtils.isNotEmpty(textLives)) {
            return textLives.get(0);
        }
        return null;
    }

    @Override
    public List<TTextLive> getTTextLiveByEid(long eid) {
        Query q = query(where("deleted").is(false));
        q.addCriteria(where("eid").is(eid));
        List<Long> ids = textLiveRepository.findIdByQuery(q);
        return getTTextLiveByIds(ids);
    }

    @Override
    public TTextLive getMainTTextLiveByEid(long eid) {
        Query q = query(where("deleted").is(false));
        q.addCriteria(where("eid").is(eid));
        q.addCriteria(where("type").is(TextLiveType.ANCHOR));
        List<Long> ids = textLiveRepository.findIdByQuery(q);
        if (CollectionUtils.isNotEmpty(ids)) {
            return getTTextLiveById(ids.get(0));
        }
        return null;
    }

    @Override
    public int reportOnlineCount(long eid) {
        boolean legal = QmtSbcEpisodeApis.getTComboEpisodeById(eid, CallerUtils.getDefaultCaller()) != null;
        if (!legal) {
            LOG.warn("episode is illegal.eid:{}", eid);
            return 0;
        }
        int count = textLiveCache.addOnlineCount(eid);
        if (count != 0) {
            List<Long> textLiveIds = getTextLiveIdByEid(eid);
            if (CollectionUtils.isNotEmpty(textLiveIds)) {
                for (Long textLiveId : textLiveIds) {
                    //删除缓存
                    textLiveCache.delete(textLiveId);
                }
            }
        }
        return count;
    }

    @Override
    public int getOnlineCount(long eid) {
        return textLiveCache.getOnlineCount(eid);
    }

    @Override
    public boolean setOnlineCount(long eid, int onlineCount) {
        boolean result = textLiveCache.setOnlineCount(eid, onlineCount);
        if (result) {
            List<Long> textLiveIds = getTextLiveIdByEid(eid);
            if (CollectionUtils.isNotEmpty(textLiveIds)) {
                for (Long textLiveId : textLiveIds) {
                    //删除缓存
                    textLiveCache.delete(textLiveId);
                }
            }
        }
        return result;
    }

    @Override
    public boolean setUpDownAnchor(long textLiveId, long anchorId, Map<UpDownAct, Integer> upDownActMap) {
        boolean result = textLiveCache.setUpDownAnchor(textLiveId, anchorId, upDownActMap);
        if (result) {
            //删除缓存
            textLiveCache.delete(textLiveId);
            TextLive textLive = findOne(textLiveId);
            //给消息中心发送消息
            LeMessage message = LeMessageBuilder.create().setEntityId(textLive.getId())
                    .setIdType(IdType.TEXT_LIVE).setBusinessTypes(ActionType.UPDATE, null).build();
            SwiftMessageApis.sendMsgAsync(message);
        }
        return result;
    }

    @Override
    public TAnchor upDownAnchor(long textLiveId, long anchorId, UpDownAct act) {
        Query q = query(where("deleted").is(false));
        q.addCriteria(where("id").is(textLiveId));
        q.addCriteria(where("anchors.anchorId").is(anchorId));
        TextLive textLive = textLiveRepository.findOneByQuery(q);
        if (textLive == null) {
            LOG.warn("fail upDownAnchor, illegal parameter. textLiveId:{},anchorId:{},act:{}", textLiveId, anchorId, act);
            return null;
        }
        textLiveCache.addUpDownAnchor(textLiveId, anchorId, act);
        //删除缓存
        textLiveCache.delete(textLiveId);
        //给消息中心发送消息
        LeMessage message = LeMessageBuilder.create().setEntityId(textLive.getId())
                .setIdType(IdType.TEXT_LIVE).setBusinessTypes(ActionType.UPDATE, null).build();
        SwiftMessageApis.sendMsgAsync(message);
        return getAnchorUpDown(textLiveId, anchorId);
    }

    @Override
    public TAnchor getAnchorUpDown(long textLiveId, long anchorId) {
        TAnchor tAnchor = new TAnchor();
        TTextLive tTextLive = getTTextLiveById(textLiveId);
        if (tTextLive == null) {
            return null;
        }
        tAnchor = tTextLive.getAnchor();
        int upNum = textLiveCache.getAnchorUpDown(textLiveId, anchorId, UpDownAct.UP);
        int downNum = textLiveCache.getAnchorUpDown(textLiveId, anchorId, UpDownAct.DOWN);
        tAnchor.setUpNum(upNum);
        tAnchor.setDownNum(downNum);
        return tAnchor;
    }

    /**
     * 保存图文直播信息至节目
     *
     * @param textLive
     */
    private void addTextLiveToEpisode(TextLive textLive) {
        Episode episode = QmtSbcEpisodeInternalApis.getEpisodeById(textLive.getEid());
        Set<Episode.SimpleTextLive> textLives = episode.getTextLives();
        Episode.SimpleTextLive simpleTextLive = new Episode.SimpleTextLive();
        simpleTextLive.setTextLiveId(textLive.getId());
        simpleTextLive.setType(textLive.getType());
        textLives.add(simpleTextLive);
        episode.setTextLives(textLives);
        if (episode.getTextLiveStatus() == null) {
            episode.setTextLiveStatus(LiveShowStatus.LIVE_NOT_START);
        }
        QmtSbcEpisodeInternalApis.saveEpisode(episode);
    }

    /**
     * 删除图文直播信息至节目
     *
     * @param textLive
     */
    private void deleteTextLiveToEpisode(TextLive textLive) {
        Episode episode = QmtSbcEpisodeInternalApis.getEpisodeById(textLive.getEid());
        Set<Episode.SimpleTextLive> textLives = episode.getTextLives();
        if (CollectionUtils.isNotEmpty(textLives)) {
            Set<Episode.SimpleTextLive> set = episode.getTextLives();
            Iterator<Episode.SimpleTextLive> iterator = set.iterator();
            while (iterator.hasNext()) {
                Episode.SimpleTextLive simpleTextLive = iterator.next();
                if (simpleTextLive.getTextLiveId() == textLive.getId()) {
                    textLives.remove(simpleTextLive);
                }
            }
        }
        QmtSbcEpisodeInternalApis.saveEpisode(episode);
    }

    private void fillTTextLiveWithAnchorUpDown(TTextLive tTextLive) {
        if (null == tTextLive) {
            return;
        }
        tTextLive.setOnlineCount(textLiveCache.getOnlineCount(tTextLive.getEid()));
        TAnchor tAnchor = tTextLive.getAnchor();
        int upNum = textLiveCache.getAnchorUpDown(tTextLive.getId(), tAnchor.getAnchorId(), UpDownAct.UP);
        int downNum = textLiveCache.getAnchorUpDown(tTextLive.getId(), tAnchor.getAnchorId(), UpDownAct.DOWN);
        tAnchor.setUpNum(upNum);
        tAnchor.setDownNum(downNum);
    }

    public List<Long> getTextLiveIdByEid(long eid) {
        Query q = query(where("deleted").is(false));
        q.addCriteria(where("eid").is(eid));
        return textLiveRepository.findIdByQuery(q);
    }

    @Override
    protected MongoCrudRepository<TextLive, Long> getInnerRepository() {
        return textLiveRepository;
    }
}
