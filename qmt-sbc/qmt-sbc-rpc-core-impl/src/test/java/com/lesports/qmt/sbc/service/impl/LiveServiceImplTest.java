package com.lesports.qmt.sbc.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.lesports.api.common.CountryCode;
import com.lesports.api.common.LiveStatus;
import com.lesports.qmt.sbc.api.common.ShieldType;
import com.lesports.qmt.sbc.api.dto.StreamSourceType;
import com.lesports.qmt.sbc.model.Live;
import com.lesports.utils.LeDateUtils;
import org.junit.Test;

import java.util.Date;

/**
 * Created by zhangxudong@le.com on 11/24/16.
 */
public class LiveServiceImplTest {

    @Test
    public void testDoCreate() {
        Live live = new Live();
        live.setEid(1L);
        live.setStartTime(LeDateUtils.formatYYYYMMDDHHMMSS(new Date()));
        live.setEndTime(LeDateUtils.formatYYYYMMDDHHMMSS(new Date()));
        live.setStreamSourceType(StreamSourceType.LETV);
        live.setThirdLivePageUrl("http://douniwan.org");
        live.setChannelId(1L);
        live.setCibnChannelId(1L);
        live.setSpecialLive(1);
        live.setIsDrm(true);
        live.setCommentaryLanguage("詹俊解说/粤语解说");
//        live.setAlbumId(1L);
//        live.setSubjectUrl("http://douniwan.org");
        live.setWeight(1);
//        live.setCharge(1);
//        live.setScreeningId("1");
        live.setPayBeginTime(LeDateUtils.formatYYYYMMDDHHMMSS(new Date()));
        live.setPayPlatforms(null);
        live.setIsManual(1);
        live.setCopyrightId(1L);
        live.setPlayPlatforms(null);
        live.setShieldType(ShieldType.ALLOW);
        live.setShieldRegion(Lists.newArrayList());
        live.setBelongArea(CountryCode.ALL);
        live.setStatus(LiveStatus.LIVE_END);
//        live.setCoverImage(Maps.newHashMap());
        live.setRelatedIds(Lists.newArrayList());
        live.setDeleted(true);
        live.setShieldRowId(1L);
//        LiveService liveService = new LiveService();
//        liveService.save(live, true);
        return;
    }
}
