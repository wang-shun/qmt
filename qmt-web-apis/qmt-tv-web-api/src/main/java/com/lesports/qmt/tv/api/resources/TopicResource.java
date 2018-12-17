package com.lesports.qmt.tv.api.resources;

import com.lesports.jersey.AlternateMediaType;
import com.lesports.jersey.annotation.LJSONP;
import com.lesports.jersey.core.LeStatus;
import com.lesports.jersey.exception.LeWebApplicationException;
import com.lesports.jersey.support.model.CallerBean;
import com.lesports.qmt.web.api.core.service.TopicService;
import com.lesports.qmt.web.api.core.service.VideoService;
import com.lesports.qmt.web.api.core.vo.TvTopicInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.*;

/**
 * lesports-projects
 * Adapt search data for lesports tv.
 *
 * @author ruiyuansheng
 * @since 16-1-6
 */
@Path("/topics")
public class TopicResource {
    private static final Logger LOG = LoggerFactory.getLogger(TopicResource.class);

    @Inject
    TopicService topicService;
    @Inject
    VideoService videoService;

    /**
     *
     * @param id
     * @param callerBean
     * @return
     */
    @GET
    @LJSONP
    @Path("/{id}")
    @Produces({AlternateMediaType.UTF_8_APPLICATION_JSON})
    public TvTopicInfo get(@PathParam("id") @DefaultValue("0") int id,
                           @BeanParam CallerBean callerBean) {
        try {
            return topicService.getTvTopicById(id,callerBean.getCallerParam());
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }



}
