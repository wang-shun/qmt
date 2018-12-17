package com.lesports.qmt.sbd.service.impl;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.lesports.api.common.CallerParam;
import com.lesports.id.api.IdType;
import com.lesports.id.client.LeIdApis;
import com.lesports.mongo.repository.MongoCrudRepository;
import com.lesports.qmt.QmtOperationType;
import com.lesports.qmt.msg.core.ActionType;
import com.lesports.qmt.msg.core.BusinessType;
import com.lesports.qmt.msg.core.LeMessage;
import com.lesports.qmt.msg.core.LeMessageBuilder;
import com.lesports.qmt.msg.producer.SwiftMessageApis;
import com.lesports.qmt.sbd.api.dto.TTeam;
import com.lesports.qmt.sbd.api.service.GetTeamSeasonsParam;
import com.lesports.qmt.sbd.api.service.GetTeams4SimpleSearchParam;
import com.lesports.qmt.sbd.api.service.GetTeamsOfCompetitionParam;
import com.lesports.qmt.sbd.api.service.GetTeamsOfSeasonParam;
import com.lesports.qmt.sbd.cache.TTeamCache;
import com.lesports.qmt.sbd.converter.TTeamVoConverter;
import com.lesports.qmt.sbd.helper.CompetitionSeasonHelper;
import com.lesports.qmt.sbd.model.CompetitionSeason;
import com.lesports.qmt.sbd.model.Team;
import com.lesports.qmt.sbd.model.TeamSeason;
import com.lesports.qmt.sbd.repository.TeamRepository;
import com.lesports.qmt.sbd.service.TeamSeasonService;
import com.lesports.qmt.sbd.service.TeamService;
import com.lesports.qmt.sbd.service.support.AbstractSbdService;
import com.lesports.qmt.sbd.utils.PinyinUtils;
import com.lesports.utils.LeDateUtils;
import com.lesports.utils.PageUtils;
import com.lesports.utils.math.LeNumberUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
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
@Service("teamService")
public class TeamServiceImpl extends AbstractSbdService<Team, Long> implements TeamService {

    private static final Logger LOG = LoggerFactory.getLogger(TeamServiceImpl.class);

    @Resource
    private TeamRepository teamRepository;
    @Resource
    private TTeamCache teamCache;
    @Resource
    private TTeamVoConverter tTeamVoConverter;
    @Resource
    private TeamSeasonService teamSeasonService;
    @Resource
    private CompetitionSeasonHelper competitionSeasonHelper;
//    @Resource
//    private TTeamCache teamCache;

    @Override
    public Team findOne(Long id) {
        return teamRepository.findOne(id);
    }

    @Override
    protected QmtOperationType getOpreationType(Team entity) {
        if (LeNumberUtils.toLong(entity.getId()) > 0) {
            return QmtOperationType.UPDATE;
        }
        return QmtOperationType.CREATE;
    }

    @Override
    protected boolean doCreate(Team entity) {
        entity.setId(LeIdApis.nextId(IdType.TEAM));
        entity.setDeleted(false);
        //设置首字母
        entity.setFirstLetter(PinyinUtils.getPinYin(entity.getName()).substring(0, 1));
        return teamRepository.save(entity);
    }

    @Override
    protected boolean doAfterCreate(Team entity) {
        List<BusinessType> businessTypes = Lists.newArrayList(BusinessType.DATA_SYNC);
        if (!indexEntity(entity.getId(), IdType.TEAM.toString())) {
            businessTypes.add(BusinessType.SEARCH_INDEX);
        }
        LeMessage message = LeMessageBuilder.create().
                setEntityId(entity.getId()).
                setIdType(IdType.TEAM).
                setData(JSON.toJSONString(entity)).
                setBusinessTypes(ActionType.ADD, businessTypes).
                build();
        SwiftMessageApis.sendMsgAsync(message);
        return true;
    }

    @Override
    protected boolean doUpdate(Team entity) {
        //未设置首字母的话，设置首字母
        if (StringUtils.isNotEmpty(entity.getName()) && StringUtils.isEmpty(entity.getFirstLetter())) {
            entity.setFirstLetter(PinyinUtils.getPinYin(entity.getName().toUpperCase()).substring(0, 1));
        }
        return teamRepository.save(entity);
    }

    @Override
    protected boolean doAfterUpdate(Team entity) {
//        teamCache.delete(entity.getId());
        List<BusinessType> businessTypes = Lists.newArrayList(BusinessType.DATA_SYNC);
        if (!indexEntity(entity.getId(), IdType.TEAM.toString())) {
            businessTypes.add(BusinessType.SEARCH_INDEX);
        }
        LeMessage message = LeMessageBuilder.create().
                setEntityId(entity.getId()).
                setIdType(IdType.TEAM).
                setData(JSON.toJSONString(entity)).
                setBusinessTypes(ActionType.UPDATE, businessTypes).
                build();
        SwiftMessageApis.sendMsgAsync(message);
        return true;
    }

    @Override
    protected boolean doDelete(Long id) {
        String now = LeDateUtils.formatYYYYMMDDHHMMSS(new Date());
        Update up = update("deleted", true).set("update_at", now);
        return teamRepository.update(id, up);
    }

    @Override
    protected boolean doAfterDelete(Team entity) {
//        teamCache.delete(entity.getId());
        List<BusinessType> businessTypes = Lists.newArrayList(BusinessType.DATA_SYNC);
        if (!indexEntity(entity.getId(), IdType.TEAM.toString())) {
            businessTypes.add(BusinessType.SEARCH_INDEX);
        }
        LeMessage message = LeMessageBuilder.create().
                setEntityId(entity.getId()).
                setIdType(IdType.TEAM).
                setData(JSON.toJSONString(entity)).
                setBusinessTypes(ActionType.DELETE, businessTypes).
                build();
        SwiftMessageApis.sendMsgAsync(message);
        return true;
    }

    @Override
    protected Team doFindExistEntity(Team entity) {
        return teamRepository.findOne(entity.getId());
    }


    @Override
    protected MongoCrudRepository getInnerRepository() {
        return teamRepository;
    }

    @Override
    public TTeam getTTeamById(long id, CallerParam caller) {
        TTeam tTeam = teamCache.findOne(id);
        if (tTeam == null) {
            Team team = teamRepository.findOne(id);
            if (team == null) {
                LOG.warn("fail to getTTeamById, team no exists. id : {}, caller : {}", id, caller);
                return null;
            }
            tTeam = tTeamVoConverter.toDto(team);
            if (tTeam == null) {
                LOG.warn("fail to getTTeamById, toTVo fail. id : {}, caller : {}", id, caller);
                return null;
            }
            teamCache.save(tTeam);
        }
        tTeamVoConverter.adaptDto(tTeam, caller);
        return tTeam;
    }

    @Override
    public List<TTeam> getTTeamsByIds(List<Long> ids, CallerParam caller) {
        if (CollectionUtils.isEmpty(ids)) {
            return Collections.EMPTY_LIST;
        }
        List<TTeam> teams = Lists.newArrayListWithExpectedSize(ids.size());
        for (Long id : ids) {
            TTeam team = getTTeamById(id, caller);
            if (team != null) {
                teams.add(team);
            }
        }
        return teams;
    }

    @Override
    public List<Long> getTeamIds4SimpleSearch(GetTeams4SimpleSearchParam p, Pageable pageable, CallerParam caller) {
        Query q = query(where("deleted").is(false));
        if (p != null) {
            if (p.getTeamType() > 0) {
                q.addCriteria(where("team_type").is(p.getTeamType()));
            }
            if (StringUtils.isNotBlank(p.getFirstLetter())) {
                q.addCriteria(where("first_letter").is(p.getFirstLetter()));
            }
            if (StringUtils.isNotBlank(p.getName())) {
                q.addCriteria(where("multi_lang_names.value").regex(p.getName()));
            }
            if (p.getLeciId() > 0) {
                q.addCriteria(where("leci.id").is(p.getLeciId()));
            }
            if (StringUtils.isNotBlank(p.getOctopusName())) {
                q.addCriteria(where("octopus_name").is(p.getOctopusName()));
            }
        }
        addInternationalCriteriaToQuery(q, caller);
        pageable = PageUtils.getValidPageable(pageable);
        q.with(pageable);
        return teamRepository.findIdByQuery(q);
    }

    @Override
    public List<Long> getTeamIdsOfSeason(GetTeamsOfSeasonParam p, Pageable pageable, CallerParam caller) {
        Query q = query(where("deleted").is(false));
        if (p != null) {
            long csid = p.getCsid();
            if (csid == 0) {
                //赛季id为0，获取最新赛季
                CompetitionSeason season = competitionSeasonHelper.getLatestSeasonByCid(p.getCid());
                if (season == null) {
                    LOG.warn("fail to getTeamIdsOfSeason, season not exists. p: {}, caller: {}", p, caller);
                    return Collections.EMPTY_LIST;
                }
                csid = season.getId();
            }
            GetTeamSeasonsParam p2 = new GetTeamSeasonsParam();
            p2.setCsid(csid);
            List<Long> tsIds = teamSeasonService.getTeamSeasonIds(p2, pageable, caller);
            List<TeamSeason> seasons = teamSeasonService.findByIds(tsIds);
            List<Long> ids = new ArrayList<>();
            for (TeamSeason season : seasons) {
                ids.add(season.getTid());
            }
            return ids;
        }
        addCountryCriteriaToQuery(q, caller);
        pageable = PageUtils.getValidPageable(pageable);
        q.with(pageable);
        return teamRepository.findIdByQuery(q);
    }

    @Override
    public List<Long> getTeamIdsOfCompetition(GetTeamsOfCompetitionParam p, Pageable pageable, CallerParam caller) {
        Query q = query(where("deleted").is(false));
        if (p != null) {
            if (p.getCid() > 0) {
                q.addCriteria(where("cids").is(p.getCid()));
            }
            if (p.getGameType() > 0) {
                q.addCriteria(where("game_f_type").is(p.getGameType()));
            }
        }
        addInternationalCriteriaToQuery(q, caller);
        pageable = PageUtils.getValidPageable(pageable);
        q.with(pageable);
        return teamRepository.findIdByQuery(q);
    }

    @Override
    public TTeam getTTeamByCampId(long id, CallerParam caller) {
        if (id <= 0) {
            return null;
        }
        Query q = query(where("deleted").is(false));
        q.addCriteria(where("camp_id").is(id + ""));
        addInternationalCriteriaToQuery(q, caller);
        List<Long> ids = teamRepository.findIdByQuery(q);
        if (CollectionUtils.isEmpty(ids)) {
            return null;
        }
        return getTTeamById(ids.get(0), caller);
    }
}