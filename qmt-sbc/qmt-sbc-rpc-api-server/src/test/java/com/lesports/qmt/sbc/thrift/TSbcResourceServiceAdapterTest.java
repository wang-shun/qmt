package com.lesports.qmt.sbc.thrift;

import com.lesports.AbstractIntegrationTest;
import com.lesports.qmt.sbc.api.dto.TResource;
import com.lesports.qmt.sbc.api.service.TSbcResourceService;
import org.junit.Assert;
import org.junit.Test;

import javax.annotation.Resource;

public class TSbcResourceServiceAdapterTest extends AbstractIntegrationTest {

    @Resource
    private TSbcResourceService.Iface thriftSbcResourceService;

    @Test
    public void testGetTResourceById() throws Exception {
        TResource resource = thriftSbcResourceService.getTResourceById(1000L, null);
        System.out.println("======================================================");
        System.out.println(resource);
        System.out.println("======================================================");
        Assert.assertNotNull(resource);
    }

    public void testGetTResourcesByIds() throws Exception {

    }
}