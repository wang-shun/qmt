package com.lesports.qmt.sbd.service.impl;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.lesports.api.common.CallerParam;
import com.lesports.qmt.mvc.QmtPage;
import com.lesports.qmt.mvc.QmtPageParam;
import com.lesports.qmt.sbd.SbdTopListInternalApis;
import com.lesports.qmt.sbd.api.common.CompetitorType;
import com.lesports.qmt.sbd.converter.TopListVoConverter;
import com.lesports.qmt.sbd.model.Player;
import com.lesports.qmt.sbd.model.Team;
import com.lesports.qmt.sbd.model.TopList;
import com.lesports.qmt.sbd.service.PlayerWebService;
import com.lesports.qmt.sbd.service.TeamWebService;
import com.lesports.qmt.sbd.service.TopListWebService;
import com.lesports.qmt.sbd.service.support.AbstractSbdWebService;
import com.lesports.qmt.sbd.vo.TopListVo;
import com.lesports.query.InternalCriteria;
import com.lesports.query.InternalQuery;
import com.lesports.utils.math.LeNumberUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by lufei on 2016/10/27
 */
@Component("topListWebService")
public class TopListWebServiceImpl extends AbstractSbdWebService<TopListVo, Long> implements TopListWebService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TopListWebServiceImpl.class);

    @Resource
    private TeamWebService teamWebService;
    @Resource
    private PlayerWebService playerWebService;
    @Resource
    private TopListVoConverter topListVoConverter;

    @Override
    public TopListVo findOne(Long id, CallerParam caller) {
        TopList topList = SbdTopListInternalApis.getTopListById(id);
        if (topList == null) {
            LOGGER.warn("fail to findOne, not exists, id:{}, caller:{}", id, caller);
            return null;
        }
        return topListVoConverter.toTVo(topList, caller);
    }

    @Override
    public boolean save(TopListVo entity, CallerParam caller) {
        boolean isCreate = isCreateOp(entity);
        TopList old = null;
        if (entity.getId() != null) {
            old = SbdTopListInternalApis.getTopListById(entity.getId());
        }
        if (old != null) {
            entity.setMultiLangNames(old.getMultiLangNames());
            entity.setMultiLangDesc(old.getMultiLangDesc());
            entity.setMultiLangUrls(old.getMultiLangUrls());
        }
        // set value into lang string list
        entity.setAllowCountries(getValidAllowCountries(entity.getAllowCountries(), entity.getCid()));
        // set value into lang string list
        entity.setMultiLangNames(setValueOfLanguage(entity.getMultiLangNames(), entity.getName(), isCreate ? null : caller.getLanguage()));
        entity.setMultiLangDesc(setValueOfLanguage(entity.getMultiLangDesc(), entity.getDescription(), isCreate ? null : caller.getLanguage()));
        entity.setMultiLangUrls(setValueOfLanguage(entity.getMultiLangUrls(), entity.getUrl(), isCreate ? null : caller.getLanguage()));
        // set default value
        entity.setName(getDefaultValueOfMultiLang(entity.getMultiLangNames()));
        entity.setDescription(getDefaultValueOfMultiLang(entity.getMultiLangDesc()));
        entity.setUrl(getDefaultValueOfMultiLang(entity.getMultiLangUrls()));
        long id = SbdTopListInternalApis.saveTopList(entity.toModel());
        return setEntityId(entity, id);
    }

    @Override
    public boolean save(TopListVo entity) {
        return false;
    }

    @Override
    public Long saveWithId(TopListVo entity) {
        return null;
    }

    @Override
    public TopListVo findOne(Long aLong) {
        return null;
    }

    @Override
    public boolean delete(Long id) {
        return SbdTopListInternalApis.deleteTopList(id);
    }

    @Override
    public long countByQuery(InternalQuery query, CallerParam caller) {
        query = addCountryCriteria(query, caller);
        return SbdTopListInternalApis.countTopListsByQuery(query);
    }


    protected InternalQuery buildCriteriaWithPage(Map<String, Object> params, QmtPageParam pageParam) {
        InternalQuery query = buildCriteria(params);
        query.with(getValidPageable(pageParam));
        return query;
    }

    protected InternalQuery buildCriteria(Map<String, Object> params) {
        InternalQuery query = new InternalQuery();
        if (MapUtils.isNotEmpty(params)) {
            long csid = MapUtils.getLongValue(params, "csid");
            if (csid > 0) {
                query.addCriteria(InternalCriteria.where("csid").is(csid));
            }
        }
        query.addCriteria(InternalCriteria.where("deleted").is(false));
        query.setSort(new Sort(Sort.Direction.DESC, "_id"));
        return query;
    }

    @Override
    public List<TopListVo> findByQuery(InternalQuery query, CallerParam caller) {
        query = addCountryCriteria(query, caller);
        List<TopList> topLists = SbdTopListInternalApis.getTopListsByQuery(query);
        return Lists.transform(topLists, new Function<TopList, TopListVo>() {
            @Nullable
            @Override
            public TopListVo apply(@Nullable TopList input) {
                return topListVoConverter.toTVo(input, caller);
            }
        });
    }

    @Override
    public QmtPage<TopListVo> list(Map<String, Object> params, QmtPageParam pageParam, CallerParam caller) {
        InternalQuery criteria = buildCriteriaWithPage(params, pageParam);
        return list(criteria, pageParam, caller);
    }

    @Override
    public boolean updateOnlineStatus(long id, boolean online, CallerParam caller) {
        return false;
    }


    @Override
    protected boolean isCreateOp(TopListVo entity) {
        if (LeNumberUtils.toLong(entity.getId()) > 0) {
            return false;
        }
        return true;
    }


    public List<TopListVo> findByParams(Map<String, Object> params, CallerParam caller) {
        InternalQuery query = buildCriteria(params);
        return findByQuery(query, caller);
    }

    @Override
    public List<Map<String, Object>> listTopListItems(long id, CallerParam caller) {
        List<Map<String, Object>> items = Lists.newArrayList();
        TopList topList = SbdTopListInternalApis.getTopListById(id);
        if (topList == null) {
            LOGGER.warn("TopList is not exists,id:{} ", id);
        }
        if (CollectionUtils.isNotEmpty(topList.getItems())) {
            for (TopList.TopListItem topListItem : topList.getItems()) {
                Map<String, Object> item = Maps.newHashMap();
                item.put("rank", topListItem.getRank());
                item.put("competitorId", topListItem.getCompetitorId());
                item.put("competitorType", topListItem.getCompetitorType());
                if (topListItem.getCompetitorType() == null) {
                    LOGGER.warn("competitorType is null,TopListId:{}", id);
                    continue;
                }
                if (topListItem.getCompetitorType() == CompetitorType.TEAM) {
                    Team team = teamWebService.findOne(topListItem.getCompetitorId(), caller);
                    if (team != null) {
                        item.put("teamName", team.getName());
                    }
                    item.put("teamId", topListItem.getTeamId());
                } else if (topListItem.getCompetitorType() == CompetitorType.PLAYER) {
                    Player player = playerWebService.findOne(topListItem.getCompetitorId(), caller);
                    item.put("playerId", topListItem.getCompetitorId());
                    if (player != null) {
                        item.put("playerName", player.getName());
                        Team team = teamWebService.findOne(topListItem.getTeamId(), caller);
                        if (team != null) {
                            item.put("teamName", team.getName());
                        }
                        item.put("teamId", topListItem.getTeamId());
                    }
                }
                if (MapUtils.isNotEmpty(topListItem.getStats())) {
                    item.putAll(topListItem.getStats());
                }
                items.add(item);
            }
        }
        return items;
    }

    @Override
    public boolean saveTopListItem(long id, TopListVo.TopListItem topListItem) {
        TopList topList = SbdTopListInternalApis.getTopListById(id);
        if (topList == null) {
            LOGGER.warn("cannot save TopListItem, id:{} is not exists", id);
            return false;
        }
        Boolean isExists = false;
        List<TopList.TopListItem> topListItems = topList.getItems();
        if (CollectionUtils.isNotEmpty(topListItems)) {
            for (int i = 0; i < topListItems.size(); i++) {
                if (topListItems.get(i).getCompetitorId().equals(topListItem.getCompetitorId()) && topListItems.get(i).getCompetitorType().equals(topListItem.getCompetitorType())) {
                    isExists = true;
                    Map<String, Object> map = topListItem.getStats();
                    String goal = (String) map.get("goal");
                    String fumble = (String) map.get("fumble");
                    if (StringUtils.isNotEmpty(goal) && StringUtils.isNotEmpty(fumble)) {
                        //积分榜才有goalDiffer
                        map.put("goalDiffer", LeNumberUtils.toInt(goal) - LeNumberUtils.toInt(fumble));
                        topListItem.setStats(map);
                    }
                    topListItems.set(i, topListItem);
                    break;
                }
            }
        }
        if (!isExists) {
            topList.addItem(topListItem);
        }
        return SbdTopListInternalApis.saveTopList(topList) > 0;
    }

    @Override
    public boolean deleteTopListItem(long id, TopListVo.TopListItem topListItem) {
        return SbdTopListInternalApis.deleteTopListItem(id, topListItem);
    }

    @Override
    public boolean publish(long id, CallerParam caller) {
        TopList topList = SbdTopListInternalApis.getTopListById(id);
        return SbdTopListInternalApis.saveTopList(topList) > 0;
    }

}
