package com.lesports.qmt.scheduler.web.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by zhangxudong@le.com on 11/10/16.
 */
@RestController
@RequestMapping(value = "/schedulers")
public class SchedulerController {

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String getSchedulerNames(@RequestParam(value = "chineseName", required = false, defaultValue = "") String chineseName) {
        return "hello this is scheduler.";
    }
}