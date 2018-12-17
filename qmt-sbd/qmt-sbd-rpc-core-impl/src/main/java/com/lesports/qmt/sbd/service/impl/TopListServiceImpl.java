package com.lesports.qmt.sbd.service.impl;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.lesports.api.common.CallerParam;
import com.lesports.api.common.PageParam;
import com.lesports.id.api.IdType;
import com.lesports.id.client.LeIdApis;
import com.lesports.mongo.repository.MongoCrudRepository;
import com.lesports.qmt.QmtOperationType;
import com.lesports.qmt.msg.core.ActionType;
import com.lesports.qmt.msg.core.BusinessType;
import com.lesports.qmt.msg.core.LeMessage;
import com.lesports.qmt.msg.core.LeMessageBuilder;
import com.lesports.qmt.msg.producer.SwiftMessageApis;
import com.lesports.qmt.sbd.api.dto.TCompetition;
import com.lesports.qmt.sbd.api.dto.TCompetitionSeason;
import com.lesports.qmt.sbd.api.dto.TTopList;
import com.lesports.qmt.sbd.api.service.GetSeasonTopListsParam;
import com.lesports.qmt.sbd.cache.TTopListCache;
import com.lesports.qmt.sbd.converter.TCompetitionSeasonVoConverter;
import com.lesports.qmt.sbd.converter.TTopListVoConverter;
import com.lesports.qmt.sbd.helper.CompetitionSeasonHelper;
import com.lesports.qmt.sbd.model.CompetitionSeason;
import com.lesports.qmt.sbd.model.TopList;
import com.lesports.qmt.sbd.repository.TopListRepository;
import com.lesports.qmt.sbd.service.TopListService;
import com.lesports.qmt.sbd.service.support.AbstractSbdService;
import com.lesports.utils.LeDateUtils;
import com.lesports.utils.PageUtils;
import com.lesports.utils.math.LeNumberUtils;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.CriteriaDefinition;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;
import static org.springframework.data.mongodb.core.query.Update.update;

/**
 * User: lufei
 * Time: 2016-10-24 : 下午12:04
 */
@Service("topListService")
public class TopListServiceImpl extends AbstractSbdService<TopList, Long> implements TopListService {

    private static final Logger LOG = LoggerFactory.getLogger(TopListServiceImpl.class);

    @Resource
    private TopListRepository topListRepository;
    @Resource
    private CompetitionSeasonHelper competitionSeasonHelper;
    @Resource
    private TTopListCache topListCache;
    @Resource
    private TTopListVoConverter tTopListVoConverter;


    @Override
    public TopList findOne(Long id) {
        return topListRepository.findOne(id);
    }

    @Override
    protected QmtOperationType getOpreationType(TopList entity) {
        if (LeNumberUtils.toLong(entity.getId()) > 0) {
            return QmtOperationType.UPDATE;
        }
        return QmtOperationType.CREATE;
    }

    @Override
    protected boolean doCreate(TopList entity) {
        entity.setId(LeIdApis.nextId(IdType.TOP_LIST));
        entity.setDeleted(false);
        return topListRepository.save(entity);
    }

    @Override
    protected boolean doAfterCreate(TopList entity) {
        LeMessage message = LeMessageBuilder.create().
                setEntityId(entity.getId()).
                setIdType(IdType.TOP_LIST).
                setData(JSON.toJSONString(entity)).
                setBusinessTypes(ActionType.ADD, ImmutableList.of(BusinessType.DATA_SYNC)).
                build();
        SwiftMessageApis.sendMsgAsync(message);
        return true;
    }

    @Override
    protected boolean doUpdate(TopList entity) {
        return topListRepository.save(entity);
    }

    @Override
    protected boolean doAfterUpdate(TopList entity) {
        LeMessage message = LeMessageBuilder.create().
                setEntityId(entity.getId()).
                setIdType(IdType.TOP_LIST).
                setData(JSON.toJSONString(entity)).
                setBusinessTypes(ActionType.UPDATE, ImmutableList.of(BusinessType.DATA_SYNC)).
                build();
        SwiftMessageApis.sendMsgAsync(message);
        return true;
    }

    @Override
    protected boolean doDelete(Long id) {
        String now = LeDateUtils.formatYYYYMMDDHHMMSS(new Date());
        Update up = update("deleted", true).set("update_at", now);
        return topListRepository.update(id, up);
    }

    @Override
    protected boolean doAfterDelete(TopList entity) {
        LeMessage message = LeMessageBuilder.create().
                setEntityId(entity.getId()).
                setIdType(IdType.TOP_LIST).
                setData(JSON.toJSONString(entity)).
                setBusinessTypes(ActionType.DELETE, ImmutableList.of(BusinessType.DATA_SYNC)).
                build();
        SwiftMessageApis.sendMsgAsync(message);
        return true;
    }

    @Override
    protected TopList doFindExistEntity(TopList entity) {
        return topListRepository.findOne(entity.getId());
    }


    @Override
    protected MongoCrudRepository getInnerRepository() {
        return topListRepository;
    }


    @Override
    public void deleteTopListItem(long id, TopList.TopListItem item) {
        Update update = new Update();
        update.pull("items", item);
        topListRepository.update(id, update);
    }

    @Override
    public TTopList getTTopListById(long id, CallerParam caller) {
        TTopList tTopList = topListCache.findOne(id);
        if (tTopList == null) {
            TopList topList = topListRepository.findOne(id);
            if (topList == null) {
                LOG.warn("fail to getTTopListById, topList no exists. id : {}, caller : {}", id, caller);
                return null;
            }
            tTopList = tTopListVoConverter.toDto(topList);
            if (tTopList == null) {
                LOG.warn("fail to getTTopListById, toTVo fail. id : {}, caller : {}", id, caller);
                return null;
            }
            topListCache.save(tTopList);
        }
        tTopListVoConverter.adaptDto(tTopList, caller);
        return tTopList;
    }

    @Override
    public List<TTopList> getTTopListsByIds(List<Long> ids, CallerParam caller) {
        if (CollectionUtils.isEmpty(ids)) {
            return Collections.EMPTY_LIST;
        }
        List<TTopList> toplists = Lists.newArrayListWithExpectedSize(ids.size());
        for (Long id : ids) {
            TTopList topList = getTTopListById(id, caller);
            if (topList != null) {
                toplists.add(topList);
            }
        }
        return toplists;
    }

    @Override
    public List<Long> getSeasonTopListIds(GetSeasonTopListsParam p, Pageable pageable, CallerParam caller) {
        Query q = query(where("deleted").is(false));
        CriteriaDefinition seasonCriteria = getSeasonCriteria(p.getCid(), p.getCsid());
        if (seasonCriteria == null) {
            LOG.warn("fail to getSeasonTopListIds. season criteria not found. p : {}", p);
            return Collections.EMPTY_LIST;
        }
        q.addCriteria(seasonCriteria);
        if (p.getType() > 0) {
            q.addCriteria(where("type").is(p.getType()));
        }
        if (p.getGroup() > 0) {
            q.addCriteria(where("group").is(p.getGroup()));
        }
        if (p.getCompetitorType() != null) {
            q.addCriteria(where("competitor_type").is(p.getCompetitorType()));
        }
        if (p.getScopeType() != null) {
            q.addCriteria(where("scope_type").is(p.getScopeType()));
        }
        if (CollectionUtils.isNotEmpty(p.getTypes())) {
            q.addCriteria(where("type").in(p.getTypes()));
        }
        addInternationalCriteriaToQuery(q, caller);
        pageable = PageUtils.getValidPageable(pageable);
        q.with(new Sort(Sort.Direction.ASC,"order"));
        q.with(pageable);
        return topListRepository.findIdByQuery(q);
    }

    private CriteriaDefinition getSeasonCriteria(long cid, long csid) {
        if (csid > 0) {
            //有赛季
            return where("csid").is(csid);
        }
        //无赛季
        if (cid > 0) {
            //有赛事
            CompetitionSeason season = competitionSeasonHelper.getLatestSeasonByCid(cid);
            if (season != null) {
                return where("csid").is(season.getId());
            }
        }

        return null;
    }


    @Override
    public List<TTopList> getCompetitorTTopLists(GetSeasonTopListsParam p, PageParam page, CallerParam caller) {
        Query q = query(where("deleted").is(false));
        CriteriaDefinition seasonCriteria = getSeasonCriteria(p.getCid(), p.getCsid());
        if (seasonCriteria == null) {
            LOG.warn("fail to getSeasonTopListIds. season criteria not found. p : {}", p);
            return Collections.EMPTY_LIST;
        }
        q.addCriteria(seasonCriteria);
        if (p.getType() > 0) {
            q.addCriteria(where("type").is(p.getType()));
        }
        if (p.getGroup() > 0) {
            q.addCriteria(where("group").is(p.getGroup()));
        }
        if (CollectionUtils.isNotEmpty(p.getTypes())) {
            q.addCriteria(where("type").in(p.getTypes()));
        }
        if(p.getScope() > 0) {
            q.addCriteria(where("scope").is(p.getScope()));
        }
        if(p.getScopeType() != null){
            q.addCriteria(where("scope_type").is(p.getScopeType()));
        }
        if(p.getCompetitorType() != null){
            q.addCriteria(where("competitor_type").is(p.getCompetitorType()));
        }
        addInternationalCriteriaToQuery(q, caller);
        Pageable pageable = PageUtils.convertToPageable(page);
        q.with(new Sort(Sort.Direction.ASC,"order"));
        q.with(pageable);
        List<Long> ids = topListRepository.findIdByQuery(q);
        return getTTopListsByIds(ids, caller);
    }
}