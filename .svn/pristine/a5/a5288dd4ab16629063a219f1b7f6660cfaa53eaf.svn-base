package com.lesports.qmt.sbd.service.impl;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.lesports.api.common.CallerParam;
import com.lesports.qmt.mvc.QmtPage;
import com.lesports.qmt.mvc.QmtPageParam;
import com.lesports.qmt.sbd.SbdTeamInternalApis;
import com.lesports.qmt.sbd.adapter.TeamAdapter;
import com.lesports.qmt.sbd.converter.TeamVoConverter;
import com.lesports.qmt.sbd.model.Team;
import com.lesports.qmt.sbd.service.TeamWebService;
import com.lesports.qmt.sbd.service.support.AbstractSbdWebService;
import com.lesports.qmt.sbd.utils.QmtSearchApis;
import com.lesports.qmt.sbd.utils.QmtSearchData;
import com.lesports.qmt.sbd.vo.TeamVo;
import com.lesports.query.InternalCriteria;
import com.lesports.query.InternalQuery;
import com.lesports.utils.CallerUtils;
import com.lesports.utils.LeStringUtils;
import com.lesports.utils.math.LeNumberUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Created by lufei on 2016/10/27
 */
@Component("teamWebService")
public class TeamWebServiceImpl extends AbstractSbdWebService<TeamVo, Long> implements TeamWebService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TeamWebServiceImpl.class);

    @Resource
    private TeamVoConverter teamVoConverter;

    @Resource
    private TeamAdapter teamAdapter;

    @Override
    public TeamVo findOne(Long id, CallerParam caller) {
        Team team = SbdTeamInternalApis.getTeamById(id);
        if (team == null) {
            LOGGER.warn("fail to findOne, not exists, id:{}, caller:{}", id, caller);
            return null;
        }
        return teamVoConverter.toTVo(team, caller);
    }

    @Override
    public boolean save(TeamVo vo, CallerParam caller) {
        boolean isCreate = isCreateOp(vo);
        Team entity = vo.toModel();
        if (!isCreate) {
            Team old = SbdTeamInternalApis.getTeamById(entity.getId());
            if (old != null) {
                entity = teamAdapter.copyEditableProperties(old, vo);
            }
        }
        // set value into lang string list
        entity.setAllowCountries(getValidAllowCountries(entity.getAllowCountries(), null));
        entity.setMultiLangNames(setValueOfLanguage(entity.getMultiLangNames(), entity.getName(), isCreate ? null : caller.getLanguage()));
        entity.setMultiLangDesc(setValueOfLanguage(entity.getMultiLangDesc(), entity.getDesc(), isCreate ? null : caller.getLanguage()));
        entity.setMultiLangCities(setValueOfLanguage(entity.getMultiLangCities(), entity.getCity(), isCreate ? null : caller.getLanguage()));
        entity.setMultiLangNicknames(setValueOfLanguage(entity.getMultiLangNicknames(), entity.getNickname(), isCreate ? null : caller.getLanguage()));
        entity.setMultiLangHomeGrounds(setValueOfLanguage(entity.getMultiLangHomeGrounds(), entity.getHomeGround(), isCreate ? null : caller.getLanguage()));
        if (entity.getLogo() != null) {
            entity.setMultiCounLogos(CallerUtils.setValueOfCountry(entity.getMultiCounLogos(), entity.getLogo().getUrl(), caller.getCountry()));
        }
        // set default value
        entity.setName(getDefaultValueOfMultiLang(entity.getMultiLangNames()));
        entity.setDesc(getDefaultValueOfMultiLang(entity.getMultiLangDesc()));
        entity.setCity(getDefaultValueOfMultiLang(entity.getMultiLangCities()));
        entity.setNickname(getDefaultValueOfMultiLang(entity.getMultiLangNicknames()));
        entity.setHomeGround(getDefaultValueOfMultiLang(entity.getMultiLangHomeGrounds()));
        long id = SbdTeamInternalApis.saveTeam(entity, true);
        return setEntityId(entity, id);
    }

    @Override
    public boolean save(TeamVo entity) {
        return false;
    }

    @Override
    public Long saveWithId(TeamVo entity) {
        return null;
    }

    @Override
    public TeamVo findOne(Long id) {
        Team team = SbdTeamInternalApis.getTeamById(id);
        return new TeamVo(team);
    }

    @Override
    public boolean delete(Long id) {
        return SbdTeamInternalApis.deleteTeam(id);
    }

    @Override
    public long countByQuery(InternalQuery query, CallerParam caller) {
        query = addCountryCriteria(query, caller);
        return SbdTeamInternalApis.countTeamsByQuery(query);
    }


    protected InternalQuery buildCriteria(Map<String, Object> params, QmtPageParam pageParam) {
        InternalQuery query = new InternalQuery();
        if (MapUtils.isNotEmpty(params)) {
            String name = MapUtils.getString(params, "name");
            if (StringUtils.isNotBlank(name)) {
                Pattern pattern = getNamePattern(name);
                query.addCriteria(InternalCriteria.where("multi_lang_names.value").regex(pattern));
            }
            long gameFType = MapUtils.getLongValue(params, "gameFType");
            if (gameFType > 0) {
                query.addCriteria(InternalCriteria.where("game_f_type").is(gameFType));
            }
            String teamType = MapUtils.getString(params, "teamType");
            if (StringUtils.isNotBlank(teamType)) {
                query.addCriteria(InternalCriteria.where("team_type").is(teamType));
            }
            long id = MapUtils.getLongValue(params, "id");
            if (id > 0) {
                query.addCriteria(InternalCriteria.where("id").is(id));
            }
        }
        query.addCriteria(InternalCriteria.where("deleted").is(false));
        query.with(getValidPageable(pageParam));
        return query;
    }

    @Override
    public List<TeamVo> findByQuery(InternalQuery query, CallerParam caller) {
        query = addCountryCriteria(query, caller);
        List<Team> teams = SbdTeamInternalApis.getTeamsByQuery(query);
        return Lists.transform(teams, new Function<Team, TeamVo>() {
            @Nullable
            @Override
            public TeamVo apply(@Nullable Team input) {
                return teamVoConverter.toTVo(input, caller);
            }
        });
    }

    /*@Override
    public QmtPage<TeamVo> list(Map<String, Object> params, QmtPageParam pageParam, CallerParam caller) {
        InternalQuery criteria = buildCriteria(params, pageParam);
        return list(criteria, pageParam, caller);
    }*/

    @Override
    public QmtPage<TeamVo> list(Map<String, Object> params, QmtPageParam pageParam, CallerParam caller) {
        params.put("sortBy", "id");
        params.put("desc", true);
        QmtSearchData qmtSearchData = QmtSearchApis.searchData(params, Team.class);
        List<Team> teams = qmtSearchData.getRows();
        List<TeamVo> teamVos = Lists.transform(teams, new Function<Team, TeamVo>() {
            @Nullable
            @Override
            public TeamVo apply(@Nullable Team input) {
                return teamVoConverter.toTVo(input, caller);
            }
        });
        return new QmtPage<>(teamVos, pageParam, qmtSearchData.getTotal());
    }

    @Override
    public boolean updateOnlineStatus(long id, boolean online, CallerParam caller) {
        Team team = SbdTeamInternalApis.getTeamById(id);
        if (team != null) {
            team.setOnlineLanguages(setOnlineLanguages(team.getOnlineLanguages(), online, caller.getLanguage()));
            return SbdTeamInternalApis.saveTeam(team, true) > 0;
        }
        return false;
    }


    @Override
    protected boolean isCreateOp(TeamVo entity) {
        if (LeNumberUtils.toLong(entity.getId()) > 0) {
            return false;
        }
        return true;
    }

    @Override
    public List<TeamVo> getTeamsByIds(String ids, CallerParam caller) {
        List<Team> teams = SbdTeamInternalApis.getTeamsByIds(LeStringUtils.commaString2LongList(ids));
        return Lists.transform(teams, new Function<Team, TeamVo>() {
            @Nullable
            @Override
            public TeamVo apply(@Nullable Team input) {
                return teamVoConverter.toTVo(input, caller);
            }
        });
    }
}
