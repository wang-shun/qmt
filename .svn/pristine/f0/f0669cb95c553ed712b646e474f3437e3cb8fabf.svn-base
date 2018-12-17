package com.lesports.qmt.play.controller;

import com.lesports.LeConstants;
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
import com.lesports.qmt.sbd.api.dto.TPlayer;
import com.lesports.qmt.sbd.api.dto.TTeam;
import com.lesports.qmt.sbd.client.SbdMatchApis;
import com.lesports.qmt.sbd.client.SbdPlayerApis;
import com.lesports.qmt.sbd.client.SbdTeamApis;
import com.lesports.qmt.web.service.*;
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
@RequestMapping("/data")
public class LePlayDataController {

    private final static Logger LOG = LoggerFactory.getLogger(LePlayDataController.class);

    @Resource
    private MatchService matchService;
    @Resource
    private VideoService videoService;
    @Resource
    private SearchService searchService;
    @Resource
    private NewsService newsService;
    @Resource
    private TeamService teamService;
    @Resource
    private PlayerService playerService;


    /**
     * 渲染搜索页，传入搜索关键字
     *
     * @param wd
     * @param uid
     * @return
     */
    @RequestMapping(value = "/s", method = RequestMethod.GET)
    public String renderSearchPage(Model model,Locale locale,
                                   @RequestParam(value = "wd", required = false, defaultValue = "") String wd,
                                   @RequestParam(value = "uid", required = false, defaultValue = "") String uid) {
        CallerParam callerParam = CallerUtils.getCallerByLocale(locale);
        boolean result = searchService.fillModelWithSearch(model, wd, uid, "", callerParam,locale);

        if (result) {
            return "data";
        } else {
            return "data";
        }
    }

    /**
     * 渲染比赛页,需要传入比赛id
     *
     * @param mid    matchId
     * @param status 状态
     * @return
     */
    @RequestMapping(value = "/match/{mid}", method = RequestMethod.GET)
    public String renderMatchPage(@PathVariable long mid,
                                  @RequestParam(value = "status", required = false, defaultValue = "-1") int status,
                                  Locale locale,
                                  HttpServletResponse response) {
        CallerParam callerParam = CallerUtils.getDefaultCaller();
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
        builder.append("forward:/data/episode/");
        builder.append(eid);
        builder.append("?status=");
        builder.append(status);

        return builder.toString();
    }

    /**
     * 渲染节目直播页面,需要传入对应节目的id
     *
     * @param liveId 直播流ID
     * @return
     */
    @RequestMapping(value = "/live/{liveId}", method = RequestMethod.GET)
    public String renderLiveEpisodePage(@PathVariable String liveId,
                                        Locale locale,
                                        HttpServletResponse response) {
        //如果直播id非10开头直接返回404状态
        if (StringUtils.isBlank(liveId) || !liveId.startsWith(LeConstants.SPORTS_LIVEID_PREFIX)) {
            LOG.error("this liveId is not sports liveId ! liveId = {}", liveId);
            response.setStatus(HttpStatus.NOT_FOUND.value());
            return null;
        }
        CallerParam callerParam = CallerUtils.getDefaultCaller();

        long eid = QmtSbcEpisodeApis.getEpisodeIdByLiveId(liveId, callerParam);
        if (eid <= 0) {
            LOG.error("this liveId has no episode ! liveId = {}", liveId);
            response.setStatus(HttpStatus.NOT_FOUND.value());
            return null;
        }

        return "forward:/data/episode/" + eid;
    }


    /**
     * m站比赛页跳转回pc比赛转向
     *
     * @param mid
     * @param status
     * @return
     */
    @RequestMapping(value = "/route/match/{mid}", method = RequestMethod.GET)
    public String redirectMatchPageForMSite(@PathVariable long mid,
                                            @RequestParam(value = "status", required = false, defaultValue = "-1") int status,
                                            Locale locale,
                                            HttpServletResponse response) {
        CallerParam callerParam = CallerUtils.getDefaultCaller();
        TMatch match = SbdMatchApis.getTMatchById(mid, callerParam);
        if (match == null) {
            LOG.error("this matchId has no match ! id = {}", mid);
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
        TComboEpisode episode = QmtSbcEpisodeApis.getTComboEpisodeById(eid, callerParam);
        if (episode == null) {
            LOG.error("fail to render episode page, episode eid : {}, caller : {} no exist.", eid, callerParam);
            response.setStatus(HttpStatus.NOT_FOUND.value());
            return null;
        }
        if ((CollectionUtils.isEmpty(episode.getStreams()) || episode.getStreams().get(0).getLiveId().equals("-1"))
                && CollectionUtils.isNotEmpty(episode.getTextlives())) {
            return "redirect:/match/" + mid + "/tlive.html";
        } else {
            return "redirect:/match/" + mid + ".html?status=" + status;
        }
    }


    /**
     * 渲染比赛页面,需要传入比赛或者对应节目的id
     *
     * @param eid
     * @param status
     * @param model
     * @return
     */
    @RequestMapping(value = "/episode/{eid}", method = RequestMethod.GET)
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
        CallerParam callerParam = CallerUtils.getDefaultCaller();
        TComboEpisode episode = QmtSbcEpisodeApis.getTComboEpisodeById(eid, callerParam);
        if (episode == null) {
            LOG.error("fail to render episode page, episode eid : {} no exist.", eid);
            response.setStatus(HttpStatus.NOT_FOUND.value());
            return null;
        }
        if (episode.getType() == EpisodeType.PROGRAM) {
            return renderProgramEpisode(model, episode, liveId, callerParam, locale);
        } else {
           return  renderMatchEpisode(model, episode, status, callerParam, locale);

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
                return "data";
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
    @RequestMapping("/video/{vid}")
    public String renderVideoPage(@PathVariable long vid, Model model,
                                  Locale locale,
                                  HttpServletResponse response) {
        CallerParam callerParam = CallerUtils.getDefaultCaller();
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
    @RequestMapping("/video/topic/s/{pkgid_vid}")
    public String renderTopicVideoPage(@PathVariable String pkgid_vid, Model model,
                                       Locale locale,
                                       HttpServletResponse response) {
        CallerParam callerParam = CallerUtils.getDefaultCaller();
        videoService.fillMode4TopicVideo(model, pkgid_vid, callerParam, locale);
        return "data";
    }

    /**
     * 渲染新闻详情页,需要传入对应的新闻id
     *
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("/news/{id}")
    public String renderNewsPage(@PathVariable long id, Model model,
                                 Locale locale,
                                 HttpServletResponse response) {
        CallerParam callerParam = CallerUtils.getDefaultCaller();
        TNews tNews = QmtSbcApis.getTNewsById(id, callerParam);
        if (tNews == null) {
            LOG.error("fail to renderNewsPage. news no exist.  id : {}, caller : {}", id, callerParam);
            response.setStatus(HttpStatus.NOT_FOUND.value());
            return null;
        }
        newsService.fillModelWithTNews(model, tNews, callerParam, locale);
        switch (tNews.getType()) {
            case VIDEO:
                return "data";
            case IMAGE_TEXT:
                return "data";
            case RICHTEXT:
                return "data";
            case IMAGE_ALBUM:
                return "data";
        }
        throw new IllegalArgumentException();
    }

    /**
     * 比赛渲染图文直播
     *
     * @param mid
     * @return
     */
    @RequestMapping("/match/{mid}/tlive")
    public String renderTextLivePage4Match(@PathVariable long mid, HttpServletResponse response) {
        CallerParam callerParam = CallerUtils.getDefaultCaller();
        TMatch match = SbdMatchApis.getTMatchById(mid, callerParam);
        if (match == null) {
            LOG.error("fail to renderTextLivePage4Match. match no exist. mid : {}, caller : {}", mid, callerParam);
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
        return "forward:/data/episode/" + eid + "/tlive";
    }


    /**
     * 图文直播ID渲染图文
     *
     * @param tliveId
     * @return
     */
    @RequestMapping(value = "/tlive/{tliveId}", method = RequestMethod.GET)
    public String renderTextLivePage(@PathVariable long tliveId, HttpServletResponse response) {
        CallerParam callerParam = CallerUtils.getDefaultCaller();
        long eid = QmtSbcEpisodeApis.getEpisodeIdByTextLiveId(tliveId, callerParam);
        if (eid <= 0) {
            LOG.error("fail to renderTextLivePage. no episode found for textlive.  tliveId : {}, caller : {}",
                    tliveId, callerParam);
            response.setStatus(HttpStatus.NOT_FOUND.value());
            return null;
        }
        return "forward:/data/episode/" + eid + "/tlive";
    }

    /**
     * 节目图文直播渲染
     *
     * @param eid
     * @param model
     * @param response
     * @return
     */
    @RequestMapping(value = "/episode/{eid}/tlive", method = RequestMethod.GET)
    public String renderTLivePage4Episode(@PathVariable long eid, Model model,
                                          Locale locale,
                                          HttpServletResponse response) {
        IdType idType = LeIdApis.checkIdTye(eid);
        if (idType == null || idType != IdType.EPISODE) {
            LOG.error("fail to renderTLivePage4Episode, illegal eid : {}", eid);
            response.setStatus(HttpStatus.NOT_FOUND.value());
            return null;
        }
        CallerParam callerParam = CallerUtils.getDefaultCaller();
        TComboEpisode episode = QmtSbcEpisodeApis.getTComboEpisodeById(eid, callerParam);
        if (episode == null) {
            LOG.error("fail to renderTLivePage4Episode, episode no exist. eid : {}, callerParam : {}", eid, callerParam);
            response.setStatus(HttpStatus.NOT_FOUND.value());
            return null;
        }
        if (CollectionUtils.isEmpty(episode.getTextlives())) {
            LOG.error("fail to renderTLivePage4Episode, episode : {} has no textlives", eid);
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return null;
        }

        if (episode.getType() == EpisodeType.MATCH) {
            TDetailMatch match = SbdMatchApis.getTDetailMatchById(episode.getMid(), callerParam);
            matchService.fillModel4TextLive(model, match, episode, callerParam, locale);
            return "data";
        }

        throw new IllegalStateException();
    }

    /**
     * 渲染球队页
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/team/{id}", method = RequestMethod.GET)
    public String renderTeamPage(@PathVariable long id,
                                 Model model,Locale locale, HttpServletResponse response) {
        CallerParam callerParam = CallerUtils.getDefaultCaller();
        TTeam team = SbdTeamApis.getTTeamById(id, callerParam);
        if (team == null) {
            LOG.error("fail to renderTeamPage. team no exist. id : {}, caller : {}", id, callerParam);
            response.setStatus(HttpStatus.NOT_FOUND.value());
            return null;
        }

        teamService.fillModelWithTeamNew(model, team, callerParam,locale);

        return "data";
    }

    /**
     * 渲染球员页
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/player/{id}", method = RequestMethod.GET)
    public String renderPlayerPage(@PathVariable long id,
                                   Model model, Locale locale, HttpServletResponse response) {
        CallerParam callerParam = CallerUtils.getDefaultCaller();
        TPlayer player = SbdPlayerApis.getTPlayerById(id, callerParam);
        if (player == null) {
            LOG.error("fail to renderPlayerPage. player no exists. id :  {}, caller : {}", id, callerParam);
            response.setStatus(HttpStatus.NOT_FOUND.value());
            return null;
        }

        playerService.fillModelWithPlayerNew(model, player, callerParam, locale);

        return "data";
    }

//      /*------------------------------------奥运会（Olympic）-----------------------------------------------*/
//
//    /**
//     * 奥运出场名单
//     *
//     * @param mid
//     * @param model
//     * @return
//     */
//    @RequestMapping(value = "2016/entrylist/{mid}", method = RequestMethod.GET)
//    public String renderOlympicMatchLineup(@PathVariable long mid,
//                                     Locale locale,
//                                     Model model,
//                                     @RequestParam(value = "liveId", required = false, defaultValue = "") String liveId,
//                                     HttpServletResponse response) {
//        if (renderOlympicMatch(mid, locale, model, response,true)) return null;
//        return "olympic";
//
//    }
//
//    private boolean renderOlympicMatch(long mid, Locale locale, Model model, HttpServletResponse response,boolean isLineup) {
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
//        matchService.fillModel4OlympicMatch(model, match, episode, callerParam, locale,isLineup);
//        return false;
//    }
//
//    /**
//     * 奥运成绩页
//     *
//     * @param mid
//     * @param model
//     * @return
//     */
//    @RequestMapping(value = "2016/results/{mid}", method = RequestMethod.GET)
//    public String renderOlympicMatchResult(@PathVariable long mid,
//                                     Locale locale,
//                                     Model model,
//                                     @RequestParam(value = "liveId", required = false, defaultValue = "") String liveId,
//                                     HttpServletResponse response) {
//        if (renderOlympicMatch(mid, locale, model, response,false)) return null;
//        return "olympic";
//
//    }

    /**
     * 渲染nba球队页
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/team/bk/{id}", method = RequestMethod.GET)
    public String renderNBATeamPage(@PathVariable long id,
                                 Model model,Locale locale, HttpServletResponse response) {
        CallerParam callerParam = CallerUtils.getDefaultCaller();
        TTeam team = SbdTeamApis.getTTeamById(id, callerParam);
        if (team == null) {
            LOG.error("fail to renderTeamPage. team no exist. id : {}, caller : {}", id, callerParam);
            response.setStatus(HttpStatus.NOT_FOUND.value());
            return null;
        }

        teamService.fillModelWithBkTeam(model, team, callerParam,locale);

        return "data";
    }

    /**
     * 渲染球员页
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/player/bk/{id}", method = RequestMethod.GET)
    public String renderNBAPlayerPage(@PathVariable long id,
                                   Model model, Locale locale, HttpServletResponse response) {
        CallerParam callerParam = CallerUtils.getDefaultCaller();
        TPlayer player = SbdPlayerApis.getTPlayerById(id, callerParam);
        if (player == null) {
            LOG.error("fail to renderPlayerPage. player no exists. id :  {}, caller : {}", id, callerParam);
            response.setStatus(HttpStatus.NOT_FOUND.value());
            return null;
        }

        playerService.fillModelWithBkPlayer(model, player, callerParam, locale);

        return "data";
    }


    /**
     * 渲染球队聚合页
     *
     * @return
     */
    @RequestMapping(value = "/teams", method = RequestMethod.GET)
    public String renderTeamListPage(Model model, Locale locale, HttpServletResponse response) {
        CallerParam callerParam = CallerUtils.getDefaultCaller();
        teamService.fillModelWithTeamList(model, callerParam,locale);
        return "data";
    }


    /**
     * 渲染球队聚合页
     *
     * @return
     */
    @RequestMapping(value = "/players", method = RequestMethod.GET)
    public String renderPlayerListPage(Model model, Locale locale, HttpServletResponse response) {
        CallerParam callerParam = CallerUtils.getDefaultCaller();
        playerService.fillModelWithPlayerList(model, callerParam, locale);
        return "data";
    }

    /**
     * 渲染突发事件专题列表页
     *
     * @param topicName
     * @param model
     * @return
     */
    @RequestMapping("/topic/h/{topicName}")
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
}