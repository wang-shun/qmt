package com.lesports.qmt.tv.api.resources;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.lesports.LeConstants;
import com.lesports.api.common.Direction;
import com.lesports.api.common.Order;
import com.lesports.api.common.PageParam;
import com.lesports.api.common.Sort;
import com.lesports.jersey.AlternateMediaType;
import com.lesports.jersey.annotation.LJSONP;
import com.lesports.jersey.annotation.cache.LOG_URL;
import com.lesports.jersey.core.LeStatus;
import com.lesports.jersey.exception.LeWebApplicationException;
import com.lesports.jersey.support.model.CallerBean;
import com.lesports.qmt.sbc.api.service.GetEpisodesOfSeasonByMetaEntryIdParam;
import com.lesports.qmt.sbc.api.service.LiveShowStatusParam;
import com.lesports.qmt.sbc.api.service.LiveTypeParam;
import com.lesports.qmt.sbd.api.dto.TCompetitionSeason;
import com.lesports.qmt.sbd.api.dto.TTeam;
import com.lesports.qmt.sbd.api.dto.TTeamPlayer;
import com.lesports.qmt.sbd.api.dto.TTeamSeason;
import com.lesports.qmt.sbd.api.service.GetCompetitorSeasonStatsParam;
import com.lesports.qmt.sbd.api.service.GetTeamSeasonsParam;
import com.lesports.qmt.sbd.client.SbdCompetitonSeasonApis;
import com.lesports.qmt.sbd.client.SbdTeamApis;
import com.lesports.qmt.sbd.client.SbdTeamSeasonApis;
import com.lesports.qmt.web.api.core.service.CompetitionSeasonStatService;
import com.lesports.qmt.web.api.core.service.EpisodeService;
import com.lesports.qmt.web.api.core.service.TeamService;
import com.lesports.qmt.web.api.core.util.ResponseUtils;
import com.lesports.qmt.web.api.core.vo.CompetitionSeasonStatVo;
import com.lesports.qmt.web.api.core.vo.CompetitionSeasonVo;
import com.lesports.qmt.web.api.core.vo.TeamVo;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.*;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
* Created by gengchengliang on 2015/6/18.
*/
@Path("/teams")
public class TeamResource {

	private static final Logger LOG = LoggerFactory.getLogger(TeamResource.class);

    @Inject
    private TeamService teamService;


    @Inject
    private EpisodeService episodeService;

    @Inject
    private CompetitionSeasonStatService competitionSeasonStatService;


    @LOG_URL
	@GET
	@LJSONP
	@Path("/{id}")
	@Produces({AlternateMediaType.UTF_8_APPLICATION_JSON})
	public TeamVo getTeamById (@PathParam("id") long id,
                              @BeanParam CallerBean callerBean) {
		try {
			return teamService.getTeamById(id, callerBean.getCallerParam());
		}catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
		}
	}

    @LOG_URL
    @GET
    @LJSONP
    @Path("/competitorSeasons")
    @Produces({AlternateMediaType.UTF_8_APPLICATION_JSON})
    public List<CompetitionSeasonVo> getCompetitorSeasons (@QueryParam("tid") long tid,
                               @BeanParam CallerBean callerBean) {
        try {
            TTeam tTeam = SbdTeamApis.getTTeamById(tid, callerBean.getCallerParam());
            List<TCompetitionSeason> seasonsOfCompetition = SbdCompetitonSeasonApis.getSeasonsOfCompetition(tTeam.getCurrentCid(), callerBean.getCallerParam());
            List<CompetitionSeasonVo> competitionSeasonVos = Lists.newArrayList();
            if(CollectionUtils.isNotEmpty(seasonsOfCompetition)){
                for(TCompetitionSeason tCompetitionSeason : seasonsOfCompetition){
                    CompetitionSeasonVo competitionSeasonVo = new CompetitionSeasonVo();
                    competitionSeasonVo.setId(tCompetitionSeason.getId());
                    competitionSeasonVo.setName(tCompetitionSeason.getName());
                    competitionSeasonVos.add(competitionSeasonVo);
                }
            }

            return competitionSeasonVos;
        }catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @LJSONP
    @GET
    @Path("/episodes")
    @Produces(AlternateMediaType.UTF_8_APPLICATION_JSON)
    public Map<String, Object> getStepEpisodes(@QueryParam("tid") long tid,
                                               @QueryParam("csid") @DefaultValue("0") long csid,
                                               @QueryParam("page") @DefaultValue("1") int page,
                                               @QueryParam("count") @DefaultValue("20") int count,
                                               @BeanParam CallerBean callerBean) {

        try {
            GetEpisodesOfSeasonByMetaEntryIdParam p = createGetEpisodesOfSeasonByMetaEntryIdParam(tid, csid, callerBean);
            PageParam pageParam =new PageParam();
            pageParam.setPage(page);
            pageParam.setCount(count);
            if(page <= 0) {
                p.setLiveShowStatusParam(LiveShowStatusParam.LIVE_END);
                Sort sort = new Sort();
                sort.addToOrders(new Order("start_time", Direction.DESC));
                pageParam.setSort(sort);
                pageParam.setPage(Math.abs(page) + 1);
            }else{
                p.setLiveShowStatusParam(LiveShowStatusParam.LIVE_NOT_END);
                Sort sort = new Sort();
                sort.addToOrders(new Order("start_time", Direction.ASC));
                pageParam.setSort(sort);
            }
            p.setLiveTypeParam(LiveTypeParam.NOT_ONLY_LIVE);
            return ResponseUtils.createPageResponse(page, episodeService.getEpisodesByCidAndRound4TV(p, pageParam, callerBean.getCallerParam()));
        } catch (LeWebApplicationException e) {
            LOG.error(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private GetEpisodesOfSeasonByMetaEntryIdParam createGetEpisodesOfSeasonByMetaEntryIdParam(long tid, long csid, CallerBean callerBean) {
        GetEpisodesOfSeasonByMetaEntryIdParam p = new GetEpisodesOfSeasonByMetaEntryIdParam();
        if(tid > 0) {
            TTeam tTeam = SbdTeamApis.getTTeamById(tid, callerBean.getCallerParam());
            p.setCid(tTeam.getCurrentCid());
        }
        p.setCsid(csid);

        return p;
    }

    @LOG_URL
    @GET
    @LJSONP
    @Path("/squads")
    @Produces({AlternateMediaType.UTF_8_APPLICATION_JSON})
    public List<CompetitionSeasonStatVo> getSquads (@QueryParam("tid") long tid,
                                                @BeanParam CallerBean callerBean) {
        try {

            TTeam tTeam = SbdTeamApis.getTTeamById(tid, callerBean.getCallerParam());
            GetTeamSeasonsParam getTeamSeasonsParam = new GetTeamSeasonsParam();
            getTeamSeasonsParam.setTid(tid);
            getTeamSeasonsParam.setCsid(tTeam.getCurrentCsid());
            List<TTeamSeason> tTeamSeasons = SbdTeamSeasonApis.getTTeamSeasons(getTeamSeasonsParam, null, callerBean.getCallerParam());
            List<Long> pids = Lists.newArrayList();
            Map<Long,TTeamPlayer> playerMap = Maps.newHashMap();
            if(CollectionUtils.isEmpty(tTeamSeasons)){
               return Collections.EMPTY_LIST;
            }
            TTeamSeason tTeamSeason = tTeamSeasons.get(0);
            for (TTeamPlayer tTeamPlayer : tTeamSeason.getPlayers()) {
                pids.add(tTeamPlayer.getPid());
                playerMap.put(tTeamPlayer.getPid(),tTeamPlayer);
            }

            GetCompetitorSeasonStatsParam p = new GetCompetitorSeasonStatsParam();
            p.setCompetitorIds(pids);
            p.setCid(tTeam.getCurrentCid());
            p.setCsid(tTeam.getCurrentCsid());
            List<CompetitionSeasonStatVo> competitorSeasonStats = competitionSeasonStatService.getCompetitorSeasonStats(p, playerMap);

            return competitorSeasonStats;
        }catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @LOG_URL
    @GET
    @LJSONP
    @Path("/allTeams")
    @Produces({AlternateMediaType.UTF_8_APPLICATION_JSON})
    public Map<String, Object>  getAllTeams (@QueryParam("tid") long tid,
                                     @BeanParam CallerBean callerBean) {
        try {
            List<TeamVo> teamVos = Lists.newArrayList();
            TTeam tTeam = SbdTeamApis.getTTeamById(tid, callerBean.getCallerParam());
            GetTeamSeasonsParam getTeamSeasonsParam = new GetTeamSeasonsParam();
            getTeamSeasonsParam.setCsid(tTeam.getCurrentCsid());
            List<TTeamSeason> tTeamSeasons = SbdTeamSeasonApis.getTTeamSeasons(getTeamSeasonsParam, null, callerBean.getCallerParam());
            if(CollectionUtils.isNotEmpty(tTeamSeasons)){
                for (TTeamSeason tTeamSeason : tTeamSeasons) {
                    TeamVo teamVo = new TeamVo();
                    teamVo.setId(tTeamSeason.getTid());
                    teamVo.setName(tTeamSeason.getTeamName());
                    teamVo.setLogo(tTeamSeason.getTeamLogo());
                    teamVos.add(teamVo);
                }

            }

            Map<String, Object> results = Maps.newHashMap();
            results.put(LeConstants.ENTRIES_KEY, teamVos);
            TCompetitionSeason tCompetitionSeason = SbdCompetitonSeasonApis.getTCompetitionSeasonById(tTeam.getCurrentCsid(), callerBean.getCallerParam());
            if(null != tCompetitionSeason) {
                results.put("csName", tCompetitionSeason.getName());
            }

            return results;
        }catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }



}
