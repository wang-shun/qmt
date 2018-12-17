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
import com.lesports.qmt.sbd.api.dto.TTeamSeason;
import com.lesports.qmt.sbd.api.service.GetTeamSeasonsParam;
import com.lesports.qmt.sbd.api.service.GetTeams4SimpleSearchParam;
import com.lesports.qmt.sbd.api.service.GetTeamsOfCompetitionParam;
import com.lesports.qmt.sbd.api.service.GetTeamsOfSeasonParam;
import com.lesports.qmt.sbd.cache.TTeamCache;
import com.lesports.qmt.sbd.cache.TTeamSeasonCache;
import com.lesports.qmt.sbd.converter.TTeamSeasonVoConverter;
import com.lesports.qmt.sbd.model.TeamSeason;
import com.lesports.qmt.sbd.repository.TeamRepository;
import com.lesports.qmt.sbd.repository.TeamSeasonRepository;
import com.lesports.qmt.sbd.service.TeamSeasonService;
import com.lesports.qmt.sbd.service.support.AbstractSbdService;
import com.lesports.utils.LeDateUtils;
import com.lesports.utils.PageUtils;
import com.lesports.utils.math.LeNumberUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
@Service("teamSeasonService")
public class TeamSeasonServiceImpl extends AbstractSbdService<TeamSeason, Long> implements TeamSeasonService {

    private static final Logger LOG = LoggerFactory.getLogger(TeamSeasonServiceImpl.class);

    @Resource
    private TeamSeasonRepository teamSeasonRepository;
    @Resource
    private TTeamSeasonVoConverter tTeamSeasonVoConverter;
    @Resource
    private TTeamSeasonCache teamSeasonCache;

    @Override
    public TeamSeason findOne(Long id) {
        return teamSeasonRepository.findOne(id);
    }

    @Override
    protected MongoCrudRepository getInnerRepository() {
        return teamSeasonRepository;
    }

    @Override
    public List<Long> getTeamSeasonIds(GetTeamSeasonsParam p, Pageable pageable, CallerParam caller) {
        Query q = new Query();
        if (p != null) {
            if (p.getTid() > 0) {
                q.addCriteria(where("tid").is(p.getTid()));
            }
            if (p.getPid() > 0) {
                q.addCriteria(where("players.pid").is(p.getPid()));
            }
            if (p.getCsid() > 0) {
                q.addCriteria(where("csid").is(p.getCsid()));
            }
        }
        addCountryCriteriaToQuery(q, caller);
        pageable = getValidPageable(pageable);
        q.with(pageable);
        return teamSeasonRepository.findIdByQuery(q);
    }

    private Pageable getValidPageable(Pageable pageable) {
        pageable = PageUtils.getValidPageable(pageable);
        if (pageable.getSort() == null) {
            pageable = new PageRequest(pageable.getPageNumber(), pageable.getPageSize(), Sort.Direction.DESC, "_id");
        }
        return pageable;
    }

    @Override
    public TTeamSeason getTTeamSeasonById(Long id, CallerParam caller) {
        TTeamSeason tTeamSeason = teamSeasonCache.findOne(id);
        if (tTeamSeason == null) {
            TeamSeason teamSeason = teamSeasonRepository.findOne(id);
            if (teamSeason == null) {
                LOG.warn("fail to getTTeamSeasonById, teamSeason no exists. id : {}, caller : {}", id, caller);
                return null;
            }
            tTeamSeason = tTeamSeasonVoConverter.toDto(teamSeason);
            if (tTeamSeason == null) {
                LOG.warn("fail to getTTeamSeasonById, toTVo fail. id : {}, caller : {}", id, caller);
                return null;
            }
            teamSeasonCache.save(tTeamSeason);
        }
        tTeamSeasonVoConverter.adaptDto(tTeamSeason, caller);
        return tTeamSeason;
    }



    @Override
    protected QmtOperationType getOpreationType(TeamSeason entity) {
        if (LeNumberUtils.toLong(entity.getId()) > 0) {
            return QmtOperationType.UPDATE;
        }
        return QmtOperationType.CREATE;
    }

    @Override
    protected boolean doDelete(Long id) {
        String now = LeDateUtils.formatYYYYMMDDHHMMSS(new Date());
        Update up = update("deleted", true).set("update_at", now);
        return teamSeasonRepository.update(id, up);
    }

    @Override
    protected boolean doAfterUpdate(TeamSeason entity) {
        LeMessage message = LeMessageBuilder.create().
                setEntityId(entity.getId()).
                setIdType(IdType.TEAM_SEASON).
                setData(JSON.toJSONString(entity)).
                setBusinessTypes(ActionType.UPDATE, ImmutableList.of(BusinessType.DATA_SYNC)).
                build();
        SwiftMessageApis.sendMsgAsync(message);
        return true;
    }

    @Override
    protected boolean doUpdate(TeamSeason entity) {
        return teamSeasonRepository.save(entity);
    }

    @Override
    protected boolean doAfterDelete(TeamSeason entity) {
        LeMessage message = LeMessageBuilder.create().
                setEntityId(entity.getId()).
                setIdType(IdType.TEAM_SEASON).
                setData(JSON.toJSONString(entity)).
                setBusinessTypes(ActionType.DELETE, ImmutableList.of(BusinessType.DATA_SYNC)).
                build();
        SwiftMessageApis.sendMsgAsync(message);
        return true;
    }

    @Override
    protected boolean doAfterCreate(TeamSeason entity) {
        LeMessage message = LeMessageBuilder.create().
                setEntityId(entity.getId()).
                setIdType(IdType.TEAM_SEASON).
                setData(JSON.toJSONString(entity)).
                setBusinessTypes(ActionType.ADD, ImmutableList.of(BusinessType.DATA_SYNC)).
                build();
        SwiftMessageApis.sendMsgAsync(message);
        return true;
    }

    @Override
    protected boolean doCreate(TeamSeason entity) {
        entity.setId(LeIdApis.nextId(IdType.TEAM_SEASON));
        entity.setDeleted(false);
        return teamSeasonRepository.save(entity);
    }

    @Override
    protected TeamSeason doFindExistEntity(TeamSeason entity) {
        return teamSeasonRepository.findOne(entity.getId());
    }



    @Override
    public List<TTeamSeason> getTTeamSeasonsByIds(List<Long> ids, CallerParam caller) {
        if (org.apache.commons.collections.CollectionUtils.isEmpty(ids)) {
            return Collections.EMPTY_LIST;
        }
        List<TTeamSeason> tTeamSeasons = Lists.newArrayListWithExpectedSize(ids.size());
        for (Long id : ids) {
            TTeamSeason teamSeason = getTTeamSeasonById(id, caller);
            if (teamSeason != null) {
                tTeamSeasons.add(teamSeason);
            }
        }
        return tTeamSeasons;
    }

    @Override
    public void addTeamPlayer(Long tsid, Long pid, Long pnumber){
        Update update = new Update();
        TeamSeason.TeamPlayer teamPlayer = new TeamSeason.TeamPlayer();
        teamPlayer.setPid(pid);
        teamPlayer.setNumber(pnumber+"");
        update.addToSet("players", teamPlayer);
        teamSeasonRepository.update(tsid, update);
    }



}