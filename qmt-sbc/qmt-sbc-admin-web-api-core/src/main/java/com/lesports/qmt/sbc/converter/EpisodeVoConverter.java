package com.lesports.qmt.sbc.converter;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.lesports.api.common.ImageUrlExt;
import com.lesports.qmt.mvc.QmtVoConverter;
import com.lesports.qmt.sbc.model.Episode;
import com.lesports.qmt.sbc.model.RelatedItem;
import com.lesports.qmt.sbc.param.EpisodeParam;
import com.lesports.qmt.sbc.vo.EpisodeVo;
import com.lesports.utils.LeStringUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by lufei1 on 2016/11/14.
 */
@Component
public class EpisodeVoConverter implements QmtVoConverter<Episode, EpisodeVo> {

    @Override
    public EpisodeVo toVo(Object object) {
        EpisodeParam param = (EpisodeParam) object;
        EpisodeVo vo = new EpisodeVo();
        vo.setId(param.getId());
        vo.setName(param.getName());
        vo.setShareName(param.getShareName());
        vo.setShareDesc(param.getShareDesc());
        vo.setDesc(param.getDesc());
        vo.setLeecoAid(param.getLeecoAid());
//        vo.setTopicUrl(param.getTopicUrl());
        vo.setOnline(param.getOnline());
        vo.setIsOctopus(param.getIsOctopus());
        vo.setOctopusMatchId(param.getOctopusMatchId());
        vo.setXinyingPay(param.getXinyingPay());
        vo.setXinyingMatchId(param.getXinyingMatchId());
        vo.setPrice(param.getPrice());
        vo.setHighlightsId(param.getHighlightsId());


        //聊天室信息
        Episode.ChatRoom chatRoom = JSON.parseObject(param.getChatRoom(), Episode.ChatRoom.class);
        if (chatRoom != null) {
            vo.setChatRoom(chatRoom);
        }

        //直播流信息
        List<Episode.LiveStream> liveStreams = JSON.parseArray(param.getLiveStreams(), Episode.LiveStream.class);
        if (liveStreams != null) {
            vo.setLiveStreams(Sets.newHashSet(liveStreams));
        }

        //图文直播信息
        List<Episode.SimpleTextLive> textLives = JSON.parseArray(param.getTextLives(), Episode.SimpleTextLive.class);
        if (textLives != null) {
            vo.setTextLives(Sets.newHashSet(textLives));
        }

        //节目图片（即焦点图），主要使用在无直播流的节目上
        ImageUrlExt image = JSON.parseObject(param.getImage(), ImageUrlExt.class);
        if (image != null) {
            vo.setImage(image);
        }

        //pc节目图片，主要使用在pc直播日历
        ImageUrlExt pcImage = JSON.parseObject(param.getPcImage(), ImageUrlExt.class);
        if (pcImage != null) {
            vo.setPcImage(pcImage);
        }

        //tv详情页背景图，主要使用在大乐tv
        ImageUrlExt tvImage = JSON.parseObject(param.getTvImage(), ImageUrlExt.class);
        if (tvImage != null) {
            vo.setTvImage(tvImage);
        }

        //tv比赛大厅广告图片,主要使用在香港tv
        ImageUrlExt tvAdImage = JSON.parseObject(param.getTvAdImage(), ImageUrlExt.class);
        if (tvAdImage != null) {
            vo.setTvAdImage(tvAdImage);
        }

        //相关内容
        List<RelatedItem> relatedItems = JSON.parseArray(param.getRelatedItems(), RelatedItem.class);
        if (CollectionUtils.isNotEmpty(relatedItems)) {
            vo.setRelatedItems(relatedItems);
        }

        //相关标签
        List<Long> relatedIds = LeStringUtils.commaString2LongList(param.getRelatedIds());
        List<Long> relatedMids = LeStringUtils.commaString2LongList(param.getRelatedMids());
        Set<Long> relatedSet = Sets.newHashSet();
        relatedSet.addAll(relatedIds);
        relatedSet.addAll(relatedMids);
        vo.setRelatedIds(relatedSet);

        return vo;
    }

    @Override
    public Episode copyEditableProperties(Episode existsEntity, EpisodeVo vo) {
        existsEntity.setId(vo.getId());
        existsEntity.setName(vo.getName());
        existsEntity.setShareName(vo.getShareName());
        existsEntity.setShareDesc(vo.getShareDesc());
        existsEntity.setDesc(vo.getDesc());
        existsEntity.setLeecoAid(vo.getLeecoAid());
//        existsEntity.setTopicUrl(vo.getTopicUrl());
        existsEntity.setOnline(vo.getOnline());
        existsEntity.setIsOctopus(vo.getIsOctopus());
        existsEntity.setOctopusMatchId(vo.getOctopusMatchId());
        existsEntity.setXinyingPay(vo.getXinyingPay());
        existsEntity.setXinyingMatchId(vo.getXinyingMatchId());
        existsEntity.setPrice(vo.getPrice());
        existsEntity.setHighlightsId(vo.getHighlightsId());
        //聊天室信息
        existsEntity.setChatRoom(vo.getChatRoom());
        //直播流信息
        Map<Long,Episode.LiveStream> liveStreamMap = Maps.newHashMap();
        if (CollectionUtils.isNotEmpty(vo.getLiveStreams())) {
            for(Episode.LiveStream liveStream : vo.getLiveStreams()){
                liveStreamMap.put(liveStream.getId(),liveStream);
            }
            Set<Episode.LiveStream> liveStreams = existsEntity.getLiveStreams();
            if (CollectionUtils.isNotEmpty(liveStreams)) {
                for(Episode.LiveStream liveStream : liveStreams){
                    Episode.LiveStream liveStreamVO = liveStreamMap.get(liveStream.getId());
                    liveStream.setOrder(liveStreamVO.getOrder());
                }
            }
            existsEntity.setLiveStreams(vo.getLiveStreams());
        }

        //图文直播信息
//        existsEntity.setTextLives(vo.getTextLives());
        //节目图片（即焦点图），主要使用在无直播流的节目上
        existsEntity.setImage(vo.getImage());
        //pc节目图片，主要使用在pc直播日历
        existsEntity.setPcImage(vo.getPcImage());
        //tv详情页背景图，主要使用在大乐tv
        existsEntity.setTvImage(vo.getTvImage());
        //tv比赛大厅广告图片,主要使用在香港tv
        existsEntity.setTvAdImage(vo.getTvAdImage());
        //相关内容
        existsEntity.setRelatedItems(vo.getRelatedItems());
        //相关标签
        existsEntity.setRelatedIds(vo.getRelatedIds());
        return existsEntity;
    }

}
