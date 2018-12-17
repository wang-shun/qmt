package com.lesports.qmt.msg.web;

import com.google.common.collect.ImmutableList;
import com.lesports.id.api.IdType;
import com.lesports.qmt.msg.constant.Constants;
import com.lesports.qmt.msg.consumer.SmsSyncConsumer;
import com.lesports.qmt.msg.core.BusinessType;
import com.lesports.qmt.msg.core.LeMessage;
import com.lesports.qmt.msg.core.LeMessageBuilder;
import com.lesports.qmt.msg.core.MessageSource;
import com.lesports.qmt.msg.producer.SwiftMessageApis;
import com.lesports.qmt.msg.service.EpisodeService;
import com.lesports.spring.boot.exception.LeWebApplicationException;
import com.lesports.spring.boot.mvc.LeStatus;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

/**
 * Created by zhangdeqiang on 2016/11/18.
 */
@RestController
@RequestMapping(value = "/message")
public class MessageResource {

    private static final Logger LOG = LoggerFactory.getLogger(MessageResource.class);
    private static final Logger monitor = LoggerFactory.getLogger("monitor");

    @Value("${hedwig.zk.group}")
    private String group;
    @Value("${search.index.url}")
    private String url;
    @Resource
    private EpisodeService episodeService;

    @RequestMapping(value = "/hello")
    public String processMessage() {
        LOG.info("hello man");
        System.out.println(group);
        System.out.println(url);
        System.out.println(Constants.areaType);
        return "hello world!";
    }

    @RequestMapping(value = "/sync")
    public boolean sync(@QueryParam("entityId") long entityId,
                                  @QueryParam("idType") int idType,
                                  @QueryParam("content") String content) {
        try {
            LeMessage message = LeMessageBuilder.create().
                    setEntityId(entityId).
                    setIdType(IdType.findByValue(idType)).
                    setData(content).
                    setBusinessTypes(null, ImmutableList.of(BusinessType.DATA_SYNC)).
                    build();
            SwiftMessageApis.sendMsgAsync(message);
            return true;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/index")
    public boolean index(@QueryParam("entityId") long entityId,
                         @QueryParam("idType") String idType) {
        try {
            LeMessage message = LeMessageBuilder.create().
                    setEntityId(entityId).
                    setIdType(IdType.valueOf(idType)).
                    setBusinessTypes(null, ImmutableList.of(BusinessType.SEARCH_INDEX)).
                    build();
            SwiftMessageApis.sendMsgAsync(message);
            return true;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/monitor")
    public boolean index(@QueryParam("value") String value) {
        if (StringUtils.isBlank(value)) {
            LOG.error("{}","传值为空错误");
            return false;
        }

        monitor.info(value);
        return true;
    }

    @RequestMapping(value = "/aspect")
    public String aspect(@QueryParam("value") String value) throws JMSException {
        String json = "{\"businessTypes\":[\"DATA_SYNC\"],\"data\":\"{\\\"callback\\\":\\\"\\\",\\\"checkDesc\\\":\\\"{\\\\\\\"prdCheckCode\\\\\\\":\\\\\\\"200\\\\\\\",\\\\\\\"prdCheckMsg\\\\\\\":{\\\\\\\"100\\\\\\\":\\\\\\\"检测完成. 视频已通过检测.\\\\\\\"},\\\\\\\"srcCheckCode\\\\\\\":200,\\\\\\\"srcCheckMsg\\\\\\\":\\\\\\\"检测完成. 视频已通过检测.\\\\\\\"}\\\",\\\"dsturl\\\":\\\"http://dispatcher.cloud.letv.com/dfs/101/video/8ffa/0fec/ver_00_22-600201628-avc-807083-aac-64135-53400-5901780-90495a84e21ee63e4ddd7c12b06df6ed-1483065956051.mp4?tag=uts&dhost=10.180.92.51\\\",\\\"duration\\\":53400,\\\"from\\\":\\\"mms\\\",\\\"fullpath\\\":\\\"\\\",\\\"imgprefix\\\":\\\"\\\",\\\"imgprefix_http\\\":\\\"\\\",\\\"mmsid\\\":61641310,\\\"outkey\\\":\\\"26927106\\\",\\\"size\\\":5901780,\\\"status\\\":25,\\\"time\\\":\\\"2016-12-30 10:46:32\\\",\\\"uploadid\\\":500127316,\\\"vtype\\\":22}\",\"entityId\":0,\"idType\":\"MMS_VIDEO\",\"messageId\":\"201612301046321018530236935\"}";
        //LeMessage leMessage = purseMessage(json, UUID.randomUUID().toString());
        SmsSyncConsumer consumer = new SmsSyncConsumer();
        MyTextMessage textMessage = new MyTextMessage();
        textMessage.setText(json);
        consumer.onMessage(textMessage);
        episodeService.syncEpisode(111);

        LOG.info("=======================");
        return value;
    }

}
