package com.lesports.qmt.play.controller;

import com.google.common.base.Stopwatch;
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
import com.lesports.qmt.web.helper.MetaFiller;
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
import java.util.concurrent.TimeUnit;

/**
 * 比赛页
 */
@Controller
@RequestMapping("/")
public class LePlayController {

    private final static Logger LOG = LoggerFactory.getLogger(LePlayController.class);

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

    @Resource
    private MetaFiller metaFiller;


    /**
     * 渲染搜索页，传入搜索关键字
     *
     * @param wd
     * @param uid
     * @return
     */
    @RequestMapping(value = "/s", method = RequestMethod.GET)
    public String renderSearchPage(Model model, Locale locale,
                                   @RequestParam(value = "wd", required = false, defaultValue = "") String wd,
                                   @RequestParam(value = "uid", required = false, defaultValue = "") String uid) {
        CallerParam callerParam = CallerUtils.getDefaultCaller();
        boolean result = searchService.fillModelWithSearch(model, wd, uid, "", callerParam, locale);

        if (result) {
            return "search/page/search/search";
        } else {
            return "search/page/none/none";
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
        CallerParam callerParam = CallerUtils.getCallerByLocale(locale);
        TMatch match = SbdMatchApis.getTMatchById(mid, callerParam);
        if (match == null) {
            LOG.error("match no exists! mid : {}, caller : {}", mid, callerParam);
            response.setStatus(HttpStatus.NOT_FOUND.value());
            return null;
        }

        long eid = match.getEid();
        LOG.info("/match eid: {},  match: {} ",eid,match);
        if (eid <= 0) {
            eid = CallerUtils.getIdofCountryAndLanguage(match.getEids(), callerParam.getCountry(), callerParam.getLanguage());
            if (eid <= 0) {
                LOG.error("episode no exists! mid : {}, caller : {}", mid, callerParam);
                response.setStatus(HttpStatus.NOT_FOUND.value());
                return null;
            }
        }

        StringBuilder builder = new StringBuilder();
        builder.append("forward:/episode/");
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
            LOG.error("this liveId has no episode ! liveId = {} ,caller: {} ", liveId, callerParam);
            response.setStatus(HttpStatus.NOT_FOUND.value());
            return null;
        }

        return "forward:/episode/" + eid;
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
        if (eid <= 0) {
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
        Stopwatch stopwatch = Stopwatch.createStarted();
        LOG.info("renderEpisodePage, eid : {}", eid);
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
        //根据后台填写的地址做302跳转
        if (episode.isIsUrlRedirect()) {
            return "redirect:" + episode.getPcRedirectUrl();
        }
        String result;
        if (episode.getType() == EpisodeType.PROGRAM) {
            result = renderProgramEpisode(model, episode, liveId, callerParam, locale);
        } else {
            result = renderMatchEpisode(model, episode, status, callerParam, locale);
        }
        stopwatch.stop();
        long elasped = stopwatch.elapsed(TimeUnit.MILLISECONDS);
        LOG.info("[cms] [play-web] [renderEpisodePage] [elapsed:{}]", elasped);
        return result;
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
                return "sport-match-desktop/page/live/live";
            case LIVE_END:
                if (CollectionUtils.isNotEmpty(episode.getVideos())) {
                    for (TSimpleVideo video : episode.getVideos()) {
                        if (video.getType() == VideoContentType.RECORD) {
                            return "forward:/video/" + video.getVid();
                        }
                    }
                    return "forward:/video/" + episode.getVideos().get(0).getVid();
                }
            case NO_LIVE:
                if (CollectionUtils.isNotEmpty(episode.getVideos())) {
                    for (TSimpleVideo video : episode.getVideos()) {
                        if (video.getType() == VideoContentType.RECORD) {
                            return "forward:/video/" + video.getVid();
                        }
                    }
                    return "forward:/video/" + episode.getVideos().get(0).getVid();
                }
            case LIVING:
                matchService.fillModel4NonMatch(model, episode, liveId, callerParam, locale);
                return "sport-match-desktop/page/live/live";
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
                return "sport-match-desktop/page/beforegame/beforegame";
            case LIVING:
                //赛中
                matchService.fillModel4Matching(model, match, episode, callerParam, locale);
                return "sport-match-desktop/page/ingame/ingame";
            case LIVE_END:
                //已结束
                matchService.fillModel4EndMatch(model, match, episode, callerParam, locale);
                return "sport-match-desktop/page/aftergame/aftergame";
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
        CallerParam callerParam = CallerUtils.getCallerByLocale(locale);
        TVideo video = QmtSbcApis.getTVideoById(vid, callerParam);
        if (video == null) {
            LOG.error("fail to renderVideoPage. video no exist. vid : {}, caller : {}", vid, callerParam);
            response.setStatus(HttpStatus.NOT_FOUND.value());
            return null;
        }
        videoService.fillModel4Video(model, video, callerParam, locale);
        return "sport-match-desktop/page/video/video";
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
        return "sport-match-desktop/page/video/video";
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
        return "sport-channel-desktop/page/emergency/emergency";
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
        CallerParam callerParam = CallerUtils.getCallerByLocale(locale);
        TNews tNews = QmtSbcApis.getTNewsById(id, callerParam);
        if (tNews == null) {
            LOG.error("fail to renderNewsPage. news no exist.  id : {}, caller : {}", id, callerParam);
            response.setStatus(HttpStatus.NOT_FOUND.value());
            return null;
        }
        newsService.fillModelWithTNews(model, tNews, callerParam, locale);
        switch (tNews.getType()) {
            case VIDEO:
				//香港-繁体 pc动态页
				if (callerParam.getCallerId() == 3001l) {
					return "hk-sport-news-desktop/page/video/video";
				}
                return "sport-news-desktop/page/videonews/videonews";
            case IMAGE_TEXT:
				//香港-繁体 pc动态页
				if (callerParam.getCallerId() == 3001l) {
					return "hk-sport-news-desktop/page/graphic/graphic";
				}
                return "sport-news-desktop/page/graphic/graphic";
            case RICHTEXT:
				//香港-繁体 pc动态页
				if (callerParam.getCallerId() == 3001l) {
					return "hk-sport-news-desktop/page/richtext/richtext";
				}
                return "sport-news-desktop/page/richtext/richtext";
            case IMAGE_ALBUM:
                return "sport-news-desktop/page/atlas/atlas";
        }
        throw new IllegalArgumentException();
    }


	//-------------- 新闻列表页测试用 ------ start ---------

	/**
	 * 新闻列表页 - 测试用
	 *
	 * @return
	 */
	@RequestMapping("/news/list")
	public String renderNewsTestList(Model model, Locale locale, HttpServletResponse response) {
		return "hk-sport-news-desktop/page/index/index";
	}

	//-------------- 新闻列表页测试用 ------ end -----------

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
        if (eid <= 0) {
            eid = CallerUtils.getIdofCountryAndLanguage(match.getEids(), callerParam.getCountry(), callerParam.getLanguage());
            if (eid <= 0) {
                LOG.error("episode no exists! mid : {}, caller : {}", mid, callerParam);
                response.setStatus(HttpStatus.NOT_FOUND.value());
                return null;
            }
        }
        return "forward:/episode/" + eid + "/tlive";
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
        return "forward:/episode/" + eid + "/tlive";
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
        //根据后台填写的地址做302跳转
        if (episode.isIsUrlRedirect()) {
            return "redirect:" + episode.getPcRedirectUrl();
        }
        if (CollectionUtils.isEmpty(episode.getTextlives())) {
            LOG.error("fail to renderTLivePage4Episode, episode : {} has no textlives", eid);
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return null;
        }

        if (episode.getType() == EpisodeType.MATCH) {
            TDetailMatch match = SbdMatchApis.getTDetailMatchById(episode.getMid(), callerParam);
            matchService.fillModel4TextLive(model, match, episode, callerParam, locale);
            return "sport-match-desktop/page/graphic-live/graphic-live";
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
    public String renderTeamPage(@PathVariable long id, @RequestParam(value = "cid", required = false, defaultValue = "47001") long cid,
                                 Model model, Locale locale, HttpServletResponse response) {
        CallerParam callerParam = CallerUtils.getDefaultCaller();
        TTeam team = SbdTeamApis.getTTeamById(id, callerParam);
        if (team == null) {
            LOG.error("fail to renderTeamPage. team no exist. id : {}, caller : {}", id, callerParam);
            response.setStatus(HttpStatus.NOT_FOUND.value());
            return null;
        }

        teamService.fillModelWithTeamNew(model, team, callerParam, locale);

        return "sport-channel-desktop/page/teamdetail/teamdetail";
    }


    /**
     * 渲染球员页
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/player/{id}", method = RequestMethod.GET)
    public String renderPlayerPage(@PathVariable long id, @RequestParam(value = "cid", required = false, defaultValue = "47001") long cid,
                                   Model model, Locale locale, HttpServletResponse response) {
        CallerParam callerParam = CallerUtils.getDefaultCaller();
        TPlayer player = SbdPlayerApis.getTPlayerById(id, callerParam);
        if (player == null) {
            LOG.error("fail to renderPlayerPage. player no exists. id :  {}, caller : {}", id, callerParam);
            response.setStatus(HttpStatus.NOT_FOUND.value());
            return null;
        }

        playerService.fillModelWithPlayerNew(model, player, callerParam, locale);

        return "sport-channel-desktop/page/player/player";
    }

    //-----------------------------欧洲杯测试用-------------------------------------

    /**
     * 欧洲杯首页
     *
     * @return
     */
    @RequestMapping("/euro2016")
    public String renderEurocupIndex(Model model, HttpServletResponse response) {
        return "sport-eurocup-desktop/page/eurocup-home/eurocup-home";
    }

    /**
     * 欧洲杯新闻
     *
     * @return
     */
    @RequestMapping("euro2016/news")
    public String renderEurocupNews(Model model, Locale locale, HttpServletResponse response) {
        metaFiller.fillEuroNewsMeta(model, locale);
        return "sport-eurocup-desktop/page/eurocup-scroll/eurocup-scroll";
    }

    /**
     * 渲染比赛页面,需要传入比赛或者对应节目的id
     *
     * @param mid
     * @param status
     * @param model
     * @return
     */
    @RequestMapping(value = "euro2016/match/{mid}", method = RequestMethod.GET)
    public String renderEurocupMatch(@PathVariable long mid,
                                     @RequestParam(value = "status", required = false, defaultValue = "-1") int status,
                                     Locale locale,
                                     Model model,
                                     @RequestParam(value = "liveId", required = false, defaultValue = "") String liveId,
                                     @RequestParam(value = "uid", required = false, defaultValue = "") String uid,
                                     HttpServletResponse response) {
        LOG.info("renderEpisodePage, mid : {}", mid);
        CallerParam callerParam = CallerUtils.getDefaultCaller();
        TDetailMatch match = SbdMatchApis.getTDetailMatchById(mid, callerParam);
        if (match == null) {
            LOG.error("match no exists! mid : {}, caller : {}", mid, callerParam);
            response.setStatus(HttpStatus.NOT_FOUND.value());
            return null;
        }

        long eid = match.getEid();
        if (eid <= 0) {
            eid = CallerUtils.getIdofCountryAndLanguage(match.getEids(), callerParam.getCountry(), callerParam.getLanguage());
            if (eid <= 0) {
                LOG.error("episode no exists! mid : {}, caller : {}", mid, callerParam);
                response.setStatus(HttpStatus.NOT_FOUND.value());
                return null;
            }
        }
        IdType idType = LeIdApis.checkIdTye(eid);
        if (idType == null || idType != IdType.EPISODE) {
            LOG.error("fail to render episode page, illegal eid : {}", eid);
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            return null;
        }
        TComboEpisode episode = QmtSbcEpisodeApis.getTComboEpisodeById(eid, callerParam);
        if (episode == null) {
            LOG.error("fail to render episode page, episode eid : {} no exist.", eid);
            response.setStatus(HttpStatus.NOT_FOUND.value());
            return null;
        }
        //根据后台填写的地址做302跳转
        if (episode.isIsUrlRedirect()) {
            return "redirect:" + episode.getPcRedirectUrl();
        }
        if (episode.getType() == EpisodeType.PROGRAM) {
            return renderProgramEpisode(model, episode, liveId, callerParam, locale);
        } else {
            matchService.fillModel4NotStartMatch(model, match, episode, callerParam, locale);
            return "sport-eurocup-desktop/page/eurocup-game/eurocup-game";

        }
    }

    /**
     * 欧洲杯赛程页
     *
     * @return
     */
    @RequestMapping("/euro2016/schedule")
    public String renderEurocupSchedule(Model model, Locale locale, HttpServletResponse response) {
        metaFiller.fillEuroScheduleMeta(model, locale);
        return "sport-eurocup-desktop/page/schedule/schedule";
    }


    /**
     * 欧洲杯数据榜
     *
     * @return
     */
    @RequestMapping("/euro2016/data")
    public String renderEurocupTopList(Model model, Locale locale, HttpServletResponse response) {
        metaFiller.fillEuroDataMeta(model, locale);
        return "sport-eurocup-desktop/page/eurocup-datatable/eurocup-datatable";
    }


    /**
     * 欧洲杯24强
     *
     * @return
     */
    @RequestMapping("/euro2016/teams")
    public String renderEurocupTop24(Model model, Locale locale, HttpServletResponse response) {
        metaFiller.fillEuroTeamsMeta(model, locale);
        return "sport-eurocup-desktop/page/top24-home/top24-home";
    }

    /**
     * 欧洲杯积分榜
     *
     * @return
     */
    @RequestMapping("/euro2016/standings")
    public String renderEurocupStandings(Model model, Locale locale, HttpServletResponse response) {
        metaFiller.fillEuroStandingsMeta(model, locale);
        return "sport-eurocup-desktop/page/score/score";
    }

    /**
     * 欧洲杯积分榜
     *
     * @return
     */
    @RequestMapping("/euro2016/scorer")
    public String renderEurocupScorer(Model model, Locale locale, HttpServletResponse response) {
        metaFiller.fillEuroScorerMeta(model, locale);
        return "sport-eurocup-desktop/page/eurocup-shot/eurocup-shot";
    }

    @RequestMapping(value = "/2016copaamerica", method = RequestMethod.GET)
    public String render2016copaamerica(Model model, HttpServletResponse response) {
        return "sport-americancup-desktop/page/americancup-home/americancup-home";

    }


    /**
     * 欧洲杯图集新闻列表页
     *
     * @return
     */
    @RequestMapping("/euro2016/news/photo")
    public String renderEurocupNewsPhoto(Model model, Locale locale, HttpServletResponse response) {
        metaFiller.fillEuroNewsPhotoMeta(model, locale);
        return "sport-eurocup-desktop/page/eurocup-picnews/eurocup-picnews";
    }

    //-----------------------------欧洲杯测试用-------------------------------------

    /**
     * 美洲杯积分榜
     *
     * @return
     */
    @RequestMapping("/2016cac/standings")
    public String renderCacStandings(Model model, Locale locale, HttpServletResponse response) {
        metaFiller.fillEuroStandingsMeta(model, locale);
        return "sport-americancup-desktop/page/score/score";
    }

    /**
     * 美洲杯射手榜
     *
     * @return
     */
    @RequestMapping("/2016cac/scorer")
    public String renderCacScorer(Model model, Locale locale, HttpServletResponse response) {
        metaFiller.fillEuroScorerMeta(model, locale);
        return "sport-americancup-desktop/page/eurocup-shot/eurocup-shot";
    }

	//-----------------------------香港测试-------------------------------------
	/**
	 * 香港首页测试
	 *
	 * @return
	 */
	@RequestMapping("/hk/index")
	public String renderHkIndex(Model model, HttpServletResponse response) {
		return "hk-channel-desktop/page/index/index";
	}

	/**
	 * 香港英超首页测试
	 *
	 * @return
	 */
	@RequestMapping("/epl/index")
	public String renderEplIndex(Model model, HttpServletResponse response) {
		return "hk-channel-desktop/page/epl-index/epl-index";
	}

	//-----------------------------香港测试-------------------------------------

	//-----------------------------香港NBA-------------------------------------

	/**
	 * 香港NBA首页
	 *
	 * @return
	 */
	@RequestMapping("/nba")
	public String renderHKNba(Model model, HttpServletResponse response) {
		return "hk-sport-nba-desktop/page/index/index";
	}

	/**
	 * 香港NBA赛程页
	 *
	 * @return
	 */
	@RequestMapping("/nba/schedule")
	public String renderHKNbaSchedule(Model model, HttpServletResponse response) {
		return "hk-sport-nba-desktop/page/schedule/schedule";
	}

	/**
	 * 香港NBA排行榜
	 *
	 * @return
	 */
	@RequestMapping("/nba/ranking")
	public String renderHKNbaRanking(Model model, HttpServletResponse response) {
		return "hk-sport-nba-desktop/page/ranking/ranking";
	}

	/**
	 * 香港NBA视频页
	 *
	 * @return
	 */
	@RequestMapping("/nba/video")
	public String renderHKNbaVideo(Model model, HttpServletResponse response) {
		return "hk-sport-nba-desktop/page/videolist/videolist";
	}

	/**
	 * 香港NBA球员页
	 *
	 * @return
	 */
	@RequestMapping("/nba/playerstat")
	public String renderHKNbaPlayers(Model model, HttpServletResponse response) {
		return "hk-sport-nba-desktop/page/player/player";
	}

}