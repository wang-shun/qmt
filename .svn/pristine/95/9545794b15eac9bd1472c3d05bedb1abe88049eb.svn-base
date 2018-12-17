package com.lesports.qmt.web.api.core.service.impl;

import com.lesports.model.LiveVideo;
import com.lesports.model.LiveVideoRes;
import com.lesports.qmt.web.api.core.service.ZhangyuTvPlayerService;
import com.lesports.utils.pojo.PlayerErrorCode;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lufei1 on 2016/10/11.
 */
@Service("zhangyuTvPlayerService")
public class ZhangyuTvPlayerServiceImpl implements ZhangyuTvPlayerService {


    @Override
    public LiveVideoRes dealZhangyuTvLive(String liveId) {
        LiveVideoRes liveVideoRes = new LiveVideoRes();
        liveVideoRes.setIsPay(0);
        liveVideoRes.setDrmFlag(0);
        liveVideoRes.setScreenings("");
        liveVideoRes.setStatus(PlayerErrorCode.SUCCESS.getCode());
        liveVideoRes.setLiveStartTime("");
        liveVideoRes.setInfos(getInfos(liveId));
        return liveVideoRes;
    }

    private Map<String, LiveVideo> getInfos(String liveid) {
        Map<String, LiveVideo> infoMaps = new HashMap<>();
        final String cid = liveid.replaceFirst("^zhangyutv_", "");
        final Map<String, String> multiBitrate = new HashMap<String, String>() {{
            put("flv_1000", cid);                          // 高清
            put("flv_720p", String.format("%s_19", cid));  // 标清(1200kb/s, 760p, 25fps)
            put("flv_350", String.format("%s_16", cid));  // 流畅( 800kb/s, 540p, 25fps)
        }};
        for (Map.Entry<String, String> entry : multiBitrate.entrySet()) {
            final String value = entry.getValue();
            LiveVideo liveVideo = new LiveVideo();
            liveVideo.setCode(1);
            liveVideo.setUrl(String.format("http://download.rtmp.kukuplay.com/live/%s/desc.m3u8?stream_id=%s", value, value));
            liveVideo.setIsPay(0);
            liveVideo.setStreamName(value);
            infoMaps.put(entry.getKey(), liveVideo);
        }
        return infoMaps;
    }

}
