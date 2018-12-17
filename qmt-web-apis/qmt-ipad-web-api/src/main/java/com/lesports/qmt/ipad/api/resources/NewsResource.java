package com.lesports.qmt.ipad.api.resources;

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
import com.lesports.qmt.sbc.api.service.GetRecommendParam;
import com.lesports.qmt.sbc.api.service.GetRelatedNewsParam;
import com.lesports.qmt.sbc.client.QmtSbcApis;
import com.lesports.qmt.web.api.core.service.NewsService;
import com.lesports.qmt.web.api.core.service.VideoService;
import com.lesports.qmt.web.api.core.util.ResponseUtils;
import com.lesports.qmt.web.api.core.vo.DetailNews;
import com.lesports.qmt.web.api.core.vo.NewsVo;
import com.lesports.qmt.web.api.core.vo.RecommendVo;
import com.lesports.utils.LeStringUtils;
import com.lesports.utils.math.LeNumberUtils;
import org.apache.commons.lang3.StringUtils;
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

	@Inject
	private VideoService videoService;

	/**
	 * 推荐新闻
	 *
	 * @return
	 */
	@GET
	@LJSONP
	@Path("/recommends")
	@Produces({AlternateMediaType.UTF_8_APPLICATION_JSON})
	public Map<String, Object> getRecommend(@QueryParam("type") @DefaultValue("0,1") String type,
											@QueryParam("newsType") @DefaultValue("0,1,2") String newsType,
											@QueryParam("relatedId") @DefaultValue("0") long relatedId,
											@BeanParam PageBean pageBean,
											@BeanParam CallerBean callerBean) {
		try {
			GetRecommendParam p = createRecommendParam(type, newsType, relatedId);
			List<RecommendVo> recommendVos = newsService.getRecommendsAndNews4App(p, pageBean.getPageParam(), callerBean.getCallerParam());
			return ResponseUtils.createPageResponse(pageBean.getPage(), recommendVos);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
		}
	}

	private GetRecommendParam createRecommendParam(String type, String newsType, long relatedId) {
		GetRecommendParam p = new GetRecommendParam();
		p.setType(type);
		p.setNewsType(newsType);
		p.setRelatedId(relatedId);
		return p;
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
            return newsService.getNewsById(id, callerBean.getCallerParam(), idType);
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
                                       @QueryParam("type") @DefaultValue("1,2") String type,
                                       @BeanParam PageBean pageBean,
                                       @BeanParam CallerBean callerBean) {
        try {
            List<NewsType> typeList = parseNewsTypes(type);

            return newsService.getRelatedNews4App(id, typeList, pageBean.getPageParam(), callerBean.getCallerParam());
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
     * 获取新闻列表
     *
     * @param relatedId 大项,小项,赛事,比赛,球员,球队
     * @param type      类型: -1:全部 0:富文本新闻 1:视频 2:图文新闻 (多个用英文逗号隔开,默认 1,2 - 因为目前app有线上服务)
     * @param isTotal   0: 不需要总数 1: 需要总数
	 * @param isNeedRecommends   0: 不需要总数 1: 需要推荐新闻
	 * @param member   0:免费 1:付费 2:全部
     * @return
     */
	@GET
	@LJSONP
	@Path("/news")
	@Produces({AlternateMediaType.UTF_8_APPLICATION_JSON})
	public Map<String, Object> getNewes(@QueryParam("relatedId") @DefaultValue("0") long relatedId,
										@QueryParam("cids") @DefaultValue("") String cids,
										@QueryParam("type") @DefaultValue("1,2") String type,
										@QueryParam("isTotal") @DefaultValue("0") int isTotal,
										@QueryParam("isNeedRecommends") @DefaultValue("1") int isNeedRecommends,
										@QueryParam("member") @DefaultValue("2") int member,
										@BeanParam PageBean pageBean,
										@BeanParam CallerBean callerBean) {
		try {
			GetRelatedNewsParam p = new GetRelatedNewsParam();
			List<Long> rids = Lists.newArrayList();
			if (StringUtils.isNotEmpty(cids)) {
				//先获取赛事新闻
				rids.addAll(LeStringUtils.commaString2LongList(cids));
			}
			if (relatedId > 0) {
				rids.add(relatedId);
			}
			p.setRelatedIds(rids);
			List<NewsType> typeList = parseNewsTypes(type);
			p.setTypes(typeList);
			p.setMember(member);

			List<NewsVo> news = newsService.getNewsListByRelatedIdWithFallback(type, p, isNeedRecommends, pageBean.getPageParam(), callerBean.getCallerParam());
			long total = QmtSbcApis.countNewsByRelatedId(p, callerBean.getCallerParam());

			return ResponseUtils.createPageResponse(pageBean.getPage(), total, news);
		} catch (LeWebApplicationException e) {
			throw e;
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
		}
	}

    /**
     * 获取焦点图新闻
     *
     * @return
     */
    @GET
    @LJSONP
    @Path("/news/focus")
    @Produces({AlternateMediaType.UTF_8_APPLICATION_JSON})
    public List<RecommendVo> getFocusNewes(@QueryParam("type") @DefaultValue("0,1") String type,
										   @BeanParam PageBean pageBean,
                                           @BeanParam CallerBean callerBean) {
        try {
            return newsService.getFocusNews4AppWithFallback(type, pageBean.getPageParam(), callerBean.getCallerParam());
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }

	/**
	 * 获取焦点图
	 *
	 * @return
	 */
	@GET
	@LJSONP
	@Path("/focus/{id}")
	@Produces({AlternateMediaType.UTF_8_APPLICATION_JSON})
	public List<RecommendVo> getFocusById(@PathParam("id") long id,
										  @QueryParam("type") @DefaultValue("0,1") String type,
										  @BeanParam PageBean pageBean,
										  @BeanParam CallerBean callerBean) {
		try {
			Preconditions.checkArgument(id > 0, "focus id can not be null!");
			return newsService.getCmsFocusNews4APP(id, type, pageBean.getPageParam(), callerBean.getCallerParam());
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * 获取视频列表
	 *
	 * @param relatedId 大项,小项,赛事,比赛,球员,球队,节目
	 * @return
	 */
	@GET
	@LJSONP
	@Path("/videos")
	@Produces({AlternateMediaType.UTF_8_APPLICATION_JSON})
	public Map<String, Object> getRelateVideos(@QueryParam("relatedId") long relatedId,
											   @BeanParam PageBean pageBean,
											   @BeanParam CallerBean callerBean) {
		try {
			Preconditions.checkArgument(relatedId != 0, "relatedId can not be null!");
			if (relatedId == 0) {
				LOG.error("fail to getRelateVideos. relatedId : {} is illegal", relatedId);
				throw new LeWebApplicationException("关联id非法", LeStatus.PARAM_INVALID);
			}
			return ResponseUtils.createPageResponse(pageBean.getPage(), videoService.getVideosByRelatedId(relatedId,
					pageBean.getPageParam(), callerBean.getCallerParam()));
		} catch (LeWebApplicationException e) {
			throw e;
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
