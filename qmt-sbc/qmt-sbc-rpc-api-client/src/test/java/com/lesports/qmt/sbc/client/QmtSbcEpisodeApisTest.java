package com.lesports.qmt.sbc.client;

import com.lesports.api.common.CallerParam;
import com.lesports.api.common.CountryCode;
import com.lesports.api.common.LanguageCode;
import com.lesports.api.common.PageParam;
import com.lesports.qmt.sbc.api.dto.TComboEpisode;
import com.lesports.qmt.sbc.api.service.GetCurrentEpisodesParam;
import com.lesports.qmt.sbc.api.service.LiveTypeParam;
import com.lesports.utils.CallerUtils;
import org.junit.Test;

import java.util.List;

/**
 * Created by lufei1 on 2016/12/20.
 */
public class QmtSbcEpisodeApisTest {

    @Test
    public void testGetEpisodeById() throws Exception {
        TComboEpisode episode = QmtSbcEpisodeApis.getTComboEpisodeById(13601005L, CallerUtils.getDefaultCaller());
        System.out.println("=======================");
        System.out.println("episode : " + episode);
        System.out.println("=======================");
    }

    @Test
    public void testGetCurrentEpisodes() throws Exception {
        GetCurrentEpisodesParam p = new GetCurrentEpisodesParam();
        p.setGameType(0);
        p.setLiveTypeParam(LiveTypeParam.NOT_ONLY_LIVE);
        PageParam pageParam = new PageParam();
        pageParam.setPage(1);
        pageParam.setCount(20);
        CallerParam caller = new CallerParam();
        caller.setCallerId(1003);
        caller.setLanguage(LanguageCode.ZH_CN);
        caller.setCountry(CountryCode.CN);
        List<TComboEpisode> episodes = QmtSbcEpisodeApis.getCurrentEpisodes(p, pageParam, caller);
        System.out.println("=======================");
        System.out.println("episodes : " + episodes);
        System.out.println("=======================");
    }
}
