package com.lesports.qmt.transcode.web.param;

import com.letv.urus.liveroom.api.dto.StreamDTO;
import com.letv.urus.streamchannel.bo.Channel;

import java.util.List;

/**
 * trunk.
 *
 * @author pangchuanxiao
 * @since 2016/11/18
 */
public class LiveChannelVo {
    private Channel channelDetail ;
    private String streamName;
    private List<StreamDTO> streams;

    public String getStreamName() {
        return streamName;
    }

    public void setStreamName(String streamName) {
        this.streamName = streamName;
    }

    public Channel getChannelDetail() {
        return channelDetail;
    }

    public void setChannelDetail(Channel channelDetail) {
        this.channelDetail = channelDetail;
    }

    public List<StreamDTO> getStreams() {
        return streams;
    }

    public void setStreams(List<StreamDTO> streams) {
        this.streams = streams;
    }
}
