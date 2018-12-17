package com.lesports.qmt.op.web.api.resources;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.lesports.LeConstants;
import com.lesports.api.common.CallerParam;
import com.lesports.api.common.CountryCode;
import com.lesports.api.common.LanguageCode;
import com.lesports.api.common.PageParam;
import com.lesports.jersey.AlternateMediaType;
import com.lesports.jersey.annotation.ENCRYPT;
import com.lesports.jersey.annotation.LJSONP;
import com.lesports.jersey.core.LeStatus;
import com.lesports.jersey.exception.LeWebApplicationException;
import com.lesports.jersey.support.model.CallerBean;
import com.lesports.jersey.support.model.PageBean;
import com.lesports.model.VideoData;
import com.lesports.model.VideoSearch;
import com.lesports.qmt.op.web.api.core.creater.VideoVoCreater;
import com.lesports.qmt.op.web.api.core.service.VideoService;
import com.lesports.qmt.op.web.api.core.util.ResponseUtils;
import com.lesports.qmt.op.web.api.core.vo.VideoVo;
import com.lesports.qmt.op.web.api.core.vo.mbaidu.ShortVideos;
import com.lesports.qmt.sbc.api.dto.TVideo;
import com.lesports.qmt.sbc.client.QmtSbcApis;
import com.lesports.utils.InnerSearchApi;
import com.lesports.utils.LeDateUtils;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by lufei1 on 2015/8/19.
 */
@Path("/")
public class VideoResource {

    private static final Logger LOG = LoggerFactory.getLogger(VideoResource.class);
    @Inject
    private VideoService videoService;

    /**
     * 获取百度短视频信息
     *
     * @param type
     * @return
     */
    @GET
    @ENCRYPT
    @Path("videos/type/{type}")
    @Produces({MediaType.APPLICATION_XML, AlternateMediaType.UTF_8_APPLICATION_JSON})
    public ShortVideos getCompetition(@PathParam("type") long type) {
        try {

            CallerParam callerParam = new CallerParam();
            callerParam.setCallerId(LeConstants.LESPORTS_PC_CALLER_CODE);
            callerParam.setCountry(CountryCode.CN);
            callerParam.setLanguage(LanguageCode.ZH_CN);
            return videoService.getShortVideosByType(type, callerParam);

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
    @Path("competition/videos")
    @ENCRYPT
    @Produces({AlternateMediaType.UTF_8_APPLICATION_JSON})
    public Map<String, Object> getCompetitionVideos(@QueryParam("cid") long cid,
                                                    @QueryParam("caller") long callerId,
                                                    @QueryParam("page") @DefaultValue("1") int page,
                                                    @QueryParam("count") @DefaultValue("20") int count) {
        try {
            Preconditions.checkArgument(cid != 0, "relatedId can not be null!");
            PageParam pageParam = new PageParam();
            pageParam.setCount(count);
            pageParam.setPage(page > 0 ? page : 1);
            CallerParam callerParam = new CallerParam();
            callerParam.setCallerId(callerId);
            callerParam.setCountry(CountryCode.CN);
            callerParam.setLanguage(LanguageCode.ZH_CN);
            //如果tcl不传cid，查询所有
            if (cid == 0 && callerId==2010) {
                LOG.error("fail to getRelateVideos. cid : {} is illegal", cid);
                throw new LeWebApplicationException("关联id非法", LeStatus.PARAM_INVALID);
            }
            //对外接口过滤中超和nba数据
            if (cid == 47001 || cid == 44001) {
                LOG.error("fail to getRelateVideos. cid : {} is illegal", cid);
                throw new LeWebApplicationException("关联id非法", LeStatus.PARAM_INVALID);
            }
            //tcl和华术过滤英超
            if(callerId == 2010 || callerId == 2027 ){
                if(cid == 20001){
                    LOG.error("fail to getRelateVideos. cid : {} is illegal", cid);
                    throw new LeWebApplicationException("关联id非法", LeStatus.PARAM_INVALID);
                }
            }
            return ResponseUtils.createPageResponse(page,
                    videoService.getVideosByRelatedId(cid, callerParam, pageParam));

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
    @Path("csl/competition/videos")
    @ENCRYPT
    @Produces({AlternateMediaType.UTF_8_APPLICATION_JSON})
    public Map<String, Object> getCslCompetitionVideos(@QueryParam("caller") long callerId,
                                                    @QueryParam("page") @DefaultValue("1") int page,
                                                    @QueryParam("count") @DefaultValue("20") int count) {
        try {
            PageParam pageParam = new PageParam();
            pageParam.setCount(count);
            pageParam.setPage(page > 0 ? page : 1);
            CallerParam callerParam = new CallerParam();
            callerParam.setCallerId(callerId);
            callerParam.setCountry(CountryCode.CN);
            callerParam.setLanguage(LanguageCode.ZH_CN);

            return ResponseUtils.createPageResponse(page,
                    videoService.getCslVideosByRelatedId(47001L, callerParam, pageParam));

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
    @Path("/videos")
    @ENCRYPT
    @Produces({AlternateMediaType.UTF_8_APPLICATION_JSON})
    public Map<String, Object> getVideos(
            @QueryParam("caller") long callerId,
            @QueryParam("page") @DefaultValue("1") int page,
            @QueryParam("count") @DefaultValue("100") int count) {
        try {
            PageParam pageParam = new PageParam();
            pageParam.setCount(count>0?count:100);
            pageParam.setPage(page > 0 ? page : 1);
            CallerParam callerParam = new CallerParam();
            callerParam.setCallerId(callerId);
            callerParam.setCountry(CountryCode.CN);
            callerParam.setLanguage(LanguageCode.ZH_CN);
            return ResponseUtils.createPageResponse(page,
                    videoService.getVideos(callerParam, pageParam));
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
    @Path("videos/increment")
    @ENCRYPT
    @Produces({AlternateMediaType.UTF_8_APPLICATION_JSON})
    public Map<String, Object> getCompetitionSubVideos(
            @QueryParam("caller") long callerId,
            @QueryParam("startTime") String beginTime,
            @QueryParam("endTime") String endTime,
            @QueryParam("page") @DefaultValue("1") int page,
            @QueryParam("count") @DefaultValue("100") int count) {
        try {
            Map map = Maps.newHashMap();
            map.put("page", page>0?page:1);
            map.put("count", count>0?count:100);
            map.put("country", 1);
            map.put("startTime", beginTime == null || beginTime.length()!=14? LeDateUtils.formatYYYYMMDDHHMMSS(LeDateUtils.addHour(new Date(), -1)) : beginTime);
            map.put("endTime", endTime == null || beginTime.length()!=14? LeDateUtils.formatYYYYMMDDHHMMSS(new Date()) : endTime);
            return getVideoByCondition(page,callerId, map);
        } catch (LeWebApplicationException e) {
            LOG.error(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private Map<String, Object> getVideoByCondition(int page,long callerId, Map conditionMap) {
        VideoData data = InnerSearchApi.searchVideoContent(conditionMap);
        if (data != null && CollectionUtils.isNotEmpty(data.getRows())) {
            List<VideoSearch> lists = data.getRows();
            return ResponseUtils.createPageResponse(page, getVideoList(callerId,lists));
        }
        return ResponseUtils.createPageResponse(page, Lists.newArrayList());
    }

    private List<VideoVo> getVideoList (long calledId,List<VideoSearch> lists){
    if(CollectionUtils.isEmpty(lists))return null;
        List<VideoVo> voList=Lists.newArrayList();
        for(VideoSearch videoSearch:lists){
            TVideo tVideo = QmtSbcApis.getTVideoById(videoSearch.getId(), new CallerParam(calledId));
            VideoVo vedio = new VideoVoCreater().createVideoVo(tVideo);
            if (tVideo.getPlatforms().contains(2)) {
                vedio.setOnline(false);
            } else {
                vedio.setOnline(true);
            }
          voList.add(vedio);
        }
        return voList;
    }

    @GET
    @LJSONP
    @Path("/videos/tag")
    @ENCRYPT
    @Produces({AlternateMediaType.UTF_8_APPLICATION_JSON})
    public Map<String, Object> getVideosByTag(
            @QueryParam("tagId") long tagId,
            @BeanParam PageBean pageBean,
            @BeanParam CallerBean callerBean) {
        try {
            return ResponseUtils.createPageResponse(pageBean.getPageParam().getPage(),
                    videoService.getVideosByRelatedId(tagId,callerBean.getCallerParam(), pageBean.getPageParam()));
        } catch (LeWebApplicationException e) {
            LOG.error(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
