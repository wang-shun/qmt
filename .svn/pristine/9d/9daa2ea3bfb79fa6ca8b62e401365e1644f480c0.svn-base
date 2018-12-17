package com.lesports.qmt.msg.handler.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Predicate;
import com.lesports.id.api.IdType;
import com.lesports.qmt.msg.core.LeMessage;
import com.lesports.qmt.msg.handler.AbstractMessageHandler;
import com.lesports.qmt.msg.handler.IMessageHandler;
import com.lesports.qmt.msg.util.IndexResult;
import com.lesports.qmt.msg.util.LeExecutors;
import com.lesports.qmt.msg.util.WebResult;
import com.lesports.utils.http.RestTemplateFactory;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

/**
 * lesports-projects.
 *
 * @author: pangchuanxiao
 * @since: 2015/7/10
 */
@Component("mmsVideoMessageHandler")
public class MmsVideoMessageHandler extends AbstractMessageHandler implements IMessageHandler {
    private static final Logger LOG = LoggerFactory.getLogger(MmsVideoMessageHandler.class);

    private static final RestTemplate TEMPLATE = RestTemplateFactory.getTemplate();
    @Value("${qmt.web.uri}")
    private String qmtWebUri;

    @Override
    protected Logger getLogger() {
        return LOG;
    }

    @Override

    public boolean handleSync(final LeMessage message) {
        if (message == null) {
            return false;
        }
        LOG.info("MmsVideoMessage :{}", JSON.toJSONString(message));

        IdType idType = message.getIdType();
        final String notifyTranscodeUrl = qmtWebUri + "/qmt/v2/transcode/notifyTranscode?caller=1001";
        try {
            MultiValueMap<String, String> param = new LinkedMultiValueMap<>();
            param.add("mmsMessage",message.getData());
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add("content-type", "application/x-www-form-urlencoded");
            HttpEntity httpEntity = new HttpEntity(param, httpHeaders);

            Predicate<String> function = mmsMessage -> {
                ResponseEntity<WebResult> responseEntity = TEMPLATE.exchange(notifyTranscodeUrl, HttpMethod.PUT, httpEntity, WebResult.class, StringUtils.lowerCase(idType.toString()), mmsMessage);
                LOG.info("Url:{} ,idType {},mmsMessage {}, result : {}.", notifyTranscodeUrl, idType, mmsMessage, JSONObject.toJSONString(responseEntity));
                return isWebResultSuccess(responseEntity);
            };
            return LeExecutors.executeWithRetry(3, 1000, function, null, "mmsMessage request qmt");
        } catch (Exception e) {
            String errorInfo = "接口调用异常：Url:{} ,idType {}" + notifyTranscodeUrl + idType;
            LOG.error("接口调用异常：{}---{}", e.getMessage(), errorInfo, e);
            this.alarmOnHandleSync(message, "pangchuanxiao@le.com,zhangdeqiang@le.com", errorInfo, MmsVideoMessageHandler.class);
            return false;
        }

    }

}
