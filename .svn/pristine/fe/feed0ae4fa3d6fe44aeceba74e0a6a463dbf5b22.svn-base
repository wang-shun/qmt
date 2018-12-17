package com.lesports.qmt.api.resources;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.lesports.jersey.AlternateMediaType;
import com.lesports.jersey.annotation.LJSONP;
import com.lesports.jersey.annotation.cache.LOG_URL;
import com.lesports.jersey.core.LeStatus;
import com.lesports.jersey.exception.LeWebApplicationException;
import com.lesports.jersey.support.model.CallerBean;
import com.lesports.qmt.sbd.api.dto.TCompetitorStat;
import com.lesports.qmt.sbd.api.dto.TDetailMatch;
import com.lesports.qmt.sbd.api.dto.TMatch;
import com.lesports.qmt.sbd.api.dto.TSimplePlayer;
import com.lesports.qmt.sbd.api.service.GetMacthActionsParam;
import com.lesports.qmt.sbd.client.SbdMatchApis;
import com.lesports.qmt.sbd.model.MatchStats;
import com.lesports.qmt.web.api.core.service.MatchService;
import com.lesports.qmt.web.api.core.vo.BestPlayerStatsVo;
import com.lesports.qmt.web.api.core.vo.MatchAction;
import com.lesports.qmt.web.api.core.vo.Squad;
import com.lesports.utils.LeStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.*;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * User: ellios
 * Time: 15-6-14 : 下午10:01
 */
@Path("/")
public class MatchResource {

    private static final Logger LOG = LoggerFactory.getLogger(MatchResource.class);

    @Inject
    MatchService matchService;

    /**
     * 查询赛程详情
     * <p/>
     * ids 赛程id,多个用英文逗号隔开,每次最多10个
     */
    @GET
    @LJSONP
    @Path("matches")
    @Produces({AlternateMediaType.UTF_8_APPLICATION_JSON})
    public Map<String, Object> getByIds(@QueryParam("ids") String ids,
                                        @BeanParam CallerBean callerBean) {
        try {
            Preconditions.checkArgument(StringUtils.isNotBlank(ids), "请传入要查询的id");
            List<Long> idList = Lists.newArrayList();
            String[] idtmp = ids.split(",");
            for (String i : idtmp) {
                idList.add(Long.parseLong(i));
            }
            Map<String, Object> result = Maps.newHashMap();
            List<TMatch> matches = SbdMatchApis.getTMatchesByIds(idList, callerBean.getCallerParam());
            result.put("matches", matches);
            result.put("count", matches.size());
            return result;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }


    /**
     * 获取赛程详情
     * id ID
     */
    @LOG_URL
    @LJSONP
    @GET
    @Path("matches/detail/{id}")
    @Produces(AlternateMediaType.UTF_8_APPLICATION_JSON)
    public TDetailMatch getDetailMatchByEid(@PathParam("id") long id,
                                            @BeanParam CallerBean callerBean) {
        try {
            TDetailMatch match = SbdMatchApis.getTDetailMatchById(id, callerBean.getCallerParam());
            return match;
        } catch (LeWebApplicationException e) {
            LOG.error(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 获取赛程信息
     * id ID
     */
    @LOG_URL
    @LJSONP
    @GET
    @Path("matches/{id}")
    @Produces(AlternateMediaType.UTF_8_APPLICATION_JSON)
    public TMatch getMatchById(@PathParam("id") long id,
                               @BeanParam CallerBean callerBean) {
        try {
            TMatch match = SbdMatchApis.getTMatchById(id, callerBean.getCallerParam());
            return match;
        } catch (LeWebApplicationException e) {
            LOG.error(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @LOG_URL
    @LJSONP
    @GET
    @Path("matches/{matchId}/action")
    @Produces(AlternateMediaType.UTF_8_APPLICATION_JSON)
    public List<MatchAction> getMatchActionsByTimeLine(@PathParam("matchId") long mid,
                                                       @QueryParam("types") String types,
                                                       @BeanParam CallerBean callerBean) {
        try {
            Preconditions.checkArgument(mid != 0, "mid can not be null !");
            GetMacthActionsParam p = new GetMacthActionsParam();
            p.setMid(mid);
            List<Long> typeList = Lists.newArrayList();
            if (StringUtils.isNotBlank(types)) {
                typeList = LeStringUtils.commaString2LongList(types);
            }
            p.setTypes(typeList);
            return matchService.getMatchActionsByTimeLine(p, callerBean.getCallerParam());
        } catch (LeWebApplicationException e) {
            LOG.error(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 通过类型获取比赛的实时统计
     * matchId 赛程id
     * type   比赛统计的类型:进球,黄牌,红牌等
     */
    @LOG_URL
    @LJSONP
    @GET
    @Path("matches/{matchId}/action/type/{type}")
    @Produces(AlternateMediaType.UTF_8_APPLICATION_JSON)
    public List getMatchActionsByType(@PathParam("matchId") long mid,
                                      @PathParam("type") long type,
                                      @BeanParam CallerBean callerBean) {
        try {
            Preconditions.checkArgument(type != 0, "type can not be null !");
            Preconditions.checkArgument(mid != 0, "mid can not be null !");
            GetMacthActionsParam p = new GetMacthActionsParam();
            p.setMid(mid);
            List<Long> typeList = Lists.newArrayList();
            typeList.add(type);
            p.setTypes(typeList);
            return matchService.getMatchActions(p, callerBean.getCallerParam());
        } catch (LeWebApplicationException e) {
            LOG.error(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }


    /**
     * 获取比赛数据
     * matchId 赛程id
     */
    @LOG_URL
    @LJSONP
    @GET
    @Path("matches/{matchId}/stat")
    @Produces(AlternateMediaType.UTF_8_APPLICATION_JSON)
    public List<TCompetitorStat> getCompetitorStat(@PathParam("matchId") long id,
                                                   @BeanParam CallerBean callerBean) {
        try {
            TDetailMatch tDetailMatch = SbdMatchApis.getTDetailMatchById(id, callerBean.getCallerParam());
            if (null != tDetailMatch) {
                return tDetailMatch.getCompetitorStats();
            }
            return Collections.EMPTY_LIST;
        } catch (LeWebApplicationException e) {
            LOG.error(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 获取球员统计数据
     * matchId 赛程id
     */
    @LOG_URL
    @LJSONP
    @GET
    @Path("matches/{matchId}/playerStat")
    @Produces(AlternateMediaType.UTF_8_APPLICATION_JSON)
    public Map<Long, List<List<TSimplePlayer>>> getPlayerStats(@PathParam("matchId") long id,
                                                               @BeanParam CallerBean callerBean) {
        try {
            return matchService.getPlayerStatsOfMatch(id, callerBean.getCallerParam());
        } catch (LeWebApplicationException e) {
            LOG.error(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 获取最佳球员统计数据
     * matchId 赛程id
     */
    @LOG_URL
    @LJSONP
    @GET
    @Path("matches/{matchId}/bestPlayerStat")
    @Produces(AlternateMediaType.UTF_8_APPLICATION_JSON)
    public Map<Long, List<BestPlayerStatsVo>> getBestPlayerStats(@PathParam("matchId") long id,
                                                                 @BeanParam CallerBean callerBean) {
        try {
            return matchService.getBestPlayerStatsOfMatch(id, callerBean.getCallerParam());
        } catch (LeWebApplicationException e) {
            LOG.error(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }

	/**
	 * 获取赛程的出场阵容
	 * matchId 赛程id
	 */
	@LOG_URL
	@LJSONP
	@GET
	@Path("matches/{matchId}/squads")
	@Produces(AlternateMediaType.UTF_8_APPLICATION_JSON)
	public List<Squad> getSquads(@PathParam("matchId") long id,
								 @BeanParam CallerBean callerBean) {
		try {
			return matchService.getSquads(id, callerBean.getCallerParam());
		} catch (LeWebApplicationException e) {
			LOG.error(e.getMessage(), e);
			throw e;
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
