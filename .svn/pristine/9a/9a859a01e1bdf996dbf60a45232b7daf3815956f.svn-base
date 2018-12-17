package com.lesports.qmt.web.api.core.creater;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.lesports.api.common.CallerParam;
import com.lesports.id.client.LeIdApis;
import com.lesports.model.RecommendRec;
import com.lesports.model.RecommendSubject;
import com.lesports.qmt.resource.cvo.TvContentCvo;
import com.lesports.qmt.sbc.api.common.VideoContentType;
import com.lesports.qmt.sbc.api.dto.ResourceItemType;
import com.lesports.qmt.sbc.api.dto.TComboEpisode;
import com.lesports.qmt.sbc.api.dto.TLiveStream;
import com.lesports.qmt.sbc.api.dto.TVideo;
import com.lesports.qmt.sbc.client.QmtSbcApis;
import com.lesports.qmt.sbc.client.QmtSbcEpisodeApis;
import com.lesports.qmt.web.api.core.util.Constants;
import com.lesports.qmt.web.api.core.util.TVResourceUtils;
import com.lesports.qmt.web.api.core.util.TvSuggestType;
import com.lesports.qmt.web.api.core.vo.TvSuggestVo;
import com.lesports.qmt.web.api.core.vo.TvThemeVo;
import com.lesports.sms.api.common.Platform;
import com.lesports.sms.api.common.TvResourceType;
import com.lesports.utils.math.LeNumberUtils;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
* Created by ruiyuansheng on 2015/10/26.
*/
@Component("tvSuggestVoCreater")
public class TvSuggestVoCreater {

    private static final Logger LOG = LoggerFactory.getLogger(TvSuggestVoCreater.class);
    private static final int lestoreType = 1;
    private static final int leGameDetailType = 11;
    private static final int leGameTopicResource = 10240;
    private static final int leGameTopicType = 3;

    private static final Function<TvContentCvo,TvSuggestVo> suugestFunction = new Function<TvContentCvo, TvSuggestVo>() {
        @Nullable
        @Override
        public TvSuggestVo apply(@Nullable TvContentCvo input) {
            return null;
        }
    };


    public TvSuggestVo createTvSuggestVo(TvContentCvo tvContentVo,CallerParam caller,TvSuggestType suggestType){
        TvSuggestVo tvSuggestVo = new TvSuggestVo();
        tvSuggestVo.setSuggestType(suggestType.getValue());
        tvSuggestVo.setResourceId(LeNumberUtils.toLong(tvContentVo.getId()));
        ResourceItemType resourceItemType = tvContentVo.getType();
        if(null != resourceItemType && resourceItemType.equals(ResourceItemType.TV_CONTENT)){
            //todo 节目类型桌面需要获取直播id，首页需要节目id
            if(tvContentVo.getSubType().equals(ResourceItemType.EPISODE)){
                TComboEpisode comboEpisode = QmtSbcEpisodeApis.getTComboEpisodeById(tvSuggestVo.getResourceId(), caller);
                if(null != comboEpisode) {
                    tvSuggestVo.setStatus(comboEpisode.getStatus().getValue());
                    tvSuggestVo.setStartTime(comboEpisode.getStartTime());
                    List<TLiveStream> liveStreamList = comboEpisode.getStreams();
                    if(CollectionUtils.isNotEmpty(liveStreamList) && suggestType == TvSuggestType.DESKTOP) {
                        if(null != liveStreamList.get(0)) {
                            String liveId =liveStreamList.get(0).getLiveId();
                            tvSuggestVo.setResourceId(LeNumberUtils.toLong(liveId));
                        }
                    }
                    if(comboEpisode.isIsPay()) {
                        tvSuggestVo.setCornerMark(1);
                    }
                }

            }else if(tvContentVo.getSubType().equals(ResourceItemType.VIDEO)){
                TVideo video = QmtSbcApis.getTVideoById(LeIdApis.convertMmsVideoIdToLeSportsVideoId(tvSuggestVo.getResourceId()), caller);
                if(null != video) {
                    if (video.getIsPay() == 1) {
                        tvSuggestVo.setCornerMark(1);
                    } else {
                        if (video.getAid() > 0) {
                            tvSuggestVo.setCornerMark(2);
                        } else {
                            if (video.getContentType().equals(VideoContentType.RECORD)) {
//                                tvSuggestVo.setCornerMark(5);
                            } else if (video.getContentType().equals(VideoContentType.HIGHLIGHTS)) {
                                tvSuggestVo.setCornerMark(6);
                            }
                        }
                    }
                }
            }

            tvSuggestVo.setResourceType(TVResourceUtils.RESOURCE_ITEM_TYPE_INTEGER_MAP.get(tvContentVo.getSubType()));
        }else {
            tvSuggestVo.setResourceType(TVResourceUtils.RESOURCE_ITEM_TYPE_INTEGER_MAP.get(tvContentVo.getType()));
        }
        tvSuggestVo.setSubType(tvContentVo.getSubType());
        tvSuggestVo.setImageUrl(tvContentVo.getImage());
        tvSuggestVo.setTitle(tvContentVo.getName());
//        tvSuggestVo.setOrder(tvContentVo.getOrder());
        tvSuggestVo.setShowTitle(tvContentVo.getShowTitle());
        tvSuggestVo.setH5Url(tvContentVo.getH5Url());
        tvSuggestVo.setRedirectValue(tvContentVo.getOtherContent());
        Integer actionType =  TVResourceUtils.ACTION_TYPE_MAP.get(tvContentVo.getSubType());
        tvSuggestVo.setActionType(null == actionType ? 0 : actionType);


        if(suggestType == TvSuggestType.DESKTOP) {
            generateActionParam(tvSuggestVo);
        }

        return tvSuggestVo;
    }




    public List<TvSuggestVo> createTvSuggestVos(List<RecommendSubject> recommendSubjects) {
        List<TvSuggestVo> tvSuggestVos = Lists.newArrayList();
        if(CollectionUtils.isNotEmpty(recommendSubjects)){
            for(RecommendSubject recommendSubject : recommendSubjects){
                List<TvSuggestVo> tvSuggestVoList = createTvSuggestVo(recommendSubject);
                if(CollectionUtils.isNotEmpty(tvSuggestVoList)){
                    tvSuggestVos.addAll(tvSuggestVoList);
                }
            }
        }
        return tvSuggestVos;
    }

    public List<TvThemeVo> createTvThemeVos(List<RecommendSubject> recommendSubjects) {
        List<TvThemeVo> tvThemeVos = Lists.newArrayList();
        if(CollectionUtils.isNotEmpty(recommendSubjects)){
            for(RecommendSubject recommendSubject : recommendSubjects){
                TvThemeVo tvThemeVo = createTvThemeVo(recommendSubject);
                tvThemeVos.add(tvThemeVo);
            }
        }
        return tvThemeVos;
    }

    public TvThemeVo createTvThemeVo(RecommendSubject recommendSubject) {
        TvThemeVo tvThemeVo = new TvThemeVo();
        TvSuggestVo tvSuggestVo = new TvSuggestVo();
        tvSuggestVo.setResourceType(TvResourceType.RECOMMEND_THEME.getValue());
        tvSuggestVo.setResourceId(LeNumberUtils.toLong(recommendSubject.getThemeID().replace("4_", "")));
        tvSuggestVo.setImageUrl(recommendSubject.getIcon());
        tvSuggestVo.setTitle(recommendSubject.getThemeName());
        tvSuggestVo.setShowTitle(true);
        tvSuggestVo.setSuggestType(TvSuggestType.DESKTOP.getValue());
        tvSuggestVo.setActionType(0);
        tvSuggestVo.setIsSuggest(true);
        tvSuggestVo.setIsTheme(true);
        tvSuggestVo.setReid(recommendSubject.getReid());
        tvSuggestVo.setArea(recommendSubject.getArea());
        tvSuggestVo.setBucket(recommendSubject.getBucket());
        generateActionParam(tvSuggestVo);
        tvThemeVo.setTheme(tvSuggestVo);
        List<TvSuggestVo> subSuggestVos = Lists.newArrayList();
        List<RecommendRec> recommendRecs = recommendSubject.getRec();
        if (CollectionUtils.isNotEmpty(recommendRecs)) {
            for (RecommendRec recommendRec : recommendRecs) {
                subSuggestVos.add(createTvSuggestVo(recommendRec));
            }
        }
        tvThemeVo.setSubSuggests(subSuggestVos);

        return tvThemeVo;
    }


    public List<TvSuggestVo> createTvSuggestVo(RecommendSubject recommendSubject) {
        List<TvSuggestVo> tvSuggestVos = Lists.newArrayList();
        TvSuggestVo tvSuggestVo = new TvSuggestVo();
        tvSuggestVo.setResourceType(TvResourceType.RECOMMEND_THEME.getValue());
        tvSuggestVo.setResourceId(LeNumberUtils.toLong(recommendSubject.getThemeID().replace("4_", "")));
        tvSuggestVo.setImageUrl(recommendSubject.getIcon());
        tvSuggestVo.setTitle(recommendSubject.getThemeName());
        tvSuggestVo.setShowTitle(true);
        tvSuggestVo.setSuggestType(TvSuggestType.DESKTOP.getValue());
        tvSuggestVo.setActionType(0);
        tvSuggestVo.setIsSuggest(true);
        tvSuggestVo.setIsTheme(true);
        tvSuggestVo.setReid(recommendSubject.getReid());
        tvSuggestVo.setArea(recommendSubject.getArea());
        tvSuggestVo.setBucket(recommendSubject.getBucket());
        generateActionParam(tvSuggestVo);
        tvSuggestVos.add(tvSuggestVo);
        List<RecommendRec>recommendRecs = recommendSubject.getRec();
        if(CollectionUtils.isNotEmpty(recommendRecs)){
            for(RecommendRec recommendRec : recommendRecs){
                tvSuggestVos.add(createTvSuggestVo(recommendRec));
            }
        }

        return tvSuggestVos;
    }

    public List<TvSuggestVo> createTvSuggestVosByRecs(List<RecommendRec> recommendRecs){
        List<TvSuggestVo> tvSuggestVos = Lists.newArrayList();
        if(CollectionUtils.isNotEmpty(recommendRecs)){
            for(RecommendRec recommendRec : recommendRecs){
                tvSuggestVos.add(createTvSuggestVo(recommendRec));
            }
        }
        return tvSuggestVos;
    }

    public TvSuggestVo createTvSuggestVo(RecommendRec recommendRec) {
        TvSuggestVo tvSuggestVo = new TvSuggestVo();
        tvSuggestVo.setResourceId(recommendRec.getVid());
        tvSuggestVo.setResourceType(TvResourceType.VIDEO.getValue());
        tvSuggestVo.setImageUrl(recommendRec.getPicurl());
        tvSuggestVo.setTitle(recommendRec.getTitle());
        tvSuggestVo.setShowTitle(true);
        tvSuggestVo.setSuggestType(TvSuggestType.DESKTOP.getValue());
        tvSuggestVo.setActionType(0);
        tvSuggestVo.setIsSuggest(true);
        if (CollectionUtils.isNotEmpty(recommendRec.getPayPlatforms()) && recommendRec.getPayPlatforms().contains(Platform.TV)) {
            tvSuggestVo.setCornerMark(1);
        } else {
            if (recommendRec.getSmsAid() > 0) {
                tvSuggestVo.setCornerMark(2);
            } else {
                if (recommendRec.getSmsVideoType().equals(VideoContentType.RECORD)) {
                } else if (recommendRec.getSmsVideoType().equals(VideoContentType.HIGHLIGHTS)) {
                    tvSuggestVo.setCornerMark(6);
                }
            }
        }
        generateActionParam(tvSuggestVo);
        return tvSuggestVo;
    }

    /**
     * 生成打洞参数
     * @param tvSuggestVo
     */
    private void generateActionParam(TvSuggestVo tvSuggestVo){
        Map<String,Object> map = Maps.newHashMap();
        if(tvSuggestVo.getActionType() == 0){
            generateLesportsAction(tvSuggestVo, map);
        }else if(tvSuggestVo.getActionType() == 1){
            generateLestoreAction(tvSuggestVo, map);
        }else if(tvSuggestVo.getActionType() == 2){
            generateLeGameDetailAction(tvSuggestVo, map);
        }else if(tvSuggestVo.getActionType() == 3){
            generateLeGameTopicAction(tvSuggestVo, map);
        }else{
            generateLesportsAction(tvSuggestVo, map);
        }


    }

    /**
     * 乐视体育客户端打洞
     * @param tvSuggestVo
     * @param map
     */
    private void generateLesportsAction(TvSuggestVo tvSuggestVo, Map<String, Object> map) {
        tvSuggestVo.setAction(Constants.LESPORTS_TV_ACTION);
        map.put("type",tvSuggestVo.getResourceType());
        if(8 == tvSuggestVo.getResourceType()){
            map.put("type",14);
        }
        if(10 == tvSuggestVo.getResourceType()){
            map.put("type",11);
        }
        map.put("from","com.lesports.launcher");
        map.put("isSuggest",tvSuggestVo.getIsSuggest());
        map.put("source",Constants.LESPORTS_TV_SOURCE);
        Map<String,Object> jumpMap = Maps.newHashMap();
        if(tvSuggestVo.getResourceType() == 0){//比赛大厅参数
            generateMatchHallAction(tvSuggestVo,map, jumpMap);
        }else if(tvSuggestVo.getResourceType() == 1){//频道参数
            generateChannelAction(tvSuggestVo, jumpMap);
        }else if(tvSuggestVo.getResourceType() == 2){//专题参数
            jumpMap.put("topicId",tvSuggestVo.getResourceId());
        }else if(tvSuggestVo.getResourceType() == 3){//自制节目参数
            jumpMap.put("programId", tvSuggestVo.getResourceId());
        }else if(tvSuggestVo.getResourceType() == 4){//收银台
            jumpMap.put("fromTag", "LeSportsDesk");
        }else if(tvSuggestVo.getResourceType() == 5){//直播参数
            jumpMap.put("liveId", tvSuggestVo.getResourceId());
        }else if(tvSuggestVo.getResourceType() == 6){//视频参数
            jumpMap.put("vid",tvSuggestVo.getResourceId());
            jumpMap.put("videoName", tvSuggestVo.getTitle());
        }else if(tvSuggestVo.getResourceType() == 7){//特殊频道参数，欧洲杯及美洲杯
            jumpMap.put("gameId",tvSuggestVo.getResourceId());
            jumpMap.put("gameName", tvSuggestVo.getTitle());
        }else if(tvSuggestVo.getResourceType() == 8){//图集新闻
            jumpMap.put("id",tvSuggestVo.getResourceId());
        }else if(tvSuggestVo.getResourceType() == 9){//轮播台
            map.put("type",15);
            jumpMap.put("carouselId",tvSuggestVo.getResourceId());
            jumpMap.put("carouselName", tvSuggestVo.getTitle());
        }else if(tvSuggestVo.getResourceType() == 10){//图集新闻
            jumpMap.put("url",tvSuggestVo.getH5Url());
        }else if(tvSuggestVo.getResourceType() == 12){//推荐主题
            map.put("type",17);
            jumpMap.put("subjectId",tvSuggestVo.getResourceId());
        }else{
            LOG.warn("resource type no exsits,resourceType:{}",tvSuggestVo.getResourceType());
        }
        generateCommand(tvSuggestVo, map, jumpMap);
    }

    /**
     * 应用商店打洞
     * @param tvSuggestVo
     * @param map
     */
    private void generateLestoreAction(TvSuggestVo tvSuggestVo, Map<String, Object> map) {
        tvSuggestVo.setAction(Constants.LESTORE_TV_ACTION);
        map.put("type",lestoreType);
        map.put("subjectId",tvSuggestVo.getResourceId().toString());
        map.put("from",Constants.LESTORE_TV_FROM);
        tvSuggestVo.setCommand(JSONObject.toJSONString(map));
    }
    private void generateLeGameDetailAction(TvSuggestVo tvSuggestVo, Map<String, Object> map) {
        tvSuggestVo.setAction(Constants.LEGAME_TV_ACTION);
        map.put("type",leGameDetailType);
        map.put("resource",leGameTopicResource);
        map.put("value",tvSuggestVo.getRedirectValue());
        tvSuggestVo.setCommand(JSONObject.toJSONString(map));
    }
    private void generateLeGameTopicAction(TvSuggestVo tvSuggestVo, Map<String, Object> map) {
        tvSuggestVo.setAction(Constants.LEGAME_TV_ACTION);
        map.put("type",leGameTopicType);
        map.put("resource",leGameTopicResource);
        map.put("value",tvSuggestVo.getRedirectValue());
        tvSuggestVo.setCommand(JSONObject.toJSONString(map));
    }

    private void generateCommand(TvSuggestVo tvSuggestVo, Map<String, Object> map, Map<String, Object> jumpMap) {
        map.put("value",jumpMap);
        tvSuggestVo.setCommand(JSONObject.toJSONString(map));
    }

    private void generateChannelAction(TvSuggestVo tvSuggestVo, Map<String, Object> jumpMap) {
        jumpMap.put("channelId", tvSuggestVo.getResourceId());
        jumpMap.put("channelName", tvSuggestVo.getTitle());
    }

    private void generateMatchHallAction(TvSuggestVo tvSuggestVo, Map<String, Object> map, Map<String, Object> jumpMap) {
        if(null != tvSuggestVo.getSubType() && tvSuggestVo.getSubType().equals(ResourceItemType.HALL_ALL)) {
            jumpMap.put("liveStatus", -1);//比赛大厅（全部）
            jumpMap.put("jumpType", 1);
        }else if(null != tvSuggestVo.getSubType() && tvSuggestVo.getSubType().equals(ResourceItemType.HALL_NOT_START)){
            jumpMap.put("liveStatus", 3);//比赛大厅（未结束）
            jumpMap.put("jumpType",1);
        }else if(null != tvSuggestVo.getSubType() && tvSuggestVo.getSubType().equals(ResourceItemType.HALL_END)){
            jumpMap.put("liveStatus", 2);//比赛大厅（已结束）
            jumpMap.put("jumpType", 1);
        }else if(null != tvSuggestVo.getSubType() && tvSuggestVo.getSubType().equals(ResourceItemType.VIP)){
            jumpMap.put("jumpType", 6);
        }else if(null != tvSuggestVo.getSubType() && tvSuggestVo.getSubType().equals(ResourceItemType.COUNTER)){
                map.put("type",4);
                if(tvSuggestVo.getSuggestType() == 2) {
                    jumpMap.put("fromTag", "LeSportsDesk");
                }else{
                    jumpMap.put("fromTag", "LeSportsTV");
                }
        }else if(null != tvSuggestVo.getSubType() && tvSuggestVo.getSubType().equals(ResourceItemType.SEARCH)){
            map.put("type",16);
            if(tvSuggestVo.getSuggestType() == 2) {
                jumpMap.put("fromTag", "LeSportsDesk");
            }else{
                jumpMap.put("fromTag", "LeSportsTV");
            }
        }else {
            jumpMap.put("jumpType",TVResourceUtils.MODEL_MAP.get(tvSuggestVo.getSubType()));
        }
    }


}
