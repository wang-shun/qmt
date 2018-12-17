package com.lesports.msite.controller;

/**
 * Created by gengchengliang on 2015/7/3.
 */

import com.lesports.api.common.CallerParam;
import com.lesports.qmt.web.helper.MetaFiller;
import com.lesports.utils.CallerUtils;
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
public class MSiteShowController {

	private final static Logger LOG = LoggerFactory.getLogger(MSiteShowController.class);
    @Resource
    private MetaFiller metaFiller;

	/**
	 * 比赛大厅
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/schedule", method = RequestMethod.GET)
	public String renderShowPage(Model model,Locale locale) {
		CallerParam callerParam = CallerUtils.getCallerByLocale(locale);
        metaFiller.fillScheduleMeta(model,locale);
		//香港-繁体 m站动态页
		if (callerParam.getCallerId() == 3004l) {
			return "hk-sport-calendar-mobile/page/calendar/calendar";
		}
        return "sport-hall-mobile/page/hall/hall";
	}

    /**
     * 渲染错误页面
     * @return
     */
    @RequestMapping(value = "/error", method = RequestMethod.GET)
    public String renderErrorPage(Model model) {
        return "sport-main-mobile/page/error/error";
    }

}
