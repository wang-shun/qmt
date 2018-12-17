package com.lesports.qmt.tv.api.resources;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.lesports.jersey.AlternateMediaType;
import com.lesports.jersey.annotation.LJSONP;
import com.lesports.jersey.annotation.cache.LOG_URL;
import com.lesports.jersey.core.LeStatus;
import com.lesports.jersey.exception.LeWebApplicationException;
import com.lesports.jersey.support.model.CallerBean;
import com.lesports.jersey.support.model.PageBean;
import com.lesports.qmt.sbc.api.common.NewsType;
import com.lesports.qmt.sbc.api.service.GetRelatedNewsParam;
import com.lesports.qmt.web.api.core.service.NewsService;
import com.lesports.qmt.web.api.core.util.ResponseUtils;
import com.lesports.qmt.web.api.core.vo.DetailNews;
import com.lesports.qmt.web.api.core.vo.NewsVo;
import com.lesports.utils.LeStringUtils;
import com.lesports.utils.math.LeNumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.*;
import java.util.List;
import java.util.Map;

/**
* Created by gengchengliang on 2015/6/17.
*/
@Path("/")
public class NewsResource {

    private static final Logger LOG = LoggerFactory.getLogger(NewsResource.class);

    @Inject
    private NewsService newsService;


    /**
     ** 获取新闻列表
     *
     * @param relatedId 大项,小项,赛事,比赛,球员,球队
     * @param type      类型: -1:全部 0:富文本新闻 1:视频 2:图文新闻 (多个用英文逗号隔开,默认 1)
     * @param pageBean
     * @param callerBean
     * @return
     */
    @GET
    @LJSONP
    @Path("/news")
    @Produces({AlternateMediaType.UTF_8_APPLICATION_JSON})
    public Map<String, Object> getNewes(@QueryParam("relatedId") @DefaultValue("0") long relatedId,
                                        @QueryParam("type") @DefaultValue("1") String type,
                                        @QueryParam("member") @DefaultValue("2") int member,
                                        @BeanParam PageBean pageBean,
                                        @BeanParam CallerBean callerBean) {
        try {

            GetRelatedNewsParam p  = new GetRelatedNewsParam();
            if(relatedId > 0) {
                p.setRelatedIds(Lists.newArrayList(relatedId));
            }
            p.setTypes(parseNewsTypes(type));
            p.setMember(member);
            if(relatedId > 0) {
                return ResponseUtils.createPageResponse(pageBean.getPage(), newsService.getRecommendNewsByRelatedIdAndTypes("", p, pageBean.getPageParam(), callerBean.getCallerParam()));
            }else{
                return ResponseUtils.createPageResponse(pageBean.getPage(),newsService.getNewsListByRelatedId(p,pageBean.getPageParam(), callerBean.getCallerParam()));
            }

        } catch (LeWebApplicationException e) {
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private List<NewsType> parseNewsTypes(String typeString) {
        List<Long> typeArray = LeStringUtils.commaString2LongList(typeString);
        List<NewsType> typeList = Lists.newArrayList();
        for (Long typeValue : typeArray) {
            NewsType t = NewsType.findByValue(LeNumberUtils.toInt(typeValue));
            if (t != null) {
                typeList.add(t);
            }
        }
        return typeList;
    }

    /**
     * 获取图文新闻详情
     * @param idType 0: 新闻id 1:视频id (默认为0)
     * @param id
     * @return
     */
    @LOG_URL
    @GET
    @LJSONP
    @Path("/news/detail/{id}")
    @Produces({AlternateMediaType.UTF_8_APPLICATION_JSON})
    public DetailNews getNewsById(@PathParam("id") long id,
                                  @BeanParam CallerBean callerBean,
                                  @QueryParam("idType") @DefaultValue("0") int idType) {
        try {
            Preconditions.checkArgument(id != 0, "newsId can not be null!");
            return newsService.getNewsById4TV(id, callerBean.getCallerParam(), idType);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 获取和某新闻相关的其他新闻
     *
     * @param id
     * @return
     */
    @LOG_URL
    @GET
    @LJSONP
    @Path("/news/{id}/related")
    @Produces({AlternateMediaType.UTF_8_APPLICATION_JSON})
    public List<NewsVo> getRelatedNews(@PathParam("id") long id,
                                       @QueryParam("type") @DefaultValue("3") String type,
                                       @BeanParam PageBean pageBean,
                                       @BeanParam CallerBean callerBean){
        try {
            List<NewsType> typeList = parseNewsTypes(type);

            return newsService.getRelatedNews4TV(id, typeList, pageBean.getPageParam(), callerBean.getCallerParam());
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
