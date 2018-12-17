package com.lesports.qmt.web.api.core.creater;

import client.SopsApis;
import com.lesports.api.common.CallerParam;
import com.lesports.qmt.web.api.core.vo.ChannelVo;
import com.lesports.sms.api.vo.TCarousel;
import com.lesports.utils.CarouselApis;
import com.lesports.utils.pojo.LiveChannels;
import com.lesports.utils.pojo.LivePlayBill;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created by ruiyuansheng on 2015/9/8.
 */
@Component("channelsVoCreater")
public class ChannelsVoCreater {
    private static final Logger LOG = LoggerFactory.getLogger(ChannelsVoCreater.class);

    public ChannelVo createChannelVoOld(int clientId,LiveChannels.LiveChannel liveChannel,CallerParam caller){

        ChannelVo channelVo = new ChannelVo();
        try{
            if(null == liveChannel){
                return null;
            }
            long channelId = liveChannel.getChannelId();
            TCarousel tCarousel = SopsApis.getTCarouselByChannelId(channelId, caller);
            channelVo.setChannelId(channelId);
            channelVo.setIsDrm(CarouselApis.isDrm(liveChannel));
            channelVo.setChannelName(liveChannel.getChannelName());
            channelVo.setCurrentEpisodeTitle(getCurrentEpisodeTitle(clientId,channelId));
            int order = 0;
            if(null != tCarousel) {
                channelVo.setPostOrigin(tCarousel.getImageUrl());
                order = tCarousel.getSort();
            }
            channelVo.setOrder(order);

        }catch (Exception e) {
            LOG.error("createChannelVo error ! channelId = {}, e = {}", liveChannel.getChannelId(), e.getMessage(), e);
        }
        return channelVo;
    }

    public ChannelVo createChannelVo(TCarousel tCarousel, LiveChannels.LiveChannel liveChannel, int clientId){

        ChannelVo channelVo = new ChannelVo();
        try{
            if(null == liveChannel){
                return null;
            }
            long channelId = liveChannel.getChannelId();
            channelVo.setChannelId(channelId);
            channelVo.setIsDrm(CarouselApis.isDrm(liveChannel));
            channelVo.setChannelName(liveChannel.getChannelName());
            channelVo.setCurrentEpisodeTitle(getCurrentEpisodeTitle(clientId, channelId));
            channelVo.setShowCornerMark(tCarousel.isShowCornerMark());
			channelVo.setIsPay(liveChannel.getIsPay());
            int order = 0;
            if(null != tCarousel) {
                channelVo.setPostOrigin(tCarousel.getImageUrl());
                order = tCarousel.getSort();
            }
            channelVo.setOrder(order);

        }catch (Exception e) {
            LOG.error("createChannelVo error ! channelId = {}, e = {}", liveChannel.getChannelId(), e.getMessage(), e);
        }
        return channelVo;
    }

    private String getCurrentEpisodeTitle(int clientId,long channelId){
        String title = "";
        LivePlayBill.CurrentRow currentRow = CarouselApis.getCurrent(clientId, (int) channelId);
        if(null != currentRow && currentRow.getCur() != null) {
            title = currentRow.getCur().getTitle();
        }
        return title;
    }
}
