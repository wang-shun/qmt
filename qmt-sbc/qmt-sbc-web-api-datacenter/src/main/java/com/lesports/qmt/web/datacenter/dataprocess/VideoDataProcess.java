package com.lesports.qmt.web.datacenter.dataprocess;

import com.lesports.api.common.CallerParam;
import com.lesports.qmt.sbc.api.dto.TVideo;
import com.lesports.qmt.sbc.client.QmtSbcApis;
import com.lesports.qmt.web.datacenter.vo.VideoVo;

/**
 * create by wangjichuan  date:16-12-22 time:14:23
 *
 * 点播的数据处理器
 */
public class VideoDataProcess implements DataProcess<TVideo,VideoVo> {
    @Override
    public VideoVo getEntity() {
        return new VideoVo();
    }

    @Override
    public void constructManualRemoteData(VideoVo videoVo, String itemId, CallerParam callerParam) {
        TVideo tVideo = QmtSbcApis.getTVideoById(Long.parseLong(itemId),callerParam);
    }
}
