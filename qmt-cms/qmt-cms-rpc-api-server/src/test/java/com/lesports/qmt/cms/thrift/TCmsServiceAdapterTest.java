package com.lesports.qmt.cms.thrift;

import com.lesports.AbstractIntegrationTest;
import com.lesports.qmt.cms.api.dto.TColumn;
import com.lesports.qmt.cms.api.dto.TColumnPage;
import com.lesports.qmt.cms.api.dto.TLayout;
import com.lesports.qmt.cms.api.dto.TWidget;
import com.lesports.qmt.cms.api.service.TCmsService;
import org.junit.Assert;
import org.junit.Test;

import javax.annotation.Resource;

import static org.junit.Assert.*;

public class TCmsServiceAdapterTest extends AbstractIntegrationTest {

    @Resource
    private TCmsService.Iface cmsService;

    @Test
    public void testGetTLayoutById() throws Exception {
        TLayout layout = cmsService.getTLayoutById(1L);
        System.out.println("===========================================");
        System.out.println(layout);
        System.out.println("===========================================");
        Assert.assertNotNull(layout);
    }

    @Test
    public void testGetTLayoutByPath() throws Exception {
        TLayout layout = cmsService.getTLayoutByPath("/page/layout/layout_a.vm");
        System.out.println("===========================================");
        System.out.println(layout);
        System.out.println("===========================================");
        Assert.assertNotNull(layout);
    }

    @Test
    public void testGetTWidgetById() throws Exception {
        TWidget widget = cmsService.getTWidgetById(701203L);
        System.out.println("===========================================");
        System.out.println(widget);
        System.out.println("===========================================");
        Assert.assertNotNull(widget);
    }

    @Test
    public void testGetTWidgetByPath() throws Exception {
        TWidget widget = cmsService.getTWidgetByPath("/widget/w3/w3.vm");
        System.out.println("===========================================");
        System.out.println(widget);
        System.out.println("===========================================");
        Assert.assertNotNull(widget);
    }

    @Test
    public void testGetTColumnById() throws Exception {
        TColumn column = cmsService.getTColumnById(301204L);
        System.out.println("===========================================");
        System.out.println(column);
        System.out.println("===========================================");
        Assert.assertNotNull(column);
    }

    @Test
    public void testGetTColumnByFullPath() throws Exception {
        TColumn column = cmsService.getTColumnByFullPath("/nba2016");
        System.out.println("===========================================");
        System.out.println(column);
        System.out.println("===========================================");
        Assert.assertNotNull(column);
    }

    @Test
    public void testGetTColumnPageById() throws Exception {
        TColumnPage cp = cmsService.getTColumnPageById(401205L);
        System.out.println("===========================================");
        System.out.println(cp);
        System.out.println("===========================================");
        Assert.assertNotNull(cp);
    }
}