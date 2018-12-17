package com.lesports.qmt.tv.api.resources;

import com.google.common.base.Preconditions;
import com.lesports.jersey.AlternateMediaType;
import com.lesports.jersey.annotation.LJSONP;
import com.lesports.jersey.core.LeStatus;
import com.lesports.jersey.exception.LeWebApplicationException;
import com.lesports.jersey.support.model.CallerBean;
import com.lesports.qmt.sbd.api.dto.TCompetitionSeason;
import com.lesports.qmt.sbd.client.SbdCompetitonSeasonApis;
import com.lesports.qmt.web.api.core.vo.CompetittionSeasonDateVo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;

/**
 * Created by ruiyuansheng on 2016/5/26.
 */
@Path("/competitions")
public class CompetitionResource {

    private static final Logger LOG = LoggerFactory.getLogger(CompetitionResource.class);
    /**
     * 通过赛事id获取赛季信息
     */
    @LJSONP
    @GET
    @Path("/{cid}/latestSeason")
    @Produces(AlternateMediaType.UTF_8_APPLICATION_JSON)
    public CompetittionSeasonDateVo getCompetitionSeasons (@PathParam("cid") long cid,
                                                     @BeanParam CallerBean callerBean){
        try {
            Preconditions.checkArgument(cid != 0, "请传入赛事id!");
            TCompetitionSeason tCompetitionSeason = SbdCompetitonSeasonApis.getLatestTCompetitionSeasonsByCid(cid, callerBean.getCallerParam());

            CompetittionSeasonDateVo competittionSeasonDateVo = new CompetittionSeasonDateVo();
            if(null == tCompetitionSeason){
                return competittionSeasonDateVo;
            }
            if(StringUtils.isNotEmpty(tCompetitionSeason.getStartTime()) && tCompetitionSeason.getStartTime().length() > 8) {
                competittionSeasonDateVo.setStartDate(tCompetitionSeason.getStartTime().substring(0, 8));
            }else{
                competittionSeasonDateVo.setStartDate("");
            }
            if(StringUtils.isNotEmpty(tCompetitionSeason.getEndTime()) && tCompetitionSeason.getEndTime().length() > 8) {
                competittionSeasonDateVo.setEndDate(tCompetitionSeason.getEndTime().substring(0, 8));
            }else{
                competittionSeasonDateVo.setEndDate("");
            }
            return competittionSeasonDateVo;
        }catch (LeWebApplicationException e){
            LOG.error(e.getMessage(), e);
            throw e;
        }catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
