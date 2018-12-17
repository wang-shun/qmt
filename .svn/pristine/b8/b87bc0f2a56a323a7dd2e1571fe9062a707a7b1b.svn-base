package com.lesports.qmt.msg.context;

import com.alibaba.fastjson.JSON;
import com.dianping.cat.Cat;
import com.dianping.cat.message.Event;
import com.lesports.id.api.IdType;
import com.lesports.qmt.log.LogContent;
import com.lesports.qmt.log.LoggerHandler;
import com.lesports.qmt.msg.constant.Constants;
import com.lesports.qmt.msg.core.BusinessType;
import com.lesports.qmt.msg.core.AreaType;
import com.lesports.qmt.msg.core.LeMessage;
import com.lesports.qmt.msg.handler.CacheHandler;
import com.lesports.qmt.msg.handler.IMessageHandler;
import com.lesports.qmt.msg.producer.area.CnSwiftMessageApis;
import com.lesports.qmt.msg.producer.area.HkSwiftMessageApis;
import com.lesports.qmt.msg.producer.area.UsSwiftMessageApis;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Created by zhangdeqiang on 2016/11/10.
 */
@Component
public class MessageProcessContext {
    private static final Logger LOG = LoggerFactory.getLogger(MessageProcessContext.class);

    @Resource
    private CacheHandler cacheHandler;
    @Resource
    private HandlerContext handlerContext;

    public void process(final LeMessage message) {
        if (message == null || CollectionUtils.isEmpty(message.getBusinessTypes())) {
            return;
        }

        if (CollectionUtils.isNotEmpty(message.getAreaTypes())) {
            if (message.getAreaTypes().contains(Constants.areaType)) {
                doHandler(message);
            }
            if (!AreaType.CN.equals(Constants.areaType) &&
                    (message.getAreaTypes().contains(AreaType.CN) || message.getAreaTypes().contains(AreaType.ALL))) {
                CnSwiftMessageApis.sendMsgAsync(message);
            }
            if (!AreaType.HK.equals(Constants.areaType) &&
                    (message.getAreaTypes().contains(AreaType.HK) || message.getAreaTypes().contains(AreaType.ALL))) {
                // 转发消息
                HkSwiftMessageApis.sendMsgAsync(message);
            }
            if (!AreaType.US.equals(Constants.areaType) &&
                    (message.getAreaTypes().contains(AreaType.US) || message.getAreaTypes().contains(AreaType.ALL))) {
                UsSwiftMessageApis.sendMsgAsync(message);
            }
        } else { //默认本地区处理
            doHandler(message);
        }
    }

    private void doHandler(LeMessage message) {
        boolean result;
        IMessageHandler handler = handlerContext.get(message.getIdType().getValue());
        if (message.getBusinessTypes().contains(BusinessType.DATA_SYNC)) {
            result = handler.handleSync(message);
            if (!result) { //同步数据是主业务，消息不消费，后续需要建立异常任务处理队列
                throw new RuntimeException("数据同步失败异常");
            }
        }
        if (message.getBusinessTypes().contains(BusinessType.SEARCH_INDEX)) {
            result = handler.handleSearchIndex(message);
            if (!result) { //索引失败
                //写入cat
                String eventName = message.getEntityId() + "|" + message.getEntityName();
                Cat.logEvent("IndexAlert", eventName, Event.SUCCESS, null);
                Cat.logError("[" + eventName + "]创建索引失败:" + JSON.toJSONString(message), null);
            }
        }
        if (message.getBusinessTypes().contains(BusinessType.CBASE_DELETE)) {
            String cacheKey = message.getContent().getKey();
            cacheHandler.deleteWebCbaseCache(cacheKey);
        }
        if (message.getBusinessTypes().contains(BusinessType.CMS_CBASE_DELETE)) {
            String cacheKey = message.getContent().getKey();
            cacheHandler.deleteCmsWebCbaseCache(cacheKey);
        }
    }

    private void alarmOnIndexFailed(LeMessage message) {
        LogContent logContent = new LogContent();
        logContent.setEntryId("" + message.getEntityId());
        logContent.setContent(message);
        logContent.setMethodPath("com.lesports.qmt.msg.context.MessageProcessContext.doHandler");

        logContent.setIdType(message.getIdType()); //索引类型
        logContent.setSendTo("zhangdeqiang@le.com"); //多个邮件用逗号分隔
        logContent.setSubject("报警邮件--创建索引失败"); //邮件标题
        logContent.setMemo("创建索引失败\n" + message); //邮件内容
        LoggerHandler.sendEMail(logContent);
    }

}
