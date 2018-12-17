package com.lesports.msite.controller;

import com.lesports.LeConstants;
import com.lesports.api.common.CallerParam;
import com.lesports.api.common.LiveShowStatus;
import com.lesports.id.api.IdType;
import com.lesports.id.client.LeIdApis;
import com.lesports.qmt.sbc.api.common.EpisodeType;
import com.lesports.qmt.sbc.api.common.VideoContentType;
import com.lesports.qmt.sbc.api.dto.TComboEpisode;
import com.lesports.qmt.sbc.api.dto.TSimpleVideo;
import com.lesports.qmt.sbc.api.dto.TVideo;
import com.lesports.qmt.sbc.client.QmtSbcApis;
import com.lesports.qmt.sbc.client.QmtSbcEpisodeApis;
import com.lesports.qmt.sbd.api.dto.TDetailMatch;
import com.lesports.qmt.sbd.api.dto.TMatch;
import com.lesports.qmt.sbd.client.SbdMatchApis;
import com.lesports.qmt.web.service.MatchService;
import com.lesports.qmt.web.service.VideoService;
import com.lesports.utils.CallerUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;


/**
 * User: ellios
 * Time: 15-6-19 : 上午9:52
 */
@Controller
@RequestMapping("/")
public class OpMSitePlayDataController {

    private final static Logger LOG = LoggerFactory.getLogger(OpMSitePlayController.class);

    @Resource
    private MatchService matchService;

    @Resource
    private VideoService videoService;

    /**
     * 渲染比赛图文直播页,需要传入比赛id
     *
     * @param mid    matchId
     * @param status 状态
     * @return
     */
    @RequestMapping(value = "/data/match/{mid}", method = RequestMethod.GET)
    public String renderMatchPage(@PathVariable long mid,
                                  @RequestParam(value = "status", required = false, defaultValue = "-1") int status,
                                  Locale locale,
                                  HttpServletResponse response) {

        return getForwardUrlByMid(mid, status,locale, response);
    }

    private String getForwardUrlByMid(long mid, int status,Locale locale, HttpServletResponse response) {
        CallerParam callerParam = CallerUtils.getCallerByLocale(locale);
        TMatch match = SbdMatchApis.getTMatchById(mid, callerParam);
        if (match == null) {
            LOG.error("match no exists! mid : {}, caller : {}", mid, callerParam);
            response.setStatus(HttpStatus.NOT_FOUND.value());
            return null;
        }
        long eid = match.getEid();
        if(eid <= 0) {
            eid = CallerUtils.getIdofCountryAndLanguage(match.getEids(), callerParam.getCountry(), callerParam.getLanguage());
            if (eid <= 0) {
                LOG.error("episode no exists! mid : {}, caller : {}", mid, callerParam);
                response.setStatus(HttpStatus.NOT_FOUND.value());
                return null;
            }
        }
        StringBuilder builder = new StringBuilder();
        builder.append("forward:/data/op/episode/");
        builder.append(eid);
        builder.append("?status=");
        builder.append(status);

        return builder.toString();
    }

    /**
     * 渲染比赛图文直播页,需要传入比赛id
     *
     * @param mid    matchId
     * @param status 状态
     * @return
     */
    @RequestMapping(value = "/data/match/{mid}/tlive", method = RequestMethod.GET)
    public String renderMatchImageTextPage(@PathVariable long mid,
                                           @RequestParam(value = "status", required = false, defaultValue = "-1") int status,
                                           Locale locale,
                                           HttpServletResponse response) {

        return getForwardUrlByMid(mid, status,locale, response);
    }

    /**
     * 渲染比赛页,需要传入比赛id
     *
     * @param liveId 图文直播id
     * @param status 状态
     * @return
     */
    @RequestMapping(value = "/data/tlive/{liveId}", method = RequestMethod.GET)
    public String renderImageTextPage(@PathVariable String liveId,
                                      @RequestParam(value = "status", required = false, defaultValue = "-1") int status,
                                      Locale locale,
                                      HttpServletResponse response) {
        //如果直播id非10开头直接返回404状态
        if (StringUtils.isBlank(liveId) || !liveId.startsWith(LeConstants.SPORTS_LIVEID_PREFIX)) {
            LOG.error("this liveId is not sports liveId ! liveId = {}", liveId);
            response.setStatus(HttpStatus.NOT_FOUND.value());
            return null;
        }
        CallerParam callerParam = CallerUtils.getCallerByLocale(locale);

        long eid = QmtSbcEpisodeApis.getEpisodeIdByLiveId(liveId, callerParam);
        if (eid <= 0) {
            LOG.error("this liveId has no episode ! liveId = {}", liveId);
            response.setStatus(HttpStatus.NOT_FOUND.value());
            return null;
        }
        return "forward:/data/op/episode/" + eid + "?status=" + status;
    }


    /**
     * 渲染比赛页面,需要传入比赛或者对应节目的id
     *
     * @param eid
     * @param status
     * @param model
     * @return
     */
    @RequestMapping(value = "/data/op/episode/{eid}", method = RequestMethod.GET)
    public String renderEpisodePage(@PathVariable long eid,
                                    @RequestParam(value = "status", required = false, defaultValue = "-1") int status,
                                    @RequestParam(value = "liveId", required = false, defaultValue = "") String liveId,
                                    Locale locale,
                                    Model model,
                                    HttpServletResponse response) {
        IdType idType = LeIdApis.checkIdTye(eid);
        if (idType == null || idType != IdType.EPISODE) {
            LOG.error("fail to render episode page, illegal eid : {}", eid);
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            return null;
        }
        CallerParam callerParam = CallerUtils.getCallerByLocale(locale);
        TComboEpisode episode = QmtSbcEpisodeApis.getTComboEpisodeById(eid, callerParam);
        if (episode == null) {
            LOG.error("fail to render episode page, episode eid : {} no exist.", eid);
            response.setStatus(HttpStatus.NOT_FOUND.value());
            return null;
        }
        if (episode.getType() == EpisodeType.PROGRAM) {
            return renderProgramEpisode(model, episode, liveId, callerParam, locale);
        } else {
            return renderMatchEpisode(model, episode, status, callerParam, locale);

        }
    }

    /**
     * 渲染program类的episode
     *
     * @param model
     * @param episode
     * @param liveId
     * @param callerParam
     * @return
     */
    private String renderProgramEpisode(Model model, TComboEpisode episode, String liveId, CallerParam callerParam,
                                        Locale locale) {
        //节目,增加未结束
        switch (episode.getStatus()) {
            case LIVE_NOT_START:
                matchService.fillModel4NonMatch(model, episode, liveId, callerParam, locale);
                return "data";
            case LIVE_END:
                if (CollectionUtils.isNotEmpty(episode.getVideos())) {
                    for (TSimpleVideo video : episode.getVideos()) {
                        if (video.getType() == VideoContentType.RECORD) {
                            return "forward:/data/video/" + video.getVid();
                        }
                    }
                    return "forward:/data/video/" + episode.getVideos().get(0).getVid();
                }
            case NO_LIVE:
                if (CollectionUtils.isNotEmpty(episode.getVideos())) {
                    for (TSimpleVideo video : episode.getVideos()) {
                        if (video.getType() == VideoContentType.RECORD) {
                            return "forward:/data/video/" + video.getVid();
                        }
                    }
                    return "forward:/data/video/" + episode.getVideos().get(0).getVid();
                }
            case LIVING:
                matchService.fillModel4NonMatch(model, episode, liveId, callerParam, locale);
                return "data";
        }
        throw new IllegalArgumentException();
    }

    /**
     * 渲染比赛类的episode
     *
     * @param model
     * @param episode
     * @param callerParam
     * @return
     */
    private String renderMatchEpisode(Model model, TComboEpisode episode, int status, CallerParam callerParam,
                                      Locale locale) {
        TDetailMatch match = SbdMatchApis.getTDetailMatchById(episode.getMid(), callerParam);
        if (match == null) {
            LOG.error("fail to render match episode page, match : {} no exists. eid : {}", episode.getMid(), episode.getId());
            throw new IllegalStateException();
        }
        LiveShowStatus mstatus = null;
        LiveShowStatus ms = LiveShowStatus.findByValue(status);
        if (status > -1 && ms != null) {
            //如果带入参数的话则以参数的状态返回对应的模板
            mstatus = ms;
        } else {
            //如果不带入参数或者传入status不为正确值的话则已以节目的真实状态返回模板
            mstatus = episode.getStatus();
        }
        model.addAttribute("status", mstatus);

        switch (mstatus) {
            case LIVE_NOT_START:
                //未开始
                matchService.fillModel4NotStartMatch(model, match, episode, callerParam, locale);
                return "data";
            case LIVING:
                //赛中
                matchService.fillModel4Matching(model, match, episode, callerParam, locale);
                return "data";
            case LIVE_END:
                //已结束
                matchService.fillModel4EndMatch(model, match, episode, callerParam, locale);
                return "data-after";
        }
        throw new IllegalArgumentException();
    }

    /**
     * 渲染节目点播页面,需要传入对应的视频id
     *
     * @param vid
     * @param model
     * @return
     */
    @RequestMapping("/data/video/{vid}")
    public String renderVideoPage(@PathVariable long vid, Model model, HttpServletResponse response,Locale locale) {
        CallerParam callerParam = CallerUtils.getCallerByLocale(locale);
        TVideo video = QmtSbcApis.getTVideoById(vid, callerParam);
        if (video == null) {
            LOG.error("fail to renderVideoPage. video no exist. vid : {}, caller : {}", vid, callerParam);
            response.setStatus(HttpStatus.NOT_FOUND.value());
            return null;
        }
        videoService.fillModel4Video(model, video, callerParam, locale);
        return "data";
    }

    /**
     * 渲染节目点播页面,需要传入对应的视频id
     *
     * @param pkgid_vid
     * @param model
     * @return
     */
    @RequestMapping("/data/video/topic/s/{pkgid_vid}")
    public String renderTopicVideoPage(@PathVariable String pkgid_vid, Model model, HttpServletResponse response,Locale locale) {
        CallerParam callerParam = CallerUtils.getCallerByLocale(locale);
        videoService.fillMode4TopicVideo(model, pkgid_vid, callerParam, locale);
        return "data";
    }

    /**
     * 渲染节目直播页面,需要传入对应节目的id
     *
     * @param liveId 直播流ID
     * @param uid    用户id
     * @return
     */
    @RequestMapping(value = "/data/live/{liveId}", method = RequestMethod.GET)
    public String renderLiveEpisodePage(@PathVariable String liveId,
                                        @RequestParam(value = "uid", required = false, defaultValue = "") String uid,
                                        Locale locale,
                                        HttpServletResponse response) {
        //如果直播id非10开头直接返回404状态
        if (StringUtils.isBlank(liveId) || !liveId.startsWith(LeConstants.SPORTS_LIVEID_PREFIX)) {
            LOG.error("this liveId is not sports liveId ! liveId = {}", liveId);
            response.setStatus(HttpStatus.NOT_FOUND.value());
            return null;
        }
        CallerParam callerParam = CallerUtils.getCallerByLocale(locale);

        long eid = QmtSbcEpisodeApis.getEpisodeIdByLiveId(liveId, callerParam);
        if (eid <= 0) {
            LOG.error("this liveId has no episode ! liveId = {}", liveId);
            response.setStatus(HttpStatus.NOT_FOUND.value());
            return null;
        }
        return "forward:/data/episode/" + eid + "?liveId=" + liveId + "&uid=" + uid;
    }
}