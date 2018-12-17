package com.lesports.qmt.config.service.impl;

import com.google.common.collect.Lists;
import com.lesports.api.common.CallerParam;
import com.lesports.id.api.IdType;
import com.lesports.id.client.LeIdApis;
import com.lesports.mongo.repository.MongoCrudRepository;
import com.lesports.qmt.QmtConstants;
import com.lesports.qmt.QmtOperationType;
import com.lesports.qmt.config.api.dto.DictEntryTopType;
import com.lesports.qmt.config.api.dto.TDictEntry;
import com.lesports.qmt.config.api.service.GetDictEntriesParam;
import com.lesports.qmt.config.cache.TDictEntryCache;
import com.lesports.qmt.config.converter.TDictEntryConverter;
import com.lesports.qmt.config.model.DictEntry;
import com.lesports.qmt.config.repository.DictRepository;
import com.lesports.qmt.config.service.DictService;
import com.lesports.qmt.msg.core.ActionType;
import com.lesports.qmt.msg.core.BusinessType;
import com.lesports.qmt.msg.core.LeMessage;
import com.lesports.qmt.msg.core.LeMessageBuilder;
import com.lesports.qmt.msg.producer.SwiftMessageApis;
import com.lesports.qmt.service.support.AbstractQmtService;
import com.lesports.utils.LeDateUtils;
import com.lesports.utils.PageUtils;
import com.lesports.utils.math.LeNumberUtils;
import com.letv.urus.liveroom.api.sports.LiveRoomSportsWriterAPI;
import com.letv.urus.liveroom.base.UrusAuth;
import com.letv.urus.liveroom.bo.MatchType;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;
import static org.springframework.data.mongodb.core.query.Update.update;

/**
 * User: ellios
 * Time: 15-6-5 : 上午1:04
 */
@Service("dictService")
public class DictServiceImpl extends AbstractQmtService<DictEntry,Long> implements DictService {

    private static final Logger LOG = LoggerFactory.getLogger(DictServiceImpl.class);

    @Resource
    private DictRepository dictRepository;

    @Resource
    private TDictEntryCache dictEntryCache;

    @Resource
    private TDictEntryConverter dictEntryConverter;

    @Resource
    private LiveRoomSportsWriterAPI liveRoomSportsWriterAPI;

    private static final UrusAuth urusAuth;

    static {
        urusAuth = new UrusAuth();
        urusAuth.setUrusAppId("id");
        urusAuth.setUrusSecret("secret");
        urusAuth.setRequestId("123");
        urusAuth.setUrusUserId("dabingge");
    }

    @Override
    protected QmtOperationType getOpreationType(DictEntry entity) {
        if (LeNumberUtils.toLong(entity.getId()) <= 0) {
            return QmtOperationType.CREATE;
        }
        return QmtOperationType.UPDATE;
    }

    @Override
    protected boolean doCreate(DictEntry entity) {
        entity.setId(LeIdApis.nextId(IdType.DICT_ENTRY));
        entity.setDeleted(false);
        return dictRepository.save(entity);
    }

    @Override
    protected boolean doAfterCreate(DictEntry entity) {
        dictEntryCache.delete(entity.getId());
        List<BusinessType> businessTypes = Lists.newArrayList();
        if (!indexEntity(entity.getId(), IdType.DICT_ENTRY.toString())) {
            businessTypes.add(BusinessType.SEARCH_INDEX);
        }
        LeMessage message = LeMessageBuilder.create()
                .setEntityId(entity.getId())
                .setIdType(IdType.DICT_ENTRY)
                .setBusinessTypes(ActionType.ADD, businessTypes)
                .build();
        SwiftMessageApis.sendMsgAsync(message);
        //同步给直播
        if(LeNumberUtils.toLong(entity.getParentId()) == CHANNEL) {
            MatchType matchType = new MatchType();
            matchType.setName(entity.getName());
            matchType.setMatchtypeid(String.valueOf(entity.getId()));
            try {
                liveRoomSportsWriterAPI.addMatchType(urusAuth, matchType);
                LOG.info("sync dict add to live success, entity: {}", entity);
            } catch (Exception e) {
                LOG.error("failed to call liveRoomSportsWriterAPI when create channel, entity:{}", entity, e);
            }
        }
        return true;
    }

    @Override
    protected boolean doUpdate(DictEntry entity) {
        return dictRepository.save(entity);
    }

    @Override
    protected boolean doAfterUpdate(DictEntry entity) {
        dictEntryCache.delete(entity.getId());
        List<BusinessType> businessTypes = Lists.newArrayList();
        if (!indexEntity(entity.getId(), IdType.DICT_ENTRY.toString())) {
            businessTypes.add(BusinessType.SEARCH_INDEX);
        }
        LeMessage message = LeMessageBuilder.create()
                .setEntityId(entity.getId())
                .setIdType(IdType.DICT_ENTRY)
                .setBusinessTypes(ActionType.UPDATE, businessTypes)
                .build();
        SwiftMessageApis.sendMsgAsync(message);
        //同步给直播
        if(LeNumberUtils.toLong(entity.getParentId()) == CHANNEL) {
            MatchType matchType = new MatchType();
            matchType.setName(entity.getName());
            matchType.setMatchtypeid(String.valueOf(entity.getId()));
            try {
                liveRoomSportsWriterAPI.updateMatchType(urusAuth, matchType);
                LOG.info("sync dict update to live success, entity: {}", entity);
            } catch (Exception e) {
                LOG.error("failed to call liveRoomSportsWriterAPI when update channel, entity:{}", entity, e);
            }
        }
        return true;
    }

    @Override
    protected boolean doDelete(Long id) {
        String now = LeDateUtils.formatYYYYMMDDHHMMSS(new Date());
        Update up = update("deleted", true).set("update_at", now);
        return dictRepository.update(id, up);
    }

    @Override
    protected boolean doAfterDelete(DictEntry entity) {
        dictEntryCache.delete(entity.getId());
        List<BusinessType> businessTypes = Lists.newArrayList();
        if (!indexEntity(entity.getId(), IdType.DICT_ENTRY.toString())) {
            businessTypes.add(BusinessType.SEARCH_INDEX);
        }
        LeMessage message = LeMessageBuilder.create()
                .setEntityId(entity.getId())
                .setIdType(IdType.DICT_ENTRY)
                .setBusinessTypes(ActionType.DELETE, businessTypes)
                .build();
        SwiftMessageApis.sendMsgAsync(message);
        //同步给直播
        if(LeNumberUtils.toLong(entity.getParentId()) == CHANNEL) {
            try {
                liveRoomSportsWriterAPI.deleteMatchType(urusAuth, String.valueOf(entity.getId()));
                LOG.info("sync dict delete to live success, entity: {}", entity);
            } catch (Exception e) {
                LOG.error("failed to call liveRoomSportsWriterAPI when delete channel, entity:{}", entity, e);
            }
        }
        return true;
    }

    @Override
    protected DictEntry doFindExistEntity(DictEntry entity) {
        return findOne(entity.getId());
    }

    @Override
    protected MongoCrudRepository getInnerRepository() {
        return dictRepository;
    }

    @Override
    public TDictEntry getTDictEntryById(Long id, CallerParam caller) {
        TDictEntry vo =  dictEntryCache.findOne(id);
        if(null == vo) {
            DictEntry dictEntry = dictRepository.findOne(id);
            if (dictEntry == null) {
                LOG.warn("fail to getTDictEntryById, dict no exists. id : {}, caller : {}", id, caller);
                return null;
            }

            vo = dictEntryConverter.toDto(dictEntry);
            if (vo == null) {
                LOG.warn("fail to getTDictEntryById, toTVo fail. id : {}, caller : {}", id, caller);
                return null;
            }
            dictEntryCache.save(vo);
        }
        dictEntryConverter.adaptDto(vo, caller);
        return vo;
    }

    @Override
    public List<TDictEntry> getTDictEntriesByIds(List<Long> ids, CallerParam caller) {
        if (CollectionUtils.isEmpty(ids)) {
            return Collections.EMPTY_LIST;
        }
        List<TDictEntry> dicts = Lists.newArrayListWithExpectedSize(ids.size());
        for (Long id : ids) {
            TDictEntry dict = getTDictEntryById(id, caller);
            if (dict != null) {
                dicts.add(dict);
            }
        }
        return dicts;
    }

    @Override
    public List<TDictEntry> getDictListByParentId(long parentId, CallerParam caller) {
        Query q = query(where("parent_id").is(parentId));// 类型
        q.addCriteria(Criteria.where("deleted").is(false));
        long count = dictRepository.countByQuery(q);
        List<DictEntry> resultList;
        if (count > QmtConstants.MAX_PAGE_SIZE) { //获取上限1000条
            Pageable pageable = PageUtils.createPageable(0, QmtConstants.MAX_PAGE_SIZE);
            Page<DictEntry> pageRulst = dictRepository.findByQuery4Page(q, pageable);
            resultList = pageRulst.getContent();
        } else {
            resultList = dictRepository.findByQuery(q);
        }
        return dictEntryConverter.toDtoList(resultList);
    }

    @Override
    public List<Long> getDictEntryIds4SimpleSearch(GetDictEntriesParam p, Pageable pageable) {
        Query q = query(where("deleted").is(false));
        if (p != null) {
            if (p.getParentId() > 0) {
                q.addCriteria(where("parent_id").is(p.getParentId()));
            }
            if (StringUtils.isNotEmpty(p.getName())) {
                q.addCriteria(where("name").regex(p.getName()));
            }
        }
        pageable = PageUtils.getValidPageable(pageable);
        q.with(pageable);
        return dictRepository.findIdByQuery(q);
    }

    @Override
    public DictEntryTopType getDictEntryTopType(long id) {
        DictEntry entry = dictRepository.findOne(id);
        if (entry == null) {
            return null;
        }
        long topParentId = entry.getTopParentId() != null ? entry.getTopParentId() : 0;
        if (topParentId == 0) {
            //已经是顶级字典项了
            topParentId = entry.getId();
        }
        return getTopType(topParentId);
    }

    private DictEntryTopType getTopType(long topEntryId) {
        int topId = (int) topEntryId;
        switch (topId) {
            case 100001000:
                return DictEntryTopType.GAME_FIRST_TYPE;
            case 100003000:
                return DictEntryTopType.GROUP;
            case 100004000:
                return DictEntryTopType.ROUND;
            case 100005000:
                return DictEntryTopType.STAGE;
            case 100006000:
                return DictEntryTopType.STATION;
            case 100007000:
                return DictEntryTopType.ROLE;
            case 100008000:
                return DictEntryTopType.POSITION;
            case 100013000:
                return DictEntryTopType.TOPLIST;
            case 100010000:
                return DictEntryTopType.DIVISION;
            case 100009000:
                return DictEntryTopType.CONFERENCE;
            case 100011000:
                return DictEntryTopType.SECTION;
        }
        return null;
    }
}
