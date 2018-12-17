package com.lesports.qmt.web.helper;

import com.google.common.base.Preconditions;
import com.lesports.LeConstants;
import com.lesports.api.common.CallerParam;
import com.lesports.api.common.CountryCode;
import com.lesports.api.common.Platform;
import com.lesports.qmt.QmtConstants;
import com.lesports.qmt.config.client.QmtConfigApis;
import com.lesports.qmt.sbc.api.common.EpisodeType;
import com.lesports.qmt.sbc.api.common.NewsType;
import com.lesports.qmt.sbc.api.common.VideoContentType;
import com.lesports.qmt.sbc.api.dto.*;
import com.lesports.qmt.sbc.client.QmtSbcApis;
import com.lesports.qmt.sbc.client.QmtSbcEpisodeApis;
import com.lesports.qmt.web.model.Share;
import com.lesports.utils.*;
import com.lesports.utils.math.LeNumberUtils;
import com.lesports.utils.pojo.LiveChannels;
import com.lesports.utils.pojo.Post;
import com.lesports.utils.pojo.SubjectData;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.MessageFormat;
import java.util.*;

/**
 * User: ellios
 * Time: 16-3-29 : 下午2:30
 */
@Component("shareHelper")
public class ShareHelper {

    private static final Logger LOG = LoggerFactory.getLogger(ShareHelper.class);

	//大陆pc域名
	private static final String CN_PC_DOMAIN_NAME = "http://sports.letv.com";
	//大陆m站域名
	private static final String CN_M_DOMAIN_NAME = "http://m.lesports.com";

	//香港pc域名
	private static final String HK_PC_DOMAIN_NAME = "http://hk.lesports.com";
	//香港m站域名
	private static final String HK_M_DOMAIN_NAME = "http://hk.m.lesports.com";

    //图文直播默认图
    private static final String SHARE_DEFAULT_PICURL_PC_1_1 = "http://i1.letvimg.com/lc04_iscms/201608/15/15/04/0406625fd00a406280524becdb678357.png"; //200*200
    private static final String SHARE_DEFAULT_PICURL_PC_4_3 = "http://i0.letvimg.com/lc02_iscms/201608/15/15/04/c7b19dcdfcc143ae87d9344efecd7004.png"; //200*150
    private static final String SHARE_DEFAULT_PICURL_APP_1_1 = "http://i2.letvimg.com/lc04_iscms/201608/06/09/27/5d87e29aaf214ef7a6f26afda2e55e67.png"; //100*100

    //分享PC地址
    private static final MessageFormat URL_PC_MATCH = new MessageFormat("http://sports.letv.com/match/{0}.html");
    private static final MessageFormat URL_PC_MATCH_HASH = new MessageFormat("http://sports.letv.com/match/{0}.html#live/{1}");
    private static final MessageFormat URL_PC_VIDEO = new MessageFormat("http://sports.letv.com/video/{0}.html");
    private static final MessageFormat URL_PC_LIVE = new MessageFormat("http://sports.letv.com/live/{0}.html");
    private static final MessageFormat URL_PC_TEXT_MATCH = new MessageFormat("http://sports.letv.com/match/{0}/tlive.html");
    private static final MessageFormat URL_PC_NEWS = new MessageFormat("http://sports.letv.com/news/{0}.html");

    //分享M站地址
    private static final MessageFormat URL_M_MATCH = new MessageFormat("http://m.lesports.com/match/{0}.html?");
    private static final MessageFormat URL_M_MATCH_HASH = new MessageFormat("http://m.lesports.com/match/{0}.html?#live/{1}");
    private static final MessageFormat URL_M_CAROUSEL = new MessageFormat("http://m.letv.com/live/play_{0}.html");
    private static final MessageFormat URL_M_VIDEO = new MessageFormat("http://m.lesports.com/video/{0}.html");
    private static final MessageFormat URL_M_LIVE = new MessageFormat("http://m.lesports.com/live/{0}.html?");
    private static final MessageFormat URL_M_NEWS = new MessageFormat("http://m.lesports.com/news/{0}.html");
    private static final MessageFormat URL_M_TOPIC = new MessageFormat("http://m.letv.com/kzt/4_{0}");
    private static final MessageFormat URL_M_POST = new MessageFormat("http://m.lesports.com/z/post/{0}.html");//帖子m站地址
	private static final MessageFormat URL_M_TOPIC_LIST = new MessageFormat("http://m.lesports.com/topic/s/{0}/");
	private static final MessageFormat URL_M_HTOPIC_LIST = new MessageFormat("http://m.lesports.com/topic/h/{0}/");

    public static final String MOBILE_PLATFORM = "420003";
    //拼接的16:9 160_120
    public static final String IMAGE_JOINT_SIZE_169_400_300 = "/169_400_300";
    public static final String IMAGE_SIZE_160_120 = "160*120";
    public static final String IMAGE_SIZE_400_300 = "400*300";

    @Resource
    private MessageSource messageSource;


    //分享描述
    private static final String DESC_MATCH = "乐视体育";
    private static final String DESC_ALBUM = "乐视体育制造";
    private static final String DESC_VIDEO = "乐视体育";
    private static final String DESC_NEWS = "乐视体育";
    private static final String DESC_TOPIC = "乐视体育";
    private static final String DESC_CAROUSEL = "乐视体育轮播台";
    private static final String DESC_POST = "乐视体育";

    //帖子默认分享标题
    private static final String DESC_TITLE = "这么好看的帖子，只在乐视体育主场里!";

    public Share getShareByType(String entityId, Share.ShareType shareType, CallerParam caller){
        Locale locale = org.springframework.util.StringUtils.parseLocaleString(caller.getLanguage().name());
        if(shareType == Share.ShareType.MATCH){
            TComboEpisode episode = QmtSbcEpisodeApis.getTComboEpisodeById(LeNumberUtils.toLong(entityId), caller);
            return getShare3Episode4Match(episode, caller, shareType, locale);
        }else if(shareType == Share.ShareType.ALBUM){
            TComboEpisode episode = QmtSbcEpisodeApis.getTComboEpisodeById(LeNumberUtils.toLong(entityId), caller);
            return getShare3Episode4Album(episode, caller, shareType, locale);
        }else if(shareType == Share.ShareType.VIDEO){
            TVideo video = QmtSbcApis.getTVideoByLeecoVid(LeNumberUtils.toLong(entityId), caller);
            return getShare3Video(video, caller, shareType, locale);
        }else if(shareType == Share.ShareType.LIVE){
            TComboEpisode episode = QmtSbcEpisodeApis.getEpisodeByLiveId(entityId, caller);
            return getShare3Episode4Match(episode, caller, shareType, locale);
        }else if(shareType == Share.ShareType.CAROUSEL){
            return getShare3Carousel(entityId, caller, shareType, locale);
        }else if(shareType == Share.ShareType.NEWS || shareType == Share.ShareType.RICHTEXT ||
                shareType == Share.ShareType.IMAGE_ALBUM){
            TNews news = QmtSbcApis.getTNewsById(LeNumberUtils.toLong(entityId), caller);
            return getShare3News(news, caller, shareType, locale);
        }else if(shareType == Share.ShareType.TOPIC){
            return getShare3SubjectId(entityId, caller, shareType, locale);
        }else if(shareType == Share.ShareType.POST){
            return getShare3Post(entityId, caller, shareType, locale);
        }else if(shareType == Share.ShareType.TOPIC_LIST){
            return getShare3TopicList(entityId, caller, shareType, locale);
        }else if(shareType == Share.ShareType.RECORD || shareType == Share.ShareType.RELATED ||
                shareType == Share.ShareType.HIGHLIGHTS){
            TVideo video = QmtSbcApis.getTVideoById(LeNumberUtils.toLong(entityId), caller);
            return getShare5Anchor3Video(video, caller, shareType, locale);
        }else{
            return createDummyShare(caller, shareType);
        }
    }


    public Share getShare3Episode4Match(TComboEpisode episode, CallerParam caller, Share.ShareType shareType, Locale locale) {
        Platform platform = QmtConfigApis.getPlatformOfCaller(caller.getCallerId());
        if (platform == null) {
            LOG.warn("fail to getShare3Episode4Match. platform is null. callerId : {}", caller.getCallerId());
            return null;
        }
        Share share = createDummyShare(caller, shareType);
        share.setId(episode.getId());
        String id = String.valueOf(episode.getId());
        boolean liveHash = false;
        if (shareType == Share.ShareType.LIVE) {
            liveHash = true;
        }
        String liveId = "";
        //分享直播图
        List<TLiveStream> streams = episode.getStreams();
        if (CollectionUtils.isNotEmpty(streams)) {
            Iterator it = streams.iterator();
            while (it.hasNext()) {
                TLiveStream liveStream = (TLiveStream) it.next();
                if (null != liveStream) {
                    //如果有直播图的话则分享图为视频图
                    String pic = "";
                    if (MapUtils.isNotEmpty(liveStream.getImages())) {
                        pic = liveStream.getImages().get(QmtConstants.LIVE_STREAM_IMAGE_SIZE_160_90);
                        if (StringUtils.isNotBlank(pic)) {
                            share.setPic(pic);
                            share.setPic43(pic);
                        }
                    }
					liveId = liveStream.getLiveId();
                    break;
                }
            }
        }
        share.setTitle(StringUtils.isNotBlank(episode.getShareName()) ? episode.getShareName() : episode.getName());
        share.setDesc(messageSource.getMessage("le.share.match.desc", null, DESC_MATCH, locale));

        if (episode.getType() == EpisodeType.MATCH) {
            if (liveHash) {
                //比赛m站分享
                if (platform == Platform.MOBILE || platform == Platform.PAD) {
                    share.setUrl(URL_M_MATCH_HASH.format(new Object[]{String.valueOf(episode.getMid()), liveId}));
                    //比赛pc分享
                } else if (platform == Platform.PC) {
                    share.setUrl(URL_PC_MATCH_HASH.format(new Object[]{String.valueOf(episode.getMid()), liveId}));
                }
            } else {
                //比赛m站分享
                if (platform == Platform.MOBILE || platform == Platform.PAD) {
                    share.setUrl(URL_M_MATCH.format(new Object[]{String.valueOf(episode.getMid())}));
                    //比赛pc分享
                } else if (platform == Platform.PC) {
                    if (CollectionUtils.isNotEmpty(episode.getTextlives()) && CollectionUtils.isEmpty(episode.getStreams())) {//无视频直播,有图文直播则为图文地址
                        share.setUrl(URL_PC_TEXT_MATCH.format(new Object[]{String.valueOf(episode.getMid())}));
                    } else {
                        share.setUrl(URL_PC_MATCH.format(new Object[]{String.valueOf(episode.getMid())}));
                    }
                }
            }
        } else if (episode.getType() == EpisodeType.PROGRAM) {
            //直播m站分享
            if (platform == Platform.MOBILE || platform == Platform.PAD) {
                share.setUrl(URL_M_LIVE.format(new Object[]{liveId}));
                //直播pc分享
            } else if (platform == Platform.PC) {
                share.setUrl(URL_PC_LIVE.format(new Object[]{liveId}));
            }
        }
        return modifyDomainNameByCaller(share, caller);
    }

    public Share getShare3Episode4Album(TComboEpisode episode, CallerParam caller, Share.ShareType shareType, Locale locale) {
        Platform platform = QmtConfigApis.getPlatformOfCaller(caller.getCallerId());
        if (platform == null) {
            LOG.warn("fail to getShare3Episode. platform is null. callerId : {}", caller.getCallerId());
            return null;
        }
        Share share = createDummyShare(caller, shareType);
        share.setId(episode.getId());
        String id = String.valueOf(episode.getId());
        //APP自制节目分享 - 分享到点播页
        if (episode != null) {
            List<TSimpleVideo> videos = episode.getVideos();
            if (CollectionUtils.isNotEmpty(videos)) {
                for (TSimpleVideo video : videos) {
                    if (video.getType() == VideoContentType.RECORD) {
                        TVideo albumVideo = QmtSbcApis.getTVideoById(video.getVid(), caller);
                        if (null != albumVideo) {
                            share = getShare3Video(albumVideo, caller, shareType, locale);
                            if (StringUtils.isNotEmpty(episode.getShareName())) {
                                share.setTitle(episode.getShareName());
                            }
                            break;
                        }
                    }
                }
            }
            share.setDesc(messageSource.getMessage("le.share.album.desc", null, DESC_ALBUM, locale));

        }
        return modifyDomainNameByCaller(share, caller);
    }

    /**
     * 获取视频上的分享
     *
     * @param video
     * @param caller
     * @param shareType
     * @return
     */
    public Share getShare3Video(TVideo video, CallerParam caller, Share.ShareType shareType, Locale locale) {
        Platform platform = QmtConfigApis.getPlatformOfCaller(caller.getCallerId());
        if (platform == null) {
            LOG.warn("fail to getShare3Episode. platform is null. callerId : {}", caller.getCallerId());
            return null;
        }
        Share share = createDummyShare(caller, shareType);
        share.setId(video.getId());
        String id = String.valueOf(video.getId());
        String pic = "";
        //有可能新的数据同步过来没有任何图片,此处报空指针
        if (MapUtils.isNotEmpty(video.getImages())) {
            pic = video.getImages().get("43");
            if (StringUtils.isNotBlank(pic)) {
                share.setPic(pic);
                share.setPic43(pic);
            }
        }
        if (StringUtils.isBlank(share.getTitle())) {
            share.setTitle(video.getName());
        }
        share.setDesc(messageSource.getMessage("le.share.video.desc", null, DESC_VIDEO, locale));

        //比赛m站分享
        if (platform == Platform.MOBILE || platform == Platform.PAD) {
            share.setUrl(URL_M_VIDEO.format(new Object[]{String.valueOf(id)}));
            //比赛pc分享
        } else if (platform == Platform.PC) {
            share.setUrl(URL_PC_VIDEO.format(new Object[]{String.valueOf(id)}));
        }
        return modifyDomainNameByCaller(share, caller);
    }

    /**
     * 获取视频上的带描点的分享
     *
     * @param video
     * @param caller
     * @param shareType
     * @return
     */
    public Share getShare5Anchor3Video(TVideo video, CallerParam caller, Share.ShareType shareType, Locale locale) {
        Share share = createDummyShare(caller, shareType);
        share.setId(video.getId());
        Set<Long> episodeIds = video.getEids();
        if (CollectionUtils.isNotEmpty(episodeIds)) {
            for (Long episodeId : episodeIds) {
                TComboEpisode episode = QmtSbcEpisodeApis.getTComboEpisodeById(episodeId, caller);
                if (episode != null) {
                    if (episode.getType() == EpisodeType.MATCH) {
                        share = getShare3Episode4Match(episode, caller, shareType, locale);
                        if (StringUtils.isNotBlank(share.getUrl())) {
                            share.setUrl(appendAnchor(share.getUrl(), shareType, String.valueOf(video.getId())));
                        }
                    } else if (episode.getType() == EpisodeType.PROGRAM) {
                        share = getShare3Episode4Album(episode, caller, shareType, locale);
                    }
                }
            }
        }
        return modifyDomainNameByCaller(share, caller);
    }

    /**
     * 通过轮播台获取分享信息
     *
     * @param channelId
     * @param caller
     * @param shareType
     * @return
     */
    public Share getShare3Carousel(String channelId, CallerParam caller, Share.ShareType shareType, Locale locale) {
        Share share = createDummyShare(caller, shareType);
        share.setId(LeNumberUtils.toLong(channelId));
        //todo 轮播太id是写死的?
        List<LiveChannels.LiveChannel> channels = CarouselApis.getChannels(1031, caller);
        if (CollectionUtils.isNotEmpty(channels)) {
            for (LiveChannels.LiveChannel channel : channels) {
                if (StringUtils.equals(channel.getChannelId().toString(), channelId)) {
                    share.setDesc(messageSource.getMessage("le.share.carousel.desc", null, DESC_CAROUSEL, locale));

                    share.setPic(StringUtils.EMPTY);
                    share.setTitle(channel.getChannelName());
                    share.setUrl(URL_M_CAROUSEL.format(new Object[]{channel.getChannelEname()}));
                    break;
                }
            }
        }
        return share;
    }

    /**
     * 通过专题Id获取分享信息
     *
     * @param subjectId
     * @param caller
     * @param shareType
     * @return
     */
    public Share getShare3SubjectId(String subjectId, CallerParam caller, Share.ShareType shareType, Locale locale) {
        Share share = createDummyShare(caller, shareType);
        share.setId(LeNumberUtils.toLong(subjectId));
        //获取专题详情
        SubjectData.SubjectInfo info = CmsSubjectApis.getSubject(LeNumberUtils.toLong(subjectId));
        if (null != info) {
            String shareImg = info.getShareImg();
            if (StringUtils.isNotBlank(shareImg)) {
                share.setPic(shareImg);
            }
            share.setTitle(StringUtils.isNotBlank(info.getShareTitle()) ? info.getShareTitle() : info.getName());
            share.setDesc(messageSource.getMessage("le.share.topic.desc", null, DESC_TOPIC, locale));
            //如专题详情中包含手机端,则去拼接m站的url
            if (info.getPlayPlatform().contains(MOBILE_PLATFORM)) {
                share.setUrl(URL_M_TOPIC.format(new Object[]{info.getPubName()}));
            }
        }
        return share;
    }

    /**
     * 获取新闻的分享
     *
     * @param news
     * @param caller
     * @param shareType
     * @return
     */
    public Share getShare3News(TNews news, CallerParam caller, Share.ShareType shareType, Locale locale) {
		Platform platform = QmtConfigApis.getPlatformOfCaller(caller.getCallerId());
        Preconditions.checkNotNull(news);
        Share share = createDummyShare(caller, shareType);
        share.setId(news.getId());
        if (null != news) {

            share.setTitle(StringUtils.isNotBlank(news.getShareName()) ? news.getShareName() : news.getName());
            share.setDesc(messageSource.getMessage("le.share.news.desc", null, DESC_NEWS, locale));
//app之前分享用h5页面,7.20日改为m站 防止回滚备用
//            share.setUrl(URL_PC_NEWS.format(new Object[]{String.valueOf(news.getId())}));
//            if(caller.getCallerId() == LeConstants.LESPORTS_MSITE_CALLER_CODE){
//                share.setUrl(URL_M_NEWS.format(new Object[]{String.valueOf(news.getId())}));
//            } else if (caller.getCallerId() == LeConstants.LESPORTS_SUPER_MOBILE_APP_CALLER_CODE || caller.getCallerId() == LeConstants.LESPORTS_MOBILE_APP_CALLER_CODE) {
//				if (shareType == Share.ShareType.NEWS) {
//					share.setUrl(URL_M_NEWS.format(new Object[]{String.valueOf(news.getId())}));
//				} else if (shareType == Share.ShareType.RICHTEXT) {
//					share.setUrl(URL_M_RICHTEXT.format(new Object[]{String.valueOf(news.getId())}));
//				} else if (shareType == Share.ShareType.IMAGE_ALBUM) {
//					share.setUrl(URL_M_IMAGE_ALBUM.format(new Object[]{String.valueOf(news.getId())}));
//				}
//			}

			//比赛pc分享
			if (platform == Platform.PC) {
				share.setUrl(URL_PC_NEWS.format(new Object[]{String.valueOf(news.getId())}));
				//比赛m站分享
			} else {
				share.setUrl(URL_M_NEWS.format(new Object[]{String.valueOf(news.getId())}));
			}

			setSharePic5News(share, news);
        }

        return modifyDomainNameByCaller(share, caller);
    }

    //todo ugly code
    private static void setSharePic5News(Share share, TNews tNews) {
        share.setPic(SHARE_DEFAULT_PICURL_PC_1_1);
        share.setPic43(SHARE_DEFAULT_PICURL_PC_4_3);
        //视频新闻: 图片已视频上的为准
        if (tNews.getType() == NewsType.VIDEO) {
            //TODO VideoImages可以后头加上
            Map<String, String> picAll = tNews.getVideoImages();
            if (MapUtils.isNotEmpty(picAll)) {
                if (StringUtils.isNotBlank(picAll.get(IMAGE_SIZE_400_300))) {
                    share.setPic(picAll.get(IMAGE_SIZE_400_300));
                    share.setPic43(picAll.get(IMAGE_SIZE_400_300));
                }
            }
            //图文新闻和富文本新闻: 已封面图为准
        } else if (tNews.getType() == NewsType.IMAGE_TEXT || tNews.getType() == NewsType.RICHTEXT) {
            //TODO 封面图现在支持多比例所以是map
			if (MapUtils.isNotEmpty(tNews.getCoverImage())) {
				String coverImage = tNews.getCoverImage().get("169");
				if (StringUtils.isNotBlank(coverImage)) {
					share.setPic(getFullImageUrl(IMAGE_JOINT_SIZE_169_400_300, coverImage));
					share.setPic43(getFullImageUrl(IMAGE_JOINT_SIZE_169_400_300, coverImage));
				}
			}
            //图集新闻: 以图集的封面图第一张为准
        } else if (tNews.getType() == NewsType.IMAGE_ALBUM) {
            List<TNewsImage> images = tNews.getImages();
            if (CollectionUtils.isNotEmpty(images)) {
                share.setPic(getFullImageUrl(IMAGE_JOINT_SIZE_169_400_300, images.get(0).getImageUrl()));
                share.setPic43(getFullImageUrl(IMAGE_JOINT_SIZE_169_400_300, images.get(0).getImageUrl()));
            }
        }
    }

    //todo ugly code
    private static String getFullImageUrl(String size, String originalUrl) {
        StringBuffer sb = new StringBuffer(originalUrl.substring(0, originalUrl.length() - 4));
        sb.append(size).append(originalUrl.substring(originalUrl.length() - 4, originalUrl.length()));
        return sb.toString();
    }

	/**
	 * 获取专题的分享
	 *
	 * @param toplistName
	 * @param caller
	 * @param shareType
	 * @return
	 */
	public Share getShare3TopicList(String toplistName, CallerParam caller, Share.ShareType shareType, Locale locale) {
		Share share = createDummyShare(caller, shareType);
		TopicListApi.TopicListVo topicList = TopicListApi.getTopicList(toplistName);
		if (topicList == null) {
			return share;
		}
		share.setDesc(messageSource.getMessage("le.share.post.desc", null, DESC_POST, locale));
		String pic = topicList.getShareImg();
		if (StringUtils.isBlank(pic)) {
			pic = topicList.getFocusMPic();
		}
		share.setPic(pic);
		//title为title,如果没有,则为帖子content的前28个字,如果都没有,则为默认标题
		share.setTitle(StringUtils.isBlank(topicList.getShareTitle())?topicList.getName():topicList.getShareTitle());
		//小专题列表分享,pc用CMS的
		if (caller.getCallerId() == LeConstants.LESPORTS_MSITE_CALLER_CODE) {
			share.setUrl(URL_M_TOPIC_LIST.format(new Object[]{topicList.getPubName()}));
		}
		return share;
	}

	/**
	 * 获取专题的分享
	 *
	 * @param toplistName
	 * @param caller
	 * @param shareType
	 * @return
	 */
	public Share getShare3HtopicList(String toplistName, CallerParam caller, Share.ShareType shareType, Locale locale) {
		Share share = createDummyShare(caller, shareType);
		HtopicListApi.TopicListVo topicList = HtopicListApi.getTopicList(toplistName);
		if (topicList == null) {
			return share;
		}
		share.setDesc(messageSource.getMessage("le.share.post.desc", null, DESC_POST, locale));
		String pic = topicList.getShareImg();
		if (StringUtils.isBlank(pic)) {
			pic = topicList.getFocusMPic();
		}
		share.setPic(pic);
		//title为title,如果没有,则为帖子content的前28个字,如果都没有,则为默认标题
		share.setTitle(StringUtils.isBlank(topicList.getShareTitle())?topicList.getName():topicList.getShareTitle());
		//小专题列表分享,pc用CMS的
		if (caller.getCallerId() == LeConstants.LESPORTS_MSITE_CALLER_CODE) {
			share.setUrl(URL_M_HTOPIC_LIST.format(new Object[]{topicList.getPubName()}));
		}
		return share;
	}

    /**
     * 获取帖子的分享
     *
     * @param postId
     * @param caller
     * @param shareType
     * @return
     */
    public Share getShare3Post(String postId, CallerParam caller, Share.ShareType shareType, Locale locale) {
        Share share = createDummyShare(caller, shareType);
        share.setId(LeNumberUtils.toLong(postId));
        Post.TopicThread post = PostApis.postInfo(postId, caller.getCallerId());
        if (post == null) {
            return share;
        }
        share.setDesc(messageSource.getMessage("le.share.post.desc", null, DESC_POST, locale));

        //图片为帖子的第一张图
        List<Post.PictureInfos> pictureInfos = post.getMsgContent().getPictureInfos();
        if (CollectionUtils.isNotEmpty(pictureInfos)) {
            if (pictureInfos.get(0).getThumb() != null && StringUtils.isNotBlank(pictureInfos.get(0).getThumb().getUrl())) {
                share.setPic(pictureInfos.get(0).getThumb().getUrl());
            }
        }
        //title为title,如果没有,则为帖子content的前28个字,如果都没有,则为默认标题
        String title = post.getTitle();
        String content = post.getMsgContent().getContent();
        if (StringUtils.isBlank(title)) {
            if (StringUtils.isBlank(content)) {
                title = DESC_TITLE;
            } else {
                title = content;
            }
        }
        share.setTitle(title);
        share.setUrl(URL_M_POST.format(new Object[]{postId}));
        return share;
    }

    /**
     * 创建分享样例模板
     *
     * @param caller
     * @param shareType
     * @return
     */
    private Share createDummyShare(CallerParam caller, Share.ShareType shareType) {
        Share share = new Share();
        share.setShareType(shareType.ordinal());
        //如果分享失败的话,则需要把字段返回,客户端通过title和url来判断分享是否成功
        share.setTitle(StringUtils.EMPTY);
        share.setDesc(StringUtils.EMPTY);
        share.setUrl(StringUtils.EMPTY);
        //pc以及m站默认图为 lesports的图
        if (caller.getCallerId() == LeConstants.LESPORTS_PC_CALLER_CODE ||
                caller.getCallerId() == LeConstants.LESPORTS_MSITE_CALLER_CODE) {
            share.setPic(SHARE_DEFAULT_PICURL_PC_1_1);
            share.setPic43(SHARE_DEFAULT_PICURL_PC_4_3);
        } else {//app的用app默认的icon
            share.setPic(SHARE_DEFAULT_PICURL_APP_1_1);
        }
        return share;
    }


    private static final String RECORD_VIDEO_ANCHOR = "#record/";//回放
    private static final String HIGHLIGHTS_VIDEO_ANCHOR = "#highlights/";//集锦
    private static final String RELATED_VIDEO_ANCHOR = "#related/";//相关
    private static final String LIVE_VIDEO_ANCHOR = "#live/";//直播

    /**
     * 获取分享的锚点
     *
     * @param url  url
     * @param type 类型: 0:回放 1:集锦 2:相关 3:直播
     * @param id   id
     * @return
     */
    private static String appendAnchor(String url, Share.ShareType type, String id) {
        if (url.contains("#")) {
            return url;
        }
        StringBuffer sb = new StringBuffer(url);
        switch (type) {
            case RECORD:
                sb.append(RECORD_VIDEO_ANCHOR).append(id);
                break;
            case HIGHLIGHTS:
                sb.append(HIGHLIGHTS_VIDEO_ANCHOR).append(id);
                break;
            case RELATED:
                sb.append(RELATED_VIDEO_ANCHOR).append(id);
                break;
            case LIVE:
                sb.append(LIVE_VIDEO_ANCHOR).append(id);
                break;
        }
        return sb.toString();
    }

	public Share modifyDomainNameByCaller (Share share, CallerParam caller) {
		if (caller.getCountry() == CountryCode.HK) {
			String url = share.getUrl();
			if (StringUtils.isNotBlank(url)) {
				if (url.contains(CN_PC_DOMAIN_NAME)) {
					url = url.replaceAll(CN_PC_DOMAIN_NAME, HK_PC_DOMAIN_NAME);
				}
				if (share.getUrl().contains(CN_M_DOMAIN_NAME)) {
					url = url.replaceAll(CN_M_DOMAIN_NAME, HK_M_DOMAIN_NAME);
				}
				share.setUrl(url);
			}
		}
		return share;
	}
}
