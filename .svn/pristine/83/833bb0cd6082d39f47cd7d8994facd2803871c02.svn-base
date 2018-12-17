package com.lesports.qmt.app.api.resources;

import com.google.common.base.Preconditions;
import com.lesports.jersey.AlternateMediaType;
import com.lesports.jersey.annotation.LJSONP;
import com.lesports.jersey.annotation.cache.LOG_URL;
import com.lesports.jersey.core.LeStatus;
import com.lesports.jersey.exception.LeWebApplicationException;
import com.lesports.jersey.support.model.CallerBean;
import com.lesports.qmt.sbd.api.dto.TCompetitorStat;
import com.lesports.qmt.sbd.api.dto.TSimplePlayer;
import com.lesports.qmt.sbd.api.service.GetMacthActionsParam;
import com.lesports.qmt.sbd.client.SbdMatchApis;
import com.lesports.qmt.web.api.core.service.MatchService;
import com.lesports.qmt.web.api.core.vo.BestPlayerStatsVo;
import com.lesports.qmt.web.api.core.vo.MatchAgainst;
import com.lesports.qmt.web.api.core.vo.Squad;
import com.lesports.utils.LeStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.*;
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
     * 获取两队比赛回顾信息,两队历史交锋,近期战绩,将要进行的比赛
     * id ID
     */
    @LOG_URL
    @LJSONP
    @GET
    @Path("matches/{id}/against")
    @Produces(AlternateMediaType.UTF_8_APPLICATION_JSON)
    public MatchAgainst getMatchAgainst(@PathParam("id") long id,
                                        @BeanParam CallerBean callerBean) {
        try {
            Preconditions.checkArgument(id != 0, "mid can not be null!");
            return matchService.getMatchAgainst(id, callerBean.getCallerParam());
            // TODO 无移植过来 @种林
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

    /**
     * 通过类型获取比赛的实时统计
     * matchId 赛程id
     * type   比赛统计的类型:进球,黄牌,红牌等
     */
    @LOG_URL
    @LJSONP
    @GET
    @Path("matches/{matchId}/action")
    @Produces(AlternateMediaType.UTF_8_APPLICATION_JSON)
    public List getMatchActionsByTypes(@PathParam("matchId") long mid,
                                       @QueryParam("types") String types,
                                       @BeanParam CallerBean callerBean) {
        try {
            Preconditions.checkArgument(StringUtils.isNotBlank(types), "types can not be null !");
            Preconditions.checkArgument(mid != 0, "mid can not be null !");
            GetMacthActionsParam p = new GetMacthActionsParam();
            p.setMid(mid);
            p.setTypes(LeStringUtils.commaString2LongList(types));
            return matchService.getMatchActionsByTypes(p, callerBean.getCallerParam());
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
            return SbdMatchApis.getCompetitorStatsOfMatch(id, callerBean.getCallerParam());
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

}
