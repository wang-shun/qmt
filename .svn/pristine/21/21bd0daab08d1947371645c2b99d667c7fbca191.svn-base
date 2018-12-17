package com.lesports.qmt.app.api.resources;

import com.google.common.base.Preconditions;
import com.lesports.jersey.AlternateMediaType;
import com.lesports.jersey.annotation.LJSONP;
import com.lesports.jersey.core.LeStatus;
import com.lesports.jersey.exception.LeWebApplicationException;
import com.lesports.jersey.support.model.CallerBean;
import com.lesports.jersey.support.model.PageBean;
import com.lesports.qmt.sbd.api.dto.TPlayer;
import com.lesports.qmt.sbd.api.service.GetPlayers4SimpleSearchParam;
import com.lesports.qmt.sbd.client.SbdPlayerApis;
import com.lesports.qmt.web.api.core.service.PlayerService;
import com.lesports.qmt.web.api.core.vo.PlayerMixedVo;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.*;
import java.util.List;
import java.util.Map;

/**
 * Created by gengchengliang on 2015/6/18.
 */
@Path("/")
public class PlayerResource {

    private static final Logger LOG = LoggerFactory.getLogger(PlayerResource.class);

    @Inject
    private PlayerService playerService;

    @GET
    @LJSONP
    @Path("players/{id}")
    @Produces({AlternateMediaType.UTF_8_APPLICATION_JSON})
    public TPlayer getPlayerById(@PathParam("id") long id,
                                 @BeanParam CallerBean callerBean) {
        try {
            Preconditions.checkArgument(id != 0, "请传入球员id");
            return SbdPlayerApis.getTPlayerById(id, callerBean.getCallerParam());
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GET
    @LJSONP
    @Path("players/leId/{leId}")
    @Produces({AlternateMediaType.UTF_8_APPLICATION_JSON})
    public TPlayer getPlayerByLeId(@PathParam("leId") long leId,
                                   @BeanParam PageBean pageBean,
                                   @BeanParam CallerBean callerBean) {
        try {
            Preconditions.checkArgument(leId != 0, "请传入球员的乐词id");
            GetPlayers4SimpleSearchParam p = new GetPlayers4SimpleSearchParam();
            p.setLeciId(leId);
            List<TPlayer> tPlayers = SbdPlayerApis.getPlayers4SimpleSearch(p, pageBean.getPageParam(), callerBean.getCallerParam());
            if (CollectionUtils.isEmpty(tPlayers)) {
                return null;
            }
            return tPlayers.get(0);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GET
    @LJSONP
    @Path("players")
    @Produces({AlternateMediaType.UTF_8_APPLICATION_JSON})
    public List<TPlayer> getPlayersByFirstLetter(@QueryParam("letter") String letter,
                                                 @QueryParam("name") String name,
                                                 @BeanParam PageBean pageBean,
                                                 @BeanParam CallerBean callerBean) {
        try {
            GetPlayers4SimpleSearchParam p = new GetPlayers4SimpleSearchParam();
            p.setFirstLetter(letter);
            p.setName(name);
            return SbdPlayerApis.getPlayers4SimpleSearch(p, pageBean.getPageParam(), callerBean.getCallerParam());
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GET
    @LJSONP
    @Path("players/{id}/mixed")
    @Produces({AlternateMediaType.UTF_8_APPLICATION_JSON})
    public PlayerMixedVo getPlayersMix(@PathParam("id") long id,
                                      @QueryParam("cid")   long cid,
                                      @QueryParam("csid")  long csid,
                                      @BeanParam CallerBean callerBean) {
        try {
            Preconditions.checkArgument(id > 0, "please check you player id !");
            return playerService.getPlayerMixed(id,cid,csid, callerBean.getCallerParam());

        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GET
    @LJSONP
    @Path("players/{id}/stats")
    @Produces({AlternateMediaType.UTF_8_APPLICATION_JSON})
    public List<Map<String,String>> getPlayerStats(@PathParam("id") long id,
                                      @QueryParam("cid")   long cid,
                                      @QueryParam("csid")  long csid,
                                      @BeanParam CallerBean callerBean) {
        try {
            Preconditions.checkArgument(id > 0, "please check you player id !");
            Preconditions.checkArgument(cid != 0, "请传入赛事id");
            return playerService.getPlayerStats(id,cid,csid, callerBean.getCallerParam());
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }




    @GET
    @LJSONP
    @Path("players/hot")
    @Produces({AlternateMediaType.UTF_8_APPLICATION_JSON})
    public List<TPlayer> getPlayersByTid (@QueryParam("cid")  long  cid,
                                              @BeanParam PageBean pageBean,
                                              @BeanParam CallerBean callerBean) {
        try {
            Preconditions.checkArgument(cid != 0, "请传入赛事id");
            List<TPlayer> tPlayers = playerService.getHotPlayersByCid(cid,callerBean.getCallerParam());
            return tPlayers;
        }catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GET
    @LJSONP
    @Path("players/pk")
    @Produces({AlternateMediaType.UTF_8_APPLICATION_JSON})
    public Map<String,Object> getPKPlayersStats (@QueryParam("cid") long cid,
                                          @QueryParam("pid")  long  pid,
                                          @QueryParam("pkId")  long  pkId,
                                          @BeanParam PageBean pageBean,
                                          @BeanParam CallerBean callerBean) {
        try {
            Preconditions.checkArgument(cid != 0, "请传入赛事id");
            Preconditions.checkArgument(pid != 0, "请传入球员id");
            LOG.info("pkId: {} " ,pkId);
            return  playerService.getPKPlayersStats(pid,pkId,cid,callerBean.getCallerParam());
        }catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
