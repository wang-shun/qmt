package com.lesports.qmt.ipad.api.resources;

import com.lesports.jersey.AlternateMediaType;
import com.lesports.jersey.annotation.LJSONP;
import com.lesports.jersey.core.LeStatus;
import com.lesports.jersey.exception.LeWebApplicationException;
import com.lesports.qmt.web.api.core.service.TopicService;
import com.lesports.qmt.web.api.core.util.ResponseUtils;
import com.lesports.qmt.web.api.core.vo.TopicInfo;
import com.lesports.qmt.web.api.core.vo.TopicItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.*;
import java.util.List;
import java.util.Map;

/**
 * lesports-projects
 * Adapt search data for le sports app.
 *
 * @author pangchuanxiao
 * @since 15-6-27
 */
@Path("/topics")
public class TopicResource {
    private static final Logger LOG = LoggerFactory.getLogger(TopicResource.class);

    @Inject
	TopicService topicService;

    /**
     * list subjects by page and count.
     *
     * @param page
     * @param count
     * @return list of {@link com.lesports.qmt.web.api.core.vo.TopicItem}
     */
    @GET
    @LJSONP
    @Path("/")
    @Produces({AlternateMediaType.UTF_8_APPLICATION_JSON})
    public Map<String, Object> list(@QueryParam("cid") @DefaultValue("0") int channelId,
                                    @QueryParam("page") @DefaultValue("1") int page,
                                    @QueryParam("count") @DefaultValue("20") int count,
									@QueryParam("secondCg") @DefaultValue("0") int secondCg) {
        try {
            List<TopicItem> items = topicService.searchCmsList(channelId, page, count, secondCg);
            return ResponseUtils.createPageResponse(page, items);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }
    /**
     * find one subject by id.
     *
     * @param id
     * @return {@link TopicInfo}
     */
    @GET
    @LJSONP
    @Path("/{id}")
    @Produces({AlternateMediaType.UTF_8_APPLICATION_JSON})
    public TopicInfo get(@PathParam("id") @DefaultValue("0") int id) {
        try {
            return topicService.get(id);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * find videos in one subject.
     *
     * @param id
     * @return {@link TopicInfo}
     */
    @GET
    @LJSONP
    @Path("/{id}/videos")
    @Produces({AlternateMediaType.UTF_8_APPLICATION_JSON})
    public Map<String, Object> get(@PathParam("id") @DefaultValue("0") int id,
                         @QueryParam("page") @DefaultValue("1")int page,
                         @QueryParam("count")@DefaultValue("5")int count) {
        try {
            List<TopicInfo.VideoInfo> videoInfos = topicService.getVideos(id, page ,count);
            return ResponseUtils.createPageResponse(page, videoInfos);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
