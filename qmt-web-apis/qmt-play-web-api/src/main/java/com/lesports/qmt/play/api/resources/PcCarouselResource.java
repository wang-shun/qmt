package com.lesports.qmt.play.api.resources;

import com.google.common.collect.Maps;
import com.lesports.LeConstants;
import com.lesports.api.common.Platform;
import com.lesports.jersey.AlternateMediaType;
import com.lesports.jersey.annotation.LJSONP;
import com.lesports.jersey.core.LeStatus;
import com.lesports.jersey.exception.LeWebApplicationException;
import com.lesports.jersey.support.model.CallerBean;
//import com.lesports.sms.api.common.Platform;
//import com.lesports.sms.api.web.core.service.CarouselService;
import com.lesports.qmt.web.api.core.service.CarouselService;
import com.lesports.utils.CarouselApis;
import com.lesports.utils.pojo.LiveChannelDetail;
import com.lesports.utils.pojo.LivePlayBill;
import com.lesports.utils.pojo.LiveStreamRows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.*;
import java.util.List;
import java.util.Map;

/**
 * 轮播台
 *
 * @author: pangchuanxiao
 * @since: 2015/7/14
 */
@Path("/sms/v1/carousels")
public class PcCarouselResource {
    private static final Logger LOG = LoggerFactory.getLogger(PcCarouselResource.class);
    @Inject
    private CarouselService carouselService;

    @LJSONP
    @GET
    @Path("/channelDetail")
    @Produces(AlternateMediaType.UTF_8_APPLICATION_JSON)
    public LiveChannelDetail getChannelDetail(@QueryParam("clientId") @DefaultValue("0") int clientId,
                                              @QueryParam("channelId") @DefaultValue("0") int channelId) {
        try {
            return carouselService.getLiveChannelDetail(clientId, channelId);
        } catch (LeWebApplicationException e) {
            LOG.error(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @LJSONP
    @GET
    @Path("/channels")
    @Produces(AlternateMediaType.UTF_8_APPLICATION_JSON)
    public Map<String, Object> getCompetition(@QueryParam("clientId") @DefaultValue("0") int clientId,
                                                         @BeanParam CallerBean callerBean) {
        try {
            Map<String, Object> results = Maps.newHashMap();
            results.put(LeConstants.ENTRIES_KEY, carouselService.getChannels(Platform.PC, clientId, callerBean.getCallerParam()));
            return results;
        } catch (LeWebApplicationException e) {
            LOG.error(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @LJSONP
    @GET
    @Path("/playbill/current")
    @Produces(AlternateMediaType.UTF_8_APPLICATION_JSON)
    public LivePlayBill.CurrentRow getCurrent(@QueryParam("clientId") @DefaultValue("0") int clientId,
                                              @QueryParam("channelId") @DefaultValue("0") int channelId) {
        try {
            return CarouselApis.getCurrent(clientId, channelId);
        } catch (LeWebApplicationException e) {
            LOG.error(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @LJSONP
    @GET
    @Path("/playbill/wholeday")
    @Produces(AlternateMediaType.UTF_8_APPLICATION_JSON)
    public List<LivePlayBill.RowContent> getWholeDay(@QueryParam("clientId") @DefaultValue("0") int clientId,
                                                     @QueryParam("channelId") @DefaultValue("0") int channelId,
                                                     @QueryParam("date") @DefaultValue("0") String date) {
        try {
            return CarouselApis.getWholeDay(clientId, channelId, date);
        } catch (LeWebApplicationException e) {
            LOG.error(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @LJSONP
    @GET
    @Path("/stream")
    @Produces(AlternateMediaType.UTF_8_APPLICATION_JSON)
    public LiveStreamRows getStream(@QueryParam("clientId") @DefaultValue("0") int clientId,
                                    @QueryParam("channelId") @DefaultValue("0") int channelId) {
        try {
            return CarouselApis.getStreams(clientId, channelId);
        } catch (LeWebApplicationException e) {
            LOG.error(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
