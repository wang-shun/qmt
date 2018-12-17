package com.lesports.qmt.web.api.core.service.impl;

//import client.SopsApis;

import client.SopsApis;
import com.google.common.collect.Lists;
import com.lesports.api.common.CallerParam;
import com.lesports.api.common.Platform;
import com.lesports.qmt.sbc.api.dto.TComboEpisode;
import com.lesports.qmt.sbc.client.QmtSbcEpisodeApis;
import com.lesports.qmt.web.api.core.creater.ChannelsVoCreater;
import com.lesports.qmt.web.api.core.service.CarouselService;
import com.lesports.qmt.web.api.core.service.VideoPlayerService;
import com.lesports.qmt.web.api.core.vo.ChannelVo;
import com.lesports.sms.api.vo.TCarousel;
import com.lesports.utils.CarouselApis;
import com.lesports.utils.pojo.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.ws.rs.DefaultValue;
import java.util.*;

/**
 * Created by ruiyuansheng on 2015/9/8.
 */
@Service("carouselService")
public class CarouseServiceImpl implements CarouselService {
    private static final Logger LOG = LoggerFactory.getLogger(CarouseServiceImpl.class);
    @Resource
    private ChannelsVoCreater channelsVoCreater;

    @Inject
    private VideoPlayerService videoPlayerService;

    @Override
    public List<ChannelVo> getChannelsOld(int clientId, CallerParam caller) {
        List<ChannelVo> returnList = Lists.newArrayList();
        List<LiveChannels.LiveChannel> liveChannels = CarouselApis.getChannelsForApiWeb(clientId);

        if (CollectionUtils.isNotEmpty(liveChannels)) {
            for (LiveChannels.LiveChannel liveChannel : liveChannels) {
                ChannelVo channelVo = channelsVoCreater.createChannelVoOld(clientId, liveChannel, caller);
                returnList.add(channelVo);
            }
        }

        if (CollectionUtils.isEmpty(returnList)) {
            return Collections.EMPTY_LIST;
        }
        Collections.sort(returnList, new Comparator<ChannelVo>() {
            @Override
            public int compare(ChannelVo o1, ChannelVo o2) {
                return (o1.getOrder() - o2.getOrder());
            }
        });
        return returnList;
    }

    @Override
    public List<ChannelVo> getChannels(Platform platform, int clientId, CallerParam caller) {
        List<ChannelVo> returnList = Lists.newArrayList();
        //获取轮播台map: key: channelId  value:对应的数据
        Map<Long, LiveChannels.LiveChannel> channelsMap = CarouselApis.getChannelsMap(clientId);

        if (MapUtils.isNotEmpty(channelsMap)) {
            //获取数据库里面的轮播台的顺序
            List<TCarousel> tCarousels = SopsApis.getTCarouselsByPlatform(com.lesports.sms.api.common.Platform.findByValue(platform.getValue()), caller);
            for (TCarousel tCarousel : tCarousels) {
                LiveChannels.LiveChannel channel = channelsMap.get(tCarousel.getChannelId());
                ChannelVo channelVo = channelsVoCreater.createChannelVo(tCarousel, channel, clientId);
                if (channelVo != null) {
                    returnList.add(channelVo);
                }
            }
        }

        if (CollectionUtils.isEmpty(returnList)) {
            return Collections.EMPTY_LIST;
        }
        return returnList;
    }

    @Override
    public List<LivePlayBill.RowContent> getChannelLivePlayBill(int clientId, int channelId, String date, CallerParam callerParam) {
        List<LivePlayBill.RowContent> results = new ArrayList<>();
        List<LivePlayBill.RowContent> rowContents = CarouselApis.getWholeDay(clientId, channelId, date);

        if (CollectionUtils.isNotEmpty(rowContents)) {
            for (LivePlayBill.RowContent rowContent : rowContents) {
                if (rowContent.getProgramType() == 1 && null != rowContent.getLiveId()) {
                    TComboEpisode tComboEpisode = QmtSbcEpisodeApis.getEpisodeByLiveId(rowContent.getLiveId(), callerParam);
                    if (null != tComboEpisode) {
                        long eid = tComboEpisode.getId();
                        if (eid > 0) {
                            rowContent.setEid(eid);
                        }
                    }
                }
                //TV图片用16:9尺寸,320*180
                updateViewPic(rowContent);
                results.add(rowContent);
            }
        }
        return results;
    }

    @Override
    public LivePlayBill.CurrentRow getCurrentChannel(int clientId, int channelId) {
        LivePlayBill.CurrentRow currentRow = CarouselApis.getCurrent(clientId, channelId);
        if (null != currentRow) {
            currentRow.setCur(updateViewPic(currentRow.getCur()));
            currentRow.setNext(updateViewPic(currentRow.getNext()));
            currentRow.setPre(updateViewPic(currentRow.getPre()));
        }
        return currentRow;
    }

    @Override
    public LiveStreamRows getRows(int clientId, int channelId, String token, String flag, String devicekey, String terminal) {
        LiveStreamRows rows = CarouselApis.getStreams(clientId, channelId);
        if (null != rows && CollectionUtils.isNotEmpty(rows.getRows())) {
            Iterator<LiveStreamRows.LiveStreamRow> iterable = rows.getRows().iterator();
            while (iterable.hasNext()) {
                LiveStreamRows.LiveStreamRow row = iterable.next();
                if (null != row && row.getRateType().contains("1080p")) {
                    iterable.remove();
                }
            }
        }
        if (StringUtils.isNotBlank(token) && StringUtils.isNotBlank(flag)) {
            Boolean landing = videoPlayerService.eachPlayLanding(token, flag, devicekey, terminal);
            if (landing) {
                rows.setStatus(PlayerErrorCode.BUSI_USER_HAS_LOGIN.getCode());
            }
        }
        return rows;
    }

    @Override
    public LiveChannelDetail getLiveChannelDetail(@DefaultValue("0") int clientId, @DefaultValue("0") int channelId) {
        return CarouselApis.getChannelDetail(clientId, channelId);
    }

    private LivePlayBill.RowContent updateViewPic(LivePlayBill.RowContent rowContent) {
        rowContent.setViewPic(rowContent.getViewPic().replaceAll("400_300", "320_180"));
        rowContent.setViewPic(rowContent.getViewPic().replaceAll("120_90", "320_180"));
        return rowContent;
    }

}
