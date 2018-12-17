package com.lesports.qmt.api.resources;

import com.google.common.base.Preconditions;
import com.lesports.jersey.AlternateMediaType;
import com.lesports.jersey.annotation.LJSONP;
import com.lesports.jersey.annotation.cache.LOG_URL;
import com.lesports.jersey.core.LeStatus;
import com.lesports.jersey.exception.LeWebApplicationException;
import com.lesports.jersey.support.model.CallerBean;
import com.lesports.jersey.support.model.PageBean;
import com.lesports.qmt.sbd.api.dto.TTeam;
import com.lesports.qmt.sbd.api.service.GetTeams4SimpleSearchParam;
import com.lesports.qmt.sbd.api.service.GetTeamsOfSeasonParam;
import com.lesports.qmt.sbd.client.SbdTeamApis;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import java.util.List;

/**
 * Created by gengchengliang on 2015/6/18.
 */
@Path("/")
public class TeamResource {

	private static final Logger LOG = LoggerFactory.getLogger(TeamResource.class);

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
	@GET
	@LJSONP
	@Path("teams/leId/{leId}")
	@Produces({AlternateMediaType.UTF_8_APPLICATION_JSON})
	public TTeam getTeamByLeId (@PathParam("leId") long leId,
                                @BeanParam PageBean pageBean,
                                @BeanParam CallerBean callerBean) {
		try {
			Preconditions.checkArgument(leId != 0, "请传入球队的乐词id");
            GetTeams4SimpleSearchParam p = new GetTeams4SimpleSearchParam();
            p.setLeciId(leId);
            List<TTeam> tTeams =  SbdTeamApis.getTeams4SimpleSearch(p, pageBean.getPageParam(), callerBean.getCallerParam());
            if(CollectionUtils.isEmpty(tTeams)){
                return null;
            }
            return tTeams.get(0);
		}catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GET
	@LJSONP
	@Path("teams/octopusName")
	@Produces({AlternateMediaType.UTF_8_APPLICATION_JSON})
	public TTeam getOctopusName (@QueryParam("name") String name,
                                 @BeanParam PageBean pageBean,
                                 @BeanParam CallerBean callerBean) {
		try {
			Preconditions.checkArgument(StringUtils.isNotBlank(name), "请传入球队的章鱼球队名称!");
            GetTeams4SimpleSearchParam p = new GetTeams4SimpleSearchParam();
            p.setOctopusName(name);
            List<TTeam> tTeams =  SbdTeamApis.getTeams4SimpleSearch(p, pageBean.getPageParam(), callerBean.getCallerParam());
            if(CollectionUtils.isEmpty(tTeams)){
                return null;
            }
            return tTeams.get(0);
		}catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * 通过首字母和球队类型和名字过滤
	 *
	 * @param letter
	 * @param teamType
	 * @param name
	 * @param pageBean
	 * @param callerBean
	 * @return
	 */
	@GET
	@LJSONP
	@Path("teams")
	@Produces({AlternateMediaType.UTF_8_APPLICATION_JSON})
	public List<TTeam> getTeamsByFirstLetter (@QueryParam("letter") String letter,
										@QueryParam("teamType") int teamType,
										@QueryParam("name") String name,
										@BeanParam PageBean pageBean,
                                        @BeanParam CallerBean callerBean) {
		try {
            GetTeams4SimpleSearchParam p = new GetTeams4SimpleSearchParam();
            p.setName(name);
            p.setFirstLetter(letter);
            p.setTeamType(teamType);
			return SbdTeamApis.getTeams4SimpleSearch(p, pageBean.getPageParam(), callerBean.getCallerParam());
		}catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 *
	 * @param cid 赛事id
	 * @param csid 赛季id
     * @param pageBean
     * @param callerBean
	 * @return
	 */
	@LOG_URL
	@GET
	@LJSONP
	@Path("competitions/{cid}/teams")
	@Produces({AlternateMediaType.UTF_8_APPLICATION_JSON})
	public List<TTeam> getTeamsByCompetitionId (@PathParam("cid") long cid,
												@PathParam("csid") @DefaultValue("0") long csid,
                                                @BeanParam PageBean pageBean,
                                                @BeanParam CallerBean callerBean) {
		try {
			Preconditions.checkArgument(cid != 0, "请传入赛事id");
            GetTeamsOfSeasonParam p = new GetTeamsOfSeasonParam();
            p.setCsid(csid);
            p.setCid(cid);
			return SbdTeamApis.getTeamsOfSeason(p,pageBean.getPageParam(),callerBean.getCallerParam());
		}catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
