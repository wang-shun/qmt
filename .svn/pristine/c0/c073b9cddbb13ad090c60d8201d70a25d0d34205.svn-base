package com.lesports.qmt.tlive.api.resources;

import com.lesports.jersey.AlternateMediaType;
import com.lesports.jersey.annotation.LJSONP;
import com.lesports.jersey.core.LeStatus;
import com.lesports.jersey.exception.LeWebApplicationException;
import com.lesports.sms.api.vo.TVote;
import com.lesports.sms.client.TextLiveApis;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;

/**
 * Created by lufei1 on 2015/9/17.
 */
@Path("/")
public class VoteResource {

    private static final Logger LOG = LoggerFactory.getLogger(TextLiveResource.class);


    @POST
    @LJSONP
    @Path("votes")
    @Produces({AlternateMediaType.UTF_8_APPLICATION_JSON})
    public TVote getLiveMessageIdsByTextLiveId(@QueryParam("voteId") long voteId,
                                               @QueryParam("optionId") long optionId) {
        try {
            return TextLiveApis.addVote(voteId, optionId);
        } catch (LeWebApplicationException e) {
            LOG.error(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GET
    @LJSONP
    @Path("votes/{voteId}")
    @Produces({AlternateMediaType.UTF_8_APPLICATION_JSON})
    public TVote getLiveMessageIdsByTextLiveId(@QueryParam("voteId") long voteId) {
        try {
            return TextLiveApis.getVote(voteId);
        } catch (LeWebApplicationException e) {
            LOG.error(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
