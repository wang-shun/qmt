package com.lesports.qmt.web.api.core.service;

import com.lesports.api.common.CallerParam;
import com.lesports.api.common.Platform;
import com.lesports.qmt.web.api.core.vo.ChannelVo;
import com.lesports.utils.pojo.LiveChannelDetail;
import com.lesports.utils.pojo.LivePlayBill;
import com.lesports.utils.pojo.LiveStreamRows;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.QueryParam;
import java.util.List;

/**
 * Created by ruiyuansheng on 2015/9/8.
 */
public interface CarouselService {

    List<ChannelVo> getChannelsOld(int clientId, CallerParam caller);
    List<ChannelVo> getChannels(Platform platform, int clientId, CallerParam caller);

    /**
     * 获取前后一天加今天的所有节目
     * @param clientId
     * @param channelId
     * @param date
     * @return
     */
    List<LivePlayBill.RowContent> getChannelLivePlayBill(int clientId, int channelId, String date, CallerParam callerParam);

    LivePlayBill.CurrentRow getCurrentChannel(int clientId, int channelId);

    LiveStreamRows getRows(int clientId, int channelId, String token, String flag, String devicekey, String terminal);


    LiveChannelDetail getLiveChannelDetail(@QueryParam("clientId") @DefaultValue("0") int clientId,
                                           @QueryParam("channelId") @DefaultValue("0") int channelId);
}
