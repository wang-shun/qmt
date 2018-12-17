package com.lesports.qmt.sbc.converter;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.google.common.collect.Sets;
import com.lesports.api.common.ImageUrlExt;
import com.lesports.api.common.Platform;
import com.lesports.qmt.sbc.api.common.ContentRating;
import com.lesports.qmt.sbc.api.common.VideoContentType;
import com.lesports.qmt.sbc.model.Video;
import com.lesports.qmt.sbc.param.VideoListParam;
import com.lesports.qmt.sbc.param.VideoParam;
import com.lesports.utils.LeStringUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.lang.reflect.Type;
import java.util.*;

/**
 * Created by zhangxudong@le.com on 2016/10/25.
 */
@Component
public class VideoVoConverter {

    //    private static final Logger LOGGER = LoggerFactory.getLogger(VideoVoConverter.class);

    public Video toVideo(VideoParam videoParam) {
        Video video = new Video();
        if (null == videoParam) return video;
        video.setId(videoParam.getId());
        video.setTitle(videoParam.getTitle());
        video.setShortTitle(videoParam.getShortTitle());
        video.setDesc(videoParam.getDesc());
        video.setShareDesc(videoParam.getShareDesc());
        video.setChannel(videoParam.getChannel());
        video.setSubChannel(videoParam.getSubChannel());
        video.setCid(videoParam.getCid());
        if (null != videoParam.getVideoType()) {
            VideoContentType videoContentType = VideoContentType.findByValue(videoParam.getVideoType());
            video.setVideoType(null == videoContentType ? VideoContentType.OTHER : videoContentType);
        }
        video.setDrmFlag(videoParam.getDrmFlag());
        video.setIsOverView(videoParam.getIsOverView());
        video.setPublishSetting(videoParam.getPublishSetting());
        video.setShieldAreaType(videoParam.getShieldAreaType());
        video.setShieldCountries(this.getCountrySubdivisionCodeSet(videoParam.getShieldCountries()));
        video.setRecommend2Homepage(videoParam.getRecommend2Homepage());
        video.setAid(null == videoParam.getAid() ? 0L : videoParam.getAid());
        video.setRelatedIds(this.getLongSet(videoParam.getRelatedIds()));
        video.setDeleted(videoParam.getDeleted());
        video.setPlayPlatforms(this.getPlatformSet(videoParam.getPlayPlatforms()));
        video.setVrsPlayPlatforms(this.getPlatformSet(videoParam.getVrsPlayPlatforms()));
        if (!StringUtils.isEmpty(videoParam.getImages())) {
            Type type = new TypeReference<Map<String, ImageUrlExt>>() {
            }.getType();
            Map<String, ImageUrlExt> coverImage = JSONObject.parseObject(videoParam.getImages(), type);
            video.setImages(coverImage);
        }
        video.setIsClone(videoParam.getClone());
        video.setDuration(videoParam.getDuration());
        video.setRelatedIds(this.getLongSet(videoParam.getRelatedIds()));
        video.setPeriod(videoParam.getPeriod());
        if (null != videoParam.getContentRating()) {
            ContentRating contentRating = ContentRating.findByValue(videoParam.getContentRating());
            video.setContentRating(null == contentRating ? ContentRating.ALL : contentRating);
        }
        video.setIsPay(videoParam.getIsPay());
        video.setPayPlatforms(this.getPlatformSet(videoParam.getPayPlatforms()));
        video.setPublishTime(videoParam.getPublishTime());
//        video.setMarkCountry(this.getCountrySubdivisionCodeSet(videoParam.getMarkCountry()));
        video.setReasonOfExReview(videoParam.getReasonOfExReview());
        video.setStarLevel(videoParam.getStarLevel());
        video.setSource(videoParam.getSource());
        video.setVideoAuthor(videoParam.getVideoAuthor());
        video.setStatements(Arrays.asList(new String[]{videoParam.getStatement(), videoParam.getStatement2()}));
        video.setCommentFlag(videoParam.isCommentFlag());
        video.setDownloadPlatforms(this.getPlatformSet(videoParam.getDownloadPlatforms()));
        video.setRecordId(videoParam.getPlaybackId());
        video.setHighlightId(videoParam.getHighlightId());
        video.setSelfProducedProgramId(videoParam.getSelfProducedProgramId());
        video.setCloneAids(new ArrayList<>(this.getLongSet(videoParam.getCloneAids())));
        video.setMatchIds(Sets.newHashSet(LeStringUtils.commaString2LongList(videoParam.getMatchIds())));
        video.setRelatedContents(LeStringUtils.commaString2LongList(videoParam.getRelatedContents()));
        video.setAllImages(LeStringUtils.commaString2StringList(videoParam.getAllImages()));
        return video;
    }

    public VideoListParam toVideoListParam(Video video) {
        VideoListParam videoListParam = new VideoListParam();
        if (null == video) return videoListParam;
        videoListParam.setId(video.getId());
        videoListParam.setTitle(video.getTitle());
        videoListParam.setType(video.getVideoType());
        videoListParam.setLeecoVid(video.getLeecoVid());
        if (null != video.getImages() && null != video.getImages().get("169"))
            videoListParam.setPreviewImageUrl(video.getImages().get("169").getUrl());
        return videoListParam;
    }

    public List<VideoListParam> toVideoListParamList(List<Video> videos) {
        if (true == CollectionUtils.isEmpty(videos)) return null;
        List<VideoListParam> result = new ArrayList<>();
        for (Video video : videos) {
            if (null == video) continue;
            result.add(this.toVideoListParam(video));
        }
        return result;
    }

    private Set<String> getCountrySubdivisionCodeSet(String src) {
        Set<String> result = new HashSet<>();
        if (StringUtils.isEmpty(src))
            return result;
        String[] piece = src.split(",");
        for (String s : piece) {
            try {
                result.add(s);
            } catch (Exception e) {
                continue;
            }
        }
        return result;
    }

    private Set<Platform> getPlatformSet(String src) {
        Set<Platform> result = new HashSet<>();
        if (StringUtils.isEmpty(src))
            return result;
        String[] piece = src.split(",");
        for (String s : piece) {
            try {
                Integer tmp = Integer.parseInt(s);
                result.add(Platform.findByValue(tmp));
            } catch (Exception e) {
                continue;
            }
        }
        return result;
    }

    private Set<Long> getLongSet(String src) {
        Set<Long> result = new HashSet<>();
        if (StringUtils.isEmpty(src))
            return result;
        String[] piece = src.split(",");
        for (String s : piece) {
            try {
                Long tmp = Long.parseLong(s);
                result.add(tmp);
            } catch (Exception e) {
                continue;
            }
        }
        return result;
    }

    private Set<Integer> getIntegerSet(String src) {
        Set<Integer> result = new HashSet<>();
        if (StringUtils.isEmpty(src))
            return result;
        String[] piece = src.split(",");
        for (String s : piece) {
            try {
                Integer tmp = Integer.parseInt(s);
                result.add(tmp);
            } catch (Exception e) {
                continue;
            }
        }
        return result;
    }
}
