package com.lesports.qmt.tv.api.resources;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.lesports.id.client.LeIdApis;
import com.lesports.jersey.AlternateMediaType;
import com.lesports.jersey.annotation.LJSONP;
import com.lesports.jersey.annotation.cache.LOG_URL;
import com.lesports.jersey.core.LeStatus;
import com.lesports.jersey.exception.LeWebApplicationException;
import com.lesports.jersey.support.model.CallerBean;
import com.lesports.jersey.support.model.PageBean;
import com.lesports.qmt.sbc.api.common.VideoContentType;
import com.lesports.qmt.sbc.api.service.GetRelatedVideosParam;
import com.lesports.qmt.web.api.core.service.VideoService;
import com.lesports.qmt.web.api.core.util.ResponseUtils;
import com.lesports.qmt.web.api.core.vo.VideoVo;
import com.lesports.utils.LeStringUtils;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.*;
import java.util.List;
import java.util.Map;

/**
* Created by gengchengliang on 2015/7/31.
*/
@Path("/")
public class VideoResource {

    private static final Logger LOG = LoggerFactory.getLogger(NewsResource.class);

    @Inject
    private VideoService videoService;

    /**
     * 获取视频
     *
     * @param id 视频id
     * @return
     */
    @LOG_URL
    @GET
    @LJSONP
    @Path("/videos/{id}")
    @Produces({AlternateMediaType.UTF_8_APPLICATION_JSON})
    public VideoVo getVideoById(@PathParam("id") long id,
                                @BeanParam CallerBean callerBean) {
        try {
            Preconditions.checkArgument(id != 0, "id can not be null!");
            if (id == 0) {
                LOG.error("fail to getRelateVideos. relatedId : {} is illegal", id);
                throw new LeWebApplicationException("关联id非法", LeStatus.PARAM_INVALID);
            }
            return videoService.getVideoById(LeIdApis.convertMmsVideoIdToLeSportsVideoId(id),callerBean.getCallerParam());
        } catch (LeWebApplicationException e) {
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 获取相关视频列表
     * @param eid
     * @param pageBean
     * @param callerBean
     * @return
     */
    @GET
    @LJSONP
    @Path("related/videos")
    @Produces({AlternateMediaType.UTF_8_APPLICATION_JSON})
    public Map<String, Object> getRelateVideos(@QueryParam("eid") long eid,
                                               @BeanParam PageBean pageBean,
                                               @BeanParam CallerBean callerBean) {
        try {

            Preconditions.checkArgument(eid != 0, "eid can not be null!");
            return videoService.getEpisodeRelatedVideos(eid,callerBean.getCallerParam(),pageBean.getPageParam());

        } catch (LeWebApplicationException e) {
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 获取此视频相关的其它视频
     *
     * @param vid

     * @return
     */
    @GET
    @LJSONP
    @Path("/videos/{vid}/related")
    @Produces({AlternateMediaType.UTF_8_APPLICATION_JSON})
    public Map<String, Object> getRelatedVideos(@PathParam("vid") long vid,
                                                @BeanParam PageBean pageBean,
                                                @BeanParam CallerBean callerBean) {
        try {
            Preconditions.checkArgument(vid != 0, "vid can not be null!");
            return videoService.getRelatedVideos4Tv(LeIdApis.convertMmsVideoIdToLeSportsVideoId(vid), callerBean.getCallerParam(), pageBean.getPageParam());
        } catch (LeWebApplicationException e) {
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 获取视频列表
     *
     * @param relatedId 大项,小项,赛事,比赛,球员,球队,节目
     * @param type      类型: -1:全部 0:回放 1:集锦 2:战报 3:正片 20:其它
     * @param pageBean
     * @param callerBean
     * @return
     */
    @GET
    @LJSONP
    @Path("/videos")
    @Produces({AlternateMediaType.UTF_8_APPLICATION_JSON})
    public Map<String, Object> getRelateVideos(@QueryParam("relatedId") long relatedId,
                                               @QueryParam("type") @DefaultValue("-1") String type,
                                               @BeanParam PageBean pageBean,
                                               @BeanParam CallerBean callerBean) {
        try {
            Preconditions.checkArgument(relatedId != 0, "relatedId can not be null!");
            if (relatedId == 0) {
                LOG.error("fail to getRelateVideos. relatedId : {} is illegal", relatedId);
                throw new LeWebApplicationException("关联id非法", LeStatus.PARAM_INVALID);
            }
            List<VideoVo> videos = Lists.newArrayList();
            GetRelatedVideosParam p = new GetRelatedVideosParam();
            p.setRelatedId(relatedId);
            if (!type.equals("-1")) {
                List<VideoContentType> typeList = getVideoContentTypes(type);
                p.setTypes(typeList);
            }
            videos = videoService.getVideosByRelatedIdAndCid(p,pageBean.getPageParam(),callerBean.getCallerParam());
            return ResponseUtils.createPageResponse(pageBean.getPage(),"", videos);
        } catch (LeWebApplicationException e) {
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private List<VideoContentType> getVideoContentTypes(String type) {
        List<Integer> types = LeStringUtils.commaString2IntegerList(type);
        List<VideoContentType> typeList = Lists.newArrayList();
        if(CollectionUtils.isNotEmpty(types)){
            for(Integer videoType : types){
                if(null != VideoContentType.findByValue(videoType)) {
                    typeList.add(VideoContentType.findByValue(videoType));
                }
            }
        }
        return typeList;
    }

}
