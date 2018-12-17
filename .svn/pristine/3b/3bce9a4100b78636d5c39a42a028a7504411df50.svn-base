package com.lesports.qmt.app.api.resources;

import com.google.common.base.Preconditions;
import com.lesports.jersey.AlternateMediaType;
import com.lesports.jersey.annotation.LJSONP;
import com.lesports.jersey.annotation.cache.LOG_URL;
import com.lesports.jersey.core.LeStatus;
import com.lesports.jersey.exception.LeWebApplicationException;
import com.lesports.jersey.support.model.CallerBean;
import com.lesports.jersey.support.model.PageBean;
import com.lesports.qmt.sbd.api.dto.TPlayer;
import com.lesports.qmt.sbd.api.dto.TTeam;
import com.lesports.qmt.sbd.api.service.GetTeamsOfSeasonParam;
import com.lesports.qmt.sbd.client.SbdTeamApis;
import com.lesports.qmt.web.api.core.service.TeamService;
import com.lesports.qmt.web.api.core.vo.TeamMixedVo;
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
public class TeamResource {

    private static final Logger LOG = LoggerFactory.getLogger(TeamResource.class);

	@Inject
	private TeamService teamService;

    @LOG_URL
    @GET
    @LJSONP
    @Path("teams/{id}")
    @Produces({AlternateMediaType.UTF_8_APPLICATION_JSON})
    public TTeam getTeamById(@PathParam("id") long id,
							 @BeanParam CallerBean callerBean,
							 @QueryParam("idType") @DefaultValue("0") int idType) {
        try {
			Preconditions.checkArgument(id > 0, "please check you id ! when idType = 0 is teamId , when idType= 1 is campId");
			TTeam tTeam = null;
			if (idType == 0) {
				tTeam = SbdTeamApis.getTTeamById(id, callerBean.getCallerParam());
			} else if (idType == 1) {
				tTeam = SbdTeamApis.getTTeamByCampId(id, callerBean.getCallerParam());
			}
            return tTeam;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * @param cid  赛事id
     * @param csid 赛季id
     * @return
     */
    @LOG_URL
    @GET
    @LJSONP
    @Path("competitions/{cid}/teams")
    @Produces({AlternateMediaType.UTF_8_APPLICATION_JSON})
    public List<TTeam> getTeamsByCompetitionId(@PathParam("cid") long cid,
                                               @PathParam("csid") @DefaultValue("0") long csid,
                                               @BeanParam PageBean pageBean,
                                               @BeanParam CallerBean callerBean) {
        try {
            GetTeamsOfSeasonParam p = new GetTeamsOfSeasonParam();
            p.setCid(cid);
            p.setCsid(csid);
            return SbdTeamApis.getTeamsOfSeason(p, pageBean.getPageParam(), callerBean.getCallerParam());
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }


	/**
	 * @param menuId  菜单ID
	 * @return
	 */
	@LOG_URL
	@GET
	@LJSONP
	@Path("menu/{menuId}/teams")
	@Produces({AlternateMediaType.UTF_8_APPLICATION_JSON})
	public List<Map<String, Object>> getTeamsByCompetitionId(@PathParam("menuId") long menuId,
											   @BeanParam PageBean pageBean,
											   @BeanParam CallerBean callerBean) {
		try {
			Preconditions.checkArgument(menuId > 0, "please check you team id !");
			return null;//teamService.getFocusedTeamsByMenu(menuId, callerBean.getCallerParam()); todo
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
		}
	}


	@GET
	@LJSONP
	@Path("teams/{id}/mixed")
	@Produces({AlternateMediaType.UTF_8_APPLICATION_JSON})
	public TeamMixedVo getTeamMixed(@PathParam("id") long id,
							 @BeanParam CallerBean callerBean) {
		try {
			Preconditions.checkArgument(id > 0, "please check you team id !");
            LOG.info("callerBean: country"+ callerBean.getCountry()+" language: " + callerBean.getLanguage());
			return teamService.getTeamMixed(id, callerBean.getCallerParam());
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
		}
	}

    @GET
    @LJSONP
    @Path("teams/{tid}/teamSeason")
    @Produces({AlternateMediaType.UTF_8_APPLICATION_JSON})
    public List<TPlayer> getPlayersByTid (@PathParam("tid") long tid,
                                          @QueryParam("cid")  long  cid,
                                          @BeanParam PageBean pageBean,
                                          @BeanParam CallerBean callerBean) {
        try {
            Preconditions.checkArgument(tid != 0, "请传入球队的id");
            Preconditions.checkArgument(cid != 0, "请传入赛事id");
            List<TPlayer> tPlayers =  teamService.getPlayersByTidAndCid(tid,cid, callerBean.getCallerParam());
            if(CollectionUtils.isEmpty(tPlayers)){
                return null;
            }
            return tPlayers;
        }catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
