package com.lesports.qmt.sbc.converter;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.lesports.api.common.CallerParam;
import com.lesports.api.common.CountryCode;
import com.lesports.id.api.IdType;
import com.lesports.id.client.LeIdApis;
import com.lesports.qmt.config.api.dto.TTag;
import com.lesports.qmt.config.client.QmtConfigApis;
import com.lesports.qmt.sbc.api.dto.TVideo;
import com.lesports.qmt.sbc.converter.support.AbstractSbcTDtoConverter;
import com.lesports.qmt.sbc.model.Album;
import com.lesports.qmt.sbc.model.Video;
import com.lesports.qmt.sbc.repository.AlbumRepository;
import com.lesports.utils.math.LeNumberUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;

/**
 * User: zhangxudong@le.com
 * Time: 16-10-27 : 下午9:58
 */
@Component("videoConverter")
public class TVideoConverter extends AbstractSbcTDtoConverter<Video, TVideo> {

    @Resource
    private AlbumRepository albumRepository;

    @Override
    protected void fillDto(TVideo dto, Video model) {
        if (null == dto || null == model) return;
        dto.setId(LeNumberUtils.toLong(model.getLeecoVid()));
        dto.setLeecoVid(LeNumberUtils.toLong(model.getLeecoVid()));
        dto.setName(model.getTitle());
        if (false == CollectionUtils.isEmpty(model.getRelatedIds())) {
            if (null == dto.getMids()) dto.setMids(Sets.newHashSet());
            if (null == dto.getTids()) dto.setTids(Sets.newHashSet());
            if (null == dto.getPids()) dto.setPids(Sets.newHashSet());
            if (null == dto.getTags()) dto.setTags(Lists.newArrayList());

            for (Long relatedId : model.getRelatedIds()) {
                IdType idType = LeIdApis.checkIdTye(relatedId);
                if (idType == null) {
                    LOG.warn("illegal relatedId : {}", relatedId);
                    continue;
                }
                if (idType == IdType.PLAYER) {
                    dto.getPids().add(relatedId);
                } else if (idType == IdType.TEAM) {
                    dto.getTids().add(relatedId);
                } else if (idType == IdType.TAG) {
                    TTag tTag = QmtConfigApis.getTTagById(relatedId);
                    dto.getTags().add(tTag);
                } else if (idType == IdType.MATCH) {
                    dto.getMids().add(relatedId);
                }
            }
        }
        dto.setGameFType(model.getChannel()); //FIXME: 大项 改成 频道
//        dto.setGameSType(1L); //FIXME:
        dto.setCid(LeNumberUtils.toLong(model.getCid()));
        dto.setContentType(model.getVideoType());
        dto.setPlatforms(model.getPlayPlatforms());
        dto.setDesc(model.getDesc());
        dto.setCloneVideo(LeNumberUtils.toBoolean(model.getIsClone()));
        dto.setHasBigImage(LeNumberUtils.toBoolean(model.getHasBigImage()));
        dto.setDuration(LeNumberUtils.toInt(model.getDuration()));
//        dto.setEids(Sets.newHashSet());
        if (false == CollectionUtils.isEmpty(model.getImages())) {
            if (null == dto.getImages()) dto.setImages(Maps.newHashMap());
            if (null != model.getImages().get("169")) dto.setImageUrl(model.getImages().get("169").getUrl());
            model.getImages().forEach((key, value) -> {
                if (null != key && null != value) dto.getImages().put(key, value.getUrl());
            });
        }
        if (null != model.getAid()) {
            dto.setAid(model.getAid());
            Album album = albumRepository.findOne(model.getAid());
            if (null != album) dto.setAlbumId(album.getLeecoAid());
        }
//        dto.setPlayLink("");
//        dto.setCommentId("");
//        dto.setMultiLangNames();
//        dto.setMultiLangDesc();
        dto.setAllowCountries(Lists.newArrayList(CountryCode.ALL)); //FIXME:
//        dto.setValidLicences();
        dto.setDrmFlag(model.getDrmFlag());
        dto.setCreateAt(model.getCreateAt());
    }

    @Override
    protected TVideo createEmptyDto() {
        return new TVideo();
    }

    public TVideo adaptDto(TVideo vo, CallerParam caller) {
        Preconditions.checkNotNull(vo);
        Preconditions.checkNotNull(caller);
        // ... //FIXME:
        return vo;
    }
}
