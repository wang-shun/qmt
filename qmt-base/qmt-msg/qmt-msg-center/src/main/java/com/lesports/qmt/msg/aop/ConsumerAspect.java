package com.lesports.qmt.msg.aop;

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


@Aspect
@Component
public class ConsumerAspect {
    private static final Logger LOG = LoggerFactory.getLogger(ConsumerAspect.class);

    /**
     * 定义一个切入点.
     */
    @Pointcut("execution(public * com.lesports.qmt.msg.consumer..*.*(..))")
    public void afterConsumer() {
    }

    /*@AfterReturning("afterConsumer()")
    public void afterConsumer(JoinPoint joinPoint) {
        // 处理完请求
        LOG.info("ConsumerAspect.afterConsumer()");

        // 记录下请求内容
        LOG.info("CLASS_METHOD : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        LOG.info("ARGS : " + Arrays.toString(joinPoint.getArgs()));
    }*/

    @AfterReturning("afterConsumer()")
    public void afterConsumer(JoinPoint joinPoint) {
        LOG.info("ConsumerAspect.afterConsumer()");

        Transaction t = Cat.newTransaction(CatConstants.TYPE_CONSUMER, "msg");
        try {
            //记录一个事件
            Cat.logEvent("Call.Consumer", "Consumer", Event.SUCCESS, "");
            //记录一个业务指标--次数
            Cat.logMetricForCount("visitCount");

            LOG.info("ARGS : " + Arrays.toString(joinPoint.getArgs()));
            t.setStatus(Transaction.SUCCESS);
        } catch (Exception e) {
            LOG.error("------ Get cat msgtree error : ", e);

            Event event = Cat.newEvent("HTTP_REST_CAT_ERROR", "Consumer");
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

    @Pointcut("execution(* com.lesports.qmt.msg.context.MessageProcessContext.*(..))")
    public void afterMessageProcess() {
    }

}
