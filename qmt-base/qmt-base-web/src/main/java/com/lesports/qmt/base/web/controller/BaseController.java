package com.lesports.qmt.base.web.controller;

import com.alibaba.fastjson.JSON;
import com.lesports.qmt.base.web.entry.EmailParam;
import com.lesports.qmt.base.web.entry.ResponseJson;
import com.lesports.qmt.base.web.util.EmailLocalCache;
import com.lesports.qmt.base.web.util.MailUtil;
import com.lesports.spring.boot.exception.LeWebApplicationException;
import com.lesports.spring.boot.mvc.LeStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import java.util.concurrent.ExecutionException;

/**
 * Created by zhangdeqiang on 2017/2/16.
 */
@RestController
@RequestMapping(value = "/base/web")
public class BaseController {
    private static final Logger LOG = LoggerFactory.getLogger(BaseController.class);

    @Autowired
    private MailUtil mailUtil;

    @RequestMapping(value = "/sendMail", method = RequestMethod.POST)
    public Object sendMail(@ModelAttribute EmailParam param) {
        try {
            LOG.info("sendMail, emailParam:{}", JSON.toJSONString(param));
            String[] value = param.getValue().split(",");
            String subject = value[0];
            String content = value[1];
            // 按照类型判断一段时间不能发送超过多少封
            Integer times = EmailLocalCache.getCacheByKey(this.getKey(param.getType()));
            if (times > 1000) {
                String errorInfo = "邮件发送失败，原因：业务类型【" + param.getType() + "】一小时内发送邮件超过阈值1000封";
                LOG.error(errorInfo);
                this.sendWarnMail(param, errorInfo);
                throw new LeWebApplicationException(errorInfo, LeStatus.FROZEN_RESOURCE);
            }
            mailUtil.sendHtmlMail(param.getTo(), param.getRe(), subject, content);
            EmailLocalCache.incrementByKey(this.getKey(param.getType()));
            return new ResponseJson<>();
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            return ResponseJson.fail(e.getMessage());
        }
    }

    private void sendWarnMail(@ModelAttribute EmailParam param, String errorInfo) throws ExecutionException, MessagingException {
        Integer warnTimes = EmailLocalCache.getCacheByKey(this.getKey(param.getType()));
        if (warnTimes < 1) {
            mailUtil.sendHtmlMail("zhangdeqiang@le.com", "", "邮件发送异常提醒", errorInfo);
            EmailLocalCache.incrementByKey("warn-"  + this.getKey(param.getType()));
        }
    }

    private String getKey(Integer type) {
        String result = "type";
        if (type != null) {
            result = result + type;
        }
        return result;
    }
}
