package com.lesports.qmt.sbc.web.log;

import com.alibaba.fastjson.JSON;
import com.lesports.cas.core.CasUser;
import com.lesports.qmt.log.LogContent;
import com.lesports.qmt.log.LoggerHandler;
import com.lesports.qmt.log.WebLogAnnotation;
import com.lesports.spring.security.authentication.LeCasAuthenticationToken;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;
import java.util.Map;

/**
 * 实现Web层的日志切面
 * Created by zhangdeqiang on 2016/12/20.
 */
@Aspect
@Component
public class WebLogAspect {
    private static final Logger LOG = LoggerFactory.getLogger(WebLogAspect.class);

    /**
     * 定义一个切入点.
     * ~ 第一个 * 代表任意修饰符及任意返回值.
     * ~ 第二个 * 任意包名
     * ~ 第三个 * 代表任意方法.
     * ~ 第四个 * 定义在web包或者子包
     * ~ 第五个 * 任意方法
     * ~ .. 匹配任意数量的参数.
     */
    @Pointcut("execution(public * com.lesports.qmt.*.web..*.*(..))" +
            " && !execution(public * com.lesports.qmt.*.web..*.get*(..))" +
            " && !execution(public * com.lesports.qmt.*.web..*.find*(..))" +
            " && !execution(public * com.lesports.qmt.*.web..*.list*(..))" +
            " && !execution(public * com.lesports.qmt.*.web..*.query*(..))")
    public void webLog() {
    }

    @AfterReturning("webLog()")
    public void doAfterReturning(JoinPoint joinPoint) {
        // 处理完请求
        LOG.info("WebLogAspect.doAfterReturning()");

        Annotation[] anns = joinPoint.getTarget().getClass().getAnnotations();
        for (Annotation ann : anns) {
            if (ann instanceof WebLogAnnotation) {
                LogContent logContent = new LogContent();
                WebLogAnnotation wl = ((WebLogAnnotation) ann);
                ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
                HttpServletRequest request = attributes.getRequest();
                Map<String, String[]> requestParamMap = request.getParameterMap();

                this.putOperator(logContent);
                this.putEntryId(logContent, wl, requestParamMap);
                logContent.setMethodPath(joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
                logContent.setIdType(wl.ID_TYPE());
                logContent.setContent(requestParamMap);
                logContent.setMemo(wl.MEMO());

                LoggerHandler.out(logContent);
                break;
            }
        }
    }

    private void putEntryId(LogContent logContent, WebLogAnnotation wl, Map<String, String[]> requestParamMap) {
        String idValue = null;
        if (requestParamMap != null) {
            for (String idAttr : wl.ID_ATTRIBUTE()) {
                String[] idValues = requestParamMap.get(idAttr);
                if (idValues != null) {
                    idValue = idValues[0];
                    break;
                }
            }
        }
        logContent.setEntryId(idValue);
    }

    private void putOperator(LogContent logContent) {
        LeCasAuthenticationToken authenticationToken = (LeCasAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        if (authenticationToken == null) {
            LOG.error("no authentication token");
            return;
        }
        String username = authenticationToken.getName();
        if (StringUtils.isNotEmpty(username)) {
            logContent.setOperator(username);
        } else {
            LOG.error("用户登录信息不存在，{}", JSON.toJSONString(authenticationToken));
        }
    }
}
