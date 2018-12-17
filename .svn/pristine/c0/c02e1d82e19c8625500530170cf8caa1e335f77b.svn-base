package com.lesports.qmt.sbc.converter;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.lesports.api.common.CallerParam;
import com.lesports.qmt.sbc.api.dto.TComboEpisode;
import com.lesports.qmt.sbc.api.dto.TProgramAlbum;
import com.lesports.qmt.sbc.api.dto.TVideo;
import com.lesports.qmt.sbc.converter.support.AbstractSbcTDtoConverter;
import com.lesports.qmt.sbc.model.Episode;
import com.lesports.qmt.sbc.model.ProgramAlbum;
import com.lesports.qmt.sbc.model.Video;
import com.lesports.qmt.sbc.service.EpisodeService;
import com.lesports.qmt.sbc.service.VideoService;
import com.lesports.qmt.sbc.utils.CommentIdUtils;
import com.lesports.utils.math.LeNumberUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Created by denghui on 2016/11/15.
 */
@Component("programAlbumConverter")
public class TProgramAlbumConverter extends AbstractSbcTDtoConverter<ProgramAlbum, TProgramAlbum> {

    @Resource
    private EpisodeService episodeService;
    @Resource
    private VideoService videoService;

    @Override
    protected void fillDto(TProgramAlbum dto, ProgramAlbum model) {
        dto.setId(model.getId());
        dto.setName(model.getName());
        dto.setLogo(Maps.transformValues(model.getLogo(), IMAGE_URL_FUNCTION));
        dto.setRecommend(LeNumberUtils.toBoolean(model.getRecommend()));
        dto.setRecommendOrder(LeNumberUtils.toInt(model.getRecommendOrder()));
        Episode episode = episodeService.getLatestEpisodeByAid(model.getId());
        if (episode == null) {
            return;
        }
        Video video = videoService.findOne(episode.getRecordId());
        if (video != null) {
            TVideo tVideo = new TVideo();
            fillTVideoWithVideo(tVideo, video);
            dto.setLatestVideo(tVideo);
        }
        TComboEpisode tComboEpisode = new TComboEpisode();
        fillEpisodeWithEpisode(tComboEpisode, episode);
        dto.setLatestEpisode(tComboEpisode);


    }

    private void fillTVideoWithVideo(TVideo tVideo, Video video){
        tVideo.setId(video.getId());
        tVideo.setLeecoVid(video.getLeecoVid());
        tVideo.setCid(LeNumberUtils.toLong(video.getCid()));
        tVideo.setName(video.getTitle());
        tVideo.setCommentId(video.getId() + "");
        tVideo.setContentType(video.getVideoType());

        tVideo.setDesc(video.getDesc());
        tVideo.setAid(video.getAid());
        tVideo.setPlatforms(video.getPlayPlatforms());
        if(CollectionUtils.isNotEmpty(video.getSupportLicences())) {
            tVideo.setValidLicences(Lists.newArrayList(video.getSupportLicences()));
        }
        if (MapUtils.isNotEmpty(video.getImages())) {
            Map<String, String> imageUrl = Maps.transformValues(video.getImages(), IMAGE_URL_FUNCTION);
            tVideo.setImageUrl(imageUrl.get("169"));//440:225
            tVideo.setImages(imageUrl);
        }
        tVideo.setCloneVideo(LeNumberUtils.toBoolean(video.getIsClone()));
        tVideo.setDuration(LeNumberUtils.toLong(video.getDuration()));
        tVideo.setHasBigImage(video.getHasBigImage() == null ? false : video.getHasBigImage());
        tVideo.setCreateAt(video.getCreateAt());
        //tVideo.setAlbumId(LeNumberUtils.toLong(video.getAlbumId()));
    }

    private void fillEpisodeWithEpisode(TComboEpisode comboEpisode, Episode episode) {
        comboEpisode.setId(episode.getId());
        comboEpisode.setMid(LeNumberUtils.toLong(episode.getMid()));
        comboEpisode.setAid(LeNumberUtils.toLong(episode.getAid()));
        comboEpisode.setStatus(episode.getStatus());
        comboEpisode.setName(episode.getName());
        comboEpisode.setType(episode.getType());
        comboEpisode.setStartTime(episode.getStartTime());
        comboEpisode.setPeriods(StringUtils.isEmpty(episode.getPeriods()) ? "" : episode.getPeriods());
        comboEpisode.setCommentId(CommentIdUtils.create(episode.getId()));
    }

    @Override
    protected TProgramAlbum createEmptyDto() {
        return new TProgramAlbum();
    }
}
