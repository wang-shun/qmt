package com.lesports.qmt.sbc.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.lesports.api.common.CallerParam;
import com.lesports.api.common.Platform;
import com.lesports.id.api.IdType;
import com.lesports.id.client.LeIdApis;
import com.lesports.model.VideoCloneResult;
import com.lesports.model.VideoCreateResult;
import com.lesports.mongo.repository.MongoCrudRepository;
import com.lesports.qmt.QmtOperationType;
import com.lesports.qmt.config.api.dto.DictEntryTopType;
import com.lesports.qmt.config.client.QmtConfigApis;
import com.lesports.qmt.config.client.QmtConfigTagInternalApis;
import com.lesports.qmt.config.model.Tag;
import com.lesports.qmt.msg.core.ActionType;
import com.lesports.qmt.msg.core.BusinessType;
import com.lesports.qmt.msg.core.LeMessage;
import com.lesports.qmt.msg.core.LeMessageBuilder;
import com.lesports.qmt.msg.producer.SwiftMessageApis;
import com.lesports.qmt.sbc.api.common.TvLicence;
import com.lesports.qmt.sbc.api.common.VideoContentType;
import com.lesports.qmt.sbc.api.common.VideoOnlineStatus;
import com.lesports.qmt.sbc.api.dto.*;
import com.lesports.qmt.sbc.api.service.GetRelatedVideosParam;
import com.lesports.qmt.sbc.cache.TVideoCache;
import com.lesports.qmt.sbc.converter.TVideoConverter;
import com.lesports.qmt.sbc.helper.LesportsToMmsMapping;
import com.lesports.qmt.sbc.model.Album;
import com.lesports.qmt.sbc.model.Video;
import com.lesports.qmt.sbc.repository.AlbumRepository;
import com.lesports.qmt.sbc.repository.VideoRepository;
import com.lesports.qmt.sbc.service.EpisodeService;
import com.lesports.qmt.sbc.service.LiveService;
import com.lesports.qmt.sbc.service.NewsService;
import com.lesports.qmt.sbc.service.VideoService;
import com.lesports.qmt.sbc.service.support.AbstractSbcService;
import com.lesports.qmt.sbc.utils.KVCacheUtils;
import com.lesports.qmt.sbc.utils.KVRedisUtils;
import com.lesports.utils.*;
import com.lesports.utils.math.LeNumberUtils;
import com.lesports.utils.pojo.GuoguangReviewResult;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.CriteriaDefinition;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;
import static org.springframework.data.mongodb.core.query.Update.update;

/**
 * Created by zhangxudong@le.com on 2016/10/25.
 */
@Service
public class VideoServiceImpl extends AbstractSbcService<Video, Long> implements VideoService {
    private static final String MMS_LESPORTS_PLATFORM = "upload";
    private static final String MMS_LESPORTS_PRIVATE_KEY = "4567";
    //视频正片类型集合
    private static final List<VideoContentType> VIDEO_FEATURE_TYPE_LIST = Lists.newArrayList(VideoContentType.RECORD, VideoContentType.FEATURE);
    //正片的catch
    private static final Utf8KeyCreater<String> FEATURE_VIDEOS_KEY_CREATER = new Utf8KeyCreater<>("V2_SMS_FEATURE_VIDEOS_TF_");
    //缓存半小时
    private static final int FEATURE_VIDEOS_CACHE_EXPIRE_TIME = 60 * 30;
    //专辑非正片的视频总数
    private static final Utf8KeyCreater<String> NON_FEATURE_VIDEOS_COUNT_KEY_CREATER = new Utf8KeyCreater<>("V2_SMS_V3_NON_FEATURE_VIDEOS_COUNT_TF_");
    //专辑正片的视频总数
    private static final Utf8KeyCreater<String> FEATURE_VIDEOS_COUNT_KEY_CREATER = new Utf8KeyCreater<>("V2_SMS_V3_FEATURE_VIDEOS_COUNT_TF_");
    //该视频在专辑非正片视频集合的index
    private static final Utf8KeyCreater<String> NON_FEATURE_VIDEO_INDEX_KEY_CREATER = new Utf8KeyCreater<>("V2_SMS_V3_NON_FEATURE_VIDEO_INDEX_TF_");
    //该视频在专辑正片视频集合的index
    private static final Utf8KeyCreater<String> FEATURE_VIDEO_INDEX_KEY_CREATER = new Utf8KeyCreater<>("V2_SMS_V3_FEATURE_VIDEO_INDEX_TF_");
    private static final Utf8KeyCreater<String> RELATED_VIDEOS_TOTAL_KEY_CREATER = new Utf8KeyCreater<>("V2_SMS_RELATED_VIDEOS_TOTAL_TF_");
    //缓存2小时
    private static final int RELATED_VIDEO_TOTAL_CACHE_EXPIRE_TIME = 3600 * 2;
    private static final boolean IS_FORCE_NOT_DRM = LeProperties.getBoolean("vod.force.not.drm", false);

    @Resource
    private AlbumRepository albumRepository;

    @Resource
    protected VideoRepository videoRepository;

    @Resource
    protected TVideoCache videoCache;

    @Resource
    protected TVideoConverter tVideoConverter;

    @Resource
    protected NewsService newsService;

    @Resource
    protected LiveService liveService;

    @Resource
    protected EpisodeService episodeService;

    @Override
    protected MongoCrudRepository<Video, Long> getInnerRepository() {
        return videoRepository;
    }

    @Override
    public Video findOne(Long id) {
        if (null == id) return null;
        Video video = videoRepository.findOne(id);
        if (null != video.getAid()) {
            Album album = albumRepository.findOne(video.getAid());
            if (null != album) video.setAlbumName(album.getTitle());
        }
        return video;
    }

    @Override
    public List<Video> findByIds(List<Long> ids) {
        if (null == ids) return null;
        List<Video> videos = videoRepository.findByIds(ids);
        if (CollectionUtils.isEmpty(videos)) return videos;

        List<Long> aids = new ArrayList<>();
        Map<Long, Long> vidAndAid = new HashMap<>();
        for (Video video : videos) {
            if (null != video && null != video.getAid()) {
                aids.add(video.getAid());
                vidAndAid.put(video.getAid(), video.getId());
            }
        }
        if (CollectionUtils.isEmpty(aids)) return videos;

        List<Album> albums = albumRepository.findByIds(aids);
        if (CollectionUtils.isEmpty(albums)) return videos;

        Map<Long, String> vidAndAName = new HashMap<>();
        for (Album album : albums) {
            Long videoId = vidAndAid.get(album.getId());
            vidAndAName.put(videoId, album.getTitle());
        }

        for (Video video : videos) {
            String albumName = vidAndAName.get(video.getId());
            video.setAlbumName(albumName);
        }
        return videos;
    }

    @Override
    protected QmtOperationType getOpreationType(Video video) {
        if (null == video || LeNumberUtils.toLong(video.getId()) <= 0) {
            return QmtOperationType.CREATE;
        }
        return QmtOperationType.UPDATE;
    }

    @Override
    protected boolean doCreate(Video video) {
        if (null == video) {
            return false;
        }
        video.setOnlineStatus(VideoOnlineStatus.OFFLINE);
        //同步到大乐视
        Map param = toVrsCreateVideoParam(video);
        VideoCreateResult videoCreateResult = MmsApis.saveVideo(param);
        LOG.info("create video to vrs : {}, result : {}", JSONObject.toJSONString(param), JSONObject.toJSONString(videoCreateResult));
        if (null == videoCreateResult || null == videoCreateResult.getData()) {
            return false;
        } else {
            video.setSupportLicences(Sets.newHashSet());
        }
        video.setLeecoVid(videoCreateResult.getData().getVid());
        video.setLeecoMid(videoCreateResult.getData().getMid());
//        video.setId(LeIdApis.nextId(IdType.VIDEO));
        video.setId(LeIdApis.convertMmsVideoIdToLeSportsVideoId(video.getLeecoVid())); // create new video
        boolean review = updateCibnReview(video.getLeecoVid(), video.getReasonOfExReview());
        if (review) {
            video.setSupportLicences(Sets.newHashSet(TvLicence.CIBN));
        }
        boolean result = videoRepository.save(video);
        if (result) {
            //声明，来源
            newsService.saveWithVideo(video);
            //更新live上的回放id
            liveService.updateLivePlaybackId(video.getRecordId(), video.getId());
            //更新episode上的highlight id
            episodeService.updateHighlightId(video.getHighlightId(), video.getId());
            //更新episode上的vid
            episodeService.updateSelfMadeId(video.getSelfProducedProgramId(), video.getId());

        }
        LOG.info("[create video][video name={}, result={}]", video.getTitle(), result);
        return result;
    }

    private Map toVrsCreateVideoParam(Video video) {
        Map param = toVrsVideoParam(video);
        //覆盖性更新
        param.put("updateType", 2);
        param.put("token", MD5Util.md5(MMS_LESPORTS_PLATFORM + MMS_LESPORTS_PRIVATE_KEY));
        return param;
    }

    private Map toVrsVideoParam(Video video) {
        Map<String, Object> videoUploadInitRequest = new HashMap<>();
        //final String MMS_LESPORTS_PLATFORM = "sport";

        videoUploadInitRequest.put("platform", MMS_LESPORTS_PLATFORM);
        videoUploadInitRequest.put("copyrightSite", "650001");
        videoUploadInitRequest.put("site", "650001");
        videoUploadInitRequest.put("nameCn", video.getTitle());
        videoUploadInitRequest.put("description", StringUtils.isEmpty(video.getDesc()) ? "default desc" : video.getDesc());
        videoUploadInitRequest.put("tag", getStringByCollection(video.getRelatedIds(), (id) -> {
            Tag tag = QmtConfigTagInternalApis.getTagById(id);
            if (null != tag) {
                return tag.getName();
            }
            return "";
        }, "default tag"));
        videoUploadInitRequest.put("category", "4"); //
        videoUploadInitRequest.put("style", MapUtils.getLongValue(LesportsToMmsMapping.STYLE, video.getCid()));
        videoUploadInitRequest.put("subCategory", MapUtils.getLongValue(LesportsToMmsMapping.SUB_CATEGORY, video.getChannel()));
        videoUploadInitRequest.put("contentRating", MapUtils.getLongValue(LesportsToMmsMapping.CONTENT_RATING, video.getContentRating()));
        videoUploadInitRequest.put("videoType", MapUtils.getLongValue(LesportsToMmsMapping.VIDEO_TYPE, video.getVideoType(), LesportsToMmsMapping.VIDEO_TYPE.get(VideoContentType.FEATURE)));
        videoUploadInitRequest.put("lang", "700004"); //简体中文
        videoUploadInitRequest.put("drmflag", video.getDrmFlag());
        videoUploadInitRequest.put("area", "50150");
        videoUploadInitRequest.put("language", "70001");
        videoUploadInitRequest.put("isPay", (LeNumberUtils.toBoolean(video.getIsPay()) ? 1 : 0));
//        videoUploadInitRequest.put("updateType", 1);
        videoUploadInitRequest.put("downPlatform", getStringByCollection(video.getDownloadPlatforms(), (platform -> String.valueOf(LesportsToMmsMapping.DOWNLOAD_PLATFORM.get(platform))), ""));
        if (video.getOnlineStatus() == VideoOnlineStatus.ONLINE) {
            videoUploadInitRequest.put("playPlatform", getStringByCollection(video.getVrsPlayPlatforms(), (platform -> String.valueOf(LesportsToMmsMapping.PLAY_PLATFORM.get(platform))), ""));
        } else {
            videoUploadInitRequest.put("tempPlayPlatform", getStringByCollection(Sets.newHashSet(Platform.MOBILE, Platform.TV, Platform.PC, Platform.PAD, Platform.MSITE),
                    (platform -> String.valueOf(LesportsToMmsMapping.PLAY_PLATFORM.get(platform))), ""));
        }

        Album album = albumRepository.findOne(video.getAid());
        videoUploadInitRequest.put("pid", null != album ? album.getLeecoAid() : "");
//        videoUploadInitRequest.put("episode", video.getTitle());
//        videoUploadInitRequest.put("childCate", video.getTitle());
//        videoUploadInitRequest.put("isPushChild", video.getTitle());
//        videoUploadInitRequest.put("funcProperty", video.getTitle());
//        videoUploadInitRequest.put("starring", video.getTitle());
//        videoUploadInitRequest.put("adPoint", video.getTitle());
//        videoUploadInitRequest.put("alias", video.getTitle());
//        videoUploadInitRequest.put("area", video.getTitle());
//        videoUploadInitRequest.put("compere", video.getTitle());
        videoUploadInitRequest.put("isDanmaku", 1);
        videoUploadInitRequest.put("disableType", LesportsToMmsMapping.SHIELD_TYPE.get(video.getShieldAreaType()));
        videoUploadInitRequest.put("controlAreas", getStringByCollection(video.getShieldCountries(), (code) -> code, ""));
//        videoUploadInitRequest.put("copyrightCompany", video.getTitle());
//        videoUploadInitRequest.put("copyrightEnd", video.getTitle());
//        videoUploadInitRequest.put("copyrightStart", video.getTitle());
//        videoUploadInitRequest.put("playPlatform", video.getTitle());
//        videoUploadInitRequest.put("mid", video.getTitle());
//        videoUploadInitRequest.put("btimeStr", video.getTitle());
//        videoUploadInitRequest.put("etimeStr", video.getTitle());
//        videoUploadInitRequest.put("vtypes", video.getTitle());
//        videoUploadInitRequest.put("md5", video.getTitle());
//        videoUploadInitRequest.put("userId", video.getTitle());
        videoUploadInitRequest.put("drmFlag", video.getDrmFlag());
//        videoUploadInitRequest.put("lang", video.getTitle());
//        videoUploadInitRequest.put("picOriginal", video.getTitle());
//        videoUploadInitRequest.put("guid", video.getTitle());
//        videoUploadInitRequest.put("language", video.getTitle());
//        videoUploadInitRequest.put("releaseDate", video.getTitle());
//        videoUploadInitRequest.put("subTitle", video.getTitle());
//        videoUploadInitRequest.put("contentRating", video.getTitle());
//        videoUploadInitRequest.put("coopPlatform", video.getTitle());
        videoUploadInitRequest.put("isPay", LeNumberUtils.toBoolean(video.getIsPay()) ? 1 : 0);

        return videoUploadInitRequest;
    }

    private Map toVrsUpdateVideoParam(Video video) {
        Map param = toVrsVideoParam(video);
        //选择性更新
        param.put("updateType", 1);
        param.put("id", video.getLeecoVid());
        param.put("token", MD5Util.md5(MMS_LESPORTS_PLATFORM + MMS_LESPORTS_PRIVATE_KEY + video.getLeecoVid()));

        return param;
    }

    private <T> String getStringByCollection(Collection<T> list, Function<T, String> mapping, String defaultValue) {
        if (CollectionUtils.isEmpty(list)) return defaultValue;
        StringBuilder result = new StringBuilder();
        list.forEach(tag -> {
            String value = null;
            try {
                value = mapping.apply(tag);
            } catch (Exception e) {
                LOG.error("{}", e.getMessage(), e);
            }
            if (false == StringUtils.isEmpty(value)) {
                result.append(value);
                result.append(",");
            }
        });
        if (StringUtils.isEmpty(result.toString())) {
            return defaultValue;
        }
        return result.toString();
    }

    @Override
    protected boolean doAfterCreate(Video video) {
        if (null == video) return false;
        videoCache.delete(video.getId());
        List<BusinessType> businessTypes = Lists.newArrayList();
        if (!indexEntity(video.getId(), IdType.VIDEO.toString())) {
            businessTypes.add(BusinessType.SEARCH_INDEX);
        }
        LeMessage message = LeMessageBuilder.create()
                .setEntityId(video.getId())
                .setIdType(IdType.VIDEO)
                .setBusinessTypes(ActionType.ADD, businessTypes)
                .build();
        SwiftMessageApis.sendMsgAsync(message);
        return true;
    }

    @Override
    protected boolean doUpdate(Video video) {
        boolean review = updateCibnReview(video.getLeecoVid(), video.getReasonOfExReview());
        if (review) {
            video.setSupportLicences(Sets.newHashSet(TvLicence.CIBN));
        } else {
            video.setSupportLicences(Sets.newHashSet());
        }
        video.setSupportLicences(Sets.newHashSet(TvLicence.CIBN));
        boolean result = videoRepository.save(video);
        if (result) {
            //声明，来源
            newsService.saveWithVideo(video);
            //更新live上的回放id
            liveService.updateLivePlaybackId(video.getRecordId(), video.getId());
            //更新episode上的highlight id
            episodeService.updateHighlightId(video.getHighlightId(), video.getId());
            //更新episode上的vid
            episodeService.updateSelfMadeId(video.getSelfProducedProgramId(), video.getId());
        }
        LOG.info("[create video][video name={}, result={}]", video.getTitle(), result);
        return result;
    }

    @Override
    protected boolean doAfterUpdate(Video video) {
        if (null == video) return false;
        //同步到媒资
        Map param = toVrsUpdateVideoParam(video);
        VideoCreateResult videoCreateResult = MmsApis.saveVideo(param);
        LOG.info("update video to vrs : {}, result : {}", JSONObject.toJSONString(param), JSONObject.toJSONString(videoCreateResult));

        if (null == videoCreateResult || 0 == videoCreateResult.getResult()) {
            LOG.error("Fail to save video {} in mms, reason : {}.", video.getId(), null == videoCreateResult ? "" : videoCreateResult.getMsgs());
        }
        videoCache.delete(video.getId());
        List<BusinessType> businessTypes = Lists.newArrayList();
        if (!indexEntity(video.getId(), IdType.VIDEO.toString())) {
            businessTypes.add(BusinessType.SEARCH_INDEX);
        }
        LeMessage message = LeMessageBuilder.create()
                .setEntityId(video.getId())
                .setIdType(IdType.VIDEO)
                .setBusinessTypes(ActionType.UPDATE, businessTypes)
                .build();
        SwiftMessageApis.sendMsgAsync(message);
        return true;
    }

    @Override
    protected boolean doDelete(Long id) {
        if (null == id) return false;
        String now = LeDateUtils.formatYYYYMMDDHHMMSS(new Date());
        Update up = update("deleted", true).set("update_at", now);
        boolean result = videoRepository.update(id, up);
        LOG.info("[delete video][video id={}, result={}]", id, result);
        return result;
    }

    @Override
    protected boolean doAfterDelete(Video video) {
        if (null == video) return false;
        return videoCache.delete(video.getId());
    }

    @Override
    protected Video doFindExistEntity(Video video) {
        if (null == video || null == video.getId()) return null;
        Video result = videoRepository.findOne(video.getId());
        return result;
    }

    /**
     * 克隆视频
     *
     * @param videoId
     * @param albumIds
     */
    @Override
    public boolean cloneVideoToAlbum(long videoId, List<Long> albumIds) {
        if (CollectionUtils.isEmpty(albumIds) || videoId <= 0) return false;
        Video video = videoRepository.findOne(videoId);
        if (null == video) return false;
        List<Album> albums = albumRepository.findByIds(albumIds);
        if (true == CollectionUtils.isEmpty(albums)) return false;

        // 同步到大乐视
        StringBuilder LeecoAids = new StringBuilder();
        Map<Long, Long> albumIdMap = new HashMap<>();
        for (Album album : albums) {
            if (null == album || null == album.getLeecoAid()) continue;
            LeecoAids.append(album.getLeecoAid()).append(",");
            albumIdMap.put(album.getLeecoAid(), album.getId());
        }
        VideoCloneResult mmsResult = MmsApis.cloneVideo(toCloneParam(video.getLeecoVid(), LeecoAids.toString())); //todo call mms api
        if (null == mmsResult || true == CollectionUtils.isEmpty(mmsResult.getData())) return false;

        // 全媒体创建克隆视频
        for (VideoCloneResult.Data data : mmsResult.getData()) {
            if (null == data) continue;
            Long albumId = albumIdMap.get(data.getPid());
            if (null == albumId) continue;
            video.setId(LeIdApis.convertMmsVideoIdToLeSportsVideoId(data.getVid())); // create new video
            video.setLeecoVid(data.getVid());
            video.setAid(albumId);
            video.setMainId(videoId);
            video.setCloneAids(albumIds);
            video.setIsClone(true);
            videoRepository.save(video); // save qmt video
        }
        return true;
    }

    private Map toCloneParam(Long leecoVid, String leecoAids) {
        Map<String, Object> map = new HashMap<>();
        map.put("token", MD5Util.md5(MMS_LESPORTS_PLATFORM + MMS_LESPORTS_PRIVATE_KEY + leecoVid));
        map.put("platform", "upload");
        map.put("id", leecoVid);
        map.put("pid", leecoAids);
        map.put("userId", "");
        map.put("cid", "");
        return map;
    }


    @Override
    public TVideo getTVideoById(long id, CallerParam caller) {
        TVideo tVideo = videoCache.findOne(id);
        if (null == tVideo) {
            Video video = videoRepository.findOne(id);
            if (null == video) {
                LOG.warn("fail to getTVideoById, video no exists. id : {}, caller : {}", id, caller);
                return null;
            }
            if (true == video.getDeleted()) {
                LOG.warn("fail to getTVideoById, video is deleted. id : {}, caller : {}", id, caller);
                return null;
            }
            tVideo = tVideoConverter.toDto(video);
            if (null == tVideo) {
                LOG.warn("fail to getTVideoById, toTVo fail. id : {}, caller : {}", id, caller);
                return null;
            }
            videoCache.save(tVideo);
        }
        tVideoConverter.adaptDto(tVideo, caller);
        return tVideo;
    }

    @Override
    public TVideo getTVideoByLeecoVid(long leecoVid, CallerParam caller) {
        long id = LeIdApis.convertMmsVideoIdToLeSportsVideoId(leecoVid);
        TVideo tVideo = videoCache.findOne(id);
        if (null == tVideo) {
            Video video = videoRepository.findOne(id);
            if (null == video) {
                LOG.warn("fail to getTVideoById, video no exists. id : {}, caller : {}", id, caller);
                return null;
            }
            if (true == video.getDeleted()) {
                LOG.warn("fail to getTVideoById, video is deleted. id : {}, caller : {}", id, caller);
                return null;
            }
            tVideo = tVideoConverter.toDto(video);
            if (null == tVideo) {
                LOG.warn("fail to getTVideoById, toTVo fail. id : {}, caller : {}", id, caller);
                return null;
            }
            videoCache.save(tVideo);
        }
        tVideoConverter.adaptDto(tVideo, caller);
        return tVideo;
    }

    @Override
    public List<TVideo> getTVideoByIds(List<Long> ids, CallerParam caller) {
        if (CollectionUtils.isEmpty(ids)) {
            return Collections.EMPTY_LIST;
        }
        List<TVideo> videos = Lists.newArrayListWithExpectedSize(ids.size());
        for (final Long id : ids) {
            TVideo tVideo = getTVideoById(id, caller);
            if (tVideo != null) {
                videos.add(tVideo);
            }
        }
        return videos;
    }

    @Override
    public List<Long> getLatestVideoIds(Pageable pageable, CallerParam caller) {
        Query q = query(Criteria.where("deleted").is(false));
        //相关视频列表不要吐克隆视频
        q.addCriteria(Criteria.where("is_clone").is(false));
        CriteriaDefinition countryCriteria = getCountryCriteria(caller);
        if (countryCriteria != null) {
            q.addCriteria(countryCriteria);
        }
        addSupportLicenceCriteria(q, caller);
        filterCaller(q, caller.getCallerId());
        pageable = getValidPageable(pageable);
        q.with(pageable);
        return videoRepository.findIdByQuery(q);
    }

    @Override
    public Video findLatestVideoByAid(long aid, CallerParam caller) {
        Query q = query(where("deleted").is(false));
        q.addCriteria(where("aids").is(aid));
        q.with(new Sort(Sort.Direction.DESC, "create_at"));
        q.addCriteria(where("video_type").is(VideoContentType.RECORD));
        addSupportLicenceCriteria(q, caller);
        Criteria callerCriteria = getCallerCriteria(caller.getCallerId());
        if (callerCriteria != null) {
            q.addCriteria(callerCriteria);
        }
        CriteriaDefinition countryCriteria = getCountryCriteria(caller);
        if (countryCriteria != null) {
            q.addCriteria(countryCriteria);
        }
        return videoRepository.findOneByQuery(q);
    }

    @Override
    public long countVideosByRelatedIdParam(GetRelatedVideosParam p, CallerParam caller) {
        Query q = query(Criteria.where("deleted").is(false));
        addRelatedVideoCriteriaToQuery(q, p, caller);
        return videoRepository.countByQuery(q);
    }

    @Override
    public List<Long> getVideoIdsByRelatedId(GetRelatedVideosParam p, Pageable pageable, CallerParam caller) {
        Query q = query(Criteria.where("deleted").is(false));
        addRelatedVideoCriteriaToQuery(q, p, caller);
        pageable = getValidPageable(pageable);
        q.with(pageable);
        return videoRepository.findIdByQuery(q);
    }

    @Override
    public List<TVideo> getRecordVideosInAlbum(long aid, Pageable pageable, CallerParam caller) {
        List<TComboEpisode> episodes = episodeService.getEpisodeByAid(aid, pageable, caller);
        if (CollectionUtils.isEmpty(episodes)) {
            return Collections.EMPTY_LIST;
        }
        return getRecordVideosFromEpisodes(episodes, caller);
    }

    @Override
    public TFeatureInfo getFeatureVideosByYears(long aid, String yearAndMonth, CallerParam caller) {
        TFeatureInfo tFeatureInfo = new TFeatureInfo();
        if (aid <= 0) {
            LOG.error("fail to getFeatureVideosByYears ! aid is empty. aid : {}", aid);
            return null;
        }
        String key = FEATURE_VIDEOS_KEY_CREATER.textKey(aid + "_" + yearAndMonth);
        Object obj = KVRedisUtils.get(key);
        if (obj != null) {
            tFeatureInfo = (TFeatureInfo) obj;
        } else {
            LOG.warn("feature videos not find in cache. key : {} try to get from mongodb", key);
            //获取专辑下最新和第一期节目
            List<TComboEpisode> firstAndLatestEpisodes = episodeService.getFirstAndLatestEpisodesByAid(aid, caller);
            if (CollectionUtils.isEmpty(firstAndLatestEpisodes)) {
                LOG.warn("fail to getFeatureVideosByYears ! aid has no firstAndLatestEpisodes. aid : {}", aid);
                return null;
            }
            String endTime = firstAndLatestEpisodes.get(0).getStartTime();
            String startTime = firstAndLatestEpisodes.get(firstAndLatestEpisodes.size() - 1).getStartTime();
            //专辑第一期节目时间
            tFeatureInfo.setStartTime(StringUtils.substring(startTime, 0, 6));
            //专辑最新一期节目时间
            String newestTime = StringUtils.substring(endTime, 0, 6);
            tFeatureInfo.setEndTime(newestTime);
            //如果不传入时间或者格式不正确,则返回最新的一个月的数据
            if (StringUtils.isBlank(yearAndMonth) || yearAndMonth.length() != 6) {
                yearAndMonth = newestTime;
            }
            List<TComboEpisode> episodesByMonth = episodeService.getEpisodeByYearAndMonth(aid, yearAndMonth, caller);
            //该专辑本月有的节目
            tFeatureInfo.setVideos(getRecordVideosFromEpisodes(episodesByMonth, caller));
            KVRedisUtils.set(key, tFeatureInfo, FEATURE_VIDEOS_CACHE_EXPIRE_TIME);
        }
        return tFeatureInfo;
    }

    @Override
    public List<Long> getNonPositiveVideosByAid(long aid, Pageable pageable, CallerParam caller) {
        Query q = query(where("deleted").is(false));
        pageable = PageUtils.getValidPageable(pageable);
        if (pageable.getSort() == null) {
            //添加默认排序
            pageable = new PageRequest(pageable.getPageNumber(), pageable.getPageSize(), Sort.Direction.DESC, "create_at");
        }
        q.addCriteria(where("aid").is(aid));
        Criteria callerCriteria = getCallerCriteria(caller.getCallerId());
        if (callerCriteria != null) {
            q.addCriteria(callerCriteria);
        }
        CriteriaDefinition countryCriteria = getCountryCriteria(caller);
        if (countryCriteria != null) {
            q.addCriteria(countryCriteria);
        }
        q.addCriteria(where("video_type").nin(VIDEO_FEATURE_TYPE_LIST)).with(pageable);
        List<Long> ids = videoRepository.findIdByQuery(q);
        if (true == CollectionUtils.isEmpty(ids)) {
            return Collections.EMPTY_LIST;
        }
        return ids;
    }

    @Override
    public TVideoInfo getTVideoInfoByRelatedId(GetRelatedVideosParam p, Pageable pageable, CallerParam caller) {
        if (p.getRelatedId() <= 0) {
            LOG.error("fail to getTVideoInfoByRelatedId. illegal param : {}", p);
            return null;
        }
        if (null == p.getTypes()) {
            p.setTypes(Arrays.asList(VideoContentType.values()));
        }
        Query q = query(where("deleted").is(false));
        addRelatedVideoCriteriaToQuery(q, p, caller);

        String key = RELATED_VIDEOS_TOTAL_KEY_CREATER.textKey(Joiner.on(',').join(p.getTypes()) + "_" + p.getRelatedId());
        long total = 0;
        Object obj = KVRedisUtils.get(key);
        if (obj != null) {
            total = (long) obj;
        } else {
            LOG.info("fail to get realted video count for p : {}, try to find from mongodb. caller : {}", p, caller);
            total = videoRepository.countByQuery(q);
            if (total > 0) {
                KVCacheUtils.set(key, total, RELATED_VIDEO_TOTAL_CACHE_EXPIRE_TIME);
            }
        }

        pageable = getValidPageable(pageable);
        q.with(pageable);
        List<Long> ids = videoRepository.findIdByQuery(q);
        if (org.apache.commons.collections.CollectionUtils.isEmpty(ids)) {
            return null;
        }
        List<TVideo> tVideos = getTVideoByIds(ids, caller);
        if (org.apache.commons.collections.CollectionUtils.isEmpty(tVideos)) {
            return null;
        }
        TVideoInfo videoInfo = new TVideoInfo();
        videoInfo.setTotal(total);
        videoInfo.setVideos(tVideos);
        return videoInfo;
    }

    @Override
    public List<Long> getAlbumVideosByAid(long aid, Pageable pageable, CallerParam caller) {
        Query q = query(where("deleted").is(false));
        q.addCriteria(where("aid").is(aid));
        q.addCriteria(where("is_clone").is(false));
        q.with(new Sort(Sort.Direction.DESC, "create_at"));
        q.addCriteria(where("video_type").is(VideoContentType.RECORD));
        addSupportLicenceCriteria(q, caller);
        Criteria callerCriteria = getCallerCriteria(caller.getCallerId());
        if (callerCriteria != null) {
            q.addCriteria(callerCriteria);
        }
        CriteriaDefinition countryCriteria = getCountryCriteria(caller);
        if (countryCriteria != null) {
            q.addCriteria(countryCriteria);
        }
        pageable = PageUtils.getValidPageable(pageable);
        q.with(pageable);
        return videoRepository.findIdByQuery(q);
    }

    @Override
    public TVideoIndexInfo getIndexOfVideoInAlbum(long vid, long aid, CallerParam caller) {
        TVideo video = getTVideoById(vid, caller);
        if (video == null) {
            LOG.warn("fail to getIndexOfVideoInAlbum. video no exists. vid : {}, aid : {}", vid, aid);
            return null;
        }
        Long vaid = video.getAid();
        if (vaid <= 0 || vaid != aid) {
            LOG.warn("fail to getTVideosAndIndexByVid. video album not match aid ! vid : {}, aid : {}", vid, aid);
            return null;
        }
        TVideoIndexInfo index = null;
        if (isFeatureVideo(video)) {
            //正片

            String cacheKey = FEATURE_VIDEO_INDEX_KEY_CREATER.textKey(aid + "_" + video.getId() + "_" + caller.getCallerId());
            Object obj = KVCacheUtils.get(cacheKey);
            if (obj != null) {
                index = (TVideoIndexInfo) obj;
            } else {
                LOG.warn("feature video index not find in cache. key : {} try to get from mongodb. caller : {}", cacheKey, caller);
                index = getIndexOfVideoInAlbumFromRepo(vid, aid, true, caller);
                KVCacheUtils.set(cacheKey, index, FEATURE_VIDEOS_CACHE_EXPIRE_TIME);
            }
        } else {
            String cacheKey = NON_FEATURE_VIDEO_INDEX_KEY_CREATER.textKey(aid + "_" + video.getId() + "_" + caller.getCallerId());
            Object obj = KVCacheUtils.get(cacheKey);
            if (obj != null) {
                index = (TVideoIndexInfo) obj;
            } else {
                LOG.warn("non feature video index not find in cache. Key : {} try to get from mongodb. caller : {}", cacheKey, caller);
                index = getIndexOfVideoInAlbumFromRepo(vid, aid, true, caller);
                KVCacheUtils.set(cacheKey, index, FEATURE_VIDEOS_CACHE_EXPIRE_TIME);
            }
        }
        return index;
    }

    @Override
    public List<Long> getVideoIdsRelatedWithSomeVideo(long vid, Pageable pageable, CallerParam caller) {
        Video video = videoRepository.findOne(vid);
        if (video == null) {
            LOG.error("fail to getRelatedTVideos. video : {} no exists", vid);
            return Collections.EMPTY_LIST;
        }
        long callerId = caller.getCallerId();
        pageable = PageUtils.getValidPageable(pageable);
        if (pageable.getSort() == null) {
            //添加默认排序
            pageable = new PageRequest(pageable.getPageNumber(), pageable.getPageSize(), Sort.Direction.DESC, "create_at");
        }
        List<Long> videoIds = Lists.newArrayList();
        String threeDayBefore = LeDateUtils.formatYYYYMMDD(LeDateUtils.addDays(new Date(), -3));



        //FIXME:这部分逻辑需要重写
//        if (false == CollectionUtils.isEmpty(video.getRelatedIds())) {
//            Query q = query(where("deleted").is(false));
//            filterCaller(q, callerId);
//            CriteriaDefinition countryCriteria = getCountryCriteria(caller);
//            if (countryCriteria != null) {
//                q.addCriteria(countryCriteria);
//            }
//            addSupportLicenceCriteria(q, caller);
//            q.addCriteria(where("create_at").gte(threeDayBefore));
//            q.addCriteria(where("related_ids").in(video.getRelatedIds())).with(pageable);
//            videoIds.addAll(videoRepository.findIdByQuery(q));
//        }
        List<Long> tids = new ArrayList<>();
        List<Long> gameFTypeIds = new ArrayList<>();
        if (false == CollectionUtils.isEmpty(video.getRelatedIds())) {
            for (Long id : video.getRelatedIds()) {
                IdType idType = LeIdApis.checkIdTye(id);
                if (IdType.TEAM == idType) {
                    tids.add(id);
                }
                if (IdType.DICT_ENTRY == idType) {
                    DictEntryTopType dictEntryTopType = QmtConfigApis.getDictEntryTopType(id);
                    if (DictEntryTopType.GAME_FIRST_TYPE == dictEntryTopType) {
                        gameFTypeIds.add(id);
                    }
                }
            }
        }

        if (false == CollectionUtils.isEmpty(tids)) {
            //按球队取
            Query q = query(where("deleted").is(false));
            filterCaller(q, callerId);
            CriteriaDefinition countryCriteria = getCountryCriteria(caller);
            if (countryCriteria != null) {
                q.addCriteria(countryCriteria);
            }
            addSupportLicenceCriteria(q, caller);
            q.addCriteria(where("create_at").gte(threeDayBefore));
            q.addCriteria(where("related_ids").is(tids)).with(pageable);
            videoIds.addAll(videoRepository.findIdByQuery(q));
        }

        Long cid = video.getCid();
        if ((CollectionUtils.isEmpty(videoIds) || videoIds.size() < pageable.getPageSize()) && cid != null) {
            //球队不足,按赛事取
            Query q = query(where("deleted").is(false));
            filterCaller(q, callerId);
            CriteriaDefinition countryCriteria = getCountryCriteria(caller);
            if (countryCriteria != null) {
                q.addCriteria(countryCriteria);
            }
            addSupportLicenceCriteria(q, caller);
            q.addCriteria(where("cid").is(cid)).with(pageable);
            q.withHint("cid_1_deleted_1");
            videoIds.addAll(videoRepository.findIdByQuery(q));
        }

//        Long gameFType = video.getGameFType();
        if ((CollectionUtils.isEmpty(videoIds) || videoIds.size() < pageable.getPageSize()) && false == CollectionUtils.isEmpty(gameFTypeIds)) {
            //赛事也不足,按大项取
            Query q = query(where("deleted").is(false));
            filterCaller(q, callerId);
            CriteriaDefinition countryCriteria = getCountryCriteria(caller);
            if (countryCriteria != null) {
                q.addCriteria(countryCriteria);
            }
            addSupportLicenceCriteria(q, caller);
            q.addCriteria(where("create_at").gte(threeDayBefore));
            q.addCriteria(where("related_ids").is(gameFTypeIds)).with(pageable);
            q.withHint("related_ids_1_create_at_1_deleted_1");
            videoIds.addAll(videoRepository.findIdByQuery(q));
        }
        if (videoIds.contains(vid)) {
            videoIds.remove(vid);
        }
        return videoIds;
    }

    /**
     * 获取视频在专辑的正片视频列表中的index,方便前端做定位
     *
     * @param vid
     * @param aid
     * @param caller
     * @return
     */
    private TVideoIndexInfo getIndexOfVideoInAlbumFromRepo(long vid, long aid, boolean isFeature, CallerParam caller) {
        TVideoIndexInfo info = new TVideoIndexInfo();
        info.setIndex(-1);
        info.setTotal(0);
        Query q = query(where("deleted").is(false));
        filterCaller(q, caller.getCallerId());
        q.addCriteria(where("aid").is(aid));
        if (isFeature) {
            q.addCriteria(where("episode_ids").exists(true));
            q.addCriteria(where("video_type").in(VIDEO_FEATURE_TYPE_LIST));
        } else {
            q.addCriteria(where("video_type").nin(VIDEO_FEATURE_TYPE_LIST));
        }
        CriteriaDefinition countryCriteria = getCountryCriteria(caller);
        if (countryCriteria != null) {
            q.addCriteria(countryCriteria);
        }
        q.withHint("aid_1");
        q.with(new Sort(Sort.Direction.DESC, "create_at"));
        List<Long> ids = videoRepository.findIdByQuery(q);

        if (org.apache.commons.collections.CollectionUtils.isNotEmpty(ids)) {
            info.setTotal(ids.size());
            info.setIndex(ids.indexOf(vid));
            return info;
        }
        return info;
    }

    /**
     * 获取每期节目下的正片视频列表
     *
     * @param episodes
     * @return
     */
    private List<TVideo> getRecordVideosFromEpisodes(List<TComboEpisode> episodes, CallerParam caller) {
        if (CollectionUtils.isEmpty(episodes)) {
            return Collections.EMPTY_LIST;
        }
        List<TVideo> results = Lists.newArrayList();
        for (TComboEpisode e : episodes) {
            List<TSimpleVideo> videos = e.getVideos();
            if (false == CollectionUtils.isEmpty(videos)) {
                for (TSimpleVideo tSimpleVideo : videos) {
                    if (tSimpleVideo.getType() == VideoContentType.RECORD) {
                        //取正片改为取录播吧 因为好多规则不统一
                        TVideo tVideo = getTVideoById(tSimpleVideo.getVid(), caller);
                        if (tVideo != null && !tVideo.isCloneVideo()) {
                            results.add(tVideo);
                            //每个自制节目放一个回放/正片就应该跳出了
                            break;
                        }
                    }
                }
            }
        }
        return results;
    }

    /**
     * 是否是正片
     *
     * @param video
     * @return
     */
    private boolean isFeatureVideo(TVideo video) {
        return VIDEO_FEATURE_TYPE_LIST.contains(video.getContentType());
    }

    private void addRelatedVideoCriteriaToQuery(Query q, GetRelatedVideosParam p, CallerParam caller) {
        if (p == null) {
            return;
        }
        //相关视频列表不要吐克隆视频
        q.addCriteria(Criteria.where("is_clone").is(false));
        addSupportLicenceCriteria(q, caller);
        Criteria relatedCriteria = getRelatedVideoCriteria(p.getRelatedId());
        if (null != relatedCriteria && null != relatedCriteria.getCriteriaObject()) {
//            if (relatedCriteria.getCriteriaObject().containsField("tag_ids")) { // FIXME:强制使用索引
//                q.withHint("tag_ids_1");
//            } else if (relatedCriteria.getCriteriaObject().containsField("aid")) {
//                q.withHint("aid_1");
//            }
            q.addCriteria(relatedCriteria);
        } else {
            LOG.error("fail to addRelatedVideoCriteriaToQuery. relatedCriteria is null. relatedId : {}, caller : {}",
                    p.getRelatedId(), caller);
            throw new IllegalArgumentException("illegal ralated id : " + p.getRelatedId());
        }
        if (p.getCid() > 0) {
            q.addCriteria(Criteria.where("cid").is(p.getCid()));
        }
        if (org.apache.commons.collections.CollectionUtils.isNotEmpty(p.getTypes())) {
            q.addCriteria(Criteria.where("video_type").in(p.getTypes()));
        }
        Criteria callerCriteria = getCallerCriteria(caller.getCallerId());
        if (callerCriteria != null) {
            q.addCriteria(callerCriteria);
        }
        CriteriaDefinition countryCriteria = getCountryCriteria(caller);
        if (countryCriteria != null) {
            q.addCriteria(countryCriteria);
        }
    }

    private void filterCaller(Query q, long callerId) {
        Criteria criteria = getCallerCriteria(callerId);
        if (criteria != null) {
            q.addCriteria(criteria);
        }
    }

    private CriteriaDefinition getCountryCriteria(CallerParam caller) {
        if (caller.getCountry() == null) {
            return null;
        }
        return Criteria.where("shield_countries").is(caller.getCountry());
    }

    private Criteria getRelatedVideoCriteria(long relatedId) {
        if (relatedId <= 0) {
            return null;
        }
        IdType idType = LeIdApis.checkIdTye(relatedId);
        if (idType == null) {
            LOG.warn("illegal relatedId : {}", relatedId);
            return null;
        }
        if (idType == IdType.COMPETITION) {
            return Criteria.where("cid").is(relatedId);
        } else if (idType == IdType.PLAYER) {
            return Criteria.where("related_ids").is(relatedId);
        } else if (idType == IdType.TEAM) {
            return Criteria.where("related_ids").is(relatedId);
        } else if (idType == IdType.ALBUM) {
            return Criteria.where("aid").is(relatedId);
        } else if (idType == IdType.TAG) {
            return Criteria.where("related_ids").is(relatedId);
        } else if (idType == IdType.MATCH) {
            return Criteria.where("related_ids").is(relatedId);
        } else if (idType == IdType.EPISODE) {
            return Criteria.where("related_ids").is(relatedId);
//            return new Criteria().orOperator(Criteria.where("related_ids").is(relatedId), Criteria.where("record_episode_id").is(relatedId), Criteria.where("highlights_episode_id").is(relatedId));
        } else if (idType == IdType.DICT_ENTRY) {
//            DictEntryTopType entryType = dictHelper.getDictEntryTopType(relatedId);
//            if (entryType == DictEntryTopType.GAME_FIRST_TYPE) {
//                return Criteria.where("game_f_type").is(relatedId);
//            } else if (entryType == DictEntryTopType.GAME_SECOND_TYPE) {
//                return Criteria.where("game_s_type").is(relatedId);
//            } else {
//                LOG.warn("illegal relatedId : {}", relatedId);
//                return null;
//            }
        }
        return null;
    }

    private Pageable getValidPageable(Pageable pageable) {
        pageable = PageUtils.getValidPageable(pageable);
        if (pageable.getSort() == null) {
            //添加默认排序
            pageable = new PageRequest(pageable.getPageNumber(), pageable.getPageSize(), Sort.Direction.DESC, "_id");
        }
        return pageable;
    }

    private boolean updateCibnReview(Long leecoVid, String reason) {
        GuoguangReviewResult result = GuoguangApis.cibnVideoReview(LeNumberUtils.toLong(leecoVid), reason);
//        if (null == result || LeNumberUtils.toInt(result.getCode()) == 0) {
//            return false;
//        }
        return true;
    }
}