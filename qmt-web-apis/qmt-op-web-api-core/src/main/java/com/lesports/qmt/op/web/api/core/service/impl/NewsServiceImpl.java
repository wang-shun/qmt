package com.lesports.qmt.op.web.api.core.service.impl;

import com.google.common.collect.Lists;
import com.lesports.api.common.CallerParam;
import com.lesports.api.common.PageParam;
import com.lesports.qmt.config.api.dto.TTag;
import com.lesports.qmt.config.client.QmtConfigApis;
import com.lesports.qmt.op.web.api.core.cache.impl.TagCache;
import com.lesports.qmt.op.web.api.core.creater.NewsVoCreater;
import com.lesports.qmt.op.web.api.core.service.NewsService;
import com.lesports.qmt.op.web.api.core.vo.GetTagCacheParam;
import com.lesports.qmt.op.web.api.core.vo.NewsVo;
import com.lesports.qmt.op.web.api.core.vo.OlyNewsVo;
import com.lesports.qmt.sbc.api.common.NewsType;
import com.lesports.qmt.sbc.api.dto.TNews;
import com.lesports.qmt.sbc.api.dto.TVideo;
import com.lesports.qmt.sbc.api.service.GetRelatedNewsParam;
import com.lesports.qmt.sbc.client.QmtSbcApis;
import com.lesports.utils.LeStringUtils;
import com.lesports.utils.math.LeNumberUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

/**
 * Created by lufei1 on 2015/11/5.
 */
@Service("newsService")
public class NewsServiceImpl  implements NewsService {

    private static final Logger LOG = LoggerFactory.getLogger(NewsServiceImpl.class);
    private static final String OLY_TAG_ID = "100119008";

    @Resource
    private NewsVoCreater newsVoCreater;
    @Resource
    private TagCache tagCache;

    @Override
    public List<NewsVo> getRecommendNews(PageParam page,CallerParam caller) {
        List<NewsVo> recommendList = Lists.newArrayList();
        List<Long> ids = Lists.newArrayList();
        int maxPageNumber = 10;
        int pageNumber = page.getPage();

        while (recommendList.size()<page.getCount() && pageNumber<=maxPageNumber) {
            page.setPage(pageNumber);

            //todo 走资源位
        }
        return recommendList;
    }

    @Override
    public List<NewsVo> getTheLatestVideos(CallerParam caller, PageParam page) {
        List<NewsVo> newsVos = Lists.newArrayList();
        List<Long> ids = QmtSbcApis.getLatestVideoIds(page, caller);
        if (CollectionUtils.isEmpty(ids)) {
            return Collections.EMPTY_LIST;
        }
        List<TVideo> tVideos = QmtSbcApis.getTVideosByIds(ids,caller);
        for (TVideo tVideo : tVideos) {
            if (tVideo != null) {
                newsVos.add(newsVoCreater.createNewsVo(tVideo));
            }
        }
        return newsVos;
    }

    @Override
    public List<OlyNewsVo> getNewsByTag(String startTime,String first,CallerParam caller, PageParam page) {
        List<OlyNewsVo> returnNews = Lists.newArrayList();


        String tagId = OLY_TAG_ID;

        GetRelatedNewsParam p = new GetRelatedNewsParam();
        p.setRelatedIds(Lists.newArrayList(Long.parseLong(tagId)));

        List<Long> typeArray = LeStringUtils.commaString2LongList("0,2,3");
        List<NewsType> typeList = Lists.newArrayList();

        for (Long typeValue : typeArray) {
            NewsType t = NewsType.findByValue(LeNumberUtils.toInt(typeValue));
            if (t != null) {
                typeList.add(t);
            }
        }
        p.setTypes(typeList);

        int curPage = 0;
        if(StringUtils.isBlank(startTime) && "0".equals(first)){
            return returnNews;
        }

        while(true){
            curPage ++;
            page.setPage(curPage);
            LOG.info("p: {} , page: {} , caller : {} ",p,page,caller);
            long start = System.currentTimeMillis();
            List<Long> ids = QmtSbcApis.getNewsIdsByRelatedId(p, page, caller);

            if(CollectionUtils.isEmpty(ids)){
                return returnNews;
            }
            List<TNews> relatedVideos = QmtSbcApis.getTNewsByIdsParallel(ids, caller);
            LOG.info("page:{} ,search time: {}" ,curPage, (System.currentTimeMillis() - start));
            for (TNews tNews : relatedVideos) {
                //不是第一次获取，根据startTime过滤
                if("0".equals(first)){
                    if(tNews.getPublishAt().compareTo(startTime)<0){
                        return returnNews;
                    }
                }
                returnNews.add(newsVoCreater.createOlyNewsVo(tNews));
            }
        }
    }


    private TTag getTag(String tagName, CallerParam caller) {
        GetTagCacheParam getTagCacheParam = new GetTagCacheParam(tagName,caller) ;
        TTag tTag = tagCache.getTag(getTagCacheParam);
        if(null != tTag){
            return tTag;
        }
        tTag = QmtConfigApis.getTTagByName(tagName);
        if(null != tTag){
            tagCache.saveTag(getTagCacheParam, tTag);
        }
        return tTag;
    }

}
