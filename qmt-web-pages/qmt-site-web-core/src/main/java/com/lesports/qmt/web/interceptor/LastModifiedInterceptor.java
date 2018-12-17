package com.lesports.qmt.web.interceptor;

import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * User: ellios
 * Time: 15-8-22 : 下午9:35
 */
public class LastModifiedInterceptor extends HandlerInterceptorAdapter {

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if (!request.getRequestURI().startsWith("/static") && !response.containsHeader(HttpHeaders.LAST_MODIFIED)) {
            response.addDateHeader(HttpHeaders.LAST_MODIFIED, System.currentTimeMillis());
        }
    }
}
