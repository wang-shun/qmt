//package com.lesports.qmt.play.controller;
//
//import client.SopsApis;
//import com.lesports.api.common.CallerParam;
//import com.lesports.cms.web.helper.MetaFiller;
//import com.lesports.cms.web.service.MatchService;
//import com.lesports.id.api.IdType;
//import com.lesports.id.client.LeIdApis;
//import com.lesports.sms.api.vo.TComboEpisode;
//import com.lesports.sms.api.vo.TDetailMatch;
//import com.lesports.sms.client.SbdsApis;
//import com.lesports.utils.CallerUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.http.HttpStatus;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;
//
//import javax.annotation.Resource;
//import javax.servlet.http.HttpServletResponse;
//import java.util.Locale;
//
///**
// * Created by ruiyuansheng on 2016/7/13.
// */
//@Controller
//@RequestMapping("/")
//public class LeOlympicController {
//
//
//    private final static Logger LOG = LoggerFactory.getLogger(LeOlympicController.class);
//
//    @Resource
//    private MatchService matchService;
//
//    @Resource
//    private MetaFiller metaFiller;
//
// /*------------------------------------奥运会（Olympic）-----------------------------------------------*/
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
//                                           Locale locale,
//                                           Model model,
//                                           @RequestParam(value = "liveId", required = false, defaultValue = "") String liveId,
//                                           HttpServletResponse response) {
//        if (renderOlympicMatch(mid, locale, model, response,true)) return null;
//        return "sport-olympicgame-desktop/page/olympic-namelist/olympic-namelist";
//
//    }
//
//    private boolean renderOlympicMatch(long mid, Locale locale, Model model, HttpServletResponse response,boolean isLineup) {
//        LOG.info("render olympic EpisodePage, mid : {}", mid);
//        CallerParam callerParam = CallerUtils.getDefaultCaller();
//        TDetailMatch match = SbdsApis.getTDetailMatchById(mid, callerParam);
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
//        if(isLineup){
//            metaFiller.fillOlympicNameListMeta(model,episode,locale);
//        }else{
//            metaFiller.fillOlympicResultsMeta(model,episode,locale);
//        }
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
//                                           Locale locale,
//                                           Model model,
//                                           @RequestParam(value = "liveId", required = false, defaultValue = "") String liveId,
//                                           HttpServletResponse response) {
//        if (renderOlympicMatch(mid, locale, model, response,false)) return null;
//        return "sport-olympicgame-desktop/page/olympic-grade/olympic-grade";
//
//    }
//
//
//    /**
//     * 奥运赛程
//     *
//     * @param model
//     * @return
//     */
//    @RequestMapping(value = "2016/schedule", method = RequestMethod.GET)
//    public String renderSchedulePage(Model model,Locale locale) {
//        metaFiller.fillScheduleMeta(model,locale);
//        return "sport-olympicgame-desktop/page/schedule/schedule";
//    }
//
//    /**
//     * 奥运奖牌榜
//     *
//     * @param model
//     * @return
//     */
//    @RequestMapping(value = "2016/medals", method = RequestMethod.GET)
//    public String renderMedalsPage(Model model,Locale locale) {
//        metaFiller.fillScheduleMeta(model,locale);
//        return "sport-olympicgame-desktop/page/medals/medals";
//    }
//
//    /**
//     * 奥运奖牌榜
//     *
//     * @param model
//     * @return
//     */
//    @RequestMapping(value = "2016/searchschedule", method = RequestMethod.GET)
//    public String renderSearchSchedulePage(Model model,Locale locale) {
//        metaFiller.fillScheduleMeta(model,locale);
//        return "sport-olympicgame-desktop/page/searchschedule/searchschedule";
//    }
//
//    /**
//     * 奥运奖牌榜
//     *
//     * @param model
//     * @return
//     */
//    @RequestMapping(value = "2016/china", method = RequestMethod.GET)
//    public String renderChinaPage(Model model,Locale locale) {
//        metaFiller.fillScheduleMeta(model,locale);
//        return "sport-olympicgame-desktop/page/army/army";
//    }
//
//
//}
