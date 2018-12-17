package com.lesports.qmt.api.resources;

import com.google.common.base.Preconditions;
import com.lesports.jersey.AlternateMediaType;
import com.lesports.jersey.annotation.LJSONP;
import com.lesports.jersey.core.LeStatus;
import com.lesports.jersey.exception.LeWebApplicationException;
import com.lesports.jersey.support.model.CallerBean;
import com.lesports.jersey.support.model.PageBean;
import com.lesports.qmt.sbd.api.dto.TCompetition;
import com.lesports.qmt.sbd.api.dto.TCompetitionSeason;
import com.lesports.qmt.sbd.api.service.GetCompetitionsParam;
import com.lesports.qmt.sbd.client.SbdCompetitionApis;
import com.lesports.qmt.sbd.client.SbdCompetitonSeasonApis;
import com.lesports.qmt.web.api.core.service.MatchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.*;
import java.util.List;

/**
 * Created by gengchengliang on 2015/6/18.
 */
@Path("/")
public class CompetitionResource {

	private static final Logger LOG = LoggerFactory.getLogger(CompetitionResource.class);

	@Inject
    MatchService matchService;

	/**
	 * 获取赛事信息
	 *
	 * id ID
	 */
	@LJSONP
	@GET
	@Path("competitions/{id}")
	@Produces(AlternateMediaType.UTF_8_APPLICATION_JSON)
	public TCompetition getCompetition (@PathParam("id") long id,
                                        @BeanParam CallerBean callerBean){
		try {
			Preconditions.checkArgument(id != 0, "请传入赛事id!");
			return SbdCompetitionApis.getTCompetitionById(id, callerBean.getCallerParam());
		}catch (LeWebApplicationException e){
			LOG.error(e.getMessage(), e);
			throw e;
		}catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
		}
	}

    /**
     * 获取赛事信息
     *
     * id ID
     */
    @LJSONP
    @GET
    @Path("/competitions")
    @Produces(AlternateMediaType.UTF_8_APPLICATION_JSON)
    public List<TCompetition> getCompetitions (@QueryParam("gameType") @DefaultValue("0") long gameType,
											   @QueryParam("chargeable") @DefaultValue("-1") int chargeable,
                                               @BeanParam PageBean pageBean,
											   @BeanParam CallerBean callerBean){
        try {
            GetCompetitionsParam p = new GetCompetitionsParam();
            p.setGameType(gameType);
			p.setChargeable(chargeable);
            return SbdCompetitionApis.getTCompetitons4SimpleSearch(p, pageBean.getPageParam(),callerBean.getCallerParam());
        }catch (LeWebApplicationException e){
            LOG.error(e.getMessage(), e);
            throw e;
        }catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }

	/**
	 * 通过赛事id获取赛季信息
	 */
	@LJSONP
	@GET
	@Path("/competitions/{cid}/seasons")
	@Produces(AlternateMediaType.UTF_8_APPLICATION_JSON)
	public List<TCompetitionSeason> getCompetitionSeasons (@PathParam("cid") long cid,
                                                           @BeanParam CallerBean callerBean){
		try {
			Preconditions.checkArgument(cid != 0, "请传入赛事id!");
			return SbdCompetitonSeasonApis.getSeasonsOfCompetition(cid, callerBean.getCallerParam());
		}catch (LeWebApplicationException e){
			LOG.error(e.getMessage(), e);
			throw e;
		}catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * 通过赛事id获取赛季信息
	 */
	@LJSONP
	@GET
	@Path("/competitions/{cid}/latestSeason")
	@Produces(AlternateMediaType.UTF_8_APPLICATION_JSON)
	public TCompetitionSeason getLatestCompetitionSeason(@PathParam("cid") long cid,
														   @BeanParam CallerBean callerBean){
		try {
			Preconditions.checkArgument(cid != 0, "请传入赛事id!");
			return SbdCompetitonSeasonApis.getLatestTCompetitionSeasonsByCid(cid, callerBean.getCallerParam());
		} catch (LeWebApplicationException e){
			LOG.error(e.getMessage(), e);
			throw e;
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
		}
	}


}
