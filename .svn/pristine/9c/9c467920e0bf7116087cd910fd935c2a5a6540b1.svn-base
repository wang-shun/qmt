package com.lesports.qmt.msg.handler.impl;

import com.lesports.qmt.msg.constant.Constants;
import com.lesports.qmt.msg.core.*;
import com.lesports.qmt.msg.handler.AbstractMessageHandler;
import com.lesports.qmt.msg.handler.IMessageHandler;
import com.lesports.qmt.msg.producer.SdlcSwiftMessageApis;
import com.lesports.qmt.msg.service.EpisodeService;
import com.lesports.qmt.msg.service.LiveService;
import com.lesports.qmt.msg.service.MatchService;
import com.lesports.qmt.sbc.client.QmtSbcEpisodeInternalApis;
import com.lesports.qmt.sbc.model.Episode;
import com.lesports.utils.CallerUtils;
import com.lesports.utils.math.LeNumberUtils;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Set;

/**
 * User: ellios
 * Time: 15-6-28 : 下午10:07
 */
@Component
public class EpisodeMessageHandler extends AbstractMessageHandler implements IMessageHandler {

    private static final Logger logger = LoggerFactory.getLogger(EpisodeMessageHandler.class);

    @Resource
    private EpisodeService episodeService;
    @Resource
    private MatchService matchService;
    @Resource
    private LiveService liveService;

    @Override
    public boolean handleSync(LeMessage message) {
        return this.handle(message);
    }

    public boolean handle(LeMessage message) {
        long episodeId = message.getEntityId();
        boolean result = episodeService.deleteEpisodeApiCache(episodeId)
                & episodeService.deleteSmsRealtimeWebEpisodeCache(episodeId);
        logger.info("delete episode cache for {}, result {}.", episodeId, result);
        MessageContent content = message.getContent();
        Episode episode = QmtSbcEpisodeInternalApis.getEpisodeById(episodeId);
        if (null != content && null != content.getMessageEvent() && content.getMessageEvent() == MessageEvent.STATUS_CHANGED
                || null != content && CollectionUtils.isNotEmpty(content.getFields()) && content.getFields().contains(Field.LIVE_STREAMS)) {
            if (null != episode) {
                if (LeNumberUtils.toLong(episode.getMid()) > 0) {
                    result = matchService.deleteMatchPage(episode.getMid());
                    logger.info("delete match page for {} as episode {} status changed, result {}.", episode.getMid(), episodeId, result);
                }

                Set<Episode.LiveStream> liveStreams = episode.getLiveStreams();
                if (CollectionUtils.isNotEmpty(liveStreams)) {
                    for (Episode.LiveStream liveStream : liveStreams) {
                        result = liveService.deleteLivePage(LeNumberUtils.toLong(liveStream.getId()));
                        logger.info("delete live page for {} as episode {} status changed, result {}.", liveStream.getId(), episodeId, result);
                    }
                }
            }
        }

        if (AreaType.CN.equals(Constants.areaType)) { //只发大陆地区同步消息
            SdlcSwiftMessageApis.sendMsgAsync(message);
        }
        //加载redis数据
        //SopsApis.getTComboEpisodeById(episodeId, CallerUtils.getDefaultCaller());

        return result;
    }

    @Override
    protected Logger getLogger() {
        return logger;
    }
}
