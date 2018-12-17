package com.lesports.qmt.transcode.web.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.lesports.qmt.sbc.api.common.TranscodeStatus;
import com.lesports.qmt.sbc.client.QmtSbcVideoInternalApis;
import com.lesports.qmt.sbc.model.VideoFormat;
import com.lesports.qmt.sbc.model.VideoMedium;
import com.lesports.qmt.transcode.web.service.VideoMediumService;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * trunk.
 *
 * @author pangchuanxiao
 * @since 2016/10/29
 */
@Service("videoMediumService")
public class VideoMediumServiceImpl implements VideoMediumService {
    private static final Logger LOGGER = LoggerFactory.getLogger(VideoMediumServiceImpl.class);
    @Override
    public VideoMedium createVideoMedium(long videoId, List<Integer> formats) {
        VideoMedium videoMedium = new VideoMedium();
        videoMedium.setVideoId(videoId);
        if (CollectionUtils.isNotEmpty(formats)) {
            List<VideoFormat> videoFormats = new ArrayList<>();
            for (Integer format : formats) {
                VideoFormat videoFormat  = new VideoFormat();
                videoFormat.setStatus(TranscodeStatus.TRANSCODING);
                videoFormat.setCodeRate(format);
                videoFormats.add(videoFormat);
            }
            videoMedium.setFormats(videoFormats);
        }
        long mediumId = QmtSbcVideoInternalApis.saveVideoMedium(videoMedium);
        LOGGER.info("Save video medium : {}, id : {}.", JSONObject.toJSONString(videoMedium), mediumId);
        return videoMedium;
    }
}
