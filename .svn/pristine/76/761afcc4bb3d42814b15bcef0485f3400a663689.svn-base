package com.lesports.qmt.resource.converter;

import com.lesports.api.common.CallerParam;
import com.lesports.qmt.resource.converter.support.AbstractCvoConverter;
import com.lesports.qmt.resource.cvo.NewsCvo;
import com.lesports.qmt.sbc.api.common.NewsType;
import com.lesports.qmt.sbc.api.dto.TNews;
import com.lesports.qmt.sbc.api.dto.TNewsImage;
import com.lesports.qmt.sbc.api.dto.TResourceContent;
import com.lesports.qmt.sbc.client.QmtSbcApis;
import com.lesports.utils.math.LeNumberUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * User: ellios
 * Time: 16-12-28 : 下午4:25
 */
public class NewsCvoConverter extends AbstractCvoConverter<NewsCvo, TNews> {
    @Override
    public NewsCvo doToCvo(TResourceContent content, TNews dto) {
        NewsCvo cvo = new NewsCvo();
        if(dto != null){
            cvo.setId(dto.getId()+"");
            cvo.setName(dto.getName());
            cvo.setVid(dto.getVid());
            cvo.setNewsType(dto.getType());
            if(dto.getType() == NewsType.RICHTEXT) {
                cvo.setImageUrl(dto.getCoverImage());
            } else if(dto.getType() == NewsType.VIDEO) {
                cvo.setImageUrl(dto.getVideoImages());
            } else if(dto.getType() == NewsType.IMAGE_ALBUM) {
                // 取cover第1张 方便pc组件调取
                for (TNewsImage tNewsImage : dto.getImages()) {
                    if (tNewsImage.isCover()) {
                        cvo.getImageUrl().put("169", tNewsImage.getImageUrl());
                        break;
                    }
                }
            } else {
                cvo.setImageUrl(dto.getCoverImage());
            }
            cvo.setCommentId(dto.getCommentId());
            cvo.setCreateTime(dto.getPublishAt());
            cvo.setUrl(dto.getPlayLink());
            cvo.setPubtime(dto.getPublishAt());
            cvo.setStarLevel(dto.getStarLevel());
            cvo.setTags(dto.getTags());
            cvo.setVideoImages(dto.getVideoImages());
            cvo.setImages(dto.getImages());
            cvo.setIsPay(dto.getIsPay());
            cvo.setDuration(dto.getDuration());
        }
        return cvo;
    }

    @Override
    public NewsCvo doToCvo(TResourceContent content, CallerParam caller) {
        TNews dto = null;
        if(StringUtils.isNotBlank(content.getItemId())){
            dto = QmtSbcApis.getTNewsById(LeNumberUtils.toLong(content.getItemId()), caller);
        }
        if (dto == null) {
            LOG.warn("dto is null. contentId : {} itemId : {}, caller : {}",content.getId(), content.getItemId(), caller);
        }
        return toCvo(content, dto);
    }

    @Override
    protected boolean supportContent(TResourceContent content) {
        switch (content.getType()){
            case NEWS:
            case IMAGE_ALBUM:
            case RICHTEXT:
            case VIDEO_NEWS: return true;
            default:return false;
        }
    }
}
