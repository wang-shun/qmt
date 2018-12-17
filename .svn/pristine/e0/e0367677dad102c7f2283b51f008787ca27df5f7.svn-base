package com.lesports.qmt.tv.api.resources;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.lesports.LeConstants;
import com.lesports.api.common.CallerParam;
import com.lesports.jersey.AlternateMediaType;
import com.lesports.jersey.annotation.LJSONP;
import com.lesports.jersey.core.LeStatus;
import com.lesports.jersey.exception.LeWebApplicationException;
import com.lesports.jersey.support.model.CallerBean;
import com.lesports.qmt.config.api.dto.ActivityType;
import com.lesports.qmt.config.api.dto.GetActivitiesParam;
import com.lesports.qmt.web.api.core.service.ActivityService;
import com.lesports.qmt.web.api.core.service.VideoService;
import com.lesports.qmt.web.api.core.vo.Activity;
import com.lesports.qmt.web.api.core.vo.VideoVo;
import com.lesports.utils.PageUtils;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.*;
import java.util.List;
import java.util.Map;

/**
 * 活动推广位
 * User: ellios
 * Time: 15-7-11 : 下午12:06
 */
@Path("/activities")
public class ActivityResource {

    private static final Logger LOG = LoggerFactory.getLogger(ActivityResource.class);
    @Inject
    ActivityService activityService;

    @Inject
    VideoService videoService;


    @GET
    @LJSONP
    @Path("/images")
    @Produces({AlternateMediaType.UTF_8_APPLICATION_JSON})
    public Map<String,Object> getTvStartUpLogByResourceType(@QueryParam("activityType")@DefaultValue("10") int activityType,
                                                            @QueryParam("relatedId") long relatedId,
                                                            @QueryParam("type") int type,
                                                            @QueryParam("count")@DefaultValue("6") int count,
                                                            @BeanParam CallerBean callerBean) {
        try {
            Map<String,Object> maps = Maps.newHashMap();
            CallerParam callerParam = callerBean.getCallerParam();
            GetActivitiesParam p = new GetActivitiesParam();
            p.setActivityType(ActivityType.findByValue(activityType));
            List<Activity> activities = activityService.getActivities(activityType, "", PageUtils.createPageParam(1, 1), callerParam);
            Activity activity = null;
            if(CollectionUtils.isNotEmpty(activities)){
                activity = activities.get(0);
            }
            if(null == activity && 12 == activityType){
                List<VideoVo> videos = Lists.newArrayList();
                if(type == 1){
                    videos = videoService.getRelatedVideosByEid(relatedId, callerParam, PageUtils.createPageParam(1,count));
                }else if(type == 2) {
                    videos = videoService.getRelatedVideos(relatedId, PageUtils.createPageParam(1,count), callerParam);
                }else{
                    videos = videoService.getLatestVideos(callerParam,PageUtils.createPageParam(1,count));
                }
                maps.put(LeConstants.ENTRIES_KEY,videos);
            }
            if(null != activity){
                maps.put("imageUrl",activity.getImageUrl());
                maps.put("desc",activity.getName());
            }
            return maps;
        } catch (LeWebApplicationException e) {
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
