package com.lesports.qmt.op.web.api.core.service.impl;


import com.google.common.collect.Lists;
import com.lesports.api.common.CallerParam;
import com.lesports.api.common.PageParam;
import com.lesports.id.api.IdType;
import com.lesports.id.client.LeIdApis;
import com.lesports.qmt.op.web.api.core.creater.VideoVoCreater;
import com.lesports.qmt.op.web.api.core.service.VideoService;
import com.lesports.qmt.op.web.api.core.util.Constants;
import com.lesports.qmt.op.web.api.core.util.MPLayLinkUtil;
import com.lesports.qmt.op.web.api.core.vo.VideoVo;
import com.lesports.qmt.op.web.api.core.vo.mbaidu.*;
import com.lesports.qmt.sbc.api.common.VideoContentType;
import com.lesports.qmt.sbc.api.dto.TVideo;
import com.lesports.qmt.sbc.api.dto.TVideoInfo;
import com.lesports.qmt.sbc.api.service.GetRelatedVideosParam;
import com.lesports.qmt.sbc.client.QmtSbcApis;
import com.lesports.qmt.sbd.api.dto.TCompetition;
import com.lesports.qmt.sbd.api.dto.TTeam;
import com.lesports.qmt.sbd.client.SbdCompetitionApis;
import com.lesports.qmt.sbd.client.SbdTeamApis;
import com.lesports.utils.LeDateUtils;
import com.lesports.utils.LeStringUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.net.URLEncoder;
import java.text.MessageFormat;
import java.util.Collections;
import java.util.List;

/**
 * Created by lufei1 on 2015/11/20.
 */
@Service("videoService")
public class VideoServiceImpl implements VideoService {

    private static final Logger LOG = LoggerFactory.getLogger(VideoServiceImpl.class);

    private static final MessageFormat URL_VIDEO = new MessageFormat("http://sports.letv.com/video/{0}.html");

    private static final MessageFormat SEARCH_URL_VIDEO = new MessageFormat("http://m.letv.com/search?wd={0}&index=0&from=input");

    @Resource
    private VideoVoCreater videoVoCreater;

    @Override
    public List<VideoVo> getVideosByRelatedId(long relatedId, CallerParam caller, PageParam page) {

        GetRelatedVideosParam p = new GetRelatedVideosParam();
        p.setRelatedId(relatedId);
        List<Long> ids = QmtSbcApis.getVideoIdsByRelatedId(p, page, caller);
        List<TVideo> relatedVideos = QmtSbcApis.getTVideosByIds(ids,caller);

        if (CollectionUtils.isEmpty(relatedVideos)) {
            LOG.info(" this id not have relatition videos ! relatedId ={}", relatedId);

            return Collections.EMPTY_LIST;
        }
        List<VideoVo> results = Lists.newArrayList();
        for (TVideo tVideo : relatedVideos) {
            results.add(videoVoCreater.createVideoVo(tVideo));
        }
        return results;
    }

    @Override
    public List<VideoVo> getCslVideosByRelatedId(long relatedId, CallerParam caller, PageParam page) {

        GetRelatedVideosParam p = new GetRelatedVideosParam();
        p.setRelatedId(relatedId);
        List<Long> ids = QmtSbcApis.getVideoIdsByRelatedId(p, page, caller);
        List<TVideo> relatedVideos = QmtSbcApis.getTVideosByIds(ids,caller);

        if (CollectionUtils.isEmpty(relatedVideos)) {
            LOG.info(" this id not have relatition videos ! relatedId ={}", relatedId);

            return Collections.EMPTY_LIST;
        }
        List<VideoVo> results = Lists.newArrayList();
        for (TVideo tVideo : relatedVideos) {
            VideoVo videoVo = videoVoCreater.createVideoVo(tVideo);
            videoVo.setMPlayLink(MPLayLinkUtil.getVideoMOpCslPlayLink(tVideo.getId(), caller));
            videoVo.setPlayLink("http://sports.le.com/video/"+tVideo.getId()+".html?ch=zchao"+caller.getCallerId());
            results.add(videoVo);
        }
        return results;
    }

    @Override
    public List<VideoVo> getVideos( CallerParam caller, PageParam page) {
        List<Long> ids = QmtSbcApis.getVideoIdsByRelatedId(null, page, caller);
        List<TVideo> relatedVideos = QmtSbcApis.getTVideosByIds(ids, caller);
        if (CollectionUtils.isEmpty(relatedVideos)) {
            LOG.info(" this id not have videos !");
            return Collections.EMPTY_LIST;
        }
        List<VideoVo> results = Lists.newArrayList();
        for (TVideo tVideo : relatedVideos) {
            VideoVo vedio = new VideoVoCreater().createVideoVo(tVideo);
            if (tVideo.getPlatforms().contains(2)) {
                vedio.setOnline(false);
            } else {
                vedio.setOnline(true);

            }
            results.add(vedio);
        }
            return results;
        }

    @Override
    public ShortVideos getShortVideosByType(long type,CallerParam caller) {
        ShortVideos shortVideos = new ShortVideos();
        if (type == 1) {
            createShortVideos(Constants.BAIDU_COMPETITION_LIST, shortVideos,caller);
        } else if (type == 2) {
            createShortVideos(Constants.BAIDU_TEAM_LIST, shortVideos,caller);
        }
        return shortVideos;
    }

    private void createShortVideos(List<Long> relateds, ShortVideos shortVideos,CallerParam caller) {
        List<ShortVideoItem> shortVideoItems = Lists.newArrayList();
        for (Long relatedId : relateds) {
            shortVideoItems.add(buildShortVideoItem(relatedId,caller));
        }
        shortVideos.setItem(shortVideoItems);
    }


    private ShortVideoItem buildShortVideoItem(long relatedId,CallerParam caller) {
        PageParam page = new PageParam();
        page.setPage(1);
        page.setCount(10);
        IdType idType = LeIdApis.checkIdTye(relatedId);
        String key = "";
        String url = "";
        if (idType.equals(IdType.COMPETITION)) {
            TCompetition tCompetition = SbdCompetitionApis.getTCompetitionById(relatedId, caller);
            key = tCompetition.getAbbreviation();
//            url = Constants.SITE_MAP.get(relatedId);
        } else if (idType.equals(IdType.TEAM)) {
            TTeam tTeam = SbdTeamApis.getTTeamById(relatedId, caller);
            key = tTeam.getName();
        }
        try {
            url = SEARCH_URL_VIDEO.format(new Object[]{URLEncoder.encode(String.valueOf(key), "UTF-8")});
        } catch (Exception e) {
            LOG.error("error when encoding next title.", e);
        }
        List<TVideo> highLightsVideos = Lists.newArrayList();
        GetRelatedVideosParam p = new GetRelatedVideosParam();
        p.setRelatedId(relatedId);
        List<VideoContentType> typeList = Lists.newArrayList(VideoContentType.HIGHLIGHTS);
        p.setTypes(typeList);
        TVideoInfo tVideoInfo = QmtSbcApis.getTVideoInfoByRelatedIdAndType(p,page,caller);
        if (tVideoInfo != null && CollectionUtils.isNotEmpty(tVideoInfo.getVideos())) {
            highLightsVideos = tVideoInfo.getVideos();
        }
        if (CollectionUtils.isEmpty(highLightsVideos)) {
            p = new GetRelatedVideosParam();
            p.setRelatedId(relatedId);
            typeList = Lists.newArrayList(VideoContentType.MATCH_REPORT);
            p.setTypes(typeList);
            tVideoInfo = QmtSbcApis.getTVideoInfoByRelatedIdAndType(p, page, caller);
            if (tVideoInfo != null && CollectionUtils.isNotEmpty(tVideoInfo.getVideos())) {
                highLightsVideos = tVideoInfo.getVideos();
            }
        }

        List<TVideo> recordVideos = Lists.newArrayList();
        p = new GetRelatedVideosParam();
        p.setRelatedId(relatedId);
        typeList = Lists.newArrayList(VideoContentType.RECORD);
        p.setTypes(typeList);
        tVideoInfo = QmtSbcApis.getTVideoInfoByRelatedIdAndType(p,page,caller);
        if (tVideoInfo != null && CollectionUtils.isNotEmpty(tVideoInfo.getVideos())) {
            recordVideos = tVideoInfo.getVideos();
        }

        List<TVideo> otherVideos = Lists.newArrayList();
        p = new GetRelatedVideosParam();
        p.setRelatedId(relatedId);
        typeList = Lists.newArrayList(VideoContentType.OTHER);
        p.setTypes(typeList);
        tVideoInfo = QmtSbcApis.getTVideoInfoByRelatedIdAndType(p,page,caller);
        if (tVideoInfo != null && CollectionUtils.isNotEmpty(tVideoInfo.getVideos())) {
            otherVideos = tVideoInfo.getVideos();
        }


        ShortVideoItem shortVideoItem = new ShortVideoItem();
        TabSet tabElement = new TabSet();
        List<Tab> tabs = Lists.newArrayList();
        buildPlayTab("精彩集锦", highLightsVideos, tabs);
        buildPlayTab("高清录播", recordVideos, tabs);
        buildPlayTab("新闻花絮", otherVideos, tabs);
        tabElement.setTabs(tabs);
        tabElement.setTitle(key + "视频_乐视体育");
        tabElement.setUrl(url);
        tabElement.setSite(url);
        shortVideoItem.setKey(key);
        shortVideoItem.setDisplay(tabElement);
        return shortVideoItem;
    }

    private void buildPlayTab(String title, List<TVideo> tVideos, List<Tab> tabs) {
        Tab tab = new Tab();
        tab.setTitle(title);
        List<Play> plays = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(tVideos)) {
            for (TVideo tVideo : tVideos) {
                Play play = new Play();
                play.setText(tVideo.getName());
                play.setImg(tVideo.getImages().get(Constants.VIDEO_SIZE));
                String duration = LeDateUtils.getHumanizedDuration(tVideo.getDuration(), true);
                if (StringUtils.isNotBlank(duration) && !duration.contains(":")) {
                    duration = "00:" + duration;
                }
                play.setTime(duration);
                play.setUrl(URL_VIDEO.format(new Object[]{String.valueOf(tVideo.getId())}));
                plays.add(play);
            }
        }
        tab.setPlay(plays);
        tabs.add(tab);
    }

    private List<VideoContentType> getVideoContentTypes(String type) {
        List<Integer> types = LeStringUtils.commaString2IntegerList(type);
        List<VideoContentType> typeList = Lists.newArrayList();
        if(CollectionUtils.isNotEmpty(types)){
            for(Integer videoType : types){
                if(null != VideoContentType.findByValue(videoType)) {
                    typeList.add(VideoContentType.findByValue(videoType));
                }
            }
        }
        return typeList;
    }
}
