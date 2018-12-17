package com.lesports.qmt.sbd;

import com.google.common.collect.Lists;
import com.lesports.api.common.CountryCode;
import com.lesports.api.common.MatchStatus;
import com.lesports.qmt.sbd.model.*;
import org.junit.Test;

/**
 * trunk.
 *
 * @author pangchuanxiao
 * @since 2016/12/5
 */
public class SbdMatchInternalApisTest {

    @Test
    public void testA() {
        Partner partner = new Partner();
        partner.setId("1695710");
        partner.setType(PartnerType.STATS);
        SbdMatchInternalApis.getMatchByPartner(partner);
    }

    @Test
    public void testSaveMatch() {
        Match match = new Match();
//        match.setId(671201003L);
        match.setName("match test5");
        match.setStartTime("20170102213000");
        match.setStatus(MatchStatus.MATCH_NOT_START);
        match.setCid(56001L);
        match.setCsid(100499002L);
        match.setGameFType(100017000L);
        match.setAllowCountries(Lists.newArrayList(CountryCode.CN));
        SbdMatchInternalApis.saveMatch(match);
    }

    @Test
    public void testmatchStats() {
        MatchStats match = new MatchStats();
//        match.setId(671201003L);
        match.setId(39901003L);
        SbdMatchInternalApis.saveMatchStats(match);

    }
    @Test
    public void testLiveEvent(){
        LiveEvent liveEvent=new LiveEvent();
        Partner validPartner=new Partner();
        validPartner.setId("1");
        validPartner.setType(PartnerType.SODA);
        liveEvent.setPartners(Lists.newArrayList(validPartner));
        liveEvent.setName("进球");
        liveEvent.setParentType(0L);
        SbdLiveEventInternalApis.saveLiveEvent(liveEvent);
    }
    @Test
    public void testLiveEventfind(){
     LiveEvent liveEvent=SbdLiveEventInternalApis.getLiveEventByNameAndParentId("失误",0L);
        System.out.print(liveEvent.toString());
    }

}
