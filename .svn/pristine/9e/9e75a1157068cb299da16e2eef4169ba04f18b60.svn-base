package com.lesports.qmt.msg.test.controller;

import com.google.common.collect.ImmutableList;
import com.lesports.id.api.IdType;
import com.lesports.qmt.msg.MsgCenterApplication;
import com.lesports.qmt.msg.core.BusinessType;
import com.lesports.qmt.msg.core.LeMessage;
import com.lesports.qmt.msg.core.LeMessageBuilder;
import com.lesports.qmt.msg.handler.impl.MmsVideoMessageHandler;
import org.apache.commons.lang3.math.NumberUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import javax.annotation.Resource;

/**
 * Created by zhangdeqiang on 2016/12/26.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = MsgCenterApplication.class)
public class MessageProcessTest {
    @Resource
    private MmsVideoMessageHandler mmsVideoMessageHandler;

    @Test
    public void testRead() {
        LeMessage leMessage = purseMessage("{\"callback\":\"\",\"checkDesc\":\"{\\\"prdCheckCode\\\":\\\"200\\\",\\\"prdCheckMsg\\\":{\\\"100\\\":\\\"检测完成. 视频已通过检测.\\\"},\\\"srcCheckCode\\\":200,\\\"srcCheckMsg\\\":\\\"检测完成. 视频已通过检测.\\\"}\",\"dsturl\":\"http://dispatcher.cloud.letv.com/dfs/23/video/ver_00_22-1089238898-avc-128324-aac-32035-31334-657712-864e67e4c364df5fbd38dcc456550429-1489134434996.mp4?tag=uts&dhost=123.125.37.76\",\"duration\":31334,\"from\":\"mms\",\"fullpath\":\"\",\"imgprefix\":\"\",\"imgprefix_http\":\"\",\"mmsid\":63418870,\"outkey\":\"28133274\",\"size\":657712,\"status\":25,\"time\":\"2017-03-10 16:27:43\",\"uploadid\":52852993,\"vtype\":9}", "testMessageId");

        boolean result = mmsVideoMessageHandler.handleSync(leMessage);

        System.out.println(result);
    }

    private LeMessage purseMessage(String mmsMessage, String messageId) {
        Assert.notNull(mmsMessage);

        BusinessType businessType = BusinessType.DATA_SYNC;
        return LeMessageBuilder.create().
                setEntityId(NumberUtils.toLong(messageId)).
                setIdType(IdType.MMS_VIDEO).
                setData(mmsMessage).
                setBusinessTypes(null, ImmutableList.of(businessType)).
                build();
    }

}
