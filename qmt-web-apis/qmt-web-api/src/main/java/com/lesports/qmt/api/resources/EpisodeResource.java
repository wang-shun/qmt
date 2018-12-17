package com.lesports.qmt.api.resources;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
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
import com.lesports.qmt.sbc.api.common.TicketType;
import com.lesports.qmt.sbc.api.dto.TComboEpisode;
import com.lesports.qmt.sbc.api.service.*;
import com.lesports.qmt.sbc.client.QmtSbcEpisodeApis;
import com.lesports.qmt.sbd.api.dto.TCompetitionSeason;
import com.lesports.qmt.sbd.client.SbdCompetitonSeasonApis;
import com.lesports.qmt.web.api.core.service.EpisodeService;
import com.lesports.qmt.web.api.core.util.ResponseUtils;
import com.lesports.qmt.web.api.core.vo.*;
import com.lesports.utils.LeDateUtils;
import com.lesports.utils.LeProperties;
import com.lesports.utils.LeStringUtils;
import com.lesports.utils.PageUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import com.lesports.qmt.web.api.core.common.PeriodType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.*;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by gengchengliang on 2015/6/18.
 */
@Path("/")
public class EpisodeResource {

    private static final Logger LOG = LoggerFactory.getLogger(EpisodeResource.class);

    private static  final  String cids = LeProperties.getString("need.preview.cids", "47001");

    private static  final  int previewIme = LeProperties.getInt("preview.time",300);

	private static final Map<Long, Integer> ROUNDID2ROUNDMAP =  Maps.newHashMap();

	private static final Map<Long, Long> ROUND2ROUNDIDMAP =  Maps.newHashMap();

	static {
		ROUNDID2ROUNDMAP.put(100031000l, 1);
		ROUNDID2ROUNDMAP.put(100032000l, 2);
		ROUNDID2ROUNDMAP.put(100033000l, 3);
		ROUNDID2ROUNDMAP.put(100034000l, 4);
		ROUNDID2ROUNDMAP.put(100035000l, 5);
		ROUNDID2ROUNDMAP.put(100036000l, 6);
		ROUNDID2ROUNDMAP.put(100037000l, 7);
		ROUNDID2ROUNDMAP.put(100038000l, 8);
		ROUNDID2ROUNDMAP.put(100039000l, 9);
		ROUNDID2ROUNDMAP.put(100040000l, 10);
		ROUNDID2ROUNDMAP.put(100041000l, 11);
		ROUNDID2ROUNDMAP.put(100042000l, 12);
		ROUNDID2ROUNDMAP.put(100043000l, 13);
		ROUNDID2ROUNDMAP.put(100044000l, 14);
		ROUNDID2ROUNDMAP.put(100045000l, 15);
		ROUNDID2ROUNDMAP.put(100046000l, 16);
		ROUNDID2ROUNDMAP.put(100047000l, 17);
		ROUNDID2ROUNDMAP.put(100048000l, 18);
		ROUNDID2ROUNDMAP.put(100049000l, 19);
		ROUNDID2ROUNDMAP.put(100050000l, 20);
		ROUNDID2ROUNDMAP.put(100051000l, 21);
		ROUNDID2ROUNDMAP.put(100052000l, 22);
		ROUNDID2ROUNDMAP.put(100053000l, 23);
		ROUNDID2ROUNDMAP.put(100054000l, 24);
		ROUNDID2ROUNDMAP.put(100055000l, 25);
		ROUNDID2ROUNDMAP.put(100056000l, 26);
		ROUNDID2ROUNDMAP.put(100057000l, 27);
		ROUNDID2ROUNDMAP.put(100058000l, 28);
		ROUNDID2ROUNDMAP.put(100059000l, 29);
		ROUNDID2ROUNDMAP.put(100060000l, 30);
		ROUNDID2ROUNDMAP.put(100061000l, 31);
		ROUNDID2ROUNDMAP.put(100062000l, 32);
		ROUNDID2ROUNDMAP.put(100063000l, 33);
		ROUNDID2ROUNDMAP.put(100064000l, 34);
		ROUNDID2ROUNDMAP.put(100065000l, 35);
		ROUNDID2ROUNDMAP.put(100066000l, 36);
		ROUNDID2ROUNDMAP.put(100067000l, 37);
		ROUNDID2ROUNDMAP.put(100068000l, 38);
		ROUNDID2ROUNDMAP.put(100069000l, 39);
		ROUNDID2ROUNDMAP.put(104609000l, 40);
		ROUNDID2ROUNDMAP.put(104610000l, 41);
		ROUNDID2ROUNDMAP.put(104611000l, 42);
		ROUNDID2ROUNDMAP.put(104612000l, 43);
		ROUNDID2ROUNDMAP.put(104613000l, 44);
		ROUNDID2ROUNDMAP.put(104614000l, 45);
		ROUNDID2ROUNDMAP.put(104615000l, 46);
		ROUNDID2ROUNDMAP.put(104616000l, 47);
		ROUNDID2ROUNDMAP.put(104617000l, 48);
		ROUNDID2ROUNDMAP.put(104618000l, 49);
		ROUNDID2ROUNDMAP.put(104619000l, 50);
	}

	static {
		ROUND2ROUNDIDMAP.put(1l, 100031000l);
		ROUND2ROUNDIDMAP.put(2l, 100032000l);
		ROUND2ROUNDIDMAP.put(3l, 100033000l);
		ROUND2ROUNDIDMAP.put(4l, 100034000l);
		ROUND2ROUNDIDMAP.put(5l, 100035000l);
		ROUND2ROUNDIDMAP.put(6l, 100036000l);
		ROUND2ROUNDIDMAP.put(7l, 100037000l);
		ROUND2ROUNDIDMAP.put(8l, 100038000l);
		ROUND2ROUNDIDMAP.put(9l, 100039000l);
		ROUND2ROUNDIDMAP.put(10l, 100040000l);
		ROUND2ROUNDIDMAP.put(11l, 100041000l);
		ROUND2ROUNDIDMAP.put(12l, 100042000l);
		ROUND2ROUNDIDMAP.put(13l, 100043000l);
		ROUND2ROUNDIDMAP.put(14l, 100044000l);
		ROUND2ROUNDIDMAP.put(15l, 100045000l);
		ROUND2ROUNDIDMAP.put(16l, 100046000l);
		ROUND2ROUNDIDMAP.put(17l, 100047000l);
		ROUND2ROUNDIDMAP.put(18l, 100048000l);
		ROUND2ROUNDIDMAP.put(19l, 100049000l);
		ROUND2ROUNDIDMAP.put(20l, 100050000l);
		ROUND2ROUNDIDMAP.put(21l, 100051000l);
		ROUND2ROUNDIDMAP.put(22l, 100052000l);
		ROUND2ROUNDIDMAP.put(23l, 100053000l);
		ROUND2ROUNDIDMAP.put(24l, 100054000l);
		ROUND2ROUNDIDMAP.put(25l, 100055000l);
		ROUND2ROUNDIDMAP.put(26l, 100056000l);
		ROUND2ROUNDIDMAP.put(27l, 100057000l);
		ROUND2ROUNDIDMAP.put(28l, 100058000l);
		ROUND2ROUNDIDMAP.put(29l, 100059000l);
		ROUND2ROUNDIDMAP.put(30l, 100060000l);
		ROUND2ROUNDIDMAP.put(31l, 100061000l);
		ROUND2ROUNDIDMAP.put(32l, 100062000l);
		ROUND2ROUNDIDMAP.put(33l, 100063000l);
		ROUND2ROUNDIDMAP.put(34l, 100064000l);
		ROUND2ROUNDIDMAP.put(35l, 100065000l);
		ROUND2ROUNDIDMAP.put(36l, 100066000l);
		ROUND2ROUNDIDMAP.put(37l, 100067000l);
		ROUND2ROUNDIDMAP.put(38l, 100068000l);
		ROUND2ROUNDIDMAP.put(39l, 100069000l);
		ROUND2ROUNDIDMAP.put(40l, 104609000l);
		ROUND2ROUNDIDMAP.put(41l, 104610000l);
		ROUND2ROUNDIDMAP.put(42l, 104611000l);
		ROUND2ROUNDIDMAP.put(43l, 104612000l);
		ROUND2ROUNDIDMAP.put(44l, 104613000l);
		ROUND2ROUNDIDMAP.put(45l, 104614000l);
		ROUND2ROUNDIDMAP.put(46l, 104615000l);
		ROUND2ROUNDIDMAP.put(47l, 104616000l);
		ROUND2ROUNDIDMAP.put(48l, 104617000l);
		ROUND2ROUNDIDMAP.put(49l, 104618000l);
		ROUND2ROUNDIDMAP.put(50l, 104619000l);
	}

    @Inject
    EpisodeService episodeService;

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
     * 直播日历每天的直播节目
     * <p/>
     * startDate 时间,精确到天(yyyyMMdd:20150528)
     * gameType 比赛分类
     * episodeType 节目类型 0:match 1:program 2:all
     * includeKey 是否包含重点赛程 0:不包含 1:包含
     */
    @GET
    @LJSONP
    @Path("someday/live/episodes")
    @Produces({AlternateMediaType.UTF_8_APPLICATION_JSON})
    public List<CalEpisodeVo> getDayEpisode(@QueryParam("startDate") String date,
                                            @QueryParam("gameType") @DefaultValue("0") long gameType,
                                            @QueryParam("episodeType") @DefaultValue("0") int episodeType,
                                            @QueryParam("includeKey") @DefaultValue("0") String includeKey,
                                            @BeanParam PageBean pageBean,
                                            @BeanParam CallerBean callerBean) {
        try {
            Preconditions.checkArgument(StringUtils.isNotBlank(date) && date.length() == 8, "startDate不为空,切格式为yyyyMMdd");
            GetSomeDayEpisodesParam p = new GetSomeDayEpisodesParam();
            if (includeKey.equals("0")) {
                p.setLiveTypeParam(LiveTypeParam.LIVE_TLIVE_KEY);
            } else if (includeKey.equals("1")) {
                p.setLiveTypeParam(LiveTypeParam.LIVE_OR_TLIVE);
            }
            p.setDate(date);
            p.setGameType(gameType);
            return episodeService.getDayEpisodesWithFallback(p, null, callerBean.getCallerParam(), true);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 直播日历N天的直播节目
     * <p/>
     * startDate 起始时间,精确到天(yyyyMMdd:20150528)
     * gameType 比赛分类
     * episodeType 节目类型 0:match 1:program
     * includeKey 是否包含重点赛程 0:不包含 1:包含
     * spanUnit 时长单位(1天:d,1周:w) 默认d
     * span 时长 默认是1,需要配合上面的spanUnit使用
     */
    @GET
    @LJSONP
    @Path("day/live/episodes")
    @Produces({AlternateMediaType.UTF_8_APPLICATION_JSON})
    public List<CalEpisodeVo> getSomedayEpisode(@QueryParam("startDate") String startDate,
                                                @QueryParam("gameType") @DefaultValue("0") long gameType,
                                                @QueryParam("episodeType") @DefaultValue("0") int episodeType,
                                                @QueryParam("includeKey") @DefaultValue("0") String includeKey,
                                                @QueryParam("spanUnit") @DefaultValue("d") String spanUnit,
                                                @QueryParam("span") @DefaultValue("1") int span,
                                                @BeanParam PageBean pageBean,
                                                @BeanParam CallerBean callerBean) {
        try {
            Preconditions.checkArgument(StringUtils.isNotBlank(startDate) && startDate.length() == 8, "startDate不为空,切格式为yyyyMMdd");
            GetSomeDayEpisodesParam p = new GetSomeDayEpisodesParam();
            if (includeKey.equals("0")) {
                p.setLiveTypeParam(LiveTypeParam.LIVE_TLIVE_KEY);
            } else if (includeKey.equals("1")) {
                p.setLiveTypeParam(LiveTypeParam.LIVE_OR_TLIVE);
			} else if (includeKey.equals("2")) {
				p.setLiveTypeParam(LiveTypeParam.NOT_ONLY_LIVE);
			}
            p.setDate(startDate);
            p.setGameType(gameType);
            int days = span * (PeriodType.findByValue(spanUnit).getDay());
            return episodeService.getSomedayEpisodes(startDate,days,p,pageBean.getPageParam(),callerBean.getCallerParam());
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * @param cid      赛事id
     * @param csid     赛季id
     * @param entryId  字典id : 支持 大项,小项,轮次,阶段,分组,分站
     * @param status   比赛状态: -1:全部 0:未开始 1:比赛中 2:已结束 3:未结束
     * @param liveType 节目包含的属性 0:只要直播 1:直播和重点赛程 2:所有节目 3:仅仅重点赛事 4：直播or图文直播
     * @return
     */
    @LJSONP
    @GET
    @Path("competitions/{cid}/entry/{entryId}/episodes")
    @Produces(AlternateMediaType.UTF_8_APPLICATION_JSON)
    public Map<String, Object> getMatchEpisodesByCsidAndRoundId(@PathParam("cid") @DefaultValue("0") long cid,
                                                                @PathParam("csid") @DefaultValue("0") long csid,
                                                                @PathParam("entryId") @DefaultValue("0") long entryId,
                                                                @QueryParam("status") @DefaultValue("-1") int status,
                                                                @QueryParam("liveType") @DefaultValue("2") int liveType,
                                                                @BeanParam PageBean pageBean,
                                                                @BeanParam CallerBean callerBean) {

        try {
            GetEpisodesOfSeasonByMetaEntryIdParam p = createGetEpisodesOfSeasonByMetaEntryIdParam(cid, csid, entryId, status, liveType);
            return ResponseUtils.createPageResponse(pageBean.getPage(), "episodes", episodeService.getMatchEpisodesByCidAndMetaEntryId(p, pageBean.getPageParam(), callerBean.getCallerParam()));
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
            }
            GetSomeDayEpisodesParam p = createGetSomeDayEpisodesParam(date, gameType, liveType, episodeType);
            return ResponseUtils.createPageResponse(page, "episodes", episodeService.getSomeDayEpisodesWithFallback(p, pageParam, callerBean.getCallerParam()));
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
     * @param cid      赛事id
     * @param csid     赛季id, 可以为0, 为0取最新赛季
     * @param dictId   阶段的字典id 支持(大项,小项,轮次,阶段,分站,分组)
     * @param status   比赛状态: -1:全部 0:未开始 1:比赛中 2:已结束 3:未结束
     * @param liveType 节目包含的属性 0:只要直播 1:直播和重点赛程 2:所有节目
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
            return ResponseUtils.createPageResponse(pageBean.getPage(),"episodes",episodeService.getMatchEpisodesByCidAndMetaEntryId(p,pageBean.getPageParam(),callerBean.getCallerParam()));
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
     * @param roundId  轮次id
     * @param status   比赛状态: -1:全部 0:未开始 1:比赛中 2:已结束 3:未结束
     * @param liveType 节目包含的属性 0:只要直播 1:直播和重点赛程 2:所有节目
     * @return
     */
    @LJSONP
    @GET
    @Path("round/{roundId}/episodes")
    @Produces(AlternateMediaType.UTF_8_APPLICATION_JSON)
    public Map<String, Object> getRoundEpisodes(@PathParam("roundId") @DefaultValue("0") long roundId,
                                                @QueryParam("cid") long cid,
                                                @QueryParam("csid") long csid,
                                                @QueryParam("status") @DefaultValue("-1") int status,
                                                @QueryParam("liveType") @DefaultValue("2") int liveType,
                                                @BeanParam PageBean pageBean,
                                                @BeanParam CallerBean callerBean) {

        try {
            Preconditions.checkArgument(cid != 0, "请传入赛事id");
            GetEpisodesOfSeasonByMetaEntryIdParam p = createGetEpisodesOfSeasonByMetaEntryIdParam(cid, csid, roundId, status, liveType);
            int totalRound = 0;
            long currentRoundId = 0;

			TCompetitionSeason tCompetitionSeason = null;
			if (csid > 0) {
				//获取某赛季
				tCompetitionSeason =  SbdCompetitonSeasonApis.getTCompetitionSeasonById(csid, callerBean.getCallerParam());
			} else if (csid <= 0) {
				//赛季id为0，获取最新赛季
				tCompetitionSeason = SbdCompetitonSeasonApis.getLatestTCompetitionSeasonsByCid(cid,callerBean.getCallerParam());
			}
			if (tCompetitionSeason == null) {
				LOG.warn("get competition season is empty. cid : {}, csid : {}, roundId : {}", cid, csid, roundId);
				return Collections.EMPTY_MAP;
			}
			currentRoundId = tCompetitionSeason.getCurrentRoundId();
			totalRound = tCompetitionSeason.getTotalRound();
			if (currentRoundId <= 0) {
				LOG.warn("getEpisodesByCidAndRoundId roundId is empty. cid : {}, csid : {}, roundId : {}", cid, csid, roundId);
				return Collections.EMPTY_MAP;
			}

            if (roundId <= 0) {
				roundId = currentRoundId;
            } else {
				if (roundId < 100) {//如果接口传入的是轮次,则转换为轮次ID
					roundId = ROUND2ROUNDIDMAP.get(roundId);
				}
			}
            p.setEntryId(roundId);
            return ResponseUtils.createPageResponse(pageBean.getPage(), "episodes", totalRound, ROUNDID2ROUNDMAP.get(currentRoundId), episodeService.getMatchEpisodesByCidAndMetaEntryId(p, pageBean.getPageParam(), callerBean.getCallerParam()));
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
     * @param dictId   阶段的字典id 支持(大项,小项,轮次,阶段,分站,分组)
     * @param status   比赛状态: -1:全部 0:未开始 1:比赛中 2:已结束 3:未结束
     * @param liveType 节目包含的属性 0:只要直播 1:直播和重点赛程 2:所有节目 3:只要重点节目 4:直播,重点,图文直播
     * @return
     */
    @LJSONP
    @GET
    @Path("topic/step/episodes")
    @Produces(AlternateMediaType.UTF_8_APPLICATION_JSON)
    public Map<String, Object> getTopicStepEpisodes(@QueryParam("cid") long cid,
                                                    @QueryParam("csid") long csid,
                                                    @QueryParam("dictId") long dictId,
                                                    @QueryParam("status") @DefaultValue("-1") int status,
                                                    @QueryParam("liveType") @DefaultValue("2") int liveType,
                                                    @BeanParam PageBean pageBean,
                                                    @BeanParam CallerBean callerBean) {

        try {
            Preconditions.checkArgument(cid != 0, "请传入赛事id");
            GetEpisodesOfSeasonByMetaEntryIdParam p = createGetEpisodesOfSeasonByMetaEntryIdParam(cid, csid, dictId, status, liveType);
            return ResponseUtils.createPageResponse(pageBean.getPage(),"episodes",episodeService.getTopicEpisodesByCidAndMetaEntryId(p,pageBean.getPageParam(),callerBean.getCallerParam()));
        } catch (LeWebApplicationException e) {
            LOG.error(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 获取节目
     * <p/>
     * id ID
     */
    @LOG_URL
    @LJSONP
    @GET
    @Path("episodes/{id}")
    @Produces(AlternateMediaType.UTF_8_APPLICATION_JSON)
    public DetailEpisodeVo getEpisodeById(@PathParam("id") long id,
                                          @BeanParam CallerBean callerBean) {
        try {
            DetailEpisodeVo episode = episodeService.getEpisodeById(id, callerBean.getCallerParam());
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
     * 轮询比分和状态的接口
     * <p/>
     * id ID
     */
    @LOG_URL(processor = BatchLogUrlProcessor.class)
    @LJSONP
    @GET
    @Path("refresh/episodes")
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
    @Path("multi/episodes")
    @Produces(AlternateMediaType.UTF_8_APPLICATION_JSON)
    public List<HallEpisodeVo> getMultiEpisodesByIds(@QueryParam("ids") String ids,
                                                     @BeanParam CallerBean callerBean) {
        try {
            Preconditions.checkArgument(StringUtils.isNotBlank(ids), "请传入ids,多个已英文逗号隔开");
            return episodeService.getMultiEpisodesByIds(ids, callerBean.getCallerParam());
        } catch (LeWebApplicationException e) {
            LOG.error(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 通过参赛者id获取节目
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
                                                         @BeanParam PageBean pageBean,
                                                         @BeanParam CallerBean callerBean) {
        try {
            Preconditions.checkArgument(id != 0, "请传入参赛者id");
            GetEpisodesOfSeasonByMetaEntryIdParam p = new GetEpisodesOfSeasonByMetaEntryIdParam();
            p.setCsid(csid);
            p.setLiveShowStatusParam(LiveShowStatusParam.findByValue(status));
            p.setLiveTypeParam(LiveTypeParam.findByValue(liveType));
            return episodeService.getEpisodesByCompetitorId(id, p,pageBean.getPageParam(),callerBean.getCallerParam());
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
    @Path("{aid}/episodes")
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


    //todo bad restful

    /**
     * 根据比赛id获取直播id
     *
     * @param mid
     * @return
     */
    @LOG_URL
    @LJSONP
    @GET
    @Path("liveStreams/{mid}/episode")
    @Produces(AlternateMediaType.UTF_8_APPLICATION_JSON)
    public Map<String, Object> getEpisodeByliveId(@PathParam("mid") long mid,
                                                  @BeanParam CallerBean callerBean) {
        try {
            Preconditions.checkArgument(mid != 0, "请传入比赛id");
            TComboEpisode comboEpisode = QmtSbcEpisodeApis.getEpisodeByMid(mid, callerBean.getCallerParam());
            if(null != comboEpisode){
                return  ResponseUtils.createPageResponse(1, comboEpisode.getStreams());
            }
            return ResponseUtils.createEmptyPageResponse(1);

        } catch (LeWebApplicationException e) {
            LOG.error(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 滚球精英节目接口,按天查询
     * <p/>
     * startDate 起始时间,精确到天(yyyyMMdd:20150528)
     * gameType 比赛分类
     * span 时长 默认是1,需要配合上面的spanUnit使用
     */
    @GET
    @LJSONP
    @Path("zhangyu/game/episodes")
    @Produces({AlternateMediaType.UTF_8_APPLICATION_JSON})
    public List<CalEpisodeVo> getZhangyuEpisode(@QueryParam("startDate") String startDate,
												@QueryParam("cid") @DefaultValue("0") long cid,
												@QueryParam("status") @DefaultValue("-1") int status,
                                                @QueryParam("gameType") @DefaultValue("0") long gameType,
                                                @QueryParam("spanUnit") @DefaultValue("d") String spanUnit,
                                                @QueryParam("span") @DefaultValue("1") int span,
                                                @BeanParam PageBean pageBean,
                                                @BeanParam CallerBean callerBean) {
        try {
            Preconditions.checkArgument(StringUtils.isNotBlank(startDate) && startDate.length() == 8, "startDate不为空,切格式为yyyyMMdd");
            GetSomeDayEpisodesParam p = new GetSomeDayEpisodesParam();
            p.setDate(startDate);
            p.setGameType(gameType);
			p.setOctopus(true);
			if (cid > 0) {
				p.setCids(Lists.newArrayList(cid));
			}
			p.setLiveShowStatusParam(LiveShowStatusParam.findByValue(status));
            int days = span * (PeriodType.findByValue(spanUnit).getDay());
            return episodeService.getSomedayEpisodes(startDate,days,p,pageBean.getPageParam(),callerBean.getCallerParam());
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 滚球精英节目接口,按照赛事查询
     * <p/>
     * cids 赛事ids,多个用英文逗号隔开
     * status 比赛状态: -1:全部 0:未开始 1:比赛中 2:已结束 3:未结束
     * liveType 节目包含的属性 0:只要直播 1:直播和重点赛程 2:所有节目
     * page 页码
     * count 每页条数
     */
    @GET
    @LJSONP
    @Path("zhangyu/competition/episodes")
    @Produces({AlternateMediaType.UTF_8_APPLICATION_JSON})
    public Map<String, Object> getZhangyuEpisodesByCids(@QueryParam("cids") @DefaultValue("") String cids,
                                                        @QueryParam("status") @DefaultValue("-1") int status,
                                                        @QueryParam("liveType") @DefaultValue("2") int liveType,
                                                        @BeanParam PageBean pageBean,
                                                        @BeanParam CallerBean callerBean) {
        try {
            Preconditions.checkArgument(StringUtils.isNotBlank(cids), "请传入cids,多个已英文逗号隔开");
            GetCurrentEpisodesParam p = new GetCurrentEpisodesParam();
            p.setLiveShowStatusParam(LiveShowStatusParam.findByValue(status));
            p.setLiveTypeParam(LiveTypeParam.findByValue(liveType));
            p.setCids(LeStringUtils.commaString2LongList(cids));
            List<HallEpisodeVo> episodes = episodeService.getZhangyuEpisodesByCids(p,pageBean.getPageParam(),callerBean.getCallerParam());
            return ResponseUtils.createPageResponse(pageBean.getPage(), episodes);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }


    /**
     * 滚球精英节目接口
     * <p/>
     * startDate 起始时间,精确到天(yyyyMMdd:20150528)
     * gameType 比赛分类
     * span 时长 默认是1,需要配合上面的spanUnit使用
     */
//    @GET
//    @LJSONP
//    @Path("sync/zhangyu/episodes")
//    @Produces({AlternateMediaType.UTF_8_APPLICATION_JSON})
//    public Map<String, String> getSyncZhangyuEpisode() {
//        try {
//            return QmtSbcEpisodeApis.syncZhangyuGames();
//        } catch (Exception e) {
//            LOG.error(e.getMessage(), e);
//            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
//        }
//    }

    /**
     * 根据章鱼赛程id获取赛程列表
     *
     * @param mids     章鱼赛程id(多个用英文逗号隔开)
     * @param liveType 节目包含的属性 0:只要直播 1:直播和重点赛程 2:所有节目 3:只要重点节目 4:直播,重点,图文直播
     * @return
     */
    @GET
    @LJSONP
    @Path("zhangyu/episodes")
    @Produces({AlternateMediaType.UTF_8_APPLICATION_JSON})
    public List<ZhangyuEpisodeVo> getZhangyuEpisodesByMids(@QueryParam("mids") String mids,
                                                           @QueryParam("liveType") @DefaultValue("2") int liveType,
                                                           @BeanParam PageBean pageBean,
                                                           @BeanParam CallerBean callerBean) {
        try {
            Preconditions.checkArgument(StringUtils.isNotBlank(mids), "请传入章鱼赛程id,多个用英文逗号隔开!");
            GetZhangyuEpisodesParam p = new GetZhangyuEpisodesParam();
            p.setLiveTypeParam(LiveTypeParam.findByValue(liveType));
            p.setMids(LeStringUtils.commaString2LongList(mids));
            return episodeService.getZhangyuEpisodesByMids(p,pageBean.getPageParam(),callerBean.getCallerParam());
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }


    /**
     * APP 按状态查询(乐视体育高尔夫用)
     * <p/>
     * gameType 比赛分类
     * episodeType 节目类型 0:match 1:program
     * status 比赛状态: -1:全部 0:未开始 1:比赛中 2:已结束 3:未结束
     * liveType 节目包含的属性 0:只要直播 1:直播和重点赛程 2:所有节目 3:仅仅重点赛事 4：直播or图文直播
     * page 页码
     * count 每页条数
     */
    @LJSONP
    @GET
    @Path("app/episodes")
    @Produces(AlternateMediaType.UTF_8_APPLICATION_JSON)
    public Map<String, Object> getChannelStatusMatchs(@QueryParam("gameType") @DefaultValue("0") long gameType,
                                                      @QueryParam("episodeType") @DefaultValue("0") int episodeType,
                                                      @QueryParam("status") @DefaultValue("-1") int status,
                                                      @QueryParam("liveType") @DefaultValue("2") int liveType,
                                                      @BeanParam PageBean pageBean,
                                                      @BeanParam CallerBean callerBean) {

        try {
            LiveTypeParam liveTypeParam = LiveTypeParam.findByValue(liveType);
            LiveShowStatusParam liveShowStatusParam = LiveShowStatusParam.findByValue(status);
            GetCurrentEpisodesParam p = createGetCurrentEpisodesParam(gameType, liveTypeParam, liveShowStatusParam);

            List<HallEpisodeVo> episodes = episodeService.getHallEpisodes4App(p, pageBean.getPageParam(), callerBean.getCallerParam());
            return ResponseUtils.createPageResponse(pageBean.getPage(),"episodes", episodes);
        } catch (LeWebApplicationException e) {
            LOG.error(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }

    }


    private GetCurrentEpisodesParam createGetCurrentEpisodesParam(long gameType, LiveTypeParam liveTypeParam,
                                                                  LiveShowStatusParam liveShowStatusParam) {
        GetCurrentEpisodesParam p = new GetCurrentEpisodesParam();
        p.setGameType(gameType);
        p.setLiveTypeParam(liveTypeParam);
        p.setLiveShowStatusParam(liveShowStatusParam);
        return p;
    }


    /**
     * 根据直播流判断是否需要试看
     * @param liveId
     * @return
     */
    @LOG_URL
    @LJSONP
    @GET
    @Path("live/{liveId}/preview")
    @Produces(AlternateMediaType.UTF_8_APPLICATION_JSON)
    public PreviewVo previewEpisode(@PathParam("liveId") String liveId,
                                    @BeanParam CallerBean callerBean) {
        try {
            Preconditions.checkArgument(StringUtils.isNotEmpty(liveId), "请传入直播id");
            HallEpisodeVo episode = episodeService.getEpisodeByLiveId(liveId,callerBean.getCallerParam());
            Long cid = episode.getCid();
            return getPreviewVo(cid);
        } catch (LeWebApplicationException e) {
            LOG.error(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private PreviewVo getPreviewVo(Long cid) {
        PreviewVo previewVo = new PreviewVo();
        if(null != cid && cid > 0){
            List<Long> cidList = LeStringUtils.commaString2LongList(cids);
            if(CollectionUtils.isNotEmpty(cidList)) {
                for(long previewCid : cidList) {
                    if(cid == previewCid) {
                        previewVo.setNeedPreview(1);
                        previewVo.setPreviewTime(previewIme);
                        return previewVo;
                    }
                }
            }
        }
        previewVo.setNeedPreview(0);
        return previewVo;
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
													  @QueryParam("cids") @DefaultValue("0") String cids,
													  @QueryParam("csid") @DefaultValue("0") long csid,
													  @QueryParam("status") @DefaultValue("-1") int status,
													  @QueryParam("liveType") @DefaultValue("2") int liveType,
													  @BeanParam PageBean pageBean,
													  @BeanParam CallerBean callerBean) {

		try {
			LiveTypeParam liveTypeParam = LiveTypeParam.findByValue(liveType);
			LiveShowStatusParam liveShowStatusParam = LiveShowStatusParam.findByValue(status);
			GetCurrentEpisodesParam p = createGetCurrentEpisodesParam(gameType, liveTypeParam, liveShowStatusParam, LeStringUtils.commaString2LongList(cids), csid);
            Sort sort = new Sort();
            sort.addToOrders(new Order("start_time", Direction.ASC));
			List<HallEpisodeVo> episodes = episodeService.getHallEpisodes4AppWithFallback(p, PageUtils.createPageParam(pageBean.getPage(), pageBean.getCount(), sort), callerBean.getCallerParam());
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
																  LiveShowStatusParam liveShowStatusParam, List<Long> cids, Long csid) {
		GetCurrentEpisodesParam p = new GetCurrentEpisodesParam();
		p.setGameType(gameType);
		p.setLiveTypeParam(liveTypeParam);
		p.setLiveShowStatusParam(liveShowStatusParam);
		p.setCids(cids);
		p.setCsid(csid);
		return p;
	}


	/**
	 * 香港首页/MLB获取赛程列表的接口
	 * status 比赛状态: -1:全部 0:未开始 1:比赛中 2:已结束 3:未结束
	 * live_type 节目包含的属性 0:只要直播 1:直播和重点赛程 2:所有节目
	 * page 页码
	 * count 每页条数
	 */
	@LJSONP
	@GET
	@Path("/timeline/episodes")
	@Produces(AlternateMediaType.UTF_8_APPLICATION_JSON)
	public Map<String, Object> getTimelineEpisodes(@QueryParam("startDate") String startDate,
												   @QueryParam("cids") String cids,
													  @QueryParam("csid") @DefaultValue("0") long csid,
													  @QueryParam("status") @DefaultValue("-1") int status,
													  @QueryParam("liveType") @DefaultValue("2") int liveType,
													  @BeanParam PageBean pageBean,
													  @BeanParam CallerBean callerBean) {

		try {
			LiveTypeParam liveTypeParam = LiveTypeParam.findByValue(liveType);
			LiveShowStatusParam liveShowStatusParam = LiveShowStatusParam.findByValue(status);
			if (StringUtils.isBlank(startDate)) {
				startDate = LeDateUtils.formatYYYYMMDD(new Date());
			} else {
				Preconditions.checkArgument(StringUtils.isNotBlank(startDate) && startDate.length() == 8, "startDate不为空,切格式为yyyyMMdd");
			}
			Preconditions.checkArgument(StringUtils.isNotBlank(cids), "please check cids !");
			GetCurrentEpisodesParam p = createGetCurrentEpisodesParam(startDate, liveTypeParam, liveShowStatusParam, LeStringUtils.commaString2LongList(cids), csid);
			Sort sort = new Sort();
			if (pageBean.getPage() > 0) {
				sort.addToOrders(new Order("start_time", Direction.ASC));
			}
			if (pageBean.getPage() < 0) {
				sort.addToOrders(new Order("start_time", Direction.DESC));
			}
			List<HallEpisodeVo> episodes = episodeService.getTimelineEpisodesByCids(p, PageUtils.createPageParam(Math.abs(pageBean.getPage()), pageBean.getCount(), sort), callerBean.getCallerParam());
			return ResponseUtils.createPageResponse(pageBean.getPage(), episodes);
		} catch (LeWebApplicationException e) {
			LOG.error(e.getMessage(), e);
			throw e;
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
		}
	}

	private GetCurrentEpisodesParam createGetCurrentEpisodesParam(String startDate, LiveTypeParam liveTypeParam,
																  LiveShowStatusParam liveShowStatusParam, List<Long> cids, Long csid) {
		GetCurrentEpisodesParam p = new GetCurrentEpisodesParam();
		p.setStartDate(startDate);
		p.setLiveTypeParam(liveTypeParam);
		p.setLiveShowStatusParam(liveShowStatusParam);
		p.setCids(cids);
		p.setCsid(csid);
		return p;
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

            List<HallEpisodeVo> episodes = episodeService.getHallMemberEpisodes(p, pageBean.getPageParam(), callerBean.getCallerParam());
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
	 * 获取直播券对应的赛程列表
	 * ticketId 直播券ID
	 * ticketType 直播券type 1:赛事券 2:单场券
	 * status 比赛状态: -1:全部 0:未开始 1:比赛中 2:已结束 3:未结束
	 * page 页码
	 * count 每页条数
	 */
	@LJSONP
	@GET
	@Path("/ticket/episodes")
	@Produces(AlternateMediaType.UTF_8_APPLICATION_JSON)
	public Map<String, Object> getTicketMatchs(@QueryParam("ticketId") @DefaultValue("0") String ticketId,
											   @QueryParam("ticketType") @DefaultValue("0") int ticketType,
											   @QueryParam("status") @DefaultValue("-1") int status,
											   @BeanParam PageBean pageBean,
											   @BeanParam CallerBean callerBean) {

		try {
			Preconditions.checkArgument(StringUtils.isNotBlank(ticketId), "please check your ticketId !");
			Preconditions.checkArgument(ticketType > 0, "please check your ticketType !");
			LiveShowStatusParam liveShowStatusParam = LiveShowStatusParam.findByValue(status);
			TicketType type = TicketType.findByValue(ticketType);
			GetCurrentEpisodesParam p = createGetTicketEpisodesParam(ticketId, type, liveShowStatusParam);

			List<HallEpisodeVo> episodes = episodeService.getTicketEpisodes(p, pageBean.getPageParam(), callerBean.getCallerParam());
			return ResponseUtils.createPageResponse(pageBean.getPage(), episodes);
		} catch (LeWebApplicationException e) {
			LOG.error(e.getMessage(), e);
			throw e;
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
		}
	}

	private GetCurrentEpisodesParam createGetTicketEpisodesParam(String ticketId, TicketType ticketType,
																  LiveShowStatusParam liveShowStatusParam) {
		GetCurrentEpisodesParam p = new GetCurrentEpisodesParam();
		p.setTicketId(ticketId);
		p.setTicketType(ticketType);
		p.setLiveShowStatusParam(liveShowStatusParam);
		return p;
	}

	/**
	 * pc新版首页
	 * @param callerBean
	 * @param cids
	 * @return
	 */
	@LJSONP
	@GET
	@Path("home/page/episodes")
	@Produces(AlternateMediaType.UTF_8_APPLICATION_JSON)
	public Map<String, Object> getHomePageEpisodes(@BeanParam CallerBean callerBean,
												   @QueryParam("cids") String cids) {
		try {
			return ResponseUtils.createPageResponse(1, "episodes", episodeService.getHomePageEpisodes(cids, callerBean.getCallerParam()));
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw new LeWebFallbackException(e.getMessage());
		}
	}
}
