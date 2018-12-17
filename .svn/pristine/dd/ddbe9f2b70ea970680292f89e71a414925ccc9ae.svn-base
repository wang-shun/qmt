package com.lesports.qmt.web.api.core.service.impl;

import com.google.common.collect.Lists;
import com.lesports.api.common.CallerParam;
import com.lesports.jersey.support.model.CallerBean;
import com.lesports.qmt.resource.ComboResourceCreaters;
import com.lesports.qmt.resource.core.ComboResource;
import com.lesports.qmt.resource.cvo.BaseCvo;
import com.lesports.qmt.resource.cvo.NewsCvo;
import com.lesports.qmt.sbc.api.common.NewsType;
import com.lesports.qmt.sbc.api.common.TvLicence;
import com.lesports.qmt.sbc.api.common.VideoContentType;
import com.lesports.qmt.sbc.api.dto.ResourceItemType;
import com.lesports.qmt.sbc.api.dto.TComboEpisode;
import com.lesports.qmt.sbc.api.dto.TNews;
import com.lesports.qmt.sbc.api.dto.TVideo;
import com.lesports.qmt.sbc.client.QmtSbcApis;
import com.lesports.qmt.sbc.client.QmtSbcEpisodeApis;
import com.lesports.qmt.web.api.core.service.CmsService;
import com.lesports.qmt.web.api.core.util.Constants;
import com.lesports.qmt.web.api.core.vo.BlockContentVo;
import com.lesports.qmt.web.api.core.vo.NewsVo;
import com.lesports.utils.PageUtils;
import com.lesports.utils.math.LeNumberUtils;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * Created by ruiyuansheng on 2016/4/11.
 */
@Service("cmsService")
public class CmsServiceImpl implements CmsService {
    private static final Logger LOG = LoggerFactory.getLogger(CmsServiceImpl.class);


    private BlockContentVo createBlockContent(NewsVo newsVo,CallerParam caller){

        BlockContentVo blockContent = new BlockContentVo();
        blockContent.setTvPic((String)newsVo.getImageUrl().get("400*225"));
        blockContent.setType(1);
        blockContent.setContent(newsVo.getVid()+"");
        blockContent.setTitle(newsVo.getName());
        blockContent.setCtime(LeNumberUtils.toLong(newsVo.getCreateTime()));
        blockContent.setVideoId(newsVo.getVid());
        if(Constants.CIBN_CALLER == caller.getCallerId()) {
            TVideo tVideo = QmtSbcApis.getTVideoById(LeNumberUtils.toLong(newsVo.getVid()), caller);
            if (null == tVideo || CollectionUtils.isEmpty(tVideo.getValidLicences()) || !tVideo.getValidLicences().contains(TvLicence.CIBN)) {
                LOG.warn("this video has no valid licences. id : {}, caller : {}", newsVo.getVid(), caller);
                return blockContent;
            }
            if(tVideo.getIsPay() == 1){
                blockContent.setCornerMark(1);
            }else{
                if(tVideo.getAid() > 0){
                    blockContent.setCornerMark(2);
                }else {
                    if (tVideo.getContentType() == VideoContentType.RECORD) {
//                        blockContent.setCornerMark(5);
                    } else if (tVideo.getContentType() == VideoContentType.HIGHLIGHTS) {
                        blockContent.setCornerMark(6);
                    }
                }
            }
        }

        return blockContent;

    }

    public List<BlockContentVo> createBlockContents(List<NewsVo> newsVos,CallerParam caller){

        if(CollectionUtils.isEmpty(newsVos)){
            return Collections.EMPTY_LIST;
        }
        List<BlockContentVo> blockContents = Lists.newArrayList();
        for(NewsVo newsVo : newsVos){
            blockContents.add(createBlockContent(newsVo,caller));
        }

        return blockContents;

    }

    @Override
    public List<BlockContentVo> getBlockContents(long rid,CallerBean caller) {
        if(rid == 0L){
            LOG.warn("rid can not be null,cmsId:{}",rid);
            return Collections.EMPTY_LIST;
        }

        ComboResource resourceData = ComboResourceCreaters.getComboResource(rid, PageUtils.createPageParam(1,30), caller.getCallerParam());
        if(null == resourceData) return Collections.EMPTY_LIST;
        List<BaseCvo> contents = resourceData.getContents();

        List<BlockContentVo> results = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(contents)) {
            for (BaseCvo content : contents) {
                BlockContentVo blockContent = new BlockContentVo();
                blockContent.setTitle(content.getName());
                blockContent.setContent(content.getId());
                blockContent.setTvPic(content.getImageUrl() != null ? content.getImageUrl().get("169") : "");
                if(ResourceItemType.NEWS == content.getType() || ResourceItemType.VIDEO_NEWS == content.getType()) {
                    NewsCvo newsCvo = (NewsCvo)content;
                    blockContent.setContent(newsCvo.getVid() + "");
                    blockContent.setType(1);
                }else if(ResourceItemType.EPISODE == content.getType()) {
                    blockContent.setContent(content.getId());
                        TComboEpisode comboEpisode = QmtSbcEpisodeApis.getTComboEpisodeById(LeNumberUtils.toLong(content.getId()), caller.getCallerParam());
                        if(null != comboEpisode) {
                            blockContent.setStartTime(comboEpisode.getStartTime());
                            blockContent.setStatus(comboEpisode.getStatus().getValue());
                            if(comboEpisode.isIsPay()){
                                blockContent.setCornerMark(1);
                            }
                        }
                    blockContent.setType(3);
                }else if(ResourceItemType.VIDEO == content.getType()){//视频类型
                    blockContent.setType(1);
                    if(Constants.CIBN_CALLER == caller.getCallerId()) {
                        TVideo tVideo = QmtSbcApis.getTVideoById(LeNumberUtils.toLong(content.getId()), caller.getCallerParam());
                        if (null == tVideo) {
                            LOG.warn("this video has not exsits id : {}, caller : {}", content.getId(), caller);
                            continue;
                        }
                        if (CollectionUtils.isEmpty(tVideo.getValidLicences()) || !tVideo.getValidLicences().contains(TvLicence.CIBN)) {
                            LOG.warn("this video has no valid licences. id : {}, caller : {},licences:{}", content.getId(), caller,tVideo.getValidLicences());
                            continue;
                        }
                        if(tVideo.getIsPay() == Constants.CHARGE){
                            blockContent.setCornerMark(1);
                        }else{
                            if(tVideo.getAid() > 0){
                                blockContent.setCornerMark(2);
                            }else {
                                if (tVideo.getContentType() == VideoContentType.RECORD) {
//                                    blockContent.setCornerMark(5);
                                } else if (tVideo.getContentType() == VideoContentType.HIGHLIGHTS) {
                                    blockContent.setCornerMark(6);
                                }
                            }
                        }
                        blockContent.setContent(tVideo.getLeecoVid()+"");
                    }
                }else if(ResourceItemType.SUBJECT == content.getType()){//专题类型
//                    blockContent.setCornerMark(3);
                    blockContent.setType(5);
                }else if(ResourceItemType.IMAGE_ALBUM == content.getType()){//新闻类型
                    TNews news = QmtSbcApis.getTNewsById(LeNumberUtils.toLong(content.getId()), caller.getCallerParam());
                    if (null != news && news.getType() == NewsType.IMAGE_ALBUM) {
                        blockContent.setCornerMark(4);

                    }
                    blockContent.setType(11);
                }else{
                    continue;
                }
                results.add(blockContent);
            }
        }
        return results;
    }

}


