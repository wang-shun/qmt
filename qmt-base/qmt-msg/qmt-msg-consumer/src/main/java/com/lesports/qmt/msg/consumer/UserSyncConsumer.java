package com.lesports.qmt.msg.consumer;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.ImmutableList;
import com.lesports.qmt.msg.context.MessageProcessContext;
import com.lesports.qmt.msg.core.AreaType;
import com.lesports.qmt.msg.core.BusinessType;
import com.lesports.qmt.msg.core.LeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * @author pangchuanxiao
 */
public class UserSyncConsumer implements MessageListener {

    private static final Logger logger = LoggerFactory.getLogger(UserSyncConsumer.class);

    @Resource
    private MessageProcessContext messageProcessContext;

    @Override
    public void onMessage(Message message) {
        try {
            String messageBody = null;
            if (message instanceof TextMessage) {
                TextMessage textMessage = (TextMessage) message;
                messageBody = textMessage.getText();
//                logger.info("receive message {}", messageBody);
                LeMessage leMessage = JSON.parseObject(messageBody, LeMessage.class);
                leMessage.setAreaTypes(ImmutableList.of(AreaType.CN)); //只在大陆区域处理
                leMessage.setBusinessTypes(ImmutableList.of(BusinessType.DATA_SYNC));
                messageProcessContext.process(leMessage);
            }
        } catch (Exception e) {
            logger.error("UserSyncConsumer error", e);
        }
    }
}
