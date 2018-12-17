package com.lesports.qmt.sbc.converter;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.lesports.api.common.ImageUrlExt;
import com.lesports.api.common.Platform;
import com.lesports.qmt.mvc.QmtVoConverter;
import com.lesports.qmt.sbc.api.common.NewsType;
import com.lesports.qmt.sbc.model.News;
import com.lesports.qmt.sbc.model.NewsImage;
import com.lesports.qmt.sbc.model.RelatedItem;
import com.lesports.qmt.sbc.param.SaveNewsParam;
import com.lesports.qmt.sbc.vo.NewsVo;
import com.lesports.qmt.sbc.vo.RelatedTagVo;
import com.lesports.utils.LeDateUtils;
import com.lesports.utils.math.LeNumberUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;

/**
 * Created by denghui on 2016/10/31.
 */
@Component
public class NewsVoConverter implements QmtVoConverter<News, NewsVo> {

    private static final Logger LOG = LoggerFactory.getLogger(NewsVoConverter.class);

    @Override
    public NewsVo toVo(Object object) {
        SaveNewsParam saveNewsParam = (SaveNewsParam) object;
        NewsVo newsVo = new NewsVo();
        newsVo.setId(saveNewsParam.getId());
        newsVo.setType(saveNewsParam.getType());

        newsVo.setJumpUrlType(saveNewsParam.getJumpUrlType());
        newsVo.setJumpUrl(saveNewsParam.getJumpUrl());
        newsVo.setJump(saveNewsParam.getJump());//已去掉跳转类型

        newsVo.setName(saveNewsParam.getName());
        newsVo.setShortName(saveNewsParam.getShortName());
        newsVo.setShareName(saveNewsParam.getShareName());
        newsVo.setDesc(saveNewsParam.getDesc());
        newsVo.setShareDesc(saveNewsParam.getShareDesc());
        newsVo.setChannelId(saveNewsParam.getChannelId());
        newsVo.setSubChannelId(saveNewsParam.getSubChannelId());

        newsVo.setStarLevel(saveNewsParam.getStarLevel());
        newsVo.setSource(saveNewsParam.getSource());
        newsVo.setAllowComment(saveNewsParam.getAllowComment());

        newsVo.setAuthor(saveNewsParam.getAuthor());
        newsVo.setCid(saveNewsParam.getCid());
        newsVo.setPlatforms(Sets.newHashSet(JSON.parseArray(saveNewsParam.getPlatforms(),Platform.class)));
        newsVo.setContent(saveNewsParam.getContent());
        newsVo.setPublishAt(LeDateUtils.tansYMDHMS2YYYYMMDDHHMMSS(saveNewsParam.getPublishAt()));
        newsVo.addStatement(saveNewsParam.getStatement());
        newsVo.setRelatedItems(JSON.parseArray(saveNewsParam.getRelatedItems(), RelatedItem.class));
        newsVo.setDraft(saveNewsParam.getDraft());
        List<RelatedTagVo> relatedTags = JSON.parseArray(saveNewsParam.getRelatedTags(), RelatedTagVo.class);
        if (CollectionUtils.isNotEmpty(relatedTags)) {
            for (RelatedTagVo relatedTag : relatedTags) {
                newsVo.addRelatedId(relatedTag.getId());
            }
        }

        //图片
        if (saveNewsParam.getType() == NewsType.IMAGE_ALBUM) {
            if (saveNewsParam.getImages().contains("\"id\":")) {
                //fix fastjson error
                saveNewsParam.setImages(saveNewsParam.getImages().replaceAll("\"id\":", "\"helperId\":"));
            }
            List<NewsImage> newsImages = JSON.parseArray(saveNewsParam.getImages(), NewsImage.class);
            //封面图数量校验
            if(!checkImageAlbumCover(newsImages)) {
                LOG.error("invalid number of cover images: {}", newsImages);
                throw new IllegalArgumentException("invalid number of cover images");
            }
            newsVo.setImages(Lists.transform(newsImages, new Function<NewsImage, NewsImage>() {
                @Nullable
                @Override
                public NewsImage apply(@Nullable NewsImage input) {
                    //只复制前端可编辑的属性
                    NewsImage newsImage = new NewsImage();
                    newsImage.setId(input.getId());
                    newsImage.setName(input.getName());
                    newsImage.setDesc(input.getDesc());
                    newsImage.setCover(input.getCover());
                    newsImage.setAggCover(input.getAggCover());
                    newsImage.setShowOrder(input.getShowOrder());
                    newsImage.setImage(input.getImage());
                    return newsImage;
                }
            }));
        } else if (saveNewsParam.getType() == NewsType.RICHTEXT) {
            Map<String, ImageUrlExt> coverImage = JSON.parseObject(saveNewsParam.getCoverImage(), Map.class);
            //封面图数量校验
            if(MapUtils.isEmpty(coverImage)) {
                LOG.error("invalid number of cover image: {}", coverImage);
                throw new IllegalArgumentException("invalid number of cover images");
            }
            newsVo.setCoverImage(coverImage);
        } else {
            LOG.error("unknown news type: {}", saveNewsParam.getType());
            throw new IllegalArgumentException("unknown news type");
        }
        return newsVo;
    }

    private boolean checkImageAlbumCover(List<NewsImage> newsImages) {
        int listCoverCount = 0, aggCoverCount = 0;
        if (CollectionUtils.isNotEmpty(newsImages)) {
            for (NewsImage newsImage : newsImages) {
                if (LeNumberUtils.toBoolean(newsImage.getCover())) {
                    listCoverCount++;
                }
                if (LeNumberUtils.toBoolean(newsImage.getAggCover())) {
                    aggCoverCount++;
                }
            }
        }
        return listCoverCount == 3 && aggCoverCount == 1;
    }

    @Override
    public News copyEditableProperties(News existsEntity, NewsVo vo) {
        existsEntity.setJumpUrlType(vo.getJumpUrlType());
        existsEntity.setJumpUrl(vo.getJumpUrl());
        existsEntity.setJump(vo.getJump());
        existsEntity.setName(vo.getName());
        existsEntity.setShareName(vo.getShareName());
        existsEntity.setShortName(vo.getShortName());
        existsEntity.setDesc(vo.getDesc());
        existsEntity.setShareDesc(vo.getShareDesc());
        existsEntity.setChannelId(vo.getChannelId());
        existsEntity.setSubChannelId(vo.getSubChannelId());
        existsEntity.setStarLevel(vo.getStarLevel());
        existsEntity.setSource(vo.getSource());
        existsEntity.setAuthor(vo.getAuthor());
        existsEntity.setCid(vo.getCid());
        existsEntity.setPlatforms(vo.getPlatforms());
        existsEntity.setContent(vo.getContent());
        if (StringUtils.isNotEmpty(vo.getPublishAt())) {
            //发布时间可编辑
            existsEntity.setPublishAt(vo.getPublishAt());
        }
        existsEntity.setStatements(vo.getStatements());
        existsEntity.setAllowComment(vo.getAllowComment());
        existsEntity.setRelatedItems(vo.getRelatedItems());
        existsEntity.setCoverImage(vo.getCoverImage());
        existsEntity.setDraft(vo.getDraft());
        existsEntity.setRelatedIds(vo.getRelatedIds());
        return existsEntity;
    }

}
