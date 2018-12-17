package com.lesports.qmt.web.api.core.creater;

import com.google.common.collect.Maps;
import com.lesports.api.common.CallerParam;
import com.lesports.api.common.Platform;
import com.lesports.qmt.config.api.dto.TCaller;
import com.lesports.qmt.config.client.QmtConfigApis;
import com.lesports.qmt.sbc.api.dto.*;
import com.lesports.qmt.sbc.api.dto.TComboEpisode;
import com.lesports.qmt.sbc.api.dto.TSimpleVideo;
import com.lesports.qmt.sbc.api.dto.TVideo;
import com.lesports.qmt.sbc.client.QmtSbcApis;
import com.lesports.qmt.sbc.client.QmtSbcResourceApis;
import com.lesports.qmt.web.api.core.util.Constants;
import com.lesports.qmt.web.api.core.util.ImageUtils;
import com.lesports.qmt.web.api.core.vo.AlbumVo;
import com.lesports.qmt.web.api.core.vo.LatestEpisodeVo;
import com.lesports.qmt.web.api.core.vo.VideoVo;
import com.lesports.sms.api.common.VideoContentType;
import com.lesports.sms.api.vo.*;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Created by ruiyuansheng on 2015/7/30.
 */
@Component("albumVoCreater")
public class AlbumVoCreater {
    private static final Logger LOG = LoggerFactory.getLogger(AlbumVoCreater.class);

    public AlbumVo createAlbumVo(TProgramAlbum album,CallerParam callerParam){

        AlbumVo albumVo = new AlbumVo();
        try {
            if (album == null || album .getLatestEpisode() == null || album.getLatestVideo() == null) {
                return null;
            }
            albumVo.setId(album.getId());
            albumVo.setName(album.getName());
//			albumVo.setAbbreviation(album.getAbbreviation());
            albumVo.setLogo(album.getLogo().get("169"));
            albumVo.setOrder(album.getRecommendOrder());
            TVideo tVideo = album.getLatestVideo();
			TComboEpisode tComboEpisode = album.getLatestEpisode();
			if(null != tComboEpisode) {
				albumVo.setLatestEpisodeVo(createEpisodeVo(tComboEpisode));
			}
			if(null != tVideo){
               VideoVo videoVo = createVideoVo(tVideo,callerParam);
                if(null == videoVo){
                    return null;
                }
                albumVo.setLatestVideoVo(videoVo);
                albumVo.setImages(videoVo.getImageUrl());
                albumVo.setLatestTime(videoVo.getCreateTime());
            }

        } catch (Exception e) {
            LOG.error("createAlbumVo error ! albumId = {}, e = {}", album.getId(), e.getMessage(), e);
        }
        return albumVo;


    }

    public VideoVo createVideoVo(TVideo tVideo,CallerParam callerParam) {
        VideoVo videoVo = new VideoVo();
//        tVideo = QmtSbcApis.getTVideoById(tVideo.getId(), callerParam);
//        if(null == tVideo){
//            return null;
//        }
        videoVo.setName(tVideo.getName());
        videoVo.setCreateTime(tVideo.getCreateAt());
        videoVo.setId(tVideo.getLeecoVid());
        videoVo.setCommentId(tVideo.getCommentId());
        videoVo.setDesc(tVideo.getDesc());
        videoVo.setDuration(tVideo.getDuration() == 0 ? null : tVideo.getDuration() + "");
        videoVo.setImageUrl(getVideoImageMap(tVideo));
        videoVo.setVid(tVideo.getLeecoVid());
        //视频类型: 0:回放 1:集锦 2:战报 3:正片 20:其它
        videoVo.setType(tVideo.getContentType() != null ? tVideo.getContentType().ordinal() : VideoContentType.OTHER.ordinal());
        return videoVo;
    }

    /**
     * 视频获取图片信息
     *
     * @param tVideo
     * @return
     */
    private Map<String, String> getVideoImageMap(TVideo tVideo) {
        Map<String, String> returnMap = Maps.newHashMap();
        Map<String, String> picAll = tVideo.getImages();
        if (picAll != null) {
            if (picAll.get(Constants.VIDEO_NEWS_IMAGE_SCALE_43) != null) {//400*300
                returnMap.put(Constants.VIDEO_NEWS_IMAGE_43,  picAll.get(Constants.VIDEO_NEWS_IMAGE_SCALE_43));
            } else {
                returnMap.put(Constants.VIDEO_NEWS_IMAGE_43, "");
            }
            if (picAll.get(Constants.VIDEO_NEWS_IMAGE_SCALE_169) != null) {//400*225
                returnMap.put(Constants.VIDEO_NEWS_IMAGE_169,  picAll.get(Constants.VIDEO_NEWS_IMAGE_SCALE_169));
            } else {
                returnMap.put(Constants.VIDEO_NEWS_IMAGE_169, "");
            }
            if (picAll.get(Constants.VIDEO_NEWS_IMAGE_SCALE_34) != null) {//399*532
                returnMap.put("399*532",  picAll.get(Constants.VIDEO_NEWS_IMAGE_SCALE_34));
            } else {
                returnMap.put(Constants.VIDEO_NEWS_IMAGE_34, "");
            }
            if (tVideo.isHasBigImage() && picAll.get(Constants.VIDEO_NEWS_IMAGE_SCALE_169) != null) {//960*540
                returnMap.put(Constants.VIDEO_NEWS_IMAGE_BIG_169,  picAll.get(Constants.VIDEO_NEWS_IMAGE_SCALE_169));
            } else {
                returnMap.put(Constants.VIDEO_NEWS_IMAGE_BIG_169, "");
            }
        }
        return returnMap;
    }

    private LatestEpisodeVo createEpisodeVo(TComboEpisode tComboEpisode){
        LatestEpisodeVo latestEpisodeVo = new LatestEpisodeVo();
        latestEpisodeVo.setId(tComboEpisode.getId());
        latestEpisodeVo.setName(tComboEpisode.getName());
        latestEpisodeVo.setStartTime(tComboEpisode.getStartTime());
        latestEpisodeVo.setPeriods(tComboEpisode.getPeriods());
        latestEpisodeVo.setAid(tComboEpisode.getAid());
        latestEpisodeVo.setImages(tComboEpisode.getImages());
        latestEpisodeVo.setDesc(tComboEpisode.getDesc());
        latestEpisodeVo.setDuration(tComboEpisode.getDuration());
        latestEpisodeVo.setCommentId(tComboEpisode.getCommentId());
        return latestEpisodeVo;
    }

    private VideoVo createVideoVo(TComboEpisode tComboEpisode,CallerParam caller){
        VideoVo videoVo = new VideoVo();
        List<TSimpleVideo> tSimpleVideos = tComboEpisode.getVideos();
        if(CollectionUtils.isNotEmpty(tSimpleVideos)){
            for(TSimpleVideo simpleVideo : tSimpleVideos){
                if(simpleVideo.getType().equals(VideoContentType.RECORD)){
//                    TVideo tVideo = SopsApis.getTVideoById(simpleVideo.getVid(), caller);
//                    if (tVideo == null) {
//                        return null;
//                    }
                    videoVo.setVid(simpleVideo.getVid());
                    videoVo.setId(simpleVideo.getVid());
                    videoVo.setCreateTime(simpleVideo.getCreateTime());
                    videoVo.setDuration(simpleVideo.getDuration()+"");
                    videoVo.setType(0);
                }
            }
        }
        videoVo.setName(tComboEpisode.getName());
        videoVo.setImageUrl(tComboEpisode.getImages());
        videoVo.setDesc(tComboEpisode.getDesc());
        videoVo.setCommentId(tComboEpisode.getCommentId());

        return videoVo;
    }

    private Platform getPlatFormFromCaller(long callerId) {
        TCaller tCaller = QmtConfigApis.getTCallerById(callerId);
        if (null == tCaller) {
            throw new IllegalArgumentException("illegal callerId : " + callerId);
        }

        return tCaller.getPlatform();

    }

}
