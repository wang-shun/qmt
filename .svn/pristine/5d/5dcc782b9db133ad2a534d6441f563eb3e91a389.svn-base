package com.lesports.qmt.sbd.service.impl;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.lesports.api.common.CallerParam;
import com.lesports.qmt.mvc.QmtPage;
import com.lesports.qmt.mvc.QmtPageParam;
import com.lesports.qmt.sbd.SbdCompetitionInternalApis;
import com.lesports.qmt.sbd.SbdCompetitionSeasonInternalApis;
import com.lesports.qmt.sbd.SbdPlayerInternalApis;
import com.lesports.qmt.sbd.SbdTeamInternalApis;
import com.lesports.qmt.sbd.converter.TeamSeasonVoConverter;
import com.lesports.qmt.sbd.model.*;
import com.lesports.qmt.sbd.service.TeamSeasonWebService;
import com.lesports.qmt.sbd.service.support.AbstractSbdWebService;
import com.lesports.qmt.sbd.vo.TeamSeasonVo;
import com.lesports.query.InternalCriteria;
import com.lesports.query.InternalQuery;
import com.lesports.utils.LeStringUtils;
import com.lesports.utils.math.LeNumberUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by lufei on 2016/10/27
 */
@Component("teamSeasonWebService")
public class TeamSeasonWebServiceImpl extends AbstractSbdWebService<TeamSeasonVo, Long> implements TeamSeasonWebService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TeamSeasonWebServiceImpl.class);

    @Resource
    private TeamSeasonVoConverter teamSeasonVoConverter;

    @Override
    public TeamSeasonVo findOne(Long id, CallerParam caller) {
        TeamSeason teamSeason = SbdCompetitionSeasonInternalApis.getTeamSeasonById(id);
        if (teamSeason == null) {
            LOGGER.warn("fail to findOne, not exists, id:{}, caller:{}", id, caller);
            return null;
        }
        return teamSeasonVoConverter.toTVo(teamSeason, caller);
    }

    @Override
    public boolean save(TeamSeasonVo entity, CallerParam caller) {
        CompetitionSeason cs = SbdCompetitionSeasonInternalApis.getCompetitionSeasonById(entity.getCsid());
        entity.setAllowCountries(getValidAllowCountries(entity.getAllowCountries(), cs == null ? null : cs.getCid()));
        LOGGER.info("save entity:{} from web", entity);
        long id = SbdCompetitionSeasonInternalApis.saveTeamSeason(entity.toModel());
        return setEntityId(entity, id);
    }

    @Override
    public boolean save(TeamSeasonVo entity) {
        return false;
    }

    @Override
    public Long saveWithId(TeamSeasonVo entity) {
        return null;
    }

    @Override
    public TeamSeasonVo findOne(Long aLong) {
        return null;
    }

    @Override
    public boolean delete(Long id) {
        // why TeamSeason has no deleted field?
        return SbdCompetitionSeasonInternalApis.deleteTeamSeason(id);
    }

    @Override
    public long countByQuery(InternalQuery query, CallerParam caller) {
        query = addCountryCriteria(query, caller);
        return SbdCompetitionSeasonInternalApis.countTeamSeasonsByQuery(query);
    }

    @Override
    public List<TeamSeasonVo> findByQuery(InternalQuery query, CallerParam caller) {
        query = addCountryCriteria(query, caller);
        List<TeamSeason> teamSeasons = SbdCompetitionSeasonInternalApis.getTeamSeasonsByQuery(query);
        return Lists.transform(teamSeasons, new Function<TeamSeason, TeamSeasonVo>() {
            @Nullable
            @Override
            public TeamSeasonVo apply(@Nullable TeamSeason input) {
                TeamSeasonVo teamSeasonVo = new TeamSeasonVo(input);
                Team team = SbdTeamInternalApis.getTeamById(input.getTid());
                if (team != null) {
                    teamSeasonVo.setTeamName(team.getName());

                }
                CompetitionSeason competitionSeason = SbdCompetitionSeasonInternalApis.getCompetitionSeasonById(teamSeasonVo.getCsid());
                if (competitionSeason != null) {
                    Competition competition = SbdCompetitionInternalApis.getCompetitionById(competitionSeason.getCid());
                    if (competition != null) {
                        teamSeasonVo.setcName(competition.getName());
                    }
                    teamSeasonVo.setSeason(competitionSeason.getSeason());
                    teamSeasonVo.setOverSeason(competitionSeason.getOverSeason());
                }
                //教练
                Player coach = SbdPlayerInternalApis.getPlayerById(input.getCoachId());
                if (coach != null) {
                    teamSeasonVo.setCoach(coach.getName());
                }
                //队长
                Player captain = SbdPlayerInternalApis.getPlayerById(input.getCurrentCaptain());
                if (captain != null) {
                    teamSeasonVo.setCaptain(captain.getName());
                }
                return teamSeasonVo;
            }
        });
    }

    @Override
    public QmtPage<TeamSeasonVo> list(Map<String, Object> params, QmtPageParam pageParam, CallerParam caller) {
        return null;
    }

    @Override
    public boolean updateOnlineStatus(long id, boolean online, CallerParam caller) {
        return false;
    }

    public List<TeamSeasonVo> findByParams(Map<String, Object> params, CallerParam caller) {
        InternalQuery query = buildCriteria(params);
        return findByQuery(query, caller);
    }

    @Override
    public TeamSeason.TeamPlayer getTeamPlayerByTsidAndPid(long tsid, long pid, CallerParam caller) {
        TeamSeason.TeamPlayer teamPlayer = null;
        if (pid > 0) {
            TeamSeason teamSeason = findOne(tsid, caller);
            if (teamSeason != null && CollectionUtils.isNotEmpty(teamSeason.getPlayers())) {
                for (TeamSeason.TeamPlayer tp : teamSeason.getPlayers()) {
                    if (tp.getPid() == pid) {
                        teamPlayer = tp;
                        break;
                    }
                }
            }
        }
        return teamPlayer;
    }

    @Override
    public boolean updateTeamSeason(long csid, String tids, CallerParam caller) {
        List<Long> teamIds = LeStringUtils.commaString2LongList(tids);
        try {
            if (CollectionUtils.isNotEmpty(teamIds)) {
                for (int i = 0; i < teamIds.size(); i++) {
                    Map<String, Object> params = Maps.newHashMap();
                    params.put("csid", csid);
                    params.put("tid", teamIds.get(i));
                    List<TeamSeasonVo> oldTeamSeasons = this.findByParams(params, caller);
                    //增加
                    if (CollectionUtils.isEmpty(oldTeamSeasons)) {
                        TeamSeasonVo teamSeasonVo = new TeamSeasonVo();
                        teamSeasonVo.setTid(teamIds.get(i));
                        teamSeasonVo.setCsid(csid);
                        this.save(teamSeasonVo, caller);
                    }
                }
            }
            //删除多余
            Map<String, Object> params = Maps.newHashMap();
            params.put("csid", csid);
            List<TeamSeasonVo> teamSeasonVos = this.findByParams(params, caller);
            if (CollectionUtils.isNotEmpty(teamSeasonVos)) {
                for (TeamSeasonVo vo : teamSeasonVos) {
                    if (!teamIds.contains(vo.getTid())) {
                        this.delete(vo.getId());
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.error("update teamSeason error. csid : {},tids:{}", csid, teamIds, e);
            return false;
        }
        return true;
    }

    @Override
    public void addTeamPlayer(long tsid, long pid, String pnumber) {
        TeamSeason ts = SbdCompetitionSeasonInternalApis.getTeamSeasonById(tsid);
        if (ts != null) {
            TeamSeason.TeamPlayer teamPlayer = new TeamSeason.TeamPlayer();
            teamPlayer.setPid(pid);
            teamPlayer.setNumber(pnumber);
            ts.addPlayer(teamPlayer);
            SbdCompetitionSeasonInternalApis.saveTeamSeason(ts);
        }
    }

    @Override
    public void deleteTeamPlayer(long tsid, long pid) {
        TeamSeason ts = SbdCompetitionSeasonInternalApis.getTeamSeasonById(tsid);
        if (ts != null) {
            TeamSeason.TeamPlayer teamPlayer = new TeamSeason.TeamPlayer();
            teamPlayer.setPid(pid);
            ts.deletePlayer(teamPlayer);
            SbdCompetitionSeasonInternalApis.saveTeamSeason(ts);
        }
    }

    @Override
    protected boolean isCreateOp(TeamSeasonVo entity) {
        if (LeNumberUtils.toLong(entity.getId()) > 0) {
            return false;
        }
        return true;
    }

    protected InternalQuery buildCriteria(Map<String, Object> params) {
        InternalQuery query = new InternalQuery();
        if (MapUtils.isNotEmpty(params)) {
            long tid = MapUtils.getLongValue(params, "tid");
            if (tid > 0) {
                query.addCriteria(InternalCriteria.where("tid").is(tid));
            }
            long csid = MapUtils.getLongValue(params, "csid");
            if (csid > 0) {
                query.addCriteria(InternalCriteria.where("csid").is(csid));
            }

            long cid = MapUtils.getLongValue(params, "cid");
            if (cid > 0) {
                query.addCriteria(InternalCriteria.where("cids").is(cid));
            }

        }
        query.addCriteria(InternalCriteria.where("deleted").is(false));
        return query;
    }
}
