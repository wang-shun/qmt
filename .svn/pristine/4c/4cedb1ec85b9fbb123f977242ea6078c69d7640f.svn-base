package com.lesports.qmt.api.resources;

import com.google.common.base.Preconditions;
import com.lesports.jersey.AlternateMediaType;
import com.lesports.jersey.annotation.LJSONP;
import com.lesports.jersey.annotation.cache.LOG_URL;
import com.lesports.jersey.core.LeStatus;
import com.lesports.jersey.exception.LeWebApplicationException;
import com.lesports.jersey.support.model.CallerBean;
import com.lesports.qmt.sbd.api.dto.TCompetitorStat;
import com.lesports.qmt.web.api.core.service.EpisodeService;
import com.lesports.qmt.web.api.core.service.MatchService;
import com.lesports.qmt.web.api.core.vo.DetailEpisodeVo;
import com.lesports.qmt.web.api.core.vo.HallEpisodeVo;
import com.lesports.qmt.web.api.core.vo.PollingEpisodeVo;
import com.lesports.qmt.web.api.core.vo.Squad;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.*;
import java.util.List;

/**
 * Created by gengchengliang on 2015/6/18.
 */
@Path("/sms/ipad/v1")
public class IPadEpisodeResource {

    private static final Logger LOG = LoggerFactory.getLogger(IPadEpisodeResource.class);

    @Inject
    private EpisodeService episodeService;
    @Inject
    private MatchService matchService;
    /**
     * 获取节目
     * <p/>
     * id ID
     */
    @LOG_URL
    @LJSONP
    @GET
    @Path("/episodes/{id}")
    @Produces(AlternateMediaType.UTF_8_APPLICATION_JSON)
    public DetailEpisodeVo getEpisodeById(@PathParam("id") long id,
                                          @BeanParam CallerBean caller) {
        try {
            return episodeService.getEpisodeByIdRealtime(id, caller.getCallerParam());
        } catch (LeWebApplicationException e) {
            LOG.error(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 轮询比分和状态的接口
     * <p/>
     * id ID
     */
    @LJSONP
    @GET
    @Path("/refresh/episodes")
    @Produces(AlternateMediaType.UTF_8_APPLICATION_JSON)
    public List<PollingEpisodeVo> getEpisodesByIds(@QueryParam("ids") String ids,
                                                   @BeanParam CallerBean caller) {
        try {
            Preconditions.checkArgument(StringUtils.isNotBlank(ids), "请传入ids,多个已英文逗号隔开");
            return episodeService.refreshEpisodesByIdsRealtime(ids, caller.getCallerParam());
        } catch (LeWebApplicationException e) {
            LOG.error(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 批量获取节目信息
     * <p/>
     * ids
     */
    @LJSONP
    @GET
    @Path("/multi/episodes")
    @Produces(AlternateMediaType.UTF_8_APPLICATION_JSON)
    public List<HallEpisodeVo> getMultiEpisodesByIds(@QueryParam("ids") String ids,
                                                     @BeanParam CallerBean caller) {
        try {
            Preconditions.checkArgument(StringUtils.isNotBlank(ids), "请传入ids,多个已英文逗号隔开");
            return episodeService.getMultiEpisodesByIds4AppRealtime(ids, caller.getCallerParam());
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
    public List<TCompetitorStat> getCompetitorStat (@PathParam("matchId") long id,
                                                    @BeanParam CallerBean caller){
        try {
            return matchService.getCompetitorStatsRealtime(id, caller.getCallerParam());
        }catch (LeWebApplicationException e){
            LOG.error(e.getMessage(), e);
            throw e;
        }catch (Exception e) {
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
    public List<Squad> getSquads (@PathParam("matchId") long id,
                                  @BeanParam CallerBean caller){
        try {
            return matchService.getSquadsRealtime(id, caller.getCallerParam());
        }catch (LeWebApplicationException e){
            LOG.error(e.getMessage(), e);
            throw e;
        }catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
