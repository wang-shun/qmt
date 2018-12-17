package com.lesports.qmt.tv.api.resources;

import com.google.common.base.Preconditions;
import com.lesports.jersey.AlternateMediaType;
import com.lesports.jersey.annotation.LJSONP;
import com.lesports.jersey.core.LeStatus;
import com.lesports.jersey.exception.LeWebApplicationException;
import com.lesports.jersey.support.model.CallerBean;
import com.lesports.qmt.web.api.core.service.PlayerService;
import com.lesports.qmt.web.api.core.vo.PlayerVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.*;
import java.util.Map;

/**
 * Created by gengchengliang on 2015/6/18.
 */
@Path("/players")
public class PlayerResource {

	private static final Logger LOG = LoggerFactory.getLogger(PlayerResource.class);

    @Inject
    private PlayerService playerService;

	@GET
	@LJSONP
	@Path("/{id}")
	@Produces({AlternateMediaType.UTF_8_APPLICATION_JSON})
	public PlayerVo getPlayerById (@PathParam("id") long id,
                                  @BeanParam CallerBean callerBean) {
		try {
			Preconditions.checkArgument(id != 0, "请传入球员id");
            return playerService.getPlayerById(id,callerBean.getCallerParam());
		}catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
		}
	}

    @GET
    @LJSONP
    @Path("/teamMates")
    @Produces({AlternateMediaType.UTF_8_APPLICATION_JSON})
    public Map<String,Object> getRelatedPlayers (@QueryParam("id") long id,
                                   @BeanParam CallerBean callerBean) {
        try {
            Preconditions.checkArgument(id != 0, "请传入球员id");
            return playerService.getPlayerTeamMates(id, callerBean.getCallerParam());
        }catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }



}
