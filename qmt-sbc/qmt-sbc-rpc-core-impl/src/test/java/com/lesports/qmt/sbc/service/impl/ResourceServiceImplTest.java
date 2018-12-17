package com.lesports.qmt.sbc.service.impl;

import com.lesports.AbstractIntegrationTest;
import com.lesports.qmt.sbc.service.ResourceService;
import org.junit.Test;

import javax.annotation.Resource;

public class ResourceServiceImplTest extends AbstractIntegrationTest {

    @Resource
    private ResourceService resourceService;

    @Test
    public void testSave() throws Exception {
      /*  QmtResource resource = new QmtResource();
        resource.setId(1000L);
        resource.setUpdateType(ResourceUpdateType.AUTO);
        resource.setName("你好");
        ImageUrlExt url = new ImageUrlExt();
        url.setIndex(0);
        url.setPath("/test.png");
        url.setUrl("http://11/test.png");
        resource.setImageUrl(url);
        CompoundTag tag = new CompoundTag();
        tag.setName("combo");
        tag.setTagIds(Lists.newArrayList(1L,2L,3L));
        QmtResource.ResourceItem item = new QmtResource.ResourceItem();
        item.setItemId(0L);
        item.setName("gogo");
        item.addTag(tag);
        item.setType(ResourceContentType.TEXT_IMAGE);
        resource.addItem(item);

        QmtResource.ResourceLink link = new QmtResource.ResourceLink();
        link.setName("link");
        link.setUrl("gogo");
        link.setOrder(1);
        resource.addLink(link);

        resourceService.save(resource);

        QmtResource re = resourceService.findOne(1000L);
        System.out.println("=================================================");
        System.out.println(re);
        System.out.println("=================================================");
        Assert.assertNotNull(re);*/
    }

    public void testFindOne() throws Exception {

    }

    public void testDelete() throws Exception {

    }

    public void testGetTResourcesByIds() throws Exception {

    }

    public void testGetTResourceById() throws Exception {

    }
}