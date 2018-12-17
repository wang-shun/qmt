package com.lesports.qmt.play.controller;

/**
 * Created by gengchengliang on 2015/7/3.
 */

import com.lesports.qmt.web.helper.MetaFiller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import java.util.Locale;

/**
 * 首页
 */
@Controller
@RequestMapping("/")
public class LeShowController {

    private final static Logger LOG = LoggerFactory.getLogger(LeShowController.class);

    @Resource
    private MetaFiller metaFiller;

    /**
     * 比赛大厅
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/schedule", method = RequestMethod.GET)
    public String renderShowPage(Model model,Locale locale) {
        metaFiller.fillScheduleMeta(model,locale);
        return "sport-hall-desktop/page/gamehall/gamehall";
    }

    @RequestMapping(value = "/carousel", method = RequestMethod.GET)
    public String renderLunboPage(Model model) {
        return "sport-main/page/carousel";
    }

    @RequestMapping(value = "/cross_www", method = RequestMethod.GET)
    public String renderCrossWWWPage() {
        return "common/page/execWsports/execWsports";
    }

    @RequestMapping(value = "/channel", method = RequestMethod.GET)
    public String renderChannelPage() {
        return "sport-main/page/channel";
    }

    @RequestMapping(value = "/guide", method = RequestMethod.GET)
    public String renderGuidePage() {
        return "sport-main/page/guide";
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String renderIndexPage() {
        return "sport-main/page/index";
    }

}
