package com.lesports.qmt.sbc.adapter;

import com.google.common.collect.Lists;
import com.lesports.api.common.CallerParam;
import com.lesports.qmt.sbc.client.QmtSbcLiveInternalApis;
import com.lesports.qmt.sbc.model.Episode;
import com.lesports.qmt.sbc.model.Live;
import com.lesports.qmt.sbc.model.RelatedItem;
import com.lesports.qmt.sbc.utils.QmtSbcUtils;
import com.lesports.qmt.sbc.vo.EpisodeVo;
import com.lesports.utils.CallerUtils;
import com.lesports.utils.math.LeNumberUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by lufei1 on 2016/11/18.
 */
@Component
public class EpisodeVoAdapter {


    public EpisodeVo adapterVo(Episode episode, CallerParam caller) {
        if(episode == null){
            return new EpisodeVo();
        }
        EpisodeVo episodeVo = new EpisodeVo(episode);
        List<EpisodeVo.Stream> streams = Lists.newArrayList();
        if (episode != null && CollectionUtils.isNotEmpty(episode.getLiveStreams())) {
            for (Episode.LiveStream stream : episode.getLiveStreams()) {
                Live live = QmtSbcLiveInternalApis.getLiveById(LeNumberUtils.toLong(stream.getId()));
                if (live != null) {
                    EpisodeVo.Stream liveStream = new EpisodeVo.Stream();
                    liveStream.setId(LeNumberUtils.valueOf(stream.getId()));
                    liveStream.setCommentaryLanguage(live.getCommentaryLanguage());
                    liveStream.setStatus(live.getStatus());
                    liveStream.setStartTime(live.getStartTime());
                    liveStream.setEndTime(live.getEndTime());
                    liveStream.setOrder(stream.getOrder());
                    streams.add(liveStream);
                }
            }
        }
        episodeVo.setStreams(streams);

        Episode.ChatRoom chatRoom = episode.getChatRoom();
        if (chatRoom == null || StringUtils.isBlank(chatRoom.getChatRoomId())) {
            if (chatRoom == null) {
                chatRoom = new Episode.ChatRoom();
            }
            chatRoom.setOpen(false);
            episodeVo.setChatRoom(chatRoom);
        }

        //相关内容
        List<RelatedItem> relatedItems = episode.getRelatedItems();
        for (RelatedItem relatedItem : relatedItems) {
            String name = QmtSbcUtils.getNameByEntityId(relatedItem.getId(), CallerUtils.getDefaultCaller());
            relatedItem.setName(name);
        }

        episodeVo.setRelatedTags(QmtSbcUtils.getRelatedTagVosByRelatedIds(episode.getRelatedIds(), caller));

        return episodeVo;
    }
}
