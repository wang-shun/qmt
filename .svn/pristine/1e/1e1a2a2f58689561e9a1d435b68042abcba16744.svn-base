package com.lesports.qmt.cms.client;

import com.lesports.qmt.cms.api.dto.TColumn;
import com.lesports.qmt.cms.api.dto.TColumnPage;
import com.lesports.qmt.cms.api.dto.TLayout;
import com.lesports.qmt.cms.api.dto.TWidget;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class QmtCmsApisTest {

    @Test
    public void testGetTLayoutById() throws Exception {
        TLayout layout = QmtCmsApis.getTLayoutById(1L);
        System.out.println("===========================================");
        System.out.println(layout);
        System.out.println("===========================================");
        Assert.assertNotNull(layout);
    }

    @Test
    public void testGetTLayoutByPath() throws Exception {
        TLayout layout = QmtCmsApis.getTLayoutByPath("/page/layout/layout_a.vm");
        System.out.println("===========================================");
        System.out.println(layout);
        System.out.println("===========================================");
        Assert.assertNotNull(layout);
    }

    @Test
    public void testGetTWidgetById() throws Exception {
        TWidget widget = QmtCmsApis.getTWidgetById(701203L);
        System.out.println("===========================================");
        System.out.println(widget);
        System.out.println("===========================================");
        Assert.assertNotNull(widget);
    }

    @Test
    public void testGetTWidgetByPath() throws Exception {
        TWidget widget = QmtCmsApis.getTWidgetByPath("/widget/w3/w3.vm");
        System.out.println("===========================================");
        System.out.println(widget);
        System.out.println("===========================================");
        Assert.assertNotNull(widget);
    }

    @Test
    public void testGetTColumnById() throws Exception {
        TColumn column = QmtCmsApis.getTColumnById(301204L);
        System.out.println("===========================================");
        System.out.println(column);
        System.out.println("===========================================");
        Assert.assertNotNull(column);
    }

    @Test
    public void testGetTColumnByFullPath() throws Exception {
        TColumn column = QmtCmsApis.getTColumnByFullPath("/nba2016");
        System.out.println("===========================================");
        System.out.println(column);
        System.out.println("===========================================");
        Assert.assertNotNull(column);
    }

    @Test
    public void testGetTColumnPageById() throws Exception {
        TColumnPage cp = QmtCmsApis.getTColumnPageById(501205L);
        System.out.println("===========================================");
        System.out.println(cp);
        System.out.println("===========================================");
        Assert.assertNotNull(cp);
    }
}