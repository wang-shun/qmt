package com.lesports.qmt.msg.producer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lesports.qmt.msg.core.LeMessage;
import com.lesports.qmt.msg.core.MsgProducerConstants;
import org.apache.qpid.client.AMQConnectionFactory;
import org.apache.qpid.url.URLSyntaxException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.core.JmsTemplate;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static com.lesports.qmt.msg.core.MsgProducerConstants.MESSAGE_PRODUCER_ON;
import static com.lesports.qmt.msg.core.MsgProducerConstants.SWIFTQ_MESSAGE_LECOLUD_URL;

/**
 * Created by lufei1 on 2015/7/1.
 */
public class CloudSwiftMessageApis {
    private static final Logger LOG = LoggerFactory.getLogger(CloudSwiftMessageApis.class);
    private static final String dest = "lesports.message.center.direct/lesports.message.lecloud";
    private static ExecutorService executorService = Executors.newFixedThreadPool(5);
    private static JmsTemplate leJmsTemplate;

    static {
        synchronized (CloudSwiftMessageApis.class) {
            try {
                AMQConnectionFactory factory = new AMQConnectionFactory(SWIFTQ_MESSAGE_LECOLUD_URL);
                CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory(factory);
                if (null != leJmsTemplate) {
                    leJmsTemplate = new JmsTemplate();
                    leJmsTemplate.setConnectionFactory(cachingConnectionFactory);
                }
            } catch (URLSyntaxException e) {
                LOG.warn("fail to create connection for {}. Ignore this if you don't send message to cloud msg center.", MsgProducerConstants.SWIFTQ_CONNECTION_URL);
            }
        }
    }

    private static boolean sendMsgSync(String dest, LeMessage message) {
        try {
            if (!MESSAGE_PRODUCER_ON) {
                LOG.info("not send message {} to {} as turn is {}.", JSONObject.toJSONString(message), dest, MESSAGE_PRODUCER_ON);
                return false;
            }
            if (null == leJmsTemplate) {
                LOG.info("not send message {} to {} as msg template is null.", JSONObject.toJSONString(message), dest);
                return false;
            }
            leJmsTemplate.convertAndSend(dest, JSON.toJSONString(message));
            LOG.info("send message {} to {}", JSONObject.toJSONString(message), dest);
        } catch (Exception e) {
            LOG.error("fail to send message {} to {}.", JSONObject.toJSONString(message), dest, e);
            return false;
        }
        return true;
    }

    public static Future<Boolean> sendMsgAsync(final LeMessage message) {
        return executorService.submit(() -> sendMsgSync(dest, message));
    }
}
