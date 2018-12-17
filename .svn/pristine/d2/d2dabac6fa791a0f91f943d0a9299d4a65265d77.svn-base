package com.lesports.qmt.web.api.core.creater;

import com.google.common.collect.Maps;
import com.lesports.api.common.CallerParam;
import com.lesports.api.common.Platform;
import com.lesports.model.RecommendRec;
import com.lesports.qmt.sbc.api.common.VideoContentType;
import com.lesports.qmt.sbc.api.dto.TComboEpisode;
import com.lesports.qmt.sbc.api.dto.TSimpleVideo;
import com.lesports.qmt.sbc.api.dto.TVideo;
import com.lesports.qmt.sbc.client.QmtSbcApis;
import com.lesports.qmt.web.api.core.util.Constants;
import com.lesports.qmt.web.api.core.util.ImageUtils;
import com.lesports.qmt.web.api.core.vo.AlbumVideo;
import com.lesports.qmt.web.api.core.vo.VideoVo;
import com.lesports.utils.LeDateUtils;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
* User: ellios
* Time: 15-11-5 : 上午10:56
*/
@Component("videoVoCreater")
public class VideoVoCreater {

    private static final Logger LOG = LoggerFactory.getLogger(VideoVoCreater.class);

    /**
     * 为video赋值
     *
     * @param tVideo
     * @return
     */
    public VideoVo createVideoVo(TVideo tVideo) {
        VideoVo videoVo = new VideoVo();
        videoVo.setName(tVideo.getName());
        videoVo.setCreateTime(tVideo.getCreateAt());
        videoVo.setId(tVideo.getId());
        videoVo.setCommentId(tVideo.getCommentId());
        videoVo.setDesc(tVideo.getDesc());
        videoVo.setDuration(tVideo.getDuration() == 0 ? null : tVideo.getDuration() + "");
        videoVo.setImageUrl(getVideoImageMap(tVideo));
        videoVo.setVid(tVideo.getId());
        //视频类型: 0:回放 1:集锦 2:战报 3:正片 20:其它
        videoVo.setType(tVideo.getContentType() != null ? tVideo.getContentType().ordinal() : VideoContentType.OTHER.ordinal());
//		videoVo.setIsPay(tVideo.getIsPay());//todo
        return videoVo;
    }

    public VideoVo createVideoVo(RecommendRec recommendRec) {
        VideoVo videoVo = new VideoVo();
        videoVo.setName(recommendRec.getTitle());
        videoVo.setId(recommendRec.getVid());
        Map<String,String> map = Maps.newHashMap();
        map.put("400*225",recommendRec.getPicurl());
        videoVo.setImageUrl(map);
        return videoVo;
    }

    public AlbumVideo createAVideo(TVideo tVideo, CallerParam caller) {
        AlbumVideo albumVideo = new AlbumVideo();
        albumVideo.setName(tVideo.getName());
        albumVideo.setCreateTime(tVideo.getCreateAt());
        albumVideo.setId(tVideo.getId());
        albumVideo.setCommentId(tVideo.getCommentId());
        albumVideo.setDesc(tVideo.getDesc());
        albumVideo.setDuration(tVideo.getDuration() == 0 ? null : tVideo.getDuration() + "");
        albumVideo.setImageUrl(getVideoImageMap(tVideo));
        albumVideo.setVid(tVideo.getId());
        //视频类型: 0:回放 1:集锦 2:战报 3:正片 20:其它
        albumVideo.setType(tVideo.getContentType() != null ? tVideo.getContentType().ordinal() : VideoContentType.OTHER.ordinal());
        fillPlayTimeAndFriendlyTime(albumVideo, tVideo);
        return albumVideo;
    }

    /**
     * 添加发布时间和格式化视频播放时长
     *
     * @param tVideo
     * @return
     */
    public void fillPlayTimeAndFriendlyTime(AlbumVideo albumVideo, TVideo tVideo) {
        String s = LeDateUtils.formatHHMMSS(tVideo.getDuration(), true);
        if (!s.contains(":")) {
            s = "00:" + s;
        }
        albumVideo.setPlayTime(s);
        albumVideo.setFriendTime(LeDateUtils.getHumanizedInterval(tVideo.getCreateAt()));
    }

    /**
     * 根据节目获取VideoVo
     * 比赛大厅相关视频专用
     * @param tComboEpisode
     * @return
     */
    public VideoVo createVideoVo4Hall(TComboEpisode tComboEpisode,CallerParam caller,Platform platform) {
        VideoVo videoVo = new VideoVo();
        try {
            if (tComboEpisode == null) {
                return null;
            }
            if (null != tComboEpisode) {
                List<TSimpleVideo> tSimpleVideos = tComboEpisode.getVideos();
                if(CollectionUtils.isNotEmpty(tSimpleVideos)){
                    for(TSimpleVideo simpleVideo : tSimpleVideos){
                        if(simpleVideo.getType().equals(VideoContentType.RECORD)){
                            TVideo tVideo = QmtSbcApis.getTVideoById(simpleVideo.getVid(), caller);
                            if (tVideo == null) {
                                return null;
                            }
                            if (CollectionUtils.isEmpty(tVideo.getPlatforms()) || !tVideo.getPlatforms().contains(platform)) {
                                LOG.info("video : {} not allow play in platform : {}.", tVideo.getId(),platform);
                                return null;
                            }
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



            }

        } catch (Exception e) {
            LOG.error("createVideoVo error ! episodeId = {}, e = {}", tComboEpisode.getId(), e.getMessage(), e);
        }
        return videoVo;
    }

    public VideoVo createVideoVo(TComboEpisode tComboEpisode) {
        VideoVo videoVo = new VideoVo();
        try {
            if (tComboEpisode == null) {
                return null;
            }
            if (null != tComboEpisode) {

                videoVo.setName(tComboEpisode.getName());
                videoVo.setImageUrl(tComboEpisode.getImages());
                videoVo.setDesc(tComboEpisode.getDesc());
                videoVo.setCommentId(tComboEpisode.getCommentId());
                List<TSimpleVideo> tSimpleVideos = tComboEpisode.getVideos();
                if(CollectionUtils.isNotEmpty(tSimpleVideos)){
                    for(TSimpleVideo simpleVideo : tSimpleVideos){
                        if(simpleVideo.getType().equals(VideoContentType.FEATURE)){
                            videoVo.setVid(simpleVideo.getVid());
                            videoVo.setId(simpleVideo.getVid());
                            videoVo.setCreateTime(simpleVideo.getCreateTime());
                            videoVo.setDuration(simpleVideo.getDuration()+"");
                            videoVo.setType(0);
                        }
                    }
                }

            }

        } catch (Exception e) {
            LOG.error("createVideoVo error ! episodeId = {}, e = {}", tComboEpisode.getId(), e.getMessage(), e);
        }
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
            if (picAll.get("43") != null) {//400*300
                returnMap.put(Constants.VIDEO_NEWS_IMAGE_43, picAll.get(Constants.VIDEO_NEWS_IMAGE_SCALE_43));
            } else {
                returnMap.put(Constants.VIDEO_NEWS_IMAGE_43, "");
            }
            if (picAll.get("169") != null) {//400*225
                returnMap.put(Constants.VIDEO_NEWS_IMAGE_169, picAll.get(Constants.VIDEO_NEWS_IMAGE_SCALE_169));
            } else {
                returnMap.put(Constants.VIDEO_NEWS_IMAGE_169, "");
            }
            if (tVideo.isHasBigImage() && picAll.get("169") != null) {//960*540
                returnMap.put(Constants.VIDEO_NEWS_IMAGE_BIG_169, picAll.get(Constants.VIDEO_NEWS_IMAGE_SCALE_169));
            } else {
                returnMap.put(Constants.VIDEO_NEWS_IMAGE_BIG_169, "");
            }
        }
        return returnMap;
    }


}
