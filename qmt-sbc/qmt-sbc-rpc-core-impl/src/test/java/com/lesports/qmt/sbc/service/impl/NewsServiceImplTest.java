package com.lesports.qmt.sbc.service.impl;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.lesports.api.common.ImageUrlExt;
import com.lesports.api.common.Platform;
import com.lesports.qmt.sbc.api.common.RelatedType;
import com.lesports.qmt.sbc.api.dto.TopicItemType;
import com.lesports.qmt.sbc.model.NewsImage;
import com.lesports.qmt.sbc.model.RelatedItem;
import com.lesports.qmt.sbc.model.TopicItem;
import com.lesports.qmt.sbc.model.TopicItemPackage;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.Map;

/**
 * Created by denghui on 2016/10/24.
 */
public class NewsServiceImplTest {

    @Test
    public void imageJson() {
        NewsImage newsImage = new NewsImage();
        newsImage.setName("图片标题");
        newsImage.setDesc("图片描述");
        newsImage.setCover(true);
        newsImage.setShowOrder(1);
        Map<String, ImageUrlExt> ext = Maps.newHashMap();
        ImageUrlExt ext11 = new ImageUrlExt();
        ext11.setUrl("http://i2.letvimg.com/lc04_sms/201611/03/16/04/16");
        ext11.setPath("/image2/20161103/16/fe6876db-0103-44d2-af1a-a6df10bbafa5.jpg");
        ImageUrlExt ext169 = new ImageUrlExt();
        ext169.setUrl("http://i2.letvimg.com/lc04_sms/201611/03/16/04/16");
        ext169.setPath("/image2/20161103/16/fe6876db-0103-44d2-af1a-a6df10bbafa5.jpg");
        ext.put("11", ext11);
        ext.put("169", ext169);

        System.out.println(ext);
    }

    @Test
    public void testJson() {
        List<Long> x = JSON.parseArray("[1,2,3]", Long.class);
        List<Platform> xx = JSON.parseArray("[\"PC\",\"MSITE\"]", Platform.class);
        return;
    }

    @Test
    public void test() {
        List<TopicItemPackage> itemPackages = Lists.newArrayList();
        TopicItemPackage itemPackage = new TopicItemPackage();
        itemPackage.setName("包1");
        itemPackage.setOrder(1);
        TopicItem topicItem = new TopicItem();
        topicItem.setItemId(1536209l);
        topicItem.setName("内容1");
        topicItem.setType(TopicItemType.IMAGE_ALBUM);
        itemPackage.addTopicItem(topicItem);
        TopicItem topicItem2 = new TopicItem();
        topicItem2.setItemId(1511209l);
        topicItem2.setName("内容2");
        topicItem2.setType(TopicItemType.RICHTEXT);
        itemPackage.addTopicItem(topicItem2);

        TopicItemPackage itemPackage2 = new TopicItemPackage();
        itemPackage2.setName("包2");
        itemPackage2.setOrder(2);
        TopicItem topicItem3 = new TopicItem();
        topicItem3.setItemId(1536209l);
        topicItem3.setName("内容3");
        topicItem3.setType(TopicItemType.IMAGE_ALBUM);
        itemPackage2.addTopicItem(topicItem3);

        itemPackages.add(itemPackage);
        itemPackages.add(itemPackage2);

        //[{"items":[{"id":1536209,"name":"内容1","type":"IMAGE_ALBUM"},{"id":1511209,"name":"内容2","type":"RICHTEXT"}],"name":"包1","order":1},{"items":[{"id":1536209,"name":"内容3","type":"IMAGE_ALBUM"}],"name":"包2","order":2}]
        System.out.println(JSON.toJSONString(itemPackages));
    }
}
