package com.lesports.qmt.api.resources;

import com.google.common.base.Preconditions;
import com.lesports.jersey.AlternateMediaType;
import com.lesports.jersey.annotation.LJSONP;
import com.lesports.jersey.annotation.cache.LOG_URL;
import com.lesports.jersey.core.LeStatus;
import com.lesports.jersey.exception.LeWebApplicationException;
import com.lesports.jersey.support.model.CallerBean;
import com.lesports.qmt.web.api.core.service.EpisodeService;
import com.lesports.qmt.web.api.core.vo.DetailEpisodeVo;
import com.lesports.qmt.web.api.core.vo.PollingEpisodeVo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.*;
import java.util.List;

/**
 * Created by gengchengliang on 2015/6/18.
 */
@Path("/sms/tv/v1")
public class TvEpisodeResource {

    private static final Logger LOG = LoggerFactory.getLogger(TvEpisodeResource.class);

    @Inject
    private EpisodeService episodeService;

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
                                          @QueryParam("fromType") @DefaultValue("0") int fromType,
                                          @BeanParam CallerBean caller) {
        try {
            //FIXME:暂时不用可以注释掉
//            if(1 == fromType){//如果是致新节目单id,转成节目id
//                TConfig tConfig = configService.getConfigByTypeAndVersion(ConfigType.TV_GLOBAL, "PROGRAM_EPISODE_MAP", caller.getCallerParam());
//                if(null != tConfig){
//                    Map<String,String> data = tConfig.getData();
//                    if(null != data){
//                        id = LeNumberUtils.toLong(data.get(id + ""));
//                    }
//
//                }
//            }
            DetailEpisodeVo detailEpisodeVo = episodeService.getEpisodeByIdRealtime(id,  caller.getCallerParam());
            if(null != detailEpisodeVo) {
                detailEpisodeVo.setRecordedId(0L);
            }

            return detailEpisodeVo;
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
    public List<PollingEpisodeVo> getEpisodesByIds(@QueryParam("ids") String ids, @BeanParam CallerBean caller) {
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

}
