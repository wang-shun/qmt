package com.lesports.qmt.api.resources;

import com.lesports.jersey.AlternateMediaType;
import com.lesports.jersey.annotation.LJSONP;
import com.lesports.jersey.core.LeStatus;
import com.lesports.jersey.exception.LeWebApplicationException;
import com.lesports.utils.CarouselApis;
import com.lesports.utils.pojo.LiveChannels;
import com.lesports.utils.pojo.LivePlayBill;
import com.lesports.utils.pojo.LiveStreamRows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import java.util.List;

/**
 * lesports-projects.
 *
 * @author: pangchuanxiao
 * @since: 2015/7/14
 */
@Path("/carousels")
public class CarouselResource {
    private static final Logger LOG = LoggerFactory.getLogger(CarouselResource.class);

    @LJSONP
    @GET
    @Path("/channels")
    @Produces(AlternateMediaType.UTF_8_APPLICATION_JSON)
    public List<LiveChannels.LiveChannel> getCompetition(@QueryParam("clientId") @DefaultValue("0") int clientId) {
        try {
            return CarouselApis.getChannels(clientId);
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
