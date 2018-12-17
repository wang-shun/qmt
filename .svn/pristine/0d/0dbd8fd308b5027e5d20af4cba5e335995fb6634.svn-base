package com.lesports.qmt.sbc.web.controller;

import com.lesports.qmt.sbc.service.NewsWebService;
import com.lesports.qmt.sbc.vo.NewsVo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

/**
 * Created by denghui on 2016/12/3.
 */
@Controller
@RequestMapping(value = "/preview")
public class PreviewController {

    @Resource
    private NewsWebService newsWebService;

    @RequestMapping(value = "/news/{id}")
    public ModelAndView hello(@PathVariable long id) {
        ModelAndView mv = new ModelAndView();
        NewsVo newsVo = newsWebService.findOne(id);
        mv.addObject("tNews", newsVo.pretty());
        mv.setViewName("index");
        return mv;
    }
}
