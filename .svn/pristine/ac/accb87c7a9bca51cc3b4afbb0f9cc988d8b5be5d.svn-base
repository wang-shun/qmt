package com.lesports.qmt.sbc.converter;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.lesports.api.common.CallerParam;
import com.lesports.api.common.Platform;
import com.lesports.id.client.LeIdApis;
import com.lesports.qmt.config.api.dto.TCaller;
import com.lesports.qmt.config.api.dto.TDictEntry;
import com.lesports.qmt.config.api.dto.TTag;
import com.lesports.qmt.config.client.QmtConfigApis;
import com.lesports.qmt.sbc.api.common.NewsType;
import com.lesports.qmt.sbc.api.common.TRelatedItem;
import com.lesports.qmt.sbc.api.dto.TNews;
import com.lesports.qmt.sbc.api.dto.TNewsImage;
import com.lesports.qmt.sbc.converter.support.AbstractSbcTDtoConverter;
import com.lesports.qmt.sbc.helper.RelatedHelper;
import com.lesports.qmt.sbc.model.News;
import com.lesports.qmt.sbc.model.NewsImage;
import com.lesports.qmt.sbc.model.RelatedItem;
import com.lesports.qmt.sbc.model.Video;
import com.lesports.qmt.sbc.repository.NewsImageRepository;
import com.lesports.qmt.sbc.repository.VideoRepository;
import com.lesports.utils.CallerUtils;
import com.lesports.utils.LeProperties;
import com.lesports.utils.math.LeNumberUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

/**
 * Created by denghui on 2016/3/25.
 */
@Component("newsConverter")
public class TNewsConverter extends AbstractSbcTDtoConverter<News, TNews> {

    private static final Logger LOG = LoggerFactory.getLogger(TNewsConverter.class);

    @Resource
    private NewsImageRepository newsImageRepository;
    @Resource
    private RelatedHelper relatedHelper;
    @Resource
    private VideoRepository videoRepository;

    @Override
    public TNews adaptDto(TNews vo, CallerParam caller){
        Preconditions.checkNotNull(vo);
        Preconditions.checkNotNull(caller);
        //根据caller的platForm判断是否付费
        vo.setIsPay(0);
        if (CollectionUtils.isNotEmpty(vo.getPayPlatforms())) {
            TCaller tCaller = QmtConfigApis.getTCallerById(caller.getCallerId());
            Platform platform = tCaller.getPlatform();
            if (vo.getPayPlatforms().contains(platform)) {
                vo.setIsPay(1);
            }
        }
        //替换富文本中视频的广告参数
        if (vo.getType() == NewsType.RICHTEXT && StringUtils.isNotBlank(vo.getContent()) && vo.getContent().contains("autoplay")) {
            String content = vo.getContent();
            if (content.contains("ark=774&")) {
                content = content.replace("ark=774&", "");
            }
            int ark;
            if (caller.getCallerId() == 1001l) {
                ark = 774;
            } else {
                ark = 1128;
            }
            String s = addArkToNewsContent(content, ark);
            vo.setContent(s);
        }
        return vo;
    }

    private String addArkToNewsContent (String content, int ark) {
        String[] autoplays = content.split("autoplay");
        StringBuffer sb1 = new StringBuffer();
        for (int i=0; i<autoplays.length; i ++ ) {
            String autoplay = autoplays[i];
            if (i == 0) {
                sb1.append(autoplay);
                continue;
            }
            StringBuffer sb = new StringBuffer();
            sb.append("autoplay").append(autoplay.substring(0, 2)).append("&ark=").append(ark).append(autoplay.substring(2, autoplay.length()));
            sb1.append(sb);
        }
        return sb1.toString();
    }

    public TNewsImage createTNewsImage(long id, News news) {
        NewsImage newsImage = newsImageRepository.findOne(id);
        if (newsImage == null) {
            return null;
        }
        TNewsImage dtoImage = new TNewsImage();
        fillTNewsImage(dtoImage, newsImage, news);
        return dtoImage;
    }

    private void fillTNewsImage(TNewsImage dtoImage, NewsImage newsImage, News news) {
        dtoImage.setId(newsImage.getId());
        if (news.getType() == NewsType.IMAGE_ALBUM) {
            if (StringUtils.isBlank(newsImage.getDesc())) {
                dtoImage.setDesc(news.getContent());
            } else {
                dtoImage.setDesc(newsImage.getDesc());
            }
            if (StringUtils.isBlank(newsImage.getName())) {
                dtoImage.setName(news.getName());
            } else {
                dtoImage.setName(newsImage.getName());
            }
        } else {
            dtoImage.setDesc(newsImage.getDesc());
            dtoImage.setName(newsImage.getName());
        }
        if (newsImage.getImage() != null) {
            dtoImage.setImageUrl(newsImage.getImage().getUrl());
        }
        dtoImage.setCover(LeNumberUtils.toBoolean(newsImage.getCover()));
        dtoImage.setAggCover(LeNumberUtils.toBoolean(newsImage.getAggCover()));
        dtoImage.setShowOrder(LeNumberUtils.toInt(newsImage.getShowOrder()));
    }

    @Override
    protected void fillDto(TNews dto, News model) {
        dto.setId(model.getId());
        dto.setDeleted(LeNumberUtils.toBoolean(model.getDeleted()));
        if(LeNumberUtils.toLong(model.getLeecoVid()) > 0){
            dto.setVid(LeNumberUtils.toLong(model.getLeecoVid()));
        }else {
            try {
                dto.setVid(LeIdApis.convertLeSportsVideoIdToMmsVideoId(LeNumberUtils.toLong(model.getVid())));
            } catch (Exception e) {
                LOG.error("fail to convertLeSportsVideoIdToMmsVideoId。id：{}", model.getVid());
            }
        }
        dto.setLeecoVid(LeNumberUtils.toLong(model.getLeecoVid()));
        dto.setCid(LeNumberUtils.toLong(model.getCid()));
        dto.setRelatedItems(fillRelatedItems(model.getRelatedItems()));
        dto.setIsJump(LeNumberUtils.toBoolean(model.getJump()));
        dto.setJumpUrlType(model.getJumpUrlType());
        dto.setJumpUrl(model.getJumpUrl());

        dto.setSource(model.getSource());
        if (LeNumberUtils.toLong(model.getChannelId()) > 0) {
            TDictEntry channelEntry = QmtConfigApis.getTDictEntryById(model.getChannelId(), null);
            if (channelEntry == null) {
                //channel必须有
                LOG.error("fail to fill resource dto, channel entry must not be null");
                throw new IllegalArgumentException("channel entry must not be null");
            }
            dto.setChannel(convertDictToChannel(channelEntry));
        }
        if (LeNumberUtils.toLong(model.getSubChannelId()) > 0) {
            TDictEntry subChannelEntry = QmtConfigApis.getTDictEntryById(model.getSubChannelId(), null);
            dto.setSubChannel(convertDictToChannel(subChannelEntry));
        }

        dto.setName(model.getName());
        dto.setShortName(model.getShortName());
        dto.setShareName(model.getShareName());
        dto.setDesc(model.getDesc());
        dto.setShareDesc(model.getShareDesc());
        dto.setContent(model.getContent());
        dto.setOnline(model.getOnline());
        dto.setPublishAt(model.getPublishAt());
        dto.setType(model.getType());
        dto.setAuthor(model.getAuthor());
        dto.setStatements(model.getStatements());
        dto.setAllowComment(LeNumberUtils.toBoolean(model.getAllowComment()));
        dto.setDrmFlag(LeNumberUtils.toInt(model.getDrmFlag()));
        if (CollectionUtils.isNotEmpty(model.getRelatedIds())) {
            for (Long relatedId : model.getRelatedIds()) {
                String name = relatedHelper.getNameByEntityId(relatedId, CallerUtils.getDefaultCaller());
                if (StringUtils.isNotEmpty(name)) {
                    TTag tTag = new TTag();
                    tTag.setId(relatedId);
                    tTag.setName(name);
                    dto.addToTags(tTag);
                }
            }
        }
        //获取图文新闻的封面图
        if (MapUtils.isNotEmpty(model.getCoverImage())) {
            dto.setCoverImage(Maps.transformValues(model.getCoverImage(), IMAGE_URL_FUNCTION));
        }
        dto.setPlatforms(model.getPlatforms());
        //添加新闻图片
        List<Long> imageIds = newsImageRepository.findIdsByNewsId(model.getId());
        for(Long imageId : imageIds){
            TNewsImage image = createTNewsImage(imageId, model);
            if(image != null){
                dto.addToImages(image);
            }
        }
        if (CollectionUtils.isNotEmpty(dto.getImages())) {
            //对图片通过order进行排序
            Collections.sort(dto.getImages(), (o1, o2) -> LeNumberUtils.toInt(o1.getShowOrder()) - LeNumberUtils.toInt(o2.getShowOrder()));
        }
        dto.setIsPay(LeNumberUtils.toBoolean(model.getPay()) ? 1 : 0);
        dto.setPayPlatforms(model.getPayPlatforms());
        if (model.getType() == NewsType.VIDEO) {
            dto.setCommentId(model.getVid() + "");
            Video video = videoRepository.findOne(model.getVid());
            if (video != null) {
                dto.setDuration(LeNumberUtils.toLong(video.getDuration()));
                dto.setHasBigImage(LeNumberUtils.toBoolean(video.getHasBigImage()));
                if (MapUtils.isNotEmpty(video.getImages())) {
                    dto.setVideoImages(Maps.transformValues(video.getImages(), IMAGE_URL_FUNCTION));
                }
            }
        } else {
            dto.setCommentId(model.getId() + "");
        }
        //落地页地址
        if (StringUtils.isNotEmpty(model.getJumpUrl())) {
            dto.setPlayLink(model.getJumpUrl());
        } else {
            dto.setPlayLink(String.format(LeProperties.getString("play.url.news"), model.getId()));
        }
    }

    private List<TRelatedItem> fillRelatedItems(List<RelatedItem> relatedItems) {
        List<TRelatedItem> tRelatedItems = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(relatedItems)) {
            for (RelatedItem relatedItem : relatedItems) {
                TRelatedItem tRelatedItem = new TRelatedItem();
                tRelatedItem.setId(LeNumberUtils.toLong(relatedItem.getId()));
                tRelatedItem.setType(relatedItem.getType());
                String name = relatedHelper.getNameByEntityId(relatedItem.getId(), CallerUtils.getDefaultCaller());
                tRelatedItem.setName(name);
                tRelatedItem.setOrder(LeNumberUtils.toInt(relatedItem.getOrder()));
                tRelatedItems.add(tRelatedItem);
            }
            Collections.sort(tRelatedItems,  (o1, o2) -> LeNumberUtils.toInt(o1.getOrder()) - LeNumberUtils.toInt(o2.getOrder()));
        }
        return tRelatedItems;
    }

    @Override
    protected TNews createEmptyDto() {
        return new TNews();
    }

}
