package com.lesports.qmt.web.service;

//import client.SopsApis;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.lesports.LeConstants;
import com.lesports.api.common.CallerParam;
import com.lesports.api.common.PageParam;
//import com.lesports.cms.web.helper.MetaFiller;
//import com.lesports.cms.web.model.VideoWebView;
//import com.lesports.cms.web.utils.HotRankApis;
import com.lesports.id.client.LeIdApis;
import com.lesports.qmt.sbc.api.common.RelatedType;
import com.lesports.qmt.sbc.api.common.TRelatedItem;
import com.lesports.qmt.sbc.api.common.VideoContentType;
import com.lesports.qmt.sbc.api.dto.*;
import com.lesports.qmt.sbc.api.service.GetRelatedVideosParam;
import com.lesports.qmt.sbc.client.QmtSbcApis;
import com.lesports.qmt.sbd.api.dto.TCompetitor;
import com.lesports.qmt.sbd.api.dto.TDetailMatch;
import com.lesports.qmt.web.helper.MetaFiller;
import com.lesports.qmt.web.helper.ShareHelper;
import com.lesports.qmt.web.model.Share;
import com.lesports.qmt.web.model.VideoWebView;
//import com.lesports.sms.web.helper.ShareHelper;
//import com.lesports.sms.web.model.Share;
import com.lesports.qmt.web.utils.HotRankApis;
import com.lesports.utils.*;
import com.lesports.utils.math.LeNumberUtils;
import org.apache.commons.beanutils.LeBeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
* User: ellios
* Time: 15-11-3 : 上午10:56
*/
@Service
public class VideoService {

    private static final Logger LOG = LoggerFactory.getLogger(VideoService.class);

    @Resource
    private ShareHelper shareHelper;

    @Resource
    private MetaFiller metaFiller;

    /**
     * 填充model信息用于视频点播页面
     *
     * @param model
     * @param video
     * @param caller
     */
    public void fillModel4Video(Model model, TVideo video, CallerParam caller, Locale locale) {
        Preconditions.checkNotNull(video);
        //相关视频(带有友好时间的)
        List<VideoWebView> relateVideos = Lists.newArrayList();
        //视频集合
        List<TVideo> tVideos = Lists.newArrayList();
        //专辑id
        long aid = video.getAid();
        if (aid > 0) {
            //专辑下的视频
            TVideoIndexInfo info = QmtSbcApis.getIndexOfVideoInAlbum(video.getId(), aid, caller);
            //m站的分页
            int featurePage = 0;
            int nonFeaturePage = 0;
            //该视频在专辑下非正片的index和视频总数
            if (info != null) {
                model.addAttribute("index", info.getIndex());
                model.addAttribute("total", info.getTotal());
                //如果是正片的话则需要计算正片的页数,非正片的话计算非正片的页数
                if (video.getContentType() == VideoContentType.RECORD || video.getContentType() == VideoContentType.FEATURE) {
                    featurePage = (int) Math.ceil((double) info.getIndex() / (double) 50) - 1;
                } else {
                    nonFeaturePage = (int) Math.ceil((double) info.getIndex() / (double) 50) - 1;
                }
            }
            model.addAttribute("featurePage", featurePage);
            model.addAttribute("nonFeaturePage", nonFeaturePage);
            if (video.getContentType() == VideoContentType.RECORD || video.getContentType() == VideoContentType.FEATURE) {
                //综艺类视频的视频类型 0:非正片 1:正片
                model.addAttribute("isFeature", 1);
                //专辑的发布时间(因为专辑的创建时间只需要精确到月,所以可以用视频的创建时间)
                String createAt = video.getCreateAt();
                String yearAndMonth = StringUtils.substring(createAt, 0, 6);
                model.addAttribute("yearAndMonth", yearAndMonth);
            } else {
                //综艺类视频的视频类型 0:非正片 1:正片
                model.addAttribute("isFeature", 0);
            }
            //m站改版需要专辑的正片50条,非正片50条
            if (caller.getCallerId() == LeConstants.LESPORTS_MSITE_CALLER_CODE || caller.getCallerId() == LeConstants.LESPORTS_HK_MSITE_CALLER_CODE) {
                GetRelatedVideosParam p = new GetRelatedVideosParam();
                p.setRelatedId(aid);
                p.setTypes(Lists.newArrayList(VideoContentType.HIGHLIGHTS, VideoContentType.MATCH_REPORT, VideoContentType.OTHER));
                List<Long> vids = QmtSbcApis.getVideoIdsByRelatedId(p, PageUtils.createPageParam(0), caller);
                List<TVideo> nonFeatureVideos = QmtSbcApis.getTVideosByIds(vids, caller);
                model.addAttribute("nonfeatures", formatVideoTime(nonFeatureVideos, false,false,caller, locale));
                List<TVideo> features = QmtSbcApis.getRecordVideosInAlbum(aid, PageUtils.createPageParam(0), caller);
                model.addAttribute("features", formatVideoTime(features,false,false,caller, locale));
            }
        } else {
            //视频的类型集合
            tVideos.add(video);
            List<Long> otherVideoIds = QmtSbcApis.getVideoIdsRelatedWithSomeVideo(video.getId(), PageUtils.createPageParam(0), caller);
            //for 里尔暴力相关视频
            List<Long> hotIds = Lists.newArrayList(25781029L,25780255L,25779844L,25779556L,25779471L,25779443L,25779332L,
                    25778581L,25772206L,25778413L,25778372L,25758221L,25780694L);
            if(hotIds.contains(video.getId())){
                hotIds.addAll(otherVideoIds);
                otherVideoIds = hotIds;
            }
            if (CollectionUtils.isNotEmpty(otherVideoIds)) {
                tVideos.addAll(QmtSbcApis.getTVideosByIds(otherVideoIds, caller));
            }
        }
        relateVideos = formatVideoTime(tVideos,true,false,caller, locale);
        if (CollectionUtils.isEmpty(relateVideos)) {
            LOG.error("formatVideoTime return null! videoId = {}", video.getId());
            return;
        }
        //视频信息
        model.addAttribute("video", video);
        //视频名称
        model.addAttribute("cname", video.getName());
        //相关视频
        model.addAttribute("relatedVideos", relateVideos);
        //视频id
        model.addAttribute("vid", video.getId());
        //热播榜
        model.addAttribute("hotRand", HotRankApis.getHotRankData());
        //播放页类型: 0:一般播放页 1:小专题页
        model.addAttribute("videoType", 0);
        //比赛中分享url
        //todo
        //model.addAttribute("share", SmsApis.getShareByCaller(tVideo.getId() + "", callerId, TShareType.VIDEO));
        model.addAttribute("share", shareHelper.getShare3Video(video, caller, Share.ShareType.VIDEO, locale));
        //添加点播页mate信息
        metaFiller.fillVideoMeta(model, video,locale);
        //定位标签
        model.addAttribute("tags", video.getTags());
        //评论id
        model.addAttribute("commentId", video.getCommentId());
        //专辑id
        model.addAttribute("aid", video.getAid());
    }

    /**
     * 填充model专题点播页面
     *
     * @param model
     * @param pkgid_vid
     */
    public void fillMode4TopicVideo(Model model, String pkgid_vid, CallerParam caller, Locale locale) {
        if (!StringUtils.contains(pkgid_vid, LeConstants.UNDERLINE)) {
            LOG.error("fail to fillMode4TopicVideo。 illegal pkgid_vid : {}", pkgid_vid);
            throw new IllegalArgumentException("illegal pkgid_vid :" + pkgid_vid);
        }

        String[] pvArray = StringUtils.split(pkgid_vid, LeConstants.UNDERLINE);
        String pkgId = pvArray[0];
        long vid = LeNumberUtils.toLong(pvArray[1]);
		TopicVideosApi.TjPackage tjPackage = TopicVideosApi.getTopicVideos(pkgId);
		if (tjPackage == null) {
			LOG.error("fail to fillMode4TopicVideo。 illegal pkgid_vid : {}", pkgid_vid);
			throw new IllegalArgumentException("illegal pkgid_vid :" + pkgid_vid);
		}
		List<TopicVideosApi.TjVideo> dataList = tjPackage.getDataList();
		if (CollectionUtils.isEmpty(dataList)) {
			LOG.error("fail to fillMode4TopicVideo。 illegal pkgid_vid : {}", pkgid_vid);
			throw new IllegalArgumentException("illegal pkgid_vid :" + pkgid_vid);
		}
        List<TVideo> videoList = Lists.newArrayList();
        List<VideoWebView> relateVideos = Lists.newArrayList();
        VideoWebView targetVideo = null;
        for (TopicVideosApi.TjVideo tjVideo : dataList) {
            TVideo tVideo = QmtSbcApis.getTVideoById(tjVideo.getTjVid(), caller);
			if (tVideo == null) {
				LOG.warn("this video is not exist in sports mongo ! videoId = {}", tjVideo.getTjVid());
				continue;
			}
			//被点击播放的视频
			if (tjVideo.getTjVid() == vid) {
				targetVideo = buildVideoWebViewByTVideo(tVideo, caller, locale);
			}
			videoList.add(tVideo);
        }

        if (targetVideo == null) {
            LOG.error("fai to find target video from package. pkgid_vid : {}", pkgid_vid);
            throw new IllegalArgumentException("fail to rfillMode4TopicVideo, illegal pkgid_vid: " + pkgid_vid);
        }
		if (CollectionUtils.isNotEmpty(videoList)) {
			relateVideos = formatVideoTime(videoList, false, false, caller, locale);
		}
        //相关视频
        model.addAttribute("relatedVideos", relateVideos);
        model.addAttribute("video", targetVideo);
        model.addAttribute("vid", targetVideo.getId());
        model.addAttribute("cname", targetVideo.getName());
        //热播榜
        model.addAttribute("hotRand", HotRankApis.getHotRankData());
        //播放页类型: 0:一般播放页 1:小专题页
        model.addAttribute("videoType", 1);
        //todo 处理分享
//        model.addAttribute("share", ShareUtils.getVideoShare(targetVideo, caller));
        //添加点播页mate信息
        metaFiller.fillVideoMeta(model, targetVideo,locale);
        //评论id
        model.addAttribute("commentId", targetVideo.getCommentId());
    }

    /**
     * 获取比赛页的相关视频
     *
     * @param episode
     * @return
     */
    public List<VideoWebView> getVideosRelated4Match(TComboEpisode episode, TDetailMatch match, CallerParam caller, Locale locale) {
        List<TVideo> videoList = Lists.newArrayList();
        //是否用人工运营的视频
        List<TRelatedItem> tRelatedItems = episode.getRelatedItems();
        //TODO 如果type为video，则使用人工视频，否则不适用
        if (CollectionUtils.isNotEmpty(tRelatedItems)) {
            for (TRelatedItem tRelatedItem : tRelatedItems) {
                if (tRelatedItem.getType().equals(RelatedType.VIDEO)) {
                    TNews tNews = QmtSbcApis.getTNewsById(tRelatedItem.getId(), caller);
                    if (tNews != null) {
                        TVideo tVideo = QmtSbcApis.getTVideoById(LeIdApis.convertMmsVideoIdToLeSportsVideoId(tNews.getVid()), caller);
                        videoList.add(tVideo);
                    }
                }
            }
        }
        else {
            List<Long> relatedIds = Lists.newArrayList();
            //优先展示两队相关视频、无两队相关视频用赛事视频补齐(上限30条)
            if (episode.isVs()) {
                //对阵的获取参赛者集合
                List<TCompetitor> competitors = match.getCompetitors();
                if (CollectionUtils.isNotEmpty(competitors)) {
                    for (TCompetitor competitor : competitors) {
                        relatedIds.add(competitor.getId());
                    }
                } else {
                    LOG.warn("no competitors in match. mid : {}", match.getId());
                }
            }
            //添加赛事id
            relatedIds.add(episode.getCid());
            //有些冷门的赛事没有相关视频,则需要用大项来进行补充
            relatedIds.add(episode.getGameFType());
            videoList = getVideosByRelatedIds(relatedIds, caller);
        }
        return formatVideoTime(videoList, false, false, caller, locale);
    }

//        if (episode.isIsOperateRelated()) {
//            if (CollectionUtils.isNotEmpty(episode.getOperateRelatedIds())) {
//                for (Long vid : episode.getOperateRelatedIds()) {
//                    TVideo video = SopsApis.getTVideoById(vid, caller);
//                    if (video != null) {
//                        videoList.add(video);
//                    }
//                }
//            }
//        } else {
//            List<Long> relatedIds = Lists.newArrayList();
//            //优先展示两队相关视频、无两队相关视频用赛事视频补齐(上限30条)
//            if (episode.isVs()) {
//                //对阵的获取参赛者集合
//                List<TCompetitor> competitors = match.getCompetitors();
//                if (CollectionUtils.isNotEmpty(competitors)) {
//                    for (TCompetitor competitor : competitors) {
//                        relatedIds.add(competitor.getId());
//                    }
//                } else {
//                    LOG.warn("no competitors in match. mid : {}", match.getId());
//                }
//            }
//            //添加赛事id
//            relatedIds.add(episode.getCid());
//            //有些冷门的赛事没有相关视频,则需要用大项来进行补充
//            relatedIds.add(episode.getGameFType());
//            videoList = getVideosByRelatedIds(relatedIds, caller);
//        }
//
//        return formatVideoTime(videoList,false,false,caller, locale);
//    }

    /**
     * 获取非比赛页的相关视频
     *
     * @param episode
     * @return
     */
    public List<VideoWebView> getVideosRelated4NonMatch(TComboEpisode episode, CallerParam caller, Locale locale) {
        List<TVideo> videoList = Lists.newArrayList();
        //是否用人工运营的视频
        List<TRelatedItem> tRelatedItems = episode.getRelatedItems();
        //TODO 如果type为video，则使用人工视频，否则不适用
        if (CollectionUtils.isEmpty(tRelatedItems)){
            for (TRelatedItem tRelatedItem : tRelatedItems){
                if (tRelatedItem.getType().equals(RelatedType.VIDEO)){
                    TVideo video = QmtSbcApis.getTVideoById(tRelatedItem.getId(), caller);
                    if (video != null) {
                        videoList.add(video);
                    }}else {
                    //异步获专辑的相关视频(该专辑的正片集合)
                    List<TVideo> videos = QmtSbcApis.getRecordVideosInAlbum(episode.getAid(), PageUtils.createPageParam(0), caller);
                    if (CollectionUtils.isNotEmpty(videos)) {
                        videoList.addAll(videos);
                    } else {
                        //如无正片,则获取关联到该节目的视频
                        if (CollectionUtils.isNotEmpty(episode.getVideos())) {
                            for (TSimpleVideo simpleVideo : episode.getVideos()) {
                                TVideo video = QmtSbcApis.getTVideoById(simpleVideo.getVid(), caller);
                                if (video != null) {
                                    videoList.add(video);
                                }
                            }
                        }
                    }
                }
            }
        }
//        if (episode.isIsOperateRelated()) {
//            if (CollectionUtils.isNotEmpty(episode.getOperateRelatedIds())) {
//                for (Long vid : episode.getOperateRelatedIds()) {
//                    TVideo video = SopsApis.getTVideoById(vid, caller);
//                    if (video != null) {
//                        videoList.add(video);
//                    }
//                }
//            }
//        } else {
//            //异步获专辑的相关视频(该专辑的正片集合)
//            List<TVideo> videos = SopsApis.getRecordVideosInAlbum(episode.getAid(), PageUtils.createPageParam(0), caller);
//			if (CollectionUtils.isNotEmpty(videos)) {
//				videoList.addAll(videos);
//			} else {
//				//如无正片,则获取关联到该节目的视频
//				if (CollectionUtils.isNotEmpty(episode.getVideos())) {
//					for (TSimpleVideo simpleVideo : episode.getVideos()) {
//						TVideo video = SopsApis.getTVideoById(simpleVideo.getVid(), caller);
//						if (video != null) {
//							videoList.add(video);
//						}
//					}
//				}
//			}
        return formatVideoTime(videoList, false,false,caller, locale);
    }

    /**
     * 获取直播点播页的相关视频
     *
     * @param relatedIds
     * @return
     */
    private List<TVideo> getVideosByRelatedIds(List<Long> relatedIds, CallerParam caller) {
        List<TVideo> videos = Lists.newArrayList();
        //获取相关视频
        for (Long relatedId : relatedIds) {
            try {
                GetRelatedVideosParam p = new GetRelatedVideosParam();
                p.setRelatedId(relatedId);
                List<Long> videoIds = QmtSbcApis.getVideoIdsByRelatedId(p, PageUtils.createPageParam(0), caller);
                List<TVideo> temp = QmtSbcApis.getTVideosByIds(videoIds, caller);
                if (CollectionUtils.isNotEmpty(temp)) {
                    videos.addAll(temp);
                    if (videos.size() >= 20) {
                        break;
                    }
                }
            } catch (Exception e) {
                LOG.error("fai to getVideosByRelatedId, relatedId : {}, caller : {}", relatedId, caller, e);
            }
        }
        Collections.sort(videos, new Comparator<TVideo>() {
            @Override
            public int compare(TVideo o1, TVideo o2) {
                return o2.getCreateAt().compareTo(o1.getCreateAt());
            }
        });
        return videos;
    }

    /**
     * 格式化视频时长和视频发布时间
     *
     * @param videoList
     * @return
     */
    public List<VideoWebView> formatVideoTime(List<TVideo> videoList, Boolean isNeedFilling, Boolean isNeedAll, CallerParam caller, Locale locale) {
        List<VideoWebView> returnList = Lists.newLinkedList();
        //过滤重复视频的idSet
        Set<Long> vidsSet = Sets.newHashSet();
        //添加发布时间和格式化视频播放时长
        if (CollectionUtils.isNotEmpty(videoList)) {
            //不过滤第一个视频是否是克隆视频的标志
            Boolean first_flag = true;
            for (TVideo tVideo : videoList) {
                try {
                    VideoWebView video = buildVideoWebViewByTVideo(tVideo, caller, locale);
                    //去掉视频集合中的重复视频和克隆视频
                    if (!vidsSet.contains(video.getId())) {
                        if (first_flag || !video.isCloneVideo()) {
                            vidsSet.add(video.getId());
                            returnList.add(video);
                            first_flag = false;//第一个视频过后就需要过滤克隆视频了
                        }
                    }
                } catch (Exception e) {
                    LOG.error("format video time fails, videoId : {}", tVideo.getId(), e);
                }
            }
        }
        if (isNeedFilling && videoList.size() < 20) {
           PageParam request = new PageParam();
            int page = 1;
            while (returnList.size() < 20 && page <= 5) {
                request.setPage(page);
                request.setCount(20 - videoList.size());
//                List<Long> ids = SopsApis.getLatestVideoIds(request,caller);
//                List<TVideo> latestVideos = SopsApis.getTVideosByIds(ids,caller);
                List<Long> ids = QmtSbcApis.getLatestVideoIds(request,caller);
                List<TVideo> latestVideos = QmtSbcApis.getTVideosByIds(ids,caller);
                if (CollectionUtils.isNotEmpty(latestVideos)) {
                    for (TVideo tVideo : latestVideos) {
                        try {
                            if (tVideo == null) {
                                continue;
                            }
                            VideoWebView video = buildVideoWebViewByTVideo(tVideo, caller,locale);
                            //去掉视频集合中的重复视频和克隆视频
                            if (!vidsSet.contains(video.getId()) && !video.isCloneVideo()) {
                                vidsSet.add(video.getId());
                                returnList.add(video);
                                if (returnList.size() > 20) {
                                    return returnList.subList(0, 20);
                                }
                            }
                        } catch (Exception e) {
                            LOG.error("format video time fails, videoId = {}", tVideo.getId());
                        }
                    }
                }
                page++;
            }
        }
        if (isNeedAll) {
            return returnList;
        }
        return returnList.size() > 20 ? returnList.subList(0, 20) : returnList;
    }

    /**
     * 添加发布时间和格式化视频播放时长
     *
     * @param tVideo
     * @return
     */
    public VideoWebView buildVideoWebViewByTVideo(TVideo tVideo, CallerParam calller, Locale locale) {
        if (tVideo == null) {
            return null;
        }
        VideoWebView v = new VideoWebView();
        LeBeanUtils.copyNotEmptyPropertiesQuietly(v, tVideo);
        String s = LeDateUtils.getHumanizedDuration(tVideo.getDuration(), true);
        if (StringUtils.isNotBlank(s) && !s.contains(":")) {
            s = "00:" + s;
        }
        v.setPlayTime(s);
        v.setFriendTime(LeDateUtils.getHumanizedInterval(tVideo.getCreateAt()));
        v.setShare(shareHelper.getShare5Anchor3Video(tVideo, calller, Share.ShareType.VIDEO, locale));
        return v;
    }

	public void fillTopicListModel(Model model, String topicName, HttpServletResponse response, CallerParam callerParam, Locale locale) {
		TopicListApi.TopicListVo topicList = TopicListApi.getTopicList(topicName);
		if (topicList == null) {
			LOG.error("this topicName get topicList error ! topicName = {}", topicName);
			return;
		}
		List<TopicListApi.TjPackage> tjPackages = topicList.getTjPackages();

		Iterator<TopicListApi.TjPackage> iterator = tjPackages.iterator();
		while (iterator.hasNext()) {
			TopicListApi.TjPackage tjPackage = iterator.next();
			//视频包内的类型为专辑时略过,暂不支持
			if (tjPackage.getPtype() == 1) {
				iterator.remove();
				continue;
			}
			List<TopicListApi.TjVideo> dataList = tjPackage.getDataList();
			if (CollectionUtils.isNotEmpty(dataList)) {
				List<TVideo> tjVideos = Lists.newArrayList();
				for (TopicListApi.TjVideo tjVideo : dataList) {
					TVideo video = QmtSbcApis.getTVideoById(tjVideo.getTjVid(), callerParam);
					if(video != null) {
						tjVideos.add(video);
					}
				}
				//如果体育库里面没有该视频包的视频,则不要此专题包
				if (CollectionUtils.isEmpty(tjVideos)) {
					iterator.remove();
					continue;
				}
				tjPackage.getDataList().clear();
				tjPackage.getDataList().addAll((List)formatVideoTime(tjVideos, false, true, callerParam, locale));
			}
		}
		//视频包
		model.addAttribute("tjPackages", tjPackages);
		//缩略图
		model.addAttribute("focusMPic", topicList.getFocusMPic());
		//导语
		model.addAttribute("introduction", topicList.getDescription());
		//分享
		model.addAttribute("share", shareHelper.getShare3TopicList(topicName, callerParam, Share.ShareType.TOPIC_LIST, locale));
		//添加点播页mate信息
		metaFiller.fillTopicListMeta(model, topicList, locale);
	}

	public void fillHtopicListModel(Model model, String topicName, HttpServletResponse response, CallerParam callerParam, Locale locale) {
		HtopicListApi.TopicListVo topicList = HtopicListApi.getTopicList(topicName);
		if (topicList == null) {
			LOG.error("this topicName get topicList error ! topicName = {}", topicName);
			return;
		}
		List<HtopicListApi.TjPackage> tjPackages = topicList.getTjPackages();

		Iterator<HtopicListApi.TjPackage> iterator = tjPackages.iterator();
		while (iterator.hasNext()) {
			HtopicListApi.TjPackage tjPackage = iterator.next();
			//视频包内的类型为专辑时略过,暂不支持
			if (tjPackage.getPtype() != 4) {
				iterator.remove();
				continue;
			}
			List<Map> dataList = tjPackage.getDataList();
			//如果体育库里面没有该视频包的视频,则不要此专题包
			if (CollectionUtils.isEmpty(dataList)) {
				iterator.remove();
				continue;
			}
		}
		//视频包
		model.addAttribute("tjPackages", tjPackages);
		//缩略图
		model.addAttribute("focusMPic", topicList.getFocusMPic());
		//导语
		model.addAttribute("introduction", topicList.getDescription());
		//突发专题id
		model.addAttribute("hid", topicList.getId());
		//分享
		model.addAttribute("share", shareHelper.getShare3HtopicList(topicName, callerParam, Share.ShareType.HTOPIC_LIST, locale));
		//添加点播页mate信息
		metaFiller.fillHtopicListMeta(model, topicList, locale);
	}
}
