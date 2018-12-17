package com.lesports.qmt.op.web.api.resources;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.lesports.LeConstants;
import com.lesports.api.common.*;
import com.lesports.jersey.AlternateMediaType;
import com.lesports.jersey.annotation.ENCRYPT;
import com.lesports.jersey.annotation.LJSONP;
import com.lesports.jersey.core.LeStatus;
import com.lesports.jersey.exception.LeWebApplicationException;
import com.lesports.qmt.op.web.api.core.service.EpisodeService;
import com.lesports.qmt.op.web.api.core.util.ResponseUtils;
import com.lesports.qmt.op.web.api.core.vo.DetailEpisodeVo;
import com.lesports.qmt.op.web.api.core.vo.HallEpisodeVo;
import com.lesports.qmt.sbc.api.service.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.*;
import java.util.List;
import java.util.Map;

/**
 * Created by lufei1 on 2015/8/13.
 */
@Path("/")
public class EpisodeResource {

    private static final Logger LOG = LoggerFactory.getLogger(EpisodeResource.class);


    @Inject
    private EpisodeService episodeService;

    /**
     * 百度影音直播列表
     *
     * @param startDate
     * @param span
     * @return
     */
    @GET
    @LJSONP
    @Path("episodes/baidu")
    @ENCRYPT
    @Produces({AlternateMediaType.UTF_8_APPLICATION_JSON})
    public Map<String, Object> getBaiduLiveEpisodes(@QueryParam("startDate") String startDate, @QueryParam("span") int span) {
        try {
            if (span < 0 || span > 7) {
                throw new LeWebApplicationException(LeStatus.PARAM_INVALID.getReason(), LeStatus.PARAM_INVALID);
            }
            PageParam pageParam = new PageParam();
            pageParam.setCount(100);
            pageParam.setPage(1);

            CallerParam callerParam = new CallerParam();
            callerParam.setCallerId(LeConstants.LESPORTS_PC_CALLER_CODE);
            callerParam.setCountry(CountryCode.CN);
            callerParam.setLanguage(LanguageCode.ZH_CN);

            return episodeService.getBaiduLiveEpisodes(startDate, span,pageParam,callerParam);
        } catch (LeWebApplicationException e) {
            LOG.error(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }


    /**
     * 按天查询节目
     *
     * @param date
     * @param gameType    比赛分类
     * @param episodeType 节目类型 0:match 1:program
     * @param page
     * @param count
     * @return
     */
    @LJSONP
    @GET
    @ENCRYPT
    @Path("day/episodes")
    @Produces(AlternateMediaType.UTF_8_APPLICATION_JSON)
    public Map<String, Object> getHallDayEpisodes(@QueryParam("startDate") String date,
                                                  @QueryParam("gameType") @DefaultValue("0") long gameType,
                                                  @QueryParam("episodeType") @DefaultValue("0") int episodeType,
                                                  @QueryParam("caller") long callerId,
                                                  @QueryParam("page") @DefaultValue("1") int page,
                                                  @QueryParam("count") @DefaultValue("20") int count) {

        try {
            Preconditions.checkArgument(StringUtils.isNotBlank(date) && date.length() == 8, "startDate不为空,切格式为yyyyMMdd");
            PageParam pageParam = new PageParam();
            pageParam.setCount(count);
            pageParam.setPage(page > 0 ? page : 1);

            GetSomeDayEpisodesParam p = new GetSomeDayEpisodesParam();
            p.setLiveTypeParam(LiveTypeParam.LIVE_OR_TLIVE);
            p.setDate(date);
            p.setGameType(gameType);

            CallerParam callerParam = new CallerParam();
            callerParam.setCallerId(callerId);
            callerParam.setCountry(CountryCode.CN);
            callerParam.setLanguage(LanguageCode.ZH_CN);

            List<HallEpisodeVo> episodes = episodeService.getSomeDayEpisodes(p,pageParam,callerParam);
            Map<String, Object> result = Maps.newHashMap();
            result.put("episodes", episodes);
            result.put("page", page);
            result.put("count", count);
            return result;
        } catch (LeWebApplicationException e) {
            LOG.error(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 按天查询节目
     *
     * @param date
     * @param episodeType 节目类型 0:match 1:program
     * @param page
     * @param count
     * @return
     */
    @LJSONP
    @GET
    @ENCRYPT
    @Path("csl/day/episodes")
    @Produces(AlternateMediaType.UTF_8_APPLICATION_JSON)
    public Map<String, Object> getCslHallDayEpisodes(@QueryParam("startDate") String date,
                                                  @QueryParam("episodeType") @DefaultValue("0") int episodeType,
                                                  @QueryParam("caller") long callerId,
                                                  @QueryParam("page") @DefaultValue("1") int page,
                                                  @QueryParam("count") @DefaultValue("20") int count) {

        try {
            Preconditions.checkArgument(StringUtils.isNotBlank(date) && date.length() == 8, "startDate不为空,切格式为yyyyMMdd");
            PageParam pageParam = new PageParam();
            pageParam.setCount(count);
            pageParam.setPage(page > 0 ? page : 1);

            GetSomeDayEpisodesParam p = new GetSomeDayEpisodesParam();
            p.setLiveTypeParam(LiveTypeParam.LIVE_OR_TLIVE);
            p.setDate(date);
            p.setCids(Lists.newArrayList(47001L));

            CallerParam callerParam = new CallerParam();
            callerParam.setCallerId(callerId);
            callerParam.setCountry(CountryCode.CN);
            callerParam.setLanguage(LanguageCode.ZH_CN);

            List<HallEpisodeVo> episodes = episodeService.getCslSomeDayEpisodes(p,pageParam,callerParam);
            Map<String, Object> result = Maps.newHashMap();
            result.put("episodes", episodes);
            result.put("page", page);
            result.put("count", count);
            return result;
        } catch (LeWebApplicationException e) {
            LOG.error(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 节目详细信息
     *
     * @param id
     * @return
     */
    @LJSONP
    @GET
    @Path("episodes/{id}")
    @ENCRYPT
    @Produces(AlternateMediaType.UTF_8_APPLICATION_JSON)
    public DetailEpisodeVo getEpisodeById(@PathParam("id") long id) {
        try {
            CallerParam callerParam = new CallerParam();
            callerParam.setCallerId(LeConstants.LESPORTS_PC_CALLER_CODE);
            callerParam.setCountry(CountryCode.CN);
            callerParam.setLanguage(LanguageCode.ZH_CN);
            DetailEpisodeVo episode = episodeService.getEpisodeById(id,callerParam);
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
     * 多条件查询接口
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
    @ENCRYPT
    @Path("/episodes")
    @Produces(AlternateMediaType.UTF_8_APPLICATION_JSON)
    public Map<String, Object> getChannelStatusMatchs(@QueryParam("gameType") @DefaultValue("0") long gameType,
                                                      @QueryParam("episodeType") @DefaultValue("-1") int episodeType,
                                                      @QueryParam("status") @DefaultValue("-1") int status,
                                                      @QueryParam("liveType") @DefaultValue("2") int liveType,
                                                      @QueryParam("page") @DefaultValue("1") int page,
                                                      @QueryParam("count") @DefaultValue("20") int count,
                                                      @QueryParam("caller") long callerId) {

        try {
            PageParam pageParam = new PageParam();
            pageParam.setCount(count);
            pageParam.setPage(page > 0 ? page : 1);
            Sort sort = new Sort();
            if(status==2){
                sort.setOrders(Lists.newArrayList(new Order("start_time", Direction.DESC)));
            }
            else{
                sort.setOrders(Lists.newArrayList(new Order("start_time", Direction.ASC)));
            }

            pageParam.setSort(sort);

            GetCurrentEpisodesParam p = new GetCurrentEpisodesParam();
            p.setLiveTypeParam(LiveTypeParam.findByValue(liveType));
            p.setLiveShowStatusParam(LiveShowStatusParam.findByValue(status));
            p.setGameType(gameType);
            p.setEpisodeTypeParam(EpisodeTypeParam.findByValue(episodeType));

            CallerParam callerParam = new CallerParam();
            callerParam.setCallerId(callerId);
            callerParam.setCountry(CountryCode.CN);
            callerParam.setLanguage(LanguageCode.ZH_CN);

            long beginTime = System.currentTimeMillis();
            List<HallEpisodeVo> episodes = episodeService.getHallEpisodes(p,pageParam,callerParam);
            long endTime  = System.currentTimeMillis();
            LOG.info("getChannelStatusMatchs time : {}" ,(endTime-beginTime));
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
