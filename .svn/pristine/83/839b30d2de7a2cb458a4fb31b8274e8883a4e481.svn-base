package com.lesports.qmt.web.api.core.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.lesports.api.common.CallerParam;
import com.lesports.api.common.PageParam;
import com.lesports.id.api.IdType;
import com.lesports.id.client.LeIdApis;
import com.lesports.qmt.config.api.dto.TMenuItem;
import com.lesports.qmt.resource.ComboResourceCreaters;
import com.lesports.qmt.resource.core.ComboResource;
import com.lesports.qmt.resource.cvo.BaseCvo;
import com.lesports.qmt.sbc.api.common.NewsType;
import com.lesports.qmt.sbc.api.dto.ResourceItemType;
import com.lesports.qmt.sbc.api.dto.TNews;
import com.lesports.qmt.sbc.api.service.GetRecommendParam;
import com.lesports.qmt.sbc.api.service.GetRelatedNewsParam;
import com.lesports.qmt.sbc.client.QmtSbcApis;
import com.lesports.qmt.web.api.core.creater.NewsVoCreater;
import com.lesports.qmt.web.api.core.rconverter.NewsResConverter;
import com.lesports.qmt.web.api.core.rconverter.RecommendResConverter;
import com.lesports.qmt.web.api.core.service.MenuService;
import com.lesports.qmt.web.api.core.service.NewsService;
import com.lesports.qmt.web.api.core.util.AppResourceContentIdConstants;
import com.lesports.qmt.web.api.core.util.Constants;
import com.lesports.qmt.web.api.core.vo.DetailNews;
import com.lesports.qmt.web.api.core.vo.NewsVo;
import com.lesports.qmt.web.api.core.vo.RecommendVo;
import com.lesports.utils.LeProperties;
import com.lesports.utils.LeStringUtils;
import com.lesports.utils.math.LeNumberUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by gengchengliang on 2015/6/18.
 */
@Service("newsService")
public class NewsServiceImpl implements NewsService {

    private static final Logger LOG = LoggerFactory.getLogger(NewsServiceImpl.class);

    @Resource
    private NewsVoCreater newsVoCreater;

    @Resource
    private RecommendResConverter recommendResConverter;

	@Resource
	private NewsResConverter newsResConverter;

	@Resource
	private MenuService menuService;

	//热门推荐的tagId
	private static final Long RECOMMEND_TAG_ID = LeProperties.getLong("lesports.recommend.tag.id", 100153008l);

    @Override
    public List<NewsVo> getNewsListByRelatedId(GetRelatedNewsParam p, PageParam pageParam, CallerParam callerParam) {
        List<NewsVo> returnNews = Lists.newArrayList();

        List<Long> ids = QmtSbcApis.getNewsIdsByRelatedId(p, pageParam, callerParam);
        if(CollectionUtils.isEmpty(ids)){
            return Collections.EMPTY_LIST;
        }
        List<TNews> relatedVideos = QmtSbcApis.getTNewsByIds(ids, callerParam);
        if (CollectionUtils.isNotEmpty(relatedVideos)) {
            for (TNews tNews : relatedVideos) {
                returnNews.add(newsVoCreater.createNewsVo(tNews));
            }
        } else {
            LOG.info(" this relatedId not have relatition videos ! relatedIds ={}", p.getRelatedIds());
        }
        return returnNews;
    }


//    @Override
//    public List<NewsVo> getNews4TvCard(GetRecommendParam p, PageParam pageParam, CallerParam callerParam) {
//        List<NewsVo> returnNews = Lists.newArrayList();
//
//        List<TNews> tNewses = SopsApis.getTNews4TvCard(p, pageParam, callerParam);
//        if (CollectionUtils.isNotEmpty(tNewses)) {
//            for (TNews tNews : tNewses) {
//                returnNews.add(newsVoCreater.createNewsVo(tNews));
//            }
//        }
//        return returnNews;
//    }

	@Override
	public List<NewsVo> getRecommendNewsByRelatedIdAndTypes(String type, GetRelatedNewsParam p, PageParam pageParam, CallerParam callerParam) {
		if (CollectionUtils.isEmpty(p.getRelatedIds())) {
			return Collections.EMPTY_LIST;
		}
		long relatedId = p.getRelatedIds().get(0);
		IdType idType = LeIdApis.checkIdTye(relatedId);
		if (idType == IdType.RESOURCE) {
			if (relatedId == 0) {//如果为0,则为热门的资源位ID
                relatedId = AppResourceContentIdConstants.APP_RECOMMEND_ID;
			}
			return getNewsByContents(relatedId, type, pageParam, callerParam);
		} else {
			return getNewsListByRelatedId(p, pageParam, callerParam);
		}
	}

    @Override
    public List<NewsVo> getNewsListByRelatedIdWithFallback(String type, GetRelatedNewsParam p, Integer isNeedRecommends, PageParam pageParam, CallerParam callerParam) {
        List<NewsVo> newsVos = Lists.newArrayList();
        try {
			if (isNeedRecommends == 1) {
				newsVos = getRecommendNewsByRelatedIdAndTypes(type, p, pageParam, callerParam);
			} else {
				newsVos = getNewsListByRelatedId(p, pageParam, callerParam);
			}
        } catch (Exception e) {
			//降级处理
        }
        return newsVos;
    }




//    @Override
//    public List<NewsVo> get24HoursNews(PageParam page, CallerParam caller) {
//        List<NewsVo> returnNews = Lists.newArrayList();
//        List<Long> newsIds = SopsApis.getTRecommendTVsByIds(page, caller);
//        if(CollectionUtils.isEmpty(newsIds)){
//            LOG.info(" cannot get 24hours news! page : {}, caller : {}", page, caller);
//            return Collections.EMPTY_LIST;
//        }
//        List<TNews> newsList = SopsApis.getTNewsByIds(newsIds,caller);
//        if(CollectionUtils.isEmpty(newsList)){
//            LOG.info(" cannot get 24hours news! page : {}, caller : {}", page, caller);
//            return Collections.EMPTY_LIST;
//        }
//        for (TNews tNews : newsList) {
//            returnNews.add(newsVoCreater.createNewsVo(tNews));
//        }
//    return returnNews;
//    }

    @Override
    public DetailNews getNewsById(long id, CallerParam caller, int idType) {
        TNews tNews = null;
		if (idType == 0) {
			tNews = QmtSbcApis.getTNewsById(id, caller);
		} else if (idType == 1) {
			tNews = QmtSbcApis.getTNewsByVid(id, caller);
		}
        if (tNews == null) {
            return null;
        }
        return newsVoCreater.createDetailNews(tNews);
    }

    @Override
    public DetailNews getNewsById4TV(long id, CallerParam caller, int idType) {
        TNews tNews = null;
        if (idType == 0) {
            tNews = QmtSbcApis.getTNewsById(id, caller);
        } else if (idType == 1) {
            tNews = QmtSbcApis.getTNewsByVid(id, caller);
        }
        if (tNews == null) {
            return null;
        }
        DetailNews detailNews =  newsVoCreater.createDetailNews(tNews);
        Map<String, String> returnMap = Maps.newHashMap();
        returnMap.put(Constants.VIDEO_NEWS_IMAGE_169, getFullImageUrl(Constants.IMAGE_JOINT_SIZE_169_400_225, tNews.getImages().get(0).getImageUrl()));
        detailNews.setImageUrl(returnMap);
        return detailNews;
    }


//    @Override todo 资源位
//    public List<RecommendVo> getRecommendsByType(String type, PageParam page, CallerParam caller) {
//        List<RecommendVo> recommendList = Lists.newArrayList();
//        GetRecommendParam p = new GetRecommendParam();
//        p.setType(type);
//        List<TRecommend> recommends = SopsApis.getRecommendsByType(p, page, caller);
//        if (CollectionUtils.isEmpty(recommends)) {
//            LOG.info(" this type have no Recommends ! type : {}, page : {}, caller : {}", type, page, caller);
//        }
//        for (TRecommend tRecommend : recommends) {
//            RecommendVo recommend = newsVoCreater.createRecommend(tRecommend,caller);
//            if (recommend != null) {
//                recommendList.add(recommend);
//            }
//        }
//
//        return recommendList;
//    }

//    @Override todo 资源位
//    public List<RecommendVo> getRecommendsByTypeWithFallback(String type, PageParam page, CallerParam caller) {
//        List<RecommendVo> recommendVos = Lists.newArrayList();
//        try {
//            recommendVos = getRecommendsByType(type, page, caller);
//        } catch (Exception e) {
//            LOG.error("get recommend by type error. type : {}", type, e);
//            if (CollectionUtils.isEmpty(recommendVos)) {
//                LOG.info("app getRecommendsByType begin to enter fallback. type : {}", type);
//                Map<String, String> paramMap = Maps.newHashMap();
//                paramMap.put("type", type);
//                paramMap.put("count", String.valueOf(page.getCount()));
//                paramMap.put("page", String.valueOf(page.getPage()));
//                recommendVos = fallbackCache.findByKey(Platform.MOBILE.name(), LeConstants.METHOD_APP_NEWS_RECOMMEND, paramMap, List.class);
//            }
//        }
//        return recommendVos;
//    }

	@Override
	public List<RecommendVo> getRecommendsAndNews(GetRecommendParam p, PageParam page, CallerParam caller) {
		Long rescontentid = 0l;
		long relatedId = p.getRelatedId();
		if (relatedId == 0) {//如果为0,则为热门的资源位ID
			rescontentid = AppResourceContentIdConstants.APP_RECOMMEND_ID;
		} else {
			IdType idType = LeIdApis.checkIdTye(relatedId);
			if (idType == IdType.RESOURCE) {
				rescontentid = relatedId;
			} else {
				if (relatedId == RECOMMEND_TAG_ID) {//如果为客户端的热门tagId,则为热门的资源位ID
					rescontentid = AppResourceContentIdConstants.APP_RECOMMEND_ID;
				} else {//客户端传入原resourceId,获取菜单中的newsResourceId
					Long index = Constants.RESOURCEID_2_INDEX.get(relatedId);
					if (index == null || index == 0) {
						return Collections.EMPTY_LIST;
					}
					Long newsResourceId = menuService.getNewsResourceId(index);
					if (newsResourceId == null || newsResourceId == 0) {
						return Collections.EMPTY_LIST;
					}
					rescontentid = newsResourceId;
				}
			}
		}
		return getRecommendsByContents(rescontentid, p.getType(), p.getNewsType(), "RECOMMEND", page, caller);
	}

	@Override
	public List<RecommendVo> getRecommendsAndNews4App(GetRecommendParam p, PageParam page, CallerParam caller) {
		Long rescontentid = 0l;
		Long autoResourceId = 0l;
		long relatedId = p.getRelatedId();
		if (relatedId == 0) {//如果为0,则为热门的资源位ID
			rescontentid = AppResourceContentIdConstants.APP_RECOMMEND_ID;
			autoResourceId = AppResourceContentIdConstants.APP_AUTO_RECOMMEND_ID;
		} else {
			IdType idType = LeIdApis.checkIdTye(relatedId);
			if (idType == IdType.RESOURCE) {
				autoResourceId = relatedId;
				//todo 新增的标签只支持一个资源位
			} else if (idType == IdType.EPISODE) {//app iOS 用的推荐的接口做的比赛页里面的新闻列表
				GetRelatedNewsParam getRelatedNewsParam = new GetRelatedNewsParam();
				getRelatedNewsParam.setRelatedIds(Lists.newArrayList(p.getRelatedId()));
				String newsType = p.getNewsType();
				if (StringUtils.isNotBlank(newsType)) {
					List<NewsType> newsTypes = Lists.newArrayList();
					String[] split = newsType.split(newsType);
					for (String type : split) {
						NewsType value = NewsType.findByValue(LeNumberUtils.toInt(type));
						newsTypes.add(value);
					}
					if (CollectionUtils.isNotEmpty(newsTypes)) {
						getRelatedNewsParam.setTypes(newsTypes);
					}
				}
				return getNewsRecommendsByRelatedId(getRelatedNewsParam, page, caller);
			} else {
				if (relatedId == RECOMMEND_TAG_ID) {//如果为客户端的热门tagId,则为热门的资源位ID
					rescontentid = AppResourceContentIdConstants.APP_RECOMMEND_ID;
					autoResourceId = AppResourceContentIdConstants.APP_AUTO_RECOMMEND_ID;
				} else {//客户端传入原resourceId,获取菜单中的newsResourceId
					Long index = Constants.RESOURCEID_2_INDEX.get(relatedId);
					if (index == null || index == 0) {
						return Collections.EMPTY_LIST;
					}
					TMenuItem tMenuItem = menuService.getNewsResourceItem(index);
					if (tMenuItem == null) {
						return Collections.EMPTY_LIST;
					}
					rescontentid = tMenuItem.getNewsResourceId();
					autoResourceId = tMenuItem.getNewsAutoResourceId();
				}
			}
		}
		if (page.getPage() == 1) {//第一页手动+自动
			List<RecommendVo> contents = getRecommendsByContents(rescontentid, p.getType(), p.getNewsType(), "RECOMMEND", page, caller);
			List<RecommendVo> autoContents = getRecommendsByContents(autoResourceId, p.getType(), p.getNewsType(), "RECOMMEND", page, caller);
			if (CollectionUtils.isEmpty(autoContents)) {//如果自动资源位无数据,则认为该标签无数据
				return Collections.EMPTY_LIST;
			}
			if (CollectionUtils.isNotEmpty(contents)) {
				//手动在前,并且过滤重复的
				List<Long> recommendIds = Lists.newArrayList();
				for (RecommendVo content : contents) {
					if (LeNumberUtils.toInt(content.getId()) > 0) {
						recommendIds.add(content.getId());
					}
				}
				for (RecommendVo autoContent : autoContents) {
					if (!recommendIds.contains(autoContent.getId())) {
						contents.add(autoContent);
						recommendIds.add(autoContent.getId());
					}
				}
				return contents;
			} else {
				return autoContents;
			}
		}
		return getRecommendsByContents(autoResourceId, p.getType(), p.getNewsType(), "RECOMMEND", page, caller);
	}


	public List<RecommendVo> getNewsRecommendsByRelatedId(GetRelatedNewsParam p, PageParam pageParam, CallerParam callerParam) {
		List<RecommendVo> returnNews = Lists.newArrayList();

		List<Long> ids = QmtSbcApis.getNewsIdsByRelatedId(p, pageParam, callerParam);
		if(CollectionUtils.isEmpty(ids)){
			return Collections.EMPTY_LIST;
		}
		List<TNews> relatedVideos = QmtSbcApis.getTNewsByIds(ids, callerParam);
		if (CollectionUtils.isNotEmpty(relatedVideos)) {
			for (TNews tNews : relatedVideos) {
				returnNews.add(newsVoCreater.createRecommendByNews(tNews));
			}
		} else {
			LOG.info(" this relatedId not have relatition videos ! relatedIds ={}", p.getRelatedIds());
		}
		return returnNews;
	}

	/**
     * 获取某新闻的相关新闻
     *
     * @param newsId
     * @return
     */
    @Override
    public List<NewsVo> getRelatedNews4App(long newsId, List<NewsType> types, PageParam page, CallerParam caller) {
        List<NewsVo> returnNews = Lists.newArrayList();
        List<TNews> relatedNews = QmtSbcApis.getNewsRelatedWithSomeNews(newsId, types, page, caller);
        if (CollectionUtils.isNotEmpty(relatedNews)) {
            for (TNews tNews : relatedNews) {
                returnNews.add(newsVoCreater.createNewsVo(tNews));
            }
        }
        return returnNews;
    }

    @Override
    public List<NewsVo> getRelatedNews4TV(long newsId, List<NewsType> types, PageParam page, CallerParam caller) {
        List<NewsVo> returnNews = Lists.newArrayList();
        List<TNews> relatedNews = QmtSbcApis.getNewsRelatedWithSomeNews(newsId, types, page, caller);
        if (CollectionUtils.isNotEmpty(relatedNews)) {
            for (TNews tNews : relatedNews) {
                Map<String, String> returnMap = Maps.newHashMap();
                NewsVo newsVo = newsVoCreater.createNewsVo(tNews);
                returnMap.put(Constants.VIDEO_NEWS_IMAGE_169, getFullImageUrl(Constants.IMAGE_JOINT_SIZE_169_400_225, tNews.getImages().get(0).getImageUrl()));
                newsVo.setImageUrl(returnMap);
                returnNews.add(newsVo);
            }
        }
        return returnNews;
    }


    private String getFullImageUrl(String size, String originalUrl) {
        StringBuffer sb = new StringBuffer(originalUrl.substring(0, originalUrl.length() - 4));
        sb.append(size).append(originalUrl.substring(originalUrl.length() - 4, originalUrl.length()));
        return sb.toString();
    }

	@Override
	public List<RecommendVo> getFocusNews4AppWithFallback(String type,PageParam pageParam, CallerParam callerParam) {
		return getCmsFocusNews4APP(2878, type, pageParam, callerParam);
	}

    @Override
    public List<RecommendVo> getCmsFocusNews4APP(long rescontentid, String type, PageParam pageParam, CallerParam callerParam) {
		if (rescontentid == 2878) {
			rescontentid = AppResourceContentIdConstants.APP_FOCUS_ID_2878;
		}
		return getRecommendsByContents(rescontentid, type, null, "FOCUS", pageParam, callerParam);
    }

	/**
	 * 获取资源位组的推荐新闻: 手动资源位里面的新闻 + 自动资源位的新闻
	 * @param id
	 * @param newsType
	 * @param pageParam
	 * @param callerParam
	 * @return
	 */
	private List<NewsVo> getNewsByContents(long id, String newsType, PageParam pageParam, CallerParam callerParam) {
		List<NewsVo> returnList = Lists.newArrayList();
		List<Long> newsTypes = LeStringUtils.commaString2LongList(newsType);
		ComboResource resourceVo = ComboResourceCreaters.getComboResource(id,pageParam, callerParam);
		List<BaseCvo> contents = resourceVo.getContents();
		if (CollectionUtils.isNotEmpty(contents)) {
			for (BaseCvo content : contents) {
				if (Lists.newArrayList(ResourceItemType.NEWS, ResourceItemType.RICHTEXT, ResourceItemType.IMAGE_ALBUM, ResourceItemType.VIDEO_NEWS).contains(content.getType())) {
					NewsVo newsVo = newsResConverter.fillVo(content);
					if (CollectionUtils.isNotEmpty(newsTypes)) {
						if (newsTypes.contains(newsVo.getNewsType())) {
							returnList.add(newsVo);
						}
					} else {
						returnList.add(newsVo);
					}
				}
			}
            return returnList;
		}
		return Collections.EMPTY_LIST;
	}


	/**
	 * 获取资源位组的推荐内容: 手动资源位里面的推荐内容 + 自动资源位的推荐内容
	 * @param id
	 * @param newsType
	 * @param pageParam
	 * @param callerParam
	 * @return
	 */
	private List<RecommendVo> getRecommendsByContents(long id, String type, String newsType, String domain, PageParam pageParam, CallerParam callerParam) {
		if (id <= 0) {
			return Collections.EMPTY_LIST;
		}
		List<RecommendVo> returnList = Lists.newArrayList();
		List<Long> types = LeStringUtils.commaString2LongList(type);
		List<Long> newsTypes = LeStringUtils.commaString2LongList(newsType);
		ComboResource resourceVo = ComboResourceCreaters.getComboResource(id, pageParam, callerParam);
		if (resourceVo == null) {
			return Collections.EMPTY_LIST;
		}
		List<BaseCvo> contents = resourceVo.getContents();
		if (CollectionUtils.isNotEmpty(contents)) {
			for (BaseCvo content : contents) {
				RecommendVo recommend = recommendResConverter.fillVo(content, domain);
				if (recommend == null || recommend.getContentType() == null) {
					continue;
				}
				//因为ipad判断如果有h5地址就直接跳转了,所以把ipad的新闻的地址去掉
				if (callerParam.getCallerId() == 1014L && Lists.newArrayList(0,2,3).contains(recommend.getContentType())) {
					recommend.setH5(null);
				}
				if (recommend != null && types.contains(LeNumberUtils.toLong(recommend.getContentType()))) {
					if (CollectionUtils.isNotEmpty(newsTypes) && (recommend.getContentType() == 0 || recommend.getContentType() == 2)) {
						if (newsTypes.contains(LeNumberUtils.toLong(recommend.getNewsType()))) {
							returnList.add(recommend);
						}
					} else {
						returnList.add(recommend);
					}
				}
			}
		}
		return returnList;
	}

	@Override
	public List<NewsVo> getMultiNews(List<Long> newsIds, CallerParam callerParam) {
		List<NewsVo> returnNews = Lists.newArrayList();
		List<TNews> tNewses = QmtSbcApis.getTNewsByIds(newsIds, callerParam);
		if (CollectionUtils.isEmpty(tNewses)) {
			return Collections.EMPTY_LIST;
		}
		for (TNews tNews : tNewses) {
			returnNews.add(newsVoCreater.createNewsVo(tNews));
		}
		return returnNews;
	}

}
