package com.lesports.msite.controller;

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

/**
 * 比赛页
 */
@Controller
@RequestMapping("/")
public class MSitePlayController {

    private final static Logger LOG = LoggerFactory.getLogger(MSitePlayController.class);

    @Resource
    private MatchService matchService;

    @Resource
    private VideoService videoService;

    @Resource
    private NewsService newsService;

    @Resource
    private MetaFiller metaFiller;

    @Resource
    private TeamService teamService;

    @Resource
    private PlayerService playerService;

    /**
     * 渲染比赛图文直播页,需要传入比赛id
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
        if(eid <= 0) {
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
     * 渲染比赛图文直播页,需要传入比赛id
     *
     * @param mid    matchId
     * @param status 状态
     * @return
     */
    @RequestMapping(value = "/match/{mid}/tlive", method = RequestMethod.GET)
    public String renderMatchImageTextPage(@PathVariable long mid,
                                           @RequestParam(value = "status", required = false, defaultValue = "-1") int status,
                                           Locale locale,
                                           HttpServletResponse response) {

        CallerParam callerParam = CallerUtils.getCallerByLocale(locale);
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
        return "forward:/episode/" +eid + "?status=" + status;
    }

    /**
     * 渲染比赛页,需要传入比赛id
     *
     * @param tliveId 图文直播id
     * @param status  状态
     * @return
     */
    @RequestMapping(value = "/tlive/{tliveId}", method = RequestMethod.GET)
    public String renderImageTextPage(@PathVariable long tliveId,
                                      @RequestParam(value = "status", required = false, defaultValue = "-1") int status,
                                      Locale locale,
                                      HttpServletResponse response) {
        CallerParam callerParam = CallerUtils.getCallerByLocale(locale);
        long eid = QmtSbcEpisodeApis.getEpisodeIdByTextLiveId(tliveId, callerParam);
        if (eid <= 0) {
            LOG.error("fail to renderTextLivePage. no episode found for textlive.  tliveId : {}, caller : {}",
                    tliveId, callerParam);
            response.setStatus(HttpStatus.NOT_FOUND.value());
            return null;
        }
        return "forward:/episode/" + eid + "?status=" + status;
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
                                    Model model, Locale locale, HttpServletResponse response) {
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
            return "redirect:" + episode.getMRedirectUrl();
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

				//香港-繁体 m站动态页
				if (callerParam.getCallerId() == 3004l) {
					return "hk-sport-live-mobile/page/live/live";
				}

                return "sport-match-mobile/page/live/live";
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

				//香港-繁体 m站动态页
				if (callerParam.getCallerId() == 3004l) {
					return "hk-sport-live-mobile/page/live/live";
				}

                return "sport-match-mobile/page/live/live";
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
        //比赛
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

		//香港-繁体 m站动态页
		if (callerParam.getCallerId() == 3004l) {
			matchService.fillModel4EndMatch(model, match, episode, callerParam, locale);
			return "hk-sport-match-mobile/page/match/match";
		}

        switch (mstatus) {
            case LIVE_NOT_START:
                matchService.fillModel4NotStartMatch(model, match, episode, callerParam, locale);
                return "sport-match-mobile/page/beforegame/beforegame";
            case LIVING:
                matchService.fillModel4Matching(model, match, episode, callerParam, locale);
                return "sport-match-mobile/page/ingame/ingame";
            case LIVE_END:
                matchService.fillModel4EndMatch(model, match, episode, callerParam, locale);
                return "sport-match-mobile/page/aftergame/aftergame";
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
    public String renderVideoPage(@PathVariable long vid, Model model, Locale locale, HttpServletResponse response) {
        CallerParam callerParam = CallerUtils.getCallerByLocale(locale);
        TVideo video = QmtSbcApis.getTVideoById(vid, callerParam);
        if (video == null) {
            LOG.error("fail to renderVideoPage. video no exist. vid : {}, caller : {}", vid, callerParam);
            response.setStatus(HttpStatus.NOT_FOUND.value());
            return null;
        }
        videoService.fillModel4Video(model, video, callerParam, locale);
		//香港-繁体 m站动态页
		if (callerParam.getCallerId() == 3004l) {
			return "hk-sport-video-mobile/page/video/video";
		}
        return "sport-match-mobile/page/video/video";
    }

    /**
     * 渲染节目点播页面,需要传入对应的视频id
     *
     * @param pkgid_vid
     * @param model
     * @return
     */
    @RequestMapping("/video/topic/s/{pkgid_vid}")
    public String renderTopicVideoPage(@PathVariable String pkgid_vid, Model model, Locale locale,
                                       HttpServletResponse response) {
        CallerParam callerParam = CallerUtils.getCallerByLocale(locale);
        videoService.fillMode4TopicVideo(model, pkgid_vid, callerParam, locale);
        return "sport-match-mobile/page/video/video";
    }

	/**
	 * 渲染小专题的列表页
	 *
	 * @param topicName
	 * @param model
	 * @return
	 */
	@RequestMapping("/topic/s/{topicName}")
	public String renderTopicListPage(@PathVariable String topicName, Model model, HttpServletResponse response,
									  Locale locale) {
		CallerParam callerParam = CallerUtils.getCallerByLocale(locale);
		if (StringUtils.isBlank(topicName)) {
			LOG.error("render topic list error ! topicName can not be null ! topicName = {} ", topicName);
			return null;
		}
		videoService.fillTopicListModel(model, topicName, response, callerParam, locale);
		return "sport-cmstopics-mobile/page/mtopics/mtopics";
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
		return "sport-cmstopics-mobile/page/emergency/emergency";
	}


	/**
     * 渲染节目直播页面,需要传入对应节目的id
     *
     * @param liveId 直播流ID
     * @param uid    用户id
     * @return
     */
    @RequestMapping(value = "/live/{liveId}", method = RequestMethod.GET)
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
        return "forward:/episode/" + eid + "?liveId=" + liveId + "&uid=" + uid;
    }

    /**
     * 渲染新闻详情页,需要传入对应的新闻id
     *
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("/news/{id}")
    public String renderNewsPage(@PathVariable long id, Model model, Locale locale, HttpServletResponse response) {
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
				//香港-繁体 m站动态页
				if (callerParam.getCallerId() == 3004l) {
					return "hk-sport-news-mobile/page/videonews/videonews";
				}
				return "sport-news-mobile/page/videonews/videonews";
            case IMAGE_TEXT:
				//香港-繁体 m站动态页
				if (callerParam.getCallerId() == 3004l) {
					return "hk-sport-news-mobile/page/graphic/graphic";
				}
                return "sport-news-mobile/page/graphic/graphic";
            case RICHTEXT:
				//香港-繁体 m站动态页
				if (callerParam.getCallerId() == 3004l) {
					return "hk-sport-news-mobile/page/richtext/richtext";
				}
                return "sport-news-mobile/page/richtext/richtext";
            case IMAGE_ALBUM:
                return "sport-news-mobile/page/atlas/atlas";
        }
        throw new IllegalArgumentException();
    }

    /**
     * 渲染帖子详情
     *
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("/z/post/{id}")
    public String renderPostPage(@PathVariable String id, Model model, HttpServletResponse response) {
        model.addAttribute("id", id);
        return "sport-community-mobile/page/sharedpost/sharedpost";
    }


    /**
     * 渲染阵营首页
     *
     * @param campId
     * @param model
     * @return
     */
    @RequestMapping("/z/camp/{campId}")
    public String renderCampPage(@PathVariable String campId, Model model, HttpServletResponse response) {
        model.addAttribute("campId", campId);
        return "sport-community-mobile/page/camp/camp";
    }

    /**
     * 渲染中超首页
     *
     * @return
     */
    @RequestMapping("/csl/schedule")
    public String renderCslSchedule(Model model, HttpServletResponse response,Locale locale) {
		CallerParam callerParam = CallerUtils.getCallerByLocale(locale);
        metaFiller.fillCslScheduleMeta(model,locale);
		//香港-繁体 m站动态页
		if (callerParam.getCallerId() == 3004l) {
			return "hk-sport-csl-mobile/page/schedule/schedule";
		}
		return "sport-csl-mobile/page/schedule/schedule";
    }

    /**
     * 渲染中超榜单
     *
     * @return
     */
    @RequestMapping("/csl/toplist")
    public String renderCslToplist(Model model, HttpServletResponse response,Locale locale) {
        metaFiller.fillCslToplistMeta(model,locale);
        return "sport-csl-mobile/page/stat/stat";
    }

	/**
	 * 渲染球队页
	 *
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/team/{id}", method = RequestMethod.GET)
	public String renderTTeamPage(@PathVariable long id, @RequestParam(value = "cid", required = false, defaultValue = "47001") long cid,
								  Model model,Locale locale) {
		CallerParam callerParam = CallerUtils.getCallerByLocale(locale);
		TTeam tTeam = SbdTeamApis.getTTeamById(id, callerParam);
		if (tTeam == null) {
			LOG.error("this team is null ! id = {}", id);
			throw new IllegalArgumentException("fail to render team page, illegal id: " + id);
		} else {
			teamService.fillModelWithTeam(model, tTeam, callerParam, locale);
		}
		return "sport-team-mobile/page/football/football";
	}

	/**
	 * 渲染球员页
	 *
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/player/{id}", method = RequestMethod.GET)
	public String renderTPlayerPage(@PathVariable long id, @RequestParam(value = "cid", required = false, defaultValue = "47001") long cid,
									Model model,Locale locale) {
		CallerParam callerParam = CallerUtils.getCallerByLocale(locale);
		TPlayer tPlayer = SbdPlayerApis.getTPlayerById(id, callerParam);
		if (tPlayer == null) {
			LOG.error("this player is null ! id = {} ", id  );
			throw new IllegalArgumentException("fail to render player page, illegal id: " + id);
		} else {
			playerService.fillModelWithPlayer(model, tPlayer,callerParam,locale);
		}
		return "sport-player-mobile/page/football/football";
	}


	//-----------------------------欧洲杯测试用-------------------------------------

    /**
     * 欧洲杯首页
     *
     * @return
     */
    @RequestMapping("/eurocup/index")
    public String renderEurocupIndex(Model model, HttpServletResponse response) {
        return "sport-eurocup-mobile/page/index/index";
    }

    /**
     * 欧洲杯赛程页
     *
     * @return
     */
    @RequestMapping("/euro2016/schedule")
    public String renderEurocupSchedule(Model model,Locale locale, HttpServletResponse response) {
        metaFiller.fillEuroScheduleMeta(model,locale);
        return "sport-eurocup-mobile/page/schedule/schedule";
    }


    /**
     * 欧洲杯积分榜
     *
     * @return
     */
    @RequestMapping("/euro2016/standings")
    public String renderEurocupTopList(Model model,Locale locale, HttpServletResponse response) {
        metaFiller.fillEuroStandingsMeta(model,locale);
        return "sport-eurocup-mobile/page/score/score";
    }


    /**
     * 欧洲杯24强
     *
     * @return
     */
    @RequestMapping("/euro2016/teams")
    public String renderEurocupTop24(Model model,Locale locale,  HttpServletResponse response) {
        metaFiller.fillEuroTeamsMeta(model,locale);
        return "sport-eurocup-mobile/page/allteams/allteams";
    }
	/**
	 * 欧洲杯新闻
	 *
	 * @return
	 */
	@RequestMapping("/euro2016/news")
	public String renderEurocupNews(Model model,Locale locale,   HttpServletResponse response) {
        metaFiller.fillEuroNewsMeta(model,locale);
		return "sport-eurocup-mobile/page/news/news";
	}

	//-----------------------------欧洲杯测试用-------------------------------------


	//-----------------------------美洲杯测试用-------------------------------------

	/**
	 * 美洲杯首页
	 *
	 * @return
	 */
	@RequestMapping("/2016cac/index")
	public String renderCopacupIndex(Model model, HttpServletResponse response) {
		return "sport-copacup-mobile/page/index/index";
	}

	/**
	 * 美洲杯赛程页
	 *
	 * @return
	 */
	@RequestMapping("/2016cac/schedule")
	public String renderCopacupSchedule(Model model, HttpServletResponse response) {
		return "sport-copacup-mobile/page/schedule/schedule";
	}


	/**
	 * 美洲杯积分榜
	 *
	 * @return
	 */
	@RequestMapping("/2016cac/standings")
	public String renderCopacupTopList(Model model, HttpServletResponse response) {
		return "sport-copacup-mobile/page/score/score";
	}


	/**
	 * 美洲杯16强
	 *
	 * @return
	 */
	@RequestMapping("/2016cac/top16")
	public String renderCopacupTop16(Model model, HttpServletResponse response) {
		return "sport-copacup-mobile/page/allteams/allteams";
	}

	/**
	 * 美洲杯新闻
	 *
	 * @return
	 */
	@RequestMapping("/2016cac/news")
	public String renderCopacupNews(Model model, HttpServletResponse response) {
		return "sport-copacup-mobile/page/news/news";
	}

	//-----------------------------美洲杯用-------------------------------------

	/**
	 * 支付页面
	 *
	 * @return
	 */
	@RequestMapping("/pay")
	public String renderPay(Model model, HttpServletResponse response) {
		return "sport-pay-mobile/page/index/index";
	}

	//-----------------------------乐体app会员中心用-------------------------------------

	/**
	 * 会员频道首页
	 *
	 * @return
	 */
	@RequestMapping("/vip/plaza")
	public String renderVipPlaza(Model model, HttpServletResponse response) {
		return "sport-user-mobile/page/vip/index/index";
	}

	/**
	 * 直播聚合页
	 *
	 * @return
	 */
	@RequestMapping("/vip/live")
	public String renderVipLive(Model model, HttpServletResponse response) {
		return "sport-user-mobile/page/vip/live/live";
	}

	/**
	 * 活动聚合页
	 *
	 * @return
	 */
	@RequestMapping("/vip/promo")
	public String renderVipPromo(Model model, HttpServletResponse response) {
		return "sport-user-mobile/page/vip/weal/weal";
	}

	//-----------------------------香港测试-------------------------------------
	/**
	 * 香港首页测试
	 *
	 * @return
	 */
	@RequestMapping("/hk/index")
	public String renderHkIndex(Model model, HttpServletResponse response) {
		return "hk-sport-main-mobile/page/index/index";
	}

	/**
	 * 香港英超首页测试
	 *
	 * @return
	 */
	@RequestMapping("/epl/index")
	public String renderEplIndex(Model model, HttpServletResponse response) {
		return "hk-sport-epl-mobile/page/index/index";
	}

	//-----------------------------香港测试 end -------------------------------------

	//-----------------------------香港英超正式 start -------------------------------------
	/**
	 * 香港英超积分榜
	 *
	 * @return
	 */
	@RequestMapping("/epl/standing")
	public String renderEplStanding(Model model, HttpServletResponse response) {
		return "hk-sport-epl-mobile/page/ranking/ranking";
	}

	/**
	 * 香港英超射手榜
	 *
	 * @return
	 */
	@RequestMapping("/epl/topscorers")
	public String renderEplTopscorers(Model model, HttpServletResponse response) {
		return "hk-sport-epl-mobile/page/soccar/soccar";
	}

	/**
	 * 香港英超助攻榜
	 *
	 * @return
	 */
	@RequestMapping("/epl/assists")
	public String renderEplAssists(Model model, HttpServletResponse response) {
		return "hk-sport-epl-mobile/page/assists/assists";
	}

	/**
	 * 香港英超赛程页
	 *
	 * @return
	 */
	@RequestMapping("/epl/schedule")
	public String renderEplSchedule(Model model, HttpServletResponse response) {
		return "hk-sport-epl-mobile/page/schedule/schedule";
	}

	//-----------------------------香港英超正式 end -------------------------------------

	//-----------------------------12强 start -------------------------------------
	/**
	 * 12强新闻页
	 *
	 * @return
	 */
	@RequestMapping("/12/news")
	public String render12News(Model model, HttpServletResponse response) {
		return "sport-top12-mobile/page/news/news";
	}

	/**
	 * 12强赛程页
	 *
	 * @return
	 */
	@RequestMapping("/12/schedule")
	public String render12Match(Model model, HttpServletResponse response) {
		return "sport-top12-mobile/page/match/match";
	}

	/**
	 * 12强球队页
	 *
	 * @return
	 */
	@RequestMapping("/12/teams")
	public String render12Teams(Model model, HttpServletResponse response) {
		return "sport-top12-mobile/page/top12/top12";
	}

	/**
	 * 12强积分榜
	 *
	 * @return
	 */
	@RequestMapping("/12/standings")
	public String render12Standings(Model model, HttpServletResponse response) {
		return "sport-top12-mobile/page/standing/standing";
	}

	/**
	 * 12强新闻页
	 *
	 * @return
	 */
	@RequestMapping("/12/scorer")
	public String render12Scorer(Model model, HttpServletResponse response) {
		return "sport-top12-mobile/page/scorer/scorer";
	}


	//-----------------------------12强 end -------------------------------------


    /**
     * 美国公开赛首页
     *
     * @return
     */
    @RequestMapping("/usopen2016")
    public String renderUSOpenIndex(Model model, HttpServletResponse response) {
        return "hk-sport-usopen-mobile/page/index/index";
    }

    /**
     * 美国公开赛赛程
     *
     * @return
     */
    @RequestMapping("/usopen2016/schedule")
    public String renderUSOpenSchedule(Model model, HttpServletResponse response) {
        return "hk-sport-usopen-mobile/page/schedule/schedule";
    }

    //-----------------------------美网-------------------------------------

	/**
	 * 香港中超首页
	 *
	 * @return
	 */
	@RequestMapping("/csl")
	public String renderHKCslIndex(Model model, HttpServletResponse response) {
		return "hk-sport-csl-mobile/page/index/index";
	}

	/**
	 * 香港MLB首页
	 *
	 * @return
	 */
	@RequestMapping("/mlb")
	public String renderHKMlbIndex(Model model, HttpServletResponse response) {
		return "hk-sport-mlb-mobile/page/index/index";
	}

	/**
	 * 香港MLB赛程页
	 *
	 * @return
	 */
	@RequestMapping("/mlb/schedule")
	public String renderHKMlbSchedule(Model model, HttpServletResponse response) {
		return "hk-sport-mlb-mobile/page/schedule/schedule";
	}

	/**
	 * 香港轮播台
	 *
	 * @return
	 */
	@RequestMapping("/carousel")
	public String renderHKCarousel(Model model, HttpServletResponse response) {
		return "hk-sport-carousel-mobile/page/index/index";
	}

	//-----------------------------香港NBA-------------------------------------

	/**
	 * 香港NBA首页
	 *
	 * @return
	 */
	@RequestMapping("/nba")
	public String renderHKNba(Model model, HttpServletResponse response) {
		return "hk-sport-nba-mobile/page/index/index";
	}

	/**
	 * 香港NBA赛程页
	 *
	 * @return
	 */
	@RequestMapping("/nba/schedule")
	public String renderHKNbaSchedule(Model model, HttpServletResponse response) {
		return "hk-sport-nba-mobile/page/schedule/schedule";
	}

	/**
	 * 香港NBA排行榜
	 *
	 * @return
	 */
	@RequestMapping("/nba/ranking")
	public String renderHKNbaRanking(Model model, HttpServletResponse response) {
		return "hk-sport-nba-mobile/page/ranking/ranking";
	}

	/**
	 * 香港NBA球员榜单
	 *
	 * @return
	 */
	@RequestMapping("/nba/playerstat")
	public String renderHKNbaPlayerstat(Model model, HttpServletResponse response) {
		return "hk-sport-nba-mobile/page/playerstat/playerstat";
	}

}