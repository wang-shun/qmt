package com.lesports.qmt.ipad.api.resources;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.lesports.jersey.AlternateMediaType;
import com.lesports.jersey.annotation.LJSONP;
import com.lesports.jersey.annotation.cache.LOG_URL;
import com.lesports.jersey.core.LeStatus;
import com.lesports.jersey.exception.LeWebApplicationException;
import com.lesports.jersey.support.model.CallerBean;
import com.lesports.qmt.sbd.api.dto.TDetailMatch;
import com.lesports.qmt.sbd.api.dto.TMatch;
import com.lesports.qmt.sbd.api.service.GetMacthActionsParam;
import com.lesports.qmt.sbd.client.SbdMatchApis;
import com.lesports.qmt.web.api.core.service.MatchService;
import com.lesports.qmt.web.api.core.vo.MatchAction;
import com.lesports.qmt.web.api.core.vo.MatchAgainst;
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

//    /**
//     * 通过球队id获取该球队的赛程
//     *
//     * @param competitorId
//     * @param csid
//     * @param page
//     * @param count
//     * @return
//     */
//    @LJSONP
//    @GET
//    @Path("matches/team/{competitorId}")
//    @Produces(AlternateMediaType.UTF_8_APPLICATION_JSON)
//    public Map<String, Object> getByTeam(@PathParam("competitorId") long competitorId,
//                                         @QueryParam("csid") @DefaultValue("-1") int csid,
//                                         @BeanParam CallerBean callerBean,
//                                         @QueryParam("page") @DefaultValue("1") int page,
//                                         @QueryParam("count") @DefaultValue("20") int count) {
//
//        try {
//            Preconditions.checkArgument(competitorId != 0, "competitorId can not be null!");
//            PageParam pageRequest = new PageParam();
//            pageRequest.setCount(count);
//            pageRequest.setPage(page > 0 ? page : 1);
//            List<TMatch> matches = SbdMatchApis.getMatchesByCompetitorId(competitorId, csid, pageRequest, callerBean.getCallerParam());
//            if (matches == null) {
//                matches = Collections.EMPTY_LIST;
//            }
//            Map<String, Object> result = Maps.newHashMap();
//            result.put("matches", matches);
//            result.put("count", matches.size());
//            return result;
//        } catch (LeWebApplicationException e) {
//            LOG.error(e.getMessage(), e);
//            throw e;
//        } catch (Exception e) {
//            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
//        }
//    }


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
     * 获取赛程信息
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
            return null;//matchService.getMatchAgainst(id, callerBean.getCallerParam()); TODO 无移植过来 @种林
        } catch (LeWebApplicationException e) {
            LOG.error(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
