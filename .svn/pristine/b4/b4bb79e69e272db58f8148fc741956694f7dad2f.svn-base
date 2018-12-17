package com.lesports.qmt.web.api.core.creater;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.lesports.api.common.CallerParam;
import com.lesports.api.common.Platform;
import com.lesports.model.LiveRoom;
import com.lesports.model.LiveRoomResponse;
import com.lesports.model.VideoInfos;
import com.lesports.qmt.sbc.api.dto.TVideo;
import com.lesports.qmt.sbc.client.QmtSbcApis;
import com.lesports.qmt.sbc.client.QmtSbcEpisodeApis;
import com.lesports.qmt.web.api.core.util.Constants;
import com.lesports.qmt.web.api.core.vo.*;
import com.lesports.utils.*;
import com.lesports.utils.pojo.SubjectData;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import javax.annotation.Resource;
import java.util.*;

/**
 * Created by pangchuanxiao on 2015/6/30.
 */
@Component
public class TopicResultAdapter {
    private static final Logger LOG = LoggerFactory.getLogger(TopicResultAdapter.class);
    private static final int MAX_SUBJECT_VIDEO = 5;
    private static final String TOPIC_COMMENTID_PREFIX = "t_";

    @Resource
    private EpisodeVoCreater episodeVoCreater;
    @Resource
    private  VideoVoCreater videoVoCreater;

    private final Function<SubjectData.SubjectItem, TopicItem> SUBJECT_ITEM_ADAPTER = new Function<SubjectData.SubjectItem, TopicItem>() {
        @Nullable
        @Override
        public TopicItem apply(@Nullable SubjectData.SubjectItem subjectItem) {
            return adapt(subjectItem);
        }
    };

    public List<TopicItem> adapt(List<SubjectData.SubjectItem> list) {
        List<TopicItem> topicItems = LeConcurrentUtils.parallelApply(list, SUBJECT_ITEM_ADAPTER);
        if (CollectionUtils.isEmpty(topicItems)) {
            return Collections.EMPTY_LIST;
        }
        return topicItems;
    }

    public TopicItem adapt(SubjectData.SubjectItem item) {
        TopicItem topicItem = new TopicItem();
        SubjectData.SubjectInfo subjectInfo = CmsSubjectApis.getSubject(item.getId());
        Map<String, String> imageUrl = Maps.newHashMap();
        if (null != subjectInfo) {
            topicItem.setVideos(adaptTjPackage(subjectInfo.getPackageIds(), MAX_SUBJECT_VIDEO));
            topicItem.setCreateTime(LeDateUtils.formatYYYYMMDDHHMMSS(new Date(subjectInfo.getCtime())));
            imageUrl.put(Constants.VIDEO_NEWS_IMAGE_43, subjectInfo.getPic43());
            imageUrl.put(Constants.VIDEO_NEWS_IMAGE_BIG_169, subjectInfo.getPic169());
        }
        topicItem.setImageUrl(imageUrl);
        topicItem.setId(item.getId());
        topicItem.setName(item.getName());
        topicItem.setType(Constants.SUBJECT);
        topicItem.setCommentId(TOPIC_COMMENTID_PREFIX + item.getId());
        return topicItem;
    }


    public TvTopicInfo adaptTvSubject(SubjectData.SubjectInfo info,CallerParam caller) {
        TvTopicInfo tvTopicInfo = new TvTopicInfo();
        tvTopicInfo.setId(info.getId());
        tvTopicInfo.setName(info.getName());
        tvTopicInfo.setCid(info.getCid());
        tvTopicInfo.setCover(info.getTvPic());
        tvTopicInfo.setDesc(info.getDescription());
        tvTopicInfo.setType(info.getTemplateJson().getTvTemplate());
         tvTopicInfo.setPackageList(adaptTvTjPackage(info.getPackageIds(),caller));
        return tvTopicInfo;

    }

    public TopicInfo adapt(SubjectData.SubjectInfo info) {
        TopicInfo topicInfo = new TopicInfo();
        topicInfo.setCommentId(TOPIC_COMMENTID_PREFIX + info.getId());
        topicInfo.setId(info.getId());
        topicInfo.setChannelId(info.getCid());
        topicInfo.setDesc(info.getDescription());
        topicInfo.setMobileFocusPic(info.getFocusMPic());
        topicInfo.setmSiteUrl("lack of property in cms interface.");
        topicInfo.setShareThumbnail(info.getShareImg());
        topicInfo.setTitle(info.getName());
        topicInfo.setVideos(adaptTjPackage(info.getPackageIds(), MAX_SUBJECT_VIDEO));
        topicInfo.setCreateTime(LeDateUtils.formatYYYYMMDDHHMMSS(new Date(info.getCtime())));
        topicInfo.setUpdateTime(LeDateUtils.formatYYYYMMDDHHMMSS(new Date(info.getMtime())));
        Map<String, String> imageMap = Maps.newHashMap();
        imageMap.put("400*300", StringUtils.isNotBlank(info.getPic43()) ? info.getPic43() : StringUtils.EMPTY);
        imageMap.put("960*540", StringUtils.isNotBlank(info.getPic169()) ? info.getPic169() : StringUtils.EMPTY);
        topicInfo.setImageUrl(imageMap);
        return topicInfo;
    }


    private TopicDataInfo generateTopicDataInfo(SubjectData.SubjectInfo.TjPackage.VideoInfo videoInfo,CallerParam caler){
        Map<String, String> imageUrl = Maps.newHashMap();
        TopicDataInfo dataInfo = new TopicDataInfo();
        VideoInfos videoInfos = MmsApis.getALLVideosInAlbum(videoInfo.getId() + "", 1, 1, "tv");
        if (null != videoInfos) {
            List<VideoInfos.VideoInfo> videoInfos1 = videoInfos.getVideoInfo();
            if (CollectionUtils.isNotEmpty(videoInfos1)) {
                TVideo tVideo = QmtSbcApis.getTVideoById(videoInfos1.get(0).getId(), caler);
                if (tVideo != null && tVideo.getPlatforms().contains(Platform.TV) ) {
                    VideoVo videoVo = videoVoCreater.createVideoVo(tVideo);
                    dataInfo.setId(videoInfo.getId());
                    if (null != videoInfo.getPicCollections()) {
                        if (null != videoInfo.getPicCollections().get(Constants.ALBUM_IMAGE_300_400)) {
                            imageUrl.put(Constants.ALBUM_IMAGE_300_400, videoInfo.getPicCollections().get(Constants.ALBUM_IMAGE_300_400));
                        } else {
                            imageUrl.put(Constants.ALBUM_IMAGE_300_400, "");
                        }
                        if (null != videoInfo.getPicCollections().get(Constants.ALBUM_IMAGE_600_800)) {
                            imageUrl.put(Constants.ALBUM_IMAGE_600_800, videoInfo.getPicCollections().get(Constants.ALBUM_IMAGE_600_800));
                        } else {
                            imageUrl.put(Constants.ALBUM_IMAGE_600_800, "");
                        }
                    }
                    dataInfo.setLatestVideoVo(videoVo);
                    dataInfo.setImageUrl(imageUrl);
                    dataInfo.setName(videoInfo.getNameCn());
                    dataInfo.setDesc(videoInfo.getDescription());
                    dataInfo.setSubTitle(videoInfo.getSubTitle());


                }
            }
        }
        return dataInfo;
    }

    private List<TopicDataInfo> createAlbumList(SubjectData.SubjectInfo.TjPackage tjPackage,CallerParam caller){
        List<TopicDataInfo> dataInfos = Lists.newArrayList();

        if(CollectionUtils.isNotEmpty(tjPackage.getDataList())) {
            for (SubjectData.SubjectInfo.TjPackage.VideoInfo videoInfo : tjPackage.getDataList()) {

                dataInfos.add(generateTopicDataInfo(videoInfo, caller));
            }
        }
        return dataInfos;
    }


    private List<TopicDataInfo> createVideoList(SubjectData.SubjectInfo.TjPackage tjPackage,CallerParam caller){
        List<TopicDataInfo> dataInfos = Lists.newArrayList();
        if(CollectionUtils.isNotEmpty(tjPackage.getDataList())) {
            for (SubjectData.SubjectInfo.TjPackage.VideoInfo videoInfo : tjPackage.getDataList()) {
                TVideo tVideo = QmtSbcApis.getTVideoByLeecoVid(videoInfo.getId(),caller);
                if (tVideo != null && tVideo.getPlatforms().contains(Platform.TV)) {
                    VideoVo videoVo = videoVoCreater.createVideoVo(tVideo);
                    videoVo.setSubTitle(videoInfo.getSubTitle());
                    dataInfos.add(videoVo);

                }
            }
        }
        return dataInfos;
    }

    private List<TopicDataInfo> createEpisodeList(SubjectData.SubjectInfo.TjPackage tjPackage,CallerParam caller) {
        List<TopicDataInfo> dataInfos = Lists.newArrayList();
        if(CollectionUtils.isNotEmpty(tjPackage.getDataList())) {
            for (SubjectData.SubjectInfo.TjPackage.VideoInfo videoInfo : tjPackage.getDataList()) {
                Map<String, String> imageUrl = Maps.newHashMap();
                String liveId = videoInfo.getRid() + "";
                HallEpisodeVo4Tv hallEpisodeVo4Tv = episodeVoCreater.createHallEpisodeVo4Tv(QmtSbcEpisodeApis.getEpisodeByLiveId(liveId, caller), caller);
                if (null != hallEpisodeVo4Tv) {
                    List<HallEpisodeVo4Tv.Lives> liveses = hallEpisodeVo4Tv.getLives();
                    List<HallEpisodeVo4Tv.Lives> livesList = Lists.newLinkedList();
                    if (CollectionUtils.isNotEmpty(liveses)) {
                        for (HallEpisodeVo4Tv.Lives lives : liveses) {
                            if (lives.getLiveId().equals(liveId)) {
                                livesList.add(0, lives);
                            } else {
                                livesList.add(lives);
                            }
                        }
                    }
                    hallEpisodeVo4Tv.setLives(livesList);
                    LiveRoomResponse liveRoomResponse = null;
                    try {
                        liveRoomResponse = LiveApis.getLiveRoom(liveId, Constants.TV_SPLAT_ID);
                    } catch (Exception e) {
                        LOG.error("getLiveRoom from live error", e);
                    }
                    List<LiveRoom> rooms = liveRoomResponse.getRows();
                    if (CollectionUtils.isNotEmpty(rooms)) {
                        LiveRoom liveRoom = rooms.get(0);
                        hallEpisodeVo4Tv.setTvBackgroudPic(liveRoom.getTvBackgroudPic());
                    }
                    imageUrl.put(Constants.LIVE_IMAGE_323_431, videoInfo.getPic43());
                    hallEpisodeVo4Tv.setImageUrl(imageUrl);
                    hallEpisodeVo4Tv.setSubTitle(videoInfo.getSubTitle());
                    dataInfos.add(hallEpisodeVo4Tv);
                }
            }
        }
        return dataInfos;
    }

    private void generateDataInfoList(SubjectData.SubjectInfo.TjPackage tjPackage ,TvTopicInfo.PackageInfo packageInfo,CallerParam caller){

            switch (tjPackage.getpType()){
                case 1:
                    if(null != tjPackage && CollectionUtils.isNotEmpty(tjPackage.getDataList())) {
                        List<TopicDataInfo> albumList = createAlbumList(tjPackage,caller);
                        if (CollectionUtils.isNotEmpty(albumList)) {
                            packageInfo.setAlbumList(albumList);
                        }
                    }
                    break;
                case 2:
                    if(null != tjPackage && CollectionUtils.isNotEmpty(tjPackage.getDataList())) {
                        List<TopicDataInfo> videoList = createVideoList(tjPackage,caller);
                        if (CollectionUtils.isNotEmpty(videoList)) {
                            packageInfo.setVideoList(videoList);
                        }
                    }
                    break;
                case 3:
                    if(null != tjPackage && CollectionUtils.isNotEmpty(tjPackage.getDataList())) {
                        List<TopicDataInfo> eisodeList = createEpisodeList(tjPackage,caller);
                        if (CollectionUtils.isNotEmpty(eisodeList)) {
                            packageInfo.setEpisodeList(eisodeList);
                        }
                    }
                    break;
                default:
                    break;
            }

    }


    private TvTopicInfo.PackageInfo getPackageInfo(SubjectData.SubjectInfo.TjPackage tjPackage,CallerParam caller){
        TvTopicInfo.PackageInfo packageInfo = new TvTopicInfo.PackageInfo();
        packageInfo.setName(tjPackage.getName());
        packageInfo.setType(tjPackage.getpType());
        packageInfo.setOrder(tjPackage.getPorder());

        generateDataInfoList(tjPackage,packageInfo,caller);

        return packageInfo;
    }

    private List<TvTopicInfo.PackageInfo> adaptTvTjPackage(List<String> packageIds,CallerParam caller) {
        if (CollectionUtils.isEmpty(packageIds)) {
            return Collections.EMPTY_LIST;
        }
        List<SubjectData.SubjectInfo.TjPackage> packages = CmsSubjectApis.getSubjectVideos(packageIds);
        List<TvTopicInfo.PackageInfo> packageInfos = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(packages)) {
            for (SubjectData.SubjectInfo.TjPackage tjPackage : packages) {
                packageInfos.add(getPackageInfo(tjPackage,caller));
            }
        }
        return packageInfos;
    }

    public List<TopicInfo.VideoInfo> adaptTjPackage(List<String> packageIds, int max) {
		if (CollectionUtils.isEmpty(packageIds)) {
			return Collections.EMPTY_LIST;
		}
		List<SubjectData.SubjectInfo.TjPackage> packages = CmsSubjectApis.getSubjectVideos(packageIds);
		List<TopicInfo.VideoInfo> videoInfos = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(packages)) {
            for (SubjectData.SubjectInfo.TjPackage tjPackage : packages) {
                if (null != tjPackage && tjPackage.getpType() == Constants.VIDEO_PACKAGE_TYPE
                        && CollectionUtils.isNotEmpty(tjPackage.getDataList())) {
                    for (SubjectData.SubjectInfo.TjPackage.VideoInfo videoInfo : tjPackage.getDataList()) {
                        if (videoInfos.size() >= max) {
                            break;
                        }
                        TopicInfo.VideoInfo info = adapt(videoInfo);
                        if (null != info) {
                            videoInfos.add(info);
                        }
                    }
                }
            }
        }
        return videoInfos;
    }

    private TopicInfo.VideoInfo adapt(SubjectData.SubjectInfo.TjPackage.VideoInfo info) {
        TopicInfo.VideoInfo videoInfo = new TopicInfo.VideoInfo();
        videoInfo.setDuration(info.getDuration());
        videoInfo.setId(info.getId());
        Map<String, String> imageUrl = Maps.newHashMap();
        imageUrl.put(Constants.VIDEO_NEWS_IMAGE_43, info.getPicAll().get(Constants.VIDEO_NEWS_IMAGE_43));
        videoInfo.setImageUrl(imageUrl);
        videoInfo.setName(info.getNameCn());
        videoInfo.setReleaseDate(info.getReleaseDate());
        videoInfo.setCreateTime(LeDateUtils.formatYYYYMMDDHHMMSS(LeDateUtils.parseYMDHMS(info.getCreateTime())));
        videoInfo.setUpdateTime(LeDateUtils.formatYYYYMMDDHHMMSS(LeDateUtils.parseYMDHMS(info.getUpdateTime())));
        return videoInfo;
    }
}
