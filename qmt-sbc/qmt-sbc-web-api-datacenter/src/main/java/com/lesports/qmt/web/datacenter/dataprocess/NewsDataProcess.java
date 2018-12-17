package com.lesports.qmt.web.datacenter.dataprocess;

import com.google.common.collect.Lists;
import com.lesports.api.common.*;
import com.lesports.qmt.sbc.api.common.NewsType;
import com.lesports.qmt.sbc.api.dto.ResourceItemType;
import com.lesports.qmt.sbc.api.dto.TNews;
import com.lesports.qmt.sbc.api.dto.TResourceContent;
import com.lesports.qmt.sbc.api.service.GetRelatedNewsParam;
import com.lesports.qmt.sbc.client.QmtSbcApis;
import com.lesports.qmt.web.datacenter.vo.H5Vo;
import com.lesports.qmt.web.datacenter.vo.NewsVo;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * create by wangjichuan  date:16-12-20 time:17:50
 */
public class NewsDataProcess implements DataProcess<TNews,NewsVo> {

    @Override
    public NewsVo getEntity() {
        return new NewsVo();
    }

    @Override
    public void constructManualRemoteData(NewsVo newsVo, String itemId, CallerParam callerParam) {
        TNews tNews = QmtSbcApis.getTNewsById(Long.parseLong(itemId), callerParam);
        if(tNews == null){
            return;
        }
        fillVoByDto(newsVo,tNews);
    }

    @Override
    public void fillVoByDto(NewsVo newsVo, TNews tNews) {
        newsVo.setId(tNews.getId()+"");
        newsVo.setName(tNews.getName());
        newsVo.setImageUrl(tNews.getCoverImage());
        newsVo.setCommentId(tNews.getCommentId());
        newsVo.setCreateTime(tNews.getPublishAt());
        newsVo.setUrl(tNews.getPlayLink());
    }

    @Override
    public List<NewsVo> constructAutoRemoteData(TResourceContent tResourceContent,PageParam pageParam, CallerParam callerParam) {

        List<Long> newsIds = QmtSbcApis.getNewsIdsByRelatedId(prepareParam(tResourceContent,pageParam,callerParam),pageParam,callerParam);
        List<TNews> tNewsList = QmtSbcApis.getTNewsByIds(newsIds,callerParam);
        List<NewsVo> newsVos = new ArrayList<>();
        fillVosByDtos(newsVos,tNewsList);
        fillVosType(newsVos,tResourceContent.getType());
        return newsVos;
    }


    private GetRelatedNewsParam prepareParam(TResourceContent tResourceContent,PageParam pageParam, CallerParam callerParam){
        GetRelatedNewsParam getRelatedNewsParam = new GetRelatedNewsParam();
        getRelatedNewsParam.setStar(tResourceContent.getStarLevel());
        List<com.lesports.qmt.sbc.api.common.NewsType> newsTypes = new ArrayList<>();
        List<ResourceItemType> resourceDataTypes = tResourceContent.getDataSearch();
        if(CollectionUtils.isNotEmpty(tResourceContent.getDataSearch())){
            for(ResourceItemType resourceItemType : resourceDataTypes){
                if(resourceItemType == ResourceItemType.RICHTEXT
                        || resourceItemType == ResourceItemType.IMAGE_ALBUM){
                    newsTypes.add(NewsType.valueOf(resourceItemType.name()));
                }else if(resourceItemType == ResourceItemType.VIDEO_NEWS){
                    newsTypes.add(NewsType.VIDEO);
                }
            }
        }
        getRelatedNewsParam.setTypes(newsTypes);
        getRelatedNewsParam.setRelatedIds(tResourceContent.getTagIds());
        if(tResourceContent.getTimeOrder() == 1){//按照更新时间来排序
            pageParam.setSort(new Sort(Lists.newArrayList(new Order("lasttime", Direction.DESC))));
        }
        return getRelatedNewsParam;
    }
}
