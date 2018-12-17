package com.lesports.qmt.log.filter;

import com.dianping.cat.Cat;
import com.dianping.cat.message.Event;
import com.dianping.cat.message.Transaction;
import com.dianping.cat.message.internal.AbstractMessage;
import com.dianping.cat.message.internal.NullMessage;
import com.lesports.qmt.log.common.CatConstants;
import com.lesports.qmt.log.common.CatContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 服务提供侧串联消息树
 */
@Component
public class HttpCatCrossFilter implements Filter {
    private static final Logger logger = LoggerFactory.getLogger(HttpCatCrossFilter.class);

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        String requestURI = request.getRequestURI();

        Transaction t = Cat.newTransaction(CatConstants.CROSS_SERVER, requestURI);

        try {
            Cat.Context context = new CatContext();
            context.addProperty(Cat.Context.ROOT, request.getHeader(Cat.Context.ROOT));
            context.addProperty(Cat.Context.PARENT, request.getHeader(Cat.Context.PARENT));
            context.addProperty(Cat.Context.CHILD, request.getHeader(Cat.Context.CHILD));
            Cat.logRemoteCallServer(context);
            this.createProviderCross(request, t);

            filterChain.doFilter(req, resp);
            t.setStatus(Transaction.SUCCESS);
        } catch (Exception e) {
            logger.error("------ Get cat msgtree error : ", e);

            Event event = null;
            event = Cat.newEvent("HTTP_REST_CAT_ERROR", requestURI);
            event.setStatus(e);
            completeEvent(event);
            t.addChild(event);
            t.setStatus(e.getClass().getSimpleName());
        } finally {
            t.complete();
        }

    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {
    }

    @Override
    public void destroy() {
    }

    /**
     * 串联provider端消息树
     *
     * @param request
     * @param t
     */
    private void createProviderCross(HttpServletRequest request, Transaction t) {

        Event crossAppEvent = Cat.newEvent(CatConstants.PROVIDER_SERVICE_APP, request.getHeader(CatConstants.CLIENT_APPLICATION_NAME));    //clientName
        Event crossServerEvent = Cat.newEvent(CatConstants.PROVIDER_SERVICE_CLIENT, request.getRemoteAddr());    //clientIp
        crossAppEvent.setStatus(Event.SUCCESS);
        crossServerEvent.setStatus(Event.SUCCESS);
        completeEvent(crossAppEvent);
        completeEvent(crossServerEvent);
        t.addChild(crossAppEvent);
        t.addChild(crossServerEvent);
    }

    private void completeEvent(Event event) {
        if (event != NullMessage.EVENT) {
            AbstractMessage message = (AbstractMessage) event;
            message.setCompleted(true);
        }
    }

}
