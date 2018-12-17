package com.lesports.qmt.op.web.api.resources;


import com.lesports.LeConstants;
import com.lesports.api.common.CallerParam;
import com.lesports.api.common.CountryCode;
import com.lesports.api.common.LanguageCode;
import com.lesports.jersey.exception.LeWebApplicationException;
import com.lesports.qmt.op.web.api.core.service.CompetitionService;
import com.lesports.qmt.op.web.api.core.vo.baidu.CompetitionVo;
import com.lesports.qmt.op.web.api.core.vo.baidu.SiteMapVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * Created by lufei1 on 2015/8/19.
 */
@Path("/")
public class CompetitionResource {

    private static final Logger LOG = LoggerFactory.getLogger(CompetitionResource.class);

    @Inject
    private CompetitionService competitionService;

    /**
     * 获取赛事及赛事视频信息
     *
     * @param id
     * @return
     */
    @GET
    @Path("competitions/baidu/{id}")
    @Produces(MediaType.APPLICATION_XML)
    public CompetitionVo getCompetition(@PathParam("id") long id,
                                        @QueryParam("currentPage") @DefaultValue("1") int currentPage,
                                        @QueryParam("perPage") @DefaultValue("25") int perPage) {
        CompetitionVo competitionVo = new CompetitionVo();
        try {
            CallerParam callerParam = new CallerParam();
            callerParam.setCallerId(LeConstants.LESPORTS_PC_CALLER_CODE);
            callerParam.setCountry(CountryCode.CN);
            callerParam.setLanguage(LanguageCode.ZH_CN);

            competitionVo = competitionService.getCompetitionVo(id,currentPage,perPage,callerParam);
        } catch (LeWebApplicationException e) {
            LOG.error(e.getMessage(), e);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return competitionVo;
    }

    /**
     * 获取赛事及赛事视频信息
     *
     * @param id
     * @return
     */
    @GET
    @Path("competitions/baidu")
    @Produces(MediaType.APPLICATION_XML)
    public SiteMapVo getCompetitionSite(@QueryParam("id") @DefaultValue("1") long id) {
        SiteMapVo siteMapVo = new SiteMapVo();
        try {
            siteMapVo = competitionService.getSiteMapVo(id);
        } catch (LeWebApplicationException e) {
            LOG.error(e.getMessage(), e);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return siteMapVo;
    }
}
