package com.lesports.qmt.op.web.api.resources;

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
import com.lesports.qmt.op.web.api.core.service.NewsService;
import com.lesports.qmt.op.web.api.core.vo.NewsVo;
import com.lesports.qmt.op.web.api.core.vo.OlyNewsVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.*;
import java.util.List;

/**
 * Created by lufei1 on 2015/11/5.
 */
@Path("/")
public class NewsResource {
    private static final Logger LOG = LoggerFactory.getLogger(NewsResource.class);


    @Inject
    private NewsService newsService;


    /**
     * 获取推荐视频新闻 hao123
     *
     * @param page
     * @param count
     * @return
     */
    @GET
    @LJSONP
    @ENCRYPT
    @Path("news/recommend")
    @Produces({AlternateMediaType.UTF_8_APPLICATION_JSON})
    public List<NewsVo> getRecommendNews(@QueryParam("page") @DefaultValue("1") int page,
                                         @QueryParam("count") @DefaultValue("20") int count) {
        try {
            PageParam pageParam = new PageParam();
            pageParam.setCount(count);
            pageParam.setPage(page > 0 ? page : 1);

            CallerParam callerParam = new CallerParam();
            callerParam.setCallerId(LeConstants.LESPORTS_PC_CALLER_CODE);
            callerParam.setCountry(CountryCode.CN);
            callerParam.setLanguage(LanguageCode.ZH_CN);

            return newsService.getRecommendNews(pageParam,callerParam);
        } catch (LeWebApplicationException e) {
            LOG.error(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }


    /**
     * 获取最新视频新闻 hao123
     *
     * @param page
     * @param count
     * @return
     */
    @GET
    @LJSONP
    @ENCRYPT
    @Path("news/latest")
    @Produces({AlternateMediaType.UTF_8_APPLICATION_JSON})
    public List<NewsVo> getTheLatestVideos(@QueryParam("page") @DefaultValue("1") int page,
                                           @QueryParam("count") @DefaultValue("20") int count,
                                           @QueryParam("caller") long callerId) {
        try {

            PageParam pageParam = new PageParam();
            pageParam.setCount(count);
            pageParam.setPage(page > 0 ? page : 1);

            CallerParam callerParam = new CallerParam();
            callerParam.setCallerId(callerId);
            callerParam.setCountry(CountryCode.CN);
            callerParam.setLanguage(LanguageCode.ZH_CN);

            return newsService.getTheLatestVideos(callerParam, pageParam);
        } catch (LeWebApplicationException e) {
            LOG.error(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }


    /**
     * 根据标签获取新闻
     *
     * @param page
     * @param count
     * @return
     */
    @GET
    @LJSONP
    @ENCRYPT
    @Path("news/tag")
    @Produces({AlternateMediaType.UTF_8_APPLICATION_JSON})
    public List<OlyNewsVo> getVideosByTag(@QueryParam("tag") @DefaultValue("奥运") String tag,
                                           @QueryParam("first") @DefaultValue("0") String first,
                                           @QueryParam("page") @DefaultValue("1") int page,
                                           @QueryParam("count") @DefaultValue("100") int count,
                                           @QueryParam("startTime") @DefaultValue("") String startTime,
                                           @QueryParam("caller") long callerId) {
        try {

            PageParam pageParam = new PageParam();
            pageParam.setCount(count);
            pageParam.setPage(page > 0 ? page : 1);

            CallerParam callerParam = new CallerParam();
            callerParam.setCallerId(callerId);
            callerParam.setCountry(CountryCode.CN);
            callerParam.setLanguage(LanguageCode.ZH_CN);

            return newsService.getNewsByTag(startTime, first, callerParam, pageParam);
        } catch (LeWebApplicationException e) {
            LOG.error(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
