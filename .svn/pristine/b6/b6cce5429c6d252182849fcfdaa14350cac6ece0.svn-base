package com.lesports.qmt.app.api.resources;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.lesports.api.common.Direction;
import com.lesports.api.common.Order;
import com.lesports.api.common.PageParam;
import com.lesports.api.common.Sort;
import com.lesports.jersey.AlternateMediaType;
import com.lesports.jersey.annotation.LJSONP;
import com.lesports.jersey.annotation.cache.BatchLogUrlProcessor;
import com.lesports.jersey.annotation.cache.LOG_URL;
import com.lesports.jersey.core.LeStatus;
import com.lesports.jersey.exception.LeWebApplicationException;
import com.lesports.jersey.exception.LeWebFallbackException;
import com.lesports.jersey.support.model.CallerBean;
import com.lesports.jersey.support.model.PageBean;
import com.lesports.qmt.sbc.api.dto.TProgramAlbum;
import com.lesports.qmt.sbc.api.service.*;
import com.lesports.qmt.sbc.client.QmtSbcApis;
import com.lesports.qmt.web.api.core.common.PeriodType;
import com.lesports.qmt.web.api.core.service.EpisodeService;
import com.lesports.qmt.web.api.core.util.ResponseUtils;
import com.lesports.qmt.web.api.core.vo.*;
import com.lesports.utils.LeStringUtils;
import com.lesports.utils.PageUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.*;
import java.util.List;
import java.util.Map;

/**
 * Created by gengchengliang on 2015/6/18.
 */
@Path("/")
public class EpisodeResource {

    private static final Logger LOG = LoggerFactory.getLogger(EpisodeResource.class);

    @Inject
    private EpisodeService episodeService;

	/**
	 * 从某天开始一段时间内每天的节目数量 - 香港app
	 * <p/>
	 * startDate 起始时间,精确到天(yyyyMMdd:20150528)
	 * gameType 比赛分类
	 * episodeType 节目类型 0:match 1:program 2:all
	 * liveType 节目包含的属性 0:只要直播 1:直播和重点赛程 2:所有节目 3:仅仅重点赛事 4：直播or图文直播
	 * spanUnit 时长单位(1天:d,1周:w) 默认d
	 * span 时长 默认是1,需要配合上面的spanUnit使用
	 */
	@GET
	@LJSONP
	@Path("day/episodes/count")
	@Produces({AlternateMediaType.UTF_8_APPLICATION_JSON})
	public Map<String, Object> getSomedayCount(@QueryParam("startDate") String startDate,
											   @QueryParam("gameType") @DefaultValue("0") long gameType,
											   @QueryParam("episodeType") @DefaultValue("0") int episodeType,
											   @QueryParam("liveType") @DefaultValue("0") int liveType,
											   @QueryParam("spanUnit") @DefaultValue("d") String spanUnit,
											   @QueryParam("span") @DefaultValue("1") int span,
											   @BeanParam CallerBean callerBean) {
		try {
			Preconditions.checkArgument(StringUtils.isNotBlank(startDate) && startDate.length() == 8, "startDate不为空,切格式为yyyyMMdd");
			LiveTypeParam liveTypeParam = LiveTypeParam.findByValue(liveType);
			int days = span * PeriodType.findByValue(spanUnit).getDay();
			CountEpisodesByDayParam p = createCountEpisodesByDayParam(startDate, days, episodeType, gameType, liveTypeParam);
			return episodeService.countEpisodesByDay(p, callerBean.getCallerParam());
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
		}
	}

	private CountEpisodesByDayParam createCountEpisodesByDayParam(String startDate, int days, int episodeType, long gameType,
																  LiveTypeParam liveTypeParam) {
		CountEpisodesByDayParam p = new CountEpisodesByDayParam();
		p.setStartDate(startDate);
		p.setDays(days);
		p.setLiveTypeParam(liveTypeParam);
		p.setGameType(gameType);
		p.setEpisodeTypeParam(EpisodeTypeParam.findByValue(episodeType));
		return p;
	}

	/**
	 * 香港app
	 * <p/>
	 * startDate 起始时间,精确到天(yyyyMMdd:20150528)
	 * gameType 比赛分类
	 * episodeType 节目类型 0:match 1:program 2:all
	 * liveType 节目包含的属性 0:只要直播 1:直播和重点赛程 2:所有节目
	 * page 页码
	 * count 每页条数
	 */
	@LJSONP
	@GET
	@Path("day/episodes")
	@Produces(AlternateMediaType.UTF_8_APPLICATION_JSON)
	public Map<String, Object> getHallEpisodesByDays(@QueryParam("startDate") String date,
													 @QueryParam("gameType") @DefaultValue("0") long gameType,
													 @QueryParam("episodeType") @DefaultValue("0") int episodeType,
													 @QueryParam("liveType") @DefaultValue("2") int liveType,
													 @QueryParam("page") int page,
													 @QueryParam("count") int count,
													 @BeanParam CallerBean callerBean) {
		Preconditions.checkArgument(StringUtils.isNotBlank(date) && date.length() == 8, "startDate不为空,切格式为yyyyMMdd");
		try {
			PageParam pageParam = null;
			if (page > 0 || count > 0) {
				pageParam = PageUtils.createPageParam(page, count);
				pageParam.setSort(new Sort(Lists.newArrayList(new Order("start_time", Direction.ASC))));
			}
			GetSomeDayEpisodesParam p = createGetSomeDayEpisodesParam(date, gameType, liveType, episodeType);
			return ResponseUtils.createPageResponse(page, "entries", episodeService.getSomeDayEpisodesWithFallback(p, pageParam, callerBean.getCallerParam()));
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw new LeWebFallbackException(e.getMessage());
		}
	}

	private GetSomeDayEpisodesParam createGetSomeDayEpisodesParam(String date, long gameType, int liveType,int episodeType) {
		GetSomeDayEpisodesParam p = new GetSomeDayEpisodesParam();
		p.setLiveTypeParam(LiveTypeParam.findByValue(liveType));
		p.setDate(date);
		p.setGameType(gameType);
		p.setEpisodeTypeParam(EpisodeTypeParam.findByValue(episodeType));
		return p;
	}


	/**
	 * 香港app - hot and living
	 * startDate 起始时间,精确到天(yyyyMMdd:20150528)
	 * gameType 比赛分类
	 * episodeType 节目类型 0:match 1:program 2:all
	 * liveType 节目包含的属性 0:只要直播 1:直播和重点赛程 2:所有节目 3:重点节目
	 * page 页码
	 * count 每页条数
	 */
	@LJSONP
	@GET
	@Path("living/episodes")
	@Produces(AlternateMediaType.UTF_8_APPLICATION_JSON)
	public Map<String, Object> getLivingEpisodes(@QueryParam("episodeType") @DefaultValue("0") int episodeType,
													 @QueryParam("liveType") @DefaultValue("3") int liveType,
													 @QueryParam("status") @DefaultValue("1") int status,
													 @BeanParam PageBean pageBean,
													 @BeanParam CallerBean callerBean) {
		try {
			PageParam pageParam = null;
			GetCurrentEpisodesParam p = createGetCurrentEpisodesParam(0, LiveTypeParam.findByValue(liveType), LiveShowStatusParam.findByValue(status), null);
			return ResponseUtils.createPageResponse(pageBean.getPage(), "entries", episodeService.getLivingEpisodes(p, pageParam, callerBean.getCallerParam()));
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw new LeWebFallbackException(e.getMessage());
		}
	}

    /**
     * APP 按状态查询
     * <p/>
     * game_type 比赛分类
     * episode_type 节目类型 0:match 1:program
     * status 比赛状态: -1:全部 0:未开始 1:比赛中 2:已结束 3:未结束
     * live_type 节目包含的属性 0:只要直播 1:直播和重点赛程 2:所有节目
     * page 页码
     * count 每页条数
     */
    @LJSONP
    @GET
    @Path("/episodes")
    @Produces(AlternateMediaType.UTF_8_APPLICATION_JSON)
    public Map<String, Object> getChannelStatusMatchs(@QueryParam("gameType") @DefaultValue("0") long gameType,
                                                      @QueryParam("cids") @DefaultValue("") String cids,
                                                      @QueryParam("status") @DefaultValue("-1") int status,
                                                      @QueryParam("liveType") @DefaultValue("2") int liveType,
                                                      @BeanParam PageBean pageBean,
                                                      @BeanParam CallerBean callerBean) {

        try {
            LiveTypeParam liveTypeParam = LiveTypeParam.findByValue(liveType);
            LiveShowStatusParam liveShowStatusParam = LiveShowStatusParam.findByValue(status);
            List<Long> cidList = Lists.newArrayList();
            cidList = LeStringUtils.commaString2LongList(cids);
            GetCurrentEpisodesParam p = createGetCurrentEpisodesParam(gameType, liveTypeParam, liveShowStatusParam, cidList);

            List<HallEpisodeVo> episodes = episodeService.getHallEpisodes4AppWithFallback(p, pageBean.getPageParam(), callerBean.getCallerParam());
            return ResponseUtils.createPageResponse(pageBean.getPage(), episodes);
        } catch (LeWebApplicationException e) {
            LOG.error(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private GetCurrentEpisodesParam createGetCurrentEpisodesParam(long gameType, LiveTypeParam liveTypeParam,
                                                                  LiveShowStatusParam liveShowStatusParam, List<Long> cids) {
        GetCurrentEpisodesParam p = new GetCurrentEpisodesParam();
        p.setGameType(gameType);
        p.setLiveTypeParam(liveTypeParam);
        p.setLiveShowStatusParam(liveShowStatusParam);
        p.setCids(cids);
        return p;
    }

    /**
     * 获取节目
     * <p/>
     * id ID
     */
    @LOG_URL
    @LJSONP
    @GET
    @Path("/episodes/{id}")
    @Produces(AlternateMediaType.UTF_8_APPLICATION_JSON)
    public DetailEpisodeVo getEpisodeById(@PathParam("id") long id,
                                          @BeanParam CallerBean callerBean) {
        try {
            return episodeService.getEpisodeById(id, callerBean.getCallerParam());
        } catch (LeWebApplicationException e) {
            LOG.error(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 轮询比分和状态的接口
     * <p/>
     * id ID
     */
    @LOG_URL(processor = BatchLogUrlProcessor.class)
    @LJSONP
    @GET
    @Path("/refresh/episodes")
    @Produces(AlternateMediaType.UTF_8_APPLICATION_JSON)
    public List<PollingEpisodeVo> getEpisodesByIds(@QueryParam("ids") String ids,
                                                   @BeanParam CallerBean callerBean) {
        try {
            Preconditions.checkArgument(StringUtils.isNotBlank(ids), "请传入ids,多个已英文逗号隔开");
            return episodeService.refreshEpisodesByIds(ids,callerBean.getCallerParam());
        } catch (LeWebApplicationException e) {
            LOG.error(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 批量获取节目信息
     * <p/>
     * ids
     */
    @LOG_URL(processor = BatchLogUrlProcessor.class)
    @LJSONP
    @GET
    @Path("/multi/episodes")
    @Produces(AlternateMediaType.UTF_8_APPLICATION_JSON)
    public List<HallEpisodeVo> getMultiEpisodesByIds(@QueryParam("ids") String ids,
                                                     @BeanParam CallerBean callerBean) {
        try {
            Preconditions.checkArgument(StringUtils.isNotBlank(ids), "请传入ids,多个已英文逗号隔开");
            return episodeService.getMultiEpisodesByIds4App(ids, callerBean.getCallerParam());
        } catch (LeWebApplicationException e) {
            LOG.error(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @LJSONP
    @GET
    @Path("/albums/{aid}/episodes")
    @Produces(AlternateMediaType.UTF_8_APPLICATION_JSON)
    public Map<String, Object> getEpisodeByAid(@PathParam("aid") long aid,
                                               @BeanParam PageBean pageBean,
                                               @BeanParam CallerBean callerBean) {
        try {
            Preconditions.checkArgument(aid != 0, "illegal album id");
            return ResponseUtils.createPageResponse(pageBean.getPage(), episodeService.getEpisodeByAid(aid,
                    pageBean.getPageParam(), callerBean.getCallerParam()));
        } catch (LeWebApplicationException e) {
            LOG.error(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @LJSONP
    @GET
    @Path("previous/{id}/episodes")
    @Produces(AlternateMediaType.UTF_8_APPLICATION_JSON)
    public Map<String, Object> getPreviousEpisodesById(@PathParam("id") long id,
                                                       @BeanParam PageBean pageBean,
                                                       @BeanParam CallerBean callerBean) {
        try {
            Preconditions.checkArgument(id > 0, "illegal album id.");
            List<LatestEpisodeVo> vos = episodeService.getPreviousEpisodesById(id, pageBean.getPageParam(), callerBean.getCallerParam());
            Map<String, Object> map = ResponseUtils.createPageResponse(pageBean.getPage(), vos);
            String title = "";
			TProgramAlbum tAlbum = QmtSbcApis.getTProgramAlbumById(id, callerBean.getCallerParam());
            if (null != tAlbum) {
                title = tAlbum.getName();
            }
            map.put("albumTitle", title);
            return map;
        } catch (LeWebApplicationException e) {
            LOG.error(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @LJSONP
    @GET
    @Path("episodes/{id}/data/h5url")
    @Produces(AlternateMediaType.UTF_8_APPLICATION_JSON)
    public DataH5Url getNbaDataH5url(@PathParam("id") long id) {
        try {
            Preconditions.checkArgument(id != 0, "请传入专辑id");
            return new DataH5Url();
        } catch (LeWebApplicationException e) {
            LOG.error(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }

	/**
	 * 通过参赛者id获取节目 - 超级手机使用
	 * id 参赛者id
	 * csid 赛季id 如果为0,则为最新的赛季 如果想查以前的请传入相应的赛季id
	 * status 比赛状态: -1:全部 0:未开始 1:比赛中 2:已结束 3:未结束
	 * liveType 节目包含的属性 0:只要直播 1:直播和重点赛程 2:所有节目
	 * page 页码
	 * count 每页条数
	 */
	@LJSONP
	@GET
	@Path("competitor/{id}/episodes/")
	@Produces(AlternateMediaType.UTF_8_APPLICATION_JSON)
	public List<HallEpisodeVo> getCompetitorEpisodesById(@PathParam("id") long id,
														 @PathParam("csid") @DefaultValue("0") int csid,
														 @QueryParam("status") @DefaultValue("-1") int status,
														 @QueryParam("liveType") @DefaultValue("2") int liveType,
														 @QueryParam("startDate") String startDate,
														 @QueryParam("endDate") String endDate,
														 @BeanParam PageBean pageBean,
														 @BeanParam CallerBean callerBean) {
		try {
			Preconditions.checkArgument(id != 0, "请传入参赛者id");
			if (StringUtils.isNotBlank(startDate) || StringUtils.isNotBlank(endDate)) {
				Preconditions.checkArgument(StringUtils.isNotBlank(startDate) && StringUtils.isNotBlank(endDate)
						&& startDate.length() == 8 && endDate.length() == 8 , "please check startDate and endDate !");
			}
			GetEpisodesOfSeasonByMetaEntryIdParam p = new GetEpisodesOfSeasonByMetaEntryIdParam();
			p.setCsid(csid);
			p.setLiveShowStatusParam(LiveShowStatusParam.findByValue(status));
			p.setLiveTypeParam(LiveTypeParam.findByValue(liveType));
			p.setStartDate(startDate);
			p.setEndDate(endDate);
			return episodeService.getEpisodesByCompetitorId(id, p, pageBean.getPageParam(), callerBean.getCallerParam());
		} catch (LeWebApplicationException e) {
			LOG.error(e.getMessage(), e);
			throw e;
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@LJSONP
	@GET
	@Path("/lephone/desktop/recommend/episodes")
	@Produces(AlternateMediaType.UTF_8_APPLICATION_JSON)
	public List<HallEpisodeVo> getLephoneDesktopEpisodes(@QueryParam("date") String date,
														 @BeanParam CallerBean callerBean,
														 @QueryParam("gameType") @DefaultValue("0") long gameType) {
		try {
			Preconditions.checkArgument(StringUtils.isNotBlank(date), "请传入日期date,格式为yyyyMMdd!");
			return null;//episodeService.getLephoneDesktopEpisodes(date, gameType, callerBean.getCallerParam());
		} catch (LeWebApplicationException e) {
			LOG.error(e.getMessage(), e);
			throw e;
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@LJSONP
	@GET
	@Path("/period/recommend/episodes")
	@Produces(AlternateMediaType.UTF_8_APPLICATION_JSON)
	public Map<String, Object> getPeriodAppRecommendEpisodes(@BeanParam CallerBean callerBean) {
		try {
			List<HallEpisodeVo> episodes = episodeService.getPeriodAppRecommendEpisodes(callerBean.getCallerParam());
			return ResponseUtils.createPageResponse(1, episodes);
		} catch (LeWebApplicationException e) {
			LOG.error(e.getMessage(), e);
			throw e;
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
		}
	}


	@LJSONP
	@GET
	@Path("/timeline/hot/episodes")
	@Produces(AlternateMediaType.UTF_8_APPLICATION_JSON)
	public Map<String, Object> getTimelineEpisodes(@QueryParam("page") @DefaultValue("1") int page,
												   @QueryParam("count") @DefaultValue("20") int count,
												   @BeanParam CallerBean callerBean) {
		try {
			LiveTypeParam liveTypeParam = LiveTypeParam.findByValue(3);
			List<HallEpisodeVo> episodes = episodeService.getTimelineEpisodes(liveTypeParam, callerBean.getCallerParam(), page, count);
			return ResponseUtils.createPageResponse(page, episodes);
		} catch (LeWebApplicationException e) {
			LOG.error(e.getMessage(), e);
			throw e;
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * 根据直播id获取节目
	 *
	 * @param liveId
	 * @return
	 */
	@LOG_URL
	@LJSONP
	@GET
	@Path("live/{liveId}/episode")
	@Produces(AlternateMediaType.UTF_8_APPLICATION_JSON)
	public HallEpisodeVo getEpisodeByliveId(@PathParam("liveId") String liveId,
											@BeanParam CallerBean callerBean) {
		try {
			Preconditions.checkArgument(StringUtils.isNotEmpty(liveId), "请传入直播id");
			HallEpisodeVo episode = episodeService.getEpisodeByLiveId(liveId,callerBean.getCallerParam());
			return episode;
		} catch (LeWebApplicationException e) {
			LOG.error(e.getMessage(), e);
			throw e;
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * 时间轴形式获取全部赛程列表的接口
	 * status 比赛状态: -1:全部 0:未开始 1:比赛中 2:已结束 3:未结束
	 * live_type 节目包含的属性 0:只要直播 1:直播和重点赛程 2:所有节目
	 * page 页码
	 * count 每页条数
	 */
	@LJSONP
	@GET
	@Path("/timeline/episodes")
	@Produces(AlternateMediaType.UTF_8_APPLICATION_JSON)
	public Map<String, Object> getTimelineEpisodes(@QueryParam("liveType") @DefaultValue("2") int liveType,
												   @QueryParam("page") @DefaultValue("1") int page,
												   @QueryParam("count") @DefaultValue("20") int count,
												   @BeanParam CallerBean callerBean) {

		try {
			LiveTypeParam liveTypeParam = LiveTypeParam.findByValue(liveType);
			List<HallEpisodeVo> episodes = episodeService.getTimelineEpisodes(liveTypeParam, callerBean.getCallerParam(), page, count);
			return ResponseUtils.createPageResponse(page, episodes);
		} catch (LeWebApplicationException e) {
			LOG.error(e.getMessage(), e);
			throw e;
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
