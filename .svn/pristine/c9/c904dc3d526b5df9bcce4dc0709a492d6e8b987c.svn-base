package com.lesports.qmt.app.api.resources;

import com.google.common.base.Preconditions;
import com.lesports.jersey.AlternateMediaType;
import com.lesports.jersey.annotation.LJSONP;
import com.lesports.jersey.annotation.cache.LOG_URL;
import com.lesports.jersey.core.LeStatus;
import com.lesports.jersey.exception.LeWebApplicationException;
import com.lesports.jersey.support.model.CallerBean;
import com.lesports.jersey.support.model.PageBean;
import com.lesports.qmt.config.api.dto.TActivity;
import com.lesports.qmt.web.api.core.service.ActivityService;
import com.lesports.qmt.web.api.core.vo.Activity;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.*;
import java.util.List;

/**
 * 活动推广位
 * User: ellios
 * Time: 15-7-11 : 下午12:06
 */
@Path("/")
public class ActivityResource {

    private static final Logger LOG = LoggerFactory.getLogger(ActivityResource.class);

    @Inject
    private ActivityService activityService;

    /**
     * 适用于在没加帖子类型之前的接口(以后的接口请用 getActivitiesByResourceType 替代)
     *
     * @param type
     * @param callerBean
     * @return
     */
    @GET
    @LJSONP
    @Path("activities")
    @Produces({AlternateMediaType.UTF_8_APPLICATION_JSON})
    public List<Activity> getActivities(@QueryParam("type") @DefaultValue("2") int type,
                                         @BeanParam CallerBean callerBean,
										 @BeanParam PageBean pageBean) {
        try {
            return activityService.getActivities(type, "0,1", pageBean.getPageParam(), callerBean.getCallerParam());
        } catch (LeWebApplicationException e) {
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @LOG_URL
    @GET
    @LJSONP
    @Path("activities/{id}")
    @Produces({AlternateMediaType.UTF_8_APPLICATION_JSON})
    public TActivity getActivity(@PathParam("id") long id,
                                 @BeanParam CallerBean callerBean) {
        try {
            Preconditions.checkArgument(id > 0, "illegal id : " + id);
            return null;//SopsApis.getTActivityById(id, callerBean.getCallerParam()); todo 映射那几个ID
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GET
    @LJSONP
    @Path("/activityType/{activityType}/activities")
    @Produces({AlternateMediaType.UTF_8_APPLICATION_JSON})
    public List<Activity> getActivitiesByResourceType(@PathParam("activityType") int activityType,
                                                       @QueryParam("resourceType") String resourceType,
													   @BeanParam PageBean pageBean,
                                                       @BeanParam CallerBean callerBean) {
        try {
            Preconditions.checkArgument(activityType > 0, "illegal activityType");
            Preconditions.checkArgument(StringUtils.isNotBlank(resourceType), "resourceType empty.");
            return activityService.getActivities(activityType, resourceType, pageBean.getPageParam(), callerBean.getCallerParam());
        } catch (LeWebApplicationException e) {
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
