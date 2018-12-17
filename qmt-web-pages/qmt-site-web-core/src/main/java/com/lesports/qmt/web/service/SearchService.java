package com.lesports.qmt.web.service;

import com.lesports.api.common.CallerParam;
//import com.lesports.cms.web.creater.SearchResultAdapter;
//import com.lesports.cms.web.helper.MetaFiller;
//import com.lesports.cms.web.model.HallEpisodeVo;
//import com.lesports.cms.web.model.SearchLiveResult;
import com.lesports.qmt.web.creater.SearchResultAdapter;
import com.lesports.qmt.web.helper.MetaFiller;
import com.lesports.qmt.web.model.HallEpisodeVo;
import com.lesports.qmt.web.model.SearchLiveResult;
import com.lesports.utils.SearchApis;
import com.lesports.utils.pojo.SearchData;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.annotation.Resource;
import java.util.*;

/**
* Created by ruiyuansheng on 2015/11/16.
*/
@Service("searchService")
public class SearchService {

    @Resource
    private SearchResultAdapter searchResultAdapter;

    @Resource
    private MetaFiller metaFiller;

    private static final Logger LOG = LoggerFactory.getLogger(SearchService.class);



    public boolean fillModelWithSearch(Model model, String wd, String uid, String imei, CallerParam caller,Locale locale) {
        boolean result = false;
        try {
            //明星数据
            List<SearchData.StarInfo> starDatas = searchStars(wd, 1, 1, caller.getCallerId(), uid, imei);
            model.addAttribute("starDatas", starDatas);

            //直播数据
            int i = 1;
            List<SearchLiveResult> liveDatas = new ArrayList<>();
            while (true) {
                List<SearchLiveResult> searchLiveResults = searchLives(wd, i++, 10, uid, imei, "1", caller);
                if (CollectionUtils.isEmpty(searchLiveResults)) {
                    break;
                }
                liveDatas.addAll(searchLiveResults);
            }
            model.addAttribute("liveDatas", liveDatas);

            //专题数据
            List<SearchData.SubjectData> subjectDatas = searchSubjects(wd, 1, 3, caller.getCallerId(), uid, imei);
            model.addAttribute("subjectDatas", highLightSubjects(subjectDatas));

            //专辑数据
            List<SearchData.AlbumData> albumDatas = searchAlbums(wd, 1, 3, caller.getCallerId(), uid, imei);
            model.addAttribute("albumDatas", highLightAlbums(albumDatas));

            //单视频数据
            List<SearchData.VideoInfo> videoDatas = searchVideos(wd, 1, 30, caller.getCallerId(), uid, imei);
            model.addAttribute("videoDatas", highLightVideos(videoDatas));

            model.addAttribute("videoCount", SearchApis.searchVideoCount(wd, 1, 10, caller.getCallerId(), uid, imei, "1", "", "", "", "", ""));

            model.addAttribute("albumCount", SearchApis.searchAlbumCount(wd, 1, 3, caller.getCallerId(), uid, imei, "1", ""));

            model.addAttribute("subjectCount", SearchApis.searchSubjectCount(wd, 1, 3, caller.getCallerId(), uid, imei, "1", ""));

            //防止XSS攻击
            wd = StringEscapeUtils.escapeHtml4(wd);
            model.addAttribute("wd", wd);
            metaFiller.fillSearchMeta(model, wd,locale);

            if (CollectionUtils.isEmpty(starDatas) && CollectionUtils.isEmpty(liveDatas) && CollectionUtils.isEmpty(subjectDatas) && CollectionUtils.isEmpty(albumDatas) && CollectionUtils.isEmpty(videoDatas)) {
                return false;
            }
            result = true;
        }catch(Exception e){
            LOG.error("search  fails, wd = {}", wd);

        }
        return result;
    }


    private List<SearchData.VideoInfo> highLightVideos(List<SearchData.VideoInfo> videoInfos){
        List<SearchData.VideoInfo> videoInfos1 = new ArrayList<>();
        if(CollectionUtils.isNotEmpty(videoInfos)){
            for(SearchData.VideoInfo videoInfo : videoInfos){
                String title = videoInfo.getName();
                videoInfo.setName(unicode2string(title));
                videoInfos1.add(videoInfo);
            }
        }
        return videoInfos1;
    }

    private List<SearchData.SubjectData> highLightSubjects(List<SearchData.SubjectData> subjectDatas){
        List<SearchData.SubjectData> subjectDatas1 = new ArrayList<>();
        if(CollectionUtils.isNotEmpty(subjectDatas)){
            for(SearchData.SubjectData subjectData : subjectDatas){
                String title = subjectData.getTitle();
                subjectData.setTitle(unicode2string(title));
                subjectDatas1.add(subjectData);
            }
        }
        return subjectDatas1;
    }

    private List<SearchData.AlbumData> highLightAlbums(List<SearchData.AlbumData> albumDatas){
        List<SearchData.AlbumData> albumDatas1 = new ArrayList<>();
        if(CollectionUtils.isNotEmpty(albumDatas)){
            for(SearchData.AlbumData albumData : albumDatas){
                String title = albumData.getName();
                albumData.setName(unicode2string(title));
                albumDatas1.add(albumData);
            }
        }
        return albumDatas1;
    }

    private String unicode2string(String s) {
        char[] chars = s.toCharArray();
        String str = "";
        for(int i = 0;i < chars.length;i++){
            if(chars[i] == '\u0001'){
                str += "<u>";
            }else if(chars[i] == '\u0002'){
                str += "</u>";
            }else{
                str += chars[i];
            }
        }
        return str;
    }

    private boolean isRepeatEpisode(HallEpisodeVo hallEpisodeVo,List<HallEpisodeVo> liveResult){

        boolean result = false;
        if(CollectionUtils.isNotEmpty(liveResult)){
            for(HallEpisodeVo hallEpisodeVo1 : liveResult){
                if(hallEpisodeVo.getId().equals(hallEpisodeVo1.getId())){
                    result =  true;
                    break;
                }
            }

        }
        return result;

    }

    private List<SearchLiveResult> searchLives(String word, int page, int count, String uid, String imei,String hl, CallerParam caller) {
        List<SearchLiveResult> searchLiveResults = new ArrayList<>();

        List<SearchData.LiveData> liveDatas = SearchApis.searchLiveData(word, page, count, caller.getCallerId(), uid, imei,hl,"");
        for (SearchData.LiveData liveData : liveDatas) {
            SearchLiveResult searchLiveResult = new SearchLiveResult();
            searchLiveResult.setChannel(liveData.getChannel());
            searchLiveResult.setGameName(liveData.getGameName());
            searchLiveResult.setImgUrl(liveData.getImgUrl());
            searchLiveResult.setSport(liveData.getSport());
            List<HallEpisodeVo> liveResult = new ArrayList<>();
            if (null != liveData && CollectionUtils.isNotEmpty(liveData.getGames())) {
                for (SearchData.LiveData.GameInfo gameInfo : liveData.getGames()) {
                    if (null != gameInfo) {
                        HallEpisodeVo hallEpisodeVo = searchResultAdapter.adapt(gameInfo, caller);
                        if (null != hallEpisodeVo  && !isRepeatEpisode(hallEpisodeVo,liveResult)) {
                            liveResult.add(hallEpisodeVo);
                        }
                    }
                }
            }
            searchLiveResult.setGames(liveResult);
            searchLiveResults.add(searchLiveResult);
        }

        return searchLiveResults;
    }

    private List<SearchData.StarInfo> searchStars(String word, int page, int count, long callerId, String uid, String imei) {
        List<SearchData.StarInfo> StarInfos = SearchApis.searchStarData(word, page, count, callerId, uid, imei,"1","");
        return StarInfos;
    }

    private List<SearchData.SubjectData> searchSubjects(String word, int page, int count, long callerId, String uid, String imei) {
        List<SearchData.SubjectData> subjectDatas = SearchApis.searchSubjectData(word, page, count, callerId, uid, imei,"1","");
        return subjectDatas;
    }

    private List<SearchData.AlbumData> searchAlbums(String word, int page, int count, long callerId, String uid, String imei) {
        List<SearchData.AlbumData> albumDatas = SearchApis.searchAlbumData(word, page, count, callerId, uid, imei,"1","");
        return albumDatas;
    }

    private List<SearchData.VideoInfo> searchVideos(String word, int page, int count, long callerId, String uid, String imei) {
        List<SearchData.VideoInfo> videoInfos = SearchApis.searchVideoData(word, page, count, callerId, uid, imei,"1","","","","","");
        return videoInfos;
    }


}
