package com.lesports.qmt.web.api.core.creater;

import com.google.common.collect.Maps;
import com.lesports.qmt.sbc.api.common.NewsType;
import com.lesports.qmt.sbc.api.dto.TComboEpisode;
import com.lesports.qmt.sbc.api.dto.TLiveStream;
import com.lesports.qmt.sbc.api.dto.TNews;
import com.lesports.qmt.sbc.api.dto.TNewsImage;
import com.lesports.qmt.web.api.core.util.Constants;
import com.lesports.qmt.web.api.core.vo.DetailNews;
import com.lesports.qmt.web.api.core.vo.NewsVo;
import com.lesports.qmt.web.api.core.vo.RecommendVo;
import com.lesports.sms.api.common.RecommendContentType;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;


/**
 * Created by gengchengliang on 2015/6/24.
 */
@Component("newsVoCreater")
public class NewsVoCreater {

    private static final Logger LOG = LoggerFactory.getLogger(NewsVoCreater.class);

    private static final String JPG_SUFFIX = ".jpg";

    private static final String PNG_SUFFIX = ".png";

    //CMS中如果type类型为1的时候为id为视频id
    private static final int CMS_BLOCK_VIDEO_TYPE = 1;
    //CMS中如果type类型为5的时候为id为专题id
    private static final int CMS_BLOCK_TOPIC_TYPE = 5;
	//节目
	private static final int CMS_BLOCK_EPISODE_TYPE = 9;
	//新闻
    private static final int CMS_BLOCK_NEWS_TYPE = 11;
	//h5
	private static final int CMS_BLOCK_H5_TYPE = 12;
	//POSTID
	private static final int CMS_BLOCK_POST_TYPE = 13;
	//CAMP
	private static final int CMS_BLOCK_CAMP_TYPE = 14;

//    @Inject
//    TopicService topicService;

//    /**
//     * 为推荐赋值
//     *
//     * @param tRecommend
//     * @return
//     */
//	public RecommendVo createRecommend(TRecommend tRecommend, CallerParam callerParam) {
//		RecommendVo recommend = new RecommendVo();
//		recommend.setId(tRecommend.getContentId());
//		recommend.setName(tRecommend.getName());
//		recommend.setOrder(tRecommend.getOrder());
//		recommend.setAdTitle(tRecommend.getAdTitle());
//		recommend.setContentType(tRecommend.getContentType().ordinal());
//		if (tRecommend.getContentType() == RecommendContentType.TOPIC) {
//			TopicInfo topicInfo = topicService.get(LeNumberUtils.toInt(tRecommend.getContentId()));
//			if (topicInfo == null) {
//				LOG.warn("this recommend topic is null ! contentId = {} ", tRecommend.getContentId());
//				return null;
//			}
//			List<TopicInfo.VideoInfo> sVideos = topicInfo.getVideos();
//			if (CollectionUtils.isEmpty(sVideos)) {
//				LOG.warn("this topic has no videos ! contentId = {} ", tRecommend.getContentId());
//				return null;
//			}
//			recommend.setUpdateTime(topicInfo.getUpdateTime());
//			recommend.setCreateTime(topicInfo.getCreateTime());
//			recommend.setImageUrl(topicInfo.getImageUrl());
//			for (TopicInfo.VideoInfo sVideo : sVideos) {
//				RecommendVo.VideoInfo video = recommend.new VideoInfo();
//				video.setId(sVideo.getId());
//				video.setName(sVideo.getName());
//				video.setImageUrl(sVideo.getImageUrl());
//				recommend.getVideos().add(video);
//				if (recommend.getVideos().size() >= 3) {//专题的话返回3个视频即可
//					break;
//				}
//			}
//		} else if (tRecommend.getContentType() == RecommendContentType.NEWS || tRecommend.getContentType() == RecommendContentType.RICHTEXT || tRecommend.getContentType() == RecommendContentType.IMAGE_ALBUM) {
//			TNews tNews = SopsApis.getTNewsById(tRecommend.getContentId(), callerParam);
//			if (tNews == null) {
//				LOG.warn("this tRecommend has no news ! contentId = {} ", tRecommend.getContentId());
//				return null;
//			}
//			recommend.setImageUrl(getImageMap(tNews));
//			recommend.setNewsType(tNews.getType().ordinal());
//			recommend.setCreateTime(tNews.getPublishAt());
//			recommend.setIsPay(tNews.getIsPay());
//			if (tNews.getType() == NewsType.IMAGE_ALBUM) {
//				recommend.setImagesCount(tNews.getImagesSize());
//			}
//		} else if (tRecommend.getContentType() == RecommendContentType.POST) {
//			String sId = tRecommend.getContentSId();
//			if (StringUtils.isBlank(sId)) {
//				LOG.warn("this postId is null ! contentId = {}, recommendId = {} ", tRecommend.getContentId(), tRecommend.getId());
//				return null;
//			}
//			Post.TopicThread post = PostApis.postInfo(sId, callerParam.getCallerId());
//			if (post == null) {
//				LOG.warn("this post is null ! contentId = {}, recommendId = {} ", tRecommend.getContentId(), tRecommend.getId());
//				return null;
//			}
//			recommend.setId(tRecommend.getId());
//			recommend.setPostId(tRecommend.getContentSId());
//			String pic = "";
//			List<Post.PictureInfos> pictureInfos = post.getMsgContent().getPictureInfos();
//			if (CollectionUtils.isNotEmpty(pictureInfos)) {
//				if (pictureInfos.get(0).getThumb() != null && StringUtils.isNotBlank(pictureInfos.get(0).getThumb().getUrl())) {
//					pic = pictureInfos.get(0).getThumb().getUrl();
//				}
//			}
//			Map<String, String> returnMap = Maps.newHashMap();
//			//添加400_225的图
//			returnMap.put(Constants.VIDEO_NEWS_IMAGE_169, pic);
//			recommend.setImageUrl(returnMap);
//			recommend.setContent(post.getMsgContent().getContent());
//			recommend.setUname(post.getUname());
//			recommend.setFigurl(post.getFigurl());
//			recommend.setCreateTime(LeDateUtils.formatYYYYMMDDHHMMSS(new Date(post.getCtime())));
//		} else if (tRecommend.getContentType() == RecommendContentType.MATCH) {
//			TComboEpisode episode = SopsApis.getTComboEpisodeById(tRecommend.getContentId(), callerParam);
//			if (episode == null) {
//				LOG.warn("this tRecommend has no episode ! contentId = {} ", tRecommend.getContentId());
//				return null;
//			}
//			recommend.setImageUrl(getEpisodeImageMap(episode));
//			recommend.setCreateTime(episode.getStartTime());
//		} else if (tRecommend.getContentType() == RecommendContentType.H5) {
//			recommend.setId(tRecommend.getId());
//			Map<String, String> returnMap = Maps.newHashMap();
//			//添加400_225的图
//			returnMap.put(Constants.VIDEO_NEWS_IMAGE_169, tRecommend.getImageUrl());
//			//添加400_300的图
//			returnMap.put(Constants.VIDEO_NEWS_IMAGE_43, tRecommend.getImageUrl());
//			recommend.setImageUrl(returnMap);
//			recommend.setH5(tRecommend.getContentUrl());
//			recommend.setCreateTime(tRecommend.getCreateAt());
//		}
//		return recommend;
//	}

	/**
	 * 新闻获取图片信息
	 *
	 * @param episode
	 * @return
	 */
	private Map<String, String> getEpisodeImageMap(TComboEpisode episode) {
		Map<String, String> returnMap = Maps.newHashMap();
		List<TLiveStream> streams = episode.getStreams();
		//如果有直播流取第一路流的图片
		if (CollectionUtils.isNotEmpty(streams)) {
			TLiveStream stream = streams.get(0);
			Map<String, String> images = stream.getImages();
			if (StringUtils.isNotBlank(images.get("pic2_960_540"))) {
				//添加960_540的图
				returnMap.put(Constants.VIDEO_NEWS_IMAGE_BIG_169, images.get("pic2_960_540"));
			}
			if (StringUtils.isNotBlank(images.get("pic3_400_225"))) {
				//添加400_225的图
				returnMap.put(Constants.VIDEO_NEWS_IMAGE_169, images.get("pic3_400_225"));
			}
			if (StringUtils.isNotBlank(images.get("pic3_400_300"))) {
				//添加400_300的图
				returnMap.put(Constants.VIDEO_NEWS_IMAGE_43, images.get("pic3_400_300"));
			}
		} else if (StringUtils.isNotBlank(episode.getImageUrl())) {
			String imageUrl = episode.getImageUrl();
			if (StringUtils.isNotBlank(imageUrl) && (imageUrl.endsWith(JPG_SUFFIX) || imageUrl.endsWith(PNG_SUFFIX))) {
				//添加960_540的图
				returnMap.put(Constants.VIDEO_NEWS_IMAGE_BIG_169, getFullImageUrl(Constants.IMAGE_JOINT_SIZE_169_960_540, imageUrl));
				//添加400_225的图
				returnMap.put(Constants.VIDEO_NEWS_IMAGE_169, getFullImageUrl(Constants.IMAGE_JOINT_SIZE_169_400_225, imageUrl));
				//添加400_300的图
				returnMap.put(Constants.VIDEO_NEWS_IMAGE_43, getFullImageUrl(Constants.IMAGE_JOINT_SIZE_169_400_300, imageUrl));
			}
		}
		return returnMap;
	}

//    /**
//     * 为焦点图赋值
//     *
//     * @param blockContent
//     * @return
//     */
//    public RecommendVo createRecommend(CmsApis.BlockContent blockContent,CallerParam caller) {
//        if (blockContent.getContent() == null || blockContent.getType() == null) {
//            return null;
//        }
//        RecommendVo recommend = new RecommendVo();
//        if (blockContent.getType() == CMS_BLOCK_VIDEO_TYPE || blockContent.getType() == CMS_BLOCK_NEWS_TYPE ) {
//            TNews tNews = null;
//			if (blockContent.getType() == CMS_BLOCK_VIDEO_TYPE) {
//				tNews = SopsApis.getTNewsByVid(LeNumberUtils.toLong(blockContent.getContent()),caller);
//			} else if (blockContent.getType() == CMS_BLOCK_NEWS_TYPE) {
//				tNews = SopsApis.getTNewsById(LeNumberUtils.toLong(blockContent.getContent()),caller);
//			}
//			if (tNews == null) {
//                LOG.error("this cms block content video has no news ! videoId = {}", blockContent.getId());
//                return null;
//            }
//			recommend.setBlockId(blockContent.getId());
//            recommend.setId(tNews.getId());
//            recommend.setName(StringUtils.isNotBlank(blockContent.getTitle()) ? blockContent.getTitle() : tNews.getName());
//			if (blockContent.getType() == CMS_BLOCK_VIDEO_TYPE) {
//				recommend.setContentType(0);
//			} else if (blockContent.getType() == CMS_BLOCK_NEWS_TYPE) {
//				recommend.setContentType(2);
//			}
//			addFocusPic(blockContent, recommend);
//            recommend.setNewsType(tNews.getType().ordinal());
//            recommend.setCreateTime(tNews.getPublishAt());
//			recommend.setIsPay(tNews.getIsPay());
//        } else if (blockContent.getType() == CMS_BLOCK_TOPIC_TYPE) {
//            TopicInfo topicInfo = topicService.get(LeNumberUtils.toInt(blockContent.getContent()));
//            if (topicInfo == null) {
//                LOG.warn("this recommend topic is null ! contentId = {} ", topicInfo.getId());
//                return null;
//            }
//			recommend.setBlockId(blockContent.getId());
//            recommend.setId(topicInfo.getId());
//            recommend.setName(StringUtils.isNotBlank(blockContent.getTitle()) ? blockContent.getTitle() : topicInfo.getTitle());
//            recommend.setContentType(1);
//            recommend.setCreateTime(topicInfo.getCreateTime());
//			addFocusPic(blockContent, recommend);
//            List<TopicInfo.VideoInfo> sVideos = topicInfo.getVideos();
//            if (CollectionUtils.isNotEmpty(sVideos)) {
//                for (TopicInfo.VideoInfo sVideo : sVideos) {
//                    RecommendVo.VideoInfo video = recommend.new VideoInfo();
//                    video.setId(sVideo.getId());
//                    video.setName(sVideo.getName());
//                    video.setImageUrl(sVideo.getImageUrl());
//                    recommend.getVideos().add(video);
//                    if (recommend.getVideos().size() >= 3) {//专题的话返回3个视频即可
//                        break;
//                    }
//                }
//            } else {
//                LOG.warn("this topic has no videos ! contentId = {} ", topicInfo.getId());
//            }
//		} else if (blockContent.getType() == CMS_BLOCK_H5_TYPE) {
//			recommend.setBlockId(blockContent.getId());
//			recommend.setId(blockContent.getId());
//			recommend.setName(blockContent.getTitle());
//			recommend.setContentType(3);
//			recommend.setCreateTime(LeDateUtils.formatYYYYMMDDHHMMSS(new Date(blockContent.getCtime())));
//			addFocusPic(blockContent, recommend);
//			recommend.setH5(blockContent.getContent());
//		} else if (blockContent.getType() == CMS_BLOCK_CAMP_TYPE) {
//			recommend.setBlockId(blockContent.getId());
//			recommend.setId(blockContent.getId());
//			recommend.setName(blockContent.getTitle());
//			recommend.setContentType(5);
//			recommend.setCreateTime(LeDateUtils.formatYYYYMMDDHHMMSS(new Date(blockContent.getCtime())));
//			addFocusPic(blockContent, recommend);
//			recommend.setCampId(blockContent.getContent());
//		} else if (blockContent.getType() == CMS_BLOCK_POST_TYPE) {
//			recommend.setBlockId(blockContent.getId());
//			recommend.setId(blockContent.getId());
//			recommend.setName(blockContent.getTitle());
//			recommend.setContentType(4);
//			recommend.setCreateTime(LeDateUtils.formatYYYYMMDDHHMMSS(new Date(blockContent.getCtime())));
//			addFocusPic(blockContent, recommend);
//			recommend.setPostId(blockContent.getContent());
//		} else if (blockContent.getType() == CMS_BLOCK_EPISODE_TYPE) {
//			TComboEpisode episode = SopsApis.getTComboEpisodeById(LeNumberUtils.toLong(blockContent.getContent()), caller);
//			if (episode == null) {
//				LOG.warn("this tRecommend has no episode ! contentId = {} ", blockContent.getId());
//				return null;
//			}
//			recommend.setBlockId(blockContent.getId());
//			recommend.setId(LeNumberUtils.toLong(blockContent.getContent()));
//			recommend.setName(StringUtils.isNotBlank(blockContent.getTitle())?blockContent.getTitle():episode.getName());
//			addFocusPic(blockContent, recommend);
//			recommend.setCreateTime(episode.getStartTime());
//			recommend.setContentType(6);
//        } else {
//            return null;
//        }
//        return recommend;
//    }
//
//	private void addFocusPic(CmsApis.BlockContent blockContent, RecommendVo recommend) {
//		Map<String, String> imageMap = Maps.newHashMap();
//		imageMap.put("750*350", StringUtils.isNotBlank(blockContent.getMobilePic()) ? blockContent.getMobilePic() : StringUtils.EMPTY);
//		if (StringUtils.isNotBlank(blockContent.getPadPic())) {
//			imageMap.put("pcPic", blockContent.getPic1());
//		}
//		if (StringUtils.isNotBlank(blockContent.getPadPic())) {
//			imageMap.put("padPic", blockContent.getPadPic());
//		}
//		if (StringUtils.isNotBlank(blockContent.getTvPic())) {
//			imageMap.put("tvPic", blockContent.getTvPic());
//		}
//		recommend.setImageUrl(imageMap);
//	}

    /**
     * 为news赋值
     *
     * @param tNews
     * @return
     */
    public NewsVo createNewsVo(TNews tNews) {
        NewsVo newsVo = new NewsVo();
        newsVo.setName(tNews.getName());
        newsVo.setCreateTime(tNews.getPublishAt());
        newsVo.setId(tNews.getId());
        newsVo.setCommentId(tNews.getCommentId());
        newsVo.setNewsType(tNews.getType().ordinal());
        newsVo.setDesc(tNews.getDesc());
        newsVo.setRecommend(0);
        newsVo.setDuration(tNews.getDuration() == 0 ? null : tNews.getDuration() + "");
        newsVo.setImageUrl(getImageMap(tNews));
        newsVo.setGameFTypeName(null == tNews.getChannel() ? "" : tNews.getChannel().getName());
        newsVo.setTags(tNews.getTags());
        if (tNews.getType() == NewsType.VIDEO) {
            newsVo.setVid(tNews.getVid());
        }
		if (tNews.getType() == NewsType.IMAGE_ALBUM) {
			newsVo.setImagesCount(tNews.getImagesSize());
		}
		newsVo.setIsPay(tNews.getIsPay());
		return newsVo;
    }


    /**
     * 新闻获取图片信息
     *
     * @param tNews
     * @return
     */
    public static Map<String, String> getImageMap(TNews tNews) {
        Map<String, String> returnMap = Maps.newHashMap();
		//视频新闻: 图片已视频上的为准
        if (tNews.getType() == NewsType.VIDEO) {
            Map<String, String> picAll = tNews.getVideoImages();
            if (picAll != null) {
                if (picAll.get(Constants.VIDEO_NEWS_IMAGE_SCALE_43) != null) {
                    returnMap.put(Constants.VIDEO_NEWS_IMAGE_43, picAll.get(Constants.VIDEO_NEWS_IMAGE_SCALE_43));
                } else {
                    returnMap.put(Constants.VIDEO_NEWS_IMAGE_43, "");
                }
                if (picAll.get(Constants.VIDEO_NEWS_IMAGE_SCALE_169) != null) {//400*225
                    returnMap.put(Constants.VIDEO_NEWS_IMAGE_169, picAll.get(Constants.VIDEO_NEWS_IMAGE_SCALE_169));
                } else {
                    returnMap.put(Constants.VIDEO_NEWS_IMAGE_169, "");
                }
                if (picAll.get(Constants.VIDEO_NEWS_IMAGE_SCALE_43) != null) {//160*120
                    returnMap.put(Constants.VIDEO_NEWS_IMAGE_160_120, picAll.get(Constants.VIDEO_NEWS_IMAGE_SCALE_43));
                } else {
                    returnMap.put(Constants.VIDEO_NEWS_IMAGE_160_120, "");
                }
                if (picAll.get(Constants.VIDEO_NEWS_IMAGE_SCALE_43) != null) {//180*135
                    returnMap.put(Constants.VIDEO_NEWS_IMAGE_180_135, picAll.get(Constants.VIDEO_NEWS_IMAGE_SCALE_43));
                } else {
                    returnMap.put(Constants.VIDEO_NEWS_IMAGE_180_135, "");
                }
                if (picAll.get(Constants.VIDEO_NEWS_IMAGE_SCALE_43) != null) {//180*135
                    returnMap.put(Constants.VIDEO_NEWS_IMAGE_200_150, picAll.get(Constants.VIDEO_NEWS_IMAGE_SCALE_43));
                } else {
                    returnMap.put(Constants.VIDEO_NEWS_IMAGE_200_150, "");
                }
                if (tNews.isHasBigImage() && StringUtils.isNotBlank(picAll.get(picAll.get(Constants.VIDEO_NEWS_IMAGE_SCALE_169)))) {//960*540
                    returnMap.put(Constants.VIDEO_NEWS_IMAGE_BIG_169, picAll.get(Constants.VIDEO_NEWS_IMAGE_SCALE_169));
                } else {
                    returnMap.put(Constants.VIDEO_NEWS_IMAGE_BIG_169, "");
                }
            }
		//图文新闻和富文本新闻: 列表图已封面图为准(拼接: 960_540,400_225,400_300 3个尺寸的)
        } else if (tNews.getType() == NewsType.IMAGE_TEXT || tNews.getType() == NewsType.RICHTEXT) {
			Map<String, String> coverImageMap = tNews.getCoverImage();
			if (coverImageMap != null) {
				String coverImage169 = coverImageMap.get("169");
				String coverImage43 = coverImageMap.get("43");
				if (StringUtils.isNotBlank(coverImage169) && (coverImage169.endsWith(JPG_SUFFIX) || coverImage169.endsWith(PNG_SUFFIX))) {
					//添加960_540的图
					returnMap.put(Constants.VIDEO_NEWS_IMAGE_BIG_169, getFullImageUrl(Constants.IMAGE_JOINT_SIZE_169_960_540, coverImage169));
					//添加400_225的图
					returnMap.put(Constants.VIDEO_NEWS_IMAGE_169, getFullImageUrl(Constants.IMAGE_JOINT_SIZE_169_400_225, coverImage169));
				}
				if (StringUtils.isNotBlank(coverImage43) && (coverImage43.endsWith(JPG_SUFFIX) || coverImage43.endsWith(PNG_SUFFIX))) {
					//添加400_300的图
					returnMap.put(Constants.VIDEO_NEWS_IMAGE_43, getFullImageUrl(Constants.IMAGE_JOINT_SIZE_169_400_300, coverImage43));
				}
			}
		//图集新闻: 以图集的前3张图片为准,拼接3个并列的400_300的图片
        } else if (tNews.getType() == NewsType.IMAGE_ALBUM) {
			List<TNewsImage> images = tNews.getImages();
			if (CollectionUtils.isEmpty(images)) {
				returnMap.put(Constants.VIDEO_NEWS_IMAGE_43, StringUtils.EMPTY);
			} else {
				//正常多于等于3张图片的处理
				if (images.size() >= 3) {
					int size = 1;
					for (TNewsImage tNewsImage : images) {
						if (tNewsImage.isCover()) {
							if (size > 3) {
								break;
							}
							if (size == 1) {
								//添加400_300_1的图
								returnMap.put(Constants.IMAGES_NEWS_IMAGE_43_1, getFullImageUrl(Constants.IMAGE_JOINT_SIZE_169_400_300, tNewsImage.getImageUrl()));
								returnMap.put(Constants.IMAGES_NEWS_IMAGE_169_1, getFullImageUrl(Constants.IMAGE_JOINT_SIZE_169_400_225, tNewsImage.getImageUrl()));
								//添加400_300的图 - 客户端相关新闻用一张400*300的展示图
								returnMap.put(Constants.VIDEO_NEWS_IMAGE_43, getFullImageUrl(Constants.IMAGE_JOINT_SIZE_169_400_300, tNewsImage.getImageUrl()));
								returnMap.put(Constants.VIDEO_NEWS_IMAGE_169, getFullImageUrl(Constants.IMAGE_JOINT_SIZE_169_400_225, tNewsImage.getImageUrl()));
							} else if (size == 2) {
								//添加400_300_2的图
								returnMap.put(Constants.IMAGES_NEWS_IMAGE_43_2, getFullImageUrl(Constants.IMAGE_JOINT_SIZE_169_400_300, tNewsImage.getImageUrl()));
								returnMap.put(Constants.IMAGES_NEWS_IMAGE_169_2, getFullImageUrl(Constants.IMAGE_JOINT_SIZE_169_400_225, tNewsImage.getImageUrl()));
							} else if (size == 3) {
								//添加400_300_3的图
								returnMap.put(Constants.IMAGES_NEWS_IMAGE_43_3, getFullImageUrl(Constants.IMAGE_JOINT_SIZE_169_400_300, tNewsImage.getImageUrl()));
								returnMap.put(Constants.IMAGES_NEWS_IMAGE_169_3, getFullImageUrl(Constants.IMAGE_JOINT_SIZE_169_400_225, tNewsImage.getImageUrl()));
							}
							size ++;
						}
					}
					//如果封面图不够3张则用前三张当封面
					if (size < 3) {
						//添加400_300_1的图
						returnMap.put(Constants.IMAGES_NEWS_IMAGE_43_1, getFullImageUrl(Constants.IMAGE_JOINT_SIZE_169_400_300, images.get(0).getImageUrl()));
						//添加400_300的图 - 客户端新闻底部的相关新闻用一张400*300的展示图
						returnMap.put(Constants.VIDEO_NEWS_IMAGE_43, getFullImageUrl(Constants.IMAGE_JOINT_SIZE_169_400_300, images.get(0).getImageUrl()));
						//添加400_300_2的图
						returnMap.put(Constants.IMAGES_NEWS_IMAGE_43_2, getFullImageUrl(Constants.IMAGE_JOINT_SIZE_169_400_300, images.get(1).getImageUrl()));
						//添加400_300_3的图
						returnMap.put(Constants.IMAGES_NEWS_IMAGE_43_3, getFullImageUrl(Constants.IMAGE_JOINT_SIZE_169_400_300, images.get(2).getImageUrl()));
					}
				} else {//如果返回图片小于三张,则给出400_225和400_300的图
					//添加16:9 400_225的图
					returnMap.put(Constants.VIDEO_NEWS_IMAGE_169, getFullImageUrl(Constants.IMAGE_JOINT_SIZE_169_400_225, images.get(0).getImageUrl()));
					//添加400_300的图
					returnMap.put(Constants.VIDEO_NEWS_IMAGE_43, getFullImageUrl(Constants.IMAGE_JOINT_SIZE_169_400_300, images.get(0).getImageUrl()));
				}
			}
		}
		return returnMap;
    }

	private static String getFullImageUrl(String size, String originalUrl) {
		StringBuffer sb = new StringBuffer(originalUrl.substring(0, originalUrl.length() - 4));
		sb.append(size).append(originalUrl.substring(originalUrl.length() - 4, originalUrl.length()));
		return sb.toString();
	}

    public DetailNews createDetailNews(TNews tNews) {
        DetailNews detailNews = new DetailNews();
        detailNews.setName(tNews.getName());
        detailNews.setTags(tNews.getTags());
        detailNews.setCreateTime(tNews.getPublishAt());
        detailNews.setId(tNews.getId());
		detailNews.setIsPay(tNews.getIsPay());
        detailNews.setCommentId(tNews.getCommentId());
        detailNews.setNewsType(tNews.getType().ordinal());
        detailNews.setDesc(tNews.getDesc());
        detailNews.setAuthor(tNews.getAuthor());
        detailNews.setRecommend(0);
        detailNews.setSource(StringUtils.isBlank(tNews.getSource()) ? StringUtils.EMPTY : tNews.getSource());
        detailNews.setDuration(tNews.getDuration() == 0 ? null : tNews.getDuration() + "");
        detailNews.setImageUrl(getImageMap(tNews));
        if (tNews.getType() == NewsType.VIDEO) {
            detailNews.setVid(tNews.getVid());
        }
        detailNews.setContent(tNews.getContent());
        List<TNewsImage> images = tNews.getImages();
        if (CollectionUtils.isNotEmpty(images)) {
            for (TNewsImage tImage : images) {
                DetailNews.NewsImage image = detailNews.new NewsImage();
                image.setId(tImage.getId());
                image.setName(tImage.getName());
                image.setDesc(tImage.getDesc());
                image.setImage(tImage.getImageUrl());
                image.setCover(tImage.isCover() ? 1 : 0);
                image.setOrder(tImage.getShowOrder());
                detailNews.getImages().add(image);
            }
            if (CollectionUtils.isNotEmpty(detailNews.getImages())) {
                Collections.sort(detailNews.getImages(), new Comparator<DetailNews.NewsImage>() {
                    @Override
                    public int compare(DetailNews.NewsImage o1, DetailNews.NewsImage o2) {
                        return o1.getOrder() - o2.getOrder();
                    }
                });
            }
        }
        return detailNews;
    }


	public RecommendVo createRecommendByNews(TNews tNews) {
		RecommendVo recommend = new RecommendVo();
		recommend.setId(tNews.getId());
		recommend.setName(tNews.getName());
		recommend.setContentType(RecommendContentType.NEWS.ordinal());
		recommend.setImageUrl(getImageMap(tNews));
		recommend.setNewsType(tNews.getType().ordinal());
		recommend.setCreateTime(tNews.getPublishAt());
		recommend.setIsPay(tNews.getIsPay());
		if (tNews.getType() == NewsType.IMAGE_ALBUM) {
			recommend.setImagesCount(tNews.getImagesSize());
		}
		return recommend;
	}
}
