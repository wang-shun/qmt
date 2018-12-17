package com.lesports.qmt.msg.consumer;


import com.alibaba.fastjson.JSON;
import com.google.common.collect.ImmutableList;
import com.lesports.id.api.IdType;
import com.lesports.qmt.msg.context.MessageProcessContext;
import com.lesports.qmt.msg.core.BusinessType;
import com.lesports.qmt.msg.core.LeMessage;
import com.lesports.qmt.msg.core.LeMessageBuilder;
import com.lesports.qmt.msg.model.LetvMMSMessage;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * trunk.
 *
 * @author pangchuanxiao
 * @since 2016/4/9
 */
public class MmsTranscodeMessageConsumer implements MessageListener {
    private static final Logger logger = LoggerFactory.getLogger(MmsTranscodeMessageConsumer.class);

    @Resource
    private MessageProcessContext messageProcessContext;

    @Override
    public void onMessage(javax.jms.Message message) {
        try {
            String messageBody = null;
            if (message instanceof TextMessage) {
                TextMessage textMessage = (TextMessage) message;
                messageBody = textMessage.getText();
                logger.info("receive mms transcode message : {}", messageBody);
                LeMessage leMessage = purseMessage(messageBody, textMessage.getJMSMessageID());
                if (null != leMessage) {
                    messageProcessContext.process(leMessage);
                }
            }
        } catch (Exception e) {
            logger.error("SmsSyncConsumer error", e);
        }
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
