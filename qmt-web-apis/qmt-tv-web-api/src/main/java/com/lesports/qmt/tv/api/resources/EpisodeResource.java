package com.lesports.qmt.tv.api.resources;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.lesports.LeConstants;
import com.lesports.api.common.Direction;
import com.lesports.api.common.Order;
import com.lesports.api.common.PageParam;
import com.lesports.api.common.Sort;
import com.lesports.jersey.AlternateMediaType;
import com.lesports.jersey.annotation.LJSONP;
import com.lesports.jersey.annotation.cache.LOG_URL;
import com.lesports.jersey.core.LeStatus;
import com.lesports.jersey.exception.LeWebApplicationException;
import com.lesports.jersey.support.model.CallerBean;
import com.lesports.jersey.support.model.PageBean;
import com.lesports.qmt.config.api.dto.TDictEntry;
import com.lesports.qmt.config.api.service.GetDictEntriesParam;
import com.lesports.qmt.config.client.QmtConfigApis;
import com.lesports.qmt.sbc.api.service.*;
import com.lesports.qmt.web.api.core.common.PeriodType;
import com.lesports.qmt.web.api.core.service.EpisodeService;
import com.lesports.qmt.web.api.core.service.VideoService;
import com.lesports.qmt.web.api.core.util.Constants;
import com.lesports.qmt.web.api.core.util.ResponseUtils;
import com.lesports.qmt.web.api.core.vo.DetailEpisodeVo;
import com.lesports.qmt.web.api.core.vo.HallEpisodeVo4Tv;
import com.lesports.qmt.web.api.core.vo.RoundVo;
import com.lesports.qmt.web.api.core.vo.VideoVo;
import com.lesports.utils.LeDateUtils;
import com.lesports.utils.LeStringUtils;
import com.lesports.utils.PageUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.*;
import java.util.Date;
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

    @Inject
    private VideoService videoService;

    /**
     * 从某天开始一段时间内每天的节目数量 - 日历使用
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
     * 比赛大厅 按状态查询
     * <p/>
     * startDate 起始时间
     * game_type 比赛分类
     * episode_type 节目类型 0:match 1:program
     * status 比赛状态: -1:全部 0:未开始 1:比赛中 2:已结束 3:未结束
     * live_type 节目包含的属性 0:只要直播 1:直播和重点赛程 2:所有节目
     * page 页码
     * count 每页条数
     */
    @LJSONP
    @GET
    @Path("day/episodes")
    @Produces(AlternateMediaType.UTF_8_APPLICATION_JSON)
    public Map<String, Object> getChannelStatusMatchs(@QueryParam("startDate") String startDate,
                                                      @QueryParam("gameType") @DefaultValue("0") long gameType,
                                                      @QueryParam("episodeType") @DefaultValue("-1") int episodeType,
                                                      @QueryParam("status") @DefaultValue("-1") int status,
                                                      @QueryParam("liveType") @DefaultValue("2") int liveType,
                                                      @QueryParam("page") int page,
                                                      @QueryParam("count") int count,
                                                      @BeanParam CallerBean callerBean) {

        try {
            PageParam pageParam = null;
            if (page > 0 || count > 0) {
                pageParam = PageUtils.createPageParam(page, count);
            }
            GetSomeDayEpisodesParam p = new GetSomeDayEpisodesParam();
            p.setLiveTypeParam(LiveTypeParam.findByValue(liveType));
            p.setDate(startDate);
            p.setGameType(gameType);
            p.setLiveShowStatusParam(LiveShowStatusParam.findByValue(status));
            p.setEpisodeTypeParam(EpisodeTypeParam.findByValue(episodeType));
            p.setSortParam(SortParam.ASC);
            List<HallEpisodeVo4Tv> episodes = Lists.newArrayList();
            episodes = episodeService.getHallEpisodes4Tv(p,pageParam,callerBean.getCallerParam());
            Map<String, Object> map =  ResponseUtils.createPageResponse(page, episodes);
            map.put("currentDate", LeDateUtils.formatYYYYMMDD(new Date()));
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
     * TV 按天及赛事id查询
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
                                                      @QueryParam("episodeType") @DefaultValue("0") int episodeType,
                                                      @QueryParam("status") @DefaultValue("-1") int status,
                                                      @QueryParam("liveType") @DefaultValue("2") int liveType,
                                                      @BeanParam PageBean pageBean,
                                                      @BeanParam CallerBean callerBean) {

        try {
            PageParam pageRequest = new PageParam();
            pageRequest.setCount(pageBean.getCount());
            pageRequest.setPage(pageBean.getPage());
            GetSomeDayEpisodesParam p = createGetSomeDayEpisodesParam(date, gameType, liveType,
                    LeStringUtils.commaString2LongList(cids), null,episodeType, status);

            List<HallEpisodeVo4Tv> episodes = episodeService.getEpisodesByCids4TV(p, pageRequest, callerBean.getCallerParam());
            return ResponseUtils.createPageResponse(pageBean.getPage(), episodes);
        } catch (LeWebApplicationException e) {
            LOG.error(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private GetSomeDayEpisodesParam createGetSomeDayEpisodesParam(String date, long gameType, int liveType, List<Long> cids,
                                                                  String endDate,int episodeType, int status) {
        GetSomeDayEpisodesParam p = new GetSomeDayEpisodesParam();
        LiveTypeParam liveTypeParam = LiveTypeParam.findByValue(liveType);
        if (null != liveTypeParam) {
            p.setLiveTypeParam(liveTypeParam);
        }
        p.setDate(date);
        p.setGameType(gameType);
        p.setEpisodeTypeParam(EpisodeTypeParam.findByValue(episodeType));
        p.setCids(cids);
        p.setEndDate(endDate);
        LiveShowStatusParam liveShowStatusParam = LiveShowStatusParam.findByValue(status);
        if (null != liveShowStatusParam) {
            p.setLiveShowStatusParam(liveShowStatusParam);
        }
        return p;
    }

    /**
     * 根据直播id获取节目详情
     * <p/>
     * id ID
     */
    @LOG_URL
    @LJSONP
    @GET
    @Path("/lives/{id}/episode")
    @Produces(AlternateMediaType.UTF_8_APPLICATION_JSON)
    public DetailEpisodeVo getEpisodeByLiveId(@PathParam("id") String liveId,
                                          @QueryParam("uid") String uid,
                                          @BeanParam CallerBean callerBean) {
        try {
            return episodeService.getDetailEpisodeByLiveId(liveId, callerBean.getCallerParam());
        } catch (LeWebApplicationException e) {
            LOG.error(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 根据专辑id获取自制节目列表
     * @param aid
     * @param type
     * @param pageBean
     * @param callerBean
     * @return
     */
    @LJSONP
    @GET
    @Path("/albums/{aid}/episodes")
    @Produces(AlternateMediaType.UTF_8_APPLICATION_JSON)
    public Map<String, Object> getEpisodeByAid(@PathParam("aid") long aid,
                                               @QueryParam("type")  @DefaultValue("0") int type,
                                               @BeanParam PageBean pageBean,
                                               @BeanParam CallerBean callerBean) {
        try {
            Preconditions.checkArgument(aid != 0, "请传入专辑id");
            List<VideoVo> latestEpisodeVos = videoService.getVideoVosByAid(aid, type,pageBean.getPageParam(),callerBean.getCallerParam());
            Map<String, Object> results = Maps.newHashMap();
            results.put(LeConstants.PAGE_KEY, pageBean.getPage());
            results.put(LeConstants.COUNT_KEY, latestEpisodeVos.size());
            results.put("title", "节目列表");
            results.put(LeConstants.ENTRIES_KEY, latestEpisodeVos);
            return results;
        } catch (LeWebApplicationException e) {
            LOG.error(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 根据专辑id获取自制节目选集
     *
     * @param id
     * @param pageBean
     * @param callerBean
     * @return
     */
    @LJSONP
    @GET
    @Path("ultimate/{id}/episodes")
    @Produces(AlternateMediaType.UTF_8_APPLICATION_JSON)
    public Map<String, Object> getEpisodeUltimateById(@PathParam("id") long id,
                                                      @QueryParam("type")  @DefaultValue("0") int type,
                                                      @BeanParam PageBean pageBean,
                                                      @BeanParam CallerBean callerBean) {
        try {
            Preconditions.checkArgument(id != 0, "请传入专辑id");
            return videoService.getUltimateMapById(id, type, pageBean.getPageParam(), callerBean.getCallerParam());
        } catch (LeWebApplicationException e) {
            LOG.error(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * @param cid      赛事id
     * @param csid     赛季id, 可以为0, 为0取最新赛季
     * @param round    轮次
     * @param status   比赛状态: -1:全部 0:未开始 1:比赛中 2:已结束 3:未结束
     * @param liveType 节目包含的属性 0:只要直播 1:直播和重点赛程 2:所有节目
     * @param pageBean
     * @param callerBean
     * @return
     */
    @LJSONP
    @GET
    @Path("step/episodes")
    @Produces(AlternateMediaType.UTF_8_APPLICATION_JSON)
    public Map<String, Object> getStepEpisodes(@QueryParam("cid") long cid,
                                               @QueryParam("csid") @DefaultValue("0") long csid,
                                               @QueryParam("round") int round,
                                               @QueryParam("status") @DefaultValue("-1") int status,
                                               @QueryParam("liveType") @DefaultValue("2") int liveType,
                                               @BeanParam PageBean pageBean,
                                               @BeanParam CallerBean callerBean) {

        try {
            Preconditions.checkArgument(cid != 0, "请传入赛事id");
            GetEpisodesOfSeasonByMetaEntryIdParam p = createGetEpisodesOfSeasonByMetaEntryIdParam(cid, csid, round, status, liveType, callerBean);
            return ResponseUtils.createPageResponse(pageBean.getPage(), episodeService.getEpisodesByCidAndRound4TV(p, pageBean.getPageParam(), callerBean.getCallerParam()));
        } catch (LeWebApplicationException e) {
            LOG.error(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private GetEpisodesOfSeasonByMetaEntryIdParam createGetEpisodesOfSeasonByMetaEntryIdParam(long cid, long csid, int round, int status, int liveType, CallerBean callerBean) {
        GetEpisodesOfSeasonByMetaEntryIdParam p = new GetEpisodesOfSeasonByMetaEntryIdParam();
        p.setCid(cid);
        p.setCsid(csid);
        p.setLiveShowStatusParam(LiveShowStatusParam.findByValue(status));
        p.setLiveTypeParam(LiveTypeParam.findByValue(liveType));
        GetDictEntriesParam p1 = new GetDictEntriesParam();
        p1.setName("^第" + round + "轮$");
        p1.setParentId(Constants.ROUND_PARENT_ID);
        List<TDictEntry> dictEntries = QmtConfigApis.getDictEntrys4SimpleSearch(p1, null, callerBean.getCallerParam());
        Preconditions.checkArgument(CollectionUtils.isNotEmpty(dictEntries), "轮次不存在");
        p.setEntryId(dictEntries.get(0).getId());
        return p;
    }

    /**
     * 获取比赛轮次信息
     *
     * @param cid
     * @param csid
     * @return
     */
    @LJSONP
    @GET
    @Path("round/episodes")
    @Produces(AlternateMediaType.UTF_8_APPLICATION_JSON)
    public RoundVo getEpisodesRound(@QueryParam("cid") long cid,
                                    @QueryParam("csid") @DefaultValue("0") long csid,
                                    @BeanParam CallerBean callerBean) {

        try {
            Preconditions.checkArgument(cid != 0, "请传入赛事id");
            return episodeService.getRoundVo(cid, csid,callerBean.getCallerParam());
        } catch (LeWebApplicationException e) {
            LOG.error(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 获取上一场，下一场
     *
     * @param eid
     * @param status
     * @param liveType
     * @return
     */
    @LJSONP
    @GET
    @Path("nearby/episodes")
    @Produces(AlternateMediaType.UTF_8_APPLICATION_JSON)
    public Map<String, DetailEpisodeVo> getNearbyEpisodes(@QueryParam("eid") long eid,
                                                          @QueryParam("status") @DefaultValue("-1") int status,
                                                          @QueryParam("liveType") @DefaultValue("2") int liveType,
                                                          @BeanParam CallerBean callerBean) {

        try {
            Preconditions.checkArgument(eid != 0, "请传入节目id");
            GetEpisodesNearbySomeEpisodeParam p = new GetEpisodesNearbySomeEpisodeParam();
            p.setEpisodeId(eid);
            p.setLiveTypeParam(LiveTypeParam.findByValue(liveType));
            p.setLiveShowStatusParam(LiveShowStatusParam.findByValue(status));

            Map<String, DetailEpisodeVo> map = episodeService.getNearbyEpisodes(p,callerBean.getCallerParam());
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
     * 一段时间内的直播节目
     * <p/>
     * startDate 起始时间
     * endDate 结束时间
     * game_type 比赛分类
     * episode_type 节目类型 0:match 1:program
     * status 比赛状态: -1:全部 0:未开始 1:比赛中 2:已结束 3:未结束
     * live_type 节目包含的属性 0:只要直播 1:直播和重点赛程 2:所有节目
     * page 页码
     * count 每页条数
     */
    @GET
    @LJSONP
    @Path("someDays/live/episodes")
    @Produces({AlternateMediaType.UTF_8_APPLICATION_JSON})
    public Map<String,Object> getSpanEpisode(@QueryParam("startDate") String startDate,
                                             @QueryParam("endDate") String endDate,
                                             @QueryParam("gameType") @DefaultValue("0") long gameType,
                                             @QueryParam("status") @DefaultValue("-1") int status,
                                             @QueryParam("liveType") @DefaultValue("2") int liveType,
                                             @BeanParam PageBean pageBean,
                                             @BeanParam CallerBean callerBean) {
        try {
            Preconditions.checkArgument(StringUtils.isNotBlank(startDate) && startDate.length() == 8, "startDate不为空,切格式为yyyyMMdd");
            Preconditions.checkArgument(StringUtils.isNotBlank(endDate) && endDate.length() == 8, "endDate,切格式为yyyyMMdd");
            GetSomeDayEpisodesParam p = new GetSomeDayEpisodesParam();
            p.setLiveTypeParam(LiveTypeParam.findByValue(liveType));
            p.setDate(startDate);
            p.setEndDate(endDate);
            p.setGameType(gameType);
            Sort sort = new Sort();
            sort.addToOrders(new Order("start_time", Direction.ASC));
            PageParam pageParam = pageBean.getPageParam();
            pageParam.setSort(sort);
            p.setLiveShowStatusParam(LiveShowStatusParam.findByValue(status));
            return ResponseUtils.createPageResponse(pageBean.getPage(),episodeService.getHallEpisodes4Tv(p,pageParam,callerBean.getCallerParam()));
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 查询会员频道数据
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
    @Path("/member/episodes")
    @Produces(AlternateMediaType.UTF_8_APPLICATION_JSON)
    public Map<String, Object> getMemberMatchs(@QueryParam("gameType") @DefaultValue("0") long gameType,
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

            List<HallEpisodeVo4Tv> episodes = episodeService.getHallMemberEpisodesForTv(p, pageBean.getPageParam(), callerBean.getCallerParam());
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

}
