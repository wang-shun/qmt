package com.lesports.qmt.msg.handler;

import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Function;
import com.lesports.api.common.CallerParam;
import com.lesports.qmt.log.LogContent;
import com.lesports.qmt.log.LoggerHandler;
import com.lesports.qmt.msg.constant.Constants;
import com.lesports.qmt.msg.core.LeMessage;
import com.lesports.qmt.msg.service.DefaultService;
import com.lesports.qmt.msg.util.WebResult;
import org.apache.commons.lang3.StringUtils;
import org.apache.thrift.TBase;
import org.slf4j.Logger;
import org.springframework.http.ResponseEntity;

import javax.annotation.Resource;


/**
 * lesports-projects.
 *
 * @author: pangchuanxiao
 * @since: 2015/7/10
 */
public abstract class AbstractMessageHandler implements IMessageHandler {
    @Resource
    private DefaultService defaultService;

    private static final String SUBJECT = "报警邮件--数据同步失败";

    protected boolean execute(long matchId, String name, Function<Long, Boolean> function) {
        try {
            boolean result = function.apply(matchId);
            if (!result) {
                getLogger().error("fail to delete {} cache for {}.", name, matchId);
            }
            return result;
        } catch (Exception e) {
            getLogger().error("fail to delete {} cache for {} error : {}", name, matchId, e.getMessage(), e);
        }
        return false;
    }

    protected boolean execute(TBase entity, String name, Function<TBase, Boolean> function) {
        try {
            boolean result = function.apply(entity);
            if (!result) {
                getLogger().error("fail to {} for {}.", name, JSONObject.toJSONString(entity));
            }
            return result;
        } catch (Exception e) {
            getLogger().error("fail to {} for {} error : {}", name, JSONObject.toJSONString(entity), e.getMessage(), e);
        }
        return false;
    }

    public boolean isWebResultSuccess(ResponseEntity<WebResult> result) {
        if (null == result) {
            return false;
        }
        if (null == result.getBody()) {
            return false;
        }
        WebResult ir = result.getBody();
        if (StringUtils.isEmpty(ir.getCode()) || !"A00000".equals(ir.getCode())) {
            return false;
        }
        return true;
    }

    // 数据同步操作时主动发起邮件报警
    protected void alarmOnHandleSync(LeMessage message, String sendTo, String memo, Class handleClass) {
        LogContent logContent = new LogContent();
        logContent.setEntryId("" + message.getEntityId());
        logContent.setContent(message);
        logContent.setMethodPath((handleClass != null ? handleClass.getName() : "") + ".handleSync");

        logContent.setIdType(message.getIdType()); //索引类型
        logContent.setSendTo(sendTo); //多个邮件用逗号分隔
        logContent.setSubject(SUBJECT); //邮件标题
        logContent.setMemo(memo); //邮件内容
        LoggerHandler.sendEMail(logContent);
    }

    protected abstract Logger getLogger();

    @Override
    public boolean handleSync(LeMessage message) {
        return defaultService.handleSync(message);
    }

    @Override
    public boolean handleSearchIndex(LeMessage message) {
        return defaultService.handleIndex(message.getEntityId(), message.getIdType());
    }

    protected CallerParam getCaller() {
        CallerParam caller = new CallerParam();
        caller.setCallerId(1001L); // not used callerId
        caller.setCountry(Constants.COUNTRY);
        caller.setLanguage(Constants.LANGUAGE);
        return caller;
    }

}
