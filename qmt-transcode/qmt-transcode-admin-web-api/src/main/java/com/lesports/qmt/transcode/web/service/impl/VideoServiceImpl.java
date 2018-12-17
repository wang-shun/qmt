package com.lesports.qmt.transcode.web.service.impl;


import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.google.common.collect.Sets;
import com.lesports.api.common.ImageUrlExt;
import com.lesports.api.common.LanguageCode;
import com.lesports.api.common.Platform;
import com.lesports.qmt.sbc.api.common.ContentRating;
import com.lesports.qmt.sbc.api.common.VideoContentType;
import com.lesports.qmt.sbc.client.QmtSbcVideoInternalApis;
import com.lesports.qmt.sbc.model.Video;
import com.lesports.qmt.transcode.web.param.LiveTaskParam;
import com.lesports.qmt.transcode.web.param.VideoTranscodeParam;
import com.lesports.qmt.transcode.web.param.VideoUploadInitParam;
import com.lesports.qmt.transcode.web.service.VideoService;
import com.lesports.utils.LeStringUtils;
import com.lesports.utils.math.LeNumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.lang.reflect.Type;
import java.util.*;

/**
 * trunk.
 *
 * @author pangchuanxiao
 * @since 2016/10/29
 */
@Service("videoService")
public class VideoServiceImpl implements VideoService {
    private static final Logger LOGGER = LoggerFactory.getLogger(VideoServiceImpl.class);

    @Override
    public Video updateVideo(VideoTranscodeParam param, Long vid) {
        Video video = QmtSbcVideoInternalApis.getVideoById(vid);
        if (null == video) {
            throw new RuntimeException("获取视频信息失败");
        }
        video.setTitle(param.getTitle());
        video.setShortTitle(param.getShortTitle());
        video.setVideoType(VideoContentType.findByValue(param.getVideoType()));
        video.setRecommend2Homepage(param.getRecommend2Homepage());
        video.setStarLevel(param.getStarLevel());
        video.setVideoAuthor(param.getVideoAuthor());
        video.setHighlightId(param.getHighlightId());
        video.setRecordId(param.getPlaybackId());
        video.setSelfProducedProgramId(param.getSelfProducedProgramId());
        video.setCid(param.getCid());
        video.setIsClone(false);
        video.setIsOverView(param.getIsOverView());
        video.setShareDesc(param.getShareDesc());
        video.setDeleted(false);
        if (0 != param.getContentRating()) {
            video.setContentRating(ContentRating.findByValue(param.getContentRating()));
        }
        video.setDesc(param.getDesc());
        video.setDrmFlag(param.getDrmFlag());
        video.setChannel(param.getChannel());
        video.setSubChannel(param.getSubChannel());
        video.setPublishTime(param.getPublishTime());
        video.setSource(param.getSource());
        video.setPeriod(param.getPeriod());
        video.setReasonOfExReview(param.getReasonOfExReview());
        video.setDuration(param.getDuration());
        //视频图片
        if (!StringUtils.isEmpty(param.getImages())) {
            Type type = new TypeReference<Map<String, ImageUrlExt>>() {
            }.getType();
            Map<String, ImageUrlExt> coverImage = JSONObject.parseObject(param.getImages(), type);
            video.setImages(coverImage);
        }
        //声明
        List<String> statements = new ArrayList<>();
        if (!StringUtils.isEmpty(param.getStatement())) {
            statements.add(param.getStatement());
        }
        if (!StringUtils.isEmpty(param.getStatement2())) {
            statements.add(param.getStatement2());
        }
        video.setStatements(statements);
        //屏蔽
        video.setShieldAreaType(param.getShieldAreaType());
        if (!StringUtils.isEmpty(param.getShieldCountries())) {
            String[] disableAreas = StringUtils.commaDelimitedListToStringArray(param.getShieldCountries());
            if (null != disableAreas) {
                Set<String> countrySubdivisionCodes = new HashSet<>();
                for (String disableArea : disableAreas) {
                    countrySubdivisionCodes.add(disableArea);
                }
                video.setShieldCountries(countrySubdivisionCodes);
            }
        }

        if (!StringUtils.isEmpty(param.getMatchIds())) {
            video.setMatchIds(Sets.newHashSet(LeStringUtils.commaString2LongList(param.getMatchIds())));
        }
        if (!StringUtils.isEmpty(param.getRelatedIds())) {
            video.setRelatedIds(Sets.newHashSet(LeStringUtils.commaString2LongList(param.getRelatedIds())));
        }
        if (!StringUtils.isEmpty(param.getRelatedContents())) {
            video.setRelatedContents(LeStringUtils.commaString2LongList(param.getRelatedContents()));
        }

        video.setLanguageCode(LanguageCode.ZH_CN);
//        video.setMarkCountry(CountryCode.CN);

        //播放平台
        if (!StringUtils.isEmpty(param.getPlayPlatforms())) {
            String[] tags = StringUtils.commaDelimitedListToStringArray(param.getPlayPlatforms());
            if (null != tags) {
                Set<Platform> platforms = new HashSet<>();
                for (String disableArea : tags) {
                    platforms.add(Platform.findByValue(LeNumberUtils.toInt(disableArea)));
                }
                video.setPlayPlatforms(platforms);
            }
        }
        //媒资播放平台
        if (!StringUtils.isEmpty(param.getVrsPlayPlatforms())) {
            String[] tags = StringUtils.commaDelimitedListToStringArray(param.getVrsPlayPlatforms());
            if (null != tags) {
                Set<Platform> platforms = new HashSet<>();
                for (String disableArea : tags) {
                    platforms.add(Platform.findByValue(LeNumberUtils.toInt(disableArea)));
                }
                video.setVrsPlayPlatforms(platforms);
            }
        }
        //付费平台
        video.setIsPay(param.isPay());
        if (!StringUtils.isEmpty(param.getPayPlatforms())) {
            String[] tags = StringUtils.commaDelimitedListToStringArray(param.getPayPlatforms());
            if (null != tags) {
                Set<Platform> platforms = new HashSet<>();
                for (String disableArea : tags) {
                    platforms.add(Platform.findByValue(LeNumberUtils.toInt(disableArea)));
                }
                video.setPayPlatforms(platforms);
            }

        }
        //下载平台
        if (!StringUtils.isEmpty(param.getDownloadPlatforms())) {
            String[] tags = StringUtils.commaDelimitedListToStringArray(param.getDownloadPlatforms());
            if (null != tags) {
                Set<Platform> platforms = new HashSet<>();
                for (String disableArea : tags) {
                    platforms.add(Platform.findByValue(LeNumberUtils.toInt(disableArea)));
                }
                video.setDownloadPlatforms(platforms);
            }

        }
        video.setAid(param.getAid());
        QmtSbcVideoInternalApis.saveVideo(video);

        return video;
    }

    @Override
    public Video createVideo(VideoUploadInitParam videoTranscodeTask) {
        Video video = new Video();
        video.setTitle(videoTranscodeTask.getLocalName());
        long videoId = QmtSbcVideoInternalApis.saveVideo(video);
        LOGGER.info("Save video : {}, id : {}.", video, videoId);
        return QmtSbcVideoInternalApis.getVideoById(videoId);
    }

    @Override
    public Video createVideo(LiveTaskParam task) {
        Video video = new Video();
        video.setTitle(task.getTaskName());
        long videoId = QmtSbcVideoInternalApis.saveVideo(video);
        LOGGER.info("Save video : {}, id : {}.", video, videoId);
        return QmtSbcVideoInternalApis.getVideoById(videoId);
    }
}
