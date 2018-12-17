package com.lesports.qmt.tv.api.resources;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import com.lesports.LeConstants;
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
import com.lesports.utils.LeProperties;
import com.lesports.utils.LeStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.*;
import java.util.*;


/**
 * User: ellios
 * Time: 15-6-14 : 下午10:01
 */
@Path("/")
public class MatchResource {

    private static final Logger LOG = LoggerFactory.getLogger(MatchResource.class);

    private static final String basketballCardCid = LeProperties.getString("basketball.card.cid");

    @Inject
    MatchService matchService;


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
    public Map<String,Object> getMatchActionsByTypes(@PathParam("matchId") long mid,
                                                     @QueryParam("types") String types,
                                                     @BeanParam CallerBean callerBean) {
        try {
            Preconditions.checkArgument(StringUtils.isNotBlank(types), "types can not be null !");
            Preconditions.checkArgument(mid != 0, "mid can not be null !");
            GetMacthActionsParam p = new GetMacthActionsParam();
            p.setMid(mid);
            p.setTypes(LeStringUtils.commaString2LongList(types));
            Map<String, Object> results = Maps.newHashMap();
            TMatch tMatch = SbdMatchApis.getTMatchById(mid, callerBean.getCallerParam());
            if(null != tMatch) {
                results.put("status", tMatch.getStatus().getValue());
                results.put(LeConstants.ENTRIES_KEY,  matchService.getMatchActionsByTimeLine(p, callerBean.getCallerParam()));
            }
            return results;
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
    public Map<String,Object> getCompetitorStat(@PathParam("matchId") long id,
                                                @BeanParam CallerBean callerBean) {
        try {
            Preconditions.checkArgument(id != 0, "id can not be null !");
            Map<String, Object> results = Maps.newHashMap();
            TDetailMatch tDetailMatch = SbdMatchApis.getTDetailMatchById(id, callerBean.getCallerParam());

            if(null != tDetailMatch) {
                results.put("status", tDetailMatch.getStatus().getValue());
                if(basketballCardCid.contains(tDetailMatch.getCid()+"") ) {
                    results.put(LeConstants.ENTRIES_KEY, matchService.getNBAPlayerStatsOfMatch(id,callerBean.getCallerParam()));
                }else{
                    results.put(LeConstants.ENTRIES_KEY, tDetailMatch.getCompetitorStats());
                }
            }

            return results;
        } catch (LeWebApplicationException e) {
            LOG.error(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
