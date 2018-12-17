package com.lesports.qmt.msg.test.controller;

import com.google.common.collect.ImmutableList;
import com.lesports.id.api.IdType;
import com.lesports.qmt.msg.MsgCenterApplication;
import com.lesports.qmt.msg.consumer.SmsSyncConsumer;
import com.lesports.qmt.msg.core.BusinessType;
import com.lesports.qmt.msg.core.LeMessage;
import com.lesports.qmt.msg.core.LeMessageBuilder;
import com.lesports.qmt.msg.web.MyTextMessage;
import org.apache.commons.lang3.math.NumberUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

/**
 * Created by zhangdeqiang on 2017/1/19.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = MsgCenterApplication.class)
public class ConsumerTest {

    @Test
    public void testConsumer() throws Exception {
        String json = "{\"businessTypes\":[\"DATA_SYNC\"],\"data\":\"{\\\"callback\\\":\\\"\\\",\\\"checkDesc\\\":\\\"{\\\\\\\"prdCheckCode\\\\\\\":\\\\\\\"200\\\\\\\",\\\\\\\"prdCheckMsg\\\\\\\":{\\\\\\\"100\\\\\\\":\\\\\\\"检测完成. 视频已通过检测.\\\\\\\"},\\\\\\\"srcCheckCode\\\\\\\":200,\\\\\\\"srcCheckMsg\\\\\\\":\\\\\\\"检测完成. 视频已通过检测.\\\\\\\"}\\\",\\\"dsturl\\\":\\\"http://dispatcher.cloud.letv.com/dfs/101/video/8ffa/0fec/ver_00_22-600201628-avc-807083-aac-64135-53400-5901780-90495a84e21ee63e4ddd7c12b06df6ed-1483065956051.mp4?tag=uts&dhost=10.180.92.51\\\",\\\"duration\\\":53400,\\\"from\\\":\\\"mms\\\",\\\"fullpath\\\":\\\"\\\",\\\"imgprefix\\\":\\\"\\\",\\\"imgprefix_http\\\":\\\"\\\",\\\"mmsid\\\":61641310,\\\"outkey\\\":\\\"26927106\\\",\\\"size\\\":5901780,\\\"status\\\":25,\\\"time\\\":\\\"2016-12-30 10:46:32\\\",\\\"uploadid\\\":500127316,\\\"vtype\\\":22}\",\"entityId\":0,\"idType\":\"MMS_VIDEO\",\"messageId\":\"201612301046321018530236935\"}";
        //LeMessage leMessage = purseMessage(json, UUID.randomUUID().toString());
        SmsSyncConsumer consumer = new SmsSyncConsumer();
        MyTextMessage textMessage = new MyTextMessage();
        textMessage.setText(json);
        consumer.onMessage(textMessage);

        System.out.println("===============================");
    }

    private LeMessage purseMessage(String json, String messageId) {
        Assert.notNull(json);

        BusinessType businessType = BusinessType.DATA_SYNC;
        return LeMessageBuilder.create().
                setEntityId(NumberUtils.toLong(messageId)).
                setIdType(IdType.MMS_VIDEO).
                setData(json).
                setBusinessTypes(null, ImmutableList.of(businessType)).
                build();
    }
}
