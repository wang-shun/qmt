package com.lesports.qmt.sbc.converter;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.lesports.api.common.ImageUrlExt;
import com.lesports.api.common.Platform;
import com.lesports.qmt.sbc.api.common.ContentRating;
import com.lesports.qmt.sbc.model.Album;
import com.lesports.qmt.sbc.param.AlbumParam;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by zhangxudong@le.com on 2016/10/25.
 */
@Component
public class AlbumVoConverter {

    public Album toAlbum(AlbumParam albumParam) {
        Album album = new Album();
        album.setId(albumParam.getId());
        album.setTitle(albumParam.getTitle());
        album.setDesc(albumParam.getDesc());
        album.setChannel(albumParam.getChannel());
        album.setSubChannel(albumParam.getSubChannel());
        album.setCid(albumParam.getCid());
        album.setBaiduCollected(albumParam.getBaiduCollected());
        album.setSelfProducedProgram(albumParam.getSelfProducedProgram());
        album.setIsPay(albumParam.getIsPay());
        album.setReasonOfExReview(albumParam.getReasonOfExReview());
        album.setRelatedIds(this.getLongSet(albumParam.getRelatedIds()));
        album.setMatchIds(this.getLongSet(albumParam.getMatchIds()));
        album.setPayPlatforms(this.getPlatformSet(albumParam.getPayPlatforms()));
        album.setContentRating(ContentRating.findByValue(albumParam.getContentRating()));

        if (false == StringUtils.isEmpty(albumParam.getImages())) {
            Type type = new TypeReference<Map<String, ImageUrlExt>>() {
            }.getType();
            Map<String, ImageUrlExt> coverImage = JSONObject.parseObject(albumParam.getImages(), type);
            album.setImages(coverImage);
        }
        return album;
    }

    private Set<Platform> getPlatformSet(String src) {
        Set<Platform> result = new HashSet<>();
        if (StringUtils.isEmpty(src)) return result;
        String[] pieces = src.split(",");
        for (String piece : pieces) {
            try {
                Integer i = Integer.valueOf(piece);
                result.add(Platform.findByValue(i));
            } catch (Exception e) {
                continue;
            }
        }
        return result;
    }

    private Set<Long> getLongSet(String src) {
        Set<Long> result = new HashSet<>();
        if (StringUtils.isEmpty(src))
            return result;
        String[] piece = src.split(",");
        for (String s : piece) {
            try {
                Long tmp = Long.parseLong(s);
                result.add(tmp);
            } catch (Exception e) {
                continue;
            }
        }
        return result;
    }
}
