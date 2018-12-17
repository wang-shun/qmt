package com.lesports.qmt.op.web.api.core.creater;

import com.google.common.collect.Maps;
import com.lesports.api.common.CallerParam;
import com.lesports.qmt.op.web.api.core.util.Constants;
import com.lesports.qmt.op.web.api.core.vo.VideoVo;
import com.lesports.qmt.sbc.api.common.VideoContentType;
import com.lesports.qmt.sbc.api.dto.TVideo;
import com.lesports.qmt.sbd.api.dto.TCompetition;
import com.lesports.qmt.sbd.client.SbdCompetitionApis;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * User: ellios
 * Time: 15-11-5 : 上午10:56
 */
@Component
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
        TCompetition competition = SbdCompetitionApis.getTCompetitionById(tVideo.getCid(), new CallerParam(2001));
        videoVo.setCompetitionName(competition != null ? competition.getName() : "");
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
            if (picAll.get(Constants.VIDEO_NEWS_IMAGE_43) != null) {//400*300
                returnMap.put(Constants.VIDEO_NEWS_IMAGE_43, picAll.get(Constants.VIDEO_NEWS_IMAGE_43));
            } else {
                returnMap.put(Constants.VIDEO_NEWS_IMAGE_43, "");
            }
            if (picAll.get(Constants.VIDEO_NEWS_IMAGE_169) != null) {//400*225
                returnMap.put(Constants.VIDEO_NEWS_IMAGE_169, picAll.get(Constants.VIDEO_NEWS_IMAGE_169));
            } else {
                returnMap.put(Constants.VIDEO_NEWS_IMAGE_169, "");
            }
            if (tVideo.isHasBigImage() && picAll.get(Constants.VIDEO_NEWS_IMAGE_BIG_169) != null) {//960*540
                returnMap.put(Constants.VIDEO_NEWS_IMAGE_BIG_169, picAll.get(Constants.VIDEO_NEWS_IMAGE_BIG_169));
            } else {
                returnMap.put(Constants.VIDEO_NEWS_IMAGE_BIG_169, "");
            }
        }
        return returnMap;
    }

}
