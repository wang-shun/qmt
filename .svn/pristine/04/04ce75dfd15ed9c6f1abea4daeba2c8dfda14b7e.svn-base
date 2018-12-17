package com.lesports.qmt.web.api.core.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.lesports.api.common.CallerParam;
import com.lesports.api.common.PubChannel;
import com.lesports.jersey.support.model.CallerBean;
import com.lesports.jersey.support.model.PageBean;
import com.lesports.model.RecommendRec;
import com.lesports.model.RecommendSubject;
import com.lesports.qmt.config.api.dto.TMenu;
import com.lesports.qmt.config.client.QmtConfigApis;
import com.lesports.qmt.resource.ComboResourceCreaters;
import com.lesports.qmt.resource.core.ComboResource;
import com.lesports.qmt.resource.cvo.BaseCvo;
import com.lesports.qmt.resource.cvo.TvContentCvo;
import com.lesports.qmt.web.api.core.creater.TvSuggestVoCreater;
import com.lesports.qmt.web.api.core.creater.VideoVoCreater;
import com.lesports.qmt.web.api.core.service.TvSuggestService;
import com.lesports.qmt.web.api.core.util.Constants;
import com.lesports.qmt.web.api.core.util.TVResourceUtils;
import com.lesports.qmt.web.api.core.util.TvSuggestType;
import com.lesports.qmt.web.api.core.vo.ReCommendTheme;
import com.lesports.qmt.web.api.core.vo.TvSuggestVo;
import com.lesports.qmt.web.api.core.vo.VideoVo;
import com.lesports.utils.PageUtils;
import com.lesports.utils.RecommendApis;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by ruiyuansheng on 2016/1/11.
 */
@Service("tvSuggestService")
public class TvSuggestServiceImpl implements TvSuggestService {


    @Resource
    TvSuggestVoCreater tvSuggestVoCreater;

    @Resource
    VideoVoCreater videoVoCreater;


    /**
     * 获取TV首页
     * @return
     */
    @Override
    public Map<String,Object> getTvHomePageVo(CallerParam callerParam){

        Map<String, Object> results = Maps.newHashMap();
        long recommendId = 0l;
        long entranceId = 0l;
        long liveId = 0l;
        long newsId = 0l;
        long topicId = 0l;
        if(callerParam.getPubChannel().equals(PubChannel.LETV)){
            recommendId = TVResourceUtils.HOMEPAGE_RECOMMENDS;
            entranceId = TVResourceUtils.HOMEPAGE_ENTRANCES;
            topicId = TVResourceUtils.HOMEPAGE_TOPICS;
            liveId = TVResourceUtils.HOMEPAGE_LIVES;
            newsId = TVResourceUtils.HOMEPAGE_NEWS;
        }else{
            recommendId = TVResourceUtils.HOMEPAGE_RECOMMENDS_TCL;
            entranceId = TVResourceUtils.HOMEPAGE_ENTRANCES_TCL;
            topicId = TVResourceUtils.HOMEPAGE_TOPICS_TCL;
            liveId = TVResourceUtils.HOMEPAGE_LIVES_TCL;
            newsId =  TVResourceUtils.HOMEPAGE_NEWS_TCL;
        }

        results.put("recommends", getResources(callerParam, recommendId,TvSuggestType.HOMEPAGE));
        results.put("entrances", getResources(callerParam, entranceId,TvSuggestType.HOMEPAGE));
        results.put("lives", getResources(callerParam, liveId,TvSuggestType.HOMEPAGE));
        results.put("news", getResources(callerParam,newsId,TvSuggestType.HOMEPAGE));
        results.put("topics", getResources(callerParam,topicId ,TvSuggestType.HOMEPAGE));

        return results;

    }

    @Override
    public Map<String,Object> getTvDesktopVo(CallerParam callerParam){

        long recommendId = 0l;
        long entranceId = 0l;
        long topicId = 0l;
        long aggregationId = 0l;
        long carouselId = 0l;
        Map<String, Object> results = Maps.newHashMap();
        recommendId = TVResourceUtils.DESKTOP_RECOMMENDS;
        entranceId = TVResourceUtils.DESKTOP_ENTRANCES;
        topicId = TVResourceUtils.DESKTOP_TOPICS;
        aggregationId = TVResourceUtils.DESKTOP_AGGREGATIONS;
        carouselId = TVResourceUtils.DESKTOP_CAROUSELS;
        results.put("recommends", getResources(callerParam,recommendId ,TvSuggestType.DESKTOP));
        results.put("entrances", getResources(callerParam, entranceId,TvSuggestType.DESKTOP));
        results.put("topics", getResources(callerParam,topicId,TvSuggestType.DESKTOP));
        results.put("aggregations", getResources(callerParam, aggregationId,TvSuggestType.DESKTOP));
        results.put("carousels", getResources(callerParam, carouselId,TvSuggestType.DESKTOP));

        return results;

    }

    @Override
    public List<TvSuggestVo> getTvMatchHallVo(CallerParam callerParam){

        return getResources(callerParam, TVResourceUtils.MATCHHALL_ENTITIES,TvSuggestType.MATCHHALL);

    }

    private List<TvSuggestVo>  getResources(CallerParam callerParam, Long id,TvSuggestType suggestType) {
        List<TvSuggestVo> recommends = Lists.newArrayList();
		ComboResource resourceData =  ComboResourceCreaters.getComboResource(id, PageUtils.createPageParam(1,20), callerParam);
        if(null == resourceData){
            return Collections.EMPTY_LIST;
        }
        List<BaseCvo> contents = resourceData.getContents();
        if(CollectionUtils.isNotEmpty(contents))
        {
            for (BaseCvo content : contents) {
                TvContentCvo tvContentVo = (TvContentCvo)content;
                recommends.add(tvSuggestVoCreater.createTvSuggestVo(tvContentVo, callerParam, suggestType));
                List<TvContentCvo> items = tvContentVo.getItems();
                if(CollectionUtils.isNotEmpty(items)){
                    for (TvContentCvo item : items) {
                        recommends.add(tvSuggestVoCreater.createTvSuggestVo(item, callerParam, suggestType));
                    }

                }

            }

        }

        return recommends;
    }


    @Override
    public ReCommendTheme getTvSuggestByThemeId(String themeId){
        List<VideoVo> videoVos = Lists.newArrayList();
        ReCommendTheme reCommendTheme = new ReCommendTheme();
        RecommendSubject recommendSubject =  RecommendApis.getMoreRecoomendsById(themeId, Constants.MAX_THEME_COUNT);
        if(CollectionUtils.isNotEmpty(recommendSubject.getRec())){
            for(RecommendRec recommendRec : recommendSubject.getRec()){
                videoVos.add(videoVoCreater.createVideoVo(recommendRec));
            }
        }
        reCommendTheme.setName(recommendSubject.getThemeName());
        reCommendTheme.setVideoVos(videoVos);
        return reCommendTheme;
    }

    public List getRecommends(String uid,String lc,int type,int page,CallerParam callerParam){
        List<RecommendSubject> recommendSubjects = Lists.newArrayList();
        if(1 == type) {
            recommendSubjects = RecommendApis.getRecoomends(uid, lc, "", page);
            return tvSuggestVoCreater.createTvSuggestVos(recommendSubjects);
        }else{
            recommendSubjects = RecommendApis.getRecoomends(uid, lc, "3-7", page);
            return tvSuggestVoCreater.createTvThemeVos(recommendSubjects);
        }

    }




}
