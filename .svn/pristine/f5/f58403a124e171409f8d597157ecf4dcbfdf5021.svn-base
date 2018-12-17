package com.lesports.qmt.msg.consumer;

import com.alibaba.fastjson.JSON;
import com.dianping.cat.Cat;
import com.dianping.cat.CatConstants;
import com.dianping.cat.message.Event;
import com.dianping.cat.message.Transaction;
import com.dianping.cat.message.internal.AbstractMessage;
import com.dianping.cat.message.internal.NullMessage;
import com.lesports.qmt.msg.context.MessageProcessContext;
import com.lesports.qmt.msg.core.LeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * Created by lufei1 on 2015/7/1.
 */
public class SmsSyncConsumer implements MessageListener {
    private static final Logger logger = LoggerFactory.getLogger(SmsSyncConsumer.class);

    @Resource
    private MessageProcessContext messageProcessContext;

    @Override
    public void onMessage(Message message) {
        try {
            String messageBody = null;
            if (message instanceof TextMessage) {
                TextMessage textMessage = (TextMessage) message;
                messageBody = textMessage.getText();
                logger.info("SmsSyncConsumer receive message {}", messageBody);
                LeMessage leMessage = JSON.parseObject(messageBody, LeMessage.class);
                messageProcessContext.process(leMessage);
            }
        } catch (Exception e) {
            logger.error("SmsSyncConsumer error", e);
        } finally {
            try {
                recordTransaction(message);
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
    }

    private void recordTransaction(Message message) {
        Transaction t = Cat.newTransaction(CatConstants.TYPE_CALL, "msg");
        try {
            //记录一个事件
            Cat.logEvent("Call.Consumer", "Consumer", Event.SUCCESS, "");
            //记录一个业务指标--次数
            Cat.logMetricForCount("consumerCount");

            TextMessage textMessage = (TextMessage) message;
            logger.info("message : " + textMessage.getText());
            t.setStatus(Transaction.SUCCESS);
        } catch (Exception e) {
            logger.error("------ Get cat msgtree error : ", e);
        } finally {
            t.complete();
        }
    }
}
