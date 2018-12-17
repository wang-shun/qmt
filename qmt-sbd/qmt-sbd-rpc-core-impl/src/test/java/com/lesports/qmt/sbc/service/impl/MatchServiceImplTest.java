package com.lesports.qmt.sbc.service.impl;

import com.lesports.AbstractIntegrationTest;
import com.lesports.api.common.CallerParam;
import com.lesports.api.common.CountryCode;
import com.lesports.api.common.LanguageCode;
import com.lesports.qmt.sbd.api.dto.TMatch;
import com.lesports.qmt.sbd.api.service.TSbdService;
import com.lesports.qmt.sbd.cache.TMatchCache;
import com.lesports.qmt.sbd.converter.TMatchVoConverter;
import com.lesports.qmt.sbd.model.Match;
import com.lesports.qmt.sbd.repository.MatchRepository;
import com.lesports.qmt.sbd.service.MatchService;
import com.lesports.utils.CallerUtils;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * Created by zhonglin on 2017/2/22.
 */
public class MatchServiceImplTest  extends AbstractIntegrationTest {

    @Resource
    private MatchRepository matchRepository;
    @Resource
    private TMatchCache matchCache;
    @Resource
    private TMatchVoConverter matchVoConverter;
    @Resource
    private MatchService matchService;

    @Test
    public void testGetTResourceById() throws Exception {
        Match match = matchService.findOne(70201003L);
        System.out.println("======================================================");
        System.out.println(match.toString());
        System.out.println("======================================================");
    }



    @Test
    public void testGetMatchFromCache() throws Exception {
        TMatch match = matchCache.findOne(70201003L);
        System.out.println("======================================================");
        System.out.println("TMatch: "+match);
        System.out.println("TMatch eids size: "+match.getEids().size());
        System.out.println("TMatch eids: "+match.getEids());
        System.out.println("TMatch eid: "+match.getEid());
        System.out.println("======================================================");
    }

    @Test
    public void testGetMatchByConverter() throws Exception {
        Match match = matchRepository.findBasicOne(70201003L);
        System.out.println("======================================================");
        System.out.println("match eids: "+match.getEids().get(0).getId());
        System.out.println("======================================================");
        TMatch tMatch = matchVoConverter.toDto(match);
        tMatch = matchVoConverter.adaptDto(tMatch, CallerUtils.getDefaultCaller());
        System.out.println("======================================================");
        System.out.println("TMatch: "+tMatch);
        System.out.println("TMatch eid: "+tMatch.getEid());
        System.out.println("======================================================");
    }


    @Test
    public void testDeleteMatchCache() throws Exception {
        System.out.println("======================================================");
        System.out.println(matchCache.delete(70201003L));
        System.out.println("======================================================");
    }

    @Test
    public void testGetTMatchById() throws Exception {
        TMatch match1 = matchCache.findOne(70201003L);
        if(match1 == null){
            match1 = matchService.getTMatchById(70201003L,CallerUtils.getDefaultCaller());
            System.out.println("======================================================");
            System.out.println("TMatch1: "+match1);
            System.out.println("TMatch eid1 size: "+match1.getEids().size());
            System.out.println("TMatch eid1: "+match1.getEids().get(0).getId());
            System.out.println("======================================================");

            TMatch match2 = matchCache.findOne(70201003L);
            System.out.println("======================================================");
            System.out.println("TMatch2: "+match2);
            System.out.println("TMatch eid2 size: "+match2.getEids().size());
            System.out.println("TMatch eid2: "+match2.getEids().get(0).getId());
            System.out.println("======================================================");
        }
        else{
            TMatch match2 = matchCache.findOne(70201003L);
            System.out.println("======================================================");
            System.out.println("TMatch2: "+match2);
            System.out.println("TMatch eid2 size: "+match2.getEids().size());
            System.out.println("TMatch eid2: "+match2.getEids().get(0).getId());
            System.out.println("======================================================");
        }



    }

}
