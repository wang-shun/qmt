package com.lesports.msite.controller;

import com.lesports.api.common.CallerParam;
import com.lesports.api.common.LiveShowStatus;
import com.lesports.id.api.IdType;
import com.lesports.id.client.LeIdApis;
import com.lesports.qmt.sbc.api.common.EpisodeType;
import com.lesports.qmt.sbc.api.common.VideoContentType;
import com.lesports.qmt.sbc.api.dto.TComboEpisode;
import com.lesports.qmt.sbc.api.dto.TNews;
import com.lesports.qmt.sbc.api.dto.TSimpleVideo;
import com.lesports.qmt.sbc.api.dto.TVideo;
import com.lesports.qmt.sbc.client.QmtSbcApis;
import com.lesports.qmt.sbc.client.QmtSbcEpisodeApis;
import com.lesports.qmt.sbd.api.dto.TDetailMatch;
import com.lesports.qmt.sbd.api.dto.TMatch;
import com.lesports.qmt.sbd.client.SbdMatchApis;
import com.lesports.qmt.web.helper.MetaFiller;
import com.lesports.qmt.web.service.MatchService;
import com.lesports.qmt.web.service.NewsService;
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
public class MSitePlayDataController {

    private final static Logger LOG = LoggerFactory.getLogger(MSitePlayDataController.class);

    @Resource
    private MatchService matchService;

    @Resource
    private VideoService videoService;

    @Resource
    private NewsService newsService;

    @Resource
    private MetaFiller metaFiller;
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

        CallerParam callerParam = CallerUtils.getCallerByLocale(locale);
        TMatch match = SbdMatchApis.getTMatchById(mid, callerParam);
        long eid = match.getEid();
        if(eid <= 0) {
            eid = CallerUtils.getIdofCountryAndLanguage(match.getEids(), callerParam.getCountry(), callerParam.getLanguage());
            if (eid <= 0) {
                LOG.error("episode no exists! mid : {}, caller : {}", mid, callerParam);
                response.setStatus(HttpStatus.NOT_FOUND.value());
                return null;
            }
        }
        return "forward:/data/episode/" +eid + "?status=" + status;
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

        CallerParam callerParam = CallerUtils.getCallerByLocale(locale);
        TMatch match = SbdMatchApis.getTMatchById(mid, callerParam);
        long eid = match.getEid();
        if(eid <= 0) {
            eid = CallerUtils.getIdofCountryAndLanguage(match.getEids(), callerParam.getCountry(), callerParam.getLanguage());
            if (eid <= 0) {
                LOG.error("episode no exists! mid : {}, caller : {}", mid, callerParam);
                response.setStatus(HttpStatus.NOT_FOUND.value());
                return null;
            }
        }
        return "forward:/data/episode/" + eid + "?status=" + status;
    }

    /**
     * 渲染比赛页,需要传入比赛id
     *
     * @param tliveId 图文直播id
     * @param status  状态
     * @return
     */
    @RequestMapping(value = "/data/tlive/{tliveId}", method = RequestMethod.GET)
    public String renderImageTextPage(@PathVariable long tliveId,
                                      @RequestParam(value = "status", required = false, defaultValue = "-1") int status,
                                      Locale locale) {
        CallerParam callerParam = CallerUtils.getCallerByLocale(locale);
        long eid = QmtSbcEpisodeApis.getEpisodeIdByTextLiveId(tliveId, callerParam);
        return "forward:/data/episode/" + eid + "?status=" + status;
    }


    /**
     * 渲染比赛页面,需要传入比赛或者对应节目的id
     *
     * @param eid
     * @param status
     * @param model
     * @return
     */
    @RequestMapping(value = "/data/episode/{eid}", method = RequestMethod.GET)
    public String renderEpisodePage(@PathVariable long eid,
                                    @RequestParam(value = "status", required = false, defaultValue = "-1") int status,
                                    @RequestParam(value = "liveId", required = false, defaultValue = "") String liveId,
                                    Locale locale, Model model) {
        IdType idType = LeIdApis.checkIdTye(eid);
        if (idType == null || idType != IdType.EPISODE) {
            LOG.error("fail to render episode page, illegal eid : {}", eid);
            throw new IllegalArgumentException("fail to render episode page, illegal eid : " + eid);
        }
        CallerParam callerParam = CallerUtils.getCallerByLocale(locale);
        TComboEpisode episode = QmtSbcEpisodeApis.getTComboEpisodeById(eid, callerParam);
        if (episode == null) {
            LOG.error("fail to render episode page, episode eid : {} no exist.", eid);
            throw new IllegalArgumentException("fail to render episode page, episode eid : " + eid + " no exist.");
        }
        if (episode.getType() == EpisodeType.PROGRAM) {
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
        } else {
            //比赛
            TDetailMatch match = SbdMatchApis.getTDetailMatchById(episode.getMid(), callerParam);
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
                    matchService.fillModel4NotStartMatch(model, match, episode, callerParam, locale);
                    return "data";
                case LIVING:
                    matchService.fillModel4Matching(model, match, episode, callerParam, locale);
                    return "data";
                case LIVE_END:
                    matchService.fillModel4EndMatch(model, match, episode, callerParam, locale);
                    return "data-after";
            }
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
    public String renderVideoPage(@PathVariable long vid, Model model, Locale locale, HttpServletResponse response) {
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
    public String renderTopicVideoPage(@PathVariable String pkgid_vid, Model model, Locale locale, HttpServletResponse response) {
        CallerParam callerParam = CallerUtils.getCallerByLocale(locale);
        videoService.fillMode4TopicVideo(model, pkgid_vid, callerParam, locale);
        return "data";
    }

	/**
	 * 渲染突发事件专题列表页
	 *
	 * @param topicName
	 * @param model
	 * @return
	 */
	@RequestMapping("/data/topic/h/{topicName}")
	public String renderHtopicListPage(@PathVariable String topicName, Model model, HttpServletResponse response,
									   Locale locale) {
		CallerParam callerParam = CallerUtils.getCallerByLocale(locale);
		if (StringUtils.isBlank(topicName)) {
			LOG.error("render hot topic list error ! topicName can not be null ! topicName = {} ", topicName);
			return null;
		}
		videoService.fillHtopicListModel(model, topicName, response, callerParam, locale);
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
                                        Locale locale) {
        CallerParam callerParam = CallerUtils.getCallerByLocale(locale);

        long eid = QmtSbcEpisodeApis.getEpisodeIdByLiveId(liveId, callerParam);
        if (eid == 0) {
            LOG.error("this liveId has no episode ! liveId = {}", liveId);
            throw new IllegalArgumentException("fail to render live page, illegal liveId: " + liveId);
        }
        return "forward:/data/episode/" + eid + "?liveId=" + liveId + "&uid=" + uid;
    }

    /**
     * 渲染新闻详情页,需要传入对应的新闻id
     *
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("/data/news/{id}")
    public String renderNewsPage(@PathVariable long id, Model model, Locale locale, HttpServletResponse response) {
        CallerParam callerParam = CallerUtils.getCallerByLocale(locale);
        TNews tNews = QmtSbcApis.getTNewsById(id, callerParam);
        if (tNews == null) {
            LOG.error("this news is null ! id = {}", id);
            throw new IllegalArgumentException("fail to render news page, illegal id: " + id);
        } else {
            newsService.fillModelWithTNews(model, tNews, callerParam, locale);
            switch (tNews.getType()) {
                case VIDEO:
                    model.addAttribute("tNews", tNews);
                case IMAGE_TEXT:
                    model.addAttribute("tNews", tNews);
                case RICHTEXT:
                    model.addAttribute("tNews", tNews);
                case IMAGE_ALBUM:
                    model.addAttribute("tNews", tNews);
            }
        }
        return "data";
    }

    /**
     * 渲染中超首页
     *
     * @return
     */
    @RequestMapping("/data/csl/schedule")
    public String renderCslSchedule(Model model, HttpServletResponse response,Locale locale) {
        metaFiller.fillCslScheduleMeta(model,locale);
        return "data";
    }

    /**
     * 渲染中超榜单
     *
     * @return
     */
    @RequestMapping("/data/csl/toplist")
    public String renderCslToplist(Model model, HttpServletResponse response,Locale locale) {
        metaFiller.fillCslToplistMeta(model,locale);
        return "data";
    }

//    /**
//     * 奥运出场名单
//     *
//     * @param mid
//     * @param model
//     * @return
//     */
//    @RequestMapping(value = "data/2016/events/{mid}", method = RequestMethod.GET)
//    public String renderOlympicMatchLineup(@PathVariable long mid,
//                                           Locale locale,
//                                           Model model,
//                                           @RequestParam(value = "liveId", required = false, defaultValue = "") String liveId,
//                                           HttpServletResponse response) {
//        if (renderOlympicMatch(mid, locale, model, response)) return null;
//        return "olympic";
//
//    }

//    private boolean renderOlympicMatch(long mid, Locale locale, Model model, HttpServletResponse response) {
//        LOG.info("render olympic EpisodePage, mid : {}", mid);
//        CallerParam callerParam = CallerUtils.getDefaultCaller();
//        TDetailMatch match = SbdMatchApis.getTDetailMatchById(mid, callerParam);
//        if (match == null) {
//            LOG.error("match no exists! mid : {}, caller : {}", mid, callerParam);
//            response.setStatus(HttpStatus.NOT_FOUND.value());
//            return true;
//        }
//
//
//        long eid = match.getEid();
//        if(eid <= 0) {
//            eid = CallerUtils.getIdofCountryAndLanguage(match.getEids(), callerParam.getCountry(), callerParam.getLanguage());
//            if (eid <= 0) {
//                LOG.error("episode no exists! mid : {}, caller : {}", mid, callerParam);
//                response.setStatus(HttpStatus.NOT_FOUND.value());
//                return true;
//            }
//        }
//        IdType idType = LeIdApis.checkIdTye(eid);
//        if (idType == null || idType != IdType.EPISODE) {
//            LOG.error("fail to render episode page, illegal eid : {}", eid);
//            response.setStatus(HttpStatus.BAD_REQUEST.value());
//            return true;
//        }
//        TComboEpisode episode = SopsApis.getTComboEpisodeById(eid, callerParam);
//        if (episode == null) {
//            LOG.error("fail to render episode page, episode eid : {} no exist.", eid);
//            response.setStatus(HttpStatus.NOT_FOUND.value());
//            return true;
//        }
//        matchService.fillModel4OlympicMatchOfMsite(model, match, episode, callerParam, locale);
//        return false;
//    }

}