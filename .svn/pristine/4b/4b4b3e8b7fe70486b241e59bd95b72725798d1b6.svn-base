package com.lesports.sms.thrift;

import com.lesports.AbstractIntegrationTest;
import com.lesports.api.common.CallerParam;
import com.lesports.api.common.CountryCode;
import com.lesports.api.common.LanguageCode;
import com.lesports.qmt.sbd.api.dto.TMatch;
import com.lesports.qmt.sbd.api.service.TSbdService;
import org.junit.Test;

import javax.annotation.Resource;

public class TSbcResourceServiceAdapterTest extends AbstractIntegrationTest {

    @Resource
    private TSbdService.Iface thriftSbdResourceService;

    @Test
    public void testGetTResourceById() throws Exception {
		CallerParam callerParam = new CallerParam();
		callerParam.setCallerId(1001l);
		callerParam.setLanguage(LanguageCode.ZH_CN);
		callerParam.setCountry(CountryCode.CN);
		TMatch tMatch = thriftSbdResourceService.getTMatchById(70201003L, callerParam);
		System.out.println("======================================================");
        System.out.println(tMatch);
        System.out.println("======================================================");
    }
}