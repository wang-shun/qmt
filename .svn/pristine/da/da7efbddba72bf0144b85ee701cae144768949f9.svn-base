package com.lesports.qmt.sbc.web.log;

import com.dianping.cat.Cat;
import com.dianping.cat.message.Event;
import com.dianping.cat.message.Transaction;
import com.dianping.cat.message.internal.AbstractMessage;
import com.dianping.cat.message.internal.NullMessage;
import com.lesports.qmt.log.common.CatConstants;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;


//@Aspect
//@Component
public class RpcAspect {
    private static final Logger LOG = LoggerFactory.getLogger(RpcAspect.class);
    private final String CROSS_NAME = "thrift";

    // TODO: 2017/2/7 修改成实现类动态切面
    @Pointcut("execution(* com.lesports.qmt.service.support.AbstractQmtService.*(..))")
    public void afterThrift() {
    }

    @AfterReturning("afterThrift()")
    public void afterThrift(JoinPoint joinPoint) {
        LOG.info("RpcAspect.afterThrift");

        Transaction t = Cat.newTransaction(CatConstants.CROSS_SERVER, CROSS_NAME);
        try {
            //记录一个事件
            Cat.logEvent("Call.Thrift", CROSS_NAME, Event.SUCCESS, "");
            //记录一个业务指标--次数
            Cat.logMetricForCount("visitCount");

            LOG.info("ARGS : " + Arrays.toString(joinPoint.getArgs()));
            t.setStatus(Transaction.SUCCESS);
        } catch (Exception e) {
            LOG.error("------ Get cat msgtree error : ", e);

            Event event = Cat.newEvent("HTTP_THRIFT_CAT_ERROR", CROSS_NAME);
            event.setStatus(e);
            completeEvent(event);
            t.addChild(event);
            t.setStatus(e.getClass().getSimpleName());
        } finally {
            t.complete();
        }
    }

    private void completeEvent(Event event) {
        if (event != NullMessage.EVENT) {
            AbstractMessage message = (AbstractMessage) event;
            message.setCompleted(true);
        }
    }
}
