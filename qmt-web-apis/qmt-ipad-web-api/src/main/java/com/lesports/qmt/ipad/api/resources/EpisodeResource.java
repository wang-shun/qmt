package com.lesports.qmt.ipad.api.resources;

import com.google.common.base.Preconditions;
import com.lesports.api.common.Direction;
import com.lesports.api.common.Order;
import com.lesports.api.common.PageParam;
import com.lesports.api.common.Sort;
import com.lesports.jersey.AlternateMediaType;
import com.lesports.jersey.annotation.LJSONP;
import com.lesports.jersey.core.LeStatus;
import com.lesports.jersey.exception.LeWebApplicationException;
import com.lesports.jersey.support.model.CallerBean;
import com.lesports.jersey.support.model.PageBean;
import com.lesports.qmt.sbc.api.dto.TProgramAlbum;
import com.lesports.qmt.sbc.api.service.*;
import com.lesports.qmt.sbc.client.QmtSbcApis;
import com.lesports.qmt.web.api.core.common.PeriodType;
import com.lesports.qmt.web.api.core.service.EpisodeService;
import com.lesports.qmt.web.api.core.util.ResponseUtils;
import com.lesports.qmt.web.api.core.vo.HallEpisodeVo;
import com.lesports.qmt.web.api.core.vo.LatestEpisodeVo;
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
     * 从某天开始一段时间内每天的节目数量 - 日历使用
     * <p/>
     * startDate 起始时间,精确到天(yyyyMMdd:20150528)
     * gameType 比赛分类
     * episodeType 节目类型 0:match 1:program 2:all
     * liveType 节目包含的属性 0:只要直播 1:直播和重点赛程 2:所有节目
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
            Preconditions.checkArgument(StringUtils.isNotBlank(startDate) || startDate.length() != 8, "startDate不为空,切格式为yyyyMMdd");
            LiveTypeParam liveTypeParam = LiveTypeParam.findByValue(liveType);
            int days = span * PeriodType.findByValue(spanUnit).getDay();
            CountEpisodesByDayParam p = createCountEpisodesByDayParam(startDate, days, gameType, liveTypeParam);
            return episodeService.countEpisodesByDay(p, callerBean.getCallerParam());
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
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
                                                      @QueryParam("startDate") String date,
                                                      @QueryParam("episodeType") @DefaultValue("-1") int episodeType,
                                                      @QueryParam("status") @DefaultValue("-1") int status,
                                                      @QueryParam("liveType") @DefaultValue("2") int liveType,
                                                      @BeanParam PageBean pageBean,
                                                      @BeanParam CallerBean callerBean) {

        try {
            PageParam pageRequest = new PageParam();
            pageRequest.setCount(pageBean.getCount());
            pageRequest.setPage(pageBean.getPage());
            Sort sort = new Sort();
            sort.addToOrders(new Order( "start_time",Direction.ASC));
            pageRequest.setSort(sort);
            GetSomeDayEpisodesParam p = createGetSomeDayEpisodesParam(date, gameType, liveType,
                    LeStringUtils.commaString2LongList(cids), null, status);

            List<HallEpisodeVo> episodes = episodeService.getEpisodesByCids4Ipad(p, pageRequest, callerBean.getCallerParam());
            return ResponseUtils.createPageResponse(pageBean.getPage(), episodes);
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
            Preconditions.checkArgument(aid != 0, "请传入专辑id");
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

    /**
     * @param cid        赛事id
     * @param csid       赛季id, 可以为0, 为0取最新赛季
     * @param dictId     阶段的字典id 支持(大项,小项,轮次,阶段,分站,分组)
     * @param status     比赛状态: -1:全部 0:未开始 1:比赛中 2:已结束 3:未结束
     * @param liveType   节目包含的属性 0:只要直播 1:直播和重点赛程 2:所有节目
     * @param pageBean
     * @param callerBean
     * @return
     */
    @LJSONP
    @GET
    @Path("step/episodes")
    @Produces(AlternateMediaType.UTF_8_APPLICATION_JSON)
    public Map<String, Object> getStepEpisodes(@QueryParam("cid") long cid,
                                               @QueryParam("csid") long csid,
                                               @QueryParam("dictId") long dictId,
                                               @QueryParam("status") @DefaultValue("-1") int status,
                                               @QueryParam("liveType") @DefaultValue("2") int liveType,
                                               @BeanParam PageBean pageBean,
                                               @BeanParam CallerBean callerBean) {

        try {
            Preconditions.checkArgument(cid != 0, "请传入赛事id");
            GetEpisodesOfSeasonByMetaEntryIdParam p = createGetEpisodesOfSeasonByMetaEntryIdParam(cid, csid, dictId, status, liveType);
            return ResponseUtils.createPageResponse(pageBean.getPage(), episodeService.getMatchEpisodesByCidAndMetaEntryId(p, pageBean.getPageParam(), callerBean.getCallerParam()));
        } catch (LeWebApplicationException e) {
            LOG.error(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private GetEpisodesOfSeasonByMetaEntryIdParam createGetEpisodesOfSeasonByMetaEntryIdParam(long cid, long csid, long entryId, int status, int liveType) {
        GetEpisodesOfSeasonByMetaEntryIdParam p = new GetEpisodesOfSeasonByMetaEntryIdParam();
        p.setCid(cid);
        p.setCsid(csid);
        p.setEntryId(entryId);
        p.setLiveTypeParam(LiveTypeParam.findByValue(liveType));
        p.setLiveShowStatusParam(LiveShowStatusParam.findByValue(status));
        return p;
    }

    /**
     * 比赛大厅,按照天查询
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
    public Map<String, Object> getHallDayEpisodes(@QueryParam("startDate") String date,
                                                  @QueryParam("gameType") @DefaultValue("0") long gameType,
                                                  @QueryParam("episodeType") @DefaultValue("0") int episodeType,
                                                  @QueryParam("liveType") @DefaultValue("2") int liveType,
                                                  @QueryParam("page") int page,
                                                  @QueryParam("count") int count,
                                                  @BeanParam CallerBean callerBean) {

        try {
            Preconditions.checkArgument(StringUtils.isNotBlank(date) && date.length() == 8, "startDate不为空,切格式为yyyyMMdd");
            PageParam pageParam = null;
            if (page > 0 || count > 0) {
                pageParam = PageUtils.createPageParam(page, count);
            }
            GetSomeDayEpisodesParam p = createGetSomeDayEpisodesParam(date, gameType, liveType, null, null, -1);
            return ResponseUtils.createPageResponse(page, episodeService.getSomeDayEpisodesWithFallback(p, pageParam, callerBean.getCallerParam()));
        } catch (LeWebApplicationException e) {
            LOG.error(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private GetSomeDayEpisodesParam createGetSomeDayEpisodesParam(String date, long gameType, int liveType, List<Long> cids,
                                                                  String endDate, int status) {
        GetSomeDayEpisodesParam p = new GetSomeDayEpisodesParam();
        LiveTypeParam liveTypeParam = LiveTypeParam.findByValue(liveType);
        if (null != liveTypeParam) {
            p.setLiveTypeParam(liveTypeParam);
        }
        p.setDate(date);
        p.setGameType(gameType);
        p.setCids(cids);
        p.setEndDate(endDate);
        LiveShowStatusParam liveShowStatusParam = LiveShowStatusParam.findByValue(status);
        if (null != liveShowStatusParam) {
            p.setLiveShowStatusParam(liveShowStatusParam);
        }
        return p;
    }

    private CountEpisodesByDayParam createCountEpisodesByDayParam(String startDate, int days, long gameType,
                                                                  LiveTypeParam liveTypeParam) {
        CountEpisodesByDayParam p = new CountEpisodesByDayParam();
        p.setStartDate(startDate);
        p.setDays(days);
        p.setLiveTypeParam(liveTypeParam);
        p.setGameType(gameType);
        return p;
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
}
