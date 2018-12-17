package com.lesports.qmt.web.datacenter.creator;

import com.google.common.collect.Lists;
import com.lesports.qmt.sbc.api.common.NewsType;
import com.lesports.qmt.sbc.api.dto.TNews;
import com.lesports.qmt.web.datacenter.vo.NewsVo;

/**
 * Created by denghui on 2016/12/19.
 */
public class NewsVoCreator {

    public static NewsVo createNewsVo(TNews dto) {
        NewsVo vo = new NewsVo();
        fillNewsVo(vo, dto);
        return vo;
    }

    private static void fillNewsVo(NewsVo vo, TNews dto) {
        vo.setNewsType(dto.getType());
        vo.setCreateTime(dto.getPublishAt());
        if (dto.getType() == NewsType.VIDEO || dto.getType() == NewsType.RICHTEXT) {
            vo.setImageUrl(dto.getVideoImages());
        } else if (dto.getType() == NewsType.IMAGE_ALBUM) {
            vo.setImages(dto.getImages());
        }
        vo.setName(dto.getName());
        vo.setDesc(dto.getDesc());
        vo.setDuration(dto.getDuration());
        vo.setCommentId(dto.getCommentId());
        vo.setVid(dto.getVid());
        vo.setTags(Lists.newArrayList(dto.getTags()));
        vo.setIsPay(dto.getIsPay());
    }
}
