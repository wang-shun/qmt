package com.lesports.qmt.cms.internal.client;

import com.lesports.api.common.PublishStatus;
import com.lesports.qmt.cms.model.ColumnPage;
import org.junit.Assert;
import org.junit.Test;

public class CmsColumnPageInternalApisTest {

    @Test
    public void testSaveColumnPage() throws Exception {
        ColumnPage page = new ColumnPage();
        page.setColumnId(1L);
        page.setLayoutId(2L);
        page.setName("ellios");
        page.setCreator("wangranyang");
        page.setUrl("http://www.lesports.com/abc");
        page.setStatus(PublishStatus.ONLINE);
        page.setData("zzzz");
        page.setVm("asdfasdfadsf");
        long id = CmsColumnPageInternalApis.saveColumnPage(page);
        ColumnPage cp = CmsColumnPageInternalApis.getColumnPageById(id);
        CmsColumnPageInternalApis.deleteColumnPage(id);
        System.out.println("======================================================");
        System.out.println(id);
        System.out.println(cp);
        System.out.println("======================================================");
        Assert.assertTrue(id > 0);
    }

    @Test
    public void testGetColumnPage() throws Exception {
        ColumnPage cp = CmsColumnPageInternalApis.getColumnPageById(301202L);
        System.out.println("======================================================");
        System.out.println(cp);
        System.out.println("======================================================");
        Assert.assertTrue(cp != null);
    }
}