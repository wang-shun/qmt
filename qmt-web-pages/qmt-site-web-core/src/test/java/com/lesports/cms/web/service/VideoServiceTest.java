package com.lesports.cms.web.service;

import com.lesports.AbstractIntegrationTest;
import com.lesports.qmt.web.service.VideoService;
import org.junit.Test;

import javax.annotation.Resource;


public class VideoServiceTest extends AbstractIntegrationTest{

    @Resource
    private VideoService videoService;

    @Test
    public void testGetVideosRelatedWithMatch() throws Exception {
//        List<VideoWebView> results = videoService.getVideosRelated4Match(SmsApis.getEpisodeById(118251005L),
//                SmsApis.getDetailMatchById(118251003), LeConstants.LESPORTS_PC_CALLER_CODE);
//        System.out.println("================================");
//        Assert.assertTrue(results.size() > 0);
//        for(VideoWebView video : results){
//            System.out.println(video);
//        }
//        System.out.println("================================");
    }
}