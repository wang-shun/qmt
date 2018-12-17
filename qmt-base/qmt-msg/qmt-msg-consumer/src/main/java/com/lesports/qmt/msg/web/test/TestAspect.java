package com.lesports.qmt.msg.web.test;

import com.lesports.qmt.log.WebLogAnnotation;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Enumeration;

/**
 * 实现Web层的日志切面
 * Created by zhangdeqiang on 2016/12/20.
 */
//@Aspect
//@Component
public class TestAspect {
    private static final Logger LOGGER = LoggerFactory.getLogger("monitor");

    /**
     * 定义一个切入点.
     * ~ 第一个 * 代表任意修饰符及任意返回值.
     * ~ 第二个 * 任意包名
     * ~ 第三个 * 代表任意方法.
     * ~ 第四个 * 定义在web包或者子包
     * ~ 第五个 * 任意方法
     * ~ .. 匹配任意数量的参数.
     */
    @Pointcut("execution(public * com.lesports.qmt.*.web..*.*(..))")
    public void webLog() {
    }

    @AfterReturning("webLog()")
    public void doAfterReturning(JoinPoint joinPoint) {
        // 处理完请求
        LOGGER.info("WebLogAspect.doAfterReturning()");

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();


        // 记录下请求内容
        LOGGER.info("URL : " + request.getRequestURL().toString());
        LOGGER.info("URI : " + request.getRequestURI());
        LOGGER.info("HTTP_METHOD : " + request.getMethod());
        LOGGER.info("IP : " + request.getRemoteAddr());
        LOGGER.info("CLASS_METHOD : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        LOGGER.info("ARGS : " + Arrays.toString(joinPoint.getArgs()));
        //获取所有参数方法一：
        Enumeration<String> enu = request.getParameterNames();
        while (enu.hasMoreElements()) {
            String paraName = enu.nextElement();
            System.out.println(paraName + ": " + request.getParameter(paraName));
        }
        Annotation[] anns = joinPoint.getTarget().getClass().getAnnotations();
        for (Annotation ann : anns) {
            System.out.println(ann.annotationType());
            if (ann instanceof WebLogAnnotation) {
                WebLogAnnotation wl = ((WebLogAnnotation) ann);
                System.out.println(wl.ID_TYPE());
                System.out.println(wl.ID_ATTRIBUTE().toString());
            }
        }
        System.out.println();
    }
}
