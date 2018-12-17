package com.lesports.qmt.cms.thrift;

import com.lesports.AbstractIntegrationTest;
import com.lesports.api.common.PublishStatus;
import com.lesports.qmt.cms.api.service.TCmsInternalService;
import com.lesports.qmt.cms.model.ColumnPage;
import com.lesports.utils.transcoders.CachedData;
import com.lesports.utils.transcoders.SerializingTranscoder;
import com.lesports.utils.transcoders.Transcoder;
import org.junit.Assert;
import org.junit.Test;

import javax.annotation.Resource;
import java.nio.ByteBuffer;

public class TCmsInternalServiceAdapterTest extends AbstractIntegrationTest {

    @Resource
    private TCmsInternalService.Iface cmsInternalService;

    protected static final Transcoder TRANSCODER = new SerializingTranscoder();

    @Test
    public void testSaveEntity() throws Exception {
        ColumnPage page = new ColumnPage();
        page.setColumnId(1L);
        page.setLayoutId(2L);
        page.setName("ellios");
        page.setCreator("wangranyang");
        page.setUrl("http://www.lesports.com/abc");
        page.setStatus(PublishStatus.ONLINE);
        page.setData("zzzz");
        page.setVm("asdfasdfadsf");
        long id = cmsInternalService.saveEntity(ByteBuffer.wrap(TRANSCODER.encode(page).getFullData()),
                ByteBuffer.wrap(TRANSCODER.encode(ColumnPage.class).getFullData()), false);
        ByteBuffer bf = cmsInternalService.getEntityBytesById(id, ByteBuffer.wrap(TRANSCODER.encode(ColumnPage.class).getFullData()));
        ColumnPage pp = (ColumnPage)TRANSCODER.decode(new CachedData(bf.array()));
        cmsInternalService.deleteEntity(id, ByteBuffer.wrap(TRANSCODER.encode(ColumnPage.class).getFullData()));
        System.out.println("======================================================");
        System.out.println(id);
        System.out.println(pp);
        System.out.println("======================================================");
        Assert.assertTrue(id > 0);
    }

}