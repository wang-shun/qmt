package com.lesports.qmt.web.api.core.service.impl;

import com.google.common.base.Stopwatch;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.lesports.LeConstants;
import com.lesports.api.common.CallerParam;
import com.lesports.api.common.PageParam;
import com.lesports.api.common.Platform;
import com.lesports.jersey.support.model.PageBean;
import com.lesports.qmt.config.api.dto.TMenu;
import com.lesports.qmt.config.api.dto.TMenuItem;
import com.lesports.qmt.config.client.QmtConfigApis;
import com.lesports.qmt.resource.ComboResourceCreaters;
import com.lesports.qmt.resource.core.ComboResource;
import com.lesports.qmt.resource.cvo.BaseCvo;
import com.lesports.qmt.sbc.api.dto.TComboEpisode;
import com.lesports.qmt.sbc.api.service.*;
import com.lesports.qmt.sbc.client.QmtSbcEpisodeApis;
import com.lesports.qmt.sbd.api.dto.TCompetitionSeason;
import com.lesports.qmt.sbd.client.SbdCompetitonSeasonApis;
import com.lesports.qmt.web.api.core.cache.impl.EpisodeCache;
import com.lesports.qmt.web.api.core.cache.impl.FallbackCache;
import com.lesports.qmt.web.api.core.creater.EpisodeVoCreater;
import com.lesports.qmt.web.api.core.rconverter.EpisodeResConverter;
import com.lesports.qmt.web.api.core.service.EpisodeService;
import com.lesports.qmt.web.api.core.util.AppResourceContentIdConstants;
import com.lesports.qmt.web.api.core.util.CollectionTools;
import com.lesports.qmt.web.api.core.vo.*;
import com.lesports.utils.LeDateUtils;
import com.lesports.utils.LeProperties;
import com.lesports.utils.LeStringUtils;
import com.lesports.utils.math.LeNumberUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Created by gengchengliang on 2015/6/18.
 */
@Service("episodeService")
public class EpisodeServiceImpl extends AbstractService implements EpisodeService {

    private static final Logger LOG = LoggerFactory.getLogger(EpisodeServiceImpl.class);

    @Resource
    private EpisodeVoCreater episodeVoCreater;
	@Resource
	private EpisodeResConverter episodeResConverter;
    @Resource
    private EpisodeCache episodeCache;
    @Resource
    private FallbackCache fallbackCache;

	//香港直播中的节目排序菜单
	private static final Long HK_LIVING_EPISODES_SORT_MENUID = LeProperties.getLong("hk.living.episodes.sort.menuid", 100918018l);




    @Override
    public List<CalEpisodeVo> getDayEpisodes(GetSomeDayEpisodesParam p, PageParam page, CallerParam caller, Boolean isNeedSection) {
        List<TComboEpisode> episodes = QmtSbcEpisodeApis.getSomeDayEpisodes(p, page, caller);
        if (CollectionUtils.isEmpty(episodes)) {
            return Collections.EMPTY_LIST;
        }
        List<CalEpisodeVo> returnList = Lists.newArrayList();
        String currentHours = null;
        if (isNeedSection) {
            String currentDate = LeDateUtils.formatYYYYMMDDHHMMSS(new Date());
            currentHours = currentDate.substring(8, 10);
        }
        for (TComboEpisode comboEpisode : episodes) {
            try {
                CalEpisodeVo calEpisodeVo = episodeVoCreater.createCalEpisodeVo(comboEpisode, currentHours, caller);
                if (calEpisodeVo != null) {
                    returnList.add(calEpisodeVo);
                }
            } catch (Exception e) {
                LOG.error("getDayEpisodes error, episodeId: " + comboEpisode.getId(), e);
                continue;
            }
        }
        Collections.sort(returnList, new Comparator<CalEpisodeVo>() {
            @Override
            public int compare(CalEpisodeVo o1, CalEpisodeVo o2) {
                if (o1.getTimeSection() != o2.getTimeSection()) {
                    return o1.getTimeSection() - o2.getTimeSection();
                } else {
                    return o1.getStartTime().compareTo(o2.getStartTime());
                }
            }
        });
        return returnList;
    }

	@Override
	public List<HallEpisodeVo> getResourceContentEpisodes(long resourceId, PageParam page, CallerParam caller) {
		List<HallEpisodeVo> returnList = Lists.newArrayList();
		ComboResource resourceVo = ComboResourceCreaters.getComboResource(resourceId, page, caller);
		if (resourceVo == null) {
			return Collections.EMPTY_LIST;
		}
		List<BaseCvo> contents = resourceVo.getContents();
		if (CollectionUtils.isNotEmpty(contents)) {
			for (BaseCvo content : contents) {
				HallEpisodeVo hallEpisodeVo = episodeResConverter.fillVo(content, caller);
				if (hallEpisodeVo != null) {
					returnList.add(hallEpisodeVo);
				}
			}
		}
		return returnList;
	}

	@Override
    public List<CalEpisodeVo> getDayEpisodesWithFallback(GetSomeDayEpisodesParam p, PageParam page, CallerParam caller, Boolean isNeedSection) {
        List<CalEpisodeVo> calEpisodeVos = null;
        try {
            calEpisodeVos = getDayEpisodes(p, page, caller, isNeedSection);
        } catch (Exception e) {
            LOG.error("get day episode with fallback error. date : {},gameType : {},callerId : {}," +
                    "isNeedSection:{}", p.getDate(), p.getGameType(), caller.getCallerId(), isNeedSection, e);
            if (CollectionUtils.isEmpty(calEpisodeVos)) {
                LOG.info("pc live episode list begin to enter fallback. date : {},gameType : {},callerId : {}," +
                                "isNeedSection:{}", p.getDate(), p.getGameType(), caller.getCallerId(), isNeedSection
                );
                Map<String, String> paramMap = Maps.newHashMap();
                paramMap.put("startDate", p.getDate());
                paramMap.put("caller", String.valueOf(caller.getCallerId()));
                calEpisodeVos = fallbackCache.findByKey(Platform.PC.name(), LeConstants.METHOD_PC_LIVE_EPISODE, paramMap, List.class);
            }
        }

        return calEpisodeVos;
    }

    /**
     * 获取N天的比赛节目信息
     *
     * @return
     */
    @Override
    public List<CalEpisodeVo> getSomedayEpisodes(String startDate,int days,GetSomeDayEpisodesParam p, PageParam page, CallerParam caller) {
        List returnList = Lists.newArrayList();
        for (int i = 0; i < days; i++) {
            String date =  LeDateUtils.formatYYYYMMDD(LeDateUtils.addDay(startDate,i));
            p.setDate(date);
            List<CalEpisodeVo> dayEpisodes = getDayEpisodes(p, page, caller, false);
            if (CollectionUtils.isNotEmpty(dayEpisodes)) {
                returnList.add(dayEpisodes);
            } else {
                returnList.add(Lists.newArrayList());
            }
        }
        return returnList;
    }


    @Override
    public List<ZhangyuEpisodeVo> getZhangyuEpisodesByMids(GetZhangyuEpisodesParam p, PageParam page, CallerParam caller) {
        List<TComboEpisode> episodes = QmtSbcEpisodeApis.getEpisodesOfOctopus(p, page, caller);
        if (CollectionUtils.isEmpty(episodes)) {
            return Collections.EMPTY_LIST;
        }
        List<ZhangyuEpisodeVo> results = Lists.newArrayList();
        for (TComboEpisode comboEpisode : episodes) {
            ZhangyuEpisodeVo hallEpisodeVo = episodeVoCreater.createZhangYuEpisode(comboEpisode);
            results.add(hallEpisodeVo);
        }
        return results;
    }

    private LoadingCache<GetRealtimeCacheParam, DetailEpisodeVo> detailEpisodeVoCacheLoadingCache = CacheBuilder.newBuilder()
            .maximumSize(500)
            .expireAfterWrite(5, TimeUnit.SECONDS)
            .build(new CacheLoader<GetRealtimeCacheParam, DetailEpisodeVo>() {
                @Override
                public DetailEpisodeVo load(GetRealtimeCacheParam key) throws Exception {
                    DetailEpisodeVo detailEpisodeVoCache = episodeCache.getDetailEpisodeVo(key);
                    if (null == detailEpisodeVoCache) {
                        TComboEpisode episode = QmtSbcEpisodeApis.getTComboEpisodeById(key.getEpisodeId(), key.getCaller());
                        if (null == episode) {
                            return null;
                        }
                        detailEpisodeVoCache = episodeVoCreater.createDetailEpisodeVo(episode, key.getCaller());
                        episodeVoCreater.buildXinyingPlayUrl(detailEpisodeVoCache, episode.getXinyingMatchId(), key.getCaller().getCallerId());
                        if (null != detailEpisodeVoCache) {
                            episodeCache.saveDetailEpisodeVo(key, detailEpisodeVoCache);
                        }
                    }
                    LOG.info("save detail episode cache : id :{}, caller : {} in memory cache.", key.getEpisodeId(), key.getCaller());
                    return detailEpisodeVoCache;
                }
            });

    @Override
    public DetailEpisodeVo getEpisodeByIdRealtime(long id, CallerParam caller) {
        return getDetailEpisodeVoRealtime(id, caller, detailEpisodeVoCacheLoadingCache);
    }

    private LoadingCache<GetRealtimeCacheParam, DetailEpisodeVo> detailEpisodeVo4AppCacheLoadingCache = CacheBuilder.newBuilder()
            .maximumSize(500)
            .expireAfterWrite(5, TimeUnit.SECONDS)
            .build(new CacheLoader<GetRealtimeCacheParam, DetailEpisodeVo>() {
                @Override
                public DetailEpisodeVo load(GetRealtimeCacheParam key) throws Exception {
                    DetailEpisodeVo detailEpisodeVoCache = episodeCache.getDetailEpisode4AppVo(key);
                    if (null == detailEpisodeVoCache) {
                        TComboEpisode episode = QmtSbcEpisodeApis.getTComboEpisodeById(key.getEpisodeId(), key.getCaller());
                        if (null == episode) {
                            return null;
                        }
                        detailEpisodeVoCache = episodeVoCreater.createDetailEpisodeVo4App(episode, key.getCaller());
                        episodeVoCreater.buildXinyingPlayUrl(detailEpisodeVoCache, episode.getXinyingMatchId(), key.getCaller().getCallerId());
                        if (null != detailEpisodeVoCache) {
                            episodeCache.saveDetailEpisodeVo4AppVo(key, detailEpisodeVoCache);
                        }
                    }
                    LOG.info("save detail episode for app cache : id :{}, caller : {} in memory cache.", key.getEpisodeId(), key.getCaller());
                    return detailEpisodeVoCache;
                }
            });

    @Override
    public DetailEpisodeVo getEpisodeById4AppRealtime(long id, CallerParam caller) {
        return getDetailEpisodeVoRealtime(id, caller, detailEpisodeVo4AppCacheLoadingCache);
    }

    private DetailEpisodeVo getDetailEpisodeVoRealtime(long id, CallerParam caller, LoadingCache<GetRealtimeCacheParam, DetailEpisodeVo> loadingCache) {
        GetRealtimeCacheParam getDetailEpisodeVoParam = new GetRealtimeCacheParam(id, caller);
        DetailEpisodeVo detailEpisodeVoCache = null;
        try {
            detailEpisodeVoCache = loadingCache.get(getDetailEpisodeVoParam);
        } catch (CacheLoader.InvalidCacheLoadException e) {
            LOG.warn("get null detail episode cache for : {}, {}", id, caller);
        } catch (Exception e) {
            LOG.error("{}", e.getMessage(), e);
        }
        if (null == detailEpisodeVoCache) {
            return null;
        }

        return detailEpisodeVoCache;
    }

	/**
	 * 构建返回的HallEpisodeVo集合
	 *
	 * @param episodes
	 * @param caller
	 * @return
	 */
	public List<HallEpisodeVo> createHkLivingHallEpisodes(List<TComboEpisode> episodes, CallerParam caller) {
		if (CollectionUtils.isEmpty(episodes)) {
			return Collections.EMPTY_LIST;
		}
		List<HallEpisodeVo> results = Lists.newArrayList();
		for (TComboEpisode comboEpisode : episodes) {
			try {
				HallEpisodeVo hallEpisodeVo = episodeVoCreater.createHallEpisodeVo(comboEpisode, caller);
				if (hallEpisodeVo != null) {
					//直播场次ID
					String liveUniqueId = hallEpisodeVo.getLiveUniqueId();
					//如果是付费节目则添加会员信息
					if (hallEpisodeVo.getIsEpisodePay() == 1 && StringUtils.isNotBlank(liveUniqueId)) {
						episodeVoCreater.addVipInfos(hallEpisodeVo, liveUniqueId);
					}
					results.add(hallEpisodeVo);
				}
			} catch (Exception e) {
				LOG.error("createHallEpisodes error, episodeId: " + comboEpisode.getId(), e);
				continue;
			}
		}
		return results;
	}


    /**
     * 构建返回的HallEpisodeVo集合
     *
     * @param episodes
     * @param caller
     * @return
     */
	@Override
    public List<HallEpisodeVo> createHallEpisodes(List<TComboEpisode> episodes, CallerParam caller) {
        if (CollectionUtils.isEmpty(episodes)) {
            return Collections.EMPTY_LIST;
        }
        List<HallEpisodeVo> results = Lists.newArrayList();
        for (TComboEpisode comboEpisode : episodes) {
            try {
                HallEpisodeVo hallEpisodeVo = episodeVoCreater.createHallEpisodeVo(comboEpisode, caller);
                if (hallEpisodeVo != null) {
                    results.add(hallEpisodeVo);
                }
            } catch (Exception e) {
                LOG.error("createHallEpisodes error, episodeId: " + comboEpisode.getId(), e);
                continue;
            }
        }
        return results;
    }

    @Override
    public List<HallEpisodeVo> getZhangyuEpisodesByCids(GetCurrentEpisodesParam p, PageParam page, CallerParam caller) {
        List<TComboEpisode> episodes = QmtSbcEpisodeApis.getCurrentEpisodes(p, page, caller);
        return createHallEpisodes(episodes, caller);
    }

    @Override
    public List<HallEpisodeVo4Tv> getEpisodesByCidAndRound4TV(GetEpisodesOfSeasonByMetaEntryIdParam p, PageParam page, CallerParam caller) {

        List<TComboEpisode> episodes = QmtSbcEpisodeApis.getEpisodesOfSeasonByMetaEntryId(p, page, caller);
        if (CollectionUtils.isEmpty(episodes)) {
            return Collections.EMPTY_LIST;
        }
        List<HallEpisodeVo4Tv> results = Lists.newArrayList();
        for (TComboEpisode comboEpisode : episodes) {
            HallEpisodeVo4Tv hallEpisodeVo = episodeVoCreater.createHallEpisodeVo4Tv(comboEpisode, caller);
            results.add(hallEpisodeVo);
        }
        return results;
    }

    @Override
    public Map<String, DetailEpisodeVo> getNearbyEpisodes(GetEpisodesNearbySomeEpisodeParam p, CallerParam callerParam) {
        Map<String, TComboEpisode> nearbyEpisodeMap = QmtSbcEpisodeApis.getTComboEpisodesNearbySomeEpisode(p, callerParam);
        if (MapUtils.isEmpty(nearbyEpisodeMap)) {
            return Maps.newHashMap();
        }
        Map<String, DetailEpisodeVo> results = Maps.newHashMap();
        for (Map.Entry<String, TComboEpisode> entry : nearbyEpisodeMap.entrySet()) {
            results.put(entry.getKey(), episodeVoCreater.createDetailEpisodeVo(entry.getValue(), callerParam));
        }
        return results;
    }

    @Override
    public List<SimpleEpisodeVo> getTopicEpisodesByCidAndMetaEntryId(GetEpisodesOfSeasonByMetaEntryIdParam p, PageParam page, CallerParam caller) {
        List<TComboEpisode> episodes = QmtSbcEpisodeApis.getEpisodesOfSeasonByMetaEntryId(p, page, caller);
        if (CollectionUtils.isEmpty(episodes)) {
            return Collections.EMPTY_LIST;
        }
        List<SimpleEpisodeVo> results = Lists.newArrayList();
        for (TComboEpisode comboEpisode : episodes) {
            SimpleEpisodeVo hallEpisodeVo = episodeVoCreater.createSimpleEpisodeVo(comboEpisode, caller);
            results.add(hallEpisodeVo);
        }
        return results;

    }


    /**
     * 日历 月的count接口
     *
     * @param p
     * @param callerParam
     * @return
     */
    @Override
    public Map<String, Object> countEpisodesByDay(CountEpisodesByDayParam p, CallerParam callerParam) {
        Map<String, Object> result = Maps.newHashMap();
        List<Long> returnList = QmtSbcEpisodeApis.countEpisodesByDay(p, callerParam);
        result.put("counts", returnList);
        return result;
    }

    /**
     * 比赛大厅,按照天查询 - 不分页处理
     * <p/>
     * date 起始时间,精确到天(yyyyMMdd:20150528)
     * gameType 比赛分类
     * episode_type 节目类型 0:match 1:program 2:all
     * contain_type 节目包含的属性 0:只要直播 1:直播和重点赛程 2:所有节目
     * page 页码
     * count 每页条数
     */
    @Override
    public List<HallEpisodeVo> getSomeDayEpisodes(GetSomeDayEpisodesParam p, PageParam page, CallerParam caller) {
        List<TComboEpisode> episodes = QmtSbcEpisodeApis.getSomeDayEpisodes(p, page, caller);
        return createHallEpisodes(episodes, caller);
    }

    @Override
    public List<HallEpisodeVo> getSomeDayEpisodesWithFallback(GetSomeDayEpisodesParam p, PageParam page, CallerParam caller) {
        List<HallEpisodeVo> hallEpisodeVos = null;
        try {
            hallEpisodeVos = getSomeDayEpisodes(p, page, caller);
        } catch (Exception e) {
            LOG.error("get someday episode with fallback error.date: {},gameType : {},callerId :{}," +
                    "liveRequest :{},pageRequest :{}", p.getDate(), p.getGameType(), caller.getCallerId(), p.getLiveTypeParam(), page, e);
            if (CollectionUtils.isEmpty(hallEpisodeVos)) {
                LOG.info("pc episode list begin to enter fallback.date: {},gameType : {},callerId :{}," +
                        "liveRequest :{},pageRequest :{}", p.getDate(), p.getGameType(), caller.getCallerId(), p.getLiveTypeParam(), page);
                Map<String, String> paramMap = Maps.newHashMap();
                paramMap.put("startDate", p.getDate());
                paramMap.put("liveType", String.valueOf(p.getLiveTypeParam()));
                paramMap.put("caller", String.valueOf(caller.getCallerId()));
                hallEpisodeVos = fallbackCache.findByKey(Platform.PC.name(), LeConstants.METHOD_PC_DAY_EPISODE, paramMap, List.class);
            }
        }

        return hallEpisodeVos;
    }


    @Override
    public DetailEpisodeVo getEpisodeById(long id, CallerParam callerParam) {
        TComboEpisode episode = QmtSbcEpisodeApis.getTComboEpisodeById(id, callerParam);
        return episodeVoCreater.createDetailEpisodeVo(episode, callerParam);
    }


    @Override
    public HallEpisodeVo getEpisodeByLiveId(String liveId, CallerParam callerParam) {
        TComboEpisode episode = QmtSbcEpisodeApis.getEpisodeByLiveId(liveId, callerParam);
        if (null == episode) {
            return new HallEpisodeVo();
        }
        return episodeVoCreater.createHallEpisodeVo(episode, callerParam);
    }

    @Override
    public DetailEpisodeVo getDetailEpisodeByLiveId(String liveId, CallerParam callerParam) {

        TComboEpisode episode = QmtSbcEpisodeApis.getEpisodeByLiveId(liveId, callerParam);
        return episodeVoCreater.createDetailEpisodeVo(episode, callerParam);
    }

    @Override
    public DetailEpisodeVo getEpisodeById4App(long id, CallerParam caller) {
        Stopwatch stopwatch = Stopwatch.createStarted();
        TComboEpisode episode = QmtSbcEpisodeApis.getTComboEpisodeById(id, caller);
        DetailEpisodeVo detailEpisodeVo = episodeVoCreater.createDetailEpisodeVo4App(episode, caller);
        episodeVoCreater.buildXinyingPlayUrl(detailEpisodeVo, episode.getXinyingMatchId(), caller.getCallerId());
        stopwatch.stop();
        LOG.info("get episodeById for tv eid: {},elapsed: {}", id, stopwatch.elapsed(TimeUnit.MILLISECONDS));
        return detailEpisodeVo;
    }

    @Override
    public List<LatestEpisodeVo> getEpisodeByAid(long aid, PageParam page, CallerParam caller) {
        List<LatestEpisodeVo> returnList = Lists.newArrayList();
        List<TComboEpisode> episodes = QmtSbcEpisodeApis.getTComboEpisodesInAlbum(aid, page, caller);
        if (CollectionUtils.isNotEmpty(episodes)) {
            for (TComboEpisode comboEpisode : episodes) {
                LatestEpisodeVo latestEpisodeVo = episodeVoCreater.createLatestEpisodeVo(comboEpisode);
                returnList.add(latestEpisodeVo);
            }
            return returnList;
        } else {
            return Collections.EMPTY_LIST;
        }
    }


    @Override
    public List<HallEpisodeVo> getHallEpisodes4App(GetCurrentEpisodesParam p, PageParam pageParam, CallerParam callerParam) {
        List<HallEpisodeVo> returnList = Lists.newArrayList();
        List<TComboEpisode> episodes = QmtSbcEpisodeApis.getCurrentEpisodes(p, pageParam, callerParam);
        if (CollectionUtils.isEmpty(episodes)) {
            return Collections.EMPTY_LIST;
        }
        for (TComboEpisode comboEpisode : episodes) {
            try {
                HallEpisodeVo hallEpisodeVo = episodeVoCreater.createHallEpisodeVo4App(comboEpisode, callerParam);
                if (hallEpisodeVo != null) {
                    returnList.add(hallEpisodeVo);
                }
            } catch (Exception e) {
                LOG.error("getHallEpisodes4App error, episodeId : {}", comboEpisode.getId(), e);
                continue;
            }
        }
        return returnList;
    }

    @Override
    public Map<String,List<HallEpisodeVo4Tv>> getCurrentEpisodes4Tv(GetCurrentEpisodesParam p, PageParam pageParam, CallerParam callerParam) {
        Map<String,List<HallEpisodeVo4Tv>> map = Maps.newHashMap();
        List<TComboEpisode> episodes = QmtSbcEpisodeApis.getCurrentEpisodes(p, pageParam, callerParam);
        if (CollectionUtils.isEmpty(episodes)) {
            return Collections.EMPTY_MAP;
        }
        for (TComboEpisode comboEpisode : episodes) {
            try {
                HallEpisodeVo4Tv hallEpisodeVo = episodeVoCreater.createHallEpisodeVo4Tv(comboEpisode, callerParam);

                if (hallEpisodeVo != null) {
                    String startDate = StringUtils.substring(hallEpisodeVo.getStartTime(),0,8);
                    if (CollectionUtils.isNotEmpty(map.get(startDate))) {
                        map.get(startDate).add(hallEpisodeVo);
                    } else {
                        List<HallEpisodeVo4Tv> hallEpisodeVo4Tvs = Lists.newArrayList();
                        hallEpisodeVo4Tvs.add(hallEpisodeVo);
                        map.put(startDate, hallEpisodeVo4Tvs);
                    }
                }
            } catch (Exception e) {
                LOG.error("getCurrentEpisodes4Tv error, episodeId : {}", comboEpisode.getId(), e);
                continue;
            }
        }
        return map;
    }

	@Override
	public List<HallEpisodeVo> getTicketEpisodes(GetCurrentEpisodesParam p, PageParam pageParam, CallerParam callerParam) {
		List<TComboEpisode> episodes = QmtSbcEpisodeApis.getTicketEpisodes(p, pageParam, callerParam);
		return createHallEpisodes(episodes, callerParam);
	}

	@Override
	public List<HallEpisodeVo> getLivingEpisodes(GetCurrentEpisodesParam p, PageParam pageParam, CallerParam callerParam) {
		List<TComboEpisode> episodes = QmtSbcEpisodeApis.getCurrentEpisodes(p, pageParam, callerParam);
		if (CollectionUtils.isEmpty(episodes)) {
			return Collections.EMPTY_LIST;
		}
		TMenu tMenu = QmtConfigApis.getTMenuById(HK_LIVING_EPISODES_SORT_MENUID);
		if (tMenu != null && CollectionUtils.isNotEmpty(tMenu.getItems())) {
			List<TComboEpisode> list = sortLivingEpisodes(episodes, tMenu);
			episodes = list;
		}
		return createHkLivingHallEpisodes(episodes, callerParam);
	}

	@Override
	public List<HallEpisodeVo> getHomePageEpisodes(String cids, CallerParam callerParam) {
		List<Long> cidList = Lists.newArrayList();
		if (StringUtils.isNotBlank(cids)) {
			cidList = LeStringUtils.commaString2LongList(cids);
		}
		List<HallEpisodeVo> episodes = Lists.newArrayList();
		List<HallEpisodeVo> endEpisodes = getTimelineEndEpisodes(cidList, LiveTypeParam.ONLY_LIVE, callerParam, PageBean.getPageParam(1, 10));
		if (CollectionUtils.isNotEmpty(endEpisodes)) {
			episodes.addAll(endEpisodes);
		}
		List<HallEpisodeVo> notEndEpisodes = getTimelineNotEndEpisodes(LiveShowStatusParam.LIVE_NOT_START, cidList, LiveTypeParam.ONLY_LIVE, callerParam, PageBean.getPageParam(1, 10));
		if (CollectionUtils.isNotEmpty(notEndEpisodes)) {
			episodes.addAll(notEndEpisodes);
		}List<HallEpisodeVo> livingEpisodes = getTimelineNotEndEpisodes(LiveShowStatusParam.LIVING, cidList, LiveTypeParam.ONLY_LIVE, callerParam, PageBean.getPageParam(1, 10));
		if (CollectionUtils.isNotEmpty(livingEpisodes)) {
			episodes.addAll(livingEpisodes);
		}
		if (CollectionUtils.isEmpty(episodes)) {
			return Collections.EMPTY_LIST;
		}
		Collections.sort(episodes, new Comparator<HallEpisodeVo>() {
			@Override
			public int compare(HallEpisodeVo o1, HallEpisodeVo o2) {
				return o1.getStartTime().compareTo(o2.getStartTime());
			}
		});
		return episodes;
	}

	private List<TComboEpisode> sortLivingEpisodes(List<TComboEpisode> episodes, TMenu tMenu) {
		List<TComboEpisode> returnList = Lists.newArrayList();
		Map<Long, List<TComboEpisode>> episodesMap = Maps.newHashMap();
		List<TComboEpisode> noCidList = Lists.newArrayList();
		//把直播中的节目分类放入map中
		for (TComboEpisode episode : episodes) {
			if (LeNumberUtils.toInt(episode.getCid()) <= 0) {
				noCidList.add(episode);
				continue;
			}
			List<TComboEpisode> episodeList = episodesMap.get(episode.getCid());
			if (CollectionUtils.isEmpty(episodeList)) {
				List<TComboEpisode> elist = Lists.newArrayList();
				elist.add(episode);
				episodesMap.put(episode.getCid(), elist);
			} else {
				episodeList.add(episode);
			}
		}
		//获取菜单中的顺序
		for (TMenuItem item : tMenu.getItems()) {
			List<TComboEpisode> list = episodesMap.get(item.getMatchCid());//menu 中有2个填赛事ID的地方,约定如果只需要赛事ID的时候取 MatchCid
			if (CollectionUtils.isNotEmpty(list)) {
				returnList.addAll(list);
				episodesMap.remove(item.getMatchCid());
			}
		}
		if (MapUtils.isEmpty(episodesMap)) {
			returnList.addAll(noCidList);
		} else {
			for (Long key : episodesMap.keySet()) {
				List<TComboEpisode> list = episodesMap.get(key);
				returnList.addAll(list);
			}
		}
		return returnList;
	}

	@Override
    public List<HallEpisodeVo> getHallEpisodes4AppWithFallback(GetCurrentEpisodesParam p, PageParam page, CallerParam caller) {
        List<HallEpisodeVo> episodeVos = null;
        try {
            episodeVos = getHallEpisodes4App(p, page, caller);
            if (CollectionUtils.isEmpty(episodeVos) && page.getPage() < 2 && CollectionUtils.isEmpty(p.getCids())) {
                //分页数小于2的情况下，列表还是空说明有问题吧
                LOG.error("some error. try to fallback!!!. p : {}, page : {}, caller : {}", p, page, caller);
                throw new IllegalStateException("some error. try to fallback!!!");
            }

        } catch (Exception e) {
            LOG.error("getHallEpisodes4AppWithFallback error, try fallback.  p : {}, page : {}, caller : {}", p, page, caller, e
            );
            if (CollectionUtils.isEmpty(episodeVos) && CollectionUtils.isNotEmpty(p.getCids())) {
                LOG.info("app episode list begin to enter fallback. p : {}, page : {}, caller : {}", p, page, caller);

                Map<String, String> paramMap = Maps.newHashMap();
                paramMap.put("page", String.valueOf(page.getPage()));
                paramMap.put("count", String.valueOf(page.getCount()));
                paramMap.put("liveType", String.valueOf(p.getLiveTypeParam()));
                paramMap.put("status", String.valueOf(p.getLiveShowStatusParam()));
                episodeVos = fallbackCache.findByKey(Platform.MOBILE.name(), LeConstants.METHOD_APP_EPISODE_LIST, paramMap, List.class);
            }
        }

        return episodeVos;
    }

	@Override //todo 香港首页
	public List<HallEpisodeVo> getTimelineEpisodesByCids(GetCurrentEpisodesParam p, PageParam page, CallerParam caller) {
//		List<TComboEpisode> episodes = QmtSbcEpisodeApis.getTimelineEpisodesByCids(p, page, caller);
		return null;//createHallEpisodes(episodes, caller);
	}


	@Override
    public List<HallEpisodeVo4Tv> getHallEpisodes4Tv(GetSomeDayEpisodesParam p, PageParam page, CallerParam caller) {
        List<HallEpisodeVo4Tv> returnList = Lists.newArrayList();
        List<TComboEpisode> episodes = QmtSbcEpisodeApis.getSomeDayEpisodes(p, page, caller);
        if (CollectionUtils.isNotEmpty(episodes)) {
            for (TComboEpisode comboEpisode : episodes) {
                HallEpisodeVo4Tv hallEpisodeVo = episodeVoCreater.createHallEpisodeVo4Tv(comboEpisode, caller);
                if (null != hallEpisodeVo) {
                    returnList.add(hallEpisodeVo);
                }
            }
            return returnList;
        } else {
            return Collections.EMPTY_LIST;
        }
    }

    @Override
    public List<HallEpisodeVo4Tv> getHallEpisodes4TvWithTimezone(GetSomeDayEpisodesParam p, PageParam page, CallerParam caller) {
        List<HallEpisodeVo4Tv> returnList = Lists.newArrayList();
        List<TComboEpisode> episodes = QmtSbcEpisodeApis.getSomeDayEpisodeIdsWithTimezone(p, page, caller);
        if (CollectionUtils.isNotEmpty(episodes)) {
            for (TComboEpisode comboEpisode : episodes) {
                HallEpisodeVo4Tv hallEpisodeVo = episodeVoCreater.createHallEpisodeVo4Tv(comboEpisode, caller);
                if (null != hallEpisodeVo) {
                    returnList.add(hallEpisodeVo);
                }
            }
            return returnList;
        } else {
            return Collections.EMPTY_LIST;
        }
    }


    @Override
    public List<HallEpisodeVo> getEpisodesByCompetitorId(long competitorId, GetEpisodesOfSeasonByMetaEntryIdParam p, PageParam page, CallerParam caller) {
        List<TComboEpisode> episodes = QmtSbcEpisodeApis.getCurrentEpisodesByCompetitorId(competitorId, p, page, caller);
        return createHallEpisodes(episodes, caller);
    }


    @Override
    public List<HallEpisodeVo4Tv> getEpisodesByCids4TV(GetSomeDayEpisodesParam p, PageParam pageParam, CallerParam callerParam) {
        List<TComboEpisode> episodes = QmtSbcEpisodeApis.getSomeDayEpisodes(p, pageParam, callerParam);
        if (CollectionUtils.isEmpty(episodes)) {
            return Collections.EMPTY_LIST;
        }
        List<HallEpisodeVo4Tv> results = Lists.newArrayList();
        for (TComboEpisode comboEpisode : episodes) {
            HallEpisodeVo4Tv hallEpisodeVo = episodeVoCreater.createHallEpisodeVo4Tv(comboEpisode, callerParam);
            results.add(hallEpisodeVo);
        }
        return results;
    }


    @Override
    public List<HallEpisodeVo> getEpisodesByCids4Ipad(GetSomeDayEpisodesParam p, PageParam pageParam, CallerParam callerParam) {
        List<TComboEpisode> episodes = QmtSbcEpisodeApis.getSomeDayEpisodes(p, pageParam, callerParam);
        if (CollectionUtils.isEmpty(episodes)) {
            return Collections.EMPTY_LIST;
        }
        List<HallEpisodeVo> results = Lists.newArrayList();
        for (TComboEpisode comboEpisode : episodes) {
            HallEpisodeVo hallEpisodeVo = episodeVoCreater.createHallEpisodeVo4App(comboEpisode, callerParam);
            results.add(hallEpisodeVo);
        }
        return results;
    }


    @Override
    public List<HallEpisodeVo> getMatchEpisodesByCidAndMetaEntryId(GetEpisodesOfSeasonByMetaEntryIdParam p, PageParam page, CallerParam caller) {
        List<HallEpisodeVo> returnList = Lists.newArrayList();
        List<TComboEpisode> episodes = QmtSbcEpisodeApis.getEpisodesOfSeasonByMetaEntryId(p, page, caller);
        return createHallEpisodes(episodes, caller);
    }

    private LoadingCache<GetRealtimeCacheParam, PollingEpisodeVo> pollingEpisodeVoLoadingCache = CacheBuilder.newBuilder()
            .maximumSize(1000)
            .expireAfterWrite(5, TimeUnit.SECONDS)
            .build(new CacheLoader<GetRealtimeCacheParam, PollingEpisodeVo>() {
                @Override
                public PollingEpisodeVo load(GetRealtimeCacheParam key) throws Exception {
                    PollingEpisodeVo pollingEpisodeVo = episodeCache.getPollingEpisodeVo(key);
                    if (null == pollingEpisodeVo) {
                        TComboEpisode episode = QmtSbcEpisodeApis.getTComboEpisodeById(key.getEpisodeId(), key.getCaller());
                        pollingEpisodeVo = episodeVoCreater.createPollingEpisodeVo(episode);
                        if (null != pollingEpisodeVo) {
                            episodeCache.savePollingEpisodeVo(key, pollingEpisodeVo);
                        }
                    }
                    LOG.info("save polling episode : {} in memory cache.", key);

                    return pollingEpisodeVo;
                }
            });

    private LoadingCache<GetRealtimeCacheParam, PollingEpisodeVo> pollingEpisodeVo4AppLoadingCache = CacheBuilder.newBuilder()
            .maximumSize(1000)
            .expireAfterWrite(5, TimeUnit.SECONDS)
            .build(new CacheLoader<GetRealtimeCacheParam, PollingEpisodeVo>() {
                @Override
                public PollingEpisodeVo load(GetRealtimeCacheParam key) throws Exception {
                    PollingEpisodeVo pollingEpisodeVo = episodeCache.getPollingEpisode4AppVo(key);
                    if (null == pollingEpisodeVo) {
                        TComboEpisode episode = QmtSbcEpisodeApis.getTComboEpisodeById(key.getEpisodeId(), key.getCaller());
                        pollingEpisodeVo = episodeVoCreater.createPollingEpisodeVo4App(episode);
                        if (null != pollingEpisodeVo) {
                            episodeCache.savePollingEpisode4AppVo(key, pollingEpisodeVo);
                        }
                    }
                    LOG.info("save polling episode for app : {} in memory cache.", key);

                    return pollingEpisodeVo;
                }
            });

    private LoadingCache<GetRealtimeCacheParam, HallEpisodeVo> hallEpisodeVoLoadingCache = CacheBuilder.newBuilder()
            .maximumSize(1000)
            .expireAfterWrite(5, TimeUnit.SECONDS)
            .build(new CacheLoader<GetRealtimeCacheParam, HallEpisodeVo>() {
                @Override
                public HallEpisodeVo load(GetRealtimeCacheParam key) throws Exception {
                    HallEpisodeVo hallEpisodeVo = episodeCache.getHallEpisodeVo(key);
                    if (null == hallEpisodeVo) {
                        TComboEpisode episode = QmtSbcEpisodeApis.getTComboEpisodeById(key.getEpisodeId(), key.getCaller());
                        hallEpisodeVo = episodeVoCreater.createHallEpisodeVo(episode, key.getCaller());
                        if (null != hallEpisodeVo) {
                            episodeCache.saveHallEpisodeVo(key, hallEpisodeVo);
                        }
                    }
                    LOG.info("save hall episode : {} in memory cache.", key);

                    return LeNumberUtils.toInt(hallEpisodeVo.getDeleted()) == 1 ? null : hallEpisodeVo;
                }
            });

    private LoadingCache<GetRealtimeCacheParam, HallEpisodeVo> hallEpisodeVo4AppLoadingCache = CacheBuilder.newBuilder()
            .maximumSize(1000)
            .expireAfterWrite(5, TimeUnit.SECONDS)
            .build(new CacheLoader<GetRealtimeCacheParam, HallEpisodeVo>() {
                @Override
                public HallEpisodeVo load(GetRealtimeCacheParam key) throws Exception {
                    HallEpisodeVo hallEpisodeVo = episodeCache.getHallEpisode4AppVo(key);
                    if (null == hallEpisodeVo) {
                        TComboEpisode episode = QmtSbcEpisodeApis.getTComboEpisodeById(key.getEpisodeId(), key.getCaller());
                        hallEpisodeVo = episodeVoCreater.createHallEpisodeVo4App(episode, key.getCaller());
                        if (null != hallEpisodeVo) {
                            episodeCache.saveHallEpisode4AppVo(key, hallEpisodeVo);
                        }
                    }
                    LOG.info("save hall episode for app : {} in memory cache.", key);

                    return LeNumberUtils.toInt(hallEpisodeVo.getDeleted()) == 1 ? null : hallEpisodeVo;
                }
            });


    @Override
    public List<PollingEpisodeVo> refreshEpisodesByIdsRealtime(String ids, CallerParam caller) {
        return get(pollingEpisodeVoLoadingCache, ids, caller);
    }

    @Override
    public List<PollingEpisodeVo> refreshEpisodesByIds4AppRealtime(String ids, CallerParam caller) {
        return get(pollingEpisodeVo4AppLoadingCache, ids, caller);
    }

    @Override
    public List<HallEpisodeVo> getMultiEpisodesByIdsRealtime(String ids, CallerParam caller) {
        return get(hallEpisodeVoLoadingCache, ids, caller);
    }

    @Override
    public List<HallEpisodeVo> getMultiEpisodesByIds4AppRealtime(String ids, CallerParam caller) {
        return get(hallEpisodeVo4AppLoadingCache, ids, caller);
    }

    @Override
    public List<PollingEpisodeVo> refreshEpisodesByIds(String ids, CallerParam caller) {
        List<PollingEpisodeVo> returnList = Lists.newArrayList();
        List<TComboEpisode> episodes = QmtSbcEpisodeApis.getTComboEpisodesByIds(CollectionTools.string2List(ids), caller);
        if (CollectionUtils.isNotEmpty(episodes)) {
            for (TComboEpisode comboEpisode : episodes) {
                PollingEpisodeVo pollingEpisodeVo = episodeVoCreater.createPollingEpisodeVo(comboEpisode);
                returnList.add(pollingEpisodeVo);
            }
            return returnList;
        } else {
            return Collections.EMPTY_LIST;
        }
    }

    @Override
    public List<PollingEpisodeVo> refreshEpisodesByIds4App(String ids, CallerParam caller) {
        List<PollingEpisodeVo> returnList = Lists.newArrayList();
        List<TComboEpisode> episodes = QmtSbcEpisodeApis.getTComboEpisodesByIds(CollectionTools.string2List(ids), caller);
        if (CollectionUtils.isNotEmpty(episodes)) {
            for (TComboEpisode comboEpisode : episodes) {
                PollingEpisodeVo pollingEpisodeVo = episodeVoCreater.createPollingEpisodeVo4App(comboEpisode);
                returnList.add(pollingEpisodeVo);
            }
            return returnList;
        } else {
            return Collections.EMPTY_LIST;
        }
    }

    @Override
    public List<HallEpisodeVo> getMultiEpisodesByIds(String ids, CallerParam caller) {
        List<TComboEpisode> episodes = QmtSbcEpisodeApis.getTComboEpisodesByIds(CollectionTools.string2List(ids), caller);
        return createHallEpisodes(episodes, caller);
    }

    @Override
    public List<HallEpisodeVo> getMultiEpisodesByIds4App(String ids, CallerParam caller) {
        try {
            List<HallEpisodeVo> returnList = Lists.newArrayList();
            List<TComboEpisode> episodes = QmtSbcEpisodeApis.getTComboEpisodesByIds(CollectionTools.string2List(ids), caller);
            if (CollectionUtils.isNotEmpty(episodes)) {
                for (TComboEpisode comboEpisode : episodes) {
                    HallEpisodeVo hallEpisodeVo = episodeVoCreater.createHallEpisodeVo4App(comboEpisode, caller);
					if (LeNumberUtils.toInt(hallEpisodeVo.getDeleted()) != 1) {
						returnList.add(hallEpisodeVo);
					}
                }
                return returnList;
            } else {
                return Collections.EMPTY_LIST;
            }
        } catch (Exception e) {
            LOG.error("getMultiEpisodesByIds fail, e={}", e);
        }
        return Collections.EMPTY_LIST;
    }

    @Override
    public List<LatestEpisodeVo> getPreviousEpisodesById(Long id, PageParam page, CallerParam caller) {
        List<LatestEpisodeVo> returnList = Lists.newArrayList();
        List<Long> episodeIds = QmtSbcEpisodeApis.getPassedEpisodeIdsInAlbum(id, page, caller);
        List<TComboEpisode> episodes = QmtSbcEpisodeApis.getTComboEpisodesByIds(episodeIds, caller);
        if (CollectionUtils.isEmpty(episodes)) {
            return Collections.EMPTY_LIST;
        }
        for (TComboEpisode comboEpisode : episodes) {
            try {
                LatestEpisodeVo latestEpisodeVo = episodeVoCreater.createLatestEpisodeVo(comboEpisode);
                returnList.add(latestEpisodeVo);
            } catch (Exception e) {
                LOG.error("fail to createLatestEpisodeVo. eid : {}", comboEpisode.getId(), e);
            }
        }
        return returnList;
    }

    @Override
    public RoundVo getRoundVo(long cid, long csid, CallerParam callerParam) {
        RoundVo roundVo = new RoundVo();
        TCompetitionSeason tCompetitionSeason = SbdCompetitonSeasonApis.getLatestTCompetitionSeasonsByCid(cid, callerParam);
        if (null != tCompetitionSeason) {
            roundVo.setCurrentRound(tCompetitionSeason.getCurrentRound());
            roundVo.setFullRound(tCompetitionSeason.getTotalRound());
        }
        return roundVo;
    }

//	@Override todo 资源位支持
//	public List<HallEpisodeVo> getLephoneDesktopEpisodes(String date, long gameType, CallerParam callerParam) {
//		List<TComboEpisode> episodes = QmtSbcEpisodeApis.getLephoneDesktopChannelEpisodes(date, gameType, callerParam);
//		return createHallEpisodes(episodes, callerParam);
//	}

	@Override
	public List<HallEpisodeVo> getPeriodAppRecommendEpisodes(CallerParam callerParam) {
		Long periodRecommendMatchResourceid = AppResourceContentIdConstants.ALL_PERIOD_RECOMMEND_MATCH_RESOURCEID;
		PageBean pageBean = new PageBean();
		pageBean.setPage(1);
		pageBean.setCount(100);
		return getResourceContentEpisodes(periodRecommendMatchResourceid, pageBean.getPageParam(), callerParam);
	}

	@Override
	public List<HallEpisodeVo> getTimelineEpisodes(LiveTypeParam liveTypeParam, CallerParam callerParam, int page, int count) {
		List<HallEpisodeVo> episodes = Lists.newArrayList();
		//如果为0的话,则为原点是已结束一页和未结束一页
		if (page == 0) {
			List<HallEpisodeVo> endEpisodes = getTimelineEndEpisodes(null, liveTypeParam, callerParam, PageBean.getPageParam(page, count));
			if (CollectionUtils.isNotEmpty(endEpisodes)) {
				episodes.addAll(endEpisodes);
			}
			List<HallEpisodeVo> notEndEpisodes = getTimelineNotEndEpisodes(LiveShowStatusParam.LIVE_NOT_END, null, liveTypeParam, callerParam, PageBean.getPageParam(page, count));
			if (CollectionUtils.isNotEmpty(notEndEpisodes)) {
				episodes.addAll(notEndEpisodes);
			}
		} else {
			int absPage = Math.abs(page) + 1;
			//page小于0说明是取已结束的
			if (page < 0) {
				episodes = getTimelineEndEpisodes(null, liveTypeParam, callerParam, PageBean.getPageParam(absPage, count));
			} else {
				episodes = getTimelineNotEndEpisodes(LiveShowStatusParam.LIVE_NOT_END, null, liveTypeParam, callerParam, PageBean.getPageParam(absPage, count));
			}
		}
		return episodes;
	}

//    @Override  奥运的就废弃吧
//    public Map<String, Object> getEpisodes4OlyMatchs(GetMatchsEpisodesParam p, PageParam pageParam, CallerParam callerParam) {
//        Map<String, Object> cache = episodeCache.getEpisodes4OlyMatchs(p, pageParam, callerParam);
//        if (cache != null) {
//            return cache;
//        }
//
//        Map<String, Object> resultMap;
//        //long total = 0L;
//        List<HallEpisodeVo> returnList = Lists.newArrayList();
//        List<TComboEpisode> episodes = QmtSbcEpisodeApis.getEpisodes4OlyMatchs(p, pageParam, callerParam);
//        if (CollectionUtils.isNotEmpty(episodes)) {
//            Map<Long, List<Integer>> chinaMedalMap = fillChinaMedalMap(p, callerParam);
//            for (TComboEpisode comboEpisode : episodes) {
//                try {
//                    HallEpisodeVo hallEpisodeVo = episodeVoCreater.createHallEpisodeVo4App(comboEpisode, callerParam); //重用之前的封装
//                    if (StringUtils.isNotBlank(hallEpisodeVo.gettLiveLink())) {
//                        hallEpisodeVo.setIsTextLive(1);
//                    } else {
//                        hallEpisodeVo.setIsTextLive(0);
//                    }
//                    hallEpisodeVo.setDisciplineUrl(comboEpisode.getItemUrl());
//                    hallEpisodeVo.setMedalList(chinaMedalMap.get(comboEpisode.getMid()));//中国队获得的奖牌列表
//                    if (CollectionUtils.isNotEmpty(comboEpisode.getCompetitors())) {
//                        for (TCompetitor tCompetitor : comboEpisode.getCompetitors()) {
//                            if (Constants.PC_OLY_DICT_CHINAID.equals(tCompetitor.getCompetitorCountryId())) {
//                                hallEpisodeVo.setHasChinaFlag(1);
//                                break;
//                            }
//                        }
//                    }
//                    hallEpisodeVo.setGoldMatchFlag(MedalType.GOLD.equals(comboEpisode.getMedalType()) ? 1 : 0);
//
//                    returnList.add(hallEpisodeVo);
//
//                } catch (Exception e) {
//                    LOG.error("getEpisodes4OlyMatchs error, episodeId : {}", comboEpisode.getId(), e);
//                    continue;
//                }
//            }
//
//            //total = QmtSbcEpisodeApis.countEpisodes4OlyMatchs(p, callerParam);
//        }
//        resultMap = ResponseUtils.createPageResponse(pageParam.getPage(), returnList);
//        episodeCache.saveEpisodes4OlyMatchs(resultMap, p, pageParam, callerParam);
//
//        return resultMap;
//    }
//
//    private Map<Long, List<Integer>> fillChinaMedalMap(GetMatchsEpisodesParam p, CallerParam callerParam) {
//        Map<Long, List<Integer>> chinaMedalMap = Maps.newHashMap(); //Map<matchId,List<medalType>>
//        GetMedalRankingParam p2 = new GetMedalRankingParam();
//        p2.setCountryId(Constants.PC_OLY_DICT_CHINAID);
//        p2.setCid(p.getCid());
//        p2.setDiscpilineId(p.getDiscpilineId());
//        //p2.setDate(p.getDate());
//        PageParam page = new PageParam();
//        page.setPage(1);
//        page.setCount(1000);//获取中国队符合参数条件的所有奖牌
//        List<TMedal> chinaMedalList = SbdsApis.getTMedalListByType(p2, page, callerParam); //中国奖牌榜
//        for (TMedal tMedal : chinaMedalList) {
//            if (chinaMedalMap.containsKey(tMedal.getMedalMatchId())) {
//                List<Integer> list = chinaMedalMap.get(tMedal.getMedalMatchId());
//                list.add(tMedal.getMedalType().getValue());
//            } else {
//                List<Integer> list = Lists.newArrayList();
//                list.add(tMedal.getMedalType().getValue());
//                chinaMedalMap.put(tMedal.getMedalMatchId(), list);
//            }
//        }
//        return chinaMedalMap;
//    }

//    @Override 晋级之路废弃吧
//	public List<HallEpisodeVo> getTheRoadOfAdvance(long cid, long csid, CallerParam callerParam) {
//		List<TComboEpisode> episodes = QmtSbcEpisodeApis.getTheRoadOfAdvance(cid, csid, callerParam);
//		List<HallEpisodeVo> hallEpisodes = createHallEpisodes(episodes, callerParam);
//		//通过晋级之路的顺序排列
//		Collections.sort(hallEpisodes, new Comparator<HallEpisodeVo>() {
//			@Override
//			public int compare(HallEpisodeVo o1, HallEpisodeVo o2) {
//				return o1.getTheRoadOrder() - o2.getTheRoadOrder();
//			}
//		});
//		return hallEpisodes;
//	}



    public List<HallEpisodeVo> getTimelineEndEpisodes(List<Long> cidList, LiveTypeParam liveTypeParam, CallerParam callerParam, PageParam page) {
		GetCurrentEpisodesParam p = new GetCurrentEpisodesParam();
		p.setLiveShowStatusParam(LiveShowStatusParam.LIVE_END);
		p.setLiveTypeParam(liveTypeParam);
		if (CollectionUtils.isNotEmpty(cidList)) {
			p.setCids(cidList);
		}
		List<TComboEpisode> endEpisodes = QmtSbcEpisodeApis.getCurrentEpisodes(p, page, callerParam);
		if (CollectionUtils.isEmpty(endEpisodes)) {
			return Collections.EMPTY_LIST;
		}
		//已结束的比赛需要翻转下list
		Collections.reverse(endEpisodes);
		return createHallEpisodes(endEpisodes, callerParam);
	}

	public List<HallEpisodeVo> getTimelineNotEndEpisodes(LiveShowStatusParam status, List<Long> cidList, LiveTypeParam liveTypeParam, CallerParam callerParam, PageParam page) {
		GetCurrentEpisodesParam p = new GetCurrentEpisodesParam();
		p.setLiveShowStatusParam(status);
		p.setLiveTypeParam(liveTypeParam);
		if (CollectionUtils.isNotEmpty(cidList)) {
			p.setCids(cidList);
		}
		List<TComboEpisode> notEndEpisodes = QmtSbcEpisodeApis.getCurrentEpisodes(p, page, callerParam);

		if (CollectionUtils.isEmpty(notEndEpisodes)) {
			return Collections.EMPTY_LIST;
		}
		return createHallEpisodes(notEndEpisodes, callerParam);
	}

    @Override
    public List<HallEpisodeVo> getHallMemberEpisodes(GetCurrentEpisodesParam p, PageParam page, CallerParam caller) {
        List<HallEpisodeVo> returnList = Lists.newArrayList();
        List<TComboEpisode> episodes = QmtSbcEpisodeApis.getCurrentMemberEpisodes(p, page, caller);
        if (CollectionUtils.isEmpty(episodes)) {
            return Collections.EMPTY_LIST;
        }
        for (TComboEpisode comboEpisode : episodes) {
            try {
                HallEpisodeVo hallEpisodeVo = episodeVoCreater.createHallEpisodeVo4App(comboEpisode, caller);
                if (hallEpisodeVo != null) {
                    returnList.add(hallEpisodeVo);
                }
            } catch (Exception e) {
                LOG.error("getHallEpisodes4App error, episodeId : {}", comboEpisode.getId(), e);
                continue;
            }
        }
        return returnList;

    }


    @Override //todo 会员
    public List<HallEpisodeVo4Tv> getHallMemberEpisodesForTv(GetCurrentEpisodesParam p, PageParam page, CallerParam caller) {
        List<HallEpisodeVo4Tv> returnList = Lists.newArrayList();
//        List<TComboEpisode> episodes = QmtSbcEpisodeApis.getCurrentMemberEpisodes(p, page, caller);
//        if (CollectionUtils.isEmpty(episodes)) {
//            return Collections.EMPTY_LIST;
//        }
//        for (TComboEpisode comboEpisode : episodes) {
//            try {
//                HallEpisodeVo4Tv hallEpisodeVo = episodeVoCreater.createHallEpisodeVo4Tv(comboEpisode, caller);
//                if (null != hallEpisodeVo) {
//                    returnList.add(hallEpisodeVo);
//                }
//            } catch (Exception e) {
//                LOG.error("getHallEpisodes4App error, episodeId : {}", comboEpisode.getId(), e);
//                continue;
//            }
//        }
        return returnList;

    }

}
